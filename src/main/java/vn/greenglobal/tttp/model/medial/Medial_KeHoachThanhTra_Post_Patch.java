package vn.greenglobal.tttp.model.medial;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import vn.greenglobal.tttp.model.KeHoachThanhTra;
import vn.greenglobal.tttp.model.Model;

@Entity
@Table(name = "medial_kehoachthanhtra")
public class Medial_KeHoachThanhTra_Post_Patch extends Model<Medial_KeHoachThanhTra_Post_Patch> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6105496722541447489L;

	public Medial_KeHoachThanhTra_Post_Patch() {
		this.setId(0l);
	}
	
	@JsonIgnore
	public Long getId() {
		return super.getId();
	}

	@Transient
	private KeHoachThanhTra keHoachThanhTra;
	@Transient
	private List<Medial_KeHoachThanhTra_CuocThanhTra_Post_Patch> cuocThanhTras = new ArrayList<Medial_KeHoachThanhTra_CuocThanhTra_Post_Patch>();

	public KeHoachThanhTra getKeHoachThanhTra() {
		return keHoachThanhTra;
	}

	public void setKeHoachThanhTra(KeHoachThanhTra keHoachThanhTra) {
		this.keHoachThanhTra = keHoachThanhTra;
	}

	public List<Medial_KeHoachThanhTra_CuocThanhTra_Post_Patch> getCuocThanhTras() {
		return cuocThanhTras;
	}

	public void setCuocThanhTras(List<Medial_KeHoachThanhTra_CuocThanhTra_Post_Patch> cuocThanhTras) {
		this.cuocThanhTras = cuocThanhTras;
	}

}
