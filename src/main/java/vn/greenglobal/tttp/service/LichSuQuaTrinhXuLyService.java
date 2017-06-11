package vn.greenglobal.tttp.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.LichSuQuaTrinhXuLy;
import vn.greenglobal.tttp.model.QLichSuQuaTrinhXuLy;
import vn.greenglobal.tttp.repository.LichSuQuaTrinhXuLyRepository;

@Component
public class LichSuQuaTrinhXuLyService {

	BooleanExpression base = QLichSuQuaTrinhXuLy.lichSuQuaTrinhXuLy.daXoa.eq(false);

	public Predicate predicateFindAll(Long donId) {
		BooleanExpression predAll = base.and(QLichSuQuaTrinhXuLy.lichSuQuaTrinhXuLy.don.id.eq(donId));
		return predAll;
	}
	
	public Predicate predicateFindAll(Long donId, Long donViId) {
		BooleanExpression predAll = base.and(QLichSuQuaTrinhXuLy.lichSuQuaTrinhXuLy.don.id.eq(donId))
				.and(QLichSuQuaTrinhXuLy.lichSuQuaTrinhXuLy.nguoiXuLy.coQuanQuanLy.donVi.id.eq(donViId));
		return predAll;
	}
	
	public int timThuTuLichSuQuaTrinhXuLyHienTai(LichSuQuaTrinhXuLyRepository repo, Long donId) {
		int thuTu = 0;
		List<LichSuQuaTrinhXuLy> lichSuList = (List<LichSuQuaTrinhXuLy>) repo.findAll(predicateFindAll(donId));
		if (lichSuList != null) {
			thuTu = lichSuList.size();
		}
		return thuTu;
	}
}
