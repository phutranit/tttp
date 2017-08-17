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
	private String phamViThanhTra = "";
	private String ghiChu = "";
	private int thoiHanThanhTra = 0;
	private LinhVucThanhTraEnum linhVucThanhTra;
	private Long doiTuongThanhTraId;

	public String getNoiDungThanhTra() {
		return noiDungThanhTra;
	}

	public void setNoiDungThanhTra(String noiDungThanhTra) {
		this.noiDungThanhTra = noiDungThanhTra;
	}

	public String getPhamViThanhTra() {
		return phamViThanhTra;
	}

	public void setPhamViThanhTra(String phamViThanhTra) {
		this.phamViThanhTra = phamViThanhTra;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
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

}