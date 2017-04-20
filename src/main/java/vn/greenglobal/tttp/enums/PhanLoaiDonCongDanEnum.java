package vn.greenglobal.tttp.enums;

public enum PhanLoaiDonCongDanEnum {

	NGUOI_DUNG_DON("Người đứng đơn"),
	THANH_VIEN_DOAN_NHIEU_NGUOI("Thành viên đoàn nhiều người"),
	NGUOI_DUOC_UY_QUYEN("Người được ủy quyền"),
	DOI_TUONG_BI_KHIEU_TO("Đối tượng bị khiếu tố");

	PhanLoaiDonCongDanEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
