package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.HinhThucGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.LoaiDoiTuongEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiNguoiDungDonEnum;
import vn.greenglobal.tttp.enums.NguonTiepNhanDonEnum;
import vn.greenglobal.tttp.enums.PhanLoaiDonCongDanEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.QuyTrinhXuLyDonEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.util.Utils;

@Entity
@Table(name = "don")
public class Don extends Model<Don> {

	private static final long serialVersionUID = 8736658787648062250L;

	private String ma = "";
	@NotBlank
	// @Lob
	private String noiDung = " ";
	private String yeuCauCuaCongDan = "";
	// @Lob
	private String huongGiaiQuyetDaThucHien = " ";
	private String lanGiaiQuyet = "";
	private String yKienXuLyDon = ""; // Xu ly don TCD
	private String ghiChuXuLyDon = ""; // Xu ly don TCD

	private String lyDoDinhChi = "";
	private String soQuyetDinhDinhChi = "";

	private String fileUyQuyen = "";
	private String urlFileUyQuyen = "";
	private String fileChungChiHanhNghe = "";
	private String urlChungChiHanhNghe = "";

	@Transient
	private Long soNgayXuLy;
	@Transient
	private String noiDungThongTinTrinhLanhDao = "";
	@Transient
	private String nguonDonText = "";
	@Transient
	private String trangThaiDonText = "";

	private int soLanKhieuNaiToCao = 0;
	private int tongSoLuotTCD;
	private int soNguoi;

	private boolean coUyQuyen = false;
	@NotNull
	private boolean thanhLapDon = false;
	private boolean daGiaiQuyet = false;
	private boolean daXuLy = false;
	private boolean yeuCauGapTrucTiepLanhDao = false;
	private boolean thanhLapTiepDanGapLanhDao = false;
	private boolean boSungThongTinBiKhieuTo = false;
	private boolean coThongTinCoQuanDaGiaiQuyet = false;
	private boolean lanhDaoDuyet = false;
	
	@NotNull
	private LocalDateTime ngayTiepNhan;
	private LocalDateTime ngayQuyetDinhDinhChi;
	private LocalDateTime ngayLapDonGapLanhDaoTmp;
	private LocalDateTime thoiHanXuLyXLD;
	private LocalDateTime ngayBatDauXLD;
	private LocalDateTime ngayKetThucXLD;
	private LocalDateTime ngayBatDauGiaiQuyet;
	private LocalDateTime ngayketThucGiaiQuyet;
	private LocalDateTime ngayHetHanGiaiQuyet;
	private LocalDateTime ngayBatDauTTXM;
	private LocalDateTime ngayketThucTTXM;
	private LocalDateTime ngayHetHanTTXM;
	private LocalDateTime ngayHetHanSauKhiGiaHanTTXM;

	@OneToOne
	private Don donLanTruoc;
	@ManyToOne
	private CongChuc canBoXuLy;
	@ManyToOne
	private CongChuc canBoXuLyPhanHeXLD;
	@NotNull
	@ManyToOne
	private LinhVucDonThu linhVucDonThu;
	@ManyToOne
	private LinhVucDonThu linhVucDonThuChiTiet;
	@ManyToOne
	private LinhVucDonThu chiTietLinhVucDonThuChiTiet;
	@ManyToOne
	private ThamQuyenGiaiQuyet thamQuyenGiaiQuyet;
	@ManyToOne
	private CoQuanQuanLy coQuanDaGiaiQuyet;
	@ManyToOne
	private CoQuanQuanLy phongBanGiaiQuyet; // Xu ly don TCD

