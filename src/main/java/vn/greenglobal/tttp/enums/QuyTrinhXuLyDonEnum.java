package vn.greenglobal.tttp.enums;

public enum QuyTrinhXuLyDonEnum {
	
	THU_LY("Thụ lý"),
	KHONG_THU_LY("Không thụ lý"),
	GIAO_VIEC("Giao việc"),
	CHUYEN_BO_PHAN_GIAI_QUYET("Chuyển bộ phận giải quyết"),
	CHUYEN_CAN_BO_XU_LY("Chuyển cán bộ xử lý"),
	DUYET("Duyệt"),
	YEU_CAU_KIEM_TRA_LAI("Yêu cầu kiểm tra lại"),
	TRINH_LANH_DAO("Trình lãnh đạo"),
	DE_XUAT_GIAO_VIEC_LAI("Đề xuất giao việc lại"),
	DE_XUAT_HUONG_XU_LY("Đề xuất hướng xử lý"),
	DINH_CHI("Đình chỉ"),
	THU_HOI_DON("Thu hồi đơn"),
	TRA_DON("Trả đơn");
	
	QuyTrinhXuLyDonEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
