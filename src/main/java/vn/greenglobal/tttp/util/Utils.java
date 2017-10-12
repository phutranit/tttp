package vn.greenglobal.tttp.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.SocketTimeoutException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.Normalizer;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Pattern;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.time.DateUtils;
import org.docx4j.org.apache.xml.security.exceptions.Base64DecodingException;
import org.docx4j.org.apache.xml.security.utils.Base64;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import airbrake.AirbrakeNotice;
import airbrake.AirbrakeNoticeBuilder;
import airbrake.AirbrakeNotifier;
import airbrake.Backtrace;
import vn.greenglobal.Application;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.Model;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.model.ThongTinEmail;

public class Utils {

	//private static List<Calendar> vietHolidays;
	private static final int startMorning = 7;
	private static final int minuteStartMorning = 30;
	private static final int endMorning = 11;
	private static final int minuteEndMorning = 30;
	private static final int startAfternoon = 13;
	@SuppressWarnings("unused")
	private static final int minuteStartAfternoon = 30;
	private static final int endAfternoon = 17;
	private static final int minuteEndAfternoon = 30;

	private static final int SATURDAY = 6;
	private static final int SUNDAY = 7;

	public static void copyValues(Model<?> source, Model<?> target, Iterable<String> properties) {
		BeanWrapper src = new BeanWrapperImpl(source);
		BeanWrapper trg = new BeanWrapperImpl(target);
		for (String propertyName : properties) {
			trg.setPropertyValue(propertyName, src.getPropertyValue(propertyName));
		}
	}

	public static ResponseEntity<Object> responseErrors(HttpStatus httpStatus, String code, String detail,
			String description) {
		List<Map<String, Object>> errors = new ArrayList<>();
		Map<String, Object> error = new HashMap<>();
		error.put("status", Long.valueOf(httpStatus.toString()));
		error.put("code", code);
		error.put("detail", detail);
		error.put("description", description);
		errors.add(error);
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("errors", errors);
		return new ResponseEntity<>(errorBody, httpStatus);
	}

	public static ResponseEntity<Object> responseInternalServerErrors(Exception e) {
		Writer result = new StringWriter();
	    PrintWriter printWriter = new PrintWriter(result);
		
		e.printStackTrace();
		if (e instanceof ConstraintViolationException)
			return returnError((ConstraintViolationException) e);
		if (e.getCause() instanceof ConstraintViolationException)
			return returnError((ConstraintViolationException) e.getCause());
		if (e.getCause() != null && e.getCause().getCause() instanceof ConstraintViolationException)
			return returnError((ConstraintViolationException) e.getCause().getCause());
		if (e.getCause() != null && e.getCause().getCause() != null
				&& e.getCause().getCause().getCause() instanceof ConstraintViolationException)
			return returnError((ConstraintViolationException) e.getCause().getCause().getCause());
		
		System.out.println("Application.app.airBrakeActive: " + Application.app.airBrakeActive);
		if (Application.app.airBrakeActive && !(e instanceof SocketTimeoutException)) {
			try {
			    e.printStackTrace(printWriter);
			    Backtrace backtrace = new Backtrace(e);
				AirbrakeNotice notice = new AirbrakeNoticeBuilder("a01791c869495905d093abee088c4370", backtrace, e, "test").newNotice();
				AirbrakeNotifier notifier = new AirbrakeNotifier("http://tracker.thanhtratp.greenglobal.vn:9836/notifier_api/v2/notices");
				notifier.notify(notice);
			} catch (Exception ex) {
			    ex.printStackTrace(printWriter);
				System.out.println("[AirBrakeActive: ERROR]: " + ex);
			}
		}
		
		return Utils.responseErrors(HttpStatus.INTERNAL_SERVER_ERROR, ApiErrorEnum.INTERNAL_SERVER_ERROR.name(),
				ApiErrorEnum.INTERNAL_SERVER_ERROR.getText(), e.getMessage());
	}

	public static ResponseEntity<Object> returnError(ConstraintViolationException e) {
		ConstraintViolation<?> vio = e.getConstraintViolations().iterator().next();
		System.out.println("returnError -> " + vio);
		if (vio.getMessageTemplate().equals("{" + NotBlank.class.getName() + ".message}"))
			return Utils.responseErrors(HttpStatus.BAD_REQUEST,
					vio.getPropertyPath().toString().toUpperCase() + "_REQUIRED", "Bạn không được bỏ trống trường này.",
					"Trường " + vio.getPropertyPath().toString().toUpperCase() + " không được để trống.");
		if (vio.getMessageTemplate().equals("{" + NotNull.class.getName() + ".message}"))
			return Utils.responseErrors(HttpStatus.BAD_REQUEST,
					vio.getPropertyPath().toString().toUpperCase() + "_NOT_NULL", "Bạn không được bỏ trống trường này.",
					"Trường " + vio.getPropertyPath().toString().toUpperCase() + " không được NULL.");
		if (vio.getMessageTemplate().equals("{" + Size.class.getName() + ".message}"))
			return Utils.responseErrors(HttpStatus.BAD_REQUEST,
					vio.getPropertyPath().toString().toUpperCase() + "_INVALID_SIZE",
					"Trường này đã nhập quá kí tự cho phép.",
					"Trường " + vio.getPropertyPath().toString().toUpperCase() + " đã nhập quá kí tự cho phép.");
		return Utils.responseErrors(HttpStatus.BAD_REQUEST, "UNKNOWN", "UNKNOWN", "UNKNOWN");
	}

