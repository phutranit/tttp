package vn.greenglobal.tttp.enums;

public enum LyDoKhongDuDieuKienXuLyEnum {

	DON_KHONG_GHI_RO_NGAY_THANG_NAM("Đơn không ghi rõ ngày, tháng, năm viết đơn; họ, tên, địa chỉ, chữ kí hoặc điểm chỉ của người viết đơn"),
	DON_KHIEU_NAI_KHONG_GHI_RO_NOI_DUNG("Đơn khiếu nại không ghi rõ họ tên, địa chỉ cơ quan, tổ chức, đơn vị, cá nhân bị khiếu nại, nội dung, lý do khiếu nại và yêu cầu của người khiếu nại"),
	DON_TO_CAO_KHONG_GHI_RO_NOI_DUNG("Đơn tố cáo không ghi rõ nội dung tố cáo; cơ quan, tổ chức, đơn vị, cá nhân bị tố cáo, hành vi vi phạm pháp luật bị tố cáo"),
	DON_KIEN_NGHI_PHAN_ANH_KHONG_GHI_RO_NOI_DUNG("Đơn kiến nghị, phản ánh không ghi rõ nội dung kiến nghị, phản ánh"),
	DON_CHUA_DUOC_DON_VI_TIEP_NHAN_DON_XU_LY_THEO_PHAP_LUAT("Đơn chưa được cơ quan, tổ chức, đơn vị tiếp nhận xử lý theo quy định của pháp luật hoặc đã được xử lý nhưng người khiếu nại, người tố cáo được quyền khiếu nại, tố cáo tiếp theo quy định của pháp luật"),
	DON_DA_DUOC_GUI_CHO_NHIEU_CO_QUAN("Đơn đã được gửi cho nhiều cơ quan tổ chức, đơn vị, cá nhân trong đó đã gửi đến đúng cơ quan, tổ chức đơn vị hoặc đúng người có thẩm quyền giải quyết"),
	DON_DA_DUOC_HUONG_DAN_1_LAN_VE_CUNG_NOI_DUNG("Đơn đã được hướng dẫn một lần về cùng nội dung");

	LyDoKhongDuDieuKienXuLyEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
