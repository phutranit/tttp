package vn.greenglobal.tttp.enums;

public enum TrangThaiTTXMEnum {
	TU_TTXM("Tự thẩm tra xác minh"),
	GIAO_TTXM("Giao đơn vị khác TTXM");
	
	TrangThaiTTXMEnum(String text){
		this.text= text;
	}
	
	public String getText() {
		return text;
	}

	private String text;
}
