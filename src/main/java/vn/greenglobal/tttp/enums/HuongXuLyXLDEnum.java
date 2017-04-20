package vn.greenglobal.tttp.enums;

public enum HuongXuLyXLDEnum {
	
	DE_XUAT_THU_LY("Đề xuất thụ lý"),
	KHONG_DU_DIEU_KIEN_THU_LY("Không đủ điều kiện thụ lý"),
	CHUYEN_DON("Chuyển đơn"),
	TRA_DON_VA_HUONG_DAN("Trả đơn và hướng dẫn"),
	LUU_DON_VA_THEO_DOI("Lưu đơn và theo dõi");
	
	HuongXuLyXLDEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
