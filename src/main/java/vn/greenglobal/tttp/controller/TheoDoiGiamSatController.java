package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.querydsl.core.types.dsl.BooleanExpression;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.CoQuanQuanLyService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.service.TheoDoiGiamSatService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "theoDoiGiamSats", description = "Theo dõi giám sát")
public class TheoDoiGiamSatController extends TttpController<Don> {

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
	private TheoDoiGiamSatService theoDoiGiamSatService;
	
	public TheoDoiGiamSatController(BaseRepository<Don, Long> repo) {
		super(repo);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/theoDoiGiamSats/tinhHinhXuLyDonTaiCacDonVi")
	@ApiOperation(value = "Lấy danh sách tình hình xử lý đơn tại các đơn vị", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachDonMoiNhatTheoDonVi(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, 
			@RequestParam(value = "quyTrinh", required = false) String quyTrinh,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "month", required = false) Long month,
			@RequestParam(value = "year", required = false) Long year,
			PersistentEntityResourceAssembler eass) {
		try {
			
//			if (Utils.tokenValidate(profileUtil, authorization) == null) {
//				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
//						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
//			}
			
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
			ThamSo thamSoCCQQLPhuongXaTT = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLChiCuc = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_CHI_CUC"));
			
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> mapTheoDoiGS = new HashMap<>();
			Map<String, Object> mapDangXuLy = new HashMap<>();
			Map<String, Object> mapDaXuLy = new HashMap<>();
			List<Map<String, Object>> coQuans = new ArrayList<>();
			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			
			if (donViXuLyXLD == Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString())
					|| donViXuLyXLD == Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString())) {
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString()));
				List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTDGDS(
						Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds,
						"CQQL_UBNDTP_DA_NANG"));
				donVis.addAll(list);
			} else if (capCoQuanQuanLyId == Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString())
					|| (donVi.getCapCoQuanQuanLy() != null && donVi.getCapCoQuanQuanLy().getId()
							.equals(Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString())))
					|| donVi.getCha() != null && donVi.getCha().getCapCoQuanQuanLy().getId()
							.equals(Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString()))) {
				// Danh sach don vi thuoc Quan Huyen
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString()));
				if (coQuanQuanLy.getCapCoQuanQuanLy() != null) {
					if (coQuanQuanLy.getCapCoQuanQuanLy().getId()
							.equals(Long.valueOf(thamSoCCQQLPhuongXaTT.getGiaTri().toString()))) {
						if (donVi.getCha() != null) {
							donViXuLyXLD = donVi.getCha().getId();
						}
					}
				}
				List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTDGDS(donViXuLyXLD, capCoQuanQuanLyIds,
						"CCQQL_UBND_QUAN_HUYEN"));
				donVis.addAll(list);
			} else if (capCoQuanQuanLyId == Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString())
					|| (donVi.getCapCoQuanQuanLy() != null && donVi.getCapCoQuanQuanLy().getId()
							.equals(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString())))) {
				// Danh sach don vi thuoc So Ban Nganh
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLChiCuc.getGiaTri().toString()));
				List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTDGDS(donViXuLyXLD, capCoQuanQuanLyIds,
						"CCQQL_SO_BAN_NGANH"));
				donVis.addAll(list);
			}
			
			donVis.forEach(dv -> { 
				System.out.println("dv " +" " +" " +dv.getTen());
			});
			System.out.println("donVis " +donVis.size());
			
			boolean isDungHan = true;
			boolean isTreHan = false;
			TrangThaiDonEnum trangThaiDangXL = TrangThaiDonEnum.DANG_XU_LY;
			TrangThaiDonEnum trangThaiDaXL = TrangThaiDonEnum.DA_XU_LY;
			BooleanExpression predDSDons = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDons(quyTrinh, tuNgay, denNgay, month, year, xuLyRepo, repo, giaiQuyetDonRepo);
			List<Don> ds = (List<Don>)repo.findAll(predDSDons);
			System.out.println("ds " +ds.size());
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonVi(predDSDons, cq.getId(), xuLyRepo, repo, giaiQuyetDonRepo);
				mapDonVi.put("id", cq.getId());
				mapDonVi.put("tenDonVi", cq.getTen());
				mapTheoDoiGS.put("donVi", mapDonVi);
				
				//Dang xu ly
				mapDangXuLy.put("dungHan", theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThai(predAll, repo, isDungHan, trangThaiDangXL));
				mapDangXuLy.put("treHan", theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThai(predAll, repo, isTreHan, trangThaiDangXL));
				mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
				
				//Da xu ly
				mapDaXuLy.put("dungHan", theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThai(predAll, repo, isDungHan, trangThaiDaXL));
				mapDaXuLy.put("treHan", theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThai(predAll, repo, isTreHan, trangThaiDaXL));
				mapTheoDoiGS.put("daXuLy", mapDaXuLy);
				
				mapTheoDoiGS.put("tongSo", theoDoiGiamSatService.getTongSoDon(predAll, repo));
				coQuans.add(mapTheoDoiGS);
				mapTheoDoiGS = new HashMap<>();
				mapDonVi = new HashMap<>();
				mapDangXuLy = new HashMap<>();
				mapDaXuLy = new HashMap<>();
			}
			map.put("donVis", coQuans);
			System.out.println("coQuans " +coQuans.size());
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
}
