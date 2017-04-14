package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "xulydon")
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

	private String quyTrinhXuLy = "";
	private String ghiChu = "";
	private String yKienXuLy = "";
	private String huongXuLy = "";
	private String moTaTrangThai = "";
	private String noiDungThongTinTrinhLanhDao = "";

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

	public String getQuyTrinhXuLy() {
		return quyTrinhXuLy;
	}

	public void setQuyTrinhXuLy(String quyTrinhXuLy) {
		this.quyTrinhXuLy = quyTrinhXuLy;
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

	public String getHuongXuLy() {
		return huongXuLy;
	}

	public void setHuongXuLy(String huongXuLy) {
		this.huongXuLy = huongXuLy;
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

}
