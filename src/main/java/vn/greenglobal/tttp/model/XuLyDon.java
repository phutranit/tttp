package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import vn.greenglobal.tttp.enums.*;

@Entity
@Table(name = "xulydon")
@ApiModel
public class XuLyDon extends Model<XuLyDon> {
	private static final long serialVersionUID = -8406016838422350892L;

	@ManyToOne
	private Don don;
	@ManyToOne
	private CongChuc congChuc;
	@ManyToOne
	private ThamQuyenGiaiQuyet thamQuyenGiaiQuyet;
	@ManyToOne
	private CoQuanQuanLy phongBanGiaiQuyet;

	private int thuTuThucHien = 0;

	private String ghiChu = "";
	private String yKienXuLy = "";
	private String moTaTrangThai = "";
	private String noiDungYeuCauXuLy = "";
	private String noiDungThongTinTrinhLanhDao = "";

	@Enumerated(EnumType.STRING)
	private QuyTrinhXuLyDonEnum quyTrinhXuLy;
	@Enumerated(EnumType.STRING)
	private HuongXuLyXLDEnum huongXuLy;
	
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	public CongChuc getCongChuc() {
		return congChuc;
	}

	public void setCongChuc(CongChuc congChuc) {
		this.congChuc = congChuc;
	}

	public ThamQuyenGiaiQuyet getThamQuyenGiaiQuyet() {
		return thamQuyenGiaiQuyet;
	}

	public void setThamQuyenGiaiQuyet(ThamQuyenGiaiQuyet thamQuyenGiaiQuyet) {
		this.thamQuyenGiaiQuyet = thamQuyenGiaiQuyet;
	}

	public CoQuanQuanLy getPhongBanGiaiQuyet() {
		return phongBanGiaiQuyet;
	}

	public void setPhongBanGiaiQuyet(CoQuanQuanLy phongBanGiaiQuyet) {
		this.phongBanGiaiQuyet = phongBanGiaiQuyet;
	}

	public int getThuTuThucHien() {
		return thuTuThucHien;
	}

	public void setThuTuThucHien(int thuTuThucHien) {
		this.thuTuThucHien = thuTuThucHien;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public String getyKienXuLy() {
		return yKienXuLy;
	}

	public void setyKienXuLy(String yKienXuLy) {
		this.yKienXuLy = yKienXuLy;
	}
	
	public String getMoTaTrangThai() {
		return moTaTrangThai;
	}

	public void setMoTaTrangThai(String moTaTrangThai) {
		this.moTaTrangThai = moTaTrangThai;
	}

	public String getNoiDungThongTinTrinhLanhDao() {
		return noiDungThongTinTrinhLanhDao;
	}

	public void setNoiDungThongTinTrinhLanhDao(String noiDungThongTinTrinhLanhDao) {
		this.noiDungThongTinTrinhLanhDao = noiDungThongTinTrinhLanhDao;
	}

	public String getNoiDungYeuCauXuLy() {
		return noiDungYeuCauXuLy;
	}

	public void setNoiDungYeuCauXuLy(String noiDungYeuCauXuLy) {
		this.noiDungYeuCauXuLy = noiDungYeuCauXuLy;
	}

	public QuyTrinhXuLyDonEnum getQuyTrinhXuLy() {
		return quyTrinhXuLy;
	}

	public void setQuyTrinhXuLy(QuyTrinhXuLyDonEnum quyTrinhXuLy) {
		this.quyTrinhXuLy = quyTrinhXuLy;
	}

	public HuongXuLyXLDEnum getHuongXuLy() {
		return huongXuLy;
	}

	public void setHuongXuLy(HuongXuLyXLDEnum huongXuLy) {
		this.huongXuLy = huongXuLy;
	}
}
