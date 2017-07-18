package vn.greenglobal.tttp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QLinhVucDonThu;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.LinhVucDonThuRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class LinhVucDonThuService {
	
	@Autowired
	private LinhVucDonThuRepository linhVucDonThuRepository;

	BooleanExpression base = QLinhVucDonThu.linhVucDonThu.daXoa.eq(false);
	
	public List<LinhVucDonThu> getTatCaDanhSachLinhVucDonThus() {
		BooleanExpression predAll = base.or(QLinhVucDonThu.linhVucDonThu.daXoa.eq(true));
		List<LinhVucDonThu> linhVucs = new ArrayList<LinhVucDonThu>();
		linhVucs.addAll((List<LinhVucDonThu>)linhVucDonThuRepository.findAll(predAll));
		return linhVucs;
	}
	
	public List<LinhVucDonThu> linhVucDonThusTheoId(List<Long> ids) { 
		BooleanExpression predAll = base;
		List<LinhVucDonThu> linhVucs = new ArrayList<LinhVucDonThu>();
		predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.id.in(ids));
		linhVucs.addAll((List<LinhVucDonThu>)linhVucDonThuRepository.findAll(predAll));
		return linhVucs;
	}
	
	public List<LinhVucDonThu> getDanhSachLinhVucDonThus(String loaiDon) {
		BooleanExpression predAll = base;
		if (StringUtils.isNotBlank(loaiDon)) { 
			LoaiDonEnum loaiDonEnum = LoaiDonEnum.valueOf(loaiDon);
			predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.loaiDon.eq(loaiDonEnum));
		}

		List<LinhVucDonThu> linhVucs = new ArrayList<LinhVucDonThu>();
		linhVucs.addAll((List<LinhVucDonThu>)linhVucDonThuRepository.findAll(predAll));
		return linhVucs;
	}
	
	public List<LinhVucDonThu> getDanhSachLinhVucDonThusByCha(LinhVucDonThu linhVuc) {
		BooleanExpression predAll = base;
		List<LinhVucDonThu> linhVucs = new ArrayList<LinhVucDonThu>();
		if (linhVuc == null) { 
			return linhVucs;
		}
		predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.cha.eq(linhVuc));
		linhVucs.addAll((List<LinhVucDonThu>)linhVucDonThuRepository.findAll(predAll));
		return linhVucs;
	}
	
	public Predicate predicateFindAll(String tuKhoa, String cha, String loaiDon) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.ten.containsIgnoreCase(tuKhoa.trim())
//					.or(QLinhVucDonThu.linhVucDonThu.ma.containsIgnoreCase(tuKhoa.trim()))
					.or(QLinhVucDonThu.linhVucDonThu.moTa.containsIgnoreCase(tuKhoa.trim())));
		}

		if (!"".equals(cha) && cha != null) {
			if ("null".equals(cha)) {
				predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.cha.isNull());
			} else if (Long.valueOf(cha) > 0) {
				predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.cha.id.eq(Long.valueOf(cha)));
			}
		}
		
		if (loaiDon != null && !"".equals(loaiDon)) {
			predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.loaiDon.eq(LoaiDonEnum.valueOf(loaiDon)));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QLinhVucDonThu.linhVucDonThu.id.eq(id));
	}

	public boolean isExists(LinhVucDonThuRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QLinhVucDonThu.linhVucDonThu.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public LinhVucDonThu delete(LinhVucDonThuRepository repo, Long id) {
		LinhVucDonThu linhVucDonThu = repo.findOne(predicateFindOne(id));

		if (linhVucDonThu != null) {
			linhVucDonThu.setDaXoa(true);
		}

		return linhVucDonThu;
	}

	public boolean checkExistsData(LinhVucDonThuRepository repo, LinhVucDonThu body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.id.ne(body.getId()));
		}

		predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.ten.eq(body.getTen()));
		if (body.getCha() != null) {
			predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.cha.id.eq(body.getCha().getId()));
		} else {
			predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.cha.isNull());
		}
		predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.loaiDon.eq(LoaiDonEnum.valueOf(body.getLoaiDon().toString())));
		List<LinhVucDonThu> linhVucDonThus = (List<LinhVucDonThu>) repo.findAll(predAll);

		return linhVucDonThus != null && linhVucDonThus.size() > 0 ? true : false;
	}

	public boolean checkUsedData(LinhVucDonThuRepository repo, DonRepository donRepository, Long id) {
		List<LinhVucDonThu> linhVucDonThuList = (List<LinhVucDonThu>) repo
				.findAll(base.and(QLinhVucDonThu.linhVucDonThu.cha.id.eq(id)));
		List<Don> donList = (List<Don>) donRepository.findAll(QDon.don.daXoa.eq(false)
				.and(QDon.don.linhVucDonThu.id.eq(id)).or(QDon.don.linhVucDonThuChiTiet.id.eq(id))
				.or(QDon.don.chiTietLinhVucDonThuChiTiet.id.eq(id)));

		if ((linhVucDonThuList != null && linhVucDonThuList.size() > 0) || (donList != null && donList.size() > 0)
				|| (donList != null && donList.size() > 0)) {
			return true;
		}

		return false;
	}
	
	public LinhVucDonThu save(LinhVucDonThu obj, Long congChucId) {
		return Utils.save(linhVucDonThuRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(LinhVucDonThu obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(linhVucDonThuRepository, obj, congChucId, eass, status);		
	}

	public String getMaLinhVuc() {
		String ma = "";
		int count = getTatCaDanhSachLinhVucDonThus().size();
		String str = String.valueOf(count);
		if (str.length() == 1) {
			ma += "00" + (count + 1);
		} else if (str.length() == 2) {
			ma += "0" + (count + 1);
		} else {
			ma += (count + 1);
		}
		return ma;
	}
}
