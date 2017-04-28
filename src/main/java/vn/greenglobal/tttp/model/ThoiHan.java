package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.LoaiThoiHanEnum;

@Entity
@Table(name = "thoihan")
@ApiModel
public class ThoiHan extends Model<ThoiHan> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5794847516802209604L;
	@NotBlank
	private int soNgay = 0;
	@NotNull
	@Enumerated(EnumType.STRING)
	private LoaiThoiHanEnum loaiThoiHanEnum;

	@ApiModelProperty(position = 1)
	public int getSoNgay() {
		return soNgay;
	}

	public void setSoNgay(int soNgay) {
		this.soNgay = soNgay;
	}

	@ApiModelProperty(position = 1)
	public LoaiThoiHanEnum getLoaiThoiHanEnum() {
		return loaiThoiHanEnum;
	}

	public void setLoaiThoiHanEnum(LoaiThoiHanEnum loaiThoiHanEnum) {
		this.loaiThoiHanEnum = loaiThoiHanEnum;
	}
	
	public Long getThoiHanId() {
		return getId();
	}

}
