package vn.greenglobal.tttp.enums;

public enum VaiTroEnum {

	LANH_DAO("Lãnh đạo"),
	TRUONG_PHONG("Trưởng phòng"),
	CHUYEN_VIEN("Chuyên viên"),
	VAN_THU("Chuyên viên nhập liệu");
	
	VaiTroEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
