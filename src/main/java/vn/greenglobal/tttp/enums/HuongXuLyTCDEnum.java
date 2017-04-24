package vn.greenglobal.tttp.enums;

public enum HuongXuLyTCDEnum {

	TIEP_NHAN_DON("Tiếp nhận đơn"),
	BO_SUNG_THONG_TIN("Bổ sung thông tin"),
	TRA_DON_VA_HUONG_DAN("Trả đơn và hướng dẫn"),
	TU_CHOI("Từ chối"),
	GIAI_QUYET_NGAY("Giải quyết ngay"),
	GIAO_DON_VI("Giao cho các đơn vị kiểm tra và đề xuất");

	HuongXuLyTCDEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
