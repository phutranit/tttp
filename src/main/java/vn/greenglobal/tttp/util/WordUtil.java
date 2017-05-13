package vn.greenglobal.tttp.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBElement;

import org.docx4j.XmlUtils;
import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.docx4j.wml.ContentAccessor;
import org.docx4j.wml.Tbl;
import org.docx4j.wml.Text;
import org.docx4j.wml.Tr;
import org.springframework.util.FileCopyUtils;

public final class WordUtil {

	public static void replaceTable(String[] placeholders, List<Map<String, String>> textToAdd,
			WordprocessingMLPackage template, int footerRows) {
		List<Object> tables = getAllElementFromObject(template.getMainDocumentPart(), Tbl.class);

		// 1. find the table
		Tbl tempTable = getTemplateTable(tables, placeholders[0]);
		if (tempTable == null) {
			return;
		}
		List<Object> rows = getAllElementFromObject(tempTable, Tr.class);
		// first row is header, second row is content
		if (rows.size() >= 2) {
			// this is our template row
			Tr templateRow = (Tr) rows.get(1);
			for (Map<String, String> replacements : textToAdd) {
				// 2 and 3 are done in this method
				addRowToTable(tempTable, templateRow, replacements, footerRows);
			}

			// 4. remove the template row
			tempTable.getContent().remove(templateRow);
			if (textToAdd.isEmpty()) {
				tempTable.getContent().remove(rows.get(0));
			}
		}
	}

	public static List<Object> getAllElementFromObject(final Object value, Class<?> toSearch) {
		List<Object> result = new ArrayList<Object>();
		Object obj = value;
		if (obj instanceof JAXBElement) {
			obj = ((JAXBElement<?>) obj).getValue();
		}
		if (obj.getClass().equals(toSearch)) {
			result.add(obj);
		} else if (obj instanceof ContentAccessor) {
			List<?> children = ((ContentAccessor) obj).getContent();
			for (Object child : children) {
				result.addAll(getAllElementFromObject(child, toSearch));
			}

		}
		return result;
	}

	private static Tbl getTemplateTable(List<Object> tables, String templateKey) {
		for (Iterator<Object> iterator = tables.iterator(); iterator.hasNext();) {
			Object tbl = iterator.next();
			List<?> textElements = getAllElementFromObject(tbl, Text.class);
			for (Object text : textElements) {
				Text textElement = (Text) text;
				if (textElement.getValue() != null && textElement.getValue().equals(templateKey)) {
					return (Tbl) tbl;
				}
			}
		}
		return null;
	}

	private static void addRowToTable(Tbl reviewtable, Tr templateRow, Map<String, String> replacements,
			int footerRows) {
		Tr workingRow = XmlUtils.deepCopy(templateRow);
		List<?> textElements = getAllElementFromObject(workingRow, Text.class);
		for (Object object : textElements) {
			Text text = (Text) object;
			String replacementValue = replacements.get(text.getValue());
			if (replacementValue != null) {
				text.setValue(replacementValue);
			}
		}
		reviewtable.getContent().add(reviewtable.getContent().size() - footerRows, workingRow);
	}
	

	
	public static void exportWord(HttpServletResponse response, String pathFile, HashMap<String, String> mappings) {
		try {
			WordprocessingMLPackage wordMLPackage;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream inputStream = null;
			File file = new File(pathFile);

			if (!file.exists()) {
				String errorMessage = "Sorry. The file you are looking for does not exist";
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
				return;
			}

			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

			wordMLPackage = WordprocessingMLPackage.load(file);
			VariablePrepare.prepare(wordMLPackage);
			wordMLPackage.getMainDocumentPart().variableReplace(mappings);
			wordMLPackage = wordMLPackage.getMainDocumentPart().convertAltChunks();
			wordMLPackage.save(out);

			response.setContentLength((int) out.size());
			out.close();

			inputStream = new BufferedInputStream(new ByteArrayInputStream(out.toByteArray()));
			FileCopyUtils.copy(inputStream, response.getOutputStream());

			response.flushBuffer();
			inputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
