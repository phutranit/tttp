package vn.greenglobal.tttp.enums;

public enum QuyenEnum {

	THAM_QUYEN_GIAI_QUYET_THEM("Thêm mới"),
	THAM_QUYEN_GIAI_QUYET_CHINH_SUA("Chỉnh sửa"),
	THAM_QUYEN_GIAI_QUYET_XOA("Xoá"),
	
	VU_VIEC_THEM("Thêm mới"),
	VU_VIEC_CHINH_SUA("Chỉnh sửa"),
	VU_VIEC_XOA("Xoá"),
	
	LOAI_TAI_LIEU_THEM("Thêm mới"),
	LOAI_TAI_LIEU_CHINH_SUA("Chỉnh sửa"),
	LOAI_TAI_LIEU_XOA("Xoá"),
	
	CHUC_VU_THEM("Thêm mới"),
	CHUC_VU_CHINH_SUA("Chỉnh sửa"),
	CHUC_VU_XOA("Xoá"),
	
	QUOC_TICH_THEM("Thêm mới"),
	QUOC_TICH_CHINH_SUA("Chỉnh sửa"),
	QUOC_TICH_XOA("Xoá"),
	
	CAP_DON_VI_HANH_CHINH_THEM("Thêm mới"),
	CAP_DON_VI_HANH_CHINH_CHINH_SUA("Chỉnh sửa"),
	CAP_DON_VI_HANH_CHINH_XOA("Xoá"),
	
	DON_VI_HANH_CHINH_THEM("Thêm mới"),
	DON_VI_HANH_CHINH_CHINH_SUA("Chỉnh sửa"),
	DON_VI_HANH_CHINH_XOA("Xoá"),
	
	CAP_CO_QUAN_QUAN_LY_THEM("Thêm mới"),
	CAP_CO_QUAN_QUAN_LY_CHINH_SUA("Chỉnh sửa"),
	CAP_CO_QUAN_QUAN_LY_XOA("Xoá"),
	
	CO_QUAN_QUAN_LY_THEM("Thêm mới"),
	CO_QUAN_QUAN_LY_CHINH_SUA("Chỉnh sửa"),
	CO_QUAN_QUAN_LY_XOA("Xoá"),
	
	TO_DAN_PHO_THEM("Thêm mới"),
	TO_DAN_PHO_CHINH_SUA("Chỉnh sửa"),
	TO_DAN_PHO_XOA("Xoá"),

	DAN_TOC_THEM("Thêm mới"),
	DAN_TOC_CHINH_SUA("Chỉnh sửa"),
	DAN_TOC_XOA("Xoá"),
	
	LINH_VUC_DON_THU_THEM("Thêm mới"),
	LINH_VUC_DON_THU_CHINH_SUA("Chỉnh sửa"),
	LINH_VUC_DON_THU_XOA("Xoá"),
	
	CONG_CHUC_THEM("Thêm mới"),
	CONG_CHUC_CHINH_SUA("Chỉnh sửa"),
	CONG_CHUC_XOA("Xoá"),
	
	CONG_DAN_THEM("Thêm mới"),
	CONG_DAN_CHINH_SUA("Chỉnh sửa"),
	CONG_DAN_XOA("Xoá"),
	
	VAI_TRO_THEM("Thêm mới"),
	VAI_TRO_CHINH_SUA("Chỉnh sửa"),
	VAI_TRO_XOA("Xoá");

	QuyenEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
