package vn.greenglobal.tttp.service;

import java.util.List;

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

@Component
public class LinhVucDonThuService {

	BooleanExpression base = QLinhVucDonThu.linhVucDonThu.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa, String cha, String loaiDon) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.ten.containsIgnoreCase(tuKhoa));
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

}
