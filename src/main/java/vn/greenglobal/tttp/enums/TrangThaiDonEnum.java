package vn.greenglobal.tttp.enums;

public enum TrangThaiDonEnum {
	DINH_CHI("Đình chỉ"), 
	DA_CHUYEN("Đã chuyển"), 
	DANG_XU_LY("Đang xử lý"), 
	CHO_XU_LY("Chờ xử lý"), 
	DA_XU_LY("Đã xử lý");
	
	TrangThaiDonEnum(String text){
		this.text= text;
	}
	
	public String getText() {
		return text;
	}

	private String text;
}
