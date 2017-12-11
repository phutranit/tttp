package vn.greenglobal.tttp.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.enums.FlowStateEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class XuLyDonService {
	QXuLyDon xuLyDon = QXuLyDon.xuLyDon;
	BooleanExpression base = xuLyDon.daXoa.eq(false);

	@Autowired
	private XuLyDonRepository xuLyDonRepo;
	
	@Autowired
	private CongChucRepository congChucRepo;
	
	public Predicate predFindXLDByDonCongChuc(XuLyDonRepository repo, List<Long> donIds, Long congChucId) {
		BooleanExpression xuLyDonQuery = base.and(xuLyDon.don.id.in(donIds)).and(QXuLyDon.xuLyDon.old.eq(false))
				.and(QXuLyDon.xuLyDon.congChuc.id.eq(congChucId)
						.or(QXuLyDon.xuLyDon.canBoXuLyChiDinh.id.eq(congChucId))
						.or(QXuLyDon.xuLyDon.canBoXuLy.id.eq(congChucId))
						.or(QXuLyDon.xuLyDon.canBoGiaoViec.id.eq(congChucId))
						.or(QXuLyDon.xuLyDon.canBoChuyenDon.id.eq(congChucId))
						);
		return xuLyDonQuery;
	}
	
	public int timThuTuXuLyDonHienTai(XuLyDonRepository repo, Long donId, Long donViId) {
		BooleanExpression where = base.and(xuLyDon.don.id.eq(donId))
				.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViId));		
		int thuTu = 0;
		List<XuLyDon> xuLyDonList = new ArrayList<XuLyDon>();
		xuLyDonList.addAll((List<XuLyDon>) repo.findAll(where));
		if (xuLyDonList != null) {
			thuTu = xuLyDonList.size();
		}
		return thuTu;
	}
	
	public XuLyDon predFindCurrent(XuLyDonRepository repo, Long id) {
		BooleanExpression where = base.and(xuLyDon.don.id.eq(id))
				.and(QXuLyDon.xuLyDon.old.eq(false));
		if (repo.exists(where)) {
			OrderSpecifier<Integer> sortOrder = xuLyDon.thuTuThucHien.desc();
			List<XuLyDon> results = (List<XuLyDon>) repo.findAll(where, sortOrder);
			Long xldId = results.get(0).getId();
			return repo.findOne(xldId);
		}
		return null;
	}

	public XuLyDon predFindXuLyDonHienTai(XuLyDonRepository repo, Long donId, Long donViXuLyXLD, Long phongBanXuLyXLD,
			Long canBoId, String chucVu, boolean coQuyTrinh, boolean isChuyenVienNhapLieu) {
		BooleanExpression xuLyDonQuery = base.and(xuLyDon.don.id.eq(donId)).and(QXuLyDon.xuLyDon.old.eq(false));
		if (chucVu.equals(VaiTroEnum.LANH_DAO.name()) || chucVu.equals(VaiTroEnum.VAN_THU.name())) {
			phongBanXuLyXLD = 0L;
		}

		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}

		if (coQuyTrinh) {
			if ((phongBanXuLyXLD != null && phongBanXuLyXLD > 0) && StringUtils.isNotEmpty(chucVu)) {
				CongChuc congChuc = congChucRepo.findOne(canBoId);
				xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanXuLy.id.eq(phongBanXuLyXLD).or(QXuLyDon.xuLyDon.phongBanXuLy.eq(congChuc.getCoQuanQuanLy().getDonVi())));
			}

			if (StringUtils.isNotEmpty(chucVu)) {
				xuLyDonQuery = xuLyDonQuery.and(
						QXuLyDon.xuLyDon.chucVu.eq(VaiTroEnum.valueOf(chucVu)).or(QXuLyDon.xuLyDon.chucVu2.eq(VaiTroEnum.valueOf(chucVu))).or(QXuLyDon.xuLyDon.chucVu.isNull()));
			}

			if (StringUtils.isNotEmpty(chucVu) && StringUtils.equals(chucVu, VaiTroEnum.CHUYEN_VIEN.name())) {
				VaiTroEnum vaiTro = VaiTroEnum.valueOf(StringUtils.upperCase(chucVu));
				BooleanExpression qChucVu = QXuLyDon.xuLyDon.chucVu.eq(vaiTro).or(QXuLyDon.xuLyDon.chucVu2.eq(vaiTro));
				BooleanExpression qLuuTamCoLanhDao = QXuLyDon.xuLyDon.canBoXuLyChiDinh.id.ne(canBoId).and(qChucVu)
						.and(QXuLyDon.xuLyDon.don.currentState.type.eq(FlowStateEnum.BAT_DAU));
				BooleanExpression qGiaoViec = QXuLyDon.xuLyDon.canBoXuLyChiDinh.id.eq(canBoId);
				BooleanExpression qLuuTamKhongLanhDao = QXuLyDon.xuLyDon.canBoXuLyChiDinh.isNull().and(qChucVu);
				if (isChuyenVienNhapLieu) {
					xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.chucVu.isNull()
							.or(qLuuTamCoLanhDao)
							.or(qGiaoViec)
							.or(qLuuTamKhongLanhDao));
				} else {
					xuLyDonQuery = xuLyDonQuery.and(qGiaoViec);
				}
			}
		}

		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();
		if (repo.exists(xuLyDonQuery)) {
			List<XuLyDon> results = (List<XuLyDon>) repo.findAll(xuLyDonQuery, sortOrder);
			Long xldId = results.get(0).getId();
			return repo.findOne(xldId);
		}
		return null;
	}
	
	public XuLyDon predFindXuLyDonCuoiCung(XuLyDonRepository repo, Long donId, Long donViXuLyXLD) {
		BooleanExpression xuLyDonQuery = base.and(xuLyDon.don.id.eq(donId)).and(QXuLyDon.xuLyDon.old.eq(false))
				.and(QXuLyDon.xuLyDon.trangThaiDon.eq(TrangThaiDonEnum.DANG_XU_LY));		

		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}

		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();
		if (repo.exists(xuLyDonQuery)) {
			List<XuLyDon> results = (List<XuLyDon>) repo.findAll(xuLyDonQuery, sortOrder);
			Long xldId = results.get(0).getId();
			return repo.findOne(xldId);
		}
		return null;
	}
	
	public XuLyDon predFindXuLyDonThuHoi(XuLyDonRepository repo, Long donId, Long donViXuLyXLD, Long canBoId) {
		BooleanExpression xuLyDonQuery = base.and(xuLyDon.don.id.eq(donId)).and(QXuLyDon.xuLyDon.old.eq(false))
				.and(QXuLyDon.xuLyDon.trangThaiDon.eq(TrangThaiDonEnum.DANG_XU_LY))
				.and(QXuLyDon.xuLyDon.canBoGiaoViec.id.eq(canBoId));		

		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}

		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();
		if (repo.exists(xuLyDonQuery)) {
			List<XuLyDon> results = (List<XuLyDon>) repo.findAll(xuLyDonQuery, sortOrder);
			Long xldId = results.get(0).getId();
			return repo.findOne(xldId);
		}
		return null;
	}
	
	public XuLyDon predFindXuLyDonCuoiCungHienTaiDonKhongQuyTrinh(XuLyDonRepository repo, Long donId, Long donViXuLyXLD) {
		BooleanExpression xuLyDonQuery = base.and(xuLyDon.don.id.eq(donId))
				.and(QXuLyDon.xuLyDon.old.eq(false))
				.and(QXuLyDon.xuLyDon.chucVu.isNull());
		
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}
		
		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();
		if (repo.exists(xuLyDonQuery)) {
			List<XuLyDon> results = (List<XuLyDon>) repo.findAll(xuLyDonQuery, sortOrder);
			Long xldId = results.get(0).getId();
			return repo.findOne(xldId);
		}
		return null;
	}
	
	public XuLyDon predFindXuLyDonCuoiCungHienTaiCuaTruongPhong(XuLyDonRepository repo, Long donId, Long donViXuLyXLD, Long phongBanXuLyXLD, VaiTroEnum vaiTro) {
		BooleanExpression xuLyDonQuery = base.and(xuLyDon.don.id.eq(donId))
				.and(QXuLyDon.xuLyDon.old.eq(false))
				.and(QXuLyDon.xuLyDon.chucVu.eq(vaiTro));
		
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}
		
		if ((phongBanXuLyXLD != null && phongBanXuLyXLD > 0)) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanXuLy.id.eq(phongBanXuLyXLD));
		}
		
		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();
		if (repo.exists(xuLyDonQuery)) {
			List<XuLyDon> results = (List<XuLyDon>) repo.findAll(xuLyDonQuery, sortOrder);
			Long xldId = results.get(0).getId();
			return repo.findOne(xldId);
		}
		return null;
	}

	public XuLyDon predFindThongTinXuLy(XuLyDonRepository repo, Long donId, Long donViXuLyXLD, Long phongBanXuLyXLD, Long canBoId, String chucVu, boolean isChuyenVienNhapLieu) {
		BooleanExpression xuLyDonQuery = base.and(xuLyDon.don.id.eq(donId))
				.and(QXuLyDon.xuLyDon.old.eq(false));
		if (chucVu.equals(VaiTroEnum.LANH_DAO.name()) || chucVu.equals(VaiTroEnum.VAN_THU.name())) {
			phongBanXuLyXLD = 0L;
		}
		CongChuc congChuc = congChucRepo.findOne(canBoId);
		if ((phongBanXuLyXLD != null && phongBanXuLyXLD > 0 ) && StringUtils.isNotBlank(chucVu)) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanXuLy.id.eq(phongBanXuLyXLD)
					.or(QXuLyDon.xuLyDon.phongBanXuLy.eq(congChuc.getCoQuanQuanLy().getDonVi())));
		}

		if (StringUtils.isNotEmpty(chucVu)) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.chucVu.eq(VaiTroEnum.valueOf(chucVu)).or(QXuLyDon.xuLyDon.chucVu2.eq(VaiTroEnum.valueOf(chucVu))));
		}

		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}
		
		if (StringUtils.isNotEmpty(chucVu) && StringUtils.equals(chucVu, VaiTroEnum.CHUYEN_VIEN.name())) {
			VaiTroEnum vaiTro = VaiTroEnum.valueOf(StringUtils.upperCase(chucVu));
			BooleanExpression qChucVu = QXuLyDon.xuLyDon.chucVu.eq(vaiTro).or(QXuLyDon.xuLyDon.chucVu2.eq(vaiTro));
			BooleanExpression qLuuTamCoLanhDao = QXuLyDon.xuLyDon.canBoXuLyChiDinh.id.ne(canBoId).and(qChucVu)
					.and(QXuLyDon.xuLyDon.don.currentState.type.eq(FlowStateEnum.BAT_DAU));
			BooleanExpression qGiaoViec = QXuLyDon.xuLyDon.canBoXuLyChiDinh.id.eq(canBoId);
			BooleanExpression qLuuTamKhongLanhDao = QXuLyDon.xuLyDon.canBoXuLyChiDinh.isNull().and(qChucVu);
			BooleanExpression qXuLy = QXuLyDon.xuLyDon.congChuc.id.eq(canBoId).and(qChucVu);
			if (isChuyenVienNhapLieu) {
				xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.chucVu.isNull()
						.or(qLuuTamCoLanhDao)
						.or(qXuLy)
						.or(qGiaoViec)
						.or(qLuuTamKhongLanhDao));
			} else {
				xuLyDonQuery = xuLyDonQuery.and(qGiaoViec);
			}
		}	
		OrderSpecifier<Integer> sortOrder = QXuLyDon.xuLyDon.thuTuThucHien.desc();

		if (repo.exists(xuLyDonQuery)) {
			List<XuLyDon> results = (List<XuLyDon>) repo.findAll(xuLyDonQuery, sortOrder);
			Long xldId = results.get(0).getId();
			return repo.findOne(xldId);
		}
		return null;
	}

	public Predicate predFindLichSuXLD(XuLyDonRepository repo, Long donId, Long donViXuLyXLD, Long phongBanXuLyXLD,
			Long congChucId) {
		BooleanExpression xuLyDonQuery = base.and(xuLyDon.don.id.eq(donId)).and(QXuLyDon.xuLyDon.old.eq(true))
				.and(QXuLyDon.xuLyDon.trangThaiDon.eq(TrangThaiDonEnum.DA_XU_LY));
		if (phongBanXuLyXLD != null && phongBanXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.phongBanXuLy.id.eq(phongBanXuLyXLD));
		}
		if (donViXuLyXLD != null && donViXuLyXLD > 0) {
			xuLyDonQuery = xuLyDonQuery.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(donViXuLyXLD));
		}
		return xuLyDonQuery;
	}
	
	public Predicate predFindChucVuIsNull(Long donId, Long phongBanId, Long donViId) {
		BooleanExpression predicate = base.and(xuLyDon.don.id.eq(donId));
		predicate = predicate.and(xuLyDon.chucVu.isNull()).and(xuLyDon.phongBanXuLy.id.eq(phongBanId))
				.and(xuLyDon.donViXuLy.id.eq(donViId));
		return predicate;
	}

	public Predicate predFindOld(Long donId, Long phongBanId, Long donViId, VaiTroEnum vaiTro) {
		BooleanExpression predicate = base.and(xuLyDon.don.id.eq(donId));
		predicate = predicate.and(xuLyDon.chucVu.eq(vaiTro)).and(xuLyDon.phongBanXuLy.id.eq(phongBanId))
				.and(xuLyDon.donViXuLy.id.eq(donViId));
		return predicate;
	}

	public Predicate predFindChuyenVienOld(Long donId, Long canBoId, Long phongBanId, Long donViId, VaiTroEnum vaiTro) {
		BooleanExpression predicate = base.and(xuLyDon.don.id.eq(donId));
		predicate = predicate.and(xuLyDon.chucVu.eq(vaiTro))
				.and(xuLyDon.congChuc.id.eq(canBoId)
						.or(xuLyDon.canBoXuLyChiDinh.id.eq(canBoId)))
				.and(xuLyDon.phongBanXuLy.id.eq(phongBanId)).and(xuLyDon.donViXuLy.id.eq(donViId));
		return predicate;
	}

	public Predicate predFindLanhDaoVanThuOld(VaiTroEnum vaiTro, Long donId, Long donViId) {
		BooleanExpression predicate = base.and(xuLyDon.don.id.eq(donId));
		predicate = predicate.and(xuLyDon.chucVu.eq(vaiTro)).and(xuLyDon.donViXuLy.id.eq(donViId));
		return predicate;
	}

	public Predicate predicateFindOne(Long id) {
		return base.and(QXuLyDon.xuLyDon.id.eq(id));
	}

	public boolean isExists(XuLyDonRepository repo, Long id) {
		Predicate predicate = base.and(QXuLyDon.xuLyDon.don.id.eq(id));
		return repo.exists(predicate);
	}

	public XuLyDon save(XuLyDon obj, Long congChucId) {
		return Utils.save(xuLyDonRepo, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(XuLyDon obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(xuLyDonRepo, obj, congChucId, eass, status);		
	}
	
}
