package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name = "thamquyengiaiquyet")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class ThamQuyenGiaiQuyet extends Model<ThamQuyenGiaiQuyet> {

	@NotBlank
	private String ten = "";
	private String moTa = "";

	@ManyToOne
	private ThamQuyenGiaiQuyet cha;

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public ThamQuyenGiaiQuyet getCha() {
		return cha;
	}

	public void setCha(ThamQuyenGiaiQuyet cha) {
		this.cha = cha;
	}

}