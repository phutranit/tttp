package vn.greenglobal.tttp.enums;

public enum LoaiVuViecEnum {

	CU("Cũ"),
	MOI_PHAT_SINH("Mới phát sinh");

	LoaiVuViecEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
