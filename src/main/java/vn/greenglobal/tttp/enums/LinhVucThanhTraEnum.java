package vn.greenglobal.tttp.enums;

public enum LinhVucThanhTraEnum {

	HANH_CHINH("Hành chính"),
	XAY_DUNG_CO_BAN("XDCB"),
	DAT_DAI("Đất đai"),
	TAI_CHINH("Tài chính"),
	CHUYEN_NGANH("Chuyên ngành");

	LinhVucThanhTraEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
