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

import org.apache.commons.lang3.StringUtils;
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

import vn.greenglobal.tttp.model.Don;
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
		// #.###,##
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
			if (flag == 1) {
				cellStyle.setBorderTop((short) 2);
				font.setFontHeightInPoints((short) fontSize);
				cellStyle.setFont(font);
			} else {
				cellStyle.setBorderTop((short) 1);
			}
			if (flag == 2) {
				cellStyle.setBorderBottom((short) 2);
				font.setFontHeightInPoints((short) fontSize);
				cellStyle.setFont(font);
			} else {
				cellStyle.setBorderBottom((short) 1);
			}

			if (i == (end - 1)) {
				cellStyle.setBorderRight((short) 2);
			} else {
				cellStyle.setBorderRight((short) 1);
			}
			if (flag == 3) {
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
	public static CellStyle setBorderAndFont(final Workbook workbook, final int borderSize, final boolean isTitle,
			final int fontSize, final String fontColor, final String textAlign) {
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
		} else if (fontColor.equals("ORANGE")) {
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
		c.setCellStyle(setBorderAndFont(wb, 0, true, 11, "", "LEFT"));
	}

	public static void exportDanhSachTiepDanThuongXuyen(HttpServletResponse response, String fileName, String sheetName,
			List<SoTiepCongDan> list, String title) throws IOException {
		// New Workbook
		Workbook wb = new XSSFWorkbook();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
		try {

			CellStyle cellCenter = setBorderAndFont(wb, 1, true, 11, "", "CENTER");
			CellStyle cellLeft = setBorderAndFont(wb, 1, false, 11, "", "LEFT");
			Cell c = null;
			// New Sheet
			Sheet sheet1 = null;
			sheet1 = wb.createSheet(sheetName);
			sheet1.createFreezePane(0, 3);
			// Row and column indexes
			int idx = 0;
			// Generate column headings
			Row row;
			row = sheet1.createRow(idx);
			c = row.createCell(0);
			c.setCellValue(title.toUpperCase());
			c.setCellStyle(setBorderAndFont(wb, 0, true, 14, "BLUE", "CENTER"));
			row.setHeight((short) 800);
			sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

			// set column width
			sheet1.setColumnWidth(0, 6 * 256);
			sheet1.setColumnWidth(1, 17 * 256);
			sheet1.setColumnWidth(2, 25 * 256);
			sheet1.setColumnWidth(3, 25 * 256);
			sheet1.setColumnWidth(4, 15 * 256);
			sheet1.setColumnWidth(5, 15 * 256);
			sheet1.setColumnWidth(6, 15 * 256);
			sheet1.setColumnWidth(7, 10 * 256);
			sheet1.setColumnWidth(8, 15 * 256);
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
			row.setHeight((short) 800);
			c = row.createCell(0);
			c.setCellValue("STT");
			c.setCellStyle(cellCenter);
			c = row.createCell(1);
			c.setCellValue("Ngày tiếp nhận");
			c.setCellStyle(cellCenter);
			c = row.createCell(2);
			c.setCellValue("Người đứng đơn");
			c.setCellStyle(cellCenter);
			c = row.createCell(3);
			c.setCellValue("Tóm tắt nội dung");
			c.setCellStyle(cellCenter);
			c = row.createCell(4);
			c.setCellValue("Phân loại đơn/số người");
			c.setCellStyle(cellCenter);
			c = row.createCell(5);
			c.setCellValue("Cơ quan đã giải quyết");
			c.setCellStyle(cellCenter);
			c = row.createCell(6);
			c.setCellValue("Hướng xử lý");
			c.setCellStyle(cellCenter);
			c = row.createCell(7);
			c.setCellValue("Lượt tiếp");
			c.setCellStyle(cellCenter);
			c = row.createCell(8);
			c.setCellValue("Cán bộ tiếp dân");
			c.setCellStyle(cellCenter);
			int i = 1;
			for (SoTiepCongDan tcd : list) {
				row = sheet1.createRow(idx);
				row.setHeight((short) 500);
				c = row.createCell(0);
				c.setCellValue(i);
				c.setCellStyle(cellCenter);
				c = row.createCell(1);
				c.setCellValue(tcd.getNgayTiepDan().format(formatter));
				c.setCellStyle(cellCenter);
				c = row.createCell(2);
				c.setCellValue(tcd.getDon().getListNguoiDungDon().size() > 0 ? tcd.getDon().getListNguoiDungDon().get(0).getCongDan().getTenDiaChiSoCMND() : "");
				c.setCellStyle(cellLeft);
				c = row.createCell(3);
				c.setCellValue(tcd.getDon().getNoiDung());
				c.setCellStyle(cellLeft);
				c = row.createCell(4);
				c.setCellValue(tcd.getDon().getLoaiDon().getText() + "/" + tcd.getDon().getSoNguoi());
				c.setCellStyle(cellCenter);
				c = row.createCell(5);
				c.setCellValue(tcd.getDon().getCoQuanDaGiaiQuyet() != null
						? tcd.getDon().getCoQuanDaGiaiQuyet().getTen() : "");
				c.setCellStyle(cellLeft);
				c = row.createCell(6);
				c.setCellValue(tcd.getHuongXuLy() != null ? tcd.getHuongXuLy().getText() : "");
				c.setCellStyle(cellCenter);
				c = row.createCell(7);
				c.setCellValue(tcd.getSoLuotTiepStr());
				c.setCellStyle(cellCenter);
				c = row.createCell(8);
				c.setCellValue(tcd.getCanBoTiepDan() != null ? tcd.getCanBoTiepDan().getHoVaTen() : "");
				c.setCellStyle(cellCenter);
				i++;
				idx++;
			}

			idx++;
			// createNoteRow(wb, sheet1, idx);
			idx++;

			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			wb.write(fileOut);
			String mimeType = "application/octet-stream";
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition",
					String.format("attachment; filename=\"" + fileName + ".xlsx" + "\""));
			response.setContentLength((int) fileOut.size());

			InputStream inputStream = new ByteArrayInputStream(fileOut.toByteArray());
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			inputStream.close();
		} finally {
			wb.close();

		}
	}
	
	public static void exportDanhSachTiepDanLanhDao(HttpServletResponse response, String fileName, String sheetName,
			List<SoTiepCongDan> list, String title) throws IOException {
		// New Workbook
		Workbook wb = new XSSFWorkbook();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
		try {

			CellStyle cellCenter = setBorderAndFont(wb, 1, true, 11, "", "CENTER");
			CellStyle cellLeft = setBorderAndFont(wb, 1, false, 11, "", "LEFT");
			Cell c = null;
			// New Sheet
			Sheet sheet1 = null;
			sheet1 = wb.createSheet(sheetName);
			sheet1.createFreezePane(0, 3);
			// Row and column indexes
			int idx = 0;
			// Generate column headings
			Row row;
			row = sheet1.createRow(idx);
			c = row.createCell(0);
			c.setCellValue(title.toUpperCase());
			c.setCellStyle(setBorderAndFont(wb, 0, true, 14, "BLUE", "CENTER"));
			row.setHeight((short) 800);
			sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

			// set column width
			sheet1.setColumnWidth(0, 6 * 256);
			sheet1.setColumnWidth(1, 17 * 256);
			sheet1.setColumnWidth(2, 25 * 256);
			sheet1.setColumnWidth(3, 25 * 256);
			sheet1.setColumnWidth(4, 15 * 256);
			sheet1.setColumnWidth(5, 15 * 256);
			sheet1.setColumnWidth(6, 15 * 256);
			sheet1.setColumnWidth(7, 10 * 256);
			sheet1.setColumnWidth(8, 15 * 256);
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
			row.setHeight((short) 800);
			c = row.createCell(0);
			c.setCellValue("STT");
			c.setCellStyle(cellCenter);
			c = row.createCell(1);
			c.setCellValue("Ngày tiếp công dân");
			c.setCellStyle(cellCenter);
			c = row.createCell(2);
			c.setCellValue("Lãnh đạo tiếp dân");
			c.setCellStyle(cellCenter);
			c = row.createCell(3);
			c.setCellValue("Người đứng đơn");
			c.setCellStyle(cellCenter);
			c = row.createCell(4);
			c.setCellValue("Tóm tắt nội dung");
			c.setCellStyle(cellCenter);
			c = row.createCell(5);
			c.setCellValue("Phân loại đơn/Số người");
			c.setCellStyle(cellCenter);
			c = row.createCell(6);
			c.setCellValue("Cơ quan đã giải quyết");
			c.setCellStyle(cellCenter);
			c = row.createCell(7);
			c.setCellValue("Kết quả tiếp dân");
			c.setCellStyle(cellCenter);
			c = row.createCell(8);
			c.setCellValue("Tình trạng xử lý");
			c.setCellStyle(cellCenter);
			
			int i = 1;
			for (SoTiepCongDan tcd : list) {
				row = sheet1.createRow(idx);
				row.setHeight((short) 500);
				c = row.createCell(0);
				c.setCellValue(i);
				c.setCellStyle(cellCenter);
				c = row.createCell(1);
				c.setCellValue(tcd.getNgayTiepDan().format(formatter));
				c.setCellStyle(cellCenter);
				c = row.createCell(2);
				c.setCellValue(tcd.getCanBoTiepDan() != null ? tcd.getCanBoTiepDan().getHoVaTen() : "");
				c.setCellStyle(cellCenter);
				c = row.createCell(3);
				c.setCellValue(tcd.getDon().getListNguoiDungDon().get(0).getCongDan().getTenDiaChiSoCMND());
				c.setCellStyle(cellLeft);
				c = row.createCell(4);
				c.setCellValue(tcd.getDon().getNoiDung());
				c.setCellStyle(cellLeft);
				c = row.createCell(5);
				c.setCellValue(tcd.getDon().getLoaiDon().getText() + "/" + tcd.getDon().getSoNguoi());
				c.setCellStyle(cellCenter);
				c = row.createCell(6);
				c.setCellValue(tcd.getDon().getCoQuanDaGiaiQuyet() != null
						? tcd.getDon().getCoQuanDaGiaiQuyet().getTen() : "");
				c.setCellStyle(cellLeft);
				c = row.createCell(7);
				c.setCellValue(tcd.getHuongGiaiQuyetTCDLanhDao() != null ? tcd.getHuongGiaiQuyetTCDLanhDao().getText() : "");
				c.setCellStyle(cellCenter);
				c = row.createCell(8);
				c.setCellValue(tcd.getTinhTrangXuLyLanhDaoStr());
				c.setCellStyle(cellCenter);
				
				i++;
				idx++;
			}

			idx++;
			// createNoteRow(wb, sheet1, idx);
			idx++;

			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			wb.write(fileOut);
			String mimeType = "application/octet-stream";
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition",
					String.format("attachment; filename=\"" + fileName + ".xlsx" + "\""));
			response.setContentLength((int) fileOut.size());

			InputStream inputStream = new ByteArrayInputStream(fileOut.toByteArray());
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			inputStream.close();
		} finally {
			wb.close();

		}
	}
	
	
	public static void exportDanhSachXuLyDon(HttpServletResponse response, String fileName, String sheetName,
			List<Don> list, String title) throws IOException {
		// New Workbook
		Workbook wb = new XSSFWorkbook();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
		try {

			CellStyle cellCenter = setBorderAndFont(wb, 1, true, 11, "", "CENTER");
			CellStyle cellLeft = setBorderAndFont(wb, 1, false, 11, "", "LEFT");
			Cell c = null;
			// New Sheet
			Sheet sheet1 = null;
			sheet1 = wb.createSheet(sheetName);
			sheet1.createFreezePane(0, 3);
			// Row and column indexes
			int idx = 0;
			// Generate column headings
			Row row;
			row = sheet1.createRow(idx);
			c = row.createCell(0);
			c.setCellValue(title.toUpperCase());
			c.setCellStyle(setBorderAndFont(wb, 0, true, 14, "BLUE", "CENTER"));
			row.setHeight((short) 800);
			sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));

			// set column width
			sheet1.setColumnWidth(0, 6 * 256);
			sheet1.setColumnWidth(1, 17 * 256);
			sheet1.setColumnWidth(2, 25 * 256);
			sheet1.setColumnWidth(3, 25 * 256);
			sheet1.setColumnWidth(4, 15 * 256);
			sheet1.setColumnWidth(5, 15 * 256);
			sheet1.setColumnWidth(6, 15 * 256);
			sheet1.setColumnWidth(7, 10 * 256);
			sheet1.setColumnWidth(8, 15 * 256);
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
			row.setHeight((short) 800);
			c = row.createCell(0);
			c.setCellValue("STT");
			c.setCellStyle(cellCenter);
			c = row.createCell(1);
			c.setCellValue("Mã đơn");
			c.setCellStyle(cellCenter);
			c = row.createCell(2);
			c.setCellValue("Ngày tiếp nhận");
			c.setCellStyle(cellCenter);
			c = row.createCell(3);
			c.setCellValue("Người đứng đơn");
			c.setCellStyle(cellCenter);
			c = row.createCell(4);
			c.setCellValue("Nguồn đơn");
			c.setCellStyle(cellCenter);
			c = row.createCell(5);
			c.setCellValue("Tóm tắc nội dung/Hạn xử lý");
			c.setCellStyle(cellCenter);
			c = row.createCell(6);
			c.setCellValue("Phân loại đơn/Số người");
			c.setCellStyle(cellCenter);
			c = row.createCell(7);
			c.setCellValue("Cơ quan đã giải quyết");
			c.setCellStyle(cellCenter);
			c = row.createCell(8);
			c.setCellValue("Cán bộ xử lý");
			c.setCellStyle(cellCenter);
			int i = 1;
			for (Don don : list) {
				row = sheet1.createRow(idx);
				row.setHeight((short) 500);
				c = row.createCell(0);
				c.setCellValue(i);
				c.setCellStyle(cellCenter);
				c = row.createCell(1);
				c.setCellValue(StringUtils.isNotEmpty(don.getMa()) ? don.getMa() : "");
				c.setCellStyle(cellCenter);
				c = row.createCell(2);
				c.setCellValue(don.getNgayTiepNhan().format(formatter));
				c.setCellStyle(cellLeft);
				c = row.createCell(3);
				String tenNDD = "";
				if(don.getListNguoiDungDon().size() > 0) {
					tenNDD = don.getListNguoiDungDon().get(0).getCongDan().getHoVaTen();
				}
				c.setCellValue(tenNDD);
				c.setCellStyle(cellLeft);
				c = row.createCell(4);
				c.setCellValue(don.getNguonDonText());
				c.setCellStyle(cellCenter);
				c = row.createCell(5);
				c.setCellValue(don.getNoiDung() + "/" +don.getThoiHanXuLyDon());
				c.setCellStyle(cellLeft);
				c = row.createCell(6);
				c.setCellValue(don.getLoaiDon().getText() + "/" +don.getSoNguoi());
				c.setCellStyle(cellCenter);
				c = row.createCell(7);
				c.setCellValue(don.getCoQuanDaGiaiQuyet() != null ? don.getCoQuanDaGiaiQuyet().getTen() : "");
				c.setCellStyle(cellCenter);
				c = row.createCell(8);
				c.setCellValue(don.getCanBoXuLy().getHoVaTen());
				c.setCellStyle(cellCenter);
				i++;
				idx++;
			}

			idx++;
			// createNoteRow(wb, sheet1, idx);
			idx++;

			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			wb.write(fileOut);
			String mimeType = "application/octet-stream";
			response.setContentType(mimeType);
			response.setHeader("Content-Disposition",
					String.format("attachment; filename=\"" + fileName + ".xlsx" + "\""));
			response.setContentLength((int) fileOut.size());

			InputStream inputStream = new ByteArrayInputStream(fileOut.toByteArray());
			FileCopyUtils.copy(inputStream, response.getOutputStream());
			response.flushBuffer();
			inputStream.close();
		} finally {
			wb.close();

		}
	}
}