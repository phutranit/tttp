package vn.greenglobal.tttp.enums;

public enum KetLuanNoiDungKhieuNaiEnum {

	DUNG_TOAN_BO("Đúng toàn bộ");

	KetLuanNoiDungKhieuNaiEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
