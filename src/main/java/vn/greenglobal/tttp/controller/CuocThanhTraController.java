package vn.greenglobal.tttp.controller;

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
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CuocThanhTra;
import vn.greenglobal.tttp.repository.CuocThanhTraRepository;
import vn.greenglobal.tttp.service.CuocThanhTraService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "cuocThanhTras", description = "Thông tin cuộc thanh tra")
public class CuocThanhTraController extends TttpController<CuocThanhTra> {

	@Autowired
	private CuocThanhTraRepository repo;

	@Autowired
	private CuocThanhTraService cuocThanhTraService;

	public CuocThanhTraController(BaseRepository<CuocThanhTra, Long> repo) {
		super(repo);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/cuocThanhTras")
	@ApiOperation(value = "Lấy danh sách Cuộc Thanh Tra", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "tenDoiTuongThanhTra", required = false) String tenDoiTuongThanhTra,
			@RequestParam(value = "soQuyetDinh", required = false) String soQuyetDinh,
			@RequestParam(value = "loaiHinhThanhTra", required = false) String loaiHinhThanhTra,
			@RequestParam(value = "linhVucThanhTra", required = false) String linhVucThanhTra,
			@RequestParam(value = "tienDoThanhTra", required = false) String tienDoThanhTra,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THANHTRA_LIETKE) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THANHTRA_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Page<CuocThanhTra> page = repo.findAll(cuocThanhTraService.predicateFindAll(tenDoiTuongThanhTra,
					soQuyetDinh, loaiHinhThanhTra, linhVucThanhTra, tienDoThanhTra, tuNgay, denNgay, donViId), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/cuocThanhTras")
	@ApiOperation(value = "Thêm mới Cuộc Thanh Tra", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Cuộc Thanh Tra thành công", response = CuocThanhTra.class),
			@ApiResponse(code = 201, message = "Thêm mới Cuộc Thanh Tra thành công", response = CuocThanhTra.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody CuocThanhTra cuocThanhTra, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THANHTRA_THEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			CoQuanQuanLy donVi = new CoQuanQuanLy();
			donVi.setId(Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString()));
			cuocThanhTra.setDonVi(donVi);
			return cuocThanhTraService.doSave(cuocThanhTra, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass, HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cuocThanhTras/{id}")
	@ApiOperation(value = "Lấy Thông Cuộc Thanh Tra theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Thông tin Cuộc Thanh Tra thành công", response = CuocThanhTra.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THANHTRA_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			CuocThanhTra cuocThanhTra = repo.findOne(cuocThanhTraService.predicateFindOne(id));
			if (cuocThanhTra == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(eass.toFullResource(cuocThanhTra), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.PATCH, value = "/cuocThanhTras/{id}")
	@ApiOperation(value = "Cập nhật Cuộc Thanh Tra", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Cuộc Thanh Tra thành công", response = CuocThanhTra.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody CuocThanhTra cuocThanhTra, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THANHTRA_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			cuocThanhTra.setId(id);
			checkDataCuocThanhTra(cuocThanhTra);
			return cuocThanhTraService.doSave(cuocThanhTra, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/cuocThanhTras/{id}")
	@ApiOperation(value = "Xoá Cuộc Thanh Tra", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Cuộc Thanh Tra thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			CuocThanhTra cuocThanhTra = cuocThanhTraService.delete(repo, id);
			if (cuocThanhTra == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			
			if (cuocThanhTra.getKeHoachThanhTra() != null) {
				Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
				cuocThanhTraService.save(cuocThanhTra, congChucId);
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_USED.name(),
						ApiErrorEnum.DATA_USED.getText(), ApiErrorEnum.DATA_USED.getText());
			}
			
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	private CuocThanhTra checkDataCuocThanhTra(CuocThanhTra cuocThanhTra) {
//		if () {
		
		return cuocThanhTra;
	}
}
