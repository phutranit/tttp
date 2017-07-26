package vn.greenglobal.tttp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

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
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiTiepDanEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.LinhVucDonThuRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.service.CoQuanQuanLyService;
import vn.greenglobal.tttp.service.LinhVucDonThuService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.service.ThongKeBaoCaoTongHopKQGQDService;
import vn.greenglobal.tttp.service.ThongKeBaoCaoTongHopKQTCDService;
import vn.greenglobal.tttp.util.ExcelUtil;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "thongKeBaoCaos", description = "Thống kê báo cáo")
public class ThongKeBaoCaoController extends TttpController<Don> {
	
	@Autowired
	private ThamSoRepository repoThamSo;
	
	@Autowired
	private CoQuanQuanLyService coQuanQuanLyService;
	
	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepo;
	
	@Autowired
	private LinhVucDonThuRepository linhVucDonThuRepo;
	
	@Autowired
	private LinhVucDonThuService linhVucDonThuService;
	
	@Autowired
	private ThamSoService thamSoService;
	
	@Autowired
	private ThongKeBaoCaoTongHopKQTCDService thongKeBaoCaoTongHopKQTCDService;
	
	@Autowired
	private ThongKeBaoCaoTongHopKQGQDService thongKeBaoCaoTongHopKQGQDService;
	
