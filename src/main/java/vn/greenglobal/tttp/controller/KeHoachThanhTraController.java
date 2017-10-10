package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.List;

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
import vn.greenglobal.tttp.enums.HinhThucThanhTraEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CuocThanhTra;
import vn.greenglobal.tttp.model.DoiTuongThanhTra;
import vn.greenglobal.tttp.model.KeHoachThanhTra;
import vn.greenglobal.tttp.model.medial.Medial_CuocThanhTra;
import vn.greenglobal.tttp.model.medial.Medial_CuocThanhTra_Delete;
import vn.greenglobal.tttp.model.medial.Medial_KeHoachThanhTra_CuocThanhTra_Post_Patch;
import vn.greenglobal.tttp.model.medial.Medial_KeHoachThanhTra_Post_Patch;
import vn.greenglobal.tttp.repository.CuocThanhTraRepository;
import vn.greenglobal.tttp.repository.DoiTuongThanhTraRepository;
import vn.greenglobal.tttp.repository.KeHoachThanhTraRepository;
import vn.greenglobal.tttp.service.CuocThanhTraService;
import vn.greenglobal.tttp.service.DoiTuongThanhTraService;
import vn.greenglobal.tttp.service.KeHoachThanhTraService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "keHoachThanhTras", description = "Kế hoạch thanh tra")
public class KeHoachThanhTraController extends TttpController<KeHoachThanhTra> {

	@Autowired
	private KeHoachThanhTraRepository repo;
	
	@Autowired
	private DoiTuongThanhTraRepository doiTuongThanhTraRepository;
	
	@Autowired
	private CuocThanhTraRepository cuocThanhTraRepository;

	@Autowired
	private KeHoachThanhTraService keHoachThanhTraService;
	
	@Autowired
	private DoiTuongThanhTraService doiTuongThanhTraService;
	
