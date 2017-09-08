package vn.greenglobal.tttp.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.VaiTroEnum;

@Entity
@Table(name = "vaitro")
@ApiModel
public class VaiTro extends Model<VaiTro> {

	private static final long serialVersionUID = -1541840816380863516L;

	public static final String VANTHU = "vanthu";
	public static final String CHUYENVIEN = "chuyenvien";
	public static final String TRUONGPHONG = "truongphong";
	public static final String LANHDAO = "lanhdao";

	public static final String[] VAITRO_DEFAULTS = { VANTHU, CHUYENVIEN, TRUONGPHONG, LANHDAO };

	@NotBlank
	@Size(max=255)
	private String ten = "";
	@NotBlank
	@Size(max=255)
	private String tenSearch = "";
	//@Lob
	@Transient
	private String quyen = "";

	@ElementCollection(targetClass = String.class, fetch = FetchType.EAGER)
	@CollectionTable(name = "vaitro_quyen", joinColumns = { @JoinColumn(name = "vaitro_id") })
	private Set<String> quyens = new HashSet<>();
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private VaiTroEnum loaiVaiTro;

	@ApiModelProperty(position = 1, required = true)
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@JsonIgnore
	public String getTenSearch() {
		return tenSearch;
	}

	public void setTenSearch(String tenSearch) {
		this.tenSearch = tenSearch;
	}

	@ApiModelProperty(position = 2, required = true)
	public String getQuyen() {
		quyen = StringUtils.collectionToCommaDelimitedString(getQuyens());
		if (quyen != null && !quyen.isEmpty()) {
			quyen = quyen.toUpperCase().replaceAll(":", "_");
		}
		return quyen;
	}

	public void setQuyen(String quyen) {
		this.quyen = quyen;
		setQuyens(quyen);
	}
	
	@ApiModelProperty(position = 3)
	public VaiTroEnum getLoaiVaiTro() {
		return loaiVaiTro;
	}

	public void setLoaiVaiTro(VaiTroEnum loaiVaiTro) {
		this.loaiVaiTro = loaiVaiTro;
	}

	@ApiModelProperty(hidden = true)
	public Set<String> getQuyens() {
		return quyens;
	}

	public void setQuyens(final Set<String> dsChoPhep) {
		quyens = dsChoPhep;
	}

	public void setQuyens(String quyens) {
		if (quyens != null && !quyens.isEmpty()) {
			Set<String> temp = new HashSet<>(Arrays.asList(quyens.toLowerCase().replaceAll("_", ":")));
			setQuyens(temp);
		}
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getVaiTroId() {
		return getId();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNguoiTaoInfo() {
		if (getNguoiTao() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getNguoiTao().getCoQuanQuanLy() != null ? getNguoiTao().getCoQuanQuanLy().getId() : 0);
			map.put("hoVaTen", getNguoiTao().getHoVaTen());
			map.put("congChucId", getNguoiTao().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNguoiSuaInfo() {
		if (getNguoiSua() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getNguoiSua().getCoQuanQuanLy() != null ? getNguoiSua().getCoQuanQuanLy().getId() : 0);
			map.put("hoVaTen", getNguoiSua().getHoVaTen());
			map.put("congChucId", getNguoiSua().getId());
			return map;
		}
		return null;
	}
}
