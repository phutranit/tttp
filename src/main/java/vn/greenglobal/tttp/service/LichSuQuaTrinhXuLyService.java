package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
import vn.greenglobal.tttp.model.LichSuQuaTrinhXuLy;
import vn.greenglobal.tttp.model.QLichSuQuaTrinhXuLy;
import vn.greenglobal.tttp.repository.LichSuQuaTrinhXuLyRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class LichSuQuaTrinhXuLyService {

	@Autowired
	private LichSuQuaTrinhXuLyRepository lichSuQuaTrinhXuLyRepo;

	BooleanExpression base = QLichSuQuaTrinhXuLy.lichSuQuaTrinhXuLy.daXoa.eq(false);

	public Predicate predicateFindAll(Long donId) {
		BooleanExpression predAll = base.and(QLichSuQuaTrinhXuLy.lichSuQuaTrinhXuLy.don.id.eq(donId));
		return predAll;
	}

	public Predicate predicateFindAll(Long donId, Long donViId) {
		BooleanExpression predAll = base.and(QLichSuQuaTrinhXuLy.lichSuQuaTrinhXuLy.don.id.eq(donId))
				.and(QLichSuQuaTrinhXuLy.lichSuQuaTrinhXuLy.donViXuLy.id.eq(donViId));
		return predAll;
	}
	
	public List<LichSuQuaTrinhXuLy> getDSLichSuQuaTrinhXuLys(LichSuQuaTrinhXuLyRepository repo, Long donId, Long donViId) {
		List<LichSuQuaTrinhXuLy> lichSuList = new ArrayList<LichSuQuaTrinhXuLy>();
		lichSuList.addAll((List<LichSuQuaTrinhXuLy>) repo.findAll(predicateFindAll(donId, donViId)));
		return lichSuList;
	}
	
	public int timThuTuLichSuQuaTrinhXuLyHienTai(LichSuQuaTrinhXuLyRepository repo, Long donId, Long donViId) {
		int thuTu = 0;
		List<LichSuQuaTrinhXuLy> lichSuList = (List<LichSuQuaTrinhXuLy>) repo.findAll(predicateFindAll(donId, donViId));
		if (lichSuList != null) {
			thuTu = lichSuList.size();
		}
		return thuTu;
	}

	public int timThuTuLichSuQuaTrinhXuLyHienTai(Long donId, Long donViId) {
		int thuTu = 0;
		List<LichSuQuaTrinhXuLy> lichSuList = (List<LichSuQuaTrinhXuLy>) lichSuQuaTrinhXuLyRepo
				.findAll(predicateFindAll(donId, donViId));
		if (lichSuList != null) {
			thuTu = lichSuList.size();
		}
		return thuTu;
	}

	public void saveLichSuQuaTrinhXuLy(Don don, CongChuc congChuc, String noiDungXuLy, CoQuanQuanLy donViXuLy,
			String ten) {
		int thuTu = timThuTuLichSuQuaTrinhXuLyHienTai(don.getId(), donViXuLy.getId());
		LichSuQuaTrinhXuLy lichSuQTXLD = new LichSuQuaTrinhXuLy(don, congChuc, LocalDateTime.now(), ten, noiDungXuLy,
				donViXuLy, thuTu);
		save(lichSuQTXLD, congChuc.getId());
	}

	public LichSuQuaTrinhXuLy save(LichSuQuaTrinhXuLy obj, Long congChucId) {
		return Utils.save(lichSuQuaTrinhXuLyRepo, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(LichSuQuaTrinhXuLy obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(lichSuQuaTrinhXuLyRepo, obj, congChucId, eass, status);		
	}
}
