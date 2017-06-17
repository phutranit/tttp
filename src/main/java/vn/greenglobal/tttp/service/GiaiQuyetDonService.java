package vn.greenglobal.tttp.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.QGiaiQuyetDon;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;

@Component
public class GiaiQuyetDonService {

	QGiaiQuyetDon giaiQuyetDon = QGiaiQuyetDon.giaiQuyetDon;
	BooleanExpression base = giaiQuyetDon.daXoa.eq(false);

	public GiaiQuyetDon predFindCurrent(GiaiQuyetDonRepository repo, Long id, boolean laTTXM) {
		BooleanExpression where = base
				.and(giaiQuyetDon.thongTinGiaiQuyetDon.id.eq(id))
				.and(giaiQuyetDon.laTTXM.eq(laTTXM));
		if (repo.exists(where)) {
			OrderSpecifier<Integer> sortOrder = QGiaiQuyetDon.giaiQuyetDon.thuTuThucHien.desc();
			List<GiaiQuyetDon> results = (List<GiaiQuyetDon>) repo.findAll(where, sortOrder);
			Long lichSuId = results.get(0).getId();
			return repo.findOne(lichSuId);
		}
		return null;
	}
	
	public Predicate predFindOld(Long donId, VaiTroEnum vaiTro, CongChuc congChuc) {
		BooleanExpression predicate = base.and(giaiQuyetDon.thongTinGiaiQuyetDon.don.id.eq(donId));
		predicate = predicate.and(giaiQuyetDon.chucVu.eq(vaiTro))
				.and(giaiQuyetDon.congChuc.coQuanQuanLy.donVi.eq(congChuc.getCoQuanQuanLy().getDonVi()));
		return predicate;
	}
	
	public GiaiQuyetDon predFindThongTinXuLy(GiaiQuyetDonRepository repo, Long donId, Long donViGiaiQuyet, Long phongBanGiaiQuyet, Long canBoId, String chucVu) {
		BooleanExpression giaiQuyetDonQuery = base.and(giaiQuyetDon.thongTinGiaiQuyetDon.don.id.eq(donId))
				.and(giaiQuyetDon.old.eq(false));
		
		if (chucVu.equals(VaiTroEnum.LANH_DAO.name()) || chucVu.equals(VaiTroEnum.VAN_THU.name())) {
			phongBanGiaiQuyet = 0L;
		}
		
		if (phongBanGiaiQuyet != null && phongBanGiaiQuyet > 0) {
			giaiQuyetDonQuery =  giaiQuyetDonQuery.and(giaiQuyetDon.phongBanGiaiQuyet.id.eq(phongBanGiaiQuyet));
		}
		
		if (StringUtils.isNotEmpty(chucVu)) {
			giaiQuyetDonQuery = giaiQuyetDonQuery.and(giaiQuyetDon.chucVu.eq(VaiTroEnum.valueOf(chucVu)));
		}
		
		if (donViGiaiQuyet != null && donViGiaiQuyet > 0) {
			giaiQuyetDonQuery = giaiQuyetDonQuery.and(giaiQuyetDon.donViGiaiQuyet.id.eq(donViGiaiQuyet));
		}
		
		if (StringUtils.isNotEmpty(chucVu) && StringUtils.equals(chucVu, VaiTroEnum.CHUYEN_VIEN.name())) {
			giaiQuyetDonQuery = giaiQuyetDonQuery.and(giaiQuyetDon.canBoXuLyChiDinh.id.eq(canBoId));
		}
		
		OrderSpecifier<Integer> sortOrder = giaiQuyetDon.thuTuThucHien.desc();
		
		if (repo.exists(giaiQuyetDonQuery)) {
			List<GiaiQuyetDon> results = (List<GiaiQuyetDon>) repo.findAll(giaiQuyetDonQuery, sortOrder);
			Long xldId = results.get(0).getId();
			return repo.findOne(xldId);
		}
		return null;
	}
}
