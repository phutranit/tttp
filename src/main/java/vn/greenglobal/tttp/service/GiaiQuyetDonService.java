package vn.greenglobal.tttp.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.QGiaiQuyetDon;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;

@Component
public class GiaiQuyetDonService {

	BooleanExpression base = QGiaiQuyetDon.giaiQuyetDon.daXoa.eq(false);

	public GiaiQuyetDon predFindCurrent(GiaiQuyetDonRepository repo, Long id, boolean laTTXM) {
		BooleanExpression where = base
				.and(QGiaiQuyetDon.giaiQuyetDon.id.eq(id))
				.and(QGiaiQuyetDon.giaiQuyetDon.laTTXM.eq(laTTXM));
		if (repo.exists(where)) {
			OrderSpecifier<Integer> sortOrder = QGiaiQuyetDon.giaiQuyetDon.thuTuThucHien.desc();
			List<GiaiQuyetDon> results = (List<GiaiQuyetDon>) repo.findAll(where, sortOrder);
			Long lichSuId = results.get(0).getId();
			return repo.findOne(lichSuId);
		}
		return null;
	}
}
