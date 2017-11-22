package vn.greenglobal.tttp.enums;

public enum TrangThaiBaoCaoDonViEnum {
	DA_GUI("Đã gửi"),
	DANG_SOAN("Đang soạn");
	
	TrangThaiBaoCaoDonViEnum(String text){
		this.text= text;
	}
	
	public String getText() {
		return text;
	}

	private String text;
}
