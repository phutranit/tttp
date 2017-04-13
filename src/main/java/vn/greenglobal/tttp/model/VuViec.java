package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "vuviec")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class VuViec extends Model<VuViec> {

	private String ten = "";

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}
}