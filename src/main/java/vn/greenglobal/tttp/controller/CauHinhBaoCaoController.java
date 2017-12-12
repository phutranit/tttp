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
import vn.greenglobal.tttp.model.CauHinhBaoCao;
import vn.greenglobal.tttp.repository.CauHinhBaoCaoRepository;
import vn.greenglobal.tttp.service.CauHinhBaoCaoService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "cauHinhBaoCaos", description = "Cấu hình báo cáo")
public class CauHinhBaoCaoController extends TttpController<CauHinhBaoCao> {

	@Autowired
	private CauHinhBaoCaoRepository repo;

	@Autowired
	private CauHinhBaoCaoService cauHinhBaoCaoService;


	public CauHinhBaoCaoController(BaseRepository<CauHinhBaoCao, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/cauHinhBaoCaos")
	@ApiOperation(value = "Lấy danh sách Cấu hình báo cáo", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			Page<CauHinhBaoCao> page = repo.findAll(cauHinhBaoCaoService.predicateFindAll(tuKhoa), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.POST, value = "/cauHinhBaoCaos")
	@ApiOperation(value = "Thêm mới Cấu hình báo cáo", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Thêm mới Cấu hình báo cáo thành công", response = CauHinhBaoCao.class),
			@ApiResponse(code = 201, message = "Thêm mới Cấu hình báo cáo thành công", response = CauHinhBaoCao.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody CauHinhBaoCao cauHinhBaoCao, PersistentEntityResourceAssembler eass) {

		try {
//			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CAUHINHBAOCAO_THEM) == null) {
//				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
//						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
//			}
			
			if (cauHinhBaoCao.getNgayBatDauBC() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.NGAYBATDAUBC_REQUIRED.name(),
						ApiErrorEnum.NGAYBATDAUBC_REQUIRED.getText(), ApiErrorEnum.NGAYBATDAUBC_REQUIRED.getText());
			}
			
			if (cauHinhBaoCao.getNgayKetThucBC() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.NGAYKETTHUCBC_REQUIRED.name(),
						ApiErrorEnum.NGAYKETTHUCBC_REQUIRED.getText(), ApiErrorEnum.NGAYKETTHUCBC_REQUIRED.getText());
			}
			if (cauHinhBaoCao.getKyBaoCao() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.KYBAOCAO_REQUIRED.name(),
						ApiErrorEnum.KYBAOCAO_REQUIRED.getText(), ApiErrorEnum.KYBAOCAO_REQUIRED.getText());
			}
			if (cauHinhBaoCao.getDanhSachBaoCao() == null && cauHinhBaoCao.getDanhSachBaoCao().isEmpty()) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DANHSACHBAOCAO_REQUIRED.name(),
						ApiErrorEnum.DANHSACHBAOCAO_REQUIRED.getText(), ApiErrorEnum.DANHSACHBAOCAO_REQUIRED.getText());
			}
			if (cauHinhBaoCao.getDonViGui() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DONVIGUI_REQUIRED.name(),
						ApiErrorEnum.DONVIGUI_REQUIRED.getText(), ApiErrorEnum.DONVIGUI_REQUIRED.getText());
			}
			cauHinhBaoCao.setNgayGuiBaoCao(cauHinhBaoCao.getNgayKetThucBC().minusDays(cauHinhBaoCao.getSoNgayTuDongGui()));
			
			cauHinhBaoCao.setTenBaoCaoSearch(Utils.unAccent(cauHinhBaoCao.getTenBaoCao().trim()));
			return cauHinhBaoCaoService.doSave(cauHinhBaoCao,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/cauHinhBaoCaos/{id}")
	@ApiOperation(value = "Lấy Cấu hình báo cáo theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Cấu hình báo cáo thành công", response = CauHinhBaoCao.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CAUHINHBAOCAO_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			CauHinhBaoCao cauHinhBaoCao = repo.findOne(cauHinhBaoCaoService.predicateFindOne(id));
			if (cauHinhBaoCao == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(eass.toFullResource(cauHinhBaoCao), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/cauHinhBaoCaos/{id}")
	@ApiOperation(value = "Cập nhật Cấu hình báo cáo", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Cấu hình báo cáo thành công", response = CauHinhBaoCao.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody CauHinhBaoCao cauHinhBaoCao, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CAUHINHBAOCAO_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			cauHinhBaoCao.setId(id);
			
			if (!cauHinhBaoCaoService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CAUHINHBAOCAO_THEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			if (cauHinhBaoCao.getNgayBatDauBC() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.NGAYBATDAUBC_REQUIRED.name(),
						ApiErrorEnum.NGAYBATDAUBC_REQUIRED.getText(), ApiErrorEnum.NGAYBATDAUBC_REQUIRED.getText());
			}
			
			if (cauHinhBaoCao.getNgayKetThucBC() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.NGAYKETTHUCBC_REQUIRED.name(),
						ApiErrorEnum.NGAYKETTHUCBC_REQUIRED.getText(), ApiErrorEnum.NGAYKETTHUCBC_REQUIRED.getText());
			}
			if (cauHinhBaoCao.getKyBaoCao() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.KYBAOCAO_REQUIRED.name(),
						ApiErrorEnum.KYBAOCAO_REQUIRED.getText(), ApiErrorEnum.KYBAOCAO_REQUIRED.getText());
			}
			if (cauHinhBaoCao.getDanhSachBaoCao() == null && cauHinhBaoCao.getDanhSachBaoCao().isEmpty()) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DANHSACHBAOCAO_REQUIRED.name(),
						ApiErrorEnum.DANHSACHBAOCAO_REQUIRED.getText(), ApiErrorEnum.DANHSACHBAOCAO_REQUIRED.getText());
			}
			if (cauHinhBaoCao.getDonViGui() == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DONVIGUI_REQUIRED.name(),
						ApiErrorEnum.DONVIGUI_REQUIRED.getText(), ApiErrorEnum.DONVIGUI_REQUIRED.getText());
			}
			cauHinhBaoCao.setNgayGuiBaoCao(cauHinhBaoCao.getNgayKetThucBC().minusDays(cauHinhBaoCao.getSoNgayTuDongGui()));			
			cauHinhBaoCao.setTenBaoCaoSearch(Utils.unAccent(cauHinhBaoCao.getTenBaoCao().trim()));
			return cauHinhBaoCaoService.doSave(cauHinhBaoCao,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/cauHinhBaoCaos/{id}")
	@ApiOperation(value = "Xoá Cấu hình báo cáo", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Cấu hình báo cáo thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CAUHINHBAOCAO_XOA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			CauHinhBaoCao cauHinhBaoCao = cauHinhBaoCaoService.delete(repo, id);
			if (cauHinhBaoCao == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			cauHinhBaoCaoService.save(cauHinhBaoCao,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
}
