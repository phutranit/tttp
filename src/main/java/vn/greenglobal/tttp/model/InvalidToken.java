package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;
import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "invalidtoken")
@ApiModel
public class InvalidToken extends Model<InvalidToken> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7550664635055554646L;

	//@Lob
	private String token = "";

	public InvalidToken() {
		super();
	}
	
	public InvalidToken(String currentToken) {
		this.token = currentToken;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}