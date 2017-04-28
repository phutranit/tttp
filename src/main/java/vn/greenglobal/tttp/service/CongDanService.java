package vn.greenglobal.tttp.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CongDan;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.QCongDan;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.repository.CongDanRepository;
import vn.greenglobal.tttp.repository.DonCongDanRepository;

@Component
public class CongDanService {
	
	BooleanExpression base = QCongDan.congDan.daXoa.eq(false);
	
	public Predicate predicateFindAll(String tuKhoa, Long tinhThanh, Long quanHuyen, 
			Long phuongXa, Long toDanPho) {
		BooleanExpression predAll = base;
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
	
	public Predicate predicateFindCongDanBySuggests(String tuKhoa, String soCMND, String diaChi) {
		BooleanExpression predAll = base;
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

	public Predicate predicateFindOne(Long id) {
		return base.and(QCongDan.congDan.id.eq(id));
	}

	public boolean isExists(CongDanRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QCongDan.congDan.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public CongDan delete(CongDanRepository repo, Long id) {
		CongDan congDan = repo.findOne(predicateFindOne(id));

		if (congDan != null) {
			congDan.setDaXoa(true);
		}

		return congDan;
	}
	
	public boolean checkUsedData(DonCongDanRepository donCongDanRepository, Long id) {
		List<Don_CongDan> donCongDanList = (List<Don_CongDan>) donCongDanRepository
				.findAll(QDon_CongDan.don_CongDan.daXoa.eq(false).and(QDon_CongDan.don_CongDan.congDan.id.eq(id)));

		if (donCongDanList != null && donCongDanList.size() > 0) {
			return true;
		}

		return false;
	}

}
