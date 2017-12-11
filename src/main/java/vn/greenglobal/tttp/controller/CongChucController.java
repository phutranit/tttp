package vn.greenglobal.tttp.controller;

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
import vn.dtt.sharedservice.cmon.consumer.citizen.CongChucSoap;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.enums.TrangThaiInvalidTokenEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.CongDan;
import vn.greenglobal.tttp.model.InvalidToken;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.model.VaiTro;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.NguoiDungRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.service.CoQuanQuanLyService;
import vn.greenglobal.tttp.service.CongChucService;
import vn.greenglobal.tttp.service.EgovService;
import vn.greenglobal.tttp.service.InvalidTokenService;
import vn.greenglobal.tttp.service.NguoiDungService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.util.CongChucList;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "congChucs", description = "Công chức")
public class CongChucController extends TttpController<CongChuc> {

	@Autowired
	private CongChucRepository repo;

	@Autowired
	private NguoiDungRepository repoNguoiDung;

	@Autowired
	private CongChucService congChucService;

	@Autowired
	private CoQuanQuanLyRepository repoCoQuanQuanLy;

	@Autowired
	private CoQuanQuanLyService coQuanQuanLyService;

	@Autowired
	private ThamSoRepository repoThamSo;

	@Autowired
	private ThamSoService thamSoService;
	
	@Autowired
	private NguoiDungService nguoiDungService;
	
	@Autowired
	private InvalidTokenService invalidTokenService;
	
	public CongChucController(BaseRepository<CongChuc, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/congChucs")
	@ApiOperation(value = "Lấy danh sách Công Chức", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "vaiTro", required = false) String vaiTro,
			@RequestParam(value = "coQuanQuanLyId", required = false) Long coQuanQuanLyId,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_LIETKE) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			Long coQuanQuanLyLoginId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
			CoQuanQuanLy coQuanQuanLyLoginCC = repoCoQuanQuanLy.findOne(coQuanQuanLyService.predicateFindOne(coQuanQuanLyLoginId));
			