	@OneToMany(mappedBy = "don", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<SoTiepCongDan> tiepCongDans = new ArrayList<SoTiepCongDan>(); // TCD

	@OneToMany(mappedBy = "don", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>(); // TCD

	@OneToMany(mappedBy = "don", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<TaiLieuBangChung> taiLieuBangChungs = new ArrayList<TaiLieuBangChung>(); // TCD
	
	@OneToMany(mappedBy = "don", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<TaiLieuVanThu> taiLieuVanThus = new ArrayList<TaiLieuVanThu>(); // TCD

	@OneToMany(mappedBy = "don", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<XuLyDon> xuLyDons = new ArrayList<XuLyDon>(); // XLD

	@Transient
	private Don_CongDan donCongDan; // TCD

	@Enumerated(EnumType.STRING)
	private TrangThaiDonEnum trangThaiDon; // TCD Enum
	@Enumerated(EnumType.STRING)
	private QuyTrinhXuLyDonEnum quyTrinhXuLy;
	@NotNull
	@Enumerated(EnumType.STRING)
	private LoaiDonEnum loaiDon;
	@NotNull
	@Enumerated(EnumType.STRING)
	private LoaiDoiTuongEnum loaiDoiTuong;
	@NotNull
	@Enumerated(EnumType.STRING)
	private NguonTiepNhanDonEnum nguonTiepNhanDon;
	@Column(nullable = true)
	@NotNull
	@Enumerated(EnumType.STRING)
	private LoaiNguoiDungDonEnum loaiNguoiDungDon;

	@Enumerated(EnumType.STRING)
	private LoaiNguoiDungDonEnum loaiNguoiBiKhieuTo;
	@Enumerated(EnumType.STRING)
	private HinhThucGiaiQuyetEnum hinhThucDaGiaiQuyet;
	@Enumerated(EnumType.STRING)
	private HuongXuLyXLDEnum huongXuLyXLD;

	private String tenCoQuanBKT = "";
	private String diaChiCoQuanBKT = "";
	private String soDienThoaiCoQuanBKT = "";
	@ManyToOne
	private DonViHanhChinh tinhThanhCoQuanBKT;
	@ManyToOne
	private DonViHanhChinh quanHuyenCoQuanBKT;
	@ManyToOne
	private DonViHanhChinh phuongXaCoQuanBKT;
	@ManyToOne
	private ToDanPho toDanPhoCoQuanBKT;

	@ManyToOne
	private State currentState;
	@Enumerated(EnumType.STRING)
	private ProcessTypeEnum processType;

	@ApiModelProperty(hidden = true)
	public List<XuLyDon> getXuLyDons() {
		return xuLyDons;
	}

	public void setXuLyDons(List<XuLyDon> xuLyDons) {
		this.xuLyDons = xuLyDons;
	}

	@ApiModelProperty(hidden = true)
	public Don_CongDan getDonCongDan() {
		return donCongDan;
	}

	public void setDonCongDan(Don_CongDan donCongDan) {
		this.donCongDan = donCongDan;
	}

	@ApiModelProperty(hidden = true)
	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	@ApiModelProperty(position = 1, required = true)
	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		if (noiDung != null && noiDung.length() == 0) {
			this.noiDung = " ";
		} else {
			this.noiDung = noiDung;
		}
	}

	public String getYeuCauCuaCongDan() {
		return yeuCauCuaCongDan;
	}

	public void setYeuCauCuaCongDan(String yeuCauCuaCongDan) {
		this.yeuCauCuaCongDan = yeuCauCuaCongDan;
	}

	public int getSoLanKhieuNaiToCao() {
		return soLanKhieuNaiToCao;
	}

	public void setSoLanKhieuNaiToCao(int soLanKhieuNaiToCao) {
		this.soLanKhieuNaiToCao = soLanKhieuNaiToCao;
	}

	public int getSoNguoi() {
		return soNguoi;
	}

	public void setSoNguoi(int soNguoi) {
		this.soNguoi = soNguoi;
	}

	public boolean isCoUyQuyen() {
		return coUyQuyen;
	}

	public void setCoUyQuyen(boolean coUyQuyen) {
		this.coUyQuyen = coUyQuyen;
	}

	@ApiModelProperty(position = 9)
	public boolean isThanhLapDon() {
		return thanhLapDon;
	}

	public boolean isBoSungThongTinBiKhieuTo() {
		return boSungThongTinBiKhieuTo;
	}

	public void setBoSungThongTinBiKhieuTo(boolean boSungThongTinBiKhieuTo) {
		this.boSungThongTinBiKhieuTo = boSungThongTinBiKhieuTo;
	}

	public boolean isCoThongTinCoQuanDaGiaiQuyet() {
		return coThongTinCoQuanDaGiaiQuyet;
	}

	public void setCoThongTinCoQuanDaGiaiQuyet(boolean coThongTinCoQuanDaGiaiQuyet) {
		this.coThongTinCoQuanDaGiaiQuyet = coThongTinCoQuanDaGiaiQuyet;
	}

	public void setThanhLapDon(boolean thanhLapDon) {
		this.thanhLapDon = thanhLapDon;
	}

	@ApiModelProperty(position = 20)
	public String getLyDoDinhChi() {
		return lyDoDinhChi;
	}

	public void setLyDoDinhChi(String lyDoDinhChi) {
		this.lyDoDinhChi = lyDoDinhChi;
	}

	@ApiModelProperty(position = 21)
	public String getSoQuyetDinhDinhChi() {
		return soQuyetDinhDinhChi;
	}

	public void setSoQuyetDinhDinhChi(String soQuyetDinhDinhChi) {
		this.soQuyetDinhDinhChi = soQuyetDinhDinhChi;
	}

	@ApiModelProperty(position = 22)
	public LocalDateTime getNgayQuyetDinhDinhChi() {
		return ngayQuyetDinhDinhChi;
	}

	public void setNgayQuyetDinhDinhChi(LocalDateTime ngayQuyetDinhDinhChi) {
		this.ngayQuyetDinhDinhChi = ngayQuyetDinhDinhChi;
	}

	@ApiModelProperty(position = 10)
	public LocalDateTime getNgayTiepNhan() {
		return ngayTiepNhan;
	}

	public void setNgayTiepNhan(LocalDateTime ngayTiepNhan) {
		this.ngayTiepNhan = ngayTiepNhan;
	}

	@ApiModelProperty(hidden = true)
	public LocalDateTime getNgayLapDonGapLanhDaoTmp() {
		return ngayLapDonGapLanhDaoTmp;
	}

	public void setNgayLapDonGapLanhDaoTmp(LocalDateTime ngayLapDonGapLanhDaoTmp) {
		this.ngayLapDonGapLanhDaoTmp = ngayLapDonGapLanhDaoTmp;
	}

	@ApiModelProperty(position = 19)
	public String getHuongGiaiQuyetDaThucHien() {
		return huongGiaiQuyetDaThucHien;
	}

	public void setHuongGiaiQuyetDaThucHien(String huongGiaiQuyetDaThucHien) {
		if (huongGiaiQuyetDaThucHien != null && huongGiaiQuyetDaThucHien.length() == 0) {
			this.huongGiaiQuyetDaThucHien = " ";
		} else {
			this.huongGiaiQuyetDaThucHien = huongGiaiQuyetDaThucHien;
		}
	}

	@ApiModelProperty(position = 17)
	public String getLanGiaiQuyet() {
		return lanGiaiQuyet;
	}

	public void setLanGiaiQuyet(String lanGiaiQuyet) {
		this.lanGiaiQuyet = lanGiaiQuyet;
	}

	@ApiModelProperty(position = 24)
	public String getyKienXuLyDon() {
		return yKienXuLyDon;
	}

	public void setyKienXuLyDon(String yKienXuLyDon) {
		this.yKienXuLyDon = yKienXuLyDon;
	}

	@ApiModelProperty(position = 25)
	public String getGhiChuXuLyDon() {
		return ghiChuXuLyDon;
	}

	public void setGhiChuXuLyDon(String ghiChuXuLyDon) {
		this.ghiChuXuLyDon = ghiChuXuLyDon;
	}

	@ApiModelProperty(position = 26, example = "{}")
	public CoQuanQuanLy getCoQuanDaGiaiQuyet() {
		return coQuanDaGiaiQuyet;
	}

	public void setCoQuanDaGiaiQuyet(CoQuanQuanLy coQuanDaGiaiQuyet) {
		this.coQuanDaGiaiQuyet = coQuanDaGiaiQuyet;
	}

	@ApiModelProperty(example = "{}")
	public Don getDonLanTruoc() {
		return donLanTruoc;
	}

	public void setDonLanTruoc(Don donLanTruoc) {
		this.donLanTruoc = donLanTruoc;
	}

	@ApiModelProperty(position = 11, example = "{}")
	public CongChuc getCanBoXuLy() {
		return canBoXuLy;
	}

	public void setCanBoXuLy(CongChuc canBoXuLy) {
		this.canBoXuLy = canBoXuLy;
	}

	@ApiModelProperty(position = 11, example = "{}")
	public CongChuc getCanBoXuLyPhanHeXLD() {
		return canBoXuLyPhanHeXLD;
	}

	public void setCanBoXuLyPhanHeXLD(CongChuc canBoXuLyPhanHeXLD) {
		this.canBoXuLyPhanHeXLD = canBoXuLyPhanHeXLD;
	}

	@ApiModelProperty(position = 16, example = "{}")
	public ThamQuyenGiaiQuyet getThamQuyenGiaiQuyet() {
		return thamQuyenGiaiQuyet;
	}

	public void setThamQuyenGiaiQuyet(ThamQuyenGiaiQuyet thamQuyenGiaiQuyet) {
		this.thamQuyenGiaiQuyet = thamQuyenGiaiQuyet;
	}

	@ApiModelProperty(hidden = true)
	public CoQuanQuanLy getPhongBanGiaiQuyet() {
		return phongBanGiaiQuyet;
	}

	public void setPhongBanGiaiQuyet(CoQuanQuanLy phongBanGiaiQuyet) {
		this.phongBanGiaiQuyet = phongBanGiaiQuyet;
	}

	@ApiModelProperty(position = 2, required = true)
	public LoaiDonEnum getLoaiDon() {
		return loaiDon;
	}

	public void setLoaiDon(LoaiDonEnum loaiDon) {
		this.loaiDon = loaiDon;
	}

	@ApiModelProperty(position = 12)
	public QuyTrinhXuLyDonEnum getQuyTrinhXuLy() {
		return quyTrinhXuLy;
	}

	public void setQuyTrinhXuLy(QuyTrinhXuLyDonEnum quyTrinhXuLy) {
		this.quyTrinhXuLy = quyTrinhXuLy;
	}

	@ApiModelProperty(position = 3, required = true)
	public LoaiDoiTuongEnum getLoaiDoiTuong() {
		return loaiDoiTuong;
	}

	public void setLoaiDoiTuong(LoaiDoiTuongEnum loaiDoiTuong) {
		this.loaiDoiTuong = loaiDoiTuong;
	}

	@ApiModelProperty(position = 4, required = true)
	public NguonTiepNhanDonEnum getNguonTiepNhanDon() {
		return nguonTiepNhanDon;
	}

	public void setNguonTiepNhanDon(NguonTiepNhanDonEnum nguonTiepNhanDon) {
		this.nguonTiepNhanDon = nguonTiepNhanDon;
	}

	@ApiModelProperty(position = 5, required = true)
	public LoaiNguoiDungDonEnum getLoaiNguoiDungDon() {
		return loaiNguoiDungDon;
	}

	public void setLoaiNguoiDungDon(LoaiNguoiDungDonEnum loaiNguoiDungDon) {
		this.loaiNguoiDungDon = loaiNguoiDungDon;
	}

	@ApiModelProperty(position = 18)
	public HinhThucGiaiQuyetEnum getHinhThucDaGiaiQuyet() {
		return hinhThucDaGiaiQuyet;
	}

	public void setHinhThucDaGiaiQuyet(HinhThucGiaiQuyetEnum hinhThucDaGiaiQuyet) {
		this.hinhThucDaGiaiQuyet = hinhThucDaGiaiQuyet;
	}

	@ApiModelProperty(position = 13)
	public HuongXuLyXLDEnum getHuongXuLyXLD() {
		return huongXuLyXLD;
	}

	public void setHuongXuLyXLD(HuongXuLyXLDEnum huongXuLyXLD) {
		this.huongXuLyXLD = huongXuLyXLD;
	}

	@ApiModelProperty(position = 13)
	public TrangThaiDonEnum getTrangThaiDon() {
		return trangThaiDon;
	}

	public void setTrangThaiDon(TrangThaiDonEnum trangThaiDon) {
		this.trangThaiDon = trangThaiDon;
	}

	@ApiModelProperty(position = 6, required = true, example = "{}")
	public LinhVucDonThu getLinhVucDonThu() {
		return linhVucDonThu;
	}

	public void setLinhVucDonThu(LinhVucDonThu linhVucDonThu) {
		this.linhVucDonThu = linhVucDonThu;
	}

	@ApiModelProperty(position = 7, required = true, example = "{}")
	public LinhVucDonThu getLinhVucDonThuChiTiet() {
		return linhVucDonThuChiTiet;
	}

	public void setLinhVucDonThuChiTiet(LinhVucDonThu linhVucDonThuChiTiet) {
		this.linhVucDonThuChiTiet = linhVucDonThuChiTiet;
	}

	@ApiModelProperty(position = 8, example = "{}")
	public LinhVucDonThu getChiTietLinhVucDonThuChiTiet() {
		return chiTietLinhVucDonThuChiTiet;
	}

	public void setChiTietLinhVucDonThuChiTiet(LinhVucDonThu chiTietLinhVucDonThuChiTiet) {
		this.chiTietLinhVucDonThuChiTiet = chiTietLinhVucDonThuChiTiet;
	}

	@ApiModelProperty(hidden = true)
	public List<SoTiepCongDan> getTiepCongDans() {
		return tiepCongDans;
	}

	public void setTiepCongDans(List<SoTiepCongDan> tiepCongDans) {
		this.tiepCongDans = tiepCongDans;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getLinhVucDonThuDon() {
		if (getLinhVucDonThu() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("linhVucDonThuId", getLinhVucDonThu().getId());
			map.put("ten", getLinhVucDonThu().getTen());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getLinhVucDonThuChiTietDon() {
		if (getLinhVucDonThuChiTiet() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("linhVucDonThuId", getLinhVucDonThuChiTiet().getId());
			map.put("ten", getLinhVucDonThuChiTiet().getTen());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getChiTietLinhVucDonThuChiTietDon() {
		if (getChiTietLinhVucDonThuChiTiet() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("linhVucDonThuId", getChiTietLinhVucDonThuChiTiet().getId());
			map.put("ten", getChiTietLinhVucDonThuChiTiet().getTen());
			return map;
		}
		return null;
	}

	@ApiModelProperty(hidden = true)
	public List<Don_CongDan> getDonCongDans() {
		return donCongDans;
	}

	public void setDonCongDans(List<Don_CongDan> donCongDans) {
		this.donCongDans = donCongDans;
	}

	@ApiModelProperty(position = 8, allowableValues = "CA_NHAN, CO_QUAN_TO_CHUC")
	public LoaiNguoiDungDonEnum getLoaiNguoiBiKhieuTo() {
		return loaiNguoiBiKhieuTo;
	}

	public void setLoaiNguoiBiKhieuTo(LoaiNguoiDungDonEnum loaiNguoiBiKhieuTo) {
		this.loaiNguoiBiKhieuTo = loaiNguoiBiKhieuTo;
	}

	@ApiModelProperty(position = 27)
	public String getTenCoQuanBKT() {
		return tenCoQuanBKT;
	}

	public void setTenCoQuanBKT(String tenCoQuanBKT) {
		this.tenCoQuanBKT = tenCoQuanBKT;
	}

	@ApiModelProperty(position = 28)
	public String getDiaChiCoQuanBKT() {
		return diaChiCoQuanBKT;
	}

	public void setDiaChiCoQuanBKT(String diaChiCoQuanBKT) {
		this.diaChiCoQuanBKT = diaChiCoQuanBKT;
	}

	@ApiModelProperty(position = 29)
	public String getSoDienThoaiCoQuanBKT() {
		return soDienThoaiCoQuanBKT;
	}

	public void setSoDienThoaiCoQuanBKT(String soDienThoaiCoQuanBKT) {
		this.soDienThoaiCoQuanBKT = soDienThoaiCoQuanBKT;
	}

	@ApiModelProperty(position = 30, example = "{}")
	public DonViHanhChinh getTinhThanhCoQuanBKT() {
		return tinhThanhCoQuanBKT;
	}

	public void setTinhThanhCoQuanBKT(DonViHanhChinh tinhThanhCoQuanBKT) {
		this.tinhThanhCoQuanBKT = tinhThanhCoQuanBKT;
	}

	@ApiModelProperty(position = 31, example = "{}")
	public DonViHanhChinh getQuanHuyenCoQuanBKT() {
		return quanHuyenCoQuanBKT;
	}

	public void setQuanHuyenCoQuanBKT(DonViHanhChinh quanHuyenCoQuanBKT) {
		this.quanHuyenCoQuanBKT = quanHuyenCoQuanBKT;
	}

	@ApiModelProperty(position = 32, example = "{}")
	public DonViHanhChinh getPhuongXaCoQuanBKT() {
		return phuongXaCoQuanBKT;
	}

	public void setPhuongXaCoQuanBKT(DonViHanhChinh phuongXaCoQuanBKT) {
		this.phuongXaCoQuanBKT = phuongXaCoQuanBKT;
	}

	@ApiModelProperty(position = 33, example = "{}")
	public ToDanPho getToDanPhoCoQuanBKT() {
		return toDanPhoCoQuanBKT;
	}

	public void setToDanPhoCoQuanBKT(ToDanPho toDanPhoCoQuanBKT) {
		this.toDanPhoCoQuanBKT = toDanPhoCoQuanBKT;
	}

	@ApiModelProperty(hidden = true)
	public Don_CongDan getDonCongDan(String phanLoaiCongDan) {
		for (Don_CongDan obj : donCongDans) {
			if (obj.getPhanLoaiCongDan().equals(phanLoaiCongDan)) {
				donCongDan = obj;
				break;
			}
		}
		return donCongDan;
	}

	@ApiModelProperty(hidden = true)
	public State getCurrentState() {
		return currentState;
	}

	public void setCurrentState(State currentState) {
		this.currentState = currentState;
	}

	@ApiModelProperty(hidden = true)
	public ProcessTypeEnum getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessTypeEnum processType) {
		this.processType = processType;
	}

	@ApiModelProperty(hidden = true)
	public int getTongSoLuotTCD() {
		return tongSoLuotTCD;
	}

	public void setTongSoLuotTCD(int tongSoLuotTCD) {
		this.tongSoLuotTCD = tongSoLuotTCD;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public List<Don_CongDan> getListNguoiDungDon() {
		List<Don_CongDan> list = new ArrayList<Don_CongDan>();
		for (Don_CongDan dcd : getDonCongDans()) {
			if (PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON.equals(dcd.getPhanLoaiCongDan()) && !dcd.isDaXoa()) {
				list.add(dcd);
			}
		}
		return list;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public List<Don_CongDan> getListThanhVienDoanDongNguoi() {
		List<Don_CongDan> list = new ArrayList<Don_CongDan>();
		for (Don_CongDan dcd : getDonCongDans()) {
			if ((PhanLoaiDonCongDanEnum.THANH_VIEN_DOAN_NHIEU_NGUOI.equals(dcd.getPhanLoaiCongDan())
					|| PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON.equals(dcd.getPhanLoaiCongDan())) && !dcd.isDaXoa()) {
				list.add(dcd);
			}
		}
		return list;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Don_CongDan getNguoiDuocUyQuyen() {
		for (Don_CongDan dcd : getDonCongDans()) {
			if (PhanLoaiDonCongDanEnum.NGUOI_DUOC_UY_QUYEN.equals(dcd.getPhanLoaiCongDan()) && !dcd.isDaXoa()) {
				return dcd;
			}
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Don_CongDan getDoiTuongBiKhieuTo() {
		if (LoaiNguoiDungDonEnum.CA_NHAN.equals(getLoaiNguoiBiKhieuTo())) {
			for (Don_CongDan dcd : getDonCongDans()) {
				if (PhanLoaiDonCongDanEnum.DOI_TUONG_BI_KHIEU_TO.equals(dcd.getPhanLoaiCongDan()) && !dcd.isDaXoa()) {
					return dcd;
				}
			}
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public List<TaiLieuBangChung> getListTaiLieuBangChung() {
		List<TaiLieuBangChung> list = new ArrayList<TaiLieuBangChung>();
		for (TaiLieuBangChung tlbc : getTaiLieuBangChungs()) {
			if (!tlbc.isDaXoa()) {
				list.add(tlbc);
			}
		}
		return list;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public List<TaiLieuVanThu> getListTaiLieuVanThu() {
		List<TaiLieuVanThu> list = new ArrayList<TaiLieuVanThu>();
		for (TaiLieuVanThu tlvt : getTaiLieuVanThus()) {
			if (!tlvt.isDaXoa()) {
				list.add(tlvt);
			}
		}
		return list;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getDonId() {
		return getId();
	}

	@ApiModelProperty(hidden = true)
	public List<TaiLieuBangChung> getTaiLieuBangChungs() {
		return taiLieuBangChungs;
	}

	public void setTaiLieuBangChungs(List<TaiLieuBangChung> taiLieuBangChungs) {
		this.taiLieuBangChungs = taiLieuBangChungs;
	}
	
	@ApiModelProperty(hidden = true)
	public List<TaiLieuVanThu> getTaiLieuVanThus() {
		return taiLieuVanThus;
	}

	public void setTaiLieuVanThus(List<TaiLieuVanThu> taiLieuVanThus) {
		this.taiLieuVanThus = taiLieuVanThus;
	}

	@ApiModelProperty(hidden = true)
	public boolean isDaGiaiQuyet() {
		return daGiaiQuyet;
	}

	public void setDaGiaiQuyet(boolean daGiaiQuyet) {
		this.daGiaiQuyet = daGiaiQuyet;
	}

	@ApiModelProperty(hidden = true)
	public boolean isDaXuLy() {
		return daXuLy;
	}

	public void setDaXuLy(boolean daXuLy) {
		this.daXuLy = daXuLy;
	}

	public boolean isYeuCauGapTrucTiepLanhDao() {
		return yeuCauGapTrucTiepLanhDao;
	}

	public void setYeuCauGapTrucTiepLanhDao(boolean yeuCauGapTrucTiepLanhDao) {
		this.yeuCauGapTrucTiepLanhDao = yeuCauGapTrucTiepLanhDao;
	}

	public boolean isThanhLapTiepDanGapLanhDao() {
		return thanhLapTiepDanGapLanhDao;
	}

	public void setThanhLapTiepDanGapLanhDao(boolean thanhLapTiepDanGapLanhDao) {
		this.thanhLapTiepDanGapLanhDao = thanhLapTiepDanGapLanhDao;
	}

	@ApiModelProperty(hidden = true)
	@Transient
	public Map<String, Object> getCoQuanDaGiaiQuyetDon() {
		if (getCoQuanDaGiaiQuyet() != null) {
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapCapCoQuanQuanLy = new HashMap<>();
			map.put("coQuanQuanLyId", getCoQuanDaGiaiQuyet().getId());
			map.put("ten", getCoQuanDaGiaiQuyet().getTen());
			mapCapCoQuanQuanLy.put("capCoQuanQuanLyId", getCoQuanDaGiaiQuyet().getCapCoQuanQuanLy() != null
					? getCoQuanDaGiaiQuyet().getCapCoQuanQuanLy().getId() : "");
			mapCapCoQuanQuanLy.put("ten", getCoQuanDaGiaiQuyet().getCapCoQuanQuanLy() != null
					? getCoQuanDaGiaiQuyet().getCapCoQuanQuanLy().getTen() : "");
			map.put("capCoQuanQuanLyInfo", mapCapCoQuanQuanLy);
			return map;
		}
		return null;
	}

	@ApiModelProperty(hidden = true)
	@Transient
	public Map<String, Object> getTinhThanhCoQuanBKTInfo() {
		if (getTinhThanhCoQuanBKT() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("donViHanhChinhId", getTinhThanhCoQuanBKT().getId());
			map.put("ten", getTinhThanhCoQuanBKT().getTen());
			return map;
		}
		return null;
	}

	@ApiModelProperty(hidden = true)
	@Transient
	public Map<String, Object> getQuanHuyenCoQuanBKTInfo() {
		if (getQuanHuyenCoQuanBKT() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("donViHanhChinhId", getQuanHuyenCoQuanBKT().getId());
			map.put("ten", getQuanHuyenCoQuanBKT().getTen());
			return map;
		}
		return null;
	}

	@ApiModelProperty(hidden = true)
	@Transient
	public Map<String, Object> getPhuongXaCoQuanBKTInfo() {
		if (getPhuongXaCoQuanBKT() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("donViHanhChinhId", getPhuongXaCoQuanBKT().getId());
			map.put("ten", getPhuongXaCoQuanBKT().getTen());
			return map;
		}
		return null;
	}

	@ApiModelProperty(hidden = true)
	@Transient
	public Map<String, Object> getToDanPhoCoQuanBKTInfo() {
		if (getToDanPhoCoQuanBKT() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("toDanPhoId", getToDanPhoCoQuanBKT().getId());
			map.put("ten", getToDanPhoCoQuanBKT().getTen());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getNguoiTaoInfo() {
		if (getNguoiTao() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId",
					getNguoiTao().getCoQuanQuanLy() != null ? getNguoiTao().getCoQuanQuanLy().getId() : 0);
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
			map.put("coQuanQuanLyId",
					getNguoiSua().getCoQuanQuanLy() != null ? getNguoiSua().getCoQuanQuanLy().getId() : 0);
			map.put("hoVaTen", getNguoiSua().getHoVaTen());
			map.put("congChucId", getNguoiSua().getId());
			return map;
		}
		return null;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getCoQuanDangQuyetInfo() {
		if (getNguoiSua() != null) {
			Map<String, Object> map = new HashMap<>();
			CoQuanQuanLy coQuanDangGiaiQuyet = null;
			if (getPhongBanGiaiQuyet() != null) {
				coQuanDangGiaiQuyet = getPhongBanGiaiQuyet();
				if (coQuanDangGiaiQuyet.getCha() != null) {
					coQuanDangGiaiQuyet = coQuanDangGiaiQuyet.getCha();
				}
			}
			map.put("coQuanQuanLyId", coQuanDangGiaiQuyet != null ? coQuanDangGiaiQuyet.getId() : 0);
			map.put("ten", coQuanDangGiaiQuyet != null ? coQuanDangGiaiQuyet.getTen() : "");
			return map;
		}
		return null;
	}

	@ApiModelProperty(hidden = true)
	public String getNguonDonText() {
		nguonDonText = nguonTiepNhanDon.getText();

		if (xuLyDons.size() > 0) {
			int thuTu = xuLyDons.size();
			XuLyDon xld = xuLyDons.get(thuTu - 1);
			if (xld.isDonChuyen()) {
				nguonDonText = xld.getCoQuanTiepNhan().getTen();
			}
		}
		return nguonDonText;
	}

	public void setNguonDonText(String nguonDonText) {
		this.nguonDonText = nguonDonText;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public ThamQuyenGiaiQuyet getThamQuyenGiaiQuyetInfo() {
		return getThamQuyenGiaiQuyet();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getThoiHanXuLyDon() {
		long thoiHan = -1;
		if (xuLyDons.size() > 0) {
			int thuTu = xuLyDons.size();
			XuLyDon xld = xuLyDons.get(thuTu - 1);
			if (xld.getThoiHanXuLy() != null) {
				thoiHan = Utils.convertLocalDateTimeToNumber(xld.getThoiHanXuLy());
			} else {
				return null;
			}
		}
		return thoiHan;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public String getNoiDungVaHanXuLy() {
		String nd = getNoiDung();
		Long hanXuLy = getThoiHanXuLyDon();
		String out = "";
		if (nd != null && !nd.isEmpty()) {
			out += " - " + nd;
		}
		if (hanXuLy != null) {
			out += "\n - " + "Còn " + hanXuLy + " ngày";
		}
		return out;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public String getPhanLoaiDonSoNguoi() {
		String nd = getLoaiDon().getText();
		Long hanXuLy = getThoiHanXuLyDon();
		String out = "";
		if (nd != null && !nd.isEmpty()) {
			out += " - " + nd;
		}
		if (hanXuLy != null) {
			out += "\n - " + "Còn " + hanXuLy + " ngày";
		}
		return out;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public String getTinhTrangXuLyText() {
		TrangThaiDonEnum ttd = getTrangThaiDon();
		QuyTrinhXuLyDonEnum qtxl = getQuyTrinhXuLy();
		String out = "- ";
		if (ttd != null) {
			out += ttd.name().equalsIgnoreCase(TrangThaiDonEnum.DANG_XU_LY.name()) ? "Đang giải quyết" : "Hoàn thành";
		}
		if (qtxl != null) {
			out += "\n - " + qtxl.getText();
		}
		return out;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public String getTrangThaiDonText() {
		trangThaiDonText = "Đang xử lý";
		if (xuLyDons.size() > 0) {
			List<XuLyDon> xlds = new ArrayList<XuLyDon>();
			// hxl
			xlds.addAll(xuLyDons);
			xlds = xlds.stream().filter(xld -> xld.getHuongXuLy() != null).collect(Collectors.toList());
			if (xlds.size() > 0) {
				// ttd ht
				trangThaiDonText = "Hoàn thành";
			}
		}
		return trangThaiDonText;
	}

	public void setTrangThaiDonText(String trangThaiDonText) {
		this.trangThaiDonText = trangThaiDonText;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public String getQuyTrinhXuLyText() {
		String out = "";
		trangThaiDonText = "Đang xử lý";
		if (xuLyDons.size() > 0) {
			List<XuLyDon> xlds = new ArrayList<XuLyDon>();
			// hxl
			xlds.addAll(xuLyDons);
			xlds = xlds.stream().filter(xld -> xld.getHuongXuLy() != null).collect(Collectors.toList());
			if (xlds.size() > 0) {
				XuLyDon xld = xlds.get(xlds.size() - 1);
				out = xld != null ? xld.getHuongXuLy().getText() : "";
				// ttd ht
				trangThaiDonText = "Hoàn thành";
			}
		}
		return out;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public String getCanBoXuLyText() {
		String out = "";
		if (xuLyDons.size() > 0) {
			List<XuLyDon> xlds = new ArrayList<XuLyDon>();
			xlds.addAll(xuLyDons);
			int thuTu = xlds.size() - 1;
			out = xlds.get(thuTu).getNguoiTao().getHoVaTen();
		}
		return out;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getThongTinXuLyInfo() {
		Map<String, Object> map = new HashMap<>();
		if (xuLyDons.size() > 0) {
			int thuTu = xuLyDons.size();
			XuLyDon xld = xuLyDons.get(thuTu - 1);
			map.put("huongXuLyText", xld.getHuongXuLy() != null ? xld.getHuongXuLy().getText() : "");
			map.put("nhomThamQuyenGiaiQuyetText",
					xld.getThamQuyenGiaiQuyet() != null ? xld.getThamQuyenGiaiQuyet().getTen() : "");

			map.put("phongBanGiaiQuyetText",
					xld.getPhongBanGiaiQuyet() != null ? xld.getPhongBanGiaiQuyet().getTen() : "");
			map.put("coQuanTiepNhanText", xld.getCoQuanTiepNhan() != null ? xld.getCoQuanTiepNhan().getTen() : "");

			map.put("lyDo", xld.getyKienXuLy() != null ? xld.getyKienXuLy() : "");
			map.put("ngayHen", xld.getyKienXuLy() != null ? xld.getyKienXuLy() : "");
			map.put("diaDiem", xld.getDiaDiem() != null ? xld.getDiaDiem() : "");
			map.put("ngayHen", xld.getNgayHenGapLanhDao() != null ? xld.getNgayHenGapLanhDao() : "");
			map.put("chucVuGiaoViec", xld.getChucVuGiaoViec() != null ? xld.getChucVuGiaoViec().getText() : "");
			map.put("isDonChuyen", xld.isDonChuyen());
			map.put("coQuanChuyenDenText", xld.getCoQuanChuyenDon() != null ? xld.getCoQuanChuyenDon().getTen() : "");
			map.put("phongBanXuLyId", xld.getPhongBanXuLy() != null ? xld.getPhongBanXuLy().getId() : "");
			map.put("phongBanXuLyText", xld.getPhongBanXuLy() != null ? xld.getPhongBanXuLy().getTen() : "");
			map.put("phongBanXuLyChiDinhId",
					xld.getPhongBanXuLyChiDinh() != null ? xld.getPhongBanXuLyChiDinh().getId() : "");
			map.put("phongBanXuLyChiDinhText",
					xld.getPhongBanXuLyChiDinh() != null ? xld.getPhongBanXuLyChiDinh().getTen() : "");
			map.put("soNgayConLai", xld.getNgayConLai() != null ? xld.getNgayConLai() : "");
			map.put("canBoXuLyChiDinhText",
					xld.getCanBoXuLyChiDinh() != null ? xld.getCanBoXuLyChiDinh().getHoVaTen() : "");
			map.put("canBoXuLyText", xld.getCanBoXuLy() != null ? xld.getCanBoXuLy().getHoVaTen() : "");
			map.put("huongXuLy", xld.getHuongXuLy() != null ? xld.getHuongXuLy() : "");
			map.put("donViPhongBanId", xld.getPhongBanXuLy() != null
					? xld.getPhongBanXuLy().getDonVi() != null ? xld.getPhongBanXuLy().getDonVi().getId() : "" : "");
			map.put("donViPhongBanText", xld.getPhongBanXuLy() != null
					? xld.getPhongBanXuLy().getDonVi() != null ? xld.getPhongBanXuLy().getDonVi().getTen() : "" : "");

			map.put("ngayHen", xld.getNgayHenGapLanhDao() != null ? xld.getNgayHenGapLanhDao() : "");
			// map.put("canBoXuLyText", xld.getCanBoXuLy() != null ?
			// xld.getCanBoXuLy().getHoVaTen() : "");
			map.put("hanXuLyText", xld.getThoiHanXuLy() != null ? xld.getThoiHanXuLy() : "");
			map.put("quyTrinhXuLyCuaLD", "");
			map.put("quyTrinhXuLyCuaPB", "");
			map.put("thoiHanXuLy",
					getThoiHanXuLyXLD() != null ? Utils.convertLocalDateTimeToNumber(getThoiHanXuLyXLD()) : "");
			map.put("noiDungTrinhLanhDao", xld.getNoiDungThongTinTrinhLanhDao());

			List<XuLyDon> xlds = new ArrayList<XuLyDon>();
			xlds.addAll(xuLyDons);
			xlds = xlds.stream().filter(x -> x.getChucVu().equals(VaiTroEnum.LANH_DAO)).collect(Collectors.toList());

			xlds.clear();
			xlds.addAll(xuLyDons);
			xlds = xlds.stream().filter(x -> x.getChucVu().equals(VaiTroEnum.TRUONG_PHONG))
					.collect(Collectors.toList());

			return map;
		}
		return null;
	}

	public String getNoiDungThongTinTrinhLanhDao() {
		return noiDungThongTinTrinhLanhDao;
	}

	public void setNoiDungThongTinTrinhLanhDao(String noiDungThongTinTrinhLanhDao) {
		this.noiDungThongTinTrinhLanhDao = noiDungThongTinTrinhLanhDao;
	}

	public Long getSoNgayXuLy() {
		return soNgayXuLy;
	}

	public void setSoNgayXuLy(long soNgayXuLy) {
		this.soNgayXuLy = soNgayXuLy;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public String getLoaiDonText() {
		String out = "";
		LoaiDonEnum loaiDon = getLoaiDon();
		if (loaiDon != null) {
			out = loaiDon.getText();
		}
		return out;
	}

	public LocalDateTime getThoiHanXuLyXLD() {
		return thoiHanXuLyXLD;
	}

	public void setThoiHanXuLyXLD(LocalDateTime thoiHanXuLyXLD) {
		this.thoiHanXuLyXLD = thoiHanXuLyXLD;
	}

	public LocalDateTime getNgayBatDauXLD() {
		return ngayBatDauXLD;
	}

	public void setNgayBatDauXLD(LocalDateTime ngayBatDauXLD) {
		this.ngayBatDauXLD = ngayBatDauXLD;
	}

	public LocalDateTime getNgayKetThucXLD() {
		return ngayKetThucXLD;
	}

	public void setNgayKetThucXLD(LocalDateTime ngayKetThucXLD) {
		this.ngayKetThucXLD = ngayKetThucXLD;
	}

	public String getFileUyQuyen() {
		return fileUyQuyen;
	}

	public void setFileUyQuyen(String fileUyQuyen) {
		this.fileUyQuyen = fileUyQuyen;
	}

	public String getUrlFileUyQuyen() {
		return urlFileUyQuyen;
	}

	public void setUrlFileUyQuyen(String urlFileUyQuyen) {
		this.urlFileUyQuyen = urlFileUyQuyen;
	}

	public String getFileChungChiHanhNghe() {
		return fileChungChiHanhNghe;
	}

	public void setFileChungChiHanhNghe(String fileChungChiHanhNghe) {
		this.fileChungChiHanhNghe = fileChungChiHanhNghe;
	}

	public String getUrlChungChiHanhNghe() {
		return urlChungChiHanhNghe;
	}

	public void setUrlChungChiHanhNghe(String urlChungChiHanhNghe) {
		this.urlChungChiHanhNghe = urlChungChiHanhNghe;
	}

	@ApiModelProperty(hidden = true)
	@Transient
	public Map<String, Object> getCurrentStateInfo() {
		if (getCurrentState() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("currentStateId", getCurrentState().getId());
			map.put("ten", getCurrentState().getTen());
			map.put("tenVietTat", getCurrentState().getTenVietTat());
			map.put("type", getCurrentState().getType().toString());
			return map;
		}
		return null;
	}

	public LocalDateTime getNgayBatDauGiaiQuyet() {
		return ngayBatDauGiaiQuyet;
	}

	public void setNgayBatDauGiaiQuyet(LocalDateTime ngayBatDauGiaiQuyet) {
		this.ngayBatDauGiaiQuyet = ngayBatDauGiaiQuyet;
	}

	public LocalDateTime getNgayketThucGiaiQuyet() {
		return ngayketThucGiaiQuyet;
	}

	public void setNgayketThucGiaiQuyet(LocalDateTime ngayketThucGiaiQuyet) {
		this.ngayketThucGiaiQuyet = ngayketThucGiaiQuyet;
	}

	public LocalDateTime getNgayHetHanGiaiQuyet() {
		return ngayHetHanGiaiQuyet;
	}

	public void setNgayHetHanGiaiQuyet(LocalDateTime ngayHetHanGiaiQuyet) {
		this.ngayHetHanGiaiQuyet = ngayHetHanGiaiQuyet;
	}

	public LocalDateTime getNgayBatDauTTXM() {
		return ngayBatDauTTXM;
	}

	public void setNgayBatDauTTXM(LocalDateTime ngayBatDauTTXM) {
		this.ngayBatDauTTXM = ngayBatDauTTXM;
	}

	public LocalDateTime getNgayketThucTTXM() {
		return ngayketThucTTXM;
	}

	public void setNgayketThucTTXM(LocalDateTime ngayketThucTTXM) {
		this.ngayketThucTTXM = ngayketThucTTXM;
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
	
	public boolean isLanhDaoDuyet() {
		return lanhDaoDuyet;
	}

	public void setLanhDaoDuyet(boolean lanhDaoDuyet) {
		this.lanhDaoDuyet = lanhDaoDuyet;
	
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public String getThoiHanXuLy() {
		String str = "";
		if (xuLyDons.size() > 0) {
			int thuTu = xuLyDons.size();
			XuLyDon xld = xuLyDons.get(thuTu - 1);
			if (xld.getThoiHanXuLy() != null) {
				long soNgayXuLy = Utils.getLaySoNgay(xld.getThoiHanXuLy());
				if (soNgayXuLy > 0) {
					return soNgayXuLy + "";
				} else if (soNgayXuLy == -1) {
					return "-1";
				} else if (soNgayXuLy == 0) {
					str = "Còn " + Utils.getLaySoGioPhut(xld.getThoiHanXuLy());
				} else {
					str = "Còn " + Utils.getLaySoGioPhut(xld.getThoiHanXuLy());
				}
			}
		}
		return str;
	}
}