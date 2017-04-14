package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "vuviec")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class VuViec extends Model<VuViec> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3736471703211724763L;
	
	@NotEmpty
	private String ten = "";
	private String noiDungVuViec = "";
	
	@ApiModelProperty(position = 1, required = true)
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@ApiModelProperty(position = 2)
	public String getNoiDungVuViec() {
		return noiDungVuViec;
	}

	public void setNoiDungVuViec(String noiDungVuViec) {
		this.noiDungVuViec = noiDungVuViec;
	}
	
	@Transient
	@ApiModelProperty(hidden=true)
	public Long getVuViecId() {
		return getId();
	}
}