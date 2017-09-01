package vn.greenglobal.tttp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.DoiTuongThayDoiEnum;
import vn.greenglobal.tttp.enums.KetQuaTrangThaiDonEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.LichSuThayDoi;
import vn.greenglobal.tttp.model.PropertyChangeObject;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.ThongTinGiaiQuyetDonRepository;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.service.LichSuThayDoiService;
import vn.greenglobal.tttp.service.ThongTinGiaiQuyetDonService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "thongTinGiaiQuyetDons", description = "Thông tin giải quyết đơn")
public class ThongTinGiaiQuyetDonController extends TttpController<ThongTinGiaiQuyetDon> {

	@Autowired
	private ThongTinGiaiQuyetDonRepository repo;

	@Autowired
	private ThongTinGiaiQuyetDonService thongTinGiaiQuyetDonService;

	@Autowired
	private DonRepository donRepo;
	
	@Autowired
	private LichSuThayDoiService lichSuThayDoiService;
	
	@Autowired
	private DonService donService;

	public ThongTinGiaiQuyetDonController(BaseRepository<ThongTinGiaiQuyetDon, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/thongTinGiaiQuyetDons/{id}")
	@ApiOperation(value = "Cập nhật thông tin Giải quyết đơn", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật thông tin Giải quyết đơn thành công", response = ThongTinGiaiQuyetDon.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody ThongTinGiaiQuyetDon thongTinGiaiQuyetDon, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			thongTinGiaiQuyetDon.setId(id);
			if (!thongTinGiaiQuyetDonService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			checkDataThongTinGiaiQuyetDon(thongTinGiaiQuyetDon);
			Don don = donRepo.findOne(donService.predicateFindOne(thongTinGiaiQuyetDon.getDon().getId()));
			don.setDonViThamTraXacMinh(thongTinGiaiQuyetDon.getDonViThamTraXacMinh());
			if (thongTinGiaiQuyetDon.getKetQuaXLDGiaiQuyet() != null) {
				if (KetQuaTrangThaiDonEnum.DA_CO_QUYET_DINH_GIAI_QUYET.equals(thongTinGiaiQuyetDon.getKetQuaXLDGiaiQuyet()) 
						&& thongTinGiaiQuyetDon.getNgayKetThucGiaiQuyet() == null) {
					thongTinGiaiQuyetDon.setNgayKetThucGiaiQuyet(Utils.localDateTimeNow());
				}
			}
			
			if (don.getKetQuaXLDGiaiQuyet() != null && don.getKetQuaXLDGiaiQuyet().equals(KetQuaTrangThaiDonEnum.YEU_CAU_GAP_LANH_DAO)) { 
				System.out.println("@@");
				don.setKetQuaXLDGiaiQuyet(KetQuaTrangThaiDonEnum.YEU_CAU_GAP_LANH_DAO);
			} else { 
				don.setKetQuaXLDGiaiQuyet(thongTinGiaiQuyetDon.getKetQuaXLDGiaiQuyet());
			}
			
			donService.save(don, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			
			ThongTinGiaiQuyetDon thongTinOld = repo.findOne(thongTinGiaiQuyetDonService.predicateFindOne(id));
			List<PropertyChangeObject> listThayDoi = thongTinGiaiQuyetDonService.getListThayDoi(thongTinGiaiQuyetDon, thongTinOld);
			if (listThayDoi.size() > 0) {
				LichSuThayDoi lichSu = new LichSuThayDoi();
				lichSu.setDoiTuongThayDoi(DoiTuongThayDoiEnum.DON);
				lichSu.setIdDoiTuong(thongTinOld.getDon().getId());
				lichSu.setNoiDung("Cập nhật thông tin giải quyết đơn");
				lichSu.setChiTietThayDoi(getChiTietThayDoi(listThayDoi));
				lichSuThayDoiService.save(lichSu, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			}
			
			return thongTinGiaiQuyetDonService.doSave(thongTinGiaiQuyetDon, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thongTinGiaiQuyetDons/{id}")
	@ApiOperation(value = "Lấy Thông tin giải quyết đơn theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Thông tin giải quyết đơn thành công", response = ThongTinGiaiQuyetDon.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.GIAIQUYETDON_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			ThongTinGiaiQuyetDon thongTinGiaiQuyetDon = repo.findOne(thongTinGiaiQuyetDonService.predicateFindOne(id));
			if (thongTinGiaiQuyetDon == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(eass.toFullResource(thongTinGiaiQuyetDon), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	private ThongTinGiaiQuyetDon checkDataThongTinGiaiQuyetDon(ThongTinGiaiQuyetDon thongTinGiaiQuyetDon) {
		if (!thongTinGiaiQuyetDon.isLapToDoanXacMinh()) {
			thongTinGiaiQuyetDon.setSoQuyetDinhThanhLapDTXM("");
			thongTinGiaiQuyetDon.setTruongDoanTTXM(null);
		}
		if (!thongTinGiaiQuyetDon.isDoiThoai()) {
			thongTinGiaiQuyetDon.setThoiGianDoiThoai(null);
			thongTinGiaiQuyetDon.setDiaDiemDoiThoai("");
		}
		if (!thongTinGiaiQuyetDon.isGiaoCoQuanDieuTra()) {
			thongTinGiaiQuyetDon.setCoQuanDieuTra(null);
			thongTinGiaiQuyetDon.setSoVuGiaoCoQuanDieuTra(0);
			thongTinGiaiQuyetDon.setSoDoiTuongGiaoCoQuanDieuTra(0);
		}
		if (!thongTinGiaiQuyetDon.isQuyetDinhGiaiQuyetKhieuNai()) {
			thongTinGiaiQuyetDon.setKetLuanNoiDungKhieuNai(null);
			thongTinGiaiQuyetDon.setSoVuGiaiQuyetKhieuNai(0);
			thongTinGiaiQuyetDon.setTienPhaiThuNhaNuocQDGQ(0l);
			thongTinGiaiQuyetDon.setDatPhaiThuNhaNuocQDGQ(0l);
			thongTinGiaiQuyetDon.setTienPhaiTraCongDanQDGQ(0l);
			thongTinGiaiQuyetDon.setDatPhaiTraCongDanQDGQ(0l);
			thongTinGiaiQuyetDon.setTongSoNguoiXuLyHanhChinh(0);
			thongTinGiaiQuyetDon.setSoNguoiDaBiXuLyHanhChinh(0);
			thongTinGiaiQuyetDon.setSoNguoiDuocTraLaiQuyenLoi(0);
			thongTinGiaiQuyetDon.setSoLanGiaiQuyetLai(0);
			thongTinGiaiQuyetDon.setKetQuaGiaiQuyetLan2(null);
			thongTinGiaiQuyetDon.setNoiDungKetLuanGiaiQuyetLai("");
			thongTinGiaiQuyetDon.setKetQuaGiaiQuyetKhac("");
		}
		if (!thongTinGiaiQuyetDon.isTheoDoiThucHien()) {
			thongTinGiaiQuyetDon.setHinhThucTheoDoi(null);
			thongTinGiaiQuyetDon.setCoQuanTheoDoi(null);
			thongTinGiaiQuyetDon.setKetQuaThucHienTheoDoi(null);
			thongTinGiaiQuyetDon.setTienPhaiThuNhaNuoc(0l);
			thongTinGiaiQuyetDon.setDatPhaiThuNhaNuoc(0l);
			thongTinGiaiQuyetDon.setTienPhaiTraCongDan(0l);
			thongTinGiaiQuyetDon.setDatPhaiTraCongDan(0l);
			thongTinGiaiQuyetDon.setTienDaThuNhaNuoc(0l);
			thongTinGiaiQuyetDon.setDatDaThuNhaNuoc(0l);
			thongTinGiaiQuyetDon.setTienDaTraCongDan(0l);
			thongTinGiaiQuyetDon.setDatDaTraCongDan(0l);
			thongTinGiaiQuyetDon.setKetQuaThucHienKhac("");
		}
		if (!thongTinGiaiQuyetDon.isKhoiTo()) {
			thongTinGiaiQuyetDon.setSoVuBiKhoiTo(0);
			thongTinGiaiQuyetDon.setSoDoiTuongBiKhoiTo(0);
		}
		return thongTinGiaiQuyetDon;
	}
}
