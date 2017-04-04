package vn.greenglobal.tttp.enums;

public enum NguonTiepNhanDon {

	TRUC_TIEP("Trực tiếp"),
	BUU_CHINH("Bưu chính"),
	DON_VI_KHAC("Đơn vị khác"),
	NGUON_KHAC("Nguồn khác");

	NguonTiepNhanDon(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
