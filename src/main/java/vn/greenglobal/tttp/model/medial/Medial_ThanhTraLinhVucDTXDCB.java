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
@Table(name = "medial_thanhtralinhvucdtxdcb")
public class Medial_ThanhTraLinhVucDTXDCB extends Model<Medial_ThanhTraLinhVucDTXDCB> {

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
	private Long kienNghiThuHoiTien;
	private Long kienNghiThuHoiDat;
	private Long kienNghiKhacTien;
	private Long kienNghiKhacDat;
	private Long kienNghiXLHanhChinhToChuc;
	private Long kienNghiXLHanhChinhCaNhan;
	private Long kienNghiXLVu;
	private Long kienNghiXLDoiTuong;
	private Long daThuTien;
	private Long daThuDat;
	private Long tongSoKLTTVaQDXLDaKTDD;
	private Long ketQuaKiemTraDonDocTienPhaiThu;
	private Long ketQuaKiemTraDonDocTienDaThu;
	private Long ketQuaKiemTraDonDocDatPhaiThu;
	private Long ketQuaKiemTraDonDocDatDaThu;
	private Long ketQuaKiemTraDonDocDaXuLyHCToChuc;
	private Long ketQuaKiemTraDonDocDaXuLyHCCaNhan;
	private Long ketQuaKiemTraDonDocDaKhoiToVu;
	private Long ketQuaKiemTraDonDocDaKhoiToDoiTuong;
	
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

	public Long getKienNghiXLHanhChinhToChuc() {
		return kienNghiXLHanhChinhToChuc;
	}

	public void setKienNghiXLHanhChinhToChuc(Long kienNghiXLHanhChinhToChuc) {
		this.kienNghiXLHanhChinhToChuc = kienNghiXLHanhChinhToChuc;
	}

	public Long getKienNghiXLHanhChinhCaNhan() {
		return kienNghiXLHanhChinhCaNhan;
	}

	public void setKienNghiXLHanhChinhCaNhan(Long kienNghiXLHanhChinhCaNhan) {
		this.kienNghiXLHanhChinhCaNhan = kienNghiXLHanhChinhCaNhan;
	}

	public Long getKienNghiXLVu() {
		return kienNghiXLVu;
	}

	public void setKienNghiXLVu(Long kienNghiXLVu) {
		this.kienNghiXLVu = kienNghiXLVu;
	}

	public Long getKienNghiXLDoiTuong() {
		return kienNghiXLDoiTuong;
	}

	public void setKienNghiXLDoiTuong(Long kienNghiXLDoiTuong) {
		this.kienNghiXLDoiTuong = kienNghiXLDoiTuong;
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

	public Long getKetQuaKiemTraDonDocDaXuLyHCToChuc() {
		return ketQuaKiemTraDonDocDaXuLyHCToChuc;
	}

	public void setKetQuaKiemTraDonDocDaXuLyHCToChuc(Long ketQuaKiemTraDonDocDaXuLyHCToChuc) {
		this.ketQuaKiemTraDonDocDaXuLyHCToChuc = ketQuaKiemTraDonDocDaXuLyHCToChuc;
	}

	public Long getKetQuaKiemTraDonDocDaXuLyHCCaNhan() {
		return ketQuaKiemTraDonDocDaXuLyHCCaNhan;
	}

	public void setKetQuaKiemTraDonDocDaXuLyHCCaNhan(Long ketQuaKiemTraDonDocDaXuLyHCCaNhan) {
		this.ketQuaKiemTraDonDocDaXuLyHCCaNhan = ketQuaKiemTraDonDocDaXuLyHCCaNhan;
	}

	public Long getKetQuaKiemTraDonDocDaKhoiToVu() {
		return ketQuaKiemTraDonDocDaKhoiToVu;
	}

	public void setKetQuaKiemTraDonDocDaKhoiToVu(Long ketQuaKiemTraDonDocDaKhoiToVu) {
		this.ketQuaKiemTraDonDocDaKhoiToVu = ketQuaKiemTraDonDocDaKhoiToVu;
	}

	public Long getKetQuaKiemTraDonDocDaKhoiToDoiTuong() {
		return ketQuaKiemTraDonDocDaKhoiToDoiTuong;
	}

	public void setKetQuaKiemTraDonDocDaKhoiToDoiTuong(Long ketQuaKiemTraDonDocDaKhoiToDoiTuong) {
		this.ketQuaKiemTraDonDocDaKhoiToDoiTuong = ketQuaKiemTraDonDocDaKhoiToDoiTuong;
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
	public Long getThanhTraLinhVucDTXDCBId() {
		return getId();
	}
}
