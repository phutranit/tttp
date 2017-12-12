package vn.greenglobal.tttp.model.medial;

import javax.persistence.Entity;
import javax.persistence.Table;
import vn.greenglobal.tttp.model.ModelThongKe;

@Entity
@Table(name = "medial_thanhtrathamnhungphathienquathanhtra")
public class Medial_ThanhTraThamNhungPHQThanhTra extends ModelThongKe<Medial_ThanhTraThamNhungPHQThanhTra> {
	
	private int soThuTu;
	
	private Long soVu;
	private Long soNguoi;
	private Long taiSanThamNhungTongTien;
	private Long taiSanThamNhungTien;
	private Long taiSanThamNhungTaiSanKhac;
	private Long taiSanThamNhungDat;
	private Long kienNghiThuHoiTongTien;
	private Long kienNghiThuHoiTien;
	private Long kienNghiThuHoiTaiSanKhac;
	private Long kienNghiThuHoiDat;
	private Long daThuTongTien;
	private Long daThuTien;
	private Long daThuTaiSanKhac;
	private Long daThuDat;
	private Long hanhChinhToChuc;
	private Long hanhChinhCaNhan;
	private Long chuyenCoQuanDieuTraVu;
	private Long chuyenCoQuanDieuTraDoiTuong;
	private Long daXuLyHanhChinhToChuc;
	private Long daXuLyHanhChinhCaNhan;
	private Long daKhoiToVu;
	private Long daKhoiToDoiTuong;
	private Long xuLyTrachNhiemNguoiDungDauKienNghi;
	private Long xuLyTrachNhiemNguoiDungDauDaXuLy;
	
	private String tenDonVi = "";
	private String ghiChu = "";
	
	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	public Long getSoVu() {
		return soVu;
	}

	public void setSoVu(Long soVu) {
		this.soVu = soVu;
	}

	public Long getSoNguoi() {
		return soNguoi;
	}

	public void setSoNguoi(Long soNguoi) {
		this.soNguoi = soNguoi;
	}

	public Long getTaiSanThamNhungTongTien() {
		return taiSanThamNhungTongTien;
	}

	public void setTaiSanThamNhungTongTien(Long taiSanThamNhungTongTien) {
		this.taiSanThamNhungTongTien = taiSanThamNhungTongTien;
	}

	public Long getTaiSanThamNhungTien() {
		return taiSanThamNhungTien;
	}

	public void setTaiSanThamNhungTien(Long taiSanThamNhungTien) {
		this.taiSanThamNhungTien = taiSanThamNhungTien;
	}

	public Long getTaiSanThamNhungTaiSanKhac() {
		return taiSanThamNhungTaiSanKhac;
	}

	public void setTaiSanThamNhungTaiSanKhac(Long taiSanThamNhungTaiSanKhac) {
		this.taiSanThamNhungTaiSanKhac = taiSanThamNhungTaiSanKhac;
	}

	public Long getTaiSanThamNhungDat() {
		return taiSanThamNhungDat;
	}

	public void setTaiSanThamNhungDat(Long taiSanThamNhungDat) {
		this.taiSanThamNhungDat = taiSanThamNhungDat;
	}

	public Long getKienNghiThuHoiTongTien() {
		return kienNghiThuHoiTongTien;
	}

	public void setKienNghiThuHoiTongTien(Long kienNghiThuHoiTongTien) {
		this.kienNghiThuHoiTongTien = kienNghiThuHoiTongTien;
	}

	public Long getKienNghiThuHoiTien() {
		return kienNghiThuHoiTien;
	}

	public void setKienNghiThuHoiTien(Long kienNghiThuHoiTien) {
		this.kienNghiThuHoiTien = kienNghiThuHoiTien;
	}

	public Long getKienNghiThuHoiTaiSanKhac() {
		return kienNghiThuHoiTaiSanKhac;
	}

	public void setKienNghiThuHoiTaiSanKhac(Long kienNghiThuHoiTaiSanKhac) {
		this.kienNghiThuHoiTaiSanKhac = kienNghiThuHoiTaiSanKhac;
	}

	public Long getKienNghiThuHoiDat() {
		return kienNghiThuHoiDat;
	}

	public void setKienNghiThuHoiDat(Long kienNghiThuHoiDat) {
		this.kienNghiThuHoiDat = kienNghiThuHoiDat;
	}

