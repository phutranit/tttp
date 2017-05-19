package vn.greenglobal.tttp.service;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.QCongChuc;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.NguoiDungRepository;

@Component
public class CongChucService {

	BooleanExpression base = QCongChuc.congChuc.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa, Long coQuanQuanLyId) {
		BooleanExpression predAll = base;
		
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QCongChuc.congChuc.hoVaTen.containsIgnoreCase(tuKhoa));
		}

		if (coQuanQuanLyId != null && coQuanQuanLyId > 0) {
			predAll = predAll.and(QCongChuc.congChuc.coQuanQuanLy.id.eq(coQuanQuanLyId));
		}
		
		return predAll;
	}
	
	public Predicate predicateFindByVaiTro(String tuKhoa, Long coQuanQuanLyId, String vaiTro) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && !"".equals(tuKhoa)) {
			predAll = predAll.and(QCongChuc.congChuc.hoVaTen.containsIgnoreCase(tuKhoa));
		}
		
		if (coQuanQuanLyId != null && coQuanQuanLyId > 0) {
			predAll = predAll.and(QCongChuc.congChuc.coQuanQuanLy.id.eq(coQuanQuanLyId));
		}
		
		if (vaiTro != null && !"".equals(vaiTro)) {
			predAll = predAll.and(QCongChuc.congChuc.nguoiDung.vaiTroMacDinh.loaiVaiTro.eq(VaiTroEnum.valueOf(StringUtils.upperCase(vaiTro))));
		}
		
		return predAll;
	}
	
	public Predicate predicateFindLanhDaoTiepCongDan(CoQuanQuanLy donVi) {
		BooleanExpression predAll = base.and(QCongChuc.congChuc.chucVu.lanhDao.eq(true));
		if (donVi != null) {
			predAll = predAll.and(QCongChuc.congChuc.coQuanQuanLy.eq(donVi));
		}
		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QCongChuc.congChuc.id.eq(id));
	}

	public Predicate predicateFindByNguoiDungId(long id) {
		return base.and(QCongChuc.congChuc.nguoiDung.id.eq(id));
	}

	public boolean isExists(CongChucRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QCongChuc.congChuc.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public CongChuc delete(CongChucRepository repo, Long id) {
		CongChuc congChuc = repo.findOne(predicateFindOne(id));

		if (congChuc != null) {
			congChuc.setDaXoa(true);
		}

		return congChuc;
	}

	public boolean checkExistsData(CongChucRepository repo, CongChuc body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QCongChuc.congChuc.id.ne(body.getId()));
		}

		predAll = predAll.and(QCongChuc.congChuc.nguoiDung.tenDangNhap.eq(body.getNguoiDung().getTenDangNhap()));
		CongChuc congChuc = repo.findOne(predAll);

		return congChuc != null ? true : false;
	}

	public void bootstrapCongChuc(CongChucRepository repo, NguoiDungRepository repoNguoiDung) {
		List<CongChuc> list = (List<CongChuc>) repo.findAll(base);
		if (list.size() == 0) {
			NguoiDung nguoiDung = new NguoiDung();
			nguoiDung.setTenDangNhap("admin");
			nguoiDung.updatePassword("tttp@123");
			nguoiDung.getQuyens().add("*");
			nguoiDung.setActive(true);
			NguoiDung nguoiDungNew = repoNguoiDung.save(nguoiDung);
			CongChuc congChuc = new CongChuc();
			congChuc.setHoVaTen("Super Admin");
			congChuc.setEmail("admin@liferay.com");
			congChuc.setNguoiDung(nguoiDungNew);
			repo.save(congChuc);
		}
	}
}
