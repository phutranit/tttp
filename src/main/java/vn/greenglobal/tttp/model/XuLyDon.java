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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.querydsl.core.annotations.QueryInit;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.util.Utils;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;

@Entity
@Table(name = "xulydon")
@ApiModel
public class XuLyDon extends Model<XuLyDon> {
	private static final long serialVersionUID = -8406016838422350892L;

	@ManyToOne
	private Don don;
	@ManyToOne
	private CongChuc congChuc;
	@ManyToOne
	private ThamQuyenGiaiQuyet thamQuyenGiaiQuyet;
	@ManyToOne
	private CongChuc canBoXuLy;
	@ManyToOne
	private CongChuc canBoXuLyChiDinh;
	
	@QueryInit("*.*.*")
	@ManyToOne
	private CoQuanQuanLy phongBanXuLy;
	@QueryInit("*.*.*")
	@ManyToOne
	private CoQuanQuanLy donViXuLy;
	@ManyToOne
	private CoQuanQuanLy phongBanXuLyChiDinh;
	@ManyToOne
	private CoQuanQuanLy phongBanGiaiQuyet;
	@ManyToOne
	private CoQuanQuanLy coQuanTiepNhan;
	@ManyToOne
	private CoQuanQuanLy coQuanChuyenDon;
	private boolean donChuyen = false;
	private int thuTuThucHien = 0;
	@Size(max=255)
	private String diaDiem;
	@Size(max=255)
	private String soQuyetDinhDinhChi;
	
	@Transient
	private LocalDateTime thoiHanXuLy;
	private LocalDateTime ngayHenGapLanhDao;
	private LocalDateTime ngayQuyetDinhDinhChi;
	private boolean old;
	
	@ManyToOne
	private State nextState;
	
	@ManyToOne
	private Form nextForm;
	
	@Transient
	private Long soNgayCuaThoiHanXuLy;
	
	@Transient
	private Long soNgayXuLy;
	
	//@Enumerated(EnumType.STRING)
	//private QuyTrinhXuLyDonEnum quyTrinhXuLy;
	@Enumerated(EnumType.STRING)
	private HuongXuLyXLDEnum huongXuLy;

	@Enumerated(EnumType.STRING)
	private TrangThaiDonEnum trangThaiDon;
	
	//@Lob
	private String ghiChu = "";
	
	@Transient
	private String yKienXuLy = "";
	@Size(max=255)
	private String moTaTrangThai = "";
	@Transient
	private String noiDungYeuCauXuLy = "";
	@Transient
	private String noiDungThongTinTrinhLanhDao = "";
	//@Lob
	private String noiDungXuLy = "";
	
	@Enumerated(EnumType.STRING)
	private VaiTroEnum chucVu;
	@Enumerated(EnumType.STRING)
	private VaiTroEnum chucVuGiaoViec;

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	public boolean isOld() {
		return old;
	}

	public void setOld(boolean old) {
		this.old = old;
	}

	public Long getSoNgayXuLy() {
		return soNgayXuLy;
	}
	
	@ApiModelProperty(example = "{}", position = 8)
	public State getNextState() {
		return nextState;
	}
	
	public void setNextState(State nextState) {
		this.nextState = nextState;
	}

	@ApiModelProperty(hidden = true)
	public Form getNextForm() {
		return nextForm;
	}

	public void setNextForm(Form nextForm) {
		this.nextForm = nextForm;
	}
	
	public void setSoNgayXuLy(long soNgayXuLy) { 
		this.soNgayXuLy = soNgayXuLy;
	}
	
	public LocalDateTime getNgayQuyetDinhDinhChi() {
		return ngayQuyetDinhDinhChi;
	}

	public void setNgayQuyetDinhDinhChi(LocalDateTime ngayQuyetDinhDinhChi) {
		this.ngayQuyetDinhDinhChi = ngayQuyetDinhDinhChi;
	}

	public String getSoQuyetDinhDinhChi() {
		return soQuyetDinhDinhChi;
	}

	public void setSoQuyetDinhDinhChi(String soQuyetDinhDinhChi) {
		this.soQuyetDinhDinhChi = soQuyetDinhDinhChi;
	}

	public LocalDateTime getNgayHenGapLanhDao() {
		return ngayHenGapLanhDao;
	}

	public void setNgayHenGapLanhDao(LocalDateTime ngayHenGapLanhDao) {
		this.ngayHenGapLanhDao = ngayHenGapLanhDao;
	}

	public String getDiaDiem() {
		return diaDiem;
	}

