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
@Table(name = "medial_tonghopketquagiaiquyettocao")
public class Medial_TongHopKetQuaGiaiQuyetToCao extends Model<Medial_TongHopKetQuaGiaiQuyetToCao> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private CoQuanQuanLy donVi;
	
	private int soThuTu;
	
	private Long tongSoDonToCao;
	private Long donNhanTrongKyBaoCao;
	private Long donTonKyTruocChuyenSang;
	private Long tongSoVuViec;
	private Long soDonThuocThamQuyen;
	private Long soVuViecThuocThamQuyen;
	private Long toCaoDung;
	private Long toCaoSai;
	private Long toCaoDungMotPhan;
	private Long kienNghiThuHoiChoNhaNuocTien;
	private Long kienNghiThuHoiChoNhaNuocDat;
	private Long traLaiChoCongDanTien;
	private Long traLaiChoCongDanDat;
	private Long soNguoiDuocTraLaiQuyenLoi;
	private Long kienNghiXuLyHanhChinhTongSoNguoi;
	private Long kienNghiXuLyHanhChinhSoNguoiDaBiXuLy;
	private Long soVuChuyenCoQuanDieuTra;
	private Long soDoiTuongChuyenCoQuanDieuTra;
	private Long soVuDaKhoiTo;
	private Long soDoiTuongDaKhoiTo;
	private Long soVuViecGiaiQuyetDungThoiHan;
	private Long soVuViecGiaiQuyetQuaThoiHan;
	private Long tongSoQuyetDinhPhaiToChucThucHien;
	private Long tongSoQuyetDinhPhaiToChucThucHienDaThucHien;
	private Long tienPhaiThuChoNhaNuoc;
	private Long datPhaiThuChoNhaNuoc;
	private Long tienDaThuChoNhaNuoc;
	private Long datDaThuChoNhaNuoc;
	private Long tienPhaiTraChoCongDan;
	private Long datPhaiTraChoCongDan;
	private Long tienDaTraChoCongDan;
	private Long datDaTraChoCongDan;
	
	private String ghiChu = "";

	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}
	
	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	public Long getTongSoDonToCao() {
		return tongSoDonToCao;
	}

	public void setTongSoDonToCao(Long tongSoDonToCao) {
		this.tongSoDonToCao = tongSoDonToCao;
	}

	public Long getDonNhanTrongKyBaoCao() {
		return donNhanTrongKyBaoCao;
	}

	public void setDonNhanTrongKyBaoCao(Long donNhanTrongKyBaoCao) {
		this.donNhanTrongKyBaoCao = donNhanTrongKyBaoCao;
	}

	public Long getDonTonKyTruocChuyenSang() {
		return donTonKyTruocChuyenSang;
	}

	public void setDonTonKyTruocChuyenSang(Long donTonKyTruocChuyenSang) {
		this.donTonKyTruocChuyenSang = donTonKyTruocChuyenSang;
	}

	public Long getTongSoVuViec() {
		return tongSoVuViec;
	}

	public void setTongSoVuViec(Long tongSoVuViec) {
		this.tongSoVuViec = tongSoVuViec;
	}

	public Long getSoDonThuocThamQuyen() {
		return soDonThuocThamQuyen;
	}

	public void setSoDonThuocThamQuyen(Long soDonThuocThamQuyen) {
		this.soDonThuocThamQuyen = soDonThuocThamQuyen;
	}

	public Long getSoVuViecThuocThamQuyen() {
		return soVuViecThuocThamQuyen;
	}

	public void setSoVuViecThuocThamQuyen(Long soVuViecThuocThamQuyen) {
		this.soVuViecThuocThamQuyen = soVuViecThuocThamQuyen;
	}

	public Long getToCaoDung() {
		return toCaoDung;
	}

	public void setToCaoDung(Long toCaoDung) {
		this.toCaoDung = toCaoDung;
	}

	public Long getToCaoSai() {
		return toCaoSai;
	}

	public void setToCaoSai(Long toCaoSai) {
		this.toCaoSai = toCaoSai;
	}

	public Long getToCaoDungMotPhan() {
		return toCaoDungMotPhan;
	}

	public void setToCaoDungMotPhan(Long toCaoDungMotPhan) {
		this.toCaoDungMotPhan = toCaoDungMotPhan;
	}

	public Long getKienNghiThuHoiChoNhaNuocTien() {
		return kienNghiThuHoiChoNhaNuocTien;
	}

	public void setKienNghiThuHoiChoNhaNuocTien(Long kienNghiThuHoiChoNhaNuocTien) {
		this.kienNghiThuHoiChoNhaNuocTien = kienNghiThuHoiChoNhaNuocTien;
	}

	public Long getKienNghiThuHoiChoNhaNuocDat() {
		return kienNghiThuHoiChoNhaNuocDat;
	}

	public void setKienNghiThuHoiChoNhaNuocDat(Long kienNghiThuHoiChoNhaNuocDat) {
		this.kienNghiThuHoiChoNhaNuocDat = kienNghiThuHoiChoNhaNuocDat;
	}

	public Long getTraLaiChoCongDanTien() {
		return traLaiChoCongDanTien;
	}

	public void setTraLaiChoCongDanTien(Long traLaiChoCongDanTien) {
		this.traLaiChoCongDanTien = traLaiChoCongDanTien;
	}

	public Long getTraLaiChoCongDanDat() {
		return traLaiChoCongDanDat;
	}

	public void setTraLaiChoCongDanDat(Long traLaiChoCongDanDat) {
		this.traLaiChoCongDanDat = traLaiChoCongDanDat;
	}

	public Long getSoNguoiDuocTraLaiQuyenLoi() {
		return soNguoiDuocTraLaiQuyenLoi;
	}

	public void setSoNguoiDuocTraLaiQuyenLoi(Long soNguoiDuocTraLaiQuyenLoi) {
		this.soNguoiDuocTraLaiQuyenLoi = soNguoiDuocTraLaiQuyenLoi;
	}

	public Long getKienNghiXuLyHanhChinhTongSoNguoi() {
		return kienNghiXuLyHanhChinhTongSoNguoi;
	}

	public void setKienNghiXuLyHanhChinhTongSoNguoi(Long kienNghiXuLyHanhChinhTongSoNguoi) {
		this.kienNghiXuLyHanhChinhTongSoNguoi = kienNghiXuLyHanhChinhTongSoNguoi;
	}

	public Long getKienNghiXuLyHanhChinhSoNguoiDaBiXuLy() {
		return kienNghiXuLyHanhChinhSoNguoiDaBiXuLy;
	}

	public void setKienNghiXuLyHanhChinhSoNguoiDaBiXuLy(Long kienNghiXuLyHanhChinhSoNguoiDaBiXuLy) {
		this.kienNghiXuLyHanhChinhSoNguoiDaBiXuLy = kienNghiXuLyHanhChinhSoNguoiDaBiXuLy;
	}

	public Long getSoVuChuyenCoQuanDieuTra() {
		return soVuChuyenCoQuanDieuTra;
	}

	public void setSoVuChuyenCoQuanDieuTra(Long soVuChuyenCoQuanDieuTra) {
		this.soVuChuyenCoQuanDieuTra = soVuChuyenCoQuanDieuTra;
	}

	public Long getSoDoiTuongChuyenCoQuanDieuTra() {
		return soDoiTuongChuyenCoQuanDieuTra;
	}

	public void setSoDoiTuongChuyenCoQuanDieuTra(Long soDoiTuongChuyenCoQuanDieuTra) {
		this.soDoiTuongChuyenCoQuanDieuTra = soDoiTuongChuyenCoQuanDieuTra;
	}

	public Long getSoVuDaKhoiTo() {
		return soVuDaKhoiTo;
	}

	public void setSoVuDaKhoiTo(Long soVuDaKhoiTo) {
		this.soVuDaKhoiTo = soVuDaKhoiTo;
	}

	public Long getSoDoiTuongDaKhoiTo() {
		return soDoiTuongDaKhoiTo;
	}

	public void setSoDoiTuongDaKhoiTo(Long soDoiTuongDaKhoiTo) {
		this.soDoiTuongDaKhoiTo = soDoiTuongDaKhoiTo;
	}

	public Long getSoVuViecGiaiQuyetDungThoiHan() {
		return soVuViecGiaiQuyetDungThoiHan;
	}

	public void setSoVuViecGiaiQuyetDungThoiHan(Long soVuViecGiaiQuyetDungThoiHan) {
		this.soVuViecGiaiQuyetDungThoiHan = soVuViecGiaiQuyetDungThoiHan;
	}

	public Long getSoVuViecGiaiQuyetQuaThoiHan() {
		return soVuViecGiaiQuyetQuaThoiHan;
	}

	public void setSoVuViecGiaiQuyetQuaThoiHan(Long soVuViecGiaiQuyetQuaThoiHan) {
		this.soVuViecGiaiQuyetQuaThoiHan = soVuViecGiaiQuyetQuaThoiHan;
	}

	public Long getTongSoQuyetDinhPhaiToChucThucHien() {
		return tongSoQuyetDinhPhaiToChucThucHien;
	}

	public void setTongSoQuyetDinhPhaiToChucThucHien(Long tongSoQuyetDinhPhaiToChucThucHien) {
		this.tongSoQuyetDinhPhaiToChucThucHien = tongSoQuyetDinhPhaiToChucThucHien;
	}

	public Long getTongSoQuyetDinhPhaiToChucThucHienDaThucHien() {
		return tongSoQuyetDinhPhaiToChucThucHienDaThucHien;
	}

	public void setTongSoQuyetDinhPhaiToChucThucHienDaThucHien(Long tongSoQuyetDinhPhaiToChucThucHienDaThucHien) {
		this.tongSoQuyetDinhPhaiToChucThucHienDaThucHien = tongSoQuyetDinhPhaiToChucThucHienDaThucHien;
	}

	public Long getTienPhaiThuChoNhaNuoc() {
		return tienPhaiThuChoNhaNuoc;
	}

	public void setTienPhaiThuChoNhaNuoc(Long tienPhaiThuChoNhaNuoc) {
		this.tienPhaiThuChoNhaNuoc = tienPhaiThuChoNhaNuoc;
	}

	public Long getDatPhaiThuChoNhaNuoc() {
		return datPhaiThuChoNhaNuoc;
	}

	public void setDatPhaiThuChoNhaNuoc(Long datPhaiThuChoNhaNuoc) {
		this.datPhaiThuChoNhaNuoc = datPhaiThuChoNhaNuoc;
	}

	public Long getTienDaThuChoNhaNuoc() {
		return tienDaThuChoNhaNuoc;
	}

	public void setTienDaThuChoNhaNuoc(Long tienDaThuChoNhaNuoc) {
		this.tienDaThuChoNhaNuoc = tienDaThuChoNhaNuoc;
	}

	public Long getDatDaThuChoNhaNuoc() {
		return datDaThuChoNhaNuoc;
	}

	public void setDatDaThuChoNhaNuoc(Long datDaThuChoNhaNuoc) {
		this.datDaThuChoNhaNuoc = datDaThuChoNhaNuoc;
	}

	public Long getTienPhaiTraChoCongDan() {
		return tienPhaiTraChoCongDan;
	}

	public void setTienPhaiTraChoCongDan(Long tienPhaiTraChoCongDan) {
		this.tienPhaiTraChoCongDan = tienPhaiTraChoCongDan;
	}

	public Long getDatPhaiTraChoCongDan() {
		return datPhaiTraChoCongDan;
	}

	public void setDatPhaiTraChoCongDan(Long datPhaiTraChoCongDan) {
		this.datPhaiTraChoCongDan = datPhaiTraChoCongDan;
	}

	public Long getTienDaTraChoCongDan() {
		return tienDaTraChoCongDan;
	}

	public void setTienDaTraChoCongDan(Long tienDaTraChoCongDan) {
		this.tienDaTraChoCongDan = tienDaTraChoCongDan;
	}

	public Long getDatDaTraChoCongDan() {
		return datDaTraChoCongDan;
	}

	public void setDatDaTraChoCongDan(Long datDaTraChoCongDan) {
		this.datDaTraChoCongDan = datDaTraChoCongDan;
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
	public Long getTongHopKetQuaGiaiQuyetToCaoId() {
		return getId();
	}
}
