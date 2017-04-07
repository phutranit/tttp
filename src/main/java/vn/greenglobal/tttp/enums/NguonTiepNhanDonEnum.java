package vn.greenglobal.tttp.enums;

public enum NguonTiepNhanDonEnum {

	TRUC_TIEP("Trực tiếp"),
	BUU_CHINH("Bưu chính"),
	DON_VI_KHAC("Đơn vị khác"),
	NGUON_KHAC("Nguồn khác"),
	LANH_DAO_TINH_THANH_PHO("Lãnh đạo Tỉnh/Thành phố");

	NguonTiepNhanDonEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
