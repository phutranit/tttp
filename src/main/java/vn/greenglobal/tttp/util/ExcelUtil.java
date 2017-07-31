package vn.greenglobal.tttp.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFPrintSetup;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.ss.util.RegionUtil;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpHeaders;
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
				
				String nguonDonText = "";
				for (Map<String, Object> map : don.getNguonDonInfo()) {
					nguonDonText = map.get("nguonDonText").toString() + " / " +map.get("donViChuyenText").toString();
				}
				c = row.createCell(4);
				c.setCellValue(nguonDonText);
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
				
				String trangThaiDonText = "";
				for(Map<String, Object>  map : don.getTrangThaiDonInfo()) {
					trangThaiDonText = map.get("trangThaiDonText").toString();
				}
				c = row.createCell(8);
				c.setCellValue(trangThaiDonText + " / " +(don.getQuyTrinhXuLyText() != "" ? don.getQuyTrinhXuLyText() : "Chưa có kết quả"));
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
		
		Map<String, CellStyle> styles = createStylesMap(wb);
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
			c.setCellStyle(cellStyleFalse9BlackCenter);
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
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanThuongXuyenNguoi").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(2);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanThuongXuyenLuot").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(3);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanThuongXuyenVuViecCu").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(4);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanThuongXuyenVuViecMoiPhatSinh").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(5);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanThuongXuyenDoanDongNguoiSoDoan").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(6);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanThuongXuyenDoanDongNguoiSoNguoi").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(7);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanThuongXuyenDoanDongNguoiVuViecCu").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(8);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(9);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoLuot").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(10);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(11);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(12);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(13);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(14);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(15);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(16);
				c.setCellValue(Integer.valueOf(map.get("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(17);
				c.setCellValue(Integer.valueOf(map.get("linhVucKhieuNaiVeTranhChap").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(18);
				c.setCellValue(Integer.valueOf(map.get("linhVucKhieuNaiVeChinhSach").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(19);
				c.setCellValue(Integer.valueOf(map.get("linhVucKhieuNaiVeNhaCuaVaTaiSan").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(20);
				c.setCellValue(Integer.valueOf(map.get("linhVucKhieuNaiVeCheDoCCVC").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(21);
				c.setCellValue(Integer.valueOf(map.get("linhVucKhieuNaiTuPhap").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(22);
				c.setCellValue(Integer.valueOf(map.get("linhVucKhieuNaiChinhTriVanHoaXaHoiKhac").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(23);
				c.setCellValue(Integer.valueOf(map.get("linhVucToCaoHanhChinh").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(24);
				c.setCellValue(Integer.valueOf(map.get("linhVucToCaoTuPhap").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(25);
				c.setCellValue(Integer.valueOf(map.get("linhVucToCaoThamNhung").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(26);
				c.setCellValue(Integer.valueOf(map.get("kienNghiPhanAnh").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(27);
				c.setCellValue(Integer.valueOf(map.get("chuaDuocGiaiQuyet").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(28);
				c.setCellValue(Integer.valueOf(map.get("chuaCoQuyetDinhGiaiQuyet").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(29);
				c.setCellValue(Integer.valueOf(map.get("daCoQuyetDinhGiaiQuyet").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(30);
				c.setCellValue(Integer.valueOf(map.get("daCoBanAnCuaToa").toString()));
				//c.setCellStyle(cellCenter);
				c.setCellStyle(styles.get("cell_number"));
				
				c = row.createCell(31);
				c.setCellValue(map.get("ghiChu").toString());
				c.setCellStyle(cellCenter);
				
				i++;
				idx++;
			}
			
			int recordSize = 0;
			int colSize = 0;
			String formula = "SUM()";
			String colName = "A";
			
			if(!maSos.isEmpty()){
				//Add TONG row
				row = sheet1.createRow(idx);
				row.setHeight((short)400);
				recordSize = maSos.size();
				colSize = maSos.get(0).size();
				
				for (int k = 0; k < colSize; k++) {
					c = row.createCell(k);
					if(k==0){
						c.setCellValue("Tổng");
						c.setCellStyle(styles.get("cell"));
					} else if(k==colSize-1){
						c.setCellStyle(styles.get("cell"));
					} else {
						colName = CellReference.convertNumToColString(k);
						formula = "SUM(" + colName + (idx-recordSize+1) + ":" + colName + row.getRowNum() + ")";
						c.setCellFormula(formula);
						c.setCellStyle(styles.get("cell_number"));
					}
				}
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
	
	public static void exportTongHopBaoCaoXuLyDonThu(HttpServletResponse response, String fileName, String sheetName,
			List<Map<String, Object>> maSos,  String tuNgay, String denNgay, String title) throws IOException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
		
		String soLieuStr = String.format("(số liệu tính từ ngày %s đến ngày %s)", 
				StringUtils.isNotBlank(tuNgay) ? Utils.getMocThoiGianLocalDateTimeStr(Utils.fixTuNgay(tuNgay)) : "", 
				StringUtils.isNotBlank(denNgay) ? Utils.getMocThoiGianLocalDateTimeStr(Utils.fixTuNgay(denNgay)) : "");
		
		
		List<String[]> header1 = new LinkedList<>();
		// Colname, fromRow, toRow, fromCol, toCol
		header1.add(new String[] {"Đơn vị", "8", "12", "0", "0"});
		header1.add(new String[] {"Tiếp nhận", "8", "8", "1", "6"});
		header1.add(new String[] {"Tổng số đơn", "9", "12", "1", "1"});
		header1.add(new String[] {"Đơn tiếp nhận trong kỳ", "9", "10", "2", "3"});
		header1.add(new String[] {"Đơn có nhiều người đứng tên", "11", "12", "2", "2"});
		header1.add(new String[] {"Đơn một người đứng tên", "11", "12", "3", "3"});
		header1.add(new String[] {"Đơn kỳ trước chuyển sang", "9", "10", "4", "5"});
		header1.add(new String[] {"Đơn có nhiều người đứng tên", "11", "12", "4", "4"});
		header1.add(new String[] {"Đơn một người đứng tên", "11", "12", "5", "5"});
		header1.add(new String[] {"Đơn đủ điều kiện xử lý", "9", "12", "6", "6"});
		
		header1.add(new String[] {"Phân loại đơn khiếu nại, tố cáo (số đơn)", "8", "8", "7", "25"});
		header1.add(new String[] {"Theo nội dung", "9", "9", "7", "19"});
		
		header1.add(new String[] {"Khiếu nại", "10", "10", "7", "13"});
		header1.add(new String[] {"Lĩnh vực hành chính", "11", "11", "7", "11"});
		header1.add(new String[] {"Tổng", "12", "12", "7", "7"});
		header1.add(new String[] {"Liên quan đến  đất đai", "12", "12", "8", "8"});
		header1.add(new String[] {"Về nhà, tài sản", "12", "12", "9", "9"});
		header1.add(new String[] {"Về chính sách, chế độ CC,VC", "12", "12", "10", "10"});
		header1.add(new String[] {"Lĩnh vực CT,VH,XH khác", "12", "12", "11", "11"});
		header1.add(new String[] {"Lĩnh vực tư pháp", "11", "12", "12", "12"});
		header1.add(new String[] {"Về Đảng", "11", "12", "13", "13"});
		
		header1.add(new String[] {"Tố cáo", "10", "10", "14", "19"});
		header1.add(new String[] {"Tổng", "11", "12", "14", "14"});
		header1.add(new String[] {"Lĩnh vực hành chính", "11", "12", "15", "15"});
		header1.add(new String[] {"Lĩnh vực tư pháp", "11", "12", "16", "16"});
		header1.add(new String[] {"Tham nhũng", "11", "12", "17", "17"});
		header1.add(new String[] {"Về Đảng", "11", "12", "18", "18"});
		header1.add(new String[] {"Lĩnh vực khác", "11", "12", "19", "19"});
		
		header1.add(new String[] {"Theo thẩm quyền giải quyết", "9", "9", "20", "22"});
		header1.add(new String[] {"Của các cơ quan hành chính các cấp", "10", "12", "20", "20"});
		header1.add(new String[] {"Của cơ quan tư pháp các cấp", "10", "12", "21", "21"});
		header1.add(new String[] {"Của cơ quan Đảng", "10", "12", "22", "22"});
		
		header1.add(new String[] {"Theo trình tự giải quyết", "9", "9", "23", "25"});
		header1.add(new String[] {"Chưa được giải quyết", "10", "12", "23", "23"});
		header1.add(new String[] {"Đã được giải quyết lần đầu", "10", "12", "24", "24"});
		header1.add(new String[] {"Đã được giải quyết nhiều lần", "10", "12", "25", "25"});
		
		header1.add(new String[] {"Đơn khác (kiến nghị, phản ánh, đơn nặc danh)", "8", "12", "26", "26"});
		header1.add(new String[] {"Kết quả xử lý đơn khiếu nại, tố cáo", "8", "8", "27", "31"});
		header1.add(new String[] {"Số văn bản hướng dẫn", "9", "12", "27", "27"});
		header1.add(new String[] {"Số đơn chuyển cơ quan có thẩm quyền", "9", "12", "28", "28"});
		header1.add(new String[] {"Số công văn đôn đốc việc giải quyết", "9", "12", "29", "29"});
		header1.add(new String[] {"Đơn thuộc thẩm quyền", "9", "10", "30", "31"});
		header1.add(new String[] {"Khiếu nại", "11", "12", "30", "30"});
		header1.add(new String[] {"Tố cáo", "11", "12", "31", "31"});
		header1.add(new String[] {"Ghi chú", "8", "12", "32", "32"});
		
		// New Workbook
		Workbook wb = new XSSFWorkbook();
				
		Map<String, CellStyle> styles = createStylesMap(wb);
		
		try {
			
			// New Sheet
			Sheet sheet1 = wb.createSheet(sheetName);
			sheet1.getPrintSetup().setLandscape(true);
			sheet1.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			sheet1.createFreezePane(0, 14);
			
			// Row and column indexes
			int idx = 0;
			Row row;
			Cell c;
			
			//Ten don vi
			row = sheet1.createRow(++idx); //idx 1
			c  = row.createCell(0);
			c.setCellValue("BỘ, NGÀNH (UBND TỈNH, THÀNH PHỐ)...");
			c.setCellStyle(setBorderAndFont(wb, null, true, 12, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 0, 12));
			
			idx+=3;
			row = sheet1.createRow(idx); //idx 4
			row.setHeight((short)600);
			c  = row.createCell(0);
			c.setCellValue("TỔNG HỢP KẾT QUẢ XỬ LÝ ĐƠN THƯ KHIẾU NẠI, TỐ CÁO");
			c.setCellStyle(setBorderAndFont(wb, null, true, 12, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 0, 32));
			
			row = sheet1.createRow(++idx); //idx 5
			c  = row.createCell(0);
			c.setCellValue(soLieuStr);
			c.setCellStyle(setBorderAndFont(wb, null, true, 12, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 0, 32));
			
			idx+=2; //idx = 7
			for (int j = 8; j <= 12; j++) {
				row = sheet1.createRow(j);
				if(j==12){
					row.setHeight((short)1200);
				} else {
					row.setHeight((short)400);
				}
				idx++;
			}
			//idx = 12
			
			String name;
			Integer row1, row2, col1, col2;
			
			CellRangeAddress range;
			
			for (String[] head : header1) {
				name = (String) head[0];
				row1 = Integer.valueOf((String) head[1]);
				row2 = Integer.valueOf((String) head[2]);
				col1 = Integer.valueOf((String) head[3]);
				col2 = Integer.valueOf((String) head[4]);
				row = sheet1.getRow(row1);
				
				c  = row.createCell(col1);
				c.setCellValue(name);
				c.setCellStyle(setBorderAndFont(wb, BorderStyle.THIN, true, 12, "BLACK", "CENTER"));
				
				if(row1 == row2 && col1 == col2){
				} else {
					range = new CellRangeAddress(row1, row2, col1, col2);
					sheet1.addMergedRegion(range);
					RegionUtil.setBorderBottom(BorderStyle.THIN.getCode(), range, sheet1);
					RegionUtil.setBorderTop(BorderStyle.THIN.getCode(), range, sheet1);
					RegionUtil.setBorderLeft(BorderStyle.THIN.getCode(), range, sheet1);
					RegionUtil.setBorderRight(BorderStyle.THIN.getCode(), range, sheet1);
				}
			}
			
			row = sheet1.createRow(++idx); //idx = 13
			row.setHeight((short)900);
			for (int i = 0; i <= 32; i++) {
				c  = row.createCell(i);
				if(i==0) { c.setCellValue("MS"); }
				else if(i==1) { c.setCellValue("1=2+3+4+5"); }
				else if(i==7) { c.setCellValue("7=8+9+10+11"); }
				else if(i==14) { c.setCellValue("14=15+16+17+18+19"); }
				else {
					c.setCellValue(i);
				}
				c.setCellStyle(setBorderAndFont(wb, BorderStyle.THIN, true, 12, "BLACK", "CENTER"));
			}
			
			idx+=1; //idx = 14
			int recordSize = 0;
			int colSize = 0;
			String formula = "SUM()";
			String colName = "A";
			
			if(!maSos.isEmpty()){
				recordSize = maSos.size();
				colSize = maSos.get(0).size();
				
				Calendar calendar = Calendar.getInstance();
				Map<String, Object> mapMaSo = null;
				Object obj;
				for (int i = 0; i <= recordSize; i++) {
					row = sheet1.createRow(i+idx);
					row.setHeight((short)400);
					// Add data here
					if(i < recordSize){
						mapMaSo = maSos.get(i);
						for (int j = 0; j < colSize; j++) {
							c  = row.createCell(j);
							obj = mapMaSo.get(String.valueOf(j));
							if (obj instanceof Number) {
								c.setCellValue(Integer.valueOf(String.valueOf(obj)));
								c.setCellStyle(styles.get("cell_number"));
								if(j==0){
									c.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
								}
							} else if (obj instanceof Date) {
								calendar.setTime((Date)(obj));
								c.setCellValue(calendar);
								c.setCellStyle(styles.get("cell_day"));
							} else {
								c.setCellValue(String.valueOf(obj));
								c.setCellStyle(styles.get("cell"));
							}
						}
					} else {
						for (int k = 0; k < colSize; k++) {
							c  = row.createCell(k);
							//Add TONG row
							if(k==0){
								c.setCellValue("Tổng");
								c.setCellStyle(styles.get("cell"));
							} else if(k==colSize-1){
								c.setCellStyle(styles.get("cell"));
							} else {
								colName = CellReference.convertNumToColString(k);
								formula = "SUM(" + colName + (idx+1) + ":" + colName + row.getRowNum() + ")";
								c.setCellFormula(formula);
								c.setCellStyle(styles.get("cell_number"));
							}
						}
					}
				}
			}
			idx = idx + recordSize + 3;
			
			row = sheet1.createRow(idx);
			c = row.createCell(25);
			c.setCellValue("....., ngày     tháng     năm");
			c.setCellStyle(setBorderAndFont(wb, null, true, 11, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 25, 32));
			
			row = sheet1.createRow(++idx);
			c = row.createCell(25);
			c.setCellValue("THỦ TRƯỞNG ĐƠN VỊ");
			c.setCellStyle(setBorderAndFont(wb, null, true, 11, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 25, 32));
			
			row = sheet1.createRow(++idx);
			c = row.createCell(25);
			c.setCellValue("(ký tên, đóng dấu)");
			c.setCellStyle(setBorderAndFont(wb, null, true, 11, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 25, 32));
			
			row = sheet1.createRow(++idx);
			c  = row.createCell(1);
			c.setCellValue("Lưu ý:");
			c.setCellStyle(setBorderAndFont(wb, null, true, 12, "BLACK", "CENTER"));
			
			row = sheet1.createRow(++idx);
			c  = row.createCell(1);
			c.setCellValue("- Tổng đơn cột số (7) + cột (14) = Tổng số đơn từ cột (20) đến cột (22) = Tổng số đơn từ cột (23) đến cột (25)");
			c.setCellStyle(styles.get("cell_sub"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 1, 20));
			
			row = sheet1.createRow(++idx);
			c  = row.createCell(1);
			c.setCellValue("- Cột (6) đủ điều kiện xử lý là loại đơn không trùng lặp, có danh và rõ nội dung, địa chỉ");
			c.setCellStyle(styles.get("cell_sub"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 1, 20));
			
			row = sheet1.createRow(++idx);
			c  = row.createCell(1);
			c.setCellValue("- Cột \"Đơn vị\" để các bộ, ngành, địa phương thống kê kết quả thực hiện của các đơn vị trực thuộc");
			c.setCellStyle(styles.get("cell_sub"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 1, 20));
			
			int width = (int) (10 * 256);
			// set column width
			sheet1.setColumnWidth(0, 35 * 256);
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
			sheet1.setColumnWidth(32, width);
			//END
			
			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			wb.write(fileOut);
			String mimeType = "application/octet-stream";
			response.setContentType(mimeType);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"" + fileName + ".xlsx" + "\""));
			response.setContentLength((int) fileOut.size());

			//InputStream inputStream = new ByteArrayInputStream(fileOut.toByteArray());
			FileCopyUtils.copy(fileOut.toByteArray(), response.getOutputStream());
			response.flushBuffer();
			//inputStream.close();
		} finally {
			wb.close();
		}
	}
	
	public static void exportTongHopBaoCaoGiaiQuyetToCao(HttpServletResponse response, String fileName, String sheetName,
			List<Map<String, Object>> maSos,  String tuNgay, String denNgay, String title) throws IOException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
		
		String soLieuStr = String.format("(số liệu tính từ ngày %s đến ngày %s)", 
				StringUtils.isNotBlank(tuNgay) ? Utils.getMocThoiGianLocalDateTimeStr(Utils.fixTuNgay(tuNgay)) : "", 
				StringUtils.isNotBlank(denNgay) ? Utils.getMocThoiGianLocalDateTimeStr(Utils.fixTuNgay(denNgay)) : "");
		
		
		List<String[]> header1 = new LinkedList<>();
		// Colname, fromRow, toRow, fromCol, toCol
		header1.add(new String[] {"Đơn vị", "8", "11", "0", "0"});
		header1.add(new String[] {"Đơn tố cáo thuộc thẩm quyền", "8", "8", "1", "4"});
		header1.add(new String[] {"Tổng số đơn tố cáo", "9", "11", "1", "1"});
		header1.add(new String[] {"Trong đó", "9", "10", "2", "4"});
		header1.add(new String[] {"Đơn nhận trong kỳ báo cáo", "11", "11", "2", "2"});
		header1.add(new String[] {"Đơn tồn kỳ trước chuyển sang", "11", "11", "3", "3"});
		header1.add(new String[] {"Tổng số vụ việc", "11", "11", "4", "4"});
		
		header1.add(new String[] {"Kết quả giải quyết", "8", "8", "5", "20"});
		header1.add(new String[] {"Đã giải quyết", "9", "10", "5", "6"});
		header1.add(new String[] {"Số đơn thuộc thẩm quyền", "11", "11", "5", "5"});
		header1.add(new String[] {"Số vụ việc thuộc thẩm quyền", "11", "11", "6", "6"});
		
		header1.add(new String[] {"Phân tích kết quả (vụ việc)", "9", "10", "7", "9"});
		header1.add(new String[] {"Tố cáo đúng", "11", "11", "7", "7"});
		header1.add(new String[] {"Tố cáo sai", "11", "11", "8", "8"});
		header1.add(new String[] {"Tố cáo đúng một phần", "11", "11", "9", "9"});
		
		header1.add(new String[] {"Kiến nghị thu hồi cho Nhà nước", "9", "10", "10", "11"});
		header1.add(new String[] {"Trả lại cho công dân", "9", "10", "12", "13"});
		header1.add(new String[] {"Tiền (Trđ)", "11", "11", "10", "10"});
		header1.add(new String[] {"Đất (m2)", "11", "11", "11", "11"});
		header1.add(new String[] {"Tiền (Trđ)", "11", "11", "12", "12"});
		header1.add(new String[] {"Đất (m2)", "11", "11", "13", "13"});
		
		header1.add(new String[] {"Số người được trả lại quyền lợi", "9", "11", "14", "14"});
		header1.add(new String[] {"Kiến nghị xử lý hành chính", "9", "10", "15", "16"});
		header1.add(new String[] {"Tổng số người", "11", "11", "15", "15"});
		header1.add(new String[] {"Số người đã bị xử lý", "11", "11", "16", "16"});
		header1.add(new String[] {"Chuyển cơ quan điều tra, khởi tố", "9", "9", "17", "20"});
		header1.add(new String[] {"Số vụ", "10", "11", "17", "17"});
		header1.add(new String[] {"Số đối tượng", "10", "11", "18", "18"});
		header1.add(new String[] {"Kết quả ", "10", "10", "19", "20"});
		header1.add(new String[] {"Số vụ đã khởi tố", "11", "11", "19", "19"});
		header1.add(new String[] {"Số đối tượng đã khởi tố", "11", "11", "20", "20"});

		header1.add(new String[] {"Chấp hành thời gian giải quyết theo quy định", "8", "9", "21", "22"});
		header1.add(new String[] {"Số vụ việc giải quyết đúng thời hạn", "10", "11", "21", "21"});
		header1.add(new String[] {"Số vụ việc giải quyết quá thời hạn", "10", "11", "22", "22"});
		header1.add(new String[] {"Việc thi hành quyết định giải quyết tố cáo", "8", "8", "23", "32"});
		header1.add(new String[] {"Tổng số quyết định phải tổ chức thực hiện trong kỳ báo cáo", "9", "11", "23", "23"});
		header1.add(new String[] {"Đã thực hiện", "9", "11", "24", "24"});
		header1.add(new String[] {"Thu hồi cho nhà nước", "9", "9", "25", "25"});
		header1.add(new String[] {"Phải thu", "10", "10", "25", "26"});
		header1.add(new String[] {"Tiền (Trđ)", "11", "11", "25", "25"});
		header1.add(new String[] {"Đất (m2)", "11", "11", "26", "26"});
		header1.add(new String[] {"Đã thu", "10", "10", "27", "28"});
		header1.add(new String[] {"Tiền (Trđ)", "11", "11", "27", "27"});
		header1.add(new String[] {"Đất (m2)", "11", "11", "28", "28"});
		header1.add(new String[] {"Trả lại cho công dân", "9", "9", "29", "32"});
		header1.add(new String[] {"Phải trả	", "10", "10", "29", "30"});
		header1.add(new String[] {"Tiền (Trđ)", "11", "11", "29", "29"});
		header1.add(new String[] {"Đất (m2)", "11", "11", "30", "30"});
		header1.add(new String[] {"Đã trả", "10", "10", "31", "32"});
		header1.add(new String[] {"Tiền (Trđ)", "11", "11", "31", "31"});
		header1.add(new String[] {"Đất (m2)", "11", "11", "32", "32"});
		header1.add(new String[] {"Ghi chú", "8", "11", "33", "33"});
		
		// New Workbook
		Workbook wb = new XSSFWorkbook();
				
		Map<String, CellStyle> styles = createStylesMap(wb);
		
		try {
			
			// New Sheet
			Sheet sheet1 = wb.createSheet(sheetName);
			sheet1.getPrintSetup().setLandscape(true);
			sheet1.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			//sheet1.createFreezePane(0, 14);
			
			// Row and column indexes
			int idx = 0;
			Row row;
			Cell c;
			
			//Ten don vi
			row = sheet1.createRow(++idx); //idx 1
			c  = row.createCell(0);
			c.setCellValue("BỘ, NGÀNH (UBND TỈNH, THÀNH PHỐ)...");
			c.setCellStyle(setBorderAndFont(wb, null, true, 12, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 0, 4));
			
			idx+=3;
			row = sheet1.createRow(idx); //idx 4
			row.setHeight((short)600);
			c  = row.createCell(0);
			c.setCellValue("TỔNG HỢP KẾT QUẢ GIẢI QUYẾT ĐƠN TỐ CÁO");
			c.setCellStyle(setBorderAndFont(wb, null, true, 12, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 0, 38));
			
			row = sheet1.createRow(++idx); //idx 5
			c  = row.createCell(0);
			c.setCellValue(soLieuStr);
			c.setCellStyle(setBorderAndFont(wb, null, true, 12, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 0, 38));
			
			idx+=2; //idx = 7
			for (int j = 8; j <= 11; j++) {
				row = sheet1.createRow(j);
				if(j==11){
					row.setHeight((short)1600);
				} else {
					row.setHeight((short)500);
				}
				idx++;
			}
			//idx = 11
			
			String name;
			Integer row1, row2, col1, col2;
			
			CellRangeAddress range;
			
			for (String[] head : header1) {
				name = (String) head[0];
				row1 = Integer.valueOf((String) head[1]);
				row2 = Integer.valueOf((String) head[2]);
				col1 = Integer.valueOf((String) head[3]);
				col2 = Integer.valueOf((String) head[4]);
				row = sheet1.getRow(row1);
				
				c  = row.createCell(col1);
				c.setCellValue(name);
				c.setCellStyle(setBorderAndFont(wb, BorderStyle.THIN, true, 12, "BLACK", "CENTER"));
				
				if(row1 == row2 && col1 == col2){
				} else {
					range = new CellRangeAddress(row1, row2, col1, col2);
					sheet1.addMergedRegion(range);
					RegionUtil.setBorderBottom(BorderStyle.THIN.getCode(), range, sheet1);
					RegionUtil.setBorderTop(BorderStyle.THIN.getCode(), range, sheet1);
					RegionUtil.setBorderLeft(BorderStyle.THIN.getCode(), range, sheet1);
					RegionUtil.setBorderRight(BorderStyle.THIN.getCode(), range, sheet1);
				}
			}
			
			row = sheet1.createRow(++idx); //idx = 12
			row.setHeight((short)500);
			for (int i = 0; i <= 33; i++) {
				c  = row.createCell(i);
				if(i==0) { c.setCellValue("MS"); }
				else if(i==1) { c.setCellValue("1=2+3"); }
				else {
					c.setCellValue(i);
				}
				c.setCellStyle(setBorderAndFont(wb, BorderStyle.THIN, true, 12, "BLACK", "CENTER"));
			}
			
			idx+=1; //idx = 13
			int recordSize = 0;
			int colSize = 0;
			String formula = "SUM()";
			String colName = "A";
			
			if(!maSos.isEmpty()){
				recordSize = maSos.size();
				colSize = maSos.get(0).size();
				
				Calendar calendar = Calendar.getInstance();
				Map<String, Object> mapMaSo = null;
				Object obj;
				for (int i = 0; i <= recordSize; i++) {
					row = sheet1.createRow(i+idx);
					row.setHeight((short)400);
					// Add data here
					if(i < recordSize){
						mapMaSo = maSos.get(i);
						for (int j = 0; j < colSize; j++) {
							c  = row.createCell(j);
							obj = mapMaSo.get(String.valueOf(j));
							if (obj instanceof Number) {
								c.setCellValue(Integer.valueOf(String.valueOf(obj)));
								c.setCellStyle(styles.get("cell_number"));
								if(j==0){
									c.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
								}
							} else if (obj instanceof Date) {
								calendar.setTime((Date)(obj));
								c.setCellValue(calendar);
								c.setCellStyle(styles.get("cell_day"));
							} else {
								c.setCellValue(String.valueOf(obj));
								c.setCellStyle(styles.get("cell"));
							}
						}
					} else {
						for (int k = 0; k < colSize; k++) {
							c  = row.createCell(k);
							//Add TONG row
							if(k==0){
								c.setCellValue("Tổng");
								c.setCellStyle(styles.get("cell"));
							} else if(k==colSize){
								c.setCellStyle(styles.get("cell"));
							} else {
								colName = CellReference.convertNumToColString(k);
								formula = "SUM(" + colName + (idx+1) + ":" + colName + row.getRowNum() + ")";
								c.setCellFormula(formula);
								c.setCellStyle(styles.get("cell_number"));
							}
						}
					}
				}
			}
			idx = idx + recordSize + 3;
			
			row = sheet1.createRow(idx);
			c  = row.createCell(1);
			c.setCellValue("Lưu ý:");
			c.setCellStyle(setBorderAndFont(wb, null, true, 12, "BLACK", "CENTER"));
			c = row.createCell(33);
			c.setCellValue("....., ngày     tháng     năm");
			c.setCellStyle(setBorderAndFont(wb, null, true, 11, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 33, 38));
			
			row = sheet1.createRow(++idx);
			c  = row.createCell(1);
			c.setCellValue("- Đối với các ngành quản lý ngành dọc ở địa phương không tổng hợp");
			c.setCellStyle(styles.get("cell_sub"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 1, 20));
			c = row.createCell(33);
			c.setCellValue("THỦ TRƯỞNG ĐƠN VỊ");
			c.setCellStyle(setBorderAndFont(wb, null, true, 11, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 33, 38));
			
			row = sheet1.createRow(++idx);
			c  = row.createCell(1);
			c.setCellValue("- Cột \"Đơn vị\" để các bộ, ngành, địa phương thống kê kết quả thực hiện của các đơn vị trực thuộc");
			c.setCellStyle(styles.get("cell_sub"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 1, 20));
			c = row.createCell(33);
			c.setCellValue("(ký tên, đóng dấu)");
			c.setCellStyle(setBorderAndFont(wb, null, true, 11, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 33, 38));
			
			
			int width = (int) (10 * 256);
			// set column width
			sheet1.setColumnWidth(0, 35 * 256);
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
			sheet1.setColumnWidth(32, width);
			sheet1.setColumnWidth(33, width);
			sheet1.setColumnWidth(34, width);
			sheet1.setColumnWidth(35, width);
			sheet1.setColumnWidth(36, width);
			sheet1.setColumnWidth(37, width);
			sheet1.setColumnWidth(38,  35 * 256);
			//END
			
			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			wb.write(fileOut);
			String mimeType = "application/octet-stream";
			response.setContentType(mimeType);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"" + fileName + ".xlsx" + "\""));
			response.setContentLength((int) fileOut.size());

			//InputStream inputStream = new ByteArrayInputStream(fileOut.toByteArray());
			FileCopyUtils.copy(fileOut.toByteArray(), response.getOutputStream());
			response.flushBuffer();
			//inputStream.close();
		} finally {
			wb.close();
		}
	}
	
	public static void exportTongHopBaoCaoGiaiQuyetKhieuNai(HttpServletResponse response, String fileName, String sheetName,
			List<Map<String, Object>> maSos,  String tuNgay, String denNgay, String title) throws IOException {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd-MM-yyyy");
		
		String soLieuStr = String.format("(số liệu tính từ ngày %s đến ngày %s)", 
				StringUtils.isNotBlank(tuNgay) ? Utils.getMocThoiGianLocalDateTimeStr(Utils.fixTuNgay(tuNgay)) : "", 
				StringUtils.isNotBlank(denNgay) ? Utils.getMocThoiGianLocalDateTimeStr(Utils.fixTuNgay(denNgay)) : "");
		
		
		List<String[]> header1 = new LinkedList<>();
		// Colname, fromRow, toRow, fromCol, toCol
		header1.add(new String[] {"Đơn vị", "8", "11", "0", "0"});
		header1.add(new String[] {"Đơn khiếu nại thuộc thẩm quyền", "8", "8", "1", "4"});
		header1.add(new String[] {"Tổng số đơn khiếu nại", "9", "11", "1", "1"});
		header1.add(new String[] {"Trong đó", "9", "10", "2", "4"});
		header1.add(new String[] {"Đơn nhận trong kỳ báo cáo", "11", "11", "2", "2"});
		header1.add(new String[] {"Đơn tồn kỳ trước chuyển sang", "11", "11", "3", "3"});
		header1.add(new String[] {"Tổng số vụ việc", "11", "11", "4", "4"});
		
		header1.add(new String[] {"Kết quả giải quyết", "8", "8", "5", "25"});
		header1.add(new String[] {"Đã giải quyết", "9", "10", "5", "8"});
		header1.add(new String[] {"Số đơn thuộc thẩm quyền", "11", "11", "5", "5"});
		header1.add(new String[] {"Số vụ việc thuộc thẩm quyền", "11", "11", "6", "6"});
		header1.add(new String[] {"Số vụ việc giải quyết bằng QĐ hành chính", "11", "11", "7", "7"});
		header1.add(new String[] {"Số vụ việc rút đơn thông qua giải thích, thuyết phục", "11", "11", "8", "8"});
		
		header1.add(new String[] {"Phân tích kết quả (vụ việc)", "9", "9", "9", "14"});
		header1.add(new String[] {"Khiếu nại đúng", "10", "11", "9", "9"});
		header1.add(new String[] {"Khiếu nại sai", "10", "11", "10", "10"});
		header1.add(new String[] {"Khiếu nại đúng một phần", "10", "11", "11", "11"});
		header1.add(new String[] {"Giải quyết lần 1", "10", "11", "12", "12"});
		header1.add(new String[] {"Giải quyết lần 2", "10", "10", "13", "14"});
		header1.add(new String[] {"Công nhận QĐ g/q lần 1", "11", "11", "13", "13"});
		header1.add(new String[] {"Hủy, sửa QĐ g/q lần 1", "11", "11", "14", "14"});
		
		header1.add(new String[] {"Kiến nghị thu hồi cho Nhà nước", "9", "10", "15", "16"});
		header1.add(new String[] {"Trả lại cho công dân", "9", "10", "17", "18"});
		header1.add(new String[] {"Tiền (Trđ)", "11", "11", "15", "15"});
		header1.add(new String[] {"Đất (m2)", "11", "11", "16", "16"});
		header1.add(new String[] {"Tiền (Trđ)", "11", "11", "17", "17"});
		header1.add(new String[] {"Đất (m2)", "11", "11", "18", "18"});
		
		header1.add(new String[] {"Số người được trả lại quyền lợi", "9", "11", "19", "19"});
		header1.add(new String[] {"Kiến nghị xử lý hành chính", "9", "10", "20", "21"});
		header1.add(new String[] {"Tổng số người", "11", "11", "20", "20"});
		header1.add(new String[] {"Số người đã bị xử lý", "11", "11", "21", "21"});
		header1.add(new String[] {"Chuyển cơ quan điều tra, khởi tố", "9", "9", "22", "25"});
		header1.add(new String[] {"Số vụ", "10", "11", "22", "22"});
		header1.add(new String[] {"Số đối tượng", "10", "11", "23", "23"});
		header1.add(new String[] {"Kết quả ", "10", "10", "24", "25"});
		header1.add(new String[] {"Số vụ đã khởi tố", "11", "11", "24", "24"});
		header1.add(new String[] {"Số đối tượng đã khởi tố", "11", "11", "25", "25"});

		header1.add(new String[] {"Chấp hành thời gian giải quyết theo quy định", "8", "9", "26", "27"});
		header1.add(new String[] {"Số vụ việc giải quyết đúng thời hạn", "10", "11", "26", "26"});
		header1.add(new String[] {"Số vụ việc giải quyết quá thời hạn", "10", "11", "27", "27"});
		header1.add(new String[] {"Việc thi hành quyết định giải quyết khiếu nại", "8", "8", "28", "37"});
		header1.add(new String[] {"Tổng số quyết định phải tổ chức thực hiện trong kỳ báo cáo", "9", "11", "28", "28"});
		header1.add(new String[] {"Đã thực hiện", "9", "11", "29", "29"});
		header1.add(new String[] {"Thu hồi cho nhà nước", "9", "9", "30", "33"});
		header1.add(new String[] {"Phải thu", "10", "10", "30", "31"});
		header1.add(new String[] {"Tiền (Trđ)", "11", "11", "30", "30"});
		header1.add(new String[] {"Đất (m2)", "11", "11", "31", "31"});
		header1.add(new String[] {"Đã thu", "10", "10", "32", "33"});
		header1.add(new String[] {"Tiền (Trđ)", "11", "11", "32", "32"});
		header1.add(new String[] {"Đất (m2)", "11", "11", "33", "33"});
		header1.add(new String[] {"Trả lại cho công dân", "9", "9", "34", "37"});
		header1.add(new String[] {"Phải trả	", "10", "10", "34", "35"});
		header1.add(new String[] {"Tiền (Trđ)", "11", "11", "34", "34"});
		header1.add(new String[] {"Đất (m2)", "11", "11", "35", "35"});
		header1.add(new String[] {"Đã trả", "10", "10", "36", "37"});
		header1.add(new String[] {"Tiền (Trđ)", "11", "11", "36", "36"});
		header1.add(new String[] {"Đất (m2)", "11", "11", "37", "37"});
		header1.add(new String[] {"Ghi chú", "8", "11", "38", "38"});
		
		// New Workbook
		Workbook wb = new XSSFWorkbook();
				
		Map<String, CellStyle> styles = createStylesMap(wb);
		
		try {
			
			// New Sheet
			Sheet sheet1 = wb.createSheet(sheetName);
			sheet1.getPrintSetup().setLandscape(true);
			sheet1.getPrintSetup().setPaperSize(HSSFPrintSetup.A4_PAPERSIZE);
			//sheet1.createFreezePane(0, 14);
			
			// Row and column indexes
			int idx = 0;
			Row row;
			Cell c;
			
			//Ten don vi
			row = sheet1.createRow(++idx); //idx 1
			c  = row.createCell(0);
			c.setCellValue("BỘ, NGÀNH (UBND TỈNH, THÀNH PHỐ)...");
			c.setCellStyle(setBorderAndFont(wb, null, true, 12, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 0, 4));
			
			idx+=3;
			row = sheet1.createRow(idx); //idx 4
			row.setHeight((short)600);
			c  = row.createCell(0);
			c.setCellValue("TỔNG HỢP KẾT QUẢ GIẢI QUYẾT ĐƠN KHIẾU NẠI");
			c.setCellStyle(setBorderAndFont(wb, null, true, 12, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 0, 38));
			
			row = sheet1.createRow(++idx); //idx 5
			c  = row.createCell(0);
			c.setCellValue(soLieuStr);
			c.setCellStyle(setBorderAndFont(wb, null, true, 12, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 0, 38));
			
			idx+=2; //idx = 7
			for (int j = 8; j <= 11; j++) {
				row = sheet1.createRow(j);
				if(j==11){
					row.setHeight((short)1600);
				} else {
					row.setHeight((short)500);
				}
				idx++;
			}
			//idx = 11
			
			String name;
			Integer row1, row2, col1, col2;
			
			CellRangeAddress range;
			
			for (String[] head : header1) {
				name = (String) head[0];
				row1 = Integer.valueOf((String) head[1]);
				row2 = Integer.valueOf((String) head[2]);
				col1 = Integer.valueOf((String) head[3]);
				col2 = Integer.valueOf((String) head[4]);
				row = sheet1.getRow(row1);
				
				c  = row.createCell(col1);
				c.setCellValue(name);
				c.setCellStyle(setBorderAndFont(wb, BorderStyle.THIN, true, 12, "BLACK", "CENTER"));
				
				if(row1 == row2 && col1 == col2){
				} else {
					range = new CellRangeAddress(row1, row2, col1, col2);
					sheet1.addMergedRegion(range);
					RegionUtil.setBorderBottom(BorderStyle.THIN.getCode(), range, sheet1);
					RegionUtil.setBorderTop(BorderStyle.THIN.getCode(), range, sheet1);
					RegionUtil.setBorderLeft(BorderStyle.THIN.getCode(), range, sheet1);
					RegionUtil.setBorderRight(BorderStyle.THIN.getCode(), range, sheet1);
				}
			}
			
			row = sheet1.createRow(++idx); //idx = 12
			row.setHeight((short)500);
			for (int i = 0; i <= 38; i++) {
				c  = row.createCell(i);
				if(i==0) { c.setCellValue("MS"); }
				else if(i==1) { c.setCellValue("1=2+3"); }
				else {
					c.setCellValue(i);
				}
				c.setCellStyle(setBorderAndFont(wb, BorderStyle.THIN, true, 12, "BLACK", "CENTER"));
			}
			
			idx+=1; //idx = 13
			int recordSize = 0;
			int colSize = 0;
			String formula = "SUM()";
			String colName = "A";
			
			if(!maSos.isEmpty()){
				recordSize = maSos.size();
				colSize = maSos.get(0).size();
				
				Calendar calendar = Calendar.getInstance();
				Map<String, Object> mapMaSo = null;
				Object obj;
				for (int i = 0; i <= recordSize; i++) {
					row = sheet1.createRow(i+idx);
					row.setHeight((short)400);
					// Add data here
					if(i < recordSize){
						mapMaSo = maSos.get(i);
						for (int j = 0; j < colSize; j++) {
							c  = row.createCell(j);
							obj = mapMaSo.get(String.valueOf(j));
							if (obj instanceof Number) {
								c.setCellValue(Integer.valueOf(String.valueOf(obj)));
								c.setCellStyle(styles.get("cell_number"));
								if(j==0){
									c.getCellStyle().setAlignment(HorizontalAlignment.CENTER);
								}
							} else if (obj instanceof Date) {
								calendar.setTime((Date)(obj));
								c.setCellValue(calendar);
								c.setCellStyle(styles.get("cell_day"));
							} else {
								c.setCellValue(String.valueOf(obj));
								c.setCellStyle(styles.get("cell"));
							}
						}
					} else {
						for (int k = 0; k < colSize; k++) {
							c  = row.createCell(k);
							//Add TONG row
							if(k==0){
								c.setCellValue("Tổng");
								c.setCellStyle(styles.get("cell"));
							} else if(k==colSize){
								c.setCellStyle(styles.get("cell"));
							} else {
								colName = CellReference.convertNumToColString(k);
								formula = "SUM(" + colName + (idx+1) + ":" + colName + row.getRowNum() + ")";
								c.setCellFormula(formula);
								c.setCellStyle(styles.get("cell_number"));
							}
						}
					}
				}
			}
			idx = idx + recordSize + 3;
			
			row = sheet1.createRow(idx);
			c  = row.createCell(1);
			c.setCellValue("Lưu ý:");
			c.setCellStyle(setBorderAndFont(wb, null, true, 12, "BLACK", "CENTER"));
			c = row.createCell(33);
			c.setCellValue("....., ngày     tháng     năm");
			c.setCellStyle(setBorderAndFont(wb, null, true, 11, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 33, 38));
			
			row = sheet1.createRow(++idx);
			c  = row.createCell(1);
			c.setCellValue("- Đối với các ngành quản lý ngành dọc ở địa phương không tổng hợp");
			c.setCellStyle(styles.get("cell_sub"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 1, 20));
			c = row.createCell(33);
			c.setCellValue("THỦ TRƯỞNG ĐƠN VỊ");
			c.setCellStyle(setBorderAndFont(wb, null, true, 11, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 33, 38));
			
			row = sheet1.createRow(++idx);
			c  = row.createCell(1);
			c.setCellValue("- Cột \"Đơn vị\" để các bộ, ngành, địa phương thống kê kết quả thực hiện của các đơn vị trực thuộc");
			c.setCellStyle(styles.get("cell_sub"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 1, 20));
			c = row.createCell(33);
			c.setCellValue("(ký tên, đóng dấu)");
			c.setCellStyle(setBorderAndFont(wb, null, true, 11, "BLACK", "CENTER"));
			sheet1.addMergedRegion(new CellRangeAddress(idx, idx, 33, 38));
			
			
			int width = (int) (10 * 256);
			// set column width
			sheet1.setColumnWidth(0, 35 * 256);
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
			sheet1.setColumnWidth(32, width);
			sheet1.setColumnWidth(33, width);
			sheet1.setColumnWidth(34, width);
			sheet1.setColumnWidth(35, width);
			sheet1.setColumnWidth(36, width);
			sheet1.setColumnWidth(37, width);
			sheet1.setColumnWidth(38,  35 * 256);
			//END
			
			ByteArrayOutputStream fileOut = new ByteArrayOutputStream();
			wb.write(fileOut);
			String mimeType = "application/octet-stream";
			response.setContentType(mimeType);
			response.setHeader(HttpHeaders.CONTENT_DISPOSITION, String.format("attachment; filename=\"" + fileName + ".xlsx" + "\""));
			response.setContentLength((int) fileOut.size());

			//InputStream inputStream = new ByteArrayInputStream(fileOut.toByteArray());
			FileCopyUtils.copy(fileOut.toByteArray(), response.getOutputStream());
			response.flushBuffer();
			//inputStream.close();
		} finally {
			wb.close();
		}
	}
	
	public static CellStyle setBorderAndFont(final Workbook workbook,
			final BorderStyle borderStyle, final boolean isTitle, final int fontSize,
			final String fontColor, final String textAlign) {
		
		final CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(true);
		
		if(borderStyle!=null){
			cellStyle.setBorderTop(borderStyle); // single line border
			cellStyle.setBorderBottom(borderStyle); // single line border
			cellStyle.setBorderLeft(borderStyle); // single line border
			cellStyle.setBorderRight(borderStyle); // single line border
		}
		
		if (textAlign.equals("RIGHT")) {
			cellStyle.setAlignment(HorizontalAlignment.RIGHT);
		} else if (textAlign.equals("CENTER")) {
			cellStyle.setAlignment(HorizontalAlignment.CENTER);
		} else if (textAlign.equals("LEFT")) {
			cellStyle.setAlignment(HorizontalAlignment.LEFT);
		}
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		
		final Font font = workbook.createFont();
		font.setFontName("Times New Roman");
		if (isTitle) {
			font.setBold(true);
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
	
	public static Map<String, CellStyle> createStylesMap(Workbook wb){
		Map<String, CellStyle> styles = new HashMap<>();
		DataFormat df = wb.createDataFormat();
		CellStyle style;
		
		Font titleFont = wb.createFont();
		titleFont.setFontHeightInPoints((short) 14);
		titleFont.setFontName("Times New Roman");
		titleFont.setBold(true);
		style = wb.createCellStyle();
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setFont(titleFont);
		styles.put("title", style);
		
		style = createBorderedStyleCell(wb);
		Font fontcell = wb.createFont();
		fontcell.setFontHeightInPoints((short) 12);
		fontcell.setFontName("Times New Roman");
		style.setAlignment(HorizontalAlignment.LEFT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(true);
		style.setFont(fontcell);
		styles.put("cell", style);
		
		style = createBorderedStyleCell(wb);
		Font fontNumber = wb.createFont();
		fontNumber.setFontHeightInPoints((short) 12);
		fontNumber.setFontName("Times New Roman");
		style.setAlignment(HorizontalAlignment.RIGHT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(true);
		style.setFont(fontNumber);
		styles.put("cell_number", style);
		
		style = createBorderedStyleCell(wb);
		Font fontNumberCenter = wb.createFont();
		fontNumber.setFontHeightInPoints((short) 12);
		fontNumber.setFontName("Times New Roman");
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(true);
		style.setFont(fontNumberCenter);
		styles.put("cell_number_center", style);
		
		style = createBorderedStyleCell(wb);
		Font fontDay = wb.createFont();
		fontDay.setFontHeightInPoints((short) 12);
		fontDay.setFontName("Times New Roman");
		style.setAlignment(HorizontalAlignment.RIGHT);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		style.setWrapText(true);
		style.setFont(fontDay);
		style.setDataFormat(df.getFormat("dd/MM/yyyy"));
		styles.put("cell_day", style);
		
		CellStyle cell_sub = wb.createCellStyle();
		Font fontSubTitle = wb.createFont();
		fontSubTitle.setFontHeightInPoints((short) 10);
		fontSubTitle.setFontName("Times New Roman");
		fontSubTitle.setItalic(true);
		cell_sub.setAlignment(HorizontalAlignment.LEFT);
		cell_sub.setVerticalAlignment(VerticalAlignment.CENTER);
		cell_sub.setWrapText(true);
		cell_sub.setFont(fontSubTitle);
		styles.put("cell_sub", cell_sub);
		
		return styles;
	}
	
	private static CellStyle createBorderedStyleCell(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		style.setBorderRight(BorderStyle.THIN);
		style.setRightBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderBottom(BorderStyle.THIN);
		style.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderLeft(BorderStyle.THIN);
		style.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		style.setBorderTop(BorderStyle.THIN);
		style.setTopBorderColor(IndexedColors.BLACK.getIndex());
		return style;
	}
	
}