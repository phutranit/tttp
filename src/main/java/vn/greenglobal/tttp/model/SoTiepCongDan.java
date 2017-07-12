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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.querydsl.core.annotations.QueryInit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.HuongGiaiQuyetTCDEnum;
import vn.greenglobal.tttp.enums.HuongXuLyTCDEnum;
import vn.greenglobal.tttp.enums.LoaiTiepDanEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;

@Entity
@Table(name = "sotiepcongdan")
@ApiModel
public class SoTiepCongDan extends Model<SoTiepCongDan> {

	private static final long serialVersionUID = -6772485280557984436L;

	@NotNull
	@ManyToOne
	private Don don;
	@NotNull
	@ManyToOne
	@QueryInit("*.*.*")
	private CongChuc canBoTiepDan;
	@NotNull
	@ManyToOne
	@QueryInit("*.*.*")
	private CoQuanQuanLy donViTiepDan;

	@NotNull
	private LocalDateTime ngayTiepDan;
	private LocalDateTime thoiHan;
	private LocalDateTime ngayHenGapLanhDao;
	private LocalDateTime ngayBaoCaoKetQua;
	private LocalDateTime ngayGuiKetQua;
	//@Lob
	private String noiDungTiepCongDan = "";
	@Size(max=255)
	private String ketQuaGiaiQuyet = "";
	private String noiDungBaoCaoKetQuaKiemTra = "";
	
