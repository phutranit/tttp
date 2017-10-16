package vn.greenglobal.tttp.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
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
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.model.DonViNgoaiHeThong;
import vn.greenglobal.tttp.repository.DonViNgoaiHeThongRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.DonViNgoaiHeThongService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "donViNgoaiHeThongs", description = "Đơn Vị Ngoài Hệ Thống")
public class DonViNgoaiHeThongController extends TttpController<DonViNgoaiHeThong> {

	@Autowired
	private DonViNgoaiHeThongRepository repo;

	@Autowired
	private DonViNgoaiHeThongService donViNgoaiHeThongService;
	
	@Autowired
	private XuLyDonRepository xuLyDonRepository;
	
	public DonViNgoaiHeThongController(BaseRepository<DonViNgoaiHeThong, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/donViNgoaiHeThongs/danhSachDonViTimKiems")
	@ApiOperation(value = "Lấy danh sách Đơn Vị Ngoài Hệ Thống", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "ten", required = false) String ten,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DONVINGOAIHETHONG_LIETKE) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DONVINGOAIHETHONG_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			Page<DonViNgoaiHeThong> page = repo.findAll(donViNgoaiHeThongService.predicateFindAll(ten),
					pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/donViNgoaiHeThongs")
	@ApiOperation(value = "Lấy danh sách tất cả Đơn Vị Ngoài Hệ Thống", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DONVINGOAIHETHONG_LIETKE) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DONVINGOAIHETHONG_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Page<DonViNgoaiHeThong> page = repo.findAll(donViNgoaiHeThongService.predicateFindAll(), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/donViNgoaiHeThongs")
	@ApiOperation(value = "Thêm mới Đơn Vị Ngoài Hệ Thống", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Đơn Vị Ngoài Hệ Thống thành công", response = DonViNgoaiHeThong.class),
			@ApiResponse(code = 201, message = "Thêm mới Đơn Vị Ngoài Hệ Thống thành công", response = DonViNgoaiHeThong.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody DonViNgoaiHeThong donViNgoaiHeThong, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DONVINGOAIHETHONG_THEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			if (StringUtils.isNotBlank(donViNgoaiHeThong.getTen()) && donViNgoaiHeThongService.checkExistsData(repo, donViNgoaiHeThong)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(), ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.TEN_EXISTS.getText());
			}

			donViNgoaiHeThong.setTenSearch(Utils.unAccent(donViNgoaiHeThong.getTen().trim()));
			return donViNgoaiHeThongService.doSave(donViNgoaiHeThong,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/donViNgoaiHeThongs/{id}")
	@ApiOperation(value = "Lấy Đơn Vị Ngoài Hệ Thống theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Đơn Vị Ngoài Hệ Thống thành công", response = DonViNgoaiHeThong.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DONVINGOAIHETHONG_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			DonViNgoaiHeThong donViNgoaiHeThong = repo.findOne(donViNgoaiHeThongService.predicateFindOne(id));
			if (donViNgoaiHeThong == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(eass.toFullResource(donViNgoaiHeThong), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/donViNgoaiHeThongs/{id}")
	@ApiOperation(value = "Cập nhật Đơn Vị Ngoài Hệ Thống", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Đơn Vị Ngoài Hệ Thống thành công", response = DonViNgoaiHeThong.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody DonViNgoaiHeThong donViNgoaiHeThong, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DONVINGOAIHETHONG_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			donViNgoaiHeThong.setId(id);
			if (StringUtils.isNotBlank(donViNgoaiHeThong.getTen()) && donViNgoaiHeThongService.checkExistsData(repo, donViNgoaiHeThong)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(), ApiErrorEnum.TEN_EXISTS.getText(), ApiErrorEnum.TEN_EXISTS.getText());
			}
			
			if (!donViNgoaiHeThongService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			donViNgoaiHeThong.setTenSearch(Utils.unAccent(donViNgoaiHeThong.getTen().trim()));
			return donViNgoaiHeThongService.doSave(donViNgoaiHeThong,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/donViNgoaiHeThongs/{id}")
	@ApiOperation(value = "Xoá Đơn Vị Ngoài Hệ Thống", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Đơn Vị Ngoài Hệ Thống thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DONVINGOAIHETHONG_XOA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (donViNgoaiHeThongService.checkUsedData(repo, xuLyDonRepository, id)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_USED.name(),
						ApiErrorEnum.DATA_USED.getText(), ApiErrorEnum.DATA_USED.getText());
			}

			DonViNgoaiHeThong donViNgoaiHeThong = donViNgoaiHeThongService.delete(repo, id);
			if (donViNgoaiHeThong == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			donViNgoaiHeThongService.save(donViNgoaiHeThong,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
}
