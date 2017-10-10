package vn.greenglobal.tttp.enums;

public enum HinhThucThanhTraEnum {

	THEO_KE_HOACH("Theo kế hoạch"),
	THUONG_XUYEN("Thường xuyên"),
	DOT_XUAT("Đột xuất");

	HinhThucThanhTraEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
