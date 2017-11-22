package vn.greenglobal.tttp.enums;

public enum TrangThaiBaoCaoDonViEnum {
	DA_GUI("Đã gửi"),
	DA_TONG_HOP("Đã tổng hợp"),
	DANG_SOAN("Đang soạn");
	
	TrangThaiBaoCaoDonViEnum(String text){
		this.text= text;
	}
	
	public String getText() {
		return text;
	}

	private String text;
}
