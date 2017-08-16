package vn.greenglobal.tttp.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.PhanLoaiDonCongDanEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.DanToc;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.DonViHanhChinh;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.PropertyChangeObject;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.model.QuocTich;
import vn.greenglobal.tttp.model.ToDanPho;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.DanTocRepository;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.repository.DonViHanhChinhRepository;
import vn.greenglobal.tttp.repository.QuocTichRepository;
import vn.greenglobal.tttp.repository.ToDanPhoRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class DonCongDanService {
	
	@Autowired
	private DonCongDanRepository donCongDanRepository;

	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepo;
	
	@Autowired
	private DonViHanhChinhRepository donViHanhChinhRepo;
	
	@Autowired
	private ToDanPhoRepository toDanPhoRepo;
	
	@Autowired 
	private DanTocRepository danTocRepo;
	
	@Autowired
	private QuocTichRepository quocTichRepo;
	
	public Predicate predicateFindAll(Long don, Long congDan, String phanLoai) {
		Predicate predAll = QDon_CongDan.don_CongDan.daXoa.eq(false);
		if (don != null && don > 0) {
			predAll = QDon_CongDan.don_CongDan.don.id.eq(don);
		}
		if (congDan != null && congDan > 0) {
			predAll = QDon_CongDan.don_CongDan.congDan.id.eq(congDan);
		}
		if (StringUtils.isNotEmpty(phanLoai)) {
			predAll = QDon_CongDan.don_CongDan.phanLoaiCongDan.stringValue().containsIgnoreCase(phanLoai);
		}
		return predAll;
	}

	public Predicate predicateFindAll(String tuKhoa) {
		BooleanExpression predAll = QDon_CongDan.don_CongDan.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QDon_CongDan.don_CongDan.congDan.hoVaTen.containsIgnoreCase(tuKhoa))
					.or(QDon_CongDan.don_CongDan.congDan.soCMNDHoChieu.eq(tuKhoa));
		}
		return predAll;
	}
	
	public Predicate predicateFindAllByNguoiUyQuyen(Don don) {
		return QDon_CongDan.don_CongDan.daXoa.eq(false).and(QDon_CongDan.don_CongDan.don.id.eq(don.getId()))
				.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.eq(PhanLoaiDonCongDanEnum.NGUOI_DUOC_UY_QUYEN));
	}

	public Predicate predicateFindByTCD(String tuKhoa) {
		BooleanExpression predAll = QDon_CongDan.don_CongDan.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QDon_CongDan.don_CongDan.congDan.hoVaTen.containsIgnoreCase(tuKhoa))
					.or(QDon_CongDan.don_CongDan.congDan.soCMNDHoChieu.eq(tuKhoa));
		}
		return predAll;
	}

	public Predicate predicateFindAllTCD(String tuKhoa, boolean thanhLapDon) {
		BooleanExpression predAll = QDon_CongDan.don_CongDan.daXoa.eq(false)
				.and(QDon_CongDan.don_CongDan.don.thanhLapDon.eq(thanhLapDon));
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return QDon_CongDan.don_CongDan.daXoa.eq(false).and(QDon_CongDan.don_CongDan.id.eq(id));
	}

	public boolean isExists(DonCongDanRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QDon_CongDan.don_CongDan.daXoa.eq(false).and(QDon_CongDan.don_CongDan.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public boolean checkExistsData(DonCongDanRepository repo, Don_CongDan body) {
		BooleanExpression predAll = QDon_CongDan.don_CongDan.daXoa.eq(false);

		if (!body.isNew()) {
			predAll = predAll.and(QDon_CongDan.don_CongDan.id.ne(body.getId()));
		}
		Don_CongDan donCongDan = repo.findOne(predAll);

		return donCongDan != null ? true : false;
	}

	public Don_CongDan delete(DonCongDanRepository repo, Long id) {
		Don_CongDan dcd = repo.findOne(predicateFindOne(id));

		if (dcd != null) {
			dcd.setDaXoa(true);
		}

		return dcd;
	}
	
	public List<PropertyChangeObject> getListThayDoi(Don_CongDan don_CongDanNew, Don_CongDan don_CongDanOld) {
		List<PropertyChangeObject> list = new ArrayList<PropertyChangeObject>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		if (don_CongDanNew.getTenCoQuan() != null 
				&& !don_CongDanNew.getTenCoQuan().equals(don_CongDanOld.getTenCoQuan())) {
			list.add(new PropertyChangeObject("Nội dung", don_CongDanOld.getTenCoQuan(), don_CongDanNew.getTenCoQuan()));
		}
		
		if (don_CongDanNew.getDiaChiCoQuan() != null 
				&& !don_CongDanNew.getDiaChiCoQuan().equals(don_CongDanOld.getDiaChiCoQuan())) {
			list.add(new PropertyChangeObject("Địa chỉ cơ quan", don_CongDanOld.getDiaChiCoQuan(), don_CongDanNew.getDiaChiCoQuan()));
		}
		if (don_CongDanNew.getSoDienThoai() != null
				&& !don_CongDanNew.getSoDienThoai().equals(don_CongDanOld.getSoDienThoai())) {
			list.add(new PropertyChangeObject("Số điện thoại", don_CongDanOld.getSoDienThoai(), don_CongDanNew.getSoDienThoai()));
		}
		if (don_CongDanNew.getHoVaTen() != null 
				&& !don_CongDanNew.getHoVaTen().equals(don_CongDanOld.getHoVaTen())) {
			list.add(new PropertyChangeObject("Họ và tên", don_CongDanOld.getHoVaTen(), don_CongDanNew.getHoVaTen()));
		}
		if (don_CongDanNew.getSoCMNDHoChieu() != null 
				&& !don_CongDanNew.getSoCMNDHoChieu().equals(don_CongDanOld.getSoCMNDHoChieu())) {
			list.add(new PropertyChangeObject("Số CMND Hộ chiếu", don_CongDanOld.getSoCMNDHoChieu(), don_CongDanNew.getSoCMNDHoChieu()));
		}
		if (don_CongDanNew.getDiaChi() != null 
				&& !don_CongDanNew.getDiaChi().equals(don_CongDanOld.getDiaChi())) {
			list.add(new PropertyChangeObject("Địa chỉ", don_CongDanOld.getDiaChi(), don_CongDanNew.getDiaChi()));
		}
		if (don_CongDanNew.getSoDienThoaiCoQuan() != null
				&& !don_CongDanNew.getSoDienThoaiCoQuan().equals(don_CongDanOld.getSoDienThoaiCoQuan())) {
			list.add(new PropertyChangeObject("Số điện thoại cơ quan", don_CongDanOld.getSoDienThoaiCoQuan(), don_CongDanNew.getSoDienThoaiCoQuan()));
		}
		if (don_CongDanNew.getThongTinGioiThieu() != null 
				&& !don_CongDanNew.getThongTinGioiThieu().equals(don_CongDanOld.getThongTinGioiThieu())) {
			list.add(new PropertyChangeObject("Thông tin giới thiệu", don_CongDanOld.getThongTinGioiThieu(), don_CongDanNew.getThongTinGioiThieu()));
		}
		if (don_CongDanNew.getNoiCapTheLuatSu() != null 
				&& !don_CongDanNew.getNoiCapTheLuatSu().equals(don_CongDanOld.getNoiCapTheLuatSu())) {
			list.add(new PropertyChangeObject("Nơi cấp thẻ luật sư", don_CongDanOld.getNoiCapTheLuatSu(), don_CongDanNew.getNoiCapTheLuatSu()));
		}
		if (don_CongDanNew.getDonVi() != null 
				&& !don_CongDanNew.getDonVi().equals(don_CongDanOld.getDonVi())) {
			list.add(new PropertyChangeObject("Đơn vị", don_CongDanOld.getDonVi(), don_CongDanNew.getDonVi()));
		}
		if (don_CongDanNew.getChucVu() != null 
				&& !don_CongDanNew.getChucVu().equals(don_CongDanOld.getChucVu())) {
			list.add(new PropertyChangeObject("Chức vụ", don_CongDanOld.getChucVu(), don_CongDanNew.getChucVu()));
		}
		if (don_CongDanNew.getSoTheLuatSu() != null 
				&& !don_CongDanNew.getSoTheLuatSu().equals(don_CongDanOld.getSoTheLuatSu())) {
			list.add(new PropertyChangeObject("Số thẻ luật sư", don_CongDanOld.getSoTheLuatSu(), don_CongDanNew.getSoTheLuatSu()));
		}
		if (don_CongDanNew.isGioiTinh() != don_CongDanOld.isGioiTinh()) {
			list.add(new PropertyChangeObject("Giới tính", don_CongDanOld.isGioiTinh() ? "Nam" : "Nữ", don_CongDanNew.isGioiTinh() ? "Nam" : "Nữ"));
		}
		if (don_CongDanNew.isLuatSu() != don_CongDanOld.isLuatSu()) {
			list.add(new PropertyChangeObject("Là luật sư", don_CongDanOld.isLuatSu() ? "Đúng" : "Sai", don_CongDanNew.isLuatSu() ? "Đúng" : "Sai"));
		}
		if (don_CongDanNew.getNgayCapTheLuatSu() != null 
				&& !don_CongDanNew.getNgayCapTheLuatSu().format(formatter).equals(
						don_CongDanOld.getNgayCapTheLuatSu() != null ? don_CongDanOld.getNgayCapTheLuatSu().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày cấp thẻ luật sư", 
					don_CongDanOld.getNgayCapTheLuatSu() != null ? don_CongDanOld.getNgayCapTheLuatSu().format(formatter) : "",
							don_CongDanNew.getNgayCapTheLuatSu().format(formatter)));
		}
		if (don_CongDanNew.getNgaySinh() != null 
				&& !don_CongDanNew.getNgaySinh().format(formatter).equals(
						don_CongDanOld.getNgaySinh() != null ? don_CongDanOld.getNgaySinh().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày sinh", 
					don_CongDanOld.getNgaySinh() != null ? don_CongDanOld.getNgaySinh().format(formatter) : "",
							don_CongDanNew.getNgaySinh().format(formatter)));
		}
		if (don_CongDanNew.getNgayCap() != null 
				&& !don_CongDanNew.getNgayCap().format(formatter).equals(
						don_CongDanOld.getNgayCap() != null ? don_CongDanOld.getNgayCap().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày cấp CMND/Hộ chiếu", 
					don_CongDanOld.getNgayCap() != null ? don_CongDanOld.getNgayCap().format(formatter) : "",
							don_CongDanNew.getNgayCap().format(formatter)));
		}
		
		if ((don_CongDanNew.getNoiCapCMND() == null && don_CongDanOld.getNoiCapCMND() != null)
				|| (don_CongDanNew.getNoiCapCMND() != null && don_CongDanOld.getNoiCapCMND() == null) 
				|| (don_CongDanNew.getNoiCapCMND() != don_CongDanOld.getNoiCapCMND())) {
			CoQuanQuanLy noiCapCMNDNew = coQuanQuanLyRepo.findOne(don_CongDanNew.getNoiCapCMND() != null ? don_CongDanNew.getNoiCapCMND().getId() : 0L);
			list.add(new PropertyChangeObject("Nơi cấp CMND",
					don_CongDanOld.getNoiCapCMND() != null ? don_CongDanOld.getNoiCapCMND().getTen() : "", noiCapCMNDNew != null ? noiCapCMNDNew.getTen() : ""));
		}
		if ((don_CongDanNew.getTinhThanh() == null && don_CongDanOld.getTinhThanh() != null)
				|| (don_CongDanNew.getTinhThanh() != null && don_CongDanOld.getTinhThanh() == null) 
				|| (don_CongDanNew.getTinhThanh() != don_CongDanOld.getTinhThanh())) {
			DonViHanhChinh donViHanhChinhNew = donViHanhChinhRepo.findOne(don_CongDanNew.getTinhThanh() != null ? don_CongDanNew.getTinhThanh().getId() : 0L);
			list.add(new PropertyChangeObject("Tỉnh thành",
					don_CongDanOld.getTinhThanh() != null ? don_CongDanOld.getTinhThanh().getTen() : "", donViHanhChinhNew != null ? donViHanhChinhNew.getTen() : ""));
		}
		
		if ((don_CongDanNew.getQuanHuyen() == null && don_CongDanOld.getQuanHuyen() != null)
				|| (don_CongDanNew.getQuanHuyen() != null && don_CongDanOld.getQuanHuyen() == null) 
				|| (don_CongDanNew.getQuanHuyen() != don_CongDanOld.getQuanHuyen())) {
			DonViHanhChinh donViHanhChinhNew = donViHanhChinhRepo.findOne(don_CongDanNew.getQuanHuyen() != null ? don_CongDanNew.getQuanHuyen().getId() : 0L);
			list.add(new PropertyChangeObject("Quận huyện",
					don_CongDanOld.getQuanHuyen() != null ? don_CongDanOld.getQuanHuyen().getTen() : "", donViHanhChinhNew != null ? donViHanhChinhNew.getTen() : ""));
		}
		
		if ((don_CongDanNew.getPhuongXa() == null && don_CongDanOld.getPhuongXa() != null)
				|| (don_CongDanNew.getPhuongXa() != null && don_CongDanOld.getPhuongXa() == null) 
				|| (don_CongDanNew.getPhuongXa() != don_CongDanOld.getPhuongXa())) {
			DonViHanhChinh donViHanhChinhNew = donViHanhChinhRepo.findOne(don_CongDanNew.getPhuongXa() != null ? don_CongDanNew.getPhuongXa().getId() : 0L);
			list.add(new PropertyChangeObject("Phường xã",
					don_CongDanOld.getPhuongXa() != null ? don_CongDanOld.getPhuongXa().getTen() : "", donViHanhChinhNew != null ? donViHanhChinhNew.getTen() : ""));
		}
		
		if ((don_CongDanNew.getToDanPho() == null && don_CongDanOld.getToDanPho() != null)
				|| (don_CongDanNew.getToDanPho() != null && don_CongDanOld.getToDanPho() == null) 
				|| (don_CongDanNew.getToDanPho() != don_CongDanOld.getToDanPho())) {
			ToDanPho toDanPhoNew = toDanPhoRepo.findOne(don_CongDanNew.getToDanPho() != null ? don_CongDanNew.getToDanPho().getId() : 0L);
			list.add(new PropertyChangeObject("Tổ dân phố",
					don_CongDanOld.getToDanPho() != null ? don_CongDanOld.getToDanPho().getTen() : "", toDanPhoNew != null ? toDanPhoNew.getTen() : ""));
		}
		
		if ((don_CongDanNew.getDanToc() == null && don_CongDanOld.getDanToc() != null)
				|| (don_CongDanNew.getDanToc() != null && don_CongDanOld.getDanToc() == null) 
				|| (don_CongDanNew.getDanToc() != don_CongDanOld.getDanToc())) {
			DanToc danTocNew = danTocRepo.findOne(don_CongDanNew.getDanToc() != null ? don_CongDanNew.getDanToc().getId() : 0L);
			list.add(new PropertyChangeObject("Dân tộc",
					don_CongDanOld.getDanToc() != null ? don_CongDanOld.getDanToc().getTen() : "", danTocNew != null ? danTocNew.getTen() : ""));
		}
		
		if ((don_CongDanNew.getQuocTich() == null && don_CongDanOld.getQuocTich() != null)
				|| (don_CongDanNew.getQuocTich() != null && don_CongDanOld.getQuocTich() == null) 
				|| (don_CongDanNew.getQuocTich() != don_CongDanOld.getQuocTich())) {
			QuocTich quocTichNew = quocTichRepo.findOne(don_CongDanNew.getQuocTich() != null ? don_CongDanNew.getQuocTich().getId() : 0L);
			list.add(new PropertyChangeObject("Quốc tịch",
					don_CongDanOld.getQuocTich() != null ? don_CongDanOld.getQuocTich().getTen() : "", quocTichNew != null ? quocTichNew.getTen() : ""));
		}
		
		return list;
	}

	public Don_CongDan save(Don_CongDan obj, Long congChucId) {
		return Utils.save(donCongDanRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(Don_CongDan obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(donCongDanRepository, obj, congChucId, eass, status);		
	}
	
}
