package vn.greenglobal.tttp.enums;

public enum NguonTiepNhanDonEnum {

	TRUC_TIEP("Trực tiếp"),
	BUU_CHINH("Bưu chính"),
	LANH_DAO_THANH_PHO("Lãnh đạo thành phố"),
	KHAC("Khác");

	NguonTiepNhanDonEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
