package vn.greenglobal.tttp.enums;

public enum ThucHienPhapLuatVeThanhTraEnum {

	THANH_TRA("Thanh tra"),
	KNTC("KNTC"),
	PCTN("PCTN"),
	CA_3("Cả 3");

	ThucHienPhapLuatVeThanhTraEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
