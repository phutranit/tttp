package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "donvihanhchinh")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class DonViHanhChinh extends Model<DonViHanhChinh> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8811521308334603087L;

	private String ma = "";
	@NotBlank
	private String ten = "";
	private String moTa = "";

	@ManyToOne
	private DonViHanhChinh cha;

	@NotNull
	@ManyToOne
	private CapDonViHanhChinh capDonViHanhChinh;

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
	public DonViHanhChinh getCha() {
		return cha;
	}

	public void setCha(DonViHanhChinh cha) {
		this.cha = cha;
	}

	@ApiModelProperty(position = 5, required = true, example="{}")
	public CapDonViHanhChinh getCapDonViHanhChinh() {
		return capDonViHanhChinh;
	}

	public void setCapDonViHanhChinh(CapDonViHanhChinh capDonViHanhChinh) {
		this.capDonViHanhChinh = capDonViHanhChinh;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getDonViHanhChinhId() {
		return getId();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public DonViHanhChinh getDonViHanhChinhCha() {
		return getCha();
	}
}