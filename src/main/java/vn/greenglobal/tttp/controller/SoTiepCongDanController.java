package vn.greenglobal.tttp.controller;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.OrderSpecifier;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.HuongGiaiQuyetTCDEnum;
import vn.greenglobal.tttp.enums.HuongXuLyTCDEnum;
import vn.greenglobal.tttp.enums.LoaiTiepDanEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.model.CoQuanToChucTiepDan;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.repository.CoQuanToChucTiepDanRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.service.SoTiepCongDanService;
import vn.greenglobal.tttp.util.ExcelUtil;
import vn.greenglobal.tttp.util.Utils;
import vn.greenglobal.tttp.util.WordUtil;

@RestController
@RepositoryRestController
@Api(value = "soTiepCongDans", description = "Sổ tiếp công dân")
public class SoTiepCongDanController extends TttpController<SoTiepCongDan> {

	@Autowired
	private SoTiepCongDanRepository repo;

	@Autowired
	private SoTiepCongDanService soTiepCongDanService;

	@Autowired
	private DonRepository repoDon;

	@Autowired
	private DonService donService;

	@Autowired
	private PagedResourcesAssembler<Don> assemblerDon;

	@Autowired
	private CoQuanToChucTiepDanRepository repoCoQuanToChucTiepDan;

	public SoTiepCongDanController(BaseRepository<SoTiepCongDan, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans")
	@ApiOperation(value = "Lấy danh sách Tiếp Công Dân", position = 1, produces = MediaType.APPLICATION_JSON_VALUE, response = SoTiepCongDan.class)
	public @ResponseBody Object getDanhSachTiepCongDans(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "phanLoaiDon", required = false) String phanLoaiDon,
			@RequestParam(value = "huongXuLy", required = false) String huongXuLy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "loaiTiepCongDan", required = false) String loaiTiepCongDan,
			PersistentEntityResourceAssembler eass) {
		
		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_LIETKE) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		Page<SoTiepCongDan> page = repo.findAll(soTiepCongDanService.predicateFindAllTCD(tuKhoa, phanLoaiDon, huongXuLy,
				tuNgay, denNgay, loaiTiepCongDan), pageable);

		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans/{id}")
	@ApiOperation(value = "Lấy Tiếp Công Dân theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy lượt Tiếp Công Dân thành công", response = SoTiepCongDan.class) })
	public ResponseEntity<Object> getSoTiepCongDans(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_XEM) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		SoTiepCongDan soTiepCongDan = repo.findOne(soTiepCongDanService.predicateFindOne(id));
		if (soTiepCongDan == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(soTiepCongDan), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/soTiepCongDans")
	@ApiOperation(value = "Thêm mới Sổ Tiếp Công Dân", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Sổ Tiếp Công Dân thành công", response = SoTiepCongDan.class),
			@ApiResponse(code = 201, message = "Thêm mới Sổ Tiếp Công Dân thành công", response = SoTiepCongDan.class) })
	public ResponseEntity<Object> createSoTiepCongDan(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody SoTiepCongDan soTiepCongDan, PersistentEntityResourceAssembler eass) {
		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_THEM) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		
		if (soTiepCongDan != null && !soTiepCongDan.getCoQuanToChucTiepDans().isEmpty()) {
			for (CoQuanToChucTiepDan coQuanToChucTiepDan : soTiepCongDan.getCoQuanToChucTiepDans()) {
				Utils.save(repoCoQuanToChucTiepDan, coQuanToChucTiepDan,
						new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			}
		}
		Don don = repoDon.findOne(soTiepCongDan.getDon().getId());
		soTiepCongDan.setDon(don);
		
		if (LoaiTiepDanEnum.DINH_KY.equals(soTiepCongDan.getLoaiTiepDan())) {
			soTiepCongDan.setHuongGiaiQuyetTCDLanhDao(HuongGiaiQuyetTCDEnum.KHOI_TAO);
			soTiepCongDan.getDon().setThanhLapTiepDanGapLanhDao(true);
		}
		
		if (LoaiTiepDanEnum.DINH_KY.equals(soTiepCongDan.getLoaiTiepDan())
				|| LoaiTiepDanEnum.DOT_XUAT.equals(soTiepCongDan.getLoaiTiepDan())) {
			soTiepCongDan.setHuongXuLy(HuongXuLyTCDEnum.KHOI_TAO);
			if (soTiepCongDan.getHuongGiaiQuyetTCDLanhDao() == null) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, "HUONGGIAIQUYET_REQUIRED",
						"Hướng giải quyết không được để trống!");
			}
			if (soTiepCongDan.isHoanThanhTCDLanhDao()) {
				soTiepCongDan.getDon().setDaXuLy(true);
				soTiepCongDan.getDon().setDaGiaiQuyet(true);
			} else {
				soTiepCongDan.getDon().setDaXuLy(true);
			}
		} else if (LoaiTiepDanEnum.THUONG_XUYEN.equals(soTiepCongDan.getLoaiTiepDan())) {
			int soLuotTiep = soTiepCongDan.getDon().getTongSoLuotTCD();
			soTiepCongDan.setSoThuTuLuotTiep(soLuotTiep + 1);
			soTiepCongDan.getDon().setTongSoLuotTCD(soLuotTiep + 1);
			if (HuongXuLyTCDEnum.YEU_CAU_GAP_LANH_DAO.equals(soTiepCongDan.getHuongXuLy())) {
				soTiepCongDan.getDon().setYeuCauGapTrucTiepLanhDao(true);
			}
		}

		ResponseEntity<Object> output = Utils.doSave(repo, soTiepCongDan,
				new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
				HttpStatus.CREATED);
		if (output.getStatusCode().equals(HttpStatus.CREATED)) {
			Utils.save(repoDon, soTiepCongDan.getDon(),
					new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
		}
		return output;
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/soTiepCongDan/{id}")
	@ApiOperation(value = "Cập nhật Sổ Tiếp Công Dân", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Sổ Tiếp Công Dân thành công", response = SoTiepCongDan.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody SoTiepCongDan soTiepCongDan, PersistentEntityResourceAssembler eass) {
		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_SUA) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		soTiepCongDan.setId(id);
		for (CoQuanToChucTiepDan coQuanToChucTiepDan : soTiepCongDan.getCoQuanToChucTiepDans()) {
			Utils.save(repoCoQuanToChucTiepDan, coQuanToChucTiepDan,
					new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
		}

		if (soTiepCongDan.getDon() != null) {
			Don don = repoDon.findOne(soTiepCongDan.getDon().getId());
			soTiepCongDan.setDon(don);
		}
		
		if (LoaiTiepDanEnum.DINH_KY.equals(soTiepCongDan.getLoaiTiepDan())) {
			soTiepCongDan.getDon().setThanhLapTiepDanGapLanhDao(true);
		}

		if (LoaiTiepDanEnum.DINH_KY.equals(soTiepCongDan.getLoaiTiepDan())
				|| LoaiTiepDanEnum.DOT_XUAT.equals(soTiepCongDan.getLoaiTiepDan())) {
			if (soTiepCongDan.getHuongGiaiQuyetTCDLanhDao() == null) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, "HUONGGIAIQUYET_REQUIRED",
						"Hướng giải quyết không được để trống!");
			}
			if (soTiepCongDan.isHoanThanhTCDLanhDao()) {
				soTiepCongDan.getDon().setDaGiaiQuyet(true);
			}
		} else if (LoaiTiepDanEnum.THUONG_XUYEN.equals(soTiepCongDan.getLoaiTiepDan())) {
			soTiepCongDan.setHuongXuLy(HuongXuLyTCDEnum.KHOI_TAO);
			if (HuongXuLyTCDEnum.YEU_CAU_GAP_LANH_DAO.equals(soTiepCongDan.getHuongXuLy())) {
				soTiepCongDan.getDon().setYeuCauGapTrucTiepLanhDao(true);
			}
		}

		ResponseEntity<Object> output = Utils.doSave(repo, soTiepCongDan,
				new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
				HttpStatus.CREATED);
		if (output.getStatusCode().equals(HttpStatus.CREATED)) {
			Utils.save(repoDon, soTiepCongDan.getDon(),
					new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
		}
		return output;
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/soTiepCongDans/{id}")
	@ApiOperation(value = "Xoá Sổ Tiếp Công Dân", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Sổ Tiếp Công Dân thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {
		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_XOA) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		
		SoTiepCongDan soTiepCongDan = repo.findOne(soTiepCongDanService.predicateFindOne(id));
		if (soTiepCongDan == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		soTiepCongDan.setDaXoa(true);
		if (LoaiTiepDanEnum.THUONG_XUYEN.equals(soTiepCongDan.getLoaiTiepDan())) {
			Don don = soTiepCongDan.getDon();
			
			if (soTiepCongDan.getSoThuTuLuotTiep() < don.getTongSoLuotTCD()) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.DATA_INVALID.name(),
						ApiErrorEnum.DATA_INVALID.getText());
			}
			int tongSoLuotTCD = don.getTongSoLuotTCD();
			don.setTongSoLuotTCD(tongSoLuotTCD - 1);
			Utils.save(repoDon, don,
					new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
		}		
		Utils.save(repo, soTiepCongDan,
				new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/hoSoVuViecYeuCauGapLanhDaos")
	@ApiOperation(value = "Lấy danh sách Hồ Sơ Vụ Việc Yêu Cầu Gặp Lãnh Đạo", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListHoSoVuViecYeuCauGapLanhDao(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay, PersistentEntityResourceAssembler eass) {
		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_LIETKE) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		Page<Don> page = repoDon.findAll(donService.predicateFindDonYeuCauGapLanhDao(tuNgay, denNgay), pageable);
		return assemblerDon.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/soTiepCongDans/{id}/huyCuocTiepDanDinhKyCuaLanhDao")
	@ApiOperation(value = "Xoá Sổ Tiếp Công Dân", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Sổ Tiếp Công Dân thành công") })
	public ResponseEntity<Object> cancelCuocTiepDanDinhKyCuaLanhDao(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {
		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.SOTIEPCONGDAN_XOA) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		SoTiepCongDan soTiepCongDan = soTiepCongDanService.cancelCuocTiepDanDinhKyCuaLanhDao(repo, id);
		if (soTiepCongDan == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		Utils.save(repo, soTiepCongDan,
				new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans/inPhieuHen")
	@ApiOperation(value = "In phiếu hẹn", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportWord(@RequestParam(value = "hoVaTen", required = false) String hoVaTen,
			@RequestParam(value = "soCMND", required = false) String soCMND,
			@RequestParam(value = "ngayCap", required = false) String ngayCap,
			@RequestParam(value = "noiCap", required = false) String noiCap,
			@RequestParam(value = "diaChi", required = false) String diaChi,
			@RequestParam(value = "diaDiemTiepCongDan", required = false) String diaDiemTiepCongDan,
			@RequestParam(value = "thoiGianTiepCongDan", required = false) String thoiGianTiepCongDan,
			@RequestParam(value = "ngayHenTiepCongDan", required = false) String ngayHenTiepCongDan,
			HttpServletResponse response) {

		HashMap<String, String> mappings = new HashMap<String, String>();
		mappings.put("hoVaTen", hoVaTen);
		mappings.put("soCMND", soCMND);
		mappings.put("ngayCap", ngayCap);
		mappings.put("noiCap", noiCap);
		mappings.put("diaChi", diaChi);
		mappings.put("diaDiemTiepCongDan", diaDiemTiepCongDan);
		mappings.put("thoiGianTiepCongDan", thoiGianTiepCongDan);
		mappings.put("thoiGianHen", ngayHenTiepCongDan);
		WordUtil.exportWord(response, getClass().getClassLoader().getResource("word/tiepcongdan/TCD_PHIEU_HEN.doc").getFile(), mappings);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans/excel")
	@ApiOperation(value = "Xuất file excel", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcel(HttpServletResponse response,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "loaiTiepCongDan", required = true) String loaiTiepCongDan) throws IOException {
		OrderSpecifier<LocalDateTime> order = QSoTiepCongDan.soTiepCongDan.ngayTiepDan.desc();
		if (LoaiTiepDanEnum.THUONG_XUYEN.name().equals(loaiTiepCongDan)) {
			ExcelUtil.exportDanhSachTiepDanThuongXuyen(response,
					"fileName", "sheetName", (List<SoTiepCongDan>) repo.findAll(soTiepCongDanService
							.predicateFindAllTCD("", null, null, tuNgay, denNgay, loaiTiepCongDan), order),
					"Danh sách sổ tiếp dân");
		} else if (LoaiTiepDanEnum.DINH_KY.name().equals(loaiTiepCongDan) || LoaiTiepDanEnum.DOT_XUAT.name().equals(loaiTiepCongDan)) {
			ExcelUtil.exportDanhSachTiepDanLanhDao(response, "fileName",
					"sheetName", (List<SoTiepCongDan>) repo.findAll(soTiepCongDanService.predicateFindAllTCD("",
							null, null, tuNgay, denNgay, loaiTiepCongDan), order),
					"Danh sách sổ tiếp dân định kỳ");
		}
	}
}
