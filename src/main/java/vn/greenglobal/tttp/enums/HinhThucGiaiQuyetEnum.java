package vn.greenglobal.tttp.enums;

public enum HinhThucGiaiQuyetEnum {

	CHUA_CO_QUYET_DINH_GIAI_QUYET("Chưa có quyết định giải quyết"),
	QUYET_DINH_LAN_I("Quyết định lần 1"),
	QUYET_DINH_LAN_II("Quyết định lần 2"),
	QUYET_DINH_CUOI_CUNG("Quyết định cuối cùng"),
	BAN_AN_SO_THAM("Bản án Sơ thẩm"),
	BAN_AN_PHUC_THAM("Bản án Phúc thẩm"),
	BAN_AN_TAI_THAM("Bản án tái thẩm"),
	QUYET_DINH_GIAM_DOC_THAM("Quyết định giám đốc thẩm"),
	TRA_LOI_BANG_VAN_BAN("Trả lời bằng văn bản"),
	KET_LUAN_THONG_BAO_BAO_CAO("Kết luận, thông báo, báo cáo");

	HinhThucGiaiQuyetEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
