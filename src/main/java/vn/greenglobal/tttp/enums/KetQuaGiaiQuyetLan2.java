package vn.greenglobal.tttp.enums;

public enum KetQuaGiaiQuyetLan2 {

	CONG_NHAN("Công nhận"),
	HUY("Hủy QĐ giải quyết lần 1"),
	SUA("Sửa QĐ giải quyết lần 1");
	
	KetQuaGiaiQuyetLan2(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
