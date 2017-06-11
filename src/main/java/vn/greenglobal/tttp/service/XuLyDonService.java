package vn.greenglobal.tttp.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.XuLyDonRepository;

@Component
public class XuLyDonService {

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
	
	public XuLyDon predFindXuLyDonHienTai(XuLyDonRepository repo, Long donId, Long donViXuLyXLD, Long phongBanXuLyXLD, String chucVu) {
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
		
		if (StringUtils.isNotBlank(chucVu)) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.chucVu.eq(VaiTroEnum.valueOf(chucVu)));
		}
		
		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();		
		if (repo.exists(xuLyDonQuery)) {
			List<XuLyDon> results = (List<XuLyDon>) repo.findAll(xuLyDonQuery, sortOrder);
			Long xldId = results.get(0).getId();
			return repo.findOne(xldId);
		}
		return null;
	}
	
	public XuLyDon predFindCurrent(XuLyDonRepository repo, Long donId, Long donViXuLyXLD, Long phongBanXuLyXLD, String chucVu) {
		BooleanExpression xuLyDonQuery = base.and(xuLyDon.don.id.eq(donId))
				.and(QXuLyDon.xuLyDon.old.eq(false));
		
		if (phongBanXuLyXLD != null && phongBanXuLyXLD > 0) {
			xuLyDonQuery =  xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanXuLy.id.eq(phongBanXuLyXLD));
		}
		
		if (StringUtils.isNotBlank(chucVu)) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.chucVu.eq(VaiTroEnum.valueOf(StringUtils.upperCase(chucVu))));
		}
		
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}
		
		/*if (congChucId != null && congChucId > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.congChuc.id.eq(congChucId));
		}*/
		
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
				.and(xuLyDon.donViXuLy.id.eq(donViId));;
		return predicate;
	}
	
	public Predicate predFindAllOld(Long donId, Long phongBanId) {
		BooleanExpression predicate = base.and(xuLyDon.don.id.eq(donId));
		predicate = predicate.and(xuLyDon.phongBanXuLy.id.eq(phongBanId));
		return predicate;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QXuLyDon.xuLyDon.id.eq(id));
	}

	public boolean isExists(XuLyDonRepository repo, Long id) {
		Predicate predicate = base.and(QXuLyDon.xuLyDon.don.id.eq(id));
		return repo.exists(predicate);
	}
}
