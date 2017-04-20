package vn.greenglobal.tttp.enums;

public enum TrangThaiDonEnum {

	DINH_CHI("Đình chỉ"),
	DA_XU_LY("Đã xử lý");
	
	TrangThaiDonEnum(String text){
		this.text= text;
	}
	
	public String getText() {
		return text;
	}

	private String text;
}
