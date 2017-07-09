package vn.greenglobal.tttp.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "coquantochuctiepdan")
@ApiModel
public class CoQuanToChucTiepDan extends Model<CoQuanToChucTiepDan> {

	private static final long serialVersionUID = -4101233843699537605L;

//	@Size(max=255)
//	private String ten = "";
//	@Size(max=255)
//	private String nguoiDaiDien = "";
//	@Size(max=255)
//	private String chucVu = "";

//	public String getTen() {
//		return ten;
//	}
//
//	public void setTen(String ten) {
//		this.ten = ten;
//	}
//
//	public String getNguoiDaiDien() {
//		return nguoiDaiDien;
//	}
//
//	public void setNguoiDaiDien(String nguoiDaiDien) {
//		this.nguoiDaiDien = nguoiDaiDien;
//	}
//
//	public String getChucVu() {
//		return chucVu;
//	}
//
//	public void setChucVu(String chucVu) {
//		this.chucVu = chucVu;
//	}

	@ManyToOne
	private CoQuanQuanLy tenCoQuanDonVi;
	@ManyToOne
	private CongChuc nguoiDaiDien;
	@ManyToOne
	private ChucVu chucVu;

	@ApiModelProperty(example = "{}")
	public CoQuanQuanLy getTenCoQuanDonVi() {
		return tenCoQuanDonVi;
	}

	public void setTenCoQuanDonVi(CoQuanQuanLy tenCoQuanDonVi) {
		this.tenCoQuanDonVi = tenCoQuanDonVi;
	}

	@ApiModelProperty(example = "{}")
	public CongChuc getNguoiDaiDien() {
		return nguoiDaiDien;
	}

	public void setNguoiDaiDien(CongChuc nguoiDaiDien) {
		this.nguoiDaiDien = nguoiDaiDien;
	}

	@ApiModelProperty(example = "{}")
	public ChucVu getChucVu() {
		return chucVu;
	}

	public void setChucVu(ChucVu chucVu) {
		this.chucVu = chucVu;
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
			map.put("coQuanQuanLyId", getTenCoQuanDonVi() != null ? getTenCoQuanDonVi().getId() : 0);
			map.put("ten", getTenCoQuanDonVi().getTen());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNguoiDaiDienInfo() {
		if (getNguoiTao() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("nguoiDaiDienId", getNguoiDaiDien() != null ? getNguoiDaiDien().getId() : 0);
			map.put("ten", getNguoiDaiDien().getHoVaTen());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getChucVuInfo() {
		if (getNguoiTao() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("chucVuId", getChucVu() != null ? getChucVu().getId() : 0);
			map.put("ten", getChucVu().getTen());
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