package vn.greenglobal.tttp.service;

import java.io.IOException;
import java.time.LocalDateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.greenglobal.tttp.model.CongChuc;
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

	public String getFileStorageLocation() {
		return System.getProperty("user.home") + fileStorageLocation;
	}

	public void upload(MultipartFile file, String fileName, Long congChucId) throws IOException {
		String originalFilename = file.getOriginalFilename();
		
		byte[] content = file.getBytes();
		Document document = new Document(fileName, content);
		DocumentMetaData documentMetaData = new DocumentMetaData(originalFilename, getFileStorageLocation(), LocalDateTime.now());
		documentFileSystemService.add(document);
		CongChuc cc = new CongChuc();
		cc.setId(congChucId);
		documentMetaData.setNguoiTao(cc);
		documentMetaData.setNguoiSua(cc);
		documentRepository.save(documentMetaData);
	}

	public Resource download(String filename) {
		return documentFileSystemService.loadAsResource(filename);
	}

}
