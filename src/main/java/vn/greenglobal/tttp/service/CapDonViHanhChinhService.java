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

import vn.greenglobal.tttp.model.CapDonViHanhChinh;
import vn.greenglobal.tttp.model.DonViHanhChinh;
import vn.greenglobal.tttp.model.QCapDonViHanhChinh;
import vn.greenglobal.tttp.model.QDonViHanhChinh;
import vn.greenglobal.tttp.repository.CapDonViHanhChinhRepository;
import vn.greenglobal.tttp.repository.DonViHanhChinhRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class CapDonViHanhChinhService {
	
	@Autowired
	private CapDonViHanhChinhRepository capDonViHanhChinhRepository;

	BooleanExpression base = QCapDonViHanhChinh.capDonViHanhChinh.daXoa.eq(false);

	public Predicate predicateFindAll(String ten) {
		BooleanExpression predAll = base;
		if (ten != null && StringUtils.isNotBlank(ten.trim())) {
			predAll = predAll.and(QCapDonViHanhChinh.capDonViHanhChinh.ten.containsIgnoreCase(ten.trim())
					.or(QCapDonViHanhChinh.capDonViHanhChinh.ma.containsIgnoreCase(ten.trim()))
					.or(QCapDonViHanhChinh.capDonViHanhChinh.moTa.containsIgnoreCase(ten.trim())));
		}
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QCapDonViHanhChinh.capDonViHanhChinh.id.eq(id));
	}

	public boolean isExists(CapDonViHanhChinhRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QCapDonViHanhChinh.capDonViHanhChinh.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public CapDonViHanhChinh delete(CapDonViHanhChinhRepository repo, Long id) {
		CapDonViHanhChinh capDonViHanhChinh = repo.findOne(predicateFindOne(id));

		if (capDonViHanhChinh != null) {
			capDonViHanhChinh.setDaXoa(true);
		}

		return capDonViHanhChinh;
	}

	public boolean checkExistsData(CapDonViHanhChinhRepository repo, CapDonViHanhChinh body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QCapDonViHanhChinh.capDonViHanhChinh.id.ne(body.getId()));
		}

		predAll = predAll.and(QCapDonViHanhChinh.capDonViHanhChinh.ten.eq(body.getTen()));
		CapDonViHanhChinh capDonViHanhChinh = repo.findOne(predAll);

		return capDonViHanhChinh != null ? true : false;
	}

	public boolean checkUsedData(DonViHanhChinhRepository repoDonViHanhChinh, Long id) {
		List<DonViHanhChinh> coQuanQuanLyList = (List<DonViHanhChinh>) repoDonViHanhChinh
				.findAll(QDonViHanhChinh.donViHanhChinh.daXoa.eq(false)
						.and(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.id.eq(id)));

		if (coQuanQuanLyList != null && coQuanQuanLyList.size() > 0) {
			return true;
		}

		return false;
	}
	
	public CapDonViHanhChinh save(CapDonViHanhChinh obj, Long congChucId) {
		return Utils.save(capDonViHanhChinhRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(CapDonViHanhChinh obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(capDonViHanhChinhRepository, obj, congChucId, eass, status);		
	}

}
