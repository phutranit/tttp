package vn.greenglobal.tttp.enums;

public enum HinhThucTheoDoiEnum {

	GIAO_CO_QUAN_KHAC_THEO_DOI("Giao cơ quan khác theo dõi"),
	TU_THEO_DOI("Tự theo dõi");
	
	HinhThucTheoDoiEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
