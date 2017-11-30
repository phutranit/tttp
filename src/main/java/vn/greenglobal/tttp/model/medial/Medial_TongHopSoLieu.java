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
@Table(name = "medial_tonghopsolieu")
public class Medial_TongHopSoLieu extends Model<Medial_TongHopSoLieu> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private CoQuanQuanLy donVi;
	
	private int soThuTu;
	
	private Long soNguoiPhaiKeKhaiTrongNam;
	private Long soNguoiDaKeKhai;
	private Long soNguoiCongKhaiTheoHTNiemYet;
	private Long soNguoiCongKhaiTheoHTTCHoiHop;
	private Long soNguoiDuocXMTSThuNhap;
	private Long soNguoiDaCoKLVVKKHTSTNKhongTrungThuc;
	private Long soNguoiDaBiXLKLDoKKHTSTNKhongTrungThuc;
	private Long soNguoiDaBiXLKLDoChamTCKKHTHBCKQMBTaiSan;
	private Long soNguoiDaBiXLTNTrongXacMinhTaiSanThuNhap;
	
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

	public Long getSoNguoiPhaiKeKhaiTrongNam() {
		return soNguoiPhaiKeKhaiTrongNam;
	}

	public void setSoNguoiPhaiKeKhaiTrongNam(Long soNguoiPhaiKeKhaiTrongNam) {
		this.soNguoiPhaiKeKhaiTrongNam = soNguoiPhaiKeKhaiTrongNam;
	}

	public Long getSoNguoiDaKeKhai() {
		return soNguoiDaKeKhai;
	}

	public void setSoNguoiDaKeKhai(Long soNguoiDaKeKhai) {
		this.soNguoiDaKeKhai = soNguoiDaKeKhai;
	}

	public Long getSoNguoiCongKhaiTheoHTNiemYet() {
		return soNguoiCongKhaiTheoHTNiemYet;
	}

	public void setSoNguoiCongKhaiTheoHTNiemYet(Long soNguoiCongKhaiTheoHTNiemYet) {
		this.soNguoiCongKhaiTheoHTNiemYet = soNguoiCongKhaiTheoHTNiemYet;
	}

	public Long getSoNguoiCongKhaiTheoHTTCHoiHop() {
		return soNguoiCongKhaiTheoHTTCHoiHop;
	}

	public void setSoNguoiCongKhaiTheoHTTCHoiHop(Long soNguoiCongKhaiTheoHTTCHoiHop) {
		this.soNguoiCongKhaiTheoHTTCHoiHop = soNguoiCongKhaiTheoHTTCHoiHop;
	}

	public Long getSoNguoiDuocXMTSThuNhap() {
		return soNguoiDuocXMTSThuNhap;
	}

	public void setSoNguoiDuocXMTSThuNhap(Long soNguoiDuocXMTSThuNhap) {
		this.soNguoiDuocXMTSThuNhap = soNguoiDuocXMTSThuNhap;
	}

	public Long getSoNguoiDaCoKLVVKKHTSTNKhongTrungThuc() {
		return soNguoiDaCoKLVVKKHTSTNKhongTrungThuc;
	}

	public void setSoNguoiDaCoKLVVKKHTSTNKhongTrungThuc(Long soNguoiDaCoKLVVKKHTSTNKhongTrungThuc) {
		this.soNguoiDaCoKLVVKKHTSTNKhongTrungThuc = soNguoiDaCoKLVVKKHTSTNKhongTrungThuc;
	}

	public Long getSoNguoiDaBiXLKLDoKKHTSTNKhongTrungThuc() {
		return soNguoiDaBiXLKLDoKKHTSTNKhongTrungThuc;
	}

	public void setSoNguoiDaBiXLKLDoKKHTSTNKhongTrungThuc(Long soNguoiDaBiXLKLDoKKHTSTNKhongTrungThuc) {
		this.soNguoiDaBiXLKLDoKKHTSTNKhongTrungThuc = soNguoiDaBiXLKLDoKKHTSTNKhongTrungThuc;
	}

	public Long getSoNguoiDaBiXLKLDoChamTCKKHTHBCKQMBTaiSan() {
		return soNguoiDaBiXLKLDoChamTCKKHTHBCKQMBTaiSan;
	}

	public void setSoNguoiDaBiXLKLDoChamTCKKHTHBCKQMBTaiSan(Long soNguoiDaBiXLKLDoChamTCKKHTHBCKQMBTaiSan) {
		this.soNguoiDaBiXLKLDoChamTCKKHTHBCKQMBTaiSan = soNguoiDaBiXLKLDoChamTCKKHTHBCKQMBTaiSan;
	}

	public Long getSoNguoiDaBiXLTNTrongXacMinhTaiSanThuNhap() {
		return soNguoiDaBiXLTNTrongXacMinhTaiSanThuNhap;
	}

	public void setSoNguoiDaBiXLTNTrongXacMinhTaiSanThuNhap(Long soNguoiDaBiXLTNTrongXacMinhTaiSanThuNhap) {
		this.soNguoiDaBiXLTNTrongXacMinhTaiSanThuNhap = soNguoiDaBiXLTNTrongXacMinhTaiSanThuNhap;
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
	public Long getTongHopSoLieuId() {
		return getId();
	}
}
