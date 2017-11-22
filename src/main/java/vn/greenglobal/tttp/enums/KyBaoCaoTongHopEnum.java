package vn.greenglobal.tttp.enums;

public enum KyBaoCaoTongHopEnum {

	QUY_I("Quý I"),
	QUY_II("Quý II"),
	QUY_III("Quý III"),
	QUY_IV("Quý IV"),
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
