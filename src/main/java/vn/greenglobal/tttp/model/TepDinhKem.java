package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "tepdinhkem")
@ApiModel
public class TepDinhKem extends Model<TepDinhKem>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4888057108535944010L;
	
	private String tenFile = "";
	private String urlFile = "";
	@ManyToOne
	private Don don;
	
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
	
	public Don getDon() {
		return don;
	}
	
	public void setDon(Don don) {
		this.don = don;
	}
}
