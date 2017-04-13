package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.ChucVu;
import vn.greenglobal.tttp.model.QChucVu;
import vn.greenglobal.tttp.repository.ChucVuRepository;

public class ChucVuService {
	
	public Predicate predicateFindAll(String ten) {
		BooleanExpression predAll = QChucVu.chucVu.daXoa.eq(false);
		if (ten != null && !"".equals(ten)) {
			predAll = predAll.and(QChucVu.chucVu.ten.eq(ten));
		}		
		return predAll;
	}
	
	public Predicate predicateFindOne(Long id) {
		return QChucVu.chucVu.daXoa.eq(false)
				.and(QChucVu.chucVu.id.eq(id));
	}
	
	public boolean isExists(ChucVuRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QChucVu.chucVu.daXoa.eq(false)
					.and(QChucVu.chucVu.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}
	
	public ChucVu deleteVuViec(ChucVuRepository repo, Long id) {
		ChucVu chucVu = null;
		if (isExists(repo, id)) {
			chucVu = new ChucVu();
			chucVu.setId(id);
			chucVu.setDaXoa(true);
		}
		return chucVu;
	}
	
	public boolean checkExistsData(ChucVuRepository repo, String ten) {
		ChucVu chucVu = repo.findOne(QChucVu.chucVu.daXoa.eq(false)
				.and(QChucVu.chucVu.ten.eq(ten)));
		return chucVu != null ? true : false;
	}
	
}
