package vn.greenglobal.tttp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
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
import vn.greenglobal.tttp.enums.TienDoThanhTraEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CuocThanhTra;
import vn.greenglobal.tttp.model.DoiTuongThanhTra;
import vn.greenglobal.tttp.model.ThanhVienDoan;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CuocThanhTraRepository;
import vn.greenglobal.tttp.repository.DoiTuongThanhTraRepository;
import vn.greenglobal.tttp.service.CoQuanQuanLyService;
import vn.greenglobal.tttp.service.CuocThanhTraService;
import vn.greenglobal.tttp.service.DoiTuongThanhTraService;
import vn.greenglobal.tttp.service.ThanhVienDoanService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "cuocThanhTras", description = "Thông tin cuộc thanh tra")
public class CuocThanhTraController extends TttpController<CuocThanhTra> {

	@Autowired
	private CuocThanhTraRepository repo;
	
	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepository;
	
	@Autowired
	private DoiTuongThanhTraRepository doiTuongThanhTraRepository;

	@Autowired
	private CuocThanhTraService cuocThanhTraService;
	
	@Autowired
	private ThanhVienDoanService thanhVienDoanService;
	
	@Autowired
	private CoQuanQuanLyService coQuanQuanLyService;
	
	@Autowired
	private DoiTuongThanhTraService doiTuongThanhTraService;
	
	@Autowired
	protected PagedResourcesAssembler<CoQuanQuanLy> assemblerCoQuanQuanLy;

