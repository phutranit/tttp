package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.*;

@Entity
@Table(name = "don_congdan")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Don_CongDan extends Model<Don_CongDan> {

	/**
	 * 
	 */
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
	
	// Phân loại người đứng đơn và loại đối tượng
	@Enumerated(EnumType.STRING)
	private LoaiNguoiDungDonEnum phanLoai;
		
	// Người đứng đơn, ủy quyền, khiếu tố
	@Enumerated(EnumType.STRING)
	private PhanLoaiDonCongDanEnum phanLoaiCongDan;
	
	private String soTheLuatSu = "";

	private boolean luatSu = false;
	
	private LocalDateTime ngayCapTheLuatSu;

	private String noiCapTheLuatSu = "";
	private String donVi = "";
	private String chucVu = "";

	@ApiModelProperty(position = 1, required = true)
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	@ApiModelProperty(position = 2, required = true)
	public CongDan getCongDan() {
		return congDan;
	}

	public void setCongDan(CongDan congDan) {
		this.congDan = congDan;
	}

	public String getTenCoQuan() {
		return tenCoQuan;
	}

	@ApiModelProperty(position = 2, required = true)
	public LoaiNguoiDungDonEnum getPhanLoai() {
		return phanLoai;
	}

	public void setPhanLoai(LoaiNguoiDungDonEnum phanLoai) {
		this.phanLoai = phanLoai;
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

	public PhanLoaiDonCongDanEnum getPhanLoaiCongDan() {
		return phanLoaiCongDan;
	}

	public void setPhanLoaiCongDan(PhanLoaiDonCongDanEnum phanLoaiCongDan) {
		this.phanLoaiCongDan = phanLoaiCongDan;
	}

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

	public String getDonVi() {
		return donVi;
	}

	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	@Transient
	public CongDan getThongTinCongDan() {
		return getCongDan();
	}
}
