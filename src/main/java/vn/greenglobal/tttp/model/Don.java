package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import vn.greenglobal.tttp.enums.HinhThucGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.HuongXuLyTCDEnum;
import vn.greenglobal.tttp.enums.LoaiDoiTuongEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiNguoiDungDonEnum;
import vn.greenglobal.tttp.enums.NguonTiepNhanDonEnum;

@Entity
@Table(name = "don")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Don extends Model<Don> {

	private static final long serialVersionUID = 8736658787648062250L;

	private String ma = "";
	private String noiDung = "";
	private String yeuCauCuaCongDan = "";
	private String lyDoTuChoi = "";
	private String ghiChuTiepCongDan = "";
	private String huongGiaiQuyetDaThucHien = "";
	private String lanGiaiQuyet = "";
	private String yKienXuLyDon = ""; // Xu ly don TCD
	private String ghiChuXuLyDon = ""; // Xu ly don TCD
	private String trangThaiDon = "";
	private String lyDoDinhChi = "";
	private String soQuyetDinhDinhChi = "";

	private int soLanKhieuNaiToCao = 0;
	private int tongSoLuotTCD;
	private int soNguoi;

	private boolean coUyQuyen = false;
	@NotNull
	private boolean thanhLapDon = false;
	private boolean tuChoiTiepCongDan = false;
	private boolean yeuCauGapTrucTiepLanhDao = false;

	private LocalDateTime ngayTiepNhan;
	private LocalDateTime ngayQuyetDinhDinhChi;

	@OneToOne
	private Don donLanTruoc;
	@ManyToOne
	private CongChuc canBoXuLy;
	@ManyToOne
	private CoQuanQuanLy donVi;
	@ManyToOne
	private LinhVucDonThu linhVucDonThu;
	@ManyToOne
	private LinhVucDonThu linhVucDonThuChiTiet;
	@ManyToOne
	private LinhVucDonThu chiTietLinhVucDonThuChiTiet;
	@NotNull
	@ManyToOne
	private VuViec vuViec;
	@ManyToOne
	private ThamQuyenGiaiQuyet thamQuyenGiaiQuyet;
	@ManyToOne
	private CapCoQuanQuanLy capCoQuanDaGiaiQuyet;
	@ManyToOne
	private CoQuanQuanLy coQuanDaGiaiQuyet;
	@ManyToOne
	private CoQuanQuanLy phongBanGiaiQuyet; // Xu ly don TCD
	@OneToMany(fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	private List<SoTiepCongDan> tiepCongDans = new ArrayList<SoTiepCongDan>(); // TCD

	/*
	 * @ManyToMany(fetch = FetchType.EAGER)
	 * 
	 * @JoinTable(name = "don_congdan", joinColumns = {
	 * 
	 * @JoinColumn(name = "don_id") }, inverseJoinColumns = {
	 * 
	 * @JoinColumn(name = "congDan_id") })
	 * 
	 * @Fetch(value = FetchMode.SUBSELECT)
	 */
	@OneToMany(mappedBy = "don", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	private List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>(); // TCD
	@Transient
	private Don_CongDan donCongDan; // TCD

	@NotNull
	@Enumerated(EnumType.STRING)
	private LoaiDonEnum loaiDon;
	@NotNull
	@Enumerated(EnumType.STRING)
	private LoaiDoiTuongEnum loaiDoiTuong;
	@NotNull
	@Enumerated(EnumType.STRING)
	private NguonTiepNhanDonEnum nguonTiepNhanDon;
	@NotNull
	@Enumerated(EnumType.STRING)
	private LoaiNguoiDungDonEnum loaiNguoiDungDon;
	@Enumerated(EnumType.STRING)
	private HinhThucGiaiQuyetEnum hinhThucDaGiaiQuyet;
	@Enumerated(EnumType.STRING)
	private HuongXuLyTCDEnum huongXuLy;

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	public String getYeuCauCuaCongDan() {
		return yeuCauCuaCongDan;
	}

	public void setYeuCauCuaCongDan(String yeuCauCuaCongDan) {
		this.yeuCauCuaCongDan = yeuCauCuaCongDan;
	}

	public String getLyDoTuChoi() {
		return lyDoTuChoi;
	}

	public void setLyDoTuChoi(String lyDoTuChoi) {
		this.lyDoTuChoi = lyDoTuChoi;
	}

	public int getSoLanKhieuNaiToCao() {
		return soLanKhieuNaiToCao;
	}

	public void setSoLanKhieuNaiToCao(int soLanKhieuNaiToCao) {
		this.soLanKhieuNaiToCao = soLanKhieuNaiToCao;
	}

	public int getSoNguoi() {
		return soNguoi;
	}

	public void setSoNguoi(int soNguoi) {
		this.soNguoi = soNguoi;
	}

	public boolean isCoUyQuyen() {
		return coUyQuyen;
	}

	public void setCoUyQuyen(boolean coUyQuyen) {
		this.coUyQuyen = coUyQuyen;
	}

	public boolean isThanhLapDon() {
		return thanhLapDon;
	}

	public void setThanhLapDon(boolean thanhLapDon) {
		this.thanhLapDon = thanhLapDon;
	}

	public boolean isTuChoiTiepCongDan() {
		return tuChoiTiepCongDan;
	}

	public void setTuChoiTiepCongDan(boolean tuChoiTiepCongDan) {
		this.tuChoiTiepCongDan = tuChoiTiepCongDan;
	}

	public String getGhiChuTiepCongDan() {
		return ghiChuTiepCongDan;
	}

	public void setGhiChuTiepCongDan(String ghiChuTiepCongDan) {
		this.ghiChuTiepCongDan = ghiChuTiepCongDan;
	}

	public String getTrangThaiDon() {
		return trangThaiDon;
	}

	public void setTrangThaiDon(String trangThaiDon) {
		this.trangThaiDon = trangThaiDon;
	}

	public String getLyDoDinhChi() {
		return lyDoDinhChi;
	}

	public void setLyDoDinhChi(String lyDoDinhChi) {
		this.lyDoDinhChi = lyDoDinhChi;
	}

	public String getSoQuyetDinhDinhChi() {
		return soQuyetDinhDinhChi;
	}

	public void setSoQuyetDinhDinhChi(String soQuyetDinhDinhChi) {
		this.soQuyetDinhDinhChi = soQuyetDinhDinhChi;
	}

	public LocalDateTime getNgayQuyetDinhDinhChi() {
		return ngayQuyetDinhDinhChi;
	}

	public void setNgayQuyetDinhDinhChi(LocalDateTime ngayQuyetDinhDinhChi) {
		this.ngayQuyetDinhDinhChi = ngayQuyetDinhDinhChi;
	}

	public LocalDateTime getNgayTiepNhan() {
		return ngayTiepNhan;
	}

	public void setNgayTiepNhan(LocalDateTime ngayTiepNhan) {
		this.ngayTiepNhan = ngayTiepNhan;
	}

	public String getHuongGiaiQuyetDaThucHien() {
		return huongGiaiQuyetDaThucHien;
	}

	public void setHuongGiaiQuyetDaThucHien(String huongGiaiQuyetDaThucHien) {
		this.huongGiaiQuyetDaThucHien = huongGiaiQuyetDaThucHien;
	}

	public String getLanGiaiQuyet() {
		return lanGiaiQuyet;
	}

	public void setLanGiaiQuyet(String lanGiaiQuyet) {
		this.lanGiaiQuyet = lanGiaiQuyet;
	}

	public String getyKienXuLyDon() {
		return yKienXuLyDon;
	}

	public void setyKienXuLyDon(String yKienXuLyDon) {
		this.yKienXuLyDon = yKienXuLyDon;
	}

	public String getGhiChuXuLyDon() {
		return ghiChuXuLyDon;
	}

	public void setGhiChuXuLyDon(String ghiChuXuLyDon) {
		this.ghiChuXuLyDon = ghiChuXuLyDon;
	}

	public CapCoQuanQuanLy getCapCoQuanDaGiaiQuyet() {
		return capCoQuanDaGiaiQuyet;
	}

	public void setCapCoQuanDaGiaiQuyet(CapCoQuanQuanLy capCoQuanDaGiaiQuyet) {
		this.capCoQuanDaGiaiQuyet = capCoQuanDaGiaiQuyet;
	}

	public CoQuanQuanLy getCoQuanDaGiaiQuyet() {
		return coQuanDaGiaiQuyet;
	}

	public void setCoQuanDaGiaiQuyet(CoQuanQuanLy coQuanDaGiaiQuyet) {
		this.coQuanDaGiaiQuyet = coQuanDaGiaiQuyet;
	}

	public Don getDonLanTruoc() {
		return donLanTruoc;
	}

	public void setDonLanTruoc(Don donLanTruoc) {
		this.donLanTruoc = donLanTruoc;
	}

	public CongChuc getCanBoXuLy() {
		return canBoXuLy;
	}

	public void setCanBoXuLy(CongChuc canBoXuLy) {
		this.canBoXuLy = canBoXuLy;
	}

	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}

	public ThamQuyenGiaiQuyet getThamQuyenGiaiQuyet() {
		return thamQuyenGiaiQuyet;
	}

	public void setThamQuyenGiaiQuyet(ThamQuyenGiaiQuyet thamQuyenGiaiQuyet) {
		this.thamQuyenGiaiQuyet = thamQuyenGiaiQuyet;
	}

	public CoQuanQuanLy getPhongBanGiaiQuyet() {
		return phongBanGiaiQuyet;
	}

	public void setPhongBanGiaiQuyet(CoQuanQuanLy phongBanGiaiQuyet) {
		this.phongBanGiaiQuyet = phongBanGiaiQuyet;
	}

	public VuViec getVuViec() {
		return vuViec;
	}

	public void setVuViec(VuViec vuViec) {
		this.vuViec = vuViec;
	}

	public LoaiDonEnum getLoaiDon() {
		return loaiDon;
	}

	public void setLoaiDon(LoaiDonEnum loaiDon) {
		this.loaiDon = loaiDon;
	}

	public LoaiDoiTuongEnum getLoaiDoiTuong() {
		return loaiDoiTuong;
	}

	public void setLoaiDoiTuong(LoaiDoiTuongEnum loaiDoiTuong) {
		this.loaiDoiTuong = loaiDoiTuong;
	}

	public NguonTiepNhanDonEnum getNguonTiepNhanDon() {
		return nguonTiepNhanDon;
	}

	public void setNguonTiepNhanDon(NguonTiepNhanDonEnum nguonTiepNhanDon) {
		this.nguonTiepNhanDon = nguonTiepNhanDon;
	}

	public LoaiNguoiDungDonEnum getLoaiNguoiDungDon() {
		return loaiNguoiDungDon;
	}

	public void setLoaiNguoiDungDon(LoaiNguoiDungDonEnum loaiNguoiDungDon) {
		this.loaiNguoiDungDon = loaiNguoiDungDon;
	}

	public HinhThucGiaiQuyetEnum getHinhThucDaGiaiQuyet() {
		return hinhThucDaGiaiQuyet;
	}

	public void setHinhThucDaGiaiQuyet(HinhThucGiaiQuyetEnum hinhThucDaGiaiQuyet) {
		this.hinhThucDaGiaiQuyet = hinhThucDaGiaiQuyet;
	}

	public HuongXuLyTCDEnum getHuongXuLy() {
		return huongXuLy;
	}

	public void setHuongXuLy(HuongXuLyTCDEnum huongXuLy) {
		this.huongXuLy = huongXuLy;
	}

	public LinhVucDonThu getLinhVucDonThu() {
		return linhVucDonThu;
	}

	public void setLinhVucDonThu(LinhVucDonThu linhVucDonThu) {
		this.linhVucDonThu = linhVucDonThu;
	}

	public LinhVucDonThu getLinhVucDonThuChiTiet() {
		return linhVucDonThuChiTiet;
	}

	public void setLinhVucDonThuChiTiet(LinhVucDonThu linhVucDonThuChiTiet) {
		this.linhVucDonThuChiTiet = linhVucDonThuChiTiet;
	}

	public LinhVucDonThu getChiTietLinhVucDonThuChiTiet() {
		return chiTietLinhVucDonThuChiTiet;
	}

	public void setChiTietLinhVucDonThuChiTiet(LinhVucDonThu chiTietLinhVucDonThuChiTiet) {
		this.chiTietLinhVucDonThuChiTiet = chiTietLinhVucDonThuChiTiet;
	}

	public List<SoTiepCongDan> getTiepCongDans() {
		return tiepCongDans;
	}

	public void setTiepCongDans(List<SoTiepCongDan> tiepCongDans) {
		this.tiepCongDans = tiepCongDans;
	}

	@Transient
	public CoQuanQuanLy getDonViDon() {
		return getDonVi();
	}

	@Transient
	public LinhVucDonThu getLinhVucDonThuDon() {
		return getLinhVucDonThu();
	}

	@Transient
	public LinhVucDonThu getLinhVucDonThuChiTietDon() {
		return getLinhVucDonThuChiTiet();
	}

	public List<Don_CongDan> getDonCongDans() {
		return donCongDans;
	}

	public void setDonCongDans(List<Don_CongDan> donCongDans) {
		this.donCongDans = donCongDans;
	}

	public Don_CongDan getDonCongDan(String phanLoaiCongDan) {
		for (Don_CongDan obj : donCongDans) {
			if (obj.getPhanLoaiCongDan().equals(phanLoaiCongDan)) {
				donCongDan = obj;
				break;
			}
		}
		return donCongDan;
	}

	public int getTongSoLuotTCD() {
		return tongSoLuotTCD;
	}

	public void setTongSoLuotTCD(int tongSoLuotTCD) {
		this.tongSoLuotTCD = tongSoLuotTCD;
	}

	@Transient
	public List<Don_CongDan> getListDonCongDan() {
		return getDonCongDans();
	}

	@Transient
	public Long getDonId() {
		return getId();
	}

	public boolean isYeuCauGapTrucTiepLanhDao() {
		return yeuCauGapTrucTiepLanhDao;
	}

	public void setYeuCauGapTrucTiepLanhDao(boolean yeuCauGapTrucTiepLanhDao) {
		this.yeuCauGapTrucTiepLanhDao = yeuCauGapTrucTiepLanhDao;
	}

}