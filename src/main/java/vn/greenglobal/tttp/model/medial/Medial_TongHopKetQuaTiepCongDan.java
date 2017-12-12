package vn.greenglobal.tttp.model.medial;

import javax.persistence.Entity;
import javax.persistence.Table;
import vn.greenglobal.tttp.model.ModelThongKe;

@Entity
@Table(name = "medial_tonghopketquatiepcongdan")
public class Medial_TongHopKetQuaTiepCongDan extends ModelThongKe<Medial_TongHopKetQuaTiepCongDan> {
	
	private int soThuTu;
	
	private Long tiepCongDanThuongXuyenLuot;
	private Long tiepCongDanThuongXuyenNguoi;
	private Long tiepCongDanThuongXuyenVuViecCu;
	private Long tiepCongDanThuongXuyenVuViecMoiPhatSinh;
	private Long tiepCongDanThuongXuyenDoanDongNguoiSoDoan;
	private Long tiepCongDanThuongXuyenDoanDongNguoiSoNguoi;
	private Long tiepCongDanThuongXuyenDoanDongNguoiVuViecCu;
	private Long tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh;
	private Long tiepCongDanDinhKyDotXuatCuaLanhDaoLuot;
	private Long tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi;
	private Long tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu;
	private Long tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh;
	private Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan;
	private Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi;
	private Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu;
	private Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh;
	private Long tongVuViecDonKhieuNaiLinhVucHanhChinh;
	private Long donKhieuNaiLinhVucHanhChinhLienQuanDenDatDai;
	private Long donKhieuNaiLinhVucHanhChinhVeNhaTaiSan;
	private Long donKhieuNaiLinhVucHanhChinhVeChinhSachCCVC;
	private Long donKhieuNaiLinhVucHanhChinhVeChinhCTVHXHKKhac;
	private Long donKhieuNaiLinhVucTuPhap;
	private Long donKhieuNaiLinhVucVeDang;
	private Long tongVuViecDonToCao;
	private Long donToCaoLinhVucHanhChinh;
	private Long donToCaoLinhVucTuPhap;
	private Long donToCaoLinhVucThamNhung;
	private Long donToCaoLinhVucVeDang;
	private Long donToCaoLinhVucKhac;
	private Long phanAnhKienNghiKhac;
	private Long chuaDuocGiaiQuyet;
	private Long chuaCoQuyetDinhGiaiQuyet;
	private Long daCoQuyetDinhGiaiQuyet;
	private Long daCoBanAnCuaToa;
	
	private String tenDonVi = "";
	private String ghiChu = "";
	
	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	public Long getTiepCongDanThuongXuyenLuot() {
		return tiepCongDanThuongXuyenLuot;
	}

	public void setTiepCongDanThuongXuyenLuot(Long tiepCongDanThuongXuyenLuot) {
		this.tiepCongDanThuongXuyenLuot = tiepCongDanThuongXuyenLuot;
	}

	public Long getTiepCongDanThuongXuyenNguoi() {
		return tiepCongDanThuongXuyenNguoi;
	}

	public void setTiepCongDanThuongXuyenNguoi(Long tiepCongDanThuongXuyenNguoi) {
		this.tiepCongDanThuongXuyenNguoi = tiepCongDanThuongXuyenNguoi;
	}

	public Long getTiepCongDanThuongXuyenVuViecCu() {
		return tiepCongDanThuongXuyenVuViecCu;
	}

	public void setTiepCongDanThuongXuyenVuViecCu(Long tiepCongDanThuongXuyenVuViecCu) {
		this.tiepCongDanThuongXuyenVuViecCu = tiepCongDanThuongXuyenVuViecCu;
	}

	public Long getTiepCongDanThuongXuyenVuViecMoiPhatSinh() {
		return tiepCongDanThuongXuyenVuViecMoiPhatSinh;
	}

	public void setTiepCongDanThuongXuyenVuViecMoiPhatSinh(Long tiepCongDanThuongXuyenVuViecMoiPhatSinh) {
		this.tiepCongDanThuongXuyenVuViecMoiPhatSinh = tiepCongDanThuongXuyenVuViecMoiPhatSinh;
	}

	public Long getTiepCongDanThuongXuyenDoanDongNguoiSoDoan() {
		return tiepCongDanThuongXuyenDoanDongNguoiSoDoan;
	}

	public void setTiepCongDanThuongXuyenDoanDongNguoiSoDoan(Long tiepCongDanThuongXuyenDoanDongNguoiSoDoan) {
		this.tiepCongDanThuongXuyenDoanDongNguoiSoDoan = tiepCongDanThuongXuyenDoanDongNguoiSoDoan;
	}

