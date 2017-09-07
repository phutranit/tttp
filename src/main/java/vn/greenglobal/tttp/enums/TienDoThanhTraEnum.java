package vn.greenglobal.tttp.enums;

public enum TienDoThanhTraEnum {

	DANG_TIEN_HANH("Đang tiến hành"),
	KET_THUC_THANH_TRA_TRUC_TIEP("Kết thúc thanh tra trực tiếp"),
	DA_BAN_HANH_KET_LUAN("Đã ban hành Kết luận "),
	THEO_DOI_THUC_THIEN_KET_LUAN_THANH_TRA("Theo dõi thực hiện kết luận thanh tra");

	TienDoThanhTraEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
