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

import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.QNguoiDung;
import vn.greenglobal.tttp.model.QVaiTro;
import vn.greenglobal.tttp.model.VaiTro;
import vn.greenglobal.tttp.repository.NguoiDungRepository;
import vn.greenglobal.tttp.repository.VaiTroRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class VaiTroService {
	
	@Autowired
	private VaiTroRepository vaiTroRepository;

	BooleanExpression base = QVaiTro.vaiTro.daXoa.eq(false);

	public Predicate predicateFindAll(String tuKhoa) {
		BooleanExpression predAll = base;
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll.and(QVaiTro.vaiTro.tenSearch.containsIgnoreCase(Utils.unAccent(tuKhoa.trim())));
		}

		return predAll;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QVaiTro.vaiTro.id.eq(id));
	}

	public boolean isExists(VaiTroRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QVaiTro.vaiTro.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public VaiTro delete(VaiTroRepository repo, Long id) {
		VaiTro vaiTro = repo.findOne(predicateFindOne(id));

		if (vaiTro != null) {
			vaiTro.setDaXoa(true);
		}

		return vaiTro;
	}

	public boolean checkExistsData(VaiTroRepository repo, VaiTro body) {
		BooleanExpression predAll = base;

		if (!body.isNew()) {
			predAll = predAll.and(QVaiTro.vaiTro.id.ne(body.getId()));
		}

		predAll = predAll.and(QVaiTro.vaiTro.ten.eq(body.getTen()));
		predAll = predAll.and(QVaiTro.vaiTro.loaiVaiTro.eq(VaiTroEnum.valueOf(body.getLoaiVaiTro().toString())));
		List<VaiTro> vaiTros = (List<VaiTro>) repo.findAll(predAll);

		return vaiTros != null && vaiTros.size() > 0 ? true : false;
	}

	public boolean checkUsedData(NguoiDungRepository nguoiDungRepository, Long id) {
		VaiTro vaiTro = new VaiTro();
		vaiTro.setId(id);
		List<NguoiDung> nguoiDungList = (List<NguoiDung>) nguoiDungRepository
				.findAll(QNguoiDung.nguoiDung.daXoa.eq(false).and(QNguoiDung.nguoiDung.vaiTros.contains(vaiTro)));

		if (nguoiDungList != null && nguoiDungList.size() > 0) {
			return true;
		}

		return false;
	}
	
	public VaiTro save(VaiTro obj, Long congChucId) {
		return Utils.save(vaiTroRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(VaiTro obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(vaiTroRepository, obj, congChucId, eass, status);		
	}

}
