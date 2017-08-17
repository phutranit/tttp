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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.KeHoachThanhTra;
import vn.greenglobal.tttp.repository.KeHoachThanhTraRepository;
import vn.greenglobal.tttp.service.KeHoachThanhTraService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "keHoachThanhTras", description = "Kế hoạch thanh tra")
public class KeHoachThanhTraController extends TttpController<KeHoachThanhTra> {

	@Autowired
	private KeHoachThanhTraRepository repo;

	@Autowired
	private KeHoachThanhTraService keHoachThanhTraService;

	public KeHoachThanhTraController(BaseRepository<KeHoachThanhTra, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/keHoachThanhTras")
	@ApiOperation(value = "Lấy danh sách Kế Hoạch Thanh Tra", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "soQuyetDinh", required = false) String soQuyetDinh,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			Page<KeHoachThanhTra> page = repo.findAll(keHoachThanhTraService.predicateFindAll(soQuyetDinh, tuNgay, denNgay), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@RequestMapping(method = RequestMethod.POST, value = "/keHoachThanhTras")
//	@ApiOperation(value = "Thêm mới nhiều Tài liệu văn thư", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = "Thêm mới nhiều Tài liệu văn thư thành công", response = Medial_TaiLieuVanThu_Post_Patch.class),
//			@ApiResponse(code = 201, message = "Thêm mới nhiều Tài liệu văn thư thành công", response = Medial_TaiLieuVanThu_Post_Patch.class) })
//	public ResponseEntity<Object> createMulti(
//			@RequestHeader(value = "Authorization", required = true) String authorization,
//			@RequestBody Medial_TaiLieuVanThu_Post_Patch params, PersistentEntityResourceAssembler eass) {
//
//		try {
//			Medial_TaiLieuVanThu_Post_Patch result = new Medial_TaiLieuVanThu_Post_Patch();
//			List<TaiLieuVanThu> listCreate = new ArrayList<TaiLieuVanThu>();
//
//			if (params != null) {
//				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
//					@Override
//					public Object doInTransaction(TransactionStatus arg0) {
//						if (params.getTaiLieuVanThus().size() > 0) {
//							for (TaiLieuVanThu taiLieuVanThu : params.getTaiLieuVanThus()) {
//								TaiLieuVanThu tlvt = keHoachThanhTraService.save(taiLieuVanThu, Long.valueOf(
//										profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//								result.getTaiLieuVanThus().add(tlvt);
//							}
//						}
//						return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.CREATED);
//					}
//				});
//			}
//
//			return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.CREATED);
//		} catch (Exception e) {
//			return Utils.responseInternalServerErrors(e);
//		}
//	}

	@RequestMapping(method = RequestMethod.GET, value = "/keHoachThanhTras/{id}")
	@ApiOperation(value = "Lấy Kế Hoạch Thanh Tra theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy Kế Hoạch Thanh Tra thành công", response = KeHoachThanhTra.class) })
	public ResponseEntity<Object> getById(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {

		try {
			KeHoachThanhTra keHoachThanhTra = repo.findOne(keHoachThanhTraService.predicateFindOne(id));
			if (keHoachThanhTra == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(eass.toFullResource(keHoachThanhTra), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

//	@SuppressWarnings({ "unchecked", "rawtypes" })
//	@RequestMapping(method = RequestMethod.PATCH, value = "/keHoachThanhTras")
//	@ApiOperation(value = "Cập nhật nhiều Tài liệu văn thư", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ApiResponses(value = {
//			@ApiResponse(code = 200, message = "Cập nhật nhiều Tài liệu văn thư thành công", response = Medial_TaiLieuVanThu_Post_Patch.class) })
//	public @ResponseBody ResponseEntity<Object> updateMulti(
//			@RequestHeader(value = "Authorization", required = true) String authorization,
//			@RequestBody Medial_TaiLieuVanThu_Post_Patch params, PersistentEntityResourceAssembler eass) {
//
//		try {
//			Medial_TaiLieuVanThu_Post_Patch result = new Medial_TaiLieuVanThu_Post_Patch();
//			List<TaiLieuVanThu> listUpdate = new ArrayList<TaiLieuVanThu>();

//			if (params != null) {
//				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
//					@Override
//					public Object doInTransaction(TransactionStatus arg0) {
//						if (params.getTaiLieuVanThus().size() > 0) {
//
//							for (TaiLieuVanThu taiLieuVanThu : params.getTaiLieuVanThus()) {
//								TaiLieuVanThu tlvt = keHoachThanhTraService.save(taiLieuVanThu, Long.valueOf(
//										profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//								result.getTaiLieuVanThus().add(tlvt);
//							}
//						}
//						return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.OK);
//					}
//				});
//			}
//
//			return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.OK);
//		} catch (Exception e) {
//			return Utils.responseInternalServerErrors(e);
//		}
//	}

//	@RequestMapping(method = RequestMethod.DELETE, value = "/taiLieuVanThus/{id}")
//	@ApiOperation(value = "Xoá Tài liệu văn thư", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Tài liệu văn thư thành công") })
//	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
//			@PathVariable("id") Long id) {
//
//		try {
//			TaiLieuVanThu taiLieuVanThu = keHoachThanhTraService.delete(repo, id);
//			if (taiLieuVanThu == null) {
//				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
//						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
//			}
//
//			keHoachThanhTraService.save(taiLieuVanThu,
//					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		} catch (Exception e) {
//			return Utils.responseInternalServerErrors(e);
//		}
//	}
//
//	@RequestMapping(method = RequestMethod.DELETE, value = "/taiLieuVanThus/multi")
//	@ApiOperation(value = "Xoá nhiều Tài liệu văn thư", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá nhiều Tài liệu văn thư thành công") })
//	public ResponseEntity<Object> deleteMulti(@RequestHeader(value = "Authorization", required = true) String authorization,
//			@RequestBody Medial_TaiLieuVanThu_Delete params) {
//
//		try {
//			List<TaiLieuVanThu> listDelete = new ArrayList<TaiLieuVanThu>();
//			if (params != null && params.getTaiLieuVanThus().size() > 0) {
//				for (Medial_TaiLieuVanThu taiLieuVanThu : params.getTaiLieuVanThus()) {
//					TaiLieuVanThu tlvt = keHoachThanhTraService.delete(repo, taiLieuVanThu.getId());
//					if (tlvt == null) {
//						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
//								ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
//					}
//					listDelete.add(tlvt);
//				}
//				for (TaiLieuVanThu tlvt : listDelete) {
//					keHoachThanhTraService.save(tlvt, Long
//							.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//				}
//			}
//
//			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//		} catch (Exception e) {
//			return Utils.responseInternalServerErrors(e);
//		}
//	}
}
