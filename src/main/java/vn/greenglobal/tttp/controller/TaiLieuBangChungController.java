package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
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
import vn.greenglobal.tttp.model.TaiLieuBangChung;
import vn.greenglobal.tttp.model.medial.Medial_TaiLieuBangChung;
import vn.greenglobal.tttp.model.medial.Medial_TaiLieuBangChung_Delete;
import vn.greenglobal.tttp.model.medial.Medial_TaiLieuBangChung_Post_Patch;
import vn.greenglobal.tttp.repository.TaiLieuBangChungRepository;
import vn.greenglobal.tttp.service.TaiLieuBangChungService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "TaiLieuBangChungs", description = "Tài liệu bằng chứng")
public class TaiLieuBangChungController extends TttpController<TaiLieuBangChung> {

	@Autowired
	private TaiLieuBangChungRepository repo;

	@Autowired
	private TaiLieuBangChungService taiLieuBangChungService;

	public TaiLieuBangChungController(BaseRepository<TaiLieuBangChung, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/taiLieuBangChungs")
	@ApiOperation(value = "Lấy danh sách Tài liệu bằng chứng", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			Page<TaiLieuBangChung> page = repo.findAll(taiLieuBangChungService.predicateFindAll(), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/taiLieuBangChungs")
	@ApiOperation(value = "Thêm mới Tài liệu bằng chứng", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Tài liệu bằng chứng thành công", response = TaiLieuBangChung.class),
			@ApiResponse(code = 201, message = "Thêm mới Tài liệu bằng chứng thành công", response = TaiLieuBangChung.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody TaiLieuBangChung taiLieuBangChung, PersistentEntityResourceAssembler eass) {

		try {
			return Utils.doSave(repo, taiLieuBangChung,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST, value = "/taiLieuBangChungs/multi")
	@ApiOperation(value = "Thêm mới nhiều Tài liệu bằng chứng", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới nhiều Tài liệu bằng chứng thành công", response = Medial_TaiLieuBangChung_Post_Patch.class),
			@ApiResponse(code = 201, message = "Thêm mới nhiều Tài liệu bằng chứng thành công", response = Medial_TaiLieuBangChung_Post_Patch.class) })
	public ResponseEntity<Object> createMulti(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_TaiLieuBangChung_Post_Patch params, PersistentEntityResourceAssembler eass) {

		try {
			Medial_TaiLieuBangChung_Post_Patch result = new Medial_TaiLieuBangChung_Post_Patch();

			if (params != null) {
				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						if (params.getTaiLieuBangChungs().size() > 0) {
							for (TaiLieuBangChung taiLieuBangChung : params.getTaiLieuBangChungs()) {
								TaiLieuBangChung tlbc = Utils.save(repo, taiLieuBangChung, Long.valueOf(
										profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
								result.getTaiLieuBangChungs().add(tlbc);
							}
						}
						return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.CREATED);
					}
				});
			}

			return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/taiLieuBangChungs/{id}")
	@ApiOperation(value = "Lấy Tài liệu bằng chứng theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Tài liệu bằng chứng thành công", response = TaiLieuBangChung.class) })
	public ResponseEntity<Object> getById(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {

		try {
			TaiLieuBangChung taiLieuBangChung = repo.findOne(taiLieuBangChungService.predicateFindOne(id));
			if (taiLieuBangChung == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(eass.toFullResource(taiLieuBangChung), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/taiLieuBangChungs/{id}")
	@ApiOperation(value = "Cập nhật Tài liệu bằng chứng", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Tài liệu bằng chứng thành công", response = TaiLieuBangChung.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody TaiLieuBangChung taiLieuBangChung, PersistentEntityResourceAssembler eass) {

		try {
			taiLieuBangChung.setId(id);
			if (!taiLieuBangChungService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			return Utils.doSave(repo, taiLieuBangChung,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PATCH, value = "/taiLieuBangChungs/multi")
	@ApiOperation(value = "Cập nhật nhiều Tài liệu bằng chứng", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật nhiều Tài liệu bằng chứng thành công", response = Medial_TaiLieuBangChung_Post_Patch.class) })
	public @ResponseBody ResponseEntity<Object> updateMulti(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_TaiLieuBangChung_Post_Patch params, PersistentEntityResourceAssembler eass) {

		try {
			Medial_TaiLieuBangChung_Post_Patch result = new Medial_TaiLieuBangChung_Post_Patch();
			List<TaiLieuBangChung> listUpdate = new ArrayList<TaiLieuBangChung>();

			if (params != null) {
				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						if (params.getTaiLieuBangChungs().size() > 0) {
							for (TaiLieuBangChung taiLieuBangChung : params.getTaiLieuBangChungs()) {
								if (!taiLieuBangChungService.isExists(repo, taiLieuBangChung.getId())) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
											ApiErrorEnum.DATA_NOT_FOUND.getText());
								}
								listUpdate.add(taiLieuBangChung);
							}
							for (TaiLieuBangChung taiLieuBangChung : listUpdate) {
								TaiLieuBangChung tlbc = Utils.save(repo, taiLieuBangChung, Long.valueOf(
										profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
								result.getTaiLieuBangChungs().add(tlbc);
							}
						}
						return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.OK);
					}
				});
			}

			return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/taiLieuBangChungs/{id}")
	@ApiOperation(value = "Xoá Tài liệu bằng chứng", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Tài liệu bằng chứng thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			TaiLieuBangChung taiLieuBangChung = taiLieuBangChungService.delete(repo, id);
			if (taiLieuBangChung == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			Utils.save(repo, taiLieuBangChung,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/taiLieuBangChungs/multi")
	@ApiOperation(value = "Xoá nhiều Tài liệu bằng chứng", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá nhiều Tài liệu bằng chứng thành công") })
	public ResponseEntity<Object> deleteMulti(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_TaiLieuBangChung_Delete params) {

		try {
			List<TaiLieuBangChung> listDelete = new ArrayList<TaiLieuBangChung>();
			if (params != null && params.getTaiLieuBangChungs().size() > 0) {
				for (Medial_TaiLieuBangChung taiLieuBangChung : params.getTaiLieuBangChungs()) {
					TaiLieuBangChung tlbc = taiLieuBangChungService.delete(repo, taiLieuBangChung.getId());
					if (tlbc == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
								ApiErrorEnum.DATA_NOT_FOUND.getText());
					}
					listDelete.add(tlbc);
				}
				for (TaiLieuBangChung tlbc : listDelete) {
					Utils.save(repo, tlbc, Long
							.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
				}
			}

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

}
