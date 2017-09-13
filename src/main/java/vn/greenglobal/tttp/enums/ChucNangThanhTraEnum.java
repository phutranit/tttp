package vn.greenglobal.tttp.enums;

public enum ChucNangThanhTraEnum {

	THEO_KE_HOACH("Theo kế hoạch"),
	THUONG_XUYEN("Thường xuyên"),
	DOT_XUAT("Đột xuất");

	ChucNangThanhTraEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
