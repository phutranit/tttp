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
@Table(name = "coquanquanly")
@JsonApiResource(type = "coquanquanlys")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class CoQuanQuanLy extends Model<CoQuanQuanLy> {

	private String ma = "";
	private String ten = "";
	private String moTa = "";

	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private CoQuanQuanLy cha;

	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private CapCoQuanQuanLy capCoQuanQuanLy;

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

	public CoQuanQuanLy getCha() {
		return cha;
	}

	public void setCha(CoQuanQuanLy cha) {
		this.cha = cha;
	}

	public CapCoQuanQuanLy getCapCoQuanQuanLy() {
		return capCoQuanQuanLy;
	}

	public void setCapCoQuanQuanLy(CapCoQuanQuanLy capCoQuanQuanLy) {
		this.capCoQuanQuanLy = capCoQuanQuanLy;
	}

}