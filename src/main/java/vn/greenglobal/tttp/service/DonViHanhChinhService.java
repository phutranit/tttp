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

import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongDan;
import vn.greenglobal.tttp.model.DonViHanhChinh;
import vn.greenglobal.tttp.model.QCoQuanQuanLy;
import vn.greenglobal.tttp.model.QCongDan;
import vn.greenglobal.tttp.model.QDonViHanhChinh;
import vn.greenglobal.tttp.model.QToDanPho;
import vn.greenglobal.tttp.model.ToDanPho;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CongDanRepository;
import vn.greenglobal.tttp.repository.DonViHanhChinhRepository;
import vn.greenglobal.tttp.repository.ToDanPhoRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class DonViHanhChinhService {
	
	@Autowired
	private DonViHanhChinhRepository donViHanhChinhRepository;

	BooleanExpression base = QDonViHanhChinh.donViHanhChinh.daXoa.eq(false);

	public Predicate predicateFindAll(String ten, Long cha, Long capDonViHanhChinh) {
		BooleanExpression predAll = base;
		if (ten != null && StringUtils.isNotBlank(ten.trim())) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.ten.containsIgnoreCase(ten.trim())
					.or(QDonViHanhChinh.donViHanhChinh.moTa.containsIgnoreCase(ten.trim()))
					.or(QDonViHanhChinh.donViHanhChinh.ma.containsIgnoreCase(ten.trim())));
		}

		if (cha != null && cha > 0) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.cha.id.eq(cha));
		}

		if (capDonViHanhChinh != null && capDonViHanhChinh > 0) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.id.eq(capDonViHanhChinh));
		}

		return predAll;
	}

	public Predicate predicateFindCapTinhThanhPho(Long capTinh, Long capThanhPhoTrucThuocTW,
			Long capThanhPhoTrucThuocTinh) {
		BooleanExpression predAll = base;

		if (capTinh != null && capTinh > 0 & capThanhPhoTrucThuocTW != null
				&& capThanhPhoTrucThuocTW > 0 & capThanhPhoTrucThuocTinh != null && capThanhPhoTrucThuocTinh > 0) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.id.eq(capTinh)
					.or(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.id.eq(capThanhPhoTrucThuocTW))
					.or(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.id.eq(capThanhPhoTrucThuocTinh)));
		}

		return predAll;
	}

	public Predicate predicateFindCapQuanHuyen(Long cha, Long capQuan, Long capHuyen) {
		BooleanExpression predAll = base;

		if (cha != null && cha > 0) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.cha.id.eq(cha));
		}

		if (capQuan != null && capQuan > 0 && capHuyen != null && capHuyen > 0) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.id.eq(capQuan)
					.or(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.id.eq(capHuyen)));
		}

		return predAll;
	}

	public Predicate predicateFindCapPhuongXa(Long cha, Long capPhuong, Long capXa) {
		BooleanExpression predAll = base;

		if (cha != null && cha > 0) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.cha.id.eq(cha));
		}

		if (capPhuong != null && capPhuong > 0 && capXa != null && capXa > 0) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.id.eq(capPhuong)
					.or(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.id.eq(capXa)));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QDonViHanhChinh.donViHanhChinh.id.eq(id));
	}

	public boolean isExists(DonViHanhChinhRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QDonViHanhChinh.donViHanhChinh.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public DonViHanhChinh delete(DonViHanhChinhRepository repo, Long id) {
		DonViHanhChinh donViHanhChinh = repo.findOne(predicateFindOne(id));

		if (donViHanhChinh != null) {
			donViHanhChinh.setDaXoa(true);
		}

		return donViHanhChinh;
	}

	public boolean checkExistsData(DonViHanhChinhRepository repo, DonViHanhChinh body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.id.ne(body.getId()));
		}

		predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.ten.eq(body.getTen()));
		if (body.getCha() != null) {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.cha.id.eq(body.getCha().getId()));
		} else {
			predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.cha.isNull());
		}
		predAll = predAll.and(QDonViHanhChinh.donViHanhChinh.capDonViHanhChinh.id.eq(body.getCapDonViHanhChinh().getId()));
		List<DonViHanhChinh> donViHanhChinhs = (List<DonViHanhChinh>) repo.findAll(predAll);

		return donViHanhChinhs != null && donViHanhChinhs.size() > 0 ? true : false;
	}

	public boolean checkUsedData(DonViHanhChinhRepository repo, CoQuanQuanLyRepository coQuanQuanLyRepository,
			ToDanPhoRepository toDanPhoRepository, CongDanRepository congDanRepository, Long id) {
		List<DonViHanhChinh> donViHanhChinhList = (List<DonViHanhChinh>) repo
				.findAll(base.and(QDonViHanhChinh.donViHanhChinh.cha.id.eq(id)));
		List<CoQuanQuanLy> coQuanQuanLyList = (List<CoQuanQuanLy>) coQuanQuanLyRepository.findAll(
				QCoQuanQuanLy.coQuanQuanLy.daXoa.eq(false).and(QCoQuanQuanLy.coQuanQuanLy.donViHanhChinh.id.eq(id)));
		List<ToDanPho> toDanPhoList = (List<ToDanPho>) toDanPhoRepository
				.findAll(QToDanPho.toDanPho.daXoa.eq(false).and(QToDanPho.toDanPho.donViHanhChinh.id.eq(id)));
		List<CongDan> congDanList = (List<CongDan>) congDanRepository
				.findAll(QCongDan.congDan.daXoa.eq(false).and(QCongDan.congDan.tinhThanh.id.eq(id))
						.or(QCongDan.congDan.quanHuyen.id.eq(id)).or(QCongDan.congDan.phuongXa.id.eq(id)));

		if ((donViHanhChinhList != null && donViHanhChinhList.size() > 0)
				|| (coQuanQuanLyList != null && coQuanQuanLyList.size() > 0)
				|| (congDanList != null && congDanList.size() > 0)
				|| (toDanPhoList != null && toDanPhoList.size() > 0)) {
			return true;
		}

		return false;
	}
	
	public DonViHanhChinh save(DonViHanhChinh obj, Long congChucId) {
		return Utils.save(donViHanhChinhRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(DonViHanhChinh obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(donViHanhChinhRepository, obj, congChucId, eass, status);		
	}

}
