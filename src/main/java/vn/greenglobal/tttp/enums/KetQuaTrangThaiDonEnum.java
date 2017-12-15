package vn.greenglobal.tttp.enums;

public enum KetQuaTrangThaiDonEnum {

	DINH_CHI("Rút đơn/Đình chỉ"),
	DE_XUAT_THU_LY("Thụ lý"),
	KHONG_DU_DIEU_KIEN_THU_LY("Không thụ lý"),
	KHONG_XU_LY_NEU_LY_DO("Không xử lý, nêu lý do"),
	CHUYEN_DON("Chuyển đơn"),
	TRA_DON_VA_HUONG_DAN("Trả đơn và hướng dẫn"),
	HUONG_DAN_VIET_LAI_DON("Hướng dẫn viết lại đơn"),
	LUU_DON_VA_THEO_DOI("Lưu đơn và theo dõi"),
	YEU_CAU_GAP_LANH_DAO("Yêu cầu gặp lãnh đạo"),
	TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN("Trả lại đơn chuyển không đúng thẩm quyền"),
	DANG_TTXM("Đang TTXM"),
	DANG_TDTH("Đang TDTH"),
	DA_CO_KET_QUA_TTXM("Đã có kết quả TTXM"),
//	CHO_DOI_THOAI("Chờ đối thoại"),
//	DA_CO_KET_QUA_DOI_THOAI("Đã có kết quả đối thoại"),
	DOI_THOAI("Đối thoại"),
	DANG_LAP_DU_THAO("Đang lập dự thảo"),
	DA_LAP_DU_THAO("Đã lập dự thảo"),
	CHO_RA_QUYET_DINH_GIAI_QUYET("Chờ ra quyết định giải quyết"),
	DA_CO_QUYET_DINH_GIAI_QUYET("Đã có quyết định giải quyết"),
	LUU_HO_SO("Lưu hồ sơ");
	
	KetQuaTrangThaiDonEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
