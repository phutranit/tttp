package vn.greenglobal.tttp.model;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "vaitro")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
@ApiModel
public class VaiTro extends Model<VaiTro> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1541840816380863516L;

	@NotBlank
	private String ten = "";
	@Lob
	private String quyen = "";

	@ManyToMany(mappedBy = "vaiTros", fetch = FetchType.LAZY)
	private Set<NguoiDung> nguoiDungs;

	@ApiModelProperty(position = 1, required = true)
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@ApiModelProperty(position = 2)
	public String getQuyen() {
		return quyen;
	}

	public void setQuyen(String quyen) {
		this.quyen = quyen;
	}

	@ApiModelProperty(position = 3)
	public Set<NguoiDung> getNguoiDungs() {
		return nguoiDungs;
	}

	public void setNguoiDungs(Set<NguoiDung> nguoiDungs) {
		this.nguoiDungs = nguoiDungs;
	}

}
