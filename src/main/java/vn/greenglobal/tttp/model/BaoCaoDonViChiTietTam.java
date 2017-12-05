package vn.greenglobal.tttp.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.LoaiBaoCaoTongHopEnum;

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
	@Enumerated(EnumType.STRING)
	private LoaiBaoCaoTongHopEnum loaiBaoCao;
	
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

	public LoaiBaoCaoTongHopEnum getLoaiBaoCao() {
		return loaiBaoCao;
	}

	public void setLoaiBaoCao(LoaiBaoCaoTongHopEnum loaiBaoCao) {
		this.loaiBaoCao = loaiBaoCao;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getBaoCaoDonViChiTietTamId() {
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