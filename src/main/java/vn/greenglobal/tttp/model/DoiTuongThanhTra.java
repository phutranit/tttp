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

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.LoaiDoiTuongThanhTraEnum;

@Entity
@Table(name = "doituongthanhtra")
@ApiModel
public class DoiTuongThanhTra extends Model<DoiTuongThanhTra> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1362735902443705601L;

	@NotBlank
	@Size(max = 255)
	private String ten = "";
	@NotBlank
	@Size(max = 255)
	private String tenSearch = "";
	@Size(max = 255)
	private String diaChi = "";
	@Size(max = 255)
	private String diaChiSearch = "";
	@Size(max = 255)
	private String soDienThoai = "";
	//@Lob
	private String ghiChu = "";
	//@Lob
	private String ghiChuSearch = "";

	@NotNull
	@ManyToOne
	private LinhVucDoiTuongThanhTra linhVucDoiTuongThanhTra;

	@NotNull
	@Enumerated(EnumType.STRING)
	private LoaiDoiTuongThanhTraEnum loaiDoiTuongThanhTra;

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@JsonIgnore
	public String getTenSearch() {
		return tenSearch;
	}

	public void setTenSearch(String tenSearch) {
		this.tenSearch = tenSearch;
	}

	public String getDiaChi() {
		return diaChi;
	}

	public void setDiaChi(String diaChi) {
		this.diaChi = diaChi;
	}

	@JsonIgnore
	public String getDiaChiSearch() {
		return diaChiSearch;
	}

	public void setDiaChiSearch(String diaChiSearch) {
		this.diaChiSearch = diaChiSearch;
	}

	public String getSoDienThoai() {
		return soDienThoai;
	}

	public void setSoDienThoai(String soDienThoai) {
		this.soDienThoai = soDienThoai;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	@JsonIgnore
	public String getGhiChuSearch() {
		return ghiChuSearch;
	}

	public void setGhiChuSearch(String ghiChuSearch) {
		this.ghiChuSearch = ghiChuSearch;
	}

	@ApiModelProperty(example = "{}")
	public LinhVucDoiTuongThanhTra getLinhVucDoiTuongThanhTra() {
		return linhVucDoiTuongThanhTra;
	}

	public void setLinhVucDoiTuongThanhTra(LinhVucDoiTuongThanhTra linhVucDoiTuongThanhTra) {
		this.linhVucDoiTuongThanhTra = linhVucDoiTuongThanhTra;
	}

	public LoaiDoiTuongThanhTraEnum getLoaiDoiTuongThanhTra() {
		return loaiDoiTuongThanhTra;
	}

	public void setLoaiDoiTuongThanhTra(LoaiDoiTuongThanhTraEnum loaiDoiTuongThanhTra) {
		this.loaiDoiTuongThanhTra = loaiDoiTuongThanhTra;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getDoiTuongThanhTraId() {
		return getId();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getLinhVucDoiTuongThanhTraInfo() {
		if (getLinhVucDoiTuongThanhTra() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getLinhVucDoiTuongThanhTra().getTen());
			map.put("linhVucDoiTuongThanhTraId", getLinhVucDoiTuongThanhTra().getId());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getLoaiDoiTuongThanhTraInfo() {
		if (getLoaiDoiTuongThanhTra() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("text", getLoaiDoiTuongThanhTra().getText());
			map.put("type", getLoaiDoiTuongThanhTra().name());
			return map;
		}
		return null;
	}

}
