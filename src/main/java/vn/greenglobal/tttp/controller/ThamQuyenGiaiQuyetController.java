package vn.greenglobal.tttp.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
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

import com.querydsl.core.types.OrderSpecifier;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.model.QThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.model.ThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.ThamQuyenGiaiQuyetRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.ThamQuyenGiaiQuyetService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "thamQuyenGiaiQuyets", description = "Thẩm Quyền Giải Quyết")
public class ThamQuyenGiaiQuyetController extends TttpController<ThamQuyenGiaiQuyet> {

	@Autowired
	private ThamQuyenGiaiQuyetRepository repo;

	@Autowired
	private ThamQuyenGiaiQuyetService thamQuyenGiaiQuyetService;

	@Autowired
	private DonRepository donRepository;

	@Autowired
	private XuLyDonRepository xuLyDonRepository;

	public ThamQuyenGiaiQuyetController(BaseRepository<ThamQuyenGiaiQuyet, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/thamQuyenGiaiQuyetsCombobox")
	@ApiOperation(value = "Lấy danh sách Thẩm Quyền Giải Quyết", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListCombobox(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			OrderSpecifier<String> sort = QThamQuyenGiaiQuyet.thamQuyenGiaiQuyet.ten.asc();
			Page<ThamQuyenGiaiQuyet> page = null;
			pageable = new PageRequest(0, 1000, null);
			List<ThamQuyenGiaiQuyet> list = (List<ThamQuyenGiaiQuyet>) repo.findAll(thamQuyenGiaiQuyetService.predicateFindAll(tuKhoa), sort);
			boolean flag = false;
			ThamQuyenGiaiQuyet tqgq = new ThamQuyenGiaiQuyet();
			if (list != null && list.size() > 0) {
				for (ThamQuyenGiaiQuyet tq : list) {
					if (Utils.unAccent("khac").equals(Utils.unAccent(tq.getTen()))) {
						list.remove(tq);
						tqgq = tq;
						flag = true;
						break;
					}
				}
			}
			
			if (flag) {
				list.add(tqgq);
			}
			
			int start = pageable.getOffset();
			int end = (start + pageable.getPageSize()) > list.size() ? list.size() : (start + pageable.getPageSize());
			page = new PageImpl<ThamQuyenGiaiQuyet>(list.subList(start, end), pageable, list.size());
			
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/thamQuyenGiaiQuyets")
	@ApiOperation(value = "Lấy danh sách Thẩm Quyền Giải Quyết", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));
			Page<ThamQuyenGiaiQuyet> page = repo.findAll(thamQuyenGiaiQuyetService.predicateFindAll(tuKhoa), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/thamQuyenGiaiQuyets")
	@ApiOperation(value = "Thêm mới Thẩm Quyền Giải Quyết", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Thẩm Quyền Giải Quyết thành công", response = ThamQuyenGiaiQuyet.class),
			@ApiResponse(code = 201, message = "Thêm mới Thẩm Quyền Giải Quyết thành công", response = ThamQuyenGiaiQuyet.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody ThamQuyenGiaiQuyet thamQuyenGiaiQuyet, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THAMQUYENGIAIQUYET_THEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (StringUtils.isNotBlank(thamQuyenGiaiQuyet.getTen())
					&& thamQuyenGiaiQuyetService.checkExistsData(repo, thamQuyenGiaiQuyet)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
						ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.TEN_EXISTS.getText());
			}
			
			thamQuyenGiaiQuyet.setTenSearch(Utils.unAccent(thamQuyenGiaiQuyet.getTen().trim()));
			thamQuyenGiaiQuyet.setMoTaSearch(Utils.unAccent(thamQuyenGiaiQuyet.getMoTa().trim()));
			return thamQuyenGiaiQuyetService.doSave(thamQuyenGiaiQuyet,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/thamQuyenGiaiQuyets/{id}")
	@ApiOperation(value = "Lấy Thẩm Quyền Giải Quyết theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Thẩm Quyền Giải Quyết thành công", response = ThamQuyenGiaiQuyet.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THAMQUYENGIAIQUYET_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			ThamQuyenGiaiQuyet thamQuyenGiaiQuyet = repo.findOne(thamQuyenGiaiQuyetService.predicateFindOne(id));
			if (thamQuyenGiaiQuyet == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(eass.toFullResource(thamQuyenGiaiQuyet), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/thamQuyenGiaiQuyets/{id}")
	@ApiOperation(value = "Cập nhật Thẩm Quyền Giải Quyết", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Thẩm Quyền Giải Quyết thành công", response = ThamQuyenGiaiQuyet.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody ThamQuyenGiaiQuyet thamQuyenGiaiQuyet, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THAMQUYENGIAIQUYET_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			thamQuyenGiaiQuyet.setId(id);
			if (StringUtils.isNotBlank(thamQuyenGiaiQuyet.getTen())
					&& thamQuyenGiaiQuyetService.checkExistsData(repo, thamQuyenGiaiQuyet)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
						ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.TEN_EXISTS.getText());
			}

			if (!thamQuyenGiaiQuyetService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			thamQuyenGiaiQuyet.setTenSearch(Utils.unAccent(thamQuyenGiaiQuyet.getTen().trim()));
			thamQuyenGiaiQuyet.setMoTaSearch(Utils.unAccent(thamQuyenGiaiQuyet.getMoTa().trim()));
			return thamQuyenGiaiQuyetService.doSave(thamQuyenGiaiQuyet,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/thamQuyenGiaiQuyets/{id}")
	@ApiOperation(value = "Xoá Thẩm Quyền Giải Quyết", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Thẩm Quyền Giải Quyết thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THAMQUYENGIAIQUYET_XOA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (thamQuyenGiaiQuyetService.checkUsedData(repo, donRepository, xuLyDonRepository, id)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_USED.name(),
						ApiErrorEnum.DATA_USED.getText(), ApiErrorEnum.DATA_USED.getText());
			}

			ThamQuyenGiaiQuyet thamQuyenGiaiQuyet = thamQuyenGiaiQuyetService.delete(repo, id);
			if (thamQuyenGiaiQuyet == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			thamQuyenGiaiQuyetService.save(thamQuyenGiaiQuyet,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
}