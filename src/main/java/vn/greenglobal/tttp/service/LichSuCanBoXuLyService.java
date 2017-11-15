package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.LichSuCanBoXuLy;
import vn.greenglobal.tttp.model.QLichSuCanBoXuLy;
import vn.greenglobal.tttp.repository.LichSuCanBoXuLyRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class LichSuCanBoXuLyService {
	
	@Autowired
	private LichSuCanBoXuLyRepository lichSuCanBoRepo;	
	
	BooleanExpression base = QLichSuCanBoXuLy.lichSuCanBoXuLy.daXoa.eq(false);
	
	public Predicate predicateFindAll(Long donId, Long donViId) {
		BooleanExpression predAll = base.and(QLichSuCanBoXuLy.lichSuCanBoXuLy.don.id.eq(donId))
				.and(QLichSuCanBoXuLy.lichSuCanBoXuLy.donVi.id.eq(donViId));
		return predAll;
	}
	
	public Predicate predicateFindOne(Long donId, Long donViId, CongChuc congChuc) {
		BooleanExpression predAll = base.and(QLichSuCanBoXuLy.lichSuCanBoXuLy.don.id.eq(donId))
				.and(QLichSuCanBoXuLy.lichSuCanBoXuLy.canBoXuLy.eq(congChuc))
				.and(QLichSuCanBoXuLy.lichSuCanBoXuLy.donVi.id.eq(donViId));
		return predAll;
	}
	
	public void saveLichSuCanBoXuLy(Don don, CongChuc congChuc, CoQuanQuanLy donViXuLy) {
		LichSuCanBoXuLy ls = lichSuCanBoRepo.findOne(predicateFindOne(don.getId(), donViXuLy.getId(), congChuc));
		if (ls == null) {
			LichSuCanBoXuLy lichSuQTXLD = new LichSuCanBoXuLy(congChuc, donViXuLy, don);
			save(lichSuQTXLD, congChuc.getId());
		}		
	}

	public LichSuCanBoXuLy save(LichSuCanBoXuLy obj, Long congChucId) {
		return Utils.save(lichSuCanBoRepo, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(LichSuCanBoXuLy obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(lichSuCanBoRepo, obj, congChucId, eass, status);		
	}
}
