package vn.greenglobal.tttp.service;

import java.util.List;

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

	BooleanExpression base = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false);

	public GiaiQuyetDon predFindCurrent(GiaiQuyetDonRepository repo, Long id, boolean laTTXM) {
		BooleanExpression where = base
				.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.id.eq(id))
				.and(QGiaiQuyetDon.giaiQuyetDon.laTTXM.eq(laTTXM));
		if (repo.exists(where)) {
			OrderSpecifier<Integer> sortOrder = QGiaiQuyetDon.giaiQuyetDon.thuTuThucHien.desc();
			List<GiaiQuyetDon> results = (List<GiaiQuyetDon>) repo.findAll(where, sortOrder);
			Long lichSuId = results.get(0).getId();
			return repo.findOne(lichSuId);
		}
		return null;
	}
	
	public Predicate predFindOld(Long donId, VaiTroEnum vaiTro, CongChuc congChuc) {
		BooleanExpression predicate = base.and(QGiaiQuyetDon.giaiQuyetDon.thongTinGiaiQuyetDon.don.id.eq(donId));
		predicate = predicate.and(QGiaiQuyetDon.giaiQuyetDon.chucVu.eq(vaiTro))
				.and(QGiaiQuyetDon.giaiQuyetDon.congChuc.coQuanQuanLy.donVi.eq(congChuc.getCoQuanQuanLy().getDonVi()));
		return predicate;
	}
}
