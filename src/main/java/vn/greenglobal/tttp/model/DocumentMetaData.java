package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "documentmetadata")
public class DocumentMetaData extends Model<DocumentMetaData> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9213336720883904141L;
	
	private String salkey = "";
	private String name = "";
	private String fileLocation = "";

	public DocumentMetaData() {
	}

	public DocumentMetaData(String salkey, String name, String fileLocation, LocalDateTime lastModified) {
		this.setSalkey(salkey);
		this.setName(name);
		this.setFileLocation(fileLocation);
		setNgayTao(lastModified);
		setNgaySua(lastModified);
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getSalkey() {
		return salkey;
	}

	public void setSalkey(String salkey) {
		this.salkey = salkey;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getFileLocation() {
		return fileLocation;
	}
}
