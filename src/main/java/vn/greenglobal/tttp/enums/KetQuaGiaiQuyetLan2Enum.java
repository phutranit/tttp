package vn.greenglobal.tttp.enums;

public enum KetQuaGiaiQuyetLan2Enum {

	CONG_NHAN_QDGQ_LAN_I("Công nhận QĐ GQ lần 1"),
	HUY_SUA_QDGQ_LAN_I("Hủy, sửa QĐ GQ lần 1");
	
	KetQuaGiaiQuyetLan2Enum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
