package vn.greenglobal.tttp.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.DocumentMetaData;
import vn.greenglobal.tttp.repository.DocumentMetaDataRepository;
import vn.greenglobal.tttp.service.FileUploadService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@Api(value = "document", description = "File upload")
public class FileUploadController extends TttpController<DocumentMetaData> {

	@Value("${file.repository}")
	private String fileStorageLocation;

	public FileUploadController(BaseRepository<DocumentMetaData, ?> repo) {
		super(repo);
	}

	@Autowired
	FileUploadService fileService;

	@Autowired
	DocumentMetaDataRepository repo;

	@RequestMapping(value = "/documents/upload", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<?> upload(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestPart(value = "file", required = true) MultipartFile file, HttpServletRequest req)
			throws IOException {

		try {
			if (!file.isEmpty()) {
				Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
				BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();

				String originalFilename = file.getOriginalFilename();
				String fileName = originalFilename.substring(0, originalFilename.lastIndexOf('.'));
				String salkey = encryptor.encryptPassword(fileName + new Date().toString());

				fileName = md5PasswordEncoder.encodePassword(fileName, salkey) + originalFilename.substring(originalFilename.lastIndexOf('.'));

				fileService.upload(file, fileName, salkey, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
				return new ResponseEntity<>(getBaseUrl(req) + fileStorageLocation + fileName, new HttpHeaders(), HttpStatus.CREATED);
			}
			return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(value = "/documents/files/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<Object> serveFile(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable String filename) throws IOException {
		Resource fileResource = fileService.download(filename);
		
		try {
			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getURI() + "\"")
					.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileResource.contentLength())).build();
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(value = "/tttpdata/files/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<Object> serveFileUpload(@PathVariable String filename, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		try {
			File file = new File(fileService.getFileStorageLocation() + filename);

			HttpHeaders header = new HttpHeaders();
			header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"");
			header.add(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.length()));

			response.setContentLength((int) file.length());

			InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
			FileCopyUtils.copy(inputStream, response.getOutputStream());

			return ResponseEntity.ok(HttpStatus.FOUND);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(value = "/documents/uploadhandler", method = RequestMethod.POST)
	public ResponseEntity<?> uploadHandler(HttpServletRequest request) {

		try {
			Enumeration<String> headers = request.getHeaderNames();
			String result = "";
			for (; headers.hasMoreElements();) {
				String s = headers.nextElement();
				result += s + " = " + request.getHeader(s) + "; ";
			}

			return ResponseEntity.ok(Collections.singletonMap("response", result));
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	public static String getBaseUrl(HttpServletRequest request) {
		String scheme = request.getScheme() + "://";
		String serverName = request.getServerName();
		String serverPort = (request.getServerPort() == 80) ? "" : ":" + request.getServerPort();
		String contextPath = request.getContextPath();
		return scheme + serverName + serverPort + contextPath;
	}
}
