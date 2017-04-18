package vn.greenglobal.tttp.enums;

public enum HuongGiaiQuyetTCDEnum {

	GIAI_QUYET_NGAY("Giải quyết ngay"), 
	CHO_GIAI_QUYET("Chờ giải quyết");

	HuongGiaiQuyetTCDEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
