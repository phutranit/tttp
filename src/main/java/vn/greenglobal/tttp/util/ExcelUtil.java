package vn.greenglobal.tttp.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.util.FileCopyUtils;

import vn.greenglobal.tttp.model.SoTiepCongDan;

public class ExcelUtil {

	private static ExcelUtil instance;

	public static ExcelUtil getInStance() {
		if (instance == null) {
			instance = new ExcelUtil();
		}
		return instance;
	}
	
	
	
	public static String formatDouble(double number) {
		//#.###,##
		final DecimalFormatSymbols decimalFormatSymbols = new DecimalFormatSymbols();
		decimalFormatSymbols.setDecimalSeparator(',');
		decimalFormatSymbols.setGroupingSeparator('.');
		final DecimalFormat decimalFormat = new DecimalFormat("#.###", decimalFormatSymbols);
		return decimalFormat.format(number);
	}
	
	@SuppressWarnings({ "unused", "deprecation" })
	private static void setBorderMore(Workbook wb, Row row, Cell c, int begin, int end, int fontSize) {
		final CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setBorderLeft((short) 1); 
		Font font = wb.createFont();
		font.setFontName("Times New Roman");
		
	}
	
	@SuppressWarnings({ "unused", "deprecation" })
	private static void setBorderMore(int flag, Workbook wb, Row row, Cell c, int begin, int end, int fontSize) {
		final CellStyle cellStyle = wb.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		cellStyle.setBorderLeft((short) 1); 
		
		Font font = wb.createFont();
		font.setFontName("Times New Roman");
		for (int i = begin; i < end; i++) {
			Cell c1 = row.createCell(i);
			if (flag==1) {
				cellStyle.setBorderTop((short) 2);
				font.setFontHeightInPoints((short) fontSize);
				cellStyle.setFont(font);
			} else {
				cellStyle.setBorderTop((short) 1);
			}
			if (flag==2) {
				cellStyle.setBorderBottom((short) 2); 
				font.setFontHeightInPoints((short) fontSize);
				cellStyle.setFont(font);
			} else {
				cellStyle.setBorderBottom((short) 1); 
			}
			
			if (i == (end-1)) {
				cellStyle.setBorderRight((short) 2);
			} else {
				cellStyle.setBorderRight((short) 1); 
			}
			if (flag==3) {
				cellStyle.setBorderTop((short) 1);
				cellStyle.setBorderBottom((short) 1); 
				font.setFontHeightInPoints((short) fontSize);
				cellStyle.setFont(font);
				cellStyle.setBorderRight((short) 1); 
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			} 
			c1.setCellStyle(cellStyle);
		}
	}
	
	@SuppressWarnings("deprecation")
	public static CellStyle setBorderAndFont(final Workbook workbook,
			final int borderSize, final boolean isTitle, final int fontSize,
			final String fontColor, final String textAlign) {
		final CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(true);
		

		cellStyle.setBorderTop((short) borderSize); // single line border
		cellStyle.setBorderBottom((short) borderSize); // single line border
		cellStyle.setBorderLeft((short) borderSize); // single line border
		cellStyle.setBorderRight((short) borderSize); // single line border
		cellStyle.setAlignment(CellStyle.VERTICAL_CENTER);

		if (textAlign.equals("RIGHT")) {
			cellStyle.setAlignment(CellStyle.ALIGN_RIGHT);
		} else if (textAlign.equals("CENTER")) {
			cellStyle.setAlignment(CellStyle.ALIGN_CENTER);
		} else if (textAlign.equals("LEFT")) {
			cellStyle.setAlignment(CellStyle.ALIGN_LEFT);
		}
		cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
		
		final Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		if (isTitle) {
			
			font.setBoldweight(Font.BOLDWEIGHT_BOLD);
			
		} 
		if (fontColor.equals("RED")) {
			font.setColor(Font.COLOR_RED);
		} else if (fontColor.equals("BLUE")) {
			font.setColor(HSSFColor.BLUE.index);
		} else if (fontColor.equals("ORANGE")){
			font.setColor(HSSFColor.ORANGE.index);
		} else {
			
		}
		font.setFontHeightInPoints((short) fontSize);
		cellStyle.setFont(font);

		return cellStyle;
	}

	public static void createNoteRow(Workbook wb, Sheet sheet1, int idx) {
		Cell c;
		org.apache.poi.ss.usermodel.Row row;
		row = sheet1.createRow(idx);
		c = row.createCell(1);
        c.setCellValue("* Theo HCP");
        c.setCellStyle(setBorderAndFont(wb, 0, true, 11,"", "LEFT"));
	}
	
	
	
	
	public static void exportDanhSachTiepDan(HttpServletResponse response, String fileName, String sheetName, List<SoTiepCongDan> list, 
			String title) throws IOException {
		// New Workbook
		Workbook wb = new XSSFWorkbook();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
		try {
		Cell c = null;
		// New Sheet
		Sheet sheet1 = null;
		sheet1 = wb.createSheet(sheetName);
		sheet1.createFreezePane(0, 4);
		// Row and column indexes
		int idx = 0;
		// Generate column headings
		Row row;
		row = sheet1.createRow(idx);
		c = row.createCell(0);
		c.setCellValue(title.toUpperCase());
		c.setCellStyle(setBorderAndFont(wb, 0, true, 14, "BLUE", "CENTER"));
		sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 10));
		
