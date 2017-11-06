package vn.greenglobal.tttp.enums;

public enum ChucNangCuocThanhTraEnum {

	HANH_CHINH("Hành chính"),
	CHUYEN_NGANH("Chuyên ngành");

	ChucNangCuocThanhTraEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
