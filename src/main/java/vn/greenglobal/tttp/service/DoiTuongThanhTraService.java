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
import vn.greenglobal.tttp.model.QDoiTuongThanhTra;
import vn.greenglobal.tttp.repository.DoiTuongThanhTraRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class DoiTuongThanhTraService {
	
	@Autowired
	private DoiTuongThanhTraRepository doiTuongThanhTraRepository;

	BooleanExpression base = QDoiTuongThanhTra.doiTuongThanhTra.daXoa.eq(false);
	
	public Predicate predicateFindAll(String tuKhoa) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QDoiTuongThanhTra.doiTuongThanhTra.ten.containsIgnoreCase(tuKhoa.trim()));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QDoiTuongThanhTra.doiTuongThanhTra.id.eq(id));
	}

	public boolean isExists(DoiTuongThanhTraRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QDoiTuongThanhTra.doiTuongThanhTra.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public DoiTuongThanhTra delete(DoiTuongThanhTraRepository repo, Long id) {
		DoiTuongThanhTra doiTuongThanhTra = repo.findOne(predicateFindOne(id));

		if (doiTuongThanhTra != null) {
			doiTuongThanhTra.setDaXoa(true);
		}

		return doiTuongThanhTra;
	}

	public boolean checkExistsData(DoiTuongThanhTraRepository repo, DoiTuongThanhTra body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QDoiTuongThanhTra.doiTuongThanhTra.id.ne(body.getId()));
		}

		predAll = predAll.and(QDoiTuongThanhTra.doiTuongThanhTra.ten.eq(body.getTen()));
		List<DoiTuongThanhTra> doiTuongThanhTras = (List<DoiTuongThanhTra>) repo.findAll(predAll);

		return doiTuongThanhTras != null && doiTuongThanhTras.size() > 0 ? true : false;
	}

//	public boolean checkUsedData(DonRepository donRepository, Long id) {
//		List<Don> donList = (List<Don>) donRepository.findAll(QDon.don.daXoa.eq(false)
//				.and(QDon.don.linhVucDonThu.id.eq(id)).or(QDon.don.linhVucDonThuChiTiet.id.eq(id))
//				.or(QDon.don.chiTietLinhVucDonThuChiTiet.id.eq(id)));
//
//		if ((linhVucDonThuList != null && linhVucDonThuList.size() > 0) || (donList != null && donList.size() > 0)
//				|| (donList != null && donList.size() > 0)) {
//			return true;
//		}
//
//		return false;
//	}
	
	public DoiTuongThanhTra save(DoiTuongThanhTra obj, Long congChucId) {
		return Utils.save(doiTuongThanhTraRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(DoiTuongThanhTra obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(doiTuongThanhTraRepository, obj, congChucId, eass, status);		
	}
}
