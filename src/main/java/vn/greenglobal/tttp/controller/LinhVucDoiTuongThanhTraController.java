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
import vn.greenglobal.tttp.model.LinhVucDoiTuongThanhTra;
import vn.greenglobal.tttp.repository.DoiTuongThanhTraRepository;
import vn.greenglobal.tttp.repository.LinhVucDoiTuongThanhTraRepository;
import vn.greenglobal.tttp.service.LinhVucDoiTuongThanhTraService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "linhVucDoiTuongThanhTras", description = "Lĩnh Vực Đối Tượng Thanh Tra")
public class LinhVucDoiTuongThanhTraController extends TttpController<LinhVucDoiTuongThanhTra> {

	@Autowired
	private LinhVucDoiTuongThanhTraRepository repo;
	
	@Autowired
	private DoiTuongThanhTraRepository doiTuongThanhTraRepository;

	@Autowired
	private LinhVucDoiTuongThanhTraService linhVucDoiTuongThanhTraService;

	public LinhVucDoiTuongThanhTraController(BaseRepository<LinhVucDoiTuongThanhTra, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/linhVucDoiTuongThanhTras")
	@ApiOperation(value = "Lấy danh sách Lĩnh Vực Đối Tượng Thanh Tra", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.LINHVUCDOITUONGTHANHTRA_LIETKE) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.LINHVUCDOITUONGTHANHTRA_XEM) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THANHTRA_THEM) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THANHTRA_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Page<LinhVucDoiTuongThanhTra> page = repo.findAll(linhVucDoiTuongThanhTraService.predicateFindAll(tuKhoa), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/linhVucDoiTuongThanhTras")
	@ApiOperation(value = "Thêm mới Lĩnh Vực Đối Tượng Thanh Tra", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Lĩnh Vực Đối Tượng Thanh Tra thành công", response = LinhVucDoiTuongThanhTra.class),
			@ApiResponse(code = 201, message = "Thêm mới Lĩnh Vực Đối Tượng Thanh Tra thành công", response = LinhVucDoiTuongThanhTra.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody LinhVucDoiTuongThanhTra linhVucDoiTuongThanhTra, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.LINHVUCDOITUONGTHANHTRA_THEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (StringUtils.isNotBlank(linhVucDoiTuongThanhTra.getTen()) && linhVucDoiTuongThanhTraService.checkExistsData(repo, linhVucDoiTuongThanhTra)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
						ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.TEN_EXISTS.getText());
			}
			
			return linhVucDoiTuongThanhTraService.doSave(linhVucDoiTuongThanhTra,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/linhVucDoiTuongThanhTras/{id}")
	@ApiOperation(value = "Lấy Lĩnh Vực Đối Tượng Thanh Tra theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Lĩnh Vực Đối Tượng Thanh Tra thành công", response = LinhVucDoiTuongThanhTra.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.LINHVUCDOITUONGTHANHTRA_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			LinhVucDoiTuongThanhTra linhVucDoiTuongThanhTra = repo.findOne(linhVucDoiTuongThanhTraService.predicateFindOne(id));
			if (linhVucDoiTuongThanhTra == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(eass.toFullResource(linhVucDoiTuongThanhTra), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/linhVucDoiTuongThanhTras/{id}")
	@ApiOperation(value = "Cập nhật Lĩnh Vực Đối Tượng Thanh Tra", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cập nhật Lĩnh Vực Đơn Thư thành công", response = LinhVucDoiTuongThanhTra.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody LinhVucDoiTuongThanhTra linhVucDoiTuongThanhTra, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.LINHVUCDOITUONGTHANHTRA_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			linhVucDoiTuongThanhTra.setId(id);
			if (StringUtils.isNotBlank(linhVucDoiTuongThanhTra.getTen())
					&& linhVucDoiTuongThanhTraService.checkExistsData(repo, linhVucDoiTuongThanhTra)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
						ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.TEN_EXISTS.getText());
			}

			if (!linhVucDoiTuongThanhTraService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			return linhVucDoiTuongThanhTraService.doSave(linhVucDoiTuongThanhTra,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/linhVucDoiTuongThanhTras/{id}")
	@ApiOperation(value = "Xoá Lĩnh Vực Đối Tượng Thanh Tra", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Lĩnh Vực Đối Tượng Thanh Tra thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.LINHVUCDOITUONGTHANHTRA_XOA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (linhVucDoiTuongThanhTraService.checkUsedData(doiTuongThanhTraRepository, id)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_USED.name(),
						ApiErrorEnum.DATA_USED.getText(), ApiErrorEnum.DATA_USED.getText());
			}

			LinhVucDoiTuongThanhTra linhVucDoiTuongThanhTra = linhVucDoiTuongThanhTraService.delete(repo, id);
			if (linhVucDoiTuongThanhTra == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			linhVucDoiTuongThanhTraService.save(linhVucDoiTuongThanhTra,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
}
