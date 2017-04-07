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
@Table(name = "thamquyengiaiquyet")
@JsonApiResource(type = "thamquyengiaiquyets")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class ThamQuyenGiaiQuyet extends Model<ThamQuyenGiaiQuyet> {

	private String ten = "";
	private String moTa = "";

	@ManyToOne
	@JsonApiToOne
	@JsonApiIncludeByDefault
	private ThamQuyenGiaiQuyet cha;

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

	public ThamQuyenGiaiQuyet getCha() {
		return cha;
	}

	public void setCha(ThamQuyenGiaiQuyet cha) {
		this.cha = cha;
	}

}