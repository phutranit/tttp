package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.KyBaoCaoTongHopEnum;

@Entity
@Table(name = "cauhinhbaocao")
@ApiModel
public class CauHinhBaoCao extends Model<CauHinhBaoCao> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 626055076154150518L;
	
	@Size(max=3000)
	private String danhSachBaoCao = "";
	private String tenBaoCao = "";
	private String tenBaoCaoSearch = "";
	private LocalDateTime ngayBatDauBC;
	private LocalDateTime ngayKetThucBC;
	private LocalDateTime ngayGuiBaoCao;
	private int soNgayTuDongGui;	
	private int namBaoCao;
	private int thangBaoCao;
	private int quyBaoCao;
	private boolean daTuDongGui;	
	@ManyToOne
	private CoQuanQuanLy donViGui;
	@Enumerated(EnumType.STRING)
	private KyBaoCaoTongHopEnum kyBaoCao;	
	@ManyToMany(fetch = FetchType.EAGER)// 
	@JoinTable(name = "cauhinhbaocao_donvinhan", joinColumns = {
			@JoinColumn(name = "cauHinhBaoCao_id") }, inverseJoinColumns = { @JoinColumn(name = "coQuanQuanLy_id") })
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<CoQuanQuanLy> donViNhans = new ArrayList<CoQuanQuanLy>();//
		
	public String getTenBaoCao() {
		return tenBaoCao;
	}

	public void setTenBaoCao(String tenBaoCao) {
		this.tenBaoCao = tenBaoCao;
	}

	public KyBaoCaoTongHopEnum getKyBaoCao() {
		return kyBaoCao;
	}

	public void setKyBaoCao(KyBaoCaoTongHopEnum kyBaoCao) {
		this.kyBaoCao = kyBaoCao;
	}

	public int getNamBaoCao() {
		return namBaoCao;
	}

	public void setNamBaoCao(int namBaoCao) {
		this.namBaoCao = namBaoCao;
	}

	public LocalDateTime getNgayBatDauBC() {
		return ngayBatDauBC;
	}
	
	public LocalDateTime getNgayGuiBaoCao() {
		return ngayGuiBaoCao;
	}

	@JsonIgnore
	public void setNgayGuiBaoCao(LocalDateTime ngayGuiBaoCao) {
		this.ngayGuiBaoCao = ngayGuiBaoCao;
	}

	public void setNgayBatDauBC(LocalDateTime ngayBatDauBC) {
		this.ngayBatDauBC = ngayBatDauBC;
	}

	public int getThangBaoCao() {
		return thangBaoCao;
	}

	public void setThangBaoCao(int thangBaoCao) {
		this.thangBaoCao = thangBaoCao;
	}

	public int getQuyBaoCao() {
		return quyBaoCao;
	}

	public void setQuyBaoCao(int quyBaoCao) {
		this.quyBaoCao = quyBaoCao;
	}

	@JsonIgnore
	public String getTenBaoCaoSearch() {
		return tenBaoCaoSearch;
	}

	public void setTenBaoCaoSearch(String tenBaoCaoSearch) {
		this.tenBaoCaoSearch = tenBaoCaoSearch;
	}

	public LocalDateTime getNgayKetThucBC() {
		return ngayKetThucBC;
	}

	public void setNgayKetThucBC(LocalDateTime ngayKetThucBC) {
		this.ngayKetThucBC = ngayKetThucBC;
	}

	public int getSoNgayTuDongGui() {
		return soNgayTuDongGui;
	}

	public void setSoNgayTuDongGui(int soNgayTuDongGui) {
		this.soNgayTuDongGui = soNgayTuDongGui;
	}

	@ApiModelProperty(position = 1, example = "{}")
	public CoQuanQuanLy getDonViGui() {
		return donViGui;
	}

	public void setDonViGui(CoQuanQuanLy donViGui) {
		this.donViGui = donViGui;
	}

	public String getDanhSachBaoCao() {
		return danhSachBaoCao;
	}

	public void setDanhSachBaoCao(String danhSachBaoCao) {
		this.danhSachBaoCao = danhSachBaoCao;
	}

	@ApiModelProperty(hidden = true)
	public boolean isDaTuDongGui() {
		return daTuDongGui;
	}

	public void setDaTuDongGui(boolean daTuDongGui) {
		this.daTuDongGui = daTuDongGui;
	}

	public List<CoQuanQuanLy> getDonViNhans() {
		return donViNhans;
	}

	public void setDonViNhans(List<CoQuanQuanLy> donViNhans) {
		this.donViNhans = donViNhans;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getCauHinhBaoCaoId() {
		return getId();
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getDonViGuiInfo() {
		if (getDonViGui() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", getDonViGui().getId());
			map.put("ten", getDonViGui().getTen());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public List<Map<String, Object>> getDonViNhanInfo() {
		if (getDonViNhans() != null && getDonViNhans().size() > 0) {
			List<Map<String, Object>> list = new ArrayList<>();
			
			for (CoQuanQuanLy cq : getDonViNhans()) {
				Map<String, Object> map = new HashMap<>();
				map.put("id", cq.getId());
				map.put("ten", cq.getTen());
				list.add(map);
			}
			return list;
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