package vn.greenglobal.tttp.model.medial;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.greenglobal.tttp.model.DoiTuongViPham;
import vn.greenglobal.tttp.model.Model;

@Entity
@Table(name = "medial_doituongvipham")
public class Medial_DoiTuongViPham_Post_Patch extends Model<Medial_DoiTuongViPham_Post_Patch> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2780710656837900538L;

	public Medial_DoiTuongViPham_Post_Patch() {
		this.setId(0l);
	}

	@Transient
	private List<DoiTuongViPham> doiTuongViPhams = new ArrayList<DoiTuongViPham>();

	public List<DoiTuongViPham> getDoiTuongViPhams() {
		return doiTuongViPhams;
	}

	public void setDoiTuongViPhams(List<DoiTuongViPham> doiTuongViPhams) {
		this.doiTuongViPhams = doiTuongViPhams;
	}

}
