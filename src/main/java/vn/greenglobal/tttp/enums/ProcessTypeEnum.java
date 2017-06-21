package vn.greenglobal.tttp.enums;

public enum ProcessTypeEnum {
	
	TIEP_CONG_DAN("Tiêp công dân"),
	XU_LY_DON("Xử lý đơn"),
	KIEM_TRA_DE_XUAT("Kiểm tra đề xuất"),
	GIAI_QUYET_DON("Giải quyết đơn"),
	THAM_TRA_XAC_MINH("Thẩm tra xác minh");
//	GIAI_QUYET_KHIEU_NAI("Giải quyết khiếu nại"),
//	GIAI_QUYET_TO_CAO("Giải quyết tố cáo"),
//	GIAI_QUYET_KIEN_NGHI_PHAN_ANH("Giải quyết kiến nghị, phản ánh")
	
	ProcessTypeEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
