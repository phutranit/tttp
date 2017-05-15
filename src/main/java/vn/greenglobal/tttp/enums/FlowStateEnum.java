package vn.greenglobal.tttp.enums;

public enum FlowStateEnum {

	BAT_DAU("Bắt đầu"),
	TRINH_LANH_DAO("Trình lãnh đạo"),
	LANH_DAO_GIAO_VIEC_TRUONG_PHONG("Lãnh đạo giao việc trưởng phòng"),
	LANH_DAO_GIAO_VIEC_CAN_BO("Lãnh đạo giao việc cán bộ"),
	TRUONG_PHONG_DE_XUAT_GIAO_VIEC_LAI("Trường phòng đề xuất giao việc lại"),
	TRUONG_PHONG_GIAO_VIEC_CAN_BO("Trưởng phòng giao việc cán bộ"),
	CAN_BO_DE_XUAT_GIAO_VIEC_LAI("Cán bộ đề xuất giao việc lại"),
	CAN_BO_DE_XUAT_HUONG_XU_LY("Cán bộ đề xuất hướng xử lý"),
	KET_THUC("Kết thúc");

	FlowStateEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
