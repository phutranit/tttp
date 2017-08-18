package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
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
	private String namThanhTra = "";
	@Size(max=255)
	private String nguoiKy = "";
	@NotBlank
	@Size(max=255)
	private String quyetDinhPheDuyetKTTT = "";
	@Lob
	private String ghiChu = "";

	@NotNull
	private LocalDateTime ngayRaQuyetDinh;
	
	@Enumerated(EnumType.STRING)
	private HinhThucKeHoachThanhTraEnum hinhThucKeHoachThanhTra;
	
	@Fetch(FetchMode.SELECT)
	@ManyToOne
	private CoQuanQuanLy donVi;

	public String getNamThanhTra() {
		return namThanhTra;
	}

	public void setNamThanhTra(String namThanhTra) {
		this.namThanhTra = namThanhTra;
	}

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
	public List<Map<String, Object>> getCuocThanhTras() {
		List<Map<String, Object>> list = new ArrayList<>();
		Map<String, Object> map = new HashMap<>();
		List<CuocThanhTra> cuocThanhTras = new ArrayList<CuocThanhTra>();
		cuocThanhTras = (List<CuocThanhTra>) Application.app.getCuocThanhTraRepository().findAll(QCuocThanhTra.cuocThanhTra.daXoa.eq(false));
		if (cuocThanhTras != null && cuocThanhTras.size() > 0) {
			for (CuocThanhTra ctt : cuocThanhTras) {
				map = new HashMap<>();
				map.put("id", ctt.getId());
				map.put("doiTuongThanhTraId", ctt.getDoiTuongThanhTra() == null ? 0 : ctt.getDoiTuongThanhTra().getId());
				map.put("tenDoiTuongThanhTra", ctt.getDoiTuongThanhTra() == null ? "" : ctt.getDoiTuongThanhTra().getTen());
				map.put("loaiDoiTuongThanhTra", ctt.getDoiTuongThanhTra() == null ? "" : ctt.getDoiTuongThanhTra().getLoaiDoiTuongThanhTra().getText());
				map.put("ghiChu", ctt.getGhiChu());
				map.put("noiDungThanhTra", ctt.getNoiDungThanhTra());
				map.put("phamViThanhTra", ctt.getKyThanhTra());
				map.put("thoiHanThanhTra", ctt.getThoiHanThanhTra());
				map.put("linhVucThanhTra", ctt.getLinhVucThanhTra());
				map.put("linhVucThanhTraText", ctt.getLinhVucThanhTra().getText());
				list.add(map);
			}
		}
		return list;
	}

}
