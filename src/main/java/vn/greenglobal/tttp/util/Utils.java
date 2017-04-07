package vn.greenglobal.tttp.util;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import vn.greenglobal.tttp.model.Model;

public class Utils {

	public static void copyValues(Model<?> source, Model<?> target, Iterable<String> properties) {
		BeanWrapper src = new BeanWrapperImpl(source);
		BeanWrapper trg = new BeanWrapperImpl(target);
		System.out.println(src.getClass());
		for (String propertyName : properties) {
			trg.setPropertyValue(propertyName, src.getPropertyValue(propertyName));
		}

	}
}
