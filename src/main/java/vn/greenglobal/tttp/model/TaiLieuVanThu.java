package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.katharsis.resource.annotations.JsonApiIncludeByDefault;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;

@Entity
@Table(name = "tailieuvanthu")
@JsonApiResource(type = "tailieuvanthus")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaiLieuVanThu extends Model<TaiLieuVanThu> {

	private String ten = "";
	private String duongDan = "";
	private String tenFile = "";
	private String soQuyetDinh = "";
	private String ngayQuyetDinh = "";

	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private LoaiTaiLieu loaiTaiLieu;
	
	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private SoTiepDan soTiepDan;
	
	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
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

	public String getNgayQuyetDinh() {
		return ngayQuyetDinh;
	}

	public void setNgayQuyetDinh(String ngayQuyetDinh) {
		this.ngayQuyetDinh = ngayQuyetDinh;
	}

	public LoaiTaiLieu getLoaiTaiLieu() {
		return loaiTaiLieu;
	}

	public void setLoaiTaiLieu(LoaiTaiLieu loaiTaiLieu) {
		this.loaiTaiLieu = loaiTaiLieu;
	}

	public SoTiepDan getSoTiepDan() {
		return soTiepDan;
	}

	public void setSoTiepDan(SoTiepDan soTiepDan) {
		this.soTiepDan = soTiepDan;
	}

	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}
	
}