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
import vn.greenglobal.tttp.enums.LoaiBaoCaoTongHopEnum;
import vn.greenglobal.tttp.enums.TrangThaiBaoCaoDonViEnum;

@Entity
@Table(name = "baocaotonghopchitiet")
@ApiModel
public class BaoCaoTongHopChiTiet extends Model<BaoCaoTongHopChiTiet> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1924196910774686508L;
	
	private LocalDate ngayTongHop;
	@ManyToOne
	private BaoCaoTongHop baoCaoTongHop;
	@Enumerated(EnumType.STRING)
	private TrangThaiBaoCaoDonViEnum trangThaiBaoCao;
	@Enumerated(EnumType.STRING)
	private LoaiBaoCaoTongHopEnum loaiBaoCao;
	
	public LocalDate getNgayTongHop() {
		return ngayTongHop;
	}

	public void setNgayTongHop(LocalDate ngayTongHop) {
		this.ngayTongHop = ngayTongHop;
	}

	public BaoCaoTongHop getBaoCaoTongHop() {
		return baoCaoTongHop;
	}

	public void setBaoCaoTongHop(BaoCaoTongHop baoCaoTongHop) {
		this.baoCaoTongHop = baoCaoTongHop;
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

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getBaoCaoTongHopChiTietId() {
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