	@SuppressWarnings("rawtypes")
	public static <T extends Model> ResponseEntity<Object> doSave(JpaRepository<T, Long> repository, T obj,
			Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		try {
			obj = save(repository, obj, congChucId);
		} catch (ConstraintViolationException e) {
			return returnError(e);
		} catch (Exception e) {
			System.out.println("doSave -> " + e.getCause());
			if (e.getCause() instanceof ConstraintViolationException)
				return returnError((ConstraintViolationException) e.getCause());
			if (e.getCause() != null && e.getCause().getCause() instanceof ConstraintViolationException)
				return returnError((ConstraintViolationException) e.getCause().getCause());
			if (e.getCause() != null && e.getCause().getCause() != null
					&& e.getCause().getCause().getCause() instanceof ConstraintViolationException)
				return returnError((ConstraintViolationException) e.getCause().getCause().getCause());
			throw e;
		}
		return new ResponseEntity<>(eass.toFullResource(obj), status);
	}

	@SuppressWarnings("rawtypes")
	public static <T extends Model> T save(JpaRepository<T, Long> repository, T obj, Long congChucId) {
		CongChuc cc = new CongChuc();
		cc.setId(congChucId);
		if (!obj.isNew()) {
			T o = repository.findOne(obj.getId());
			obj.setNgayTao(o.getNgayTao());
			obj.setNgaySua(Utils.localDateTimeNow());
			obj.setNguoiTao(o.getNguoiTao());
			obj.setNguoiSua(cc);
		} else {
			obj.setNguoiTao(cc);
			obj.setNguoiSua(obj.getNguoiTao());
		}
		obj = repository.save(obj);
		return obj;
	}

