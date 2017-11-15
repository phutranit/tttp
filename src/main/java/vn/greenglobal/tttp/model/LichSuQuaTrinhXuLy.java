package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Size;

import com.querydsl.core.annotations.QueryInit;

import io.swagger.annotations.ApiModelProperty;

@Entity
@Table(name = "lichsuquatrinhxuly")
public class LichSuQuaTrinhXuLy extends Model<LichSuQuaTrinhXuLy> {
	private static final long serialVersionUID = 1L;

	public LichSuQuaTrinhXuLy(Don don, CongChuc nguoiXuLy, LocalDateTime ngayXuLy, 
			String ten, String noiDung, CoQuanQuanLy donViXuLy, int thuTuThucHien) {
		this.don = don;
		this.nguoiXuLy = nguoiXuLy;
		this.ngayXuLy = ngayXuLy;
		this.ten = ten;
		this.noiDung = noiDung;
		this.donViXuLy = donViXuLy;
		this.thuTuThucHien = thuTuThucHien;
	}
	
	public LichSuQuaTrinhXuLy() {
		
	}
	
	@Size(max=255)
	private String ten = "";
	//@Lob
	private String noiDung = "";
	private int thuTuThucHien = 0;
	
	@QueryInit("*.*.*")
	@ManyToOne
	private CongChuc nguoiXuLy;
	@ManyToOne
	private Don don;
	@ManyToOne
	private CoQuanQuanLy donViXuLy;
	
	private LocalDateTime ngayXuLy;
	
	private boolean laLanhDao = false;
	
	@ApiModelProperty(position = 1)
	public String getTen() {
		return ten;
	}

	public void setTen(String ten) {
		this.ten = ten;
	}

	@ApiModelProperty(position = 2)
	public LocalDateTime getNgayXuLy() {
		return ngayXuLy;
	}

	public void setNgayXuLy(LocalDateTime ngayXuLy) {
		this.ngayXuLy = ngayXuLy;
	}

	@ApiModelProperty(position = 3, example = "{}")
	public CongChuc getNguoiXuLy() {
		return nguoiXuLy;
	}
	
	@ApiModelProperty(hidden = true)
	public Don getDon() {
		return don;
	}

	public void setDon(Don don) {
		this.don = don;
	}

	public void setNguoiXuLy(CongChuc nguoiXuLy) {
		this.nguoiXuLy = nguoiXuLy;
	}

	@ApiModelProperty(position = 4)
	public String getNoiDung() {
		return noiDung;
	}

	public void setNoiDung(String noiDung) {
		this.noiDung = noiDung;
	}

	public int getThuTuThucHien() {
		return thuTuThucHien;
	}

	public void setThuTuThucHien(int thuTuThucHien) {
		this.thuTuThucHien = thuTuThucHien;
	}

	@Transient
	public String getNguoiXuLyText() {
		String str = "";
		if (getTen() != null && ("Chuyển đơn".equals(getTen()) || "Chuyển đơn vị TTXM".equals(getTen()) || "Chuyển đơn vị KTDX".equals(getTen()))) {
			if (getNguoiXuLy() != null) {
				str = getNguoiXuLy().getCoQuanQuanLy().getDonVi().getTen();
			}
		} else {
			if (getNguoiXuLy() != null) {
				str = getNguoiXuLy().getHoVaTen();
			}
		}
		return str;
	}

	@ApiModelProperty(hidden = true)
	public CoQuanQuanLy getDonViXuLy() {
		return donViXuLy;
	}

	public void setDonViXuLy(CoQuanQuanLy donViXuLy) {
		this.donViXuLy = donViXuLy;
	}

	public boolean isLaLanhDao() {
		return laLanhDao;
	}

	public void setLaLanhDao(boolean laLanhDao) {
		this.laLanhDao = laLanhDao;
	}
}
