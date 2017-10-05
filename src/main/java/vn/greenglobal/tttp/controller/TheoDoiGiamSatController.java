package vn.greenglobal.tttp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import vn.greenglobal.tttp.enums.LoaiTiepDanEnum;
import vn.greenglobal.tttp.enums.LoaiVuViecEnum;
import vn.greenglobal.tttp.enums.ProcessTypeEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.model.CapCoQuanQuanLy;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.GiaiQuyetDonRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.CoQuanQuanLyService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.service.TheoDoiGiamSatService;
import vn.greenglobal.tttp.util.ExcelUtil;
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
	public @ResponseBody ResponseEntity<Object> getDSTinhHinhXuLyDonTaiDonVi(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, 
			@RequestParam(value = "quyTrinh", required = false) String quyTrinh,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "month", required = false) Long month,
			@RequestParam(value = "year", required = false) Long year,
			PersistentEntityResourceAssembler eass) {
		try {
			
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Long coQuanQuanLyId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
			CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViXuLyXLD);
			CoQuanQuanLy coQuanQuanLy = coQuanQuanLyRepo.findOne(coQuanQuanLyId);
			Long capCoQuanQuanLyId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("capCoQuanQuanLyId").toString());
			ThamSo thamSoCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLUBNDSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoCCQQLUBNDPhuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLPhuongXaTT = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLChiCuc = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_CHI_CUC"));
			ThamSo thamSoCCQQLTrungTam = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_TRUNG_TAM"));
			
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> mapTheoDoiGS = new HashMap<>();
			Map<String, Object> mapDangXuLy = new HashMap<>();
			Map<String, Object> mapDaXuLy = new HashMap<>();
			List<Map<String, Object>> coQuans = new ArrayList<>();
			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			
			if (donViXuLyXLD == Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString())
					|| donViXuLyXLD == Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString())) {
				//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString()));
				List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTDGDS(
						Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds,
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
							.equals(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString())))|| 
					donVi.getCha() != null && donVi.getCha().getCapCoQuanQuanLy().getId()
							.equals(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()))) {
				// Danh sach don vi thuoc So Ban Nganh
				if (coQuanQuanLy.getCapCoQuanQuanLy() != null) {
					if (coQuanQuanLy.getCapCoQuanQuanLy().getId()
							.equals(Long.valueOf(thamSoCCQQLChiCuc.getGiaTri().toString())) ||
							coQuanQuanLy.getCapCoQuanQuanLy().getId()
							.equals(Long.valueOf(thamSoCCQQLTrungTam.getGiaTri().toString())) ) {
						if (donVi.getCha() != null) {
							donViXuLyXLD = donVi.getCha().getId();
						}
					}
				}
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLTrungTam.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLChiCuc.getGiaTri().toString()));
				List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTDGDS(donViXuLyXLD, capCoQuanQuanLyIds,
						"CCQQL_SO_BAN_NGANH"));
				donVis.addAll(list);
			}

			if (year == null) { 
				year = Long.valueOf(Utils.localDateTimeNow().getYear());
			}
			boolean isDungHan = true;
			boolean isTreHan = false;
			TrangThaiDonEnum trangThaiDangXL = TrangThaiDonEnum.DANG_XU_LY;
			TrangThaiDonEnum trangThaiDaXL = TrangThaiDonEnum.DA_XU_LY;
			TrangThaiDonEnum trangThaiDangGQ = TrangThaiDonEnum.DANG_GIAI_QUYET;
			TrangThaiDonEnum trangThaiDaGQ = TrangThaiDonEnum.DA_GIAI_QUYET;
			ProcessTypeEnum processType = ProcessTypeEnum.XU_LY_DON;
			Long tongSoDonDungHanDangXL = 0L;
			Long tongSoDonTreHanDangXL = 0L;
			Long tongSoDonDungHanDaXL = 0L;
			Long tongSoDonTreHanDaXL = 0L;
			Long tongSo = 0L;
			
			Long tongDonDungHanDangXL = 0L;
			Long tongDonTreHanDangXL = 0L;
			Long tongDonDungHanDaXL = 0L;
			Long tongDonTreHanDaXL = 0L;
			Long tongSoDangDaXL = 0L;
			BooleanExpression predDSAllDons = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDons(tuNgay, denNgay, month, year, xuLyRepo, repo, giaiQuyetDonRepo);
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predDSDons = predDSAllDons;
				BooleanExpression predDSXLDons = predDSAllDons;
				mapDonVi.put("id", cq.getId());
				mapDonVi.put("tenDonVi", cq.getTen());
				mapTheoDoiGS.put("donVi", mapDonVi);
				if (StringUtils.isNotBlank(quyTrinh)) {
					processType = ProcessTypeEnum.valueOf(quyTrinh);
					predDSDons = predDSDons.and(QDon.don.processType.eq(processType));
					if (processType.equals(ProcessTypeEnum.GIAI_QUYET_DON)) { 
						predDSDons = predDSDons.or(QDon.don.thongTinGiaiQuyetDon.ngayBatDauGiaiQuyet.isNotNull());
						// giai quyet don
						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViGQD(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);
						
						// dang giai quyet
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isDungHan, trangThaiDangGQ);
						tongDonDungHanDangXL += tongSoDonDungHanDangXL;
						mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isTreHan, trangThaiDangGQ);
						tongDonTreHanDangXL += tongSoDonTreHanDangXL;
						mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
						mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
						
						//da giai quyet
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isDungHan, trangThaiDaGQ);
						tongDonDungHanDaXL += tongSoDonDungHanDaXL;
						mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isTreHan, trangThaiDaGQ);
						tongDonTreHanDaXL += tongSoDonTreHanDaXL;
						mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
						mapTheoDoiGS.put("daXuLy", mapDaXuLy);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						tongSoDangDaXL += tongSo;
						mapTheoDoiGS.put("tongSo", tongSo);
					}
					
					if (processType.equals(ProcessTypeEnum.THAM_TRA_XAC_MINH)) { 
						//tham tra xac minh
						predDSDons = predDSDons.or(QDon.don.thongTinGiaiQuyetDon.ngayBatDauTTXM.isNotNull());

						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViTTXM(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);
						
						// dang giai quyet
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isDungHan, trangThaiDangGQ);
						tongDonDungHanDangXL += tongSoDonDungHanDangXL;
						mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isTreHan, trangThaiDangGQ);
						tongDonTreHanDangXL += tongSoDonTreHanDangXL;
						mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
						mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
						
						//da giai quyet
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isDungHan, trangThaiDaGQ);
						tongDonDungHanDaXL += tongSoDonDungHanDaXL;
						mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isTreHan, trangThaiDaGQ);
						tongDonTreHanDaXL += tongSoDonTreHanDaXL;
						mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
						mapTheoDoiGS.put("daXuLy", mapDaXuLy);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						tongSoDangDaXL += tongSo;
						mapTheoDoiGS.put("tongSo", tongSo);
					}
					
					if (processType.equals(ProcessTypeEnum.KIEM_TRA_DE_XUAT)) {
						predDSDons = predDSDons.or(QDon.don.processType.isNull().and(QDon.don.trangThaiKTDX.isNotNull()));
						//kiem tra de xuat
						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViKTDX(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);

						// dang giai quyet
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isDungHan, trangThaiDangGQ);
						tongDonDungHanDangXL += tongSoDonDungHanDangXL;
						mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isTreHan, trangThaiDangGQ);
						tongDonTreHanDangXL += tongSoDonTreHanDangXL;
						mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
						mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
						
						//da giai quyet
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isDungHan, trangThaiDaGQ);
						tongDonDungHanDaXL += tongSoDonDungHanDaXL;
						mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isTreHan, trangThaiDaGQ);
						tongDonTreHanDaXL += tongSoDonTreHanDaXL;
						mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
						mapTheoDoiGS.put("daXuLy", mapDaXuLy);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						tongSoDangDaXL += tongSo;
						mapTheoDoiGS.put("tongSo", tongSo);
					}
					
					if (processType.equals(ProcessTypeEnum.XU_LY_DON)) {
						predDSXLDons = predDSXLDons.and(QDon.don.thanhLapDon.isTrue());

						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViXLD(predDSXLDons, cq.getId(), xuLyRepo, repo);
						
						//Dang xu ly
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDangXL);
						tongDonDungHanDangXL += tongSoDonDungHanDangXL;
						mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDangXL);
						tongDonTreHanDangXL += tongSoDonTreHanDangXL;
						mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
						mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
						
						//Da xu ly
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDaXL);
						tongDonDungHanDaXL += tongSoDonDungHanDaXL;
						mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDaXL);
						tongDonTreHanDaXL += tongSoDonTreHanDaXL;
						mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
						mapTheoDoiGS.put("daXuLy", mapDaXuLy);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						tongSoDangDaXL += tongSo;
						mapTheoDoiGS.put("tongSo", tongSo);
					}
				} else { 
					//xu ly don
//					predDSDons = predDSDons.and(QDon.don.processType.eq(processType));
					predDSXLDons = predDSXLDons.and(QDon.don.thanhLapDon.isTrue());
					
					BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViXLD(predDSXLDons, cq.getId(), xuLyRepo, repo);
					//Dang xu ly
					tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDangXL);
					tongDonDungHanDangXL += tongSoDonDungHanDangXL;
					mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
					
					tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDangXL);
					tongDonTreHanDangXL += tongSoDonTreHanDangXL;
					mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
					mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
					
					//Da xu ly
					tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDaXL);
					tongDonDungHanDaXL += tongSoDonDungHanDaXL;
					mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
					
					tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDaXL);
					tongDonTreHanDaXL += tongSoDonTreHanDaXL;
					mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
					mapTheoDoiGS.put("daXuLy", mapDaXuLy);
					
					tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
					tongSoDangDaXL += tongSo;
					mapTheoDoiGS.put("tongSo", tongSo);
				}
				coQuans.add(mapTheoDoiGS);
				mapTheoDoiGS = new HashMap<>();
				mapDonVi = new HashMap<>();
				mapDangXuLy = new HashMap<>();
				mapDaXuLy = new HashMap<>();
			}
			map.put("tongDonDungHanDangXL", tongDonDungHanDangXL);
			map.put("tongDonTreHanDangXL", tongDonTreHanDangXL);
			map.put("tongDonDungHanDaXL", tongDonDungHanDaXL);
			map.put("tongDonTreHanDaXL", tongDonTreHanDaXL);
			map.put("tongDonDangDaXL", tongSoDangDaXL);
			
			map.put("donVis", coQuans);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/theoDoiGiamSats/tinhHinhXuLyDonTaiCacDonVi/xuatExcel")
	@ApiOperation(value = "Xuất file excel danh sách tình hình xử lý đơn tại các đơn vị", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelDSTinhHinhXuLyDonTaiDonVi(HttpServletResponse response,
			@RequestParam(value = "quyTrinh", required = false) String quyTrinh,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "month", required = false) Long month,
			@RequestParam(value = "year", required = false) Long year,
			@RequestParam(value = "capCoQuanQuanLyId", required = true) Long capCoQuanQuanLyId,
			@RequestParam(value = "coQuanQuanLyId", required = true) Long coQuanQuanLyId,
			@RequestParam(value = "donViId", required = true) Long donViId) throws IOException {

		try {
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			Map<String, Object> mapMaSo = new HashMap<>();
			
			CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViId);
			CoQuanQuanLy coQuanQuanLy = coQuanQuanLyRepo.findOne(coQuanQuanLyId);
			Long donViXuLyXLD = donVi.getId();
			ThamSo thamSoCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLUBNDSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoCCQQLUBNDPhuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLPhuongXaTT = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLChiCuc = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_CHI_CUC"));
			ThamSo thamSoCCQQLTrungTam = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_TRUNG_TAM"));
			
			if (donViXuLyXLD == Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString())
					|| donViXuLyXLD == Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString())) {
				//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString()));
				List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTDGDS(
						Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds,
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
							.equals(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString())))|| 
					donVi.getCha() != null && donVi.getCha().getCapCoQuanQuanLy().getId()
							.equals(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()))) {
				// Danh sach don vi thuoc So Ban Nganh
				if (coQuanQuanLy.getCapCoQuanQuanLy() != null) {
					if (coQuanQuanLy.getCapCoQuanQuanLy().getId()
							.equals(Long.valueOf(thamSoCCQQLChiCuc.getGiaTri().toString())) ||
							coQuanQuanLy.getCapCoQuanQuanLy().getId()
							.equals(Long.valueOf(thamSoCCQQLTrungTam.getGiaTri().toString())) ) {
						if (donVi.getCha() != null) {
							donViXuLyXLD = donVi.getCha().getId();
						}
					}
				}
				
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLTrungTam.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLChiCuc.getGiaTri().toString()));
				List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTDGDS(donViXuLyXLD, capCoQuanQuanLyIds,
						"CCQQL_SO_BAN_NGANH"));
				donVis.addAll(list);
			}

			if (year == null) { 
				year = Long.valueOf(Utils.localDateTimeNow().getYear());
			}
			boolean isDungHan = true;
			boolean isTreHan = false;
			TrangThaiDonEnum trangThaiDangXL = TrangThaiDonEnum.DANG_XU_LY;
			TrangThaiDonEnum trangThaiDaXL = TrangThaiDonEnum.DA_XU_LY;
			TrangThaiDonEnum trangThaiDangGQ = TrangThaiDonEnum.DANG_GIAI_QUYET;
			TrangThaiDonEnum trangThaiDaGQ = TrangThaiDonEnum.DA_GIAI_QUYET;
			ProcessTypeEnum processType = ProcessTypeEnum.XU_LY_DON;
			Long tongSoDonDungHanDangXL = 0L;
			Long tongSoDonTreHanDangXL = 0L;
			Long tongSoDonDungHanDaXL = 0L;
			Long tongSoDonTreHanDaXL = 0L;
			Long tongSo = 0L;
			
			BooleanExpression predDSAllDons = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDons(tuNgay, denNgay, month, year, xuLyRepo, repo, giaiQuyetDonRepo);
			int idx = 0;
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predDSDons = predDSAllDons;
				BooleanExpression predDSXLDons = predDSAllDons;
				idx += 1;
				mapMaSo.put("0", idx);
				mapMaSo.put("1", cq.getTen());
				if (StringUtils.isNotBlank(quyTrinh)) {
					processType = ProcessTypeEnum.valueOf(quyTrinh);
					predDSDons = predDSDons.and(QDon.don.processType.eq(processType));
					if (processType.equals(ProcessTypeEnum.GIAI_QUYET_DON)) { 
						predDSDons = predDSDons.or(QDon.don.thongTinGiaiQuyetDon.ngayBatDauGiaiQuyet.isNotNull());
						// giai quyet don
						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViGQD(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);
						
						// dang giai quyet
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isDungHan, trangThaiDangGQ);
						mapMaSo.put("2", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isTreHan, trangThaiDangGQ);
						mapMaSo.put("3", tongSoDonTreHanDangXL);
						
						//da giai quyet
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isDungHan, trangThaiDaGQ);
						mapMaSo.put("4", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isTreHan, trangThaiDaGQ);
						mapMaSo.put("5", tongSoDonTreHanDaXL);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						mapMaSo.put("6", tongSo);
					}
					
					if (processType.equals(ProcessTypeEnum.THAM_TRA_XAC_MINH)) { 
						//tham tra xac minh
						predDSDons = predDSDons.or(QDon.don.thongTinGiaiQuyetDon.ngayBatDauTTXM.isNotNull());

						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViTTXM(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);
						
						// dang giai quyet
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isDungHan, trangThaiDangGQ);
						mapMaSo.put("2", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isTreHan, trangThaiDangGQ);
						mapMaSo.put("3", tongSoDonTreHanDangXL);
						
						//da giai quyet
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isDungHan, trangThaiDaGQ);
						mapMaSo.put("4", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isTreHan, trangThaiDaGQ);
						mapMaSo.put("5", tongSoDonTreHanDaXL);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						mapMaSo.put("6", tongSo);
					}
					
					if (processType.equals(ProcessTypeEnum.KIEM_TRA_DE_XUAT)) {
						predDSDons = predDSDons.or(QDon.don.processType.isNull().and(QDon.don.trangThaiKTDX.isNotNull()));
						//kiem tra de xuat
						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViKTDX(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);

						// dang giai quyet
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isDungHan, trangThaiDangGQ);
						mapMaSo.put("2", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isTreHan, trangThaiDangGQ);
						mapMaSo.put("3", tongSoDonTreHanDangXL);
						
						//da giai quyet
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isDungHan, trangThaiDaGQ);
						mapMaSo.put("4", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isTreHan, trangThaiDaGQ);
						mapMaSo.put("5", tongSoDonTreHanDaXL);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						mapMaSo.put("6", tongSo);
					}
					
					if (processType.equals(ProcessTypeEnum.XU_LY_DON)) {
						predDSXLDons = predDSXLDons.and(QDon.don.thanhLapDon.isTrue());

						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViXLD(predDSXLDons, cq.getId(), xuLyRepo, repo);
						
						//Dang xu ly
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDangXL);
						mapMaSo.put("2", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDangXL);
						mapMaSo.put("3", tongSoDonTreHanDangXL);
						
						//Da xu ly
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDaXL);
						mapMaSo.put("4", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDaXL);
						mapMaSo.put("5", tongSoDonTreHanDaXL);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						mapMaSo.put("6", tongSo);
					}
				} else { 
					//xu ly don
					predDSXLDons = predDSXLDons.and(QDon.don.thanhLapDon.isTrue());
					
					BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViXLD(predDSXLDons, cq.getId(), xuLyRepo, repo);
					//Dang xu ly
					tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDangXL);
					mapMaSo.put("2", tongSoDonDungHanDangXL);
					
					tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDangXL);
					mapMaSo.put("3", tongSoDonTreHanDangXL);
					
					//Da xu ly
					tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDaXL);
					mapMaSo.put("4", tongSoDonDungHanDaXL);
					
					tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDaXL);
					mapMaSo.put("5", tongSoDonTreHanDaXL);
					
					tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
					mapMaSo.put("6", tongSo);
				}
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<>();
			}
			
			ExcelUtil.exportTheoDoiGiamSatTheoDonVi(response, "DanhSachTinhHinhXuLyDonTaiDonVi", "sheetName", 
					maSos, "Danh sách tình hình xử lý đơn tại các đơn vị", null);
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/theoDoiGiamSats/tinhHinhXuLyDonTaiCacDonViCon")
	@ApiOperation(value = "Lấy danh sách tình hình xử lý đơn tại các đơn vị con", position = 2, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> getDanhSachDonMoiNhatTheoDonViCon(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, 
			@RequestParam(value = "quyTrinh", required = false) String quyTrinh,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "month", required = false) Long month,
			@RequestParam(value = "year", required = false) Long year,
			@RequestParam(value = "donViId", required = false) Long donViId,
			PersistentEntityResourceAssembler eass) {
		try {
			
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			if (donViId == null) {
				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
			}
			
			CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViId);
			ThamSo thamSoCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLUBNDSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoCCQQLUBNDPhuongXaTT = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLChiCuc = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_CHI_CUC"));
			ThamSo thamSoCCQQLTrungTam = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_TRUNG_TAM"));
			
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> mapTheoDoiGS = new HashMap<>();
			Map<String, Object> mapDangXuLy = new HashMap<>();
			Map<String, Object> mapDaXuLy = new HashMap<>();
			List<Map<String, Object>> coQuans = new ArrayList<>();
			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			
			mapDonVi.put("id",donVi.getId());
			mapDonVi.put("tenDonVi",donVi.getTen());
			map.put("donViCha", mapDonVi);
			
			mapDonVi = new HashMap<>();
			if (donViId == Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString())) {				
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString()));
				List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindConCuaDonViChaTDGDS(
						Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds, "CQQL_UBNDTP_DA_NANG"));
				
				donVis.addAll(list);
				donVis.remove(donVi);
			} else {
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDPhuongXaTT.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLTrungTam.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLChiCuc.getGiaTri().toString()));
				List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindConCuaDonViChaTDGDS(
						donViId, capCoQuanQuanLyIds, ""));
				donVis.addAll(list);
				donVis.remove(donVi);
			}
			
			if (year == null) { 
				year = Long.valueOf(Utils.localDateTimeNow().getYear());
			}
			boolean isDungHan = true;
			boolean isTreHan = false;
			TrangThaiDonEnum trangThaiDangXL = TrangThaiDonEnum.DANG_XU_LY;
			TrangThaiDonEnum trangThaiDaXL = TrangThaiDonEnum.DA_XU_LY;
			TrangThaiDonEnum trangThaiDangGQ = TrangThaiDonEnum.DANG_GIAI_QUYET;
			TrangThaiDonEnum trangThaiDaGQ = TrangThaiDonEnum.DA_GIAI_QUYET;
			ProcessTypeEnum processType = ProcessTypeEnum.XU_LY_DON;
			Long tongSoDonDungHanDangXL = 0L;
			Long tongSoDonTreHanDangXL = 0L;
			Long tongSoDonDungHanDaXL = 0L;
			Long tongSoDonTreHanDaXL = 0L;
			Long tongSo = 0L;
			
			Long tongDonDungHanDangXL = 0L;
			Long tongDonTreHanDangXL = 0L;
			Long tongDonDungHanDaXL = 0L;
			Long tongDonTreHanDaXL = 0L;
			Long tongSoDangDaXL = 0L;
			BooleanExpression predDSAllDons = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDons(tuNgay, denNgay, month, year, xuLyRepo, repo, giaiQuyetDonRepo);
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predDSDons = predDSAllDons;
				BooleanExpression predDSXLDons = predDSAllDons;
				mapDonVi.put("id", cq.getId());
				mapDonVi.put("tenDonVi", cq.getTen());
				mapTheoDoiGS.put("donVi", mapDonVi);
				if (StringUtils.isNotBlank(quyTrinh)) {
					processType = ProcessTypeEnum.valueOf(quyTrinh);
					predDSDons = predDSDons.and(QDon.don.processType.eq(processType));
					if (processType.equals(ProcessTypeEnum.GIAI_QUYET_DON)) { 
						predDSDons = predDSDons.or(QDon.don.thongTinGiaiQuyetDon.ngayBatDauGiaiQuyet.isNotNull());
						// giai quyet don
						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViGQD(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);
						// dang giai quyet
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isDungHan, trangThaiDangGQ);
						tongDonDungHanDangXL += tongSoDonDungHanDangXL;
						mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isTreHan, trangThaiDangGQ);
						tongDonTreHanDangXL += tongSoDonTreHanDangXL;
						mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
						mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
						
						//da giai quyet
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isDungHan, trangThaiDaGQ);
						tongDonDungHanDaXL += tongSoDonDungHanDaXL;
						mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isTreHan, trangThaiDaGQ);
						tongDonTreHanDaXL += tongSoDonTreHanDaXL;
						mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
						mapTheoDoiGS.put("daXuLy", mapDaXuLy);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						tongSoDangDaXL += tongSo;
						mapTheoDoiGS.put("tongSo", tongSo);
					}
					
					if (processType.equals(ProcessTypeEnum.THAM_TRA_XAC_MINH)) { 
						//tham tra xac minh
						predDSDons = predDSDons.or(QDon.don.thongTinGiaiQuyetDon.ngayBatDauTTXM.isNotNull());

						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViTTXM(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);
						
						// dang giai quyet
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isDungHan, trangThaiDangGQ);
						tongDonDungHanDangXL += tongSoDonDungHanDangXL;
						mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isTreHan, trangThaiDangGQ);
						tongDonTreHanDangXL += tongSoDonTreHanDangXL;
						mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
						mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
						
						//da giai quyet
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isDungHan, trangThaiDaGQ);
						tongDonDungHanDaXL += tongSoDonDungHanDaXL;
						mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isTreHan, trangThaiDaGQ);
						tongDonTreHanDaXL += tongSoDonTreHanDaXL;
						mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
						mapTheoDoiGS.put("daXuLy", mapDaXuLy);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						tongSoDangDaXL += tongSo;
						mapTheoDoiGS.put("tongSo", tongSo);
					}
					
					if (processType.equals(ProcessTypeEnum.KIEM_TRA_DE_XUAT)) {
						predDSDons = predDSDons.or(QDon.don.processType.isNull().and(QDon.don.trangThaiKTDX.isNotNull()));
						//kiem tra de xuat
						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViKTDX(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);

						// dang giai quyet
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isDungHan, trangThaiDangGQ);
						tongDonDungHanDangXL += tongSoDonDungHanDangXL;
						mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isTreHan, trangThaiDangGQ);
						tongDonTreHanDangXL += tongSoDonTreHanDangXL;
						mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
						mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
						
						//da giai quyet
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isDungHan, trangThaiDaGQ);
						tongDonDungHanDaXL += tongSoDonDungHanDaXL;
						mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isTreHan, trangThaiDaGQ);
						tongDonTreHanDaXL += tongSoDonTreHanDaXL;
						mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
						mapTheoDoiGS.put("daXuLy", mapDaXuLy);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						tongSoDangDaXL += tongSo;
						mapTheoDoiGS.put("tongSo", tongSo);
					}
					
					if (processType.equals(ProcessTypeEnum.XU_LY_DON)) {
						predDSXLDons = predDSXLDons.and(QDon.don.thanhLapDon.isTrue());

						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViXLD(predDSXLDons, cq.getId(), xuLyRepo, repo);
						
						//Dang xu ly
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDangXL);
						tongDonDungHanDangXL += tongSoDonDungHanDangXL;
						mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDangXL);
						tongDonTreHanDangXL += tongSoDonTreHanDangXL;
						mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
						mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
						
						//Da xu ly
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDaXL);
						tongDonDungHanDaXL += tongSoDonDungHanDaXL;
						mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDaXL);
						tongDonTreHanDaXL += tongSoDonTreHanDaXL;
						mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
						mapTheoDoiGS.put("daXuLy", mapDaXuLy);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						tongSoDangDaXL += tongSo;
						mapTheoDoiGS.put("tongSo", tongSo);
					}
				} else { 
					//xu ly don
					predDSXLDons = predDSXLDons.and(QDon.don.thanhLapDon.isTrue());
					
					BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViXLD(predDSXLDons, cq.getId(), xuLyRepo, repo);
					//Dang xu ly
					tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDangXL);
					tongDonDungHanDangXL += tongSoDonDungHanDangXL;
					mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
					
					tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDangXL);
					tongDonTreHanDangXL += tongSoDonTreHanDangXL;
					mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
					mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
					
					//Da xu ly
					tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDaXL);
					tongDonDungHanDaXL += tongSoDonDungHanDaXL;
					mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
					
					tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDaXL);
					tongDonTreHanDaXL += tongSoDonTreHanDaXL;
					mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
					mapTheoDoiGS.put("daXuLy", mapDaXuLy);
					
					tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
					tongSoDangDaXL += tongSo;
					mapTheoDoiGS.put("tongSo", tongSo);
				}
				coQuans.add(mapTheoDoiGS);
				mapTheoDoiGS = new HashMap<>();
				mapDonVi = new HashMap<>();
				mapDangXuLy = new HashMap<>();
				mapDaXuLy = new HashMap<>();
			}
			map.put("tongDonDungHanDangXL", tongDonDungHanDangXL);
			map.put("tongDonTreHanDangXL", tongDonTreHanDangXL);
			map.put("tongDonDungHanDaXL", tongDonDungHanDaXL);
			map.put("tongDonTreHanDaXL", tongDonTreHanDaXL);
			map.put("tongDonDangDaXL", tongSoDangDaXL);
			
			map.put("donVis", coQuans);
			
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/theoDoiGiamSats/tinhHinhXuLyDonTaiCacDonViCon/xuatExcel")
	@ApiOperation(value = "Xuất file excel danh sách tình hình xử lý đơn tại các đơn vị", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelDSTinhHinhXuLyDonTrenDiaBan(HttpServletResponse response,
			@RequestParam(value = "quyTrinh", required = false) String quyTrinh,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "month", required = false) Long month,
			@RequestParam(value = "year", required = false) Long year,
			@RequestParam(value = "donViId", required = true) Long donViId) throws IOException {

		try {
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			Map<String, Object> mapMaSo = new HashMap<>();
			
			CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViId);
			Long donViXuLyXLD = donVi.getId();
			ThamSo thamSoCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLUBNDSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoCCQQLUBNDPhuongXaTT = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLChiCuc = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_CHI_CUC"));
			ThamSo thamSoCCQQLTrungTam = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_TRUNG_TAM"));

			if (donViXuLyXLD == Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString())) {	
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString()));
				List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindConCuaDonViChaTDGDS(
						Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds, "CQQL_UBNDTP_DA_NANG"));
				
				donVis.addAll(list);
				donVis.remove(donVi);
			} else {
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDPhuongXaTT.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLTrungTam.getGiaTri().toString()));
				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLChiCuc.getGiaTri().toString()));
				List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindConCuaDonViChaTDGDS(
						donViXuLyXLD, capCoQuanQuanLyIds, ""));
				donVis.addAll(list);
				donVis.remove(donVi);
			}

			if (year == null) { 
				year = Long.valueOf(Utils.localDateTimeNow().getYear());
			}
			boolean isDungHan = true;
			boolean isTreHan = false;
			TrangThaiDonEnum trangThaiDangXL = TrangThaiDonEnum.DANG_XU_LY;
			TrangThaiDonEnum trangThaiDaXL = TrangThaiDonEnum.DA_XU_LY;
			TrangThaiDonEnum trangThaiDangGQ = TrangThaiDonEnum.DANG_GIAI_QUYET;
			TrangThaiDonEnum trangThaiDaGQ = TrangThaiDonEnum.DA_GIAI_QUYET;
			ProcessTypeEnum processType = ProcessTypeEnum.XU_LY_DON;
			Long tongSoDonDungHanDangXL = 0L;
			Long tongSoDonTreHanDangXL = 0L;
			Long tongSoDonDungHanDaXL = 0L;
			Long tongSoDonTreHanDaXL = 0L;
			Long tongSo = 0L;
			
			BooleanExpression predDSAllDons = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDons(tuNgay, denNgay, month, year, xuLyRepo, repo, giaiQuyetDonRepo);
			int idx = 0;
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predDSDons = predDSAllDons;
				BooleanExpression predDSXLDons = predDSAllDons;
				idx += 1;
				mapMaSo.put("0", idx);
				mapMaSo.put("1", cq.getTen());
				if (StringUtils.isNotBlank(quyTrinh)) {
					processType = ProcessTypeEnum.valueOf(quyTrinh);
					predDSDons = predDSDons.and(QDon.don.processType.eq(processType));
					if (processType.equals(ProcessTypeEnum.GIAI_QUYET_DON)) { 
						predDSDons = predDSDons.or(QDon.don.thongTinGiaiQuyetDon.ngayBatDauGiaiQuyet.isNotNull());
						// giai quyet don
						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViGQD(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);
						
						// dang giai quyet
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isDungHan, trangThaiDangGQ);
						mapMaSo.put("2", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isTreHan, trangThaiDangGQ);
						mapMaSo.put("3", tongSoDonTreHanDangXL);
						
						//da giai quyet
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isDungHan, trangThaiDaGQ);
						mapMaSo.put("4", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isTreHan, trangThaiDaGQ);
						mapMaSo.put("5", tongSoDonTreHanDaXL);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						mapMaSo.put("6", tongSo);
					}
					
					if (processType.equals(ProcessTypeEnum.THAM_TRA_XAC_MINH)) { 
						//tham tra xac minh
						predDSDons = predDSDons.or(QDon.don.thongTinGiaiQuyetDon.ngayBatDauTTXM.isNotNull());

						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViTTXM(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);
						
						// dang giai quyet
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isDungHan, trangThaiDangGQ);
						mapMaSo.put("2", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isTreHan, trangThaiDangGQ);
						mapMaSo.put("3", tongSoDonTreHanDangXL);
						
						//da giai quyet
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isDungHan, trangThaiDaGQ);
						mapMaSo.put("4", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isTreHan, trangThaiDaGQ);
						mapMaSo.put("5", tongSoDonTreHanDaXL);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						mapMaSo.put("6", tongSo);
					}
					
					if (processType.equals(ProcessTypeEnum.KIEM_TRA_DE_XUAT)) {
						predDSDons = predDSDons.or(QDon.don.processType.isNull().and(QDon.don.trangThaiKTDX.isNotNull()));
						//kiem tra de xuat
						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViKTDX(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);

						// dang giai quyet
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isDungHan, trangThaiDangGQ);
						mapMaSo.put("2", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isTreHan, trangThaiDangGQ);
						mapMaSo.put("3", tongSoDonTreHanDangXL);
						
						//da giai quyet
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isDungHan, trangThaiDaGQ);
						mapMaSo.put("4", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isTreHan, trangThaiDaGQ);
						mapMaSo.put("5", tongSoDonTreHanDaXL);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						mapMaSo.put("6", tongSo);
					}
					
					if (processType.equals(ProcessTypeEnum.XU_LY_DON)) {
						predDSXLDons = predDSXLDons.and(QDon.don.thanhLapDon.isTrue());

						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViXLD(predDSXLDons, cq.getId(), xuLyRepo, repo);
						
						//Dang xu ly
						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDangXL);
						mapMaSo.put("2", tongSoDonDungHanDangXL);
						
						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDangXL);
						mapMaSo.put("3", tongSoDonTreHanDangXL);
						
						//Da xu ly
						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDaXL);
						mapMaSo.put("4", tongSoDonDungHanDaXL);
						
						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDaXL);
						mapMaSo.put("5", tongSoDonTreHanDaXL);
						
						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
						mapMaSo.put("6", tongSo);
					}
				} else { 
					//xu ly don
					predDSXLDons = predDSXLDons.and(QDon.don.thanhLapDon.isTrue());
					
					BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViXLD(predDSXLDons, cq.getId(), xuLyRepo, repo);
					//Dang xu ly
					tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDangXL);
					mapMaSo.put("2", tongSoDonDungHanDangXL);
					
					tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDangXL);
					mapMaSo.put("3", tongSoDonTreHanDangXL);
					
					//Da xu ly
					tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDaXL);
					mapMaSo.put("4", tongSoDonDungHanDaXL);
					
					tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDaXL);
					mapMaSo.put("5", tongSoDonTreHanDaXL);
					
					tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
					mapMaSo.put("6", tongSo);
				}
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<>();
			}
			
			ExcelUtil.exportTheoDoiGiamSatTheoDonVi(response, "DanhSachTinhHinhXuLyDonTaiDonVi", "sheetName", 
					maSos, "Danh sách tình hình xử lý đơn tại địa bàn", null);
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
//	@RequestMapping(method = RequestMethod.GET, value = "/theoDoiGiamSats/tinhHinhXuLyDonTaiCacDonViCon/xuatExcel")
//	@ApiOperation(value = "Xuất excel danh sách tình hình xử lý đơn tại các đơn vị con", position = 2, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public @ResponseBody ResponseEntity<Object> xuatExcelDanhSachDonMoiNhatTheoDonViCon(
//			HttpServletResponse response, 
//			@RequestParam(value = "quyTrinh", required = false) String quyTrinh,
//			@RequestParam(value = "tuNgay", required = false) String tuNgay,
//			@RequestParam(value = "denNgay", required = false) String denNgay,
//			@RequestParam(value = "month", required = false) Long month,
//			@RequestParam(value = "year", required = false) Long year,
//			@RequestParam(value = "donViId", required = false) Long donViId
//			) throws IOException {
//		try {
//			
//			if (donViId == null) {
//				return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
//						ApiErrorEnum.DATA_NOT_FOUND.getText(), ApiErrorEnum.DATA_NOT_FOUND.getText());
//			}
//			
//			// Tim kiem don vi hien tai
//			CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViId);
//			
//			Long capCoQuanQuanLyId = (Long) donVi.getCapCoQuanQuanLyCQQL().get("capCoQuanQuanLyId");
//			
//			ThamSo thamSoCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
//			ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
//			ThamSo thamSoCCQQLUBNDSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
//			ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
//			ThamSo thamSoCCQQLUBNDPhuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
//			ThamSo thamSoCCQQLPhuongXaTT = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
//			ThamSo thamSoCCQQLChiCuc = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_CHI_CUC"));
//			ThamSo thamSoCCQQLTrungTam = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_TRUNG_TAM"));
//			
//			Map<String, Object> map = new HashMap<>();
//			Map<String, Object> mapDonVi = new HashMap<>();
//			Map<String, Object> mapTheoDoiGS = new HashMap<>();
//			Map<String, Object> mapDangXuLy = new HashMap<>();
//			Map<String, Object> mapDaXuLy = new HashMap<>();
//			List<Map<String, Object>> coQuans = new ArrayList<>();
//			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
//			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
//			
//			mapDonVi.put("id",donVi.getId());
//			mapDonVi.put("tenDonVi",donVi.getTen());
//			
//			map.put("donViCha", mapDonVi);
//			
//			mapDonVi = new HashMap<>();
//			
//			if (donViId == Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString())
//					|| donViId == Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString())) {
//				//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()));
//				
//				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString()));
//				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
//				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString()));
//				
//				List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindConCuaDonViCha(
//						Long.valueOf(thamSoCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds, "CQQL_UBNDTP_DA_NANG"));
//				
//				donVis.addAll(list);
//				donVis.remove(donVi);
//				
//			} else if (capCoQuanQuanLyId == Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString())
//					|| (donVi.getCapCoQuanQuanLy() != null && donVi.getCapCoQuanQuanLy().getId()
//							.equals(Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString())))
//					|| donVi.getCha() != null && donVi.getCha().getCapCoQuanQuanLy().getId()
//							.equals(Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString()))) {
//				
//				// Danh sach don vi thuoc Quan Huyen
//				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDPhuongXa.getGiaTri().toString()));
//				
//				if (donVi.getCapCoQuanQuanLy() != null) {
//					if (donVi.getCapCoQuanQuanLy().getId()
//							.equals(Long.valueOf(thamSoCCQQLPhuongXaTT.getGiaTri().toString()))) {
//						if (donVi.getCha() != null) {
//							donViId = donVi.getCha().getId();
//						}
//					}
//				}
//				List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindConCuaDonViCha(donViId, capCoQuanQuanLyIds,
//						"CCQQL_UBND_QUAN_HUYEN"));
//				
//				donVis.addAll(list);
//				donVis.remove(donVi);
//				
//			} else if (capCoQuanQuanLyId == Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString())
//					|| (donVi.getCapCoQuanQuanLy() != null && donVi.getCapCoQuanQuanLy().getId()
//							.equals(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString())))|| 
//					donVi.getCha() != null && donVi.getCha().getCapCoQuanQuanLy().getId()
//							.equals(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()))) {
//				
//				// Danh sach don vi thuoc So Ban Nganh
//				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
//				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLTrungTam.getGiaTri().toString()));
//				capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLChiCuc.getGiaTri().toString()));
//				
//				if (donVi.getCapCoQuanQuanLy() != null) {
//					if (donVi.getCapCoQuanQuanLy().getId()
//							.equals(Long.valueOf(thamSoCCQQLChiCuc.getGiaTri().toString()))) {
//						if (donVi.getCha() != null) {
//							donViId = donVi.getCha().getId();
//						}
//					}
//				}
//				
//				List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindConCuaDonViCha(donViId, capCoQuanQuanLyIds,
//						"CCQQL_SO_BAN_NGANH"));
//				donVis.addAll(list);
//				donVis.remove(donVi);
//			}
//
//			if (year == null) { 
//				year = Long.valueOf(Utils.localDateTimeNow().getYear());
//			}
//			boolean isDungHan = true;
//			boolean isTreHan = false;
//			TrangThaiDonEnum trangThaiDangXL = TrangThaiDonEnum.DANG_XU_LY;
//			TrangThaiDonEnum trangThaiDaXL = TrangThaiDonEnum.DA_XU_LY;
//			TrangThaiDonEnum trangThaiDangGQ = TrangThaiDonEnum.DANG_GIAI_QUYET;
//			TrangThaiDonEnum trangThaiDaGQ = TrangThaiDonEnum.DA_GIAI_QUYET;
//			ProcessTypeEnum processType = ProcessTypeEnum.XU_LY_DON;
//			Long tongSoDonDungHanDangXL = 0L;
//			Long tongSoDonTreHanDangXL = 0L;
//			Long tongSoDonDungHanDaXL = 0L;
//			Long tongSoDonTreHanDaXL = 0L;
//			Long tongSo = 0L;
//			
//			Long tongDonDungHanDangXL = 0L;
//			Long tongDonTreHanDangXL = 0L;
//			Long tongDonDungHanDaXL = 0L;
//			Long tongDonTreHanDaXL = 0L;
//			Long tongSoDangDaXL = 0L;
//			BooleanExpression predDSAllDons = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDons(tuNgay, denNgay, month, year, xuLyRepo, repo, giaiQuyetDonRepo);
//			for (CoQuanQuanLy cq : donVis) {
//				BooleanExpression predDSDons = predDSAllDons;
//				BooleanExpression predDSXLDons = predDSAllDons;
//				mapDonVi.put("id", cq.getId());
//				mapDonVi.put("tenDonVi", cq.getTen());
//				mapTheoDoiGS.put("donVi", mapDonVi);
//				if (StringUtils.isNotBlank(quyTrinh)) {
//					processType = ProcessTypeEnum.valueOf(quyTrinh);
//					predDSDons = predDSDons.and(QDon.don.processType.eq(processType));
//					if (processType.equals(ProcessTypeEnum.GIAI_QUYET_DON)) { 
//						predDSDons = predDSDons.or(QDon.don.thongTinGiaiQuyetDon.ngayBatDauGiaiQuyet.isNotNull());
//						// giai quyet don
//						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViGQD(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);
//						// dang giai quyet
//						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isDungHan, trangThaiDangGQ);
//						tongDonDungHanDangXL += tongSoDonDungHanDangXL;
//						mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
//						
//						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isTreHan, trangThaiDangGQ);
//						tongDonTreHanDangXL += tongSoDonTreHanDangXL;
//						mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
//						mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
//						
//						//da giai quyet
//						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isDungHan, trangThaiDaGQ);
//						tongDonDungHanDaXL += tongSoDonDungHanDaXL;
//						mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
//						
//						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiGQD(predAll, repo, isTreHan, trangThaiDaGQ);
//						tongDonTreHanDaXL += tongSoDonTreHanDaXL;
//						mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
//						mapTheoDoiGS.put("daXuLy", mapDaXuLy);
//						
//						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
//						tongSoDangDaXL += tongSo;
//						mapTheoDoiGS.put("tongSo", tongSo);
//					}
//					
//					if (processType.equals(ProcessTypeEnum.THAM_TRA_XAC_MINH)) { 
//						//tham tra xac minh
//						predDSDons = predDSDons.or(QDon.don.thongTinGiaiQuyetDon.ngayBatDauTTXM.isNotNull());
//
//						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViTTXM(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);
//						
//						// dang giai quyet
//						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isDungHan, trangThaiDangGQ);
//						tongDonDungHanDangXL += tongSoDonDungHanDangXL;
//						mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
//						
//						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isTreHan, trangThaiDangGQ);
//						tongDonTreHanDangXL += tongSoDonTreHanDangXL;
//						mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
//						mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
//						
//						//da giai quyet
//						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isDungHan, trangThaiDaGQ);
//						tongDonDungHanDaXL += tongSoDonDungHanDaXL;
//						mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
//						
//						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiTTXM(predAll, repo, isTreHan, trangThaiDaGQ);
//						tongDonTreHanDaXL += tongSoDonTreHanDaXL;
//						mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
//						mapTheoDoiGS.put("daXuLy", mapDaXuLy);
//						
//						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
//						tongSoDangDaXL += tongSo;
//						mapTheoDoiGS.put("tongSo", tongSo);
//					}
//					
//					if (processType.equals(ProcessTypeEnum.KIEM_TRA_DE_XUAT)) {
//						predDSDons = predDSDons.or(QDon.don.processType.isNull().and(QDon.don.trangThaiKTDX.isNotNull()));
//						//kiem tra de xuat
//						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViKTDX(predDSDons, cq.getId(), giaiQuyetDonRepo, repo);
//
//						// dang giai quyet
//						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isDungHan, trangThaiDangGQ);
//						tongDonDungHanDangXL += tongSoDonDungHanDangXL;
//						mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
//						
//						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isTreHan, trangThaiDangGQ);
//						tongDonTreHanDangXL += tongSoDonTreHanDangXL;
//						mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
//						mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
//						
//						//da giai quyet
//						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isDungHan, trangThaiDaGQ);
//						tongDonDungHanDaXL += tongSoDonDungHanDaXL;
//						mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
//						
//						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiKTDX(predAll, repo, isTreHan, trangThaiDaGQ);
//						tongDonTreHanDaXL += tongSoDonTreHanDaXL;
//						mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
//						mapTheoDoiGS.put("daXuLy", mapDaXuLy);
//						
//						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
//						tongSoDangDaXL += tongSo;
//						mapTheoDoiGS.put("tongSo", tongSo);
//					}
//					
//					if (processType.equals(ProcessTypeEnum.XU_LY_DON)) {
//						predDSXLDons = predDSXLDons.and(QDon.don.thanhLapDon.isTrue());
//
//						BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViXLD(predDSXLDons, cq.getId(), xuLyRepo, repo);
//						
//						//Dang xu ly
//						tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDangXL);
//						tongDonDungHanDangXL += tongSoDonDungHanDangXL;
//						mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
//						
//						tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDangXL);
//						tongDonTreHanDangXL += tongSoDonTreHanDangXL;
//						mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
//						mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
//						
//						//Da xu ly
//						tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDaXL);
//						tongDonDungHanDaXL += tongSoDonDungHanDaXL;
//						mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
//						
//						tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDaXL);
//						tongDonTreHanDaXL += tongSoDonTreHanDaXL;
//						mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
//						mapTheoDoiGS.put("daXuLy", mapDaXuLy);
//						
//						tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
//						tongSoDangDaXL += tongSo;
//						mapTheoDoiGS.put("tongSo", tongSo);
//					}
//				} else { 
//					//xu ly don
//					predDSXLDons = predDSXLDons.and(QDon.don.thanhLapDon.isTrue());
//					
//					BooleanExpression predAll = (BooleanExpression) theoDoiGiamSatService.predicateFindDanhSachDonsTheoDonViXLD(predDSXLDons, cq.getId(), xuLyRepo, repo);
//					//Dang xu ly
//					tongSoDonDungHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDangXL);
//					tongDonDungHanDangXL += tongSoDonDungHanDangXL;
//					mapDangXuLy.put("dungHan", tongSoDonDungHanDangXL);
//					
//					tongSoDonTreHanDangXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDangXL);
//					tongDonTreHanDangXL += tongSoDonTreHanDangXL;
//					mapDangXuLy.put("treHan", tongSoDonTreHanDangXL);
//					mapTheoDoiGS.put("dangXuLy", mapDangXuLy);
//					
//					//Da xu ly
//					tongSoDonDungHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isDungHan, trangThaiDaXL);
//					tongDonDungHanDaXL += tongSoDonDungHanDaXL;
//					mapDaXuLy.put("dungHan", tongSoDonDungHanDaXL);
//					
//					tongSoDonTreHanDaXL = theoDoiGiamSatService.getTongSoDonDungHanTreHanByTrangThaiXLD(predAll, repo, isTreHan, trangThaiDaXL);
//					tongDonTreHanDaXL += tongSoDonTreHanDaXL;
//					mapDaXuLy.put("treHan", tongSoDonTreHanDaXL);
//					mapTheoDoiGS.put("daXuLy", mapDaXuLy);
//					
//					tongSo = tongSoDonDungHanDangXL + tongSoDonTreHanDangXL +tongSoDonDungHanDaXL + tongSoDonTreHanDaXL;
//					tongSoDangDaXL += tongSo;
//					mapTheoDoiGS.put("tongSo", tongSo);
//				}
//				coQuans.add(mapTheoDoiGS);
//				mapTheoDoiGS = new HashMap<>();
//				mapDonVi = new HashMap<>();
//				mapDangXuLy = new HashMap<>();
//				mapDaXuLy = new HashMap<>();
//			}
//			map.put("tongDonDungHanDangXL", tongDonDungHanDangXL);
//			map.put("tongDonTreHanDangXL", tongDonTreHanDangXL);
//			map.put("tongDonDungHanDaXL", tongDonDungHanDaXL);
//			map.put("tongDonTreHanDaXL", tongDonTreHanDaXL);
//			map.put("tongDonDangDaXL", tongSoDangDaXL);
//			
//			map.put("donVis", coQuans);
//			
//			//ExcelUtil.exportTheoDoiGiamSatTheoDonVi(response, "DanhSachTheoDoiGiamSatTheoDonVi", "sheetName", maSos, tuNgay, denNgay, "TÌNH HÌNH XỬ LÝ ĐƠN TRÊN ĐỊA BÀN " + donVi.getTen().toUpperCase(), quyTrinh);
//		} catch (Exception e) {
//			return Utils.responseInternalServerErrors(e);
//		}
//	}
}