	public static boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}

	public static LocalDateTime fixTuNgay(String tuNgayCurrent) {
		// Fix tuNgay
		ZonedDateTime zdt = ZonedDateTime.parse(tuNgayCurrent);
		LocalDateTime tuNgay = zdt.toLocalDateTime();
		tuNgay = LocalDateTime.of(LocalDate.of(tuNgay.getYear(), tuNgay.getMonth(), tuNgay.getDayOfMonth()),
				LocalTime.MIN);
		return tuNgay;
	}

	public static LocalDateTime fixDenNgay(String denNgayCurrent) {
		// Fix denNgay
		ZonedDateTime zdt = ZonedDateTime.parse(denNgayCurrent);
		LocalDateTime denNgay = zdt.toLocalDateTime();
		denNgay = LocalDateTime.of(LocalDate.of(denNgay.getYear(), denNgay.getMonth(), denNgay.getDayOfMonth()),
				LocalTime.MAX);
		return denNgay;
	}

	public static LocalDateTime convertNumberToLocalDateTime(LocalDateTime ngayBatDau, Long soNgayXuLy) {
		long i = 1;
		LocalDateTime ngayKetThuc = ngayBatDau;
		if (ngayKetThuc != null && soNgayXuLy != null && soNgayXuLy > 0) {
			while (i < soNgayXuLy) {
				ngayKetThuc = ngayKetThuc.plusDays(1);
				if (ngayKetThuc.getDayOfWeek().getValue() == SATURDAY
						|| ngayKetThuc.getDayOfWeek().getValue() == SUNDAY) {
					continue;
				}
				i++;
			}
			ngayKetThuc = LocalDateTime.of(
					LocalDate.of(ngayKetThuc.getYear(), ngayKetThuc.getMonth(), ngayKetThuc.getDayOfMonth()),
					LocalTime.MAX);
		}
		return ngayKetThuc != null ? ngayKetThuc : null;
	}

	public static LocalDateTime convertNumberToLocalDateTimeGoc(LocalDateTime ngayBatDau, Long soNgayGiaHan) {
		long i = 1;
		LocalDateTime ngayKetThuc = ngayBatDau;
		if (ngayKetThuc != null && soNgayGiaHan != null && soNgayGiaHan > 0) {
			ngayKetThuc = ngayKetThuc.plusDays(1);
			while (i < soNgayGiaHan) {
				ngayKetThuc = ngayKetThuc.plusDays(1);
				i++;
			}
			ngayKetThuc = LocalDateTime.of(
					LocalDate.of(ngayKetThuc.getYear(), ngayKetThuc.getMonth(), ngayKetThuc.getDayOfMonth()),
					LocalTime.MAX);
		}

		return ngayKetThuc != null ? ngayKetThuc : null;
	}

	public static LocalDateTime convertNumberToLocalDateTimeTinhTheoNgayLamViec(LocalDateTime ngayBatDau, Long soNgayGiaHan) {
		long i = 1;
		LocalDateTime ngayKetThuc = ngayBatDau;
		if (ngayKetThuc != null && soNgayGiaHan != null && soNgayGiaHan > 0) {
			ngayKetThuc = ngayKetThuc.plusDays(1);
			while (i < soNgayGiaHan) {
				ngayKetThuc = ngayKetThuc.plusDays(1);
				if (ngayKetThuc.getDayOfWeek().getValue() == SATURDAY
						|| ngayKetThuc.getDayOfWeek().getValue() == SUNDAY) {
					continue;
				}
				i++;
			}
			ngayKetThuc = LocalDateTime.of(
					LocalDate.of(ngayKetThuc.getYear(), ngayKetThuc.getMonth(), ngayKetThuc.getDayOfMonth()),
					LocalTime.MAX);
		}

		return ngayKetThuc != null ? ngayKetThuc : null;
	}
	
	public static Long convertLocalDateTimeToNumber(LocalDateTime tuNgay, LocalDateTime denNgay) {
		long soNgayXuLy = 0;
		if (tuNgay != null && denNgay != null) {
			soNgayXuLy = 1;
			int check = tuNgay.compareTo(denNgay);
			tuNgay = LocalDateTime.of(LocalDate.of(tuNgay.getYear(), tuNgay.getMonth(), tuNgay.getDayOfMonth()),
					LocalTime.MAX);
			denNgay = LocalDateTime.of(LocalDate.of(denNgay.getYear(), denNgay.getMonth(), denNgay.getDayOfMonth()),
					LocalTime.MAX);
			if (check == 0) {
				soNgayXuLy = 0;
			}
			while (tuNgay.compareTo(denNgay) < 0) {
				tuNgay = tuNgay.plusDays(1);
				// if (tuNgay.getDayOfWeek().getValue() == SATURDAY ||
				// tuNgay.getDayOfWeek().getValue() == SUNDAY) {
				// continue;
				// }
				soNgayXuLy++;
			}
		}
		return soNgayXuLy;
	}

	public static Long convertLocalDateTimeToNumber(LocalDateTime ngayKetThuc) {
		long soNgayXuLy = 0;
		if (ngayKetThuc != null) {
			soNgayXuLy = 1;
			LocalDateTime ngayHienTai = Utils.localDateTimeNow();
			ngayHienTai = LocalDateTime.of(
					LocalDate.of(ngayHienTai.getYear(), ngayHienTai.getMonth(), ngayHienTai.getDayOfMonth()),
					LocalTime.MAX);
			ngayKetThuc = LocalDateTime.of(
					LocalDate.of(ngayKetThuc.getYear(), ngayKetThuc.getMonth(), ngayKetThuc.getDayOfMonth()),
					LocalTime.MAX);
			int check = ngayHienTai.compareTo(ngayKetThuc);
			if (check == 0) {
				soNgayXuLy = 0;
			}
			while (ngayHienTai.compareTo(ngayKetThuc) < 0) {
				ngayHienTai = ngayHienTai.plusDays(1);
				if (ngayHienTai.getDayOfWeek().getValue() == SATURDAY
						|| ngayHienTai.getDayOfWeek().getValue() == SUNDAY) {
					continue;
				}
				soNgayXuLy++;
			}
		}
		return soNgayXuLy;
	}

	public static NguoiDung quyenValidate(ProfileUtils profileUtil, String authorization, QuyenEnum quyen) {
		NguoiDung nguoiDung = profileUtil.getUserInfo(authorization);
		if (nguoiDung != null && nguoiDung.checkQuyen(quyen)) {
			return nguoiDung;
		}
		return null;
	}
	
	public static NguoiDung tokenValidate(ProfileUtils profileUtil, String authorization) {
		NguoiDung nguoiDung = profileUtil.getUserInfo(authorization);
		if (nguoiDung != null) {
			return nguoiDung;
		}
		return null;
	}

	public static List<Method> getMethodsAnnotatedWith(final Class<?>... types) {
		final List<Method> methods = new ArrayList<Method>();
		for (Class<?> clasz : types) {
			Class<?> klass = clasz;
			final List<Method> allMethods = new ArrayList<Method>(Arrays.asList(klass.getDeclaredMethods()));
			for (final Method method : allMethods) {
				if (method.isAnnotationPresent(FlowMethodCollection.class)) {
					methods.add(method);
				}
			}
		}
		return methods;
	}

	public static CoQuanQuanLy getDonViByCongChuc(CongChuc congChuc, ThamSo thamSo) {
		if (congChuc != null && congChuc.getCoQuanQuanLy() != null) {
			if (thamSo != null
					&& thamSo.getGiaTri().toString().equals(congChuc.getCoQuanQuanLy().getCapCoQuanQuanLy().getId())) {
				return congChuc.getCoQuanQuanLy().getCha();
			} else {
				return congChuc.getCoQuanQuanLy();
			}
		}
		return null;
	}

