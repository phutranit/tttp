package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "loaivanban")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class LoaiVanBan extends Model<LoaiVanBan> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -860559220425480311L;
	
	private int soThuTu;
	private String ten = "";
	private String moTa = "";
	
	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}
	
}