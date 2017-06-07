package vn.greenglobal.tttp.model.medial;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.greenglobal.tttp.model.DoanDiCung;
import vn.greenglobal.tttp.model.Model;

@Entity
@Table(name = "medial_doandicung")
public class Medial_DoanDiCung_Post_Patch extends Model<Medial_DoanDiCung_Post_Patch> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4230336149189627026L;

	public Medial_DoanDiCung_Post_Patch() {
		this.setId(0l);
	}

	@Transient
	private List<DoanDiCung> doanDiCungs = new ArrayList<DoanDiCung>();

	public List<DoanDiCung> getDoanDiCungs() {
		return doanDiCungs;
	}

	public void setDoanDiCungs(List<DoanDiCung> doanDiCungs) {
		this.doanDiCungs = doanDiCungs;
	}

}
