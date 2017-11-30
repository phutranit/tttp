package vn.greenglobal.tttp.model.medial;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Model;

@Entity
@Table(name = "medial_ketquachuyeuvectpcthamnhung")
public class Medial_KetQuaChuYeuVeCTPCThamNhung extends Model<Medial_KetQuaChuYeuVeCTPCThamNhung> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private CoQuanQuanLy donVi;
	
	private int soThuTu;
	
	private Long chinhSachPhapLuatSoVanBanBanHanh;
	private Long chinhSachPhapLuatSoVanBanDuocSuaDoi;
	private Long tuyenTruyenPBGDPLVePCTNSoLuotCanBo;
	private Long tuyenTruyenPBGDPLVePCTNSoLop;
	private Long tuyenTruyenPBGDPLVePCTNSoLuongDauSach; //5
	private Long thucHienCacBPPNTNSoCoQuanTCDVDuocKiemTra;
	private Long thucHienCacBPPNTNSoCoQuanTCDVBiPhatHien;
	private Long thucHienCacBPPNTNSoVanBanDuocBanHanhMoi;
	private Long thucHienCacBPPNTNSoVanBanDuocSuaDoiBoSungChoPhuHop;
	private Long thucHienCacBPPNTNSoCuocKiemTra;
	private Long thucHienCacBPPNTNSoVuViPham;
	private Long thucHienCacBPPNTNSoNguoiBiPhatHienDaViPham;
	private Long thucHienCacBPPNTNSoNguoiViPhamDaBiXuLyKyLuat;
	private Long thucHienCacBPPNTNSoNguoiViPhamDaBiXuLyHinhSu;
	private Long thucHienCacBPPNTNTongGiaTriDuocKienNghi;
	private Long thucHienCacBPPNTNTongGiaTriDuocThuHoiVaBoiThuong;
	private Long thucHienCacBPPNTNSoNguoiDaNopLaiQuaTang;
	private Long thucHienCacBPPNTNGiaTriQuaTang;  //18
	private Long thucHienCacBPPNTNSoCoQuanTCDVThucHienQuyTacUngXu;
	private Long thucHienCacBPPNTNSoCanBoViPhamQuyTacUngXu;
	private Long thucHienCacBPPNTNSoCanBoDuocChuyenDoiVTCTPNTN; //21
	private Long thucHienCacBPPNTNSoNguoiDuocXMViecKeKhaiTSThuNhap;
	private Long thucHienCacBPPNTNSoNguoiBiKetLuanKKHKhongTrungThuc;
	private Long thucHienCacBPPNTNSoNguoiDDBiKLThieuTrachNhiem;
	private Long thucHienCacBPPNTNSoNguoiDDDaBiXLHSThieuTrachNhiem;
	private Long thucHienCacBPPNTNSoNguoiDDBiKLKyLuat;
	private Long thucHienCacBPPNTNSoCoQuanTCDaApDungISO;
	private Long thucHienCacBPPNTNTyLeCoQuanTCDaChiTraLuong;
	private Long thucHienCacBPPNTNSoVuThamNhungDaDuocPhatHienQuaKTNoiBo;
	private Long thucHienCacBPPNTNSoDoiTuongThamNhungDuocPhatHienQuaKTNoiBo; //30
	private Long thucHienCacBPPNTNSoVuThamNhungDuocPhatHienQuaCTThanhTra;
	private Long thucHienCacBPPNTNSoDoiTuongThamNhungBiPhatHienQuaCTThanhTra;
	private Long thucHienCacBPPNTNSoDonToCaoThamNhungThuocTQGQCuaCacCQTC;
	private Long thucHienCacBPPNTNSoDonToCaoThamNhungDaDuocGiaiQuyet;
	private Long thucHienCacBPPNTNSoVuThamNhungDuocPhatHienQuaGQKNTC;
	private Long thucHienCacBPPNTNSoDoiTuongThamNhungBiPhatHienQuaGQKNTC;
	private Long thucHienCacBPPNTNSoVuAnThamNhungDuocCoQuanCNKhoiTo;
	private Long thucHienCacBPPNTNSoDoiTuongThamNhungBiCoQuanCNKhoiTo; //38
	private Long thucHienCacBPPNTNSoVuAnThamNhungDaDuaRaXetXu;
	private Long thucHienCacBPPNTNSoDoiTuongBiKetAnThamNhungItNghiemTrong;
	private Long thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungNghiemTrong;
	private Long thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungRatNghiemTrong;
	private Long thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungDacBietNghiemTrong;
	private Long thucHienCacBPPNTNSVuViecThamNhungDuocXLHanhChinh;
	private Long thucHienCacBPPNTNSoCanBoCCVCBiKyLuatHCHanhViThamNhung;
	private Long thucHienCacBPPNTNSoVuViecThamNhungDuocPhatHienXemXetXuLy; //47
	private Long thucHienCacBPPNTNSoDoiTuongThamNhungDuocPhatHienDangXemXetXuLy;
	private Long thucHienCacBPPNTNTaiSanThamNhungDuocThuHoiBangTien;
	private Long thucHienCacBPPNTNTaiSanThamNhungDuocThuHoiBangDatDai;
	private Long thucHienCacBPPNTNTaiSanThamNhungKhongTheThuHoiBangTien;
	private Long thucHienCacBPPNTNTaiSanThamNhungKhongTheThuHoiBangDatDai;
	private Long thucHienCacBPPNTNSoNguoiToCaoHanhViThamNhungBiTraThu;
