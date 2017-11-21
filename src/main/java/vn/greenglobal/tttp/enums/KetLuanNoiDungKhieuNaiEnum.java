package vn.greenglobal.tttp.enums;

public enum KetLuanNoiDungKhieuNaiEnum {

	DUNG_TOAN_BO("Đúng toàn bộ"),
	SAI_TOAN_BO("Sai toàn bộ"),
	DUNG_MOT_PHAN("Đúng một phần"),
	KHAC("Khác");

	KetLuanNoiDungKhieuNaiEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
