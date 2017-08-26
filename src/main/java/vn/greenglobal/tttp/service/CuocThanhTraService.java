package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CuocThanhTra;
import vn.greenglobal.tttp.model.QCuocThanhTra;
import vn.greenglobal.tttp.repository.CuocThanhTraRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class CuocThanhTraService {
	
	@Autowired
	private CuocThanhTraRepository cuocThanhTraRepository;

	BooleanExpression base = QCuocThanhTra.cuocThanhTra.daXoa.eq(false);
		
//	public Predicate predicateFindAll(String tuKhoa, Long linhVucId) {
//		BooleanExpression predAll = base;
//		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
//			predAll = predAll.and(QDoiTuongThanhTra.doiTuongThanhTra.ten.containsIgnoreCase(tuKhoa.trim()));
//		}
//		
//		if (linhVucId != null && linhVucId > 0) {
//			predAll = predAll.and(QDoiTuongThanhTra.doiTuongThanhTra.linhVucDoiTuongThanhTra.id.eq(linhVucId));
//		}
//
//		return predAll;
//	}
	
	public Predicate predicateFindAllByKeHoachThanhTra(Long keHoachThanhTraId) {
		BooleanExpression predAll = base;

		if (keHoachThanhTraId != null && keHoachThanhTraId > 0) {
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.id.eq(keHoachThanhTraId));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QCuocThanhTra.cuocThanhTra.id.eq(id));
	}

	public boolean isExists(CuocThanhTraRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QCuocThanhTra.cuocThanhTra.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public CuocThanhTra delete(CuocThanhTraRepository repo, Long id) {
		CuocThanhTra cuocThanhTra = repo.findOne(predicateFindOne(id));

		if (cuocThanhTra != null) {
			cuocThanhTra.setDaXoa(true);
		}

		return cuocThanhTra;
	}

//	public boolean checkExistsData(DoiTuongThanhTraRepository repo, DoiTuongThanhTra body) {
//		BooleanExpression predAll = base;
//
//		if (!body.isNew()) {
//			predAll = predAll.and(QDoiTuongThanhTra.doiTuongThanhTra.id.ne(body.getId()));
//		}
//
//		predAll = predAll.and(QDoiTuongThanhTra.doiTuongThanhTra.ten.eq(body.getTen())
//				.and(QDoiTuongThanhTra.doiTuongThanhTra.linhVucDoiTuongThanhTra.id.eq(body.getLinhVucDoiTuongThanhTra().getId()))
//				.and(QDoiTuongThanhTra.doiTuongThanhTra.loaiDoiTuongThanhTra.eq(LoaiDoiTuongThanhTraEnum.valueOf(body.getLoaiDoiTuongThanhTra().toString()))));
//		List<DoiTuongThanhTra> doiTuongThanhTras = (List<DoiTuongThanhTra>) repo.findAll(predAll);
//
//		return doiTuongThanhTras != null && doiTuongThanhTras.size() > 0 ? true : false;
//	}

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
	
	public CuocThanhTra save(CuocThanhTra obj, Long congChucId) {
		return Utils.save(cuocThanhTraRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(CuocThanhTra obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(cuocThanhTraRepository, obj, congChucId, eass, status);		
	}
}
