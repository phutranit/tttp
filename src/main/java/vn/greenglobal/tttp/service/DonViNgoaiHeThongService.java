package vn.greenglobal.tttp.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;


import vn.greenglobal.tttp.model.DonViNgoaiHeThong;
import vn.greenglobal.tttp.model.QDonViNgoaiHeThong;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.DonViNgoaiHeThongRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class DonViNgoaiHeThongService {
	
	@Autowired
	private DonViNgoaiHeThongRepository donViNgoaiHeThongRepository;
	
	BooleanExpression base = QDonViNgoaiHeThong.donViNgoaiHeThong.daXoa.eq(false);
	
	public Predicate predicateFindAll() { 
		BooleanExpression predAll = base;
		return predAll;
	}
	
	public Predicate predicateFindAll(String ten) {
		BooleanExpression predAll = base;		
		if (ten != null && StringUtils.isNotBlank(ten.trim())) {
			predAll = predAll.and(QDonViNgoaiHeThong.donViNgoaiHeThong.tenSearch.containsIgnoreCase(Utils.unAccent(ten.trim())));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QDonViNgoaiHeThong.donViNgoaiHeThong.id.eq(id));
	}

	public boolean isExists(DonViNgoaiHeThongRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QDonViNgoaiHeThong.donViNgoaiHeThong.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public DonViNgoaiHeThong delete(DonViNgoaiHeThongRepository repo, Long id) {
		DonViNgoaiHeThong donViNgoaiHeThong = repo.findOne(predicateFindOne(id));

		if (donViNgoaiHeThong != null) {
			donViNgoaiHeThong.setDaXoa(true);
		}

		return donViNgoaiHeThong;
	}

	public boolean checkExistsData(DonViNgoaiHeThongRepository repo, DonViNgoaiHeThong body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QDonViNgoaiHeThong.donViNgoaiHeThong.id.ne(body.getId()));
		}

		predAll = predAll.and(QDonViNgoaiHeThong.donViNgoaiHeThong.ten.eq(body.getTen()));
		
		List<DonViNgoaiHeThong> donViNgoaiHeThongs = (List<DonViNgoaiHeThong>) repo.findAll(predAll);

		return donViNgoaiHeThongs != null && donViNgoaiHeThongs.size() > 0 ? true : false;
	}

	public boolean checkUsedData(DonViNgoaiHeThongRepository repo, XuLyDonRepository xuLyDonRepository, Long id) {
		List<XuLyDon> xuLyDonList = (List<XuLyDon>) xuLyDonRepository.findAll(
				QXuLyDon.xuLyDon.daXoa.eq(false).and(QXuLyDon.xuLyDon.donViNgoaiHeThong.id.eq(id)));
		if ((xuLyDonList != null && xuLyDonList.size() > 0)) {
			return true;
		}
		return false;
	}
	
	public DonViNgoaiHeThong save(DonViNgoaiHeThong obj, Long congChucId) {
		return Utils.save(donViNgoaiHeThongRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(DonViNgoaiHeThong obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(donViNgoaiHeThongRepository, obj, congChucId, eass, status);		
	}

}
