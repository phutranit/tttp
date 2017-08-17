package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.KeHoachThanhTra;
import vn.greenglobal.tttp.model.QKeHoachThanhTra;
import vn.greenglobal.tttp.repository.KeHoachThanhTraRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class KeHoachThanhTraService {
	
	@Autowired
	private KeHoachThanhTraRepository keHoachThanhTraRepository;

	BooleanExpression base = QKeHoachThanhTra.keHoachThanhTra.daXoa.eq(false);
	
	public Predicate predicateFindAll(String soQuyetDinh, String tuNgay, String denNgay) {
		BooleanExpression predAll = base;
		
		if (soQuyetDinh != null && StringUtils.isNotBlank(soQuyetDinh.trim())) {
			predAll = predAll.and(QKeHoachThanhTra.keHoachThanhTra.soQuyetDinh.containsIgnoreCase(soQuyetDinh.trim()));
		}
		
		if (tuNgay != null && denNgay != null && StringUtils.isNotBlank(tuNgay.trim())
				&& StringUtils.isNotBlank(denNgay.trim())) {
			LocalDateTime tuNgaySearch = Utils.fixTuNgay(tuNgay);
			LocalDateTime denNgaySearch = Utils.fixDenNgay(denNgay);
			predAll = predAll.and(QKeHoachThanhTra.keHoachThanhTra.ngayRaQuyetDinh.between(tuNgaySearch, denNgaySearch));
		} else if (StringUtils.isBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
			LocalDateTime denNgaySearch = Utils.fixDenNgay(denNgay);
			predAll = predAll.and(QKeHoachThanhTra.keHoachThanhTra.ngayRaQuyetDinh.before(denNgaySearch));
		} else if (StringUtils.isNotBlank(tuNgay) && StringUtils.isBlank(denNgay)) {
			LocalDateTime tuNgaySearch = Utils.fixTuNgay(tuNgay);
			predAll = predAll.and(QKeHoachThanhTra.keHoachThanhTra.ngayRaQuyetDinh.after(tuNgaySearch));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QKeHoachThanhTra.keHoachThanhTra.id.eq(id));
	}

	public boolean isExists(KeHoachThanhTraRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QKeHoachThanhTra.keHoachThanhTra.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public KeHoachThanhTra delete(KeHoachThanhTraRepository repo, Long id) {
		KeHoachThanhTra keHoachThanhTra = repo.findOne(predicateFindOne(id));

		if (keHoachThanhTra != null) {
			keHoachThanhTra.setDaXoa(true);
		}

		return keHoachThanhTra;
	}

	public boolean checkExistsData(KeHoachThanhTraRepository repo, KeHoachThanhTra body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QKeHoachThanhTra.keHoachThanhTra.id.ne(body.getId()));
		}

		predAll = predAll.and(QKeHoachThanhTra.keHoachThanhTra.soQuyetDinh.eq(body.getSoQuyetDinh()));
		List<KeHoachThanhTra> doiTuongThanhTras = (List<KeHoachThanhTra>) repo.findAll(predAll);

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
	
	public KeHoachThanhTra save(KeHoachThanhTra obj, Long congChucId) {
		return Utils.save(keHoachThanhTraRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(KeHoachThanhTra obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(keHoachThanhTraRepository, obj, congChucId, eass, status);		
	}
}
