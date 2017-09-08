package vn.greenglobal.tttp.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "donvihanhchinh")
@ApiModel
public class DonViHanhChinh extends Model<DonViHanhChinh> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8811521308334603087L;

	@Size(max=255)
	private String ma = "";
	@NotBlank
	@Size(max=255)
	private String ten = "";
	@NotBlank
	@Size(max=255)
	private String tenSearch = "";
	//@Lob
	private String moTa = "";
	//@Lob
	private String moTaSearch = "";

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

	@JsonIgnore
	public String getTenSearch() {
		return tenSearch;
	}

	public void setTenSearch(String tenSearch) {
		this.tenSearch = tenSearch;
	}

	@ApiModelProperty(position = 3)
	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@JsonIgnore
	public String getMoTaSearch() {
		return moTaSearch;
	}

	public void setMoTaSearch(String moTaSearch) {
		this.moTaSearch = moTaSearch;
	}

	@ApiModelProperty(position = 4)
	public DonViHanhChinh getCha() {
		return cha;
	}

	public void setCha(DonViHanhChinh cha) {
		this.cha = cha;
	}

	@ApiModelProperty(position = 5, required = true, example = "{}")
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

	@Transient
	@ApiModelProperty(hidden = true)
	public CapDonViHanhChinh getCapDonViHanhChinhDVHC() {
		return getCapDonViHanhChinh();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNguoiTaoInfo() {
		if (getNguoiTao() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getNguoiTao().getCoQuanQuanLy() != null ? getNguoiTao().getCoQuanQuanLy().getId() : 0);
			map.put("hoVaTen", getNguoiTao().getHoVaTen());
			map.put("congChucId", getNguoiTao().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNguoiSuaInfo() {
		if (getNguoiSua() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getNguoiSua().getCoQuanQuanLy() != null ? getNguoiSua().getCoQuanQuanLy().getId() : 0);
			map.put("hoVaTen", getNguoiSua().getHoVaTen());
			map.put("congChucId", getNguoiSua().getId());
			return map;
		}
		return null;
	}
}