	public void setDiaDiem(String diaDiem) {
		this.diaDiem = diaDiem;
	}

	public LocalDateTime getThoiHanXuLy() {
		return thoiHanXuLy;
	}

	public void setThoiHanXuLy(LocalDateTime thoiHanXuLy) {
		this.thoiHanXuLy = thoiHanXuLy;
	}
	
	@ApiModelProperty(position = 8)
	public VaiTroEnum getChucVuGiaoViec() {
		return chucVuGiaoViec;
	}

	public void setChucVuGiaoViec(VaiTroEnum chucVuGiaoViec) {
		this.chucVuGiaoViec = chucVuGiaoViec;
	}
	
	public TrangThaiDonEnum getTrangThaiDon() {
		return trangThaiDon;
	}

	public void setTrangThaiDon(TrangThaiDonEnum trangThaiDon) {
		this.trangThaiDon = trangThaiDon;
	}
	
	@ApiModelProperty(example = "{}", position = 7)
	public CoQuanQuanLy getCoQuanTiepNhan() {
		return coQuanTiepNhan;
	}

	public void setCoQuanTiepNhan(CoQuanQuanLy coQuanTiepNhan) {
		this.coQuanTiepNhan = coQuanTiepNhan;
	}

	@ApiModelProperty(example = "{}", position = 1)
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	@ApiModelProperty(example = "{}", position = 14)
	public CongChuc getCongChuc() {
		return congChuc;
	}

	public void setCongChuc(CongChuc congChuc) {
		this.congChuc = congChuc;
	}

	@ApiModelProperty(example = "{}")
	public ThamQuyenGiaiQuyet getThamQuyenGiaiQuyet() {
		return thamQuyenGiaiQuyet;
	}

	public void setThamQuyenGiaiQuyet(ThamQuyenGiaiQuyet thamQuyenGiaiQuyet) {
		this.thamQuyenGiaiQuyet = thamQuyenGiaiQuyet;
	}

	@ApiModelProperty(example = "{}", position = 6)
	public CoQuanQuanLy getPhongBanGiaiQuyet() {
		return phongBanGiaiQuyet;
	}

	public void setPhongBanGiaiQuyet(CoQuanQuanLy phongBanGiaiQuyet) {
		this.phongBanGiaiQuyet = phongBanGiaiQuyet;
	}

	@ApiModelProperty(hidden = true)
	public int getThuTuThucHien() {
		return thuTuThucHien;
	}

	public void setThuTuThucHien(int thuTuThucHien) {
		this.thuTuThucHien = thuTuThucHien;
	}

	@ApiModelProperty(hidden = true)
	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		if (ghiChu != null && ghiChu.length() == 0) {
			this.ghiChu = " ";
		} else {
			this.ghiChu = ghiChu;
		}
	}

	@ApiModelProperty(position = 4)
	public String getyKienXuLy() {
		return yKienXuLy;
	}

	public void setyKienXuLy(String yKienXuLy) {
		this.yKienXuLy = yKienXuLy;
	}

	@ApiModelProperty(position = 16)
	public String getMoTaTrangThai() {
		return moTaTrangThai;
	}

	public void setMoTaTrangThai(String moTaTrangThai) {
		this.moTaTrangThai = moTaTrangThai;
	}

	@ApiModelProperty(position = 18)
	public String getNoiDungThongTinTrinhLanhDao() {
		return noiDungThongTinTrinhLanhDao;
	}

	public void setNoiDungThongTinTrinhLanhDao(String noiDungThongTinTrinhLanhDao) {
		this.noiDungThongTinTrinhLanhDao = noiDungThongTinTrinhLanhDao;
	}

	@ApiModelProperty(position = 19)
	public String getNoiDungYeuCauXuLy() {
		return noiDungYeuCauXuLy;
	}
	
	public void setNoiDungYeuCauXuLy(String noiDungYeuCauXuLy) {
		this.noiDungYeuCauXuLy = noiDungYeuCauXuLy;
	}
	
	@ApiModelProperty(position = 13, example = "{}")
	public CongChuc getCanBoXuLyChiDinh() {
		return canBoXuLyChiDinh;
	}

	@ApiModelProperty(position = 12)
	public void setCanBoXuLyChiDinh(CongChuc canBoChiDinh) {
		this.canBoXuLyChiDinh = canBoChiDinh;
	}

	@ApiModelProperty(position = 4)
	public String getNoiDungXuLy() {
		return noiDungXuLy;
	}

	public void setNoiDungXuLy(String noiDungXuLy) {
		this.noiDungXuLy = noiDungXuLy;
	}
	
