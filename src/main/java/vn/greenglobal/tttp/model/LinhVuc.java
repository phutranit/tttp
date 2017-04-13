package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "linhvuc")
public class LinhVuc extends Model<LinhVuc> {
	private String ten = "";
	private String ghiChu = "";
	
	@ManyToOne
	private LinhVuc cha;
	
	private boolean linhVucKhac;

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public LinhVuc getCha() {
		return cha;
	}

	public void setCha(LinhVuc cha) {
		this.cha = cha;
	}

	public boolean isLinhVucKhac() {
		return linhVucKhac;
	}

	public void setLinhVucKhac(boolean linhVucKhac) {
		this.linhVucKhac = linhVucKhac;
	}

	@Transient
	public Long getLinhVucId() {
		return getId();
	}
}
