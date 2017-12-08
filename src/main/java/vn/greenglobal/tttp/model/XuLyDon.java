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
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.LyDoKhongDuDieuKienXuLyEnum;
import vn.greenglobal.tttp.enums.PhanLoaiDonEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.TrangThaiTTXMEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.util.Utils;

@Entity
@Table(name = "xulydon")
@ApiModel
public class XuLyDon extends Model<XuLyDon> {
	private static final long serialVersionUID = -8406016838422350892L;

	@QueryInit("*.*")
	@ManyToOne
	private Don don;
	@ManyToOne
	private CongChuc congChuc;
	@ManyToOne
	private ThamQuyenGiaiQuyet thamQuyenGiaiQuyet;
	@ManyToOne
	private CongChuc canBoXuLy;
	@QueryInit("*.*.*")
	@ManyToOne
	private CongChuc canBoXuLyChiDinh;
	@ManyToOne
	private CongChuc canBoChuyenDon;
	@ManyToOne
	private CongChuc canBoGiaoViec;
	@ManyToOne
	private CongChuc truongPhongChiDinh;
	@ManyToOne
	private CongChuc chuyenVienChiDinh;
	
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
	private CoQuanQuanLy donViThamTraXacMinh;
	@ManyToOne
	private CongChuc canBoGiaiQuyet;
	@ManyToOne
	private CoQuanQuanLy coQuanTiepNhan;
	@ManyToOne
	private CoQuanQuanLy coQuanChuyenDon;
	@ManyToOne
	private DonViNgoaiHeThong donViNgoaiHeThong;
	
	private boolean donChuyen = false;
	private boolean donTra = false;
	private int thuTuThucHien = 0;
	@Size(max=255)
	private String diaDiem;
	@Size(max=255)
	private String soQuyetDinhDinhChi;
	
	@Transient
	private LocalDateTime thoiHanXuLy;
	private LocalDateTime ngayHenGapLanhDao;
	private LocalDateTime ngayQuyetDinhDinhChi;
	private LocalDateTime thoiHanBaoCaoKetQuaTTXM;
	private LocalDateTime hanGiaiQuyet;
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
	
	@Enumerated(EnumType.STRING)
	private TrangThaiTTXMEnum trangThaiTTXM;
	@Enumerated(EnumType.STRING)
	private PhanLoaiDonEnum phanLoaiDon;
	
	@Enumerated(EnumType.STRING)
	private LyDoKhongDuDieuKienXuLyEnum lyDoKhongDuDieuKienThuLy;
	
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
	private VaiTroEnum chucVu2;
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

	@JsonIgnore
	@ApiModelProperty(hidden = true)
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

	@ApiModelProperty(hidden = true)
	public CongChuc getCanBoGiaoViec() {
		return canBoGiaoViec;
	}

