package vn.greenglobal.tttp.enums;

public enum LoaiDoiTuongEnum {

	HANH_VI_HANH_CHINH("Hành vi hành chính"),
	QUYET_DINH_HANH_CHINH("Quyết định hành chính");

	LoaiDoiTuongEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
