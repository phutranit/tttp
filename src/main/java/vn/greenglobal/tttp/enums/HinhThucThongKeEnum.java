package vn.greenglobal.tttp.enums;

public enum HinhThucThongKeEnum {
	DON_VI("Đơn vị"),
	CAP_DON_VI("Cấp đơn vị");
	
	HinhThucThongKeEnum (String text) { 
		this.text = text;
	}

	public String getText() {
		return text;
	}

	private String text;
}
