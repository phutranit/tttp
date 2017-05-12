package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "wf_process")
@ApiModel
public class Process extends Model<Process>{

	private static final long serialVersionUID = -421604915416180673L;
	
	@NotBlank
	private String tenQuyTrinh;
	private String ghiChu;
	
	public String getTenQuyTrinh() {
		return tenQuyTrinh;
	}
	
	public void setTenQuyTrinh(String tenQuyTrinh) {
		this.tenQuyTrinh = tenQuyTrinh;
	}
	
	public String getGhiChu() {
		return ghiChu;
	}
	
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	
	

}
