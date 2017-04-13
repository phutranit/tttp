package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "linhvucdonthu")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class LinhVucDonThu extends Model<LinhVucDonThu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6767282651849050987L;
	
	private String ma = "";
	private String ten = "";
	private String moTa = "";

	private boolean linhVucKhac;

	@ManyToOne
	private LinhVucDonThu cha;

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

	public boolean isLinhVucKhac() {
		return linhVucKhac;
	}

	public void setLinhVucKhac(boolean linhVucKhac) {
		this.linhVucKhac = linhVucKhac;
	}

	public LinhVucDonThu getCha() {
		return cha;
	}

	public void setCha(LinhVucDonThu cha) {
		this.cha = cha;
	}

}