package vn.greenglobal.tttp.model.medial;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.greenglobal.tttp.model.CuocThanhTra;
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

	@Transient
	private String soQuyetDinh = "";
	@Transient
	private String nhiemVu = "";
	@Transient
	private int kyThanhTra = 0;
	@Transient
	private LocalDateTime ngayRaQuyetDinh;
	@Transient
	private List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();

	public String getSoQuyetDinh() {
		return soQuyetDinh;
	}

	public void setSoQuyetDinh(String soQuyetDinh) {
		this.soQuyetDinh = soQuyetDinh;
	}

	public String getNhiemVu() {
		return nhiemVu;
	}

	public void setNhiemVu(String nhiemVu) {
		this.nhiemVu = nhiemVu;
	}

	public int getKyThanhTra() {
		return kyThanhTra;
	}

	public void setKyThanhTra(int kyThanhTra) {
		this.kyThanhTra = kyThanhTra;
	}

	public LocalDateTime getNgayRaQuyetDinh() {
		return ngayRaQuyetDinh;
	}

	public void setNgayRaQuyetDinh(LocalDateTime ngayRaQuyetDinh) {
		this.ngayRaQuyetDinh = ngayRaQuyetDinh;
	}

	public List<CuocThanhTra> getCuocThanhTras() {
		return cuocThanhTras;
	}

	public void setCuocThanhTras(List<CuocThanhTra> cuocThanhTras) {
		this.cuocThanhTras = cuocThanhTras;
	}

}
