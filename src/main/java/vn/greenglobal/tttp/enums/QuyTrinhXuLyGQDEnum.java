package vn.greenglobal.tttp.enums;

public enum QuyTrinhXuLyGQDEnum {

	GIAO_VIEC_CAN_BO_GQ("Giao việc cho cán bộ giải quyết"),
	DE_XUAT_GIAO_VIEC("Đề xuất giao việc lại"),
	CHUYEN_CHO_VAN_THU("Chuyển cho văn thư"),
	GIAO_CHO_CAN_BO_GQ("Giao cho cán bộ giải quyết"),
	CHUYEN_VE_DON_VI_GIAI_QUYET("Chuyển về đơn vị giải quyết"),
	LAP_DU_THAO_QUYET_DINH_GQ_KN("Lập dự thảo quyết định giải quyết khiếu nại"),
	LAP_DU_THAO_QUYET_DINH_GQ_TC("Lập dự thảo quyết định giải quyết tố cáo");
	
	QuyTrinhXuLyGQDEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