	@Autowired
	private CuocThanhTraService cuocThanhTraService;

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
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "namThanhTra", required = false) Integer namThanhTra,
			@RequestParam(value = "donViIds", required = false) List<CoQuanQuanLy> donViIds, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THANHTRA_LIETKE) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THANHTRA_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			if (donViIds == null || donViIds.size() <= 0) {
				donViIds = new ArrayList<CoQuanQuanLy>();
				CoQuanQuanLy cq = new CoQuanQuanLy();
				cq.setId(Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString()));
				donViIds.add(cq);
			}
			
			Page<KeHoachThanhTra> page = repo.findAll(keHoachThanhTraService.predicateFindAll(soQuyetDinh, tuNgay, denNgay, namThanhTra, donViIds), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/keHoachThanhTras/checkExistsData")
	@ApiOperation(value = "Lấy Kế Hoạch Thanh Tra theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy Kế Hoạch Thanh Tra thành công", response = KeHoachThanhTra.class) })
	public ResponseEntity<Object> checkExistsData(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "id", required = false) Long id,
			@RequestParam(value = "namThanhTra", required = true) Integer namThanhTra,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THANHTRA_THEM) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THANHTRA_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			KeHoachThanhTra khtt = new KeHoachThanhTra();
			khtt.setId(id);
			khtt.setNamThanhTra(namThanhTra);
			if (keHoachThanhTraService.checkExistsData(repo, khtt)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.KEHOACHTHANHTRA_EXISTS.name(),
						ApiErrorEnum.KEHOACHTHANHTRA_EXISTS.getText(), ApiErrorEnum.KEHOACHTHANHTRA_EXISTS.getText());
			} else {
				return null;
			}
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST, value = "/keHoachThanhTras")
	@ApiOperation(value = "Thêm mới Kế Hoạch Thanh Tra", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Kế Hoạch Thanh Tra thành công", response = KeHoachThanhTra.class),
			@ApiResponse(code = 201, message = "Thêm mới Kế Hoạch Thanh Tra thành công", response = KeHoachThanhTra.class) })
	public ResponseEntity<Object> create(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_KeHoachThanhTra_Post_Patch params, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THANHTRA_THEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			if (params != null && params.getKeHoachThanhTra() != null) {
				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						if (!StringUtils.isNotBlank(params.getKeHoachThanhTra().getQuyetDinhPheDuyetKTTT())
								|| params.getKeHoachThanhTra().getNgayRaQuyetDinh() == null
								|| params.getKeHoachThanhTra().getChucNangKeHoachThanhTra() == null
								|| params.getKeHoachThanhTra().getNamThanhTra() <= 0) {
							return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_REQUIRED.name(),
									ApiErrorEnum.DATA_REQUIRED.getText(), ApiErrorEnum.DATA_REQUIRED.getText());
						}
						if (!Utils.isValidStringLength(params.getKeHoachThanhTra().getQuyetDinhPheDuyetKTTT(), 255)) {
							return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_INVALID_SIZE.name(),
									ApiErrorEnum.DATA_INVALID_SIZE.getText(), ApiErrorEnum.DATA_INVALID_SIZE.getText());
						}
						if (params.getCuocThanhTras() == null || params.getCuocThanhTras().size() == 0) {
							return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.CUOCTHANHTRA_REQUIRED.name(),
									ApiErrorEnum.CUOCTHANHTRA_REQUIRED.getText(), ApiErrorEnum.CUOCTHANHTRA_REQUIRED.getText());
						}
						if (params.getCuocThanhTras() != null && params.getCuocThanhTras().size() > 0) {
							for (Medial_KeHoachThanhTra_CuocThanhTra_Post_Patch ctt : params.getCuocThanhTras()) {
								if (ctt.getDoiTuongThanhTraId() == null || ctt.getDoiTuongThanhTraId() == 0) {
									return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_REQUIRED.name(),
											ApiErrorEnum.DATA_REQUIRED.getText(), ApiErrorEnum.DATA_REQUIRED.getText());
								}
								if (!doiTuongThanhTraService.isExists(doiTuongThanhTraRepository, ctt.getDoiTuongThanhTraId())) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DOITUONGTHANHTRA_NOT_FOUND.name(),
											ApiErrorEnum.DOITUONGTHANHTRA_NOT_FOUND.getText(), ApiErrorEnum.DOITUONGTHANHTRA_NOT_FOUND.getText());
								}
								if (!Utils.isValidStringLength(ctt.getNoiDungThanhTra(), 255) || !Utils.isValidStringLength(ctt.getKyThanhTra(), 255)
										|| !Utils.isValidStringLength(ctt.getGhiChu(), 255)) {
									return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_INVALID_SIZE.name(),
											ApiErrorEnum.DATA_INVALID_SIZE.getText(), ApiErrorEnum.DATA_INVALID_SIZE.getText());
								}
							}
						}
						
						Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
						Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
						
						if (donViId != null && donViId > 0) {
							CoQuanQuanLy donVi = new CoQuanQuanLy();
							donVi.setId(donViId);
							params.getKeHoachThanhTra().setDonVi(donVi);
						}
						KeHoachThanhTra khtt = keHoachThanhTraService.save(params.getKeHoachThanhTra(), congChucId);
						if (khtt != null && khtt.getId() != null && khtt.getId() > 0) {
							for (Medial_KeHoachThanhTra_CuocThanhTra_Post_Patch ctt : params.getCuocThanhTras()) {
								CuocThanhTra cttSave = new CuocThanhTra();
								cttSave.setId(ctt.getId());
								cttSave.setHinhThucThanhTra(HinhThucThanhTraEnum.THEO_KE_HOACH);
								cttSave.setNoiDungThanhTra(ctt.getNoiDungThanhTra());
								cttSave.setKyThanhTra(ctt.getKyThanhTra());
								cttSave.setThoiHanThanhTra(ctt.getThoiHanThanhTra());
								cttSave.setGhiChu(ctt.getGhiChu());
								cttSave.setLinhVucThanhTra(ctt.getLinhVucThanhTra());
								DoiTuongThanhTra dttt = new DoiTuongThanhTra();
								dttt.setId(ctt.getDoiTuongThanhTraId());
								cttSave.setDoiTuongThanhTra(dttt);
								if (ctt.getDonViChuTriId() != null && ctt.getDonViChuTriId() > 0) {
									CoQuanQuanLy dvct = new CoQuanQuanLy();
									dvct.setId(ctt.getDonViChuTriId());
									cttSave.setDonViChuTri(dvct);
								}
								CoQuanQuanLy donVi = new CoQuanQuanLy();
								donVi.setId(donViId);
								cttSave.setDonVi(donVi);
								cttSave.setDonViPhoiHop(ctt.getDonViPhoiHop());
								cttSave.setKeHoachThanhTra(khtt);
								cuocThanhTraService.save(cttSave, congChucId);
							}
						}
						
						return new ResponseEntity<>(eass.toFullResource(khtt), HttpStatus.CREATED);
					}
				});
			}

			return new ResponseEntity<>(eass.toFullResource(new KeHoachThanhTra()), HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PATCH, value = "/keHoachThanhTras/{id}")
	@ApiOperation(value = "Cập nhật Kế Hoạch Thanh Tra", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Kế Hoạch Thanh Tra thành công", response = KeHoachThanhTra.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody Medial_KeHoachThanhTra_Post_Patch params, PersistentEntityResourceAssembler eass) {
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.THANHTRA_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			if (params != null && params.getKeHoachThanhTra() != null) {
				return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
					@Override
					public Object doInTransaction(TransactionStatus arg0) {
						params.getKeHoachThanhTra().setId(id);
						
						if (!keHoachThanhTraService.isExists(repo, id)) {
							return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
									ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
						}
						if (!StringUtils.isNotBlank(params.getKeHoachThanhTra().getQuyetDinhPheDuyetKTTT())
								|| params.getKeHoachThanhTra().getNgayRaQuyetDinh() == null
								|| params.getKeHoachThanhTra().getChucNangKeHoachThanhTra() == null
								|| params.getKeHoachThanhTra().getNamThanhTra() <= 0) {
							return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_REQUIRED.name(),
									ApiErrorEnum.DATA_REQUIRED.getText(), ApiErrorEnum.DATA_REQUIRED.getText());
						}
						if (!Utils.isValidStringLength(params.getKeHoachThanhTra().getQuyetDinhPheDuyetKTTT(), 255)) {
							return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_INVALID_SIZE.name(),
									ApiErrorEnum.DATA_INVALID_SIZE.getText(), ApiErrorEnum.DATA_INVALID_SIZE.getText());
						}
						
						if (params.getCuocThanhTras() == null || params.getCuocThanhTras().size() == 0) {
							return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.CUOCTHANHTRA_REQUIRED.name(),
									ApiErrorEnum.CUOCTHANHTRA_REQUIRED.getText(), ApiErrorEnum.CUOCTHANHTRA_REQUIRED.getText());
						}
						if (params.getCuocThanhTras() != null && params.getCuocThanhTras().size() > 0) {
							for (Medial_KeHoachThanhTra_CuocThanhTra_Post_Patch ctt : params.getCuocThanhTras()) {
								if (ctt != null && ctt.getId() > 0 && !cuocThanhTraService.isExists(cuocThanhTraRepository, ctt.getId())) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
											ApiErrorEnum.CUOCTHANHTRA_NOT_FOUND.getText(), ApiErrorEnum.CUOCTHANHTRA_NOT_FOUND.getText());
								}
								
								if (ctt.getDoiTuongThanhTraId() == null || ctt.getDoiTuongThanhTraId() == 0) {
									return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_REQUIRED.name(),
											ApiErrorEnum.DATA_REQUIRED.getText(), ApiErrorEnum.DATA_REQUIRED.getText());
								}
								if (!doiTuongThanhTraService.isExists(doiTuongThanhTraRepository, ctt.getDoiTuongThanhTraId())) {
									return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DOITUONGTHANHTRA_NOT_FOUND.name(),
											ApiErrorEnum.DOITUONGTHANHTRA_NOT_FOUND.getText(), ApiErrorEnum.DOITUONGTHANHTRA_NOT_FOUND.getText());
								}
								if (!Utils.isValidStringLength(ctt.getNoiDungThanhTra(), 255) || !Utils.isValidStringLength(ctt.getKyThanhTra(), 255)
										|| !Utils.isValidStringLength(ctt.getGhiChu(), 255)) {
									return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_INVALID_SIZE.name(),
											ApiErrorEnum.DATA_INVALID_SIZE.getText(), ApiErrorEnum.DATA_INVALID_SIZE.getText());
								}
							}
						}
						
						Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
						Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
						KeHoachThanhTra keHoachThanhTraOld = repo.findOne(keHoachThanhTraService.predicateFindOne(params.getKeHoachThanhTra().getId()));
						keHoachThanhTraOld.setGhiChu(params.getKeHoachThanhTra().getGhiChu());
						keHoachThanhTraOld.setChucNangKeHoachThanhTra(params.getKeHoachThanhTra().getChucNangKeHoachThanhTra());
						keHoachThanhTraOld.setNamThanhTra(params.getKeHoachThanhTra().getNamThanhTra());
						keHoachThanhTraOld.setNgayRaQuyetDinh(params.getKeHoachThanhTra().getNgayRaQuyetDinh());
						keHoachThanhTraOld.setNguoiKy(params.getKeHoachThanhTra().getNguoiKy());
						keHoachThanhTraOld.setQuyetDinhPheDuyetKTTT(params.getKeHoachThanhTra().getQuyetDinhPheDuyetKTTT());
						
						KeHoachThanhTra khtt = keHoachThanhTraService.save(keHoachThanhTraOld, congChucId);
						if (khtt != null && khtt.getId() != null && khtt.getId() > 0) {
							for (Medial_KeHoachThanhTra_CuocThanhTra_Post_Patch ctt : params.getCuocThanhTras()) {
								CuocThanhTra cttSave = new CuocThanhTra();
								if (ctt.getId() != null && ctt.getId() > 0) {
									CuocThanhTra cuocThanhTraOld = cuocThanhTraRepository.findOne(cuocThanhTraService.predicateFindOne(ctt.getId()));
									if (cuocThanhTraOld != null) {
										cuocThanhTraOld.setNoiDungThanhTra(ctt.getNoiDungThanhTra());
										cuocThanhTraOld.setKyThanhTra(ctt.getKyThanhTra());
										cuocThanhTraOld.setThoiHanThanhTra(ctt.getThoiHanThanhTra());
										cuocThanhTraOld.setGhiChu(ctt.getGhiChu());
										cuocThanhTraOld.setLinhVucThanhTra(ctt.getLinhVucThanhTra());
										DoiTuongThanhTra dttt = new DoiTuongThanhTra();
										dttt.setId(ctt.getDoiTuongThanhTraId());
										cuocThanhTraOld.setDoiTuongThanhTra(dttt);
										if (ctt.getDonViChuTriId() != null && ctt.getDonViChuTriId() > 0) {
											CoQuanQuanLy dvct = new CoQuanQuanLy();
											dvct.setId(ctt.getDonViChuTriId());
											cuocThanhTraOld.setDonViChuTri(dvct);
										}
										cuocThanhTraOld.setDonViPhoiHop(ctt.getDonViPhoiHop());
										cuocThanhTraOld.setKeHoachThanhTra(khtt);
										cttSave = cuocThanhTraOld;
									}
								} else {
									cttSave.setHinhThucThanhTra(HinhThucThanhTraEnum.THEO_KE_HOACH);
									cttSave.setNoiDungThanhTra(ctt.getNoiDungThanhTra());
									cttSave.setKyThanhTra(ctt.getKyThanhTra());
									cttSave.setThoiHanThanhTra(ctt.getThoiHanThanhTra());
									cttSave.setGhiChu(ctt.getGhiChu());
									cttSave.setLinhVucThanhTra(ctt.getLinhVucThanhTra());
									DoiTuongThanhTra dttt = new DoiTuongThanhTra();
									dttt.setId(ctt.getDoiTuongThanhTraId());
									cttSave.setDoiTuongThanhTra(dttt);
									if (ctt.getDonViChuTriId() != null && ctt.getDonViChuTriId() > 0) {
										CoQuanQuanLy dvct = new CoQuanQuanLy();
										dvct.setId(ctt.getDonViChuTriId());
										cttSave.setDonViChuTri(dvct);
									}
									CoQuanQuanLy donVi = new CoQuanQuanLy();
									donVi.setId(donViId);
									cttSave.setDonVi(donVi);
									cttSave.setDonViPhoiHop(ctt.getDonViPhoiHop());
									cttSave.setKeHoachThanhTra(khtt);
								}
								cuocThanhTraService.save(cttSave, congChucId);
							}
						}
						
						return new ResponseEntity<>(eass.toFullResource(khtt), HttpStatus.CREATED);
					}
				});
			}

			return new ResponseEntity<>(eass.toFullResource(new KeHoachThanhTra()), HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/keHoachThanhTras/{id}")
	@ApiOperation(value = "Xoá Kế Hoạch Thanh Tra", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Kế Hoạch Thanh Tra thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			KeHoachThanhTra keHoachThanhTra = keHoachThanhTraService.delete(repo, id);
			if (keHoachThanhTra == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			
			if (keHoachThanhTraService.checkUsedData(cuocThanhTraRepository, id)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_KEHOACHTHANHTRA_USED.name(),
						ApiErrorEnum.DATA_KEHOACHTHANHTRA_USED.getText(), ApiErrorEnum.DATA_KEHOACHTHANHTRA_USED.getText());
			}
			
			Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
					
			if (keHoachThanhTra != null && keHoachThanhTra.getId() != null && keHoachThanhTra.getId() > 0) {
				List<CuocThanhTra> cuocThanhTras = (List<CuocThanhTra>) cuocThanhTraRepository
						.findAll(cuocThanhTraService.predicateFindAllByKeHoachThanhTra(keHoachThanhTra.getId()));
				if (cuocThanhTras != null && cuocThanhTras.size() > 0) {
					for (CuocThanhTra ctt : cuocThanhTras) {
						CuocThanhTra cuocThanhTra = cuocThanhTraService.delete(cuocThanhTraRepository, ctt.getId());
						if (cuocThanhTra != null) {
							cuocThanhTraService.save(cuocThanhTra, congChucId);
						}
					}
				}
			}

			keHoachThanhTraService.save(keHoachThanhTra, congChucId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/keHoachThanhTras/{id}/cuocThanhTras")
	@ApiOperation(value = "Xoá Cuộc Thanh Tra trong Kế Hoạch Thanh Tra", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Cuộc Thanh Tra trong Kế Hoạch Thanh Tra thành công") })
	public ResponseEntity<Object> deleteCuocThanhTraTrongKeHoachThanhTra(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id, @RequestBody Medial_CuocThanhTra_Delete params) {

		try {
			List<CuocThanhTra> listDelete = new ArrayList<CuocThanhTra>();
			if (params != null && params.getCuocThanhTras().size() > 0) {
				for (Medial_CuocThanhTra cuocThanhTra : params.getCuocThanhTras()) {
					CuocThanhTra ctt = cuocThanhTraService.delete(cuocThanhTraRepository, cuocThanhTra.getId());
					if (ctt == null) {
						return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
								ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
					}
					listDelete.add(ctt);
				}
				for (CuocThanhTra ctt : listDelete) {
					cuocThanhTraService.save(ctt, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
				}
			}

			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
}