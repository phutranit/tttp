package vn.greenglobal.tttp.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.QCoQuanQuanLy;
import vn.greenglobal.tttp.model.QCongChuc;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;

@Component
public class CoQuanQuanLyService {

	BooleanExpression base = QCoQuanQuanLy.coQuanQuanLy.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa, Long cha, Long capCoQuanQuanLy, Long donViHanhChinh,
			Boolean notCha) {

		BooleanExpression predAll = base;
		if (tuKhoa != null && !"".equals(tuKhoa) && !notCha) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.ma.containsIgnoreCase(tuKhoa)
					.or(QCoQuanQuanLy.coQuanQuanLy.ten.containsIgnoreCase(tuKhoa))
					.or(QCoQuanQuanLy.coQuanQuanLy.moTa.containsIgnoreCase(tuKhoa)));
		}

		if (cha != null && cha > 0 && !notCha) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.cha.id.eq(cha));
		}

		if (capCoQuanQuanLy != null && capCoQuanQuanLy > 0 && !notCha) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLy));
		}

		if (donViHanhChinh != null && donViHanhChinh > 0 && !notCha) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.donViHanhChinh.id.eq(donViHanhChinh));
		}

		if (donViHanhChinh == null && capCoQuanQuanLy == null && cha == null && notCha) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.cha.id.isNull());
		}

		return predAll;
	}
	
	public Predicate predicateFindAllByName(String ten) {

		BooleanExpression predAll = base;
		if (ten != null && !"".equals(ten)) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.ten.containsIgnoreCase(ten));
		}

		return predAll;
	}

	public Predicate predicateFindNoiCapCMND(Long capCoQuanQuanLy, Long loaiCoQuanQuanLy) {
		BooleanExpression predAll = base;

		if (capCoQuanQuanLy != null && capCoQuanQuanLy > 0 & loaiCoQuanQuanLy != null && loaiCoQuanQuanLy > 0) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLy)
					.and(QCoQuanQuanLy.coQuanQuanLy.loaiCoQuanQuanLy.id.eq(loaiCoQuanQuanLy)));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QCoQuanQuanLy.coQuanQuanLy.id.eq(id));
	}

	public boolean isExists(CoQuanQuanLyRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QCoQuanQuanLy.coQuanQuanLy.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public CoQuanQuanLy delete(CoQuanQuanLyRepository repo, Long id) {
		CoQuanQuanLy coQuanQuanLy = repo.findOne(predicateFindOne(id));

		if (coQuanQuanLy != null) {
			coQuanQuanLy.setDaXoa(true);
		}

		return coQuanQuanLy;
	}

	public boolean checkExistsData(CoQuanQuanLyRepository repo, CoQuanQuanLy body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.id.ne(body.getId()));
		}

		predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.ten.eq(body.getTen()));
		CoQuanQuanLy coQuanQuanLy = repo.findOne(predAll);

		return coQuanQuanLy != null ? true : false;
	}

	public boolean checkUsedData(CoQuanQuanLyRepository repo, CongChucRepository congChucRepository,
			DonRepository donRepository, SoTiepCongDanRepository soTiepCongDanRepository,
			XuLyDonRepository xuLyDonRepository, Long id) {
		List<CoQuanQuanLy> coQuanQuanLyList = (List<CoQuanQuanLy>) repo
				.findAll(base.and(QCoQuanQuanLy.coQuanQuanLy.cha.id.eq(id)));
		List<CongChuc> congChucList = (List<CongChuc>) congChucRepository
				.findAll(QCongChuc.congChuc.daXoa.eq(false).and(QCongChuc.congChuc.coQuanQuanLy.id.eq(id)));
		List<Don> donList = (List<Don>) donRepository.findAll(QDon.don.daXoa.eq(false)
				.and(QDon.don.coQuanDaGiaiQuyet.id.eq(id)).or(QDon.don.phongBanGiaiQuyet.id.eq(id)));
		List<XuLyDon> xuLyDonList = (List<XuLyDon>) xuLyDonRepository.findAll(QXuLyDon.xuLyDon.daXoa.eq(false)
				.and(QXuLyDon.xuLyDon.phongBanXuLy.id.eq(id)).or(QXuLyDon.xuLyDon.coQuanTiepNhan.id.eq(id))
				.or(QXuLyDon.xuLyDon.phongBanGiaiQuyet.id.eq(id)));

		if ((coQuanQuanLyList != null && coQuanQuanLyList.size() > 0)
				|| (congChucList != null && congChucList.size() > 0) || (donList != null && donList.size() > 0)
				|| (xuLyDonList != null && xuLyDonList.size() > 0)) {
			return true;
		}

		return false;
	}

}
