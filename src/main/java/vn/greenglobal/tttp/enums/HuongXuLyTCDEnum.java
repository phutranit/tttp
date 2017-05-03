package vn.greenglobal.tttp.enums;

public enum HuongXuLyTCDEnum {

	TIEP_NHAN_DON("Tiếp nhận đơn"),
	BO_SUNG_THONG_TIN("Bổ sung thông tin"),
	TRA_DON_VA_HUONG_DAN("Trả đơn và hướng dẫn"),
	TU_CHOI("Từ chối"),
	GAP_LANH_DAO("Yêu cầu gặp lãnh đạo"),
	GIAI_QUYET_NGAY("Giải quyết ngay"),
	CHO_GIAI_QUYET("Chờ giải quyết");

	HuongXuLyTCDEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
