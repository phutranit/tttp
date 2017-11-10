package vn.greenglobal.tttp.enums;

public enum PhanLoaiDonEnum {

	DU_DIEU_KIEN_XU_LY("Đủ điều kiện xử lý"),
	KHONG_DU_DIEU_KIEN_XU_LY("Không đủ điều kiện xử lý");

	PhanLoaiDonEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
