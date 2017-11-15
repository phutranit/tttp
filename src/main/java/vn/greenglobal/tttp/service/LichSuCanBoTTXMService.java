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
import vn.greenglobal.tttp.model.LichSuCanBoTTXM;
import vn.greenglobal.tttp.model.QLichSuCanBoTTXM;
import vn.greenglobal.tttp.repository.LichSuCanBoTTXMRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class LichSuCanBoTTXMService {
	
	@Autowired
	private LichSuCanBoTTXMRepository lichSuCanBoRepo;	
	
	BooleanExpression base = QLichSuCanBoTTXM.lichSuCanBoTTXM.daXoa.eq(false);
	
	public Predicate predicateFindAll(Long donId, Long donViId) {
		BooleanExpression predAll = base.and(QLichSuCanBoTTXM.lichSuCanBoTTXM.don.id.eq(donId))
				.and(QLichSuCanBoTTXM.lichSuCanBoTTXM.donVi.id.eq(donViId));
		return predAll;
	}
	
	public Predicate predicateFindOne(Long donId, Long donViId, CongChuc congChuc) {
		BooleanExpression predAll = base.and(QLichSuCanBoTTXM.lichSuCanBoTTXM.don.id.eq(donId))
				.and(QLichSuCanBoTTXM.lichSuCanBoTTXM.canBoXuLy.eq(congChuc))
				.and(QLichSuCanBoTTXM.lichSuCanBoTTXM.donVi.id.eq(donViId));
		return predAll;
	}
	
	public void saveLichSuCanBoXuLy(Don don, CongChuc congChuc, CoQuanQuanLy donViXuLy) {
		LichSuCanBoTTXM ls = lichSuCanBoRepo.findOne(predicateFindOne(don.getId(), donViXuLy.getId(), congChuc));
		if (ls == null) {
			LichSuCanBoTTXM lichSuQTXLD = new LichSuCanBoTTXM(congChuc, donViXuLy, don);
			save(lichSuQTXLD, congChuc.getId());
		}		
	}

	public LichSuCanBoTTXM save(LichSuCanBoTTXM obj, Long congChucId) {
		return Utils.save(lichSuCanBoRepo, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(LichSuCanBoTTXM obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(lichSuCanBoRepo, obj, congChucId, eass, status);		
	}
}
