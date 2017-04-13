package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.model.ThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.repository.ThamQuyenGiaiQuyetRepository;

public class ThamQuyenGiaiQuyetService {
	
	public Predicate predicateFindAll(String tuKhoa, Long cha) {
		BooleanExpression predAll = QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.ten.containsIgnoreCase(tuKhoa)
					.or(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.moTa.containsIgnoreCase(tuKhoa)));
		}
		
		if (cha != null && cha > 0) {
			predAll = predAll.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.cha.id.eq(cha));
		}
		
		return predAll;
	}
	
	public Predicate predicateFindOne(Long id) {
		return QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.daXoa.eq(false)
				.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.id.eq(id));
	}
	
	public boolean isExists(ThamQuyenGiaiQuyetRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.daXoa.eq(false)
					.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}
	
	public ThamQuyenGiaiQuyet deleteThamQuyenGiaiQuyet(ThamQuyenGiaiQuyetRepository repo, Long id) {
		ThamQuyenGiaiQuyet thamQuyenGiaiQuyet = null;
		if (isExists(repo, id)) {
			thamQuyenGiaiQuyet = new ThamQuyenGiaiQuyet();
			thamQuyenGiaiQuyet.setId(id);
			thamQuyenGiaiQuyet.setDaXoa(true);
		}
		return thamQuyenGiaiQuyet;
	}
	
	public boolean checkExistsData(ThamQuyenGiaiQuyetRepository repo, String ten) {
		ThamQuyenGiaiQuyet thamQuyenGiaiQuyet = repo.findOne(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.daXoa.eq(false)
				.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.ten.eq(ten)));
		return thamQuyenGiaiQuyet != null ? true : false;
	}
	
}
