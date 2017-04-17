package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "loaitailieu")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
@ApiModel
public class LoaiTaiLieu extends Model<LoaiTaiLieu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3826710912952156958L;

	@NotBlank
	private String ten = "";
	private String moTa = "";

	@ApiModelProperty(position = 1, required = true)
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@ApiModelProperty(position = 2)
	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getLoaiTaiLieuId() {
		return getId();
	}

}