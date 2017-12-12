package vn.greenglobal.tttp.model.medial;

import javax.persistence.Entity;
import javax.persistence.Table;
import vn.greenglobal.tttp.model.ModelThongKe;

@Entity
@Table(name = "medial_tonghopcongtacxdllthanhtra")
public class Medial_TongHopCongTacXDLLThanhTra extends ModelThongKe<Medial_TongHopCongTacXDLLThanhTra> {
	
	private int soThuTu;
	
	private Long tongSo;
	private Long soTTVCaoCapVaTuongDuong;
	private Long soTTVChinhVaTuongDuong;
	private Long soTTVVaTuongDuong;
	private Long soCBCCVCTrongBienChe;
	private Long soLaoDongHopDong;
	private Long tiepNhanTuyenDung;
	private Long nghiHuuChuyenCongTac;
	private Long boNhiemChucVuLanhDao;
	private Long boNhiemVaoNgachNangNgach;
	private Long chuyenDoiViTriCongTac;
	private Long viPhamKLPhaiXuLy;
	private Long viPhamKLDaXuLy;
	private Long tongSoNhuCau;
	private Long tongSoDaThucHien;
	private Long thanhTraVienNhuCau;
	private Long thanhTraVienDaThucHien;
	private Long thanhTraVienChinhNhuCau;
	private Long thanhTraVienChinhDaThucHien;
	private Long thanhTraVienCaoCapNhuCau;
	private Long thanhTraVienCaoCapDaThucHien;
	private Long daoTaoLyLuanChinhTri;
	private Long daoTaoKhac;
	private String tenDonVi = "";
	private String ghiChu = "";
	
	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	public Long getTongSo() {
		return tongSo;
	}

	public void setTongSo(Long tongSo) {
		this.tongSo = tongSo;
	}

	public Long getSoTTVCaoCapVaTuongDuong() {
		return soTTVCaoCapVaTuongDuong;
	}

	public void setSoTTVCaoCapVaTuongDuong(Long soTTVCaoCapVaTuongDuong) {
		this.soTTVCaoCapVaTuongDuong = soTTVCaoCapVaTuongDuong;
	}

	public Long getSoTTVChinhVaTuongDuong() {
		return soTTVChinhVaTuongDuong;
	}

	public void setSoTTVChinhVaTuongDuong(Long soTTVChinhVaTuongDuong) {
		this.soTTVChinhVaTuongDuong = soTTVChinhVaTuongDuong;
	}

	public Long getSoTTVVaTuongDuong() {
		return soTTVVaTuongDuong;
	}

	public void setSoTTVVaTuongDuong(Long soTTVVaTuongDuong) {
		this.soTTVVaTuongDuong = soTTVVaTuongDuong;
	}

	public Long getSoCBCCVCTrongBienChe() {
		return soCBCCVCTrongBienChe;
	}

	public void setSoCBCCVCTrongBienChe(Long soCBCCVCTrongBienChe) {
		this.soCBCCVCTrongBienChe = soCBCCVCTrongBienChe;
	}

	public Long getSoLaoDongHopDong() {
		return soLaoDongHopDong;
	}

	public void setSoLaoDongHopDong(Long soLaoDongHopDong) {
		this.soLaoDongHopDong = soLaoDongHopDong;
	}

	public Long getTiepNhanTuyenDung() {
		return tiepNhanTuyenDung;
	}

	public void setTiepNhanTuyenDung(Long tiepNhanTuyenDung) {
		this.tiepNhanTuyenDung = tiepNhanTuyenDung;
	}

	public Long getNghiHuuChuyenCongTac() {
		return nghiHuuChuyenCongTac;
	}

	public void setNghiHuuChuyenCongTac(Long nghiHuuChuyenCongTac) {
		this.nghiHuuChuyenCongTac = nghiHuuChuyenCongTac;
	}

	public Long getBoNhiemChucVuLanhDao() {
		return boNhiemChucVuLanhDao;
	}

	public void setBoNhiemChucVuLanhDao(Long boNhiemChucVuLanhDao) {
		this.boNhiemChucVuLanhDao = boNhiemChucVuLanhDao;
	}

