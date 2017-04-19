package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;

public class SoTiepCongDanService {
	private static Log log = LogFactory.getLog(SoTiepCongDanService.class);
	
	public Predicate predicateFindOne(Long id) {
		return QSoTiepCongDan.soTiepCongDan.daXoa.eq(false).and(QSoTiepCongDan.soTiepCongDan.id.eq(id));
	}
	
	public Predicate predicateFindAllTCD(String tuKhoa, String phanLoaiDon, String huongXuLy, String tuNgay, String denNgay, String loaiTiepCongDan, boolean thanhLapDon) {
		BooleanExpression predAll = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false)
				.and(QSoTiepCongDan.soTiepCongDan.don.thanhLapDon.eq(thanhLapDon));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			log.info("-- tuKhoa : " +tuKhoa);
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.donCongDans.any().congDan.hoVaTen.containsIgnoreCase(tuKhoa)
					.or(QSoTiepCongDan.soTiepCongDan.don.donCongDans.any().congDan.soCMNDHoChieu.containsIgnoreCase(tuKhoa)));
		}
		
		if (phanLoaiDon != null && !"".equals(phanLoaiDon)) {
			predAll = predAll
					.and(QSoTiepCongDan.soTiepCongDan.don.loaiDon.stringValue().containsIgnoreCase(phanLoaiDon));
		}
		if (huongXuLy != null && !"".equals(huongXuLy)) {
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.huongXuLy.stringValue().containsIgnoreCase(huongXuLy));
		}
		if (loaiTiepCongDan != null && !"".equals(loaiTiepCongDan)) {
			predAll = predAll
					.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.stringValue().containsIgnoreCase(loaiTiepCongDan));
		}
		if (tuNgay != null && denNgay != null) {
			LocalDateTime dtTuNgay = LocalDateTime.parse(tuNgay, formatter);
			LocalDateTime dtDenNgay = LocalDateTime.parse(denNgay, formatter);
			log.info("-- dtTuNgay : " + dtTuNgay);
			log.info("-- dtDenNgay : " + dtDenNgay);
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepNhan.between(dtTuNgay, dtDenNgay));
		} else {
			if (tuNgay != null) {
				LocalDateTime dtTuNgay = LocalDateTime.parse(tuNgay, formatter);
				log.info("-- dtTuNgay : " + dtTuNgay);
				predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepNhan.after(dtTuNgay));
			}
			if (denNgay != null) {
				LocalDateTime dtDenNgay = LocalDateTime.parse(denNgay, formatter);
				log.info("-- dtTuNgay : " + dtDenNgay);
				predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepNhan.before(dtDenNgay));
			}
		}
		
		return predAll;
	}

	public boolean isExists(SoTiepCongDanRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false).and(QSoTiepCongDan.soTiepCongDan.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public SoTiepCongDan deleteSoTiepCongDan(SoTiepCongDanRepository repo, Long id) {
		SoTiepCongDan stCongDan = null;
		if (isExists(repo, id)) {
			stCongDan = new SoTiepCongDan();
			stCongDan.setId(id);
			stCongDan.setDaXoa(true);
		}
		return stCongDan;
	}
}
