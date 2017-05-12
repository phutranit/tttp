package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "wf_process_of_role")
@ApiModel
public class ProcessOfRole extends Model<ProcessOfRole>{

	private static final long serialVersionUID = 4673435892745663616L;

	@ManyToOne
	private Process process;
	@ManyToOne
	private VaiTro vaiTro;
	@ManyToOne
	private CoQuanQuanLy coQuan;
	
	public Process getProcess() {
		return process;
	}
	
	public void setProcess(Process process) {
		this.process = process;
	}
	
	public VaiTro getVaiTro() {
		return vaiTro;
	}
	
	public void setVaiTro(VaiTro vaiTro) {
		this.vaiTro = vaiTro;
	}
	
	public CoQuanQuanLy getCoQuan() {
		return coQuan;
	}
	
	public void setCoQuan(CoQuanQuanLy coQuan) {
		this.coQuan = coQuan;
	}
	
}
