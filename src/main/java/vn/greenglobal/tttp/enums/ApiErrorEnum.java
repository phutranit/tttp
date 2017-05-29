package vn.greenglobal.tttp.enums;

public enum ApiErrorEnum {

	LOAITEPDINHKEM_REQUIRED("Loại tệp đính kèm không được để trống!"),
	SOQUYETDINH_REQUIRED("Số quyết định không được để trống!"),
	NGAYQUYETDINH_REQUIRED("Ngày quyết định không được để trống!"),
	PHONG_BAN_GIAI_QUYET_REQUIRED("Trường phòng ban giải quyết không được để trống!"),
	HOVATEN_REQUIRED("Trường Họ và tên không được để trống!"),
	NGAYSINH_REQUIRED("Trường ngày sinh không được để trống!"),
	COQUANQUANLY_REQUIRED("Trường cơ quan quản lý không được để trống!"),
	CHUCVU_REQUIRED("Trường chức vụ không được để trống!"),
	MATKHAU_REQUIRED("Trường mật khẩu không được để trống!"),
	DON_REQUIRED("Trường đơn không được để trống!"),
	THONGTINGIAIQUYETDON_REQUIRED("Trường thongTinGiaiQuyetDon không được để trống!"),
	TENDANGNHAP_REQUIRED("Trường tên đăng nhập không được để trống!"),
	TENDANGNHAP_EXISTS("Tên đăng nhập đã tồn tại trong hệ thống!"),
	TEN_EXISTS("Tên đã tồn tại trong hệ thống!"),
	MA_TEN_EXISTS("Mã hoặc tên đã tồn tại trong hệ thống!"),
	CONGDAN_EXISTS("Công dân đã tồn tại trong hệ thống!"),
	DATA_NOT_FOUND("Dữ liệu này không tồn tại trong hệ thống!"),
	ROLE_FORBIDDEN("Không có quyền thực hiện chức năng này!"),
	CONGCHUC_FORBIDDEN("Không thể xóa tài khoản Administator!"),
	DATA_USED("Dữ liệu này đang được sử dụng!"),
	EMAIL_INVALID("Trường email không đúng định dạng!"),
	DATA_INVALID("Dữ liệu không hợp lệ!"),
	LOGIN_USER_PASSWORD_INCORRECT("Tài khoản đăng nhập hoặc mật khẩu không chính xác!"),
	LOGIN_INFOMATION_REQUIRED("Thông tin đăng nhập không được để trống!"),
	PROCESS_NOT_FOUND("Không tìm thấy quy trình!"),
	DON_NOT_FOUND("Không tìm thấy đơn!"),
	PROCESS_TYPE_REQUIRED("Trường processType không được để trống!"),
	NEXT_STATE_REQUIRED("Trường nextState không được để trống!"),
	HUONGXULY_REQUIRED("Trường huongXuLy không được để trống!"),
	HUONGGIAIQUYET_REQUIRED("Trường huongGiaiQuyetTCDLanhDao không được để trống!"),
	TRANSITION_FORBIDDEN("Quy trình không đúng!"),
	TRANSITION_INVALID("Trạng thái không có trong quy trình!");
		
	ApiErrorEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
