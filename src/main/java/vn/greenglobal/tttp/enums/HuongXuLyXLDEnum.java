package vn.greenglobal.tttp.enums;

public enum HuongXuLyXLDEnum {
	
	DINH_CHI("Đình chỉ"),
	DE_XUAT_THU_LY("Thụ lý"),
	KHONG_DU_DIEU_KIEN_THU_LY("Không thụ lý"),
	CHUYEN_DON("Chuyển đơn"),
	TRA_DON_VA_HUONG_DAN("Trả đơn và hướng dẫn"),
	LUU_DON_VA_THEO_DOI("Lưu đơn và theo dõi"),
	YEU_CAU_GAP_LANH_DAO("Yêu cầu gặp lãnh đạo"),
	TRA_LAI_DON_KHONG_DUNG_THAM_QUYEN("Trả lại đơn không đúng thẩm quyền");
	
	HuongXuLyXLDEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
