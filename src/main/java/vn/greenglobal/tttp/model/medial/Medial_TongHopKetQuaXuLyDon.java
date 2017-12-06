package vn.greenglobal.tttp.model.medial;

import javax.persistence.Entity;
import javax.persistence.Table;
import vn.greenglobal.tttp.model.ModelThongKe;

@Entity
@Table(name = "medial_tonghopketquaxulydon")
public class Medial_TongHopKetQuaXuLyDon extends ModelThongKe<Medial_TongHopKetQuaXuLyDon> {
	
	private int soThuTu;
	
	private Long tongSoDonTiepNhanXLDTCD;
	private Long tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy;
	private Long tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy;
	private Long tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang;
	private Long tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang;
	private Long tongSoDonDuDieuKienThuLy;
	private Long tongSoDonKhieuNaiLinhVucHanhChinh;
	private Long tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai;
	private Long tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan;
	private Long tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC;
	private Long tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac;
	private Long tongSoDonKhieuNaiLinhVucTuPhap;
	private Long tongSoDonKhieuNaiLinhVucVeDang;
	private Long tongSoDonLinhVucToCao;
	private Long tongSoDonToCaoLinhVucHanhChinh;
	private Long tongSoDonToCaoLinhVucTuPhap;
	private Long tongSoDonToCaoLinhVucThamNhung;
	private Long tongSoDonToCaoLinhVucVeDang;
	private Long tongSoDonToCaoLinhVucKhac;
	private Long tongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap;
	private Long tongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap;
	private Long tongSoDonTheoTQGQCuaCoQuanDang;
	private Long tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet;
	private Long tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyet;
	private Long tongSoDonXLDDonKienNghiPhanAnh;
	private Long tongSoDonXLDSoVanBanHuongDan;
	private Long tongSoDonChuyenCQCoThamQuyen;
	private Long tongSoDonCoSoCongVanDonDocViecGiaiQuyet;
	private Long tongSoDonThuocThamQuyenKhieuNai;
	private Long tongSoDonThuocThamQuyenToCao;
	private String tenDonVi = "";
	private String ghiChu = "";
	
	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	public Long getTongSoDonTiepNhanXLDTCD() {
		return tongSoDonTiepNhanXLDTCD;
	}

	public void setTongSoDonTiepNhanXLDTCD(Long tongSoDonTiepNhanXLDTCD) {
		this.tongSoDonTiepNhanXLDTCD = tongSoDonTiepNhanXLDTCD;
	}

	public Long getTongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy() {
		return tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy;
	}

	public void setTongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy(
			Long tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy) {
		this.tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy = tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy;
	}

	public Long getTongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy() {
		return tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy;
	}

	public void setTongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy(
			Long tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy) {
		this.tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy = tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy;
	}

	public Long getTongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang() {
		return tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang;
	}

	public void setTongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang(
			Long tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang) {
		this.tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang = tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang;
	}

	public Long getTongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang() {
		return tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang;
	}

	public void setTongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang(
			Long tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang) {
		this.tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang = tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang;
	}

	public Long getTongSoDonDuDieuKienThuLy() {
		return tongSoDonDuDieuKienThuLy;
	}

	public void setTongSoDonDuDieuKienThuLy(Long tongSoDonDuDieuKienThuLy) {
		this.tongSoDonDuDieuKienThuLy = tongSoDonDuDieuKienThuLy;
	}

	public Long getTongSoDonKhieuNaiLinhVucHanhChinh() {
		return tongSoDonKhieuNaiLinhVucHanhChinh;
	}

	public void setTongSoDonKhieuNaiLinhVucHanhChinh(Long tongSoDonKhieuNaiLinhVucHanhChinh) {
		this.tongSoDonKhieuNaiLinhVucHanhChinh = tongSoDonKhieuNaiLinhVucHanhChinh;
	}

	public Long getTongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai() {
		return tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai;
	}

	public void setTongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai(
			Long tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai) {
		this.tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai = tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai;
	}

	public Long getTongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan() {
		return tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan;
	}

	public void setTongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan(Long tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan) {
		this.tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan = tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan;
	}

	public Long getTongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC() {
		return tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC;
	}

	public void setTongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC(
			Long tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC) {
		this.tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC = tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC;
	}

	public Long getTongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac() {
		return tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac;
	}

	public void setTongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac(
			Long tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac) {
		this.tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac = tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac;
	}

	public Long getTongSoDonKhieuNaiLinhVucTuPhap() {
		return tongSoDonKhieuNaiLinhVucTuPhap;
	}

	public void setTongSoDonKhieuNaiLinhVucTuPhap(Long tongSoDonKhieuNaiLinhVucTuPhap) {
		this.tongSoDonKhieuNaiLinhVucTuPhap = tongSoDonKhieuNaiLinhVucTuPhap;
	}

	public Long getTongSoDonKhieuNaiLinhVucVeDang() {
		return tongSoDonKhieuNaiLinhVucVeDang;
	}

	public void setTongSoDonKhieuNaiLinhVucVeDang(Long tongSoDonKhieuNaiLinhVucVeDang) {
		this.tongSoDonKhieuNaiLinhVucVeDang = tongSoDonKhieuNaiLinhVucVeDang;
	}

	public Long getTongSoDonLinhVucToCao() {
		return tongSoDonLinhVucToCao;
	}

	public void setTongSoDonLinhVucToCao(Long tongSoDonLinhVucToCao) {
		this.tongSoDonLinhVucToCao = tongSoDonLinhVucToCao;
	}

