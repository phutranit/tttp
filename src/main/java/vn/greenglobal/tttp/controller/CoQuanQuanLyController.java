package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import vn.greenglobal.tttp.model.CapCoQuanQuanLy;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.repository.TransitionRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.CoQuanQuanLyService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "coQuanQuanLys", description = "Cơ Quan Quản Lý")
public class CoQuanQuanLyController extends TttpController<CoQuanQuanLy> {

	@Autowired
	private CoQuanQuanLyRepository repo;

	@Autowired
	private CoQuanQuanLyService coQuanQuanLyService;
	
	@Autowired
	private CongChucRepository congChucRepository;

	@Autowired
	private DonRepository donRepository;

	@Autowired
	private SoTiepCongDanRepository soTiepCongDanRepository;

	@Autowired
	private XuLyDonRepository xuLyDonRepository;
	
	@Autowired
	private TransitionRepository repoTransition;

	@Autowired
	private ThamSoRepository repoThamSo;

	@Autowired
	private ThamSoService thamSoService;

	public CoQuanQuanLyController(BaseRepository<CoQuanQuanLy, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/donViThongKes")
	@ApiOperation(value = "Lấy danh sách đơn vị thống kê", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getDonViThongKes(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));
			List<CoQuanQuanLy> list = new ArrayList<>();

			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			ThamSo thamSoUBNDTPDN = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoSBN = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoUBNDQH = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			
			ThamSo thamSoCQQLUBND = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoDVHCTPDaNang = repoThamSo.findOne(thamSoService.predicateFindTen("DVHC_TP_DA_NANG"));
			ThamSo thamSoCQQLThanhTra = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLPhongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo thamSoLCQQLBoCongAn = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));
			Long idCapPhongBan = Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString());

			if (donViId.equals(Long.valueOf(thamSoCQQLUBND.getGiaTri().toString()))
					|| donViId.equals(Long.valueOf(thamSoCQQLThanhTra.getGiaTri().toString()))) {
				//list.add(repo.findOne((Long.valueOf(thamSoCQQLUBND.getGiaTri().toString()))));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDTPDN.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoSBN.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoUBNDQH.getGiaTri().toString()));
				List<CoQuanQuanLy> listDonViCon = (List<CoQuanQuanLy>) repo.findAll(coQuanQuanLyService
						.predicateFindAllNotPhongBanNotCongAn(null, idCapPhongBan,
								Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), 
								Long.valueOf(thamSoDVHCTPDaNang.getGiaTri().toString())));
				list.addAll(listDonViCon);
			} else {
				list.add(repo.findOne(donViId));
				capCoQuanQuanLyIds.add(idCapPhongBan);
				List<CoQuanQuanLy> listDonViCon = (List<CoQuanQuanLy>) repo
						.findAll(coQuanQuanLyService.predicateFindAllNotPhongBanNotCongAn(donViId, idCapPhongBan, 
								Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), null));
				list.addAll(listDonViCon);
			}

			Page<CoQuanQuanLy> page = new PageImpl<CoQuanQuanLy>(list, pageable, list.size());
			System.out.println("page size1 " +page.getNumberOfElements());
			page.forEach(p -> {
				System.out.println("p " +p.getTen());
			});
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys")
	@ApiOperation(value = "Lấy danh sách Cơ Quan Quản Lý", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "cha", required = false) Long cha,
			@RequestParam(value = "capCoQuanQuanLy", required = false) Long capCoQuanQuanLy,
			@RequestParam(value = "donViHanhChinh", required = false) Long donViHanhChinh,
			@RequestParam(value = "listCoQuanQuanLys", required = false) List<CoQuanQuanLy> listCoQuanQuanLys,
			@RequestParam(value = "listCapCoQuanQuanLys", required = false) List<CapCoQuanQuanLy> listCapCoQuanQuanLys,
			PersistentEntityResourceAssembler eass) {
		
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.COQUANQUANLY_LIETKE) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.COQUANQUANLY_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			Page<CoQuanQuanLy> page = repo.findAll(coQuanQuanLyService.predicateFindAll(tuKhoa, cha, capCoQuanQuanLy, donViHanhChinh, listCoQuanQuanLys, listCapCoQuanQuanLys), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/coQuanQuanLyTimKiems")
	@ApiOperation(value = "Lấy danh sách tất cả Cơ Quan Quản Lý", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListCoQuanQuanLys(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, PersistentEntityResourceAssembler eass) {
		
		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.COQUANQUANLY_LIETKE) == null
					&& Utils.quyenValidate(profileUtil, authorization, QuyenEnum.COQUANQUANLY_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));
			ThamSo thamSoDVHCTPDaNang = repoThamSo.findOne(thamSoService.predicateFindTen("DVHC_TP_DA_NANG"));
			Page<CoQuanQuanLy> page = repo.findAll(coQuanQuanLyService.predicateFindAll(Long.valueOf(thamSoDVHCTPDaNang.getGiaTri().toString())), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/listDonViTimKiems")
	@ApiOperation(value = "Lấy danh sách Cơ quan quản lý bằng cách tìm kiếm tên", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListDonViByName(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa, PersistentEntityResourceAssembler eass) {
		
		try {
			Page<CoQuanQuanLy> page = repo.findAll(coQuanQuanLyService.predicateFindAllByName(tuKhoa), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/donVis")
	@ApiOperation(value = "Lấy danh sách đơn vị", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getDonVis(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, PersistentEntityResourceAssembler eass) {

		try {
			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Page<CoQuanQuanLy> page = null;
			Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Long capCoQuanQuanLyCuaDonViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("capCoQuanQuanLyCuaDonViId").toString());
			
			ThamSo thamSoCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoCCQQLUBNDPhuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLPhongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo thamSoLCQQLBoCongAn = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));

			if (donViId != null && donViId > 0 && capCoQuanQuanLyCuaDonViId != null && capCoQuanQuanLyCuaDonViId > 0) {
				if (donViId == Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString())
						|| donViId == Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString())) {
					// Danh sach don vi thuoc UBNDTP Da Nang
					page = repo.findAll(coQuanQuanLyService.predicateFindAllDonViNotPhongBanNotCongAn(
								Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
								Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), null), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLSoBanNganh.getGiaTri().toString())) {
					// Danh sach don vi thuoc So Ban Nganh
					page = repo.findAll(coQuanQuanLyService.predicateFindChinhDonViVaConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString())), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString())) {
					// Danh sach don vi thuoc Quan Huyen
					page = repo.findAll(coQuanQuanLyService.predicateFindChinhDonViVaConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString())), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString())) {
					// Danh sach don vi thuoc Phuong Xa
					page = repo.findAll(coQuanQuanLyService.predicateFindChinhDonViVaConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString())), pageable);
				} else {
					page = repo.findAll(coQuanQuanLyService.predicateFindChinhDonViVaConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString())), pageable);
				}
			}

			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/donViTrucThuocs")
	@ApiOperation(value = "Lấy danh sách đơn vị trực thuộc", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getDonViTrucThuocs(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "cha", required = false) Long cha,
			Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			ThamSo thamSoCCQQLPhongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			Page<CoQuanQuanLy> page = repo.findAll(coQuanQuanLyService.predicateFindPhongBanThuocDonVi(cha,
					Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString())), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/donViTheoDois")
	@ApiOperation(value = "Lấy danh sách đơn vị theo dõi", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getDonViTheoDois(@RequestHeader(value = "Authorization", required = true) String authorization, 
			Pageable pageable, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			ThamSo thamSoCCQQLPhongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo thamSoLCQQLBoCongAn = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));
			ThamSo thamSoDVHCTPDaNang = repoThamSo.findOne(thamSoService.predicateFindTen("DVHC_TP_DA_NANG"));
			Page<CoQuanQuanLy> page = repo.findAll(coQuanQuanLyService.predicateFindAllDonViNotPhongBanNotCongAn(
					Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
					Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()),
					Long.valueOf(thamSoDVHCTPDaNang.getGiaTri().toString())), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/donViChuTris")
	@ApiOperation(value = "Lấy danh sách đơn vị chủ trì", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getDonViChuTris(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			Page<CoQuanQuanLy> page = null;
			Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Long capCoQuanQuanLyCuaDonViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("capCoQuanQuanLyCuaDonViId").toString());
			ThamSo thamSoCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoCCQQLUBNDPhuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLPhongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo thamSoLCQQLBoCongAn = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));

			if (donViId != null && donViId > 0 && capCoQuanQuanLyCuaDonViId != null && capCoQuanQuanLyCuaDonViId > 0) {
				if (donViId == Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString())
						|| donViId == Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString())) {
					// Danh sach don vi thuoc UBNDTP Da Nang
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), false), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLSoBanNganh.getGiaTri().toString())) {
					// Danh sach don vi thuoc So Ban Nganh
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), false), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString())) {
					// Danh sach don vi thuoc Quan Huyen
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), false), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString())) {
					// Danh sach don vi thuoc Phuong Xa
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), false), pageable);
				} else {
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), false), pageable);
				}
			}

			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/donViNhanTTXMs")
	@ApiOperation(value = "Lấy danh sách đơn vị chủ trì", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getDonViNhanTTXM(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			Page<CoQuanQuanLy> page = null;
			Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Long capCoQuanQuanLyCuaDonViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("capCoQuanQuanLyCuaDonViId").toString());
			ThamSo thamSoCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoCCQQLUBNDPhuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLPhongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo thamSoLCQQLBoCongAn = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));

			if (donViId != null && donViId > 0 && capCoQuanQuanLyCuaDonViId != null && capCoQuanQuanLyCuaDonViId > 0) {
				if (donViId == Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString())
						|| donViId == Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString())) {
					// Danh sach don vi thuoc UBNDTP Da Nang
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), true), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLSoBanNganh.getGiaTri().toString())) {
					// Danh sach don vi thuoc So Ban Nganh
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), true), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString())) {
					// Danh sach don vi thuoc Quan Huyen
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), true), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString())) {
					// Danh sach don vi thuoc Phuong Xa
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), true), pageable);
				} else {
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), true), pageable);
				}
			}

			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/donViChuTris/cuocThanhTra")
	@ApiOperation(value = "Lấy danh sách đơn vị chủ trì cuộc thanh tra", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getDonViChuTriCuocThanhTras(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Page<CoQuanQuanLy> page = null;
			Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Long capCoQuanQuanLyCuaDonViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("capCoQuanQuanLyCuaDonViId").toString());
			
			ThamSo thamSoCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoCCQQLUBNDPhuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLPhongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo thamSoCCQQLChiCuc = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));
			ThamSo thamSoLCQQLBoCongAn = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));

			if (donViId != null && donViId > 0 && capCoQuanQuanLyCuaDonViId != null && capCoQuanQuanLyCuaDonViId > 0) {
				if (donViId == Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString())
						|| donViId == Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString())) {
					// Danh sach don vi thuoc UBNDTP Da Nang
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLChiCuc.getGiaTri().toString()));
					page = repo.findAll(coQuanQuanLyService.predicateFindAllDonViNotPhongBanNotCongAnNotPhuongXaNotChiCuc(
							capCoQuanQuanLyIds, Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString())), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLSoBanNganh.getGiaTri().toString())) {
					// Danh sach don vi thuoc So Ban Nganh
					page = repo.findAll(coQuanQuanLyService.predicateFindChinhDonViVaConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString())), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString())) {
					// Danh sach don vi thuoc Quan Huyen
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()));
					page = repo.findAll(coQuanQuanLyService.predicateFindChinhDonViVaConCuaDonViVaNotPhongBanNotCongAnNotPhuongXa(donViId,
							capCoQuanQuanLyIds, Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString())), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString())) {
					// Danh sach don vi thuoc Phuong Xa
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()));
					page = repo.findAll(coQuanQuanLyService.predicateFindChinhDonViVaConCuaDonViVaNotPhongBanNotCongAnNotPhuongXa(donViId,
							capCoQuanQuanLyIds, Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString())), pageable);
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()));
					page = repo.findAll(coQuanQuanLyService.predicateFindChinhDonViVaConCuaDonViVaNotPhongBanNotCongAnNotPhuongXa(donViId,
							capCoQuanQuanLyIds, Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString())), pageable);
				}
			}

			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/donViBanHanh/cuocThanhTra")
	@ApiOperation(value = "Lấy danh sách đơn vị ban hành cuộc thanh tra", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getDonViBanHanhCuocThanhTras(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Page<CoQuanQuanLy> page = null;
			Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Long capCoQuanQuanLyCuaDonViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("capCoQuanQuanLyCuaDonViId").toString());
			
			ThamSo thamSoCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoCCQQLUBNDPhuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLPhongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo thamSoCCQQLChiCuc = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));
			ThamSo thamSoLCQQLBoCongAn = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));

			if (donViId != null && donViId > 0 && capCoQuanQuanLyCuaDonViId != null && capCoQuanQuanLyCuaDonViId > 0) {
				if (donViId == Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString())
						|| donViId == Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString())) {
					// Danh sach don vi thuoc UBNDTP Da Nang
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLChiCuc.getGiaTri().toString()));
					page = repo.findAll(coQuanQuanLyService.predicateFindAllDonViNotPhongBanNotCongAnNotPhuongXaNotChiCuc(
							capCoQuanQuanLyIds, Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString())), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLSoBanNganh.getGiaTri().toString())) {
					// Danh sach don vi thuoc So Ban Nganh
					page = repo.findAll(coQuanQuanLyService.predicateFindChinhDonViVaConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString())), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString())) {
					// Danh sach don vi thuoc Quan Huyen
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()));
					page = repo.findAll(coQuanQuanLyService.predicateFindChinhDonViVaConCuaDonViVaNotPhongBanNotCongAnNotPhuongXa(donViId,
							capCoQuanQuanLyIds, Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString())), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString())) {
					// Danh sach don vi thuoc Phuong Xa
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()));
					page = repo.findAll(coQuanQuanLyService.predicateFindChinhDonViVaConCuaDonViVaNotPhongBanNotCongAnNotPhuongXa(donViId,
							capCoQuanQuanLyIds, Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString())), pageable);
				} else {
					List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString()));
					capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()));
					page = repo.findAll(coQuanQuanLyService.predicateFindChinhDonViVaConCuaDonViVaNotPhongBanNotCongAnNotPhuongXa(donViId,
							capCoQuanQuanLyIds, Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString())), pageable);
				}
			}

			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/donViPhoiHops")
	@ApiOperation(value = "Lấy danh sách đơn vị phối hợp", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getDonViPhoiHops(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "coQuanQuanLy", required = false) Long coQuanQuanLy, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			Page<CoQuanQuanLy> page = null;
			Long donViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Long capCoQuanQuanLyCuaDonViId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("capCoQuanQuanLyCuaDonViId").toString());
			ThamSo thamSoCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoCCQQLUBNDPhuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLPhongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo thamSoLCQQLBoCongAn = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));

			if (donViId != null && donViId > 0 && capCoQuanQuanLyCuaDonViId != null && capCoQuanQuanLyCuaDonViId > 0) {
				if (donViId == Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString())
						|| donViId == Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString())) {
					// Danh sach don vi thuoc UBNDTP Da Nang
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), false), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLSoBanNganh.getGiaTri().toString())) {
					// Danh sach don vi thuoc So Ban Nganh
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), false), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString())) {
					// Danh sach don vi thuoc Quan Huyen
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), false), pageable);
				} else if (capCoQuanQuanLyCuaDonViId == Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString())) {
					// Danh sach don vi thuoc Phuong Xa
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), false), pageable);
				} else {
					page = repo.findAll(coQuanQuanLyService.predicateFindConCuaDonViVaNotPhongBanNotCongAn(donViId,
							Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
							Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()), false), pageable);
				}
			}

			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/coQuanTiepNhans")
	@ApiOperation(value = "Lấy danh sách đơn vị chủ trì", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getCoQuanTiepNhan(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {
		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			ThamSo thamSoCCQQLPhongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo thamSoLCQQLBoCongAn = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));
			ThamSo thamSoDVHCTPDaNang = repoThamSo.findOne(thamSoService.predicateFindTen("DVHC_TP_DA_NANG"));
			List<CoQuanQuanLy> coQuanQuanLys = new ArrayList<CoQuanQuanLy>();
			coQuanQuanLys.addAll((List<CoQuanQuanLy>) repo.findAll(coQuanQuanLyService.predicateFindAllDonViNotPhongBanNotCongAn(
					Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
					Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()),
					Long.valueOf(thamSoDVHCTPDaNang.getGiaTri().toString()))));
			
			//Fix chuyenDon - cap nhat 17/11
			//CoQuanQuanLy donViKhac = new CoQuanQuanLy();
			//donViKhac.setId(0L);
			//donViKhac.setTen("Khác");
			//coQuanQuanLys.add(donViKhac);
			int start = pageable.getOffset();
			int end = (start + pageable.getPageSize()) > coQuanQuanLys.size() ? coQuanQuanLys.size() : (start + pageable.getPageSize());
			
			Page<CoQuanQuanLy> page = new PageImpl<CoQuanQuanLy>(coQuanQuanLys.subList(start, end), pageable, coQuanQuanLys.size());
			
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/coQuanDaGiaiQuyets")
	@ApiOperation(value = "Lấy danh sách Cơ Quan đã giải quyết", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListCoQuanDaGiaiQuyet(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "capCoQuanQuanLy", required = false) Long capCoQuanQuanLy,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			Page<CoQuanQuanLy> page = repo.findAll(coQuanQuanLyService.predicateFindAll("", null, capCoQuanQuanLy, null, null, null),
					pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/donViKhongQuyTrinhs")
	@ApiOperation(value = "Lấy danh sách Đơn vị không quy trình", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListDonViKhongQuyTrinh(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));
			Page<CoQuanQuanLy> page = repo.findAll(coQuanQuanLyService.getListDonViKhongQuyTrinh(repoTransition),
					pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/noiCapCMNDs")
	@ApiOperation(value = "Lấy danh sách Nơi Cấp Chứng Minh Nhân Dân", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListDonViHanhChinhTheoCapTinhThanhPho(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));
			Page<CoQuanQuanLy> page = null;
			ThamSo thamSoOne = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoTwo = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));
			ThamSo thamSoThree = repoThamSo.findOne(thamSoService.predicateFindTen("CDVHC_TINH"));
			ThamSo thamSoFour = repoThamSo.findOne(thamSoService.predicateFindTen("CDVHC_THANH_PHO_TRUC_THUOC_TW"));
			ThamSo thamSoFive = repoThamSo.findOne(thamSoService.predicateFindTen("CDVHC_THANH_PHO-TRUC_THUOC_TINH"));
			List<Long> donViHanhChinhList = new ArrayList<Long>();
			donViHanhChinhList.add(Long.valueOf(thamSoThree.getGiaTri().toString()));
			donViHanhChinhList.add(Long.valueOf(thamSoFour.getGiaTri().toString()));
			donViHanhChinhList.add(Long.valueOf(thamSoFive.getGiaTri().toString()));
			
			if (thamSoOne != null && thamSoTwo != null) {
				page = repo.findAll(coQuanQuanLyService.predicateFindNoiCapCMND(tuKhoa, Long.valueOf(thamSoOne.getGiaTri().toString()),
								Long.valueOf(thamSoTwo.getGiaTri().toString()), donViHanhChinhList), pageable);
			}

			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/coQuanDieuTras")
	@ApiOperation(value = "Lấy danh sách Cơ quan điều tra", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListCoQuanDieuTra(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Page<CoQuanQuanLy> page = null;
			ThamSo boCongAn = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));
			ThamSo dvhcDaNang = repoThamSo.findOne(thamSoService.predicateFindTen("DVHC_TP_DA_NANG"));
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (boCongAn != null && dvhcDaNang != null) {
				CoQuanQuanLy caDaNang = repo.findOne(coQuanQuanLyService.predicateFindByLoaiCQQLVaDVHC(Long.valueOf(boCongAn.getGiaTri().toString()),
						Long.valueOf(dvhcDaNang.getGiaTri().toString())));
				list.add(caDaNang);
				if (caDaNang != null) {
					list.addAll((List<CoQuanQuanLy>) repo.findAll(coQuanQuanLyService.predicateFindByCha(caDaNang.getId())));
				}
			}
			page = new PageImpl<CoQuanQuanLy>(list, pageable, list.size());
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/coQuanDieuTras/cuocThanhTra")
	@ApiOperation(value = "Lấy danh sách Cơ quan điều tra", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getListCoQuanDieuTraThanhTra(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			Page<CoQuanQuanLy> page = null;
			ThamSo boCongAn = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));
			ThamSo dvhcDaNang = repoThamSo.findOne(thamSoService.predicateFindTen("DVHC_TP_DA_NANG"));
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			if (boCongAn != null && dvhcDaNang != null) {
				CoQuanQuanLy caDaNang = repo.findOne(coQuanQuanLyService.predicateFindByLoaiCQQLVaDVHC(Long.valueOf(boCongAn.getGiaTri().toString()),
						Long.valueOf(dvhcDaNang.getGiaTri().toString())));
				list.add(caDaNang);
				if (caDaNang != null) {
					list.addAll((List<CoQuanQuanLy>) repo.findAll(coQuanQuanLyService.predicateFindByCha(caDaNang.getId())));
				}
			}
			page = new PageImpl<CoQuanQuanLy>(list, pageable, list.size());
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/danhSachCoQuanCungThamGiaTiepDanComboBox")
	@ApiOperation(value = "Lấy danh sách đơn cùng tham gia tiếp dân", position = 7, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getDanhSachCoQuanThamGiaTiepDans(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {
		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			ThamSo thamSoCCQQLPhongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo thamSoLCQQLBoCongAn = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));
			ThamSo thamSoDVHCTPDaNang = repoThamSo.findOne(thamSoService.predicateFindTen("DVHC_TP_DA_NANG"));
			Page<CoQuanQuanLy> page = repo.findAll(coQuanQuanLyService.predicateFindAllDonViNotPhongBanNotCongAn(
					Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()),
					Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()),
					Long.valueOf(thamSoDVHCTPDaNang.getGiaTri().toString())), pageable);
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/coQuanQuanLys")
	@ApiOperation(value = "Thêm mới Cơ Quan Quản Lý", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Cơ Quan Quản Lý thành công", response = CoQuanQuanLy.class),
			@ApiResponse(code = 201, message = "Thêm mới Cơ Quan Quản Lý thành công", response = CoQuanQuanLy.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody CoQuanQuanLy coQuanQuanLy, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.COQUANQUANLY_THEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			if (StringUtils.isNotBlank(coQuanQuanLy.getTen()) && coQuanQuanLyService.checkExistsData(repo, coQuanQuanLy)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
						ApiErrorEnum.DATA_EXISTS.getText(), ApiErrorEnum.TEN_EXISTS.getText());
			}

			if (coQuanQuanLy.getCapCoQuanQuanLy() != null) {
				ThamSo thamSo = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
				if (thamSo != null && thamSo.getGiaTri().toString().equals(coQuanQuanLy.getCapCoQuanQuanLy().getId().toString())) {
					if (coQuanQuanLy.getCha() != null) {
						coQuanQuanLy.setDonVi(coQuanQuanLy.getCha());
					} else {
						coQuanQuanLy.setDonVi(coQuanQuanLy);
					}
				} else {
					coQuanQuanLy.setDonVi(coQuanQuanLy);
				}
			}

			coQuanQuanLy.setTenSearch(Utils.unAccent(coQuanQuanLy.getTen().trim()));
			coQuanQuanLy.setMoTaSearch(Utils.unAccent(coQuanQuanLy.getMoTa().trim()));
			return coQuanQuanLyService.doSave(coQuanQuanLy,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.CREATED);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/{id}")
	@ApiOperation(value = "Lấy Cơ Quan Quản Lý theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Cơ Quan Quản Lý thành công", response = CoQuanQuanLy.class) })
	public ResponseEntity<Object> getById(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long id, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.COQUANQUANLY_XEM) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			CoQuanQuanLy coQuanQuanLy = repo.findOne(coQuanQuanLyService.predicateFindOne(id));
			if (coQuanQuanLy == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			return new ResponseEntity<>(eass.toFullResource(coQuanQuanLy), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/coQuanQuanLys/{id}")
	@ApiOperation(value = "Cập nhật Cơ Quan Quản Lý", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Cơ Quan Quản Lý thành công", response = CoQuanQuanLy.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody CoQuanQuanLy coQuanQuanLy, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.COQUANQUANLY_SUA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			coQuanQuanLy.setId(id);
			if (StringUtils.isNotBlank(coQuanQuanLy.getTen()) && coQuanQuanLyService.checkExistsData(repo, coQuanQuanLy)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
						ApiErrorEnum.DATA_EXISTS.getText(),ApiErrorEnum.TEN_EXISTS.getText());
			}

			if (!coQuanQuanLyService.isExists(repo, id)) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			if (coQuanQuanLy.getCapCoQuanQuanLy() != null) {
				ThamSo thamSo = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
				if (thamSo != null
						&& thamSo.getGiaTri().toString().equals(coQuanQuanLy.getCapCoQuanQuanLy().getId().toString())) {
					if (coQuanQuanLy.getCha() != null) {
						coQuanQuanLy.setDonVi(coQuanQuanLy.getCha());
					} else {
						coQuanQuanLy.setDonVi(coQuanQuanLy);
					}
				} else {
					coQuanQuanLy.setDonVi(coQuanQuanLy);
				}
			}
			
			coQuanQuanLy.setTenSearch(Utils.unAccent(coQuanQuanLy.getTen().trim()));
			coQuanQuanLy.setMoTaSearch(Utils.unAccent(coQuanQuanLy.getMoTa().trim()));
			return coQuanQuanLyService.doSave(coQuanQuanLy,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
					HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/coQuanQuanLys/{id}")
	@ApiOperation(value = "Xoá Cơ Quan Quản Lý", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Cơ Quan Quản Lý thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.COQUANQUANLY_XOA) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			if (coQuanQuanLyService.checkUsedData(repo, congChucRepository, donRepository, soTiepCongDanRepository,
					xuLyDonRepository, id)) {
				return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.DATA_USED.name(),
						ApiErrorEnum.DATA_USED.getText(), ApiErrorEnum.DATA_USED.getText());
			}

			CoQuanQuanLy coQuanQuanLy = coQuanQuanLyService.delete(repo, id);
			if (coQuanQuanLy == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}

			coQuanQuanLyService.save(coQuanQuanLy,
					Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/phongBansTheoDonVi")
	@ApiOperation(value = "Lấy danh sách Phong ban theo đơn vị", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getDanhSachPhongBanstheoDonVi(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
			Long donViId = Long.valueOf(commonProfile.getAttribute("donViId").toString());
			Page<CoQuanQuanLy> page = repo.findAll(coQuanQuanLyService.predicateFindPhongBanDonBanDonvi(donViId, thamSoService, repoThamSo, null), pageable);

			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/phongBanKhacPhongBanHienTaiTheoDonVi")
	@ApiOperation(value = "Lấy danh sách Phong ban theo đơn vị", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getDanhSachPhongBanKhacPhongBanHienTaiTheoDonVi(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
			Long donViId = Long.valueOf(commonProfile.getAttribute("donViId").toString());
			Long coQuanQuanLyId = Long.valueOf(commonProfile.getAttribute("coQuanQuanLyId").toString());
			Page<CoQuanQuanLy> page = repo.findAll(coQuanQuanLyService.predicateFindPhongBanDonBanDonvi(donViId, thamSoService, repoThamSo, coQuanQuanLyId), pageable);

			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/coQuanQuanLys/donViKeHoachThanhTra")
	@ApiOperation(value = "Lấy danh sách đơn vị kế hoạch thanh tra", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getDonViKeHoachThanhTra(@RequestHeader(value = "Authorization", required = true) String authorization, 
			Pageable pageable, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			pageable = new PageRequest(0, 1000, new Sort(new Order(Direction.ASC, "ten")));

			ThamSo thamSoCCQQLPhongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo thamSoLCQQLBoCongAn = repoThamSo.findOne(thamSoService.predicateFindTen("LCCQQL_BO_CONG_AN"));
			ThamSo thamSoDVHCTPDaNang = repoThamSo.findOne(thamSoService.predicateFindTen("DVHC_TP_DA_NANG"));
			ThamSo thamSoCCQQLUBNDPhuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString()));
			capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLPhongBan.getGiaTri().toString()));
			Page<CoQuanQuanLy> page = repo.findAll(coQuanQuanLyService.predicateFindAllDonViNotPhongBanNotCongAnNotPhuongXa(
					capCoQuanQuanLyIds,
					Long.valueOf(thamSoLCQQLBoCongAn.getGiaTri().toString()),
					Long.valueOf(thamSoDVHCTPDaNang.getGiaTri().toString())), pageable);
			
			return assembler.toResource(page, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
}