//	private Long thucHienCacBPPNTNSoNguoiToCaoHanhViThamNhungDuocKhenThuong;
	private Long thucHienCacBPPNTNSoNguoiDuocTangBangKhenCuaThuTuongChinhPhu; //56
	private Long thucHienCacBPPNTNSoNguoiTangBangKhenCuaBoNganhDiaPhuong;
	private Long thucHienCacBPPNTNSoNguoiTangGiayKhen;
	
	private String ghiChu = "";

	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}
	
	public Long getChinhSachPhapLuatSoVanBanBanHanh() {
		return chinhSachPhapLuatSoVanBanBanHanh;
	}

	public void setChinhSachPhapLuatSoVanBanBanHanh(Long chinhSachPhapLuatSoVanBanBanHanh) {
		this.chinhSachPhapLuatSoVanBanBanHanh = chinhSachPhapLuatSoVanBanBanHanh;
	}

	public Long getChinhSachPhapLuatSoVanBanDuocSuaDoi() {
		return chinhSachPhapLuatSoVanBanDuocSuaDoi;
	}

	public void setChinhSachPhapLuatSoVanBanDuocSuaDoi(Long chinhSachPhapLuatSoVanBanDuocSuaDoi) {
		this.chinhSachPhapLuatSoVanBanDuocSuaDoi = chinhSachPhapLuatSoVanBanDuocSuaDoi;
	}

	public Long getTuyenTruyenPBGDPLVePCTNSoLuotCanBo() {
		return tuyenTruyenPBGDPLVePCTNSoLuotCanBo;
	}

	public void setTuyenTruyenPBGDPLVePCTNSoLuotCanBo(Long tuyenTruyenPBGDPLVePCTNSoLuotCanBo) {
		this.tuyenTruyenPBGDPLVePCTNSoLuotCanBo = tuyenTruyenPBGDPLVePCTNSoLuotCanBo;
	}

	public Long getTuyenTruyenPBGDPLVePCTNSoLop() {
		return tuyenTruyenPBGDPLVePCTNSoLop;
	}

	public void setTuyenTruyenPBGDPLVePCTNSoLop(Long tuyenTruyenPBGDPLVePCTNSoLop) {
		this.tuyenTruyenPBGDPLVePCTNSoLop = tuyenTruyenPBGDPLVePCTNSoLop;
	}

	public Long getTuyenTruyenPBGDPLVePCTNSoLuongDauSach() {
		return tuyenTruyenPBGDPLVePCTNSoLuongDauSach;
	}

	public void setTuyenTruyenPBGDPLVePCTNSoLuongDauSach(Long tuyenTruyenPBGDPLVePCTNSoLuongDauSach) {
		this.tuyenTruyenPBGDPLVePCTNSoLuongDauSach = tuyenTruyenPBGDPLVePCTNSoLuongDauSach;
	}

	public Long getThucHienCacBPPNTNSoCoQuanTCDVDuocKiemTra() {
		return thucHienCacBPPNTNSoCoQuanTCDVDuocKiemTra;
	}

	public void setThucHienCacBPPNTNSoCoQuanTCDVDuocKiemTra(Long thucHienCacBPPNTNSoCoQuanTCDVDuocKiemTra) {
		this.thucHienCacBPPNTNSoCoQuanTCDVDuocKiemTra = thucHienCacBPPNTNSoCoQuanTCDVDuocKiemTra;
	}

	public Long getThucHienCacBPPNTNSoCoQuanTCDVBiPhatHien() {
		return thucHienCacBPPNTNSoCoQuanTCDVBiPhatHien;
	}

	public void setThucHienCacBPPNTNSoCoQuanTCDVBiPhatHien(Long thucHienCacBPPNTNSoCoQuanTCDVBiPhatHien) {
		this.thucHienCacBPPNTNSoCoQuanTCDVBiPhatHien = thucHienCacBPPNTNSoCoQuanTCDVBiPhatHien;
	}

	public Long getThucHienCacBPPNTNSoVanBanDuocBanHanhMoi() {
		return thucHienCacBPPNTNSoVanBanDuocBanHanhMoi;
	}

	public void setThucHienCacBPPNTNSoVanBanDuocBanHanhMoi(Long thucHienCacBPPNTNSoVanBanDuocBanHanhMoi) {
		this.thucHienCacBPPNTNSoVanBanDuocBanHanhMoi = thucHienCacBPPNTNSoVanBanDuocBanHanhMoi;
	}

	public Long getThucHienCacBPPNTNSoVanBanDuocSuaDoiBoSungChoPhuHop() {
		return thucHienCacBPPNTNSoVanBanDuocSuaDoiBoSungChoPhuHop;
	}

	public void setThucHienCacBPPNTNSoVanBanDuocSuaDoiBoSungChoPhuHop(
			Long thucHienCacBPPNTNSoVanBanDuocSuaDoiBoSungChoPhuHop) {
		this.thucHienCacBPPNTNSoVanBanDuocSuaDoiBoSungChoPhuHop = thucHienCacBPPNTNSoVanBanDuocSuaDoiBoSungChoPhuHop;
	}

	public Long getThucHienCacBPPNTNSoCuocKiemTra() {
		return thucHienCacBPPNTNSoCuocKiemTra;
	}

	public void setThucHienCacBPPNTNSoCuocKiemTra(Long thucHienCacBPPNTNSoCuocKiemTra) {
		this.thucHienCacBPPNTNSoCuocKiemTra = thucHienCacBPPNTNSoCuocKiemTra;
	}

	public Long getThucHienCacBPPNTNSoVuViPham() {
		return thucHienCacBPPNTNSoVuViPham;
	}

	public void setThucHienCacBPPNTNSoVuViPham(Long thucHienCacBPPNTNSoVuViPham) {
		this.thucHienCacBPPNTNSoVuViPham = thucHienCacBPPNTNSoVuViPham;
	}

	public Long getThucHienCacBPPNTNSoNguoiBiPhatHienDaViPham() {
		return thucHienCacBPPNTNSoNguoiBiPhatHienDaViPham;
	}

	public void setThucHienCacBPPNTNSoNguoiBiPhatHienDaViPham(Long thucHienCacBPPNTNSoNguoiBiPhatHienDaViPham) {
		this.thucHienCacBPPNTNSoNguoiBiPhatHienDaViPham = thucHienCacBPPNTNSoNguoiBiPhatHienDaViPham;
	}

	public Long getThucHienCacBPPNTNSoNguoiViPhamDaBiXuLyKyLuat() {
		return thucHienCacBPPNTNSoNguoiViPhamDaBiXuLyKyLuat;
	}

	public void setThucHienCacBPPNTNSoNguoiViPhamDaBiXuLyKyLuat(Long thucHienCacBPPNTNSoNguoiViPhamDaBiXuLyKyLuat) {
		this.thucHienCacBPPNTNSoNguoiViPhamDaBiXuLyKyLuat = thucHienCacBPPNTNSoNguoiViPhamDaBiXuLyKyLuat;
	}

	public Long getThucHienCacBPPNTNSoNguoiViPhamDaBiXuLyHinhSu() {
		return thucHienCacBPPNTNSoNguoiViPhamDaBiXuLyHinhSu;
	}

	public void setThucHienCacBPPNTNSoNguoiViPhamDaBiXuLyHinhSu(Long thucHienCacBPPNTNSoNguoiViPhamDaBiXuLyHinhSu) {
		this.thucHienCacBPPNTNSoNguoiViPhamDaBiXuLyHinhSu = thucHienCacBPPNTNSoNguoiViPhamDaBiXuLyHinhSu;
	}

	public Long getThucHienCacBPPNTNTongGiaTriDuocKienNghi() {
		return thucHienCacBPPNTNTongGiaTriDuocKienNghi;
	}

	public void setThucHienCacBPPNTNTongGiaTriDuocKienNghi(Long thucHienCacBPPNTNTongGiaTriDuocKienNghi) {
		this.thucHienCacBPPNTNTongGiaTriDuocKienNghi = thucHienCacBPPNTNTongGiaTriDuocKienNghi;
	}

	public Long getThucHienCacBPPNTNTongGiaTriDuocThuHoiVaBoiThuong() {
		return thucHienCacBPPNTNTongGiaTriDuocThuHoiVaBoiThuong;
	}

	public void setThucHienCacBPPNTNTongGiaTriDuocThuHoiVaBoiThuong(Long thucHienCacBPPNTNTongGiaTriDuocThuHoiVaBoiThuong) {
		this.thucHienCacBPPNTNTongGiaTriDuocThuHoiVaBoiThuong = thucHienCacBPPNTNTongGiaTriDuocThuHoiVaBoiThuong;
	}

	public Long getThucHienCacBPPNTNSoNguoiDaNopLaiQuaTang() {
		return thucHienCacBPPNTNSoNguoiDaNopLaiQuaTang;
	}

	public void setThucHienCacBPPNTNSoNguoiDaNopLaiQuaTang(Long thucHienCacBPPNTNSoNguoiDaNopLaiQuaTang) {
		this.thucHienCacBPPNTNSoNguoiDaNopLaiQuaTang = thucHienCacBPPNTNSoNguoiDaNopLaiQuaTang;
	}

	public Long getThucHienCacBPPNTNGiaTriQuaTang() {
		return thucHienCacBPPNTNGiaTriQuaTang;
	}

	public void setThucHienCacBPPNTNGiaTriQuaTang(Long thucHienCacBPPNTNGiaTriQuaTang) {
		this.thucHienCacBPPNTNGiaTriQuaTang = thucHienCacBPPNTNGiaTriQuaTang;
	}

	public Long getThucHienCacBPPNTNSoCoQuanTCDVThucHienQuyTacUngXu() {
		return thucHienCacBPPNTNSoCoQuanTCDVThucHienQuyTacUngXu;
	}

	public void setThucHienCacBPPNTNSoCoQuanTCDVThucHienQuyTacUngXu(Long thucHienCacBPPNTNSoCoQuanTCDVThucHienQuyTacUngXu) {
		this.thucHienCacBPPNTNSoCoQuanTCDVThucHienQuyTacUngXu = thucHienCacBPPNTNSoCoQuanTCDVThucHienQuyTacUngXu;
	}

	public Long getThucHienCacBPPNTNSoCanBoViPhamQuyTacUngXu() {
		return thucHienCacBPPNTNSoCanBoViPhamQuyTacUngXu;
	}

	public void setThucHienCacBPPNTNSoCanBoViPhamQuyTacUngXu(Long thucHienCacBPPNTNSoCanBoViPhamQuyTacUngXu) {
		this.thucHienCacBPPNTNSoCanBoViPhamQuyTacUngXu = thucHienCacBPPNTNSoCanBoViPhamQuyTacUngXu;
	}

	public Long getThucHienCacBPPNTNSoCanBoDuocChuyenDoiVTCTPNTN() {
		return thucHienCacBPPNTNSoCanBoDuocChuyenDoiVTCTPNTN;
	}

	public void setThucHienCacBPPNTNSoCanBoDuocChuyenDoiVTCTPNTN(Long thucHienCacBPPNTNSoCanBoDuocChuyenDoiVTCTPNTN) {
		this.thucHienCacBPPNTNSoCanBoDuocChuyenDoiVTCTPNTN = thucHienCacBPPNTNSoCanBoDuocChuyenDoiVTCTPNTN;
	}

	public Long getThucHienCacBPPNTNSoNguoiDuocXMViecKeKhaiTSThuNhap() {
		return thucHienCacBPPNTNSoNguoiDuocXMViecKeKhaiTSThuNhap;
	}

	public void setThucHienCacBPPNTNSoNguoiDuocXMViecKeKhaiTSThuNhap(
			Long thucHienCacBPPNTNSoNguoiDuocXMViecKeKhaiTSThuNhap) {
		this.thucHienCacBPPNTNSoNguoiDuocXMViecKeKhaiTSThuNhap = thucHienCacBPPNTNSoNguoiDuocXMViecKeKhaiTSThuNhap;
	}

	public Long getThucHienCacBPPNTNSoNguoiBiKetLuanKKHKhongTrungThuc() {
		return thucHienCacBPPNTNSoNguoiBiKetLuanKKHKhongTrungThuc;
	}

	public void setThucHienCacBPPNTNSoNguoiBiKetLuanKKHKhongTrungThuc(
			Long thucHienCacBPPNTNSoNguoiBiKetLuanKKHKhongTrungThuc) {
		this.thucHienCacBPPNTNSoNguoiBiKetLuanKKHKhongTrungThuc = thucHienCacBPPNTNSoNguoiBiKetLuanKKHKhongTrungThuc;
	}

	public Long getThucHienCacBPPNTNSoNguoiDDBiKLThieuTrachNhiem() {
		return thucHienCacBPPNTNSoNguoiDDBiKLThieuTrachNhiem;
	}

	public void setThucHienCacBPPNTNSoNguoiDDBiKLThieuTrachNhiem(Long thucHienCacBPPNTNSoNguoiDDBiKLThieuTrachNhiem) {
		this.thucHienCacBPPNTNSoNguoiDDBiKLThieuTrachNhiem = thucHienCacBPPNTNSoNguoiDDBiKLThieuTrachNhiem;
	}

	public Long getThucHienCacBPPNTNSoNguoiDDDaBiXLHSThieuTrachNhiem() {
		return thucHienCacBPPNTNSoNguoiDDDaBiXLHSThieuTrachNhiem;
	}

	public void setThucHienCacBPPNTNSoNguoiDDDaBiXLHSThieuTrachNhiem(
			Long thucHienCacBPPNTNSoNguoiDDDaBiXLHSThieuTrachNhiem) {
		this.thucHienCacBPPNTNSoNguoiDDDaBiXLHSThieuTrachNhiem = thucHienCacBPPNTNSoNguoiDDDaBiXLHSThieuTrachNhiem;
	}

	public Long getThucHienCacBPPNTNSoNguoiDDBiKLKyLuat() {
		return thucHienCacBPPNTNSoNguoiDDBiKLKyLuat;
	}

	public void setThucHienCacBPPNTNSoNguoiDDBiKLKyLuat(Long thucHienCacBPPNTNSoNguoiDDBiKLKyLuat) {
		this.thucHienCacBPPNTNSoNguoiDDBiKLKyLuat = thucHienCacBPPNTNSoNguoiDDBiKLKyLuat;
	}

	public Long getThucHienCacBPPNTNSoCoQuanTCDaApDungISO() {
		return thucHienCacBPPNTNSoCoQuanTCDaApDungISO;
	}

	public void setThucHienCacBPPNTNSoCoQuanTCDaApDungISO(Long thucHienCacBPPNTNSoCoQuanTCDaApDungISO) {
		this.thucHienCacBPPNTNSoCoQuanTCDaApDungISO = thucHienCacBPPNTNSoCoQuanTCDaApDungISO;
	}

	public Long getThucHienCacBPPNTNTyLeCoQuanTCDaChiTraLuong() {
		return thucHienCacBPPNTNTyLeCoQuanTCDaChiTraLuong;
	}

	public void setThucHienCacBPPNTNTyLeCoQuanTCDaChiTraLuong(Long thucHienCacBPPNTNTyLeCoQuanTCDaChiTraLuong) {
		this.thucHienCacBPPNTNTyLeCoQuanTCDaChiTraLuong = thucHienCacBPPNTNTyLeCoQuanTCDaChiTraLuong;
	}

	public Long getThucHienCacBPPNTNSoVuThamNhungDaDuocPhatHienQuaKTNoiBo() {
		return thucHienCacBPPNTNSoVuThamNhungDaDuocPhatHienQuaKTNoiBo;
	}

	public void setThucHienCacBPPNTNSoVuThamNhungDaDuocPhatHienQuaKTNoiBo(
			Long thucHienCacBPPNTNSoVuThamNhungDaDuocPhatHienQuaKTNoiBo) {
		this.thucHienCacBPPNTNSoVuThamNhungDaDuocPhatHienQuaKTNoiBo = thucHienCacBPPNTNSoVuThamNhungDaDuocPhatHienQuaKTNoiBo;
	}

	public Long getThucHienCacBPPNTNSoDoiTuongThamNhungDuocPhatHienQuaKTNoiBo() {
		return thucHienCacBPPNTNSoDoiTuongThamNhungDuocPhatHienQuaKTNoiBo;
	}

	public void setThucHienCacBPPNTNSoDoiTuongThamNhungDuocPhatHienQuaKTNoiBo(
			Long thucHienCacBPPNTNSoDoiTuongThamNhungDuocPhatHienQuaKTNoiBo) {
		this.thucHienCacBPPNTNSoDoiTuongThamNhungDuocPhatHienQuaKTNoiBo = thucHienCacBPPNTNSoDoiTuongThamNhungDuocPhatHienQuaKTNoiBo;
	}

	public Long getThucHienCacBPPNTNSoVuThamNhungDuocPhatHienQuaCTThanhTra() {
		return thucHienCacBPPNTNSoVuThamNhungDuocPhatHienQuaCTThanhTra;
	}

	public void setThucHienCacBPPNTNSoVuThamNhungDuocPhatHienQuaCTThanhTra(
			Long thucHienCacBPPNTNSoVuThamNhungDuocPhatHienQuaCTThanhTra) {
		this.thucHienCacBPPNTNSoVuThamNhungDuocPhatHienQuaCTThanhTra = thucHienCacBPPNTNSoVuThamNhungDuocPhatHienQuaCTThanhTra;
	}

	public Long getThucHienCacBPPNTNSoDoiTuongThamNhungBiPhatHienQuaCTThanhTra() {
		return thucHienCacBPPNTNSoDoiTuongThamNhungBiPhatHienQuaCTThanhTra;
	}

	public void setThucHienCacBPPNTNSoDoiTuongThamNhungBiPhatHienQuaCTThanhTra(
			Long thucHienCacBPPNTNSoDoiTuongThamNhungBiPhatHienQuaCTThanhTra) {
		this.thucHienCacBPPNTNSoDoiTuongThamNhungBiPhatHienQuaCTThanhTra = thucHienCacBPPNTNSoDoiTuongThamNhungBiPhatHienQuaCTThanhTra;
	}

	public Long getThucHienCacBPPNTNSoDonToCaoThamNhungThuocTQGQCuaCacCQTC() {
		return thucHienCacBPPNTNSoDonToCaoThamNhungThuocTQGQCuaCacCQTC;
	}

	public void setThucHienCacBPPNTNSoDonToCaoThamNhungThuocTQGQCuaCacCQTC(
			Long thucHienCacBPPNTNSoDonToCaoThamNhungThuocTQGQCuaCacCQTC) {
		this.thucHienCacBPPNTNSoDonToCaoThamNhungThuocTQGQCuaCacCQTC = thucHienCacBPPNTNSoDonToCaoThamNhungThuocTQGQCuaCacCQTC;
	}

	public Long getThucHienCacBPPNTNSoDonToCaoThamNhungDaDuocGiaiQuyet() {
		return thucHienCacBPPNTNSoDonToCaoThamNhungDaDuocGiaiQuyet;
	}

	public void setThucHienCacBPPNTNSoDonToCaoThamNhungDaDuocGiaiQuyet(
			Long thucHienCacBPPNTNSoDonToCaoThamNhungDaDuocGiaiQuyet) {
		this.thucHienCacBPPNTNSoDonToCaoThamNhungDaDuocGiaiQuyet = thucHienCacBPPNTNSoDonToCaoThamNhungDaDuocGiaiQuyet;
	}

	public Long getThucHienCacBPPNTNSoVuThamNhungDuocPhatHienQuaGQKNTC() {
		return thucHienCacBPPNTNSoVuThamNhungDuocPhatHienQuaGQKNTC;
	}

	public void setThucHienCacBPPNTNSoVuThamNhungDuocPhatHienQuaGQKNTC(
			Long thucHienCacBPPNTNSoVuThamNhungDuocPhatHienQuaGQKNTC) {
		this.thucHienCacBPPNTNSoVuThamNhungDuocPhatHienQuaGQKNTC = thucHienCacBPPNTNSoVuThamNhungDuocPhatHienQuaGQKNTC;
	}

	public Long getThucHienCacBPPNTNSoDoiTuongThamNhungBiPhatHienQuaGQKNTC() {
		return thucHienCacBPPNTNSoDoiTuongThamNhungBiPhatHienQuaGQKNTC;
	}

	public void setThucHienCacBPPNTNSoDoiTuongThamNhungBiPhatHienQuaGQKNTC(
			Long thucHienCacBPPNTNSoDoiTuongThamNhungBiPhatHienQuaGQKNTC) {
		this.thucHienCacBPPNTNSoDoiTuongThamNhungBiPhatHienQuaGQKNTC = thucHienCacBPPNTNSoDoiTuongThamNhungBiPhatHienQuaGQKNTC;
	}

	public Long getThucHienCacBPPNTNSoVuAnThamNhungDuocCoQuanCNKhoiTo() {
		return thucHienCacBPPNTNSoVuAnThamNhungDuocCoQuanCNKhoiTo;
	}

	public void setThucHienCacBPPNTNSoVuAnThamNhungDuocCoQuanCNKhoiTo(
			Long thucHienCacBPPNTNSoVuAnThamNhungDuocCoQuanCNKhoiTo) {
		this.thucHienCacBPPNTNSoVuAnThamNhungDuocCoQuanCNKhoiTo = thucHienCacBPPNTNSoVuAnThamNhungDuocCoQuanCNKhoiTo;
	}

	public Long getThucHienCacBPPNTNSoDoiTuongThamNhungBiCoQuanCNKhoiTo() {
		return thucHienCacBPPNTNSoDoiTuongThamNhungBiCoQuanCNKhoiTo;
	}

	public void setThucHienCacBPPNTNSoDoiTuongThamNhungBiCoQuanCNKhoiTo(
			Long thucHienCacBPPNTNSoDoiTuongThamNhungBiCoQuanCNKhoiTo) {
		this.thucHienCacBPPNTNSoDoiTuongThamNhungBiCoQuanCNKhoiTo = thucHienCacBPPNTNSoDoiTuongThamNhungBiCoQuanCNKhoiTo;
	}

	public Long getThucHienCacBPPNTNSoVuAnThamNhungDaDuaRaXetXu() {
		return thucHienCacBPPNTNSoVuAnThamNhungDaDuaRaXetXu;
	}

	public void setThucHienCacBPPNTNSoVuAnThamNhungDaDuaRaXetXu(Long thucHienCacBPPNTNSoVuAnThamNhungDaDuaRaXetXu) {
		this.thucHienCacBPPNTNSoVuAnThamNhungDaDuaRaXetXu = thucHienCacBPPNTNSoVuAnThamNhungDaDuaRaXetXu;
	}

	public Long getThucHienCacBPPNTNSoDoiTuongBiKetAnThamNhungItNghiemTrong() {
		return thucHienCacBPPNTNSoDoiTuongBiKetAnThamNhungItNghiemTrong;
	}

	public void setThucHienCacBPPNTNSoDoiTuongBiKetAnThamNhungItNghiemTrong(
			Long thucHienCacBPPNTNSoDoiTuongBiKetAnThamNhungItNghiemTrong) {
		this.thucHienCacBPPNTNSoDoiTuongBiKetAnThamNhungItNghiemTrong = thucHienCacBPPNTNSoDoiTuongBiKetAnThamNhungItNghiemTrong;
	}

	public Long getThucHienCacBPPNTNSoDoiTuongPhamToiThamNhungNghiemTrong() {
		return thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungNghiemTrong;
	}

	public void setThucHienCacBPPNTNSoDoiTuongPhamToiThamNhungNghiemTrong(
			Long thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungNghiemTrong) {
		this.thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungNghiemTrong = thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungNghiemTrong;
	}

	public Long getThucHienCacBPPNTNSoDoiTuongPhamToiThamNhungRatNghiemTrong() {
		return thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungRatNghiemTrong;
	}

	public void setThucHienCacBPPNTNSoDoiTuongPhamToiThamNhungRatNghiemTrong(
			Long thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungRatNghiemTrong) {
		this.thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungRatNghiemTrong = thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungRatNghiemTrong;
	}

	public Long getThucHienCacBPPNTNSoDoiTuongPhamToiThamNhungDacBietNghiemTrong() {
		return thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungDacBietNghiemTrong;
	}

	public void setThucHienCacBPPNTNSoDoiTuongPhamToiThamNhungDacBietNghiemTrong(
			Long thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungDacBietNghiemTrong) {
		this.thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungDacBietNghiemTrong = thucHienCacBPPNTNSoDoiTuongPhamToiThamNhungDacBietNghiemTrong;
	}

	public Long getThucHienCacBPPNTNSVuViecThamNhungDuocXLHanhChinh() {
		return thucHienCacBPPNTNSVuViecThamNhungDuocXLHanhChinh;
	}

	public void setThucHienCacBPPNTNSVuViecThamNhungDuocXLHanhChinh(Long thucHienCacBPPNTNSVuViecThamNhungDuocXLHanhChinh) {
		this.thucHienCacBPPNTNSVuViecThamNhungDuocXLHanhChinh = thucHienCacBPPNTNSVuViecThamNhungDuocXLHanhChinh;
	}

	public Long getThucHienCacBPPNTNSoCanBoCCVCBiKyLuatHCHanhViThamNhung() {
		return thucHienCacBPPNTNSoCanBoCCVCBiKyLuatHCHanhViThamNhung;
	}

	public void setThucHienCacBPPNTNSoCanBoCCVCBiKyLuatHCHanhViThamNhung(
			Long thucHienCacBPPNTNSoCanBoCCVCBiKyLuatHCHanhViThamNhung) {
		this.thucHienCacBPPNTNSoCanBoCCVCBiKyLuatHCHanhViThamNhung = thucHienCacBPPNTNSoCanBoCCVCBiKyLuatHCHanhViThamNhung;
	}

	public Long getThucHienCacBPPNTNSoVuViecThamNhungDuocPhatHienXemXetXuLy() {
		return thucHienCacBPPNTNSoVuViecThamNhungDuocPhatHienXemXetXuLy;
	}

	public void setThucHienCacBPPNTNSoVuViecThamNhungDuocPhatHienXemXetXuLy(
			Long thucHienCacBPPNTNSoVuViecThamNhungDuocPhatHienXemXetXuLy) {
		this.thucHienCacBPPNTNSoVuViecThamNhungDuocPhatHienXemXetXuLy = thucHienCacBPPNTNSoVuViecThamNhungDuocPhatHienXemXetXuLy;
	}

	public Long getThucHienCacBPPNTNSoDoiTuongThamNhungDuocPhatHienDangXemXetXuLy() {
		return thucHienCacBPPNTNSoDoiTuongThamNhungDuocPhatHienDangXemXetXuLy;
	}

	public void setThucHienCacBPPNTNSoDoiTuongThamNhungDuocPhatHienDangXemXetXuLy(
			Long thucHienCacBPPNTNSoDoiTuongThamNhungDuocPhatHienDangXemXetXuLy) {
		this.thucHienCacBPPNTNSoDoiTuongThamNhungDuocPhatHienDangXemXetXuLy = thucHienCacBPPNTNSoDoiTuongThamNhungDuocPhatHienDangXemXetXuLy;
	}

	public Long getThucHienCacBPPNTNTaiSanThamNhungDuocThuHoiBangTien() {
		return thucHienCacBPPNTNTaiSanThamNhungDuocThuHoiBangTien;
	}

	public void setThucHienCacBPPNTNTaiSanThamNhungDuocThuHoiBangTien(
			Long thucHienCacBPPNTNTaiSanThamNhungDuocThuHoiBangTien) {
		this.thucHienCacBPPNTNTaiSanThamNhungDuocThuHoiBangTien = thucHienCacBPPNTNTaiSanThamNhungDuocThuHoiBangTien;
	}

	public Long getThucHienCacBPPNTNTaiSanThamNhungDuocThuHoiBangDatDai() {
		return thucHienCacBPPNTNTaiSanThamNhungDuocThuHoiBangDatDai;
	}

	public void setThucHienCacBPPNTNTaiSanThamNhungDuocThuHoiBangDatDai(
			Long thucHienCacBPPNTNTaiSanThamNhungDuocThuHoiBangDatDai) {
		this.thucHienCacBPPNTNTaiSanThamNhungDuocThuHoiBangDatDai = thucHienCacBPPNTNTaiSanThamNhungDuocThuHoiBangDatDai;
	}

	public Long getThucHienCacBPPNTNTaiSanThamNhungKhongTheThuHoiBangTien() {
		return thucHienCacBPPNTNTaiSanThamNhungKhongTheThuHoiBangTien;
	}

	public void setThucHienCacBPPNTNTaiSanThamNhungKhongTheThuHoiBangTien(
			Long thucHienCacBPPNTNTaiSanThamNhungKhongTheThuHoiBangTien) {
		this.thucHienCacBPPNTNTaiSanThamNhungKhongTheThuHoiBangTien = thucHienCacBPPNTNTaiSanThamNhungKhongTheThuHoiBangTien;
	}

	public Long getThucHienCacBPPNTNTaiSanThamNhungKhongTheThuHoiBangDatDai() {
		return thucHienCacBPPNTNTaiSanThamNhungKhongTheThuHoiBangDatDai;
	}

	public void setThucHienCacBPPNTNTaiSanThamNhungKhongTheThuHoiBangDatDai(
			Long thucHienCacBPPNTNTaiSanThamNhungKhongTheThuHoiBangDatDai) {
		this.thucHienCacBPPNTNTaiSanThamNhungKhongTheThuHoiBangDatDai = thucHienCacBPPNTNTaiSanThamNhungKhongTheThuHoiBangDatDai;
	}

	public Long getThucHienCacBPPNTNSoNguoiToCaoHanhViThamNhungBiTraThu() {
		return thucHienCacBPPNTNSoNguoiToCaoHanhViThamNhungBiTraThu;
	}

	public void setThucHienCacBPPNTNSoNguoiToCaoHanhViThamNhungBiTraThu(
			Long thucHienCacBPPNTNSoNguoiToCaoHanhViThamNhungBiTraThu) {
		this.thucHienCacBPPNTNSoNguoiToCaoHanhViThamNhungBiTraThu = thucHienCacBPPNTNSoNguoiToCaoHanhViThamNhungBiTraThu;
	}

	public Long getThucHienCacBPPNTNSoNguoiDuocTangBangKhenCuaThuTuongChinhPhu() {
		return thucHienCacBPPNTNSoNguoiDuocTangBangKhenCuaThuTuongChinhPhu;
	}

	public void setThucHienCacBPPNTNSoNguoiDuocTangBangKhenCuaThuTuongChinhPhu(
			Long thucHienCacBPPNTNSoNguoiDuocTangBangKhenCuaThuTuongChinhPhu) {
		this.thucHienCacBPPNTNSoNguoiDuocTangBangKhenCuaThuTuongChinhPhu = thucHienCacBPPNTNSoNguoiDuocTangBangKhenCuaThuTuongChinhPhu;
	}

	public Long getThucHienCacBPPNTNSoNguoiTangBangKhenCuaBoNganhDiaPhuong() {
		return thucHienCacBPPNTNSoNguoiTangBangKhenCuaBoNganhDiaPhuong;
	}

	public void setThucHienCacBPPNTNSoNguoiTangBangKhenCuaBoNganhDiaPhuong(
			Long thucHienCacBPPNTNSoNguoiTangBangKhenCuaBoNganhDiaPhuong) {
		this.thucHienCacBPPNTNSoNguoiTangBangKhenCuaBoNganhDiaPhuong = thucHienCacBPPNTNSoNguoiTangBangKhenCuaBoNganhDiaPhuong;
	}

	public Long getThucHienCacBPPNTNSoNguoiTangGiayKhen() {
		return thucHienCacBPPNTNSoNguoiTangGiayKhen;
	}

	public void setThucHienCacBPPNTNSoNguoiTangGiayKhen(Long thucHienCacBPPNTNSoNguoiTangGiayKhen) {
		this.thucHienCacBPPNTNSoNguoiTangGiayKhen = thucHienCacBPPNTNSoNguoiTangGiayKhen;
	}

	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getDonViInfo() {
		if (getDonVi() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getDonVi().getId());
			map.put("ten", getDonVi().getTen());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Long getKetQuaChuYeuVeCTPCThamNhungId() {
		return getId();
	}
}
