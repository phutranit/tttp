package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "nguoidung")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class NguoiDung extends Model<NguoiDung> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6979954418350232111L;
	
	private String tenDangNhap = "";
	private String matKhau = "";
	private String hinhDaiDien = "";
	private String salkey = "";

	private boolean active;

	@ManyToOne
	private CongChuc congChuc;

	public String getTenDangNhap() {
		return tenDangNhap;
	}

	public void setTenDangNhap(String tenDangNhap) {
		this.tenDangNhap = tenDangNhap;
	}

	public String getMatKhau() {
		return matKhau;
	}

	public void setMatKhau(String matKhau) {
		this.matKhau = matKhau;
	}

	public String getHinhDaiDien() {
		return hinhDaiDien;
	}

	public void setHinhDaiDien(String hinhDaiDien) {
		this.hinhDaiDien = hinhDaiDien;
	}

	public String getSalkey() {
		return salkey;
	}

	public void setSalkey(String salkey) {
		this.salkey = salkey;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public CongChuc getCongChuc() {
		return congChuc;
	}

	public void setCongChuc(CongChuc congChuc) {
		this.congChuc = congChuc;
	}

}