package vn.greenglobal.tttp.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
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
	
}
