package vn.greenglobal.tttp.model;

import java.time.LocalDate;
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
import vn.greenglobal.tttp.enums.TrangThaiBaoCaoDonViEnum;

@Entity
@Table(name = "baocaodonvichitiet")
@ApiModel
public class BaoCaoDonViChiTiet extends Model<BaoCaoDonViChiTiet> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2740249455927215380L;
	
	private LocalDate ngayNop;
	@Size(max=4000)
	private String soLieuBaoCao = "";
	@ManyToOne
	private BaoCaoDonVi baoCaoDonVi;
	@ManyToOne
	private BaoCaoDonViChiTiet cha;
	@Enumerated(EnumType.STRING)
	private TrangThaiBaoCaoDonViEnum trangThaiBaoCao;
	@Enumerated(EnumType.STRING)
	private LoaiBaoCaoTongHopEnum loaiBaoCao;
	private boolean heThongTao;
	private boolean tuThem;
	
	public LocalDate getNgayNop() {
		return ngayNop;
	}

	public void setNgayNop(LocalDate ngayNop) {
		this.ngayNop = ngayNop;
	}

	public String getSoLieuBaoCao() {
		return soLieuBaoCao;
	}

	public void setSoLieuBaoCao(String soLieuBaoCao) {
		this.soLieuBaoCao = soLieuBaoCao;
	}

	public BaoCaoDonVi getBaoCaoDonVi() {
		return baoCaoDonVi;
	}

	public void setBaoCaoDonVi(BaoCaoDonVi baoCaoDonVi) {
		this.baoCaoDonVi = baoCaoDonVi;
	}

	public BaoCaoDonViChiTiet getCha() {
		return cha;
	}

	public void setCha(BaoCaoDonViChiTiet cha) {
		this.cha = cha;
	}

	public TrangThaiBaoCaoDonViEnum getTrangThaiBaoCao() {
		return trangThaiBaoCao;
	}

	public void setTrangThaiBaoCao(TrangThaiBaoCaoDonViEnum trangThaiBaoCao) {
		this.trangThaiBaoCao = trangThaiBaoCao;
	}

	public LoaiBaoCaoTongHopEnum getLoaiBaoCao() {
		return loaiBaoCao;
	}

	public void setLoaiBaoCao(LoaiBaoCaoTongHopEnum loaiBaoCao) {
		this.loaiBaoCao = loaiBaoCao;
	}

	public boolean isHeThongTao() {
		return heThongTao;
	}

	public void setHeThongTao(boolean heThongTao) {
		this.heThongTao = heThongTao;
	}

	public boolean isTuThem() {
		return tuThem;
	}

	public void setTuThem(boolean tuThem) {
		this.tuThem = tuThem;
	}
	
	@ApiModelProperty(hidden = true)
	@Transient
	public Map<String, Object> getLoaiBaoCaoInfo() {
		if (getLoaiBaoCao() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getLoaiBaoCao().getText());
			map.put("giaTri", getLoaiBaoCao().name());
			return map;
		}
		return null;
	}
	
	@ApiModelProperty(hidden = true)
	@Transient
	public Map<String, Object> getTrangThaiBaoCaoInfo() {
		if (getTrangThaiBaoCao() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getTrangThaiBaoCao().getText());
			map.put("giaTri", getTrangThaiBaoCao().name());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getBaoCaoDonViChiTietId() {
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