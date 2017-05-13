package vn.greenglobal.tttp.model.medial;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.Model;

@Entity
@Table(name = "medial_doncongdan")
public class Medial_DonCongDan_Post_Patch extends Model<Medial_DonCongDan_Post_Patch> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2472231640596764184L;

	public Medial_DonCongDan_Post_Patch() {
		this.setId(0l);
	}

	@Transient
	private List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();

	public List<Don_CongDan> getDonCongDans() {
		return donCongDans;
	}

	public void setDonCongDans(List<Don_CongDan> donCongDans) {
		this.donCongDans = donCongDans;
	}

}
