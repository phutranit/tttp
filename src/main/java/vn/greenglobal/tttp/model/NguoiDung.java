package vn.greenglobal.tttp.model;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.QuyenEnum;

@Entity
@Table(name = "nguoidung")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
@ApiModel
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
	@JoinTable(name = "nguoidung_vaitro", joinColumns = @JoinColumn(name = "nguoidung_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "vaitro_id", referencedColumnName = "id"))
	private Set<VaiTro> vaiTros;// = new HashSet<>(0);
	
	@ElementCollection(fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	@CollectionTable(name = "nguoidung_quyen", joinColumns = {@JoinColumn(name = "nguoidung_id", referencedColumnName = "id")})
	private Set<String> quyens;// = new HashSet<>(0);
	
	@Transient
	private Set<String> tatCaQuyens = new HashSet<>();
	
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

//	@JsonIgnore
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

	@ApiModelProperty(hidden = true)
	@JsonIgnore
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
	
	public Set<String> getQuyens() {
		return quyens;
	}
	
	public void setQuyens(final Set<String> dsChoPhep) {
		quyens = dsChoPhep;
	}
	
	public Set<String> getTatCaQuyens() {
		if (tatCaQuyens.isEmpty()) {
			tatCaQuyens.addAll(quyens);
			for (VaiTro vaiTro : vaiTros) {
				tatCaQuyens.add(vaiTro.getTen());
				tatCaQuyens.addAll(vaiTro.getQuyens());
			}
		}
		System.out.println(tatCaQuyens);
		return tatCaQuyens;
	}

	@Transient
	private Quyen quyen = new Quyen(new SimpleAccountRealm() {
		@Override
		protected AuthorizationInfo getAuthorizationInfo(final PrincipalCollection arg0) {
			final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
			info.setStringPermissions(getTatCaQuyens());
			return info;
		}
	});
	
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

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getNguoiDungId() {
		return getId();
	}

	public Quyen getQuyen() {
		return quyen;
	}
	
	public boolean checkQuyen(String resource, String action){
		
		return true;
	}
	
	public boolean checkQuyen(QuyenEnum quyen){
		return getQuyen().getRealm().isPermitted(null, quyen.name().toLowerCase().replace("_", ":"));
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Set<VaiTro> getVaiTroNguoiDung() {
		return getVaiTros();
	}
	
}