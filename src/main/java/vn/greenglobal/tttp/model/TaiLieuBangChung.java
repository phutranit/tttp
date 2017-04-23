package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "tailieubangchung")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
@ApiModel
public class TaiLieuBangChung extends Model<TaiLieuBangChung> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7863478663069074533L;
	@NotBlank
	private String ten = "";
	private String tinhTrangTaiLieu = "";
	private String duongDan = "";
	private String tenFile = "";

	private int soTrang = 0;

	@NotNull
	@ManyToOne
	private LoaiTaiLieu loaiTaiLieu;

	@ManyToOne
	private SoTiepCongDan soTiepCongDan;

	@NotNull
	@ManyToOne
	private Don don;

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getTinhTrangTaiLieu() {
		return tinhTrangTaiLieu;
	}

	public void setTinhTrangTaiLieu(String tinhTrangTaiLieu) {
		this.tinhTrangTaiLieu = tinhTrangTaiLieu;
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

	public int getSoTrang() {
		return soTrang;
	}

	public void setSoTrang(int soTrang) {
		this.soTrang = soTrang;
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
	public Long getTaiLieuBangChungId() {
		return getId();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public LoaiTaiLieu getLoaiTaiLieuTLBC() {
		return getLoaiTaiLieu();
	}
}