	@ManyToOne
	private CoQuanQuanLy donViChuTri;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "sotiepcongdan_has_donviphoihop", joinColumns = {
			@JoinColumn(name = "soTiepCongDan_id") }, inverseJoinColumns = {
					@JoinColumn(name = "coQuanQuanLy_id") })
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<CoQuanQuanLy> donViPhoiHops = new ArrayList<CoQuanQuanLy>();
	@Size(max=255)
	private String trangThaiKetQua = "";
	//@Lob
	private String noiDungBoSung = "";
	@Size(max=255)
	private String diaDiemGapLanhDao = "";
	private boolean hoanThanhTCDLanhDao;
	private boolean hoanThanhTCDThuongXuyen;
	
	@Transient
	private String huongXuLyText = "";

	private int soThuTuLuotTiep = 0;

	@NotNull
	@Enumerated(EnumType.STRING)
	private HuongXuLyTCDEnum huongXuLy;
	@NotNull
	@Enumerated(EnumType.STRING)
	private LoaiTiepDanEnum loaiTiepDan;
	
	@Enumerated(EnumType.STRING)
	private HuongGiaiQuyetTCDEnum huongGiaiQuyetTCDLanhDao;
	
	@Enumerated(EnumType.STRING)
	private HuongGiaiQuyetTCDEnum trinhTrangXuLyTCDLanhDao;

	@ManyToOne
	private CoQuanQuanLy phongBanGiaiQuyet;
	@Size(max=255)
	private String yKienXuLy = "";
	@Size(max=255)
	private String ghiChuXuLy = "";
	@Transient
	private boolean chuyenDonViKiemTra;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "coquantochuctiepdan_has_sotiepcongdan", joinColumns = {
			@JoinColumn(name = "soTiepCongDan_id") }, inverseJoinColumns = {
					@JoinColumn(name = "coQuanToChucTiepDan_id") })
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<CoQuanToChucTiepDan> coQuanToChucTiepDans = new ArrayList<CoQuanToChucTiepDan>();

	@OneToMany(mappedBy = "soTiepCongDan", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<TaiLieuVanThu> taiLieuVanThus = new ArrayList<TaiLieuVanThu>();
	
	@OneToMany(mappedBy = "soTiepCongDan", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<TaiLieuBangChung> taiLieuBangChungs = new ArrayList<TaiLieuBangChung>();
	
	public String getNoiDungBaoCaoKetQuaKiemTra() {
		return noiDungBaoCaoKetQuaKiemTra;
	}

	public void setNoiDungBaoCaoKetQuaKiemTra(String noiDungBaoCaoKetQuaKiemTra) {
		this.noiDungBaoCaoKetQuaKiemTra = noiDungBaoCaoKetQuaKiemTra;
	}

	public LocalDateTime getNgayBaoCaoKetQua() {
		return ngayBaoCaoKetQua;
	}

	public void setNgayBaoCaoKetQua(LocalDateTime ngayBaoCaoKetQua) {
		this.ngayBaoCaoKetQua = ngayBaoCaoKetQua;
	}

	public LocalDateTime getNgayGuiKetQua() {
		return ngayGuiKetQua;
	}

	public void setNgayGuiKetQua(LocalDateTime ngayGuiKetQua) {
		this.ngayGuiKetQua = ngayGuiKetQua;
	}

	public List<CoQuanToChucTiepDan> getCoQuanToChucTiepDans() {
		return coQuanToChucTiepDans;
	}

	public void setCoQuanToChucTiepDans(List<CoQuanToChucTiepDan> coQuanToChucTiepDans) {
		this.coQuanToChucTiepDans = coQuanToChucTiepDans;
	}
	
	
	public boolean isChuyenDonViKiemTra() {
		return chuyenDonViKiemTra;
	}

	public void setChuyenDonViKiemTra(boolean chuyenDonViKiemTra) {
		this.chuyenDonViKiemTra = chuyenDonViKiemTra;
	}

	@ApiModelProperty(example = "{}")
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	@ApiModelProperty(example = "{}")
	public CongChuc getCanBoTiepDan() {
		return canBoTiepDan;
	}

	public void setCanBoTiepDan(CongChuc canBoTiepDan) {
		this.canBoTiepDan = canBoTiepDan;
	}

	@ApiModelProperty(example = "{}")
	public CoQuanQuanLy getDonViTiepDan() {
		return donViTiepDan;
	}

	public void setDonViTiepDan(CoQuanQuanLy donViTiepDan) {
		this.donViTiepDan = donViTiepDan;
	}

	public LocalDateTime getNgayTiepDan() {
		return ngayTiepDan;
	}

	public void setNgayTiepDan(LocalDateTime ngayTiepDan) {
		this.ngayTiepDan = ngayTiepDan;
	}

	public boolean isHoanThanhTCDLanhDao() {
		return hoanThanhTCDLanhDao;
	}

	public void setHoanThanhTCDLanhDao(boolean hoanThanhTCDLanhDao) {
		this.hoanThanhTCDLanhDao = hoanThanhTCDLanhDao;
	}

	public boolean isHoanThanhTCDThuongXuyen() {
		return hoanThanhTCDThuongXuyen;
	}

	public void setHoanThanhTCDThuongXuyen(boolean hoanThanhTCDThuongXuyen) {
		this.hoanThanhTCDThuongXuyen = hoanThanhTCDThuongXuyen;
	}

	public LocalDateTime getThoiHan() {
		return thoiHan;
	}

	public void setThoiHan(LocalDateTime thoiHan) {
		this.thoiHan = thoiHan;
	}

	public String getNoiDungTiepCongDan() {
		return noiDungTiepCongDan;
	}

	public void setNoiDungTiepCongDan(String noiDungTiepCongDan) {
		if (noiDungTiepCongDan != null && noiDungTiepCongDan.length() == 0) {
			this.noiDungTiepCongDan = " ";
		} else {
			this.noiDungTiepCongDan = noiDungTiepCongDan;
		}
	}

	public String getKetQuaGiaiQuyet() {
		return ketQuaGiaiQuyet;
	}

	public void setKetQuaGiaiQuyet(String ketQuaGiaiQuyet) {
		this.ketQuaGiaiQuyet = ketQuaGiaiQuyet;
	}

	@ApiModelProperty(example = "{}")
	public CoQuanQuanLy getDonViChuTri() {
		return donViChuTri;
	}

	public void setDonViChuTri(CoQuanQuanLy donViChuTri) {
		this.donViChuTri = donViChuTri;
	}

	public List<CoQuanQuanLy> getDonViPhoiHops() {
		return donViPhoiHops;
	}

	public void setDonViPhoiHops(List<CoQuanQuanLy> donViPhoiHops) {
		this.donViPhoiHops = donViPhoiHops;
	}

	public String getTrangThaiKetQua() {
		return trangThaiKetQua;
	}

	public void setTrangThaiKetQua(String trangThaiKetQua) {
		this.trangThaiKetQua = trangThaiKetQua;
	}

	public String getNoiDungBoSung() {
		return noiDungBoSung;
	}

	public void setNoiDungBoSung(String noiDungBoSung) {
		if (noiDungBoSung != null && noiDungBoSung.length() == 0) {
			this.noiDungBoSung = " ";
		} else {
			this.noiDungBoSung = noiDungBoSung;
		}
	}

	public String getDiaDiemGapLanhDao() {
		return diaDiemGapLanhDao;
	}

	public void setDiaDiemGapLanhDao(String diaDiemGapLanhDao) {
		this.diaDiemGapLanhDao = diaDiemGapLanhDao;
	}

	public LocalDateTime getNgayHenGapLanhDao() {
		return ngayHenGapLanhDao;
	}

	public void setNgayHenGapLanhDao(LocalDateTime ngayHenGapLanhDao) {
		this.ngayHenGapLanhDao = ngayHenGapLanhDao;
	}

	public HuongXuLyTCDEnum getHuongXuLy() {
		return huongXuLy;
	}

	public void setHuongXuLy(HuongXuLyTCDEnum huongXuLy) {
		this.huongXuLy = huongXuLy;
	}

	public LoaiTiepDanEnum getLoaiTiepDan() {
		return loaiTiepDan;
	}

	public void setLoaiTiepDan(LoaiTiepDanEnum loaiTiepDan) {
		this.loaiTiepDan = loaiTiepDan;
	}
	
	public HuongGiaiQuyetTCDEnum getHuongGiaiQuyetTCDLanhDao() {
		return huongGiaiQuyetTCDLanhDao;
	}

	public void setHuongGiaiQuyetTCDLanhDao(HuongGiaiQuyetTCDEnum huongGiaiQuyetTCDLanhDao) {
		this.huongGiaiQuyetTCDLanhDao = huongGiaiQuyetTCDLanhDao;
	}

	@ApiModelProperty(hidden = true)
	public HuongGiaiQuyetTCDEnum getTrinhTrangXuLyTCDLanhDao() {
		return trinhTrangXuLyTCDLanhDao;
	}

	public void setTrinhTrangXuLyTCDLanhDao(HuongGiaiQuyetTCDEnum trinhTrangXuLyTCDLanhDao) {
		this.trinhTrangXuLyTCDLanhDao = trinhTrangXuLyTCDLanhDao;
	}

	@ApiModelProperty(hidden = true)
	public String getHuongXuLyText() {
		if (getHuongXuLy() != null) {
			huongXuLyText = getHuongXuLy().getText();
		}
		return huongXuLyText;
	}

	public void setHuongXuLyText(String huongXuLyText) {
		this.huongXuLyText = huongXuLyText;
	}

	public int getSoThuTuLuotTiep() {
		return soThuTuLuotTiep;
	}

	public void setSoThuTuLuotTiep(int soThuTuLuotTiep) {
		this.soThuTuLuotTiep = soThuTuLuotTiep;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public List<CoQuanToChucTiepDan> getCoQuanToChucTiepDanSTCD() {
		return coQuanToChucTiepDans;
	}

	@ApiModelProperty(hidden = true)
	public Don getDonSTCD() {
		return getDon();
	}

	@ApiModelProperty(example = "{}")
	public CoQuanQuanLy getPhongBanGiaiQuyet() {
		return phongBanGiaiQuyet;
	}

	public void setPhongBanGiaiQuyet(CoQuanQuanLy phongBanGiaiQuyet) {
		this.phongBanGiaiQuyet = phongBanGiaiQuyet;
	}

	public String getyKienXuLy() {
		return yKienXuLy;
	}

	public void setyKienXuLy(String yKienXuLy) {
		this.yKienXuLy = yKienXuLy;
	}

	public String getGhiChuXuLy() {
		return ghiChuXuLy;
	}

	public void setGhiChuXuLy(String ghiChuXuLy) {
		this.ghiChuXuLy = ghiChuXuLy;
	}

	@ApiModelProperty(hidden = true)
	public List<TaiLieuVanThu> getTaiLieuVanThus() {
		return taiLieuVanThus;
	}

	public void setTaiLieuVanThus(List<TaiLieuVanThu> taiLieuVanThus) {
		this.taiLieuVanThus = taiLieuVanThus;
	}
	
	@ApiModelProperty(hidden = true)
	public List<TaiLieuBangChung> getTaiLieuBangChungs() {
		return taiLieuBangChungs;
	}

	public void setTaiLieuBangChungs(List<TaiLieuBangChung> taiLieuBangChungs) {
		this.taiLieuBangChungs = taiLieuBangChungs;
	}

	@ApiModelProperty(hidden = true)
	@Transient
	public List<TaiLieuVanThu> getListTaiLieuVanThu() {
		List<TaiLieuVanThu> list = new ArrayList<TaiLieuVanThu>();
		for (TaiLieuVanThu tlvt : getTaiLieuVanThus()) {
			if (!tlvt.isDaXoa() && (ProcessTypeEnum.TIEP_CONG_DAN.equals(tlvt.getLoaiQuyTrinh()) ||
					ProcessTypeEnum.KIEM_TRA_DE_XUAT.equals(tlvt.getLoaiQuyTrinh()))) {
				list.add(tlvt);
			}
		}
		return list;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public String getSoLuotTiepStr() {
		String out = "";
		out += getSoThuTuLuotTiep() + "/";
		if (getDon() != null) {
			out += getDon().getTongSoLuotTCD();
		} else {
			out += "1";
		}
		return out;
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
	public Map<String, Object> getCanBoTiepDanInfo() {
		if (getCanBoTiepDan() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("congChucId", getCanBoTiepDan() != null ? getCanBoTiepDan().getId() : 0);
			map.put("hoVaTen", getCanBoTiepDan() != null ? getCanBoTiepDan().getHoVaTen() : "");
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public String getTinhTrangXuLyLanhDaoStr() {
		String str = "";
		if (getHuongGiaiQuyetTCDLanhDao() != null && getTrinhTrangXuLyTCDLanhDao() != null) {
			HuongGiaiQuyetTCDEnum huongGiaiQuyetTCDLanhDao = getHuongGiaiQuyetTCDLanhDao();
			HuongGiaiQuyetTCDEnum tinhTrangXuLyTCDLanhDao = getTrinhTrangXuLyTCDLanhDao();
			str = huongGiaiQuyetTCDLanhDao.getText();
			if (huongGiaiQuyetTCDLanhDao.equals(HuongGiaiQuyetTCDEnum.CHO_GIAI_QUYET)) {		
				if (tinhTrangXuLyTCDLanhDao.equals(HuongGiaiQuyetTCDEnum.DA_CO_BAO_CAO_KIEM_TRA_DE_XUAT)) { 
					str += " - "+tinhTrangXuLyTCDLanhDao.getText();
				} else if (tinhTrangXuLyTCDLanhDao.equals(HuongGiaiQuyetTCDEnum.GIAO_DON_VI_KIEM_TRA_VA_DE_XUAT)) {
					str += " - Giao kiểm tra đề xuất";
					if (getDonViChuTri() != null) { 
						str += "/" +getDonViChuTri().getTen();
					}
				} else {
					str += " - " +tinhTrangXuLyTCDLanhDao.getText();
				}
			} else {
				str += " - " +tinhTrangXuLyTCDLanhDao.getText();
			}
		} else { 
			if (getHuongGiaiQuyetTCDLanhDao() != null) {
				HuongGiaiQuyetTCDEnum huongGiaiQuyetTCDLanhDao = getHuongGiaiQuyetTCDLanhDao();
				str = huongGiaiQuyetTCDLanhDao.getText();
				str += " - Đang xử lý";
			}
		}
		return str;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public String getHuongGiaiQuyetTCDLanhDaoStr() {
		if (getHuongGiaiQuyetTCDLanhDao() != null) { 
			if (getHuongGiaiQuyetTCDLanhDao().equals(HuongGiaiQuyetTCDEnum.KHOI_TAO)) {
				return "Chờ tiếp";
			}
		}
		return getHuongGiaiQuyetTCDLanhDao() != null ? getHuongGiaiQuyetTCDLanhDao().getText() : "";
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public List<TaiLieuBangChung> getTaiLieuBangChungBoSungs() {
		List<TaiLieuBangChung> list = new ArrayList<TaiLieuBangChung>();
		for (TaiLieuBangChung tlbc : getTaiLieuBangChungs()) {
			if (!tlbc.isDaXoa()) {
				list.add(tlbc);
			}
		}
		return list;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getThongTinYeuCauKiemTraDeXuatInfo() {		
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> mapDonViDuocGiaoTTXM = new HashMap<>();
		Map<String, Object> mapDonViPhoiHop = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();		
		mapDonViDuocGiaoTTXM.put("ten", getDonViChuTri() != null ? getDonViChuTri().getTen() : "");
		mapDonViDuocGiaoTTXM.put("coQuanQuanLyId", getDonViChuTri() != null ? getDonViChuTri().getId() : "");
		map.put("donViDuocGiaoTTXM", mapDonViDuocGiaoTTXM);
		
		if (getDonViPhoiHops() != null) { 
			for (CoQuanQuanLy donViPhoihop : getDonViPhoiHops()) {
				mapDonViPhoiHop.put("ten", donViPhoihop.getTen());
				mapDonViPhoiHop.put("coQuanQuanLyId", donViPhoihop.getId());
				list.add(mapDonViPhoiHop);
				mapDonViPhoiHop = new HashMap<>();
			}
		}
		map.put("donViPhoiHops", list);
		map.put("yKienXuLy", getNoiDungBaoCaoKetQuaKiemTra());
		map.put("ngayBaoCaoKQ", getNgayBaoCaoKetQua() != null ? getNgayBaoCaoKetQua() : "");
		map.put("ngayGuiKQ", getNgayGuiKetQua() != null ? getNgayGuiKetQua() : "");
		return map;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public List<CoQuanQuanLy> getDanhSachDonViPhoiHops() {
		List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
		for (CoQuanQuanLy donViPhoiHop : getDonViPhoiHops()) {
			if (!donViPhoiHop.isDaXoa()) {
				list.add(donViPhoiHop);
			}
		}
		return list;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getDonViChuTriInfo() {
		Map<String, Object> map = new HashMap<>();
		if (getDonViChuTri() != null) {
			map.put("ten", getDonViChuTri().getTen());
			map.put("coQuanQuanLyId", getDonViChuTri().getId());
		}
		return map;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Long getSoTiepCongDanId() {
		return getId();
	}
}