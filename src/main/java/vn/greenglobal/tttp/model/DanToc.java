package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "dantoc")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class DanToc extends Model<DanToc> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7550664635055554646L;
	
	private String ma = "";
	private String ten = "";
	private String tenKhac = "";
	private String moTa = "";

	private boolean thieuSo;

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

	public String getTenKhac() {
		return tenKhac;
	}

	public void setTenKhac(String tenKhac) {
		this.tenKhac = tenKhac;
	}

	public boolean isThieuSo() {
		return thieuSo;
	}

	public void setThieuSo(boolean thieuSo) {
		this.thieuSo = thieuSo;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

}