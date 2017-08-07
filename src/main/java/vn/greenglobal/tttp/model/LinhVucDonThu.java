package vn.greenglobal.tttp.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.LoaiDonEnum;

@Entity
@Table(name = "linhvucdonthu")
@ApiModel
public class LinhVucDonThu extends Model<LinhVucDonThu> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6767282651849050987L;

	@Size(max=255)
	private String ma = "";
	@NotBlank
	@Size(max=255)
	private String ten = "";
	@Size(max=255)
	private String moTa = "";
	
	private int soThuTu = 0;

	private boolean linhVucKhac;

	@ManyToOne
	private LinhVucDonThu cha;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private LoaiDonEnum loaiDon;

	@ApiModelProperty(position = 1)
	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	@ApiModelProperty(position = 2, required = true)
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@ApiModelProperty(position = 3)
	public String getMoTa() {
		return moTa;
	}

	public void setMoTa(String moTa) {
		this.moTa = moTa;
	}

	public int getSoThuTu() {
		return soThuTu;
	}

	public void setSoThuTu(int soThuTu) {
		this.soThuTu = soThuTu;
	}

	@ApiModelProperty(position = 4)
	public boolean isLinhVucKhac() {
		return linhVucKhac;
	}

	public void setLinhVucKhac(boolean linhVucKhac) {
		this.linhVucKhac = linhVucKhac;
	}

	@ApiModelProperty(position = 5)
	public LinhVucDonThu getCha() {
		return cha;
	}

	public void setCha(LinhVucDonThu cha) {
		this.cha = cha;
	}

	public LoaiDonEnum getLoaiDon() {
		return loaiDon;
	}

	public void setLoaiDon(LoaiDonEnum loaiDon) {
		this.loaiDon = loaiDon;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getLinhVucDonThuId() {
		return getId();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public LinhVucDonThu getLinhVucDonThuCha() {
		return getCha();
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNguoiTaoInfo() {
		if (getNguoiTao() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getNguoiTao().getCoQuanQuanLy() != null ? getNguoiTao().getCoQuanQuanLy().getId() : 0);
			map.put("hoVaTen", getNguoiTao().getHoVaTen());
			map.put("congChucId", getNguoiTao().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNguoiSuaInfo() {
		if (getNguoiSua() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getNguoiSua().getCoQuanQuanLy() != null ? getNguoiSua().getCoQuanQuanLy().getId() : 0);
			map.put("hoVaTen", getNguoiSua().getHoVaTen());
			map.put("congChucId", getNguoiSua().getId());
			return map;
		}
		return null;
	}

}