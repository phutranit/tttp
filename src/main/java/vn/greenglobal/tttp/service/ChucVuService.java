package vn.greenglobal.tttp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.ChucVu;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.QChucVu;
import vn.greenglobal.tttp.model.QCongChuc;
import vn.greenglobal.tttp.repository.ChucVuRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;

@Component
public class ChucVuService {

	@Autowired
	private ChucVuRepository repo;
	
	BooleanExpression base = QChucVu.chucVu.daXoa.eq(false);

	public Predicate predicateFindAll(String ten) {
		BooleanExpression predAll = base;
		if (ten != null && StringUtils.isNotBlank(ten.trim())) {
			predAll = predAll.and(QChucVu.chucVu.ten.containsIgnoreCase(ten.trim())
					.or(QChucVu.chucVu.moTa.containsIgnoreCase(ten.trim())));
		}
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QChucVu.chucVu.id.eq(id));
	}
	
	public Page<ChucVu> findAll(String ten, Pageable pageable) {
		return repo.findAll(predicateFindAll(ten), pageable);
	}
	
	public List<ChucVu> findAll(String ten) {
		return (List<ChucVu>) repo.findAll(predicateFindAll(ten));
	}
	
	public List<ChucVu> findAll() {
		return (List<ChucVu>) repo.findAll();
	}

	public boolean isExists(ChucVuRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QChucVu.chucVu.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public ChucVu delete(ChucVuRepository repo, Long id) {
		ChucVu chucVu = repo.findOne(predicateFindOne(id));

		if (chucVu != null) {
			chucVu.setDaXoa(true);
		}

		return chucVu;
	}

	public boolean checkExistsData(ChucVuRepository repo, ChucVu body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QChucVu.chucVu.id.ne(body.getId()));
		}

		predAll = predAll.and(QChucVu.chucVu.ten.eq(body.getTen()));
		ChucVu chucVu = repo.findOne(predAll);

		return chucVu != null ? true : false;
	}

	public boolean checkUsedData(CongChucRepository congChucRepository, Long id) {
		List<CongChuc> congChucList = (List<CongChuc>) congChucRepository
				.findAll(QCongChuc.congChuc.daXoa.eq(false).and(QCongChuc.congChuc.chucVu.id.eq(id)));

		if (congChucList != null && congChucList.size() > 0) {
			return true;
		}

		return false;
	}

}
