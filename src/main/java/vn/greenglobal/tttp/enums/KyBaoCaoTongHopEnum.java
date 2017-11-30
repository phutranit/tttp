package vn.greenglobal.tttp.enums;

public enum KyBaoCaoTongHopEnum {

	THEO_QUY("Theo quý"),
	THEO_THANG("Theo tháng"),
	SAU_THANG("6 tháng"),
	CHIN_THANG("9 tháng"),
	NAM("Năm");

	KyBaoCaoTongHopEnum(String text) {
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
