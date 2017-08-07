package vn.greenglobal.tttp.enums;

public enum TrangThaiDonEnum {
	
	DINH_CHI("Đình chỉ"),
	DANG_XU_LY("Đang xử lý"),
	DA_XU_LY("Đã xử lý"),
	DANG_GIAI_QUYET("Đang giải quyết"),
	DA_GIAI_QUYET("Đã giải quyết"),
	HOAN_THANH("Hoàn thành");
	
	TrangThaiDonEnum(String text){
		this.text= text;
	}
	
	public String getText() {
		return text;
	}

	private String text;
}
