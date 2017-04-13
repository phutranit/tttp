package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "loaitailieus")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
@ApiModel
public class LoaiTaiLieu extends Model<LoaiTaiLieu> {

	private String ten = "";
	private String moTa = "";

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