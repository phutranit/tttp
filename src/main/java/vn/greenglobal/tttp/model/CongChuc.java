package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "congchuc")
@ApiModel
public class CongChuc extends Model<CongChuc> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1368951945883561494L;

	private String ma = "";
	@NotBlank
	private String hoVaTen = "";
	private String soCMNDHoCHieu = "";
	private String diaChi = "";
	private String dienThoai = "";
	@NotBlank
	private String email = "";

	private LocalDateTime ngaySinh;
	private LocalDateTime ngayCap;

	private boolean gioiTinh;

	@ManyToOne
	private CoQuanQuanLy coQuanQuanLy;
	
	@ManyToOne
	private CoQuanQuanLy noiCapCMND;

	@ManyToOne
	private ChucVu chucVu;

	@NotNull
	@ManyToOne
	private NguoiDung nguoiDung;

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	@ApiModelProperty(position = 1, required = true)
	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}

	@ApiModelProperty(position = 2)
	public String getSoCMNDHoCHieu() {
		return soCMNDHoCHieu;
	}

	public void setSoCMNDHoCHieu(String soCMNDHoCHieu) {
		this.soCMNDHoCHieu = soCMNDHoCHieu;
	}

	@ApiModelProperty(position = 3, required = true, example = "{}")
	public CoQuanQuanLy getNoiCapCMND() {
		return noiCapCMND;
	}

	public void setNoiCapCMND(CoQuanQuanLy noiCapCMND) {
		this.noiCapCMND = noiCapCMND;
	}

	@ApiModelProperty(position = 4)
	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	@ApiModelProperty(position = 5)
	public String getDienThoai() {
		return dienThoai;
	}

	public void setDienThoai(String dienThoai) {
		this.dienThoai = dienThoai;
	}

	@ApiModelProperty(position = 6, required = true)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ApiModelProperty(position = 7, required = true)
	public LocalDateTime getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(LocalDateTime ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	@ApiModelProperty(position = 8)
	public LocalDateTime getNgayCap() {
		return ngayCap;
	}

	public void setNgayCap(LocalDateTime ngayCap) {
		this.ngayCap = ngayCap;
	}

	@ApiModelProperty(position = 9)
	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	@ApiModelProperty(position = 10, required = true, example = "{}")
	public CoQuanQuanLy getCoQuanQuanLy() {
		return coQuanQuanLy;
	}

	public void setCoQuanQuanLy(CoQuanQuanLy coQuanQuanLy) {
		this.coQuanQuanLy = coQuanQuanLy;
	}

	@ApiModelProperty(position = 11, example = "{}")
	public ChucVu getChucVu() {
		return chucVu;
	}

	public void setChucVu(ChucVu chucVu) {
		this.chucVu = chucVu;
	}

	@ApiModelProperty(position = 12, required = true)
	public NguoiDung getNguoiDung() {
		return nguoiDung;
	}

	public void setNguoiDung(NguoiDung nguoiDung) {
		this.nguoiDung = nguoiDung;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getCongChucId() {
		return getId();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public NguoiDung getNguoiDungCongChuc() {
		return getNguoiDung();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getChucVuCongChuc() {
		if (getChucVu() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("chucVuId", getChucVu().getId());
			map.put("ten", getChucVu().getTen());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getDonViCongChuc() {
		if (getCoQuanQuanLy() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getCoQuanQuanLy().getId());
			map.put("ten", getCoQuanQuanLy().getTen());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNoiCapCMNDInfo() {
		if (getNoiCapCMND() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getNoiCapCMND().getId());
			map.put("ten", getNoiCapCMND().getTen());
			return map;
		}
		return null;
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