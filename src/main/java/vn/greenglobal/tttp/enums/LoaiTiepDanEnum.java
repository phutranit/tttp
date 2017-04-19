package vn.greenglobal.tttp.enums;

public enum LoaiTiepDanEnum {

	TIEP_DAN_THUONG_XUYEN("Tiếp dân thường xuyên"),
	TIEP_DAN_DINH_KY_CUA_LANH_DAO("Tiếp dân định kỳ của lãnh đạo"),
	TIEP_DAN_DOT_XUAT_CUA_LANH_DAO("Tiếp dân đột xuất của lãnh đạo");

	LoaiTiepDanEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
