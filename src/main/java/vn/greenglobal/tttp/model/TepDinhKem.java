package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.LoaiFileDinhKemEnum;

@Entity
@Table(name = "tepdinhkem")
@ApiModel
public class TepDinhKem extends Model<TepDinhKem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4888057108535944010L;
	
	@Size(max=255)
	private String tenFile = "";
	@Size(max=255)
	private String urlFile = "";
	@ManyToOne
	private Don don;
	@Enumerated(EnumType.STRING)
	private LoaiFileDinhKemEnum loaiFileDinhKem;
	
	public String getTenFile() {
		return tenFile;
	}
	
	public void setTenFile(String tenFile) {
		this.tenFile = tenFile;
	}
	
	public String getUrlFile() {
		return urlFile;
	}
	
	public void setUrlFile(String urlFile) {
		this.urlFile = urlFile;
	}
	
	@ApiModelProperty(example = "{}")
	public Don getDon() {
		return don;
	}
	
	public void setDon(Don don) {
		this.don = don;
	}

	public LoaiFileDinhKemEnum getLoaiFileDinhKem() {
		return loaiFileDinhKem;
	}

	public void setLoaiFileDinhKem(LoaiFileDinhKemEnum loaiFileDinhKem) {
		this.loaiFileDinhKem = loaiFileDinhKem;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Long getTepDinhKemId() {
		return getId();
	}
}
