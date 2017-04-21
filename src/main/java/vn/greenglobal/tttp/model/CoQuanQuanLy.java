package vn.greenglobal.tttp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "coquanquanly")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
@ApiModel
public class CoQuanQuanLy extends Model<CoQuanQuanLy> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7182349007861458999L;
	
	private String ma = "";
	@NotBlank
	private String ten = "";
	private String moTa = "";

	@ManyToOne
	private CoQuanQuanLy cha;

	@ManyToOne
	private CapCoQuanQuanLy capCoQuanQuanLy;

	@OneToMany(mappedBy = "donVi", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	private List<CongChuc> congChucs = new ArrayList<CongChuc>(); // XLD
	
	public List<CongChuc> getCongChucs() {
		return congChucs;
	}

	public void setCongChucs(List<CongChuc> congChucs) {
		this.congChucs = congChucs;
	}
	
	@ApiModelProperty(position = 1)
	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}
	
	@ApiModelProperty(position = 2, required = true)
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@ApiModelProperty(position = 3)
	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	@ApiModelProperty(position = 4)
	public CoQuanQuanLy getCha() {
		return cha;
	}

	public void setCha(CoQuanQuanLy cha) {
		this.cha = cha;
	}

	@ApiModelProperty(position = 5, required = true, example="{}")
	public CapCoQuanQuanLy getCapCoQuanQuanLy() {
		return capCoQuanQuanLy;
	}

	public void setCapCoQuanQuanLy(CapCoQuanQuanLy capCoQuanQuanLy) {
		this.capCoQuanQuanLy = capCoQuanQuanLy;
	}
	
	@Transient
	@ApiModelProperty(hidden=true)
	public Long getCoQuanQuanLyId() {
		return getId();
	}
	
	@Transient
	@ApiModelProperty(hidden=true)
	public CoQuanQuanLy getCoQuanQuanLyCha() {
		return getCha();
	}
	
	@Transient
	@ApiModelProperty(hidden=true)
	public CapCoQuanQuanLy getCapCoQuanQuanLyCQQL() {
		return getCapCoQuanQuanLy();
	}

}