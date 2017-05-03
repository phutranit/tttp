package vn.greenglobal.tttp.enums;

public enum LoaiThoiHanEnum {

	THOIHAN_XULYDON("Loại thời hạn xử lý đơn"),
	THOIHAN_GIAIQUYETKHIEUNAI("Loại thời hạn giải quyết khiếu nại"),
	THOIHAN_GIAIQUYETTOCAO("Loại thời hạn giải quyết tố cáo"),
	THOIHAN_KIENNGHIPHANANH("Loại thời hạn giải quyết kiến nghị phản ánh");

	LoaiThoiHanEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
