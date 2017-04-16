package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "congchuc")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class CongChuc extends Model<CongChuc> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1368951945883561494L;
	
	private String ma = "";
	private String hoVaTen = "";
	private String soCMNDHoCHieu = "";
	private String noiCap = "";
	private String diaChi = "";
	private String dienThoai = "";
	private String email = "";

	private LocalDateTime ngaySinh;
	private LocalDateTime ngayCap;

	private boolean gioiTinh;

	@ManyToOne
	private CoQuanQuanLy donVi;

	@ManyToOne
	private ChucVu chucVu;

	@ManyToOne
	private CongChuc congChuc;
	
	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}

	public String getSoCMNDHoCHieu() {
		return soCMNDHoCHieu;
	}

	public void setSoCMNDHoCHieu(String soCMNDHoCHieu) {
		this.soCMNDHoCHieu = soCMNDHoCHieu;
	}

	public String getNoiCap() {
		return noiCap;
	}

	public void setNoiCap(String noiCap) {
		this.noiCap = noiCap;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getDienThoai() {
		return dienThoai;
	}

	public void setDienThoai(String dienThoai) {
		this.dienThoai = dienThoai;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public LocalDateTime getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(LocalDateTime ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public LocalDateTime getNgayCap() {
		return ngayCap;
	}

	public void setNgayCap(LocalDateTime ngayCap) {
		this.ngayCap = ngayCap;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}

	public ChucVu getChucVu() {
		return chucVu;
	}

	public void setChucVu(ChucVu chucVu) {
		this.chucVu = chucVu;
	}

	
	public CongChuc getCongChuc() {
		return congChuc;
	}

	public void setCongChuc(CongChuc congChuc) {
		this.congChuc = congChuc;
	}
}