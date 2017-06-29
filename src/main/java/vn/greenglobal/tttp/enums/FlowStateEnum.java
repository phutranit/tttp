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
	CAN_BO_CHUYEN_VAN_THU_GIAO_TTXM("Cán bộ chuyển văn thư giao TTXM"),
	VAN_THU_CHUYEN_DON_VI_TTXM("Văn thư chuyển đơn vị TTXM"),
	CAN_BO_CHUYEN_DON_VI_TTXM("Cán bộ chuyển đơn vị TTXM"),
	CAN_BO_NHAN_KET_QUA_TTXM("Cán bộ nhận kết quả TTXM"),
	CAN_BO_CHUYEN_VAN_THU_KET_QUA_TTXM("Cán bộ chuyển văn thư kết quả TTXM"),
	CAN_BO_CHUYEN_KET_QUA_DON_VI_GIAO("Cán bộ chuyển kết quả đơn vị giao"),
	CAN_BO_GIAI_QUYET_DON("Cán bộ giải quyết đơn"),
	CAN_BO_CHUYEN_VE_DON_VI_GIAI_QUYET("Cán bộ chuyển về đơn vị giải quyết"),
	KET_THUC("Kết thúc");

	FlowStateEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
