package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.util.Utils;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.QuyTrinhXuLyDonEnum;
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
	@ManyToOne
	private CoQuanQuanLy phongBanXuLy;
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
	private LocalDateTime thoiHanXuLy;
	private LocalDateTime ngayHenGapLanhDao;
	private String diaDiem;
	private LocalDateTime ngayQuyetDinhDinhChi;
	private String soQuyetDinhDinhChi;
	
	@Transient
	private Long soNgayXuLy;
	
	public Long getSoNgayXuLy() {
		return soNgayXuLy;
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
	
	@Lob
	private String ghiChu = "";
	private String yKienXuLy = "";
	private String moTaTrangThai = "";
	private String noiDungYeuCauXuLy = "";
	private String noiDungThongTinTrinhLanhDao = "";

	@Enumerated(EnumType.STRING)
	private VaiTroEnum chucVu;
	@Enumerated(EnumType.STRING)
	private VaiTroEnum chucVuGiaoViec;
	
	@ApiModelProperty(position = 8)
	public VaiTroEnum getChucVuGiaoViec() {
		return chucVuGiaoViec;
	}

	public void setChucVuGiaoViec(VaiTroEnum chucVuGiaoViec) {
		this.chucVuGiaoViec = chucVuGiaoViec;
	}

	@Enumerated(EnumType.STRING)
	private QuyTrinhXuLyDonEnum quyTrinhXuLy;
	@Enumerated(EnumType.STRING)
	private HuongXuLyXLDEnum huongXuLy;

	@Enumerated(EnumType.STRING)
	private TrangThaiDonEnum trangThaiDon;

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
		this.ghiChu = ghiChu;
	}

	@ApiModelProperty(position = 4)
	public String getyKienXuLy() {
		return yKienXuLy;
	}

	public void setyKienXuLy(String yKienXuLy) {
		this.yKienXuLy = yKienXuLy;
	}

	@ApiModelProperty(example = "{}", position = 16)
	public String getMoTaTrangThai() {
		return moTaTrangThai;
	}

	public void setMoTaTrangThai(String moTaTrangThai) {
		this.moTaTrangThai = moTaTrangThai;
	}

	@ApiModelProperty(example = "{}", position = 18)
	public String getNoiDungThongTinTrinhLanhDao() {
		return noiDungThongTinTrinhLanhDao;
	}

	public void setNoiDungThongTinTrinhLanhDao(String noiDungThongTinTrinhLanhDao) {
		this.noiDungThongTinTrinhLanhDao = noiDungThongTinTrinhLanhDao;
	}

	@ApiModelProperty(example = "{}", position = 19)
	public String getNoiDungYeuCauXuLy() {
		return noiDungYeuCauXuLy;
	}

	@ApiModelProperty(position = 13, example = "{}")
	public CongChuc getCanBoXuLyChiDinh() {
		return canBoXuLyChiDinh;
	}

	@ApiModelProperty(position = 12)
	public void setCanBoXuLyChiDinh(CongChuc canBoChiDinh) {
		this.canBoXuLyChiDinh = canBoChiDinh;
	}

	public void setNoiDungYeuCauXuLy(String noiDungYeuCauXuLy) {
		this.noiDungYeuCauXuLy = noiDungYeuCauXuLy;
	}

	@ApiModelProperty(position = 5)
	public QuyTrinhXuLyDonEnum getQuyTrinhXuLy() {
		return quyTrinhXuLy;
	}

	public void setQuyTrinhXuLy(QuyTrinhXuLyDonEnum quyTrinhXuLy) {
		this.quyTrinhXuLy = quyTrinhXuLy;
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
}