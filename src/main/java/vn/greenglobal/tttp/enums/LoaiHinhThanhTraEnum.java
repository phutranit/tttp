package vn.greenglobal.tttp.enums;

public enum LoaiHinhThanhTraEnum {

	MOI("Mới"),
	THANH_TRA_LAI("Thanh tra lại");

	LoaiHinhThanhTraEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
