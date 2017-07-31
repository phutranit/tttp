package vn.greenglobal.tttp.enums;

public enum LinhVucThanhTraEnum {

	HANH_CHINH("Hành chính"),
	XAY_DUNG("Xây dựng"),
	DAT_DAI("Đất đai"),
	TAI_CHINH("Tài chính");

	LinhVucThanhTraEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
