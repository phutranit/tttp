package vn.greenglobal.tttp.model.medial;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.Application;
import vn.greenglobal.tttp.enums.KetQuaTrangThaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiNguoiDungDonEnum;
import vn.greenglobal.tttp.enums.PhanLoaiDonCongDanEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.model.DoanDiCung;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.Model;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.util.Utils;

@Entity
@Table(name = "medial_dontracuu")
public class Medial_DonTraCuu extends Model<Medial_DonTraCuu>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3019291568638856731L;
	
	public Medial_DonTraCuu() {
		this.setId(0l);
	}
	
	public void copyDon(Don don, Long donViId) {
		setDonGocId(don.getDonGocId() != null && don.getDonGocId() > 0 ? don.getDonGocId() : don.getId());
		setNoiDung(don.getNoiDung());
		setMaDon(don.getMa());
		setMaHoSo(don.getMaHoSo());
		setNguonDon(don.getNguonTiepNhanDon().getText());
		setLoaiDoiTuong(don.getLoaiDoiTuong().getText());
		setLoaiDonThu(don.getLoaiDon());
		setCoUyQuyen(don.isCoUyQuyen());
		setDaGiaiQuyet(don.isDaGiaiQuyet());
		setCoQuanDaGiaiQuyet(don.getCoQuanDaGiaiQuyetDonThu());
		setSoVanBanDaGiaiQuyet(don.getSoVanBanDaGiaiQuyet());
		setNgayBanHanhVanBanDaGiaiQuyet(don.getNgayBanHanhVanBanDaGiaiQuyet());
		setNgayTiepNhanDon(don.getNgayTiepNhan());
		setTrangThaiDon(don.getTrangThaiXLDGiaiQuyet() != null ? don.getTrangThaiXLDGiaiQuyet().getText() : "");
		setLoaiDoiTuong(don.getLoaiDoiTuong() != null ? don.getLoaiDoiTuong().getText() : "");
		setKetQuaDon(don.getKetQuaXLDGiaiQuyet() != null ? don.getKetQuaXLDGiaiQuyet().getText() : "");
		setLinhVucDonThu(don.getLinhVucDonThu() != null ? don.getLinhVucDonThu().getTen() : "");
		setLinhVucDonThuChiTiet(don.getLinhVucDonThuChiTiet() != null ? don.getLinhVucDonThuChiTiet().getTen() : "");
		setLoaiVuViec(don.getLoaiVuViec() != null ? don.getLoaiVuViec().getText() : "");
		setLinhVucChiTietKhac(don.getLinhVucChiTietKhac());
		setLoaiNguoiDungDon(don.getLoaiNguoiDungDon());
		setSoNguoi(don.getSoNguoi());
		setProcessType(don.getProcessType());
		setDoanDiCungs(don.getDoanDiCungs());
		setThongTinGiaiQuyetDon(don.getThongTinGiaiQuyetDon());
		setTrangThaiTTXM(don.getTrangThaiTTXM());
		setTrangThaiKTDX(don.getTrangThaiKTDX());
		setNgayBatDauXLD(don.getNgayBatDauXLD());
		setThoiHanXuLyXLD(don.getThoiHanXuLyXLD());
		setKetQuaXLDGiaiQuyet(don.getKetQuaXLDGiaiQuyet());
		if (don.getProcessType().equals(ProcessTypeEnum.XU_LY_DON) || don.getProcessType().equals(ProcessTypeEnum.GIAI_QUYET_DON)) {
			setTenDonViGiuDenHienTai(don.getDonViXuLyGiaiQuyet() != null ? don.getDonViXuLyGiaiQuyet().getTen() : "");
			if (don.getProcessType().equals(ProcessTypeEnum.XU_LY_DON)) {
				setHanXuLy(don.getThoiHanXuLyXLD());
			} else {
				if (don.getLoaiDon().equals(LoaiDonEnum.DON_TO_CAO)) {
					if (don.getThongTinGiaiQuyetDon().getNgayHetHanSauKhiGiaHanGiaiQuyet() != null) {
						setHanXuLy(don.getThongTinGiaiQuyetDon().getNgayHetHanSauKhiGiaHanGiaiQuyet());
					} else {
						setHanXuLy(don.getThongTinGiaiQuyetDon().getNgayHetHanGiaiQuyet());
					}					
				} else {
					setHanXuLy(don.getThongTinGiaiQuyetDon().getNgayHetHanGiaiQuyet());
				}
			}
			if (donViId != null && don.getDonViXuLyGiaiQuyet() != null) {
				if (donViId.equals(don.getDonViXuLyGiaiQuyet().getId())) {
					setCanBoXuLy(don.getCanBoXuLyChiDinh() != null ? don.getCanBoXuLyChiDinh().getHoVaTen() : "");
				}
			}
		} else if (don.getProcessType().equals(ProcessTypeEnum.THAM_TRA_XAC_MINH)) {
			setTenDonViGiuDenHienTai(don.getDonViThamTraXacMinh() != null ? don.getDonViThamTraXacMinh().getTen() : "");
			if (don.getKetQuaXLDGiaiQuyet().equals(KetQuaTrangThaiDonEnum.DANG_LAP_DU_THAO)) {
				setHanXuLy(don.getThongTinGiaiQuyetDon().getNgayHetHanGiaoLapDuThao());
			} else {
				if (don.getLoaiDon().equals(LoaiDonEnum.DON_KHIEU_NAI)) {
					if (don.getThongTinGiaiQuyetDon().getNgayHetHanSauKhiGiaHanTTXM() != null) {
						setHanXuLy(don.getThongTinGiaiQuyetDon().getNgayHetHanSauKhiGiaHanTTXM());
					} else {
						setHanXuLy(don.getThongTinGiaiQuyetDon().getNgayHetHanTTXM());
					}
				} else {
					setHanXuLy(don.getThongTinGiaiQuyetDon().getNgayHetHanTTXM());
				}
			}	
			if (donViId != null && don.getDonViThamTraXacMinh() != null) {
				if (donViId.equals(don.getDonViThamTraXacMinh().getId())) {
					setCanBoXuLy(don.getCanBoTTXMChiDinh() != null ? don.getCanBoTTXMChiDinh().getHoVaTen() : "");
				}
			}
		} else if (don.getProcessType().equals(ProcessTypeEnum.KIEM_TRA_DE_XUAT)) {
			setTenDonViGiuDenHienTai(don.getDonViKiemTraDeXuat() != null ? don.getDonViKiemTraDeXuat().getTen() : "");
			setHanXuLy(don.getThongTinGiaiQuyetDon().getNgayHetHanKTDX());
			if (donViId != null && don.getDonViKiemTraDeXuat() != null) {
				if (donViId.equals(don.getDonViKiemTraDeXuat().getId())) {
					setCanBoXuLy(don.getCanBoKTDXChiDinh() != null ? don.getCanBoKTDXChiDinh().getHoVaTen() : "");
				}
			}
		}
	}
	
	@JsonIgnore
	private Long donGocId;
	private String noiDung = "";
	private String maDon = "";
	private String maHoSo = "";
	private String nguonDon = "";
	private String trangThaiDon = "";
	private String loaiDoiTuong = "";
	private String ketQuaDon = "";
	private String tenDonViGiuDenHienTai = "";
	private String linhVucDonThu = "";
	private String linhVucDonThuChiTiet = "";
	private String linhVucChiTietKhac = "";
	private String loaiVuViec = "";
	private String soVanBanDaGiaiQuyet = "";
	private String coQuanDaGiaiQuyet = "";
	private String canBoXuLy = "";
	
	private int soNguoi;
	private boolean coUyQuyen;
	private boolean daGiaiQuyet;
	
	private LocalDateTime hanXuLy;
	@JsonIgnore
	private LocalDateTime ngayBatDauXLD;
	@JsonIgnore
	private LocalDateTime thoiHanXuLyXLD;
	@JsonIgnore
	private LocalDateTime ngayTiepNhanDon;
	private LocalDateTime ngayBanHanhVanBanDaGiaiQuyet;
	@JsonIgnore
	private ProcessTypeEnum processType;
	@JsonIgnore
	private ThongTinGiaiQuyetDon thongTinGiaiQuyetDon;
	@JsonIgnore
	private TrangThaiDonEnum trangThaiTTXM;
	@JsonIgnore
	private TrangThaiDonEnum trangThaiKTDX;
	@JsonIgnore
	private KetQuaTrangThaiDonEnum ketQuaXLDGiaiQuyet;
	@JsonIgnore
	private LoaiDonEnum loaiDonThu;
	@JsonIgnore
	private LoaiNguoiDungDonEnum loaiNguoiDungDon;
	@JsonIgnore
	@Transient
	private List<DoanDiCung> doanDiCungs = new ArrayList<DoanDiCung>();
		
	public String getMaDon() {
		return maDon;
	}

	public void setMaDon(String maDon) {
		this.maDon = maDon;
	}
	
	public String getMaHoSo() {
		return maHoSo;
	}

	public void setMaHoSo(String maHoSo) {
		this.maHoSo = maHoSo;
	}

	public String getLoaiVuViec() {
		return loaiVuViec;
	}

	public void setLoaiVuViec(String loaiVuViec) {
		this.loaiVuViec = loaiVuViec;
	}
	
	public LoaiDonEnum getLoaiDonThu() {
		return loaiDonThu;
	}

	public void setLoaiDonThu(LoaiDonEnum loaiDonThu) {
		this.loaiDonThu = loaiDonThu;
	}

	public String getCoQuanDaGiaiQuyet() {
		return coQuanDaGiaiQuyet;
	}

	public void setCoQuanDaGiaiQuyet(String coQuanDaGiaiQuyet) {
		this.coQuanDaGiaiQuyet = coQuanDaGiaiQuyet;
	}

	public String getNguonDon() {
		return nguonDon;
	}

	public void setNguonDon(String nguonDon) {
		this.nguonDon = nguonDon;
	}

	public LoaiNguoiDungDonEnum getLoaiNguoiDungDon() {
		return loaiNguoiDungDon;
	}

	public void setLoaiNguoiDungDon(LoaiNguoiDungDonEnum loaiNguoiDungDon) {
		this.loaiNguoiDungDon = loaiNguoiDungDon;
	}

	public LocalDateTime getNgayTiepNhanDon() {
		return ngayTiepNhanDon;
	}

	public void setNgayTiepNhanDon(LocalDateTime ngayTiepNhanDon) {
		this.ngayTiepNhanDon = ngayTiepNhanDon;
	}

	public String getLinhVucDonThu() {
		return linhVucDonThu;
	}

	public void setLinhVucDonThu(String linhVucDonThu) {
		this.linhVucDonThu = linhVucDonThu;
	}

	public String getLinhVucDonThuChiTiet() {
		return linhVucDonThuChiTiet;
	}

	public void setLinhVucDonThuChiTiet(String linhVucDonThuChiTiet) {
		this.linhVucDonThuChiTiet = linhVucDonThuChiTiet;
	}

	public String getLinhVucChiTietKhac() {
		return linhVucChiTietKhac;
	}

	public void setLinhVucChiTietKhac(String linhVucChiTietKhac) {
		this.linhVucChiTietKhac = linhVucChiTietKhac;
	}

	public String getSoVanBanDaGiaiQuyet() {
		return soVanBanDaGiaiQuyet;
	}

	public void setSoVanBanDaGiaiQuyet(String soVanBanDaGiaiQuyet) {
		this.soVanBanDaGiaiQuyet = soVanBanDaGiaiQuyet;
	}

	public String getCanBoXuLy() {
		return canBoXuLy;
	}

	public void setCanBoXuLy(String canBoXuLy) {
		this.canBoXuLy = canBoXuLy;
	}

	public LocalDateTime getNgayBanHanhVanBanDaGiaiQuyet() {
		return ngayBanHanhVanBanDaGiaiQuyet;
	}

	public void setNgayBanHanhVanBanDaGiaiQuyet(LocalDateTime ngayBanHanhVanBanDaGiaiQuyet) {
		this.ngayBanHanhVanBanDaGiaiQuyet = ngayBanHanhVanBanDaGiaiQuyet;
	}

	public int getSoNguoi() {
		return soNguoi;
	}

	public void setSoNguoi(int soNguoi) {
		this.soNguoi = soNguoi;
	}

	public String getLoaiDoiTuong() {
		return loaiDoiTuong;
	}

	public void setLoaiDoiTuong(String loaiDoiTuong) {
		this.loaiDoiTuong = loaiDoiTuong;
	}	

	public LocalDateTime getHanXuLy() {
		return hanXuLy;
	}

	public void setHanXuLy(LocalDateTime hanXuLy) {
		this.hanXuLy = hanXuLy;
	}

	public Long getDonGocId() {
		return donGocId;
	}
	
	public void setDonGocId(Long donGocId) {
		this.donGocId = donGocId;
	}
	
	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	public String getTrangThaiDon() {
		return trangThaiDon;
	}

	public void setTrangThaiDon(String trangThaiDon) {
		this.trangThaiDon = trangThaiDon;
	}

	public String getKetQuaDon() {
		return ketQuaDon;
	}

	public void setKetQuaDon(String ketQuaDon) {
		this.ketQuaDon = ketQuaDon;
	}

	public String getTenDonViGiuDenHienTai() {
		return tenDonViGiuDenHienTai;
	}

	public void setTenDonViGiuDenHienTai(String tenDonViGiuDenHienTai) {
		this.tenDonViGiuDenHienTai = tenDonViGiuDenHienTai;
	}
	
	public boolean isCoUyQuyen() {
		return coUyQuyen;
	}

	public void setCoUyQuyen(boolean coUyQuyen) {
		this.coUyQuyen = coUyQuyen;
	}

	public boolean isDaGiaiQuyet() {
		return daGiaiQuyet;
	}

	public void setDaGiaiQuyet(boolean daGiaiQuyet) {
		this.daGiaiQuyet = daGiaiQuyet;
	}

	public LocalDateTime getNgayBatDauXLD() {
		return ngayBatDauXLD;
	}

	public void setNgayBatDauXLD(LocalDateTime ngayBatDauXLD) {
		this.ngayBatDauXLD = ngayBatDauXLD;
	}

	public LocalDateTime getThoiHanXuLyXLD() {
		return thoiHanXuLyXLD;
	}

	public void setThoiHanXuLyXLD(LocalDateTime thoiHanXuLyXLD) {
		this.thoiHanXuLyXLD = thoiHanXuLyXLD;
	}

	public ProcessTypeEnum getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessTypeEnum processType) {
		this.processType = processType;
	}
	
	public ThongTinGiaiQuyetDon getThongTinGiaiQuyetDon() {
		return thongTinGiaiQuyetDon;
	}

	public void setThongTinGiaiQuyetDon(ThongTinGiaiQuyetDon thongTinGiaiQuyetDon) {
		this.thongTinGiaiQuyetDon = thongTinGiaiQuyetDon;
	}
	
	public TrangThaiDonEnum getTrangThaiTTXM() {
		return trangThaiTTXM;
	}

	public void setTrangThaiTTXM(TrangThaiDonEnum trangThaiTTXM) {
		this.trangThaiTTXM = trangThaiTTXM;
	}	

	public TrangThaiDonEnum getTrangThaiKTDX() {
		return trangThaiKTDX;
	}

	public void setTrangThaiKTDX(TrangThaiDonEnum trangThaiKTDX) {
		this.trangThaiKTDX = trangThaiKTDX;
	}
	
	public KetQuaTrangThaiDonEnum getKetQuaXLDGiaiQuyet() {
		return ketQuaXLDGiaiQuyet;
	}

	public void setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum ketQuaXLDGiaiQuyet) {
		this.ketQuaXLDGiaiQuyet = ketQuaXLDGiaiQuyet;
	}
	
	public List<DoanDiCung> getDoanDiCungs() {
		return doanDiCungs;
	}

	public void setDoanDiCungs(List<DoanDiCung> doanDiCungs) {
		this.doanDiCungs = doanDiCungs;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getThoiHanInfo() {
		if (getProcessType().equals(ProcessTypeEnum.XU_LY_DON)) {
			return Utils.convertThoiHan(getNgayBatDauXLD(), getThoiHanXuLyXLD(), null);
		} else if (getProcessType().equals(ProcessTypeEnum.GIAI_QUYET_DON)) {
			if (getThongTinGiaiQuyetDon() != null) {
				return Utils.convertThoiHan(getThongTinGiaiQuyetDon().getNgayBatDauGiaiQuyet(),
						getThongTinGiaiQuyetDon().getNgayHetHanGiaiQuyet(),
						getThongTinGiaiQuyetDon().getNgayHetHanSauKhiGiaHanGiaiQuyet());
			}
		} else if (getProcessType().equals(ProcessTypeEnum.THAM_TRA_XAC_MINH)) {
			if (getTrangThaiTTXM() != null && getTrangThaiTTXM().equals(TrangThaiDonEnum.DANG_GIAI_QUYET)) { 
				if (getThongTinGiaiQuyetDon() != null) {
					if (getKetQuaXLDGiaiQuyet().equals(KetQuaTrangThaiDonEnum.DANG_LAP_DU_THAO)) {
						return Utils.convertThoiHan(getThongTinGiaiQuyetDon().getNgayBatDauGiaoLapDuThao(),
								getThongTinGiaiQuyetDon().getNgayHetHanGiaoLapDuThao(),
								getThongTinGiaiQuyetDon().getNgayHetHanSauKhiGiaHanGiaoLapDuThao());
					} else {
						return Utils.convertThoiHan(getThongTinGiaiQuyetDon().getNgayBatDauTTXM(),
								getThongTinGiaiQuyetDon().getNgayHetHanTTXM(),
								getThongTinGiaiQuyetDon().getNgayHetHanSauKhiGiaHanTTXM());
					}
				}
			}
		} else if (getProcessType().equals(ProcessTypeEnum.KIEM_TRA_DE_XUAT)) {
			if (getThongTinGiaiQuyetDon() != null) {
				if (getTrangThaiKTDX() != null && getTrangThaiKTDX().equals(TrangThaiDonEnum.DANG_GIAI_QUYET)) { 
					return Utils.convertThoiHan(getThongTinGiaiQuyetDon().getNgayBatDauKTDX(),
							getThongTinGiaiQuyetDon().getNgayHetHanKTDX(),
							getThongTinGiaiQuyetDon().getNgayHetHanSauKhiGiaHanKTDX());
				}
			}
		}
		return new HashMap<>();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public List<Don_CongDan> getListNguoiDungDon() {
		List<Don_CongDan> list = new ArrayList<Don_CongDan>();
		Don donGoc = Application.app.getDonRepository().findOne(getDonGocId());
		list = (List<Don_CongDan>) Application.app.getDonCongDanRepository()
				.findAll(QDon_CongDan.don_CongDan.don.eq(donGoc)
						.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON))
						.and(QDon_CongDan.don_CongDan.daXoa.eq(false)));
		return list;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Don_CongDan getNguoiDuocUyQuyen() {
		List<Don_CongDan> list = new ArrayList<Don_CongDan>();
		Don donGoc = Application.app.getDonRepository().findOne(getDonGocId());
		list = (List<Don_CongDan>) Application.app.getDonCongDanRepository()
				.findAll(QDon_CongDan.don_CongDan.don.eq(donGoc)
						.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUOC_UY_QUYEN))
						.and(QDon_CongDan.don_CongDan.daXoa.eq(false)));
		return list != null && list.size() > 0 ? list.get(0) : null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public List<DoanDiCung> getListDoanDiCung() {
		List<DoanDiCung> list = new ArrayList<DoanDiCung>();
		if (getDonGocId() != null && getDonGocId() > 0) { 
			Don donGoc = Application.app.getDonRepository().findOne(getDonGocId());
			for (DoanDiCung dcd : donGoc.getDoanDiCungs()) {
				if (!dcd.isDaXoa()) {
					list.add(dcd);
				}
			}
		} else { 
			for (DoanDiCung dcd : getDoanDiCungs()) {
				if (!dcd.isDaXoa()) {
					list.add(dcd);
				}
			}
		}
		return list;
	}
	
	@ApiModelProperty(hidden = true)
	@Transient
	public Map<String, Object> getLoaiDonThuInfo() {
		if (getLoaiDonThu() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getLoaiDonThu().getText());
			map.put("giaTri", getLoaiDonThu().toString());
			return map;
		}
		return null;
	}
	
	@ApiModelProperty(hidden = true)
	@Transient
	public Map<String, Object> getLoaiNguoiDungDonInfo() {
		if (getLoaiNguoiDungDon() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getLoaiNguoiDungDon().getText());
			map.put("giaTri", getLoaiNguoiDungDon().toString());
			return map;
		}
		return null;
	}
}
