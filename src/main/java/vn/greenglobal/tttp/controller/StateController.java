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
import vn.greenglobal.tttp.model.State;
import vn.greenglobal.tttp.repository.DonViHasStateRepository;
import vn.greenglobal.tttp.repository.StateRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.service.StateService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "states", description = "Trang thái")
public class StateController extends TttpController<State> {

	@Autowired
	private DonViHasStateRepository donViHasStateRepo;

	@Autowired
	private TransitionRepository transitionRepo;

	@Autowired
	private StateRepository stateRepo;

	@Autowired
	private StateService stateService;

	public StateController(BaseRepository<State, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/states")
	@ApiOperation(value = "Lấy danh sách Thạng Thái", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "type", required = false) String type, PersistentEntityResourceAssembler eass) {
		
		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.STATE_LIETKE) == null
				&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.STATE_XEM) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		Page<State> page = stateRepo.findAll(stateService.predicateFindAll(tuKhoa, type), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/states/{id}")
	@ApiOperation(value = "Lấy Thạng Thái theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Thạng Thái thành công", response = State.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.STATE_XEM) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		State state = stateRepo.findOne(stateService.predicateFindOne(id));
		if (state == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(eass.toFullResource(state), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/states")
	@ApiOperation(value = "Thêm mới Trạng Thái", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Trạng Thái thành công", response = State.class),
			@ApiResponse(code = 201, message = "Thêm mới Trạng Thái thành công", response = State.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody State state, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.STATE_THEM) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		if (StringUtils.isNotBlank(state.getTen()) && StringUtils.isNotBlank(state.getTenVietTat()) && state.getType() != null
				&& stateService.checkExistsData(stateRepo, state)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_EXISTS.name(),
					ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.DATA_EXISTS.getText());
		}

		return stateService.doSave(state,
				Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
				eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/states/{id}")
	@ApiOperation(value = "Cập nhật Trạng Thái", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Trạng Thái thành công", response = State.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody State state, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.STATE_SUA) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		state.setId(id);
		if (!stateService.isExists(stateRepo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		if (StringUtils.isNotBlank(state.getTen()) && StringUtils.isNotBlank(state.getTenVietTat()) && state.getType() != null
				&& stateService.checkExistsData(stateRepo, state)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_EXISTS.name(),
					ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.DATA_EXISTS.getText());
		}

		return stateService.doSave(state,
				Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
				eass, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/states/{id}")
	@ApiOperation(value = "Xoá Trạng Thái", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Trạng Thái thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.STATE_XOA) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
					ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}

		if (stateService.checkUsedData(donViHasStateRepo, transitionRepo, id)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_USED.name(),
					ApiErrorEnum.DATA_USED.getText(), ApiErrorEnum.DATA_USED.getText());
		}

		State state = stateService.delete(stateRepo, id);
		if (state == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		stateService.save(state,
				Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}