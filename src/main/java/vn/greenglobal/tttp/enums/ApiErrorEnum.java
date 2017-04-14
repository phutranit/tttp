package vn.greenglobal.tttp.enums;

public enum ApiErrorEnum {

	TEN_REQUIRED("Trường tên không được để trống!"),
	TEN_EXISTS("Tên đã tồn tại trong hệ thống!"),
	MA_TEN_EXISTS("Mã hoặc tên đã tồn tại trong hệ thống!"),
	DATA_NOT_FOUND("Dữ liệu này không tồn tại trong hệ thống!");

	ApiErrorEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
