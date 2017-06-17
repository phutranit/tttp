package vn.greenglobal.tttp.enums;

public enum ApiErrorEnum {

	LOAITEPDINHKEM_REQUIRED("Loại tệp đính kèm không được để trống!"),
	SOQUYETDINH_REQUIRED("Số quyết định không được để trống!"),
	NGAYQUYETDINH_REQUIRED("Ngày quyết định không được để trống!"),
	PHONG_BAN_GIAI_QUYET_REQUIRED("Trường phongBanGiaiQuyet không được để trống!"),
	HOVATEN_REQUIRED("Trường Họ và tên không được để trống!"),
	NGAYSINH_REQUIRED("Trường ngày sinh không được để trống!"),
	COQUANQUANLY_REQUIRED("Trường cơ quan quản lý không được để trống!"),
	CHUCVU_REQUIRED("Trường chức vụ không được để trống!"),
	MATKHAU_REQUIRED("Trường mật khẩu không được để trống!"),
	DON_REQUIRED("Trường đơn không được để trống!"),
	THONGTINGIAIQUYETDON_REQUIRED("Trường thongTinGiaiQuyetDon không được để trống!"),
	TENDANGNHAP_REQUIRED("Trường tên đăng nhập không được để trống!"),
	EMAIL_REQUIRED("Trường email không được để trống!"),
	TENDANGNHAP_EXISTS("Tên đăng nhập đã tồn tại trong hệ thống!"),
	TEN_EXISTS("Tên đã tồn tại trong hệ thống!"),
	DATA_EXISTS("Dữ liệu trùng với dữ liệu đã có trong hệ thống!"),
	EMAIL_EXISTS("Email đã tồn tại trong hệ thống!"),
	MA_TEN_EXISTS("Mã hoặc tên đã tồn tại trong hệ thống!"),
	CONGDAN_EXISTS("Công dân đã tồn tại trong hệ thống!"),
	DATA_NOT_FOUND("Dữ liệu này không tồn tại trong hệ thống!"),
	ROLE_FORBIDDEN("Không có quyền thực hiện chức năng này!"),
	CONGCHUC_FORBIDDEN("Không thể xóa tài khoản Administator!"),
	DATA_USED("Dữ liệu này đang được sử dụng!"),
	EMAIL_INVALID("Trường email không đúng định dạng!"),
	DATA_INVALID("Dữ liệu không hợp lệ!"),
	LOGIN_USER_PASSWORD_INCORRECT("Tài khoản đăng nhập hoặc mật khẩu không chính xác!"),
	USER_NOT_EXISTS("Tài khoản này không tồn tại trong hệ thống!"),
	LOGIN_INFOMATION_REQUIRED("Thông tin đăng nhập không được để trống!"),
	PROCESS_NOT_FOUND("Không tìm thấy quy trình!"),
	PROCESS_TTXM_NOT_FOUND("Không tìm thấy quy trình TTXM ở đơn vị được giao TTXM!"),
	PROCESS_GQD_NOT_FOUND("Không tìm thấy quy trình GQD ở đơn vị được giao GQD!"),
	DON_NOT_FOUND("Không tìm thấy đơn!"),
	PROCESS_TYPE_REQUIRED("Trường processType không được để trống!"),
	NEXT_STATE_REQUIRED("Trường nextState không được để trống!"),
	HUONGXULY_REQUIRED("Trường huongXuLy không được để trống!"),
	HUONGGIAIQUYET_REQUIRED("Trường huongGiaiQuyetTCDLanhDao không được để trống!"),
	TRANSITION_FORBIDDEN("Quy trình không đúng!"),
	TRANSITION_INVALID("Trạng thái không có trong quy trình!"),
	TRANSITION_TTXM_INVALID("Trạng thái TTXM không có trong quy trình!"),
	TRANSITION_GQD_INVALID("Trạng thái GQD không có trong quy trình!"),
	PHONGBANXULYCHIDINH_REQUIRED("Trường phongBanXuLyChiDinh không được để trống!"),
	CANBOXULYCHIDINH_REQUIRED("Trường canBoXuLyChiDinh không được để trống!"),
	DONVITHAMTRAXACMINH_REQUIRED("Trường donViThamTraXacMinh không được để trống!"),
	DONVICHUTRI_REQUIRED("Trường donViChuTri không được để trống!"),
	INTERNAL_SERVER_ERROR("Lỗi hệ thống!");
	
	ApiErrorEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
