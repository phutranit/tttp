package vn.greenglobal.tttp.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.TrangThaiInvalidTokenEnum;

@Entity
@Table(name = "invalidtoken")
@ApiModel
public class InvalidToken extends Model<InvalidToken> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7550664635055554646L;

	// @Lob
	private String token = "";

	private boolean active = true;

	@ManyToOne
	private NguoiDung nguoiDung;
	
	@Enumerated(EnumType.STRING)
	private TrangThaiInvalidTokenEnum trangThaiToken;
	
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

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public NguoiDung getNguoiDung() {
		return nguoiDung;
	}

	public void setNguoiDung(NguoiDung nguoiDung) {
		this.nguoiDung = nguoiDung;
	}

	public TrangThaiInvalidTokenEnum getTrangThaiToken() {
		return trangThaiToken;
	}

	public void setTrangThaiToken(TrangThaiInvalidTokenEnum trangThaiToken) {
		this.trangThaiToken = trangThaiToken;
	}
}