package vn.greenglobal.tttp.enums;

public enum DoiTuongThayDoiEnum {

	CONG_DAN("Công dân"),
	DON_CONG_DAN("Đơn - Công dân"),
	DON("Đơn");

	DoiTuongThayDoiEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
