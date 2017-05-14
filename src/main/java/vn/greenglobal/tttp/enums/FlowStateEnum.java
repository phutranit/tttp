package vn.greenglobal.tttp.enums;

public enum FlowStateEnum {

	BAT_DAU(""),
	TRINH_LANH_DAO(""),
	LANH_DAO_GIAO_VIEC_TRUONG_PHONG(""),
	TRUONG_PHONG_DE_XUAT_GIAO_VIEC_LAI(""),
	TRUONG_PHONG_GIAO_VIEC_CAN_BO(""),
	CAN_BO_DE_XUAT_GIAO_VIEC_LAI(""),
	CAN_BO_DE_XUAT_HUONG_XU_LY(""),
	KET_THUC("");

	FlowStateEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
