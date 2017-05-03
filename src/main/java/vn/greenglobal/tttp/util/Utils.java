package vn.greenglobal.tttp.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.docx4j.model.datastorage.migration.VariablePrepare;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.FileCopyUtils;

import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Model;
import vn.greenglobal.tttp.model.NguoiDung;

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
			return Utils.responseErrors(HttpStatus.BAD_REQUEST,
					vio.getPropertyPath().toString().toUpperCase() + "_REQUIRED",
					"Trường " + vio.getPropertyPath() + " không được để trống!");
		if (vio.getMessageTemplate().equals("{" + NotNull.class.getName() + ".message}"))
			return Utils.responseErrors(HttpStatus.BAD_REQUEST,
					vio.getPropertyPath().toString().toUpperCase() + "_NOT_NULL",
					"Trường " + vio.getPropertyPath() + " không được NULL!");
		if (vio.getMessageTemplate().equals("{" + Size.class.getName() + ".message}"))
			return Utils.responseErrors(HttpStatus.BAD_REQUEST,
					vio.getPropertyPath().toString().toUpperCase() + "_INVALID_SIZE",
					"Trường " + vio.getPropertyPath() + " không được để trống!");
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
		tuNgay = LocalDateTime.of(tuNgay.getYear(), tuNgay.getMonth(), tuNgay.getDayOfMonth(), 0, 0, 0);
		return tuNgay;
	}

	public static LocalDateTime fixDenNgay(String denNgayCurrent) {
		// Fix denNgay
		ZonedDateTime zdt = ZonedDateTime.parse(denNgayCurrent);
		LocalDateTime denNgay = zdt.toLocalDateTime();
		denNgay = LocalDateTime.of(denNgay.getYear(), denNgay.getMonth(), denNgay.getDayOfMonth(), 23, 59, 59);
		return denNgay;
	}

	public static void exportWord(HttpServletResponse response, String pathFile, HashMap<String, String> mappings) {
		try {
			WordprocessingMLPackage wordMLPackage;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			InputStream inputStream = null;
			File file = new File(pathFile);

			if (!file.exists()) {
				String errorMessage = "Sorry. The file you are looking for does not exist";
				OutputStream outputStream = response.getOutputStream();
				outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
				outputStream.close();
				return;
			}

			String mimeType = URLConnection.guessContentTypeFromName(file.getName());
			if (mimeType == null) {
				mimeType = "application/octet-stream";
			}

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", String.format("attachment; filename=\"" + file.getName() + "\""));

			wordMLPackage = WordprocessingMLPackage.load(file);
			VariablePrepare.prepare(wordMLPackage);
			wordMLPackage.getMainDocumentPart().variableReplace(mappings);
			wordMLPackage = wordMLPackage.getMainDocumentPart().convertAltChunks();
			wordMLPackage.save(out);

			response.setContentLength((int) out.size());
			out.close();

			inputStream = new BufferedInputStream(new ByteArrayInputStream(out.toByteArray()));
			FileCopyUtils.copy(inputStream, response.getOutputStream());

			response.flushBuffer();
			inputStream.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static NguoiDung quyenValidate(ProfileUtils profileUtil, String authorization, QuyenEnum quyen) {
		NguoiDung nguoiDung = profileUtil.getUserInfo(authorization);
		if (nguoiDung != null && nguoiDung.checkQuyen(quyen)) {
			return nguoiDung;
		}
		return null;
	}
}
