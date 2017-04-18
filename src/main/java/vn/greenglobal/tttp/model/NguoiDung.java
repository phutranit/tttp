package vn.greenglobal.tttp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;
import org.jasypt.util.password.BasicPasswordEncryptor;

@Entity
@Table(name = "nguoidung")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class NguoiDung extends Model<NguoiDung> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6979954418350232111L;

	@NotBlank
	private String tenDangNhap = "";
	@NotBlank
	private String matKhau = "";
	private String hinhDaiDien = "";
	private String salkey = "";

	private boolean active;

	@ManyToMany
	@JoinTable(name = "nguoidung_vaitro", 
			joinColumns = @JoinColumn(name = "nguoidung_id", referencedColumnName = "id"), 
			inverseJoinColumns = @JoinColumn(name = "vaitro_id", referencedColumnName = "id")
			)
	private Set<VaiTro> vaiTros = new HashSet<>(0);;

	public NguoiDung() {
	}
	
	public NguoiDung(String tenDangNhap, String matKhau, boolean active) {
		super();
		this.tenDangNhap = tenDangNhap;
		this.matKhau = matKhau;
		this.active = active;
	}

	public NguoiDung(String tenDangNhap, String matKhau, boolean active, Set<VaiTro> vaiTros) {
		super();
		this.tenDangNhap = tenDangNhap;
		this.matKhau = matKhau;
		this.active = active;
		this.vaiTros = vaiTros;
	}

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

	public Set<VaiTro> getVaiTros() {
		return vaiTros;
	}

	public void setVaiTros(Set<VaiTro> vaiTros) {
		this.vaiTros = vaiTros;
	}

	public void updatePassword(String pass) {
		BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
		String salkey = getSalkey();
		if (salkey == null || salkey.equals("")) {
			salkey = encryptor.encryptPassword((new Date()).toString());
		}
		String passNoHash = pass + salkey;
		String passHash = encryptor.encryptPassword(passNoHash);
		setSalkey(salkey);
		setMatKhau(passHash);
	}
}