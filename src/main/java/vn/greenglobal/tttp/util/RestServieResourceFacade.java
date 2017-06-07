package vn.greenglobal.tttp.util;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import vn.greenglobal.tttp.service.FileUploadService;

@Component
public class RestServieResourceFacade {

	@Autowired
	FileUploadService fileUploadService;

	public void upload(MultipartFile file) throws IOException {
		fileUploadService.upload(file, null);
	}

	public Resource download(String filename) throws IOException {
		return fileUploadService.download(filename);
	}

	public void upload(MultipartFile[] files) throws IOException {
		for (MultipartFile file : files) {
			fileUploadService.upload(file, null);
		}
	}
}
