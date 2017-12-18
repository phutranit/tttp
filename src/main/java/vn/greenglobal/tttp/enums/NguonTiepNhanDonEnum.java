package vn.greenglobal.tttp.enums;

public enum NguonTiepNhanDonEnum {

	TRUC_TIEP("Trực tiếp"),
	BUU_CHINH("Bưu chính"),
	LANH_DAO_THANH_PHO("Lãnh đạo giao"),
	CO_QUAN_BAO_CHI("Cơ quan báo chí"),
	CHUYEN_DON("Chuyển đơn"),
	GIAO_TTXM("Giao thẩm tra xác minh"),
	GIAO_KTDX("Giao kiểm tra đề xuất"),
	GIAO_TDTH("Giao theo dõi thực hiện"),
	HOI_DONG_NHAN_DAN_TP("Hội đồng nhân dân TP"),
	DAI_BIEU_QUOC_HOI_TP("Đại biểu Quốc hội TP"),
	UBMTTQ_VIET_NAM_TP("UBMTTQ Việt Nam TP"),
	TRUNG_UONG("Trung ương"),
	KHAC("Khác");

	NguonTiepNhanDonEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
