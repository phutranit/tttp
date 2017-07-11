package vn.greenglobal.tttp.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "coquantochuctiepdan")
@ApiModel
public class CoQuanToChucTiepDan extends Model<CoQuanToChucTiepDan> {

	private static final long serialVersionUID = -4101233843699537605L;

	@Size(max=255)
	private String chucVu = "";

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	@NotNull
	@ManyToOne
	private CoQuanQuanLy coQuanDonVi;
	@NotNull
	@ManyToOne
	private CongChuc nguoiDaiDien;

	@ApiModelProperty(example = "{}")
	public CoQuanQuanLy getCoQuanDonVi() {
		return coQuanDonVi;
	}

	public void setCoQuanDonVi(CoQuanQuanLy coQuanDonVi) {
		this.coQuanDonVi = coQuanDonVi;
	}

	@ApiModelProperty(example = "{}")
	public CongChuc getNguoiDaiDien() {
		return nguoiDaiDien;
	}

	public void setNguoiDaiDien(CongChuc nguoiDaiDien) {
		this.nguoiDaiDien = nguoiDaiDien;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getCoQuanToChucTiepDanId() {
		return getId();
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCoQuanDonViInfo() {
		if (getNguoiTao() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getCoQuanDonVi() != null ? getCoQuanDonVi().getId() : 0);
			map.put("ten", getCoQuanDonVi().getTen());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNguoiDaiDienInfo() {
		if (getNguoiTao() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("congChucId", getNguoiDaiDien() != null ? getNguoiDaiDien().getId() : 0);
			map.put("ten", getNguoiDaiDien().getHoVaTen());
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