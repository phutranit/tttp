package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;

@Entity
@Table(name = "wf_form")
@ApiModel
public class Form extends Model<Form> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3975638610686661750L;

	@NotBlank
	private String ten = "";
	@NotBlank
	private String alias = "";
	
	@Enumerated(EnumType.STRING)
	@NotNull
	private ProcessTypeEnum processType;

	@ApiModelProperty(position = 1, required = true)
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@ApiModelProperty(position = 2, required = true)
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public ProcessTypeEnum getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessTypeEnum processType) {
		this.processType = processType;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getFormId() {
		return getId();
	}
}