package vn.greenglobal.tttp.model.medial;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.annotations.ApiModelProperty;
import vn.greenglobal.Application;
import vn.greenglobal.tttp.enums.KetQuaTrangThaiDonEnum;
import vn.greenglobal.tttp.enums.PhanLoaiDonCongDanEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.Model;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.util.Utils;

@Entity
@Table(name = "medial_dontracuu")
public class Medial_DonTraCuu extends Model<Medial_DonTraCuu>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3019291568638856731L;
	
	public Medial_DonTraCuu() {
		this.setId(0l);
	}
	
	public void copyDon(Don don) {
		setDonGocId(don.getDonGocId() != null && don.getDonGocId() > 0 ? don.getDonGocId() : don.getId());
		setNoiDung(don.getNoiDung());
		setTrangThaiDon(don.getTrangThaiXLDGiaiQuyet().getText());
		setKetQuaDon(don.getKetQuaXLDGiaiQuyet().getText());
		setProcessType(don.getProcessType());
		setThongTinGiaiQuyetDon(don.getThongTinGiaiQuyetDon());
		setTrangThaiTTXM(don.getTrangThaiTTXM());
		setTrangThaiKTDX(don.getTrangThaiKTDX());
		setNgayBatDauXLD(don.getNgayBatDauXLD());
		setThoiHanXuLyXLD(don.getThoiHanXuLyXLD());
		setKetQuaXLDGiaiQuyet(don.getKetQuaXLDGiaiQuyet());
		if (don.getProcessType().equals(ProcessTypeEnum.XU_LY_DON) || don.getProcessType().equals(ProcessTypeEnum.GIAI_QUYET_DON)) {
			setTenDonViGiuDenHienTai(don.getDonViXuLyGiaiQuyet() != null ? don.getDonViXuLyGiaiQuyet().getTen() : "");
		} else if (don.getProcessType().equals(ProcessTypeEnum.THAM_TRA_XAC_MINH)) {
			setTenDonViGiuDenHienTai(don.getDonViThamTraXacMinh() != null ? don.getDonViThamTraXacMinh().getTen() : "");
			
		} else if (don.getProcessType().equals(ProcessTypeEnum.KIEM_TRA_DE_XUAT)) {
			setTenDonViGiuDenHienTai(don.getDonViKiemTraDeXuat() != null ? don.getDonViKiemTraDeXuat().getTen() : "");
		}
	}
	
	@JsonIgnore
	private Long donGocId;
	private String noiDung = "";
	private String trangThaiDon = "";
	private String ketQuaDon = "";
	private String tenDonViGiuDenHienTai = "";
	@JsonIgnore
	private LocalDateTime ngayBatDauXLD;
	@JsonIgnore
	private LocalDateTime thoiHanXuLyXLD;
	@JsonIgnore
	private ProcessTypeEnum processType;
	@JsonIgnore
	private ThongTinGiaiQuyetDon thongTinGiaiQuyetDon;
	@JsonIgnore
	private TrangThaiDonEnum trangThaiTTXM;
	@JsonIgnore
	private TrangThaiDonEnum trangThaiKTDX;
	@JsonIgnore
	private KetQuaTrangThaiDonEnum ketQuaXLDGiaiQuyet;
	
	public Long getDonGocId() {
		return donGocId;
	}
	
	public void setDonGocId(Long donGocId) {
		this.donGocId = donGocId;
	}
	
	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	public String getTrangThaiDon() {
		return trangThaiDon;
	}

	public void setTrangThaiDon(String trangThaiDon) {
		this.trangThaiDon = trangThaiDon;
	}

	public String getKetQuaDon() {
		return ketQuaDon;
	}

	public void setKetQuaDon(String ketQuaDon) {
		this.ketQuaDon = ketQuaDon;
	}

	public String getTenDonViGiuDenHienTai() {
		return tenDonViGiuDenHienTai;
	}

	public void setTenDonViGiuDenHienTai(String tenDonViGiuDenHienTai) {
		this.tenDonViGiuDenHienTai = tenDonViGiuDenHienTai;
	}
	
	public LocalDateTime getNgayBatDauXLD() {
		return ngayBatDauXLD;
	}

	public void setNgayBatDauXLD(LocalDateTime ngayBatDauXLD) {
		this.ngayBatDauXLD = ngayBatDauXLD;
	}

	public LocalDateTime getThoiHanXuLyXLD() {
		return thoiHanXuLyXLD;
	}

	public void setThoiHanXuLyXLD(LocalDateTime thoiHanXuLyXLD) {
		this.thoiHanXuLyXLD = thoiHanXuLyXLD;
	}

	public ProcessTypeEnum getProcessType() {
		return processType;
	}

	public void setProcessType(ProcessTypeEnum processType) {
		this.processType = processType;
	}
	
	public ThongTinGiaiQuyetDon getThongTinGiaiQuyetDon() {
		return thongTinGiaiQuyetDon;
	}

	public void setThongTinGiaiQuyetDon(ThongTinGiaiQuyetDon thongTinGiaiQuyetDon) {
		this.thongTinGiaiQuyetDon = thongTinGiaiQuyetDon;
	}
	
	public TrangThaiDonEnum getTrangThaiTTXM() {
		return trangThaiTTXM;
	}

	public void setTrangThaiTTXM(TrangThaiDonEnum trangThaiTTXM) {
		this.trangThaiTTXM = trangThaiTTXM;
	}	

	public TrangThaiDonEnum getTrangThaiKTDX() {
		return trangThaiKTDX;
	}

	public void setTrangThaiKTDX(TrangThaiDonEnum trangThaiKTDX) {
		this.trangThaiKTDX = trangThaiKTDX;
	}
	
	public KetQuaTrangThaiDonEnum getKetQuaXLDGiaiQuyet() {
		return ketQuaXLDGiaiQuyet;
	}

	public void setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum ketQuaXLDGiaiQuyet) {
		this.ketQuaXLDGiaiQuyet = ketQuaXLDGiaiQuyet;
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public Map<String, Object> getThoiHanInfo() {
		if (getProcessType().equals(ProcessTypeEnum.XU_LY_DON)) {
			return Utils.convertThoiHan(getNgayBatDauXLD(), getThoiHanXuLyXLD(), null);
		} else if (getProcessType().equals(ProcessTypeEnum.GIAI_QUYET_DON)) {
			if (getThongTinGiaiQuyetDon() != null) {
				return Utils.convertThoiHan(getThongTinGiaiQuyetDon().getNgayBatDauGiaiQuyet(),
						getThongTinGiaiQuyetDon().getNgayHetHanGiaiQuyet(),
						getThongTinGiaiQuyetDon().getNgayHetHanSauKhiGiaHanGiaiQuyet());
			}
		} else if (getProcessType().equals(ProcessTypeEnum.THAM_TRA_XAC_MINH)) {
			if (getTrangThaiTTXM() != null && getTrangThaiTTXM().equals(TrangThaiDonEnum.DANG_GIAI_QUYET)) { 
				if (getThongTinGiaiQuyetDon() != null) {
					if (getKetQuaXLDGiaiQuyet().equals(KetQuaTrangThaiDonEnum.DANG_LAP_DU_THAO)) {
						return Utils.convertThoiHan(getThongTinGiaiQuyetDon().getNgayBatDauGiaoLapDuThao(),
								getThongTinGiaiQuyetDon().getNgayHetHanGiaoLapDuThao(),
								getThongTinGiaiQuyetDon().getNgayHetHanSauKhiGiaHanGiaoLapDuThao());
					} else {
						return Utils.convertThoiHan(getThongTinGiaiQuyetDon().getNgayBatDauTTXM(),
								getThongTinGiaiQuyetDon().getNgayHetHanTTXM(),
								getThongTinGiaiQuyetDon().getNgayHetHanSauKhiGiaHanTTXM());
					}
				}
			}
		} else if (getProcessType().equals(ProcessTypeEnum.KIEM_TRA_DE_XUAT)) {
			if (getThongTinGiaiQuyetDon() != null) {
				if (getTrangThaiKTDX() != null && getTrangThaiKTDX().equals(TrangThaiDonEnum.DANG_GIAI_QUYET)) { 
					return Utils.convertThoiHan(getThongTinGiaiQuyetDon().getNgayBatDauKTDX(),
							getThongTinGiaiQuyetDon().getNgayHetHanKTDX(),
							getThongTinGiaiQuyetDon().getNgayHetHanSauKhiGiaHanKTDX());
				}
			}
		}
		return new HashMap<>();
	}

	@Transient
	@ApiModelProperty(hidden = true)
	public List<Don_CongDan> getListNguoiDungDon() {
		List<Don_CongDan> list = new ArrayList<Don_CongDan>();
		Don donGoc = Application.app.getDonRepository().findOne(getDonGocId());
		list = (List<Don_CongDan>) Application.app.getDonCongDanRepository()
				.findAll(QDon_CongDan.don_CongDan.don.eq(donGoc)
						.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON))
						.and(QDon_CongDan.don_CongDan.daXoa.eq(false)));
		return list;
	}
}
