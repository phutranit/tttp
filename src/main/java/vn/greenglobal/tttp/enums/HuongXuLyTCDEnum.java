package vn.greenglobal.tttp.enums;

public enum HuongXuLyTCDEnum {

	TIEP_NHAN_DON("Tiếp nhận đơn"),
	BO_SUNG_THONG_TIN("Bổ sung thông tin"),
	TRA_DON_VA_HUONG_DAN("Trả đơn và hướng dẫn"),
	TU_CHOI("Từ chối"),
	KHAC("Khác"),
	YEU_CAU_GAP_LANH_DAO("Yêu cầu gặp lãnh đạo");

	HuongXuLyTCDEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
