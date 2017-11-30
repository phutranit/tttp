package vn.greenglobal.tttp.model.medial;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Model;

@Entity
@Table(name = "medial_congtacquanlynhanuocvethanhtra")
public class Medial_CongTacQuanLyNNVeThanhTra extends Model<Medial_CongTacQuanLyNNVeThanhTra> {

	private static final long serialVersionUID = 1L;

	@ManyToOne
	private CoQuanQuanLy donVi;
	
	private int soThuTu;
	
	private Long thucHienPLVeThanhTraSoCuoc;
	private Long thucHienPLVeThanhTraSoDonVi;
	private Long thucHienPLVeThanhTraKNTCPCTNSoCuoc;
	private Long thucHienPLVeThanhTraKNTCPCTNSoDonVi;
	
	private String ghiChu = "";

	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}
	
	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	public Long getThucHienPLVeThanhTraSoCuoc() {
		return thucHienPLVeThanhTraSoCuoc;
	}

	public void setThucHienPLVeThanhTraSoCuoc(Long thucHienPLVeThanhTraSoCuoc) {
		this.thucHienPLVeThanhTraSoCuoc = thucHienPLVeThanhTraSoCuoc;
	}

	public Long getThucHienPLVeThanhTraSoDonVi() {
		return thucHienPLVeThanhTraSoDonVi;
	}

	public void setThucHienPLVeThanhTraSoDonVi(Long thucHienPLVeThanhTraSoDonVi) {
		this.thucHienPLVeThanhTraSoDonVi = thucHienPLVeThanhTraSoDonVi;
	}

	public Long getThucHienPLVeThanhTraKNTCPCTNSoCuoc() {
		return thucHienPLVeThanhTraKNTCPCTNSoCuoc;
	}

	public void setThucHienPLVeThanhTraKNTCPCTNSoCuoc(Long thucHienPLVeThanhTraKNTCPCTNSoCuoc) {
		this.thucHienPLVeThanhTraKNTCPCTNSoCuoc = thucHienPLVeThanhTraKNTCPCTNSoCuoc;
	}

	public Long getThucHienPLVeThanhTraKNTCPCTNSoDonVi() {
		return thucHienPLVeThanhTraKNTCPCTNSoDonVi;
	}

	public void setThucHienPLVeThanhTraKNTCPCTNSoDonVi(Long thucHienPLVeThanhTraKNTCPCTNSoDonVi) {
		this.thucHienPLVeThanhTraKNTCPCTNSoDonVi = thucHienPLVeThanhTraKNTCPCTNSoDonVi;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getDonViInfo() {
		if (getDonVi() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getDonVi().getId());
			map.put("ten", getDonVi().getTen());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Long getCongTacQuanLyNhaNuocVeThanhTraId() {
		return getId();
	}
}
