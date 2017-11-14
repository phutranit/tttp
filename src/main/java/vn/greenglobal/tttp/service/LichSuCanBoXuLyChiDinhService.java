package vn.greenglobal.tttp.service;

import java.util.List;

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
import vn.greenglobal.tttp.model.LichSuCanBoXuLyChiDinh;
import vn.greenglobal.tttp.model.QLichSuCanBoXuLyChiDinh;
import vn.greenglobal.tttp.repository.LichSuCanBoXuLyChiDinhRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class LichSuCanBoXuLyChiDinhService {
	
	@Autowired
	private LichSuCanBoXuLyChiDinhRepository lichSuCanBoRepo;	
	
	BooleanExpression base = QLichSuCanBoXuLyChiDinh.lichSuCanBoXuLyChiDinh.daXoa.eq(false);
	
	public Predicate predicateFindAll(Long donId, Long donViId) {
		BooleanExpression predAll = base.and(QLichSuCanBoXuLyChiDinh.lichSuCanBoXuLyChiDinh.don.id.eq(donId))
				.and(QLichSuCanBoXuLyChiDinh.lichSuCanBoXuLyChiDinh.donVi.id.eq(donViId));
		return predAll;
	}
	
	public Predicate predicateFindOne(Long donId, Long donViId, CongChuc congChuc) {
		BooleanExpression predAll = base.and(QLichSuCanBoXuLyChiDinh.lichSuCanBoXuLyChiDinh.don.id.eq(donId))
				.and(QLichSuCanBoXuLyChiDinh.lichSuCanBoXuLyChiDinh.canBoChiDinh.eq(congChuc))
				.and(QLichSuCanBoXuLyChiDinh.lichSuCanBoXuLyChiDinh.donVi.id.eq(donViId));
		return predAll;
	}
	
	public void saveLichSuCanBoXuLyChiDinh(Don don, CongChuc congChuc, CoQuanQuanLy donViXuLy, CongChuc congChucBiThuHoi) {
//		if (congChucBiThuHoi != null) {
//			List<LichSuCanBoXuLyChiDinh> listThuHoi = (List<LichSuCanBoXuLyChiDinh>) lichSuCanBoRepo.findAll(predicateFindAll(don.getId(), donViXuLy.getId()));
//			if (listThuHoi != null) {
//				for (LichSuCanBoXuLyChiDinh ls : listThuHoi) {
//					ls.setDaXoa(true);
//					save(ls, congChuc.getId());
//					break;
//				}
//			}
//		}
		LichSuCanBoXuLyChiDinh ls = lichSuCanBoRepo.findOne(predicateFindOne(don.getId(), donViXuLy.getId(), congChuc));
		if (ls == null) {
			LichSuCanBoXuLyChiDinh lichSuQTXLD = new LichSuCanBoXuLyChiDinh(congChuc, donViXuLy, don);
			save(lichSuQTXLD, congChuc.getId());
		}		
	}

	public LichSuCanBoXuLyChiDinh save(LichSuCanBoXuLyChiDinh obj, Long congChucId) {
		return Utils.save(lichSuCanBoRepo, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(LichSuCanBoXuLyChiDinh obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(lichSuCanBoRepo, obj, congChucId, eass, status);		
	}
}
