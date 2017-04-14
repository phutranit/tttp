package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "linhvucdonthu")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class LinhVucDonThu extends Model<LinhVucDonThu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6767282651849050987L;
	
	private String ma = "";
	
	@NotEmpty
	private String ten = "";
	private String moTa = "";

	private boolean linhVucKhac;

	@ManyToOne
	private LinhVucDonThu cha;

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

	@ApiModelProperty(position = 4)
	public boolean isLinhVucKhac() {
		return linhVucKhac;
	}

	public void setLinhVucKhac(boolean linhVucKhac) {
		this.linhVucKhac = linhVucKhac;
	}

	@ApiModelProperty(position = 5)
	public LinhVucDonThu getCha() {
		return cha;
	}

	public void setCha(LinhVucDonThu cha) {
		this.cha = cha;
	}
	
	@Transient
	@ApiModelProperty(hidden=true)
	public Long getLinhVucDonThuId() {
		return getId();
	}
	
	@Transient
	@ApiModelProperty(hidden=true)
	public LinhVucDonThu getLinhVucDonThuCha() {
		return getCha();
	}

}