package vn.greenglobal.tttp.service;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.PropertyChangeObject;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.ThamQuyenGiaiQuyetRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;

@Component
public class XuLyDonService {

	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepo;
	
	@Autowired
	private ThamQuyenGiaiQuyetRepository thamQuyenGiaiQuyetRepository;
	
	@Autowired
	private CongChucRepository congChucRepo;
	
	QXuLyDon xuLyDon = QXuLyDon.xuLyDon;
	BooleanExpression base = xuLyDon.daXoa.eq(false);

	public XuLyDon predFindCurrent(XuLyDonRepository repo, Long id) {
		BooleanExpression where = base.and(xuLyDon.don.id.eq(id));
		if (repo.exists(where)) {
			OrderSpecifier<Integer> sortOrder = xuLyDon.thuTuThucHien.desc();
			List<XuLyDon> results = (List<XuLyDon>) repo.findAll(where, sortOrder);
			Long xldId = results.get(0).getId();
			return repo.findOne(xldId);
		}
		return null;
	}
	
	public XuLyDon predFindXuLyDonHienTai(XuLyDonRepository repo, Long donId, Long donViXuLyXLD, Long phongBanXuLyXLD, Long canBoId, String chucVu) {
		BooleanExpression xuLyDonQuery = base.and(xuLyDon.don.id.eq(donId))
				.and(QXuLyDon.xuLyDon.old.eq(false));
		if (chucVu.equals(VaiTroEnum.LANH_DAO.name()) || chucVu.equals(VaiTroEnum.VAN_THU.name())) {
			phongBanXuLyXLD = 0L;
		}
		if (phongBanXuLyXLD != null && phongBanXuLyXLD > 0) {
			xuLyDonQuery =  xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanXuLy.id.eq(phongBanXuLyXLD));
		}
	
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}
		
		if (StringUtils.isNotEmpty(chucVu)) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.chucVu.eq(VaiTroEnum.valueOf(chucVu)));
		}
		
		if (StringUtils.isNotEmpty(chucVu) && StringUtils.equals(chucVu, VaiTroEnum.CHUYEN_VIEN.name())) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.canBoXuLyChiDinh.id.eq(canBoId));
		}
		
		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();		
		if (repo.exists(xuLyDonQuery)) {
			List<XuLyDon> results = (List<XuLyDon>) repo.findAll(xuLyDonQuery, sortOrder);
			Long xldId = results.get(0).getId();
			return repo.findOne(xldId);
		}
		return null;
	}
	
	public XuLyDon predFindThongTinXuLy(XuLyDonRepository repo, Long donId, Long donViXuLyXLD, Long phongBanXuLyXLD, Long canBoId, String chucVu) {
		BooleanExpression xuLyDonQuery = base.and(xuLyDon.don.id.eq(donId))
				.and(QXuLyDon.xuLyDon.old.eq(false));
		
		if (chucVu.equals(VaiTroEnum.LANH_DAO.name()) || chucVu.equals(VaiTroEnum.VAN_THU.name())) {
			phongBanXuLyXLD = 0L;
		}
		
		if (phongBanXuLyXLD != null && phongBanXuLyXLD > 0) {
			xuLyDonQuery =  xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanXuLy.id.eq(phongBanXuLyXLD));
		}
		
		if (StringUtils.isNotEmpty(chucVu)) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.chucVu.eq(VaiTroEnum.valueOf(chucVu)));
		}
		
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}
		
		if (StringUtils.isNotEmpty(chucVu) && StringUtils.equals(chucVu, VaiTroEnum.CHUYEN_VIEN.name())) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.canBoXuLyChiDinh.id.eq(canBoId));
		}
		
		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();
		
		if (repo.exists(xuLyDonQuery)) {
			List<XuLyDon> results = (List<XuLyDon>) repo.findAll(xuLyDonQuery, sortOrder);
			Long xldId = results.get(0).getId();
			return repo.findOne(xldId);
		}
		return null;
	}
	
	public Predicate predFindLichSuXLD(XuLyDonRepository repo, Long donId, Long donViXuLyXLD, Long phongBanXuLyXLD,
			Long congChucId) {
		BooleanExpression xuLyDonQuery = base.and(xuLyDon.don.id.eq(donId))
				.and(QXuLyDon.xuLyDon.old.eq(true))
				.and(QXuLyDon.xuLyDon.trangThaiDon.eq(TrangThaiDonEnum.DA_XU_LY));
		if (phongBanXuLyXLD != null && phongBanXuLyXLD > 0) {
			xuLyDonQuery =  xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanXuLy.id.eq(phongBanXuLyXLD));
		}
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}
		return xuLyDonQuery;
	}
	
	public Predicate predFindOld(Long donId, Long phongBanId, Long donViId, VaiTroEnum vaiTro) {
		BooleanExpression predicate = base.and(xuLyDon.don.id.eq(donId));
		predicate = predicate.and(xuLyDon.chucVu.eq(vaiTro))
				.and(xuLyDon.phongBanXuLy.id.eq(phongBanId))
				.and(xuLyDon.donViXuLy.id.eq(donViId));
		return predicate;
	}
	
	public Predicate predFindChuyenVienOld(Long donId, Long canBoId, Long phongBanId, Long donViId, VaiTroEnum vaiTro) {
		BooleanExpression predicate = base.and(xuLyDon.don.id.eq(donId));
		predicate = predicate.and(xuLyDon.chucVu.eq(vaiTro))
				.and(xuLyDon.canBoXuLyChiDinh.id.eq(canBoId))
				.and(xuLyDon.phongBanXuLy.id.eq(phongBanId))
				.and(xuLyDon.donViXuLy.id.eq(donViId));
		return predicate;
	}
	
	public Predicate predFindLanhDaoVanThuOld(VaiTroEnum vaiTro, Long donId, Long donViId) {
		BooleanExpression predicate = base.and(xuLyDon.don.id.eq(donId));
		predicate = predicate.and(xuLyDon.chucVu.eq(vaiTro))
				.and(xuLyDon.donViXuLy.id.eq(donViId));
		return predicate;
	}
	
	public Predicate predicateFindOne(Long id) {
		return base.and(QXuLyDon.xuLyDon.id.eq(id));
	}

	public boolean isExists(XuLyDonRepository repo, Long id) {
		Predicate predicate = base.and(QXuLyDon.xuLyDon.don.id.eq(id));
		return repo.exists(predicate);
	}
	
	public List<PropertyChangeObject> getListThayDoi(XuLyDon xuLyDonNew, XuLyDon xuLyDonOld) {
		List<PropertyChangeObject> list = new ArrayList<PropertyChangeObject>();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		if (xuLyDonNew.getNoiDungXuLy() != null
				&& !xuLyDonNew.getNoiDungXuLy().equals(xuLyDonOld.getNoiDungXuLy())) {
			list.add(new PropertyChangeObject("Nội dung xử lý", "noiDungXuLy", xuLyDonNew.getNoiDungXuLy()));
		}
		
		if (xuLyDonNew.getyKienXuLy() != null
				&& !xuLyDonNew.getyKienXuLy().equals(xuLyDonOld.getyKienXuLy())) {
			list.add(new PropertyChangeObject("Ý kiến xử lý", "yKienXuLy", xuLyDonNew.getyKienXuLy()));
		}
		
		if (xuLyDonNew.getNoiDungYeuCauXuLy() != null
				&& !xuLyDonNew.getNoiDungYeuCauXuLy().equals(xuLyDonOld.getNoiDungYeuCauXuLy())) {
			list.add(new PropertyChangeObject("Yêu cầu xử lý", "noiDungYeuCauXuLy", xuLyDonNew.getNoiDungYeuCauXuLy()));
		}
		
		if (xuLyDonNew.getNoiDungThongTinTrinhLanhDao() != null
				&& !xuLyDonNew.getNoiDungThongTinTrinhLanhDao().equals(xuLyDonOld.getNoiDungThongTinTrinhLanhDao())) {
			list.add(new PropertyChangeObject("Thông tin trình lãnh đạo", "noiDungThongTinTrinhLanhDao", xuLyDonNew.getNoiDungThongTinTrinhLanhDao()));
		}
		
		if (xuLyDonNew.getDiaDiem() != null
				&& !xuLyDonNew.getDiaDiem().equals(xuLyDonOld.getDiaDiem())) {
			list.add(new PropertyChangeObject("Địa điểm", "diaDiem", xuLyDonNew.getDiaDiem()));
		}
		
		if (xuLyDonNew.getSoQuyetDinhDinhChi() != null
				&& !xuLyDonNew.getSoQuyetDinhDinhChi().equals(xuLyDonOld.getSoQuyetDinhDinhChi())) {
			list.add(new PropertyChangeObject("Số quyết định đình chỉ", "soQuyetDinhDinhChi", xuLyDonNew.getSoQuyetDinhDinhChi()));
		}
		
		if (xuLyDonNew.getThoiHanXuLy() != null
				&& !xuLyDonNew.getThoiHanXuLy().format(formatter).equals(
						xuLyDonOld.getThoiHanXuLy() != null ? xuLyDonOld.getThoiHanXuLy().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Thời hạn xử lý", "thoiHanXuLy",
					xuLyDonNew.getThoiHanXuLy().format(formatter)));
		}
		
		if (xuLyDonNew.getNgayHenGapLanhDao() != null
				&& !xuLyDonNew.getNgayHenGapLanhDao().format(formatter).equals(
						xuLyDonOld.getNgayHenGapLanhDao() != null ? xuLyDonOld.getNgayHenGapLanhDao().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày hẹn gặp lãnh đạo", "ngayHenGapLanhDao",
					xuLyDonNew.getNgayHenGapLanhDao().format(formatter)));
		}
		
		if (xuLyDonNew.getNgayQuyetDinhDinhChi() != null
				&& !xuLyDonNew.getNgayQuyetDinhDinhChi().format(formatter).equals(
						xuLyDonOld.getNgayQuyetDinhDinhChi() != null ? xuLyDonOld.getNgayQuyetDinhDinhChi().format(formatter) : "")) {
			list.add(new PropertyChangeObject("Ngày quyết định đình chỉ", "ngayQuyetDinhDinhChi",
					xuLyDonNew.getNgayQuyetDinhDinhChi().format(formatter)));
		}
		
		if (xuLyDonNew.getPhongBanXuLyChiDinh() != null && xuLyDonNew.getPhongBanXuLyChiDinh() != xuLyDonOld.getPhongBanXuLyChiDinh()) {
			CoQuanQuanLy coQuanQuanLyNew = coQuanQuanLyRepo.findOne(xuLyDonNew.getPhongBanXuLyChiDinh().getId());
			list.add(new PropertyChangeObject("Phòng ban xử lý","phongBanXuLyChiDinh", coQuanQuanLyNew.getTen()));
		}
		
		if (xuLyDonNew.getPhongBanGiaiQuyet() != null && xuLyDonNew.getPhongBanGiaiQuyet() != xuLyDonOld.getPhongBanGiaiQuyet()) {
			CoQuanQuanLy coQuanQuanLyNew = coQuanQuanLyRepo.findOne(xuLyDonNew.getPhongBanGiaiQuyet().getId());
			list.add(new PropertyChangeObject("Phòng ban giải quyết","phongBanGiaiQuyet", coQuanQuanLyNew.getTen()));
		}
		
		if (xuLyDonNew.getCoQuanTiepNhan() != null && xuLyDonNew.getCoQuanTiepNhan() != xuLyDonOld.getCoQuanTiepNhan()) {
			CoQuanQuanLy coQuanQuanLyNew = coQuanQuanLyRepo.findOne(xuLyDonNew.getCoQuanTiepNhan().getId());
			list.add(new PropertyChangeObject("Cơ quan tiếp nhận","coQuanTiepNhan", coQuanQuanLyNew.getTen()));
		}
		
		if (xuLyDonNew.getHuongXuLy() != null 
				&& !xuLyDonNew.getHuongXuLy().equals(xuLyDonOld.getHuongXuLy())) {
			list.add(new PropertyChangeObject("Hướng xử lý", "huongXuLy", xuLyDonNew.getHuongXuLy().getText()));
		}
	}
}
