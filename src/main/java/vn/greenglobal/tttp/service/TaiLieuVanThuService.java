package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.BuocGiaiQuyetEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.model.QTaiLieuVanThu;
import vn.greenglobal.tttp.model.TaiLieuVanThu;
import vn.greenglobal.tttp.repository.TaiLieuVanThuRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class TaiLieuVanThuService {
	
	@Autowired
	private TaiLieuVanThuRepository taiLieuVanThuRepository;

	BooleanExpression base = QTaiLieuVanThu.taiLieuVanThu.daXoa.eq(false);

	public Predicate predicateFindAll(Long donId, String loaiQuyTrinh, String buocGiaiQuyet) {
		BooleanExpression predAll = base;
		
		if (donId != null && donId > 0) {
			predAll = predAll.and(QTaiLieuVanThu.taiLieuVanThu.don.id.eq(donId));
		}
		
		if (loaiQuyTrinh != null && !"".equals(loaiQuyTrinh.trim())) {
			predAll =predAll.and(QTaiLieuVanThu.taiLieuVanThu.loaiQuyTrinh.eq(ProcessTypeEnum.valueOf(loaiQuyTrinh.trim())));
		}
		
		if (buocGiaiQuyet != null && !"".equals(buocGiaiQuyet.trim())) {
			predAll = predAll.and(QTaiLieuVanThu.taiLieuVanThu.buocGiaiQuyet.eq(BuocGiaiQuyetEnum.valueOf(buocGiaiQuyet.trim())));
		}
		
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QTaiLieuVanThu.taiLieuVanThu.id.eq(id));
	}

	public boolean isExists(TaiLieuVanThuRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QTaiLieuVanThu.taiLieuVanThu.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public TaiLieuVanThu delete(TaiLieuVanThuRepository repo, Long id) {
		TaiLieuVanThu taiLieuVanThu = repo.findOne(predicateFindOne(id));

		if (taiLieuVanThu != null) {
			taiLieuVanThu.setDaXoa(true);
		}

		return taiLieuVanThu;
	}

	public boolean checkExistsData(TaiLieuVanThuRepository repo, TaiLieuVanThu body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QTaiLieuVanThu.taiLieuVanThu.id.ne(body.getId()));
		}

		predAll = predAll.and(QTaiLieuVanThu.taiLieuVanThu.ten.eq(body.getTen()));
		TaiLieuVanThu taiLieuVanThu = repo.findOne(predAll);

		return taiLieuVanThu != null ? true : false;
	}
	
	public TaiLieuVanThu save(TaiLieuVanThu obj, Long congChucId) {
		return Utils.save(taiLieuVanThuRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(TaiLieuVanThu obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(taiLieuVanThuRepository, obj, congChucId, eass, status);		
	}

}
