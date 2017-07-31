package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import vn.greenglobal.tttp.enums.LinhVucThanhTraEnum;

@Entity
@Table(name = "cuocthanhtra")
@ApiModel
public class CuocThanhTra extends Model<CuocThanhTra> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5826384114395527137L;
	
	private String noiDungThanhTra = "";
	private String phamViThanhTra = "";
	private String gh = "";
	
	private int thoiHan = 0;
	
	private LinhVucThanhTraEnum linhVucThanhTra;
	
	private DoiTuongThanhTra doiTuongThanhTra;
	

}
