package vn.greenglobal.tttp.enums;

public enum BuocGiaiQuyetEnum {

	DINH_CHI_DON("Rút đơn/Đình chỉ"),
	GIA_HAN("Gia hạn"),
	THU_LY_DON("Thụ lý đơn"),
	THAM_TRAC_XAC_MINH("Thẩm tra xác minh vụ việc"),
	RA_QUYET_DINH("Ra quyết định giải quyết"),
	LUAT_SU("Luật sư"),
	BAO_CAO_KET_QUA_TTXM("Báo cáo kết quả TTXM"),
	QUYET_DINH_GIAI_QUYET("Quyết định giải quyết"),
	GIAO_CO_QUAN_DIEU_TRA("Giao cơ quan điều tra"),
	TAI_LIEU_KEM_THEO("Tài liệu kèm theo"),
	KET_LUAN_THANH_TRA("Kết luận thanh tra");

	BuocGiaiQuyetEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
