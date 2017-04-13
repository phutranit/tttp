package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Table(name = "sotiepcongdan")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class SoTiepCongDan extends Model<SoTiepCongDan> {

	@ManyToOne
	private Don don;
	@ManyToOne
	private CongChuc canBoTiepDan;
	@ManyToOne
	private CoQuanQuanLy donVi;

	private LocalDateTime ngayTiepDan;
	private LocalDateTime ngayTiepNhan;
	private LocalDateTime thoiHan;

	private String loaiTiepDan = "";
	private String noiDungTiepCongDan = "";
	private String ketQuaGiaiQuyet = "";
	private String donViChuTri = "";
	private String donViPhoiHop = "";
	private String trangThaiKetQua = "";
	private String diaDiemTiepDan = "";
	private String noiDungBoSung = "";

	private boolean giaiQuyetNgay = false;
	private boolean choGiaiQuyet = false;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "coquantochuctiepdan_has_sotiepcongdan", joinColumns = {
			@JoinColumn(name = "sotiepcongdan_id") }, inverseJoinColumns = {
					@JoinColumn(name = "coquantochuctiepdan_id") })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<CoQuanToChucTiepDan> coQuanToChucTiepDans;
	
	public List<CoQuanToChucTiepDan> getCoQuanToChucTiepDans() {
		return coQuanToChucTiepDans;
	}

	public void setCoQuanToChucTiepDans(List<CoQuanToChucTiepDan> coQuanToChucTiepDans) {
		this.coQuanToChucTiepDans = coQuanToChucTiepDans;
	}

	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	public CongChuc getCanBoTiepDan() {
		return canBoTiepDan;
	}

	public void setCanBoTiepDan(CongChuc canBoTiepDan) {
		this.canBoTiepDan = canBoTiepDan;
	}

	public CoQuanQuanLy getDonVi() {
		return donVi;
	}

	public void setDonVi(CoQuanQuanLy donVi) {
		this.donVi = donVi;
	}

	public LocalDateTime getNgayTiepDan() {
		return ngayTiepDan;
	}

	public void setNgayTiepDan(LocalDateTime ngayTiepDan) {
		this.ngayTiepDan = ngayTiepDan;
	}

	public LocalDateTime getNgayTiepNhan() {
		return ngayTiepNhan;
	}

	public void setNgayTiepNhan(LocalDateTime ngayTiepNhan) {
		this.ngayTiepNhan = ngayTiepNhan;
	}

	public LocalDateTime getThoiHan() {
		return thoiHan;
	}

	public void setThoiHan(LocalDateTime thoiHan) {
		this.thoiHan = thoiHan;
	}

	public String getLoaiTiepDan() {
		return loaiTiepDan;
	}

	public void setLoaiTiepDan(String loaiTiepDan) {
		this.loaiTiepDan = loaiTiepDan;
	}

	public String getNoiDungTiepCongDan() {
		return noiDungTiepCongDan;
	}

	public void setNoiDungTiepCongDan(String noiDungTiepCongDan) {
		this.noiDungTiepCongDan = noiDungTiepCongDan;
	}

	public String getKetQuaGiaiQuyet() {
		return ketQuaGiaiQuyet;
	}

	public void setKetQuaGiaiQuyet(String ketQuaGiaiQuyet) {
		this.ketQuaGiaiQuyet = ketQuaGiaiQuyet;
	}

	public String getDonViChuTri() {
		return donViChuTri;
	}

	public void setDonViChuTri(String donViChuTri) {
		this.donViChuTri = donViChuTri;
	}

	public String getDonViPhoiHop() {
		return donViPhoiHop;
	}

	public void setDonViPhoiHop(String donViPhoiHop) {
		this.donViPhoiHop = donViPhoiHop;
	}

	public String getTrangThaiKetQua() {
		return trangThaiKetQua;
	}

	public void setTrangThaiKetQua(String trangThaiKetQua) {
		this.trangThaiKetQua = trangThaiKetQua;
	}

	public String getDiaDiemTiepDan() {
		return diaDiemTiepDan;
	}

	public void setDiaDiemTiepDan(String diaDiemTiepDan) {
		this.diaDiemTiepDan = diaDiemTiepDan;
	}

	public String getNoiDungBoSung() {
		return noiDungBoSung;
	}

	public void setNoiDungBoSung(String noiDungBoSung) {
		this.noiDungBoSung = noiDungBoSung;
	}

	public boolean isGiaiQuyetNgay() {
		return giaiQuyetNgay;
	}

	public void setGiaiQuyetNgay(boolean giaiQuyetNgay) {
		this.giaiQuyetNgay = giaiQuyetNgay;
	}

	public boolean isChoGiaiQuyet() {
		return choGiaiQuyet;
	}

	public void setChoGiaiQuyet(boolean choGiaiQuyet) {
		this.choGiaiQuyet = choGiaiQuyet;
	}

	/*@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "sotiepcongdan_coquantochuctiepdan", joinColumns = {
			@JoinColumn(name = "soTiepCongDan_id") }, inverseJoinColumns = {
					@JoinColumn(name = "coQuanToChucTiepDan_id") })
	@Fetch(value = FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	@JsonApiToMany
	@JsonApiIncludeByDefault
	private List<CoQuanToChucTiepDan> coQuanToChucTiepDans = new ArrayList<>();

	public List<CoQuanToChucTiepDan> getCoQuanToChucTiepDans() {
		return coQuanToChucTiepDans;
	}

	public void setCoQuanToChucTiepDans(List<CoQuanToChucTiepDan> coQuanToChucTiepDans) {
		this.coQuanToChucTiepDans = coQuanToChucTiepDans;
	}*/
}