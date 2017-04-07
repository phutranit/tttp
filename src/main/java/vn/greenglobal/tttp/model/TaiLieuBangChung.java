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
@Table(name = "tailieubangchung")
@JsonApiResource(type = "tailieubangchungs")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class TaiLieuBangChung extends Model<TaiLieuBangChung> {

	private String ten = "";
	private String tinhTrangTaiLieu = "";
	private String duongDan = "";
	private String tenFile = "";

	private int soTrang = 0;

	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private LoaiTaiLieu loaiTaiLieu;
	
	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private SoTiepCongDan soTiepCongDan;
	
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

	public LoaiTaiLieu getLoaiTaiLieu() {
		return loaiTaiLieu;
	}

	public void setLoaiTaiLieu(LoaiTaiLieu loaiTaiLieu) {
		this.loaiTaiLieu = loaiTaiLieu;
	}

	public SoTiepCongDan getSoTiepCongDan() {
		return soTiepCongDan;
	}

	public void setSoTiepCongDan(SoTiepCongDan soTiepCongDan) {
		this.soTiepCongDan = soTiepCongDan;
	}

	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

}