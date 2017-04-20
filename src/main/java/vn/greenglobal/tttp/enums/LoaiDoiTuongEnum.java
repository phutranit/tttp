package vn.greenglobal.tttp.enums;

public enum LoaiDoiTuongEnum {

	QUYET_DINH_HANH_CHINH("Quyết đị hành chính");

	LoaiDoiTuongEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
