package vn.greenglobal.tttp.enums;

public enum HinhThucKienNghiEnum {

	CA_NHAN("Cá nhân"),
	TO_CHUC("Tổ chức");

	HinhThucKienNghiEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
