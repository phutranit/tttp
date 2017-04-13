package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "quoctich")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class QuocTich extends Model<QuocTich> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6346735397507161561L;
	
	private String ma = "";
	private String ten = "";
	private String moTa = "";

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

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

}