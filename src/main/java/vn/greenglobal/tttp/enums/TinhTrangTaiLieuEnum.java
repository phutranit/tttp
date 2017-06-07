package vn.greenglobal.tttp.enums;

public enum TinhTrangTaiLieuEnum {

	BAN_GOC("Bản gốc"),
	BAN_SAO("Bảo sao"),
	BAN_SAO_CONG_CHUNG("Bảo sao công chứng");

	TinhTrangTaiLieuEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
