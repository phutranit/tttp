package vn.greenglobal.tttp.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.BuocGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.CoQuanDieuTraThanhTraEnum;
import vn.greenglobal.tttp.enums.HinhThucKienNghiEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;

@Entity
@Table(name = "doituongvipham")
@ApiModel
public class DoiTuongViPham extends Model<DoiTuongViPham> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5973872672011950280L;
	
	@Size(max=255)
	private String ten = "";
	@Size(max=255) // Kien nghi chuyen co quan dieu tra
	private String soQuyetDinhChuyenCoQuanDieuTra = "";
	@Size(max=255) // Kien nghi chuyen co quan dieu tra
	private String nguoiRaQuyetDinhChuyenCoQuanDieuTra = "";
	@Size(max=255) // Kien nghi chuyen co quan dieu tra
	private String coQuanDieuTraKhac = "";
	@Lob // Kien nghi chuyen co quan dieu tra
	private String donViViPham = "";
	@Lob // Kien nghi chuyen co quan dieu tra
	private String viPhamKhac = "";
	@Lob // Kien nghi xu ly ve hanh chinh
	private String toChucCoSaiPham = "";
	@Lob // Kien nghi xu ly ve hanh chinh
	private String caNhanCoSaiPham = "";
	@Lob // Kien nghi xu ly ve kinh te
	private String saiPhamKhac = "";
	@Size(max=255) // Kien nghi xu ly ve kinh te
	private String quyetDinhGiaoDat = "";
	@Lob // Kien nghi xu ly khac
	private String noiDungKienNghiXuLyKhac = "";
	@Size(max=255) // Kien nghi xu ly ve hanh chinh
	private String soQuyetDinhKienNghiXuLyHanhChinhToChuc = "";
	@Size(max=255) // Kien nghi xu ly ve hanh chinh
	private String soQuyetDinhKienNghiXuLyHanhChinhCaNhan = "";
	@Size(max=255) // Thu hoi trong qua trinh thanh tra
	private String quyetDinhGiaoDatThuHoiTrongQuaTrinhThanhTra;
	
	//Kien nghi chuyen co quan dieu tra
	private boolean chuyenCoQuanDieuTra;
		
	// Kien nghi chuyen co quan dieu tra
	private int soVuViecKienNghiChuyenCoQuanDieuTra = 0;
	// Kien nghi chuyen co quan dieu tra
	private int soVuChuyenCoQuanDieuTra = 0;
	
	// Thu hoi trong qua trinh thanh tra
	private long tienDaThuTrongQuaTrinhThanhTra;
	private long datDaThuTrongQuaTrinhThanhTra;
	private long tongSoTienThuHoiTrongQuaTrinhThanhTra;
	private long caNhanTienThuHoiTrongQuaTrinhThanhTra;
	private long toChucTienThuHoiTrongQuaTrinhThanhTra;
	// Kien nghi xu ly ve kinh te
	private long saiPhamVeTienKienNghiThuHoiKienNghiXuLyVeKinhTe;
	private long saiPhamVeDatKienNghiThuHoiKienNghiXuLyVeKinhTe;
	private long saiPhamVeTienKienNghiKhacXuLyVeKinhTe;
	private long saiPhamVeDatKienNghiKhacXuLyVeKinhTe;
	private long tongSoTienViPhamKienNghiXuLyVeKinhTe;
	private long tienCaNhanViPhamKienNghiXuLyVeKinhTe;
	private long tienToChucViPhamKienNghiXuLyVeKinhTe;
	private long soTienKienNghiThuHoiKienNghiXuLyVeKinhTe;
	private long tongSoTienXuLyTaiSanViPhamKienNghiXuLyVeKinhTe;
	private long tienTichThuXuLyTaiSanViPhamKienNghiXuLyVeKinhTe;
	private long tienTieuHuyXuLyTaiSanViPhamKienNghiXuLyVeKinhTe;
	private long tongSoTienXuPhatViPhamKienNghiXuLyVeKinhTe;
	private long tienCaNhanXuPhatViPhamKienNghiXuLyVeKinhTe;
	private long tienToChucXuPhatViPhamKienNghiXuLyVeKinhTe;
	// Kien nghi xu ly khac
	private long tienKienNghiXuLyKhac;
	private long datKienNghiXuLyKhac;
	// Kien nghi xu ly ve dat
	private long datLanChiemKienNghiXuLyVeDat;
	private long giaoCapDatSaiDoiTuongQDKienNghiXuLyVeDat;
	private long capBanDatTraiThamQuyenKienNghiXuLyVeDat;
	private long capGCNQSDDatSaiKienNghiXuLyVeDat;
	private long cNChoThueKhongDungQDKienNghiXuLyVeDat;
	private long sDDatKhongDungMDKienNghiXuLyVeDat;
	private long boHoanHoaKienNghiXuLyVeDat;
	private long saiPhamKhacKienNghiXuLyVeDat;
	
	// Kien nghi xu ly ve hanh chinh
	@Enumerated(EnumType.STRING)
	private HinhThucKienNghiEnum hinhThucKienNghiToChuc;
	@Enumerated(EnumType.STRING)
	private HinhThucKienNghiEnum hinhThucKienNghiCaNhan;
	// Kien nghi chuyen co quan dieu tra
	@Enumerated(EnumType.STRING)
	private CoQuanDieuTraThanhTraEnum coQuanDieuTraThanhTra;
	
	@ManyToOne // Kien nghi chuyen co quan dieu tra
	private CoQuanQuanLy coQuanDieuTra;
	@ManyToOne
	private CuocThanhTra cuocThanhTra;
	
	@OneToMany(mappedBy = "doiTuongViPham", fetch = FetchType.EAGER)
	@Fetch(value = FetchMode.SELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)	
	private List<TaiLieuVanThu> taiLieuVanThus = new ArrayList<TaiLieuVanThu>(); //

	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	public String getSoQuyetDinhChuyenCoQuanDieuTra() {
		return soQuyetDinhChuyenCoQuanDieuTra;
	}

	public void setSoQuyetDinhChuyenCoQuanDieuTra(String soQuyetDinhChuyenCoQuanDieuTra) {
		this.soQuyetDinhChuyenCoQuanDieuTra = soQuyetDinhChuyenCoQuanDieuTra;
	}

	public String getNguoiRaQuyetDinhChuyenCoQuanDieuTra() {
		return nguoiRaQuyetDinhChuyenCoQuanDieuTra;
	}

	public void setNguoiRaQuyetDinhChuyenCoQuanDieuTra(String nguoiRaQuyetDinhChuyenCoQuanDieuTra) {
		this.nguoiRaQuyetDinhChuyenCoQuanDieuTra = nguoiRaQuyetDinhChuyenCoQuanDieuTra;
	}

	public String getCoQuanDieuTraKhac() {
		return coQuanDieuTraKhac;
	}

	public void setCoQuanDieuTraKhac(String coQuanDieuTraKhac) {
		this.coQuanDieuTraKhac = coQuanDieuTraKhac;
	}

	public String getToChucCoSaiPham() {
		return toChucCoSaiPham;
	}

	public void setToChucCoSaiPham(String toChucCoSaiPham) {
		this.toChucCoSaiPham = toChucCoSaiPham;
	}

	public String getCaNhanCoSaiPham() {
		return caNhanCoSaiPham;
	}

	public void setCaNhanCoSaiPham(String caNhanCoSaiPham) {
		this.caNhanCoSaiPham = caNhanCoSaiPham;
	}

	public String getSaiPhamKhac() {
		return saiPhamKhac;
	}

	public void setSaiPhamKhac(String saiPhamKhac) {
		this.saiPhamKhac = saiPhamKhac;
	}

	public String getQuyetDinhGiaoDat() {
		return quyetDinhGiaoDat;
	}

	public void setQuyetDinhGiaoDat(String quyetDinhGiaoDat) {
		this.quyetDinhGiaoDat = quyetDinhGiaoDat;
	}

	public String getNoiDungKienNghiXuLyKhac() {
		return noiDungKienNghiXuLyKhac;
	}

	public void setNoiDungKienNghiXuLyKhac(String noiDungKienNghiXuLyKhac) {
		this.noiDungKienNghiXuLyKhac = noiDungKienNghiXuLyKhac;
	}

	public String getSoQuyetDinhKienNghiXuLyHanhChinhToChuc() {
		return soQuyetDinhKienNghiXuLyHanhChinhToChuc;
	}

	public void setSoQuyetDinhKienNghiXuLyHanhChinhToChuc(String soQuyetDinhKienNghiXuLyHanhChinhToChuc) {
		this.soQuyetDinhKienNghiXuLyHanhChinhToChuc = soQuyetDinhKienNghiXuLyHanhChinhToChuc;
	}

	public String getSoQuyetDinhKienNghiXuLyHanhChinhCaNhan() {
		return soQuyetDinhKienNghiXuLyHanhChinhCaNhan;
	}

	public void setSoQuyetDinhKienNghiXuLyHanhChinhCaNhan(String soQuyetDinhKienNghiXuLyHanhChinhCaNhan) {
		this.soQuyetDinhKienNghiXuLyHanhChinhCaNhan = soQuyetDinhKienNghiXuLyHanhChinhCaNhan;
	}

	public String getQuyetDinhGiaoDatThuHoiTrongQuaTrinhThanhTra() {
		return quyetDinhGiaoDatThuHoiTrongQuaTrinhThanhTra;
	}

	public void setQuyetDinhGiaoDatThuHoiTrongQuaTrinhThanhTra(String quyetDinhGiaoDatThuHoiTrongQuaTrinhThanhTra) {
		this.quyetDinhGiaoDatThuHoiTrongQuaTrinhThanhTra = quyetDinhGiaoDatThuHoiTrongQuaTrinhThanhTra;
	}

	public String getDonViViPham() {
		return donViViPham;
	}

	public void setDonViViPham(String donViViPham) {
		this.donViViPham = donViViPham;
	}

	public String getViPhamKhac() {
		return viPhamKhac;
	}

	public void setViPhamKhac(String viPhamKhac) {
		this.viPhamKhac = viPhamKhac;
	}

	public boolean isChuyenCoQuanDieuTra() {
		return chuyenCoQuanDieuTra;
	}

	public void setChuyenCoQuanDieuTra(boolean chuyenCoQuanDieuTra) {
		this.chuyenCoQuanDieuTra = chuyenCoQuanDieuTra;
	}

	public int getSoVuViecKienNghiChuyenCoQuanDieuTra() {
		return soVuViecKienNghiChuyenCoQuanDieuTra;
	}

	public void setSoVuViecKienNghiChuyenCoQuanDieuTra(int soVuViecKienNghiChuyenCoQuanDieuTra) {
		this.soVuViecKienNghiChuyenCoQuanDieuTra = soVuViecKienNghiChuyenCoQuanDieuTra;
	}

	public int getSoVuChuyenCoQuanDieuTra() {
		return soVuChuyenCoQuanDieuTra;
	}

	public void setSoVuChuyenCoQuanDieuTra(int soVuChuyenCoQuanDieuTra) {
		this.soVuChuyenCoQuanDieuTra = soVuChuyenCoQuanDieuTra;
	}

	public long getTienDaThuTrongQuaTrinhThanhTra() {
		return tienDaThuTrongQuaTrinhThanhTra;
	}

	public void setTienDaThuTrongQuaTrinhThanhTra(long tienDaThuTrongQuaTrinhThanhTra) {
		this.tienDaThuTrongQuaTrinhThanhTra = tienDaThuTrongQuaTrinhThanhTra;
	}

	public long getDatDaThuTrongQuaTrinhThanhTra() {
		return datDaThuTrongQuaTrinhThanhTra;
	}

	public void setDatDaThuTrongQuaTrinhThanhTra(long datDaThuTrongQuaTrinhThanhTra) {
		this.datDaThuTrongQuaTrinhThanhTra = datDaThuTrongQuaTrinhThanhTra;
	}

	public long getTongSoTienThuHoiTrongQuaTrinhThanhTra() {
		return tongSoTienThuHoiTrongQuaTrinhThanhTra;
	}

	public void setTongSoTienThuHoiTrongQuaTrinhThanhTra(long tongSoTienThuHoiTrongQuaTrinhThanhTra) {
		this.tongSoTienThuHoiTrongQuaTrinhThanhTra = tongSoTienThuHoiTrongQuaTrinhThanhTra;
	}

	public long getCaNhanTienThuHoiTrongQuaTrinhThanhTra() {
		return caNhanTienThuHoiTrongQuaTrinhThanhTra;
	}

	public void setCaNhanTienThuHoiTrongQuaTrinhThanhTra(long caNhanTienThuHoiTrongQuaTrinhThanhTra) {
		this.caNhanTienThuHoiTrongQuaTrinhThanhTra = caNhanTienThuHoiTrongQuaTrinhThanhTra;
	}

	public long getToChucTienThuHoiTrongQuaTrinhThanhTra() {
		return toChucTienThuHoiTrongQuaTrinhThanhTra;
	}

	public void setToChucTienThuHoiTrongQuaTrinhThanhTra(long toChucTienThuHoiTrongQuaTrinhThanhTra) {
		this.toChucTienThuHoiTrongQuaTrinhThanhTra = toChucTienThuHoiTrongQuaTrinhThanhTra;
	}

	public long getSaiPhamVeTienKienNghiThuHoiKienNghiXuLyVeKinhTe() {
		return saiPhamVeTienKienNghiThuHoiKienNghiXuLyVeKinhTe;
	}

	public void setSaiPhamVeTienKienNghiThuHoiKienNghiXuLyVeKinhTe(long saiPhamVeTienKienNghiThuHoiKienNghiXuLyVeKinhTe) {
		this.saiPhamVeTienKienNghiThuHoiKienNghiXuLyVeKinhTe = saiPhamVeTienKienNghiThuHoiKienNghiXuLyVeKinhTe;
	}

	public long getSaiPhamVeDatKienNghiThuHoiKienNghiXuLyVeKinhTe() {
		return saiPhamVeDatKienNghiThuHoiKienNghiXuLyVeKinhTe;
	}

	public void setSaiPhamVeDatKienNghiThuHoiKienNghiXuLyVeKinhTe(long saiPhamVeDatKienNghiThuHoiKienNghiXuLyVeKinhTe) {
		this.saiPhamVeDatKienNghiThuHoiKienNghiXuLyVeKinhTe = saiPhamVeDatKienNghiThuHoiKienNghiXuLyVeKinhTe;
	}

	public long getSaiPhamVeTienKienNghiKhacXuLyVeKinhTe() {
		return saiPhamVeTienKienNghiKhacXuLyVeKinhTe;
	}

	public void setSaiPhamVeTienKienNghiKhacXuLyVeKinhTe(long saiPhamVeTienKienNghiKhacXuLyVeKinhTe) {
		this.saiPhamVeTienKienNghiKhacXuLyVeKinhTe = saiPhamVeTienKienNghiKhacXuLyVeKinhTe;
	}

	public long getSaiPhamVeDatKienNghiKhacXuLyVeKinhTe() {
		return saiPhamVeDatKienNghiKhacXuLyVeKinhTe;
	}

	public void setSaiPhamVeDatKienNghiKhacXuLyVeKinhTe(long saiPhamVeDatKienNghiKhacXuLyVeKinhTe) {
		this.saiPhamVeDatKienNghiKhacXuLyVeKinhTe = saiPhamVeDatKienNghiKhacXuLyVeKinhTe;
	}

	public long getTongSoTienViPhamKienNghiXuLyVeKinhTe() {
		return tongSoTienViPhamKienNghiXuLyVeKinhTe;
	}

	public void setTongSoTienViPhamKienNghiXuLyVeKinhTe(long tongSoTienViPhamKienNghiXuLyVeKinhTe) {
		this.tongSoTienViPhamKienNghiXuLyVeKinhTe = tongSoTienViPhamKienNghiXuLyVeKinhTe;
	}

	public long getTienCaNhanViPhamKienNghiXuLyVeKinhTe() {
		return tienCaNhanViPhamKienNghiXuLyVeKinhTe;
	}

	public void setTienCaNhanViPhamKienNghiXuLyVeKinhTe(long tienCaNhanViPhamKienNghiXuLyVeKinhTe) {
		this.tienCaNhanViPhamKienNghiXuLyVeKinhTe = tienCaNhanViPhamKienNghiXuLyVeKinhTe;
	}

	public long getTienToChucViPhamKienNghiXuLyVeKinhTe() {
		return tienToChucViPhamKienNghiXuLyVeKinhTe;
	}

	public void setTienToChucViPhamKienNghiXuLyVeKinhTe(long tienToChucViPhamKienNghiXuLyVeKinhTe) {
		this.tienToChucViPhamKienNghiXuLyVeKinhTe = tienToChucViPhamKienNghiXuLyVeKinhTe;
	}

	public long getSoTienKienNghiThuHoiKienNghiXuLyVeKinhTe() {
		return soTienKienNghiThuHoiKienNghiXuLyVeKinhTe;
	}

	public void setSoTienKienNghiThuHoiKienNghiXuLyVeKinhTe(long soTienKienNghiThuHoiKienNghiXuLyVeKinhTe) {
		this.soTienKienNghiThuHoiKienNghiXuLyVeKinhTe = soTienKienNghiThuHoiKienNghiXuLyVeKinhTe;
	}

	public long getTongSoTienXuLyTaiSanViPhamKienNghiXuLyVeKinhTe() {
		return tongSoTienXuLyTaiSanViPhamKienNghiXuLyVeKinhTe;
	}

	public void setTongSoTienXuLyTaiSanViPhamKienNghiXuLyVeKinhTe(long tongSoTienXuLyTaiSanViPhamKienNghiXuLyVeKinhTe) {
		this.tongSoTienXuLyTaiSanViPhamKienNghiXuLyVeKinhTe = tongSoTienXuLyTaiSanViPhamKienNghiXuLyVeKinhTe;
	}

	public long getTienTichThuXuLyTaiSanViPhamKienNghiXuLyVeKinhTe() {
		return tienTichThuXuLyTaiSanViPhamKienNghiXuLyVeKinhTe;
	}

	public void setTienTichThuXuLyTaiSanViPhamKienNghiXuLyVeKinhTe(long tienTichThuXuLyTaiSanViPhamKienNghiXuLyVeKinhTe) {
		this.tienTichThuXuLyTaiSanViPhamKienNghiXuLyVeKinhTe = tienTichThuXuLyTaiSanViPhamKienNghiXuLyVeKinhTe;
	}

	public long getTienTieuHuyXuLyTaiSanViPhamKienNghiXuLyVeKinhTe() {
		return tienTieuHuyXuLyTaiSanViPhamKienNghiXuLyVeKinhTe;
	}

	public void setTienTieuHuyXuLyTaiSanViPhamKienNghiXuLyVeKinhTe(long tienTieuHuyXuLyTaiSanViPhamKienNghiXuLyVeKinhTe) {
		this.tienTieuHuyXuLyTaiSanViPhamKienNghiXuLyVeKinhTe = tienTieuHuyXuLyTaiSanViPhamKienNghiXuLyVeKinhTe;
	}

	public long getTongSoTienXuPhatViPhamKienNghiXuLyVeKinhTe() {
		return tongSoTienXuPhatViPhamKienNghiXuLyVeKinhTe;
	}

	public void setTongSoTienXuPhatViPhamKienNghiXuLyVeKinhTe(long tongSoTienXuPhatViPhamKienNghiXuLyVeKinhTe) {
		this.tongSoTienXuPhatViPhamKienNghiXuLyVeKinhTe = tongSoTienXuPhatViPhamKienNghiXuLyVeKinhTe;
	}

	public long getTienCaNhanXuPhatViPhamKienNghiXuLyVeKinhTe() {
		return tienCaNhanXuPhatViPhamKienNghiXuLyVeKinhTe;
	}

	public void setTienCaNhanXuPhatViPhamKienNghiXuLyVeKinhTe(long tienCaNhanXuPhatViPhamKienNghiXuLyVeKinhTe) {
		this.tienCaNhanXuPhatViPhamKienNghiXuLyVeKinhTe = tienCaNhanXuPhatViPhamKienNghiXuLyVeKinhTe;
	}

	public long getTienToChucXuPhatViPhamKienNghiXuLyVeKinhTe() {
		return tienToChucXuPhatViPhamKienNghiXuLyVeKinhTe;
	}

	public void setTienToChucXuPhatViPhamKienNghiXuLyVeKinhTe(long tienToChucXuPhatViPhamKienNghiXuLyVeKinhTe) {
		this.tienToChucXuPhatViPhamKienNghiXuLyVeKinhTe = tienToChucXuPhatViPhamKienNghiXuLyVeKinhTe;
	}

	public long getTienKienNghiXuLyKhac() {
		return tienKienNghiXuLyKhac;
	}

	public void setTienKienNghiXuLyKhac(long tienKienNghiXuLyKhac) {
		this.tienKienNghiXuLyKhac = tienKienNghiXuLyKhac;
	}

	public long getDatKienNghiXuLyKhac() {
		return datKienNghiXuLyKhac;
	}

	public void setDatKienNghiXuLyKhac(long datKienNghiXuLyKhac) {
		this.datKienNghiXuLyKhac = datKienNghiXuLyKhac;
	}

	public long getDatLanChiemKienNghiXuLyVeDat() {
		return datLanChiemKienNghiXuLyVeDat;
	}

	public void setDatLanChiemKienNghiXuLyVeDat(long datLanChiemKienNghiXuLyVeDat) {
		this.datLanChiemKienNghiXuLyVeDat = datLanChiemKienNghiXuLyVeDat;
	}

	public long getGiaoCapDatSaiDoiTuongQDKienNghiXuLyVeDat() {
		return giaoCapDatSaiDoiTuongQDKienNghiXuLyVeDat;
	}

	public void setGiaoCapDatSaiDoiTuongQDKienNghiXuLyVeDat(long giaoCapDatSaiDoiTuongQDKienNghiXuLyVeDat) {
		this.giaoCapDatSaiDoiTuongQDKienNghiXuLyVeDat = giaoCapDatSaiDoiTuongQDKienNghiXuLyVeDat;
	}

	public long getCapBanDatTraiThamQuyenKienNghiXuLyVeDat() {
		return capBanDatTraiThamQuyenKienNghiXuLyVeDat;
	}

	public void setCapBanDatTraiThamQuyenKienNghiXuLyVeDat(long capBanDatTraiThamQuyenKienNghiXuLyVeDat) {
		this.capBanDatTraiThamQuyenKienNghiXuLyVeDat = capBanDatTraiThamQuyenKienNghiXuLyVeDat;
	}

	public long getCapGCNQSDDatSaiKienNghiXuLyVeDat() {
		return capGCNQSDDatSaiKienNghiXuLyVeDat;
	}

	public void setCapGCNQSDDatSaiKienNghiXuLyVeDat(long capGCNQSDDatSaiKienNghiXuLyVeDat) {
		this.capGCNQSDDatSaiKienNghiXuLyVeDat = capGCNQSDDatSaiKienNghiXuLyVeDat;
	}

	public long getcNChoThueKhongDungQDKienNghiXuLyVeDat() {
		return cNChoThueKhongDungQDKienNghiXuLyVeDat;
	}

	public void setcNChoThueKhongDungQDKienNghiXuLyVeDat(long cNChoThueKhongDungQDKienNghiXuLyVeDat) {
		this.cNChoThueKhongDungQDKienNghiXuLyVeDat = cNChoThueKhongDungQDKienNghiXuLyVeDat;
	}

	public long getsDDatKhongDungMDKienNghiXuLyVeDat() {
		return sDDatKhongDungMDKienNghiXuLyVeDat;
	}

	public void setsDDatKhongDungMDKienNghiXuLyVeDat(long sDDatKhongDungMDKienNghiXuLyVeDat) {
		this.sDDatKhongDungMDKienNghiXuLyVeDat = sDDatKhongDungMDKienNghiXuLyVeDat;
	}

	public long getBoHoanHoaKienNghiXuLyVeDat() {
		return boHoanHoaKienNghiXuLyVeDat;
	}

	public void setBoHoanHoaKienNghiXuLyVeDat(long boHoanHoaKienNghiXuLyVeDat) {
		this.boHoanHoaKienNghiXuLyVeDat = boHoanHoaKienNghiXuLyVeDat;
	}

	public long getSaiPhamKhacKienNghiXuLyVeDat() {
		return saiPhamKhacKienNghiXuLyVeDat;
	}

	public void setSaiPhamKhacKienNghiXuLyVeDat(long saiPhamKhacKienNghiXuLyVeDat) {
		this.saiPhamKhacKienNghiXuLyVeDat = saiPhamKhacKienNghiXuLyVeDat;
	}

	public HinhThucKienNghiEnum getHinhThucKienNghiToChuc() {
		return hinhThucKienNghiToChuc;
	}

	public void setHinhThucKienNghiToChuc(HinhThucKienNghiEnum hinhThucKienNghiToChuc) {
		this.hinhThucKienNghiToChuc = hinhThucKienNghiToChuc;
	}

	public HinhThucKienNghiEnum getHinhThucKienNghiCaNhan() {
		return hinhThucKienNghiCaNhan;
	}

	public void setHinhThucKienNghiCaNhan(HinhThucKienNghiEnum hinhThucKienNghiCaNhan) {
		this.hinhThucKienNghiCaNhan = hinhThucKienNghiCaNhan;
	}

	public CoQuanDieuTraThanhTraEnum getCoQuanDieuTraThanhTra() {
		return coQuanDieuTraThanhTra;
	}

	public void setCoQuanDieuTraThanhTra(CoQuanDieuTraThanhTraEnum coQuanDieuTraThanhTra) {
		this.coQuanDieuTraThanhTra = coQuanDieuTraThanhTra;
	}

	@ApiModelProperty(example = "{}")
	public CoQuanQuanLy getCoQuanDieuTra() {
		return coQuanDieuTra;
	}

	public void setCoQuanDieuTra(CoQuanQuanLy coQuanDieuTra) {
		this.coQuanDieuTra = coQuanDieuTra;
	}

	@ApiModelProperty(example = "{}")
	public CuocThanhTra getCuocThanhTra() {
		return cuocThanhTra;
	}

	public void setCuocThanhTra(CuocThanhTra cuocThanhTra) {
		this.cuocThanhTra = cuocThanhTra;
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
	public Long getDoiTuongViPhamId() {
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
	public List<TaiLieuVanThu> getListTaiLieuVanThuChuyenCoQuanDieuTra() {
		List<TaiLieuVanThu> list = new ArrayList<TaiLieuVanThu>();
		for (TaiLieuVanThu tlvt : getTaiLieuVanThus()) {
			if (!tlvt.isDaXoa() && ProcessTypeEnum.THANH_TRA.equals(tlvt.getLoaiQuyTrinh())
					&& BuocGiaiQuyetEnum.CHUYEN_CO_QUAN_DIEU_TRA.equals(tlvt.getBuocGiaiQuyet())) {
				list.add(tlvt);
			}
		}
		return list;
	}
	
	@Transient
	@ApiModelProperty(hidden = true)
	public List<TaiLieuVanThu> getListTaiLieuVanThuKienNghiXuLyKhac() {
		List<TaiLieuVanThu> list = new ArrayList<TaiLieuVanThu>();
		for (TaiLieuVanThu tlvt : getTaiLieuVanThus()) {
			if (!tlvt.isDaXoa() && ProcessTypeEnum.THANH_TRA.equals(tlvt.getLoaiQuyTrinh())
					&& BuocGiaiQuyetEnum.KIEN_NGHI_XU_LY_KHAC.equals(tlvt.getBuocGiaiQuyet())) {
				list.add(tlvt);
			}
		}
		return list;
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
	public Long getCuocThanhTraId() {
		if (getCuocThanhTra() != null) {
			return getCuocThanhTra().getId();
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty( hidden = true )
	public Map<String, Object> getHinhThucKienNghiToChucInfo() {
		if (getHinhThucKienNghiToChuc() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("type", getHinhThucKienNghiToChuc().name());
			map.put("text", getHinhThucKienNghiToChuc().getText());
			return map;
		}
		return null;
	}
	
	@Transient
	@ApiModelProperty( hidden = true )
	public Map<String, Object> getHinhThucKienNghiCaNhanInfo() {
		if (getHinhThucKienNghiCaNhan() != null) {
			Map<String, Object> map = new HashMap<>();
			map.put("type", getHinhThucKienNghiCaNhan().name());
			map.put("text", getHinhThucKienNghiCaNhan().getText());
			return map;
		}
		return null;
	}
}