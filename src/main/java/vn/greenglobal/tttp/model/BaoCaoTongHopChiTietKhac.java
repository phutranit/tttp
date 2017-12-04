package vn.greenglobal.tttp.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "baocaotonghopchitietkhac")
@ApiModel
public class BaoCaoTongHopChiTietKhac extends Model<BaoCaoTongHopChiTietKhac> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8600228205596552113L;
	
	private BaoCaoTongHopChiTiet baoCaoTongHopChiTiet;
	@Size(max=4000)
	private String soLieuBaoCao = "";
	

	public BaoCaoTongHopChiTiet getBaoCaoTongHopChiTiet() {
		return baoCaoTongHopChiTiet;
	}

	public void setBaoCaoTongHopChiTiet(BaoCaoTongHopChiTiet baoCaoTongHopChiTiet) {
		this.baoCaoTongHopChiTiet = baoCaoTongHopChiTiet;
	}

	public String getSoLieuBaoCao() {
		return soLieuBaoCao;
	}

	public void setSoLieuBaoCao(String soLieuBaoCao) {
		this.soLieuBaoCao = soLieuBaoCao;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getBaoCaoTongHopChiTietKhacId() {
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
}