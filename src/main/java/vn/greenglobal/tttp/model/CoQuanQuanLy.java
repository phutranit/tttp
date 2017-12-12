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
@Table(name = "coquanquanly")
@ApiModel
public class CoQuanQuanLy extends Model<CoQuanQuanLy> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7182349007861458999L;

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
	
	private boolean isDonViNhanTTXM;
	private boolean isDonViNhanTheoDoiThucHien;
	private boolean isDonViTmp;
	
	@ManyToOne
	private CoQuanQuanLy cha;

	@ManyToOne
	private LoaiCoQuanQuanLy loaiCoQuanQuanLy;
	
	@ManyToOne
	@NotNull
	private CapCoQuanQuanLy capCoQuanQuanLy;

	@ManyToOne
	@NotNull
	private DonViHanhChinh donViHanhChinh;
	
	@ManyToOne
	private CoQuanQuanLy donVi;

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

	public boolean isDonViNhanTTXM() {
		return isDonViNhanTTXM;
	}

	public void setDonViNhanTTXM(boolean isDonViNhanTTXM) {
		this.isDonViNhanTTXM = isDonViNhanTTXM;
	}

	public boolean isDonViNhanTheoDoiThucHien() {
		return isDonViNhanTheoDoiThucHien;
	}

	public void setDonViNhanTheoDoiThucHien(boolean isDonViNhanTheoDoiThucHien) {
		this.isDonViNhanTheoDoiThucHien = isDonViNhanTheoDoiThucHien;
	}

	public boolean isDonViTmp() {
		return isDonViTmp;
	}

	public void setDonViTmp(boolean isDonViTmp) {
		this.isDonViTmp = isDonViTmp;
	}

	@ApiModelProperty(position = 4)
	public CoQuanQuanLy getCha() {
		return cha;
	}

	public void setCha(CoQuanQuanLy cha) {
		this.cha = cha;
	}
	
	@ApiModelProperty(position = 5, required = true, example = "{}")
	public LoaiCoQuanQuanLy getLoaiCoQuanQuanLy() {
		return loaiCoQuanQuanLy;
	}

	public void setLoaiCoQuanQuanLy(LoaiCoQuanQuanLy loaiCoQuanQuanLy) {
		this.loaiCoQuanQuanLy = loaiCoQuanQuanLy;
	}

	@ApiModelProperty(position = 6, required = true, example = "{}")
	public CapCoQuanQuanLy getCapCoQuanQuanLy() {
		return capCoQuanQuanLy;
	}

	public void setCapCoQuanQuanLy(CapCoQuanQuanLy capCoQuanQuanLy) {
		this.capCoQuanQuanLy = capCoQuanQuanLy;
	}

	@ApiModelProperty(position = 7, required = true, example = "{}")
	public DonViHanhChinh getDonViHanhChinh() {
		return donViHanhChinh;
	}

	public void setDonViHanhChinh(DonViHanhChinh donViHanhChinh) {
		this.donViHanhChinh = donViHanhChinh;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getCoQuanQuanLyId() {
		return getId();
	}
	
	@ApiModelProperty(hidden = true)
	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getDonViInfo() {
		if (getDonVi() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ma", getDonVi().getMa());
			map.put("ten", getDonVi().getTen());
			map.put("coQuanQuanLyId", getDonVi().getId());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCoQuanQuanLyCha() {
		if (getCha() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ma", getCha().getMa());
			map.put("ten", getCha().getTen());
			map.put("coQuanQuanLyId", getCha().getId());
			map.put("capCoQuanQuanLyId", getCha().getCapCoQuanQuanLy() != null ? getCha().getCapCoQuanQuanLy().getId() : 0);
			map.put("donViHanhChinhId", getCha().getDonViHanhChinh() != null ? getCha().getDonViHanhChinh().getId() : 0);
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getLoaiCoQuanQuanLyInfo() {
		if (getLoaiCoQuanQuanLy() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getLoaiCoQuanQuanLy().getTen());
			map.put("loaiCoQuanQuanLyId", getLoaiCoQuanQuanLy().getId());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCapCoQuanQuanLyCQQL() {
		if (getCapCoQuanQuanLy() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ma", getCapCoQuanQuanLy().getMa());
			map.put("ten", getCapCoQuanQuanLy().getTen());
			map.put("capCoQuanQuanLyId", getCapCoQuanQuanLy().getId());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getDonViHanhChinhCQQL() {
		if (getDonViHanhChinh() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ma", getDonViHanhChinh().getMa());
			map.put("ten", getDonViHanhChinh().getTen());
			map.put("donViHanhChinhId", getDonViHanhChinh().getId());
			return map;
		}
		return null;
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