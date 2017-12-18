package vn.greenglobal.tttp.enums;

public enum ThongKeBaoCaoLoaiKyEnum {
	THEO_QUY("Theo quý"),
	SAU_THANG_DAU_NAM("6 tháng đầu năm"),
	CHIN_THANG_DAU_NAM("9 tháng đầu năm"),
	THEO_NAM("Theo năm"),
	THEO_THANG("Theo tháng"),
	TUY_CHON("Tùy chọn");
	
	ThongKeBaoCaoLoaiKyEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
