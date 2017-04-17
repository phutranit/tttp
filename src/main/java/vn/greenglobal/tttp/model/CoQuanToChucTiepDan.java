package vn.greenglobal.tttp.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "coquantochuctiepdan")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class CoQuanToChucTiepDan extends Model<CoQuanToChucTiepDan> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4101233843699537605L;

	private String ten = "";
	private String nguoiDaiDien = "";
	private String chucVu = "";

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "coquantochuctiepdan_has_sotiepcongdan", joinColumns = {
			@JoinColumn(name = "coquantochuctiepdan_id") }, inverseJoinColumns = {
					@JoinColumn(name = "sotiepcongdan_id") })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<SoTiepCongDan> soTiepDans;

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getNguoiDaiDien() {
		return nguoiDaiDien;
	}

	public void setNguoiDaiDien(String nguoiDaiDien) {
		this.nguoiDaiDien = nguoiDaiDien;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public List<SoTiepCongDan> getSoTiepDans() {
		return soTiepDans;
	}

	public void setSoTiepDans(List<SoTiepCongDan> soTiepDans) {
		this.soTiepDans = soTiepDans;
	}
}