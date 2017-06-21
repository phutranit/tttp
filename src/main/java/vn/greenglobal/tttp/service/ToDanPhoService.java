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

import vn.greenglobal.tttp.model.CongDan;
import vn.greenglobal.tttp.model.QCongDan;
import vn.greenglobal.tttp.model.QToDanPho;
import vn.greenglobal.tttp.model.ToDanPho;
import vn.greenglobal.tttp.repository.CongDanRepository;
import vn.greenglobal.tttp.repository.ToDanPhoRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class ToDanPhoService {
	
	@Autowired
	private ToDanPhoRepository toDanPhoRepository;

	BooleanExpression base = QToDanPho.toDanPho.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa, Long donViHanhChinh) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QToDanPho.toDanPho.ten.containsIgnoreCase(tuKhoa.trim())
					.or(QToDanPho.toDanPho.moTa.containsIgnoreCase(tuKhoa.trim())));
		}

		if (donViHanhChinh != null && donViHanhChinh > 0) {
			predAll = predAll.and(QToDanPho.toDanPho.donViHanhChinh.id.eq(donViHanhChinh));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QToDanPho.toDanPho.id.eq(id));
	}

	public boolean isExists(ToDanPhoRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QToDanPho.toDanPho.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public ToDanPho delete(ToDanPhoRepository repo, Long id) {
		ToDanPho toDanPho = repo.findOne(predicateFindOne(id));

		if (toDanPho != null) {
			toDanPho.setDaXoa(true);
		}

		return toDanPho;
	}

	public boolean checkExistsData(ToDanPhoRepository repo, ToDanPho body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QToDanPho.toDanPho.id.ne(body.getId()));
		}

		predAll = predAll.and(QToDanPho.toDanPho.ten.eq(body.getTen()));
		predAll = predAll.and(QToDanPho.toDanPho.donViHanhChinh.id.eq(body.getDonViHanhChinh().getId()));
		List<ToDanPho> toDanPhos = (List<ToDanPho>) repo.findAll(predAll);

		return toDanPhos != null && toDanPhos.size() > 0 ? true : false;
	}

	public boolean checkUsedData(CongDanRepository congDanRepository, Long id) {
		List<CongDan> congDanList = (List<CongDan>) congDanRepository
				.findAll(QCongDan.congDan.daXoa.eq(false).and(QCongDan.congDan.toDanPho.id.eq(id)));

		if (congDanList != null && congDanList.size() > 0) {
			return true;
		}

		return false;
	}
	
	public ToDanPho save(ToDanPho obj, Long congChucId) {
		return Utils.save(toDanPhoRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(ToDanPho obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(toDanPhoRepository, obj, congChucId, eass, status);		
	}

}
