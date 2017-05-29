package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.HinhThucTheoDoiEnum;
import vn.greenglobal.tttp.enums.KetLuanNoiDungKhieuNaiEnum;
import vn.greenglobal.tttp.enums.KetQuaThucHienTheoDoiEnum;

@Entity
@Table(name = "giaiquyetdon")
public class GiaiQuyetDon extends Model<GiaiQuyetDon> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 445465484063016490L;

	private String soQuyetDinhThanhLapDTXM = "";
	private String soQuyetDinhGiaHan = "";
	private String diaDiemDoiThoai = "";

	private LocalDateTime thoiGianDoiThoai;
	private LocalDateTime ngayBaoCaoKetQuaTTXM;

	private boolean lapToDoanXacMinh;
	private boolean giaHanGiaiQuyet;
	private boolean doiThoai;
	private boolean giaoCoQuanDieuTra;
	private boolean khoiTo;
	private boolean quyetDinhGiaiQuyetKhieuNai;
	private boolean theoDoiThucHien;
	private boolean daThuLy;
	private boolean daThamTraXacMinhVuViec;
	private boolean daRaQuyetDinhGiaiQuyet;

	private int soDoiTuongBiKhoiTo;
	private int tongSoNguoiXuLyHanhChinh;
	private int soNguoiDaBiXuLyHanhChinh;

	private long tienPhaiThuNhaNuoc;
	private long datPhaiThuNhaNuoc;
	private long tienPhaiTraCongDan;
	private long datPhaiTraCongDan;
	private long tienDaThuNhaNuoc;
	private long datDaThuNhaNuoc;
	private long tienDaTraCongDan;
	private long datDaTraCongDan;

	@ManyToOne
	private CongChuc canBoGiaiQuyet;
	@ManyToOne
	private CongChuc truongDoanTTXM;
	@ManyToOne
	private CongChuc canBoThamTraXacMinh;
	@ManyToOne
	private CoQuanQuanLy coQuanDieuTra;
	@ManyToOne
	private CoQuanQuanLy coQuanTheoDoi;
	@ManyToOne
	private CoQuanQuanLy donViThamTraXacMinh;
	
	@OneToOne
	private Don don;

	@Enumerated(EnumType.STRING)
	private KetLuanNoiDungKhieuNaiEnum ketLuanNoiDungKhieuNai;
	@Enumerated(EnumType.STRING)
	private HinhThucTheoDoiEnum hinhThucTheoDoi;
	@Enumerated(EnumType.STRING)
	private KetQuaThucHienTheoDoiEnum ketQuaThucHienTheoDoi;

	public String getSoQuyetDinhThanhLapDTXM() {
		return soQuyetDinhThanhLapDTXM;
	}

	public void setSoQuyetDinhThanhLapDTXM(String soQuyetDinhThanhLapDTXM) {
		this.soQuyetDinhThanhLapDTXM = soQuyetDinhThanhLapDTXM;
	}

	public String getSoQuyetDinhGiaHan() {
		return soQuyetDinhGiaHan;
	}

	public void setSoQuyetDinhGiaHan(String soQuyetDinhGiaHan) {
		this.soQuyetDinhGiaHan = soQuyetDinhGiaHan;
	}

	public String getDiaDiemDoiThoai() {
		return diaDiemDoiThoai;
	}

	public void setDiaDiemDoiThoai(String diaDiemDoiThoai) {
		this.diaDiemDoiThoai = diaDiemDoiThoai;
	}

	public LocalDateTime getThoiGianDoiThoai() {
		return thoiGianDoiThoai;
	}

	public void setThoiGianDoiThoai(LocalDateTime thoiGianDoiThoai) {
		this.thoiGianDoiThoai = thoiGianDoiThoai;
	}

	public LocalDateTime getNgayBaoCaoKetQuaTTXM() {
		return ngayBaoCaoKetQuaTTXM;
	}

	public void setNgayBaoCaoKetQuaTTXM(LocalDateTime ngayBaoCaoKetQuaTTXM) {
		this.ngayBaoCaoKetQuaTTXM = ngayBaoCaoKetQuaTTXM;
	}

	public boolean isLapToDoanXacMinh() {
		return lapToDoanXacMinh;
	}

	public void setLapToDoanXacMinh(boolean lapToDoanXacMinh) {
		this.lapToDoanXacMinh = lapToDoanXacMinh;
	}

	public boolean isGiaHanGiaiQuyet() {
		return giaHanGiaiQuyet;
	}

	public void setGiaHanGiaiQuyet(boolean giaHanGiaiQuyet) {
		this.giaHanGiaiQuyet = giaHanGiaiQuyet;
	}

	public boolean isDoiThoai() {
		return doiThoai;
	}

	public void setDoiThoai(boolean doiThoai) {
		this.doiThoai = doiThoai;
	}

	public boolean isGiaoCoQuanDieuTra() {
		return giaoCoQuanDieuTra;
	}

	public void setGiaoCoQuanDieuTra(boolean giaoCoQuanDieuTra) {
		this.giaoCoQuanDieuTra = giaoCoQuanDieuTra;
	}

	public boolean isKhoiTo() {
		return khoiTo;
	}

	public void setKhoiTo(boolean khoiTo) {
		this.khoiTo = khoiTo;
	}

	public boolean isQuyetDinhGiaiQuyetKhieuNai() {
		return quyetDinhGiaiQuyetKhieuNai;
	}

	public void setQuyetDinhGiaiQuyetKhieuNai(boolean quyetDinhGiaiQuyetKhieuNai) {
		this.quyetDinhGiaiQuyetKhieuNai = quyetDinhGiaiQuyetKhieuNai;
	}

	public boolean isTheoDoiThucHien() {
		return theoDoiThucHien;
	}

	public void setTheoDoiThucHien(boolean theoDoiThucHien) {
		this.theoDoiThucHien = theoDoiThucHien;
	}

	public boolean isDaThuLy() {
		return daThuLy;
	}

	public void setDaThuLy(boolean daThuLy) {
		this.daThuLy = daThuLy;
	}

	public boolean isDaThamTraXacMinhVuViec() {
		return daThamTraXacMinhVuViec;
	}

	public void setDaThamTraXacMinhVuViec(boolean daThamTraXacMinhVuViec) {
		this.daThamTraXacMinhVuViec = daThamTraXacMinhVuViec;
	}

	public boolean isDaRaQuyetDinhGiaiQuyet() {
		return daRaQuyetDinhGiaiQuyet;
	}

	public void setDaRaQuyetDinhGiaiQuyet(boolean daRaQuyetDinhGiaiQuyet) {
		this.daRaQuyetDinhGiaiQuyet = daRaQuyetDinhGiaiQuyet;
	}

	public int getSoDoiTuongBiKhoiTo() {
		return soDoiTuongBiKhoiTo;
	}

	public void setSoDoiTuongBiKhoiTo(int soDoiTuongBiKhoiTo) {
		this.soDoiTuongBiKhoiTo = soDoiTuongBiKhoiTo;
	}

	public int getTongSoNguoiXuLyHanhChinh() {
		return tongSoNguoiXuLyHanhChinh;
	}

	public void setTongSoNguoiXuLyHanhChinh(int tongSoNguoiXuLyHanhChinh) {
		this.tongSoNguoiXuLyHanhChinh = tongSoNguoiXuLyHanhChinh;
	}

	public int getSoNguoiDaBiXuLyHanhChinh() {
		return soNguoiDaBiXuLyHanhChinh;
	}

	public void setSoNguoiDaBiXuLyHanhChinh(int soNguoiDaBiXuLyHanhChinh) {
		this.soNguoiDaBiXuLyHanhChinh = soNguoiDaBiXuLyHanhChinh;
	}

	public long getTienPhaiThuNhaNuoc() {
		return tienPhaiThuNhaNuoc;
	}

	public void setTienPhaiThuNhaNuoc(long tienPhaiThuNhaNuoc) {
		this.tienPhaiThuNhaNuoc = tienPhaiThuNhaNuoc;
	}

	public long getDatPhaiThuNhaNuoc() {
		return datPhaiThuNhaNuoc;
	}

	public void setDatPhaiThuNhaNuoc(long datPhaiThuNhaNuoc) {
		this.datPhaiThuNhaNuoc = datPhaiThuNhaNuoc;
	}

	public long getTienPhaiTraCongDan() {
		return tienPhaiTraCongDan;
	}

	public void setTienPhaiTraCongDan(long tienPhaiTraCongDan) {
		this.tienPhaiTraCongDan = tienPhaiTraCongDan;
	}

	public long getDatPhaiTraCongDan() {
		return datPhaiTraCongDan;
	}

	public void setDatPhaiTraCongDan(long datPhaiTraCongDan) {
		this.datPhaiTraCongDan = datPhaiTraCongDan;
	}

	public long getTienDaThuNhaNuoc() {
		return tienDaThuNhaNuoc;
	}

	public void setTienDaThuNhaNuoc(long tienDaThuNhaNuoc) {
		this.tienDaThuNhaNuoc = tienDaThuNhaNuoc;
	}

	public long getDatDaThuNhaNuoc() {
		return datDaThuNhaNuoc;
	}

	public void setDatDaThuNhaNuoc(long datDaThuNhaNuoc) {
		this.datDaThuNhaNuoc = datDaThuNhaNuoc;
	}

	public long getTienDaTraCongDan() {
		return tienDaTraCongDan;
	}

	public void setTienDaTraCongDan(long tienDaTraCongDan) {
		this.tienDaTraCongDan = tienDaTraCongDan;
	}

	public long getDatDaTraCongDan() {
		return datDaTraCongDan;
	}

	public void setDatDaTraCongDan(long datDaTraCongDan) {
		this.datDaTraCongDan = datDaTraCongDan;
	}

	public CongChuc getCanBoGiaiQuyet() {
		return canBoGiaiQuyet;
	}

	public void setCanBoGiaiQuyet(CongChuc canBoGiaiQuyet) {
		this.canBoGiaiQuyet = canBoGiaiQuyet;
	}

	public CongChuc getTruongDoanTTXM() {
		return truongDoanTTXM;
	}

	public void setTruongDoanTTXM(CongChuc truongDoanTTXM) {
		this.truongDoanTTXM = truongDoanTTXM;
	}

	public CongChuc getCanBoThamTraXacMinh() {
		return canBoThamTraXacMinh;
	}

	public void setCanBoThamTraXacMinh(CongChuc canBoThamTraXacMinh) {
		this.canBoThamTraXacMinh = canBoThamTraXacMinh;
	}

	public CoQuanQuanLy getCoQuanDieuTra() {
		return coQuanDieuTra;
	}

	public void setCoQuanDieuTra(CoQuanQuanLy coQuanDieuTra) {
		this.coQuanDieuTra = coQuanDieuTra;
	}

	public CoQuanQuanLy getCoQuanTheoDoi() {
		return coQuanTheoDoi;
	}

	public void setCoQuanTheoDoi(CoQuanQuanLy coQuanTheoDoi) {
		this.coQuanTheoDoi = coQuanTheoDoi;
	}

	public CoQuanQuanLy getDonViThamTraXacMinh() {
		return donViThamTraXacMinh;
	}
	
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	public void setDonViThamTraXacMinh(CoQuanQuanLy donViThamTraXacMinh) {
		this.donViThamTraXacMinh = donViThamTraXacMinh;
	}

	public KetLuanNoiDungKhieuNaiEnum getKetLuanNoiDungKhieuNai() {
		return ketLuanNoiDungKhieuNai;
	}

	public void setKetLuanNoiDungKhieuNai(KetLuanNoiDungKhieuNaiEnum ketLuanNoiDungKhieuNai) {
		this.ketLuanNoiDungKhieuNai = ketLuanNoiDungKhieuNai;
	}

	public HinhThucTheoDoiEnum getHinhThucTheoDoi() {
		return hinhThucTheoDoi;
	}

	public void setHinhThucTheoDoi(HinhThucTheoDoiEnum hinhThucTheoDoi) {
		this.hinhThucTheoDoi = hinhThucTheoDoi;
	}

	public KetQuaThucHienTheoDoiEnum getKetQuaThucHienTheoDoi() {
		return ketQuaThucHienTheoDoi;
	}

	public void setKetQuaThucHienTheoDoi(KetQuaThucHienTheoDoiEnum ketQuaThucHienTheoDoi) {
		this.ketQuaThucHienTheoDoi = ketQuaThucHienTheoDoi;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Long getGiaiQuyetDonId() {
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
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCanBoGiaiQuyetInfo() {
		if (getCanBoGiaiQuyet() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("hoVaTen", getCanBoGiaiQuyet().getHoVaTen());
			map.put("congChucId", getCanBoGiaiQuyet().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getTruongDoanTTXMInfo() {
		if (getTruongDoanTTXM() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("hoVaTen", getTruongDoanTTXM().getHoVaTen());
			map.put("congChucId", getTruongDoanTTXM().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCanBoThamTraXacMinhInfo() {
		if (getCanBoThamTraXacMinh() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("hoVaTen", getCanBoThamTraXacMinh().getHoVaTen());
			map.put("congChucId", getCanBoThamTraXacMinh().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCoQuanDieuTraInfo() {
		if (getCoQuanDieuTra() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getCoQuanDieuTra().getTen());
			map.put("coQuanQuanLyId", getCoQuanDieuTra().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCoQuanTheoDoiInfo() {
		if (getCoQuanTheoDoi() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getCoQuanTheoDoi().getTen());
			map.put("coQuanQuanLyId", getCoQuanTheoDoi().getId());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getDonViThamTraXacMinhInfo() {
		if (getDonViThamTraXacMinh() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("ten", getDonViThamTraXacMinh().getTen());
			map.put("coQuanQuanLyId", getDonViThamTraXacMinh().getId());
			return map;
		}
		return null;
	}

}
