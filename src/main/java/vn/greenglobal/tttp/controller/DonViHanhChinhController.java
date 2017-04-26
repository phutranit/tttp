package vn.greenglobal.tttp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.PagedResources;
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
import vn.greenglobal.tttp.model.DonViHanhChinh;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.repository.DonViHanhChinhRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.service.DonViHanhChinhService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "donViHanhChinhs", description = "Đơn Vị Hành Chính")
public class DonViHanhChinhController extends TttpController<DonViHanhChinh> {

	@Autowired
	private DonViHanhChinhRepository repo;

	@Autowired
	private ThamSoRepository repoThamSo;

	@Autowired
	DonViHanhChinhService donViHanhChinhService;

	@Autowired
	ThamSoService thamSoService;

	public DonViHanhChinhController(BaseRepository<DonViHanhChinh, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/donViHanhChinhs")
	@ApiOperation(value = "Lấy danh sách Đơn Vị Hành Chính", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "ten", required = false) String ten,
			@RequestParam(value = "capDonViHanhChinh", required = false) Long capDonViHanhChinh,
			@RequestParam(value = "cha", required = false) Long cha, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DONVIHANHCHINH_LIETKE) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		
		Page<DonViHanhChinh> page = repo.findAll(donViHanhChinhService.predicateFindAll(ten, cha, capDonViHanhChinh),
				pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/donViHanhChinhs/capTinhThanhPho")
	@ApiOperation(value = "Lấy danh sách Đơn Vị Hành Chính Cấp Tỉnh Thành Phố", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<DonViHanhChinh> getListDonViHanhChinhTheoCapTinhThanhPho(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {
		
		Page<DonViHanhChinh> page = null;
		ThamSo thamSoOne = repoThamSo.findOne(thamSoService.predicateFindTen("CDVHC_TINH"));
		ThamSo thamSoTwo = repoThamSo.findOne(thamSoService.predicateFindTen("CDVHC_THANH_PHO_TRUC_THUOC_TW"));
		ThamSo thamSoThree = repoThamSo.findOne(thamSoService.predicateFindTen("CDVHC_THANH_PHO-TRUC_THUOC_TINH"));
		if (thamSoOne != null && thamSoTwo != null && thamSoThree != null) {
			page = repo.findAll(
					donViHanhChinhService.predicateFindCapTinhThanhPho(new Long(thamSoOne.getGiaTri().toString()),
							new Long(thamSoTwo.getGiaTri().toString()), new Long(thamSoThree.getGiaTri().toString())),
					pageable);
		}

		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/donViHanhChinhs/capQuanHuyen")
	@ApiOperation(value = "Lấy danh sách Đơn Vị Hành Chính Cấp Quận Huyện", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<DonViHanhChinh> getListDonViHanhChinhTheoCapQuanHuyen(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "cha", required = false) Long cha, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		Page<DonViHanhChinh> page = null;
		ThamSo thamSoOne = repoThamSo.findOne(thamSoService.predicateFindTen("CDVHC_QUAN"));
		ThamSo thamSoTwo = repoThamSo.findOne(thamSoService.predicateFindTen("CDVHC_HUYEN"));
		if (thamSoOne != null && thamSoOne != null) {
			page = repo.findAll(donViHanhChinhService.predicateFindCapQuanHuyen(cha,
					new Long(thamSoOne.getGiaTri().toString()), new Long(thamSoTwo.getGiaTri().toString())), pageable);
		}

		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/donViHanhChinhs/capPhuongXa")
	@ApiOperation(value = "Lấy danh sách Đơn Vị Hành Chính Cấp Phường Xã", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<DonViHanhChinh> getListDonViHanhChinhTheoCapPhuongXa(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "cha", required = false) Long cha, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		Page<DonViHanhChinh> page = null;
		ThamSo thamSoOne = repoThamSo.findOne(thamSoService.predicateFindTen("CDVHC_PHUONG"));
		ThamSo thamSoTwo = repoThamSo.findOne(thamSoService.predicateFindTen("CDVHC_XA"));
		if (thamSoOne != null && thamSoOne != null) {
			page = repo.findAll(donViHanhChinhService.predicateFindCapQuanHuyen(cha,
					new Long(thamSoOne.getGiaTri().toString()), new Long(thamSoTwo.getGiaTri().toString())), pageable);
		}

		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/donViHanhChinhs")
	@ApiOperation(value = "Thêm mới Đơn Vị Hành Chính", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Đơn Vị Hành Chính thành công", response = DonViHanhChinh.class),
			@ApiResponse(code = 201, message = "Thêm mới Đơn Vị Hành Chính thành công", response = DonViHanhChinh.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody DonViHanhChinh donViHanhChinh, PersistentEntityResourceAssembler eass) {
		
		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DONVIHANHCHINH_THEM) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		
		return Utils.doSave(repo, donViHanhChinh, new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/donViHanhChinhs/{id}")
	@ApiOperation(value = "Lấy Đơn Vị Hành Chính theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Đơn Vị Hành Chính thành công", response = DonViHanhChinh.class) })
	public ResponseEntity<Object> getById(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DONVIHANHCHINH_XEM) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		
		DonViHanhChinh donViHanhChinh = repo.findOne(donViHanhChinhService.predicateFindOne(id));
		if (donViHanhChinh == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(eass.toFullResource(donViHanhChinh), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/donViHanhChinhs/{id}")
	@ApiOperation(value = "Cập nhật Đơn Vị Hành Chính", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Đơn Vị Hành Chính thành công", response = DonViHanhChinh.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody DonViHanhChinh donViHanhChinh, PersistentEntityResourceAssembler eass) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DONVIHANHCHINH_SUA) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		
		donViHanhChinh.setId(id);
		if (!donViHanhChinhService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		return Utils.doSave(repo, donViHanhChinh, new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/donViHanhChinhs/{id}")
	@ApiOperation(value = "Xoá Đơn Vị Hành Chính", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Đơn Vị Hành Chính thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DONVIHANHCHINH_XOA) == null) {
			return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
		}
		
		DonViHanhChinh donViHanhChinh = donViHanhChinhService.delete(repo, id);
		if (donViHanhChinh == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		Utils.save(repo, donViHanhChinh, new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
