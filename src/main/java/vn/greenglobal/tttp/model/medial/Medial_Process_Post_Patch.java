package vn.greenglobal.tttp.model.medial;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.greenglobal.tttp.model.Model;
import vn.greenglobal.tttp.model.Process;
import vn.greenglobal.tttp.model.Transition;

@Entity
@Table(name = "medial_process")
public class Medial_Process_Post_Patch extends Model<Medial_Process_Post_Patch> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 114111238601830817L;

	public Medial_Process_Post_Patch() {
		this.setId(0l);
	}

	@Transient
	private Process process;

	@Transient
	private List<Transition> transitions = new ArrayList<Transition>();

	public Process getProcess() {
		return process;
	}

	public void setProcess(Process process) {
		this.process = process;
	}

	public List<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(List<Transition> transitions) {
		this.transitions = transitions;
	}

}
