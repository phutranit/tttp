package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "linhvucdoituongthanhtra")
@ApiModel
public class LinhVucDoiTuongThanhTra extends Model<LinhVucDoiTuongThanhTra> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7069686993087879386L;

	@NotBlank
	@Size(max=255)
	private String ten = "";
	@Size(max=255)
	private String moTa = "";

	private int soThuTu = 0;

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}
	
	@Transient
	@ApiModelProperty( hidden = true )
	public Long getLinhVucDoiTuongThanhTraId() {
		return getId();
	}

}
