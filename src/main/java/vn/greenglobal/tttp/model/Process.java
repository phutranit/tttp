package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
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
	@ManyToOne
	private CoQuanQuanLy coQuanQuanLy;
	@ManyToOne
	private VaiTro vaiTro;
	private boolean owner;
	@OneToOne
	private Process cha;
	@Enumerated(EnumType.STRING)
	private ProcessTypeEnum processType;
	
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

	public CoQuanQuanLy getCoQuanQuanLy() {
		return coQuanQuanLy;
	}

	public void setCoQuanQuanLy(CoQuanQuanLy coQuanQuanLy) {
		this.coQuanQuanLy = coQuanQuanLy;
	}

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
}
