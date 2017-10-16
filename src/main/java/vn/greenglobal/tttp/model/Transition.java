package vn.greenglobal.tttp.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.TenQuyTrinhEnum;


@Entity
@Table(name = "wf_transition")
@ApiModel
public class Transition extends Model<Transition>{

	private static final long serialVersionUID = -1388936774978057856L;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private TenQuyTrinhEnum tenQuyTrinh;

	@NotNull
	@ManyToOne
	private Process process;
	
	@NotNull
	@ManyToOne
	private State currentState;
	
	@NotNull
	@ManyToOne
	private State nextState;
	
	@NotNull
	@ManyToOne
	private Form form;
	
	@ApiModelProperty(example = "{}")
	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	@ApiModelProperty(example = "{}")
	public State getNextState() {
		return nextState;
	}

	public void setNextState(State nextState) {
		this.nextState = nextState;
	}

	@ApiModelProperty(example = "{}")
	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public TenQuyTrinhEnum getTenQuyTrinh() {
		return tenQuyTrinh;
	}

	public void setTenQuyTrinh(TenQuyTrinhEnum tenQuyTrinh) {
		this.tenQuyTrinh = tenQuyTrinh;
	}

	@ApiModelProperty(example = "{}")
	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Long getTransitionId() {
		return getId();
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCurrentStateInfo() {
		if (getCurrentState() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("stateId", getCurrentState().getId());
			map.put("ten", getCurrentState().getTen());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNextStateInfo() {
		if (getNextState() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("stateId", getNextState().getId());
			map.put("ten", getNextState().getTen());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getProcessInfo() {
		if (getProcess() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("processId", getProcess().getId());
			map.put("ten", getProcess().getTenQuyTrinh());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getFormInfo() {
		if (getForm() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("formId", getForm().getId());
			map.put("ten", getForm().getTen());
			return map;
		}
		return null;
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
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getTenQuyTrinhInfo() {
		if (getTenQuyTrinh() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("text", getTenQuyTrinh().getText());
			map.put("type", getTenQuyTrinh().name());
			return map;
		}
		return null;
	}
}
