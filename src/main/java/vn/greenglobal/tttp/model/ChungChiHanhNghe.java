package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.katharsis.resource.annotations.JsonApiResource;

@Entity
@Table(name = "chungchihanhnghe")
@JsonApiResource(type = "chungchihanhnghes")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class ChungChiHanhNghe extends Model<ChungChiHanhNghe> {

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