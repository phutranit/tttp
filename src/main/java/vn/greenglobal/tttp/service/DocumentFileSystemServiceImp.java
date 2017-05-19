package vn.greenglobal.tttp.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import vn.greenglobal.tttp.model.Document;
import vn.greenglobal.tttp.util.upload.StorageException;
import vn.greenglobal.tttp.util.upload.StorageFileNotFoundException;

@Component
public class DocumentFileSystemServiceImp implements DocumentFileSystemService{

	private static final Logger LOG = Logger.getLogger(DocumentFileSystemServiceImp.class);
	
	@Value("${file.repository}")
    private String fileStorageLocation;

    @PostConstruct
    public void init() {
        createDirectory(fileStorageLocation);
    }

    public String getFileStorageLocation() {
		return System.getProperty("user.home")+File.separator+fileStorageLocation;
	}
    
    @Override
    public void add(Document document) throws IOException {

        if (StringUtils.isEmpty(document.getName())) {
            LOG.info("file name cant be null");
            throw new IOException();
        }

        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(getFileStorageLocation(), document.getName())));
        stream.write(document.getContent());
        stream.close();
    }
    
    @Override
	public Resource loadAsResource(String filename) {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new StorageFileNotFoundException("Could not read file: " + filename);

			}
		} catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}
    
    @Override
	public Stream<Path> loadAll() {
    	Path path = Paths.get(getFileStorageLocation());
		try {
			return Files.walk(path, 1).filter(p -> !path.equals(path))
					.map(p -> path.relativize(p));
		} catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		Path path = Paths.get(getFileStorageLocation());
		return path.resolve(filename);
	}
	
    private void createDirectory(String path) {
        File file = new File(path);
        file.mkdirs();
    }
}
