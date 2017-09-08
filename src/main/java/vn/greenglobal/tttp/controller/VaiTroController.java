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
import vn.greenglobal.tttp.model.VaiTro;
import vn.greenglobal.tttp.repository.NguoiDungRepository;
import vn.greenglobal.tttp.repository.VaiTroRepository;
import vn.greenglobal.tttp.service.VaiTroService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "vaiTros", description = "Vai trò")
public class VaiTroController extends TttpController<VaiTro> {

	@Autowired
	private VaiTroRepository repo;

	@Autowired
	private VaiTroService vaiTroService;

	@Autowired
	private NguoiDungRepository nguoiDungRepository;

	public VaiTroController(BaseRepository<VaiTro, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/vaiTros")
	@ApiOperation(value = "Lấy danh sách Vai Trò", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.VAITRO_LIETKE) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.VAITRO_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Page<VaiTro> page = repo.findAll(vaiTroService.predicateFindAll(tuKhoa), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/vaiTros")
	@ApiOperation(value = "Thêm mới Vai Trò", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Thêm mới Vai Trò thành công", response = VaiTro.class),
			@ApiResponse(code = 201, message = "Thêm mới Vai trò thành công", response = VaiTro.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody VaiTro vaiTro, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.VAITRO_THEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (StringUtils.isNotBlank(vaiTro.getTen()) && vaiTroService.checkExistsData(repo, vaiTro)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
						ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.TEN_EXISTS.getText());
			}
			
			vaiTro.setTenSearch(Utils.unAccent(vaiTro.getTen().trim()));
			return vaiTroService.doSave(vaiTro,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/vaiTros/{id}")
	@ApiOperation(value = "Lấy Vai Trò theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Vai Trò thành công", response = VaiTro.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.VAITRO_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			VaiTro vaiTro = repo.findOne(vaiTroService.predicateFindOne(id));
			if (vaiTro == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(eass.toFullResource(vaiTro), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/vaiTros/{id}")
	@ApiOperation(value = "Cập nhật Vai Trò", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Vai Trò thành công", response = VaiTro.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody VaiTro vaiTro, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.VAITRO_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			vaiTro.setId(id);
			if (StringUtils.isNotBlank(vaiTro.getTen()) && vaiTroService.checkExistsData(repo, vaiTro)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
						ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.TEN_EXISTS.getText());
			}

			if (!vaiTroService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			vaiTro.setTenSearch(Utils.unAccent(vaiTro.getTen().trim()));
			return vaiTroService.doSave(vaiTro,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/vaiTros/{id}")
	@ApiOperation(value = "Xoá Vai Trò", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Vai Trò thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.VAITRO_XOA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (vaiTroService.checkUsedData(nguoiDungRepository, id)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_USED.name(),
						ApiErrorEnum.DATA_USED.getText(), ApiErrorEnum.DATA_USED.getText());
			}

			VaiTro vaiTro = vaiTroService.delete(repo, id);
			if (vaiTro == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			vaiTroService.save(vaiTro,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

}
