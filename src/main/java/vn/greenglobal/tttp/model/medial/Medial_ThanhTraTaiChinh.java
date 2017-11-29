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
@Table(name = "medial_thanhtrahanhchinh")
public class Medial_ThanhTraTaiChinh extends Model<Medial_ThanhTraTaiChinh> {

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
	private Long soTienViPham;
	private Long soTienKienNghiThuHoi;
	private Long kienNghiKhac;
	private Long kienNghiXLHanhChinhToChuc;
	private Long kienNghiXLHanhChinhCaNhan;
	private Long kienNghiXuLyVu;
	private Long kienNghiXuLyDoiTuong;
	private Long soTienDaThu;
	private Long tongSoKLTTVaQDXLDaKTDD;
	private Long kiemTraDonDocTienPhaiThu;
	private Long kiemTraDonDocTienDaThu;
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

	public Long getSoTienViPham() {
		return soTienViPham;
	}

	public void setSoTienViPham(Long soTienViPham) {
		this.soTienViPham = soTienViPham;
	}

	public Long getSoTienKienNghiThuHoi() {
		return soTienKienNghiThuHoi;
	}

	public void setSoTienKienNghiThuHoi(Long soTienKienNghiThuHoi) {
		this.soTienKienNghiThuHoi = soTienKienNghiThuHoi;
	}

	public Long getKienNghiKhac() {
		return kienNghiKhac;
	}

	public void setKienNghiKhac(Long kienNghiKhac) {
		this.kienNghiKhac = kienNghiKhac;
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

	public Long getKienNghiXuLyVu() {
		return kienNghiXuLyVu;
	}

	public void setKienNghiXuLyVu(Long kienNghiXuLyVu) {
		this.kienNghiXuLyVu = kienNghiXuLyVu;
	}

	public Long getKienNghiXuLyDoiTuong() {
		return kienNghiXuLyDoiTuong;
	}

	public void setKienNghiXuLyDoiTuong(Long kienNghiXuLyDoiTuong) {
		this.kienNghiXuLyDoiTuong = kienNghiXuLyDoiTuong;
	}

	public Long getSoTienDaThu() {
		return soTienDaThu;
	}

	public void setSoTienDaThu(Long soTienDaThu) {
		this.soTienDaThu = soTienDaThu;
	}

	public Long getTongSoKLTTVaQDXLDaKTDD() {
		return tongSoKLTTVaQDXLDaKTDD;
	}

	public void setTongSoKLTTVaQDXLDaKTDD(Long tongSoKLTTVaQDXLDaKTDD) {
		this.tongSoKLTTVaQDXLDaKTDD = tongSoKLTTVaQDXLDaKTDD;
	}

	public Long getKiemTraDonDocTienPhaiThu() {
		return kiemTraDonDocTienPhaiThu;
	}

	public void setKiemTraDonDocTienPhaiThu(Long kiemTraDonDocTienPhaiThu) {
		this.kiemTraDonDocTienPhaiThu = kiemTraDonDocTienPhaiThu;
	}

	public Long getKiemTraDonDocTienDaThu() {
		return kiemTraDonDocTienDaThu;
	}

	public void setKiemTraDonDocTienDaThu(Long kiemTraDonDocTienDaThu) {
		this.kiemTraDonDocTienDaThu = kiemTraDonDocTienDaThu;
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
	public Long getThanhTraHanhChinhId() {
		return getId();
	}
}
