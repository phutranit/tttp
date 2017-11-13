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
@Table(name = "lichsucanboxulychidinh")
@ApiModel
public class LichSuCanBoXuLyChiDinh extends Model<LichSuCanBoXuLyChiDinh> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2506862994836261167L;

	@ManyToOne
	private CongChuc canBoChiDinh;
	@ManyToOne
	private CoQuanQuanLy donVi;

	public CongChuc getCanBoChiDinh() {
		return canBoChiDinh;
	}

	public void setCanBoChiDinh(CongChuc canBoChiDinh) {
		this.canBoChiDinh = canBoChiDinh;
	}

	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getLichSuCanBoXyLyChiDinhId() {
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
	public Map<String, Object> getCanBoChiDinhInfo() {
		if (getCanBoChiDinh() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getCanBoChiDinh().getCoQuanQuanLy() != null ? getNguoiSua().getCoQuanQuanLy().getId() : 0);
			map.put("hoVaTen", getCanBoChiDinh().getHoVaTen());
			map.put("congChucId", getCanBoChiDinh().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getDonViInfo() {
		if (getDonVi() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getDonVi().getId());
			map.put("ten", getDonVi().getTen());
			return map;
		}
		return null;
	}
}