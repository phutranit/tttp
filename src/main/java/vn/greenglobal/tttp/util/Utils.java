package vn.greenglobal.tttp.util;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

import vn.greenglobal.tttp.model.Model;

public class Utils {

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
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, vio.getPropertyPath().toString().toUpperCase() + "_REQUIRED", "Trường " + vio.getPropertyPath() + " không được để trống!");
		if (vio.getMessageTemplate().equals("{" + NotNull.class.getName() + ".message}"))
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, vio.getPropertyPath().toString().toUpperCase() + "_NOT_NULL", "Trường " + vio.getPropertyPath() + " không được NULL!");
		if (vio.getMessageTemplate().equals("{" + Size.class.getName() + ".message}"))
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, vio.getPropertyPath().toString().toUpperCase() + "_INVALID_SIZE", "Trường " + vio.getPropertyPath() + " không được để trống!");
		return Utils.responseErrors(HttpStatus.BAD_REQUEST, "UNKNOWN", "UNKNOWN");
	}

	public static <T> ResponseEntity<Object> doSave(JpaRepository<T, ?> repository, T obj, PersistentEntityResourceAssembler eass, HttpStatus status) {
		try {
			obj = repository.save(obj);
		} catch (ConstraintViolationException e) {
			return returnError(e);
		} catch (Exception e) {
			System.out.println("doSave -> " + e.getCause());
			if (e.getCause() instanceof ConstraintViolationException)
				return returnError((ConstraintViolationException) e.getCause());
			if (e.getCause() != null && e.getCause().getCause() instanceof ConstraintViolationException)
				return returnError((ConstraintViolationException) e.getCause().getCause());
			if (e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause().getCause() instanceof ConstraintViolationException)
				return returnError((ConstraintViolationException) e.getCause().getCause());
			throw e;
		}
		return new ResponseEntity<>(eass.toFullResource(obj), status);
	}
	
	public static boolean isValidEmailAddress(String email) {
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
	}
	
	public static LocalDateTime fixTuNgay(String tuNgayCurrent) {
		// Fix tuNgay
		//LocalDateTime tuNgay = LocalDateTime.parse(tuNgayCurrent, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZ"));
		LocalDateTime tuNgay = LocalDateTime.parse(tuNgayCurrent);
		tuNgay = LocalDateTime.of(tuNgay.getYear(),
				tuNgay.getMonth(),tuNgay.getDayOfMonth(),0,0,0);
		return tuNgay;
	}
	
	public static LocalDateTime fixDenNgay(String denNgayCurrent) {
		// Fix denNgay
		LocalDateTime denNgay = LocalDateTime.parse(denNgayCurrent);
		denNgay = LocalDateTime.of(denNgay.getYear(),
				denNgay.getMonth(),denNgay.getDayOfMonth(),23,59,59);
		return denNgay;
	}
}
