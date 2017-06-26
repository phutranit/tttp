package vn.greenglobal.tttp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;

@Entity
@Table(name = "wf_process")
@ApiModel
public class Process extends Model<Process>{

	private static final long serialVersionUID = -421604915416180673L;
	
	@NotBlank
	@Size(max=255)
	private String tenQuyTrinh;
	
	@Size(max=255)
	private String ghiChu;
	
	private boolean owner = false;
	
	@NotNull
	@ManyToOne
	private CoQuanQuanLy coQuanQuanLy;
	
	@NotNull
	@ManyToOne
	private VaiTro vaiTro;
	
	@OneToOne
	private Process cha;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private ProcessTypeEnum processType;
	
	@OneToMany(mappedBy = "process", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<Transition> transitions = new ArrayList<Transition>();
	
	public List<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}

	public String getTenQuyTrinh() {
		return tenQuyTrinh;
	}
	
	public void setTenQuyTrinh(String tenQuyTrinh) {
		this.tenQuyTrinh = tenQuyTrinh;
	}
	
	public String getGhiChu() {
		return ghiChu;
	}
	
	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	@ApiModelProperty(example = "{}")
	public CoQuanQuanLy getCoQuanQuanLy() {
		return coQuanQuanLy;
	}

	public void setCoQuanQuanLy(CoQuanQuanLy coQuanQuanLy) {
		this.coQuanQuanLy = coQuanQuanLy;
	}

	@ApiModelProperty(example = "{}")
	public VaiTro getVaiTro() {
		return vaiTro;
	}

	public void setVaiTro(VaiTro vaiTro) {
		this.vaiTro = vaiTro;
	}

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	@ApiModelProperty(example = "{}")
	public Process getCha() {
		return cha;
	}

	public void setCha(Process cha) {
		this.cha = cha;
	}

	public ProcessTypeEnum getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessTypeEnum processType) {
		this.processType = processType;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Long getProcessId() {
		return getId();
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public List<Transition> getListTransition() {
		List<Transition> list = new ArrayList<Transition>();
		for (Transition transition : getTransitions()) {
			if (!transition.isDaXoa()) {
				list.add(transition);
			}
		}
		return list;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getVaiTroInfo() {
		if (getVaiTro() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("vaiTroId", getVaiTro().getId());
			map.put("ten", getVaiTro().getTen());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCoQuanQuanLyInfo() {
		if (getCoQuanQuanLy() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getCoQuanQuanLy().getId());
			map.put("ten", getCoQuanQuanLy().getTen());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getChaInfo() {
		if (getCha() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("quyTrinhId", getCha().getId());
			map.put("ten", getCha().getTenQuyTrinh());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getProcessTypeInfo() {
		if (getProcessType() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getProcessType().getText());
			map.put("giaiTri", getProcessType().name());
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
}
