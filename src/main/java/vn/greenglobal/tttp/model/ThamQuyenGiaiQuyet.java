package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "thamquyengiaiquyet")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
@ApiModel
public class ThamQuyenGiaiQuyet extends Model<ThamQuyenGiaiQuyet> {
	
	private String ten = "";
	private String moTa = "";

	@ManyToOne
	private ThamQuyenGiaiQuyet cha;

	@ApiModelProperty(position = 1, required = true)
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@ApiModelProperty(position = 2)
	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@ApiModelProperty(position = 3)
	public ThamQuyenGiaiQuyet getCha() {
		return cha;
	}

	public void setCha(ThamQuyenGiaiQuyet cha) {
		this.cha = cha;
	}
	
	@Transient
	@ApiModelProperty(hidden=true)
	public Long getThamQuyenGiaiQuyetId() {
		return getId();
	}

}