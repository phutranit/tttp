package vn.greenglobal.tttp.enums;

public enum ProcessTypeEnum {
	
	XU_LY_DON("Xử lý đơn"),
	GIAI_QUYET_KHIEU_NAI("Giải quyết khiếu nại"),
	GIAI_QUYET_TO_CAO("Giải quyết tố cáo"),
	GIAI_QUYET_KIEN_NGHI_PHAN_ANH("Giải quyết kiến nghị, phản ánh");
	
	ProcessTypeEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