	public Long getTongSoDonToCaoLinhVucHanhChinh() {
		return tongSoDonToCaoLinhVucHanhChinh;
	}

	public void setTongSoDonToCaoLinhVucHanhChinh(Long tongSoDonToCaoLinhVucHanhChinh) {
		this.tongSoDonToCaoLinhVucHanhChinh = tongSoDonToCaoLinhVucHanhChinh;
	}

	public Long getTongSoDonToCaoLinhVucTuPhap() {
		return tongSoDonToCaoLinhVucTuPhap;
	}

	public void setTongSoDonToCaoLinhVucTuPhap(Long tongSoDonToCaoLinhVucTuPhap) {
		this.tongSoDonToCaoLinhVucTuPhap = tongSoDonToCaoLinhVucTuPhap;
	}

	public Long getTongSoDonToCaoLinhVucThamNhung() {
		return tongSoDonToCaoLinhVucThamNhung;
	}

	public void setTongSoDonToCaoLinhVucThamNhung(Long tongSoDonToCaoLinhVucThamNhung) {
		this.tongSoDonToCaoLinhVucThamNhung = tongSoDonToCaoLinhVucThamNhung;
	}

	public Long getTongSoDonToCaoLinhVucVeDang() {
		return tongSoDonToCaoLinhVucVeDang;
	}

	public void setTongSoDonToCaoLinhVucVeDang(Long tongSoDonToCaoLinhVucVeDang) {
		this.tongSoDonToCaoLinhVucVeDang = tongSoDonToCaoLinhVucVeDang;
	}

	public Long getTongSoDonToCaoLinhVucKhac() {
		return tongSoDonToCaoLinhVucKhac;
	}

	public void setTongSoDonToCaoLinhVucKhac(Long tongSoDonToCaoLinhVucKhac) {
		this.tongSoDonToCaoLinhVucKhac = tongSoDonToCaoLinhVucKhac;
	}

	public Long getTongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap() {
		return tongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap;
	}

	public void setTongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap(Long tongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap) {
		this.tongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap = tongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap;
	}

	public Long getTongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap() {
		return tongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap;
	}

	public void setTongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap(Long tongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap) {
		this.tongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap = tongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap;
	}

	public Long getTongSoDonTheoTQGQCuaCoQuanDang() {
		return tongSoDonTheoTQGQCuaCoQuanDang;
	}

	public void setTongSoDonTheoTQGQCuaCoQuanDang(Long tongSoDonTheoTQGQCuaCoQuanDang) {
		this.tongSoDonTheoTQGQCuaCoQuanDang = tongSoDonTheoTQGQCuaCoQuanDang;
	}

	public Long getTongSoDonTheoTTGiaiQuyetChuaGiaiQuyet() {
		return tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet;
	}

	public void setTongSoDonTheoTTGiaiQuyetChuaGiaiQuyet(Long tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet) {
		this.tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet = tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet;
	}

	public Long getTongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyet() {
		return tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyet;
	}

	public void setTongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyet(Long tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyet) {
		this.tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyet = tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyet;
	}

	public Long getTongSoDonXLDDonKienNghiPhanAnh() {
		return tongSoDonXLDDonKienNghiPhanAnh;
	}

	public void setTongSoDonXLDDonKienNghiPhanAnh(Long tongSoDonXLDDonKienNghiPhanAnh) {
		this.tongSoDonXLDDonKienNghiPhanAnh = tongSoDonXLDDonKienNghiPhanAnh;
	}

	public Long getTongSoDonXLDSoVanBanHuongDan() {
		return tongSoDonXLDSoVanBanHuongDan;
	}

	public void setTongSoDonXLDSoVanBanHuongDan(Long tongSoDonXLDSoVanBanHuongDan) {
		this.tongSoDonXLDSoVanBanHuongDan = tongSoDonXLDSoVanBanHuongDan;
	}

	public Long getTongSoDonChuyenCQCoThamQuyen() {
		return tongSoDonChuyenCQCoThamQuyen;
	}

	public void setTongSoDonChuyenCQCoThamQuyen(Long tongSoDonChuyenCQCoThamQuyen) {
		this.tongSoDonChuyenCQCoThamQuyen = tongSoDonChuyenCQCoThamQuyen;
	}

	public Long getTongSoDonCoSoCongVanDonDocViecGiaiQuyet() {
		return tongSoDonCoSoCongVanDonDocViecGiaiQuyet;
	}

	public void setTongSoDonCoSoCongVanDonDocViecGiaiQuyet(Long tongSoDonCoSoCongVanDonDocViecGiaiQuyet) {
		this.tongSoDonCoSoCongVanDonDocViecGiaiQuyet = tongSoDonCoSoCongVanDonDocViecGiaiQuyet;
	}

	public Long getTongSoDonThuocThamQuyenKhieuNai() {
		return tongSoDonThuocThamQuyenKhieuNai;
	}

	public void setTongSoDonThuocThamQuyenKhieuNai(Long tongSoDonThuocThamQuyenKhieuNai) {
		this.tongSoDonThuocThamQuyenKhieuNai = tongSoDonThuocThamQuyenKhieuNai;
	}

	public Long getTongSoDonThuocThamQuyenToCao() {
		return tongSoDonThuocThamQuyenToCao;
	}

	public void setTongSoDonThuocThamQuyenToCao(Long tongSoDonThuocThamQuyenToCao) {
		this.tongSoDonThuocThamQuyenToCao = tongSoDonThuocThamQuyenToCao;
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
