package vn.greenglobal.tttp.service;

import java.io.IOException;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.greenglobal.tttp.model.Document;
import vn.greenglobal.tttp.model.DocumentMetaData;
import vn.greenglobal.tttp.repository.DocumentMetaDataRepository;

@Service
public class FileUploadService {

	@Value("${file.repository}")
	private String fileStorageLocation;

	@Autowired
	DocumentFileSystemService documentFileSystemService;

	@Autowired
	DocumentMetaDataRepository documentRepository;

	public void upload(MultipartFile file) throws IOException {
		String fileName = file.getOriginalFilename();
		byte[] content = file.getBytes();
		Document document = new Document(fileName, content);
		DocumentMetaData documentMetaData = new DocumentMetaData(fileName, fileStorageLocation, LocalDateTime.now());
		documentFileSystemService.add(document);
		documentRepository.save(documentMetaData);
	}

	public Resource download(String filename) {
		return documentFileSystemService.loadAsResource(filename);
	}

}
