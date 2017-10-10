package vn.greenglobal.tttp.enums;

public enum ChucNangKeHoachThanhTraEnum {

	HANH_CHINH("Hành chính"),
	CHUYEN_NGANH("Chuyên ngành");

	ChucNangKeHoachThanhTraEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
