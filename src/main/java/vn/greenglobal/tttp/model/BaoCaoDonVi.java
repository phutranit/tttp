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

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.TrangThaiBaoCaoDonViEnum;

@Entity
@Table(name = "baocaodonvi")
@ApiModel
public class BaoCaoDonVi extends Model<BaoCaoDonVi> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2740249455927215380L;
	@ManyToOne
	private CoQuanQuanLy donVi;
	@ManyToOne
	private BaoCaoTongHop baoCaoTongHop;
	private LocalDate ngayGui;
	@Enumerated(EnumType.STRING)
	private TrangThaiBaoCaoDonViEnum trangThaiBaoCao;

	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}

	public BaoCaoTongHop getBaoCaoTongHop() {
		return baoCaoTongHop;
	}

	public void setBaoCaoTongHop(BaoCaoTongHop baoCaoTongHop) {
		this.baoCaoTongHop = baoCaoTongHop;
	}

	public LocalDate getNgayGui() {
		return ngayGui;
	}

	public void setNgayGui(LocalDate ngayGui) {
		this.ngayGui = ngayGui;
	}
	
	public TrangThaiBaoCaoDonViEnum getTrangThaiBaoCao() {
		return trangThaiBaoCao;
	}

	public void setTrangThaiBaoCao(TrangThaiBaoCaoDonViEnum trangThaiBaoCao) {
		this.trangThaiBaoCao = trangThaiBaoCao;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getBaoCaoDonViId() {
		return getId();
	}
	@Transient
	@ApiModelProperty(hidden = true)
	public BaoCaoTongHop getBaoCaoTongHopInfo() {
		return getBaoCaoTongHop();
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