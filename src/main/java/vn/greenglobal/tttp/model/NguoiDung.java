package vn.greenglobal.tttp.model;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;
import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.QuyenEnum;

@Entity
@Table(name = "nguoidung")
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

	@NotNull
	@ManyToOne
	private VaiTro vaiTroMacDinh;

	private boolean active;

	@ManyToMany
	@JoinTable(name = "nguoidung_vaitro", joinColumns = @JoinColumn(name = "nguoidung_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "vaitro_id", referencedColumnName = "id"))
	private Set<VaiTro> vaiTros;// = new HashSet<>(0);

	@ElementCollection(fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	@CollectionTable(name = "nguoidung_quyen", joinColumns = {
			@JoinColumn(name = "nguoidung_id", referencedColumnName = "id") })
	private Set<String> quyens = new HashSet<>();

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

	@ApiModelProperty(hidden = true)
	public Set<String> getQuyens() {
		return quyens;
	}

	public void setQuyens(final Set<String> dsChoPhep) {
		quyens = dsChoPhep;
	}

	@JsonIgnore
	public Set<String> getTatCaQuyens() {
		if (tatCaQuyens.isEmpty()) {
			tatCaQuyens.addAll(quyens);
			for (VaiTro vaiTro : vaiTros) {
				tatCaQuyens.add(vaiTro.getTen());
				for (String str : vaiTro.getQuyens()) {
					tatCaQuyens.addAll(Arrays.asList(str.split(",")));
				}
			}
		}
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
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		BasicPasswordEncryptor encryptor = new BasicPasswordEncryptor();
		String salkey = getSalkey();
		if (salkey == null || salkey.equals("")) {
			salkey = encryptor.encryptPassword((new Date()).toString());
		}
		String passHash = md5PasswordEncoder.encodePassword(pass, salkey);
		setSalkey(salkey);
		setMatKhau(passHash);
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getNguoiDungId() {
		return getId();
	}

	@ApiModelProperty(hidden = true)
	public Quyen getQuyen() {
		return quyen = new Quyen(new SimpleAccountRealm() {
			@Override
			protected AuthorizationInfo getAuthorizationInfo(final PrincipalCollection arg0) {
				final SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
				info.setStringPermissions(getTatCaQuyens());
				return info;
			}
		});
	}

	@Deprecated
	public boolean checkQuyen(String resource, String action) {
		return true;
	}

	public boolean checkQuyen(QuyenEnum quyen) {
		return getQuyen().getRealm().isPermitted(null, quyen.name().toLowerCase().replace('_', ':'));
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Set<VaiTro> getVaiTroNguoiDung() {
		return getVaiTros();
	}

	public boolean checkPassword(String password) {
		Md5PasswordEncoder md5PasswordEncoder = new Md5PasswordEncoder();
		String salkey = getSalkey();
		if (salkey == null || salkey.equals("")) {
			return false;
		}
		return md5PasswordEncoder.isPasswordValid(getMatKhau(), password, getSalkey());
	}

	@ApiModelProperty(example = "{}")
	public VaiTro getVaiTroMacDinh() {
		return vaiTroMacDinh;
	}

	public void setVaiTroMacDinh(VaiTro vaiTroMacDinh) {
		this.vaiTroMacDinh = vaiTroMacDinh;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public VaiTro getVaiTroMacDinhInfo() {
		return getVaiTroMacDinh();
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNguoiTaoInfo() {
		if (getNguoiTao() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getNguoiTao().getCoQuanQuanLy() != null ? getNguoiTao().getCoQuanQuanLy().getId() : 0);
			map.put("hoVaTen", getNguoiTao().getHoVaTen());
			map.put("congChucId", getNguoiTao().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNguoiSuaInfo() {
		if (getNguoiSua() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getNguoiSua().getCoQuanQuanLy() != null ? getNguoiSua().getCoQuanQuanLy().getId() : 0);
			map.put("hoVaTen", getNguoiSua().getHoVaTen());
			map.put("congChucId", getNguoiSua().getId());
			return map;
		}
		return null;
	}

}