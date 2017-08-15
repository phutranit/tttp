package vn.greenglobal.tttp.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.BuocGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.TinhTrangTaiLieuEnum;

@Entity
@Table(name = "tailieubangchung")
@ApiModel
public class TaiLieuBangChung extends Model<TaiLieuBangChung> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7863478663069074533L;
	@NotBlank
	@Size(max=255)
	private String ten = "";
	@Size(max=255)
	private String duongDan = "";
	@Size(max=255)
	private String tenFile = "";

	@Enumerated(EnumType.STRING)
	private TinhTrangTaiLieuEnum tinhTrangTaiLieu;
	@Enumerated(EnumType.STRING)
	private BuocGiaiQuyetEnum buocGiaiQuyet;
	
	@ManyToOne
	private SoTiepCongDan soTiepCongDan;

	@NotNull
	@ManyToOne
	private Don don;
	
	@Size(max=255)
	private String typeRequired = "";
	private boolean required;

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getDuongDan() {
		return duongDan;
	}

	public void setDuongDan(String duongDan) {
		this.duongDan = duongDan;
	}

	public String getTenFile() {
		return tenFile;
	}

	public void setTenFile(String tenFile) {
		this.tenFile = tenFile;
	}

	public TinhTrangTaiLieuEnum getTinhTrangTaiLieu() {
		return tinhTrangTaiLieu;
	}

	public void setTinhTrangTaiLieu(TinhTrangTaiLieuEnum tinhTrangTaiLieu) {
		this.tinhTrangTaiLieu = tinhTrangTaiLieu;
	}
	
	public BuocGiaiQuyetEnum getBuocGiaiQuyet() {
		return buocGiaiQuyet;
	}

	public void setBuocGiaiQuyet(BuocGiaiQuyetEnum buocGiaiQuyet) {
		this.buocGiaiQuyet = buocGiaiQuyet;
	}

	@ApiModelProperty(example = "{}")
	public SoTiepCongDan getSoTiepCongDan() {
		return soTiepCongDan;
	}

	public void setSoTiepCongDan(SoTiepCongDan soTiepCongDan) {
		this.soTiepCongDan = soTiepCongDan;
	}

	@ApiModelProperty(example = "{}")
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	public String getTypeRequired() {
		return typeRequired;
	}

	public void setTypeRequired(String typeRequired) {
		this.typeRequired = typeRequired;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getTaiLieuBangChungId() {
		return getId();
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public String getTinhTrangTaiLieuStr() {
		return getTinhTrangTaiLieu() != null ? getTinhTrangTaiLieu().getText() : "";
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