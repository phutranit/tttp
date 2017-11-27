package vn.greenglobal.tttp.enums;

public enum TinhTrangXuLyEnum {

	DUNG_HAN("Đúng hạn"),
	TRE_HAN("Trễ hạn");

	TinhTrangXuLyEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
