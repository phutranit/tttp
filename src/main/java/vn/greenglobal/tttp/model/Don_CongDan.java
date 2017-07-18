package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.PhanLoaiDonCongDanEnum;

@Entity
@Table(name = "don_congdan", indexes = {@Index(columnList = "phanLoaiCongDan, don_id, daXoa", name = "IndexPLDDX"),
		@Index(columnList = "phanLoaiCongDan, don_id, congDan_id", name = "IndexPLDCD"),
		@Index(columnList = "don_id, daXoa", name = "IndexDDX"), @Index(columnList = "id, daXoa", name = "IndexIDDX"),
		@Index(columnList = "congDan_id, daXoa", name = "IndexCDDX"), @Index(columnList = "daXoa", name = "IndexDX")})
public class Don_CongDan extends Model<Don_CongDan> {

	private static final long serialVersionUID = -7123036795988588832L;

	@NotNull
	@ManyToOne
	private Don don;
	@NotNull
	@ManyToOne
	private CongDan congDan;

	@Size(max=255)
	private String tenCoQuan = "";
	@Size(max=255)
	private String diaChiCoQuan = "";
	@Size(max=255)
	private String soDienThoai = "";
	@Size(max=255)
	private String hoVaTen = "";
	@Size(max=255)
	private String soCMNDHoChieu = "";
	@Size(max=255)
	private String diaChi = "";
	@Size(max=255)
	private String soDienThoaiCoQuan = "";

	// Người đứng đơn, ủy quyền, khiếu tố
	@NotNull
	@Enumerated(EnumType.STRING)
	private PhanLoaiDonCongDanEnum phanLoaiCongDan;

	@Size(max=255)
	private String soTheLuatSu = "";
	private boolean gioiTinh;
	private boolean luatSu = false;

	private LocalDateTime ngayCapTheLuatSu;
	private LocalDateTime ngaySinh;
	private LocalDateTime ngayCap;

	@Size(max=255)
	private String thongTinGioiThieu = "";
	@Size(max=255)
	private String noiCapTheLuatSu = "";
	@Size(max=255)
	private String donVi = "";
	@Size(max=255)
	private String chucVu = "";
	
	@ManyToOne
	private CoQuanQuanLy noiCapCMND;
	
	@ManyToOne
	private DonViHanhChinh tinhThanh;
	@ManyToOne
	private DonViHanhChinh quanHuyen;
	@ManyToOne
	private DonViHanhChinh phuongXa;

	@ManyToOne
	private ToDanPho toDanPho;

	@ManyToOne
	private QuocTich quocTich;

	@ManyToOne
	private DanToc danToc;

	@ApiModelProperty(position = 3, required = true, example = "{}")
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	@ApiModelProperty(position = 4, required = true, example = "{}")
	public CongDan getCongDan() {
		return congDan;
	}

	public void setCongDan(CongDan congDan) {
		this.congDan = congDan;
	}

	public String getTenCoQuan() {
		return tenCoQuan;
	}

	public void setTenCoQuan(String tenCoQuan) {
		this.tenCoQuan = tenCoQuan;
	}

	public String getDiaChiCoQuan() {
		return diaChiCoQuan;
	}

