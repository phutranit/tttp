package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
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
import vn.greenglobal.tttp.enums.KyBaoCaoTongHopEnum;

@Entity
@Table(name = "baocaotonghop")
@ApiModel
public class BaoCaoTongHop extends Model<BaoCaoTongHop> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8452451590975658258L;
	@Size(max=3000)
	private String danhSachBaoCao = "";
	private String tenBaoCao = "";
	private String tenBaoCaoSearch = "";
	private LocalDateTime ngayBatDauBC;
	private LocalDateTime ngayKetThucBC;
	private int namBaoCao;
	private int quyBaoCao;
	private int thangBaoCao;
	@ManyToOne
	private CoQuanQuanLy donVi;
	@Enumerated(EnumType.STRING)
	private KyBaoCaoTongHopEnum kyBaoCao;	
	
	public KyBaoCaoTongHopEnum getKyBaoCao() {
		return kyBaoCao;
	}

	public void setKyBaoCao(KyBaoCaoTongHopEnum kyBaoCao) {
		this.kyBaoCao = kyBaoCao;
	}

	public int getNamBaoCao() {
		return namBaoCao;
	}
	
	public String getTenBaoCao() {
		return tenBaoCao;
	}

	public void setTenBaoCao(String tenBaoCao) {
		this.tenBaoCao = tenBaoCao;
	}

	public void setNamBaoCao(int namBaoCao) {
		this.namBaoCao = namBaoCao;
	}

	public int getQuyBaoCao() {
		return quyBaoCao;
	}

	public void setQuyBaoCao(int quyBaoCao) {
		this.quyBaoCao = quyBaoCao;
	}

	public int getThangBaoCao() {
		return thangBaoCao;
	}

	public void setThangBaoCao(int thangBaoCao) {
		this.thangBaoCao = thangBaoCao;
	}

	public LocalDateTime getNgayBatDauBC() {
		return ngayBatDauBC;
	}

	public void setNgayBatDauBC(LocalDateTime ngayBatDauBC) {
		this.ngayBatDauBC = ngayBatDauBC;
	}

	public LocalDateTime getNgayKetThucBC() {
		return ngayKetThucBC;
	}

	public void setNgayKetThucBC(LocalDateTime ngayKetThucBC) {
		this.ngayKetThucBC = ngayKetThucBC;
	}

	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}

	public String getDanhSachBaoCao() {
		return danhSachBaoCao;
	}

	public void setDanhSachBaoCao(String danhSachBaoCao) {
		this.danhSachBaoCao = danhSachBaoCao;
	}

	@ApiModelProperty(hidden = true)
	public String getTenBaoCaoSearch() {
		return tenBaoCaoSearch;
	}

	public void setTenBaoCaoSearch(String tenBaoCaoSearch) {
		this.tenBaoCaoSearch = tenBaoCaoSearch;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getBaoCaoTongHopId() {
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