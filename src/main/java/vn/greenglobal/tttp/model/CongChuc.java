package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "congchuc")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class CongChuc extends Model<CongChuc> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1368951945883561494L;
	
	private String ma = "";
	@NotBlank
	private String hoVaTen = "";
	private String soCMNDHoCHieu = "";
	private String noiCap = "";
	private String diaChi = "";
	private String dienThoai = "";
	@NotBlank
	private String email = "";
	@NotNull
	private LocalDateTime ngaySinh;
	private LocalDateTime ngayCap;

	private boolean gioiTinh;

	@NotNull
	@ManyToOne
	private CoQuanQuanLy donVi;

	@NotNull
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

	@ApiModelProperty(position = 3)
	public String getNoiCap() {
		return noiCap;
	}

	public void setNoiCap(String noiCap) {
		this.noiCap = noiCap;
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

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+0000")
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

	@ApiModelProperty(position = 10, required = true)
	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}

	@ApiModelProperty(position = 11)
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
}