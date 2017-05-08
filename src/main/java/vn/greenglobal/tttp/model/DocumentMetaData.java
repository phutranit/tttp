package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.beans.Transient;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table(name = "documentmetadata")
public class DocumentMetaData extends Model<DocumentMetaData> {

	private static final long serialVersionUID = 3960938328339804565L;

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String fileLocation;

    public DocumentMetaData() {
    }

    public DocumentMetaData(String name, String fileLocation, LocalDateTime lastModified) {
        this.setName(name);
        this.setFileLocation(fileLocation);
        setNgayTao(lastModified);
        setNgaySua(lastModified);
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
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
