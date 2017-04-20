package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

@Entity
@Table(name = "congdan")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class CongDan extends Model<CongDan> {

	private static final long serialVersionUID = 2302822305956477280L;
	
	@NotBlank
	private String hoVaTen = "";
	private String soDienThoai = "";
	private String soCMNDHoChieu = "";
	private String noiCap = "";
	private String diaChi = "";

	@NotNull
	private LocalDateTime ngaySinh;
	private LocalDateTime ngayCap;

	private boolean gioiTinh;

	@ManyToOne
	private DonViHanhChinh tinhThanh;
	
	@ManyToOne
	private DonViHanhChinh quanHuyen;
	
	@ManyToOne
	private DonViHanhChinh phuongXa;
	
	@ManyToOne
	private ToDanPho toDanPho;
	
	@NotNull
	@ManyToOne
	private QuocTich quocTich;
	
	@NotNull
	@ManyToOne
	private DanToc danToc;
	
	@OneToMany(mappedBy = "congDan", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SUBSELECT)
	@OrderBy("ngayTao ASC")
	private List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>(); // TCD 
	
	public List<Don_CongDan> getDonCongDans() {
		
		return donCongDans;
	}

	public void setDonCongDans(List<Don_CongDan> donCongDans) {
		this.donCongDans = donCongDans;
	}

	@ApiModelProperty(position = 1, required = true)
	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}

	@ApiModelProperty(position = 2)
	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	@ApiModelProperty(position = 3)
	public String getSoCMNDHoChieu() {
		return soCMNDHoChieu;
	}

	public void setSoCMNDHoChieu(String soCMNDHoChieu) {
		this.soCMNDHoChieu = soCMNDHoChieu;
	}

	@ApiModelProperty(position = 4)
	public String getNoiCap() {
		return noiCap;
	}

	public void setNoiCap(String noiCap) {
		this.noiCap = noiCap;
	}

	@ApiModelProperty(position = 5)
	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	@ApiModelProperty(position = 6, required = true)
	public LocalDateTime getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(LocalDateTime ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	@ApiModelProperty(position = 7)
	public LocalDateTime getNgayCap() {
		return ngayCap;
	}

	public void setNgayCap(LocalDateTime ngayCap) {
		this.ngayCap = ngayCap;
	}

	@ApiModelProperty(position = 8)
	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
	}

	@ApiModelProperty(position = 9, example="{}")
	public DonViHanhChinh getTinhThanh() {
		return tinhThanh;
	}

	public void setTinhThanh(DonViHanhChinh tinhThanh) {
		this.tinhThanh = tinhThanh;
	}

	@ApiModelProperty(position = 10, example="{}")
	public DonViHanhChinh getQuanHuyen() {
		return quanHuyen;
	}

	public void setQuanHuyen(DonViHanhChinh quanHuyen) {
		this.quanHuyen = quanHuyen;
	}

	@ApiModelProperty(position = 11, example="{}")
	public DonViHanhChinh getPhuongXa() {
		return phuongXa;
	}

	public void setPhuongXa(DonViHanhChinh phuongXa) {
		this.phuongXa = phuongXa;
	}

	@ApiModelProperty(position = 12, example="{}")
	public ToDanPho getToDanPho() {
		return toDanPho;
	}

	public void setToDanPho(ToDanPho toDanPho) {
		this.toDanPho = toDanPho;
	}

	@ApiModelProperty(position = 13, example="{}")
	public QuocTich getQuocTich() {
		return quocTich;
	}

	public void setQuocTich(QuocTich quocTich) {
		this.quocTich = quocTich;
	}

	@ApiModelProperty(position = 14, example="{}")
	public DanToc getDanToc() {
		return danToc;
	}

	public void setDanToc(DanToc danToc) {
		this.danToc = danToc;
	}

	@Transient
	@ApiModelProperty(hidden=true)
	public Long getCongDanId() {
		return getId();
	}
}