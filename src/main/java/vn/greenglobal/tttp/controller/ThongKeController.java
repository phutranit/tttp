package vn.greenglobal.tttp.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.DonService;
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
	private DonService donService;
	
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
			Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			String vaiTroNguoiDungHienTai = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro")
					.toString();

			pageable = new PageRequest(0, 3, new Sort(new Order(Direction.DESC, "ngayTao")));
			Page<Don> pageData = repo.findAll(donService.predicateFindDSDonMoiNhat(donViXuLyXLD, vaiTroNguoiDungHienTai,
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
			Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			String vaiTroNguoiDungHienTai = profileUtil.getCommonProfile(authorization).getAttribute("loaiVaiTro")
					.toString();

			pageable = new PageRequest(0, 3, new Sort(new Order(Direction.DESC, "ngayTao")));
			Page<Don> pageData = repo.findAll(donService.predicateFindDSDonTreHan(donViXuLyXLD, vaiTroNguoiDungHienTai,
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
		mapDonThongKe.put("tongSoDon", donService.getThongKeTongSoDon(donViXuLyXLD, xuLyRepo, repo, giaiQuyetDonRepo));
		mapDonThongKe.put("tongSoDonChuaGiaiQuyet", donService.getThongKeTongSoDonChuaGiaiQuyet(donViXuLyXLD, xuLyRepo, repo, giaiQuyetDonRepo));
		mapDonThongKe.put("tongSoDonTreHan", donService.getThongKeTongSoDonTreHan(donViXuLyXLD, xuLyRepo, repo, giaiQuyetDonRepo));
		map.put("ten", "ĐƠN TẠI ĐƠN VỊ " +StringUtils.upperCase(donVi != null ? donVi.getDonVi().getTen() : "") +" NĂM " +LocalDateTime.now().getYear());
		map.put("thongKe", mapDonThongKe);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKes/thongKeDonTaiDiaBan")
	@ApiOperation(value = "Thống kê đơn tại địa bàn", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody ResponseEntity<Object> getThongKeDonTaiDiaBan(
			@RequestHeader(value = "Authorization", required = true) String authorization) {
		Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
		CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViXuLyXLD);
		if (donVi.getCha() != null) { 
			donViXuLyXLD = donVi.getCha().getId();
		}
		System.out.println("donViXuLyXLD " +donViXuLyXLD);
		Map<String, Object> map = new HashMap<>();
		Map<String, Object> mapDonThongKe = new HashMap<>();
		mapDonThongKe.put("tongSoDon", donService.getThongKeTongSoDon(donViXuLyXLD, xuLyRepo, repo, giaiQuyetDonRepo));
		mapDonThongKe.put("tongSoDonChuaGiaiQuyet", donService.getThongKeTongSoDonChuaGiaiQuyet(donViXuLyXLD, xuLyRepo, repo, giaiQuyetDonRepo));
		mapDonThongKe.put("tongSoDonTreHan", donService.getThongKeTongSoDonTreHan(donViXuLyXLD, xuLyRepo, repo, giaiQuyetDonRepo));
		map.put("ten", "ĐƠN TẠI ĐỊA BÀN " +StringUtils.upperCase(donVi != null ? donVi.getDonVi().getTen() : "") +" NĂM " +LocalDateTime.now().getYear());
		map.put("thongKe", mapDonThongKe);
		return new ResponseEntity<>(map, HttpStatus.OK);
	}

}
