package vn.greenglobal.tttp.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
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
	
	public XuLyDon predFindCurrent(XuLyDonRepository repo, Long donId, Long donViXuLyXLD, Long phongBanXuLyXLD, String chucVu, 
			Long congChucId, String trangThaiDon) {
		BooleanExpression xuLyDonQuery = base.and(xuLyDon.don.id.eq(donId))
				.and(QXuLyDon.xuLyDon.old.eq(false));
		
		if (phongBanXuLyXLD != null && phongBanXuLyXLD > 0) {
			xuLyDonQuery =  xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanXuLy.id.eq(phongBanXuLyXLD));
		}
		
		if (StringUtils.isNotBlank(chucVu)) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.chucVu.eq(VaiTroEnum.valueOf(StringUtils.upperCase(chucVu))));
		}
		
		if (StringUtils.isNotBlank(trangThaiDon)) {			
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.trangThaiDon.stringValue().eq(trangThaiDon));
		}
		
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}
		
		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();
		
		if (repo.exists(xuLyDonQuery)) {
			List<XuLyDon> results = (List<XuLyDon>) repo.findAll(xuLyDonQuery, sortOrder);
			Long xldId = results.get(0).getId();
			return repo.findOne(xldId);
		}
		return null;
	}
	
	public Predicate predFindOld(Long donId, VaiTroEnum vaiTro) {
		BooleanExpression predicate = base.and(xuLyDon.don.id.eq(donId));
		predicate = predicate.and(xuLyDon.chucVu.eq(vaiTro));
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
