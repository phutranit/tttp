package vn.greenglobal.tttp.enums;

public enum LoaiFileDinhKemEnum {

	UY_QUYEN("Ủy quyền"),
	CHUNG_CHI_HANH_NGHE("Chứng chỉ hành nghề"),
	VAN_BAN_DA_GIAI_QUYET("Văn bản đã giải quyết");

	LoaiFileDinhKemEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
