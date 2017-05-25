package vn.greenglobal.tttp.enums;

public enum HinhThucTheoDoiEnum {

	GIAO_CO_QUAN_KHAC("Giao cơ quan khác theo dõi");
	
	HinhThucTheoDoiEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
