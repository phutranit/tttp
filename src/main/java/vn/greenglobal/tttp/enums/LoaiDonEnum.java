package vn.greenglobal.tttp.enums;

public enum LoaiDonEnum {

	DON_KHIEU_NAI("Đơn khiếu nại"),
	DON_TO_CAO("Đơn tố cáo"),
	DON_KIEN_NGHI_PHAN_ANH("Đơn kiến nghị, phản ánh"),
	DON_CO_NHIEU_NOI_DUNG_KHAC_NHAU("Đơn có nhiều nội dung khác nhau");

	LoaiDonEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
