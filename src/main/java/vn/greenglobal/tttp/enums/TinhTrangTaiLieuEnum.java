package vn.greenglobal.tttp.enums;

public enum TinhTrangTaiLieuEnum {

	BAN_CHINH("Bản chính"),
	BAN_SAO("Bản sao"),
	BAN_PHOTO("Bản photo"),
	VAM_BAN_KHAC("Văn bản khác");

	TinhTrangTaiLieuEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
