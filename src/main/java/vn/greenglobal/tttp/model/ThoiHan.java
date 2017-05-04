package vn.greenglobal.tttp.model;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

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

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getThoiHanId() {
		return getId();
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
