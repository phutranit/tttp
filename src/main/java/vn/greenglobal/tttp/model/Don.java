package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.katharsis.resource.annotations.JsonApiResource;
import vn.greenglobal.tttp.enums.*;

@Entity
@Table(name = "don")
@JsonApiResource(type = "dons")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Don extends Model<Don> {

	private String ma = "";
	private String noiDUng = "";
	private String yeuCauCuaCongDan = "";
	private String diaDiemGapLanhDao = "";
	private String lyDoTuChoi = "";
	private String huongXuLy = "";

	private int soLanKhieuNaiToCao = 0;
	private int soNguoi;

	private boolean coUyQuyen = false;
	private boolean thanhLapDon = false;
	private boolean tuChoiTiepCongDan = false;
	private boolean yeuCauGapTrucTiepLanhDao = false;

	private LocalDateTime ngayTiepNhan;
	private LocalDateTime ngayHenGapLanhDao;

	@OneToOne
	private Don donLanTruoc;
	@ManyToOne
	private CongChuc canBoXuLy;
	@ManyToOne
	private CoQuanQuanLy donVi;
	@ManyToOne
	private LinhVuc linhVuc;
	@ManyToOne
	private LinhVuc linhVucChiTiet;
	@ManyToOne
	private LinhVuc chiTietLinhVucChiTiet;

	@ManyToOne
	private ThamQuyenGiaiQuyet thamQuyenGiaiQuyet;
	@ManyToOne
	private CoQuanQuanLy phongBanGiaiQuyet;

	private LoaiDonEnum loaiDon;
	private LoaiDoiTuongEnum loaiDoiTuong;
	private NguonTiepNhanDonEnum nguonTiepNhanDon;

	public String getMa() {
		return ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	public String getNoiDUng() {
		return noiDUng;
	}

	public void setNoiDUng(String noiDUng) {
		this.noiDUng = noiDUng;
	}

	public String getYeuCauCuaCongDan() {
		return yeuCauCuaCongDan;
	}

	public void setYeuCauCuaCongDan(String yeuCauCuaCongDan) {
		this.yeuCauCuaCongDan = yeuCauCuaCongDan;
	}

	public String getDiaDiemGapLanhDao() {
		return diaDiemGapLanhDao;
	}

	public void setDiaDiemGapLanhDao(String diaDiemGapLanhDao) {
		this.diaDiemGapLanhDao = diaDiemGapLanhDao;
	}

	public String getLyDoTuChoi() {
		return lyDoTuChoi;
	}

	public void setLyDoTuChoi(String lyDoTuChoi) {
		this.lyDoTuChoi = lyDoTuChoi;
	}

	public String getHuongXuLy() {
		return huongXuLy;
	}

	public void setHuongXuLy(String huongXuLy) {
		this.huongXuLy = huongXuLy;
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

	public boolean isThanhLapDon() {
		return thanhLapDon;
	}

	public void setThanhLapDon(boolean thanhLapDon) {
		this.thanhLapDon = thanhLapDon;
	}

	public boolean isTuChoiTiepCongDan() {
		return tuChoiTiepCongDan;
	}

	public void setTuChoiTiepCongDan(boolean tuChoiTiepCongDan) {
		this.tuChoiTiepCongDan = tuChoiTiepCongDan;
	}

	public boolean isYeuCauGapTrucTiepLanhDao() {
		return yeuCauGapTrucTiepLanhDao;
	}

	public void setYeuCauGapTrucTiepLanhDao(boolean yeuCauGapTrucTiepLanhDao) {
		this.yeuCauGapTrucTiepLanhDao = yeuCauGapTrucTiepLanhDao;
	}

	public LocalDateTime getNgayTiepNhan() {
		return ngayTiepNhan;
	}

	public void setNgayTiepNhan(LocalDateTime ngayTiepNhan) {
		this.ngayTiepNhan = ngayTiepNhan;
	}

	public LocalDateTime getNgayHenGapLanhDao() {
		return ngayHenGapLanhDao;
	}

	public void setNgayHenGapLanhDao(LocalDateTime ngayHenGapLanhDao) {
		this.ngayHenGapLanhDao = ngayHenGapLanhDao;
	}

	public Don getDonLanTruoc() {
		return donLanTruoc;
	}

	public void setDonLanTruoc(Don donLanTruoc) {
		this.donLanTruoc = donLanTruoc;
	}

	public CongChuc getCanBoXuLy() {
		return canBoXuLy;
	}

	public void setCanBoXuLy(CongChuc canBoXuLy) {
		this.canBoXuLy = canBoXuLy;
	}

	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}

	public LinhVuc getLinhVuc() {
		return linhVuc;
	}

	public void setLinhVuc(LinhVuc linhVuc) {
		this.linhVuc = linhVuc;
	}

	public LinhVuc getLinhVucChiTiet() {
		return linhVucChiTiet;
	}

	public void setLinhVucChiTiet(LinhVuc linhVucChiTiet) {
		this.linhVucChiTiet = linhVucChiTiet;
	}

	public LinhVuc getChiTietLinhVucChiTiet() {
		return chiTietLinhVucChiTiet;
	}

	public void setChiTietLinhVucChiTiet(LinhVuc chiTietLinhVucChiTiet) {
		this.chiTietLinhVucChiTiet = chiTietLinhVucChiTiet;
	}

	public ThamQuyenGiaiQuyet getThamQuyenGiaiQuyet() {
		return thamQuyenGiaiQuyet;
	}

	public void setThamQuyenGiaiQuyet(ThamQuyenGiaiQuyet thamQuyenGiaiQuyet) {
		this.thamQuyenGiaiQuyet = thamQuyenGiaiQuyet;
	}

	public CoQuanQuanLy getPhongBanGiaiQuyet() {
		return phongBanGiaiQuyet;
	}

	public void setPhongBanGiaiQuyet(CoQuanQuanLy phongBanGiaiQuyet) {
		this.phongBanGiaiQuyet = phongBanGiaiQuyet;
	}

	public LoaiDonEnum getLoaiDon() {
		return loaiDon;
	}

	public void setLoaiDon(LoaiDonEnum loaiDon) {
		this.loaiDon = loaiDon;
	}

	public LoaiDoiTuongEnum getLoaiDoiTuong() {
		return loaiDoiTuong;
	}

	public void setLoaiDoiTuong(LoaiDoiTuongEnum loaiDoiTuong) {
		this.loaiDoiTuong = loaiDoiTuong;
	}

	public NguonTiepNhanDonEnum getNguonTiepNhanDon() {
		return nguonTiepNhanDon;
	}

	public void setNguonTiepNhanDon(NguonTiepNhanDonEnum nguonTiepNhanDon) {
		this.nguonTiepNhanDon = nguonTiepNhanDon;
	}
}