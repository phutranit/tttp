package vn.greenglobal.tttp.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.util.Utils;

@Entity
@Table(name = "baocaodonvichitiettam")
@ApiModel
public class BaoCaoDonViChiTietTam extends Model<BaoCaoDonViChiTietTam> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6692844077535174907L;
	@Size(max=4000)
	private String soLieuBaoCao = "";
	@ManyToOne
	private BaoCaoDonViChiTiet baoCaoDonViChiTiet;
	
	@JsonIgnore
	public String getSoLieuBaoCao() {
		return soLieuBaoCao;
	}

	public void setSoLieuBaoCao(String soLieuBaoCao) {
		this.soLieuBaoCao = soLieuBaoCao;
	}

	public BaoCaoDonViChiTiet getBaoCaoDonViChiTiet() {
		return baoCaoDonViChiTiet;
	}

	public void setBaoCaoDonViChiTiet(BaoCaoDonViChiTiet baoCaoDonViChiTiet) {
		this.baoCaoDonViChiTiet = baoCaoDonViChiTiet;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getBaoCaoDonViChiTietInfo() {
		if (getBaoCaoDonViChiTiet() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("baoCaoDonViChiTietId", getBaoCaoDonViChiTiet().getId());
			map.put("chaId", getBaoCaoDonViChiTiet().getCha().getId());
			map.put("trangThaiBaoCao", getBaoCaoDonViChiTiet().getTrangThaiBaoCao().name());
			map.put("loaiBaoCao", getBaoCaoDonViChiTiet().getLoaiBaoCao().name());
			map.put("tuThem", getBaoCaoDonViChiTiet().isTuThem()	);
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getBaoCaoDonViChiTietTamId() {
		return getId();
	}
	
	@Transient
	@ApiModelProperty(hidden = true) 
	public Object getSoLieuBaoCaoInfo() {
		if (getSoLieuBaoCao() != null && !getSoLieuBaoCao().isEmpty()) {
			return Utils.getSoLieuBaoCaoByJson(getBaoCaoDonViChiTiet().getLoaiBaoCao(), getSoLieuBaoCao());
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