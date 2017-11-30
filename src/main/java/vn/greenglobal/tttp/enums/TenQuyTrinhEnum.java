package vn.greenglobal.tttp.enums;

public enum TenQuyTrinhEnum {

	QUY_TRINH_BAN_TIEP_CONG_DAN_I("Quy trình ban tiếp công dân thành phố (CVNL hoặc CVXL > TruongPhong > CVXL)"),
	QUY_TRINH_BAN_TIEP_CONG_DAN_II("Quy trình ban tiếp công dân thành phố (CVNL > TruongPhong > CVXL)"),
	QUY_TRINH_4_BUOC_DAY_DU_I("Quy trình 4 bước đầy đủ (CVNL hoặc CVXL > LanhDao > TruongPhong > CVXL) --- (CVNL hoặc CVXL > LanhDao > Chuyển ngầm cho TruongPhong và chuyển thẳng cho CVXL)"),
	QUY_TRINH_4_BUOC_DAY_DU_II("Quy trình 4 bước đầy đủ (CVNL > LanhDao > TruongPhong > CVXL) --- (CVNL > LanhDao > Chuyển ngầm cho TruongPhong và chuyển thẳng cho CVXL)"),
	QUY_TRINH_1_BUOC_KHONG_DAY_DU("Quy trình 1 bước, không đầy đủ (Tất cả các vai trò đều như nhau, đều có thể xử lý ngay)");

	TenQuyTrinhEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
