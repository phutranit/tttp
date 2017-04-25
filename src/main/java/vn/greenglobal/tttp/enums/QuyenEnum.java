package vn.greenglobal.tttp.enums;

public enum QuyenEnum {

	THAMQUYENGIAIQUYET_LIETKE("Liệt kê"),
	THAMQUYENGIAIQUYET_XEM("Xem"),
	THAMQUYENGIAIQUYET_THEM("Thêm mới"),
	THAMQUYENGIAIQUYET_SUA("Chỉnh sửa"),
	THAMQUYENGIAIQUYET_XOA("Xoá"),
	
	VUVIEC_LIETKE("Liệt kê"),
	VUVIEC_XEM("Xem"),
	VUVIEC_THEM("Thêm mới"),
	VUVIEC_SUA("Chỉnh sửa"),
	VUVIEC_XOA("Xoá"),
	
	LOAITAILIEU_LIETKE("Liệt kê"),
	LOAITAILIEU_XEM("Xem"),
	LOAITAILIEU_THEM("Thêm mới"),
	LOAITAILIEU_SUA("Chỉnh sửa"),
	LOAITAILIEU_XOA("Xoá"),
	
	CHUCVU_LIETKE("Liệt kê"),
	CHUCVU_XEM("Xem"),
	CHUCVU_THEM("Thêm mới"),
	CHUCVU_SUA("Chỉnh sửa"),
	CHUCVU_XOA("Xoá"),
	
	QUOCTICH_LIETKE("Liệt kê"),
	QUOCTICH_XEM("Xem"),
	QUOCTICH_THEM("Thêm mới"),
	QUOCTICH_SUA("Chỉnh sửa"),
	QUOCTICH_XOA("Xoá"),
	
	CAPDONVIHANHCHINH_LIETKE("Liệt kê"),
	CAPDONVIHANHCHINH_XEM("Xem"),
	CAPDONVIHANHCHINH_THEM("Thêm mới"),
	CAPDONVIHANHCHINH_SUA("Chỉnh sửa"),
	CAPDONVIHANHCHINH_XOA("Xoá"),
	
	DONVIHANHCHINH_LIETKE("Liệt kê"),
	DONVIHANHCHINH_XEM("Xem"),
	DONVIHANHCHINH_THEM("Thêm mới"),
	DONVIHANHCHINH_SUA("Chỉnh sửa"),
	DONVIHANHCHINH_XOA("Xoá"),
	
	CAPCOQUANQUANLY_LIETKE("Liệt kê"),
	CAPCOQUANQUANLY_XEM("Xem"),
	CAPCOQUANQUANLY_THEM("Thêm mới"),
	CAPCOQUANQUANLY_SUA("Chỉnh sửa"),
	CAPCOQUANQUANLY_XOA("Xoá"),
	
	COQUANQUANLY_LIETKE("Liệt kê"),
	COQUANQUANLY_XEM("Xem"),
	COQUANQUANLY_THEM("Thêm mới"),
	COQUANQUANLY_SUA("Chỉnh sửa"),
	COQUANQUANLY_XOA("Xoá"),
	
	TODANPHO_LIETKE("Liệt kê"),
	TODANPHO_XEM("Xem"),
	TODANPHO_THEM("Thêm mới"),
	TODANPHO_SUA("Chỉnh sửa"),
	TODANPHO_XOA("Xoá"),

	DANTOC_LIETKE("Liệt kê"),
	DANTOC_XEM("Xem"),
	DANTOC_THEM("Thêm mới"),
	DANTOC_SUA("Chỉnh sửa"),
	DANTOC_XOA("Xoá"),
	
	LINHVUCDONTHU_LIETKE("Liệt kê"),
	LINHVUCDONTHU_XEM("Xem"),
	LINHVUCDONTHU_THEM("Thêm mới"),
	LINHVUCDONTHU_SUA("Chỉnh sửa"),
	LINHVUCDONTHU_XOA("Xoá"),
	
	CONGCHUC_LIETKE("Liệt kê"),
	CONGCHUC_XEM("Xem"),
	CONGCHUC_THEM("Thêm mới"),
	CONGCHUC_SUA("Chỉnh sửa"),
	CONGCHUC_XOA("Xoá"),
	
	CONGDAN_LIETKE("Liệt kê"),
	CONGDAN_XEM("Xem"),
	CONGDAN_THEM("Thêm mới"),
	CONGDAN_SUA("Chỉnh sửa"),
	CONGDAN_XOA("Xoá"),
	
	VAITRO_LIETKE("Liệt kê"),
	VAITRO_XEM("Xem"),
	VAITRO_THEM("Thêm mới"),
	VAITRO_SUA("Chỉnh sửa"),
	VAITRO_XOA("Xoá"),
	
	XULYDON_LIETKE("Liệt kê"),
	XULYDON_XEM("Xem"),
	XULYDON_THEM("Thêm mới"),
	XULYDON_SUA("Chỉnh sửa"),
	XULYDON_XOA("Xoá");

	QuyenEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