	public ThongKeBaoCaoController(BaseRepository<Don, Long> repo) {
		super(repo);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaTiepCongDan")
	@ApiOperation(value = "Tổng hợp kết quả tiếp công dân", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaTiepCongDan(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			PersistentEntityResourceAssembler eass) {
		try {
			
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			Long donViXuLyXLD = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			Long coQuanQuanLyId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());
			CoQuanQuanLy coQuanQuanLy = coQuanQuanLyRepo.findOne(coQuanQuanLyId);
			Long capCoQuanQuanLyId = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("capCoQuanQuanLyId").toString());
			ThamSo thamSoCCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLUBNDSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoCCQQLUBNDPhuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLPhuongXaTT = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLChiCuc = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_CHI_CUC"));
			
			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()));
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString()));
			capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString()));
			
			
			if (donViId != null && donViId > 0) {
				CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViId);
				if (donVi != null) {
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindOne(donVi.getId())));
				}
			} else { 
				list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTHTKBC(
						Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds,
						"CQQL_UBNDTP_DA_NANG")));
			}
			donVis.addAll(list);
			
			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQTCDService.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			LinhVucDonThu linhVucHanhChinh = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhap = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhung = linhVucDonThuRepo.findOne(37L);
			LinhVucDonThu linhVucCheDoChinhSach = linhVucDonThuRepo.findOne(3L);
			LinhVucDonThu linhVucDatDaiNhaCuaVaTaiSan = linhVucDonThuRepo.findOne(4L);
			LinhVucDonThu linhVucKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucChinhTriVanHoaXaHoiKhac = linhVucDonThuRepo.findOne(53L);
			
			List<LinhVucDonThu> linhVucTranhChapVeDatDais = new ArrayList<LinhVucDonThu>();
			linhVucTranhChapVeDatDais.addAll(linhVucDonThuService.getDanhSachLinhVucDonThusByCha(linhVucDatDaiNhaCuaVaTaiSan));
			List<LinhVucDonThu> linhVucCheDoChinhSachs = new ArrayList<LinhVucDonThu>();
			linhVucCheDoChinhSachs.addAll(linhVucDonThuService.getDanhSachLinhVucDonThusByCha(linhVucCheDoChinhSach));
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSTCDDonVi = predAllDSTCD;
				predAllDSTCDDonVi = predAllDSTCDDonVi.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(cq.getId())
						.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(cq.getId()))
						.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(cq.getId())));
				
				BooleanExpression predAllDSTCDThuongXuyen = predAllDSTCDDonVi;
				predAllDSTCDThuongXuyen = predAllDSTCDThuongXuyen.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.THUONG_XUYEN));
				
				BooleanExpression predAllDSTCDLanhDao = predAllDSTCDDonVi;
				predAllDSTCDLanhDao = predAllDSTCDLanhDao.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DINH_KY)
						.or(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DOT_XUAT)));
				
				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("donVi", mapDonVi);
				
				// tiep cong dan thuong xuyen
				//luoc - 1
				mapMaSo.put("tiepCongDanThuongXuyenLuot", thongKeBaoCaoTongHopKQTCDService.getTongSoLuocTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen));
				
				//nguoi - 2
				mapMaSo.put("tiepCongDanThuongXuyenNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, 
						true, false));
				
				//vu viec cu - 3
				mapMaSo.put("tiepCongDanThuongXuyenVuViecCu", "");
				
				//vu viec moi phat sinh - 4
				mapMaSo.put("tiepCongDanThuongXuyenVuViecMoiPhatSinh", "");
				
				//doan dong nguoi
				//so doan - 5
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiSoDoan", thongKeBaoCaoTongHopKQTCDService.getTongSoDoanDongNguoiTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true));
				
				//nguoi - 6
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiSoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, 
						false, true));
				
				//doan dong nguoi - vu viec
				//cu - 7
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiVuViecCu", "");
				
				//moi phat sinh - 8
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh", "");
				
				//tiep cong dan dinh ky dot xuat cua lanh dao
				//luoc - 9
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoLuot", thongKeBaoCaoTongHopKQTCDService.getTongSoLuocTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao));
				
				//nguoi - 10
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao,
						true, false));
				
				//tiep cong dan cua lanh dao - vu viec
				//cu - 11
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu", "");
				
				//moi phat sinh - 12
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh", "");
				
				//tiep cong dan cua lanh dao - doan dong nguoi
				//so doan - 13
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan", thongKeBaoCaoTongHopKQTCDService.getTongSoDoanDongNguoiTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true));
				
				//so nguoi - 14
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao,
						false, true));
				
				//tiep cong dan cua lanh dao - doan dong nguoi - vu viec
				//cu - 15
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu", "");
				
				//moi phat sinh - 16
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh", "");
				
				//Noi dung tiep cong dan
				//don khieu nai - linh vuc hanh chinh
				//veTranhChap - 17
				mapMaSo.put("linhVucVeTranhChap", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCon(predAllDSTCDDonVi, linhVucTranhChapVeDatDais));
								
				//veChinhSach - 18
				mapMaSo.put("linhVucVeChinhSach", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVucChiTietCha(predAllDSTCDDonVi, linhVucCheDoChinhSach));

				//veNhaCuaVaTaiSan - 19
				mapMaSo.put("linhVucVeNhaCuaVaTaiSan", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVucChiTietCha(predAllDSTCDDonVi, linhVucDatDaiNhaCuaVaTaiSan));
										
				//veCheDoCC,VC - 20
				mapMaSo.put("linhVucVeCheDoCCVC", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCon(predAllDSTCDDonVi, linhVucCheDoChinhSachs));
				
				//linhVucTuPhap - 21
				mapMaSo.put("linhVucTuPhap", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi, linhVucKhieuNaiTuPhap));

				//linhVucChinhTriVanHoaXaHoi - 22
				mapMaSo.put("linhVucChinhTriVanHoaXaHoiKhac", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi, linhVucChinhTriVanHoaXaHoiKhac));
				
				//don to cao
				//linhVucHanhChinh - 23
				mapMaSo.put("linhVucHanhChinh", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucHanhChinhVaTuPhap(predAllDSTCDDonVi, linhVucHanhChinh));
				
				//linhVucHanhChinh - 24
				mapMaSo.put("linhVucTuPhap", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucHanhChinhVaTuPhap(predAllDSTCDDonVi, linhVucTuPhap));
				
				//linhVucThamNhung - 25
				mapMaSo.put("linhVucThamNhung", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucThamNhung(predAllDSTCDDonVi, linhVucThamNhung));
				
				//don kien nghi phan anh
				//kienNghiPhanAnh - 26
				mapMaSo.put("kienNghiPhanAnh", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKienNghiPhanAnh(predAllDSTCDDonVi));
				
				//ket qua tiep dan
				//chua duoc giai quyet - 27
				mapMaSo.put("chuaDuocGiaiQuyet", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonChuaChotHuongXuLyXLD(predAllDSTCDDonVi));

				//da duoc giai quyet
				//chua co quyet dinh giai quyet - 28
				mapMaSo.put("chuaCoQuyetDinhGiaiQuyet", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonChuaCoQuyetDinhGiaiQuyet(predAllDSTCDDonVi));
				
				//da co quyet dinh giai quyet - 29
				mapMaSo.put("daCoQuyetDinhGiaiQuyet", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonDaCoQuyetDinhGiaiQuyet(predAllDSTCDDonVi));
				
				//da co ban an cua toa - 30
				mapMaSo.put("daCoBanAnCuaToa", "");
				
				//da co ban an cua toa - 31
				mapMaSo.put("ghiChu", "");
				
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
				mapDonVi = new HashMap<String, Object>();
			}
			map.put("maSos", maSos);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaGiaiQuyetDonKhieuNai")
	@ApiOperation(value = "Tổng hợp kết quả kết quả giải quyết khiếu nại", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaGiaiQuyetDonKhieuNai(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			PersistentEntityResourceAssembler eass) {
		try {
			
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}			
			ThamSo thamSoCCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCCQQLUBNDSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			
			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
			
			
			if (donViId != null && donViId > 0) {
				CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViId);
				if (donVi != null) {
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindOne(donVi.getId())));
				}
			} else { 
				list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTHTKBC(
						Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds,
						"CQQL_UBNDTP_DA_NANG")));
			}
			donVis.addAll(list);
			
			BooleanExpression predAllDSDon = (BooleanExpression) thongKeBaoCaoTongHopKQGQDService.predicateFindAllGQD(loaiKy, quy, year, month, tuNgay, denNgay);
			predAllDSDon = predAllDSDon.and(QDon.don.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
					.and(QDon.don.thanhLapDon.eq(true))
					.and(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.DE_XUAT_THU_LY));
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSGQDDonVi = predAllDSDon;
				predAllDSGQDDonVi = predAllDSGQDDonVi.and(QDon.don.donViXuLyGiaiQuyet.id.eq(cq.getId())
						.or(QDon.don.donViXuLyGiaiQuyet.cha.id.eq(cq.getId()))
						.or(QDon.don.donViXuLyGiaiQuyet.cha.cha.id.eq(cq.getId())));				
				
				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("donVi", mapDonVi);	
				
				//1 - Tong so don khieu nai
				mapMaSo.put("tongSoDonKhieuNai", thongKeBaoCaoTongHopKQGQDService.getTongSoDonKhieuNai(predAllDSGQDDonVi));
				
				//2 - Don nhan trong ky bao cao
				mapMaSo.put("donNhanTrongKyBaoCao", 0L);
				
				//3 - Don ton ky truoc chuyen sang
				mapMaSo.put("donTonKyTruocChuyenSang", 0L);
				
				//4 - Tong so vu viec
				mapMaSo.put("tongSoVuViec", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNai(predAllDSGQDDonVi));
				
				//5 - So don thuoc tham quyen
				mapMaSo.put("soDonThuocThamQuyen", thongKeBaoCaoTongHopKQGQDService.getTongSoDonKhieuNaiThuocThamQuyen(predAllDSGQDDonVi));
				
				//6 - So vu viec thuoc tham quyen
				mapMaSo.put("soVuViecThuocThamQuyen",thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiThuocThamQuyen(predAllDSGQDDonVi));
				
				//7 - So vu viec giai quyet bang QD hanh chinh
				mapMaSo.put("soVuViecGiaiQuyetBangQDHanhChinh", thongKeBaoCaoTongHopKQGQDService.getTongSoDonKhieuNaiCoQuyetDinh(predAllDSGQDDonVi));
				
				//8 - So vu viec rut don thong qua giai thich, thuyet phuc
				mapMaSo.put("soVuViecRutDonThongQuaGiaiThichThuyetPhuc", thongKeBaoCaoTongHopKQGQDService.getTongSoDonKhieuNaiDinhChi(predAllDSGQDDonVi));
				
				//9 - Khieu nai dung
				mapMaSo.put("khieuNaiDung", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiDung(predAllDSGQDDonVi));
				
				//10 - Khieu nai sai
				mapMaSo.put("khieuNaiSai", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiSai(predAllDSGQDDonVi));
				
				//11 - Khieu nai dung 1 phan
				mapMaSo.put("khieuNaiDungMotPhan",thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiDungMotPhan(predAllDSGQDDonVi));
				
				//12 - Giai quyet lan 1
				mapMaSo.put("giaiQuyetLan1", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiGiaiQuyetLan1(predAllDSGQDDonVi));
				
				//13 - Cong nhan quyet dinh giai quyet lan 1
				mapMaSo.put("congNhanQuyetDinhGiaiQuyetLan1", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiGiaiQuyetLan2CongNhan(predAllDSGQDDonVi));
				
				//14 - Huy, sua quyet dinh giai quyet lan 1
				mapMaSo.put("huySuaQuyetDinhGiaiQuyetLan1", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiGiaiQuyetLan2HuySua(predAllDSGQDDonVi));
				
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
				mapDonVi = new HashMap<String, Object>();
			}
			map.put("maSos", maSos);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaTiepCongDan/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp báo cáo tiếp công dân", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcel(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId
			) throws IOException {
		
		try {
			//CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViXuLyXLD);
			//CoQuanQuanLy coQuanQuanLy = coQuanQuanLyRepo.findOne(coQuanQuanLyId);
			ThamSo thamSoCCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLUBNDSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoCCQQLUBNDPhuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLPhuongXaTT = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLChiCuc = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_CHI_CUC"));
			
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()));
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString()));
			capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString()));
			
			if (donViId != null && donViId > 0) {
				CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViId);
				if (donVi != null) {
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindOne(donVi.getId())));
				}
			} else { 
				list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTHTKBC(
						Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds,
						"CQQL_UBNDTP_DA_NANG")));
			}
			donVis.addAll(list);
			
			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQTCDService.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			LinhVucDonThu linhVucHanhChinh = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhap = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhung = linhVucDonThuRepo.findOne(37L);
			LinhVucDonThu linhVucCheDoChinhSach = linhVucDonThuRepo.findOne(3L);
			LinhVucDonThu linhVucDatDaiNhaCuaVaTaiSan = linhVucDonThuRepo.findOne(4L);
			LinhVucDonThu linhVucKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucChinhTriVanHoaXaHoiKhac = linhVucDonThuRepo.findOne(53L);
			
			List<LinhVucDonThu> linhVucTranhChapVeDatDais = new ArrayList<LinhVucDonThu>();
			linhVucTranhChapVeDatDais.addAll(linhVucDonThuService.getDanhSachLinhVucDonThusByCha(linhVucDatDaiNhaCuaVaTaiSan));
			List<LinhVucDonThu> linhVucCheDoChinhSachs = new ArrayList<LinhVucDonThu>();
			linhVucCheDoChinhSachs.addAll(linhVucDonThuService.getDanhSachLinhVucDonThusByCha(linhVucCheDoChinhSach));
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSTCDDonVi = predAllDSTCD;
				predAllDSTCDDonVi = predAllDSTCDDonVi.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(cq.getId())
						.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(cq.getId()))
						.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(cq.getId())));
				
				BooleanExpression predAllDSTCDThuongXuyen = predAllDSTCDDonVi;
				predAllDSTCDThuongXuyen = predAllDSTCDThuongXuyen.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.THUONG_XUYEN));
				
				BooleanExpression predAllDSTCDLanhDao = predAllDSTCDDonVi;
				predAllDSTCDLanhDao = predAllDSTCDLanhDao.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DINH_KY)
						.or(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DOT_XUAT)));
						
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("tenDonVi", cq.getTen());
				
				// tiep cong dan thuong xuyen
				//luoc - 1
				mapMaSo.put("tiepCongDanThuongXuyenLuot", thongKeBaoCaoTongHopKQTCDService.getTongSoLuocTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen));
				
				//nguoi - 2
				mapMaSo.put("tiepCongDanThuongXuyenNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, 
						true, false));
				
				//vu viec cu - 3
				mapMaSo.put("tiepCongDanThuongXuyenVuViecCu", 0);
				
				//vu viec moi phat sinh - 4
				mapMaSo.put("tiepCongDanThuongXuyenVuViecMoiPhatSinh", 0);
				
				//doan dong nguoi
				//so doan - 5
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiSoDoan", thongKeBaoCaoTongHopKQTCDService.getTongSoDoanDongNguoiTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true));
				
				//nguoi - 6
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiSoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, 
						false, true));
				
				//doan dong nguoi - vu viec
				//cu - 7
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiVuViecCu", 0);
				
				//moi phat sinh - 8
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh", 0);
				
				//tiep cong dan dinh ky dot xuat cua lanh dao
				//luoc - 9
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoLuot", thongKeBaoCaoTongHopKQTCDService.getTongSoLuocTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao));
				
				//nguoi - 10
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao,
						true, false));
				
				//tiep cong dan cua lanh dao - vu viec
				//cu - 11
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu", 0);
				
				//moi phat sinh - 12
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh", 0);
				
				//tiep cong dan cua lanh dao - doan dong nguoi
				//so doan - 13
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan", thongKeBaoCaoTongHopKQTCDService.getTongSoDoanDongNguoiTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true));
				
				//so nguoi - 14
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao,
						false, true));
				
				//tiep cong dan cua lanh dao - doan dong nguoi - vu viec
				//cu - 15
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu", 0);
				
				//moi phat sinh - 16
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh", 0);
				
				//Noi dung tiep cong dan
				//don khieu nai - linh vuc hanh chinh
				//veTranhChap - 17
				mapMaSo.put("linhVucKhieuNaiVeTranhChap", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCon(predAllDSTCDDonVi, linhVucTranhChapVeDatDais));
								
				//veChinhSach - 18
				mapMaSo.put("linhVucKhieuNaiVeChinhSach", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVucChiTietCha(predAllDSTCDDonVi, linhVucCheDoChinhSach));

				//veNhaCuaVaTaiSan - 19
				mapMaSo.put("linhVucKhieuNaiVeNhaCuaVaTaiSan", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVucChiTietCha(predAllDSTCDDonVi, linhVucDatDaiNhaCuaVaTaiSan));
										
				//veCheDoCC,VC - 20
				mapMaSo.put("linhVucKhieuNaiVeCheDoCCVC", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCon(predAllDSTCDDonVi, linhVucCheDoChinhSachs));
				
				//linhVucTuPhap - 21
				mapMaSo.put("linhVucKhieuNaiTuPhap", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi, linhVucKhieuNaiTuPhap));

				//linhVucChinhTriVanHoaXaHoi - 22
				mapMaSo.put("linhVucKhieuNaiChinhTriVanHoaXaHoiKhac", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi, linhVucChinhTriVanHoaXaHoiKhac));
				
				//don to cao
				//linhVucHanhChinh - 23
				mapMaSo.put("linhVucToCaoHanhChinh", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucHanhChinhVaTuPhap(predAllDSTCDDonVi, linhVucHanhChinh));
				
				//linhVucHanhChinh - 24
				mapMaSo.put("linhVucToCaoTuPhap", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucHanhChinhVaTuPhap(predAllDSTCDDonVi, linhVucTuPhap));
				
				//linhVucThamNhung - 25
				mapMaSo.put("linhVucToCaoThamNhung", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucThamNhung(predAllDSTCDDonVi, linhVucThamNhung));
				
				//don kien nghi phan anh
				//kienNghiPhanAnh - 26
				mapMaSo.put("kienNghiPhanAnh", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKienNghiPhanAnh(predAllDSTCDDonVi));
				
				//ket qua tiep dan
				//chua duoc giai quyet - 27
				mapMaSo.put("chuaDuocGiaiQuyet", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonChuaChotHuongXuLyXLD(predAllDSTCDDonVi));

				//da duoc giai quyet
				//chua co quyet dinh giai quyet - 28
				mapMaSo.put("chuaCoQuyetDinhGiaiQuyet", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonChuaCoQuyetDinhGiaiQuyet(predAllDSTCDDonVi));
				
				//da co quyet dinh giai quyet - 29
				mapMaSo.put("daCoQuyetDinhGiaiQuyet", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonDaCoQuyetDinhGiaiQuyet(predAllDSTCDDonVi));
				
				//da co ban an cua toa - 30
				mapMaSo.put("daCoBanAnCuaToa", 0);
				
				//da co ban an cua toa - 31
				mapMaSo.put("ghiChu", 0);
				
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopBaoCaoTiepCongDan(response, "DanhSachTongHopThongKeBaoCaoTiepCongDan", "sheetName", maSos, tuNgay, denNgay, "Danh sách tổng hợp thống kê báo cáo tiếp công dân");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaXuLyDonThu/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp báo cáo xử lý đơn thư", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExceluLyDonThu(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId
			) throws IOException {
		
		try {
			//CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViXuLyXLD);
			//CoQuanQuanLy coQuanQuanLy = coQuanQuanLyRepo.findOne(coQuanQuanLyId);
			ThamSo thamSoCCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCQQLThanhTraThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_THANH_TRA_THANH_PHO"));
			ThamSo thamSoCCQQLUBNDSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo thamSoCCQQLUBNDQuanHuyen = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_QUAN_HUYEN"));
			ThamSo thamSoCCQQLUBNDPhuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLPhuongXaTT = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));
			ThamSo thamSoCCQQLChiCuc = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_CHI_CUC"));
			
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()));
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString()));
			capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
			
			if (donViId != null && donViId > 0) {
				CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(donViId);
				if (donVi != null) {
					list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindOne(donVi.getId())));
				}
			} else { 
				list.addAll((List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTHTKBC(
						Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds,
						"CQQL_UBNDTP_DA_NANG")));
			}
			donVis.addAll(list);
			
			
			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQTCDService.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			LinhVucDonThu linhVucHanhChinh = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhap = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhung = linhVucDonThuRepo.findOne(37L);
			LinhVucDonThu linhVucCheDoChinhSach = linhVucDonThuRepo.findOne(3L);
			LinhVucDonThu linhVucDatDaiNhaCuaVaTaiSan = linhVucDonThuRepo.findOne(4L);
			LinhVucDonThu linhVucKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucChinhTriVanHoaXaHoiKhac = linhVucDonThuRepo.findOne(53L);
			
			List<LinhVucDonThu> linhVucTranhChapVeDatDais = new ArrayList<LinhVucDonThu>();
			linhVucTranhChapVeDatDais.addAll(linhVucDonThuService.getDanhSachLinhVucDonThusByCha(linhVucDatDaiNhaCuaVaTaiSan));
			List<LinhVucDonThu> linhVucCheDoChinhSachs = new ArrayList<LinhVucDonThu>();
			linhVucCheDoChinhSachs.addAll(linhVucDonThuService.getDanhSachLinhVucDonThusByCha(linhVucCheDoChinhSach));
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSTCDDonVi = predAllDSTCD;
				predAllDSTCDDonVi = predAllDSTCDDonVi.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(cq.getId())
						.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(cq.getId()))
						.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(cq.getId())));
				
				BooleanExpression predAllDSTCDThuongXuyen = predAllDSTCDDonVi;
				predAllDSTCDThuongXuyen = predAllDSTCDThuongXuyen.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.THUONG_XUYEN));
				
				BooleanExpression predAllDSTCDLanhDao = predAllDSTCDDonVi;
				predAllDSTCDLanhDao = predAllDSTCDLanhDao.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DINH_KY)
						.or(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DOT_XUAT)));
						
				mapMaSo = new HashMap<String, Object>();
				
				mapMaSo.put("1", 1);
				mapMaSo.put("2", 2);
				mapMaSo.put("3", 3);
				mapMaSo.put("4", 4);
				mapMaSo.put("5", 5);
				mapMaSo.put("6", 6);
				mapMaSo.put("7", 7);
				mapMaSo.put("8", 8);
				mapMaSo.put("9", 9);
				mapMaSo.put("10", 0);
				mapMaSo.put("11", 11);
				mapMaSo.put("12", 12);
				mapMaSo.put("13", 13);
				mapMaSo.put("14", 14);
				mapMaSo.put("15", 15);
				mapMaSo.put("16", 16);
				mapMaSo.put("17", 17);
				mapMaSo.put("18", 18);
				mapMaSo.put("19", 19);
				mapMaSo.put("20", 20);
				mapMaSo.put("21", 21);
				mapMaSo.put("22", 22);
				mapMaSo.put("23", 23);
				mapMaSo.put("24", 24);
				mapMaSo.put("25", 25);
				mapMaSo.put("26", 26);
				mapMaSo.put("27", 27);
				mapMaSo.put("28", 28);
				mapMaSo.put("29", 29);
				mapMaSo.put("30", 30);
				mapMaSo.put("31", 31);
				mapMaSo.put("32", 32);
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopBaoCaoXuLyDonThu(response, "DanhSachTongHopThongKeBaoCaoXuLyDonThu", "sheetName", maSos, tuNgay, denNgay, "Danh sách tổng hợp thống kê báo cáo xử lý đơn thư");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
}
