package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "quoctich")
@ApiModel
public class QuocTich extends Model<QuocTich> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6346735397507161561L;

	private String ma = "";
	@NotBlank
	private String ten = "";
	private String moTa = "";

	@ApiModelProperty(position = 1)
	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	@ApiModelProperty(position = 2, required = true)
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@ApiModelProperty(position = 3)
	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getQuocTichId() {
		return getId();
	}

}