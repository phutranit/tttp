package vn.greenglobal.tttp.enums;

public enum DonViTheoDoiGQDEnum {
	THANH_TRA("Thanh tra"),
	KHAC("Khác");

	DonViTheoDoiGQDEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
