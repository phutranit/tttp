package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.tttp.enums.ChucVuEnum;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.QuyTrinhXuLyDonEnum;

@Entity
@Table(name = "xulydon")
@ApiModel
public class XuLyDon extends Model<XuLyDon> {
	private static final long serialVersionUID = -8406016838422350892L;

	@ManyToOne
	private Don don;
	@ManyToOne
	private CongChuc congChuc;
	@ManyToOne
	private ThamQuyenGiaiQuyet thamQuyenGiaiQuyet;
	@ManyToOne
	private CoQuanQuanLy phongBanXuLy;
	@ManyToOne 
	private CongChuc canBoXuLy;
	@ManyToOne 
	private CongChuc canBoXuLyChiDinh;
	@ManyToOne
	private CoQuanQuanLy coQuanTiepNhan;
	@ManyToOne
	private CoQuanQuanLy phongBanGiaiQuyet;
	
	private int thuTuThucHien = 0;
	
	@Lob
	private String ghiChu = "";
	private String yKienXuLy = "";
	private String moTaTrangThai = "";
	private String noiDungYeuCauXuLy = "";
	private String noiDungThongTinTrinhLanhDao = "";

	@Enumerated(EnumType.STRING)
	private ChucVuEnum chucVu;
	@Enumerated(EnumType.STRING)
	private ChucVuEnum chucVuGiaoViec;
	public ChucVuEnum getChucVuGiaoViec() {
		return chucVuGiaoViec;
	}

	public void setChucVuGiaoViec(ChucVuEnum chucVuGiaoViec) {
		this.chucVuGiaoViec = chucVuGiaoViec;
	}

	@Enumerated(EnumType.STRING)
	private QuyTrinhXuLyDonEnum quyTrinhXuLy;
	@Enumerated(EnumType.STRING)
	private HuongXuLyXLDEnum huongXuLy;
	
	@ApiModelProperty(example = "{}")
	public CoQuanQuanLy getCoQuanTiepNhan() {
		return coQuanTiepNhan;
	}

	public void setCoQuanTiepNhan(CoQuanQuanLy coQuanTiepNhan) {
		this.coQuanTiepNhan = coQuanTiepNhan;
	}

	@ApiModelProperty(example = "{}")
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	@ApiModelProperty(example = "{}")
	public CongChuc getCongChuc() {
		return congChuc;
	}

	public void setCongChuc(CongChuc congChuc) {
		this.congChuc = congChuc;
	}

	@ApiModelProperty(example = "{}")
	public ThamQuyenGiaiQuyet getThamQuyenGiaiQuyet() {
		return thamQuyenGiaiQuyet;
	}

	public void setThamQuyenGiaiQuyet(ThamQuyenGiaiQuyet thamQuyenGiaiQuyet) {
		this.thamQuyenGiaiQuyet = thamQuyenGiaiQuyet;
	}

	@ApiModelProperty(example = "{}")
	public CoQuanQuanLy getPhongBanGiaiQuyet() {
		return phongBanGiaiQuyet;
	}

	public void setPhongBanGiaiQuyet(CoQuanQuanLy phongBanGiaiQuyet) {
		this.phongBanGiaiQuyet = phongBanGiaiQuyet;
	}

	public int getThuTuThucHien() {
		return thuTuThucHien;
	}

	public void setThuTuThucHien(int thuTuThucHien) {
		this.thuTuThucHien = thuTuThucHien;
	}

	public String getGhiChu() {
		return ghiChu;
	}

	public void setGhiChu(String ghiChu) {
		this.ghiChu = ghiChu;
	}

	public String getyKienXuLy() {
		return yKienXuLy;
	}

	public void setyKienXuLy(String yKienXuLy) {
		this.yKienXuLy = yKienXuLy;
	}
	
	public String getMoTaTrangThai() {
		return moTaTrangThai;
	}

	public void setMoTaTrangThai(String moTaTrangThai) {
		this.moTaTrangThai = moTaTrangThai;
	}

	public String getNoiDungThongTinTrinhLanhDao() {
		return noiDungThongTinTrinhLanhDao;
	}

	public void setNoiDungThongTinTrinhLanhDao(String noiDungThongTinTrinhLanhDao) {
		this.noiDungThongTinTrinhLanhDao = noiDungThongTinTrinhLanhDao;
	}

	public String getNoiDungYeuCauXuLy() {
		return noiDungYeuCauXuLy;
	}
	
	@ApiModelProperty(example = "{}")
	public CongChuc getCanBoXuLyChiDinh() {
		return canBoXuLyChiDinh;
	}

	public void setCanBoXuLyChiDinh(CongChuc canBoChiDinh) {
		this.canBoXuLyChiDinh = canBoChiDinh;
	}

	public void setNoiDungYeuCauXuLy(String noiDungYeuCauXuLy) {
		this.noiDungYeuCauXuLy = noiDungYeuCauXuLy;
	}

	public QuyTrinhXuLyDonEnum getQuyTrinhXuLy() {
		return quyTrinhXuLy;
	}

	public void setQuyTrinhXuLy(QuyTrinhXuLyDonEnum quyTrinhXuLy) {
		this.quyTrinhXuLy = quyTrinhXuLy;
	}

	public HuongXuLyXLDEnum getHuongXuLy() {
		return huongXuLy;
	}

	public void setHuongXuLy(HuongXuLyXLDEnum huongXuLy) {
		this.huongXuLy = huongXuLy;
	}
	
	public ChucVuEnum getChucVu() {
		return chucVu;
	}

	public void setChucVu(ChucVuEnum chucVu) {
		this.chucVu = chucVu;
	}
	
	@ApiModelProperty(example = "{}")
	public CoQuanQuanLy getPhongBanXuLy() {
		return phongBanXuLy;
	}

	public void setPhongBanXuLy(CoQuanQuanLy phongBanXuLy) {
		this.phongBanXuLy = phongBanXuLy;
	}
	
	@ApiModelProperty(example = "{}")
	public CongChuc getCanBoXuLy() {
		return canBoXuLy;
	}

	public void setCanBoXuLy(CongChuc canBoXuLy) {
		this.canBoXuLy = canBoXuLy;
	}
}
