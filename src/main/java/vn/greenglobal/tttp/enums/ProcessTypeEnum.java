package vn.greenglobal.tttp.enums;

public enum ProcessTypeEnum {
	
	THONG_TIN_DON("Thông tin đơn"),
	TIEP_CONG_DAN("Tiêp công dân"),
	XU_LY_DON("Xử lý đơn"),
	KIEM_TRA_DE_XUAT("Kiểm tra đề xuất"),
	GIAI_QUYET_DON("Giải quyết đơn"),
	THAM_TRA_XAC_MINH("Thẩm tra xác minh"),
	THANH_TRA("Thanh tra"),
	THEO_DOI_THUC_HIEN("Theo dõi thực hiện"),
	KE_HOACH_THANH_TRA("Kế  hoạch thanh tra");
	
	ProcessTypeEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
