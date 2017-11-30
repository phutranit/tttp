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
@Table(name = "medial_thanhtralai")
public class Medial_ThanhTraLai extends Model<Medial_ThanhTraLai> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private CoQuanQuanLy donVi;
	
	private int soThuTu;
	
	private Long tongSoCuocThanhTraLai;
	private Long kyTruocChuyenSang;
	private Long trienKhaiTrongKyBaoCao;
	private Long ketThucThanhTraTrucTiep;
	private Long daBanHanhKetLuan;
	private Long viPhamTrinhTu;
	private Long ketLuanKhongPhuHop;
	private Long saiLamApDungPhapLuat;
	private Long coYLamSaiLechHoSo;
	private Long viPhamNghiemTrong;
	private Long soDonViDaThanhTraLai;
	private Long soDonViCoViPham;
	private Long viPhamVeKinhTeTien;
	private Long viPhamVeKinhTeDat;
	private Long kienNghiThuHoiTien;
	private Long kienNghiThuHoiDat;
	private Long kienNghiKhacTien;
	private Long kienNghiKhacDat;
	private Long hanhChinhToChuc;
	private Long hanhChinhCaNhan;
	private Long chuyenCoQuanDieuTraVu;
	private Long chuyenCoQuanDieuTraDoiTuong;
	private Long ketQuaThucHienTienDaThu;
	private Long ketQuaThucHienDatDaThu;
	private Long ketQuaThucHienToChuc;
	private Long ketQuaThucHienCaNhan;
	private Long ketQuaThucHienVu;
	private Long ketQuaThucHienDoiTuong;
	
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

	public Long getTongSoCuocThanhTraLai() {
		return tongSoCuocThanhTraLai;
	}

	public void setTongSoCuocThanhTraLai(Long tongSoCuocThanhTraLai) {
		this.tongSoCuocThanhTraLai = tongSoCuocThanhTraLai;
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

	public Long getViPhamTrinhTu() {
		return viPhamTrinhTu;
	}

	public void setViPhamTrinhTu(Long viPhamTrinhTu) {
		this.viPhamTrinhTu = viPhamTrinhTu;
	}

	public Long getKetLuanKhongPhuHop() {
		return ketLuanKhongPhuHop;
	}

	public void setKetLuanKhongPhuHop(Long ketLuanKhongPhuHop) {
		this.ketLuanKhongPhuHop = ketLuanKhongPhuHop;
	}

	public Long getSaiLamApDungPhapLuat() {
		return saiLamApDungPhapLuat;
	}

	public void setSaiLamApDungPhapLuat(Long saiLamApDungPhapLuat) {
		this.saiLamApDungPhapLuat = saiLamApDungPhapLuat;
	}

	public Long getCoYLamSaiLechHoSo() {
		return coYLamSaiLechHoSo;
	}

	public void setCoYLamSaiLechHoSo(Long coYLamSaiLechHoSo) {
		this.coYLamSaiLechHoSo = coYLamSaiLechHoSo;
	}

	public Long getViPhamNghiemTrong() {
		return viPhamNghiemTrong;
	}

	public void setViPhamNghiemTrong(Long viPhamNghiemTrong) {
		this.viPhamNghiemTrong = viPhamNghiemTrong;
	}

	public Long getSoDonViDaThanhTraLai() {
		return soDonViDaThanhTraLai;
	}

	public void setSoDonViDaThanhTraLai(Long soDonViDaThanhTraLai) {
		this.soDonViDaThanhTraLai = soDonViDaThanhTraLai;
	}

	public Long getSoDonViCoViPham() {
		return soDonViCoViPham;
	}

	public void setSoDonViCoViPham(Long soDonViCoViPham) {
		this.soDonViCoViPham = soDonViCoViPham;
	}

	public Long getViPhamVeKinhTeTien() {
		return viPhamVeKinhTeTien;
	}

	public void setViPhamVeKinhTeTien(Long viPhamVeKinhTeTien) {
		this.viPhamVeKinhTeTien = viPhamVeKinhTeTien;
	}

	public Long getViPhamVeKinhTeDat() {
		return viPhamVeKinhTeDat;
	}

	public void setViPhamVeKinhTeDat(Long viPhamVeKinhTeDat) {
		this.viPhamVeKinhTeDat = viPhamVeKinhTeDat;
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

	public Long getKetQuaThucHienTienDaThu() {
		return ketQuaThucHienTienDaThu;
	}

	public void setKetQuaThucHienTienDaThu(Long ketQuaThucHienTienDaThu) {
		this.ketQuaThucHienTienDaThu = ketQuaThucHienTienDaThu;
	}

	public Long getKetQuaThucHienDatDaThu() {
		return ketQuaThucHienDatDaThu;
	}

	public void setKetQuaThucHienDatDaThu(Long ketQuaThucHienDatDaThu) {
		this.ketQuaThucHienDatDaThu = ketQuaThucHienDatDaThu;
	}

	public Long getKetQuaThucHienToChuc() {
		return ketQuaThucHienToChuc;
	}

	public void setKetQuaThucHienToChuc(Long ketQuaThucHienToChuc) {
		this.ketQuaThucHienToChuc = ketQuaThucHienToChuc;
	}

	public Long getKetQuaThucHienCaNhan() {
		return ketQuaThucHienCaNhan;
	}

	public void setKetQuaThucHienCaNhan(Long ketQuaThucHienCaNhan) {
		this.ketQuaThucHienCaNhan = ketQuaThucHienCaNhan;
	}

	public Long getKetQuaThucHienVu() {
		return ketQuaThucHienVu;
	}

	public void setKetQuaThucHienVu(Long ketQuaThucHienVu) {
		this.ketQuaThucHienVu = ketQuaThucHienVu;
	}

	public Long getKetQuaThucHienDoiTuong() {
		return ketQuaThucHienDoiTuong;
	}

	public void setKetQuaThucHienDoiTuong(Long ketQuaThucHienDoiTuong) {
		this.ketQuaThucHienDoiTuong = ketQuaThucHienDoiTuong;
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
	public Long getThanhTraLaiId() {
		return getId();
	}
}
