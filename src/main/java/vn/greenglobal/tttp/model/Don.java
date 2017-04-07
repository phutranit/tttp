package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.katharsis.resource.annotations.JsonApiResource;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.NguonTiepNhanDonEnum;

@Entity
@Table(name = "don")
@JsonApiResource(type = "dons")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Don extends Model<Don> {

	private String ma = "";
	private String noiDUng = "";
	private String yeuCauCuaCongDan = "";

	private int soLanKhieuNaiToCao = 0;
	private int soNguoi;

	private LocalDateTime ngayTiepNhan;

	private Don donLanTruoc;

	private LoaiDonEnum loaiDon;
	private NguonTiepNhanDonEnum nguonTiepNhanDon;

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getNoiDUng() {
		return noiDUng;
	}

	public void setNoiDUng(String noiDUng) {
		this.noiDUng = noiDUng;
	}

	public String getYeuCauCuaCongDan() {
		return yeuCauCuaCongDan;
	}

	public void setYeuCauCuaCongDan(String yeuCauCuaCongDan) {
		this.yeuCauCuaCongDan = yeuCauCuaCongDan;
	}

	public int getSoLanKhieuNaiToCao() {
		return soLanKhieuNaiToCao;
	}

	public void setSoLanKhieuNaiToCao(int soLanKhieuNaiToCao) {
		this.soLanKhieuNaiToCao = soLanKhieuNaiToCao;
	}

	public int getSoNguoi() {
		return soNguoi;
	}

	public void setSoNguoi(int soNguoi) {
		this.soNguoi = soNguoi;
	}

	public LocalDateTime getNgayTiepNhan() {
		return ngayTiepNhan;
	}

	public void setNgayTiepNhan(LocalDateTime ngayTiepNhan) {
		this.ngayTiepNhan = ngayTiepNhan;
	}

	public Don getDonLanTruoc() {
		return donLanTruoc;
	}

	public void setDonLanTruoc(Don donLanTruoc) {
		this.donLanTruoc = donLanTruoc;
	}

	public LoaiDonEnum getLoaiDon() {
		return loaiDon;
	}

	public void setLoaiDon(LoaiDonEnum loaiDon) {
		this.loaiDon = loaiDon;
	}

	public NguonTiepNhanDonEnum getNguonTiepNhanDon() {
		return nguonTiepNhanDon;
	}

	public void setNguonTiepNhanDon(NguonTiepNhanDonEnum nguonTiepNhanDon) {
		this.nguonTiepNhanDon = nguonTiepNhanDon;
	}

}