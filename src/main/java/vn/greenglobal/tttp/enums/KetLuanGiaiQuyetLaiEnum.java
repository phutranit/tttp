package vn.greenglobal.tttp.enums;

public enum KetLuanGiaiQuyetLaiEnum {

	DONG_Y("Dồng ý"),
	PHAN_BAC("Phản bác");
	
	KetLuanGiaiQuyetLaiEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
