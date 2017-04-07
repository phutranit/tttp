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
@Table(name = "vuviec")
@JsonApiResource(type = "vuviecs")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class VuViec extends Model<VuViec> {

	private String ten = "";

	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private Don don;

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

}