//	private static void genVietHolidays() {
//		if (vietHolidays == null) {
//			vietHolidays = new ArrayList<Calendar>();
//			vietHolidays.add(getHoliday(2017, 05, 04));
//			vietHolidays.add(getHoliday(2017, 05, 05));
//		}
//	}

//	private static Calendar getHoliday(int year, int month, int date) {
//		Calendar cal = Calendar.getInstance();
//		cal.set(year, month - 1, date);
//		return cal;
//	}
	
	public static String getMocThoiGianLocalDateTimeStr(LocalDateTime thoiHan) {
		String str = "";
		if (thoiHan == null) { 
			return str;
		}
		str = thoiHan.getDayOfMonth() +"/" + thoiHan.getMonthValue() + "/" +thoiHan.getYear();
		return str;
	}

	public static Calendar getMocThoiGianLocalDateTime(LocalDateTime thoiHan, int gioMoc, int phutMoc) {
		Calendar c = Calendar.getInstance();
		c.set(thoiHan.getYear(), thoiHan.getMonthValue() - 1, thoiHan.getDayOfMonth(), thoiHan.getHour(),
				thoiHan.getMinute());

		c.set(Calendar.HOUR_OF_DAY, gioMoc);
		c.set(Calendar.MINUTE, phutMoc);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}

	/**
	 * Lay so gio phut con lai neu thoi han xu ly con 1 ngay
     * @param gioHienTai lay gio hanh chinh hien tai
     * @param thoiHanKetThuc thoi han ngay ket thuc
     * @return str so gio phut con lai cua xu ly
     */
	@SuppressWarnings("deprecation")
	public static String getLaySoGioPhut(LocalDateTime gioHienTai, LocalDateTime thoiHanKetThuc) {
		//Calendar cal = Calendar.getInstance();
		//LocalDateTime gioHienTai = Utils.localDateTimeNow();
		int gio = 0;
		int phut = 0;
		String str = "";
		if (gioHienTai != null && thoiHanKetThuc != null) { 
			Calendar batDau = getMocThoiGianLocalDateTime(gioHienTai, gioHienTai.getHour(), gioHienTai.getMinute());
			if (batDau.get(Calendar.AM_PM) == 0) {
				LocalDateTime gioKetThuc = LocalDateTime.of(thoiHanKetThuc.getYear(), thoiHanKetThuc.getMonth(),
						thoiHanKetThuc.getDayOfMonth(), thoiHanKetThuc.getHour(), thoiHanKetThuc.getMinute());
				Calendar ketThuc = getMocThoiGianLocalDateTime(gioKetThuc, endMorning, minuteEndMorning);
				
				if (!DateUtils.isSameDay(batDau, ketThuc)) { 
					return "";
				}

				int bonus = 4;
				phut = 0;
				// check thoi gian gio hanh chinh
				Calendar mocDauBuoiSang = getMocThoiGianLocalDateTime(gioHienTai, startMorning, minuteEndMorning);
				long gioDauBuoiSang = mocDauBuoiSang.getTimeInMillis();
				
				if (batDau.getTimeInMillis() >= gioDauBuoiSang) {
					gio = ketThuc.getTime().getHours() - batDau.getTime().getHours();
					// check dieu kien thuoc gio hanh chinh
					if (ketThuc.getTime().getMinutes() > batDau.getTime().getMinutes()) {
						phut = ketThuc.getTime().getMinutes() - batDau.getTime().getMinutes();
					} else if (ketThuc.getTime().getMinutes() < batDau.getTime().getMinutes()) {
						phut = batDau.getTime().getMinutes() - ketThuc.getTime().getMinutes();
						phut = 60 - phut;
						gio = gio - 1;
					}
				} else {
					gio = 4;
				}
				gio += bonus;
			} else {
				LocalDateTime gioKetThuc = LocalDateTime.of(thoiHanKetThuc.getYear(), thoiHanKetThuc.getMonth(),
						thoiHanKetThuc.getDayOfMonth(), thoiHanKetThuc.getHour(), thoiHanKetThuc.getMinute());
				Calendar ketThuc = getMocThoiGianLocalDateTime(gioKetThuc, endAfternoon, minuteEndAfternoon);
				if (!DateUtils.isSameDay(batDau, ketThuc)) { 
					return "";
				}
				
				gio = ketThuc.getTime().getHours() - batDau.getTime().getHours();
				phut = 0;

				// check thoi gian gio hanh chinh
				Calendar mocDauBuoiChieu = getMocThoiGianLocalDateTime(gioHienTai, startAfternoon, minuteEndAfternoon);
				Calendar mocCuoiBuoiChieu = getMocThoiGianLocalDateTime(gioHienTai, endAfternoon, minuteEndAfternoon);
				
				long gioDauBuoiChieu = mocDauBuoiChieu.getTimeInMillis();
				long gioCuoiBuoiChieu = mocCuoiBuoiChieu.getTimeInMillis();	
				if (batDau.getTimeInMillis() > gioCuoiBuoiChieu) { 
					return "";
				}
				if (batDau.getTimeInMillis() >= gioDauBuoiChieu) {					
					// check dieu kien thuoc gio hanh chinh
					if (ketThuc.getTime().getMinutes() > batDau.getTime().getMinutes()) {
						phut = ketThuc.getTime().getMinutes() - batDau.getTime().getMinutes();
					} else if (ketThuc.getTime().getMinutes() < batDau.getTime().getMinutes()) {
						phut = batDau.getTime().getMinutes() - ketThuc.getTime().getMinutes();
						phut = 60 - phut;
						gio = gio - 1;
					}
				} else {
					gio = 4;
				}
			}

			str = ((String.valueOf(gio).length() == 1 ? "0" + gio : gio)) + " giờ " + (String.valueOf(phut).length() == 1 ? "0" + phut : phut)  + " phút";
		}
		return str;
	}

	/**
	 * Lay so ngay cua thoi han xu ly
     * @param ngayBatDau thoi han tu ngay bat dau
     * @param ngayKetThuc thoi han ngay ket thuc
     * @param gioHanhChinhHienTai gio hanh chinh lam viec hien tai
     * @return soNgayXuLy so ngay con lai de xu ly
     */
	public static Long getLaySoNgay(LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc,
			LocalDateTime gioHanhChinhHienTai) {
		long soNgayXuLy = 0;
		boolean checkNgayNghi = false;

		if (ngayBatDau != null && ngayKetThuc != null && gioHanhChinhHienTai != null) {
			Calendar now = getMocThoiGianLocalDateTime(gioHanhChinhHienTai, gioHanhChinhHienTai.getHour(),
					gioHanhChinhHienTai.getMinute());
			Calendar cal = getMocThoiGianLocalDateTime(gioHanhChinhHienTai, gioHanhChinhHienTai.getHour(),
					gioHanhChinhHienTai.getMinute());
			Calendar start = getMocThoiGianLocalDateTime(ngayBatDau, ngayBatDau.getHour(), ngayBatDau.getMinute());
			Calendar end = getMocThoiGianLocalDateTime(ngayKetThuc, ngayKetThuc.getHour(), ngayKetThuc.getMinute());

			if (start.before(end) || DateUtils.isSameDay(start, end)) {
				// while (start.before(end) || DateUtils.isSameDay(start, end))
				// {
				// // check ngay nghi
				// if (isInvalidNgayNghi(start.getTime())) {
				// end.add(Calendar.DATE, 1);
				// }
				// start.add(Calendar.DATE, 1);
				// }

				// check ngay hop le
				if (cal.before(end) || DateUtils.isSameDay(cal, end)) {
					// lay so ngay de xu ly
					while (cal.before(end) || DateUtils.isSameDay(cal, end)) {
						// check ngay nghi
						// if (!isInvalidNgayNghi(cal.getTime())) {
						if (DateUtils.isSameDay(cal, now)) {
							// check thuoc gio hanh chinh
							if (cal.get(Calendar.AM_PM) == 0) {
								// AM
								// check thoi gian gio hanh chinh
								// Calendar mocDauBuoiSang =
								// getMocThoiGianLocalDateTime(gioHanhChinh,
								// startMorning,
								// minuteStartMorning);
								Calendar mocDauBuoiSang = getMocThoiGianLocalDateTime(gioHanhChinhHienTai, startMorning,
										minuteStartMorning);
								if (!DateUtils.isSameDay(cal, mocDauBuoiSang)) {
									return soNgayXuLy = 0;
								}
								long gioBuoiSang = mocDauBuoiSang.getTimeInMillis();
								if (cal.getTimeInMillis() <= gioBuoiSang) {
									soNgayXuLy = 1;
								} else {
									if (DateUtils.isSameDay(end, now)) {
										soNgayXuLy = -3;
									}
								}
							} else {
								// PM
								// Calendar mocDauBuoiChieu =
								// getMocThoiGianLocalDateTime(gioHanhChinh,
								// endAfternoon,
								// minuteEndAfternoon);
								Calendar mocDauBuoiChieu = getMocThoiGianLocalDateTime(gioHanhChinhHienTai,
										endAfternoon, minuteEndAfternoon);
								if (!DateUtils.isSameDay(cal, mocDauBuoiChieu)) {
									return soNgayXuLy = 0;
								}
								long gioBuoiChieu = mocDauBuoiChieu.getTimeInMillis();
								if (cal.getTimeInMillis() < gioBuoiChieu) {
									checkNgayNghi = true;
								}
							}
						} else {
							soNgayXuLy += 1;
							if (DateUtils.isSameDay(cal, end)) {
								break;
							}
						}
						// }
						cal.add(Calendar.DATE, 1);
					}
				} else {
					soNgayXuLy = -1;
				}
				if (soNgayXuLy == 0 && checkNgayNghi) {
					soNgayXuLy = -2;
				}
			} else {
				soNgayXuLy = -1;
			}
		}
		return soNgayXuLy;
	}

	/**
	 * Lay so ngay da tre han xu ly
     * @param ngayHienTai ngay hien tai tai trong tuan
     * @param ngayBatDau thoi han tu ngay bat dau
     * @param ngayKetThuc thoi han ngay ket thuc
     * @return soNgayXuLy so ngay da tre han cua xu ly
     */
	public static Long getLayNgayTreHan(LocalDateTime ngayHienTai, LocalDateTime ngayBatDau, LocalDateTime ngayKetThuc) {
		long soNgayXuLy = 0;
		try {
			if (ngayHienTai != null && ngayBatDau != null && ngayKetThuc != null) { 
				Calendar lichHienTai = getMocThoiGianLocalDateTime(ngayHienTai, ngayHienTai.getHour(), ngayHienTai.getMinute());
//				Calendar start = getMocThoiGianLocalDateTime(ngayBatDau, ngayBatDau.getHour(), ngayBatDau.getMinute());
				Calendar end = getMocThoiGianLocalDateTime(ngayKetThuc, ngayKetThuc.getHour(), ngayKetThuc.getMinute());

//				if (start.before(end) || DateUtils.isSameDay(start, end)) {
//					while (start.before(end) || DateUtils.isSameDay(start, end)) {
//						// check ngay nghi
//						if (isInvalidNgayNghi(start.getTime())) {
//							end.add(Calendar.DATE, 1);
//						}
//						start.add(Calendar.DATE, 1);
//					}
//				}
				
				if (end.before(lichHienTai) && !DateUtils.isSameDay(end, lichHienTai)) {
					end.add(Calendar.DATE, 1);
					while (end.before(lichHienTai) || DateUtils.isSameDay(end, lichHienTai)) {
						soNgayXuLy += 1;
						end.add(Calendar.DATE, 1);
					}
				}
			}
		} catch (DateTimeException e) { 
			return soNgayXuLy = 0;
		}
		
		return soNgayXuLy;
	}
	
	public static boolean isValidNgayTreHan(LocalDateTime thoiHan) {
		boolean isValid = false;
		LocalDateTime now = Utils.localDateTimeNow();
		Calendar ngayHienTai = getMocThoiGianLocalDateTime(now, now.getHour(), now.getMinute());
		Calendar end = getMocThoiGianLocalDateTime(thoiHan, thoiHan.getHour(), thoiHan.getMinute());
		
		if (ngayHienTai.after(end) && !DateUtils.isSameDay(ngayHienTai, end)) {
			isValid = true;
		}
		return isValid;
	}
	
	public static boolean isValidNgayTreHanTinhTheoNgayKetThuc(LocalDateTime thoiHan, LocalDateTime ngayKetThuc) {
		boolean isValid = false;
		Calendar thoiHanXL = getMocThoiGianLocalDateTime(thoiHan, thoiHan.getHour(), thoiHan.getMinute());
		Calendar ketThuc = getMocThoiGianLocalDateTime(ngayKetThuc, ngayKetThuc.getHour(), ngayKetThuc.getMinute());
		
		if (ketThuc.after(thoiHanXL) && !DateUtils.isSameDay(ketThuc, thoiHanXL)) {
			isValid = true;
		}
		return isValid;
	}

	public static String getMaDon() {
		DateFormat df = new SimpleDateFormat("yyMMdd");
		String out = df.format(new Date());
		Random generator = new Random();
		Integer randomNumber = generator.nextInt(9999 - 1) + 1;
		String randomNumberStr = "" + randomNumber;
		for (int m = 1; m <= (4 - randomNumber.toString().length()); m++) {
			randomNumberStr = "0" + randomNumberStr;
		}
		return out + randomNumberStr;
	}
	
	public static Double round(Number src, int decimalPlaces) {
	    return Optional.ofNullable(src)
	            .map(Number::doubleValue)
	            .map(BigDecimal::new)
	            .map(dbl -> dbl.setScale(decimalPlaces, BigDecimal.ROUND_HALF_UP))
	            .map(BigDecimal::doubleValue)
	            .orElse(null);
	}
	
	public static LocalDateTime localDateTimeNow() {
		return LocalDateTime.now().atZone(ZoneId.of("Etc/GMT+7")).withZoneSameInstant(ZoneOffset.UTC).toLocalDateTime();
	}
	
	public static Map<String, Object> convertThoiHan(LocalDateTime ngayBatDauTmp, LocalDateTime ngayHetHanTmp, LocalDateTime ngayHetHanSauKhiGianHanTmp) {
		Map<String, Object> mapType = new HashMap<>();
		LocalDateTime gioHanhChinhHienTai = localDateTimeNow();
		LocalDateTime ngayBatDau = null;
		LocalDateTime ngayHetHan = null;
		long soNgayXuLy = 0;
		
		
		if (ngayBatDauTmp != null) {
			ngayBatDau = ngayBatDauTmp;
			if (ngayHetHanTmp != null && ngayHetHanSauKhiGianHanTmp != null) {
				ngayHetHan = ngayHetHanSauKhiGianHanTmp;
				soNgayXuLy = Utils.getLaySoNgay(ngayBatDau, ngayHetHan, gioHanhChinhHienTai);
			} else if (ngayHetHanTmp != null && ngayHetHanSauKhiGianHanTmp == null) {
				ngayHetHan = ngayHetHanTmp;
				soNgayXuLy = Utils.getLaySoNgay(ngayBatDau, ngayHetHan, gioHanhChinhHienTai);
			}
			if (soNgayXuLy >= 0) {
				mapType.put("type", "DAY");
				mapType.put("value", soNgayXuLy);
			} else if (soNgayXuLy == -1) {
				mapType.put("type", "DAY");
				mapType.put("value", -Utils.getLayNgayTreHan(gioHanhChinhHienTai, ngayBatDau, ngayHetHan));
			} else if (soNgayXuLy == -2 || soNgayXuLy == -3) {
				mapType.put("type", "TIME");
				mapType.put("value", Utils.getLaySoGioPhut(gioHanhChinhHienTai, ngayHetHan));
			} 
		}
		return mapType;
	}

	public static boolean isValidNgayDungHanGQD(LocalDateTime thoiHan) {
		boolean isValid = false;
		Calendar now = Calendar.getInstance();
		Calendar ketThuc = getMocThoiGianLocalDateTime(thoiHan, 0, 0);
		if (now.after(ketThuc) || DateUtils.isSameDay(ketThuc, now)) {
			isValid = true;
		}
		return isValid;
	}
	
	public static boolean isValidNgayDungHanDangXuLy(LocalDateTime thoiHan) {
		boolean isValid = false;
		LocalDateTime thoiGianHienTai = Utils.localDateTimeNow();
		Calendar now = getMocThoiGianLocalDateTime(thoiGianHienTai, thoiGianHienTai.getHour(), thoiGianHienTai.getMinute());
		Calendar ketThuc = getMocThoiGianLocalDateTime(thoiHan, 0, 0);
		
		if (now.before(ketThuc) || DateUtils.isSameDay(ketThuc, now)) {
			isValid = true;
		}
		return isValid;
	}
	
	public static boolean isValidNgayTreHanDangXuLy(LocalDateTime thoiHan) {
		boolean isValid = false;
		LocalDateTime thoiGianHienTai = Utils.localDateTimeNow();
		Calendar now = getMocThoiGianLocalDateTime(thoiGianHienTai, thoiGianHienTai.getHour(), thoiGianHienTai.getMinute());
		Calendar ketThuc = getMocThoiGianLocalDateTime(thoiHan, 0, 0);
		
		if (now.after(ketThuc) && !DateUtils.isSameDay(ketThuc, now)) {
			isValid = true;
		}
		return isValid;
	}
	
	public static boolean isValidNgayDungHanDaXuLy(LocalDateTime thoiHan, LocalDateTime ngayKetThuc) {
		boolean isValid = false;
		Calendar thoiHanXL = getMocThoiGianLocalDateTime(thoiHan, 0, 0);
		Calendar thoiHanKetThucXL = getMocThoiGianLocalDateTime(ngayKetThuc, 0, 0);
		
		if (thoiHanKetThucXL.before(thoiHanXL) || DateUtils.isSameDay(thoiHanXL, thoiHanKetThucXL)) {
			isValid = true;
		}
		return isValid;
	}
	
	public static boolean isValidNgayTreHanDaXuLy(LocalDateTime thoiHan, LocalDateTime ngayKetThuc) {
		boolean isValid = false;
		
		Calendar thoiHanXL = getMocThoiGianLocalDateTime(thoiHan, 0, 0);
		Calendar thoiHanKetThucXL = getMocThoiGianLocalDateTime(ngayKetThuc, 0, 0);
		
		if (thoiHanXL.before(thoiHanKetThucXL) && !DateUtils.isSameDay(thoiHanXL, thoiHanKetThucXL)) {
			isValid = true;
		}
		return isValid;
	}
	
	private static boolean isInvalidNgayNghi(Date date) {
//		if (vietHolidays == null) {
//			genVietHolidays();
//		}
		
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		boolean flag = now.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
				|| now.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY;
		
//		if (!flag) {
//			for (Calendar c : vietHolidays) {
//				if (c.get(Calendar.YEAR) == now.get(Calendar.YEAR) && c.get(Calendar.MONTH) == now.get(Calendar.MONTH)
//						&& c.get(Calendar.DATE) == now.get(Calendar.DATE)) {
//					flag = true;
//					break;
//				}
//			}
//		}
		return flag;
	}
	
	/**
	 * Lay so ngay cua thoi han xu ly thanh tra
     * @param ngayBatDau thoi han tu ngay bat dau
     * @param soNgayThanhTra so ngay thanh tra 
     * @return soNgayXuLy so ngay con lai de xu ly
     */
	public static Long getLaySoNgayXuLyThanhTra(LocalDateTime ngayBatDau, Long soNgayThanhTra) {
		Long soNgayXuLy = 0L;

		if (ngayBatDau != null && (soNgayThanhTra != null && soNgayThanhTra > 0)) {
			Calendar start = getMocThoiGianLocalDateTime(ngayBatDau, ngayBatDau.getHour(), ngayBatDau.getMinute());
			Calendar end = getMocThoiGianLocalDateTime(ngayBatDau, ngayBatDau.getHour(), ngayBatDau.getMinute());
			for (int i = 1; i < soNgayThanhTra; i++) {
				end.add(Calendar.DATE, 1);
			}
			
			while (start.before(end) || DateUtils.isSameDay(start, end)) {
				if (isInvalidNgayNghi(start.getTime())) { 
					end.add(Calendar.DATE, 1);
				}
				soNgayXuLy += 1;
				start.add(Calendar.DATE, 1);
			}
		}
		return soNgayXuLy;
	}
	
	public static Map<String, Object> convertThoiHanThanhTra(LocalDateTime ngayBatDauTmp, Long soNgayThanhTra) {
		Map<String, Object> mapType = new HashMap<>();
		LocalDateTime ngayBatDau = null;
		LocalDateTime ngayHetHan = null;
		long soNgayXuLy = 0;

		if (ngayBatDauTmp != null) {
			ngayBatDau = ngayBatDauTmp;
			ngayHetHan = ngayBatDauTmp;
			if (soNgayThanhTra != null && soNgayThanhTra > 0) {
				soNgayXuLy = Utils.getLaySoNgayXuLyThanhTra(ngayBatDau, soNgayThanhTra);
				ngayHetHan = ngayHetHan.plusDays(soNgayXuLy);
			}
			if (soNgayXuLy >= 0) {
				mapType.put("type", "DAY");
				mapType.put("value", soNgayXuLy);
			}
		}
		return mapType;
	}

	public static boolean isValidStringLength(String str, int length) {
		if (str != null && !"".equals(str)) {
			return str.length() <= length ? true : false;
		} else {
			return true;
		}
	}
	
	public static int getQuyHienTai() {
		int quy = 0;
		int month = Utils.localDateTimeNow().getMonthValue();
		if (month >= 1 && month <= 3) {
			quy = 1;
		} else if (month >= 4 && month <= 6) {
			quy = 2;
		} else if (month >= 7 && month <= 9) {
			quy = 3;
		} else if (month >= 10 && month <= 12) {
			quy = 4;
		}
		return quy;
	}

	public static String unAccent(String s) {
		if (s != null && !"".equals(s)) {
			String temp = Normalizer.normalize(s.toLowerCase(), Normalizer.Form.NFD);
			Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
			String result = pattern.matcher(temp).replaceAll("").replaceAll("đ", "d").replaceAll("[^a-zA-Z0-9 -]", "");
			return result.replace("  ", " ");
		}
		return "";
	}
	
	public static Don changeQuyenTuXuLy(Don don, boolean quyenTuXLDGQD, boolean quyenTTXM, boolean quyenKTDX) {
		don.setTuXuLyXLDGQD(quyenTuXLDGQD);
		don.setTuXuLyTTXM(quyenTTXM);
		don.setTuXuLyKTDX(quyenKTDX);
		return don;
	}
	
	public static void sendEmailGmail(String mailTo, String subject, String content, ThongTinEmail thongTinEmail) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", thongTinEmail.isEnableAuth());
		props.put("mail.smtp.starttls.enable", thongTinEmail.isEnableStarttls());
		props.put("mail.smtp.host", thongTinEmail.getHost());
		props.put("mail.smtp.port", thongTinEmail.getPort());

		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				String password = new String(org.apache.commons.codec.binary.Base64.decodeBase64(thongTinEmail.getPassword().getBytes()));
				return new PasswordAuthentication(thongTinEmail.getUsername(), password);
			}
		});

		try {

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(thongTinEmail.getUsername()));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(mailTo));
			message.setSubject(subject);
			// message.setText("Dear Mail Crawler," + "\n\n No spam to my email,
			// please!");
			message.setContent(MessageFormat.format(content, "THANH TRA ĐÀ NẴNG"), "text/html; charset=utf-8");
			Transport.send(message);

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}