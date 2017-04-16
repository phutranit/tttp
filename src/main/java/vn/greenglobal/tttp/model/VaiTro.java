package vn.greenglobal.tttp.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "vaitro")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class VaiTro extends Model<VaiTro>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1541840816380863516L;

	private String ma;
	private String ten;
	
	@ManyToMany(mappedBy="vaiTros", fetch = FetchType.LAZY)
	private Set<NguoiDung> nguoiDungs;

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
	
	public Set<NguoiDung> getNguoiDungs() {
		return nguoiDungs;
	}

	public void setNguoiDungs(Set<NguoiDung> nguoiDungs) {
		this.nguoiDungs = nguoiDungs;
	}

}
