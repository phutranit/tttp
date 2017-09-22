package vn.greenglobal.tttp.enums;

public enum TrangThaiYeuCauGapLanhDaoEnum {
	DONG_Y("Đồng ý"),
	CHUA_DONG_Y("Chưa đồng ý"),
	KHONG_DONG_Y("Không đồng ý");

	TrangThaiYeuCauGapLanhDaoEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
