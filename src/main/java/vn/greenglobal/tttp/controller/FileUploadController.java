package vn.greenglobal.tttp.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
import vn.greenglobal.tttp.service.FileUploadService;

@RestController
@Api(value = "document", description = "File upload")
public class FileUploadController extends TttpController<DocumentMetaData> {

	public FileUploadController(BaseRepository<DocumentMetaData, ?> repo) {
		super(repo);
	}

	@Autowired
	//RestServieResourceFacade restServieResourceFacade;
	FileUploadService fileService;
	
	
	
	@RequestMapping(value = "/documents/upload", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public ResponseEntity<?> upload(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestPart(value = "file", required = true) MultipartFile file) throws IOException {

		if (!file.isEmpty()) {
			fileService.upload(file);
			Resource fileResource = fileService.download(file.getOriginalFilename());
			return new ResponseEntity<>(fileResource.getURL(), new HttpHeaders(), HttpStatus.CREATED);
		}
		return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
	}

	@RequestMapping(value = "/documents/files/{filename:.+}", method = RequestMethod.GET)
	public ResponseEntity<Object> serveFile(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable String filename) throws IOException {
		Resource fileResource = fileService.download(filename);// storageService.loadAsResource(filename);
		System.out.println("uri:" + fileResource.getURI());
		System.out.println("url:" + fileResource.getURL());
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileResource.getURI() + "\"")
				.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(fileResource.contentLength())).build();
	}

}
