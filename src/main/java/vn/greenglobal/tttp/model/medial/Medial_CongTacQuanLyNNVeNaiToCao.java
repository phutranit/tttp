package vn.greenglobal.tttp.model.medial;

import javax.persistence.Entity;
import javax.persistence.Table;
import vn.greenglobal.tttp.model.ModelThongKe;

@Entity
@Table(name = "medial_congtacquanlynhanuocvekhieunaitocao")
public class Medial_CongTacQuanLyNNVeNaiToCao extends ModelThongKe<Medial_CongTacQuanLyNNVeNaiToCao> {
	
	private int soThuTu;
	
	private Long soCuoc;
	private Long soDonVi;
	
	private String ghiChu = "";
	private String tenDonVi = "";
	
	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}
	
	public Long getSoCuoc() {
		return soCuoc;
	}

	public void setSoCuoc(Long soCuoc) {
		this.soCuoc = soCuoc;
	}

	public Long getSoDonVi() {
		return soDonVi;
	}

	public void setSoDonVi(Long soDonVi) {
		this.soDonVi = soDonVi;
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