	public Long getTiepCongDanThuongXuyenDoanDongNguoiSoNguoi() {
		return tiepCongDanThuongXuyenDoanDongNguoiSoNguoi;
	}

	public void setTiepCongDanThuongXuyenDoanDongNguoiSoNguoi(Long tiepCongDanThuongXuyenDoanDongNguoiSoNguoi) {
		this.tiepCongDanThuongXuyenDoanDongNguoiSoNguoi = tiepCongDanThuongXuyenDoanDongNguoiSoNguoi;
	}

	public Long getTiepCongDanThuongXuyenDoanDongNguoiVuViecCu() {
		return tiepCongDanThuongXuyenDoanDongNguoiVuViecCu;
	}

	public void setTiepCongDanThuongXuyenDoanDongNguoiVuViecCu(Long tiepCongDanThuongXuyenDoanDongNguoiVuViecCu) {
		this.tiepCongDanThuongXuyenDoanDongNguoiVuViecCu = tiepCongDanThuongXuyenDoanDongNguoiVuViecCu;
	}

	public Long getTiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh() {
		return tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh;
	}

	public void setTiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh(
			Long tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh) {
		this.tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh = tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh;
	}

	public Long getTiepCongDanDinhKyDotXuatCuaLanhDaoLuot() {
		return tiepCongDanDinhKyDotXuatCuaLanhDaoLuot;
	}

	public void setTiepCongDanDinhKyDotXuatCuaLanhDaoLuot(Long tiepCongDanDinhKyDotXuatCuaLanhDaoLuot) {
		this.tiepCongDanDinhKyDotXuatCuaLanhDaoLuot = tiepCongDanDinhKyDotXuatCuaLanhDaoLuot;
	}

	public Long getTiepCongDanDinhKyDotXuatCuaLanhDaoNguoi() {
		return tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi;
	}

	public void setTiepCongDanDinhKyDotXuatCuaLanhDaoNguoi(Long tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi) {
		this.tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi = tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi;
	}

	public Long getTiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu() {
		return tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu;
	}

	public void setTiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu(Long tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu) {
		this.tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu = tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu;
	}

	public Long getTiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh() {
		return tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh;
	}

	public void setTiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh(
			Long tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh) {
		this.tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh = tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh;
	}

	public Long getTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan() {
		return tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan;
	}

	public void setTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan(
			Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan) {
		this.tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan = tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan;
	}

	public Long getTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi() {
		return tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi;
	}

	public void setTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi(
			Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi) {
		this.tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi = tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi;
	}

	public Long getTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu() {
		return tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu;
	}

	public void setTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu(
			Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu) {
		this.tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu = tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu;
	}

	public Long getTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh() {
		return tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh;
	}

	public void setTiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh(
			Long tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh) {
		this.tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh = tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh;
	}

	public Long getTongVuViecDonKhieuNaiLinhVucHanhChinh() {
		return tongVuViecDonKhieuNaiLinhVucHanhChinh;
	}

	public void setTongVuViecDonKhieuNaiLinhVucHanhChinh(Long tongVuViecDonKhieuNaiLinhVucHanhChinh) {
		this.tongVuViecDonKhieuNaiLinhVucHanhChinh = tongVuViecDonKhieuNaiLinhVucHanhChinh;
	}

	public Long getDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai() {
		return donKhieuNaiLinhVucHanhChinhLienQuanDenDatDai;
	}

	public void setDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai(Long donKhieuNaiLinhVucHanhChinhLienQuanDenDatDai) {
		this.donKhieuNaiLinhVucHanhChinhLienQuanDenDatDai = donKhieuNaiLinhVucHanhChinhLienQuanDenDatDai;
	}

	public Long getDonKhieuNaiLinhVucHanhChinhVeNhaTaiSan() {
		return donKhieuNaiLinhVucHanhChinhVeNhaTaiSan;
	}

	public void setDonKhieuNaiLinhVucHanhChinhVeNhaTaiSan(Long donKhieuNaiLinhVucHanhChinhVeNhaTaiSan) {
		this.donKhieuNaiLinhVucHanhChinhVeNhaTaiSan = donKhieuNaiLinhVucHanhChinhVeNhaTaiSan;
	}

	public Long getDonKhieuNaiLinhVucHanhChinhVeChinhSachCCVC() {
		return donKhieuNaiLinhVucHanhChinhVeChinhSachCCVC;
	}

	public void setDonKhieuNaiLinhVucHanhChinhVeChinhSachCCVC(Long donKhieuNaiLinhVucHanhChinhVeChinhSachCCVC) {
		this.donKhieuNaiLinhVucHanhChinhVeChinhSachCCVC = donKhieuNaiLinhVucHanhChinhVeChinhSachCCVC;
	}

