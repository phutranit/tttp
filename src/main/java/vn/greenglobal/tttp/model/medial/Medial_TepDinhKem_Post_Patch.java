package vn.greenglobal.tttp.model.medial;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.greenglobal.tttp.model.Model;
import vn.greenglobal.tttp.model.TepDinhKem;

@SuppressWarnings("serial")
@Entity
@Table(name = "medial_tepdinhkem")
public class Medial_TepDinhKem_Post_Patch extends Model<Medial_TepDinhKem_Post_Patch> {


	public Medial_TepDinhKem_Post_Patch() {
		this.setId(0l);
	}

	@Transient
	private List<TepDinhKem> tepDinhKems = new ArrayList<TepDinhKem>();

	public List<TepDinhKem> getTepDinhKems() {
		return tepDinhKems;
	}

	public void setTepDinhKems(List<TepDinhKem> tepDinhKems) {
		this.tepDinhKems = tepDinhKems;
	}
}
