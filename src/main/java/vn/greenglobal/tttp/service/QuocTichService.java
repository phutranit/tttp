package vn.greenglobal.tttp.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CongDan;
import vn.greenglobal.tttp.model.QCongDan;
import vn.greenglobal.tttp.model.QQuocTich;
import vn.greenglobal.tttp.model.QuocTich;
import vn.greenglobal.tttp.repository.CongDanRepository;
import vn.greenglobal.tttp.repository.QuocTichRepository;

@Component
public class QuocTichService {

	BooleanExpression base = QQuocTich.quocTich.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QQuocTich.quocTich.ma.containsIgnoreCase(tuKhoa.trim())
					.or(QQuocTich.quocTich.ten.containsIgnoreCase(tuKhoa.trim()))
					.or(QQuocTich.quocTich.moTa.containsIgnoreCase(tuKhoa.trim())));
		}
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QQuocTich.quocTich.id.eq(id));
	}

	public boolean isExists(QuocTichRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QQuocTich.quocTich.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public QuocTich delete(QuocTichRepository repo, Long id) {
		QuocTich quocTich = repo.findOne(predicateFindOne(id));

		if (quocTich != null) {
			quocTich.setDaXoa(true);
		}

		return quocTich;
	}

	public boolean checkExistsData(QuocTichRepository repo, QuocTich body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QQuocTich.quocTich.id.ne(body.getId()));
		}

		predAll = predAll.and(QQuocTich.quocTich.ten.eq(body.getTen()));
		QuocTich quocTich = repo.findOne(predAll);

		return quocTich != null ? true : false;
	}

	public boolean checkUsedData(CongDanRepository congDanRepository, Long id) {
		List<CongDan> congDanList = (List<CongDan>) congDanRepository
				.findAll(QCongDan.congDan.daXoa.eq(false).and(QCongDan.congDan.quocTich.id.eq(id)));

		if (congDanList != null && congDanList.size() > 0) {
			return true;
		}

		return false;
	}

}
