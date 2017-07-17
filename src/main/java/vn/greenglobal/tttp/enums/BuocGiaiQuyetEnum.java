package vn.greenglobal.tttp.enums;

public enum BuocGiaiQuyetEnum {

	DINH_CHI_DON("Rút đơn/Đình chỉ"),
	GIA_HAN("Gia hạn"),
	THU_LY_DON("Thụ lý đơn"),
	THAM_TRAC_XAC_MINH("Thẩm tra xác minh vụ việc"),
	RA_QUYET_DINH("Ra quyết định giải quyết");

	BuocGiaiQuyetEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
