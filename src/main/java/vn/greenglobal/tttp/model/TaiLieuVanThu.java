package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "tailieuvanthu")
@ApiModel
public class TaiLieuVanThu extends Model<TaiLieuVanThu> {
	private static final long serialVersionUID = -9223009647319074416L;

	private String ten = "";
	private String duongDan = "";
	private String tenFile = "";
	private String soQuyetDinh = "";
	private LocalDateTime ngayQuyetDinh;

	@ManyToOne
	private LoaiTaiLieu loaiTaiLieu;

	@ManyToOne
	private SoTiepCongDan soTiepCongDan;

	@ManyToOne
	private Don don;

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getDuongDan() {
		return duongDan;
	}

	public void setDuongDan(String duongDan) {
		this.duongDan = duongDan;
	}

	public String getTenFile() {
		return tenFile;
	}

	public void setTenFile(String tenFile) {
		this.tenFile = tenFile;
	}

	public String getSoQuyetDinh() {
		return soQuyetDinh;
	}

	public void setSoQuyetDinh(String soQuyetDinh) {
		this.soQuyetDinh = soQuyetDinh;
	}

	public LocalDateTime getNgayQuyetDinh() {
		return ngayQuyetDinh;
	}

	public void setNgayQuyetDinh(LocalDateTime ngayQuyetDinh) {
		this.ngayQuyetDinh = ngayQuyetDinh;
	}

	@ApiModelProperty(example = "{}")
	public LoaiTaiLieu getLoaiTaiLieu() {
		return loaiTaiLieu;
	}

	public void setLoaiTaiLieu(LoaiTaiLieu loaiTaiLieu) {
		this.loaiTaiLieu = loaiTaiLieu;
	}

	@ApiModelProperty(example = "{}")
	public SoTiepCongDan getSoTiepCongDan() {
		return soTiepCongDan;
	}

	public void setSoTiepCongDan(SoTiepCongDan soTiepCongDan) {
		this.soTiepCongDan = soTiepCongDan;
	}

	@ApiModelProperty(example = "{}")
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getTaiLieuVanThuId() {
		return getId();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public LoaiTaiLieu getLoaiTaiLieuTLVT() {
		return getLoaiTaiLieu();
	}
}