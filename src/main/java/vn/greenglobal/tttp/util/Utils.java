package vn.greenglobal.tttp.util;

import java.lang.reflect.Method;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Model;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.ThamSo;

public class Utils {

	private static final int SATURDAY = 6;
	private static final int SUNDAY = 7;

	public static void copyValues(Model<?> source, Model<?> target, Iterable<String> properties) {
		BeanWrapper src = new BeanWrapperImpl(source);
		BeanWrapper trg = new BeanWrapperImpl(target);
		for (String propertyName : properties) {
			trg.setPropertyValue(propertyName, src.getPropertyValue(propertyName));
		}
	}

	public static ResponseEntity<Object> responseErrors(HttpStatus httpStatus, String code, String detail) {
		List<Map<String, Object>> errors = new ArrayList<>();
		Map<String, Object> error = new HashMap<>();
		error.put("status", new Long(httpStatus.toString()));
		error.put("code", code);
		error.put("detail", detail);
		errors.add(error);
		Map<String, List<Map<String, Object>>> errorBody = new HashMap<>();
		errorBody.put("errors", errors);
		return new ResponseEntity<>(errorBody, httpStatus);
	}

	public static ResponseEntity<Object> returnError(ConstraintViolationException e) {
		ConstraintViolation<?> vio = e.getConstraintViolations().iterator().next();
		System.out.println("returnError -> " + vio);
		if (vio.getMessageTemplate().equals("{" + NotBlank.class.getName() + ".message}"))
			return Utils.responseErrors(HttpStatus.BAD_REQUEST,
					vio.getPropertyPath().toString().toUpperCase() + "_REQUIRED",
					"Trường " + vio.getPropertyPath().toString().toUpperCase() + " không được để trống!");
		if (vio.getMessageTemplate().equals("{" + NotNull.class.getName() + ".message}"))
			return Utils.responseErrors(HttpStatus.BAD_REQUEST,
					vio.getPropertyPath().toString().toUpperCase() + "_NOT_NULL",
					"Trường " + vio.getPropertyPath().toString().toUpperCase() + " không được NULL!");
		if (vio.getMessageTemplate().equals("{" + Size.class.getName() + ".message}"))
			return Utils.responseErrors(HttpStatus.BAD_REQUEST,
					vio.getPropertyPath().toString().toUpperCase() + "_INVALID_SIZE",
					"Trường " + vio.getPropertyPath().toString().toUpperCase() + " không được để trống!");
		return Utils.responseErrors(HttpStatus.BAD_REQUEST, "UNKNOWN", "UNKNOWN");
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
				return returnError((ConstraintViolationException) e.getCause().getCause());
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
			obj.setNgaySua(LocalDateTime.now());
			obj.setNguoiTao(o.getNguoiTao());
			obj.setNguoiSua(cc);
		} else {
			obj.setNguoiTao(cc);
			obj.setNguoiSua(obj.getNguoiTao());
		}
		obj = repository.save(obj);
		return obj;
	}

	// @SuppressWarnings("rawtypes")
	// public static <T extends Model> ResponseEntity<Object>
	// doSave(JpaRepository<T, Long> repository, T obj,
	// PersistentEntityResourceAssembler eass, HttpStatus status) {
	// try {
	// obj = save(repository, obj);
	// } catch (ConstraintViolationException e) {
	// return returnError(e);
	// } catch (Exception e) {
	// System.out.println("doSave -> " + e.getCause());
	// if (e.getCause() instanceof ConstraintViolationException)
	// return returnError((ConstraintViolationException) e.getCause());
	// if (e.getCause() != null && e.getCause().getCause() instanceof
	// ConstraintViolationException)
	// return returnError((ConstraintViolationException)
	// e.getCause().getCause());
	// if (e.getCause() != null && e.getCause().getCause() != null &&
	// e.getCause().getCause().getCause() instanceof
	// ConstraintViolationException)
	// return returnError((ConstraintViolationException)
	// e.getCause().getCause());
	// throw e;
	// }
	// return new ResponseEntity<>(eass.toFullResource(obj), status);
	// }
	//
	// @SuppressWarnings("rawtypes")
	// public static <T extends Model> T save(JpaRepository<T, Long> repository,
	// T obj) {
	// if (!obj.isNew()) {
	// T o = repository.findOne(obj.getId());
	// obj.setNgayTao(o.getNgayTao());
	// obj.setNgaySua(LocalDateTime.now());
	// obj.setNguoiTao(o.getNguoiTao());
	// }
	// obj = repository.save(obj);
	// return obj;
	// }

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
		soNgayXuLy = soNgayXuLy != null ? soNgayXuLy : 0L;
		LocalDateTime ngayKetThuc = ngayBatDau;
		while (i < soNgayXuLy) {
			ngayKetThuc = ngayKetThuc.plusDays(1);
			if (ngayKetThuc.getDayOfWeek().getValue() == SATURDAY || ngayKetThuc.getDayOfWeek().getValue() == SUNDAY) {
				continue;
			}
			i++;
		}
		ngayKetThuc = LocalDateTime.of(
				LocalDate.of(ngayKetThuc.getYear(), ngayKetThuc.getMonth(), ngayKetThuc.getDayOfMonth()),
				LocalTime.MAX);
		return ngayKetThuc;
	}

	public static Long convertLocalDateTimeToNumber(LocalDateTime ngayKetThuc) {
		long soNgayXuLy = 0;
		LocalDateTime ngayHienTai = LocalDateTime.now();
		ngayHienTai = LocalDateTime.of(
				LocalDate.of(ngayHienTai.getYear(), ngayHienTai.getMonth(), ngayHienTai.getDayOfMonth()),
				LocalTime.MAX);
		int check = ngayHienTai.compareTo(ngayKetThuc);
		while (check < 0) {
			ngayHienTai = ngayHienTai.plusDays(1);
			check += ngayHienTai.compareTo(ngayKetThuc);
			if (ngayHienTai.getDayOfWeek().getValue() == SATURDAY || ngayHienTai.getDayOfWeek().getValue() == SUNDAY) {
				continue;
			}
			soNgayXuLy++;
		}
		System.out.println("soNgayXuLy " +soNgayXuLy);
		return soNgayXuLy;
	}

	public static NguoiDung quyenValidate(ProfileUtils profileUtil, String authorization, QuyenEnum quyen) {
		NguoiDung nguoiDung = profileUtil.getUserInfo(authorization);
		if (nguoiDung != null && nguoiDung.checkQuyen(quyen)) {
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
	
	/*
	 * public static void main(String[] args) {
	 * getMethodsAnnotatedWith(Utils.class); getMethodsAnnotatedWith(new
	 * Class<?>[] { Utils.class, Don.class}); }
	 */
	
	public static CoQuanQuanLy getDonViByCongChuc(CongChuc congChuc, ThamSo thamSo) {
		if (congChuc != null && congChuc.getCoQuanQuanLy() != null) {
			if (thamSo != null && thamSo.getGiaTri().toString().equals(congChuc.getCoQuanQuanLy().getCapCoQuanQuanLy().getId())) {
				return congChuc.getCoQuanQuanLy().getCha();
			} else {
				return congChuc.getCoQuanQuanLy();
			}
		}
		return null;
	}
}
