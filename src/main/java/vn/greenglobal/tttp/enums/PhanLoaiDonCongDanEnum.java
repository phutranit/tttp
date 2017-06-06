package vn.greenglobal.tttp.enums;

public enum PhanLoaiDonCongDanEnum {

	NGUOI_DUNG_DON("Người đứng đơn"),
	NGUOI_DUOC_UY_QUYEN("Người được ủy quyền");

	PhanLoaiDonCongDanEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
