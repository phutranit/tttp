package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.VaiTroThanhVienDoanEnum;

@Entity
@Table(name = "thanhviendoan")
@ApiModel
public class ThanhVienDoan extends Model<ThanhVienDoan> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8313443941584469082L;

	@Size(max = 255)
	private String hoVaTen = "";
	@Size(max = 255)
	private String donVi = "";
	@Size(max = 255)
	private String chucVu = "";

	private LocalDateTime ngaySinh;

	@Enumerated(EnumType.STRING)
	private VaiTroThanhVienDoanEnum vaiTroThanhVienDoan;

	public String getHoVaTen() {
		return hoVaTen;
	}

	public void setHoVaTen(String hoVaTen) {
		this.hoVaTen = hoVaTen;
	}

	public String getDonVi() {
		return donVi;
	}

	public void setDonVi(String donVi) {
		this.donVi = donVi;
	}

	public String getChucVu() {
		return chucVu;
	}

	public void setChucVu(String chucVu) {
		this.chucVu = chucVu;
	}

	public LocalDateTime getNgaySinh() {
		return ngaySinh;
	}

	public void setNgaySinh(LocalDateTime ngaySinh) {
		this.ngaySinh = ngaySinh;
	}

	public VaiTroThanhVienDoanEnum getVaiTroThanhVienDoan() {
		return vaiTroThanhVienDoan;
	}

	public void setVaiTroThanhVienDoan(VaiTroThanhVienDoanEnum vaiTroThanhVienDoan) {
		this.vaiTroThanhVienDoan = vaiTroThanhVienDoan;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getThanhVienDoanId() {
		return getId();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getVaiTroThanhVienDoanInfo() {
		if (getVaiTroThanhVienDoan() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("text", getVaiTroThanhVienDoan().getText());
			map.put("type", getVaiTroThanhVienDoan().name());
			return map;
		}
		return null;
	}

}