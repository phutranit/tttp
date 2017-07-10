package vn.greenglobal.tttp.enums;

public enum QuaTrinhXuLyEnum {
	TIEP_CONG_DAN("Tiếp công dân"),
	CHUYEN_XU_LY_DON("Chuyển xử lý đơn"),
	TRINH_LANH_DAO("Trình lãnh đạo"),
	GIAO_PHONG_BAN("Giao phòng ban"),
	GIAO_CAN_BO_XU_LY("Giao cán bộ xử lý"),
	CHUYEN_CAN_BO_NHAP_LIEU("Chuyển cán bộ nhập liệu"),
	CHUYEN_PHONG_GIAI_QUYET("Chuyển phòng giải quyết"),
	GIAO_CAN_BO_GIAI_QUYET("Giao cán bộ giải quyết"),
	CHUYEN_DON_VI_TTXM("Chuyển đơn vị TTXM"),
	CHUYEN_DON_VI_KTDX("Chuyển đơn vị KTDX"),
	CHUYEN_CHUYEN_VIEN_NHAP_LIEU("Chuyển chuyên viên nhập liệu"),
	GUI_BAO_CAO_TTXM_CHO_DON_VI_GIAO("Gửi báo cáo TTXm cho đơn vị giao"),
	CHUYEN_KET_QUA_VE_DON_VI_GIAI_QUYET("Chuyển kết quả về đơn vị giải quyết");
	
	QuaTrinhXuLyEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
