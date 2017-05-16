package vn.greenglobal.tttp.enums;

public enum ApiErrorEnum {

	PHONG_BAN_GIAI_QUYET_REQUIRED("Trường phòng ban giải quyết không được để trống!"),
	HOVATEN_REQUIRED("Trường Họ và tên không được để trống!"),
	NGAYSINH_REQUIRED("Trường ngày sinh không được để trống!"),
	COQUANQUANLY_REQUIRED("Trường cơ quan quản lý không được để trống!"),
	CHUCVU_REQUIRED("Trường chức vụ không được để trống!"),
	MATKHAU_REQUIRED("Trường mật khẩu không được để trống!"),
	TENDANGNHAP_REQUIRED("Trường tên đăng nhập không được để trống!"),
	TENDANGNHAP_EXISTS("Tên đăng nhập đã tồn tại trong hệ thống!"),
	TEN_EXISTS("Tên đã tồn tại trong hệ thống!"),
	MA_TEN_EXISTS("Mã hoặc tên đã tồn tại trong hệ thống!"),
	DATA_NOT_FOUND("Dữ liệu này không tồn tại trong hệ thống!"),
	ROLE_FORBIDDEN("Không có quyền thực hiện chức năng này!"),
	DATA_USED("Dữ liệu này đang được sử dụng!"),
	EMAIL_INVALID("Trường email không đúng định dạng!"),
	LOGIN_USER_PASSWORD_INCORRECT("Tài khoản đăng nhập hoặc mật khẩu không chính xác!"),
	LOGIN_INFOMATION_REQUIRED("Thông tin đăng nhập không được để trống!"),
	PROCESS_NOT_FOUND("Không tìm thấy process!"),
	TRANSITION_FORBIDDEN("Quy trình không đúng!");
	
	
	
	ApiErrorEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
