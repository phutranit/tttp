package vn.greenglobal.tttp.enums;

public enum HinhThucGiaiQuyetEnum {

	CA_NHAN("Cá nhân");

	HinhThucGiaiQuyetEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
