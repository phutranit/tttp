package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.model.ThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.repository.ThamQuyenGiaiQuyetRepository;

public class ThamQuyenGiaiQuyetService {
	
	public Predicate predicateFindAll(String ten, String moTa, Long chaId) {
		BooleanExpression predAll = QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.daXoa.eq(false);
		if (ten != null && !"".equals(ten)) {
			predAll = predAll.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.ten.eq(ten));
		}
		
		if (moTa != null && !"".equals(moTa)) {
			predAll = predAll.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.moTa.eq(moTa));
		}
		
		if (chaId != null && chaId > 0) {
			predAll = predAll.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.cha.id.eq(chaId));
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
	
}
