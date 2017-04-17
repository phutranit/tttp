package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "tailieuvanthu")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaiLieuVanThu extends Model<TaiLieuVanThu> {
	private static final long serialVersionUID = -9223009647319074416L;
	
	private String ten = "";
	private String duongDan = "";
	private String tenFile = "";
	private String soQuyetDinh = "";
	private String ngayQuyetDinh = "";

	@ManyToOne
	private LoaiTaiLieu loaiTaiLieu;
	
	@ManyToOne
	private SoTiepCongDan soTiepCongDan;
	
	@ManyToOne
	private Don don;

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getDuongDan() {
		return duongDan;
	}

	public void setDuongDan(String duongDan) {
		this.duongDan = duongDan;
	}

	public String getTenFile() {
		return tenFile;
	}

	public void setTenFile(String tenFile) {
		this.tenFile = tenFile;
	}

	public String getSoQuyetDinh() {
		return soQuyetDinh;
	}

	public void setSoQuyetDinh(String soQuyetDinh) {
		this.soQuyetDinh = soQuyetDinh;
	}

	public String getNgayQuyetDinh() {
		return ngayQuyetDinh;
	}

	public void setNgayQuyetDinh(String ngayQuyetDinh) {
		this.ngayQuyetDinh = ngayQuyetDinh;
	}

	public LoaiTaiLieu getLoaiTaiLieu() {
		return loaiTaiLieu;
	}

	public void setLoaiTaiLieu(LoaiTaiLieu loaiTaiLieu) {
		this.loaiTaiLieu = loaiTaiLieu;
	}

	public SoTiepCongDan getSoTiepCongDan() {
		return soTiepCongDan;
	}

	public void setSoTiepCongDan(SoTiepCongDan soTiepCongDan) {
		this.soTiepCongDan = soTiepCongDan;
	}

	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}
	
}