package vn.greenglobal.tttp.enums;

public enum HuongXuLyXLDEnum {
	
	CHUYEN_DON("Chuyển đơn"),
	DE_XUAT_THU_LY("Đề xuất thụ lý"),
	TRA_DON_VA_HUONG_DAN("Trả đơn và hướng dẫn"),
	DA_DE_XUAT_GIAO_VIEC_LAI("Đã đề xuất giao việc lại"),
	KHONG_DU_DIEU_KIEN_THU_LY("Không đủ điều kiện thụ lý");
	
	HuongXuLyXLDEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
