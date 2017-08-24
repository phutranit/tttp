package vn.greenglobal.tttp.enums;

public enum LoaiDoiTuongThanhTraEnum {

	CA_NHAN("Công dân"),
	TO_CHUC("Tổ chức");

	LoaiDoiTuongThanhTraEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
