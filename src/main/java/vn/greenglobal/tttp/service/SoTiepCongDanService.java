package vn.greenglobal.tttp.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.HuongGiaiQuyetTCDEnum;
import vn.greenglobal.tttp.enums.PhanLoaiDonCongDanEnum;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.QDon_CongDan;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class SoTiepCongDanService {
	
	@Autowired
	private SoTiepCongDanRepository soTiepCongDanRepository;

	BooleanExpression base = QSoTiepCongDan.soTiepCongDan.daXoa.eq(false);
	BooleanExpression baseDonCongDan = QDon_CongDan.don_CongDan.daXoa.eq(false);
	
	public Predicate predicateFindOne(Long id) {
		return base.and(QSoTiepCongDan.soTiepCongDan.id.eq(id));
	}

	public Predicate predicateFindAllTCD(String tuKhoa, String phanLoaiDon, String huongXuLy, String tuNgay,
			String denNgay, String loaiTiepCongDan, Long donViId, Long lanhDaoId, String tenLanhDao, String tenNguoiDungDon, String tinhTrangXuLy,
			String ketQuaTiepDan, CongChucService congChucService, CongChucRepository congChucRepo, DonCongDanRepository donCongDanRepo) {
		BooleanExpression predAll = base;
		BooleanExpression donCongDanQuery = baseDonCongDan;
		
		if (tuKhoa != null && StringUtils.isNotBlank(tuKhoa.trim())) {
			predAll = predAll
					.and(QSoTiepCongDan.soTiepCongDan.don.donCongDans.any().congDan.hoVaTen.containsIgnoreCase(tuKhoa.trim())
							.or(QSoTiepCongDan.soTiepCongDan.don.donCongDans.any().congDan.soCMNDHoChieu.containsIgnoreCase(tuKhoa.trim())));
		}
		
		if (tenLanhDao != null && StringUtils.isNotBlank(tenLanhDao.trim())) {
			List<CongChuc> lanhDaos = new ArrayList<CongChuc>();
			lanhDaos.addAll((List<CongChuc>)congChucRepo.findAll(congChucService.predicateFindByTenLD(tenLanhDao)));
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.canBoTiepDan.in(lanhDaos));
		}
		
		if (phanLoaiDon != null && StringUtils.isNotBlank(phanLoaiDon.trim())) {
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.loaiDon.stringValue().containsIgnoreCase(phanLoaiDon.trim()));
		}

		predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(donViId)
				.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(donViId))
				.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(donViId)));

		if (huongXuLy != null && StringUtils.isNotBlank(huongXuLy.trim())) {
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.huongXuLy.stringValue().containsIgnoreCase(huongXuLy.trim()));
		}
		if (loaiTiepCongDan != null && StringUtils.isNotBlank(loaiTiepCongDan.trim())) {
			predAll = predAll
					.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.stringValue().containsIgnoreCase(loaiTiepCongDan.trim()));
		}
		if (StringUtils.isNotBlank(tuNgay) && StringUtils.isNotBlank(denNgay)) {
			LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
			LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);

			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepDan.between(dtTuNgay, dtDenNgay));
		} else {
			if (StringUtils.isNotBlank(tuNgay)) {
				LocalDateTime dtTuNgay = Utils.fixTuNgay(tuNgay);
				predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepDan.after(dtTuNgay));
			}
			if (StringUtils.isNotBlank(denNgay)) {
				LocalDateTime dtDenNgay = Utils.fixDenNgay(denNgay);
				predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.ngayTiepDan.before(dtDenNgay));
			}
		}
		
		if (lanhDaoId != null && lanhDaoId > 0) {
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.canBoTiepDan.isNotNull()
					.and(QSoTiepCongDan.soTiepCongDan.canBoTiepDan.id.eq(lanhDaoId)));
		}
		
		if (tinhTrangXuLy != null && StringUtils.isNotBlank(tinhTrangXuLy.trim())) {
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.trinhTrangXuLyTCDLanhDao.eq(HuongGiaiQuyetTCDEnum.valueOf(tinhTrangXuLy.trim())));
		}
		
		if (ketQuaTiepDan != null && StringUtils.isNotBlank(ketQuaTiepDan.trim())) {
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.huongGiaiQuyetTCDLanhDao.isNotNull()
					.and(QSoTiepCongDan.soTiepCongDan.huongGiaiQuyetTCDLanhDao.eq(HuongGiaiQuyetTCDEnum.valueOf(ketQuaTiepDan.trim()))));
		}

		List<Don_CongDan> donCongDans = new ArrayList<Don_CongDan>();
		List<Don> dons = new ArrayList<Don>();
		
		if (tenNguoiDungDon != null && StringUtils.isNotBlank(tenNguoiDungDon.trim())) {
			donCongDanQuery = donCongDanQuery
					.and(QDon_CongDan.don_CongDan.phanLoaiCongDan.stringValue()
							.eq(PhanLoaiDonCongDanEnum.NGUOI_DUNG_DON.name()))
					.and(QDon_CongDan.don_CongDan.congDan.hoVaTen.containsIgnoreCase(tenNguoiDungDon.trim())
							.or(QDon_CongDan.don_CongDan.tenCoQuan.containsIgnoreCase(tenNguoiDungDon.trim())));
			donCongDans = (List<Don_CongDan>) donCongDanRepo.findAll(donCongDanQuery);
			dons = donCongDans.stream().map(d -> d.getDon()).distinct().collect(Collectors.toList());
			predAll = predAll.and(QSoTiepCongDan.soTiepCongDan.don.in(dons));
		}

		return predAll;
	}

	public boolean isExists(SoTiepCongDanRepository repo, Long id) {
		if (id != null && id > 0) {
			Predicate predicate = base.and(QSoTiepCongDan.soTiepCongDan.id.eq(id));
			return repo.exists(predicate);
		}
		return false;
	}

	public SoTiepCongDan deleteSoTiepCongDan(SoTiepCongDanRepository repo, Long id) {
		SoTiepCongDan soTiepCongDan = repo.findOne(predicateFindOne(id));

		if (soTiepCongDan != null) {
			soTiepCongDan.setDaXoa(true);
		}

		return soTiepCongDan;
	}

	public SoTiepCongDan cancelCuocTiepDanDinhKyCuaLanhDao(SoTiepCongDanRepository repo, Long id) {
		SoTiepCongDan soTiepCongDan = repo.findOne(predicateFindOne(id));

		if (soTiepCongDan != null) {
			soTiepCongDan.getDon().setThanhLapTiepDanGapLanhDao(false);
			soTiepCongDan.setDaXoa(true);
		}

		return soTiepCongDan;
	}
	
	public SoTiepCongDan save(SoTiepCongDan obj, Long congChucId) {
		return Utils.save(soTiepCongDanRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(SoTiepCongDan obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(soTiepCongDanRepository, obj, congChucId, eass, status);		
	}
}
