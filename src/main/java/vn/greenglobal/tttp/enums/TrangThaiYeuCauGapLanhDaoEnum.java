package vn.greenglobal.tttp.enums;

public enum TrangThaiYeuCauGapLanhDaoEnum {
	DONG_Y("Đồng ý"),
	CHO_XIN_Y_KIEN("Chờ xin ý kiến"),
	KHONG_DONG_Y("Không đồng ý");

	TrangThaiYeuCauGapLanhDaoEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