	public void setDiaChiCoQuan(String diaChiCoQuan) {
		this.diaChiCoQuan = diaChiCoQuan;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	@ApiModelProperty(position = 2, required = true)
	public PhanLoaiDonCongDanEnum getPhanLoaiCongDan() {
		return phanLoaiCongDan;
	}

	public void setPhanLoaiCongDan(PhanLoaiDonCongDanEnum phanLoaiCongDan) {
		this.phanLoaiCongDan = phanLoaiCongDan;
	}

	@ApiModelProperty(position = 6)
	public boolean isLuatSu() {
		return luatSu;
	}

	public void setLuatSu(boolean luatSu) {
		this.luatSu = luatSu;
	}

	public String getSoTheLuatSu() {
		return soTheLuatSu;
	}

	public void setSoTheLuatSu(String soTheLuatSu) {
		this.soTheLuatSu = soTheLuatSu;
	}

	public LocalDateTime getNgayCapTheLuatSu() {
		return ngayCapTheLuatSu;
	}

	public void setNgayCapTheLuatSu(LocalDateTime ngayCapTheLuatSu) {
		this.ngayCapTheLuatSu = ngayCapTheLuatSu;
	}

	public String getNoiCapTheLuatSu() {
		return noiCapTheLuatSu;
	}

	public void setNoiCapTheLuatSu(String noiCapTheLuatSu) {
		this.noiCapTheLuatSu = noiCapTheLuatSu;
	}

	public String getThongTinGioiThieu() {
		return thongTinGioiThieu;
	}

	public void setThongTinGioiThieu(String thongTinGioiThieu) {
		this.thongTinGioiThieu = thongTinGioiThieu;
	}

	public String getDonVi() {
		return donVi;
	}

	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}

	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}

	public String getSoCMNDHoChieu() {
		return soCMNDHoChieu;
	}

	public void setSoCMNDHoChieu(String soCMNDHoChieu) {
		this.soCMNDHoChieu = soCMNDHoChieu;
	}

	public boolean isGioiTinh() {
		return gioiTinh;
	}

	public void setGioiTinh(boolean gioiTinh) {
		this.gioiTinh = gioiTinh;
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

	@ApiModelProperty(position = 3, example = "{}")
	public CoQuanQuanLy getNoiCapCMND() {
		return noiCapCMND;
	}

	public void setNoiCapCMND(CoQuanQuanLy noiCapCMND) {
		this.noiCapCMND = noiCapCMND;
	}

	@ApiModelProperty(position = 3, example = "{}")
	public DonViHanhChinh getTinhThanh() {
		return tinhThanh;
	}

	public void setTinhThanh(DonViHanhChinh tinhThanh) {
		this.tinhThanh = tinhThanh;
	}

	@ApiModelProperty(position = 3, example = "{}")
	public DonViHanhChinh getQuanHuyen() {
		return quanHuyen;
	}

	public void setQuanHuyen(DonViHanhChinh quanHuyen) {
		this.quanHuyen = quanHuyen;
	}

	@ApiModelProperty(position = 3, example = "{}")
	public DonViHanhChinh getPhuongXa() {
		return phuongXa;
	}

	public void setPhuongXa(DonViHanhChinh phuongXa) {
		this.phuongXa = phuongXa;
	}

	@ApiModelProperty(position = 3, example = "{}")
	public ToDanPho getToDanPho() {
		return toDanPho;
	}

	public void setToDanPho(ToDanPho toDanPho) {
		this.toDanPho = toDanPho;
	}

	@ApiModelProperty(position = 3, example = "{}")
	public QuocTich getQuocTich() {
		return quocTich;
	}

	public void setQuocTich(QuocTich quocTich) {
		this.quocTich = quocTich;
	}

	@ApiModelProperty(position = 3, example = "{}")
	public DanToc getDanToc() {
		return danToc;
	}

	public void setDanToc(DanToc danToc) {
		this.danToc = danToc;
	}
	
	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	public String getSoDienThoaiCoQuan() {
		return soDienThoaiCoQuan;
	}

	public void setSoDienThoaiCoQuan(String soDienThoaiCoQuan) {
		this.soDienThoaiCoQuan = soDienThoaiCoQuan;
	}

	@ApiModelProperty(position = 5)
	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public CongDan getThongTinCongDan() {
		return getCongDan();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getDonId() {
		return getDon().getId();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getDonCongDanId() {
		return getId();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public LocalDateTime getNgayTiepNhan() {
		if (getDon() != null) {
			return getDon().getNgayTiepNhan();
		}
		return null;
	}
}
