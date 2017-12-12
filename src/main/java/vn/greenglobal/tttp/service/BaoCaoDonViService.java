package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.KyBaoCaoTongHopEnum;
import vn.greenglobal.tttp.model.BaoCaoDonVi;
import vn.greenglobal.tttp.model.QBaoCaoDonVi;
import vn.greenglobal.tttp.repository.BaoCaoDonViRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class BaoCaoDonViService {
	
	@Autowired
	private BaoCaoDonViRepository baoCaoDonViRepository;

	BooleanExpression base = QBaoCaoDonVi.baoCaoDonVi.daXoa.eq(false);

	public Predicate predicateFindAll(String loaiKy, int quy, int nam, int thang, Long donViXuLy) {
		BooleanExpression predAll = base;
		
		if (nam > 0) {
			predAll = predAll.and(QBaoCaoDonVi.baoCaoDonVi.baoCaoTongHop.namBaoCao.eq(nam));
		}
		
		if (loaiKy != null) {
			KyBaoCaoTongHopEnum loaiKyEnum = KyBaoCaoTongHopEnum.valueOf(loaiKy);
			predAll = predAll.and(QBaoCaoDonVi.baoCaoDonVi.baoCaoTongHop.kyBaoCao.eq(loaiKyEnum));
			
			if (loaiKyEnum.equals(KyBaoCaoTongHopEnum.THEO_QUY)) {
				predAll = predAll.and(QBaoCaoDonVi.baoCaoDonVi.baoCaoTongHop.quyBaoCao.eq(quy));
			}
			if (loaiKyEnum.equals(KyBaoCaoTongHopEnum.THEO_THANG)) {
				predAll = predAll.and(QBaoCaoDonVi.baoCaoDonVi.baoCaoTongHop.thangBaoCao.eq(thang));
			}
		}
		
		if (donViXuLy != null && donViXuLy > 0) {
			predAll = predAll.and(QBaoCaoDonVi.baoCaoDonVi.donVi.id.eq(donViXuLy));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QBaoCaoDonVi.baoCaoDonVi.id.eq(id));
	}
	
	public BaoCaoDonVi save(BaoCaoDonVi obj, Long congChucId) {
		return Utils.save(baoCaoDonViRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(BaoCaoDonVi obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(baoCaoDonViRepository, obj, congChucId, eass, status);		
	}

}
