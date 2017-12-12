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
@Table(name = "tailieubaocao")
@ApiModel
public class TaiLieuBaoCao extends Model<TaiLieuBaoCao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7425254662967733039L;
	private String ten = "";
	private String soVanBan = "";
	private String tenFile = "";
	private String urlFile = "";
	@ManyToOne
	private BaoCaoDonVi baoCaoDonVi;
	@ManyToOne
	private BaoCaoDonViChiTiet baoCaoDonViChiTiet;
	@ManyToOne
	private BaoCaoTongHopChiTiet baoCaoTongHopChiTiet;
	
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getSoVanBan() {
		return soVanBan;
	}

	public void setSoVanBan(String soVanBan) {
		this.soVanBan = soVanBan;
	}

	public String getTenFile() {
		return tenFile;
	}

	public void setTenFile(String tenFile) {
		this.tenFile = tenFile;
	}

	public String getUrlFile() {
		return urlFile;
	}

	public void setUrlFile(String urlFile) {
		this.urlFile = urlFile;
	}

	public BaoCaoDonVi getBaoCaoDonVi() {
		return baoCaoDonVi;
	}

	public void setBaoCaoDonVi(BaoCaoDonVi baoCaoDonVi) {
		this.baoCaoDonVi = baoCaoDonVi;
	}

	public BaoCaoDonViChiTiet getBaoCaoDonViChiTiet() {
		return baoCaoDonViChiTiet;
	}

	public void setBaoCaoDonViChiTiet(BaoCaoDonViChiTiet baoCaoDonViChiTiet) {
		this.baoCaoDonViChiTiet = baoCaoDonViChiTiet;
	}

	public BaoCaoTongHopChiTiet getBaoCaoTongHopChiTiet() {
		return baoCaoTongHopChiTiet;
	}

	public void setBaoCaoTongHopChiTiet(BaoCaoTongHopChiTiet baoCaoTongHopChiTiet) {
		this.baoCaoTongHopChiTiet = baoCaoTongHopChiTiet;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getTaiLieuBaoCaoId() {
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