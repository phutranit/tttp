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

import vn.greenglobal.tttp.model.DoiTuongThanhTra;
import vn.greenglobal.tttp.model.LinhVucDoiTuongThanhTra;
import vn.greenglobal.tttp.model.QDoiTuongThanhTra;
import vn.greenglobal.tttp.model.QLinhVucDoiTuongThanhTra;
import vn.greenglobal.tttp.repository.DoiTuongThanhTraRepository;
import vn.greenglobal.tttp.repository.LinhVucDoiTuongThanhTraRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class LinhVucDoiTuongThanhTraService {
	
	@Autowired
	private LinhVucDoiTuongThanhTraRepository linhVucDoiTuongThanhTraRepository;

	BooleanExpression base = QLinhVucDoiTuongThanhTra.linhVucDoiTuongThanhTra.daXoa.eq(false);
	
	public Predicate predicateFindAll(String tuKhoa) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QLinhVucDoiTuongThanhTra.linhVucDoiTuongThanhTra.ten.containsIgnoreCase(tuKhoa.trim())
					.or(QLinhVucDoiTuongThanhTra.linhVucDoiTuongThanhTra.moTa.containsIgnoreCase(tuKhoa.trim())));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QLinhVucDoiTuongThanhTra.linhVucDoiTuongThanhTra.id.eq(id));
	}

	public boolean isExists(LinhVucDoiTuongThanhTraRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QLinhVucDoiTuongThanhTra.linhVucDoiTuongThanhTra.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public LinhVucDoiTuongThanhTra delete(LinhVucDoiTuongThanhTraRepository repo, Long id) {
		LinhVucDoiTuongThanhTra linhVucDoiTuongThanhTra = repo.findOne(predicateFindOne(id));

		if (linhVucDoiTuongThanhTra != null) {
			linhVucDoiTuongThanhTra.setDaXoa(true);
		}

		return linhVucDoiTuongThanhTra;
	}

	public boolean checkExistsData(LinhVucDoiTuongThanhTraRepository repo, LinhVucDoiTuongThanhTra body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QLinhVucDoiTuongThanhTra.linhVucDoiTuongThanhTra.id.ne(body.getId()));
		}

		predAll = predAll.and(QLinhVucDoiTuongThanhTra.linhVucDoiTuongThanhTra.ten.eq(body.getTen()));
		List<LinhVucDoiTuongThanhTra> linhVucDoiTuongThanhTras = (List<LinhVucDoiTuongThanhTra>) repo.findAll(predAll);

		return linhVucDoiTuongThanhTras != null && linhVucDoiTuongThanhTras.size() > 0 ? true : false;
	}

	public boolean checkUsedData(DoiTuongThanhTraRepository doiTuongThanhTraRepository, Long id) {
		List<DoiTuongThanhTra> doiTuongThanhTralist = (List<DoiTuongThanhTra>) doiTuongThanhTraRepository
				.findAll(QDoiTuongThanhTra.doiTuongThanhTra.daXoa.eq(false)
						.and(QDoiTuongThanhTra.doiTuongThanhTra.linhVucDoiTuongThanhTra.id.eq(id)));

		if (doiTuongThanhTralist != null && doiTuongThanhTralist.size() > 0) {
			return true;
		}

		return false;
	}
	
	public LinhVucDoiTuongThanhTra save(LinhVucDoiTuongThanhTra obj, Long congChucId) {
		return Utils.save(linhVucDoiTuongThanhTraRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(LinhVucDoiTuongThanhTra obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(linhVucDoiTuongThanhTraRepository, obj, congChucId, eass, status);		
	}
}
