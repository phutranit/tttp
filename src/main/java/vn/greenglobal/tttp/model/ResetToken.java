package vn.greenglobal.tttp.model;


import javax.persistence.Entity;
import javax.persistence.Table;
import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "resettoken")
@ApiModel
public class ResetToken extends Model<ResetToken> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5113927396215844471L;
	private String token = "";
	
	public ResetToken() {
	}
	
	public ResetToken(String token) {
		this.token = token;
	}

	public String getToken() {
		return token;
	}
	
	public void setToken(String token) {
		this.token = token;
	}
}