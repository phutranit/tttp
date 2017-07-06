package vn.greenglobal.tttp.enums;

public enum QuaTrinhXuLyEnum {
	TIEP_CONG_DAN("Tiếp công dân"),
	CHUYEN_XU_LY_DON("Chuyển xử lý đơn"),
	TRINH_LANH_DAO("Trình lãnh đạo"),
	GIAO_PHONG_BAN("Giao phòng ban"),
	GIAO_CAN_BO_XU_LY("Giao cán bộ xử lý"),
	CHUYEN_CAN_BO_NHAP_LIEU("Chuyển cán bộ nhập liệu");
	
	QuaTrinhXuLyEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
