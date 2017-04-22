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
import vn.greenglobal.tttp.enums.HuongXuLyTCDEnum;
import vn.greenglobal.tttp.enums.LoaiTiepDanEnum;

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
	private CoQuanQuanLy donViTiepDan;

	@NotNull
	private LocalDateTime ngayTiepDan;
	private LocalDateTime thoiHan;
	private LocalDateTime ngayHenGapLanhDao;
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

	private int soThuTuLuotTiep = 0;

	@NotNull
	@Enumerated(EnumType.STRING)
	private HuongXuLyTCDEnum huongXuLy;
	@NotNull
	@Enumerated(EnumType.STRING)
	private LoaiTiepDanEnum loaiTiepDan;

	@ManyToOne
	private CoQuanQuanLy phongBanGiaiQuyet;
	private String yKienXuLy = "";
	private String ghiChuXuLy = "";

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "coquantochuctiepdan_has_sotiepcongdan", joinColumns = {
			@JoinColumn(name = "soTiepCongDan_id") }, inverseJoinColumns = {
					@JoinColumn(name = "coQuanToChucTiepDan_id") })
	@Fetch(value = FetchMode.SUBSELECT)
	private List<CoQuanToChucTiepDan> coQuanToChucTiepDans = new ArrayList<CoQuanToChucTiepDan>();

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
	public CoQuanQuanLy getDonViTiepDan() {
		return donViTiepDan;
	}

	public void setDonViTiepDan(CoQuanQuanLy donViTiepDan) {
		this.donViTiepDan = donViTiepDan;
	}

	public LocalDateTime getNgayTiepDan() {
		return ngayTiepDan;
	}

	public void setNgayTiepDan(LocalDateTime ngayTiepDan) {
		this.ngayTiepDan = ngayTiepDan;
	}

	public LocalDateTime getThoiHan() {
		return thoiHan;
	}

	public void setThoiHan(LocalDateTime thoiHan) {
		this.thoiHan = thoiHan;
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

	public String getDiaDiemGapLanhDao() {
		return diaDiemGapLanhDao;
	}

	public void setDiaDiemGapLanhDao(String diaDiemGapLanhDao) {
		this.diaDiemGapLanhDao = diaDiemGapLanhDao;
	}

	public LocalDateTime getNgayHenGapLanhDao() {
		return ngayHenGapLanhDao;
	}

	public void setNgayHenGapLanhDao(LocalDateTime ngayHenGapLanhDao) {
		this.ngayHenGapLanhDao = ngayHenGapLanhDao;
	}

	public HuongXuLyTCDEnum getHuongXuLy() {
		return huongXuLy;
	}

	public void setHuongXuLy(HuongXuLyTCDEnum huongXuLy) {
		this.huongXuLy = huongXuLy;
	}

	public LoaiTiepDanEnum getLoaiTiepDan() {
		return loaiTiepDan;
	}

	public void setLoaiTiepDan(LoaiTiepDanEnum loaiTiepDan) {
		this.loaiTiepDan = loaiTiepDan;
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
	@ApiModelProperty(hidden = true)
	public List<CoQuanToChucTiepDan> getCoQuanToChucTiepDanSTCD() {
		return coQuanToChucTiepDans;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Don getDonSTCD() {
		return getDon();
	}

	@Transient
	public CoQuanQuanLy getPhongBanGiaiQuyet() {
		return phongBanGiaiQuyet;
	}

	public void setPhongBanGiaiQuyet(CoQuanQuanLy phongBanGiaiQuyet) {
		this.phongBanGiaiQuyet = phongBanGiaiQuyet;
	}

	@Transient
	public String getyKienXuLy() {
		return yKienXuLy;
	}

	public void setyKienXuLy(String yKienXuLy) {
		this.yKienXuLy = yKienXuLy;
	}

	@Transient
	public String getGhiChuXuLy() {
		return ghiChuXuLy;
	}

	public void setGhiChuXuLy(String ghiChuXuLy) {
		this.ghiChuXuLy = ghiChuXuLy;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public String getSoLuotTiepStr() {
		String out = "";
		out += getSoThuTuLuotTiep() + "/";
		if (getDon() != null) {
			out += getDon().getTongSoLuotTCD();
		} else {
			out += "1";
		}
		return out;
	}

}