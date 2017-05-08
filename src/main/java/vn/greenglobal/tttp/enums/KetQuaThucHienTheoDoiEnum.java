package vn.greenglobal.tttp.enums;

public enum KetQuaThucHienTheoDoiEnum {

	DA_THUC_HIEN("Đã thực hiện"),
	CHUA_THUC_HIEN("Chưa thực hiện");
	
	KetQuaThucHienTheoDoiEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
