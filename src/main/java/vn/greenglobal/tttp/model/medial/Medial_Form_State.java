package vn.greenglobal.tttp.model.medial;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import vn.greenglobal.tttp.model.Form;
import vn.greenglobal.tttp.model.Model;
import vn.greenglobal.tttp.model.State;

@Entity
@Table(name = "medial_form_state")
public class Medial_Form_State extends Model<Medial_Form_State>{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6526639173866366603L;
	
	public Medial_Form_State() {
		this.setId(0l);
	}
	
	
	@Transient
	private List<State> listNextStates = new ArrayList<State>();
	private Form currentForm;
	public List<State> getListNextStates() {
		return listNextStates;
	}
	public void setListNextStates(List<State> listNextStates) {
		this.listNextStates = listNextStates;
	}
	public Form getCurrentForm() {
		return currentForm;
	}
	public void setCurrentForm(Form currentForm) {
		this.currentForm = currentForm;
	}
}
