package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;

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
	private String ten;
	
	private String tenVietTat;
	
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
	public Long getStateId() {
		return getId();
	}
}
