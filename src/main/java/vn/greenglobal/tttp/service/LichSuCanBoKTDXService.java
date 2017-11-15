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
import vn.greenglobal.tttp.model.LichSuCanBoKTDX;
import vn.greenglobal.tttp.model.QLichSuCanBoKTDX;
import vn.greenglobal.tttp.repository.LichSuCanBoKTDXRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class LichSuCanBoKTDXService {
	
	@Autowired
	private LichSuCanBoKTDXRepository lichSuCanBoRepo;	
	
	BooleanExpression base = QLichSuCanBoKTDX.lichSuCanBoKTDX.daXoa.eq(false);
	
	public Predicate predicateFindAll(Long donId, Long donViId) {
		BooleanExpression predAll = base.and(QLichSuCanBoKTDX.lichSuCanBoKTDX.don.id.eq(donId))
				.and(QLichSuCanBoKTDX.lichSuCanBoKTDX.donVi.id.eq(donViId));
		return predAll;
	}
	
	public Predicate predicateFindOne(Long donId, Long donViId, CongChuc congChuc) {
		BooleanExpression predAll = base.and(QLichSuCanBoKTDX.lichSuCanBoKTDX.don.id.eq(donId))
				.and(QLichSuCanBoKTDX.lichSuCanBoKTDX.canBoXuLy.eq(congChuc))
				.and(QLichSuCanBoKTDX.lichSuCanBoKTDX.donVi.id.eq(donViId));
		return predAll;
	}
	
	public void saveLichSuCanBoXuLy(Don don, CongChuc congChuc, CoQuanQuanLy donViXuLy) {
		LichSuCanBoKTDX ls = lichSuCanBoRepo.findOne(predicateFindOne(don.getId(), donViXuLy.getId(), congChuc));
		if (ls == null) {
			LichSuCanBoKTDX lichSuQTXLD = new LichSuCanBoKTDX(congChuc, donViXuLy, don);
			save(lichSuQTXLD, congChuc.getId());
		}		
	}

	public LichSuCanBoKTDX save(LichSuCanBoKTDX obj, Long congChucId) {
		return Utils.save(lichSuCanBoRepo, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(LichSuCanBoKTDX obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(lichSuCanBoRepo, obj, congChucId, eass, status);		
	}
}
