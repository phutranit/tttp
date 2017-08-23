package vn.greenglobal.tttp.enums;

public enum VaiTroThanhVienDoanEnum {

	TRUONG_DOAN("Trưởng đoàn"),
	PHO_DOAN("Phó đoàn"),
	THANH_VIEN("Thành viên");

	VaiTroThanhVienDoanEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
