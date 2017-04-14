package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "dantoc")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class DanToc extends Model<DanToc> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7550664635055554646L;
	
	private String ma = "";
	@NotEmpty
	private String ten = "";
	private String tenKhac = "";
	private String moTa = "";

	private boolean thieuSo;

	@ApiModelProperty(position = 2)
	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	@ApiModelProperty(position = 1, required = true)
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@ApiModelProperty(position = 3)
	public String getTenKhac() {
		return tenKhac;
	}

	public void setTenKhac(String tenKhac) {
		this.tenKhac = tenKhac;
	}

	@ApiModelProperty(position = 4)
	public boolean isThieuSo() {
		return thieuSo;
	}

	public void setThieuSo(boolean thieuSo) {
		this.thieuSo = thieuSo;
	}

	@ApiModelProperty(position = 5)
	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@Transient
	@ApiModelProperty(hidden=true)
	public Long getDanTocId() {
		return getId();
	}
}