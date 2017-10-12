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
import vn.greenglobal.tttp.enums.LinhVucThanhTraEnum;
import vn.greenglobal.tttp.enums.LoaiHinhThanhTraEnum;
import vn.greenglobal.tttp.enums.TienDoThanhTraEnum;
import vn.greenglobal.tttp.model.CuocThanhTra;
import vn.greenglobal.tttp.model.QCuocThanhTra;
import vn.greenglobal.tttp.repository.CuocThanhTraRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class CuocThanhTraService {

	@Autowired
	private CuocThanhTraRepository cuocThanhTraRepository;

	BooleanExpression base = QCuocThanhTra.cuocThanhTra.daXoa.eq(false);

	public Predicate predicateFindAll(Integer namThanhTra, String soQuyetDinhKeHoach, String tenDoiTuongThanhTra, String soQuyetDinh, String loaiHinhThanhTra,
			String linhVucThanhTra, String tienDoThanhTra, String tuNgay, String denNgay, Long donViId) {
		BooleanExpression predAll = base;
		if (donViId != null && donViId > 0) {
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.donVi.id.eq(donViId));
		}
		
		if (namThanhTra != null && namThanhTra > 0) {
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.namThanhTra.eq(namThanhTra));
		}
		
		if (soQuyetDinhKeHoach != null && StringUtils.isNotBlank(soQuyetDinhKeHoach.trim())) {
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.isNotNull()
					.and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.quyetDinhPheDuyetKTTT.containsIgnoreCase(soQuyetDinhKeHoach)));
		}
		
		if (tenDoiTuongThanhTra != null && StringUtils.isNotBlank(tenDoiTuongThanhTra.trim())) {
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.doiTuongThanhTra.ten.containsIgnoreCase(tenDoiTuongThanhTra));
		}
		
		if (soQuyetDinh != null && StringUtils.isNotBlank(soQuyetDinh.trim())) {
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.soQuyetDinhVeViecThanhTra.containsIgnoreCase(soQuyetDinh.trim()));
		}
		
		if (loaiHinhThanhTra != null && StringUtils.isNotBlank(loaiHinhThanhTra.trim())) {
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.loaiHinhThanhTra.eq(LoaiHinhThanhTraEnum.valueOf(loaiHinhThanhTra.trim())));
		}
		
		if (linhVucThanhTra != null && StringUtils.isNotBlank(linhVucThanhTra.trim())) {
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.linhVucThanhTra.eq(LinhVucThanhTraEnum.valueOf(linhVucThanhTra.trim())));
		}
		
		if (tienDoThanhTra != null && StringUtils.isNotBlank(tienDoThanhTra.trim())) {
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.tienDoThanhTra.eq(TienDoThanhTraEnum.valueOf(tienDoThanhTra.trim())));
		}
		
		if (tuNgay != null && denNgay != null && StringUtils.isNotBlank(tuNgay.trim())
				&& StringUtils.isNotBlank(denNgay.trim())) {
			LocalDateTime tuNgaySearch = Utils.fixTuNgay(tuNgay);
			LocalDateTime denNgaySearch = Utils.fixDenNgay(denNgay);
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.between(tuNgaySearch, denNgaySearch)
					.or(QCuocThanhTra.cuocThanhTra.ngayBanHanhQuyetDinhXuLy.between(tuNgaySearch, denNgaySearch))
					.or(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.isNotNull().and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.ngayRaQuyetDinh.between(tuNgaySearch, denNgaySearch))));
		} else if (StringUtils.isBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
			LocalDateTime denNgaySearch = Utils.fixDenNgay(denNgay);
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.before(denNgaySearch)
					.or(QCuocThanhTra.cuocThanhTra.ngayBanHanhQuyetDinhXuLy.before(denNgaySearch))
					.or(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.isNotNull().and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.ngayRaQuyetDinh.before(denNgaySearch))));
		} else if (StringUtils.isNotBlank(tuNgay) && StringUtils.isBlank(denNgay)) {
			LocalDateTime tuNgaySearch = Utils.fixTuNgay(tuNgay);
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.ngayRaQuyetDinh.after(tuNgaySearch)
					.or(QCuocThanhTra.cuocThanhTra.ngayBanHanhQuyetDinhXuLy.after(tuNgaySearch))
					.or(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.isNotNull().and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.ngayRaQuyetDinh.before(tuNgaySearch))));
		}

		return predAll;
	}
	
	public Predicate predicateFindThanhTraTrungResult(List<Long> cuocThanhTraIds, String tenDoiTuongThanhTra, String soQuyetDinh) {
		BooleanExpression predAll = base;
		predAll = predAll.and(QCuocThanhTra.cuocThanhTra.daXoa.eq(false))
				.and(QCuocThanhTra.cuocThanhTra.id.in(cuocThanhTraIds));
		
		if (tenDoiTuongThanhTra != null && StringUtils.isNotBlank(tenDoiTuongThanhTra.trim())) {
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.doiTuongThanhTra.ten.containsIgnoreCase(tenDoiTuongThanhTra));
		}
		
		if (soQuyetDinh != null && StringUtils.isNotBlank(soQuyetDinh.trim())) {
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.quyetDinhPheDuyetKTTT.containsIgnoreCase(soQuyetDinh));
		}
	
		return predAll;
	}
			
	public Predicate predicateFindThanhTraTrung(int namThanhTra) {
		BooleanExpression predAll = base;
		predAll = predAll.and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.isNotNull())
				.and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.daXoa.eq(false))
				.and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.namThanhTra.eq(namThanhTra));
		return predAll;
	}

	public Predicate predicateFindAllByKeHoachThanhTra(Long keHoachThanhTraId) {
		BooleanExpression predAll = base;

		if (keHoachThanhTraId != null && keHoachThanhTraId > 0) {
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.keHoachThanhTra.id.eq(keHoachThanhTraId));
		}

		return predAll;
	}
	
	public Predicate predicateFindAllByDoiTuongThanhTra(Integer namThanhTra, Long doiTuongThanhTraId) {
		BooleanExpression predAll = base;
		if (doiTuongThanhTraId != null && doiTuongThanhTraId > 0) {
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.namThanhTra.eq(namThanhTra));
			predAll = predAll.and(QCuocThanhTra.cuocThanhTra.doiTuongThanhTra.id.eq(doiTuongThanhTraId)
					.or(QCuocThanhTra.cuocThanhTra.doiTuongThanhTras.any().id.eq(doiTuongThanhTraId)));
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

	public CuocThanhTra save(CuocThanhTra obj, Long congChucId) {
		return Utils.save(cuocThanhTraRepository, obj, congChucId);
	}

	public ResponseEntity<Object> doSave(CuocThanhTra obj, Long congChucId, PersistentEntityResourceAssembler eass,
			HttpStatus status) {
		return Utils.doSave(cuocThanhTraRepository, obj, congChucId, eass, status);
	}
}
