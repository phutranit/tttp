package vn.greenglobal.tttp.model.medial;

import vn.greenglobal.tttp.enums.LinhVucThanhTraEnum;
import vn.greenglobal.tttp.model.Model;

public class Medial_KeHoachThanhTra_CuocThanhTra_Post_Patch
		extends Model<Medial_KeHoachThanhTra_CuocThanhTra_Post_Patch> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4470756964123189859L;

	private String noiDungThanhTra = "";
	private String kyThanhTra = "";
	private String ghiChu = "";
	private String donViPhoiHop = "";
	private int thoiHanThanhTra = 0;
	private LinhVucThanhTraEnum linhVucThanhTra;
	private Long doiTuongThanhTraId;
	private Long donViChuTriId;

	public String getNoiDungThanhTra() {
		return noiDungThanhTra;
	}

	public void setNoiDungThanhTra(String noiDungThanhTra) {
		this.noiDungThanhTra = noiDungThanhTra;
	}

	public String getKyThanhTra() {
		return kyThanhTra;
	}

	public void setKyThanhTra(String kyThanhTra) {
		this.kyThanhTra = kyThanhTra;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public String getDonViPhoiHop() {
		return donViPhoiHop;
	}

	public void setDonViPhoiHop(String donViPhoiHop) {
		this.donViPhoiHop = donViPhoiHop;
	}

	public int getThoiHanThanhTra() {
		return thoiHanThanhTra;
	}

	public void setThoiHanThanhTra(int thoiHanThanhTra) {
		this.thoiHanThanhTra = thoiHanThanhTra;
	}

	public LinhVucThanhTraEnum getLinhVucThanhTra() {
		return linhVucThanhTra;
	}

	public void setLinhVucThanhTra(LinhVucThanhTraEnum linhVucThanhTra) {
		this.linhVucThanhTra = linhVucThanhTra;
	}

	public Long getDoiTuongThanhTraId() {
		return doiTuongThanhTraId;
	}

	public void setDoiTuongThanhTraId(Long doiTuongThanhTraId) {
		this.doiTuongThanhTraId = doiTuongThanhTraId;
	}

	public Long getDonViChuTriId() {
		return donViChuTriId;
	}

	public void setDonViChuTriId(Long donViChuTriId) {
		this.donViChuTriId = donViChuTriId;
	}

}