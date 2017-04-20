package vn.greenglobal.tttp.enums;

public enum QuyTrinhXuLyDonEnum {

	GIAO_VIEC("Giao việc"),
	DUYET("Duyệt"),
	DINH_CHI("Đình chỉ"),
	DE_XUAT_GIAO_VIEC_LAI("Đề xuất giao việc lại"),
	DE_XUAT_HUONG_XU_LY("Đề xuất hướng xử lý");
	
	QuyTrinhXuLyDonEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