	public void setCanBoGiaoViec(CongChuc canBoGiaoViec) {
		this.canBoGiaoViec = canBoGiaoViec;
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

	@ApiModelProperty(hidden = true)
	public LocalDateTime getThoiHanXuLy() {
		return thoiHanXuLy;
	}

	public void setThoiHanXuLy(LocalDateTime thoiHanXuLy) {
		this.thoiHanXuLy = thoiHanXuLy;
	}
	
	@ApiModelProperty(example = "{}", position = 8)
	public CoQuanQuanLy getDonViThamTraXacMinh() {
		return donViThamTraXacMinh;
	}

	public void setDonViThamTraXacMinh(CoQuanQuanLy donViThamTraXacMinh) {
		this.donViThamTraXacMinh = donViThamTraXacMinh;
	}

	public LocalDateTime getThoiHanBaoCaoKetQuaTTXM() {
		return thoiHanBaoCaoKetQuaTTXM;
	}

	public void setThoiHanBaoCaoKetQuaTTXM(LocalDateTime thoiHanBaoCaoKetQuaTTXM) {
		this.thoiHanBaoCaoKetQuaTTXM = thoiHanBaoCaoKetQuaTTXM;
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
	
	@Transient
	public PhanLoaiDonEnum getPhanLoaiDon() {
		return phanLoaiDon;
	}

	public void setPhanLoaiDon(PhanLoaiDonEnum phanLoaiDon) {
		this.phanLoaiDon = phanLoaiDon;
	}

	@Transient
	public LyDoKhongDuDieuKienXuLyEnum getLyDoKhongDuDieuKienThuLy() {
		return lyDoKhongDuDieuKienThuLy;
	}

	public void setLyDoKhongDuDieuKienThuLy(LyDoKhongDuDieuKienXuLyEnum lyDoKhongDuDieuKienThuLy) {
		this.lyDoKhongDuDieuKienThuLy = lyDoKhongDuDieuKienThuLy;
	}

	@ApiModelProperty(example = "{}", position = 7)
	public CoQuanQuanLy getCoQuanTiepNhan() {
		return coQuanTiepNhan;
	}

	public void setCoQuanTiepNhan(CoQuanQuanLy coQuanTiepNhan) {
		this.coQuanTiepNhan = coQuanTiepNhan;
	}
	
	@ApiModelProperty(example = "{}", position = 7)
	public DonViNgoaiHeThong getDonViNgoaiHeThong() {
		return donViNgoaiHeThong;
	}

	public void setDonViNgoaiHeThong(DonViNgoaiHeThong donViNgoaiHeThong) {
		this.donViNgoaiHeThong = donViNgoaiHeThong;
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
	
	public LocalDateTime getHanGiaiQuyet() {
		return hanGiaiQuyet;
	}

	public void setHanGiaiQuyet(LocalDateTime hanGiaiQuyet) {
		this.hanGiaiQuyet = hanGiaiQuyet;
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
	
	@ApiModelProperty(example = "{}", position = 6)
	public CongChuc getCanBoGiaiQuyet() {
		return canBoGiaiQuyet;
	}

	public void setCanBoGiaiQuyet(CongChuc canBoGiaiQuyet) {
		this.canBoGiaiQuyet = canBoGiaiQuyet;
	}

	@JsonIgnore
	@ApiModelProperty(hidden = true)
	public int getThuTuThucHien() {
		return thuTuThucHien;
	}

	public void setThuTuThucHien(int thuTuThucHien) {
		this.thuTuThucHien = thuTuThucHien;
	}	
	
	public TrangThaiTTXMEnum getTrangThaiTTXM() {
		return trangThaiTTXM;
	}

	public void setTrangThaiTTXM(TrangThaiTTXMEnum trangThaiTTXM) {
		this.trangThaiTTXM = trangThaiTTXM;
	}

	@JsonIgnore
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

	@ApiModelProperty(hidden = true)
	public String getyKienXuLy() {
		return yKienXuLy;
	}

	public void setyKienXuLy(String yKienXuLy) {
		this.yKienXuLy = yKienXuLy;
	}

	@JsonIgnore
	@ApiModelProperty(position = 16)
	public String getMoTaTrangThai() {
		return moTaTrangThai;
	}

	public void setMoTaTrangThai(String moTaTrangThai) {
		this.moTaTrangThai = moTaTrangThai;
	}

	@ApiModelProperty(hidden = true)
	public String getNoiDungThongTinTrinhLanhDao() {
		return noiDungThongTinTrinhLanhDao;
	}

	public void setNoiDungThongTinTrinhLanhDao(String noiDungThongTinTrinhLanhDao) {
		this.noiDungThongTinTrinhLanhDao = noiDungThongTinTrinhLanhDao;
	}

	@ApiModelProperty(hidden = true)
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

	@ApiModelProperty(example = "{}")
	public CongChuc getCanBoChuyenDon() {
		return canBoChuyenDon;
	}

	public void setCanBoChuyenDon(CongChuc canBoChuyenDon) {
		this.canBoChuyenDon = canBoChuyenDon;
	}
	
	@ApiModelProperty(example = "{}")
	public CongChuc getTruongPhongChiDinh() {
		return truongPhongChiDinh;
	}

	public void setTruongPhongChiDinh(CongChuc truongPhongChiDinh) {
		this.truongPhongChiDinh = truongPhongChiDinh;
	}

	@ApiModelProperty(example = "{}")
	public CongChuc getChuyenVienChiDinh() {
		return chuyenVienChiDinh;
	}

	public void setChuyenVienChiDinh(CongChuc chuyenVienChiDinh) {
		this.chuyenVienChiDinh = chuyenVienChiDinh;
	}

	@ApiModelProperty(hidden = true)
	public String getNoiDungXuLy() {
		return noiDungXuLy;
	}

	public void setNoiDungXuLy(String noiDungXuLy) {
		this.noiDungXuLy = noiDungXuLy;
	}
	
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
	
	@ApiModelProperty(hidden = true)
	public VaiTroEnum getChucVu2() {
		return chucVu2;
	}

	public void setChucVu2(VaiTroEnum chucVu2) {
		this.chucVu2 = chucVu2;
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
	
	public boolean isDonTra() {
		return donTra;
	}

	public void setDonTra(boolean donTra) {
		this.donTra = donTra;
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
	@JsonIgnore
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
	public Map<String, Object> getNextFormInfo() {
		if (getNextForm() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getNextForm().getTen());
			map.put("alias", getNextForm().getAlias());
			return map;
		}
		return null;
	}

	@JsonIgnore
	@Transient
	public Long getSoNgayCuaThoiHanXuLy() {
		long soNgay = 0; 
		//soNgay = Utils.convertLocalDateTimeToNumber(getNgayTao(), getThoiHanXuLy());
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
		Map<String, Object> mapCoQuanChuyenDon = null;
		Map<String, Object> mapCanBoXuLy =  new HashMap<>();
		Map<String, Object> mapCanBoXuLyChiDinh = null;
		Map<String, Object> mapCanBoChuyenDon = null;
		Map<String, Object> mapPhongBanChiDinh = null;
		Map<String, Object> mapYeuCauGapLanhDao = new HashMap<String, Object>();
		Map<String, Object> mapTrangThaiXuLyDon = null;
		Map<String, Object> mapChucVuGiaoViec = null;
		Map<String, Object> mapNextState = null;
		Map<String, Object> mapTruongPhongChiDinh = null;
		Map<String, Object> mapChuyenVienChiDinh = null;
		Map<String, Object> mapDonViXuLyXLD = null;
		Map<String, Object> mapDinhChi = null;
		Map<String, Object> mapThamQuyenGiaiQuyet = null;
		Map<String, Object> mapPhongBanGiaiQuyet = null;
		Map<String, Object> mapCoQuanTiepNhan = null;
		Map<String, Object> mapTrangThaiTTXM = null;
		Map<String, Object> donViTTXM = null;
		Map<String, Object> mapDonViNgoaiHeThong = null;
		
		map.put("xuLyDonId", getId());
		map.put("quyTrinhXuLy", "");
		map.put("huongXuLy", null);
		map.put("thamQuyenGiaiQuyet", null);
		map.put("phongBanGiaiQuyet", null);
		map.put("yKienXuLy", getNoiDungXuLy());
		map.put("phanLoaiDonInfo", null);
		map.put("lyDoKhongDuDieuKienThuLyInfo", null);
		map.put("donViNgoaiHeThong", null);
		
		mapCanBoXuLy.put("id", getNguoiTao().getId());
		mapCanBoXuLy.put("ten", getNguoiTao().getHoVaTen());
		map.put("canBoXuLy", mapCanBoXuLy);
		
		if (getPhongBanXuLyChiDinh() != null) {
			mapPhongBanChiDinh = new HashMap<String, Object>();
			mapPhongBanChiDinh.put("id", getPhongBanXuLyChiDinh().getId());
			mapPhongBanChiDinh.put("ten", getPhongBanXuLyChiDinh().getTen());
		}
		map.put("phongBanXuLyChiDinh", mapPhongBanChiDinh);
		
		if (getCanBoXuLyChiDinh() != null) {
			mapCanBoXuLyChiDinh = new HashMap<String, Object>();
			mapCanBoXuLyChiDinh.put("id", getCanBoXuLyChiDinh().getId());
			mapCanBoXuLyChiDinh.put("ten", getCanBoXuLyChiDinh().getHoVaTen());
		}
		map.put("canBoXuLyChiDinh", mapCanBoXuLyChiDinh);
		
		if (getTruongPhongChiDinh() != null) { 
			mapTruongPhongChiDinh = new HashMap<String, Object>();
			mapTruongPhongChiDinh.put("id", getTruongPhongChiDinh().getId());
			mapTruongPhongChiDinh.put("ten", getTruongPhongChiDinh().getHoVaTen());
		}
		map.put("truongPhongChiDinh", mapTruongPhongChiDinh);
		
		if (getChuyenVienChiDinh() != null) { 
			mapChuyenVienChiDinh = new HashMap<String, Object>();
			mapChuyenVienChiDinh.put("id", getChuyenVienChiDinh().getId());
			mapChuyenVienChiDinh.put("ten", getChuyenVienChiDinh().getHoVaTen());
		}
		map.put("chuyenVienChiDinh", mapChuyenVienChiDinh);
		
		map.put("thoiHanXuLy", getDon() != null ? getDon().getThoiHanXuLyXLD() : "");
		
		if (getCoQuanChuyenDon() != null) {
			mapCoQuanChuyenDon = new HashMap<String, Object>();
			mapCoQuanChuyenDon.put("id", getCoQuanChuyenDon().getDonVi().getId());
			mapCoQuanChuyenDon.put("ten", getCoQuanChuyenDon().getDonVi().getTen());
		}
		map.put("coQuanChuyenDon", mapCoQuanChuyenDon);
		
		if (getCanBoChuyenDon() != null) {
			mapCanBoChuyenDon = new HashMap<String, Object>();
			mapCanBoChuyenDon.put("id", getCanBoChuyenDon().getId());
			mapCanBoChuyenDon.put("ten", getCanBoChuyenDon().getHoVaTen());
		}
		map.put("canBoChuyenDon", mapCanBoChuyenDon);
		
		if (getDonViXuLy() != null) {
			mapDonViXuLyXLD = new HashMap<String, Object>();
			mapDonViXuLyXLD.put("id", getDonViXuLy().getId());
			mapDonViXuLyXLD.put("ten", getDonViXuLy().getTen());
		}
		map.put("donViXuLy", mapDonViXuLyXLD);
		
		if (getNextState() != null) {
			mapNextState = new HashMap<String, Object>();
			mapNextState.put("id", getNextState().getId());
			mapNextState.put("type", getNextState().getType());
			map.put("quyTrinhXuLy", getNextState().getTenVietTat());
		}
		map.put("nextState", mapNextState);

		if (getTrangThaiDon() != null) {
			mapTrangThaiXuLyDon = new HashMap<String, Object>();
			mapTrangThaiXuLyDon.put("ten", getTrangThaiDon().getText());
			mapTrangThaiXuLyDon.put("enum", getTrangThaiDon().name());
		}
		map.put("trangThaiXuLyDon", mapTrangThaiXuLyDon);
		
		if (getChucVuGiaoViec() != null) {
			mapChucVuGiaoViec = new HashMap<String, Object>();
			mapChucVuGiaoViec.put("ten", getChucVuGiaoViec().getText());
			mapChucVuGiaoViec.put("giaTri", VaiTroEnum.valueOf(getChucVuGiaoViec().name()));
		}
		map.put("chucVuGiaoViec", mapChucVuGiaoViec);
		
		if (getHuongXuLy() != null) {
			mapHuongXuLy.put("ten", getHuongXuLy().getText());
			mapHuongXuLy.put("giaTri", getHuongXuLy().name());
			if (getHuongXuLy().equals(HuongXuLyXLDEnum.DE_XUAT_THU_LY)) {
				mapPhongBanGiaiQuyet = new HashMap<String, Object>();
				mapPhongBanGiaiQuyet.put("id", getPhongBanGiaiQuyet() != null ? getPhongBanGiaiQuyet().getId() : "");
				mapPhongBanGiaiQuyet.put("ten", getPhongBanGiaiQuyet() != null ? getPhongBanGiaiQuyet().getTen() : "");
				
				mapThamQuyenGiaiQuyet = new HashMap<String, Object>();
				mapThamQuyenGiaiQuyet.put("id", getThamQuyenGiaiQuyet() != null ? getThamQuyenGiaiQuyet().getId() : "");
				mapThamQuyenGiaiQuyet.put("ten", getThamQuyenGiaiQuyet() != null ? getThamQuyenGiaiQuyet().getTen() : "");
				
				map.put("hanGiaiQuyet", getHanGiaiQuyet());
				map.put("thoiHanBaoCaoKetQuaTTXM", getThoiHanBaoCaoKetQuaTTXM());
				
				mapTrangThaiTTXM = new HashMap<String, Object>();
				mapTrangThaiTTXM.put("ten", getTrangThaiTTXM() != null ? getTrangThaiTTXM().getText() : "");
				mapTrangThaiTTXM.put("enum", getTrangThaiTTXM() != null ? getTrangThaiTTXM().name() : "");
				
				donViTTXM = new HashMap<String, Object>();
				donViTTXM.put("id", getDonViThamTraXacMinh() != null ? getDonViThamTraXacMinh().getId() : "");
				donViTTXM.put("ten", getDonViThamTraXacMinh() != null ? getDonViThamTraXacMinh().getTen() : "");	
			} else if (getHuongXuLy().equals(HuongXuLyXLDEnum.CHUYEN_DON)) {
				if (getCoQuanTiepNhan() != null) { 
					mapCoQuanTiepNhan = new HashMap<String, Object>();
					mapCoQuanTiepNhan.put("id", getCoQuanTiepNhan().getId());
					mapCoQuanTiepNhan.put("ten", getCoQuanTiepNhan().getTen());
				}

				if (getDonViNgoaiHeThong() != null) {
					mapDonViNgoaiHeThong = new HashMap<String, Object>();
					mapDonViNgoaiHeThong.put("id", getDonViNgoaiHeThong().getId());
					mapDonViNgoaiHeThong.put("ten", getDonViNgoaiHeThong().getTen());
				}
				
				if (getDonViNgoaiHeThong() != null && getCoQuanTiepNhan() == null) {
					mapCoQuanTiepNhan = new HashMap<String, Object>();
					mapCoQuanTiepNhan.put("id", null);
					mapCoQuanTiepNhan.put("ten", null);
				}
				
				if (getThamQuyenGiaiQuyet() != null) {
					mapThamQuyenGiaiQuyet = new HashMap<String, Object>();
					mapThamQuyenGiaiQuyet.put("id", getThamQuyenGiaiQuyet().getId());
					mapThamQuyenGiaiQuyet.put("ten", getThamQuyenGiaiQuyet().getTen());
				}
			} else if (getHuongXuLy().equals(HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY)
					|| getHuongXuLy().equals(HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN)
					|| getHuongXuLy().equals(HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI)) {
				mapThamQuyenGiaiQuyet = new HashMap<String, Object>();
				mapThamQuyenGiaiQuyet.put("id", getThamQuyenGiaiQuyet() != null ? getThamQuyenGiaiQuyet().getId() : "");
				mapThamQuyenGiaiQuyet.put("ten", getThamQuyenGiaiQuyet() != null ? getThamQuyenGiaiQuyet().getTen() : "");	
			} else if (getHuongXuLy().equals(HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO)) {
				mapYeuCauGapLanhDao.put("diaDiem", getDiaDiem());
				mapYeuCauGapLanhDao.put("ngayHenGapLanhDao", getNgayHenGapLanhDao());
				map.put("yeuCauGapLanhDao", mapYeuCauGapLanhDao);
			} else if (getHuongXuLy().equals(HuongXuLyXLDEnum.DINH_CHI)) {
				mapDinhChi = new HashMap<String, Object>();
				mapDinhChi.put("soQuyetDinhDinhChi", getSoQuyetDinhDinhChi() != null ? getSoQuyetDinhDinhChi() : "");
				mapDinhChi.put("ngayQuyetDinhDinhChi", getNgayQuyetDinhDinhChi() != null ? getNgayQuyetDinhDinhChi() : "");
				mapDinhChi.put("noiDung", getDon().getLyDoDinhChi()!= null ? getDon().getLyDoDinhChi() : "");
			} else {
				mapThamQuyenGiaiQuyet = new HashMap<String, Object>();
				mapThamQuyenGiaiQuyet.put("id", getThamQuyenGiaiQuyet() != null ? getThamQuyenGiaiQuyet().getId() : "");
				mapThamQuyenGiaiQuyet.put("ten", getThamQuyenGiaiQuyet() != null ? getThamQuyenGiaiQuyet().getTen() : "");
			}
			
			map.put("dinhChi", mapDinhChi);
			map.put("phongBanGiaiQuyet", mapPhongBanGiaiQuyet);
			map.put("thamQuyenGiaiQuyet", mapThamQuyenGiaiQuyet);
			map.put("coQuanTiepNhan", mapCoQuanTiepNhan);
			map.put("huongXuLy", mapHuongXuLy);
			map.put("mapTrangThaiTTXM", mapTrangThaiTTXM);
			map.put("donViThamTraXacMinh", donViTTXM);
			map.put("donViNgoaiHeThong", mapDonViNgoaiHeThong);
		}

//		Bo phanLoaiDon - cap nhat 16/11
//		Don don = getDon();
//		if (don != null) {
//			map.put("phanLoaiDonInfo", don.getPhanLoaiDonInfo());
//			map.put("lyDoKhongDuDieuKienThuLyInfo", don.getLyDoKhongDuDieuKienThuLyInfo());
//		}
		
		return map;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Long getXuLyDonId() {
		return getId();
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public String getMaHoSo() {
		return don.getMaHoSo();
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public String getMaDon() {
		return don.getMa();
	}
	
}