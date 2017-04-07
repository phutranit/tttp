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
@Table(name = "giayuyquyen")
@JsonApiResource(type = "giayuyquyens")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class GiayUyQuyen extends Model<GiayUyQuyen> {

	private String ten = "";
	private String duongDan = "";

	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private CongDan congDan;

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

	public CongDan getCongDan() {
		return congDan;
	}

	public void setCongDan(CongDan congDan) {
		this.congDan = congDan;
	}
	
}