		// set column width
		sheet1.setColumnWidth(0, 6 * 256);
		sheet1.setColumnWidth(1, 17 * 256);
		sheet1.setColumnWidth(2, 25 * 256);
		sheet1.setColumnWidth(3, 25 * 256);
		sheet1.setColumnWidth(4, 15 * 256);
		sheet1.setColumnWidth(5, 15 * 256);
		sheet1.setColumnWidth(6, 15 * 256);
		sheet1.setColumnWidth(7, 15 * 256);
		sheet1.setColumnWidth(8, 15 * 256);
		sheet1.setColumnWidth(9, 20 * 256);
		sheet1.setColumnWidth(9, 15 * 256);
		// Generate rows header of grid
		idx++;
		row = sheet1.createRow(idx);
		c = row.createCell(0);
		c.setCellValue("");
		c.setCellStyle(setBorderAndFont(wb, 0, false, 12, "", "LEFT"));
		sheet1.addMergedRegion(new CellRangeAddress(1, 1, 0, 6));
		idx++;
		row = sheet1.createRow(idx);
		idx++;
		
		c = row.createCell(0);			
		c.setCellValue("STT");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));
		sheet1.addMergedRegion(new CellRangeAddress(idx-1, idx, 0, 0));
		c = row.createCell(1);
		c.setCellValue("Ngày tiếp");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));
		sheet1.addMergedRegion(new CellRangeAddress(idx-1, idx, 1, 1));
		c = row.createCell(2);
		c.setCellValue("Họ và tên - Địa chỉ - CMND/Hộ chiếu của công dân");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));	
		sheet1.addMergedRegion(new CellRangeAddress(idx-1, idx, 2, 2));
		c = row.createCell(3);
		c.setCellValue("Nội dung đơn thư/vụ việc");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));
		sheet1.addMergedRegion(new CellRangeAddress(idx-1, idx, 3, 3));
		c = row.createCell(4);
		c.setCellValue("Phân loại đơn, số người");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));
		sheet1.addMergedRegion(new CellRangeAddress(idx-1, idx, 4, 4));
		c = row.createCell(5);
		c.setCellValue("Cơ quan đã giải quyết");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));
		sheet1.addMergedRegion(new CellRangeAddress(idx-1, idx, 5, 5));
		c = row.createCell(6);
		c.setCellValue("Hướng xử lý");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));
		sheet1.addMergedRegion(new CellRangeAddress(idx-1, idx-1, 6, 8));
		c = row.createCell(9);
		c.setCellValue("Theo dõi kết quả giải quyết");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));
		sheet1.addMergedRegion(new CellRangeAddress(idx-1, idx, 9,9));
		c = row.createCell(10);
		c.setCellValue("Ghi chú");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));
		sheet1.addMergedRegion(new CellRangeAddress(idx-1, idx, 10, 10));
		
		row = sheet1.createRow(idx);
		row.setHeight((short)1000);
		
		c = row.createCell(6);
		c.setCellValue("Thụ lý để giải quyết");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));
		
		c = row.createCell(7);
		c.setCellValue("Trả đơn và hướng dẫn");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));
		c = row.createCell(8);
		c.setCellValue("Chuyển đơn đến cơ quan, tổ chức, đơn vị có thẩm quyền");
		c.setCellStyle(setBorderAndFont(wb, 1, true, 11, "","CENTER"));
		idx++;
		
		
		int i = 1;
		for (SoTiepCongDan tcd: list) {
			row = sheet1.createRow(idx);
			c = row.createCell(0);			
			c.setCellValue(i);
			c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT"));				
			c = row.createCell(1);
			c.setCellValue(tcd.getNgayTiepDan().format(formatter));
			c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT"));				
			c = row.createCell(2);
			c.setCellValue(tcd.getDon().getListNguoiDungDon().get(0).getCongDan().getTenDiaChiSoCMND());
			c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT"));		
			c = row.createCell(3);			
			c.setCellValue(tcd.getDon().getNoiDung());
			c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT"));	
			c = row.createCell(4);			
			c.setCellValue(tcd.getDon().getLoaiDon().getText() + "/" + tcd.getDon().getSoNguoi());
			c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT"));	
			c = row.createCell(5);			
			c.setCellValue(tcd.getDon().getCoQuanDaGiaiQuyet() != null ? tcd.getDon().getCoQuanDaGiaiQuyet().getTen() : "");
			c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT"));
			c = row.createCell(6);			
			c.setCellValue("");
			c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT"));
			c = row.createCell(7);			
			c.setCellValue("");
			c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT"));
			c = row.createCell(8);			
			c.setCellValue("");
			c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT"));
			c = row.createCell(9);			
			c.setCellValue("");
			c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT"));
			c = row.createCell(10);			
			c.setCellValue("");
			c.setCellStyle(setBorderAndFont(wb, 1, false, 11, "","LEFT"));
			i++;
			idx++;
		}
		
		idx++;
		//createNoteRow(wb, sheet1, idx);
		idx++;
		
		ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
		wb.write(fileOut);
		//System.out.println("fileName: " + fileName);
		String mimeType = "application/octet-stream";
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + fileName + ".xlsx" + "\""));
		response.setContentLength((int) fileOut.size());
		
		InputStream inputStream = new ByteArrayInputStream(fileOut.toByteArray());
		FileCopyUtils.copy(inputStream , response.getOutputStream());
		response.flushBuffer();
        inputStream.close();
		} finally {
			wb.close();
			
		}
	}
}