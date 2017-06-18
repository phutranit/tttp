package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.LichSuQuaTrinhXuLy;
import vn.greenglobal.tttp.model.QLichSuQuaTrinhXuLy;
import vn.greenglobal.tttp.repository.LichSuQuaTrinhXuLyRepository;

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
	
	/*public int timThuTuLichSuQuaTrinhXuLyHienTai(LichSuQuaTrinhXuLyRepository repo, Long donId) {
		int thuTu = 0;
		List<LichSuQuaTrinhXuLy> lichSuList = (List<LichSuQuaTrinhXuLy>) repo.findAll(predicateFindAll(donId));
		if (lichSuList != null) {
			thuTu = lichSuList.size();
		}
		return thuTu;
	}*/
	
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
		List<LichSuQuaTrinhXuLy> lichSuList = (List<LichSuQuaTrinhXuLy>) lichSuQuaTrinhXuLyRepo.findAll(predicateFindAll(donId, donViId));
		if (lichSuList != null) {
			thuTu = lichSuList.size();
		}
		return thuTu;
	}
	
	public LichSuQuaTrinhXuLy save(LichSuQuaTrinhXuLy obj, Long congChucId) {
		CongChuc cc = new CongChuc();
		cc.setId(congChucId);
		if (!obj.isNew()) {
			LichSuQuaTrinhXuLy o = lichSuQuaTrinhXuLyRepo.findOne(obj.getId());
			obj.setNgayTao(o.getNgayTao());
			obj.setNgaySua(LocalDateTime.now());
			obj.setNguoiTao(o.getNguoiTao());
			obj.setNguoiSua(cc);
		} else {
			obj.setNguoiTao(cc);
			obj.setNguoiSua(obj.getNguoiTao());
		}
		obj = lichSuQuaTrinhXuLyRepo.save(obj);
		return obj;
	}
}
