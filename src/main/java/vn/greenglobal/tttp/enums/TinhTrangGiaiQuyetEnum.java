package vn.greenglobal.tttp.enums;

public enum TinhTrangGiaiQuyetEnum {

	DANG_GIAI_QUYET("Đang giải quyết"),
	DA_GIAI_QUYET("Đã giải quyết");
	
	TinhTrangGiaiQuyetEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
