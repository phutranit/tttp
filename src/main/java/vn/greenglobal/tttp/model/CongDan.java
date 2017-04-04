package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.katharsis.resource.annotations.JsonApiIncludeByDefault;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;

@Entity
@Table(name = "congdan")
@JsonApiResource(type = "congdans")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class CongDan extends Model<CongDan> {

	private String hoVaTen = "";
	private String soDienThoai = "";
	private String soCMNDHoChieu = "";
	private String noiCap = "";
	private String diaChi = "";

	private LocalDateTime ngaySinh;
	private LocalDateTime ngayCap;

	private boolean gioiTinh;

	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private DonViHanhChinh tinhThanh;
	
	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private DonViHanhChinh quanHuyen;
	
	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private DonViHanhChinh phuongXa;
	
	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private ToDanPho toDanPho;
	
	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private QuocTich quocTich;
	
	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private DanToc danToc;

	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getSoCMNDHoChieu() {
		return soCMNDHoChieu;
	}

	public void setSoCMNDHoChieu(String soCMNDHoChieu) {
		this.soCMNDHoChieu = soCMNDHoChieu;
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

	public DonViHanhChinh getTinhThanh() {
		return tinhThanh;
	}

	public void setTinhThanh(DonViHanhChinh tinhThanh) {
		this.tinhThanh = tinhThanh;
	}

	public DonViHanhChinh getQuanHuyen() {
		return quanHuyen;
	}

	public void setQuanHuyen(DonViHanhChinh quanHuyen) {
		this.quanHuyen = quanHuyen;
	}

	public DonViHanhChinh getPhuongXa() {
		return phuongXa;
	}

	public void setPhuongXa(DonViHanhChinh phuongXa) {
		this.phuongXa = phuongXa;
	}

	public ToDanPho getToDanPho() {
		return toDanPho;
	}

	public void setToDanPho(ToDanPho toDanPho) {
		this.toDanPho = toDanPho;
	}

	public QuocTich getQuocTich() {
		return quocTich;
	}

	public void setQuocTich(QuocTich quocTich) {
		this.quocTich = quocTich;
	}

	public DanToc getDanToc() {
		return danToc;
	}

	public void setDanToc(DanToc danToc) {
		this.danToc = danToc;
	}

}