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
import vn.greenglobal.tttp.model.TaiLieuVanThu;
import vn.greenglobal.tttp.model.medial.Medial_TaiLieuVanThu;
import vn.greenglobal.tttp.model.medial.Medial_TaiLieuVanThu_Delete;
import vn.greenglobal.tttp.model.medial.Medial_TaiLieuVanThu_Post_Patch;
import vn.greenglobal.tttp.repository.TaiLieuVanThuRepository;
import vn.greenglobal.tttp.service.TaiLieuVanThuService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "TaiLieuVanThus", description = "Tài liệu văn thư")
public class TaiLieuVanThuController extends TttpController<TaiLieuVanThu> {

	@Autowired
	private TaiLieuVanThuRepository repo;

	@Autowired
	private TaiLieuVanThuService taiLieuVanThuService;

	public TaiLieuVanThuController(BaseRepository<TaiLieuVanThu, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/taiLieuVanThus")
	@ApiOperation(value = "Lấy danh sách Tài liệu văn thư", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "donId", required = false) Long donId,
			@RequestParam(value = "loaiQuyTrinh", required = false) String loaiQuyTrinh,
			@RequestParam(value = "buocGiaiQuyet", required = false) String buocGiaiQuyet,Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			Page<TaiLieuVanThu> page = repo.findAll(taiLieuVanThuService.predicateFindAll(donId, loaiQuyTrinh, buocGiaiQuyet), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/taiLieuVanThus")
	@ApiOperation(value = "Thêm mới Tài liệu văn thư", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Tài liệu văn thư thành công", response = TaiLieuVanThu.class),
			@ApiResponse(code = 201, message = "Thêm mới Tài liệu văn thư thành công", response = TaiLieuVanThu.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody TaiLieuVanThu taiLieuVanThu, PersistentEntityResourceAssembler eass) {
		
		try {
//			if (taiLieuVanThu.getLoaiTepDinhKem() == null) {
//				return Utils.responseErrors(HttpStatus.BAD_REQUEST, "LOAITEPDINHKEM_REQUIRED",
//						"Loại tệp đính kèm không được để trống.", "Loại tệp đính kèm không được để trống.");
//			} else if (LoaiTepDinhKemEnum.QUYET_DINH.equals(taiLieuVanThu.getLoaiTepDinhKem())) {
//				if (taiLieuVanThu.getSoQuyetDinh() == null || taiLieuVanThu.getSoQuyetDinh().isEmpty()) {
//					return Utils.responseErrors(HttpStatus.BAD_REQUEST, "SOQUYETDINH_REQUIRED",
//							"Số quyết định không được để trống.", "Số quyết định không được để trống.");
//				}
//				if (taiLieuVanThu.getNgayQuyetDinh() == null) {
//					return Utils.responseErrors(HttpStatus.BAD_REQUEST, "NGAYQUYETDINH_REQUIRED",
//							"Ngày quyết định không được để trống.", "Ngày quyết định không được để trống.");
//				}
//			}
			return taiLieuVanThuService.doSave(taiLieuVanThu,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST, value = "/taiLieuVanThus/multi")
	@ApiOperation(value = "Thêm mới nhiều Tài liệu văn thư", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới nhiều Tài liệu văn thư thành công", response = Medial_TaiLieuVanThu_Post_Patch.class),
			@ApiResponse(code = 201, message = "Thêm mới nhiều Tài liệu văn thư thành công", response = Medial_TaiLieuVanThu_Post_Patch.class) })
	public ResponseEntity<Object> createMulti(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_TaiLieuVanThu_Post_Patch params, PersistentEntityResourceAssembler eass) {

		try {
			Medial_TaiLieuVanThu_Post_Patch result = new Medial_TaiLieuVanThu_Post_Patch();
//			List<TaiLieuVanThu> listCreate = new ArrayList<TaiLieuVanThu>();

			if (params != null) {
				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						if (params.getTaiLieuVanThus().size() > 0) {
//							for (TaiLieuVanThu taiLieuVanThu : params.getTaiLieuVanThus()) {
//								if (taiLieuVanThu.getLoaiTepDinhKem() == null) {
//									return Utils.responseErrors(HttpStatus.BAD_REQUEST,
//											ApiErrorEnum.LOAITEPDINHKEM_REQUIRED.name(),
//											ApiErrorEnum.LOAITEPDINHKEM_REQUIRED.getText(), ApiErrorEnum.LOAITEPDINHKEM_REQUIRED.getText());
//								} else if (LoaiTepDinhKemEnum.QUYET_DINH.equals(taiLieuVanThu.getLoaiTepDinhKem())) {
//									if (taiLieuVanThu.getSoQuyetDinh() == null
//											|| taiLieuVanThu.getSoQuyetDinh().isEmpty()) {
//										return Utils.responseErrors(HttpStatus.BAD_REQUEST,
//												ApiErrorEnum.SOQUYETDINH_REQUIRED.name(),
//												ApiErrorEnum.SOQUYETDINH_REQUIRED.getText(), ApiErrorEnum.SOQUYETDINH_REQUIRED.getText());
//									}
//									if (taiLieuVanThu.getNgayQuyetDinh() == null) {
//										return Utils.responseErrors(HttpStatus.BAD_REQUEST,
//												ApiErrorEnum.NGAYQUYETDINH_REQUIRED.name(),
//												ApiErrorEnum.NGAYQUYETDINH_REQUIRED.getText(), ApiErrorEnum.NGAYQUYETDINH_REQUIRED.getText());
//									}
//								}
//								listCreate.add(taiLieuVanThu);
//							}
//							for (TaiLieuVanThu taiLieuVanThu : listCreate) {
//								TaiLieuVanThu tlvt = taiLieuVanThuService.save(taiLieuVanThu, Long.valueOf(
//										profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//								result.getTaiLieuVanThus().add(tlvt);
//							}
							for (TaiLieuVanThu taiLieuVanThu : params.getTaiLieuVanThus()) {
								TaiLieuVanThu tlvt = taiLieuVanThuService.save(taiLieuVanThu, Long.valueOf(
										profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
								result.getTaiLieuVanThus().add(tlvt);
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

	@RequestMapping(method = RequestMethod.GET, value = "/taiLieuVanThus/{id}")
	@ApiOperation(value = "Lấy Tài liệu văn thư theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy Tài liệu văn thư thành công", response = TaiLieuVanThu.class) })
	public ResponseEntity<Object> getById(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {

		try {
			TaiLieuVanThu taiLieuVanThu = repo.findOne(taiLieuVanThuService.predicateFindOne(id));
			if (taiLieuVanThu == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(eass.toFullResource(taiLieuVanThu), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/taiLieuVanThus/{id}")
	@ApiOperation(value = "Cập nhật Tài liệu văn thư", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Tài liệu văn thư thành công", response = TaiLieuVanThu.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody TaiLieuVanThu taiLieuVanThu, PersistentEntityResourceAssembler eass) {

		try {
			taiLieuVanThu.setId(id);
			if (!taiLieuVanThuService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

//			if (taiLieuVanThu.getLoaiTepDinhKem() == null) {
//				return Utils.responseErrors(HttpStatus.BAD_REQUEST, "LOAITEPDINHKEM_REQUIRED",
//						"Loại tệp đính kèm không được để trống.", "Loại tệp đính kèm không được để trống.");
//			} else if (LoaiTepDinhKemEnum.QUYET_DINH.equals(taiLieuVanThu.getLoaiTepDinhKem())) {
//				if (taiLieuVanThu.getSoQuyetDinh() == null || taiLieuVanThu.getSoQuyetDinh().isEmpty()) {
//					return Utils.responseErrors(HttpStatus.BAD_REQUEST, "SOQUYETDINH_REQUIRED",
//							"Số quyết định không được để trống.", "Số quyết định không được để trống.");
//				}
//				if (taiLieuVanThu.getNgayQuyetDinh() == null) {
//					return Utils.responseErrors(HttpStatus.BAD_REQUEST, "NGAYQUYETDINH_REQUIRED",
//							"Ngày quyết định không được để trống.", "Ngày quyết định không được để trống.");
//				}
//			}

			return taiLieuVanThuService.doSave(taiLieuVanThu,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PATCH, value = "/taiLieuVanThus/multi")
	@ApiOperation(value = "Cập nhật nhiều Tài liệu văn thư", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật nhiều Tài liệu văn thư thành công", response = Medial_TaiLieuVanThu_Post_Patch.class) })
	public @ResponseBody ResponseEntity<Object> updateMulti(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_TaiLieuVanThu_Post_Patch params, PersistentEntityResourceAssembler eass) {

		try {
			Medial_TaiLieuVanThu_Post_Patch result = new Medial_TaiLieuVanThu_Post_Patch();
//			List<TaiLieuVanThu> listUpdate = new ArrayList<TaiLieuVanThu>();

			if (params != null) {
				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						if (params.getTaiLieuVanThus().size() > 0) {
//							for (TaiLieuVanThu taiLieuVanThu : params.getTaiLieuVanThus()) {
//								if (!taiLieuVanThuService.isExists(repo, taiLieuVanThu.getId())) {
//									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
//											ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
//								}
//								if (taiLieuVanThu.getLoaiTepDinhKem() == null) {
//									return Utils.responseErrors(HttpStatus.BAD_REQUEST,
//											ApiErrorEnum.LOAITEPDINHKEM_REQUIRED.name(),
//											ApiErrorEnum.LOAITEPDINHKEM_REQUIRED.getText(), ApiErrorEnum.LOAITEPDINHKEM_REQUIRED.getText());
//								} else if (LoaiTepDinhKemEnum.QUYET_DINH.equals(taiLieuVanThu.getLoaiTepDinhKem())) {
//									if (taiLieuVanThu.getSoQuyetDinh() == null
//											|| taiLieuVanThu.getSoQuyetDinh().isEmpty()) {
//										return Utils.responseErrors(HttpStatus.BAD_REQUEST,
//												ApiErrorEnum.SOQUYETDINH_REQUIRED.name(),
//												ApiErrorEnum.SOQUYETDINH_REQUIRED.getText(), ApiErrorEnum.SOQUYETDINH_REQUIRED.getText());
//									}
//									if (taiLieuVanThu.getNgayQuyetDinh() == null) {
//										return Utils.responseErrors(HttpStatus.BAD_REQUEST,
//												ApiErrorEnum.NGAYQUYETDINH_REQUIRED.name(),
//												ApiErrorEnum.NGAYQUYETDINH_REQUIRED.getText(), ApiErrorEnum.NGAYQUYETDINH_REQUIRED.getText());
//									}
//								}
//								listUpdate.add(taiLieuVanThu);
//							}
//							for (TaiLieuVanThu taiLieuVanThu : listUpdate) {
//								TaiLieuVanThu tlvt = taiLieuVanThuService.save(taiLieuVanThu, Long.valueOf(
//										profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//								result.getTaiLieuVanThus().add(tlvt);
//							}
							for (TaiLieuVanThu taiLieuVanThu : params.getTaiLieuVanThus()) {
								TaiLieuVanThu tlvt = taiLieuVanThuService.save(taiLieuVanThu, Long.valueOf(
										profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
								result.getTaiLieuVanThus().add(tlvt);
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

	@RequestMapping(method = RequestMethod.DELETE, value = "/taiLieuVanThus/{id}")
	@ApiOperation(value = "Xoá Tài liệu văn thư", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Tài liệu văn thư thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			TaiLieuVanThu taiLieuVanThu = taiLieuVanThuService.delete(repo, id);
			if (taiLieuVanThu == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			taiLieuVanThuService.save(taiLieuVanThu,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/taiLieuVanThus/multi")
	@ApiOperation(value = "Xoá nhiều Tài liệu văn thư", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá nhiều Tài liệu văn thư thành công") })
	public ResponseEntity<Object> deleteMulti(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_TaiLieuVanThu_Delete params) {

		try {
			List<TaiLieuVanThu> listDelete = new ArrayList<TaiLieuVanThu>();
			if (params != null && params.getTaiLieuVanThus().size() > 0) {
				for (Medial_TaiLieuVanThu taiLieuVanThu : params.getTaiLieuVanThus()) {
					TaiLieuVanThu tlvt = taiLieuVanThuService.delete(repo, taiLieuVanThu.getId());
					if (tlvt == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
								ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
					}
					listDelete.add(tlvt);
				}
				for (TaiLieuVanThu tlvt : listDelete) {
					taiLieuVanThuService.save(tlvt, Long
							.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
				}
			}

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
}
