package vn.greenglobal.tttp.enums;

public enum ApiErrorEnum {

	LOAITEPDINHKEM_REQUIRED("Loại tệp đính kèm không được để trống."),
	SOQUYETDINH_REQUIRED("Số quyết định không được để trống."),
	NGAYQUYETDINH_REQUIRED("Ngày quyết định không được để trống."),
	PHONG_BAN_GIAI_QUYET_REQUIRED("Trường phongBanGiaiQuyet không được để trống."),
	CAN_BO_GIAI_QUYET_REQUIRED("Cán bộ giải quyết không được để trống."),
	DATA_REQUIRED("Bạn không được bỏ trống trường này."),
	HOVATEN_REQUIRED("Trường Họ và tên không được để trống."),
	NGAYSINH_REQUIRED("Trường ngày sinh không được để trống."),
	COQUANQUANLY_REQUIRED("Trường cơ quan quản lý không được để trống."),
	CHUCVU_REQUIRED("Trường chức vụ không được để trống."),
	MATKHAU_REQUIRED("Trường mật khẩu không được để trống."),
	QUYTRINHGIAIQUYET_REQUIRED("Trường quy trình giải quyết không được để trống."),
	TRANGTHAITTXM_REQUIRED("Trường trangThaiTTXM không được để trống."),
	THOIHANBAOCAOKETQUATTXM_REQUIRED("Trường thoiHanBaoCaoKetQuaTTXM không được để trống."),
	HANGIAIQUYET_REQUIRED("Trường hanGiaiQuyet không được để trống."),
	DON_REQUIRED("Trường đơn không được để trống."),
	THONGTINGIAIQUYETDON_REQUIRED("Trường thongTinGiaiQuyetDon không được để trống."),
	TENDANGNHAP_REQUIRED("Trường tên đăng nhập không được để trống."),
	EMAIL_REQUIRED("Trường email không được để trống."),
	TENDANGNHAP_EXISTS("Tên đăng nhập đã tồn tại trong hệ thống."),
	TEN_EXISTS("Tên đã tồn tại trong hệ thống."),
	EMAIL_EXISTS("Email đã tồn tại trong hệ thống."),
	MA_TEN_EXISTS("Mã hoặc tên đã tồn tại trong hệ thống."),
	CONGDAN_EXISTS("Công dân đã tồn tại trong hệ thống."),
	DATA_EXISTS("Dữ liệu này đã tồn tại trong hệ thống."),
	DATA_NOT_FOUND("Dữ liệu này không tồn tại trong hệ thống."),
	DOITUONGTHANHTRA_NOT_FOUND("Đối tượng thanh tra này không tồn tại trong hệ thống."),
	CUOCTHANHTRA_NOT_FOUND("Cuộc thanh tra này không tồn tại trong hệ thống."),
	ROLE_FORBIDDEN("Tài khoản hiện tại Không có quyền thực hiện chức năng này."),
	CONGCHUC_FORBIDDEN("Không thể xóa tài khoản Administator."),
	DATA_USED("Dữ liệu này đang được sử dụng."),
	DATA_KEHOACHTHANHTRA_USED("Kế hoạch thanh tra này đã có cuộc thanh tra đang tiến hành."),
	EMAIL_INVALID("Trường email không đúng định dạng."),
	DATA_INVALID("Dữ liệu không hợp lệ."),
	LOGIN_USER_PASSWORD_INCORRECT("Địa chỉ thư điện tử hoặc Mật khẩu không chính xác."),
	USER_NOT_EXISTS("Tài khoản này không tồn tại trong hệ thống."),
	LOGIN_INFOMATION_REQUIRED("Thông tin đăng nhập không được để trống."),
	PROCESS_NOT_FOUND("Không tìm thấy quy trình."),
	PROCESS_TTXM_NOT_FOUND("Không tìm thấy quy trình TTXM ở đơn vị được giao TTXM."),
	PROCESS_GQD_NOT_FOUND("Không tìm thấy quy trình GQD ở đơn vị được giao GQD."),
	DON_NOT_FOUND("Không tìm thấy đơn."),
	PROCESS_TYPE_REQUIRED("Trường processType không được để trống."),
	NEXT_STATE_REQUIRED("Trường nextState không được để trống."),
	HUONGXULY_REQUIRED("Trường huongXuLy không được để trống."),
	HUONGGIAIQUYET_REQUIRED("Trường huongGiaiQuyetTCDLanhDao không được để trống."),
	TRANSITION_FORBIDDEN("Quy trình không đúng."),
	TRANSITION_INVALID("Trạng thái không có trong quy trình."),
	TRANSITION_TTXM_INVALID("Trạng thái TTXM không có trong quy trình."),
	TRANSITION_GQD_INVALID("Trạng thái GQD không có trong quy trình."),
	PHONGBANXULYCHIDINH_REQUIRED("Trường phongBanXuLyChiDinh không được để trống."),
	CANBOXULYCHIDINH_REQUIRED("Trường canBoXuLyChiDinh không được để trống."),
	DONVITHAMTRAXACMINH_REQUIRED("Trường donViThamTraXacMinh không được để trống."),
	DONVICHUTRI_REQUIRED("Trường donViChuTri không được để trống."),
	INTERNAL_SERVER_ERROR("Lỗi hệ thống."),
	DON_VI_NOT_EXISTS("Đơn vị này không tồn tại."),
	MA_TRA_EXISTS("Ma trận của đơn vị này đã được tạo."),
	CUOCTHANHTRA_REQUIRED("Bạn phải chọn ít nhất một cuộc thanh tra."),
	DATA_INVALID_SIZE("Bạn đã nhập quá kí tự cho phép."),
	KEHOACHTHANHTRA_EXISTS("Đã có kế hoach thanh tra cho năm này."),
	TRANG_THAI_YCGLD_REQUIRED("Trường trangThaiYeuCauGapLanhDao không được bỏ trống."),
	LY_DO_THAY_DOI_TRANG_THAI_YCGLD_REQUIRED("Trường lyDoThayDoiTrangThaiYeuCauGapLanhDao không được bỏ trống.");
		
	ApiErrorEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
