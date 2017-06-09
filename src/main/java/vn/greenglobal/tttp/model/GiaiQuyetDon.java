package vn.greenglobal.tttp.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryInit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.TinhTrangGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;

@Entity
@Table(name = "giaiquyetdon")
@ApiModel
public class GiaiQuyetDon extends Model<GiaiQuyetDon> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -626978002365811586L;

	// @Lob
	private String yKienGiaiQuyet;

	private int thuTuThucHien;

	private boolean laTTXM;

	@ManyToOne
	@QueryInit("*.*.*")
	private ThongTinGiaiQuyetDon thongTinGiaiQuyetDon;
	@ManyToOne
	private State nextState;
	@ManyToOne
	private Form nextForm;
	@ManyToOne
	@QueryInit("*.*.*")
	private CongChuc congChuc;
	@ManyToOne
	@QueryInit("*.*.*")
	private CoQuanQuanLy phongBanGiaiQuyet;
	@ManyToOne
	private CoQuanQuanLy donViGiaiQuyet;
	@ManyToOne
	private CongChuc canBoXuLyChiDinh;
	@ManyToOne
	private SoTiepCongDan soTiepCongDan;
	private boolean old;

	@Enumerated(EnumType.STRING)
	private TinhTrangGiaiQuyetEnum tinhTrangGiaiQuyet;

	@Enumerated(EnumType.STRING)
	private VaiTroEnum chucVu;

	@ApiModelProperty(hidden = true)
	public VaiTroEnum getChucVu() {
		return chucVu;
	}

	public void setChucVu(VaiTroEnum chucVu) {
		this.chucVu = chucVu;
	}

	public String getyKienGiaiQuyet() {
		return yKienGiaiQuyet;
	}

	public void setyKienGiaiQuyet(String yKienGiaiQuyet) {
		this.yKienGiaiQuyet = yKienGiaiQuyet;
	}

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	public boolean isOld() {
		return old;
	}

	public void setOld(boolean old) {
		this.old = old;
	}

	@ApiModelProperty(hidden = true)
	public int getThuTuThucHien() {
		return thuTuThucHien;
	}

	public void setThuTuThucHien(int thuTuThucHien) {
		this.thuTuThucHien = thuTuThucHien;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public ThongTinGiaiQuyetDon getThongTinGiaiQuyetDon() {
		return thongTinGiaiQuyetDon;
	}

	public void setThongTinGiaiQuyetDon(ThongTinGiaiQuyetDon thongTinGiaiQuyetDon) {
		this.thongTinGiaiQuyetDon = thongTinGiaiQuyetDon;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public CoQuanQuanLy getPhongBanGiaiQuyet() {
		return phongBanGiaiQuyet;
	}

	public void setPhongBanGiaiQuyet(CoQuanQuanLy phongBanGiaiQuyet) {
		this.phongBanGiaiQuyet = phongBanGiaiQuyet;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public State getNextState() {
		return nextState;
	}

	public void setNextState(State nextState) {
		this.nextState = nextState;
	}

	@ApiModelProperty(hidden = true)
	public CongChuc getCongChuc() {
		return congChuc;
	}

	public void setCongChuc(CongChuc congChuc) {
		this.congChuc = congChuc;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public CongChuc getCanBoXuLyChiDinh() {
		return canBoXuLyChiDinh;
	}

	public void setCanBoXuLyChiDinh(CongChuc canBoXuLyChiDinh) {
		this.canBoXuLyChiDinh = canBoXuLyChiDinh;
	}

	@ApiModelProperty(hidden = true)
	public TinhTrangGiaiQuyetEnum getTinhTrangGiaiQuyet() {
		return tinhTrangGiaiQuyet;
	}

	public void setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum tinhTrangGiaiQuyet) {
		this.tinhTrangGiaiQuyet = tinhTrangGiaiQuyet;
	}

	@ApiModelProperty(hidden = true)
	public Form getNextForm() {
		return nextForm;
	}

	public void setNextForm(Form nextForm) {
		this.nextForm = nextForm;
	}

	@ApiModelProperty(hidden = true)
	public boolean isLaTTXM() {
		return laTTXM;
	}

	public void setLaTTXM(boolean laTTXM) {
		this.laTTXM = laTTXM;
	}
	
	@ApiModelProperty(hidden = true)
	public SoTiepCongDan getSoTiepCongDan() {
		return soTiepCongDan;
	}

	public void setSoTiepCongDan(SoTiepCongDan soTiepCongDan) {
		this.soTiepCongDan = soTiepCongDan;
	}
	
	public CoQuanQuanLy getDonViGiaiQuyet() {
		return donViGiaiQuyet;
	}

	public void setDonViGiaiQuyet(CoQuanQuanLy donViGiaiQuyet) {
		this.donViGiaiQuyet = donViGiaiQuyet;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getLichSuGiaiQuyetId() {
		return getId();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getNextFormId() {
		if (getNextForm() != null) {
			return getNextForm().getId();
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNguoiTaoInfo() {
		if (getNguoiTao() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId",
					getNguoiTao().getCoQuanQuanLy() != null ? getNguoiTao().getCoQuanQuanLy().getId() : 0);
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
			map.put("coQuanQuanLyId",
					getNguoiSua().getCoQuanQuanLy() != null ? getNguoiSua().getCoQuanQuanLy().getId() : 0);
			map.put("hoVaTen", getNguoiSua().getHoVaTen());
			map.put("congChucId", getNguoiSua().getId());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getThongTinGiaiQuyetDonInfo() {
		if (getThongTinGiaiQuyetDon() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("thongTinGiaiQuyetDonId", getThongTinGiaiQuyetDon().getId());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNextStateInfo() {
		if (getNextState() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getNextState().getTen());
			map.put("tenVietTat", getNextState().getTenVietTat());
			map.put("type", getNextState().getType());
			map.put("nextStateId", getNextState().getId());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCongChucInfo() {
		if (getCongChuc() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("hoVaTen", getCongChuc().getHoVaTen());
			if (getCongChuc() != null && getCongChuc().getCoQuanQuanLy() != null) {
				Map<String, Object> mapCoQuanQuanLy = new HashMap<>();
				mapCoQuanQuanLy.put("ten", getCongChuc().getCoQuanQuanLy().getTen());
				if (getCongChuc().getCoQuanQuanLy().getDonVi() != null) {
					Map<String, Object> mapDonVi = new HashMap<>();
					mapDonVi.put("ten", getCongChuc().getCoQuanQuanLy().getDonVi().getTen());
					mapDonVi.put("coQuanQuanLyId", getCongChuc().getCoQuanQuanLy().getDonVi().getId());
					mapCoQuanQuanLy.put("donViInfo", mapDonVi);
				}
				mapCoQuanQuanLy.put("coQuanQuanLyId", getCongChuc().getCoQuanQuanLy().getId());
				map.put("coQuanQuanLyInfo", mapCoQuanQuanLy);
			}
			map.put("congChucId", getCongChuc().getId());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCanBoXuLyChiDinhInfo() {
		if (getCanBoXuLyChiDinh() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("hoVaTen", getCanBoXuLyChiDinh().getHoVaTen());
			if (getCongChuc() != null && getCanBoXuLyChiDinh().getCoQuanQuanLy() != null) {
				Map<String, Object> mapCoQuanQuanLy = new HashMap<>();
				mapCoQuanQuanLy.put("ten", getCanBoXuLyChiDinh().getCoQuanQuanLy().getTen());
				if (getCanBoXuLyChiDinh().getCoQuanQuanLy().getDonVi() != null) {
					Map<String, Object> mapDonVi = new HashMap<>();
					mapDonVi.put("ten", getCanBoXuLyChiDinh().getCoQuanQuanLy().getDonVi().getTen());
					mapDonVi.put("coQuanQuanLyId", getCanBoXuLyChiDinh().getCoQuanQuanLy().getDonVi().getId());
					mapCoQuanQuanLy.put("donViInfo", mapDonVi);
				}
				mapCoQuanQuanLy.put("coQuanQuanLyId", getCanBoXuLyChiDinh().getCoQuanQuanLy().getId());
				map.put("coQuanQuanLyInfo", mapCoQuanQuanLy);
			}
			map.put("congChucId", getCanBoXuLyChiDinh().getId());
			return map;
		}
		return null;
	}

}