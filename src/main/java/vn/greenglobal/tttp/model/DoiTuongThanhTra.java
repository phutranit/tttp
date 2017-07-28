package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "doituongthanhtra")
@ApiModel
public class DoiTuongThanhTra extends Model<DoiTuongThanhTra> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1362735902443705601L;
	
	private String ten = "";
	private String diaChi = "";
	private String ghiChu = "";
	
	private LinhVucDoiTuongThanhTra linhVucDoiTuongThanhTra;
	

}
