package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.HinhThucTheoDoiEnum;
import vn.greenglobal.tttp.enums.KetLuanNoiDungKhieuNaiEnum;
import vn.greenglobal.tttp.enums.KetQuaThucHienTheoDoiEnum;

@Entity
@Table(name = "thongtingiaiquyetdon")
public class ThongTinGiaiQuyetDon extends Model<ThongTinGiaiQuyetDon> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 445465484063016490L;

	@Size(max=255)
	private String soQuyetDinhThanhLapDTXM = "";
	@Size(max=255)
	private String soQuyetDinhGiaHanGiaiQuyet = "";
	@Size(max=255)
	private String soQuyetDinhGiaHanTTXM = "";
	@Size(max=255)
	private String diaDiemDoiThoai = "";
	@Size(max=255)
	private String lyDoGiaHanGiaiQuyet = "";
	@Size(max=255)
	private String lyDoGiaHanTTXM = "";
	@Size(max=255)
	private String noiDung = "";

	private LocalDateTime thoiGianDoiThoai;
	private LocalDateTime ngayBaoCaoKetQuaTTXM;
	
	private LocalDateTime ngayBatDauGiaiQuyet;
	private LocalDateTime ngayKetThucGiaiQuyet;
	private LocalDateTime ngayHetHanGiaiQuyet;
	private LocalDateTime ngayHetHanSauKhiGiaHanGiaiQuyet;
	
	private LocalDateTime ngayBatDauTTXM;
	private LocalDateTime ngayKetThucTTXM;
	private LocalDateTime ngayHetHanTTXM;
	private LocalDateTime ngayHetHanSauKhiGiaHanTTXM;
	
	private LocalDateTime ngayBatDauKTDX;
	private LocalDateTime ngayKetThucKTDX;
	private LocalDateTime ngayHetHanKTDX;
	private LocalDateTime ngayHetHanSauKhiGiaHanKTDX;
	
	private LocalDateTime ngayRaQuyetDinhGiaHanTTXM;
	private LocalDateTime ngayRaQuyetDinhGiaHanGiaiQuyet;

	private boolean lapToDoanXacMinh;
	private boolean giaHanGiaiQuyet;
	private boolean giaHanTTXM;
	private boolean doiThoai;
	private boolean giaoCoQuanDieuTra;
	private boolean khoiTo;
	private boolean quyetDinhGiaiQuyetKhieuNai;
	private boolean theoDoiThucHien;
	private boolean daThuLy;
	private boolean daThamTraXacMinhVuViec;
	private boolean daRaQuyetDinhGiaiQuyet;

	private int soVuGiaoCoQuanDieuTra;
	private int soDoiTuongGiaoCoQuanDieuTra;
	private int soVuBiKhoiTo;
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
	@JoinColumn(name = "don_id")
	private Don don;
	
	@OneToMany(mappedBy = "thongTinGiaiQuyetDon", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<GiaiQuyetDon> giaiQuyetDons = new ArrayList<GiaiQuyetDon>(); // XLD

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

	public String getSoQuyetDinhGiaHanGiaiQuyet() {
		return soQuyetDinhGiaHanGiaiQuyet;
	}

	public void setSoQuyetDinhGiaHanGiaiQuyet(String soQuyetDinhGiaHanGiaiQuyet) {
		this.soQuyetDinhGiaHanGiaiQuyet = soQuyetDinhGiaHanGiaiQuyet;
	}

	public String getDiaDiemDoiThoai() {
		return diaDiemDoiThoai;
	}

	public void setDiaDiemDoiThoai(String diaDiemDoiThoai) {
		this.diaDiemDoiThoai = diaDiemDoiThoai;
	}

	public String getLyDoGiaHanGiaiQuyet() {
		return lyDoGiaHanGiaiQuyet;
	}

	public void setLyDoGiaHanGiaiQuyet(String lyDoGiaHanGiaiQuyet) {
		this.lyDoGiaHanGiaiQuyet = lyDoGiaHanGiaiQuyet;
	}

	public String getLyDoGiaHanTTXM() {
		return lyDoGiaHanTTXM;
	}

	public void setLyDoGiaHanTTXM(String lyDoGiaHanTTXM) {
		this.lyDoGiaHanTTXM = lyDoGiaHanTTXM;
	}

	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	public String getSoQuyetDinhGiaHanTTXM() {
		return soQuyetDinhGiaHanTTXM;
	}

	public void setSoQuyetDinhGiaHanTTXM(String soQuyetDinhGiaHanTTXM) {
		this.soQuyetDinhGiaHanTTXM = soQuyetDinhGiaHanTTXM;
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
	public LocalDateTime getNgayBatDauGiaiQuyet() {
		return ngayBatDauGiaiQuyet;
	}

	public void setNgayBatDauGiaiQuyet(LocalDateTime ngayBatDauGiaiQuyet) {
		this.ngayBatDauGiaiQuyet = ngayBatDauGiaiQuyet;
	}

	public LocalDateTime getNgayKetThucGiaiQuyet() {
		return ngayKetThucGiaiQuyet;
	}

	public void setNgayKetThucGiaiQuyet(LocalDateTime ngayKetThucGiaiQuyet) {
		this.ngayKetThucGiaiQuyet = ngayKetThucGiaiQuyet;
	}
	
	public List<GiaiQuyetDon> getGiaiQuyetDons() {
		return giaiQuyetDons;
	}

	public void setGiaiQuyetDons(List<GiaiQuyetDon> giaiQuyetDons) {
		this.giaiQuyetDons = giaiQuyetDons;
	}

	public LocalDateTime getNgayHetHanGiaiQuyet() {
		return ngayHetHanGiaiQuyet;
	}

	public void setNgayHetHanGiaiQuyet(LocalDateTime ngayHetHanGiaiQuyet) {
		this.ngayHetHanGiaiQuyet = ngayHetHanGiaiQuyet;
	}

	public LocalDateTime getNgayHetHanSauKhiGiaHanGiaiQuyet() {
		return ngayHetHanSauKhiGiaHanGiaiQuyet;
	}

	public void setNgayHetHanSauKhiGiaHanGiaiQuyet(LocalDateTime ngayHetHanSauKhiGiaHanGiaiQuyet) {
		this.ngayHetHanSauKhiGiaHanGiaiQuyet = ngayHetHanSauKhiGiaHanGiaiQuyet;
	}

	public LocalDateTime getNgayBatDauTTXM() {
		return ngayBatDauTTXM;
	}

	public void setNgayBatDauTTXM(LocalDateTime ngayBatDauTTXM) {
		this.ngayBatDauTTXM = ngayBatDauTTXM;
	}

	public LocalDateTime getNgayKetThucTTXM() {
		return ngayKetThucTTXM;
	}

	public void setNgayKetThucTTXM(LocalDateTime ngayKetThucTTXM) {
		this.ngayKetThucTTXM = ngayKetThucTTXM;
	}

	public LocalDateTime getNgayHetHanTTXM() {
		return ngayHetHanTTXM;
	}

	public void setNgayHetHanTTXM(LocalDateTime ngayHetHanTTXM) {
		this.ngayHetHanTTXM = ngayHetHanTTXM;
	}

	public LocalDateTime getNgayHetHanSauKhiGiaHanTTXM() {
		return ngayHetHanSauKhiGiaHanTTXM;
	}

	public void setNgayHetHanSauKhiGiaHanTTXM(LocalDateTime ngayHetHanSauKhiGiaHanTTXM) {
		this.ngayHetHanSauKhiGiaHanTTXM = ngayHetHanSauKhiGiaHanTTXM;
	}

	public LocalDateTime getNgayBatDauKTDX() {
		return ngayBatDauKTDX;
	}

	public void setNgayBatDauKTDX(LocalDateTime ngayBatDauKTDX) {
		this.ngayBatDauKTDX = ngayBatDauKTDX;
	}

	public LocalDateTime getNgayKetThucKTDX() {
		return ngayKetThucKTDX;
	}

	public void setNgayKetThucKTDX(LocalDateTime ngayKetThucKTDX) {
		this.ngayKetThucKTDX = ngayKetThucKTDX;
	}

	public LocalDateTime getNgayHetHanKTDX() {
		return ngayHetHanKTDX;
	}

	public void setNgayHetHanKTDX(LocalDateTime ngayHetHanKTDX) {
		this.ngayHetHanKTDX = ngayHetHanKTDX;
	}

	public LocalDateTime getNgayHetHanSauKhiGiaHanKTDX() {
		return ngayHetHanSauKhiGiaHanKTDX;
	}

	public void setNgayHetHanSauKhiGiaHanKTDX(LocalDateTime ngayHetHanSauKhiGiaHanKTDX) {
		this.ngayHetHanSauKhiGiaHanKTDX = ngayHetHanSauKhiGiaHanKTDX;
	}

	public LocalDateTime getNgayRaQuyetDinhGiaHanTTXM() {
		return ngayRaQuyetDinhGiaHanTTXM;
	}

	public LocalDateTime getNgayRaQuyetDinhGiaHanGiaiQuyet() {
		return ngayRaQuyetDinhGiaHanGiaiQuyet;
	}

	public void setNgayRaQuyetDinhGiaHanGiaiQuyet(LocalDateTime ngayRaQuyetDinhGiaHanGiaiQuyet) {
		this.ngayRaQuyetDinhGiaHanGiaiQuyet = ngayRaQuyetDinhGiaHanGiaiQuyet;
	}

	public void setNgayRaQuyetDinhGiaHanTTXM(LocalDateTime ngayRaQuyetDinhGiaHanTTXM) {
		this.ngayRaQuyetDinhGiaHanTTXM = ngayRaQuyetDinhGiaHanTTXM;
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

	public boolean isGiaHanTTXM() {
		return giaHanTTXM;
	}

	public void setGiaHanTTXM(boolean giaHanTTXM) {
		this.giaHanTTXM = giaHanTTXM;
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

	public int getSoVuGiaoCoQuanDieuTra() {
		return soVuGiaoCoQuanDieuTra;
	}

	public void setSoVuGiaoCoQuanDieuTra(int soVuGiaoCoQuanDieuTra) {
		this.soVuGiaoCoQuanDieuTra = soVuGiaoCoQuanDieuTra;
	}

	public int getSoDoiTuongGiaoCoQuanDieuTra() {
		return soDoiTuongGiaoCoQuanDieuTra;
	}

	public void setSoDoiTuongGiaoCoQuanDieuTra(int soDoiTuongGiaoCoQuanDieuTra) {
		this.soDoiTuongGiaoCoQuanDieuTra = soDoiTuongGiaoCoQuanDieuTra;
	}

	public int getSoVuBiKhoiTo() {
		return soVuBiKhoiTo;
	}

	public void setSoVuBiKhoiTo(int soVuBiKhoiTo) {
		this.soVuBiKhoiTo = soVuBiKhoiTo;
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

	@ApiModelProperty(example = "{}", position = 2)
	public CongChuc getCanBoGiaiQuyet() {
		return canBoGiaiQuyet;
	}

	public void setCanBoGiaiQuyet(CongChuc canBoGiaiQuyet) {
		this.canBoGiaiQuyet = canBoGiaiQuyet;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public CongChuc getTruongDoanTTXM() {
		return truongDoanTTXM;
	}

	public void setTruongDoanTTXM(CongChuc truongDoanTTXM) {
		this.truongDoanTTXM = truongDoanTTXM;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public CongChuc getCanBoThamTraXacMinh() {
		return canBoThamTraXacMinh;
	}

	public void setCanBoThamTraXacMinh(CongChuc canBoThamTraXacMinh) {
		this.canBoThamTraXacMinh = canBoThamTraXacMinh;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public CoQuanQuanLy getCoQuanDieuTra() {
		return coQuanDieuTra;
	}

	public void setCoQuanDieuTra(CoQuanQuanLy coQuanDieuTra) {
		this.coQuanDieuTra = coQuanDieuTra;
	}

	@ApiModelProperty(example = "{}", position = 2)
	public CoQuanQuanLy getCoQuanTheoDoi() {
		return coQuanTheoDoi;
	}

	public void setCoQuanTheoDoi(CoQuanQuanLy coQuanTheoDoi) {
		this.coQuanTheoDoi = coQuanTheoDoi;
	}

	public CoQuanQuanLy getDonViThamTraXacMinh() {
		return donViThamTraXacMinh;
	}
	
	@ApiModelProperty(example = "{}", position = 2)
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
	public Long getThongTinGiaiQuyetDonId() {
		return getId();
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public Long getDonId() {
		if (getDon() != null) {
			return getDon().getId();
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
