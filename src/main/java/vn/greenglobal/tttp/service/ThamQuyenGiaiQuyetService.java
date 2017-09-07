package vn.greenglobal.tttp.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.ThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.ThamQuyenGiaiQuyetRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class ThamQuyenGiaiQuyetService {
	
	@Autowired
	private ThamQuyenGiaiQuyetRepository thamQuyenGiaiQuyetRepository;

	BooleanExpression base = QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.tenSearch.containsIgnoreCase(Utils.unAccent(tuKhoa.trim()))
					.or(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.moTaSearch.containsIgnoreCase(Utils.unAccent(tuKhoa.trim()))));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.id.eq(id));
	}

	public boolean isExists(ThamQuyenGiaiQuyetRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public ThamQuyenGiaiQuyet delete(ThamQuyenGiaiQuyetRepository repo, Long id) {
		ThamQuyenGiaiQuyet thamQuyenGiaiQuyet = repo.findOne(predicateFindOne(id));

		if (thamQuyenGiaiQuyet != null) {
			thamQuyenGiaiQuyet.setDaXoa(true);
		}

		return thamQuyenGiaiQuyet;
	}

	public boolean checkExistsData(ThamQuyenGiaiQuyetRepository repo, ThamQuyenGiaiQuyet body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.id.ne(body.getId()));
		}

		predAll = predAll.and(QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.ten.eq(body.getTen()));
		ThamQuyenGiaiQuyet thamQuyenGiaiQuyet = repo.findOne(predAll);

		return thamQuyenGiaiQuyet != null ? true : false;
	}

	public boolean checkUsedData(ThamQuyenGiaiQuyetRepository repo, DonRepository donRepository,
			XuLyDonRepository xuLyDonRepository, Long id) {
		List<Don> donList = (List<Don>) donRepository
				.findAll(QDon.don.daXoa.eq(false).and(QDon.don.thamQuyenGiaiQuyet.id.eq(id)));
		List<XuLyDon> xuLyDonList = (List<XuLyDon>) xuLyDonRepository
				.findAll(QXuLyDon.xuLyDon.daXoa.eq(false).and(QXuLyDon.xuLyDon.thamQuyenGiaiQuyet.id.eq(id)));

		if ((donList != null && donList.size() > 0) || (xuLyDonList != null && xuLyDonList.size() > 0)) {
			return true;
		}

		return false;
	}
	
	public ThamQuyenGiaiQuyet save(ThamQuyenGiaiQuyet obj, Long congChucId) {
		return Utils.save(thamQuyenGiaiQuyetRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(ThamQuyenGiaiQuyet obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(thamQuyenGiaiQuyetRepository, obj, congChucId, eass, status);		
	}

}
