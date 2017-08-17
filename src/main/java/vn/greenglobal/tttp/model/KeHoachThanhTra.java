package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.Application;

@Entity
@Table(name = "kehoachthanhtra")
@ApiModel
public class KeHoachThanhTra extends Model<KeHoachThanhTra> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4128391955224263426L;

	@Size(max=255)
	private String soQuyetDinh = "";
	@Lob
	private String ghiChu = "";
	
	private int kyThanhTra = 0;

	private LocalDateTime ngayRaQuyetDinh;
	
	public String getSoQuyetDinh() {
		return soQuyetDinh;
	}

	public void setSoQuyetDinh(String soQuyetDinh) {
		this.soQuyetDinh = soQuyetDinh;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public int getKyThanhTra() {
		return kyThanhTra;
	}

	public void setKyThanhTra(int kyThanhTra) {
		this.kyThanhTra = kyThanhTra;
	}

	public LocalDateTime getNgayRaQuyetDinh() {
		return ngayRaQuyetDinh;
	}

	public void setNgayRaQuyetDinh(LocalDateTime ngayRaQuyetDinh) {
		this.ngayRaQuyetDinh = ngayRaQuyetDinh;
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
				map.put("phamViThanhTra", ctt.getPhamViThanhTra());
				map.put("thoiHanThanhTra", ctt.getThoiHanThanhTra());
				map.put("linhVucThanhTra", ctt.getLinhVucThanhTra());
				map.put("linhVucThanhTraText", ctt.getLinhVucThanhTra().getText());
				list.add(map);
			}
		}
		return list;
	}

}
