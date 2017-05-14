package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;


@Entity
@Table(name = "wf_transition")
@ApiModel
public class Transition extends Model<Transition>{

	private static final long serialVersionUID = -1388936774978057856L;

	private String ten;
	private String ghiChu;
	
	@ManyToOne
	private Process process;
	@ManyToOne
	private State currentState;
	@ManyToOne
	private State nextState;
	@ManyToOne
	private Form form;
	
	public String getTen() {
		return ten;
	}
	
	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	public State getNextState() {
		return nextState;
	}

	public void setNextState(State nextState) {
		this.nextState = nextState;
	}

	public Form getForm() {
		return form;
	}

	public void setForm(Form form) {
		this.form = form;
	}
}
