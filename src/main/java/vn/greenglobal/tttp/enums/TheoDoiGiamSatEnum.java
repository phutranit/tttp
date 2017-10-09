package vn.greenglobal.tttp.enums;

public enum TheoDoiGiamSatEnum {
	TRE_HAN("Trễ hạn"),
	DUNG_HAN("Đúng hạn");

	TheoDoiGiamSatEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