	public Long getDonKhieuNaiLinhVucHanhChinhVeChinhCTVHXHKKhac() {
		return donKhieuNaiLinhVucHanhChinhVeChinhCTVHXHKKhac;
	}

	public void setDonKhieuNaiLinhVucHanhChinhVeChinhCTVHXHKKhac(Long donKhieuNaiLinhVucHanhChinhVeChinhCTVHXHKKhac) {
		this.donKhieuNaiLinhVucHanhChinhVeChinhCTVHXHKKhac = donKhieuNaiLinhVucHanhChinhVeChinhCTVHXHKKhac;
	}

	public Long getDonKhieuNaiLinhVucTuPhap() {
		return donKhieuNaiLinhVucTuPhap;
	}

	public void setDonKhieuNaiLinhVucTuPhap(Long donKhieuNaiLinhVucTuPhap) {
		this.donKhieuNaiLinhVucTuPhap = donKhieuNaiLinhVucTuPhap;
	}

	public Long getDonKhieuNaiLinhVucVeDang() {
		return donKhieuNaiLinhVucVeDang;
	}

	public void setDonKhieuNaiLinhVucVeDang(Long donKhieuNaiLinhVucVeDang) {
		this.donKhieuNaiLinhVucVeDang = donKhieuNaiLinhVucVeDang;
	}

	public Long getTongVuViecDonToCao() {
		return tongVuViecDonToCao;
	}

	public void setTongVuViecDonToCao(Long tongVuViecDonToCao) {
		this.tongVuViecDonToCao = tongVuViecDonToCao;
	}

	public Long getDonToCaoLinhVucHanhChinh() {
		return donToCaoLinhVucHanhChinh;
	}

	public void setDonToCaoLinhVucHanhChinh(Long donToCaoLinhVucHanhChinh) {
		this.donToCaoLinhVucHanhChinh = donToCaoLinhVucHanhChinh;
	}

	public Long getDonToCaoLinhVucTuPhap() {
		return donToCaoLinhVucTuPhap;
	}

	public void setDonToCaoLinhVucTuPhap(Long donToCaoLinhVucTuPhap) {
		this.donToCaoLinhVucTuPhap = donToCaoLinhVucTuPhap;
	}

	public Long getDonToCaoLinhVucThamNhung() {
		return donToCaoLinhVucThamNhung;
	}

	public void setDonToCaoLinhVucThamNhung(Long donToCaoLinhVucThamNhung) {
		this.donToCaoLinhVucThamNhung = donToCaoLinhVucThamNhung;
	}

	public Long getDonToCaoLinhVucVeDang() {
		return donToCaoLinhVucVeDang;
	}

	public void setDonToCaoLinhVucVeDang(Long donToCaoLinhVucVeDang) {
		this.donToCaoLinhVucVeDang = donToCaoLinhVucVeDang;
	}

	public Long getDonToCaoLinhVucKhac() {
		return donToCaoLinhVucKhac;
	}

	public void setDonToCaoLinhVucKhac(Long donToCaoLinhVucKhac) {
		this.donToCaoLinhVucKhac = donToCaoLinhVucKhac;
	}

	public Long getPhanAnhKienNghiKhac() {
		return phanAnhKienNghiKhac;
	}

	public void setPhanAnhKienNghiKhac(Long phanAnhKienNghiKhac) {
		this.phanAnhKienNghiKhac = phanAnhKienNghiKhac;
	}

	public Long getChuaDuocGiaiQuyet() {
		return chuaDuocGiaiQuyet;
	}

	public void setChuaDuocGiaiQuyet(Long chuaDuocGiaiQuyet) {
		this.chuaDuocGiaiQuyet = chuaDuocGiaiQuyet;
	}

	public Long getChuaCoQuyetDinhGiaiQuyet() {
		return chuaCoQuyetDinhGiaiQuyet;
	}

	public void setChuaCoQuyetDinhGiaiQuyet(Long chuaCoQuyetDinhGiaiQuyet) {
		this.chuaCoQuyetDinhGiaiQuyet = chuaCoQuyetDinhGiaiQuyet;
	}

	public Long getDaCoQuyetDinhGiaiQuyet() {
		return daCoQuyetDinhGiaiQuyet;
	}

	public void setDaCoQuyetDinhGiaiQuyet(Long daCoQuyetDinhGiaiQuyet) {
		this.daCoQuyetDinhGiaiQuyet = daCoQuyetDinhGiaiQuyet;
	}

	public Long getDaCoBanAnCuaToa() {
		return daCoBanAnCuaToa;
	}

	public void setDaCoBanAnCuaToa(Long daCoBanAnCuaToa) {
		this.daCoBanAnCuaToa = daCoBanAnCuaToa;
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
