package vn.greenglobal.tttp.enums;

public enum HuongGiaiQuyetTCDEnum {

	GIAI_QUYET_NGAY("Giải quyết ngay"),
	GIAO_DON_VI_KIEM_TRA_VA_DE_XUAT("Giao cho các đơn vị kiểm tra và đề xuất"),
	CHO_GIAI_QUYET("Chờ giải quyết");

	HuongGiaiQuyetTCDEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
