package vn.greenglobal.tttp.enums;

public enum TrangThaiXuLyDonEnum {
	DANG_XU_LY("Đang xử lý"), 
	DA_XU_LY("Đã xử lý");
	
	TrangThaiXuLyDonEnum(String text){
		this.text= text;
	}
	
	public String getText() {
		return text;
	}

	private String text;
}
