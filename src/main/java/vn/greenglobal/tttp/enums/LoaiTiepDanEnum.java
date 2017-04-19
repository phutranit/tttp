package vn.greenglobal.tttp.enums;

public enum LoaiTiepDanEnum {

	THUONG_XUYEN("Tiếp công dân thường xuyên"),
	DINH_KY("Tiếp công dân định kỳ của lãnh đạo"),
	DOT_XUAT("Tiếp công dân đột xuất của lãnh đạo");

	LoaiTiepDanEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