	public Long getDaThuTongTien() {
		return daThuTongTien;
	}

	public void setDaThuTongTien(Long daThuTongTien) {
		this.daThuTongTien = daThuTongTien;
	}

	public Long getDaThuTien() {
		return daThuTien;
	}

	public void setDaThuTien(Long daThuTien) {
		this.daThuTien = daThuTien;
	}

	public Long getDaThuTaiSanKhac() {
		return daThuTaiSanKhac;
	}

	public void setDaThuTaiSanKhac(Long daThuTaiSanKhac) {
		this.daThuTaiSanKhac = daThuTaiSanKhac;
	}

	public Long getDaThuDat() {
		return daThuDat;
	}

	public void setDaThuDat(Long daThuDat) {
		this.daThuDat = daThuDat;
	}

	public Long getHanhChinhToChuc() {
		return hanhChinhToChuc;
	}

	public void setHanhChinhToChuc(Long hanhChinhToChuc) {
		this.hanhChinhToChuc = hanhChinhToChuc;
	}

	public Long getHanhChinhCaNhan() {
		return hanhChinhCaNhan;
	}

	public void setHanhChinhCaNhan(Long hanhChinhCaNhan) {
		this.hanhChinhCaNhan = hanhChinhCaNhan;
	}

	public Long getChuyenCoQuanDieuTraVu() {
		return chuyenCoQuanDieuTraVu;
	}

	public void setChuyenCoQuanDieuTraVu(Long chuyenCoQuanDieuTraVu) {
		this.chuyenCoQuanDieuTraVu = chuyenCoQuanDieuTraVu;
	}

	public Long getChuyenCoQuanDieuTraDoiTuong() {
		return chuyenCoQuanDieuTraDoiTuong;
	}

	public void setChuyenCoQuanDieuTraDoiTuong(Long chuyenCoQuanDieuTraDoiTuong) {
		this.chuyenCoQuanDieuTraDoiTuong = chuyenCoQuanDieuTraDoiTuong;
	}

	public Long getDaXuLyHanhChinhToChuc() {
		return daXuLyHanhChinhToChuc;
	}

	public void setDaXuLyHanhChinhToChuc(Long daXuLyHanhChinhToChuc) {
		this.daXuLyHanhChinhToChuc = daXuLyHanhChinhToChuc;
	}

	public Long getDaXuLyHanhChinhCaNhan() {
		return daXuLyHanhChinhCaNhan;
	}

	public void setDaXuLyHanhChinhCaNhan(Long daXuLyHanhChinhCaNhan) {
		this.daXuLyHanhChinhCaNhan = daXuLyHanhChinhCaNhan;
	}

	public Long getDaKhoiToVu() {
		return daKhoiToVu;
	}

	public void setDaKhoiToVu(Long daKhoiToVu) {
		this.daKhoiToVu = daKhoiToVu;
	}

	public Long getDaKhoiToDoiTuong() {
		return daKhoiToDoiTuong;
	}

	public void setDaKhoiToDoiTuong(Long daKhoiToDoiTuong) {
		this.daKhoiToDoiTuong = daKhoiToDoiTuong;
	}

	public Long getXuLyTrachNhiemNguoiDungDauKienNghi() {
		return xuLyTrachNhiemNguoiDungDauKienNghi;
	}

	public void setXuLyTrachNhiemNguoiDungDauKienNghi(Long xuLyTrachNhiemNguoiDungDauKienNghi) {
		this.xuLyTrachNhiemNguoiDungDauKienNghi = xuLyTrachNhiemNguoiDungDauKienNghi;
	}

	public Long getXuLyTrachNhiemNguoiDungDauDaXuLy() {
		return xuLyTrachNhiemNguoiDungDauDaXuLy;
	}

	public void setXuLyTrachNhiemNguoiDungDauDaXuLy(Long xuLyTrachNhiemNguoiDungDauDaXuLy) {
		this.xuLyTrachNhiemNguoiDungDauDaXuLy = xuLyTrachNhiemNguoiDungDauDaXuLy;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public String getTenDonVi() {
		return tenDonVi;
	}

	public void setTenDonVi(String tenDonVi) {
		this.tenDonVi = tenDonVi;
	}
}
