package vn.greenglobal.tttp.enums;

public enum ApiErrorEnum {

	PHONG_BAN_GIAI_QUYET_REQUIRED("Trường phòng ban giải quyết không được để trống!"),
	TEN_REQUIRED("Trường tên không được để trống!"),
	TEN_EXISTS("Tên đã tồn tại trong hệ thống!"),
	MA_TEN_EXISTS("Mã hoặc tên đã tồn tại trong hệ thống!"),
	DATA_NOT_FOUND("Dữ liệu này không tồn tại trong hệ thống!"),
	ROLE_FORBIDDEN("Không có quyền thực hiện chức năng này!"),
	DATA_USED("Dữ liệu này đang được sử dụng!"),
	DATA_INCORRECT("Dữ liệu không đúng"),
	USER_PASSWORD_INCORRECT("Tài khoản đăng nhập hoặc mật khẩu không chính xác!");
	
	ApiErrorEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
