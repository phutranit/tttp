package vn.greenglobal.tttp.enums;

public enum LoaiBaoCaoTongHopEnum {

	KET_QUA_THANH_TRA_HANH_CHINH("1a - Tổng hợp kết quả thanh tra hành chính"),
	KET_QUA_THANH_TRA_TRONG_LINH_VUC_DAU_TU_XAY_DUNG_CO_BAN("1b - Tổng hợp kết quả thanh tra trong lĩnh vực đầu tư xây dựng cơ bản"),
	KET_QUA_THANH_TRA_TRONG_LINH_VUC_TAI_CHINH_NGAN_SACH("1c - Tổng hợp kết quả thanh tra trong lĩnh vực tài chính ngân sách"),
	KET_QUA_THANH_TRA_TRONG_LINH_VUC_DAT_DAI("1d - Tổng hợp kết quả thanh tra trong lĩnh vực đất đai"),
	KET_QUA_THANH_TRA_LAI("1đ - Tổng hợp kết quả thanh tra lại"),
	KET_QUA_THANH_TRA_KIEM_TRA_CHUYEN_NGANH("1e - Tổng hợp kết quả thanh tra, kiểm tra chuyên ngành"),
	KET_QUA_PHAT_HIEN_XU_LY_THAM_NHUNG_QUA_THANH_TRA("1g - Tổng hợp kết quả phát hiện, xử lý tham nhũng phát hiện qua công tác cuả ngành thanh tra"),
	CONG_TAC_QUAN_LY_NHA_NUOC_VE_THANH_TRA("1f - Công tác quản lý nhà nước về thanh tra"),
	CONG_TAC_XAY_DUNG_LUC_LUONG_THANH_TRA("1h - Tổng hợp công tác xây dựng lực lượng thanh tra"),
	KET_QUA_TIEP_CONG_DAN("2a - Tổng hợp kết quả tiếp công dân"),
	KET_QUA_XU_LY_DON_KHIEU_NAI_TO_CAO("2b - Tổng hợp kết quả xử lý đơn khiếu nại, tố cáo"),
	KET_QUA_GIAI_QUYET_DON_KHIEU_NAI("2c - Tổng hợp kết quả giải quyết đơn khiếu nại"),
	KET_QUA_GIAI_QUYET_DON_TO_CAO("2d - Tổng hợp kết quả giải quyết đơn tố cáo"),
	CONG_TAC_QUAN_LY_NHA_NUOC_VE_KHIEU_NAI_TO_CAO("2đ - Công tác quản lý nhà nước về Khiếu nại, Tố cáo"),
	KET_QUA_CHU_YEU_VE_CONG_TAC_PHONG_CHONG_THAM_NHUNG("3a - Kết quả chủ yếu về công tác phòng, chống tham nhũng "),
	SO_LIEU_KE_KHAI_XAC_MINH_TAI_SAN("4 - Biểu tổng hợp số liệu kê khai, công khai, xác minh tài sản, thu nhập");

	LoaiBaoCaoTongHopEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
