package vn.greenglobal.tttp.service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;

import vn.greenglobal.tttp.model.Document;

public interface DocumentFileSystemService {

	public void add(Document document) throws IOException;
	public Resource loadAsResource(String filename);
	public Stream<Path> loadAll();
	public Path load(String filename);
}
