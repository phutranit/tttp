package vn.greenglobal.tttp.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.DoiTuongThayDoiEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.model.CongDan;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.LichSuThayDoi;
import vn.greenglobal.tttp.model.PropertyChangeObject;
import vn.greenglobal.tttp.repository.CongDanRepository;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.LichSuThayDoiRepository;
import vn.greenglobal.tttp.service.CongDanService;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.service.LichSuThayDoiService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "congDans", description = "Công Dân")
public class CongDanController extends TttpController<CongDan> {

	@Autowired
	private CongDanRepository repo;

	@Autowired
	private LichSuThayDoiRepository repoLichSu;

	@Autowired
	private CongDanService congDanService;

	@Autowired
	private DonCongDanRepository donCongDanRepository;

	@Autowired
	private DonRepository donRepository;

	@Autowired
	private DonService donService;

	@Autowired
	protected PagedResourcesAssembler<Don> donAssembler;

	@Autowired
	private LichSuThayDoiService lichSuThayDoiService;

	@Autowired
	protected PagedResourcesAssembler<LichSuThayDoi> lichSuThayDoiAssembler;

	public CongDanController(BaseRepository<CongDan, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/congDans")
	@ApiOperation(value = "Lấy danh sách Công Dân", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "tinhThanh", required = false) Long tinhThanh,
			@RequestParam(value = "quanHuyen", required = false) Long quanHuyen,
			@RequestParam(value = "phuongXa", required = false) Long phuongXa,
			@RequestParam(value = "toDanPho", required = false) Long toDanPho, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGDAN_LIETKE) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGDAN_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Page<CongDan> page = repo
					.findAll(congDanService.predicateFindAll(tuKhoa, tinhThanh, quanHuyen, phuongXa, toDanPho), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/congDans")
	@ApiOperation(value = "Thêm mới Công Dân", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Công Dân thành công", response = CongDan.class),
			@ApiResponse(code = 201, message = "Thêm mới Công Dân thành công", response = CongDan.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody CongDan congDan, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGDAN_THEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (StringUtils.isNotBlank(congDan.getHoVaTen()) && StringUtils.isNotBlank(congDan.getDiaChi())
					&& StringUtils.isNotBlank(congDan.getSoCMNDHoChieu())) {
				CongDan congDanExists = repo.findOne(congDanService.predicateFindCongDanExists(congDan.getHoVaTen(),
						congDan.getSoCMNDHoChieu(), congDan.getDiaChi()));
				if (congDanExists != null) {
					return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.CONGDAN_EXISTS.name(),
							ApiErrorEnum.CONGDAN_EXISTS.getText());
				}
			}

			if (StringUtils.isNotBlank(congDan.getHoVaTen()) && StringUtils.isNotBlank(congDan.getDiaChi())
					&& StringUtils.isNotBlank(congDan.getSoCMNDHoChieu())) {
				CongDan congDanExists = repo.findOne(congDanService.predicateFindCongDanExists(congDan.getHoVaTen(),
						congDan.getSoCMNDHoChieu(), congDan.getDiaChi()));
				if (congDanExists != null) {
					return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.CONGDAN_EXISTS.name(),
							ApiErrorEnum.CONGDAN_EXISTS.getText());
				}
			}

			return Utils.doSave(repo, congDan,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/congDanBySuggests")
	@ApiOperation(value = "Lấy danh sách Suggest Công Dân", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getCongDanBySuggests(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "soCMNND", required = false) String soCMNND,
			@RequestParam(value = "diaChi", required = false) String diaChi, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGDAN_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Page<CongDan> page = repo.findAll(congDanService.predicateFindCongDanBySuggests(tuKhoa, soCMNND, diaChi),
					pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/congDanExists")
	@ApiOperation(value = "Kiểm tra công dân tồn tại", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getCongDanExists(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "soCMND", required = false) String soCMND,
			@RequestParam(value = "diaChi", required = false) String diaChi, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGDAN_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (StringUtils.isNotBlank(tuKhoa) && StringUtils.isNotBlank(soCMND) && StringUtils.isNotBlank(diaChi)) {
				List<CongDan> congDanExists = (List<CongDan>) repo
						.findAll(congDanService.predicateFindCongDanExists(tuKhoa, soCMND, diaChi));
				if (!congDanExists.isEmpty()) {
					return new ResponseEntity<>(eass.toFullResource(congDanExists.get(0)), HttpStatus.OK);
				}
			}
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cmndExists")
	@ApiOperation(value = "Kiểm tra CMND tồn tại", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getCongDanExists(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "soCMND", required = false) String soCMND, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGDAN_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (StringUtils.isNotBlank(soCMND)) {
				List<CongDan> congDanExists = (List<CongDan>) repo
						.findAll(congDanService.predicateFindCongDanExists(soCMND));
				if (!congDanExists.isEmpty()) {
					return new ResponseEntity<>(eass.toFullResource(congDanExists.get(0)), HttpStatus.OK);
				}
			}
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "/congDans/{id}/danhSachDons")
	@ApiOperation(value = "Lấy Danh sách đơn của Công Dân theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Danh sách đơn của Công dân thành công", response = Don.class) })
	public Object getDanhSachDonsByCongDan(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			Page<Don> page = donRepository.findAll(donService.predicateFindByCongDan(id), pageable);
			return donAssembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/congDans/{id}")
	@ApiOperation(value = "Lấy Công Dân theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Công Dân thành công", response = CongDan.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGDAN_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			CongDan congDan = repo.findOne(congDanService.predicateFindOne(id));
			if (congDan == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(eass.toFullResource(congDan), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(method = RequestMethod.GET, value = "/congDans/{id}/lichSus")
	@ApiOperation(value = "Lấy Lịch sử thay đổi của Công Dân theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Lịch sử thay đổi của Công Dân thành công", response = LichSuThayDoi.class) })
	public Object getLichSuThayDoiCongDan(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {
		
		try {
			Page<LichSuThayDoi> page = repoLichSu.findAll(lichSuThayDoiService.predicateFindAll(DoiTuongThayDoiEnum.CONG_DAN, id), pageable);
			return lichSuThayDoiAssembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/congDans/{id}/lichSus/{idLichSu}")
	@ApiOperation(value = "Lấy Công Dân theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Công Dân thành công", response = LichSuThayDoi.class) })
	public ResponseEntity<Object> getLichSuById(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@PathVariable("idLichSu") long idLichSu, PersistentEntityResourceAssembler eass) {

		try {
			LichSuThayDoi lichSuThayDoi = repoLichSu.findOne(lichSuThayDoiService.predicateFindOne(idLichSu));
			if (lichSuThayDoi == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(eass.toFullResource(lichSuThayDoi), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/congDans/{id}")
	@ApiOperation(value = "Cập nhật Công Dân", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cập nhật Công Dân thành công", response = CongDan.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody CongDan congDan, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGDAN_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			congDan.setId(id);
			if (!congDanService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			if (StringUtils.isNotBlank(congDan.getHoVaTen()) && StringUtils.isNotBlank(congDan.getDiaChi())
					&& StringUtils.isNotBlank(congDan.getSoCMNDHoChieu())) {
				CongDan congDanExists = repo.findOne(congDanService.predicateFindCongDanExists(congDan.getHoVaTen(),
						congDan.getSoCMNDHoChieu(), congDan.getDiaChi()));
				if (congDanExists != null && id != congDanExists.getId().longValue()) {
					return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.CONGDAN_EXISTS.name(),
							ApiErrorEnum.CONGDAN_EXISTS.getText());
				}
			}
			CongDan congDanOld = repo.findOne(congDanService.predicateFindOne(id));
			List<PropertyChangeObject> listThayDoi = congDanService.getListThayDoi(congDan, congDanOld);
			LichSuThayDoi lichSu = new LichSuThayDoi();
			lichSu.setDoiTuongThayDoi(DoiTuongThayDoiEnum.CONG_DAN);
			lichSu.setIdDoiTuong(id);
			lichSu.setNoiDung("Cập nhật thông tin công dân " + congDanOld.getHoVaTen());
			lichSu.setChiTietThayDoi(getChiTietThayDoi(listThayDoi));
			Utils.save(repoLichSu, lichSu,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return Utils.doSave(repo, congDan,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/congDans/{id}")
	@ApiOperation(value = "Xoá Công Dân", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Công Dân thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGDAN_XOA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (congDanService.checkUsedData(donCongDanRepository, id)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_USED.name(),
						ApiErrorEnum.DATA_USED.getText());
			}

			CongDan congDan = congDanService.delete(repo, id);
			if (congDan == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			Utils.save(repo, congDan,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}
}
