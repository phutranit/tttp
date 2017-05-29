package vn.greenglobal.tttp.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.LichSuGiaiQuyetDon;
import vn.greenglobal.tttp.model.QLichSuGiaiQuyetDon;
import vn.greenglobal.tttp.repository.LichSuGiaiQuyetDonRepository;

@Component
public class LichSuGiaiQuyetDonService {

	BooleanExpression base = QLichSuGiaiQuyetDon.lichSuGiaiQuyetDon.daXoa.eq(false);

	public LichSuGiaiQuyetDon predFindCurrent(LichSuGiaiQuyetDonRepository repo, Long id) {
		BooleanExpression where = base.and(QLichSuGiaiQuyetDon.lichSuGiaiQuyetDon.giaiQuyetDon.id.eq(id));
		if (repo.exists(where)) {
			OrderSpecifier<Integer> sortOrder = QLichSuGiaiQuyetDon.lichSuGiaiQuyetDon.thuTuThucHien.desc();
			List<LichSuGiaiQuyetDon> results = (List<LichSuGiaiQuyetDon>) repo.findAll(where, sortOrder);
			Long lichSuId = results.get(0).getId();
			return repo.findOne(lichSuId);
		}
		return null;
	}
}
