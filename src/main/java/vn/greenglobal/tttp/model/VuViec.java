package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "vuviec")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class VuViec extends Model<VuViec> {

	private String ten = "";
	private String noiDungVuViec = "";

	@ManyToOne
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

	public String getNoiDungVuViec() {
		return noiDungVuViec;
	}

	public void setNoiDungVuViec(String noiDungVuViec) {
		this.noiDungVuViec = noiDungVuViec;
	}
}