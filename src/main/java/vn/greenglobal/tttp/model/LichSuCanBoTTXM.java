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
@Table(name = "lichsucanbottxmchidinh")
@ApiModel
public class LichSuCanBoTTXM extends Model<LichSuCanBoTTXM> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3811126854333108737L;
	
	public LichSuCanBoTTXM() {
	}
	
	public LichSuCanBoTTXM(CongChuc canBoXuLy, CoQuanQuanLy donVi, Don don) {
		this.canBoXuLy = canBoXuLy;
		this.donVi = donVi;
		this.don = don;
	}
	
	@ManyToOne
	private CongChuc canBoXuLy;
	@ManyToOne
	private CoQuanQuanLy donVi;
	@ManyToOne
	private Don don;
	
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	public CongChuc getCanBoXuLy() {
		return canBoXuLy;
	}

	public void setCanBoXuLy(CongChuc canBoXuLy) {
		this.canBoXuLy = canBoXuLy;
	}

	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getLichSuCanBoXuLyId() {
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
	public Map<String, Object> getCanBoXuLyInfo() {
		if (getCanBoXuLy() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getCanBoXuLy().getCoQuanQuanLy() != null ? getNguoiSua().getCoQuanQuanLy().getId() : 0);
			map.put("hoVaTen", getCanBoXuLy().getHoVaTen());
			map.put("congChucId", getCanBoXuLy().getId());
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