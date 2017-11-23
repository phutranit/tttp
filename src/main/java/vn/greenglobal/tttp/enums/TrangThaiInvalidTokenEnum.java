package vn.greenglobal.tttp.enums;

public enum TrangThaiInvalidTokenEnum {
	THAY_DOI_THONG_TIN_NGUOI_DUNG("Thay đổi thông tin người dùng"),
	THAY_DOI_VAI_TRO_NGUOI_DUNG("Thay đổi vai trò người dùng"),
	DANG_NHAP("Đăng nhập"),
	DANG_XUAT("Đăng xuất");
	
	TrangThaiInvalidTokenEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
