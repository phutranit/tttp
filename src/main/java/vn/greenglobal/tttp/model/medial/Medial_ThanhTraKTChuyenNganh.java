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
@Table(name = "medial_thanhtrakiemtrachuyennganh")
public class Medial_ThanhTraKTChuyenNganh extends Model<Medial_ThanhTraKTChuyenNganh> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private CoQuanQuanLy donVi;
	
	private int soThuTu;
	
	private Long soCuocTTKTTongSo;
	private Long thanhLapDoan;
	private Long thanhTraDocLap;
	private Long caNhanDuocThanhTra;
	private Long caNhanDuocKiemTra;
	private Long toChucDuocThanhTra;
	private Long toChucDuocKiemTra;
	private Long soCoViPhamTongSo;
	private Long soCoViPhamCaNhan;
	private Long soCoViPhanToChuc;
	private Long soQDXuPhatHanhChinhTongSo;
	private Long soQDXuPhatHanhChinhCaNhan;
	private Long soQDXuPhatHanhChinhToChuc;
	private Long soTienViPhamTongSo;
	private Long soTienViPhamCaNhan;
	private Long soTienViPhamToChuc;
	private Long soTienKienNghiThuHoi;
	private Long soTienXuLyTaiSanTongSo;
	private Long soTienXuLyTaiSanTichThu;
	private Long soTienXuLyTaiSanTieuHuy;
	private Long soTienXuLyViPhamTongSo;
	private Long soTienXuLyViPhamCaNhan;
	private Long soTienXuLyViPhamToChuc;
	private Long soTienDaThuTongSo;
	private Long soTienDaThuCaNhan;
	private Long soTienDaThuToChuc;
	
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

	public Long getSoCuocTTKTTongSo() {
		return soCuocTTKTTongSo;
	}

	public void setSoCuocTTKTTongSo(Long soCuocTTKTTongSo) {
		this.soCuocTTKTTongSo = soCuocTTKTTongSo;
	}

	public Long getThanhLapDoan() {
		return thanhLapDoan;
	}

	public void setThanhLapDoan(Long thanhLapDoan) {
		this.thanhLapDoan = thanhLapDoan;
	}

	public Long getThanhTraDocLap() {
		return thanhTraDocLap;
	}

	public void setThanhTraDocLap(Long thanhTraDocLap) {
		this.thanhTraDocLap = thanhTraDocLap;
	}

	public Long getCaNhanDuocThanhTra() {
		return caNhanDuocThanhTra;
	}

	public void setCaNhanDuocThanhTra(Long caNhanDuocThanhTra) {
		this.caNhanDuocThanhTra = caNhanDuocThanhTra;
	}

	public Long getCaNhanDuocKiemTra() {
		return caNhanDuocKiemTra;
	}

	public void setCaNhanDuocKiemTra(Long caNhanDuocKiemTra) {
		this.caNhanDuocKiemTra = caNhanDuocKiemTra;
	}

	public Long getToChucDuocThanhTra() {
		return toChucDuocThanhTra;
	}

	public void setToChucDuocThanhTra(Long toChucDuocThanhTra) {
		this.toChucDuocThanhTra = toChucDuocThanhTra;
	}

	public Long getToChucDuocKiemTra() {
		return toChucDuocKiemTra;
	}

	public void setToChucDuocKiemTra(Long toChucDuocKiemTra) {
		this.toChucDuocKiemTra = toChucDuocKiemTra;
	}

	public Long getSoCoViPhamTongSo() {
		return soCoViPhamTongSo;
	}

	public void setSoCoViPhamTongSo(Long soCoViPhamTongSo) {
		this.soCoViPhamTongSo = soCoViPhamTongSo;
	}

	public Long getSoCoViPhamCaNhan() {
		return soCoViPhamCaNhan;
	}

	public void setSoCoViPhamCaNhan(Long soCoViPhamCaNhan) {
		this.soCoViPhamCaNhan = soCoViPhamCaNhan;
	}

	public Long getSoCoViPhanToChuc() {
		return soCoViPhanToChuc;
	}

	public void setSoCoViPhanToChuc(Long soCoViPhanToChuc) {
		this.soCoViPhanToChuc = soCoViPhanToChuc;
	}

	public Long getSoQDXuPhatHanhChinhTongSo() {
		return soQDXuPhatHanhChinhTongSo;
	}

	public void setSoQDXuPhatHanhChinhTongSo(Long soQDXuPhatHanhChinhTongSo) {
		this.soQDXuPhatHanhChinhTongSo = soQDXuPhatHanhChinhTongSo;
	}

	public Long getSoQDXuPhatHanhChinhCaNhan() {
		return soQDXuPhatHanhChinhCaNhan;
	}

	public void setSoQDXuPhatHanhChinhCaNhan(Long soQDXuPhatHanhChinhCaNhan) {
		this.soQDXuPhatHanhChinhCaNhan = soQDXuPhatHanhChinhCaNhan;
	}

	public Long getSoQDXuPhatHanhChinhToChuc() {
		return soQDXuPhatHanhChinhToChuc;
	}

	public void setSoQDXuPhatHanhChinhToChuc(Long soQDXuPhatHanhChinhToChuc) {
		this.soQDXuPhatHanhChinhToChuc = soQDXuPhatHanhChinhToChuc;
	}

	public Long getSoTienViPhamTongSo() {
		return soTienViPhamTongSo;
	}

	public void setSoTienViPhamTongSo(Long soTienViPhamTongSo) {
		this.soTienViPhamTongSo = soTienViPhamTongSo;
	}

	public Long getSoTienViPhamCaNhan() {
		return soTienViPhamCaNhan;
	}

	public void setSoTienViPhamCaNhan(Long soTienViPhamCaNhan) {
		this.soTienViPhamCaNhan = soTienViPhamCaNhan;
	}

	public Long getSoTienViPhamToChuc() {
		return soTienViPhamToChuc;
	}

	public void setSoTienViPhamToChuc(Long soTienViPhamToChuc) {
		this.soTienViPhamToChuc = soTienViPhamToChuc;
	}

	public Long getSoTienKienNghiThuHoi() {
		return soTienKienNghiThuHoi;
	}

	public void setSoTienKienNghiThuHoi(Long soTienKienNghiThuHoi) {
		this.soTienKienNghiThuHoi = soTienKienNghiThuHoi;
	}

	public Long getSoTienXuLyTaiSanTongSo() {
		return soTienXuLyTaiSanTongSo;
	}

	public void setSoTienXuLyTaiSanTongSo(Long soTienXuLyTaiSanTongSo) {
		this.soTienXuLyTaiSanTongSo = soTienXuLyTaiSanTongSo;
	}

	public Long getSoTienXuLyTaiSanTichThu() {
		return soTienXuLyTaiSanTichThu;
	}

	public void setSoTienXuLyTaiSanTichThu(Long soTienXuLyTaiSanTichThu) {
		this.soTienXuLyTaiSanTichThu = soTienXuLyTaiSanTichThu;
	}

	public Long getSoTienXuLyTaiSanTieuHuy() {
		return soTienXuLyTaiSanTieuHuy;
	}

	public void setSoTienXuLyTaiSanTieuHuy(Long soTienXuLyTaiSanTieuHuy) {
		this.soTienXuLyTaiSanTieuHuy = soTienXuLyTaiSanTieuHuy;
	}

	public Long getSoTienXuLyViPhamTongSo() {
		return soTienXuLyViPhamTongSo;
	}

	public void setSoTienXuLyViPhamTongSo(Long soTienXuLyViPhamTongSo) {
		this.soTienXuLyViPhamTongSo = soTienXuLyViPhamTongSo;
	}

	public Long getSoTienXuLyViPhamCaNhan() {
		return soTienXuLyViPhamCaNhan;
	}

	public void setSoTienXuLyViPhamCaNhan(Long soTienXuLyViPhamCaNhan) {
		this.soTienXuLyViPhamCaNhan = soTienXuLyViPhamCaNhan;
	}

	public Long getSoTienXuLyViPhamToChuc() {
		return soTienXuLyViPhamToChuc;
	}

	public void setSoTienXuLyViPhamToChuc(Long soTienXuLyViPhamToChuc) {
		this.soTienXuLyViPhamToChuc = soTienXuLyViPhamToChuc;
	}

	public Long getSoTienDaThuTongSo() {
		return soTienDaThuTongSo;
	}

	public void setSoTienDaThuTongSo(Long soTienDaThuTongSo) {
		this.soTienDaThuTongSo = soTienDaThuTongSo;
	}

	public Long getSoTienDaThuCaNhan() {
		return soTienDaThuCaNhan;
	}

	public void setSoTienDaThuCaNhan(Long soTienDaThuCaNhan) {
		this.soTienDaThuCaNhan = soTienDaThuCaNhan;
	}

	public Long getSoTienDaThuToChuc() {
		return soTienDaThuToChuc;
	}

	public void setSoTienDaThuToChuc(Long soTienDaThuToChuc) {
		this.soTienDaThuToChuc = soTienDaThuToChuc;
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
	public Long getThanhTraKiemTraChuyenNganhId() {
		return getId();
	}
}
