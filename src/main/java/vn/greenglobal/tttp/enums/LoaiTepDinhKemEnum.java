package vn.greenglobal.tttp.enums;

public enum LoaiTepDinhKemEnum {

	QUYET_DINH("Quyết định"),
	KHAC("Khác");

	LoaiTepDinhKemEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
