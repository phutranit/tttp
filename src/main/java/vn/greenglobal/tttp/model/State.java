package vn.greenglobal.tttp.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.FlowStateEnum;

@Entity
@Table(name = "wf_state")
@ApiModel
public class State extends Model<State>{

	private static final long serialVersionUID = 814001972864916332L;

	@NotBlank
	@Size(max=255)
	private String ten;
	
	@NotBlank
	@Size(max=255)
	private String tenVietTat;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private FlowStateEnum type;
	
	public String getTen() {
		return ten;
	}
	
	public void setTen(String ten) {
		this.ten = ten;
	}
	
	public String getTenVietTat() {
		return tenVietTat;
	}

	public void setTenVietTat(String tenVietTat) {
		this.tenVietTat = tenVietTat;
	}

	public FlowStateEnum getType() {
		return type;
	}
	
	public void setType(FlowStateEnum type) {
		this.type = type;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getTypeInfo() {
		if (getType() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getType().getText());
			map.put("giaiTri", getType().name());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Long getStateId() {
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
