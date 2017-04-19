package vn.greenglobal.tttp.service;

import org.apache.commons.lang3.StringUtils;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CongDan;
import vn.greenglobal.tttp.model.QCongDan;
import vn.greenglobal.tttp.repository.CongDanRepository;

public class CongDanService {
	
	public Predicate predicateFindCongDanBySuggests(String tuKhoa, String soCMND, String diaChi) {
		BooleanExpression predAll = QCongDan.congDan.daXoa.eq(false);
		if (StringUtils.isNotBlank(tuKhoa)) {
			predAll = predAll.and(QCongDan.congDan.hoVaTen.containsIgnoreCase(tuKhoa));
		}
		if (StringUtils.isNotBlank(soCMND)) {
			predAll = predAll.and(QCongDan.congDan.soCMNDHoChieu.containsIgnoreCase(soCMND));
		}
		if (StringUtils.isNotBlank(diaChi)) {
			predAll = predAll.and(QCongDan.congDan.diaChi.containsIgnoreCase(diaChi));
		}
		return predAll;
	}
	
	public Predicate predicateFindAll(String tuKhoa, Long tinhThanh, Long quanHuyen, 
			Long phuongXa, Long toDanPho) {
		BooleanExpression predAll = QCongDan.congDan.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QCongDan.congDan.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QCongDan.congDan.soDienThoai.containsIgnoreCase(tuKhoa))
					.or(QCongDan.congDan.diaChi.containsIgnoreCase(tuKhoa))
					.or(QCongDan.congDan.soCMNDHoChieu.containsIgnoreCase(tuKhoa)));
		}
		
		if (tinhThanh != null && tinhThanh > 0) {
			predAll = predAll.and(QCongDan.congDan.tinhThanh.id.eq(tinhThanh));
		}
		if (quanHuyen != null && quanHuyen > 0) {
			predAll = predAll.and(QCongDan.congDan.quanHuyen.id.eq(quanHuyen));
		}

		if (phuongXa != null && phuongXa > 0) {
			predAll = predAll.and(QCongDan.congDan.phuongXa.id.eq(phuongXa));
		}

		if (toDanPho != null && toDanPho > 0) {
			predAll = predAll.and(QCongDan.congDan.toDanPho.id.eq(toDanPho));
		}
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return QCongDan.congDan.daXoa.eq(false)
				.and(QCongDan.congDan.id.eq(id));
	}

	public boolean isExists(CongDanRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QCongDan.congDan.daXoa.eq(false)
					.and(QCongDan.congDan.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public CongDan deleteCongDan(CongDanRepository repo, Long id) {
		CongDan congDan = null;
		if (isExists(repo, id)) {
			congDan = new CongDan();
			congDan.setId(id);
			congDan.setDaXoa(true);
		}
		return congDan;
	}

}
