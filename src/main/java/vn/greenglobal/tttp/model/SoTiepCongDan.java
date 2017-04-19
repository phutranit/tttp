package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.*;

@Entity
@Table(name = "sotiepcongdan")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
@ApiModel
public class SoTiepCongDan extends Model<SoTiepCongDan> {

	private static final long serialVersionUID = -6772485280557984436L;

	@NotNull
	@ManyToOne
	private Don don;
	@NotNull
	@ManyToOne
	private CongChuc canBoTiepDan;
	@NotNull
	@ManyToOne
	private CoQuanQuanLy donVi;

	@NotNull
	private LocalDateTime ngayTiepDan;
	private LocalDateTime ngayTiepNhan;
	private LocalDateTime thoiHan;
	private LocalDateTime ngayHenGapLanhDao;

	@Enumerated(EnumType.STRING)
	private LoaiTiepDanEnum loaiTiepDan;
	private String noiDungTiepCongDan = "";
	private String ketQuaGiaiQuyet = "";
	private String donViChuTri = "";
	private String donViPhoiHop = "";
	private String trangThaiKetQua = "";
	private String diaDiemTiepDan = "";
	private String noiDungBoSung = "";
	private String diaDiemGapLanhDao = "";
	@Transient
	private String huongXuLyText = "";

	private boolean giaiQuyetNgay = false;
	private boolean choGiaiQuyet = false;
	private boolean yeuCauGapTrucTiepLanhDao = false;

	private int soThuTuLuotTiep = 0;

	@Enumerated(EnumType.STRING)
	private HuongGiaiQuyetTCDEnum huongGiaiQuyet;
	@Enumerated(EnumType.STRING)
	private HuongXuLyTCDEnum huongXuLy;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "coquantochuctiepdan_has_sotiepcongdan", joinColumns = {
			@JoinColumn(name = "soTiepCongDan_id") }, inverseJoinColumns = {
					@JoinColumn(name = "coQuanToChucTiepDan_id") })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<CoQuanToChucTiepDan> coQuanToChucTiepDans;

	public List<CoQuanToChucTiepDan> getCoQuanToChucTiepDans() {
		return coQuanToChucTiepDans;
	}

	public void setCoQuanToChucTiepDans(List<CoQuanToChucTiepDan> coQuanToChucTiepDans) {
		this.coQuanToChucTiepDans = coQuanToChucTiepDans;
	}

	@ApiModelProperty(example = "{}")
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	@ApiModelProperty(example = "{}")
	public CongChuc getCanBoTiepDan() {
		return canBoTiepDan;
	}

	public void setCanBoTiepDan(CongChuc canBoTiepDan) {
		this.canBoTiepDan = canBoTiepDan;
	}

	@ApiModelProperty(example = "{}")
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

	public LoaiTiepDanEnum getLoaiTiepDan() {
		return loaiTiepDan;
	}

	public void setLoaiTiepDan(LoaiTiepDanEnum loaiTiepDan) {
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

	public String getDiaDiemGapLanhDao() {
		return diaDiemGapLanhDao;
	}

	public void setDiaDiemGapLanhDao(String diaDiemGapLanhDao) {
		this.diaDiemGapLanhDao = diaDiemGapLanhDao;
	}

	public boolean isYeuCauGapTrucTiepLanhDao() {
		return yeuCauGapTrucTiepLanhDao;
	}

	public void setYeuCauGapTrucTiepLanhDao(boolean yeuCauGapTrucTiepLanhDao) {
		this.yeuCauGapTrucTiepLanhDao = yeuCauGapTrucTiepLanhDao;
	}

	public LocalDateTime getNgayHenGapLanhDao() {
		return ngayHenGapLanhDao;
	}

	public void setNgayHenGapLanhDao(LocalDateTime ngayHenGapLanhDao) {
		this.ngayHenGapLanhDao = ngayHenGapLanhDao;
	}

	public HuongGiaiQuyetTCDEnum getHuongGiaiQuyet() {
		return huongGiaiQuyet;
	}

	public void setHuongGiaiQuyet(HuongGiaiQuyetTCDEnum huongGiaiQuyet) {
		this.huongGiaiQuyet = huongGiaiQuyet;
	}

	public HuongXuLyTCDEnum getHuongXuLy() {
		return huongXuLy;
	}

	public void setHuongXuLy(HuongXuLyTCDEnum huongXuLy) {
		this.huongXuLy = huongXuLy;
	}

	public String getHuongXuLyText() {
		return huongXuLyText;
	}

	public void setHuongXuLyText(String huongXuLyText) {
		if (huongXuLy != null) {
			huongXuLyText = huongXuLy.getText();
		}
		this.huongXuLyText = huongXuLyText;
	}

	public int getSoThuTuLuotTiep() {
		return soThuTuLuotTiep;
	}

	public void setSoThuTuLuotTiep(int soThuTuLuotTiep) {
		this.soThuTuLuotTiep = soThuTuLuotTiep;
	}
	
	@Transient
	public List<CoQuanToChucTiepDan> getcoQuanToChucTiepDanSTCD() {
		return coQuanToChucTiepDans;
	}
	
}