package vn.greenglobal.tttp.model.medial;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.greenglobal.tttp.model.Model;
import vn.greenglobal.tttp.model.TaiLieuVanThu;

@Entity
@Table(name = "medial_tailieuvanthu")
public class Medial_TaiLieuVanThu_Post_Patch extends Model<Medial_TaiLieuVanThu_Post_Patch> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2780710656837900538L;

	public Medial_TaiLieuVanThu_Post_Patch() {
		this.setId(0l);
	}

	@Transient
	private List<TaiLieuVanThu> taiLieuVanThus = new ArrayList<TaiLieuVanThu>();

	public List<TaiLieuVanThu> getTaiLieuVanThus() {
		return taiLieuVanThus;
	}

	public void setTaiLieuVanThus(List<TaiLieuVanThu> taiLieuVanThus) {
		this.taiLieuVanThus = taiLieuVanThus;
	}

}