	public Long getBoNhiemVaoNgachNangNgach() {
		return boNhiemVaoNgachNangNgach;
	}

	public void setBoNhiemVaoNgachNangNgach(Long boNhiemVaoNgachNangNgach) {
		this.boNhiemVaoNgachNangNgach = boNhiemVaoNgachNangNgach;
	}

	public Long getChuyenDoiViTriCongTac() {
		return chuyenDoiViTriCongTac;
	}

	public void setChuyenDoiViTriCongTac(Long chuyenDoiViTriCongTac) {
		this.chuyenDoiViTriCongTac = chuyenDoiViTriCongTac;
	}

	public Long getViPhamKLPhaiXuLy() {
		return viPhamKLPhaiXuLy;
	}

	public void setViPhamKLPhaiXuLy(Long viPhamKLPhaiXuLy) {
		this.viPhamKLPhaiXuLy = viPhamKLPhaiXuLy;
	}

	public Long getViPhamKLDaXuLy() {
		return viPhamKLDaXuLy;
	}

	public void setViPhamKLDaXuLy(Long viPhamKLDaXuLy) {
		this.viPhamKLDaXuLy = viPhamKLDaXuLy;
	}

	public Long getTongSoNhuCau() {
		return tongSoNhuCau;
	}

	public void setTongSoNhuCau(Long tongSoNhuCau) {
		this.tongSoNhuCau = tongSoNhuCau;
	}

	public Long getTongSoDaThucHien() {
		return tongSoDaThucHien;
	}

	public void setTongSoDaThucHien(Long tongSoDaThucHien) {
		this.tongSoDaThucHien = tongSoDaThucHien;
	}

	public Long getThanhTraVienNhuCau() {
		return thanhTraVienNhuCau;
	}

	public void setThanhTraVienNhuCau(Long thanhTraVienNhuCau) {
		this.thanhTraVienNhuCau = thanhTraVienNhuCau;
	}

	public Long getThanhTraVienDaThucHien() {
		return thanhTraVienDaThucHien;
	}

	public void setThanhTraVienDaThucHien(Long thanhTraVienDaThucHien) {
		this.thanhTraVienDaThucHien = thanhTraVienDaThucHien;
	}

	public Long getThanhTraVienChinhNhuCau() {
		return thanhTraVienChinhNhuCau;
	}

	public void setThanhTraVienChinhNhuCau(Long thanhTraVienChinhNhuCau) {
		this.thanhTraVienChinhNhuCau = thanhTraVienChinhNhuCau;
	}

	public Long getThanhTraVienChinhDaThucHien() {
		return thanhTraVienChinhDaThucHien;
	}

	public void setThanhTraVienChinhDaThucHien(Long thanhTraVienChinhDaThucHien) {
		this.thanhTraVienChinhDaThucHien = thanhTraVienChinhDaThucHien;
	}

	public Long getThanhTraVienCaoCapNhuCau() {
		return thanhTraVienCaoCapNhuCau;
	}

	public void setThanhTraVienCaoCapNhuCau(Long thanhTraVienCaoCapNhuCau) {
		this.thanhTraVienCaoCapNhuCau = thanhTraVienCaoCapNhuCau;
	}

	public Long getThanhTraVienCaoCapDaThucHien() {
		return thanhTraVienCaoCapDaThucHien;
	}

	public void setThanhTraVienCaoCapDaThucHien(Long thanhTraVienCaoCapDaThucHien) {
		this.thanhTraVienCaoCapDaThucHien = thanhTraVienCaoCapDaThucHien;
	}

	public Long getDaoTaoLyLuanChinhTri() {
		return daoTaoLyLuanChinhTri;
	}

	public void setDaoTaoLyLuanChinhTri(Long daoTaoLyLuanChinhTri) {
		this.daoTaoLyLuanChinhTri = daoTaoLyLuanChinhTri;
	}

	public Long getDaoTaoKhac() {
		return daoTaoKhac;
	}

	public void setDaoTaoKhac(Long daoTaoKhac) {
		this.daoTaoKhac = daoTaoKhac;
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
