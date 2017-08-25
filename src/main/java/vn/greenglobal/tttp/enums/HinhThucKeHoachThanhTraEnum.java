package vn.greenglobal.tttp.enums;

public enum HinhThucKeHoachThanhTraEnum {

	HANH_CHINH("Hành chính"),
	CHUYEN_NGANH("Chuyên ngành");

	HinhThucKeHoachThanhTraEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
