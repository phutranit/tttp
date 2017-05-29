package vn.greenglobal.tttp.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.TinhTrangGiaiQuyetEnum;

@Entity
@Table(name = "lichsugiaiquyetdon")
@ApiModel
public class LichSuGiaiQuyetDon extends Model<LichSuGiaiQuyetDon> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -626978002365811586L;

	@Lob
	private String yKienGiaiQuyet;
	@Lob
	private String ghiChu;

	private int thuTuThucHien;
	
	@ManyToOne
	private GiaiQuyetDon giaiQuyetDon;
	
	@ManyToOne
	private State nextState;
	@ManyToOne
	private CongChuc congChuc;
	
	@Enumerated(EnumType.STRING)
	private TinhTrangGiaiQuyetEnum tinhTrangGiaiQuyet;
	
	@ManyToOne
	private CongChuc canBoXuLyChiDinh;

	public String getyKienGiaiQuyet() {
		return yKienGiaiQuyet;
	}

	public void setyKienGiaiQuyet(String yKienGiaiQuyet) {
		this.yKienGiaiQuyet = yKienGiaiQuyet;
	}
	
	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public int getThuTuThucHien() {
		return thuTuThucHien;
	}

	public void setThuTuThucHien(int thuTuThucHien) {
		this.thuTuThucHien = thuTuThucHien;
	}

	public GiaiQuyetDon getGiaiQuyetDon() {
		return giaiQuyetDon;
	}

	public void setGiaiQuyetDon(GiaiQuyetDon giaiQuyetDon) {
		this.giaiQuyetDon = giaiQuyetDon;
	}

	public State getNextState() {
		return nextState;
	}

	public void setNextState(State nextState) {
		this.nextState = nextState;
	}

	public CongChuc getCongChuc() {
		return congChuc;
	}

	public void setCongChuc(CongChuc congChuc) {
		this.congChuc = congChuc;
	}

	public TinhTrangGiaiQuyetEnum getTinhTrangGiaiQuyet() {
		return tinhTrangGiaiQuyet;
	}

	public void setTinhTrangGiaiQuyet(TinhTrangGiaiQuyetEnum tinhTrangGiaiQuyet) {
		this.tinhTrangGiaiQuyet = tinhTrangGiaiQuyet;
	}
	
	public CongChuc getCanBoXuLyChiDinh() {
		return canBoXuLyChiDinh;
	}

	public void setCanBoXuLyChiDinh(CongChuc canBoXuLyChiDinh) {
		this.canBoXuLyChiDinh = canBoXuLyChiDinh;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getLichSuGiaiQuyetId() {
		return getId();
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
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getGiaiQuyetDonInfo() {
		if (getGiaiQuyetDon() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("giaiQuyetDonId", getNguoiSua().getId());
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
	
}