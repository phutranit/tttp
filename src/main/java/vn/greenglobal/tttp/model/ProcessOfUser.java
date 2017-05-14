package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "wf_process_of_user")
@ApiModel
public class ProcessOfUser extends Model<ProcessOfUser>{

	private static final long serialVersionUID = 4673435892745663616L;

	@ManyToOne
	private Process process;
	@ManyToOne
	private CongChuc congChuc;
	
	public Process getProcess() {
		return process;
	}
	
	public void setProcess(Process process) {
		this.process = process;
	}
	
	public CongChuc getCongChuc() {
		return congChuc;
	}
	
	public void setCongChuc(CongChuc congChuc) {
		this.congChuc = congChuc;
	}
	

}