	public CuocThanhTraController(BaseRepository<CuocThanhTra, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/cuocThanhTras")
	@ApiOperation(value = "Lấy danh sách Cuộc Thanh Tra", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "namThanhTra", required = false) Integer namThanhTra,
			@RequestParam(value = "quyetDinhPheDuyetKTTT", required = false) String quyetDinhPheDuyetKTTT,
			@RequestParam(value = "soQuyetDinh", required = false) String soQuyetDinh,
			@RequestParam(value = "tenDoiTuongThanhTra", required = false) String tenDoiTuongThanhTra,
			@RequestParam(value = "loaiHinhThanhTra", required = false) String loaiHinhThanhTra,
			@RequestParam(value = "linhVucThanhTra", required = false) String linhVucThanhTra,
			@RequestParam(value = "tienDoThanhTra", required = false) String tienDoThanhTra,
			@RequestParam(value = "soKetLuanThanhTra", required = false) String soKetLuanThanhTra,
			@RequestParam(value = "soQuyetDinhXL", required = false) String soQuyetDinhXL,
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
			Page<CuocThanhTra> page = repo.findAll(cuocThanhTraService.predicateFindAll(namThanhTra, quyetDinhPheDuyetKTTT, tenDoiTuongThanhTra,
					soQuyetDinh, loaiHinhThanhTra, linhVucThanhTra, tienDoThanhTra, tuNgay, denNgay, donViId, soKetLuanThanhTra, soQuyetDinhXL), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/cuocThanhTraTrungOlds")
	@ApiOperation(value = "Lấy danh sách Cuộc Thanh Tra Trùng", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListThanhTraTrungOld(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "tenDoiTuongThanhTra", required = false) String tenDoiTuongThanhTra,
			@RequestParam(value = "soQuyetDinh", required = false) String soQuyetDinh,
			@RequestParam(value = "namThanhTra", required = false) Integer namThanhTra,
			Pageable pageable, PersistentEntityResourceAssembler eass) {

		try {
			
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			List<Long> cuocThanhTraIds = new ArrayList<Long>();
			int current = Utils.localDateTimeNow().getYear();
			
			if (namThanhTra != null && namThanhTra > 0) {
				cuocThanhTraIds.addAll(getCuocThanhTraIdsOld(namThanhTra));
			} else {
				for (int i = current; i >= 2010; i--) {
					cuocThanhTraIds.addAll(getCuocThanhTraIdsOld(i));
				}
			}
			
			Page<CuocThanhTra> page = repo.findAll(cuocThanhTraService.predicateFindThanhTraTrungResultOld(cuocThanhTraIds, tenDoiTuongThanhTra, soQuyetDinh), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/cuocThanhTraTrungs")
	@ApiOperation(value = "Lấy danh sách Cuộc Thanh Tra Trùng", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListThanhTraTrung(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "tenDoiTuongThanhTra", required = false) String tenDoiTuongThanhTra,
			@RequestParam(value = "soQuyetDinhPheDuyetKHTT", required = false) String soQuyetDinhPheDuyetKHTT,
			@RequestParam(value = "donViId", required = false) Long donViId,
			@RequestParam(value = "namThanhTra", required = false) Integer namThanhTra,
			Pageable pageable, PersistentEntityResourceAssembler eass) {

		try {
			
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			List<List<Object>> cuocThanhTraIdVaDoiTuongIds = new ArrayList<List<Object>>();
			List<List<Object>> cuocThanhTraIdVaDoiTuongIdsResult = new ArrayList<List<Object>>();
			HashSet<String> cuocThanhTraIdVaDoiTuongIdsUnique = new HashSet<String>();
			List<CuocThanhTra> listResult = new ArrayList<CuocThanhTra>();
			
			int current = Utils.localDateTimeNow().getYear();
			
			if (namThanhTra != null && namThanhTra > 0) {
				cuocThanhTraIdVaDoiTuongIds.addAll(getCuocThanhTraIdVaDoiTuongIds(namThanhTra, tenDoiTuongThanhTra, donViId, soQuyetDinhPheDuyetKHTT));
			} else {
				for (int i = current; i >= 2010; i--) {
					cuocThanhTraIdVaDoiTuongIds.addAll(getCuocThanhTraIdVaDoiTuongIds(i, tenDoiTuongThanhTra, donViId, soQuyetDinhPheDuyetKHTT));
				}
			}
			
			for (int j = 0; j <= cuocThanhTraIdVaDoiTuongIds.size() - 1; j++) {
				cuocThanhTraIdVaDoiTuongIdsUnique.add(cuocThanhTraIdVaDoiTuongIds.get(j).get(0).toString().concat(((DoiTuongThanhTra) cuocThanhTraIdVaDoiTuongIds.get(j).get(1)).getId().toString()));
			}
			
			if (cuocThanhTraIdVaDoiTuongIdsUnique != null && cuocThanhTraIdVaDoiTuongIdsUnique.size() > 0) {
				List<String> strList = new ArrayList<String>(cuocThanhTraIdVaDoiTuongIdsUnique);
				for (String u : strList) {
					List<Object> ll = new ArrayList<Object>();
					ll.add(u.substring(0, 1));
					ll.add(u.substring(1, 2));
					cuocThanhTraIdVaDoiTuongIdsResult.add(ll);
				}
			}
			
			System.out.println("cuocThanhTraIdVaDoiTuongIdsResult.size(): " + cuocThanhTraIdVaDoiTuongIdsResult.size());
			
			for (List<Object> l : cuocThanhTraIdVaDoiTuongIdsResult) {
				DoiTuongThanhTra dt = doiTuongThanhTraRepository.findOne(doiTuongThanhTraService.predicateFindOne(Long.valueOf(l.get(1).toString())));
				List<CuocThanhTra> ctts = (List<CuocThanhTra>) repo.findAll(cuocThanhTraService.predicateFindThanhTraTrungResult(Long.valueOf(l.get(0).toString()), dt));
				ctts.get(0).setDoiTuongThanhTraTrung(dt);
				System.out.println("DoiTuongThanhTra: " + ctts.get(0).getDoiTuongThanhTraTrung().getTen() + " --- "
						+ ctts.get(0).getDoiTuongThanhTraTrung().getId() + "   CuocThanhTraId: " + ctts.get(0).getId());
				listResult.add(ctts.get(0));
			}
			
			return assembler.toResource(new PageImpl<CuocThanhTra>(listResult, pageable, listResult.size()), (ResourceAssembler) eass);
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

			Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			CoQuanQuanLy donVi = new CoQuanQuanLy();
			donVi.setId(Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString()));
			cuocThanhTra.setDonVi(donVi);
			for (ThanhVienDoan tvd : cuocThanhTra.getThanhVienDoans()) {
				thanhVienDoanService.save(tvd, congChucId);
			}
			checkTienDoThanhTra(cuocThanhTra);
			return cuocThanhTraService.doSave(cuocThanhTra, congChucId, eass, HttpStatus.CREATED);
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

			Long congChucId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
			cuocThanhTra.setId(id);
			CoQuanQuanLy donVi = new CoQuanQuanLy();
			donVi.setId(Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString()));
			cuocThanhTra.setDonVi(donVi);
			for (ThanhVienDoan tvd : cuocThanhTra.getThanhVienDoans()) {
				thanhVienDoanService.save(tvd, congChucId);
			}
			checkDataCuocThanhTra(cuocThanhTra);
			checkTienDoThanhTra(cuocThanhTra);
			return cuocThanhTraService.doSave(cuocThanhTra, congChucId, eass, HttpStatus.OK);
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

			if (cuocThanhTra.getKeHoachThanhTra() == null) {
				Long congChucId = Long
						.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
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
	
	private CuocThanhTra checkTienDoThanhTra(CuocThanhTra cuocThanhTra) {
		if (cuocThanhTra.getSoQuyetDinhVeViecThanhTra() != null && !"".equals(cuocThanhTra.getSoQuyetDinhVeViecThanhTra())
				&& cuocThanhTra.getNgayRaQuyetDinh() != null && !"".equals(cuocThanhTra.getNgayRaQuyetDinh())
				&& cuocThanhTra.getNgayCongBoQuyetDinhThanhTra() != null && !"".equals(cuocThanhTra.getNgayCongBoQuyetDinhThanhTra())
				&& (cuocThanhTra.getSoThongBaoKetThucTTTT() == null || "".equals(cuocThanhTra.getSoThongBaoKetThucTTTT())
						|| cuocThanhTra.getNgayBanHanhThongBaoKetThucTTTT() == null || "".equals(cuocThanhTra.getNgayBanHanhThongBaoKetThucTTTT()))
				&& (cuocThanhTra.getSoKetLuanThanhTra() == null || "".equals(cuocThanhTra.getSoKetLuanThanhTra())
						|| cuocThanhTra.getNgayBanHanhKetLuanThanhTra() == null || "".equals(cuocThanhTra.getNgayBanHanhKetLuanThanhTra()))) {
			cuocThanhTra.setTienDoThanhTra(TienDoThanhTraEnum.DANG_TIEN_HANH);
		}
		if (cuocThanhTra.getSoThongBaoKetThucTTTT() != null && !"".equals(cuocThanhTra.getSoThongBaoKetThucTTTT())
				&& cuocThanhTra.getNgayBanHanhThongBaoKetThucTTTT() != null && !"".equals(cuocThanhTra.getNgayBanHanhThongBaoKetThucTTTT())
				&& (cuocThanhTra.getSoQuyetDinhVeViecThanhTra() == null || "".equals(cuocThanhTra.getSoQuyetDinhVeViecThanhTra())
						|| cuocThanhTra.getNgayRaQuyetDinh() == null || "".equals(cuocThanhTra.getNgayRaQuyetDinh())
						|| cuocThanhTra.getNgayCongBoQuyetDinhThanhTra() == null || "".equals(cuocThanhTra.getNgayCongBoQuyetDinhThanhTra()))
				&& (cuocThanhTra.getSoKetLuanThanhTra() == null || "".equals(cuocThanhTra.getSoKetLuanThanhTra())
						|| cuocThanhTra.getNgayBanHanhKetLuanThanhTra() == null || "".equals(cuocThanhTra.getNgayBanHanhKetLuanThanhTra()))) {
			cuocThanhTra.setTienDoThanhTra(TienDoThanhTraEnum.KET_THUC_THANH_TRA_TRUC_TIEP);
		}
		if (cuocThanhTra.getSoKetLuanThanhTra() != null && !"".equals(cuocThanhTra.getSoKetLuanThanhTra())
				&& cuocThanhTra.getNgayBanHanhKetLuanThanhTra() != null && !"".equals(cuocThanhTra.getNgayBanHanhKetLuanThanhTra())
				&& (cuocThanhTra.getSoQuyetDinhVeViecThanhTra() == null || "".equals(cuocThanhTra.getSoQuyetDinhVeViecThanhTra())
						|| cuocThanhTra.getNgayRaQuyetDinh() == null || "".equals(cuocThanhTra.getNgayRaQuyetDinh())
						|| cuocThanhTra.getNgayCongBoQuyetDinhThanhTra() == null || "".equals(cuocThanhTra.getNgayCongBoQuyetDinhThanhTra()))
				&& (cuocThanhTra.getSoThongBaoKetThucTTTT() == null || "".equals(cuocThanhTra.getSoThongBaoKetThucTTTT())
						|| cuocThanhTra.getNgayBanHanhThongBaoKetThucTTTT() == null || "".equals(cuocThanhTra.getNgayBanHanhThongBaoKetThucTTTT()))) {
			cuocThanhTra.setTienDoThanhTra(TienDoThanhTraEnum.DA_BAN_HANH_KET_LUAN);
		}
		return cuocThanhTra;
	}

	private CuocThanhTra checkDataCuocThanhTra(CuocThanhTra cuocThanhTra) {
		if (!cuocThanhTra.isThanhVienDoan()) {
			cuocThanhTra.setSoQuyetDinhThanhLapDoan("");
			cuocThanhTra.setNgayRaQuyetDinhThanhLapDoan(null);
			cuocThanhTra.setThanhVienDoans(new ArrayList<ThanhVienDoan>());
		} else if (!cuocThanhTra.isChuyenCoQuanDieuTra()) {
			/*cuocThanhTra.setSoVuDieuTra(0);
			cuocThanhTra.setSoDoiTuongDieuTra(9);
			cuocThanhTra.setCoQuanDieuTra(null);
			cuocThanhTra.setSoQuyetDinhDieuTra("");
			cuocThanhTra.setNguoiRaQuyetDinhDieuTra("");*/
		} else if (!cuocThanhTra.isViPham()) {
			/*cuocThanhTra.setSoQDXuPhatHCDuocBanHanh("");
			cuocThanhTra.setNoiDungViPhamKhac("");
			cuocThanhTra.setToChucXuLyHanhChinhViPham(0);
			cuocThanhTra.setCaNhanXuLyHanhChinhViPham(0);
			cuocThanhTra.setTienThuViPham(0);
			cuocThanhTra.setDatThuViPham(0);
			cuocThanhTra.setTienTraKienNghiThuHoi(0);
			cuocThanhTra.setDatTraKienNghiThuHoi(0);
			cuocThanhTra.setTienTraKienNghiKhac(0);
			cuocThanhTra.setDatTraKienNghiKhac(0);
			cuocThanhTra.setTienDaThuTrongQuaTrinhThanhTra(0);
			cuocThanhTra.setDatDaThuTrongQuaTrinhThanhTra(0);
			cuocThanhTra.setDatLanChiem(0);
			cuocThanhTra.setGiaoDatCapDatSaiDoiTuong(0);
			cuocThanhTra.setCapBanDatTraiThamQuyen(0);
			cuocThanhTra.setCapGCNQSDDatSai(0);
			cuocThanhTra.setChoThueKhongDungQD(0);
			cuocThanhTra.setDatKhongDungMDSaiQD(0);
			cuocThanhTra.setBoHoangHoa(0);
			cuocThanhTra.setViPhamKhac(0);
			cuocThanhTra.setSoTienViPham(0);
			cuocThanhTra.setSoTienXuPhatViPham(0);
			cuocThanhTra.setSoTienKienNghiThuHoi(0);
			cuocThanhTra.setSoTienTichThuXuLyTaiSanViPham(0);
			cuocThanhTra.setSoTienTieuHuyXuLyTaiSanViPham(0);*/
		} else if (!cuocThanhTra.isNoiDungThamNhung()) {
			cuocThanhTra.setTenNguoi("");
			cuocThanhTra.setToChucXuLyHanhChinhThamNhung(0);
			cuocThanhTra.setCaNhanXuLyHanhChinhThamNhung(0);
			cuocThanhTra.setSoVuThamNhung(0);
			cuocThanhTra.setSoDoiTuongThamNhung(0);
			cuocThanhTra.setSoNguoiXuLyTrachNhiemDungDau(0);
			cuocThanhTra.setTienThamNhung(0);
			cuocThanhTra.setTaiSanKhacThamNhung(0);
			cuocThanhTra.setDatThamNhung(0);
			cuocThanhTra.setTienKienNghiThuHoi(0);
			cuocThanhTra.setTaiSanKhacKienNghiThuHoi(0);
			cuocThanhTra.setDatKienNghiThuHoi(0);
			cuocThanhTra.setTienDaThu(0);
			cuocThanhTra.setTaiSanKhacDaThu(0);
			cuocThanhTra.setDatDaThu(0);
		}
		return cuocThanhTra;
	}
	
	private List<Long> getCuocThanhTraIdsOld(int namThanhTra) {
		List<Long> cuocThanhTraIds = new ArrayList<Long>();
		List<CuocThanhTra> listCuocThanhTraTheoNam = (List<CuocThanhTra>) repo.findAll(cuocThanhTraService.predicateFindThanhTraTrungOld(namThanhTra));
		
		if (listCuocThanhTraTheoNam != null && listCuocThanhTraTheoNam.size() > 0) {
			for (int j = 0; j < listCuocThanhTraTheoNam.size() - 1; j++) {
				for (int k = j + 1; k < listCuocThanhTraTheoNam.size(); k++) {
					if (listCuocThanhTraTheoNam.get(k).getDoiTuongThanhTra().getId().equals(
							listCuocThanhTraTheoNam.get(j).getDoiTuongThanhTra().getId())) {
						if (cuocThanhTraIds.size() > 0) {
							boolean flagJ = false;
							boolean flagK = false;
							for (Long l : cuocThanhTraIds) {
								if (listCuocThanhTraTheoNam.get(j).getId().equals(l)) {
									flagJ = true;
								}
								if (listCuocThanhTraTheoNam.get(k).getId().equals(l)) {
									flagK = true;
								}
							}
							if (!flagJ) {
								cuocThanhTraIds.add(listCuocThanhTraTheoNam.get(j).getId());
							}
							if (!flagK) {
								cuocThanhTraIds.add(listCuocThanhTraTheoNam.get(k).getId());
							}
						} else {
							cuocThanhTraIds.add(listCuocThanhTraTheoNam.get(j).getId());
							cuocThanhTraIds.add(listCuocThanhTraTheoNam.get(k).getId());
						}
					}
				}
			}
		}
		return cuocThanhTraIds;
	}
	
	private List<List<Object>> getCuocThanhTraIdVaDoiTuongIds(int namThanhTra, String tenDoiTuongThanhTra, Long donViId, String soQuyetDinhPheDuyetKHTT) {
		List<List<Object>> cuocThanhTraIdVaDoiTuongIds = new ArrayList<List<Object>>();
		List<CuocThanhTra> listCuocThanhTraTheoNam = (List<CuocThanhTra>) repo.findAll(cuocThanhTraService.predicateFindThanhTraTrung(namThanhTra, tenDoiTuongThanhTra, donViId, soQuyetDinhPheDuyetKHTT));
		List<Object> ids = null;
		
		if (listCuocThanhTraTheoNam != null && listCuocThanhTraTheoNam.size() > 0) {
			for (int j = 0; j < listCuocThanhTraTheoNam.size() - 1; j++) {
				for (int k = j + 1; k < listCuocThanhTraTheoNam.size(); k++) {
					List<DoiTuongThanhTra> listDoiTuongThanhTraOne = new ArrayList<DoiTuongThanhTra>();
					List<DoiTuongThanhTra> listDoiTuongThanhTraTwo = new ArrayList<DoiTuongThanhTra>();
					listDoiTuongThanhTraOne.add(listCuocThanhTraTheoNam.get(j).getDoiTuongThanhTra());
					listDoiTuongThanhTraOne.addAll(listCuocThanhTraTheoNam.get(j).getDoiTuongThanhTras());
					listDoiTuongThanhTraTwo.add(listCuocThanhTraTheoNam.get(k).getDoiTuongThanhTra());
					listDoiTuongThanhTraTwo.addAll(listCuocThanhTraTheoNam.get(k).getDoiTuongThanhTras());
					
					List<DoiTuongThanhTra> trungNhieuDoiTuong = checkTrungNhieuDoiTuong(listDoiTuongThanhTraOne, listDoiTuongThanhTraTwo);
					
					if (trungNhieuDoiTuong.size() > 0) {
//						if (cuocThanhTraIdVaDoiTuongIds.size() > 0) {
//							List<List<Boolean>> flag = new ArrayList<List<Boolean>>();
//							for (DoiTuongThanhTra doiTuong : trungNhieuDoiTuong) {
//								for (List<Object> l : cuocThanhTraIdVaDoiTuongIds) {
//									List<Boolean> tmp = new ArrayList<Boolean>();
//									if (listCuocThanhTraTheoNam.get(j).getId().equals(l.get(0)) && doiTuong.equals(l.get(1))) {
//										tmp.add(true);
//									} else {
//										tmp.add(false);
//									}
//									if (listCuocThanhTraTheoNam.get(k).getId().equals(l.get(0)) && doiTuong.equals(l.get(1))) {
//										tmp.add(true);
//									} else {
//										tmp.add(false);
//									}
//									flag.add(tmp);
//								}
//							}
//							
//							for (int m = 0; m < trungNhieuDoiTuong.size(); m++) {
//								if (!flag.get(m).get(0)) {
//									ids = new ArrayList<Object>();
//									ids.add(listCuocThanhTraTheoNam.get(j).getId());
//									ids.add(trungNhieuDoiTuong.get(m));
//									cuocThanhTraIdVaDoiTuongIds.add(ids);
//									System.out.println("Add lan sau: " + ids.get(0) + " --- " + ((DoiTuongThanhTra) ids.get(1)).getId());
//								}
//								if (!flag.get(m).get(1)) {
//									ids = new ArrayList<Object>();
//									ids.add(listCuocThanhTraTheoNam.get(k).getId());
//									ids.add(trungNhieuDoiTuong.get(m));
//									cuocThanhTraIdVaDoiTuongIds.add(ids);
//									System.out.println("Add lan sau: " + ids.get(0) + " --- " + ((DoiTuongThanhTra) ids.get(1)).getId());
//								}
//							}
//						} else {
//							boolean isThemLanDau = false;
							for (DoiTuongThanhTra doiTuongThanhTra : trungNhieuDoiTuong) {
								ids = new ArrayList<Object>();
								ids.add(listCuocThanhTraTheoNam.get(j).getId());
								ids.add(doiTuongThanhTra);
								cuocThanhTraIdVaDoiTuongIds.add(ids);
								
								ids = new ArrayList<Object>();
								ids.add(listCuocThanhTraTheoNam.get(k).getId());
								ids.add(doiTuongThanhTra);
								cuocThanhTraIdVaDoiTuongIds.add(ids);
								
//								if (cuocThanhTraIdVaDoiTuongIds.size() > 0) {
//									isThemLanDau = true;
//									break;
//								}
							}
							
//							if (isThemLanDau) {
//								List<List<Boolean>> flag = new ArrayList<List<Boolean>>();
//								for (int n = 0; n < trungNhieuDoiTuong.size(); n++) {
//									for (List<Object> l : cuocThanhTraIdVaDoiTuongIds) {
//										List<Boolean> tmp = new ArrayList<Boolean>();
//										if (listCuocThanhTraTheoNam.get(j).getId().equals(l.get(0)) && trungNhieuDoiTuong.get(n).equals(l.get(1))) {
//											tmp.add(true);
//										} else {
//											tmp.add(false);
//										}
//										if (listCuocThanhTraTheoNam.get(k).getId().equals(l.get(0)) && trungNhieuDoiTuong.get(n).equals(l.get(1))) {
//											tmp.add(true);
//										} else {
//											tmp.add(false);
//										}
//										flag.add(tmp);
//									}
//								}
//								
//								for (int m = 0; m < trungNhieuDoiTuong.size(); m++) {
//									if (!flag.get(m).get(0)) {
//										ids = new ArrayList<Object>();
//										ids.add(listCuocThanhTraTheoNam.get(j).getId());
//										ids.add(trungNhieuDoiTuong.get(m));
//										cuocThanhTraIdVaDoiTuongIds.add(ids);
//										System.out.println("Add lan sau: " + ids.get(0) + " --- " + ((DoiTuongThanhTra) ids.get(1)).getId());
//									}
//									if (!flag.get(m).get(1)) {
//										ids = new ArrayList<Object>();
//										ids.add(listCuocThanhTraTheoNam.get(k).getId());
//										ids.add(trungNhieuDoiTuong.get(m));
//										cuocThanhTraIdVaDoiTuongIds.add(ids);
//										System.out.println("Add lan sau: " + ids.get(0) + " --- " + ((DoiTuongThanhTra) ids.get(1)).getId());
//									}
//								}
//							}
//						}
					}
//					if (listCuocThanhTraTheoNam.get(j).getDoiTuongThanhTra().getId().equals(
//							listCuocThanhTraTheoNam.get(k).getDoiTuongThanhTra().getId())) {
//						if (cuocThanhTraIdVaDoiTuongIds.size() > 0) {
//							boolean flagJ = false;
//							boolean flagK = false;
//							for (List<Object> l : cuocThanhTraIdVaDoiTuongIds) {
//								if (listCuocThanhTraTheoNam.get(j).getId().equals(l.get(0))
//										&& listCuocThanhTraTheoNam.get(j).getDoiTuongThanhTra().equals(l.get(1))) {
//									flagJ = true;
//								}
//								if (listCuocThanhTraTheoNam.get(k).getId().equals(l.get(0))
//										&& listCuocThanhTraTheoNam.get(k).getDoiTuongThanhTra().equals(l.get(1))) {
//									flagK = true;
//								}
//							}
//							if (!flagJ) {
//								ids = new ArrayList<Object>();
//								ids.add(listCuocThanhTraTheoNam.get(j).getId());
//								ids.add(listCuocThanhTraTheoNam.get(j).getDoiTuongThanhTra());
//								cuocThanhTraIdVaDoiTuongIds.add(ids);
//							}
//							if (!flagK) {
//								ids = new ArrayList<Object>();
//								ids.add(listCuocThanhTraTheoNam.get(k).getId());
//								ids.add(listCuocThanhTraTheoNam.get(k).getDoiTuongThanhTra());
//								cuocThanhTraIdVaDoiTuongIds.add(ids);
//							}
//						} else {
//							ids = new ArrayList<Object>();
//							ids.add(listCuocThanhTraTheoNam.get(j).getId());
//							ids.add(listCuocThanhTraTheoNam.get(j).getDoiTuongThanhTra());
//							cuocThanhTraIdVaDoiTuongIds.add(ids);
//							ids = new ArrayList<Object>();
//							ids.add(listCuocThanhTraTheoNam.get(k).getId());
//							ids.add(listCuocThanhTraTheoNam.get(k).getDoiTuongThanhTra());
//							cuocThanhTraIdVaDoiTuongIds.add(ids);
//						}
//					}
				}
			}
		}
		return cuocThanhTraIdVaDoiTuongIds;
	}
	
	private List<DoiTuongThanhTra> checkTrungNhieuDoiTuong(List<DoiTuongThanhTra> listDoiTuongThanhTraOne, List<DoiTuongThanhTra> listDoiTuongThanhTraTwo) {
		List<DoiTuongThanhTra> doiTuongThanhTraIds = new ArrayList<DoiTuongThanhTra>();
		for (int j = 0; j < listDoiTuongThanhTraOne.size(); j++) {
			for (int k = 0; k < listDoiTuongThanhTraTwo.size(); k++) {
				if (listDoiTuongThanhTraOne.get(j).getId().equals(listDoiTuongThanhTraTwo.get(k).getId())) {
					if (doiTuongThanhTraIds.size() > 0) {
						boolean flag = false;
						for (DoiTuongThanhTra dttt : doiTuongThanhTraIds) {
							if (dttt.getId().equals(listDoiTuongThanhTraOne.get(j).getId())) {
								flag = true;
							}
						}
						if (!flag) {
							doiTuongThanhTraIds.add(listDoiTuongThanhTraOne.get(j));
						}
					} else {
						doiTuongThanhTraIds.add(listDoiTuongThanhTraOne.get(j));
					}
				}
			}
		}
		return doiTuongThanhTraIds;
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/cuocThanhTras/thoiHanThanhTra")
	@ApiOperation(value = "Lấy số Ngày của thời hạn thanh tra", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public Object getThoiHanThanhTra(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "ngayCongBoQD", required = true) String ngayCongBoQD,
			@RequestParam(value = "thoiHanThanhTra", required = true) Long thoiHanThanhTra,
			PersistentEntityResourceAssembler eass) {
		
		try {
			LocalDateTime thoiHan = Utils.fixTuNgay(ngayCongBoQD);
			Long soNgayThanhTra = Utils.getLaySoNgayXuLyThanhTra(thoiHan, thoiHanThanhTra);
			if (thoiHanThanhTra != null && thoiHanThanhTra == 0) { 
				thoiHan = thoiHan.plusDays(soNgayThanhTra);
			} else { 
				thoiHan = thoiHan.plusDays(soNgayThanhTra - 1);
			}
			
			if (thoiHan == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			return new ResponseEntity<>(thoiHan, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/cuocThanhTras/validateTrungDoiTuongThanhTra")
	@ApiOperation(value = "Validate trùng đối tượng thanh tra", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object validateTrungDoiTuongThanhTra(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "doiTuongThanhTraId", required = true) Long doiTuongThanhTraId,
			@RequestParam(value = "namThanhTra", required = true) Integer namThanhTra,
			Pageable pageable,PersistentEntityResourceAssembler eass) {
		
		try {
			Page<CoQuanQuanLy> page = null;
			HashSet<Long> donViIds = new HashSet<Long>();
			List<CoQuanQuanLy> coQuanQUanLys = new ArrayList<CoQuanQuanLy>();
			List<CuocThanhTra> cuocThanhTraTrungDoiTuongs = (List<CuocThanhTra>) repo.findAll(cuocThanhTraService.predicateFindAllByDoiTuongThanhTra(namThanhTra, doiTuongThanhTraId));
			if (cuocThanhTraTrungDoiTuongs != null && cuocThanhTraTrungDoiTuongs.size() > 0) {
				for (CuocThanhTra ctt : cuocThanhTraTrungDoiTuongs) {
					donViIds.add(ctt.getDonVi().getId());
				}
				if (donViIds != null && donViIds.size() > 0) {
					List<Long> listDonVi = new ArrayList<Long>(donViIds);
					coQuanQUanLys = (List<CoQuanQuanLy>) coQuanQuanLyRepository.findAll(coQuanQuanLyService.predicateFindAllByListId(listDonVi));
					page = coQuanQuanLyRepository.findAll(coQuanQuanLyService.predicateFindAllByListId(listDonVi), pageable);
				}
			}
			
			if (page == null) {
				page = new PageImpl<CoQuanQuanLy>(coQuanQUanLys, pageable, coQuanQUanLys.size());
			}
			return assemblerCoQuanQuanLy.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
}
