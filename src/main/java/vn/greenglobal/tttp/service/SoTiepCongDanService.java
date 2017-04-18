package vn.greenglobal.tttp.service;

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
	
	public Predicate predicateFindAllTCD(String tuKhoa, boolean thanhLapDon) {
		BooleanExpression predAll = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false);
		
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			log.info("-- tuKhoa : " +tuKhoa);
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.donCongDans.any().congDan.hoVaTen.containsIgnoreCase(tuKhoa)
							.or(QSoTiepCongDan.soTiepCongDan.don.donCongDans.any().congDan.soCMNDHoChieu.eq(tuKhoa)));
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
