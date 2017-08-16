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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.HinhThucTheoDoiEnum;
import vn.greenglobal.tttp.enums.KetQuaGiaiQuyetLan2Enum;
import vn.greenglobal.tttp.enums.KetLuanNoiDungKhieuNaiEnum;
import vn.greenglobal.tttp.enums.KetQuaThucHienTheoDoiEnum;
import vn.greenglobal.tttp.enums.KetQuaTrangThaiDonEnum;

@Entity
@Table(name = "thongtingiaiquyetdon")
public class ThongTinGiaiQuyetDon extends Model<ThongTinGiaiQuyetDon> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 445465484063016490L;

	@Size(max=255)
	private String soQuyetDinhThanhLapDTXM = "";
	@Size(max=255)
	private String soQuyetDinhGiaHanGiaiQuyet = "";
	@Size(max=255)
	private String soQuyetDinhGiaHanTTXM = "";
	@Size(max=255)
	private String diaDiemDoiThoai = "";
	@Size(max=255)
	private String lyDoGiaHanGiaiQuyet = "";
	@Size(max=255)
	private String lyDoGiaHanTTXM = "";
	@Size(max=255)
	private String ketQuaGiaiQuyetKhac = "";
	@Size(max=255)
	private String ketQuaThucHienKhac = "";
	//@Lob
	private String noiDung = "";
	//@Lob
	private String noiDungKetLuanGiaiQuyetLai = "";

	private LocalDateTime thoiGianDoiThoai;
	private LocalDateTime ngayBaoCaoKetQuaTTXM;
	
	private LocalDateTime ngayBatDauGiaiQuyet;
	private LocalDateTime ngayKetThucGiaiQuyet;
	private LocalDateTime ngayHetHanGiaiQuyet;
	private LocalDateTime ngayHetHanSauKhiGiaHanGiaiQuyet;
	
	private LocalDateTime ngayBatDauTTXM;
	private LocalDateTime ngayKetThucTTXM;
	private LocalDateTime ngayHetHanTTXM;
	private LocalDateTime ngayHetHanSauKhiGiaHanTTXM;
	private LocalDateTime ngayTraBaoCaoTTXM;
	
	private LocalDateTime ngayBatDauKTDX;
	private LocalDateTime ngayKetThucKTDX;
	private LocalDateTime ngayHetHanKTDX;
	private LocalDateTime ngayHetHanSauKhiGiaHanKTDX;
	
	private LocalDateTime ngayRaQuyetDinhGiaHanTTXM;
	private LocalDateTime ngayRaQuyetDinhGiaHanGiaiQuyet;

	private boolean lapToDoanXacMinh;
	private boolean giaHanGiaiQuyet;
	private boolean giaHanTTXM;
	private boolean doiThoai;
	private boolean giaoCoQuanDieuTra;
	private boolean khoiTo;
	private boolean quyetDinhGiaiQuyetKhieuNai;
	private boolean theoDoiThucHien;
	private boolean daThuLy;
	private boolean daThamTraXacMinhVuViec;
	private boolean daRaQuyetDinhGiaiQuyet;

	private int soVuGiaoCoQuanDieuTra;
	private int soDoiTuongGiaoCoQuanDieuTra;
	private int soVuBiKhoiTo;
	private int soDoiTuongBiKhoiTo;
	private int tongSoNguoiXuLyHanhChinh;
	private int soNguoiDaBiXuLyHanhChinh;
	private int soLanGiaiQuyetLai = 1;
	private int soVuGiaiQuyetKhieuNai = 0;
	private int soNguoiDuocTraLaiQuyenLoi;
	
	private long tienPhaiThuNhaNuocQDGQ;
	private long datPhaiThuNhaNuocQDGQ;
	private long tienPhaiTraCongDanQDGQ;
	private long datPhaiTraCongDanQDGQ;

	private long tienPhaiThuNhaNuoc;
	private long datPhaiThuNhaNuoc;
	private long tienPhaiTraCongDan;
	private long datPhaiTraCongDan;
	private long tienDaThuNhaNuoc;
	private long datDaThuNhaNuoc;
	private long tienDaTraCongDan;
	private long datDaTraCongDan;

	@ManyToOne
	private CongChuc canBoGiaiQuyet;
	@ManyToOne
	private CongChuc truongDoanTTXM;
	@ManyToOne
	private CongChuc canBoThamTraXacMinh;
	@ManyToOne
	private CoQuanQuanLy coQuanDieuTra;
	@ManyToOne
	private CoQuanQuanLy coQuanTheoDoi;
	@ManyToOne
	private CoQuanQuanLy donViThamTraXacMinh;
	@ManyToOne
	private CoQuanQuanLy donViGiaoThamTraXacMinh;
	
	@OneToOne
	@JoinColumn(name = "don_id")
	private Don don;
	
	@OneToMany(mappedBy = "thongTinGiaiQuyetDon", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<GiaiQuyetDon> giaiQuyetDons = new ArrayList<GiaiQuyetDon>(); // XLD

	@Enumerated(EnumType.STRING)
	private KetLuanNoiDungKhieuNaiEnum ketLuanNoiDungKhieuNai;
	@Enumerated(EnumType.STRING)
	private KetLuanNoiDungKhieuNaiEnum ketLuanNoiDungKhieuNaiGiaoTTXM;
	@Enumerated(EnumType.STRING)
	private HinhThucTheoDoiEnum hinhThucTheoDoi;
	@Enumerated(EnumType.STRING)
	private KetQuaThucHienTheoDoiEnum ketQuaThucHienTheoDoi;
	@Enumerated(EnumType.STRING)
	private KetQuaTrangThaiDonEnum ketQuaXLDGiaiQuyet;
	@Enumerated(EnumType.STRING)
	private KetQuaGiaiQuyetLan2Enum ketQuaGiaiQuyetLan2;
	
	/**
	 * Bat dau tao fields Luu thong tin tam thoi.
	 */
	//@Lob
	private String yKienGiaiQuyet = "";
	//@Lob
	private String yKienCuaDonViGiaoTTXM = "";
	//@Lob
	private String yKienXuLyDon = "";
	@ManyToOne
	private CongChuc canBoXuLyChiDinh;
	@ManyToOne
	private CoQuanQuanLy phongBanGiaiQuyet;
	@ManyToOne
	private State nextState;

	public String getyKienGiaiQuyet() {
		return yKienGiaiQuyet;
	}

	public void setyKienGiaiQuyet(String yKienGiaiQuyet) {
		this.yKienGiaiQuyet = yKienGiaiQuyet;
	}

	public CongChuc getCanBoXuLyChiDinh() {
		return canBoXuLyChiDinh;
	}

	public String getyKienCuaDonViGiaoTTXM() {
		return yKienCuaDonViGiaoTTXM;
	}

	public String getyKienXuLyDon() {
		return yKienXuLyDon;
	}

	public void setyKienXuLyDon(String yKienXuLyDon) {
		this.yKienXuLyDon = yKienXuLyDon;
	}

	public void setyKienCuaDonViGiaoTTXM(String yKienCuaDonViGiaoTTXM) {
		this.yKienCuaDonViGiaoTTXM = yKienCuaDonViGiaoTTXM;
	}

	public void setCanBoXuLyChiDinh(CongChuc canBoXuLyChiDinh) {
		this.canBoXuLyChiDinh = canBoXuLyChiDinh;
	}

	public CoQuanQuanLy getPhongBanGiaiQuyet() {
		return phongBanGiaiQuyet;
	}

	public void setPhongBanGiaiQuyet(CoQuanQuanLy phongBanGiaiQuyet) {
		this.phongBanGiaiQuyet = phongBanGiaiQuyet;
	}

	public State getNextState() {
		return nextState;
	}

	public void setNextState(State nextState) {
		this.nextState = nextState;
	}
	/**
	 * Ket thuc tao fields Luu thong tin tam thoi.
	 */

	public String getSoQuyetDinhThanhLapDTXM() {
		return soQuyetDinhThanhLapDTXM;
	}

	public void setSoQuyetDinhThanhLapDTXM(String soQuyetDinhThanhLapDTXM) {
		this.soQuyetDinhThanhLapDTXM = soQuyetDinhThanhLapDTXM;
	}

	public String getSoQuyetDinhGiaHanGiaiQuyet() {
		return soQuyetDinhGiaHanGiaiQuyet;
	}

	public void setSoQuyetDinhGiaHanGiaiQuyet(String soQuyetDinhGiaHanGiaiQuyet) {
		this.soQuyetDinhGiaHanGiaiQuyet = soQuyetDinhGiaHanGiaiQuyet;
	}

	public String getDiaDiemDoiThoai() {
		return diaDiemDoiThoai;
	}

	public void setDiaDiemDoiThoai(String diaDiemDoiThoai) {
		this.diaDiemDoiThoai = diaDiemDoiThoai;
	}

	public String getLyDoGiaHanGiaiQuyet() {
		return lyDoGiaHanGiaiQuyet;
	}

	public void setLyDoGiaHanGiaiQuyet(String lyDoGiaHanGiaiQuyet) {
		this.lyDoGiaHanGiaiQuyet = lyDoGiaHanGiaiQuyet;
	}

	public String getLyDoGiaHanTTXM() {
		return lyDoGiaHanTTXM;
	}

	public void setLyDoGiaHanTTXM(String lyDoGiaHanTTXM) {
		this.lyDoGiaHanTTXM = lyDoGiaHanTTXM;
	}

	public String getKetQuaGiaiQuyetKhac() {
		return ketQuaGiaiQuyetKhac;
	}

	public void setKetQuaGiaiQuyetKhac(String ketQuaGiaiQuyetKhac) {
		this.ketQuaGiaiQuyetKhac = ketQuaGiaiQuyetKhac;
	}

	public String getKetQuaThucHienKhac() {
		return ketQuaThucHienKhac;
	}

	public void setKetQuaThucHienKhac(String ketQuaThucHienKhac) {
		this.ketQuaThucHienKhac = ketQuaThucHienKhac;
	}

	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	public String getNoiDungKetLuanGiaiQuyetLai() {
		return noiDungKetLuanGiaiQuyetLai;
	}

	public void setNoiDungKetLuanGiaiQuyetLai(String noiDungKetLuanGiaiQuyetLai) {
		this.noiDungKetLuanGiaiQuyetLai = noiDungKetLuanGiaiQuyetLai;
	}

	public String getSoQuyetDinhGiaHanTTXM() {
		return soQuyetDinhGiaHanTTXM;
	}

	public void setSoQuyetDinhGiaHanTTXM(String soQuyetDinhGiaHanTTXM) {
		this.soQuyetDinhGiaHanTTXM = soQuyetDinhGiaHanTTXM;
	}

	public LocalDateTime getThoiGianDoiThoai() {
		return thoiGianDoiThoai;
	}

	public void setThoiGianDoiThoai(LocalDateTime thoiGianDoiThoai) {
		this.thoiGianDoiThoai = thoiGianDoiThoai;
	}

	public LocalDateTime getNgayBaoCaoKetQuaTTXM() {
		return ngayBaoCaoKetQuaTTXM;
	}

	public void setNgayBaoCaoKetQuaTTXM(LocalDateTime ngayBaoCaoKetQuaTTXM) {
		this.ngayBaoCaoKetQuaTTXM = ngayBaoCaoKetQuaTTXM;
	}
	public LocalDateTime getNgayBatDauGiaiQuyet() {
		return ngayBatDauGiaiQuyet;
	}

	public void setNgayBatDauGiaiQuyet(LocalDateTime ngayBatDauGiaiQuyet) {
		this.ngayBatDauGiaiQuyet = ngayBatDauGiaiQuyet;
	}

	public LocalDateTime getNgayKetThucGiaiQuyet() {
		return ngayKetThucGiaiQuyet;
	}

	public void setNgayKetThucGiaiQuyet(LocalDateTime ngayKetThucGiaiQuyet) {
		this.ngayKetThucGiaiQuyet = ngayKetThucGiaiQuyet;
	}
	
	public List<GiaiQuyetDon> getGiaiQuyetDons() {
		return giaiQuyetDons;
	}

	public void setGiaiQuyetDons(List<GiaiQuyetDon> giaiQuyetDons) {
		this.giaiQuyetDons = giaiQuyetDons;
	}

	public LocalDateTime getNgayHetHanGiaiQuyet() {
		return ngayHetHanGiaiQuyet;
	}

	public void setNgayHetHanGiaiQuyet(LocalDateTime ngayHetHanGiaiQuyet) {
		this.ngayHetHanGiaiQuyet = ngayHetHanGiaiQuyet;
	}

	public LocalDateTime getNgayHetHanSauKhiGiaHanGiaiQuyet() {
		return ngayHetHanSauKhiGiaHanGiaiQuyet;
	}

	public void setNgayHetHanSauKhiGiaHanGiaiQuyet(LocalDateTime ngayHetHanSauKhiGiaHanGiaiQuyet) {
		this.ngayHetHanSauKhiGiaHanGiaiQuyet = ngayHetHanSauKhiGiaHanGiaiQuyet;
	}

	public LocalDateTime getNgayBatDauTTXM() {
		return ngayBatDauTTXM;
	}

	public void setNgayBatDauTTXM(LocalDateTime ngayBatDauTTXM) {
		this.ngayBatDauTTXM = ngayBatDauTTXM;
	}

	public LocalDateTime getNgayKetThucTTXM() {
		return ngayKetThucTTXM;
	}

	public void setNgayKetThucTTXM(LocalDateTime ngayKetThucTTXM) {
		this.ngayKetThucTTXM = ngayKetThucTTXM;
	}

	public LocalDateTime getNgayHetHanTTXM() {
		return ngayHetHanTTXM;
	}

	public void setNgayHetHanTTXM(LocalDateTime ngayHetHanTTXM) {
		this.ngayHetHanTTXM = ngayHetHanTTXM;
	}

	public LocalDateTime getNgayHetHanSauKhiGiaHanTTXM() {
		return ngayHetHanSauKhiGiaHanTTXM;
	}

	public void setNgayHetHanSauKhiGiaHanTTXM(LocalDateTime ngayHetHanSauKhiGiaHanTTXM) {
		this.ngayHetHanSauKhiGiaHanTTXM = ngayHetHanSauKhiGiaHanTTXM;
	}

	public LocalDateTime getNgayTraBaoCaoTTXM() {
		return ngayTraBaoCaoTTXM;
	}

	public void setNgayTraBaoCaoTTXM(LocalDateTime ngayTraBaoCaoTTXM) {
		this.ngayTraBaoCaoTTXM = ngayTraBaoCaoTTXM;
	}

	public LocalDateTime getNgayBatDauKTDX() {
		return ngayBatDauKTDX;
	}

	public void setNgayBatDauKTDX(LocalDateTime ngayBatDauKTDX) {
		this.ngayBatDauKTDX = ngayBatDauKTDX;
	}

	public LocalDateTime getNgayKetThucKTDX() {
		return ngayKetThucKTDX;
	}

	public void setNgayKetThucKTDX(LocalDateTime ngayKetThucKTDX) {
		this.ngayKetThucKTDX = ngayKetThucKTDX;
	}

	public LocalDateTime getNgayHetHanKTDX() {
		return ngayHetHanKTDX;
	}

	public void setNgayHetHanKTDX(LocalDateTime ngayHetHanKTDX) {
		this.ngayHetHanKTDX = ngayHetHanKTDX;
	}

	public LocalDateTime getNgayHetHanSauKhiGiaHanKTDX() {
		return ngayHetHanSauKhiGiaHanKTDX;
	}

	public void setNgayHetHanSauKhiGiaHanKTDX(LocalDateTime ngayHetHanSauKhiGiaHanKTDX) {
		this.ngayHetHanSauKhiGiaHanKTDX = ngayHetHanSauKhiGiaHanKTDX;
	}

	public LocalDateTime getNgayRaQuyetDinhGiaHanTTXM() {
		return ngayRaQuyetDinhGiaHanTTXM;
	}

	public LocalDateTime getNgayRaQuyetDinhGiaHanGiaiQuyet() {
		return ngayRaQuyetDinhGiaHanGiaiQuyet;
	}

	public void setNgayRaQuyetDinhGiaHanGiaiQuyet(LocalDateTime ngayRaQuyetDinhGiaHanGiaiQuyet) {
		this.ngayRaQuyetDinhGiaHanGiaiQuyet = ngayRaQuyetDinhGiaHanGiaiQuyet;
	}

	public void setNgayRaQuyetDinhGiaHanTTXM(LocalDateTime ngayRaQuyetDinhGiaHanTTXM) {
		this.ngayRaQuyetDinhGiaHanTTXM = ngayRaQuyetDinhGiaHanTTXM;
	}

	public boolean isLapToDoanXacMinh() {
		return lapToDoanXacMinh;
	}

	public void setLapToDoanXacMinh(boolean lapToDoanXacMinh) {
		this.lapToDoanXacMinh = lapToDoanXacMinh;
	}

	public boolean isGiaHanGiaiQuyet() {
		return giaHanGiaiQuyet;
	}

	public void setGiaHanGiaiQuyet(boolean giaHanGiaiQuyet) {
		this.giaHanGiaiQuyet = giaHanGiaiQuyet;
	}

	public boolean isGiaHanTTXM() {
		return giaHanTTXM;
	}

	public void setGiaHanTTXM(boolean giaHanTTXM) {
		this.giaHanTTXM = giaHanTTXM;
	}

	public boolean isDoiThoai() {
		return doiThoai;
	}

	public void setDoiThoai(boolean doiThoai) {
		this.doiThoai = doiThoai;
	}

	public boolean isGiaoCoQuanDieuTra() {
		return giaoCoQuanDieuTra;
	}

	public void setGiaoCoQuanDieuTra(boolean giaoCoQuanDieuTra) {
		this.giaoCoQuanDieuTra = giaoCoQuanDieuTra;
	}

	public boolean isKhoiTo() {
		return khoiTo;
	}

	public void setKhoiTo(boolean khoiTo) {
		this.khoiTo = khoiTo;
	}

	public boolean isQuyetDinhGiaiQuyetKhieuNai() {
		return quyetDinhGiaiQuyetKhieuNai;
	}

	public void setQuyetDinhGiaiQuyetKhieuNai(boolean quyetDinhGiaiQuyetKhieuNai) {
		this.quyetDinhGiaiQuyetKhieuNai = quyetDinhGiaiQuyetKhieuNai;
	}

	public boolean isTheoDoiThucHien() {
		return theoDoiThucHien;
	}

	public void setTheoDoiThucHien(boolean theoDoiThucHien) {
		this.theoDoiThucHien = theoDoiThucHien;
	}

	public boolean isDaThuLy() {
		return daThuLy;
	}

	public void setDaThuLy(boolean daThuLy) {
		this.daThuLy = daThuLy;
	}

	public boolean isDaThamTraXacMinhVuViec() {
		return daThamTraXacMinhVuViec;
	}

	public void setDaThamTraXacMinhVuViec(boolean daThamTraXacMinhVuViec) {
		this.daThamTraXacMinhVuViec = daThamTraXacMinhVuViec;
	}

	public boolean isDaRaQuyetDinhGiaiQuyet() {
		return daRaQuyetDinhGiaiQuyet;
	}

	public void setDaRaQuyetDinhGiaiQuyet(boolean daRaQuyetDinhGiaiQuyet) {
		this.daRaQuyetDinhGiaiQuyet = daRaQuyetDinhGiaiQuyet;
	}

	public int getSoVuGiaoCoQuanDieuTra() {
		return soVuGiaoCoQuanDieuTra;
	}

	public void setSoVuGiaoCoQuanDieuTra(int soVuGiaoCoQuanDieuTra) {
		this.soVuGiaoCoQuanDieuTra = soVuGiaoCoQuanDieuTra;
	}

	public int getSoDoiTuongGiaoCoQuanDieuTra() {
		return soDoiTuongGiaoCoQuanDieuTra;
	}

	public void setSoDoiTuongGiaoCoQuanDieuTra(int soDoiTuongGiaoCoQuanDieuTra) {
		this.soDoiTuongGiaoCoQuanDieuTra = soDoiTuongGiaoCoQuanDieuTra;
	}

	public int getSoVuBiKhoiTo() {
		return soVuBiKhoiTo;
	}

	public void setSoVuBiKhoiTo(int soVuBiKhoiTo) {
		this.soVuBiKhoiTo = soVuBiKhoiTo;
	}

	public int getSoDoiTuongBiKhoiTo() {
		return soDoiTuongBiKhoiTo;
	}

	public void setSoDoiTuongBiKhoiTo(int soDoiTuongBiKhoiTo) {
		this.soDoiTuongBiKhoiTo = soDoiTuongBiKhoiTo;
	}

	public int getTongSoNguoiXuLyHanhChinh() {
		return tongSoNguoiXuLyHanhChinh;
	}

	public void setTongSoNguoiXuLyHanhChinh(int tongSoNguoiXuLyHanhChinh) {
		this.tongSoNguoiXuLyHanhChinh = tongSoNguoiXuLyHanhChinh;
	}

	public int getSoNguoiDaBiXuLyHanhChinh() {
		return soNguoiDaBiXuLyHanhChinh;
	}

	public void setSoNguoiDaBiXuLyHanhChinh(int soNguoiDaBiXuLyHanhChinh) {
		this.soNguoiDaBiXuLyHanhChinh = soNguoiDaBiXuLyHanhChinh;
	}

	public int getSoLanGiaiQuyetLai() {
		return soLanGiaiQuyetLai;
	}

	public void setSoLanGiaiQuyetLai(int soLanGiaiQuyetLai) {
		this.soLanGiaiQuyetLai = soLanGiaiQuyetLai;
	}

	public int getSoVuGiaiQuyetKhieuNai() {
		return soVuGiaiQuyetKhieuNai;
	}

	public void setSoVuGiaiQuyetKhieuNai(int soVuGiaiQuyetKhieuNai) {
		this.soVuGiaiQuyetKhieuNai = soVuGiaiQuyetKhieuNai;
	}

	public int getSoNguoiDuocTraLaiQuyenLoi() {
		return soNguoiDuocTraLaiQuyenLoi;
	}

	public long getTienPhaiThuNhaNuocQDGQ() {
		return tienPhaiThuNhaNuocQDGQ;
	}

	public void setTienPhaiThuNhaNuocQDGQ(long tienPhaiThuNhaNuocQDGQ) {
		this.tienPhaiThuNhaNuocQDGQ = tienPhaiThuNhaNuocQDGQ;
	}

	public long getDatPhaiThuNhaNuocQDGQ() {
		return datPhaiThuNhaNuocQDGQ;
	}

	public void setDatPhaiThuNhaNuocQDGQ(long datPhaiThuNhaNuocQDGQ) {
		this.datPhaiThuNhaNuocQDGQ = datPhaiThuNhaNuocQDGQ;
	}

	public long getTienPhaiTraCongDanQDGQ() {
		return tienPhaiTraCongDanQDGQ;
	}

	public void setTienPhaiTraCongDanQDGQ(long tienPhaiTraCongDanQDGQ) {
		this.tienPhaiTraCongDanQDGQ = tienPhaiTraCongDanQDGQ;
	}

	public long getDatPhaiTraCongDanQDGQ() {
		return datPhaiTraCongDanQDGQ;
	}

	public void setDatPhaiTraCongDanQDGQ(long datPhaiTraCongDanQDGQ) {
		this.datPhaiTraCongDanQDGQ = datPhaiTraCongDanQDGQ;
	}

	public void setSoNguoiDuocTraLaiQuyenLoi(int soNguoiDuocTraLaiQuyenLoi) {
		this.soNguoiDuocTraLaiQuyenLoi = soNguoiDuocTraLaiQuyenLoi;
	}

	public long getTienPhaiThuNhaNuoc() {
		return tienPhaiThuNhaNuoc;
	}

	public void setTienPhaiThuNhaNuoc(long tienPhaiThuNhaNuoc) {
		this.tienPhaiThuNhaNuoc = tienPhaiThuNhaNuoc;
	}

	public long getDatPhaiThuNhaNuoc() {
		return datPhaiThuNhaNuoc;
	}

	public void setDatPhaiThuNhaNuoc(long datPhaiThuNhaNuoc) {
		this.datPhaiThuNhaNuoc = datPhaiThuNhaNuoc;
	}

	public long getTienPhaiTraCongDan() {
		return tienPhaiTraCongDan;
	}

	public void setTienPhaiTraCongDan(long tienPhaiTraCongDan) {
		this.tienPhaiTraCongDan = tienPhaiTraCongDan;
	}

	public long getDatPhaiTraCongDan() {
		return datPhaiTraCongDan;
	}

	public void setDatPhaiTraCongDan(long datPhaiTraCongDan) {
		this.datPhaiTraCongDan = datPhaiTraCongDan;
	}

	public long getTienDaThuNhaNuoc() {
		return tienDaThuNhaNuoc;
	}

	public void setTienDaThuNhaNuoc(long tienDaThuNhaNuoc) {
		this.tienDaThuNhaNuoc = tienDaThuNhaNuoc;
	}

	public long getDatDaThuNhaNuoc() {
		return datDaThuNhaNuoc;
	}

	public void setDatDaThuNhaNuoc(long datDaThuNhaNuoc) {
		this.datDaThuNhaNuoc = datDaThuNhaNuoc;
	}

	public long getTienDaTraCongDan() {
		return tienDaTraCongDan;
	}

	public void setTienDaTraCongDan(long tienDaTraCongDan) {
		this.tienDaTraCongDan = tienDaTraCongDan;
	}

	public long getDatDaTraCongDan() {
		return datDaTraCongDan;
	}

	public void setDatDaTraCongDan(long datDaTraCongDan) {
		this.datDaTraCongDan = datDaTraCongDan;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public CongChuc getCanBoGiaiQuyet() {
		return canBoGiaiQuyet;
	}

	public void setCanBoGiaiQuyet(CongChuc canBoGiaiQuyet) {
		this.canBoGiaiQuyet = canBoGiaiQuyet;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public CongChuc getTruongDoanTTXM() {
		return truongDoanTTXM;
	}

	public void setTruongDoanTTXM(CongChuc truongDoanTTXM) {
		this.truongDoanTTXM = truongDoanTTXM;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public CongChuc getCanBoThamTraXacMinh() {
		return canBoThamTraXacMinh;
	}

	public void setCanBoThamTraXacMinh(CongChuc canBoThamTraXacMinh) {
		this.canBoThamTraXacMinh = canBoThamTraXacMinh;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public CoQuanQuanLy getCoQuanDieuTra() {
		return coQuanDieuTra;
	}

	public void setCoQuanDieuTra(CoQuanQuanLy coQuanDieuTra) {
		this.coQuanDieuTra = coQuanDieuTra;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public CoQuanQuanLy getCoQuanTheoDoi() {
		return coQuanTheoDoi;
	}

	public void setCoQuanTheoDoi(CoQuanQuanLy coQuanTheoDoi) {
		this.coQuanTheoDoi = coQuanTheoDoi;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public CoQuanQuanLy getDonViThamTraXacMinh() {
		return donViThamTraXacMinh;
	}

	public void setDonViThamTraXacMinh(CoQuanQuanLy donViThamTraXacMinh) {
		this.donViThamTraXacMinh = donViThamTraXacMinh;
	}
	
	@ApiModelProperty(example = "{}", position = 2)
	public CoQuanQuanLy getDonViGiaoThamTraXacMinh() {
		return donViGiaoThamTraXacMinh;
	}

	public void setDonViGiaoThamTraXacMinh(CoQuanQuanLy donViGiaoThamTraXacMinh) {
		this.donViGiaoThamTraXacMinh = donViGiaoThamTraXacMinh;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	public KetLuanNoiDungKhieuNaiEnum getKetLuanNoiDungKhieuNai() {
		return ketLuanNoiDungKhieuNai;
	}

	public void setKetLuanNoiDungKhieuNai(KetLuanNoiDungKhieuNaiEnum ketLuanNoiDungKhieuNai) {
		this.ketLuanNoiDungKhieuNai = ketLuanNoiDungKhieuNai;
	}

	public KetLuanNoiDungKhieuNaiEnum getKetLuanNoiDungKhieuNaiGiaoTTXM() {
		return ketLuanNoiDungKhieuNaiGiaoTTXM;
	}

	public void setKetLuanNoiDungKhieuNaiGiaoTTXM(KetLuanNoiDungKhieuNaiEnum ketLuanNoiDungKhieuNaiGiaoTTXM) {
		this.ketLuanNoiDungKhieuNaiGiaoTTXM = ketLuanNoiDungKhieuNaiGiaoTTXM;
	}

	public HinhThucTheoDoiEnum getHinhThucTheoDoi() {
		return hinhThucTheoDoi;
	}

	public void setHinhThucTheoDoi(HinhThucTheoDoiEnum hinhThucTheoDoi) {
		this.hinhThucTheoDoi = hinhThucTheoDoi;
	}

	public KetQuaThucHienTheoDoiEnum getKetQuaThucHienTheoDoi() {
		return ketQuaThucHienTheoDoi;
	}

	public void setKetQuaThucHienTheoDoi(KetQuaThucHienTheoDoiEnum ketQuaThucHienTheoDoi) {
		this.ketQuaThucHienTheoDoi = ketQuaThucHienTheoDoi;
	}
	
	@ApiModelProperty(hidden = true)
	public KetQuaTrangThaiDonEnum getKetQuaXLDGiaiQuyet() {
		return ketQuaXLDGiaiQuyet;
	}

	public void setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum ketQuaXLDGiaiQuyet) {
		this.ketQuaXLDGiaiQuyet = ketQuaXLDGiaiQuyet;
	}

	public KetQuaGiaiQuyetLan2Enum getKetQuaGiaiQuyetLan2() {
		return ketQuaGiaiQuyetLan2;
	}

	public void setKetQuaGiaiQuyetLan2(KetQuaGiaiQuyetLan2Enum ketQuaGiaiQuyetLan2) {
		this.ketQuaGiaiQuyetLan2 = ketQuaGiaiQuyetLan2;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getThongTinGiaiQuyetDonId() {
		return getId();
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Long getDonId() {
		if (getDon() != null) {
			return getDon().getId();
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
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCanBoGiaiQuyetInfo() {
		if (getCanBoGiaiQuyet() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("hoVaTen", getCanBoGiaiQuyet().getHoVaTen());
			map.put("congChucId", getCanBoGiaiQuyet().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getTruongDoanTTXMInfo() {
		if (getTruongDoanTTXM() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("hoVaTen", getTruongDoanTTXM().getHoVaTen());
			map.put("congChucId", getTruongDoanTTXM().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCanBoThamTraXacMinhInfo() {
		if (getCanBoThamTraXacMinh() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("hoVaTen", getCanBoThamTraXacMinh().getHoVaTen());
			map.put("congChucId", getCanBoThamTraXacMinh().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCoQuanDieuTraInfo() {
		if (getCoQuanDieuTra() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getCoQuanDieuTra().getTen());
			map.put("coQuanQuanLyId", getCoQuanDieuTra().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCoQuanTheoDoiInfo() {
		if (getCoQuanTheoDoi() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getCoQuanTheoDoi().getTen());
			map.put("coQuanQuanLyId", getCoQuanTheoDoi().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getDonViThamTraXacMinhInfo() {
		if (getDonViThamTraXacMinh() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getDonViThamTraXacMinh().getTen());
			map.put("coQuanQuanLyId", getDonViThamTraXacMinh().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getDonViGiaoThamTraXacMinhInfo() {
		if (getDonViGiaoThamTraXacMinh() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getDonViGiaoThamTraXacMinh().getTen());
			map.put("coQuanQuanLyId", getDonViGiaoThamTraXacMinh().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCanBoXuLyChiDinhInfo() {
		if (getCanBoXuLyChiDinh() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getCanBoXuLyChiDinh().getHoVaTen());
			map.put("congChucId", getCanBoXuLyChiDinh().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getPhongBanGiaiQuyetInfo() {
		if (getPhongBanGiaiQuyet() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getPhongBanGiaiQuyet().getTen());
			map.put("coQuanQuanLyId", getPhongBanGiaiQuyet().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNextStateInfo() {
		if (getNextState() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("id", getNextState().getId());
			map.put("type", getNextState().getType().name());
			return map;
		}
		return null;
	}

}
