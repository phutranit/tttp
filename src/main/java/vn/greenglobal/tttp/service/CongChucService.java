package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.QCongChuc;
import vn.greenglobal.tttp.repository.CongChucRepository;

public class CongChucService {

	public Predicate predicateFindAll(String tuKhoa) {
		BooleanExpression predAll = QCongChuc.congChuc.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QCongChuc.congChuc.hoVaTen.containsIgnoreCase(tuKhoa));
		}
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return QCongChuc.congChuc.daXoa.eq(false)
				.and(QCongChuc.congChuc.id.eq(id));
	}

	public boolean isExists(CongChucRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QCongChuc.congChuc.daXoa.eq(false)
					.and(QCongChuc.congChuc.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public CongChuc deleteCongChuc(CongChucRepository repo, Long id) {
		CongChuc congChuc = repo.findOne(predicateFindOne(id));
		
		if (congChuc != null) {
			congChuc.setDaXoa(true);
		}

		return congChuc;
	}
	
	public boolean checkExistsData(CongChucRepository repo, CongChuc body) {
		BooleanExpression predAll = QCongChuc.congChuc.daXoa.eq(false);

		if (!body.isNew()) {
			predAll = predAll.and(QCongChuc.congChuc.id.ne(body.getId()));
		}

		predAll = predAll.and(QCongChuc.congChuc.nguoiDung.tenDangNhap.eq(body.getNguoiDung().getTenDangNhap()));
		CongChuc congChuc = repo.findOne(predAll);

		return congChuc != null ? true : false;
	}
}
