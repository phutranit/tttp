package vn.greenglobal.tttp.enums;

public enum CanCuThanhTraLaiEnum {

	VI_PHAM_TRINH_TU("Vi phạm trình tự, thủ tục dẫn đến sai lệch nghiêm trọng KL"),
	KET_LUAN_KHONG_PHU_HOP("Nội dung kết luận không phù hợp chứng cứ"),
	SAI_LAM_AP_DUNG_PHAP_LUAT("Sai lầm trong áp dụng PL khi KL"),
	CO_Y_LAM_SAI_LECH_HO_SO("Cố ý làm sai lệch hồ sơ hoặc cố ý KL trái pháp luật"),
	VI_PHAM_NGHIEM_TRONG("Vi phạm nghiêm trọng chưa được phát hiện đầy đủ");

	CanCuThanhTraLaiEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
