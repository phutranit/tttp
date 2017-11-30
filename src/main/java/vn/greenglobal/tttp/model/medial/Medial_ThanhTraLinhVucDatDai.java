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
@Table(name = "medial_thanhtralinhvucdatdai")
public class Medial_ThanhTraLinhVucDatDai extends Model<Medial_ThanhTraLinhVucDatDai> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private CoQuanQuanLy donVi;
	
	private int soThuTu;
	
	private Long tongSoCuocThanhTra;
	private Long kyTruocChuyenSang;
	private Long trienKhaiTrongKyBaoCao;
	private Long theoKeHoach;
	private Long dotXuat;
	private Long ketThucThanhTraTrucTiep;
	private Long daBanHanhKetLuan;
	private Long soDonViDuocThanhTra;
	private Long soDonViCoViPham;
	private Long tongViPhamTien;
	private Long tongViPhamDat;
	private Long datLanChiem;
	private Long giaoDatCapDatSaiDoiTuong;
	private Long capBanDatTraiThamQuyen;
	private Long capGCNQSDDSai;
	private Long chuyenNhuongChoThueKhongDungQuyDinh;
	private Long suDungDatKhongDungMucDich;
	private Long boHoangHoa;
	private Long viPhamKhac;
	private Long kienNghiThuHoiTien;
	private Long kienNghiThuHoiDat;
	private Long kienNghiThuHoiQDGiaoDat;
	private Long kienNghiKhacTien;
	private Long kienNghiKhacDat;
	private Long hanhChinhToChuc;
	private Long hanhChinhCaNhan;
	private Long chuyenCoQuanDieuTraVu;
	private Long chuyenCoQuanDieuTraDoiTuong;
	private Long daThuTien;
	private Long daThuDat;
	private Long daThuQdGiaoDat;
	private Long tongSoKLTTVaQDXLDaKTDD;
	private Long ketQuaKiemTraDonDocTienPhaiThu;
	private Long ketQuaKiemTraDonDocTienDaThu;
	private Long ketQuaKiemTraDonDocDatPhaiThu;
	private Long ketQuaKiemTraDonDocDatDaThu;
	private Long daXuLyHanhChinhToChuc;
	private Long daXuLyHanhChinhCaNhan;
	private Long daKhoiToVu;
	private Long daKhoiToDoiTuong;
	
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

	public Long getTongSoCuocThanhTra() {
		return tongSoCuocThanhTra;
	}

	public void setTongSoCuocThanhTra(Long tongSoCuocThanhTra) {
		this.tongSoCuocThanhTra = tongSoCuocThanhTra;
	}

	public Long getKyTruocChuyenSang() {
		return kyTruocChuyenSang;
	}

	public void setKyTruocChuyenSang(Long kyTruocChuyenSang) {
		this.kyTruocChuyenSang = kyTruocChuyenSang;
	}

	public Long getTrienKhaiTrongKyBaoCao() {
		return trienKhaiTrongKyBaoCao;
	}

	public void setTrienKhaiTrongKyBaoCao(Long trienKhaiTrongKyBaoCao) {
		this.trienKhaiTrongKyBaoCao = trienKhaiTrongKyBaoCao;
	}

	public Long getTheoKeHoach() {
		return theoKeHoach;
	}

	public void setTheoKeHoach(Long theoKeHoach) {
		this.theoKeHoach = theoKeHoach;
	}

	public Long getDotXuat() {
		return dotXuat;
	}

	public void setDotXuat(Long dotXuat) {
		this.dotXuat = dotXuat;
	}

	public Long getKetThucThanhTraTrucTiep() {
		return ketThucThanhTraTrucTiep;
	}

	public void setKetThucThanhTraTrucTiep(Long ketThucThanhTraTrucTiep) {
		this.ketThucThanhTraTrucTiep = ketThucThanhTraTrucTiep;
	}

	public Long getDaBanHanhKetLuan() {
		return daBanHanhKetLuan;
	}

	public void setDaBanHanhKetLuan(Long daBanHanhKetLuan) {
		this.daBanHanhKetLuan = daBanHanhKetLuan;
	}

	public Long getSoDonViDuocThanhTra() {
		return soDonViDuocThanhTra;
	}

	public void setSoDonViDuocThanhTra(Long soDonViDuocThanhTra) {
		this.soDonViDuocThanhTra = soDonViDuocThanhTra;
	}

	public Long getSoDonViCoViPham() {
		return soDonViCoViPham;
	}

	public void setSoDonViCoViPham(Long soDonViCoViPham) {
		this.soDonViCoViPham = soDonViCoViPham;
	}

	public Long getTongViPhamTien() {
		return tongViPhamTien;
	}

	public void setTongViPhamTien(Long tongViPhamTien) {
		this.tongViPhamTien = tongViPhamTien;
	}

	public Long getTongViPhamDat() {
		return tongViPhamDat;
	}

	public void setTongViPhamDat(Long tongViPhamDat) {
		this.tongViPhamDat = tongViPhamDat;
	}

	public Long getDatLanChiem() {
		return datLanChiem;
	}

	public void setDatLanChiem(Long datLanChiem) {
		this.datLanChiem = datLanChiem;
	}

	public Long getGiaoDatCapDatSaiDoiTuong() {
		return giaoDatCapDatSaiDoiTuong;
	}

	public void setGiaoDatCapDatSaiDoiTuong(Long giaoDatCapDatSaiDoiTuong) {
		this.giaoDatCapDatSaiDoiTuong = giaoDatCapDatSaiDoiTuong;
	}

	public Long getCapBanDatTraiThamQuyen() {
		return capBanDatTraiThamQuyen;
	}

	public void setCapBanDatTraiThamQuyen(Long capBanDatTraiThamQuyen) {
		this.capBanDatTraiThamQuyen = capBanDatTraiThamQuyen;
	}

	public Long getCapGCNQSDDSai() {
		return capGCNQSDDSai;
	}

	public void setCapGCNQSDDSai(Long capGCNQSDDSai) {
		this.capGCNQSDDSai = capGCNQSDDSai;
	}

	public Long getChuyenNhuongChoThueKhongDungQuyDinh() {
		return chuyenNhuongChoThueKhongDungQuyDinh;
	}

	public void setChuyenNhuongChoThueKhongDungQuyDinh(Long chuyenNhuongChoThueKhongDungQuyDinh) {
		this.chuyenNhuongChoThueKhongDungQuyDinh = chuyenNhuongChoThueKhongDungQuyDinh;
	}

	public Long getSuDungDatKhongDungMucDich() {
		return suDungDatKhongDungMucDich;
	}

	public void setSuDungDatKhongDungMucDich(Long suDungDatKhongDungMucDich) {
		this.suDungDatKhongDungMucDich = suDungDatKhongDungMucDich;
	}

	public Long getBoHoangHoa() {
		return boHoangHoa;
	}

	public void setBoHoangHoa(Long boHoangHoa) {
		this.boHoangHoa = boHoangHoa;
	}

	public Long getViPhamKhac() {
		return viPhamKhac;
	}

	public void setViPhamKhac(Long viPhamKhac) {
		this.viPhamKhac = viPhamKhac;
	}

	public Long getKienNghiThuHoiTien() {
		return kienNghiThuHoiTien;
	}

	public void setKienNghiThuHoiTien(Long kienNghiThuHoiTien) {
		this.kienNghiThuHoiTien = kienNghiThuHoiTien;
	}

	public Long getKienNghiThuHoiDat() {
		return kienNghiThuHoiDat;
	}

	public void setKienNghiThuHoiDat(Long kienNghiThuHoiDat) {
		this.kienNghiThuHoiDat = kienNghiThuHoiDat;
	}

	public Long getKienNghiThuHoiQDGiaoDat() {
		return kienNghiThuHoiQDGiaoDat;
	}

	public void setKienNghiThuHoiQDGiaoDat(Long kienNghiThuHoiQDGiaoDat) {
		this.kienNghiThuHoiQDGiaoDat = kienNghiThuHoiQDGiaoDat;
	}

	public Long getKienNghiKhacTien() {
		return kienNghiKhacTien;
	}

	public void setKienNghiKhacTien(Long kienNghiKhacTien) {
		this.kienNghiKhacTien = kienNghiKhacTien;
	}

	public Long getKienNghiKhacDat() {
		return kienNghiKhacDat;
	}

	public void setKienNghiKhacDat(Long kienNghiKhacDat) {
		this.kienNghiKhacDat = kienNghiKhacDat;
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

	public Long getDaThuTien() {
		return daThuTien;
	}

	public void setDaThuTien(Long daThuTien) {
		this.daThuTien = daThuTien;
	}

	public Long getDaThuDat() {
		return daThuDat;
	}

	public void setDaThuDat(Long daThuDat) {
		this.daThuDat = daThuDat;
	}

	public Long getDaThuQdGiaoDat() {
		return daThuQdGiaoDat;
	}

	public void setDaThuQdGiaoDat(Long daThuQdGiaoDat) {
		this.daThuQdGiaoDat = daThuQdGiaoDat;
	}

	public Long getTongSoKLTTVaQDXLDaKTDD() {
		return tongSoKLTTVaQDXLDaKTDD;
	}

	public void setTongSoKLTTVaQDXLDaKTDD(Long tongSoKLTTVaQDXLDaKTDD) {
		this.tongSoKLTTVaQDXLDaKTDD = tongSoKLTTVaQDXLDaKTDD;
	}

	public Long getKetQuaKiemTraDonDocTienPhaiThu() {
		return ketQuaKiemTraDonDocTienPhaiThu;
	}

	public void setKetQuaKiemTraDonDocTienPhaiThu(Long ketQuaKiemTraDonDocTienPhaiThu) {
		this.ketQuaKiemTraDonDocTienPhaiThu = ketQuaKiemTraDonDocTienPhaiThu;
	}

	public Long getKetQuaKiemTraDonDocTienDaThu() {
		return ketQuaKiemTraDonDocTienDaThu;
	}

	public void setKetQuaKiemTraDonDocTienDaThu(Long ketQuaKiemTraDonDocTienDaThu) {
		this.ketQuaKiemTraDonDocTienDaThu = ketQuaKiemTraDonDocTienDaThu;
	}

	public Long getKetQuaKiemTraDonDocDatPhaiThu() {
		return ketQuaKiemTraDonDocDatPhaiThu;
	}

	public void setKetQuaKiemTraDonDocDatPhaiThu(Long ketQuaKiemTraDonDocDatPhaiThu) {
		this.ketQuaKiemTraDonDocDatPhaiThu = ketQuaKiemTraDonDocDatPhaiThu;
	}

	public Long getKetQuaKiemTraDonDocDatDaThu() {
		return ketQuaKiemTraDonDocDatDaThu;
	}

	public void setKetQuaKiemTraDonDocDatDaThu(Long ketQuaKiemTraDonDocDatDaThu) {
		this.ketQuaKiemTraDonDocDatDaThu = ketQuaKiemTraDonDocDatDaThu;
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
	public Long getThanhTraLinhVucDatDaiId() {
		return getId();
	}
}
