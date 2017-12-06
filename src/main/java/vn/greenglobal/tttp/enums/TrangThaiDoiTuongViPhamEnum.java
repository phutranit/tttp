package vn.greenglobal.tttp.enums;

public enum TrangThaiDoiTuongViPhamEnum {

	DANG_SOAN("Đang soạn"),
	HOAN_THANH("Hoàn thành");

	TrangThaiDoiTuongViPhamEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
