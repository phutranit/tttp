package vn.greenglobal.tttp.model.medial;

import javax.persistence.Entity;
import javax.persistence.Table;
import vn.greenglobal.tttp.model.ModelThongKe;

@Entity
@Table(name = "medial_tonghopsolieu")
public class Medial_TongHopSoLieu extends ModelThongKe<Medial_TongHopSoLieu> {
	
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
	private String tenDonVi = "";
	private String ghiChu = "";
	
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

	public String getTenDonVi() {
		return tenDonVi;
	}

	public void setTenDonVi(String tenDonVi) {
		this.tenDonVi = tenDonVi;
	}
}
