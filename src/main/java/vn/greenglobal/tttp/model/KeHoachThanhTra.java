package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "kehoachthanhtra")
@ApiModel
public class KeHoachThanhTra extends Model<KeHoachThanhTra> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4128391955224263426L;
	
	private String soQuyetDinh = "";
	private String kyThanhTra = "";
	
	private LocalDateTime ngayRaQuyetDinh;

}
