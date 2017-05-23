package vn.greenglobal.tttp.model.medial;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.greenglobal.tttp.model.Model;
import vn.greenglobal.tttp.model.TaiLieuBangChung;

@Entity
@Table(name = "medial_tailieubangchung")
public class Medial_TaiLieuBangChung_Post_Patch extends Model<Medial_TaiLieuBangChung_Post_Patch> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -587243419460457142L;

	public Medial_TaiLieuBangChung_Post_Patch() {
		this.setId(0l);
	}

	@Transient
	private List<TaiLieuBangChung> taiLieuBangChungs = new ArrayList<TaiLieuBangChung>();

	public List<TaiLieuBangChung> getTaiLieuBangChungs() {
		return taiLieuBangChungs;
	}

	public void setTaiLieuBangChungs(List<TaiLieuBangChung> taiLieuBangChungs) {
		this.taiLieuBangChungs = taiLieuBangChungs;
	}

}
