package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.Application;
import vn.greenglobal.tttp.enums.HinhThucKeHoachThanhTraEnum;

@Entity
@Table(name = "kehoachthanhtra")
@ApiModel
public class KeHoachThanhTra extends Model<KeHoachThanhTra> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4128391955224263426L;

	@Size(max=255)
	private String nguoiKy = "";
	@NotBlank
	@Size(max=255)
	private String quyetDinhPheDuyetKTTT = "";
	//@Lob
	private String ghiChu = "";
	@NotNull
	private int namThanhTra = 0;
	
	@NotNull
	private LocalDateTime ngayRaQuyetDinh;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private HinhThucKeHoachThanhTraEnum hinhThucKeHoachThanhTra;
	
	@Fetch(FetchMode.SELECT)
	@NotNull
	@ManyToOne
	private CoQuanQuanLy donVi;

	public String getNguoiKy() {
		return nguoiKy;
	}

	public void setNguoiKy(String nguoiKy) {
		this.nguoiKy = nguoiKy;
	}

	public String getQuyetDinhPheDuyetKTTT() {
		return quyetDinhPheDuyetKTTT;
	}

	public void setQuyetDinhPheDuyetKTTT(String quyetDinhPheDuyetKTTT) {
		this.quyetDinhPheDuyetKTTT = quyetDinhPheDuyetKTTT;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public int getNamThanhTra() {
		return namThanhTra;
	}

	public void setNamThanhTra(int namThanhTra) {
		this.namThanhTra = namThanhTra;
	}

	public LocalDateTime getNgayRaQuyetDinh() {
		return ngayRaQuyetDinh;
	}

	public void setNgayRaQuyetDinh(LocalDateTime ngayRaQuyetDinh) {
		this.ngayRaQuyetDinh = ngayRaQuyetDinh;
	}

	public HinhThucKeHoachThanhTraEnum getHinhThucKeHoachThanhTra() {
		return hinhThucKeHoachThanhTra;
	}

	public void setHinhThucKeHoachThanhTra(HinhThucKeHoachThanhTraEnum hinhThucKeHoachThanhTra) {
		this.hinhThucKeHoachThanhTra = hinhThucKeHoachThanhTra;
	}

	@ApiModelProperty( hidden = true )
	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}

	@Transient
	@ApiModelProperty( hidden = true )
	public Long getKeHoachThanhTraId() {
		return getId();
	}
	
	@Transient
	@ApiModelProperty( hidden = true )
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
	
	@Transient
	@ApiModelProperty( hidden = true )
	public List<Map<String, Object>> getCuocThanhTras() {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		cuocThanhTras = (List<CuocThanhTra>) Application.app.getCuocThanhTraRepository().findAll(QCuocThanhTra.cuocThanhTra.daXoa.eq(false));
		if (cuocThanhTras != null && cuocThanhTras.size() > 0) {
			for (CuocThanhTra ctt : cuocThanhTras) {
				map = new HashMap<>();
				map.put("id", ctt.getId());
				if (ctt.getDoiTuongThanhTra() != null) {
					Map<String, Object> mapDoiTuongThanhTra = new HashMap<>();
					mapDoiTuongThanhTra.put("doiTuongThanhTraId", ctt.getDoiTuongThanhTra().getId());
					mapDoiTuongThanhTra.put("ten", ctt.getDoiTuongThanhTra().getTen());
					map.put("doiTuongThanhTraInfo", mapDoiTuongThanhTra);
				} else {
					map.put("doiTuongThanhTraInfo", null);
				}
				if (ctt.getDonViChuTri() != null) {
					Map<String, Object> mapDonViChuTri = new HashMap<>();
					mapDonViChuTri.put("donViChuTriId", ctt.getDonViChuTri().getId());
					mapDonViChuTri.put("ten", ctt.getDonViChuTri().getTen());
					map.put("doiTuongThanhTraInfo", mapDonViChuTri);
				} else {
					map.put("doiTuongThanhTraInfo", null);
				}
				map.put("loaiDoiTuongThanhTra", ctt.getDoiTuongThanhTra() == null ? "" : ctt.getDoiTuongThanhTra().getLoaiDoiTuongThanhTra().getText());
				map.put("ghiChu", ctt.getGhiChu());
				map.put("noiDungThanhTra", ctt.getNoiDungThanhTra());
				if (ctt.getLinhVucThanhTra() != null) {
					Map<String, Object> mapLinhVucThanhTra = new HashMap<>();
					mapLinhVucThanhTra.put("type", ctt.getLinhVucThanhTra());
					mapLinhVucThanhTra.put("text", ctt.getLinhVucThanhTra().getText());
					map.put("linhVucThanhTraInfo", mapLinhVucThanhTra);
				} else {
					map.put("linhVucThanhTraInfo", null);
				}
				list.add(map);
			}
		}
		return list;
	}

}
