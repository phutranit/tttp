package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "kehoachthanhtra")
@ApiModel
public class KeHoachThanhTra extends Model<KeHoachThanhTra> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4128391955224263426L;

	@Size(max=255)
	private String soQuyetDinh = "";
	@Size(max=255)
	private String kyThanhTra = "";

	private LocalDateTime ngayRaQuyetDinh;

	public String getSoQuyetDinh() {
		return soQuyetDinh;
	}

	public void setSoQuyetDinh(String soQuyetDinh) {
		this.soQuyetDinh = soQuyetDinh;
	}

	public String getKyThanhTra() {
		return kyThanhTra;
	}

	public void setKyThanhTra(String kyThanhTra) {
		this.kyThanhTra = kyThanhTra;
	}

	public LocalDateTime getNgayRaQuyetDinh() {
		return ngayRaQuyetDinh;
	}

	public void setNgayRaQuyetDinh(LocalDateTime ngayRaQuyetDinh) {
		this.ngayRaQuyetDinh = ngayRaQuyetDinh;
	}
	
	@Transient
	@ApiModelProperty( hidden = true )
	public Long getKeHoachThanhTraId() {
		return getId();
	}

}
