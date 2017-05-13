package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.PhanLoaiDonCongDanEnum;

@Entity
@Table(name = "don_congdan")
public class Don_CongDan extends Model<Don_CongDan> {

	private static final long serialVersionUID = -7123036795988588832L;

	@NotNull
	@ManyToOne
	private Don don;
	@NotNull
	@ManyToOne
	private CongDan congDan;

	private String tenCoQuan = "";
	private String diaChiCoQuan = "";
	private String soDienThoai = "";

	// Người đứng đơn, ủy quyền, khiếu tố
	@NotNull
	@Enumerated(EnumType.STRING)
	private PhanLoaiDonCongDanEnum phanLoaiCongDan;

	private String soTheLuatSu = "";

	private boolean luatSu = false;

	private LocalDateTime ngayCapTheLuatSu;

	private String thongTinGioiThieu = "";
	private String noiCapTheLuatSu = "";
	private String donVi = "";
	private String chucVu = "";

	@ApiModelProperty(position = 3, required = true, example = "{}")
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	@ApiModelProperty(position = 4, required = true)
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
