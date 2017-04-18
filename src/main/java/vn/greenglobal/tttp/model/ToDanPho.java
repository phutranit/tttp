package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "todanpho")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class ToDanPho extends Model<ToDanPho> {
	private static final long serialVersionUID = 5662282127057182748L;
	
	@NotNull
	private String ten = "";
	private String moTa = "";

	@ManyToOne
	private DonViHanhChinh donViHanhChinh;

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

	@ApiModelProperty(position = 3, required = true, example = "{}")
	public DonViHanhChinh getDonViHanhChinh() {
		return donViHanhChinh;
	}

	public void setDonViHanhChinh(DonViHanhChinh donViHanhChinh) {
		this.donViHanhChinh = donViHanhChinh;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getToDanPhoId() {
		return getId();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public DonViHanhChinh getToDanPhoDVHC() {
		return getDonViHanhChinh();
	}
}