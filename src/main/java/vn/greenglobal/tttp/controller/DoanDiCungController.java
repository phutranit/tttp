package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
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
import vn.greenglobal.tttp.model.DoanDiCung;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.medial.Medial_DoanDiCung;
import vn.greenglobal.tttp.model.medial.Medial_DoanDiCung_Delete;
import vn.greenglobal.tttp.model.medial.Medial_DoanDiCung_Post_Patch;
import vn.greenglobal.tttp.repository.DoanDiCungRepository;
import vn.greenglobal.tttp.service.DoanDiCungService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "doanDiCungs", description = "Đoàn đi cùng")
public class DoanDiCungController extends TttpController<Don_CongDan> {

	@Autowired
	private DoanDiCungRepository repo;

	@Autowired
	private DoanDiCungService doanDiCungService;

	public DoanDiCungController(BaseRepository<Don_CongDan, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST, value = "/doanDiCungs/multi")
	@ApiOperation(value = "Thêm mới nhiều đoàn đi cùng", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới nhiều đoàn đi cùng thành công", response = Medial_DoanDiCung_Post_Patch.class),
			@ApiResponse(code = 201, message = "Thêm mới nhiều đoàn đi cùng thành công", response = Medial_DoanDiCung_Post_Patch.class) })
	public ResponseEntity<Object> createMulti(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_DoanDiCung_Post_Patch params, PersistentEntityResourceAssembler eass) {

		try {
			Medial_DoanDiCung_Post_Patch result = new Medial_DoanDiCung_Post_Patch();

			if (params != null) {
				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						if (params.getDoanDiCungs().size() > 0) {
							for (DoanDiCung doanDiCung : params.getDoanDiCungs()) {
								DoanDiCung ddc = Utils.save(repo, doanDiCung, Long.valueOf(
										profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
								result.getDoanDiCungs().add(ddc);
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PATCH, value = "/doanDiCungs/multi")
	@ApiOperation(value = "Cập nhật nhiều đoàn đi cùng", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật nhiều đoàn đi cùng thành công", response = Medial_DoanDiCung_Post_Patch.class) })
	public @ResponseBody ResponseEntity<Object> updateMulti(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_DoanDiCung_Post_Patch params, PersistentEntityResourceAssembler eass) {

		try {
			Medial_DoanDiCung_Post_Patch result = new Medial_DoanDiCung_Post_Patch();
			List<DoanDiCung> listUpdate = new ArrayList<DoanDiCung>();

			if (params != null) {
				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						if (params.getDoanDiCungs().size() > 0) {
							for (DoanDiCung doanDiCung : params.getDoanDiCungs()) {
								if (!doanDiCungService.isExists(repo, doanDiCung.getId())) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
											ApiErrorEnum.DATA_NOT_FOUND.getText());
								}
								listUpdate.add(doanDiCung);
							}
							for (DoanDiCung doanDiCung : listUpdate) {
								DoanDiCung ddc = Utils.save(repo, doanDiCung, Long.valueOf(
										profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
								result.getDoanDiCungs().add(ddc);
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

	@RequestMapping(method = RequestMethod.DELETE, value = "/doanDiCungs/multi")
	@ApiOperation(value = "Xoá nhiều đoàn đi cùng", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá nhiều đoàn đi cùng thành công") })
	public ResponseEntity<Object> deleteMulti(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_DoanDiCung_Delete params) {

		try {
			List<DoanDiCung> listDelete = new ArrayList<DoanDiCung>();
			if (params != null && params.getDoanDiCungs().size() > 0) {
				for (Medial_DoanDiCung doanDiCung : params.getDoanDiCungs()) {
					DoanDiCung ddc = doanDiCungService.delete(repo, doanDiCung.getId());
					if (ddc == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
								ApiErrorEnum.DATA_NOT_FOUND.getText());
					}
					listDelete.add(ddc);
				}
				for (DoanDiCung doanDiCung : listDelete) {
					Utils.save(repo, doanDiCung, Long
							.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
				}
			}

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors();
		}
	}

}
