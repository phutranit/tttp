package vn.greenglobal.tttp.enums;

public enum ThanhTraKiemTraEnum {

	THANH_TRA("Thanh tra"),
	KIEM_TRA("Kiểm tra");

	ThanhTraKiemTraEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
