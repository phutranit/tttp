package vn.greenglobal.tttp.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CauHinhBaoCao;
import vn.greenglobal.tttp.model.QCauHinhBaoCao;
import vn.greenglobal.tttp.repository.CauHinhBaoCaoRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class CauHinhBaoCaoService {
	
	@Autowired
	private CauHinhBaoCaoRepository cauHinhBaoCaoRepository;

	BooleanExpression base = QCauHinhBaoCao.cauHinhBaoCao.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QCauHinhBaoCao.cauHinhBaoCao.tenBaoCao.containsIgnoreCase(Utils.unAccent(tuKhoa.trim()))
					.or(QCauHinhBaoCao.cauHinhBaoCao.tenBaoCaoSearch.containsIgnoreCase(Utils.unAccent(tuKhoa.trim()))));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QCauHinhBaoCao.cauHinhBaoCao.id.eq(id));
	}

	public boolean isExists(CauHinhBaoCaoRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QCauHinhBaoCao.cauHinhBaoCao.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public CauHinhBaoCao delete(CauHinhBaoCaoRepository repo, Long id) {
		CauHinhBaoCao CauHinhBaoCao = repo.findOne(predicateFindOne(id));

		if (CauHinhBaoCao != null) {
			CauHinhBaoCao.setDaXoa(true);
		}

		return CauHinhBaoCao;
	}
	
	public CauHinhBaoCao save(CauHinhBaoCao obj, Long congChucId) {
		return Utils.save(cauHinhBaoCaoRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(CauHinhBaoCao obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(cauHinhBaoCaoRepository, obj, congChucId, eass, status);		
	}
}
