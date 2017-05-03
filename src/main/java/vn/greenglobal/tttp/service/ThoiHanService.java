package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QThoiHan;
import vn.greenglobal.tttp.model.ThoiHan;
import vn.greenglobal.tttp.repository.ThoiHanRepository;

@Component
public class ThoiHanService {

	BooleanExpression base = QThoiHan.thoiHan.daXoa.eq(false);

	public Predicate predicateFindAll(String soNgay, String loaiThoiHan) {
		BooleanExpression predAll = base;

		int num = soNgay != null ? new Integer(soNgay) : 0;

		if (num > 0) {
			predAll = predAll.and(QThoiHan.thoiHan.soNgay.eq(num));
		}

		if (loaiThoiHan != null && !"".equals(loaiThoiHan)) {
			predAll = predAll.and(QThoiHan.thoiHan.loaiThoiHanEnum.stringValue().eq(loaiThoiHan));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QThoiHan.thoiHan.id.eq(id));
	}

	public boolean isExists(ThoiHanRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QThoiHan.thoiHan.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public ThoiHan delete(ThoiHanRepository repo, Long id) {
		ThoiHan thoiHan = repo.findOne(predicateFindOne(id));

		if (thoiHan != null) {
			thoiHan.setDaXoa(true);
		}

		return thoiHan;
	}

	public boolean checkExistsData(ThoiHanRepository repo, ThoiHan body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QThoiHan.thoiHan.id.ne(body.getId()));
		}

		predAll = predAll.and(QThoiHan.thoiHan.soNgay.eq(body.getSoNgay())
				.and(QThoiHan.thoiHan.loaiThoiHanEnum.eq(body.getLoaiThoiHanEnum())));
		ThoiHan thoiHan = repo.findOne(predAll);

		return thoiHan != null ? true : false;
	}

}
