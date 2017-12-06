package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import vn.greenglobal.tttp.enums.TrangThaiDoiTuongViPhamEnum;
import vn.greenglobal.tttp.model.DoiTuongViPham;
import vn.greenglobal.tttp.model.medial.Medial_DoiTuongViPham;
import vn.greenglobal.tttp.model.medial.Medial_DoiTuongViPham_Delete;
import vn.greenglobal.tttp.model.medial.Medial_DoiTuongViPham_Post_Patch;
import vn.greenglobal.tttp.repository.DoiTuongViPhamRepository;
import vn.greenglobal.tttp.service.DoiTuongViPhamService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "doiTuongViPhams", description = "Đối Tượng Vi Phạm")
public class DoiTuongViPhamController extends TttpController<DoiTuongViPham> {

	@Autowired
	private DoiTuongViPhamRepository repo;

	@Autowired
	private DoiTuongViPhamService doiTuongViPhamService;

	public DoiTuongViPhamController(BaseRepository<DoiTuongViPham, Long> repo) {
		super(repo);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/doiTuongViPhams")
	@ApiOperation(value = "Lấy danh sách Đối Tượng Vi Phạm", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "cuocThanhTraId", required = false) Long cuocThanhTraId,
			PersistentEntityResourceAssembler eass) {
		try {
			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "id")));
			Page<DoiTuongViPham> page = repo.findAll(doiTuongViPhamService.predicateFindAll(cuocThanhTraId), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST, value = "/doiTuongViPhams/multi")
	@ApiOperation(value = "Thêm mới nhiều Đối Tượng Vi Phạm", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới nhiều Đối Tượng Vi Phạm", response = Medial_DoiTuongViPham_Post_Patch.class),
			@ApiResponse(code = 201, message = "Thêm mới nhiều Đối Tượng Vi Phạm", response = Medial_DoiTuongViPham_Post_Patch.class) })
	public ResponseEntity<Object> createMulti(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_DoiTuongViPham_Post_Patch params, PersistentEntityResourceAssembler eass) {

		try {
			Medial_DoiTuongViPham_Post_Patch result = new Medial_DoiTuongViPham_Post_Patch();

			if (params != null) {
				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						if (params.getDoiTuongViPhams().size() > 0) {
							for (DoiTuongViPham doiTuongViPham : params.getDoiTuongViPhams()) {
								doiTuongViPham.setTrangThaiDoiTuongViPham(TrangThaiDoiTuongViPhamEnum.DANG_SOAN);
								checkDataDoiTuongViPham(doiTuongViPham);
								DoiTuongViPham dtvp = doiTuongViPhamService.save(doiTuongViPham, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
								result.getDoiTuongViPhams().add(dtvp);
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

	@RequestMapping(method = RequestMethod.GET, value = "/doiTuongViPhams/{id}")
	@ApiOperation(value = "Lấy Đối Tượng Vi Phạm theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy Đối Tượng Vi Phạm thành công", response = DoiTuongViPham.class) })
	public ResponseEntity<Object> getById(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {

		try {
			DoiTuongViPham doiTuongViPham = repo.findOne(doiTuongViPhamService.predicateFindOne(id));
			if (doiTuongViPham == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(eass.toFullResource(doiTuongViPham), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PATCH, value = "/doiTuongViPhams/multi")
	@ApiOperation(value = "Cập nhật nhiều Đối Tượng Vi Phạm", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật nhiều Đối Tượng Vi Phạm thành công", response = Medial_DoiTuongViPham_Post_Patch.class) })
	public @ResponseBody ResponseEntity<Object> updateMulti(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_DoiTuongViPham_Post_Patch params, PersistentEntityResourceAssembler eass) {

		try {
			Medial_DoiTuongViPham_Post_Patch result = new Medial_DoiTuongViPham_Post_Patch();

			if (params != null) {
				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						if (params.getDoiTuongViPhams().size() > 0) {
							for (DoiTuongViPham doiTuongViPham : params.getDoiTuongViPhams()) {
								checkDataDoiTuongViPham(doiTuongViPham);
								DoiTuongViPham dtvp = doiTuongViPhamService.save(doiTuongViPham, Long.valueOf(
										profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
								result.getDoiTuongViPhams().add(dtvp);
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

	@RequestMapping(method = RequestMethod.DELETE, value = "/doiTuongViPhams/multi")
	@ApiOperation(value = "Xoá nhiều Đối Tượng Vi Phạm", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá nhiều Đối Tượng Vi Phạm thành công") })
	public ResponseEntity<Object> deleteMulti(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_DoiTuongViPham_Delete params) {

		try {
			List<DoiTuongViPham> listDelete = new ArrayList<DoiTuongViPham>();
			if (params != null && params.getDoiTuongViPhams().size() > 0) {
				for (Medial_DoiTuongViPham doiTuongViPham : params.getDoiTuongViPhams()) {
					DoiTuongViPham dtvp = doiTuongViPhamService.delete(repo, doiTuongViPham.getId());
					if (dtvp == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
								ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
					}
					listDelete.add(dtvp);
				}
				for (DoiTuongViPham dtvp : listDelete) {
					doiTuongViPhamService.save(dtvp, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
				}
			}

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	private DoiTuongViPham checkDataDoiTuongViPham(DoiTuongViPham doiTuongViPham) {
		if (!doiTuongViPham.isChuyenCoQuanDieuTra()) {
			doiTuongViPham.setSoQuyetDinhChuyenCoQuanDieuTra("");
			doiTuongViPham.setNguoiRaQuyetDinhChuyenCoQuanDieuTra("");
			doiTuongViPham.setCoQuanDieuTra(null);
			doiTuongViPham.setSoVuChuyenCoQuanDieuTra(0);
			doiTuongViPham.setDonViViPham("");
			doiTuongViPham.setViPhamKhac("");
		}
		return doiTuongViPham;
	}
}
