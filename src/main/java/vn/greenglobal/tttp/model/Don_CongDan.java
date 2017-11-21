package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonIgnore;

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
	@ManyToOne(fetch = FetchType.LAZY)
	@Fetch(FetchMode.JOIN)
	private Don don;
//	@Fetch(FetchMode.SELECT)
	@ManyToOne(fetch = FetchType.LAZY)
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
	private String hoVaTenSearch = "";
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
	@Size(max=255)
	private String toThon = "";
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
	
//	@Fetch(FetchMode.SELECT)
	@ManyToOne(fetch = FetchType.LAZY)
	private CoQuanQuanLy noiCapCMND;
//	@Fetch(FetchMode.SELECT)
	@ManyToOne(fetch = FetchType.LAZY)
	private DonViHanhChinh tinhThanh;
//	@Fetch(FetchMode.SELECT)
	@ManyToOne(fetch = FetchType.LAZY)
	private DonViHanhChinh quanHuyen;
//	@Fetch(FetchMode.SELECT)
	@ManyToOne(fetch = FetchType.LAZY)
	private DonViHanhChinh phuongXa;
//	@Fetch(FetchMode.SELECT)
	@ManyToOne(fetch = FetchType.LAZY)
	private ToDanPho toDanPho;
//	@Fetch(FetchMode.SELECT)
	@ManyToOne(fetch = FetchType.LAZY)
	private QuocTich quocTich;
//	@Fetch(FetchMode.SELECT)
	@ManyToOne(fetch = FetchType.LAZY)
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

	public String getToThon() {
		return toThon;
	}

	public void setToThon(String toThon) {
		this.toThon = toThon;
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

	@JsonIgnore
	public String getHoVaTenSearch() {
		return hoVaTenSearch;
	}

	public void setHoVaTenSearch(String hoVaTenSearch) {
		this.hoVaTenSearch = hoVaTenSearch;
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
	public Long getCongDanId() {
		if (getCongDan() != null) { 
			return getCongDan().getId();
		}
		return null;
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
	public Map<String, Object> getQuocTichCongDan() {
		if (getQuocTich() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("quocTichId", getQuocTich().getId());
			map.put("ten", getQuocTich().getTen());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getDanTocCongDan() {
		if (getDanToc() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("danTocId", getDanToc().getId());
			map.put("ten", getDanToc().getTen());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getToDanPhoCongDan() {
		if (getToDanPho() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("toDanPhoId", getToDanPho().getId());
			map.put("ten", getToDanPho().getTen());
			return map;
		}
		return null;

	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getQuanHuyenCongDan() {
		if (getQuanHuyen() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("donViHanhChinhId", getQuanHuyen().getId());
			map.put("ten", getQuanHuyen().getTen());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getPhuongXaCongDan() {
		if (getPhuongXa() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("donViHanhChinhId", getPhuongXa().getId());
			map.put("ten", getPhuongXa().getTen());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getTinhThanhCongDan() {
		if (getTinhThanh() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("donViHanhChinhId", getTinhThanh().getId());
			map.put("ten", getTinhThanh().getTen());
			return map;
		}
		return null;
	}
}
