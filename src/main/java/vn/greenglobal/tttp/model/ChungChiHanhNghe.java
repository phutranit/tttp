package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "chungchihanhnghe")
@ApiModel
public class ChungChiHanhNghe extends Model<ChungChiHanhNghe> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3305792884790507318L;

	private String ma = "";
	private String ten = "";
	private String duongDan = "";

	@ManyToOne
	private Don_CongDan congDan;

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getDuongDan() {
		return duongDan;
	}

	public void setDuongDan(String duongDan) {
		this.duongDan = duongDan;
	}

	public Don_CongDan getCongDan() {
		return congDan;
	}

	public void setCongDan(Don_CongDan congDan) {
		this.congDan = congDan;
	}

}