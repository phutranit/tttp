package vn.greenglobal.tttp.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.dsl.BooleanExpression;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.CoQuanQuanLyService;
import vn.greenglobal.tttp.service.LinhVucDonThuService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.service.ThongKeService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "thongKes", description = "Thống kê danh sách Đơn")
public class ThongKeController extends TttpController<Don> {

	@Autowired
	private DonRepository repo;

	@Autowired
	private XuLyDonRepository xuLyRepo;

	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepo;
	
	@Autowired
	private GiaiQuyetDonRepository giaiQuyetDonRepo;
	
	@Autowired
	private ThamSoRepository repoThamSo;
	
	@Autowired
	private ThamSoService thamSoService;
	
	@Autowired
	private CoQuanQuanLyService coQuanQuanLyService;
	
	@Autowired
	private LinhVucDonThuService linhVucDonThuService;
	
	@Autowired
	private ThongKeService thongKeService;
	
	public ThongKeController(BaseRepository<Don, Long> repo) {
		super(repo);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/thongKes/thongKeDonMoiNhat")
	@ApiOperation(value = "Lấy danh sách đơn mới nhất của đơn vị", position = 4, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy danh sách đơn mới nhất của đơn vị thành công", response = Don.class),
			@ApiResponse(code = 203, message = "Không có quyền lấy dữ liệu"),
			@ApiResponse(code = 204, message = "Không có dữ liệu"),
			@ApiResponse(code = 400, message = "Param không đúng kiểu"), })
	public @ResponseBody Object getDanhSachDonMoiNhatTheoDonVi(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, PersistentEntityResourceAssembler eass) {

		try {
			int year = LocalDateTime.now().getYear();
			Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			String vaiTroNguoiDungHienTai = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro")
					.toString();

			pageable = new PageRequest(0, 3, new Sort(new Order(Direction.DESC, "ngayTao")));
			Page<Don> pageData = repo.findAll(thongKeService.predicateFindDSDonMoiNhat(donViXuLyXLD, vaiTroNguoiDungHienTai, year,
					xuLyRepo, repo, giaiQuyetDonRepo), pageable);
			return assembler.toResource(pageData, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/thongKes/thongKeDonTreHan")
	@ApiOperation(value = "Lấy danh sách đơn trễ hạn của đơn vị", position = 5, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy danh sách đơn trễ hạn của đơn vị thành công", response = Don.class),
			@ApiResponse(code = 203, message = "Không có quyền lấy dữ liệu"),
			@ApiResponse(code = 204, message = "Không có dữ liệu"),
			@ApiResponse(code = 400, message = "Param không đúng kiểu"), })
	public @ResponseBody Object getDanhSachDonTreHanTheoDonVi(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, PersistentEntityResourceAssembler eass) {

		try {
			int year = LocalDateTime.now().getYear();
			int month = LocalDateTime.now().getMonthValue();
			Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			String vaiTroNguoiDungHienTai = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro")
					.toString();

			pageable = new PageRequest(0, 3, new Sort(new Order(Direction.DESC, "ngayTao")));
			Page<Don> pageData = repo.findAll(thongKeService.predicateFindDSDonTreHan(donViXuLyXLD, vaiTroNguoiDungHienTai, month,
					xuLyRepo, repo, giaiQuyetDonRepo), pageable);
			return assembler.toResource(pageData, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKes/thongKeDonTaiDonVi")
	@ApiOperation(value = "Thống kê đơn tại đơn vị", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getThongKeDonTaiDonVi(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
		CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViXuLyXLD);
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> mapDonThongKe = new HashMap<>();
		int year = LocalDateTime.now().getYear();
		mapDonThongKe.put("tongSoDon", thongKeService.getThongKeTongSoDon(donViXuLyXLD, year, xuLyRepo, repo, giaiQuyetDonRepo));
		//mapDonThongKe.put("tongSoDonChuaGiaiQuyet", donService.getThongKeTongSoDonChuaGiaiQuyet(donViXuLyXLD, year, xuLyRepo, repo, giaiQuyetDonRepo));
		mapDonThongKe.put("tongSoDonTreHanCuaXuLyDon", thongKeService.getThongKeTongSoDonTreHanXLD(donViXuLyXLD, year, xuLyRepo, repo));
		mapDonThongKe.put("tongSoDonTreHanCuaGiaiQuyetDon", thongKeService.getThongKeTongSoDonTreHanGQD(donViXuLyXLD, year, repo, giaiQuyetDonRepo));
		map.put("ten", "ĐƠN TẠI ĐƠN VỊ " +StringUtils.upperCase(donVi != null ? donVi.getDonVi().getTen() : "") +" NĂM " +year);
		map.put("thongKe", mapDonThongKe);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKes/thongKeDonTaiDiaBan")
	@ApiOperation(value = "Thống kê đơn tại địa bàn", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getThongKeDonTaiDiaBan(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
		Long coQuanQuanLyId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
		CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViXuLyXLD);
		CoQuanQuanLy coQuanQuanLy = coQuanQuanLyRepo.findOne(coQuanQuanLyId);
		Long capCoQuanQuanLyId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("capCoQuanQuanLyId").toString());
		ThamSo thamSoCCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
		ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
		ThamSo thamSoCCQQLUBNDSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
		ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
		ThamSo thamSoCCQQLUBNDPhuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
		ThamSo thamSoCCQQLChiCuc = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_CHI_CUC"));

		List<CoQuanQuanLy> coQuans = new ArrayList<CoQuanQuanLy>();
		int year = LocalDateTime.now().getYear();
		if (donViXuLyXLD == Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString())
				|| donViXuLyXLD == Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString())) {
			// Danh sach don vi thuoc UBNDTP Da Nang
			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
			capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString()));
			List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonVi(
					Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds,
					"CQQL_UBNDTP_DA_NANG"));
			coQuans.addAll(list);
			if (coQuans.contains(coQuanQuanLy)) { 
				coQuans.remove(coQuanQuanLy);
			}
		} else if (capCoQuanQuanLyId == Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString())) {
			// Danh sach don vi thuoc Quan Huyen
			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString()));
			coQuans.addAll((List<CoQuanQuanLy>)coQuanQuanLyRepo.findAll(coQuanQuanLyService
					.predicateFindDonViVaConCuaDonVi(donViXuLyXLD, capCoQuanQuanLyIds,
					"CCQQL_UBND_QUAN_HUYEN")));
		} else if (capCoQuanQuanLyId == Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString())) {
			// Danh sach don vi thuoc So Ban Nganh
			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLChiCuc.getGiaTri().toString()));
			coQuans.addAll((List<CoQuanQuanLy>)coQuanQuanLyRepo.findAll(coQuanQuanLyService
					.predicateFindDonViVaConCuaDonVi(donViXuLyXLD, capCoQuanQuanLyIds, "CCQQL_SO_BAN_NGANH")));
		} else if (capCoQuanQuanLyId == Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString())) {
			// Danh sach don vi thuoc Phuong Xa
			coQuans.addAll((List<CoQuanQuanLy>)coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindOne(donViXuLyXLD)));
		}
		
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> mapDonThongKe = new HashMap<>();
		mapDonThongKe.put("tongSoDon", thongKeService.getThongKeTongSoDonThuocNhieuCoQuan(coQuans, year, xuLyRepo, repo, giaiQuyetDonRepo));
		//mapDonThongKe.put("tongSoDonChuaGiaiQuyet", donService.getThongKeTongSoDonChuaGiaiQuyetCuaNhieuCoQuan(coQuans, year, xuLyRepo, repo, giaiQuyetDonRepo));
		mapDonThongKe.put("tongSoDonTreHanCuaXuLyDon", thongKeService.getThongKeTongSoDonTreHanCuaNhieuCoQuanXLD(coQuans, year, xuLyRepo, repo));
		mapDonThongKe.put("tongSoDonTreHanCuaGiaiQuyetDon", thongKeService.getThongKeTongSoDonTreHanCuaNhieuCoQuanGQD(coQuans, year, repo, giaiQuyetDonRepo));
		map.put("ten", "ĐƠN TẠI ĐỊA BÀN " +StringUtils.upperCase(donVi != null ? donVi.getDonVi().getTen() : "") +" NĂM " +year);
		map.put("thongKe", mapDonThongKe);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKes/thongKeBieuDoDonTheoPhanLoai")
	@ApiOperation(value = "Thống kê biểu đồ đơn theo phân loại", position = 8, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getThongKeBieuDoDonTheoPhanLoai(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> mapDonThongKe = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		
		Long tongSoDon = 0L;
		int year = LocalDateTime.now().getYear();
		BooleanExpression predAll = (BooleanExpression) thongKeService.predicateFindDanhSachDonsTheoDonVi(donViXuLyXLD, year, xuLyRepo, repo, giaiQuyetDonRepo);
		tongSoDon = Long.valueOf(((List<Don>) repo.findAll(predAll)).size());
		
		for (LoaiDonEnum loaiDon : LoaiDonEnum.values()) {
			BooleanExpression predDon = predAll;
			int phanTram = thongKeService.getPhanTramTongSoDonTheoPhanLoai(predDon, loaiDon, tongSoDon, repo);
			mapDonThongKe.put("tenPhanLoaiDon", loaiDon.getText());
			mapDonThongKe.put("phanTram", phanTram);
			list.add(mapDonThongKe);
			mapDonThongKe = new HashMap<>();
		}
		map.put("ten", "THỐNG KÊ ĐƠN THEO PHÂN LOẠI ĐƠN " +" NĂM " +year);
		map.put("phanLoaiDons", list);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKes/thongKeBieuDoDonTheoLinhVuc")
	@ApiOperation(value = "Thống kê biểu đồ đơn theo lĩnh vực", position = 9, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getThongKeBieuDoDonTheoLinhVuc(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> mapDonThongKe = new HashMap<>();
		List<Map<String, Object>> list = new ArrayList<>();
		
		List<LinhVucDonThu> linhVucs = new ArrayList<LinhVucDonThu>();
		List<Long> ids = Arrays.asList(1L, 11L, 15L, 17L, 19L, 23L, 24L, 26L, 39L, 50L, 52L);		
		linhVucs.addAll(linhVucDonThuService.linhVucDonThusTheoId(ids));
		
		Long tongSoDon = 0L;
		int year = LocalDateTime.now().getYear();
		BooleanExpression predAll = (BooleanExpression) thongKeService.predicateFindDanhSachDonsTheoDonVi(donViXuLyXLD, year, xuLyRepo, repo, giaiQuyetDonRepo);
		tongSoDon = Long.valueOf(((List<Don>) repo.findAll(predAll)).size());
		
		for (LinhVucDonThu linhVuc : linhVucs) {
			BooleanExpression predDon = predAll;
			int phanTram = thongKeService.getPhanTramTongSoDonTheoLinhVuc(predDon, linhVuc.getId(), tongSoDon, repo);
			mapDonThongKe.put("tenLinhVucDon", linhVuc.getTen());
			mapDonThongKe.put("phanTram", phanTram);
			list.add(mapDonThongKe);
			mapDonThongKe = new HashMap<>();
		}
		map.put("ten", "THỐNG KÊ ĐƠN THEO LĨNH VỰC " +" NĂM " +year);
		map.put("linhVucs", list);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKes/thongKeBieuDoDonCuaNam")
	@ApiOperation(value = "Thống kê biểu đồ đơn của năm", position = 10, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getThongKeBieuDoDonCuaNam(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> mapDonThongKe = new HashMap<>();
		List<Map<String, Object>> listLoaiDon = new ArrayList<>();
		
		int year = LocalDateTime.now().getYear();
		BooleanExpression predAll = (BooleanExpression) thongKeService.predicateFindDanhSachDonsTheoDonVi(donViXuLyXLD, year, xuLyRepo, repo, giaiQuyetDonRepo);
		
		for (LoaiDonEnum loaiDon : LoaiDonEnum.values()) {
			BooleanExpression predDon = predAll;
			Long tongSo = thongKeService.getTongSoDonTheoPhanLoai(predDon, loaiDon, repo);
			mapDonThongKe.put("tongSoDon", tongSo);
			mapDonThongKe.put("loaiDon", loaiDon.getText());
			listLoaiDon.add(mapDonThongKe);
			mapDonThongKe = new HashMap<>();
		}
		map.put("ten", "THỐNG KÊ SỐ LƯỢNG ĐƠN TRONG " +" NĂM " +year);
		map.put("tongSoDon", thongKeService.getThongKeTongSoDon(donViXuLyXLD, year, xuLyRepo, repo, giaiQuyetDonRepo));
		map.put("donTreHan", thongKeService.getThongKeTongSoDonTreHan(donViXuLyXLD, year, xuLyRepo, repo, giaiQuyetDonRepo));
		map.put("phanLoaiDons", listLoaiDon);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
}
