package vn.greenglobal.tttp.enums;

public enum TinhTrangTaiLieuEnum {

	BAN_GOC("Bản gốc"),
	BAN_SAO("Bản sao"),
	BAN_SAO_CONG_CHUNG("Bản sao công chứng");

	TinhTrangTaiLieuEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
