package vn.greenglobal.tttp.enums;

public enum TenQuyTrinhEnum {

	QUY_TRINH_BAN_TIEP_CONG_DAN("Quy trình ban tiếp công dân thành phố"),
	QUY_TRINH_4_BUOC_DAY_DU("Quy trình 4 bước đầy đủ"),
	QUY_TRINH_1_BUOC_KHONG_DAY_DU("Quy trình 1 bước, không đầy đủ");

	TenQuyTrinhEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
