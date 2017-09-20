package vn.greenglobal.tttp.enums;

public enum NguonTiepNhanDonEnum {

	TRUC_TIEP("Trực tiếp"),
	BUU_CHINH("Bưu chính"),
	LANH_DAO_THANH_PHO("Lãnh đạo thành phố"),
	CO_QUAN_BAO_CHI("Cơ quan báo chí"),
	CHUYEN_DON("Chuyển đơn"),
	GIAO_TTXM("Giao thẩm tra xác minh"),
	GIAO_KTDX("Giao kiểm tra đề xuất"),
	KHAC("Khác");

	NguonTiepNhanDonEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
