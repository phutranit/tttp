package vn.greenglobal.tttp.enums;

public enum LoaiNguoiDungDonEnum {

	DOAN_DONG_NGUOI("Đoàn đông người"),
	CO_QUAN_TO_CHUC("Cơ quan - Tổ chức"),
	CA_NHAN("Cá nhân");
	
	LoaiNguoiDungDonEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
