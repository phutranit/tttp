package vn.greenglobal.tttp.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

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
			sheet1.addMergedRegion(new CellRangeAddress(0, 0, 0, 9));

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
			c.setCellValue("Tình trạng xử lý/Kết quả xử lý");
			c.setCellStyle(cellCenter);
			c = row.createCell(9);
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
				c.setCellValue(don.getNoiDung() + " / " +don.getThoiHanXuLyDon());
				c.setCellStyle(cellLeft);
				c = row.createCell(6);
				c.setCellValue(don.getLoaiDon().getText() + " / " +don.getSoNguoi() +" người");
				c.setCellStyle(cellCenter);
				c = row.createCell(7);
				c.setCellValue(don.getCoQuanDaGiaiQuyet() != null ? don.getCoQuanDaGiaiQuyet().getTen() : "");
				c.setCellStyle(cellCenter);
				c = row.createCell(8);
				c.setCellValue(don.getTrangThaiDonText() + " / " +(don.getQuyTrinhXuLyText() != "" ? don.getQuyTrinhXuLyText() : "Chưa có kết quả"));
				c.setCellStyle(cellCenter);
				c = row.createCell(9);
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
	
	@SuppressWarnings("unused")
	public static void exportTongHopBaoCaoTiepCongDan(HttpServletResponse response, String fileName, String sheetName,
			List<Map<String, Object>> maSos,  String tuNgay, String denNgay, String title) throws IOException {
		// New Workbook
		Workbook wb = new XSSFWorkbook();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
		
		String soLieuStr = String.format("(số liệu tính từ ngày %s đến ngày %s)", 
				StringUtils.isNotBlank(tuNgay) ? Utils.getMocThoiGianLocalDateTimeStr(Utils.fixTuNgay(tuNgay)) : "", 
				StringUtils.isNotBlank(denNgay) ? Utils.getMocThoiGianLocalDateTimeStr(Utils.fixTuNgay(denNgay)) : "");
		
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
			row.setHeight((short)350);
			c = row.createCell(0);
			
			c.setCellValue("BỘ, NGÀNH (UBND TỈNH, THÀNH PHỐ)...");
			c.setCellStyle(setBorderAndFont(wb, 0, true, 11, "BLACK", "RIGHT"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 0, 6));
			
			idx += 4;
			row = sheet1.createRow(idx);
			c = row.createCell(0);
			c.setCellValue("TỔNG HỢP KẾT QUẢ TIẾP CÔNG DÂN");
			c.setCellStyle(setBorderAndFont(wb, 0, true, 11, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 0, 31));
			
			idx ++;
			row = sheet1.createRow(idx);
			c = row.createCell(0);
			c.setCellValue(soLieuStr);
			c.setCellStyle(setBorderAndFont(wb, 0, true, 11, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 0, 31));
			idx ++;
			
			int width = (int) (10 * 256);
			// set column width
			sheet1.setColumnWidth(0, 25 * 256);
			sheet1.setColumnWidth(1, width);
			sheet1.setColumnWidth(2, width);
			sheet1.setColumnWidth(3, width);
			sheet1.setColumnWidth(4, width);
			sheet1.setColumnWidth(5, width);
			sheet1.setColumnWidth(6, width);
			sheet1.setColumnWidth(7, width);
			sheet1.setColumnWidth(8, width);
			sheet1.setColumnWidth(9, width);
			sheet1.setColumnWidth(10, width);
			sheet1.setColumnWidth(11, width);
			sheet1.setColumnWidth(12, width);
			sheet1.setColumnWidth(13, width);
			sheet1.setColumnWidth(14, width);
			sheet1.setColumnWidth(15, width);
			sheet1.setColumnWidth(16, width);
			sheet1.setColumnWidth(17, width);
			sheet1.setColumnWidth(18, width);
			sheet1.setColumnWidth(19, width);
			sheet1.setColumnWidth(20, width);
			sheet1.setColumnWidth(21, width);
			sheet1.setColumnWidth(22, width);
			sheet1.setColumnWidth(23, width);
			sheet1.setColumnWidth(24, width);
			sheet1.setColumnWidth(25, width);
			sheet1.setColumnWidth(26, width);
			sheet1.setColumnWidth(27, width);
			sheet1.setColumnWidth(28, width);
			sheet1.setColumnWidth(29, width);
			sheet1.setColumnWidth(30, width);
			sheet1.setColumnWidth(31, width);
//			// Generate rows header of grid
			
			CellStyle cellStyleFalse12BlackCenter = setBorderAndFont(wb, 1, false, 12, "BLACK", "CENTER");
			CellStyle cellStyleFalse9BlackCenter = setBorderAndFont(wb, 1, false, 9, "BLACK", "CENTER");
			CellStyle cellStyleFalse10BlackCenter = setBorderAndFont(wb, 1, false, 10, "BLACK", "CENTER");
			CellStyle cellStyleFalse11BlackCenter = setBorderAndFont(wb, 1, false, 11, "BLACK", "CENTER");
			CellStyle cellStyleFalse8BlackCenter = setBorderAndFont(wb, 1, false, 8, "BLACK", "CENTER");
			CellStyle cellStyleFalse7BlackCenter = setBorderAndFont(wb, 1, false, 7, "BLACK", "CENTER");
			
			idx += 3;
			row = sheet1.createRow(idx);
			row.setHeight((short)1000);
			c = row.createCell(0);
			setBorderMore(0,wb,row,c,0,32,12);
			c.setCellValue("Đơn vị");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 3, 0, 0));
			
			c = row.createCell(1);
			c.setCellValue("Tiếp thường xuyên");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 1, 8));
			
			c = row.createCell(9);
			c.setCellValue("Tiếp định kỳ và đột xuất của Lãnh đạo");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 9, 16));
			
			c = row.createCell(17);
			c.setCellValue("Nội dung tiếp công dân (số vụ việc)");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 17, 26));
			
			c = row.createCell(27);
			c.setCellValue("Kết quả qua tiếp dân (số vụ việc)");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 27, 30));
			
			c = row.createCell(31);
			c.setCellValue("Ghi chú");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 3, 31, 31));
			
			idx++;
			row = sheet1.createRow(idx);
			row.setHeight((short)1000);
			c = row.createCell(1);
			setBorderMore(0,wb,row,c,0,32,0);
			c.setCellValue("Lượt");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 2, 1, 1));
			
			c = row.createCell(2);
			c.setCellValue("Người");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 2, 2, 2));
			
			//group - 3
			c = row.createCell(3);
			c.setCellValue("Vụ việc");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 3, 4));
			
			//group - 5
			c = row.createCell(5);
			c.setCellValue("Đoàn đông người");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 5, 8));
			
			//tiep cong dan dinh ky dot xuat
			c = row.createCell(9);
			c.setCellValue("Lượt");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 2, 9, 9));
			
			c = row.createCell(10);
			c.setCellValue("Người");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 2, 10, 10));
			
			//group - 11
			c = row.createCell(11);
			c.setCellValue("Vụ việc");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 11, 12));
			
			//group - 13
			c = row.createCell(13);
			c.setCellValue("Đoàn đông người");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 13, 16));
			
			//group - 17
			c = row.createCell(17);
			c.setCellValue("Khiếu nại");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 17, 22));
			
			//group - 23
			c = row.createCell(23);
			c.setCellValue("Tố cáo");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 23, 25));
			
			//group - 26
			c = row.createCell(26);
			c.setCellValue("Phản ánh, kiến nghị, khác");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 2, 26, 26));
			
			//group - 27
			c = row.createCell(27);
			c.setCellValue("Chưa được giải quyết");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 2, 27, 27));
			
			//group - 28
			c = row.createCell(28);
			c.setCellValue("Đã được giải quyết");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 28, 30));
			
			//tiep cong dan thuong xuyen
			//field - 5
			idx++;
			row = sheet1.createRow(idx);
			row.setHeight((short)1000);
			c = row.createCell(3);
			setBorderMore(0,wb,row,c,0,32,0);
			c.setCellValue("Cũ ");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 3, 3));
			
			c = row.createCell(4);
			c.setCellValue("Mới phát sinh");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 4, 4));
			
			c = row.createCell(5);
			c.setCellValue("Số đoàn");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 5, 5));
			
			c = row.createCell(6);
			c.setCellValue("Người");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 6, 6));
			
			//group - 7
			c = row.createCell(7);
			c.setCellValue("Vụ việc");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 7, 8));
			
			//field group - 11
			c = row.createCell(11);
			c.setCellValue("Cũ");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 11, 11));
			
			c = row.createCell(12);
			c.setCellValue("Mới phát sinh");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 12, 12));
			
			//field group - 13
			c = row.createCell(13);
			c.setCellValue("Số đoàn");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 13, 13));
			
			c = row.createCell(14);
			c.setCellValue("Người");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 14, 14));
			
			//group - 11
			c = row.createCell(15);
			c.setCellValue("Vụ việc");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 15, 16));
			
			//group 17
			c = row.createCell(17);
			c.setCellValue("Lĩnh vực hành chính");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 17, 20));

			//group 21
			c = row.createCell(21);
			c.setCellValue("Lĩnh vực tư pháp");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 21, 21));
			
			//group 22
			c = row.createCell(22);
			c.setCellValue("Lĩnh vực CT,VH,XH khác");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 22, 22));
			
			//group 23
			c = row.createCell(23);
			c.setCellValue("Lĩnh vực hành chính");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 23, 23));
			
			//group 24
			c = row.createCell(24);
			c.setCellValue("Lĩnh vực tư pháp");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 24, 24));
			
			//group 25
			c = row.createCell(25);
			c.setCellValue("Tham nhũng");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 25, 25));
			
			//group 28
			c = row.createCell(28);
			c.setCellValue("Chưa có QĐ giải quyết");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 28, 28));
			
			//group 29
			c = row.createCell(29);
			c.setCellValue("Đã có QĐ giải quyết (lần 1,2, cuối cùng)");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 29, 29));
			
			//group 30
			c = row.createCell(30);
			c.setCellValue("Đã có bản án của Tòa");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx + 1, 30, 30));
			
			idx++;
			row = sheet1.createRow(idx);
			row.setHeight((short)1600);
			//field group 7 - 7
			c = row.createCell(7);
			setBorderMore(0,wb,row,c,0,32,0);
			c.setCellValue("Cũ");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			
			//field group 7 - 8
			c = row.createCell(8);
			c.setCellValue("Mới phát sinh");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			
			//field group 15 - 15
			c = row.createCell(15);
			c.setCellValue("Cũ");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			
			//field group 15 - 16
			c = row.createCell(16);
			c.setCellValue("Mới phát sinh");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			
			//field group 17 - 17
			c = row.createCell(17);
			c.setCellValue("Về tranh chấp, đòi đất cũ, đền bù, giải tỏa...");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			
			
			//field group 17 - 18
			c = row.createCell(18);
			c.setCellValue("Về chính sách");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			
			//field group 17 - 19
			c = row.createCell(19);
			c.setCellValue("Về nhà, tài sản");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			
			//field group 17 - 20
			c = row.createCell(20);
			c.setCellValue("Về chế độ CC,VC");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			
			idx ++;
			row = sheet1.createRow(idx);
			c = row.createCell(0);
			c.setCellValue("MS");
			c.setCellStyle(cellStyleFalse12BlackCenter);
			
			for (int i = 1;i <= 31;i ++) {
				c = row.createCell(i);
				c.setCellValue(i);
				c.setCellStyle(cellStyleFalse12BlackCenter);
			}
			
			idx++;
			
			int i = 1;
			for (Map<String, Object> map : maSos) {
				row = sheet1.createRow(idx);
				row.setHeight((short) 1000);
				c = row.createCell(0);
				c.setCellValue(map.get("tenDonVi").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(1);
				c.setCellValue(map.get("tiepCongDanThuongXuyenNguoi").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(2);
				c.setCellValue(map.get("tiepCongDanThuongXuyenLuot").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(3);
				c.setCellValue(map.get("tiepCongDanThuongXuyenVuViecCu").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(4);
				c.setCellValue(map.get("tiepCongDanThuongXuyenVuViecMoiPhatSinh").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(5);
				c.setCellValue(map.get("tiepCongDanThuongXuyenDoanDongNguoiSoDoan").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(6);
				c.setCellValue(map.get("tiepCongDanThuongXuyenDoanDongNguoiSoNguoi").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(7);
				c.setCellValue(map.get("tiepCongDanThuongXuyenDoanDongNguoiVuViecCu").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(8);
				c.setCellValue(map.get("tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(9);
				c.setCellValue(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoLuot").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(10);
				c.setCellValue(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(11);
				c.setCellValue(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(12);
				c.setCellValue(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(13);
				c.setCellValue(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(14);
				c.setCellValue(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(15);
				c.setCellValue(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(16);
				c.setCellValue(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(17);
				c.setCellValue(map.get("linhVucKhieuNaiVeTranhChap").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(18);
				c.setCellValue(map.get("linhVucKhieuNaiVeChinhSach").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(19);
				c.setCellValue(map.get("linhVucKhieuNaiVeNhaCuaVaTaiSan").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(20);
				c.setCellValue(map.get("linhVucKhieuNaiVeCheDoCCVC").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(21);
				c.setCellValue(map.get("linhVucKhieuNaiTuPhap").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(22);
				c.setCellValue(map.get("linhVucKhieuNaiChinhTriVanHoaXaHoiKhac").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(23);
				c.setCellValue(map.get("linhVucToCaoHanhChinh").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(24);
				c.setCellValue(map.get("linhVucToCaoTuPhap").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(25);
				c.setCellValue(map.get("linhVucToCaoThamNhung").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(26);
				c.setCellValue(map.get("kienNghiPhanAnh").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(27);
				c.setCellValue(map.get("chuaDuocGiaiQuyet").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(28);
				c.setCellValue(map.get("chuaCoQuyetDinhGiaiQuyet").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(29);
				c.setCellValue(map.get("daCoQuyetDinhGiaiQuyet").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(30);
				c.setCellValue(map.get("daCoBanAnCuaToa").toString());
				c.setCellStyle(cellCenter);
				
				c = row.createCell(31);
				c.setCellValue(map.get("ghiChu").toString());
				c.setCellStyle(cellCenter);
				
				i++;
				idx++;
			}
			idx++;
			idx++;
			row = sheet1.createRow(idx);
			c = row.createCell(22);
			c.setCellValue("....., ngày     tháng     năm");
			c.setCellStyle(setBorderAndFont(wb, 0, true, 11, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 22, 31));
			
			idx++;
			row = sheet1.createRow(idx);
			c = row.createCell(22);
			c.setCellValue("THỦ TRƯỞNG ĐƠN VỊ");
			c.setCellStyle(setBorderAndFont(wb, 0, true, 11, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 22, 31));
			
			idx++;
			row = sheet1.createRow(idx);
			c = row.createCell(22);
			c.setCellValue("(ký tên, đóng dấu)");
			c.setCellStyle(setBorderAndFont(wb, 0, true, 11, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 22, 31));
			
			
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