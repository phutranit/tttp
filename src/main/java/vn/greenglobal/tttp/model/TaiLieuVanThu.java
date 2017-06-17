package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
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
import vn.greenglobal.tttp.enums.BuocGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.LoaiTepDinhKemEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;

@Entity
@Table(name = "tailieuvanthu")
@ApiModel
public class TaiLieuVanThu extends Model<TaiLieuVanThu> {
	private static final long serialVersionUID = -9223009647319074416L;

	@NotBlank
	@Size(max=255)
	private String ten = "";
	@Size(max=255)
	private String duongDan = "";
	@Size(max=255)
	private String tenFile = "";
	@Size(max=255)
	private String soQuyetDinh = "";
	
	private LocalDateTime ngayQuyetDinh;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private LoaiTepDinhKemEnum loaiTepDinhKem;
	@NotNull
	@Enumerated(EnumType.STRING)
	private ProcessTypeEnum loaiQuyTrinh;
	@Enumerated(EnumType.STRING)
	private BuocGiaiQuyetEnum buocGiaiQuyet;

	@ManyToOne
	private SoTiepCongDan soTiepCongDan;
	@NotNull
	@ManyToOne
	private Don don;

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getDuongDan() {
		return duongDan;
	}

	public void setDuongDan(String duongDan) {
		this.duongDan = duongDan;
	}

	public String getTenFile() {
		return tenFile;
	}

	public void setTenFile(String tenFile) {
		this.tenFile = tenFile;
	}

	public String getSoQuyetDinh() {
		return soQuyetDinh;
	}

	public void setSoQuyetDinh(String soQuyetDinh) {
		this.soQuyetDinh = soQuyetDinh;
	}

	public LocalDateTime getNgayQuyetDinh() {
		return ngayQuyetDinh;
	}

	public void setNgayQuyetDinh(LocalDateTime ngayQuyetDinh) {
		this.ngayQuyetDinh = ngayQuyetDinh;
	}

	@ApiModelProperty(example = "{}")
	public SoTiepCongDan getSoTiepCongDan() {
		return soTiepCongDan;
	}

	public void setSoTiepCongDan(SoTiepCongDan soTiepCongDan) {
		this.soTiepCongDan = soTiepCongDan;
	}

	@ApiModelProperty(example = "{}")
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}
	
	public LoaiTepDinhKemEnum getLoaiTepDinhKem() {
		return loaiTepDinhKem;
	}

	public void setLoaiTepDinhKem(LoaiTepDinhKemEnum loaiTepDinhKem) {
		this.loaiTepDinhKem = loaiTepDinhKem;
	}
	
	public ProcessTypeEnum getLoaiQuyTrinh() {
		return loaiQuyTrinh;
	}

	public void setLoaiQuyTrinh(ProcessTypeEnum loaiQuyTrinh) {
		this.loaiQuyTrinh = loaiQuyTrinh;
	}

	public BuocGiaiQuyetEnum getBuocGiaiQuyet() {
		return buocGiaiQuyet;
	}

	public void setBuocGiaiQuyet(BuocGiaiQuyetEnum buocGiaiQuyet) {
		this.buocGiaiQuyet = buocGiaiQuyet;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public String getLoaiTepDinhKemText() {
		return getLoaiTepDinhKem() != null ? getLoaiTepDinhKem().getText() : "";
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getTaiLieuVanThuId() {
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