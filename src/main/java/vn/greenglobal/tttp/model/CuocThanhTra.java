package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import io.swagger.annotations.ApiModel;
import vn.greenglobal.tttp.enums.CanCuThanhTraLaiEnum;
import vn.greenglobal.tttp.enums.HinhThucThanhTraEnum;
import vn.greenglobal.tttp.enums.HinhThucTheoDoiEnum;
import vn.greenglobal.tttp.enums.KetQuaThucHienTheoDoiEnum;
import vn.greenglobal.tttp.enums.LinhVucThanhTraEnum;
import vn.greenglobal.tttp.enums.LoaiHinhThanhTraEnum;

@Entity
@Table(name = "cuocthanhtra")
@ApiModel
public class CuocThanhTra extends Model<CuocThanhTra> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5826384114395527137L;

	@Size(max = 255)
	private String ten = "";
	@Size(max = 255)
	private String soQuyetDinh = "";
	@Size(max = 255)
	private String soQuyetDinhThanhLapDoan = "";
	@Size(max = 255)
	private String soQDXuPhatHCDuocBanHanh = "";
	@Size(max = 255)
	private String phamViThanhTra = "";
	@Lob
	private String noiDungThanhTra = "";
	@Lob
	private String ghiChu = "";
	@Lob
	private String tomTatKetLuanThanhTra = "";
	@Lob
	private String tenNguoi = "";

	private int thoiHanThanhTra = 0;
	private int luotThanhTra = 0;
	private int soVuDieuTra = 0;
	private int soDoiTuongDieuTra = 0;
	private int soVuThamNhung = 0;
	private int soDoiTuongThamNhung = 0;
	private int toChucXuLyHanhChinhViPham = 0;
	private int caNhanXuLyHanhChinhViPham = 0;
	private int toChucXuLyHanhChinhThamNhung = 0;
	private int caNhanXuLyHanhChinhThamNhung = 0;
	private int toChucDaXuLyHanhChinh = 0;
	private int caNhanDaXuLyHanhChinh = 0;
	private int soVuKhoiTo = 0;
	private int soDoiTuongKhoiTo = 0;
	private int soNguoiXuLyTrachNhiemDungDau = 0;
	private int soViPham = 0;

	private boolean giaoCoQuanDieuTra;
	private boolean viPham;
	private boolean phatHienThamNhung;
	private boolean theoDoiThucHien;
	private boolean kiemTraDonDoc;
	private boolean thanhLapDoan;

	private long tienThuViPham;
	private long datThuViPham;
	private long tienTraKienNghiThuHoi;
	private long datTraKienNghiThuHoi;
	private long tienTraKienNghiKhac;
	private long datTraKienNghiKhac;
	private long tienThuTrongQuaTrinhThanhTra;
	private long datThuTrongQuaTrinhThanhTra;
	private long tienThamNhung;
	private long taiSanKhacThamNhung;
	private long datThamNhung;
	private long tienKienNghiThuHoi;
	private long taiSanKhacKienNghiThuHoi;
	private long datKienNghiThuHoi;
	private long tienDaThu;
	private long taiSanKhacDaThu;
	private long datDaThu;
	private long tienPhaiThuTheoKetLuan;
	private long datPhaiThuTheoKetLuan;
	private long tienDaThuTheoKetLuan;
	private long datDaThuTheoKetLuan;
	private long datLanChiem;
	private long giaoDatCapDatSaiDoiTuong;
	private long capBanDatTraiThamQuyetn;
	private long capGCNQSDDatSai;
	private long choThueKhongDungQD;
	private long datKhongDungMDSaiQD;
	private long boHoangHoa;
	private long viPhamKhac;
	private long soTienViPham;
	private long soTienXuPhatViPham;
	private long soTienKienNghiThuHoi;
	private long soTienTichThuXuLyTaiSanViPham;
	private long soTienTieuHuyXuLyTaiSanViPham;

	private LocalDateTime ngayQuyetDinh;
	private LocalDateTime ngayRaQuyetDinhThanhLapDoan;
	private LocalDateTime ngayCongBoQuyetDinhThanhTra;
	private LocalDateTime thoiGianKiemTraDonDoc;

	@Enumerated(EnumType.STRING)
	private LinhVucThanhTraEnum linhVucThanhTra;
	@Enumerated(EnumType.STRING)
	private HinhThucTheoDoiEnum hinhThucTheoDoi;
	@Enumerated(EnumType.STRING)
	private KetQuaThucHienTheoDoiEnum ketQuaThucHienTheoDoi;
	@Enumerated(EnumType.STRING)
	private HinhThucThanhTraEnum hinhThucThanhTra;
	@Enumerated(EnumType.STRING)
	private LoaiHinhThanhTraEnum loaiHinhThanhTra;
	@Enumerated(EnumType.STRING)
	private CanCuThanhTraLaiEnum canCuThanhTraLai;

	@Fetch(FetchMode.SELECT)
	@ManyToOne
	private DoiTuongThanhTra doiTuongThanhTra;
	@Fetch(FetchMode.SELECT)
	@ManyToOne
	private KeHoachThanhTra keHoachThanhTra;
	@Fetch(FetchMode.SELECT)
	@ManyToOne
	private CoQuanQuanLy coQuanDieuTra;
	@Fetch(FetchMode.SELECT)
	@ManyToOne
	private CoQuanQuanLy donViTheoDoi;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "cuocthanhtra_has_donviphoihop", joinColumns = {
			@JoinColumn(name = "cuocThanhTra_id") }, inverseJoinColumns = { @JoinColumn(name = "coQuanQuanLy_id") })
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<CoQuanQuanLy> donViPhoiHops = new ArrayList<CoQuanQuanLy>();

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "cuocthanhtra_has_thanhviendoan", joinColumns = {
			@JoinColumn(name = "cuocThanhTra_id") }, inverseJoinColumns = { @JoinColumn(name = "thanhVienDoan_id") })
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	private List<ThanhVienDoan> thanhVienDoans = new ArrayList<ThanhVienDoan>();

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getSoQuyetDinh() {
		return soQuyetDinh;
	}

	public void setSoQuyetDinh(String soQuyetDinh) {
		this.soQuyetDinh = soQuyetDinh;
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

	public String getPhamViThanhTra() {
		return phamViThanhTra;
	}

	public void setPhamViThanhTra(String phamViThanhTra) {
		this.phamViThanhTra = phamViThanhTra;
	}

	public String getNoiDungThanhTra() {
		return noiDungThanhTra;
	}

	public void setNoiDungThanhTra(String noiDungThanhTra) {
		this.noiDungThanhTra = noiDungThanhTra;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
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

	public int getThoiHanThanhTra() {
		return thoiHanThanhTra;
	}

	public void setThoiHanThanhTra(int thoiHanThanhTra) {
		this.thoiHanThanhTra = thoiHanThanhTra;
	}

	public int getLuotThanhTra() {
		return luotThanhTra;
	}

	public void setLuotThanhTra(int luotThanhTra) {
		this.luotThanhTra = luotThanhTra;
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

	public int getToChucDaXuLyHanhChinh() {
		return toChucDaXuLyHanhChinh;
	}

	public void setToChucDaXuLyHanhChinh(int toChucDaXuLyHanhChinh) {
		this.toChucDaXuLyHanhChinh = toChucDaXuLyHanhChinh;
	}

	public int getCaNhanDaXuLyHanhChinh() {
		return caNhanDaXuLyHanhChinh;
	}

	public void setCaNhanDaXuLyHanhChinh(int caNhanDaXuLyHanhChinh) {
		this.caNhanDaXuLyHanhChinh = caNhanDaXuLyHanhChinh;
	}

	public int getSoVuKhoiTo() {
		return soVuKhoiTo;
	}

	public void setSoVuKhoiTo(int soVuKhoiTo) {
		this.soVuKhoiTo = soVuKhoiTo;
	}

	public int getSoDoiTuongKhoiTo() {
		return soDoiTuongKhoiTo;
	}

	public void setSoDoiTuongKhoiTo(int soDoiTuongKhoiTo) {
		this.soDoiTuongKhoiTo = soDoiTuongKhoiTo;
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

	public boolean isGiaoCoQuanDieuTra() {
		return giaoCoQuanDieuTra;
	}

	public void setGiaoCoQuanDieuTra(boolean giaoCoQuanDieuTra) {
		this.giaoCoQuanDieuTra = giaoCoQuanDieuTra;
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

	public boolean isTheoDoiThucHien() {
		return theoDoiThucHien;
	}

	public void setTheoDoiThucHien(boolean theoDoiThucHien) {
		this.theoDoiThucHien = theoDoiThucHien;
	}

	public boolean isKiemTraDonDoc() {
		return kiemTraDonDoc;
	}

	public void setKiemTraDonDoc(boolean kiemTraDonDoc) {
		this.kiemTraDonDoc = kiemTraDonDoc;
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

	public long getTienPhaiThuTheoKetLuan() {
		return tienPhaiThuTheoKetLuan;
	}

	public void setTienPhaiThuTheoKetLuan(long tienPhaiThuTheoKetLuan) {
		this.tienPhaiThuTheoKetLuan = tienPhaiThuTheoKetLuan;
	}

	public long getDatPhaiThuTheoKetLuan() {
		return datPhaiThuTheoKetLuan;
	}

	public void setDatPhaiThuTheoKetLuan(long datPhaiThuTheoKetLuan) {
		this.datPhaiThuTheoKetLuan = datPhaiThuTheoKetLuan;
	}

	public long getTienDaThuTheoKetLuan() {
		return tienDaThuTheoKetLuan;
	}

	public void setTienDaThuTheoKetLuan(long tienDaThuTheoKetLuan) {
		this.tienDaThuTheoKetLuan = tienDaThuTheoKetLuan;
	}

	public long getDatDaThuTheoKetLuan() {
		return datDaThuTheoKetLuan;
	}

	public void setDatDaThuTheoKetLuan(long datDaThuTheoKetLuan) {
		this.datDaThuTheoKetLuan = datDaThuTheoKetLuan;
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

	public long getCapBanDatTraiThamQuyetn() {
		return capBanDatTraiThamQuyetn;
	}

	public void setCapBanDatTraiThamQuyetn(long capBanDatTraiThamQuyetn) {
		this.capBanDatTraiThamQuyetn = capBanDatTraiThamQuyetn;
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

	public LocalDateTime getNgayQuyetDinh() {
		return ngayQuyetDinh;
	}

	public void setNgayQuyetDinh(LocalDateTime ngayQuyetDinh) {
		this.ngayQuyetDinh = ngayQuyetDinh;
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

	public LocalDateTime getThoiGianKiemTraDonDoc() {
		return thoiGianKiemTraDonDoc;
	}

	public void setThoiGianKiemTraDonDoc(LocalDateTime thoiGianKiemTraDonDoc) {
		this.thoiGianKiemTraDonDoc = thoiGianKiemTraDonDoc;
	}

	public LinhVucThanhTraEnum getLinhVucThanhTra() {
		return linhVucThanhTra;
	}

	public void setLinhVucThanhTra(LinhVucThanhTraEnum linhVucThanhTra) {
		this.linhVucThanhTra = linhVucThanhTra;
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

	public CanCuThanhTraLaiEnum getCanCuThanhTraLai() {
		return canCuThanhTraLai;
	}

	public void setCanCuThanhTraLai(CanCuThanhTraLaiEnum canCuThanhTraLai) {
		this.canCuThanhTraLai = canCuThanhTraLai;
	}

	public DoiTuongThanhTra getDoiTuongThanhTra() {
		return doiTuongThanhTra;
	}

	public void setDoiTuongThanhTra(DoiTuongThanhTra doiTuongThanhTra) {
		this.doiTuongThanhTra = doiTuongThanhTra;
	}

	public KeHoachThanhTra getKeHoachThanhTra() {
		return keHoachThanhTra;
	}

	public void setKeHoachThanhTra(KeHoachThanhTra keHoachThanhTra) {
		this.keHoachThanhTra = keHoachThanhTra;
	}

	public CoQuanQuanLy getCoQuanDieuTra() {
		return coQuanDieuTra;
	}

	public void setCoQuanDieuTra(CoQuanQuanLy coQuanDieuTra) {
		this.coQuanDieuTra = coQuanDieuTra;
	}

	public CoQuanQuanLy getDonViTheoDoi() {
		return donViTheoDoi;
	}

	public void setDonViTheoDoi(CoQuanQuanLy donViTheoDoi) {
		this.donViTheoDoi = donViTheoDoi;
	}

	public List<CoQuanQuanLy> getDonViPhoiHops() {
		return donViPhoiHops;
	}

	public void setDonViPhoiHops(List<CoQuanQuanLy> donViPhoiHops) {
		this.donViPhoiHops = donViPhoiHops;
	}

	public List<ThanhVienDoan> getThanhVienDoans() {
		return thanhVienDoans;
	}

	public void setThanhVienDoans(List<ThanhVienDoan> thanhVienDoans) {
		this.thanhVienDoans = thanhVienDoans;
	}

}
