package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
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
import vn.greenglobal.tttp.model.TepDinhKem;
import vn.greenglobal.tttp.model.medial.Medial_TepDinhKem;
import vn.greenglobal.tttp.model.medial.Medial_TepDinhKem_Delete;
import vn.greenglobal.tttp.model.medial.Medial_TepDinhKem_Post_Patch;
import vn.greenglobal.tttp.repository.TepDinhKemRepository;
import vn.greenglobal.tttp.service.TepDinhKemService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "TepDinhKems", description = "Tệp đính kèm")
public class TepDinhKemController extends TttpController<TepDinhKem> {

	@Autowired
	private TepDinhKemRepository repo;

	@Autowired
	private TepDinhKemService TepDinhKemService;

	public TepDinhKemController(BaseRepository<TepDinhKem, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/tepDinhKems")
	@ApiOperation(value = "Thêm mới Tệp đính kèm", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Tệp đính kèm thành công", response = TepDinhKem.class),
			@ApiResponse(code = 201, message = "Thêm mới Tệp đính kèm thành công", response = TepDinhKem.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody TepDinhKem tepDinhKem, PersistentEntityResourceAssembler eass) {
		
		try {
			if (tepDinhKem.getLoaiFileDinhKem() == null) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.LOAITEPDINHKEM_REQUIRED.name(),
						ApiErrorEnum.LOAITEPDINHKEM_REQUIRED.getText());
			}
			return Utils.doSave(repo, tepDinhKem,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST, value = "/tepDinhKems/multi")
	@ApiOperation(value = "Thêm mới nhiều Tệp đính kèm", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới nhiều Tệp đính kèm thành công", response = Medial_TepDinhKem_Post_Patch.class),
			@ApiResponse(code = 201, message = "Thêm mới nhiều Tệp đính kèm thành công", response = Medial_TepDinhKem_Post_Patch.class) })
	public ResponseEntity<Object> createMulti(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_TepDinhKem_Post_Patch params, PersistentEntityResourceAssembler eass) {

		try {
			Medial_TepDinhKem_Post_Patch result = new Medial_TepDinhKem_Post_Patch();
			List<TepDinhKem> listCreate = new ArrayList<TepDinhKem>();

			if (params != null) {
				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						if (params.getTepDinhKems().size() > 0) {
							for (TepDinhKem tepDinhKem : params.getTepDinhKems()) {
								if (tepDinhKem.getLoaiFileDinhKem() == null) {
									return Utils.responseErrors(HttpStatus.BAD_REQUEST,
											ApiErrorEnum.LOAITEPDINHKEM_REQUIRED.name(),
											ApiErrorEnum.LOAITEPDINHKEM_REQUIRED.getText());
								}
								listCreate.add(tepDinhKem);
							}
							for (TepDinhKem tepDinhKem : listCreate) {
								TepDinhKem tlvt = Utils.save(repo, tepDinhKem, Long.valueOf(
										profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
								result.getTepDinhKems().add(tlvt);
							}
						}
						return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.CREATED);
					}
				});
			}

			return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/tepDinhKems/{id}")
	@ApiOperation(value = "Lấy Tệp đính kèm theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Tệp đính kèm thành công", response = TepDinhKem.class) })
	public ResponseEntity<Object> getById(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {

		try {
			TepDinhKem tepDinhKem = repo.findOne(TepDinhKemService.predicateFindOne(id));
			if (tepDinhKem == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(eass.toFullResource(tepDinhKem), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/tepDinhKems/{id}")
	@ApiOperation(value = "Cập nhật Tệp đính kèm", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Tệp đính kèm thành công", response = TepDinhKem.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody TepDinhKem tepDinhKem, PersistentEntityResourceAssembler eass) {

		try {
			tepDinhKem.setId(id);
			if (!TepDinhKemService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			if (tepDinhKem.getLoaiFileDinhKem() == null) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, "LOAITEPDINHKEM_REQUIRED",
						"Loại tệp đính kèm không được để trống!");
			}

			return Utils.doSave(repo, tepDinhKem,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PATCH, value = "/tepDinhKems/multi")
	@ApiOperation(value = "Cập nhật nhiều Tệp đính kèm", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật nhiều Tệp đính kèm thành công", response = Medial_TepDinhKem_Post_Patch.class) })
	public @ResponseBody ResponseEntity<Object> updateMulti(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_TepDinhKem_Post_Patch params, PersistentEntityResourceAssembler eass) {

		try {
			Medial_TepDinhKem_Post_Patch result = new Medial_TepDinhKem_Post_Patch();
			List<TepDinhKem> listUpdate = new ArrayList<TepDinhKem>();

			if (params != null) {
				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						if (params.getTepDinhKems().size() > 0) {
							for (TepDinhKem tepDinhKem : params.getTepDinhKems()) {
								if (!TepDinhKemService.isExists(repo, tepDinhKem.getId())) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
											ApiErrorEnum.DATA_NOT_FOUND.getText());
								}
								if (tepDinhKem.getLoaiFileDinhKem() == null) {
									return Utils.responseErrors(HttpStatus.BAD_REQUEST,
											ApiErrorEnum.LOAITEPDINHKEM_REQUIRED.name(),
											ApiErrorEnum.LOAITEPDINHKEM_REQUIRED.getText());
								}
								listUpdate.add(tepDinhKem);
							}
							for (TepDinhKem tepDinhKem : listUpdate) {
								TepDinhKem tlvt = Utils.save(repo, tepDinhKem, Long.valueOf(
										profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
								result.getTepDinhKems().add(tlvt);
							}
						}
						return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.OK);
					}
				});
			}

			return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/tepDinhKems/{id}")
	@ApiOperation(value = "Xoá Tệp đính kèm", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Tệp đính kèm thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			TepDinhKem tepDinhKem = TepDinhKemService.delete(repo, id);
			if (tepDinhKem == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			Utils.save(repo, tepDinhKem,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/tepDinhKems/multi")
	@ApiOperation(value = "Xoá nhiều Tệp đính kèm", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá nhiều Tệp đính kèm thành công") })
	public ResponseEntity<Object> deleteMulti(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_TepDinhKem_Delete params) {

		try {
			List<TepDinhKem> listDelete = new ArrayList<TepDinhKem>();
			if (params != null && params.getTepDinhKems().size() > 0) {
				for (Medial_TepDinhKem TepDinhKem : params.getTepDinhKems()) {
					TepDinhKem tlvt = TepDinhKemService.delete(repo, TepDinhKem.getId());
					if (tlvt == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
								ApiErrorEnum.DATA_NOT_FOUND.getText());
					}
					listDelete.add(tlvt);
				}
				for (TepDinhKem tlvt : listDelete) {
					Utils.save(repo, tlvt, Long
							.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
				}
			}

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}
}
