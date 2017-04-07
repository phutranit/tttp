package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.katharsis.resource.annotations.JsonApiIncludeByDefault;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToOne;

@Entity
@Table(name = "capcoquanquanly")
@JsonApiResource(type = "capcoquanquanlys")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class CapCoQuanQuanLy extends Model<CapCoQuanQuanLy> {

	private String ma = "";
	private String ten = "";
	private String moTa = "";

	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private CapCoQuanQuanLy cha;
	
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

	public CapCoQuanQuanLy getCha() {
		return cha;
	}

	public void setCha(CapCoQuanQuanLy cha) {
		this.cha = cha;
	}

}