			Page<CongChuc> page = repo.findAll(congChucService.predicateFindAll(tuKhoa, vaiTro, coQuanQuanLyId, congChucId, coQuanQuanLyLoginCC), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/congChucs/vaiTro")
	@ApiOperation(value = "Lấy danh sách Công Chức", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListByVaiTro(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "coQuanQuanLyId") Long coQuanQuanLyId, @RequestParam(value = "vaiTro") String vaiTro,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Page<CongChuc> page = repo.findAll(congChucService.predicateFindByVaiTro(coQuanQuanLyId, vaiTro), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/lanhDaoTiepCongDans")
	@ApiOperation(value = "Lấy danh sách Lãnh đạo tiếp công dân", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListDanhSachLanhDaoTCD(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			CongChuc congChuc = repo.findOne(
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			ThamSo thamSo = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			CoQuanQuanLy donVi = null;
			if (congChuc != null && congChuc.getCoQuanQuanLy() != null) {
				if (thamSo != null && thamSo.getGiaTri().toString()
						.equals(congChuc.getCoQuanQuanLy().getCapCoQuanQuanLy().getId().toString())) {
					donVi = congChuc.getCoQuanQuanLy().getCha();
				} else {
					donVi = congChuc.getCoQuanQuanLy();
				}
			}
			Page<CongChuc> page = repo.findAll(congChucService.predicateFindLanhDaoTiepCongDan(donVi), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST, value = "/congChucs")
	@ApiOperation(value = "Thêm mới Công Chức", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Công Chức thành công", response = CongChuc.class),
			@ApiResponse(code = 201, message = "Thêm mới Công Chức thành công", response = CongChuc.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody CongChuc congChuc, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_THEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (congChuc.getNguoiDung() == null) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.LOGIN_INFOMATION_REQUIRED.name(),
						ApiErrorEnum.LOGIN_INFOMATION_REQUIRED.getText(), ApiErrorEnum.LOGIN_INFOMATION_REQUIRED.getText());
			} else {
				if (congChuc.getNguoiDung().getMatKhau() == null || congChuc.getNguoiDung().getMatKhau().isEmpty()) {
					return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.MATKHAU_REQUIRED.name(),
							ApiErrorEnum.MATKHAU_REQUIRED.getText(), ApiErrorEnum.MATKHAU_REQUIRED.getText());
				}
				if (congChuc.getNguoiDung().getEmail() == null | congChuc.getNguoiDung().getEmail().isEmpty()) {
					return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.EMAIL_REQUIRED.name(),
							ApiErrorEnum.EMAIL_REQUIRED.getText(), ApiErrorEnum.EMAIL_REQUIRED.getText());
				}

				if (congChuc.getNguoiDung().getEmail() != null
						&& !Utils.isValidEmailAddress(congChuc.getNguoiDung().getEmail())) {
					return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.EMAIL_INVALID.name(),
							ApiErrorEnum.EMAIL_INVALID.getText(), ApiErrorEnum.EMAIL_INVALID.getText());
				}
			}

			if (congChuc.getHoVaTen() == null && congChuc.getHoVaTen().isEmpty()) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.HOVATEN_REQUIRED.name(),
						ApiErrorEnum.HOVATEN_REQUIRED.getText(), ApiErrorEnum.HOVATEN_REQUIRED.getText());
			}

			if (congChuc.getCoQuanQuanLy() == null) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.COQUANQUANLY_REQUIRED.name(),
						ApiErrorEnum.COQUANQUANLY_REQUIRED.getText(), ApiErrorEnum.COQUANQUANLY_REQUIRED.getText());
			}

			if (congChuc.getChucVu() == null) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.CHUCVU_REQUIRED.name(),
						ApiErrorEnum.CHUCVU_REQUIRED.getText(), ApiErrorEnum.CHUCVU_REQUIRED.getText());
			}

			if (StringUtils.isNotBlank(congChuc.getNguoiDung().getEmail())
					&& congChucService.checkExistsData(repo, congChuc)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.EMAIL_EXISTS.name(),
						ApiErrorEnum.EMAIL_EXISTS.getText(), ApiErrorEnum.EMAIL_EXISTS.getText());
			}

			congChuc.getNguoiDung().setActive(true);
			if (!VaiTroEnum.CHUYEN_VIEN.equals(congChuc.getNguoiDung().getVaiTroMacDinh().getLoaiVaiTro())) {
				congChuc.getNguoiDung().setChuyenVienNhapLieu(false);
			}
			return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
				@Override
				public Object doInTransaction(TransactionStatus arg0) {
					congChuc.getNguoiDung().updatePassword(congChuc.getNguoiDung().getMatKhau());
					nguoiDungService.save(congChuc.getNguoiDung(), Long.valueOf(
							profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
					congChuc.setHoVaTenSearch(Utils.unAccent(congChuc.getHoVaTen().trim()));
					return congChucService.doSave(congChuc,
							Long.valueOf(
									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
							eass, HttpStatus.CREATED);
				}
			});
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/congChucs/{id}")
	@ApiOperation(value = "Lấy Công Chức theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Công Chức thành công", response = CongChuc.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			CongChuc congChuc = repo.findOne(congChucService.predicateFindOne(id));
			if (congChuc == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(eass.toFullResource(congChuc), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PATCH, value = "/congChucs/{id}")
	@ApiOperation(value = "Cập nhật Công Chức", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Công Chức thành công", response = CongChuc.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody CongChuc congChuc, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			congChuc.setId(id);
			if (congChuc.getNguoiDung() == null) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.LOGIN_INFOMATION_REQUIRED.name(),
						ApiErrorEnum.LOGIN_INFOMATION_REQUIRED.getText(), ApiErrorEnum.LOGIN_INFOMATION_REQUIRED.getText());
			}
			/*
			 * else { if (congChuc.getNguoiDung().getMatKhau() == null ||
			 * congChuc.getNguoiDung().getMatKhau().isEmpty()) { return
			 * Utils.responseErrors(HttpStatus.BAD_REQUEST, "MATKHAU_REQUIRED",
			 * "Trường mật khẩu không được để trống!"); } if
			 * (congChuc.getNguoiDung().getTenDangNhap() == null ||
			 * congChuc.getNguoiDung().getTenDangNhap().isEmpty()) { return
			 * Utils.responseErrors(HttpStatus.BAD_REQUEST,
			 * "TENDANGNHAP_REQUIRED",
			 * "Trường tên đăng nhập không được để trống!"); } }
			 */

			if (congChuc.getNguoiDung().getEmail() != null
					&& !Utils.isValidEmailAddress(congChuc.getNguoiDung().getEmail())) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.EMAIL_INVALID.name(),
						ApiErrorEnum.EMAIL_INVALID.getText(), ApiErrorEnum.EMAIL_INVALID.getText());
			}

			if (StringUtils.isNotBlank(congChuc.getNguoiDung().getEmail())
					&& congChucService.checkExistsData(repo, congChuc)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.EMAIL_EXISTS.name(),
						ApiErrorEnum.EMAIL_EXISTS.getText(), ApiErrorEnum.EMAIL_EXISTS.getText());
			}

			if (congChuc.getHoVaTen() == null && congChuc.getHoVaTen().isEmpty()) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.HOVATEN_REQUIRED.name(),
						ApiErrorEnum.HOVATEN_REQUIRED.getText(), ApiErrorEnum.HOVATEN_REQUIRED.getText());
			}

			if (congChuc.getCoQuanQuanLy() == null) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.COQUANQUANLY_REQUIRED.name(),
						ApiErrorEnum.COQUANQUANLY_REQUIRED.getText(), ApiErrorEnum.COQUANQUANLY_REQUIRED.getText());
			}

			if (congChuc.getChucVu() == null) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.CHUCVU_REQUIRED.name(),
						ApiErrorEnum.CHUCVU_REQUIRED.getText(), ApiErrorEnum.CHUCVU_REQUIRED.getText());
			}

			if (!congChucService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			congChuc.getNguoiDung().setActive(true);
			if (!VaiTroEnum.CHUYEN_VIEN.equals(congChuc.getNguoiDung().getVaiTroMacDinh().getLoaiVaiTro())) {
				congChuc.getNguoiDung().setChuyenVienNhapLieu(false);
			}
			return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
				@Override
				public Object doInTransaction(TransactionStatus arg0) {
					if (congChuc.getNguoiDung().getMatKhau() != null
							&& !"".equals(congChuc.getNguoiDung().getMatKhau())) {
						congChuc.getNguoiDung().updatePassword(congChuc.getNguoiDung().getMatKhau());
					} else {
						NguoiDung nd = repoNguoiDung.findOne(congChuc.getNguoiDung().getId());
						congChuc.getNguoiDung().setMatKhau(nd.getMatKhau());
						congChuc.getNguoiDung().setSalkey(nd.getSalkey());
					}
					
					CongChuc congChucOld = repo.findOne(congChucService.predicateFindOne(congChuc.getId()));
					NguoiDung nguoiDung = repoNguoiDung.findOne(nguoiDungService.predicateFindOne(congChuc.getNguoiDung().getId()));
					InvalidToken invalidToken = invalidTokenService.predFindToKenCurrentByUser(nguoiDung.getId());
					
					boolean checkVaiTros = false;
					if (congChuc.getNguoiDung().getVaiTros() != null) {
						if (congChucOld != null) {
							VaiTro vaiTro = congChuc.getNguoiDung().getVaiTros().iterator().next();
							for (VaiTro vaiTroOld : nguoiDung.getVaiTros()) {
								if (vaiTroOld.getId() != vaiTro.getId()) { 
									checkVaiTros = true;
									break;
								}
							}
						} else { 
							checkVaiTros = true;
						}
					}
					
					if (!congChucOld.getHoVaTen().equals(congChuc.getHoVaTen()) || !nguoiDung.getEmail().equals(congChuc.getNguoiDung().getEmail()) || 
							!nguoiDung.getMatKhau().equals(congChuc.getNguoiDung().getMatKhau()) ||
							nguoiDung.getVaiTroMacDinh().getId() != congChuc.getNguoiDung().getVaiTroMacDinh().getId() || 
							checkVaiTros) {
						if (invalidToken != null) { 
							invalidToken.setActive(false);
							invalidToken.setTrangThaiToken(TrangThaiInvalidTokenEnum.THAY_DOI_THONG_TIN_NGUOI_DUNG);
							invalidTokenService.save(invalidToken, Long.valueOf(
									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
						}
					}

					nguoiDungService.save(congChuc.getNguoiDung(), Long.valueOf(
							profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
					congChuc.setHoVaTenSearch(Utils.unAccent(congChuc.getHoVaTen().trim()));
					return congChucService.doSave(congChuc,
							Long.valueOf(
									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
							eass, HttpStatus.CREATED);
				}

			});
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/congChucs/{id}")
	@ApiOperation(value = "Xoá Công Chức", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Công Chức thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_XOA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			if (id.equals(1L)) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.CONGCHUC_FORBIDDEN.name(),
						ApiErrorEnum.CONGCHUC_FORBIDDEN.getText(), ApiErrorEnum.CONGCHUC_FORBIDDEN.getText());
			}

			CongChuc congChuc = congChucService.delete(repo, id);
			if (congChuc == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			} else {
				congChuc.getNguoiDung().setDaXoa(true);
				congChuc.getNguoiDung().setActive(false);
			}

			congChucService.save(congChuc, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			nguoiDungService.save(congChuc.getNguoiDung(), Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/congChucs/truongDoanTTXMs")
	@ApiOperation(value = "Lấy danh sách trưởng đoàn TTXM", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy danh sách trưởng đoàn TTXM thành công", response = CongChuc.class) })
	public @ResponseBody Object getListTruongDoanTTXM(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			PersistentEntityResourceAssembler eass, Pageable pageable) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Long donViId = (Long) profileUtil.getCommonProfile(authorization).getAttribute("donViId");

			Page<CongChuc> page = repo.findAll(congChucService.predicateFindAllTruongDoanTTXM(donViId), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/congChucs/{donViId}/thuocDonVi")
	@ApiOperation(value = "Lấy Danh sách người đại diện theo cơ quan đơn vị id", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getDSNguoiDaiDienByCoQuanDonViId(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("donViId") long id, @RequestParam(value = "isTruongPhong", required = false) boolean isTruongPhong,
			Pageable pageable, PersistentEntityResourceAssembler eass) {
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			CoQuanQuanLy coQuanDonVi = repoCoQuanQuanLy.findOne(id);
			if (coQuanDonVi == null) { 
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			Page<CongChuc> page = repo.findAll(congChucService.predicateFindDSNguoiDaiDienByCoQuanDonViId(coQuanDonVi.getId(), isTruongPhong),
					pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/congChucs/egov/{id}")
	@ApiOperation(value = "Lấy Công Chức Egov theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Công Chức thành công") })
	public ResponseEntity<Object> getCongChucEgovById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			CongChucSoap congChuc = EgovService.getCongChucEgovByCongChucId(id);
			//CongChuc congChuc = repo.findOne(congChucService.predicateFindOne(id));
			if (congChuc == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(congChuc, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/congChucs/egov/cqql/{cqqlid}")
	@ApiOperation(value = "Lấy DS Công Chức theo cqql Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy Công Chức thành công") })
	public ResponseEntity<Object> getCongChucEgovByCqqlId(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("cqqlid") long id, PersistentEntityResourceAssembler eass) {
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.CONGCHUC_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			CongChucList congChuc = EgovService.getDsCongChucByCQQL(id);
			//CongChuc congChuc = repo.findOne(congChucService.predicateFindOne(id));
			if (congChuc == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}

			return new ResponseEntity<>(congChuc, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/congChucs/egov/checkChange")
	@ApiOperation(value = "Kiểm tra công dân tồn tại", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> checkChange(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value ="username", required = false) String username) {
		return null;
	}
}
