package vn.greenglobal.tttp.enums;

public enum HinhThucGiaiQuyetEnum {

	CA_NHAN("Cá nhân"),
	KIEM_TRA_NHAC_NHO("Kiểm tra, nhắc nhở");

	HinhThucGiaiQuyetEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
