package vn.greenglobal.tttp.util;

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

import vn.greenglobal.core.model.common.BaseRepository;
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
		System.out.println(vio);
		if (vio.getMessageTemplate().equals("{" + NotBlank.class.getName() + ".message}"))
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, vio.getPropertyPath().toString().toUpperCase() + "_REQUIRED", "Trường " + vio.getPropertyPath() + " không được để trống!");
		if (vio.getMessageTemplate().equals("{" + NotNull.class.getName() + ".message}"))
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, vio.getPropertyPath().toString().toUpperCase() + "_REQUIRED", "Trường " + vio.getPropertyPath() + " không được để trống!");
		if (vio.getMessageTemplate().equals("{" + Size.class.getName() + ".message}"))
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, vio.getPropertyPath().toString().toUpperCase() + "_REQUIRED", "Trường " + vio.getPropertyPath() + " không được để trống!");
		return Utils.responseErrors(HttpStatus.BAD_REQUEST, "UNKNOWN", "UNKNOWN");
	}

	public static <T> ResponseEntity<Object> doSave(JpaRepository<T, ?> repository, T obj, PersistentEntityResourceAssembler eass, HttpStatus status) {
		try {
			repository.save(obj);
		} catch (ConstraintViolationException e) {
			return returnError(e);
		}
		return new ResponseEntity<>(eass.toFullResource(obj), status);
	}
}
