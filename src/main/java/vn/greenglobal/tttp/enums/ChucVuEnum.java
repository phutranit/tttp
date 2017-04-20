package vn.greenglobal.tttp.enums;

public enum ChucVuEnum {

	LANH_DAO("Lãnh đạo"),
	TRUONG_PHONG("Trưởng phòng"),
	CAN_BO("Cán bộ"),
	VAN_THU("Văn thư");
	
	ChucVuEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
