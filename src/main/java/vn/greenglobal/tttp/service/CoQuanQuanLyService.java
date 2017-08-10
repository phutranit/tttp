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
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.QCoQuanQuanLy;
import vn.greenglobal.tttp.model.QCongChuc;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class CoQuanQuanLyService {
	
	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepository;

	BooleanExpression base = QCoQuanQuanLy.coQuanQuanLy.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa, Long cha, Long capCoQuanQuanLy, Long donViHanhChinh) {

		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.ma.containsIgnoreCase(tuKhoa.trim())
					.or(QCoQuanQuanLy.coQuanQuanLy.ten.containsIgnoreCase(tuKhoa.trim()))
					.or(QCoQuanQuanLy.coQuanQuanLy.moTa.containsIgnoreCase(tuKhoa.trim())));
		}

		if (cha != null && cha > 0) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.cha.id.eq(cha));
		}

		if (capCoQuanQuanLy != null && capCoQuanQuanLy > 0) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLy));
		}

		if (donViHanhChinh != null && donViHanhChinh > 0) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.donViHanhChinh.id.eq(donViHanhChinh));
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

		if (capCoQuanQuanLy != null && capCoQuanQuanLy > 0 && loaiCoQuanQuanLy != null && loaiCoQuanQuanLy > 0) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLy)
					.and(QCoQuanQuanLy.coQuanQuanLy.loaiCoQuanQuanLy.id.eq(loaiCoQuanQuanLy)));
		}

		return predAll;
	}
	
	public CoQuanQuanLy getCha(CoQuanQuanLyRepository repo, Long id) {
		CoQuanQuanLy coQuanQuanLy = repo.findOne(predicateFindOne(id));
		if (coQuanQuanLy != null) {
			coQuanQuanLy = coQuanQuanLy.getCha();
		}
		return coQuanQuanLy != null ? coQuanQuanLy : null;
	}
	
	public Predicate predicateFindPhongBan(Long capCoQuanQuanLy, Long id, CoQuanQuanLyRepository repo, 
			ThamSoService thamSoService, ThamSoRepository repoThamSo) {
		BooleanExpression predAll = base;
		boolean check = false;

		ThamSo thamSoQuan = repoThamSo.findOne(thamSoService.predicateFindTen("CDVHC_QUAN"));
		ThamSo thamSoHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CDVHC_HUYEN"));
		ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));

		CoQuanQuanLy cqql = getCha(repo, id);
		
		if(cqql != null) {
			id = cqql.getId();
			if(cqql.getCapCoQuanQuanLy().getId().equals(Long.valueOf(thamSoQuan.getGiaTri().toString())) || 
					cqql.getCapCoQuanQuanLy().getId().equals(Long.valueOf(thamSoHuyen.getGiaTri().toString())) || 
					cqql.getCapCoQuanQuanLy().getId().equals(Long.valueOf(thamSoSBN.getGiaTri().toString()))) {
				id = cqql.getId();
				check = true;
			}
		}
		
		if(check || cqql == null) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.cha.id.eq(id))
					.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLy));
			
			return predAll;
		}
		
		return predicateFindPhongBan(capCoQuanQuanLy, id, repo, thamSoService, repoThamSo);
	}
	
	public Predicate predicateFindPhongBanDonBanDonvi(Long donViId, ThamSoService thamSoService, ThamSoRepository repoThamSo) {
		BooleanExpression predAll = base;
		ThamSo thamSoPB = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
		
		predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.cha.id.eq(donViId)
				.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(Long.valueOf(thamSoPB.getGiaTri().toString()))));
		return predAll;
	}
	
	public Predicate predicateFindOne(Long id) {
		return base.and(QCoQuanQuanLy.coQuanQuanLy.id.eq(id));
	}
	
	public Predicate predicateFindByDonVi(Long id, Long capCoQuanQuanLyId) {
		BooleanExpression predAll = base.and(QCoQuanQuanLy.coQuanQuanLy.cha.id.eq(id)
				.and(QCoQuanQuanLy.coQuanQuanLy.id.ne(id)));
		if (capCoQuanQuanLyId != null) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyId));
		}
		return predAll;
	}
	
	public Predicate predicateFindByCha(Long chaId) {
		BooleanExpression predAll = base.and(QCoQuanQuanLy.coQuanQuanLy.cha.id.eq(chaId));
		return predAll;
	}
	
	public Predicate predicateFindByLoaiCQQLVaDVHC(Long loaiCQQLId, Long donViHanhChinhId) {
		BooleanExpression predAll = base.and(QCoQuanQuanLy.coQuanQuanLy.loaiCoQuanQuanLy.id.eq(loaiCQQLId));
		if (donViHanhChinhId != null) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.donViHanhChinh.id.eq(donViHanhChinhId));
		}
		return predAll;
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
		if (body.getCha() != null) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.cha.id.eq(body.getCha().getId()));
		} else {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.cha.isNull());
		}
		predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(body.getCapCoQuanQuanLy().getId()));
		predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.donViHanhChinh.id.eq(body.getDonViHanhChinh().getId()));
		List<CoQuanQuanLy> coQuanQuanLys = (List<CoQuanQuanLy>) repo.findAll(predAll);

		return coQuanQuanLys != null && coQuanQuanLys.size() > 0 ? true : false;
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
	
	public Predicate predicateFindDonViVaConCuaDonVi(Long coQuanQuanLyId, List<Long> capCoQuanQuanLyIds, String type, Long coQuanQuanLyDaChonId) {
		BooleanExpression predAll = base;
		
		if (coQuanQuanLyId != null && coQuanQuanLyId > 0) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.cha.id.eq(coQuanQuanLyId));
			
			if ("CQQL_UBNDTP_DA_NANG".equals(type)) {
				predAll.and(QCoQuanQuanLy.coQuanQuanLy.id.eq(coQuanQuanLyId));
				predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyIds.get(0))
						.or(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyIds.get(1)))
						.or(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyIds.get(2))));
			} else if ("CCQQL_UBND_QUAN_HUYEN".equals(type)) {
				predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyIds.get(0))
						.or(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyIds.get(1))));
			}
			
			if (coQuanQuanLyDaChonId != null && coQuanQuanLyDaChonId > 0) {
				predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.id.ne(coQuanQuanLyDaChonId));
			}
		}

		return predAll;
	}
	
	public Predicate predicateFindDonViVaConCuaDonVi(Long coQuanQuanLyId, List<Long> capCoQuanQuanLyIds, String type) {
		BooleanExpression predAll = base;
		if (coQuanQuanLyId != null && coQuanQuanLyId > 0) {
			predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.id.eq(coQuanQuanLyId)
					.or(QCoQuanQuanLy.coQuanQuanLy.cha.id.eq(coQuanQuanLyId)));
			if ("CQQL_UBNDTP_DA_NANG".equals(type)) {
				predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyIds.get(0))
						.or(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyIds.get(1))));
			} else if ("CCQQL_SO_BAN_NGANH".equals(type)) { 
				predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyIds.get(0)));
			} else if ("CCQQL_UBND_QUAN_HUYEN".equals(type)) {
				predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyIds.get(0)));
			} 
		}
		return predAll;
	}
	
	public Predicate predicateFindDSCoQuanDonViThamGiaTiepDan(Long coQuanQuanLyId, List<Long> capCoQuanQuanLyIds) {
		BooleanExpression predAll = base;
		predAll = predAll.and(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyIds.get(0))
				.or(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyIds.get(1))
						.or(QCoQuanQuanLy.coQuanQuanLy.capCoQuanQuanLy.id.eq(capCoQuanQuanLyIds.get(2))
					)));
		if (coQuanQuanLyId != null && coQuanQuanLyId > 0) {
			predAll = predAll.or(QCoQuanQuanLy.coQuanQuanLy.id.eq(coQuanQuanLyId));
		}
		return predAll;
	}
	
	public CoQuanQuanLy save(CoQuanQuanLy obj, Long congChucId) {
		return Utils.save(coQuanQuanLyRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(CoQuanQuanLy obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(coQuanQuanLyRepository, obj, congChucId, eass, status);		
	}
}
