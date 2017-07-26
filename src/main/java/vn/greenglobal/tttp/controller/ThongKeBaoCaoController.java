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
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.ThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.LinhVucDonThuRepository;
import vn.greenglobal.tttp.repository.ThamQuyenGiaiQuyetRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.service.CoQuanQuanLyService;
import vn.greenglobal.tttp.service.LinhVucDonThuService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.service.ThongKeBaoCaoTongHopKQGQDService;
import vn.greenglobal.tttp.service.ThongKeBaoCaoTongHopKQTCDService;
import vn.greenglobal.tttp.service.ThongKeBaoCaoTongHopKQXLDService;
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
	private ThamQuyenGiaiQuyetRepository thamQuyenGiaiQuyetRepo;
	
	@Autowired
	private LinhVucDonThuService linhVucDonThuService;
	
	@Autowired
	private ThamSoService thamSoService;
	
	@Autowired
	private ThongKeBaoCaoTongHopKQTCDService thongKeBaoCaoTongHopKQTCDService;
	
	@Autowired
	private ThongKeBaoCaoTongHopKQXLDService thongKeBaoCaoTongHopKQXLDService;

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
				mapMaSo.put("1tiepCongDanThuongXuyenLuot", thongKeBaoCaoTongHopKQTCDService.getTongSoLuocTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen));
				
				//nguoi - 2
				mapMaSo.put("2tiepCongDanThuongXuyenNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, 
						true, false));
				
				//vu viec cu - 3
				mapMaSo.put("3tiepCongDanThuongXuyenVuViecCu", "");
				
				//vu viec moi phat sinh - 4
				mapMaSo.put("4tiepCongDanThuongXuyenVuViecMoiPhatSinh", "");
				
				//doan dong nguoi
				//so doan - 5
				mapMaSo.put("5tiepCongDanThuongXuyenDoanDongNguoiSoDoan", thongKeBaoCaoTongHopKQTCDService.getTongSoDoanDongNguoiTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true));
				
				//nguoi - 6
				mapMaSo.put("6tiepCongDanThuongXuyenDoanDongNguoiSoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, 
						false, true));
				
				//doan dong nguoi - vu viec
				//cu - 7
				mapMaSo.put("7tiepCongDanThuongXuyenDoanDongNguoiVuViecCu", "");
				
				//moi phat sinh - 8
				mapMaSo.put("8tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh", "");
				
				//tiep cong dan dinh ky dot xuat cua lanh dao
				//luoc - 9
				mapMaSo.put("9tiepCongDanDinhKyDotXuatCuaLanhDaoLuot", thongKeBaoCaoTongHopKQTCDService.getTongSoLuocTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao));
				
				//nguoi - 10
				mapMaSo.put("10tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao,
						true, false));
				
				//tiep cong dan cua lanh dao - vu viec
				//cu - 11
				mapMaSo.put("11tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu", "");
				
				//moi phat sinh - 12
				mapMaSo.put("12tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh", "");
				
				//tiep cong dan cua lanh dao - doan dong nguoi
				//so doan - 13
				mapMaSo.put("13tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan", thongKeBaoCaoTongHopKQTCDService.getTongSoDoanDongNguoiTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true));
				
				//so nguoi - 14
				mapMaSo.put("14tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao,
						false, true));
				
				//tiep cong dan cua lanh dao - doan dong nguoi - vu viec
				//cu - 15
				mapMaSo.put("15tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu", "");
				
				//moi phat sinh - 16
				mapMaSo.put("16tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh", "");
				
				//Noi dung tiep cong dan
				//don khieu nai - linh vuc hanh chinh
				//veTranhChap - 17
				mapMaSo.put("17linhVucVeTranhChap", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCon(predAllDSTCDDonVi, linhVucTranhChapVeDatDais));
								
				//veChinhSach - 18
				mapMaSo.put("18linhVucVeChinhSach", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVucChiTietCha(predAllDSTCDDonVi, linhVucCheDoChinhSach));

				//veNhaCuaVaTaiSan - 19
				mapMaSo.put("19linhVucVeNhaCuaVaTaiSan", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVucChiTietCha(predAllDSTCDDonVi, linhVucDatDaiNhaCuaVaTaiSan));
										
				//veCheDoCC,VC - 20
				mapMaSo.put("20linhVucVeCheDoCCVC", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCon(predAllDSTCDDonVi, linhVucCheDoChinhSachs));
				
				//linhVucTuPhap - 21
				mapMaSo.put("21linhVucTuPhap", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi, linhVucKhieuNaiTuPhap));

				//linhVucChinhTriVanHoaXaHoi - 22
				mapMaSo.put("22linhVucChinhTriVanHoaXaHoiKhac", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi, linhVucChinhTriVanHoaXaHoiKhac));
				
				//don to cao
				//linhVucHanhChinh - 23
				mapMaSo.put("23linhVucHanhChinh", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucHanhChinhVaTuPhap(predAllDSTCDDonVi, linhVucHanhChinh));
				
				//linhVucHanhChinh - 24
				mapMaSo.put("24linhVucTuPhap", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucHanhChinhVaTuPhap(predAllDSTCDDonVi, linhVucTuPhap));
				
				//linhVucThamNhung - 25
				mapMaSo.put("25linhVucThamNhung", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucThamNhung(predAllDSTCDDonVi, linhVucThamNhung));
				
				//don kien nghi phan anh
				//kienNghiPhanAnh - 26
				mapMaSo.put("26kienNghiPhanAnh", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKienNghiPhanAnh(predAllDSTCDDonVi));
				
				//ket qua tiep dan
				//chua duoc giai quyet - 27
				mapMaSo.put("27chuaDuocGiaiQuyet", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonChuaChotHuongXuLyXLD(predAllDSTCDDonVi));

				//da duoc giai quyet
				//chua co quyet dinh giai quyet - 28
				mapMaSo.put("28chuaCoQuyetDinhGiaiQuyet", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonChuaCoQuyetDinhGiaiQuyet(predAllDSTCDDonVi));
				
				//da co quyet dinh giai quyet - 29
				mapMaSo.put("29daCoQuyetDinhGiaiQuyet", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonDaCoQuyetDinhGiaiQuyet(predAllDSTCDDonVi));
				
				//da co ban an cua toa - 30
				mapMaSo.put("30daCoBanAnCuaToa", "");
				
				//da co ban an cua toa - 31
				mapMaSo.put("31ghiChu", "");
				
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
				mapMaSo.put("1tongSoDonKhieuNai", thongKeBaoCaoTongHopKQGQDService.getTongSoDon(predAllDSGQDDonVi));
				
				//2 - Don nhan trong ky bao cao
				mapMaSo.put("2donNhanTrongKyBaoCao", 0L);
				
				//3 - Don ton ky truoc chuyen sang
				mapMaSo.put("3donTonKyTruocChuyenSang", 0L);
				
				//4 - Tong so vu viec
				mapMaSo.put("4tongSoVuViec", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNai(predAllDSGQDDonVi));
				
				//5 - So don thuoc tham quyen
				mapMaSo.put("5soDonThuocThamQuyen", thongKeBaoCaoTongHopKQGQDService.getTongSoDonThuocThamQuyen(predAllDSGQDDonVi));
				
				//6 - So vu viec thuoc tham quyen
				mapMaSo.put("6soVuViecThuocThamQuyen",thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecThuocThamQuyen(predAllDSGQDDonVi));
				
				//7 - So vu viec giai quyet bang QD hanh chinh
				mapMaSo.put("7soVuViecGiaiQuyetBangQDHanhChinh", thongKeBaoCaoTongHopKQGQDService.getTongSoDonCoQuyetDinh(predAllDSGQDDonVi));
				
				//8 - So vu viec rut don thong qua giai thich, thuyet phuc
				mapMaSo.put("8soVuViecRutDonThongQuaGiaiThichThuyetPhuc", thongKeBaoCaoTongHopKQGQDService.getTongSoDonDinhChi(predAllDSGQDDonVi));
				
				//9 - Khieu nai dung
				mapMaSo.put("9khieuNaiDung", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiDung(predAllDSGQDDonVi));
				
				//10 - Khieu nai sai
				mapMaSo.put("10khieuNaiSai", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiSai(predAllDSGQDDonVi));
				
				//11 - Khieu nai dung 1 phan
				mapMaSo.put("11khieuNaiDungMotPhan",thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecDungMotPhan(predAllDSGQDDonVi));
				
				//12 - Giai quyet lan 1
				mapMaSo.put("12giaiQuyetLan1", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetLan1(predAllDSGQDDonVi));
				
				//13 - Cong nhan quyet dinh giai quyet lan 1
				mapMaSo.put("13congNhanQuyetDinhGiaiQuyetLan1", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetLan2CongNhan(predAllDSGQDDonVi));
				
				//14 - Huy, sua quyet dinh giai quyet lan 1
				mapMaSo.put("14huySuaQuyetDinhGiaiQuyetLan1", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetLan2HuySua(predAllDSGQDDonVi));
				
				//15 - Kien nghi thu hoi cho nha nuoc Tien
				mapMaSo.put("15kienNghiThuHoiChoNhaNuocTien", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "tienNhaNuoc"));
				
				//16 - Kien nghi thu hoi cho nha nuoc Dat
				mapMaSo.put("16kienNghiThuHoiChoNhaNuocDat", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "datNhaNuoc"));
				
				//17 - Tra lai cho cong dan Tien
				mapMaSo.put("17traLaiChoCongDanTien", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "tienCongDan"));
				
				//18 - Tra lai cho cong dan Dat
				mapMaSo.put("18traLaiChoCongDanDat", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "datCongDan"));
				
				//19 - So nguoi duoc tra lai quyen loi
				mapMaSo.put("19soNguoiDuocTraLaiQuyenLoi", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiDuocTraLaiQuyenLoi(predAllDSGQDDonVi));
				
				//20 - Kien nghi xu ly hanh chinh Tong so nguoi
				mapMaSo.put("20kienNghiXuLyHanhChinhTongSoNguoi", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiXuLyHanhChinh(predAllDSGQDDonVi));
				
				//21 - Kien nghi xu ly hanh chinh So nguoi da bi xu ly
				mapMaSo.put("21KienNghiXuLyHanhChinhSoNguoiDaBiXuLy", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiDaBiXuLyHanhChinh(predAllDSGQDDonVi));
				
				//22 - Chuyen co quan dieu tra so vu
				mapMaSo.put("22soVuChuyenCoQuanDieuTra", thongKeBaoCaoTongHopKQGQDService.getTongSoVuChuyenCoQuanDieuTra(predAllDSGQDDonVi));
				
				//23 - Chuyen co quan dieu tra so doi tuong
				mapMaSo.put("23soDoiTuongChuyenCoQuanDieuTra", thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongChuyenCoQuanDieuTra(predAllDSGQDDonVi));
				
				//24 - So vu da khoi to
				mapMaSo.put("24soVuDaKhoiTo", thongKeBaoCaoTongHopKQGQDService.getTongSoVuDaKhoiTo(predAllDSGQDDonVi));
				
				//25 - So doi tuong da khoi to
				mapMaSo.put("25soDoiTuongDaKhoiTo", thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongDaKhoiTo(predAllDSGQDDonVi));
				
				//26 - So vu viec giai quyet dung thoi han
				mapMaSo.put("26soVuViecGiaiQuyetDungThoiHan", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetKhieuNaiDungThoiHan(predAllDSGQDDonVi));
				
				//27 - So vu viec giai quyet qua thoi han
				mapMaSo.put("27soVuViecGiaiQuyetQuaThoiHan", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetKhieuNaiQuaThoiHan(predAllDSGQDDonVi));
				
				//28 - Tong so quyet dinh phai to chuc thuc hien trong ky bao cao
				mapMaSo.put("28tongSoQuyetDinhPhaiToChucThucHien", 0L);
				
				//29 - Tong so quyet dinh phai to chuc thuc hien trong ky bao cao da thuc hien
				mapMaSo.put("29tongSoQuyetDinhPhaiToChucThucHienDaThucHien", 0L);
				
				//30 - Tien phai thu cho nha nuoc
				mapMaSo.put("30tienPhaiThucChoNhaNuoc", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienNhaNuocPhaiThu"));
				
				//31 - Dat phai thu cho nha nuoc
				mapMaSo.put("31datPhaiThucChoNhaNuoc", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datNhaNuocPhaiThu"));
				
				//32 - Tien da thu cho nha nuoc				
				mapMaSo.put("32tienDaThuChoNhaNuoc", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienNhaNuocDaThu"));
				
				//33 - Dat da thu cho nha nuoc
				mapMaSo.put("33datDaThuChoNhaNuoc", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datNhaNuocDaThu"));
				
				//34 - Tien phai tra cho cong dan
				mapMaSo.put("34tienPhaiTraChoCongDan", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienCongDanPhaiTra"));
				
				//35 - Dat phai tra cho cong dan
				mapMaSo.put("35datPhaiTraChoCongDan", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datCongDanPhaiTra"));
				
				//36 - Tien da tra cho cong dan
				mapMaSo.put("36tienDaTraChoCongDan", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienCongDanDaTra"));
				
				//37 - Dat da tra cho cong dan
				mapMaSo.put("37datDaTraChoCongDan", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datCongDanDaTra"));
				
				//38 - Ghi chu
				mapMaSo.put("38ghiChu", "");
				
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
			ThamSo thamSoCCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCCQQLUBNDSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			
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
				mapMaSo.put("ghiChu", "");
				
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopBaoCaoTiepCongDan(response, "DanhSachTongHopThongKeBaoCaoTiepCongDan", "sheetName", maSos, tuNgay, denNgay, "Danh sách tổng hợp thống kê báo cáo tiếp công dân");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaBaoCaoXuLyDon")
	@ApiOperation(value = "Tổng hợp kết quả báo cáo xử lý đơn thư", position = 3, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaBaoCaoXuLyDon(@RequestHeader(value = "Authorization", required = true) String authorization,
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
			
			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllXLD(loaiKy, quy, year, month, tuNgay, denNgay);
			
			//khieu nai
			List<Long> idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(53L);
			idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(5L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(3L);
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(28L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(4L);
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(29L);
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(32L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(4L);
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(30L);
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(31L);
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(34L);
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(33L);
			
			LinhVucDonThu linhVucHanhChinhDonKhieuNai = linhVucDonThuRepo.findOne(1L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiVeDang = linhVucDonThuRepo.findOne(11L);
			
			//to cao
			LinhVucDonThu linhVucHanhChinhDonToCao = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhapDonToCao = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhungDonToCao = linhVucDonThuRepo.findOne(37L);
			LinhVucDonThu linhVucVeDangDonToCao = linhVucDonThuRepo.findOne(17L);
			LinhVucDonThu linhVucKhacDonToCao = linhVucDonThuRepo.findOne(39L);
			
			//tham quyen giai quyet 
			ThamQuyenGiaiQuyet thamQuyenGiaiQuyetHanhChinh = thamQuyenGiaiQuyetRepo.findOne(1L);
			ThamQuyenGiaiQuyet thamQuyenGiaiQuyetTuPhap = thamQuyenGiaiQuyetRepo.findOne(2L);
			ThamQuyenGiaiQuyet thamQuyenGiaiQuyetCQDang = thamQuyenGiaiQuyetRepo.findOne(3L);
			
			List<LinhVucDonThu> linhVucLienQuanDenDatDais = new ArrayList<LinhVucDonThu>();
			linhVucLienQuanDenDatDais.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais));
			
			List<LinhVucDonThu> linhVucCTVHXHKKhacs = new ArrayList<LinhVucDonThu>();
			linhVucCTVHXHKKhacs.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs));
			
			List<LinhVucDonThu> linhVucVeChinhSachCCVCs = new ArrayList<LinhVucDonThu>();
			linhVucVeChinhSachCCVCs.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs));
			
			List<LinhVucDonThu> linhVucVeNhaCuaTaiSans = new ArrayList<LinhVucDonThu>();
			linhVucVeChinhSachCCVCs.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDXLDDonVi = predAllDSXLD;
				BooleanExpression predAllDSTCDDonVi = predAllDSTCD;
				predAllDSTCDDonVi = predAllDSTCDDonVi.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(cq.getId())
						.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(cq.getId()))
						.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(cq.getId())));
				
				predAllDXLDDonVi = predAllDXLDDonVi.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId())
						.or(QXuLyDon.xuLyDon.donViXuLy.cha.id.eq(cq.getId()))
						.or(QXuLyDon.xuLyDon.donViXuLy.cha.cha.id.eq(cq.getId())));
				
				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("donVi", mapDonVi);
				
				//tong so don - 1
				mapMaSo.put("1tongSoDonTiepNhanXLDTCD", thongKeBaoCaoTongHopKQXLDService.getTongSoDonTiepNhanXLDTCD(predAllDXLDDonVi, predAllDSTCDDonVi));
				
				//don co nhieu nguoi dung ten - trong ky - 2
				mapMaSo.put("2tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy", 0);
				
				//don co mot nguoi dung ten - trong ky - 3
				mapMaSo.put("3tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy", 0);
				
				
				//don co nhieu nguoi dung ten - truoc ky - 4
				mapMaSo.put("4tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang", 0);
				
				//don co mot nguoi dung ten - truoc ky - 5
				mapMaSo.put("5tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang", 0);
				
				//don du dieu kien xu ly - 6
				mapMaSo.put("6tongSoDonDuDieuKienThuLy", thongKeBaoCaoTongHopKQXLDService.getTongSoDonDuDieuKienThuLyLuuDonVaTheoDoi(predAllDXLDDonVi));
				
				Long tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDSTCDDonVi, 
						linhVucHanhChinhDonKhieuNai, linhVucLienQuanDenDatDais);
				Long tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDSTCDDonVi, 
						linhVucHanhChinhDonKhieuNai, linhVucVeNhaCuaTaiSans);
				Long tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDSTCDDonVi,
						linhVucHanhChinhDonKhieuNai, linhVucVeChinhSachCCVCs);
				Long tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDSTCDDonVi, 
						linhVucHanhChinhDonKhieuNai, linhVucCTVHXHKKhacs);
				Long tongSoDonKhieuNaiLinhVucHanhChinh = tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai + tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan + tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC
						+ tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac;
				//tong - 7
				mapMaSo.put("7tongSoDonKhieuNaiLinhVucHanhChinh", tongSoDonKhieuNaiLinhVucHanhChinh);
				
				//lien quan den dat dai - 8
				mapMaSo.put("8tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai", tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai);
				
				//ve nha tai san - 9
				mapMaSo.put("9tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan", tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan);
				
				//ve chinh sach che do, cc, vc - 10
				mapMaSo.put("10tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC", tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC);
				
				//linh vuc kinh te chinh tri xa hoi khac - 11
				mapMaSo.put("11tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac", tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac);
				
				//linh vuc tu phap - 12
				mapMaSo.put("12tongSoDonKhieuNaiLinhVucTuPhap",  thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(predAllDSTCDDonVi, linhVucHanhChinhKhieuNaiTuPhap));
				
				//linh vuc ve dang - 13
				mapMaSo.put("13tongSoDonKhieuNaiLinhVucVeDang", thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(predAllDSTCDDonVi, linhVucHanhChinhKhieuNaiVeDang));
				
				Long tongSoDonToCaoLinhVucHanhChinh = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDSTCDDonVi, linhVucHanhChinhDonToCao);
				Long tongSoDonToCaoLinhVucTuPhap = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDSTCDDonVi, linhVucTuPhapDonToCao);
				Long tongSoDonToCaoLinhVucThamNhung = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucChiTietCha(predAllDSTCDDonVi, linhVucTuPhapDonToCao, linhVucThamNhungDonToCao);
				Long tongSoDonToCaoLinhVucVeDang = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDSTCDDonVi, linhVucVeDangDonToCao);
				Long tongSoDonToCaoLinhVucKhac= thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDSTCDDonVi, linhVucKhacDonToCao);
				Long tongSoDonLinhVucToCao = tongSoDonToCaoLinhVucHanhChinh + tongSoDonToCaoLinhVucTuPhap + tongSoDonToCaoLinhVucThamNhung + tongSoDonToCaoLinhVucVeDang+ tongSoDonToCaoLinhVucKhac;
				
				//tong so don linh vuc to cao - 14
				mapMaSo.put("14tongSoDonLinhVucToCao", tongSoDonLinhVucToCao);
				
				//don to cao linh vuc hanh chinh - 15
				mapMaSo.put("15tongSoDonToCaoLinhVucHanhChinh", tongSoDonToCaoLinhVucHanhChinh);
				
				//don to cao linh vuc tu phap - 16
				mapMaSo.put("16tongSoDonToCaoLinhVucTuPhap", tongSoDonToCaoLinhVucTuPhap);
				
				//don to cao linh vuc tham nhung - 17 // ?
				mapMaSo.put("17tongSoDonToCaoLinhVucThamNhung", tongSoDonToCaoLinhVucThamNhung);
								
				//don to cao linh vuc ve dang - 18
				mapMaSo.put("18tongSoDonToCaoLinhVucVeDang", tongSoDonToCaoLinhVucVeDang);

				//don to cao linh vuc khac - 19
				mapMaSo.put("19tongSoDonToCaoLinhVucKhac", tongSoDonToCaoLinhVucKhac);
										
				//theo tham quyen giai quyet - cua cac co quan hanh chinh cac cap - 20
				mapMaSo.put("20tongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoThamQuyenGiaiQuyet(predAllDXLDDonVi, thamQuyenGiaiQuyetHanhChinh));
				
				//theo tham quyen giai quyet - cua cac co quan tu phap cac cap - 21
				mapMaSo.put("21tongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoThamQuyenGiaiQuyet(predAllDXLDDonVi, thamQuyenGiaiQuyetTuPhap));

				//theo tham quyen giai quyet - cua co quan dang - 22
				mapMaSo.put("22tongSoDonTheoTQGQCuaCoQuanDang", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoThamQuyenGiaiQuyet(predAllDXLDDonVi, thamQuyenGiaiQuyetCQDang));
				
				//theo trinh tu giai quyet - chua giai quyet - 23
				mapMaSo.put("23tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet", "0");
				
				//theo trinh tu giai quyet - da duoc giai quyet lan dau - 24
				mapMaSo.put("24tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyetLanDau", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoTrinhTuGiaiQuyetDaDuocGiaiQuyetLanDau(predAllDXLDDonVi));
				
				//theo trinh tu giai quyet - da duoc giai quyet nhieu lan - 25
				mapMaSo.put("25tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyetNhieuLan", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoTrinhTuGiaiQuyetDaDuocGiaiQuyetNhieuLan(predAllDXLDDonVi));
				
				//don kien nghi phan anh - 26
				mapMaSo.put("26tongSoDonXLDDonKienNghiPhanAnh", thongKeBaoCaoTongHopKQXLDService.getTongSoDonKienNghiPhanAnhHXLLuuDonVaTheoDoi(predAllDXLDDonVi));
				
				//so van ban huong dan - 27
				mapMaSo.put("27tongSoDonXLDSoVanBanHuongDan", thongKeBaoCaoTongHopKQXLDService.getTongSoDonHXLTraDonVaHuongDan(predAllDXLDDonVi));

				//so don chuyen co tham quyen - 28
				mapMaSo.put("28tongSoDonChuyenCQCoThamQuyen", thongKeBaoCaoTongHopKQXLDService.getTongSoDonChuyenCQCoThamQuyen(predAllDXLDDonVi));
				
				//so cong van don doc viec giai quyet - 29
				mapMaSo.put("29tongSoDonCoSoCongVanDonDocViecGiaiQuyet", "0");
				
				//tong so don thuoc tham quyen khieu nai - 30
				mapMaSo.put("30tongSoDonThuocThamQuyenKhieuNai", thongKeBaoCaoTongHopKQXLDService.getTongSoDonThuocThamQuyenKhieuNai(predAllDXLDDonVi));
				
				//tong so don thuoc tham quyen to cao - 31
				mapMaSo.put("31tongSoDonThuocThamQuyenToCao", thongKeBaoCaoTongHopKQXLDService.getTongSoDonThuocThamQuyenToCao(predAllDXLDDonVi));
				
				//ghi chu - 32
				mapMaSo.put("32ghiChu", "");
				
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaXuLyDonThu/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp báo cáo xử lý đơn thư", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
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
			ThamSo thamSoCCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
			ThamSo thamSoCCQQLUBNDSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			
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
			
			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllXLD(loaiKy, quy, year, month, tuNgay, denNgay);
			
			//khieu nai
			List<Long> idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(53L);
			idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(5L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(3L);
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(28L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(4L);
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(29L);
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(32L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(4L);
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(30L);
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(31L);
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(34L);
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(33L);
			
			LinhVucDonThu linhVucHanhChinhDonKhieuNai = linhVucDonThuRepo.findOne(1L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiVeDang = linhVucDonThuRepo.findOne(11L);
			
			//to cao
			LinhVucDonThu linhVucHanhChinhDonToCao = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhapDonToCao = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhungDonToCao = linhVucDonThuRepo.findOne(37L);
			LinhVucDonThu linhVucVeDangDonToCao = linhVucDonThuRepo.findOne(17L);
			LinhVucDonThu linhVucKhacDonToCao = linhVucDonThuRepo.findOne(39L);
			
			//tham quyen giai quyet 
			ThamQuyenGiaiQuyet thamQuyenGiaiQuyetHanhChinh = thamQuyenGiaiQuyetRepo.findOne(1L);
			ThamQuyenGiaiQuyet thamQuyenGiaiQuyetTuPhap = thamQuyenGiaiQuyetRepo.findOne(2L);
			ThamQuyenGiaiQuyet thamQuyenGiaiQuyetCQDang = thamQuyenGiaiQuyetRepo.findOne(3L);
			
			List<LinhVucDonThu> linhVucLienQuanDenDatDais = new ArrayList<LinhVucDonThu>();
			linhVucLienQuanDenDatDais.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais));
			
			List<LinhVucDonThu> linhVucCTVHXHKKhacs = new ArrayList<LinhVucDonThu>();
			linhVucCTVHXHKKhacs.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs));
			
			List<LinhVucDonThu> linhVucVeChinhSachCCVCs = new ArrayList<LinhVucDonThu>();
			linhVucVeChinhSachCCVCs.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs));
			
			List<LinhVucDonThu> linhVucVeNhaCuaTaiSans = new ArrayList<LinhVucDonThu>();
			linhVucVeChinhSachCCVCs.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDXLDDonVi = predAllDSXLD;
				BooleanExpression predAllDSTCDDonVi = predAllDSTCD;
				predAllDSTCDDonVi = predAllDSTCDDonVi.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(cq.getId())
						.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(cq.getId()))
						.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(cq.getId())));
				
				predAllDXLDDonVi = predAllDXLDDonVi.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId())
						.or(QXuLyDon.xuLyDon.donViXuLy.cha.id.eq(cq.getId()))
						.or(QXuLyDon.xuLyDon.donViXuLy.cha.cha.id.eq(cq.getId())));
				
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("0", cq.getTen());
				
				mapMaSo.put("1", thongKeBaoCaoTongHopKQXLDService.getTongSoDonTiepNhanXLDTCD(predAllDXLDDonVi, predAllDSTCDDonVi));
				mapMaSo.put("2", 0);
				mapMaSo.put("3", 0);
				mapMaSo.put("4", 0);
				mapMaSo.put("5", 0);
				mapMaSo.put("6", thongKeBaoCaoTongHopKQXLDService.getTongSoDonDuDieuKienThuLyLuuDonVaTheoDoi(predAllDXLDDonVi));
				
				Long tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDSTCDDonVi, 
						linhVucHanhChinhDonKhieuNai, linhVucLienQuanDenDatDais);
				Long tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDSTCDDonVi, 
						linhVucHanhChinhDonKhieuNai, linhVucVeNhaCuaTaiSans);
				Long tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDSTCDDonVi,
						linhVucHanhChinhDonKhieuNai, linhVucVeChinhSachCCVCs);
				Long tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDSTCDDonVi, 
						linhVucHanhChinhDonKhieuNai, linhVucCTVHXHKKhacs);
				Long tongSoDonKhieuNaiLinhVucHanhChinh = tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai + tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan + tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC
						+ tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac;
				
				mapMaSo.put("7", tongSoDonKhieuNaiLinhVucHanhChinh);
				mapMaSo.put("8", tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai);
				mapMaSo.put("9", tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan);
				mapMaSo.put("10", tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC);
				mapMaSo.put("11", tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac);
				mapMaSo.put("12", thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(predAllDSTCDDonVi, linhVucHanhChinhKhieuNaiTuPhap));
				
				mapMaSo.put("13", thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(predAllDSTCDDonVi, linhVucHanhChinhKhieuNaiVeDang));
				
				Long tongSoDonToCaoLinhVucHanhChinh = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDSTCDDonVi, linhVucHanhChinhDonToCao);
				Long tongSoDonToCaoLinhVucTuPhap = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDSTCDDonVi, linhVucTuPhapDonToCao);
				Long tongSoDonToCaoLinhVucThamNhung = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucChiTietCha(predAllDSTCDDonVi, linhVucTuPhapDonToCao, linhVucThamNhungDonToCao);
				Long tongSoDonToCaoLinhVucVeDang = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDSTCDDonVi, linhVucVeDangDonToCao);
				Long tongSoDonToCaoLinhVucKhac= thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDSTCDDonVi, linhVucKhacDonToCao);
				Long tongSoDonLinhVucToCao = tongSoDonToCaoLinhVucHanhChinh + tongSoDonToCaoLinhVucTuPhap + tongSoDonToCaoLinhVucThamNhung + tongSoDonToCaoLinhVucVeDang+ tongSoDonToCaoLinhVucKhac;
				
				
				mapMaSo.put("14", tongSoDonLinhVucToCao);
				mapMaSo.put("15", tongSoDonToCaoLinhVucHanhChinh);
				mapMaSo.put("16", tongSoDonToCaoLinhVucTuPhap);
				mapMaSo.put("17", tongSoDonToCaoLinhVucThamNhung);
				mapMaSo.put("18", tongSoDonToCaoLinhVucVeDang);
				mapMaSo.put("19", tongSoDonToCaoLinhVucKhac);
				mapMaSo.put("20", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoThamQuyenGiaiQuyet(predAllDXLDDonVi, thamQuyenGiaiQuyetHanhChinh));
				
				mapMaSo.put("21", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoThamQuyenGiaiQuyet(predAllDXLDDonVi, thamQuyenGiaiQuyetTuPhap));

				mapMaSo.put("22", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoThamQuyenGiaiQuyet(predAllDXLDDonVi, thamQuyenGiaiQuyetCQDang));
				
				mapMaSo.put("23", 0);
				
				mapMaSo.put("24", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoTrinhTuGiaiQuyetDaDuocGiaiQuyetLanDau(predAllDXLDDonVi));
				
				mapMaSo.put("25", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoTrinhTuGiaiQuyetDaDuocGiaiQuyetNhieuLan(predAllDXLDDonVi));
				
				mapMaSo.put("26", thongKeBaoCaoTongHopKQXLDService.getTongSoDonKienNghiPhanAnhHXLLuuDonVaTheoDoi(predAllDXLDDonVi));
				
				mapMaSo.put("27", thongKeBaoCaoTongHopKQXLDService.getTongSoDonHXLTraDonVaHuongDan(predAllDXLDDonVi));

				mapMaSo.put("28", thongKeBaoCaoTongHopKQXLDService.getTongSoDonChuyenCQCoThamQuyen(predAllDXLDDonVi));
				
				mapMaSo.put("29", 0);
				
				mapMaSo.put("30", thongKeBaoCaoTongHopKQXLDService.getTongSoDonThuocThamQuyenKhieuNai(predAllDXLDDonVi));
				
				mapMaSo.put("31", thongKeBaoCaoTongHopKQXLDService.getTongSoDonThuocThamQuyenToCao(predAllDXLDDonVi));
				
				mapMaSo.put("32", "");
				
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopBaoCaoXuLyDonThu(response, "DanhSachTongHopThongKeBaoCaoXuLyDonThu", "sheetName", maSos, tuNgay, denNgay, "Danh sách tổng hợp thống kê báo cáo xử lý đơn thư");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaGiaiQuyetDonKhieuNai/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả giải quyết đơn khiếu nại", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelGiaiQuyetKhieuNai(HttpServletResponse response,
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
	
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()));
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString()));
			capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
			List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTHTKBC(
					Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds,
					"CQQL_UBNDTP_DA_NANG"));
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
				mapMaSo.put("33", 33);
				mapMaSo.put("34", 34);
				mapMaSo.put("35", 35);
				mapMaSo.put("36", 36);
				mapMaSo.put("37", 37);
				mapMaSo.put("38", 38);
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopBaoCaoGiaiQuyetKhieuNai(response, "DanhSachTongHopThongKeBaoCaoGiaiQuyetKhieuNai", "sheetName", maSos, tuNgay, denNgay, "Danh sách tổng hợp thống kê báo cáo giải quyết đơn khiếu nại");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaGiaiQuyetDonToCao/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả giải quyết đơn tố cáo", position = 6, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelGiaiQuyetToCao(HttpServletResponse response,
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
	
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()));
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString()));
			capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
			List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTHTKBC(
					Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds,
					"CQQL_UBNDTP_DA_NANG"));
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
				mapMaSo.put("33", 33);
				mapMaSo.put("34", 34);
				mapMaSo.put("35", 35);
				mapMaSo.put("36", 36);
				mapMaSo.put("37", 37);
				mapMaSo.put("38", 38);
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopBaoCaoGiaiQuyetToCao(response, "DanhSachTongHopThongKeBaoCaoGiaiQuyetToCao", "sheetName", maSos, tuNgay, denNgay, "Danh sách tổng hợp thống kê báo cáo giải quyết đơn tố cáo");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
}
