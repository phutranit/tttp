package vn.greenglobal.tttp.model;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "vaitro")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
@ApiModel
public class VaiTro extends Model<VaiTro> {

	private static final long serialVersionUID = -1541840816380863516L;

	public static final String VANTHU = "vanthu";
	public static final String CHUYENVIEN = "chuyenvien";
	public static final String TRUONGPHONG = "truongphong";
	public static final String LANHDAO = "lanhdao";
	
	public static final String[] VAITRO_DEFAULTS = { VANTHU, CHUYENVIEN, TRUONGPHONG, LANHDAO };

	@NotBlank
	private String ten = "";
	//@Lob
	@Transient
	private String quyen = "";

	@ElementCollection(fetch = FetchType.EAGER)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	@CollectionTable(name = "vaitro_quyen", joinColumns = {@JoinColumn(name = "vaitro_id")})
	private Set<String> quyens = new HashSet<>();
	
	
	@ApiModelProperty(position = 1, required = true)
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@ApiModelProperty(position = 2, required = true)
	public String getQuyen() {
		quyen = StringUtils.collectionToCommaDelimitedString(getQuyens());
		if(quyen!=null && !quyen.isEmpty()){
			quyen = quyen.toUpperCase();
		}
		return quyen;
	}

	public void setQuyen(String quyen) {
		this.quyen = quyen;
	}

	public Set<String> getQuyens() {
		return quyens;
	}
	
	public void setQuyens(final Set<String> dsChoPhep) {
		quyens = dsChoPhep;
	}
	
	public void setQuyens(String quyens) {
		if(quyens!=null && !quyens.isEmpty()){
			Set<String> temp = new HashSet<>(Arrays.asList(quyens.toLowerCase()));
			setQuyens(temp);
		}
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Long getVaiTroId() {
		return getId();
	}

}

