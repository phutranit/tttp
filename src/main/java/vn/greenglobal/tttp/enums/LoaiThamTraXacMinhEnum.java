package vn.greenglobal.tttp.enums;

public enum LoaiThamTraXacMinhEnum {

	DUNG_HOAN_TOAN("Đúng hoàn toàn"),
	DUNG_MOT_PHAN("Đúng một phần"),
	SAI_HOAN_TOAN("Sai hoàn toàn");
	
	LoaiThamTraXacMinhEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
