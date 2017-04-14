package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.QLinhVucDonThu;
import vn.greenglobal.tttp.repository.LinhVucDonThuRepository;

public class LinhVucDonThuService {

	public Predicate predicateFindAll(String tuKhoa, Long cha) {
		BooleanExpression predAll = QLinhVucDonThu.linhVucDonThu.daXoa.eq(false);
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.ten.containsIgnoreCase(tuKhoa)
					.or(QLinhVucDonThu.linhVucDonThu.moTa.containsIgnoreCase(tuKhoa)));
		}

		if (cha != null && cha > 0) {
			predAll = predAll.and(QLinhVucDonThu.linhVucDonThu.cha.id.eq(cha));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return QLinhVucDonThu.linhVucDonThu.daXoa.eq(false)
				.and(QLinhVucDonThu.linhVucDonThu.id.eq(id));
	}

	public boolean isExists(LinhVucDonThuRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = QLinhVucDonThu.linhVucDonThu.daXoa.eq(false)
					.and(QLinhVucDonThu.linhVucDonThu.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public LinhVucDonThu deleteLinhVucDonThu(LinhVucDonThuRepository repo, Long id) {
		LinhVucDonThu linhVucDonThu = null;
		if (isExists(repo, id)) {
			linhVucDonThu = new LinhVucDonThu();
			linhVucDonThu.setId(id);
			linhVucDonThu.setDaXoa(true);
		}
		return linhVucDonThu;
	}

	public boolean checkExistsData(LinhVucDonThuRepository repo, String ten) {
		LinhVucDonThu linhVucDonThu = repo.findOne(QLinhVucDonThu.linhVucDonThu.daXoa.eq(false)
				.and(QLinhVucDonThu.linhVucDonThu.ten.eq(ten)));
		return linhVucDonThu != null ? true : false;
	}

}