//	@ApiModelProperty(position = 5)
//	public QuyTrinhXuLyDonEnum getQuyTrinhXuLy() {
//		return quyTrinhXuLy;
//	}
//
//	public void setQuyTrinhXuLy(QuyTrinhXuLyDonEnum quyTrinhXuLy) {
//		this.quyTrinhXuLy = quyTrinhXuLy;
//	}

	@ApiModelProperty(position = 10)
	public HuongXuLyXLDEnum getHuongXuLy() {
		return huongXuLy;
	}

	public void setHuongXuLy(HuongXuLyXLDEnum huongXuLy) {
		this.huongXuLy = huongXuLy;
	}

	@ApiModelProperty(position = 2)
	public VaiTroEnum getChucVu() {
		return chucVu;
	}

	public void setChucVu(VaiTroEnum chucVu) {
		this.chucVu = chucVu;
	}

	@ApiModelProperty(example = "{}", position = 3)
	public CoQuanQuanLy getPhongBanXuLy() {
		return phongBanXuLy;
	}

	public void setPhongBanXuLy(CoQuanQuanLy phongBanXuLy) {
		this.phongBanXuLy = phongBanXuLy;
	}

	@ApiModelProperty(example = "{}", position = 15)
	public CongChuc getCanBoXuLy() {
		return canBoXuLy;
	}

	public void setCanBoXuLy(CongChuc canBoXuLy) {
		this.canBoXuLy = canBoXuLy;
	}
	
	@ApiModelProperty(example = "{}", position = 8)
	public CoQuanQuanLy getCoQuanChuyenDon() {
		return coQuanChuyenDon;
	}

	public void setCoQuanChuyenDon(CoQuanQuanLy coQuanChuyenDon) {
		this.coQuanChuyenDon = coQuanChuyenDon;
	}

	public boolean isDonChuyen() {
		return donChuyen;
	}

	public void setDonChuyen(boolean donChuyen) {
		this.donChuyen = donChuyen;
	}
	
	@ApiModelProperty(example = "{}", position = 8)
	public CoQuanQuanLy getPhongBanXuLyChiDinh() {
		return phongBanXuLyChiDinh;
	}

	public void setPhongBanXuLyChiDinh(CoQuanQuanLy phongBanXuLyChiDinh) {
		this.phongBanXuLyChiDinh = phongBanXuLyChiDinh;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNguoiTaoInfo() {
		if (getNguoiTao() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getNguoiTao().getCoQuanQuanLy() != null ? getNguoiTao().getCoQuanQuanLy().getId() : 0);
			map.put("hoVaTen", getNguoiTao().getHoVaTen());
			map.put("nhanVienId", getNguoiTao().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Long getNgayConLai() {
		return Utils.convertLocalDateTimeToNumber(this.getThoiHanXuLy());
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNguoiSuaInfo() {
		if (getNguoiSua() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getNguoiSua().getCoQuanQuanLy() != null ? getNguoiSua().getCoQuanQuanLy().getId() : 0);
			map.put("hoVaTen", getNguoiSua().getHoVaTen());
			map.put("nhanVienId", getNguoiSua().getId());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getSoNgayCuaThoiHanXuLy() {
		long soNgay = 0; 
		soNgay = Utils.convertLocalDateTimeToNumber(getNgayTao(), getThoiHanXuLy());
		return soNgay;
	}

	public void setSoNgayCuaThoiHanXuLy(Long soNgayCuaThoiHanXuLy) {
		this.soNgayCuaThoiHanXuLy = soNgayCuaThoiHanXuLy;
	}

	@ApiModelProperty(example = "{}", position = 8)
	public CoQuanQuanLy getDonViXuLy() {
		return donViXuLy;
	}

	public void setDonViXuLy(CoQuanQuanLy donViXuLy) {
		this.donViXuLy = donViXuLy;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getQuaTrinhXuLyInfo() {
		Map<String, Object> map = new HashMap<>();
		map.put("tenQuaTrinhXuLy", getNextState() != null ? getNextState().getTenVietTat() : "");
		map.put("ngayXuLy", getNgayTao());
		map.put("nguoiXuLy", getNguoiTao().getHoVaTen());
		map.put("noiDung", getNoiDungXuLy());
		
		return map;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getThongTinXuLyInfo() {
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> mapHuongXuLy = new HashMap<>();
		Map<String, Object> mapThamQuyenGiaiQuyet = new HashMap<>();
		Map<String, Object> mapPhongBanGiaiQuyet = new HashMap<>();
		Map<String, Object> mapCoQuanTiepNhan = new HashMap<>();
		Map<String, Object> mapCanBoXuLy = new HashMap<>();
		Map<String, Object> mapCanBoXuLyChiDinh = new HashMap<>();
		Map<String, Object> mapPhongBanChiDinh = new HashMap<>();
		
		map.put("quyTrinhXuLy", "");
		map.put("huongXuLy", "");
		map.put("thamQuyenGiaiQuyet", "");
		map.put("phongBanGiaiQuyet", "");
		map.put("yKienXuLy", getNoiDungXuLy());
		
		mapCanBoXuLy.put("id", getNguoiTao().getId());
		mapCanBoXuLy.put("ten", getNguoiTao().getHoVaTen());
		map.put("canBoXuLy", mapCanBoXuLy);
		
		mapPhongBanChiDinh.put("id", getPhongBanXuLyChiDinh() != null ?  getPhongBanXuLyChiDinh().getId() : "");
		mapPhongBanChiDinh.put("ten", getPhongBanXuLyChiDinh() != null ? getPhongBanXuLyChiDinh().getTen() : "");
		map.put("phongBanXuLyChiDinh", mapPhongBanChiDinh);
		
		mapCanBoXuLyChiDinh.put("id", getCanBoXuLyChiDinh() != null ? getCanBoXuLyChiDinh().getId() : "");
		mapCanBoXuLyChiDinh.put("ten", getCanBoXuLyChiDinh() != null ?getCanBoXuLyChiDinh().getHoVaTen() : "");
		map.put("canBoXuLyChiDinh", mapCanBoXuLyChiDinh);
		
		map.put("thoiHanXuLy", getDon() != null ? getDon().getThoiHanXuLyXLD() : "");
		
		if (getHuongXuLy() != null) {
			mapHuongXuLy.put("ten", getHuongXuLy().getText());
			mapHuongXuLy.put("giatTri", getHuongXuLy().name());
			if (getHuongXuLy().equals(HuongXuLyXLDEnum.DE_XUAT_THU_LY)) {
				mapPhongBanGiaiQuyet.put("id", getPhongBanGiaiQuyet() != null ? getPhongBanGiaiQuyet().getId() : "");
				mapPhongBanGiaiQuyet.put("ten", getPhongBanGiaiQuyet() != null ? getPhongBanGiaiQuyet().getTen() : "");
				mapThamQuyenGiaiQuyet.put("id", getThamQuyenGiaiQuyet() != null ? getThamQuyenGiaiQuyet().getId() : "");
				mapThamQuyenGiaiQuyet.put("ten", getThamQuyenGiaiQuyet() != null ? getThamQuyenGiaiQuyet().getTen() : "");				
			} else if (getHuongXuLy().equals(HuongXuLyXLDEnum.CHUYEN_DON)) {				
				mapCoQuanTiepNhan.put("id", getCoQuanTiepNhan() != null ? getCoQuanTiepNhan().getId() : "");
				mapCoQuanTiepNhan.put("ten", getCoQuanTiepNhan() != null ? getCoQuanTiepNhan().getTen() : "");	
				mapThamQuyenGiaiQuyet.put("id", getThamQuyenGiaiQuyet() != null ? getThamQuyenGiaiQuyet().getId() : "");
				mapThamQuyenGiaiQuyet.put("ten", getThamQuyenGiaiQuyet() != null ? getThamQuyenGiaiQuyet().getTen() : "");	
			} else if (getHuongXuLy().equals(HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY)
					|| getHuongXuLy().equals(HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN)
					|| getHuongXuLy().equals(HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI)) {
				mapThamQuyenGiaiQuyet.put("id", getThamQuyenGiaiQuyet() != null ? getThamQuyenGiaiQuyet().getId() : "");
				mapThamQuyenGiaiQuyet.put("ten", getThamQuyenGiaiQuyet() != null ? getThamQuyenGiaiQuyet().getTen() : "");	
			}
			map.put("phongBanGiaiQuyet", mapPhongBanGiaiQuyet);
			map.put("thamQuyenGiaiQuyet", mapThamQuyenGiaiQuyet);
			map.put("coQuanTiepNhan", mapCoQuanTiepNhan);
			map.put("huongXuLy", mapHuongXuLy);
		}
		if (getNextState() != null) {
			map.put("quyTrinhXuLy", getNextState().getTenVietTat());
		}
		return map;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Long getXuLyDonId() {
		return getId();
	}
}