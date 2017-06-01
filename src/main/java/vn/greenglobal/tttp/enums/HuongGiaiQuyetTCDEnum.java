package vn.greenglobal.tttp.enums;

public enum HuongGiaiQuyetTCDEnum {

	DANG_GIAI_QUYET("Đang giải quyết"),
	HOAN_THANH("Hoàn thành"),
	DA_CO_BAO_CAO_KIEM_TRA_DE_XUAT("Đã có báo cáo kiểm tra đề xuất"),
	
	KHOI_TAO("Khởi tạo"),
	GIAI_QUYET_NGAY("Giải quyết ngay"),
	GIAO_DON_VI_KIEM_TRA_VA_DE_XUAT("Giao cho các đơn vị kiểm tra và đề xuất"),
	CHO_GIAI_QUYET("Chờ giải quyết");

	HuongGiaiQuyetTCDEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
