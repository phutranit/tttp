package vn.greenglobal.tttp.enums;

public enum ThongKeBaoCaoLoaiKyEnum {
	THEO_QUY("Theo quý"),
	SAU_THANG_DAU_NAM("6 tháng đầu năm"),
	SAU_THANG_CUOI_NAM("6 tháng cuối năm"),
	THEO_THANG("Theo tháng"),
	THUY_CHON("Tùy chọn");
	
	ThongKeBaoCaoLoaiKyEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
