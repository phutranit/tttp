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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import vn.greenglobal.tttp.enums.BuocGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.HinhThucThanhTraEnum;
import vn.greenglobal.tttp.enums.LinhVucThanhTraEnum;
import vn.greenglobal.tttp.enums.LoaiHinhThanhTraEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.TienDoThanhTraEnum;

@Entity
@Table(name = "cuocthanhtra")
@ApiModel
public class CuocThanhTra extends Model<CuocThanhTra> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5826384114395527137L;

	@Size(max = 255)
	private String ten = "";//
	@Size(max = 255) // Thanh lap doan
	private String soQuyetDinhThanhLapDoan = "";//
	@Size(max = 255) // Vi pham
	private String soQDXuPhatHCDuocBanHanh = "";//
	@Size(max = 255) // Pham vi thanh tra
	private String kyThanhTra = "";//
	@Size(max = 255)
	private String donViBanHanh = "";//
	@Size(max = 255)
	private String soKetLuanThanhTra = "";//
	@Size(max = 255)
	private String soQuyetDinhGiaHan = "";//
	@Size(max = 255)
	private String soQuyetDinhVeViecThanhTra = "";//
	@Size(max = 255)
	private String ghiChu = "";//
	//@Lob
	private String noiDungThanhTra = "";//
	//@Lob
	private String canCuThanhTraDotXuat = "";//
	//@Lob
	private String canCuThanhTraLai = "";//
	//@Lob
	private String tomTatKetLuanThanhTra = "";//
	//@Lob // Tham nhung
	private String tenNguoi = "";//
	//@Lob
	private String donViPhoiHop = "";//
	//@Lob
	private String doiTuongThanhTraLienQuan = "";//
	//@Lob
	private String doiTuongViPham = "";//
	//@Lob
	private String thongBaoKetThuc = "";//
	//@Lob
	private String lyDonGiaHan = "";//

	private int thoiHanThanhTra = 0;//
	
	// Chuyen co quan dieu tra
	private int soVuDieuTra = 0;//
	private int soDoiTuongDieuTra = 0;//

	// Vi pham
	private int toChucXuLyHanhChinhViPham = 0;//
	private int caNhanXuLyHanhChinhViPham = 0;//
	private int soViPham = 0;//

	// Tham nhung
	private int toChucXuLyHanhChinhThamNhung = 0;//
	private int caNhanXuLyHanhChinhThamNhung = 0;//
	private int soVuThamNhung = 0;//
	private int soDoiTuongThamNhung = 0;//
	private int soNguoiXuLyTrachNhiemDungDau = 0;//

	// Theo doi thuc hien
	// private int toChucDaXuLyHanhChinh = 0;
	// private int caNhanDaXuLyHanhChinh = 0;
	// private int soVuKhoiTo = 0;
	// private int soDoiTuongKhoiTo = 0;

	private boolean chuyenCoQuanDieuTra;//
	private boolean viPham;//
	private boolean phatHienThamNhung;//
	private boolean thanhLapDoan;//
	// private boolean theoDoiThucHien;
	// private boolean kiemTraDonDoc;

	// Vi pham
	private long tienThuViPham;//
	private long datThuViPham;//
	private long tienTraKienNghiThuHoi;//
	private long datTraKienNghiThuHoi;//
	private long tienTraKienNghiKhac;//
	private long datTraKienNghiKhac;//
	private long tienThuTrongQuaTrinhThanhTra;//
	private long datThuTrongQuaTrinhThanhTra;//
	private long datLanChiem;//
	private long giaoDatCapDatSaiDoiTuong;//
	private long capBanDatTraiThamQuyen;//
	private long capGCNQSDDatSai;//
	private long choThueKhongDungQD;//
	private long datKhongDungMDSaiQD;//
	private long boHoangHoa;//
	private long viPhamKhac;//
	private long soTienViPham;//
	private long soTienXuPhatViPham;//
	private long soTienKienNghiThuHoi;//
	private long soTienTichThuXuLyTaiSanViPham;//
	private long soTienTieuHuyXuLyTaiSanViPham;//

	// Tham nhung
	private long tienThamNhung;//
	private long taiSanKhacThamNhung;//
	private long datThamNhung;//
	private long tienKienNghiThuHoi;//
	private long taiSanKhacKienNghiThuHoi;//
	private long datKienNghiThuHoi;//
	private long tienDaThu;//
	private long taiSanKhacDaThu;//
	private long datDaThu;//

	// Ket luan
	// private long tienPhaiThuTheoKetLuan;
	// private long datPhaiThuTheoKetLuan;
	// private long tienDaThuTheoKetLuan;
	// private long datDaThuTheoKetLuan;

	private LocalDateTime ngayRaQuyetDinh;//
	private LocalDateTime ngayCongBoQuyetDinhThanhTra;//
	private LocalDateTime ngayBanHanhKetLuanThanhTra;//
	private LocalDateTime ngayHetHanGiaHanThanhTra;//
	private LocalDateTime ngayRaQuyetDinhGiaHan;//

	// Thanh lap doan
	private LocalDateTime ngayRaQuyetDinhThanhLapDoan;//
	
	// Kiem tra don doc
	// private LocalDateTime thoiGianKiemTraDonDoc;

	@Enumerated(EnumType.STRING)
	private LinhVucThanhTraEnum linhVucThanhTra;//
	@Enumerated(EnumType.STRING)
	private HinhThucThanhTraEnum hinhThucThanhTra;//
	@Enumerated(EnumType.STRING)
	private LoaiHinhThanhTraEnum loaiHinhThanhTra;//
	@Enumerated(EnumType.STRING)
	private TienDoThanhTraEnum tienDoThanhTra;//

	// Theo doi thuc hien
	// @Enumerated(EnumType.STRING)
	// private HinhThucTheoDoiEnum hinhThucTheoDoi;
	// @Enumerated(EnumType.STRING)
	// private KetQuaThucHienTheoDoiEnum ketQuaThucHienTheoDoi;

	@NotNull
	@ManyToOne
	private DoiTuongThanhTra doiTuongThanhTra;//
	@ManyToOne
	private KeHoachThanhTra keHoachThanhTra;//
	@ManyToOne// Chuyen co quan dieu tra
	private CoQuanQuanLy coQuanDieuTra;//
	@NotNull
	@ManyToOne
	private CoQuanQuanLy donViChuTri;//
	@NotNull
	@ManyToOne
	private CoQuanQuanLy donVi;//

	// Theo doi thuc hien
	// @Fetch(FetchMode.SELECT)
	// @ManyToOne
	// private CoQuanQuanLy donViTheoDoi;

	@ManyToMany(fetch = FetchType.EAGER)// Thanh lap doan
	@JoinTable(name = "cuocthanhtra_has_thanhviendoan", joinColumns = {
			@JoinColumn(name = "cuocThanhTra_id") }, inverseJoinColumns = { @JoinColumn(name = "thanhVienDoan_id") })
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<ThanhVienDoan> thanhVienDoans = new ArrayList<ThanhVienDoan>();//
	
	@OneToMany(mappedBy = "cuocThanhTra", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<TaiLieuVanThu> taiLieuVanThus = new ArrayList<TaiLieuVanThu>(); //

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getSoQuyetDinhThanhLapDoan() {
		return soQuyetDinhThanhLapDoan;
	}

	public void setSoQuyetDinhThanhLapDoan(String soQuyetDinhThanhLapDoan) {
		this.soQuyetDinhThanhLapDoan = soQuyetDinhThanhLapDoan;
	}

	public String getSoQDXuPhatHCDuocBanHanh() {
		return soQDXuPhatHCDuocBanHanh;
	}

	public void setSoQDXuPhatHCDuocBanHanh(String soQDXuPhatHCDuocBanHanh) {
		this.soQDXuPhatHCDuocBanHanh = soQDXuPhatHCDuocBanHanh;
	}

	public String getKyThanhTra() {
		return kyThanhTra;
	}

	public void setKyThanhTra(String kyThanhTra) {
		this.kyThanhTra = kyThanhTra;
	}

	public String getCanCuThanhTraLai() {
		return canCuThanhTraLai;
	}

	public void setCanCuThanhTraLai(String canCuThanhTraLai) {
		this.canCuThanhTraLai = canCuThanhTraLai;
	}

	public String getDonViBanHanh() {
		return donViBanHanh;
	}

	public void setDonViBanHanh(String donViBanHanh) {
		this.donViBanHanh = donViBanHanh;
	}

	public String getSoKetLuanThanhTra() {
		return soKetLuanThanhTra;
	}

	public void setSoKetLuanThanhTra(String soKetLuanThanhTra) {
		this.soKetLuanThanhTra = soKetLuanThanhTra;
	}

	public String getSoQuyetDinhGiaHan() {
		return soQuyetDinhGiaHan;
	}

	public void setSoQuyetDinhGiaHan(String soQuyetDinhGiaHan) {
		this.soQuyetDinhGiaHan = soQuyetDinhGiaHan;
	}

	public String getSoQuyetDinhVeViecThanhTra() {
		return soQuyetDinhVeViecThanhTra;
	}

	public void setSoQuyetDinhVeViecThanhTra(String soQuyetDinhVeViecThanhTra) {
		this.soQuyetDinhVeViecThanhTra = soQuyetDinhVeViecThanhTra;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public String getNoiDungThanhTra() {
		return noiDungThanhTra;
	}

	public void setNoiDungThanhTra(String noiDungThanhTra) {
		this.noiDungThanhTra = noiDungThanhTra;
	}

	public String getCanCuThanhTraDotXuat() {
		return canCuThanhTraDotXuat;
	}

	public void setCanCuThanhTraDotXuat(String canCuThanhTraDotXuat) {
		this.canCuThanhTraDotXuat = canCuThanhTraDotXuat;
	}

	public String getTomTatKetLuanThanhTra() {
		return tomTatKetLuanThanhTra;
	}

	public void setTomTatKetLuanThanhTra(String tomTatKetLuanThanhTra) {
		this.tomTatKetLuanThanhTra = tomTatKetLuanThanhTra;
	}

	public String getTenNguoi() {
		return tenNguoi;
	}

	public void setTenNguoi(String tenNguoi) {
		this.tenNguoi = tenNguoi;
	}

	public String getDonViPhoiHop() {
		return donViPhoiHop;
	}

	public void setDonViPhoiHop(String donViPhoiHop) {
		this.donViPhoiHop = donViPhoiHop;
	}

	public String getDoiTuongThanhTraLienQuan() {
		return doiTuongThanhTraLienQuan;
	}

	public void setDoiTuongThanhTraLienQuan(String doiTuongThanhTraLienQuan) {
		this.doiTuongThanhTraLienQuan = doiTuongThanhTraLienQuan;
	}

	public String getDoiTuongViPham() {
		return doiTuongViPham;
	}

	public void setDoiTuongViPham(String doiTuongViPham) {
		this.doiTuongViPham = doiTuongViPham;
	}

	public String getThongBaoKetThuc() {
		return thongBaoKetThuc;
	}

	public void setThongBaoKetThuc(String thongBaoKetThuc) {
		this.thongBaoKetThuc = thongBaoKetThuc;
	}

	public String getLyDonGiaHan() {
		return lyDonGiaHan;
	}

	public void setLyDonGiaHan(String lyDonGiaHan) {
		this.lyDonGiaHan = lyDonGiaHan;
	}

	public int getThoiHanThanhTra() {
		return thoiHanThanhTra;
	}

	public void setThoiHanThanhTra(int thoiHanThanhTra) {
		this.thoiHanThanhTra = thoiHanThanhTra;
	}

	public int getSoVuDieuTra() {
		return soVuDieuTra;
	}

	public void setSoVuDieuTra(int soVuDieuTra) {
		this.soVuDieuTra = soVuDieuTra;
	}

	public int getSoDoiTuongDieuTra() {
		return soDoiTuongDieuTra;
	}

	public void setSoDoiTuongDieuTra(int soDoiTuongDieuTra) {
		this.soDoiTuongDieuTra = soDoiTuongDieuTra;
	}

	public int getSoVuThamNhung() {
		return soVuThamNhung;
	}

	public void setSoVuThamNhung(int soVuThamNhung) {
		this.soVuThamNhung = soVuThamNhung;
	}

	public int getSoDoiTuongThamNhung() {
		return soDoiTuongThamNhung;
	}

	public void setSoDoiTuongThamNhung(int soDoiTuongThamNhung) {
		this.soDoiTuongThamNhung = soDoiTuongThamNhung;
	}

	public int getToChucXuLyHanhChinhViPham() {
		return toChucXuLyHanhChinhViPham;
	}

	public void setToChucXuLyHanhChinhViPham(int toChucXuLyHanhChinhViPham) {
		this.toChucXuLyHanhChinhViPham = toChucXuLyHanhChinhViPham;
	}

	public int getCaNhanXuLyHanhChinhViPham() {
		return caNhanXuLyHanhChinhViPham;
	}

	public void setCaNhanXuLyHanhChinhViPham(int caNhanXuLyHanhChinhViPham) {
		this.caNhanXuLyHanhChinhViPham = caNhanXuLyHanhChinhViPham;
	}

	public int getToChucXuLyHanhChinhThamNhung() {
		return toChucXuLyHanhChinhThamNhung;
	}

	public void setToChucXuLyHanhChinhThamNhung(int toChucXuLyHanhChinhThamNhung) {
		this.toChucXuLyHanhChinhThamNhung = toChucXuLyHanhChinhThamNhung;
	}

	public int getCaNhanXuLyHanhChinhThamNhung() {
		return caNhanXuLyHanhChinhThamNhung;
	}

	public void setCaNhanXuLyHanhChinhThamNhung(int caNhanXuLyHanhChinhThamNhung) {
		this.caNhanXuLyHanhChinhThamNhung = caNhanXuLyHanhChinhThamNhung;
	}

	public int getSoNguoiXuLyTrachNhiemDungDau() {
		return soNguoiXuLyTrachNhiemDungDau;
	}

	public void setSoNguoiXuLyTrachNhiemDungDau(int soNguoiXuLyTrachNhiemDungDau) {
		this.soNguoiXuLyTrachNhiemDungDau = soNguoiXuLyTrachNhiemDungDau;
	}

	public int getSoViPham() {
		return soViPham;
	}

	public void setSoViPham(int soViPham) {
		this.soViPham = soViPham;
	}

	public boolean isChuyenCoQuanDieuTra() {
		return chuyenCoQuanDieuTra;
	}

	public void setChuyenCoQuanDieuTra(boolean chuyenCoQuanDieuTra) {
		this.chuyenCoQuanDieuTra = chuyenCoQuanDieuTra;
	}

	public boolean isViPham() {
		return viPham;
	}

	public void setViPham(boolean viPham) {
		this.viPham = viPham;
	}

	public boolean isPhatHienThamNhung() {
		return phatHienThamNhung;
	}

	public void setPhatHienThamNhung(boolean phatHienThamNhung) {
		this.phatHienThamNhung = phatHienThamNhung;
	}

	public boolean isThanhLapDoan() {
		return thanhLapDoan;
	}

	public void setThanhLapDoan(boolean thanhLapDoan) {
		this.thanhLapDoan = thanhLapDoan;
	}

	public long getTienThuViPham() {
		return tienThuViPham;
	}

	public void setTienThuViPham(long tienThuViPham) {
		this.tienThuViPham = tienThuViPham;
	}

	public long getDatThuViPham() {
		return datThuViPham;
	}

	public void setDatThuViPham(long datThuViPham) {
		this.datThuViPham = datThuViPham;
	}

	public long getTienTraKienNghiThuHoi() {
		return tienTraKienNghiThuHoi;
	}

	public void setTienTraKienNghiThuHoi(long tienTraKienNghiThuHoi) {
		this.tienTraKienNghiThuHoi = tienTraKienNghiThuHoi;
	}

	public long getDatTraKienNghiThuHoi() {
		return datTraKienNghiThuHoi;
	}

	public void setDatTraKienNghiThuHoi(long datTraKienNghiThuHoi) {
		this.datTraKienNghiThuHoi = datTraKienNghiThuHoi;
	}

	public long getTienTraKienNghiKhac() {
		return tienTraKienNghiKhac;
	}

	public void setTienTraKienNghiKhac(long tienTraKienNghiKhac) {
		this.tienTraKienNghiKhac = tienTraKienNghiKhac;
	}

	public long getDatTraKienNghiKhac() {
		return datTraKienNghiKhac;
	}

	public void setDatTraKienNghiKhac(long datTraKienNghiKhac) {
		this.datTraKienNghiKhac = datTraKienNghiKhac;
	}

	public long getTienThuTrongQuaTrinhThanhTra() {
		return tienThuTrongQuaTrinhThanhTra;
	}

	public void setTienThuTrongQuaTrinhThanhTra(long tienThuTrongQuaTrinhThanhTra) {
		this.tienThuTrongQuaTrinhThanhTra = tienThuTrongQuaTrinhThanhTra;
	}

	public long getDatThuTrongQuaTrinhThanhTra() {
		return datThuTrongQuaTrinhThanhTra;
	}

	public void setDatThuTrongQuaTrinhThanhTra(long datThuTrongQuaTrinhThanhTra) {
		this.datThuTrongQuaTrinhThanhTra = datThuTrongQuaTrinhThanhTra;
	}

	public long getTienThamNhung() {
		return tienThamNhung;
	}

	public void setTienThamNhung(long tienThamNhung) {
		this.tienThamNhung = tienThamNhung;
	}

	public long getTaiSanKhacThamNhung() {
		return taiSanKhacThamNhung;
	}

	public void setTaiSanKhacThamNhung(long taiSanKhacThamNhung) {
		this.taiSanKhacThamNhung = taiSanKhacThamNhung;
	}

	public long getDatThamNhung() {
		return datThamNhung;
	}

	public void setDatThamNhung(long datThamNhung) {
		this.datThamNhung = datThamNhung;
	}

	public long getTienKienNghiThuHoi() {
		return tienKienNghiThuHoi;
	}

	public void setTienKienNghiThuHoi(long tienKienNghiThuHoi) {
		this.tienKienNghiThuHoi = tienKienNghiThuHoi;
	}

	public long getTaiSanKhacKienNghiThuHoi() {
		return taiSanKhacKienNghiThuHoi;
	}

	public void setTaiSanKhacKienNghiThuHoi(long taiSanKhacKienNghiThuHoi) {
		this.taiSanKhacKienNghiThuHoi = taiSanKhacKienNghiThuHoi;
	}

	public long getDatKienNghiThuHoi() {
		return datKienNghiThuHoi;
	}

	public void setDatKienNghiThuHoi(long datKienNghiThuHoi) {
		this.datKienNghiThuHoi = datKienNghiThuHoi;
	}

	public long getTienDaThu() {
		return tienDaThu;
	}

	public void setTienDaThu(long tienDaThu) {
		this.tienDaThu = tienDaThu;
	}

	public long getTaiSanKhacDaThu() {
		return taiSanKhacDaThu;
	}

	public void setTaiSanKhacDaThu(long taiSanKhacDaThu) {
		this.taiSanKhacDaThu = taiSanKhacDaThu;
	}

	public long getDatDaThu() {
		return datDaThu;
	}

	public void setDatDaThu(long datDaThu) {
		this.datDaThu = datDaThu;
	}

	public long getDatLanChiem() {
		return datLanChiem;
	}

	public void setDatLanChiem(long datLanChiem) {
		this.datLanChiem = datLanChiem;
	}

	public long getGiaoDatCapDatSaiDoiTuong() {
		return giaoDatCapDatSaiDoiTuong;
	}

	public void setGiaoDatCapDatSaiDoiTuong(long giaoDatCapDatSaiDoiTuong) {
		this.giaoDatCapDatSaiDoiTuong = giaoDatCapDatSaiDoiTuong;
	}

	public long getCapBanDatTraiThamQuyen() {
		return capBanDatTraiThamQuyen;
	}

	public void setCapBanDatTraiThamQuyen(long capBanDatTraiThamQuyen) {
		this.capBanDatTraiThamQuyen = capBanDatTraiThamQuyen;
	}

	public long getCapGCNQSDDatSai() {
		return capGCNQSDDatSai;
	}

	public void setCapGCNQSDDatSai(long capGCNQSDDatSai) {
		this.capGCNQSDDatSai = capGCNQSDDatSai;
	}

	public long getChoThueKhongDungQD() {
		return choThueKhongDungQD;
	}

	public void setChoThueKhongDungQD(long choThueKhongDungQD) {
		this.choThueKhongDungQD = choThueKhongDungQD;
	}

	public long getDatKhongDungMDSaiQD() {
		return datKhongDungMDSaiQD;
	}

	public void setDatKhongDungMDSaiQD(long datKhongDungMDSaiQD) {
		this.datKhongDungMDSaiQD = datKhongDungMDSaiQD;
	}

	public long getBoHoangHoa() {
		return boHoangHoa;
	}

	public void setBoHoangHoa(long boHoangHoa) {
		this.boHoangHoa = boHoangHoa;
	}

	public long getViPhamKhac() {
		return viPhamKhac;
	}

	public void setViPhamKhac(long viPhamKhac) {
		this.viPhamKhac = viPhamKhac;
	}

	public long getSoTienViPham() {
		return soTienViPham;
	}

	public void setSoTienViPham(long soTienViPham) {
		this.soTienViPham = soTienViPham;
	}

	public long getSoTienXuPhatViPham() {
		return soTienXuPhatViPham;
	}

	public void setSoTienXuPhatViPham(long soTienXuPhatViPham) {
		this.soTienXuPhatViPham = soTienXuPhatViPham;
	}

	public long getSoTienKienNghiThuHoi() {
		return soTienKienNghiThuHoi;
	}

	public void setSoTienKienNghiThuHoi(long soTienKienNghiThuHoi) {
		this.soTienKienNghiThuHoi = soTienKienNghiThuHoi;
	}

	public long getSoTienTichThuXuLyTaiSanViPham() {
		return soTienTichThuXuLyTaiSanViPham;
	}

	public void setSoTienTichThuXuLyTaiSanViPham(long soTienTichThuXuLyTaiSanViPham) {
		this.soTienTichThuXuLyTaiSanViPham = soTienTichThuXuLyTaiSanViPham;
	}

	public long getSoTienTieuHuyXuLyTaiSanViPham() {
		return soTienTieuHuyXuLyTaiSanViPham;
	}

	public void setSoTienTieuHuyXuLyTaiSanViPham(long soTienTieuHuyXuLyTaiSanViPham) {
		this.soTienTieuHuyXuLyTaiSanViPham = soTienTieuHuyXuLyTaiSanViPham;
	}

	public LocalDateTime getNgayRaQuyetDinh() {
		return ngayRaQuyetDinh;
	}

	public void setNgayRaQuyetDinh(LocalDateTime ngayRaQuyetDinh) {
		this.ngayRaQuyetDinh = ngayRaQuyetDinh;
	}

	public LocalDateTime getNgayRaQuyetDinhThanhLapDoan() {
		return ngayRaQuyetDinhThanhLapDoan;
	}

	public void setNgayRaQuyetDinhThanhLapDoan(LocalDateTime ngayRaQuyetDinhThanhLapDoan) {
		this.ngayRaQuyetDinhThanhLapDoan = ngayRaQuyetDinhThanhLapDoan;
	}

	public LocalDateTime getNgayCongBoQuyetDinhThanhTra() {
		return ngayCongBoQuyetDinhThanhTra;
	}

	public void setNgayCongBoQuyetDinhThanhTra(LocalDateTime ngayCongBoQuyetDinhThanhTra) {
		this.ngayCongBoQuyetDinhThanhTra = ngayCongBoQuyetDinhThanhTra;
	}

	public LocalDateTime getNgayBanHanhKetLuanThanhTra() {
		return ngayBanHanhKetLuanThanhTra;
	}

	public void setNgayBanHanhKetLuanThanhTra(LocalDateTime ngayBanHanhKetLuanThanhTra) {
		this.ngayBanHanhKetLuanThanhTra = ngayBanHanhKetLuanThanhTra;
	}

	public LocalDateTime getNgayHetHanGiaHanThanhTra() {
		return ngayHetHanGiaHanThanhTra;
	}

	public void setNgayHetHanGiaHanThanhTra(LocalDateTime ngayHetHanGiaHanThanhTra) {
		this.ngayHetHanGiaHanThanhTra = ngayHetHanGiaHanThanhTra;
	}

	public LocalDateTime getNgayRaQuyetDinhGiaHan() {
		return ngayRaQuyetDinhGiaHan;
	}

	public void setNgayRaQuyetDinhGiaHan(LocalDateTime ngayRaQuyetDinhGiaHan) {
		this.ngayRaQuyetDinhGiaHan = ngayRaQuyetDinhGiaHan;
	}

	public LinhVucThanhTraEnum getLinhVucThanhTra() {
		return linhVucThanhTra;
	}

	public void setLinhVucThanhTra(LinhVucThanhTraEnum linhVucThanhTra) {
		this.linhVucThanhTra = linhVucThanhTra;
	}

	public HinhThucThanhTraEnum getHinhThucThanhTra() {
		return hinhThucThanhTra;
	}

	public void setHinhThucThanhTra(HinhThucThanhTraEnum hinhThucThanhTra) {
		this.hinhThucThanhTra = hinhThucThanhTra;
	}

	public LoaiHinhThanhTraEnum getLoaiHinhThanhTra() {
		return loaiHinhThanhTra;
	}

	public void setLoaiHinhThanhTra(LoaiHinhThanhTraEnum loaiHinhThanhTra) {
		this.loaiHinhThanhTra = loaiHinhThanhTra;
	}

	public TienDoThanhTraEnum getTienDoThanhTra() {
		return tienDoThanhTra;
	}

	public void setTienDoThanhTra(TienDoThanhTraEnum tienDoThanhTra) {
		this.tienDoThanhTra = tienDoThanhTra;
	}

	@Transient
	@ApiModelProperty(example = "{}")
	public DoiTuongThanhTra getDoiTuongThanhTra() {
		return doiTuongThanhTra;
	}

	public void setDoiTuongThanhTra(DoiTuongThanhTra doiTuongThanhTra) {
		this.doiTuongThanhTra = doiTuongThanhTra;
	}

	@Transient
	@ApiModelProperty(example = "{}")
	public KeHoachThanhTra getKeHoachThanhTra() {
		return keHoachThanhTra;
	}

	public void setKeHoachThanhTra(KeHoachThanhTra keHoachThanhTra) {
		this.keHoachThanhTra = keHoachThanhTra;
	}

	@Transient
	@ApiModelProperty(example = "{}")
	public CoQuanQuanLy getCoQuanDieuTra() {
		return coQuanDieuTra;
	}

	public void setCoQuanDieuTra(CoQuanQuanLy coQuanDieuTra) {
		this.coQuanDieuTra = coQuanDieuTra;
	}

	@Transient
	@ApiModelProperty(example = "{}")
	public CoQuanQuanLy getDonViChuTri() {
		return donViChuTri;
	}

	public void setDonViChuTri(CoQuanQuanLy donViChuTri) {
		this.donViChuTri = donViChuTri;
	}

	@Transient
	@ApiModelProperty(example = "{}", hidden = true)
	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}

	public List<ThanhVienDoan> getThanhVienDoans() {
		return thanhVienDoans;
	}

	public void setThanhVienDoans(List<ThanhVienDoan> thanhVienDoans) {
		this.thanhVienDoans = thanhVienDoans;
	}

	@ApiModelProperty(hidden = true)
	public List<TaiLieuVanThu> getTaiLieuVanThus() {
		return taiLieuVanThus;
	}

	public void setTaiLieuVanThus(List<TaiLieuVanThu> taiLieuVanThus) {
		this.taiLieuVanThus = taiLieuVanThus;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Long getCuocThanhTraId() {
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
	@ApiModelProperty( hidden = true )
	public Map<String, Object> getDoiTuongThanhTraInfo() {
		if (getDoiTuongThanhTra() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("doiTuongThanhTraId", getDoiTuongThanhTra().getId());
			map.put("ten", getDoiTuongThanhTra().getTen());
			map.put("loaiDoiTuongThanhTraInfo", getDoiTuongThanhTra().getLoaiDoiTuongThanhTraInfo());
			map.put("linhVucDoiTuongThanhTraInfo", getDoiTuongThanhTra().getLinhVucDoiTuongThanhTraInfo());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty( hidden = true )
	public Map<String, Object> getKeHoachThanhTraInfo() {
		if (getKeHoachThanhTra() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("keHoachThanhTraId", getKeHoachThanhTra().getId());
			map.put("namThanhTra", getKeHoachThanhTra().getNamThanhTra());
			map.put("quyetDinhPheDuyetKTTT", getKeHoachThanhTra().getQuyetDinhPheDuyetKTTT());
			map.put("ngayRaQuyetDinh", getKeHoachThanhTra().getNgayRaQuyetDinh());
			map.put("nguoiKy", getKeHoachThanhTra().getNguoiKy());
			map.put("ghiChu", getKeHoachThanhTra().getGhiChu());
			map.put("hinhThucKeHoachThanhTraInfo", getKeHoachThanhTra().getHinhThucKeHoachThanhTraInfo());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty( hidden = true )
	public Map<String, Object> getCoQuanDieuTraInfo() {
		if (getCoQuanDieuTra() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getCoQuanDieuTra().getId());
			map.put("ten", getCoQuanDieuTra().getTen());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty( hidden = true )
	public Map<String, Object> getDonViChuTriInfo() {
		if (getDonViChuTri() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("coQuanQuanLyId", getDonViChuTri().getId());
			map.put("ten", getDonViChuTri().getTen());
			return map;
		}
		return null;
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
	@ApiModelProperty( hidden = true )
	public Map<String, Object> getLinhVucThanhTraInfo() {
		if (getLinhVucThanhTra() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("type", getLinhVucThanhTra().name());
			map.put("text", getLinhVucThanhTra().getText());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty( hidden = true )
	public Map<String, Object> getHinhThucThanhTraInfo() {
		if (getHinhThucThanhTra() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("type", getHinhThucThanhTra().name());
			map.put("text", getHinhThucThanhTra().getText());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty( hidden = true )
	public Map<String, Object> getLoaiHinhThanhTraInfo() {
		if (getLoaiHinhThanhTra() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("type", getLoaiHinhThanhTra().name());
			map.put("text", getLoaiHinhThanhTra().getText());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty( hidden = true )
	public Map<String, Object> getTienDoThanhTraInfo() {
		if (getTienDoThanhTra() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("type", getTienDoThanhTra().name());
			map.put("text", getTienDoThanhTra().getText());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public List<TaiLieuVanThu> getListTaiLieuVanThuKetLuanThanhTra() {
		List<TaiLieuVanThu> list = new ArrayList<TaiLieuVanThu>();
		for (TaiLieuVanThu tlvt : getTaiLieuVanThus()) {
			if (!tlvt.isDaXoa() && ProcessTypeEnum.THANH_TRA.equals(tlvt.getLoaiQuyTrinh())
					&& BuocGiaiQuyetEnum.KET_LUAN_THANH_TRA.equals(tlvt.getBuocGiaiQuyet())) {
				list.add(tlvt);
			}
		}
		return list;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public List<TaiLieuVanThu> getListTaiLieuVanThuGiaHan() {
		List<TaiLieuVanThu> list = new ArrayList<TaiLieuVanThu>();
		for (TaiLieuVanThu tlvt : getTaiLieuVanThus()) {
			if (!tlvt.isDaXoa() && ProcessTypeEnum.THANH_TRA.equals(tlvt.getLoaiQuyTrinh())
					&& BuocGiaiQuyetEnum.GIA_HAN.equals(tlvt.getBuocGiaiQuyet())) {
				list.add(tlvt);
			}
		}
		return list;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public List<TaiLieuVanThu> getListTaiLieuVanThu() {
		List<TaiLieuVanThu> list = new ArrayList<TaiLieuVanThu>();
		for (TaiLieuVanThu tlvt : getTaiLieuVanThus()) {
			if (!tlvt.isDaXoa() && ((ProcessTypeEnum.THANH_TRA.equals(tlvt.getLoaiQuyTrinh()) && tlvt.getBuocGiaiQuyet() == null)
					|| ProcessTypeEnum.KE_HOACH_THANH_TRA.equals(tlvt.getLoaiQuyTrinh()))) {
				list.add(tlvt);
			}
		}
		return list;
	}

}
