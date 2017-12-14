package vn.greenglobal.tttp.enums;

public enum FlowStateEnum {

	BAT_DAU("Bắt đầu"),
	TRINH_LANH_DAO("Trình lãnh đạo"),	
	TRINH_TRUONG_PHONG("Trình trưởng phòng"),	
	VAN_THU_CHUYEN_DON_VI_TTXM("Văn thư chuyển đơn vị TTXM"),
	LANH_DAO_GIAO_VIEC_TRUONG_PHONG("Lãnh đạo giao việc trưởng phòng"),
	LANH_DAO_GIAO_VIEC_CAN_BO("Lãnh đạo giao việc cán bộ"),
	LANH_DAO_THU_HOI("Lãnh đạo thu hồi"),
	TRUONG_PHONG_CHUYEN_DON_VI_TTXM("Trưởng phòng chuyển đơn vị TTXM"),
	TRUONG_PHONG_DE_XUAT_GIAO_VIEC_LAI("Trường phòng đề xuất giao việc lại"),
	TRUONG_PHONG_GIAO_VIEC_CAN_BO("Trưởng phòng giao việc cán bộ"),
	TRUONG_PHONG_GIAO_VIEC_CAN_BO_SAU_KHI_NHAN_KET_QUA_TTXM("Trưởng phòng giao việc cán bộ"),
	TRUONG_PHONG_GIAO_VIEC_CAN_BO_SAU_KHI_NHAN_DU_THAO("Trưởng phòng giao việc cán bộ"),
	TRUONG_PHONG_GIAO_VIEC_CAN_BO_LAP_DU_THAO("Trưởng phòng giao việc cán bộ"),
	TRUONG_PHONG_NHAN_KET_QUA_TTXM("Trưởng phòng nhận kết quả TTXM"),
	TRUONG_PHONG_NHAN_YEU_CAU_LAP_DU_THAO("Trưởng phòng nhận yêu cầu lập dự thảo"),
	TRUONG_PHONG_THU_HOI("Trưởng phòng thu hồi"),
	TRUONG_PHONG_THU_HOI_SAU_KHI_NHAN_DU_THAO("Trưởng phòng thu hồi"),
	TRUONG_PHONG_NHAN_DU_THAO("Trưởng phòng nhận dự thảo"),
	CAN_BO_DE_XUAT_GIAO_VIEC_LAI("Cán bộ đề xuất giao việc lại"),
	CAN_BO_DE_XUAT_HUONG_XU_LY("Cán bộ đề xuất hướng xử lý"),
	CAN_BO_CHUYEN_VAN_THU_GIAO_TTXM("Cán bộ chuyển văn thư giao TTXM"),
	CAN_BO_CHUYEN_VAN_THU_KET_QUA_TTXM("Cán bộ chuyển văn thư kết quả TTXM"),
	CAN_BO_CHUYEN_DON_VI_TTXM("Cán bộ chuyển đơn vị TTXM"),
	CAN_BO_NHAN_KET_QUA_TTXM("Cán bộ nhận kết quả TTXM"),
	CAN_BO_CHUYEN_KET_QUA_DON_VI_GIAO("Cán bộ chuyển kết quả đơn vị giao"),
	CAN_BO_GIAI_QUYET_DON("Cán bộ giải quyết đơn"),
	CAN_BO_GIAO_DON_VI_TTXM_LAP_DU_THAO("Cán bộ giao đơn vị TTXM lập dự thảo"),
	CAN_BO_CHUYEN_VE_DON_VI_GIAI_QUYET("Cán bộ chuyển về đơn vị giải quyết"),
	CAN_BO_CHUYEN_DU_THAO_VE_DON_VI_GIAI_QUYET("Cán bộ chuyển dự thảo về đơn vị giải quyết"),
	CAN_BO_CHUYEN_VE_TP_DON_VI_GIAI_QUYET("Cán bộ chuyển về đơn vị giải quyết"),
	CAN_BO_GUI_YEU_CAU_THEO_DOI_THUC_HIEN("Cán bộ, trưởng phòng gửi yêu cầu theo dõi thực hiện"),
	CAN_BO_TDTH_LUU_DON("Cán bộ, trưởng phòng TDTH lưu đơn"),
	YEU_CAU_GAP_LANH_DAO("Yêu cầu gặp lãnh đạo"),
	KET_THUC("Kết thúc");

	FlowStateEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
