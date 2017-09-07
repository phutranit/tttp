package vn.greenglobal.tttp.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
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
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiTiepDanEnum;
import vn.greenglobal.tttp.enums.LoaiVuViecEnum;
import vn.greenglobal.tttp.enums.ThongKeBaoCaoLoaiKyEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.QDon;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.model.ThamQuyenGiaiQuyet;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CuocThanhTraRepository;
import vn.greenglobal.tttp.repository.LinhVucDonThuRepository;
import vn.greenglobal.tttp.repository.ThamQuyenGiaiQuyetRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.service.LinhVucDonThuService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.service.ThongKeBaoCaoTongHopKQGQDService;
import vn.greenglobal.tttp.service.ThongKeBaoCaoTongHopKQTCDService;
import vn.greenglobal.tttp.service.ThongKeBaoCaoTongHopKQXLDService;
import vn.greenglobal.tttp.service.ThongKeTongHopThanhTraService;
import vn.greenglobal.tttp.util.ExcelUtil;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "thongKeBaoCaos", description = "Thống kê báo cáo")
public class ThongKeBaoCaoController extends TttpController<Don> {
	
	@Autowired
	private ThamSoRepository repoThamSo;
	
	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepo;
	
	@Autowired
	private LinhVucDonThuRepository linhVucDonThuRepo;
	
	@Autowired
	private ThamQuyenGiaiQuyetRepository thamQuyenGiaiQuyetRepo;
	
	@Autowired
	private LinhVucDonThuService linhVucDonThuService;
	
	@Autowired
	private CuocThanhTraRepository cuocThanhTraRepo;
	
	@Autowired
	private ThamSoService thamSoService;
	
	@Autowired
	private ThongKeBaoCaoTongHopKQTCDService thongKeBaoCaoTongHopKQTCDService;
	
	@Autowired
	private ThongKeBaoCaoTongHopKQXLDService thongKeBaoCaoTongHopKQXLDService;

	@Autowired
	private ThongKeBaoCaoTongHopKQGQDService thongKeBaoCaoTongHopKQGQDService;
	
	@Autowired
	private ThongKeTongHopThanhTraService thongKeTongHopThanhTraService;
	
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
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			PersistentEntityResourceAssembler eass) {
		try {
			
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			Long donViXuLy = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
	
			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
						
			if (listDonVis != null) {
				for (CoQuanQuanLy dv : listDonVis) {
					CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
					if (coQuan != null) {
						list.add(coQuan);
					}					
				}
			} else {
				coQuanQuanLyRepo.findOne(donViXuLy);
				list.add(coQuanQuanLyRepo.findOne(donViXuLy));
			}
			donVis.addAll(list);
			
			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQTCDService.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			LinhVucDonThu linhVucHanhChinh = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhap = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhung = linhVucDonThuRepo.findOne(39L);
			LinhVucDonThu linhVucKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucChinhTriVanHoaXaHoiKhac = linhVucDonThuRepo.findOne(13L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais.add(55L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachs.add(5L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(57L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs.add(53L);
			
			List<LinhVucDonThu> linhVucTranhChapVeDatDais = new ArrayList<LinhVucDonThu>();
			linhVucTranhChapVeDatDais.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais));
			
			List<LinhVucDonThu> linhVucVeChinhSachs = new ArrayList<LinhVucDonThu>();
			linhVucVeChinhSachs.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeChinhSachs));
			
			List<LinhVucDonThu> linhVucVeNhaCuaTaiSans = new ArrayList<LinhVucDonThu>();
			linhVucVeNhaCuaTaiSans.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));
			
			List<LinhVucDonThu> linhVucVeCheDoCCVCs = new ArrayList<LinhVucDonThu>();
			linhVucVeCheDoCCVCs.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs));
			
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
						false, false));
				
				//vu viec cu - 3
				mapMaSo.put("tiepCongDanThuongXuyenVuViecCu", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, false, LoaiVuViecEnum.CU));
				
				//vu viec moi phat sinh - 4
				mapMaSo.put("tiepCongDanThuongXuyenVuViecMoiPhatSinh", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, false, LoaiVuViecEnum.MOI_PHAT_SINH));
				
				//doan dong nguoi
				//so doan - 5
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiSoDoan", thongKeBaoCaoTongHopKQTCDService.getTongSoDoanDongNguoiTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true));
				
				//nguoi - 6
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiSoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, 
						false, true));
				
				//doan dong nguoi - vu viec
				//cu - 7
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiVuViecCu", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true, LoaiVuViecEnum.CU));
				
				//moi phat sinh - 8
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true, LoaiVuViecEnum.MOI_PHAT_SINH));
				
				//tiep cong dan dinh ky dot xuat cua lanh dao
				//luoc - 9
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoLuot", thongKeBaoCaoTongHopKQTCDService.getTongSoLuocTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao));
				
				//nguoi - 10
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao));
				
				//tiep cong dan cua lanh dao - vu viec
				//cu - 11
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, false, LoaiVuViecEnum.CU));
				
				//moi phat sinh - 12
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, false, LoaiVuViecEnum.MOI_PHAT_SINH));
				
				//tiep cong dan cua lanh dao - doan dong nguoi
				//so doan - 13
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan", thongKeBaoCaoTongHopKQTCDService.getTongSoDoanDongNguoiTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true));
				
				//so nguoi - 14
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao,
						false, true));
				
				//tiep cong dan cua lanh dao - doan dong nguoi - vu viec
				//cu - 15
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true, LoaiVuViecEnum.CU));
				
				//moi phat sinh - 16
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true, LoaiVuViecEnum.MOI_PHAT_SINH));

				//ve tranh chap - 17
				mapMaSo.put("linhVucVeTranhChap", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi, linhVucTranhChapVeDatDais));
								
				//veChinhSach - 18
				mapMaSo.put("linhVucVeChinhSach", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi, linhVucVeChinhSachs));

				//veNhaCuaVaTaiSan - 19
				mapMaSo.put("linhVucVeNhaCuaVaTaiSan", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi, linhVucVeNhaCuaTaiSans));
										
				//veCheDoCC,VC - 20
				mapMaSo.put("linhVucVeCheDoCCVC", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi, linhVucVeCheDoCCVCs));
				
				//linhVucTuPhap - 21
				mapMaSo.put("linhVucTuPhapDonKhieuNai", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi, linhVucKhieuNaiTuPhap));

				//linhVucChinhTriVanHoaXaHoi - 22
				mapMaSo.put("linhVucChinhTriVanHoaXaHoiKhac", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi, linhVucChinhTriVanHoaXaHoiKhac));
				
				//don to cao
				//linhVucHanhChinh - 23
				mapMaSo.put("linhVucHanhChinh", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucDonThu(predAllDSTCDDonVi, linhVucHanhChinh));
				
				//linhVucHanhChinh - 24
				mapMaSo.put("linhVucTuPhapDonToCao", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucDonThu(predAllDSTCDDonVi, linhVucTuPhap));
				
				//linhVucThamNhung - 25
				mapMaSo.put("linhVucThamNhung", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucDonThu(predAllDSTCDDonVi, linhVucThamNhung));
				
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
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			PersistentEntityResourceAssembler eass) {
		try {
			
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}				
			
			if (year == null || year == 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			if (quy == null) {
				quy = Utils.getQuyHienTai();
			}
			
			Map<String, Object> mapDonVi = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			ThamSo phongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo phuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));			
			Long idCapCQQLPhongBan = Long.valueOf(phongBan.getGiaTri().toString());
			Long idCapCQQLPhuongXa = Long.valueOf(phuongXa.getGiaTri().toString());
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			if (listDonVis != null) {
				for (CoQuanQuanLy dv : listDonVis) {
					CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
					if (coQuan != null) {
						donVis.add(coQuan);
					}				
				}
			}
			
			BooleanExpression predAllDSDon = (BooleanExpression) thongKeBaoCaoTongHopKQGQDService.predicateFindAllGQD(loaiKy, quy, year, month, tuNgay, denNgay);
			predAllDSDon = predAllDSDon.and(QDon.don.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
					.and(QDon.don.thanhLapDon.eq(true))
					.and(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.DE_XUAT_THU_LY));
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSGQDDonVi = predAllDSDon;
				predAllDSGQDDonVi = predAllDSGQDDonVi.and(QDon.don.donViXuLyGiaiQuyet.id.eq(cq.getId())
						.or(QDon.don.donViXuLyGiaiQuyet.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))
						.or(QDon.don.donViXuLyGiaiQuyet.cha.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))						
						);				
				
				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("donVi", mapDonVi);	
				
				//1 - Tong so don khieu nai
				mapMaSo.put("tongSoDonKhieuNai", thongKeBaoCaoTongHopKQGQDService.getTongSoDon(predAllDSGQDDonVi));
				
				Long donNhanTrongKyBaoCao = thongKeBaoCaoTongHopKQGQDService.getTongSoDonTrongKyBaoCao(predAllDSGQDDonVi, loaiKy, quy, year, month, tuNgay, denNgay);
				
				ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
				Long donTonKyTruocChuyenSang = loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON) && StringUtils.isBlank(tuNgay) ? 
						0 : thongKeBaoCaoTongHopKQGQDService.getTongSoDonTonKyTruoc(predAllDSGQDDonVi, loaiKy, quy, year, month, tuNgay, denNgay);
				
				//2 - Don nhan trong ky bao cao
				mapMaSo.put("donNhanTrongKyBaoCao", donNhanTrongKyBaoCao);
				
				//3 - Don ton ky truoc chuyen sang
				mapMaSo.put("donTonKyTruocChuyenSang", donTonKyTruocChuyenSang);
				
				//4 - Tong so vu viec
				mapMaSo.put("tongSoVuViec", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViec(predAllDSGQDDonVi));
				
				//5 - So don thuoc tham quyen
				mapMaSo.put("soDonThuocThamQuyen", thongKeBaoCaoTongHopKQGQDService.getTongSoDonKhieuNaiThuocThamQuyen(predAllDSGQDDonVi));
				
				//6 - So vu viec thuoc tham quyen
				mapMaSo.put("soVuViecThuocThamQuyen",thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiThuocThamQuyen(predAllDSGQDDonVi));
				
				//7 - So vu viec giai quyet bang QD hanh chinh
				mapMaSo.put("soVuViecGiaiQuyetBangQDHanhChinh", thongKeBaoCaoTongHopKQGQDService.getTongSoDonCoQuyetDinh(predAllDSGQDDonVi));
				
				//8 - So vu viec rut don thong qua giai thich, thuyet phuc
				mapMaSo.put("soVuViecRutDonThongQuaGiaiThichThuyetPhuc", thongKeBaoCaoTongHopKQGQDService.getTongSoDonDinhChi(predAllDSGQDDonVi));
				
				//9 - Khieu nai dung
				mapMaSo.put("khieuNaiDung", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiDung(predAllDSGQDDonVi));
				
				//10 - Khieu nai sai
				mapMaSo.put("khieuNaiSai", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiSai(predAllDSGQDDonVi));
				
				//11 - Khieu nai dung 1 phan
				mapMaSo.put("khieuNaiDungMotPhan",thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecDungMotPhan(predAllDSGQDDonVi));
				
				//12 - Giai quyet lan 1
				mapMaSo.put("giaiQuyetLan1", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetLan1(predAllDSGQDDonVi));
				
				//13 - Cong nhan quyet dinh giai quyet lan 1
				mapMaSo.put("congNhanQuyetDinhGiaiQuyetLan1", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetLan2CongNhan(predAllDSGQDDonVi));
				
				//14 - Huy, sua quyet dinh giai quyet lan 1
				mapMaSo.put("huySuaQuyetDinhGiaiQuyetLan1", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetLan2HuySua(predAllDSGQDDonVi));
				
				//15 - Kien nghi thu hoi cho nha nuoc Tien
				mapMaSo.put("kienNghiThuHoiChoNhaNuocTien", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "tienNhaNuoc"));
				
				//16 - Kien nghi thu hoi cho nha nuoc Dat
				mapMaSo.put("kienNghiThuHoiChoNhaNuocDat", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "datNhaNuoc"));
				
				//17 - Tra lai cho cong dan Tien
				mapMaSo.put("traLaiChoCongDanTien", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "tienCongDan"));
				
				//18 - Tra lai cho cong dan Dat
				mapMaSo.put("traLaiChoCongDanDat", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "datCongDan"));
				
				//19 - So nguoi duoc tra lai quyen loi
				mapMaSo.put("soNguoiDuocTraLaiQuyenLoi", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiDuocTraLaiQuyenLoi(predAllDSGQDDonVi));
				
				//20 - Kien nghi xu ly hanh chinh Tong so nguoi
				mapMaSo.put("kienNghiXuLyHanhChinhTongSoNguoi", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiXuLyHanhChinh(predAllDSGQDDonVi));
				
				//21 - Kien nghi xu ly hanh chinh So nguoi da bi xu ly
				mapMaSo.put("kienNghiXuLyHanhChinhSoNguoiDaBiXuLy", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiDaBiXuLyHanhChinh(predAllDSGQDDonVi));
				
				//22 - Chuyen co quan dieu tra so vu
				mapMaSo.put("soVuChuyenCoQuanDieuTra", thongKeBaoCaoTongHopKQGQDService.getTongSoVuChuyenCoQuanDieuTra(predAllDSGQDDonVi));
				
				//23 - Chuyen co quan dieu tra so doi tuong
				mapMaSo.put("soDoiTuongChuyenCoQuanDieuTra", thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongChuyenCoQuanDieuTra(predAllDSGQDDonVi));
				
				//24 - So vu da khoi to
				mapMaSo.put("soVuDaKhoiTo", thongKeBaoCaoTongHopKQGQDService.getTongSoVuDaKhoiTo(predAllDSGQDDonVi));
				
				//25 - So doi tuong da khoi to
				mapMaSo.put("soDoiTuongDaKhoiTo", thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongDaKhoiTo(predAllDSGQDDonVi));
				
				//26 - So vu viec giai quyet dung thoi han
				mapMaSo.put("soVuViecGiaiQuyetDungThoiHan", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetKhieuNaiDungThoiHan(predAllDSGQDDonVi));
				
				//27 - So vu viec giai quyet qua thoi han
				mapMaSo.put("soVuViecGiaiQuyetQuaThoiHan", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetKhieuNaiQuaThoiHan(predAllDSGQDDonVi));
				
				//28 - Tong so quyet dinh phai to chuc thuc hien trong ky bao cao
				mapMaSo.put("tongSoQuyetDinhPhaiToChucThucHien", 0L);
				
				//29 - Tong so quyet dinh phai to chuc thuc hien trong ky bao cao da thuc hien
				mapMaSo.put("tongSoQuyetDinhPhaiToChucThucHienDaThucHien", 0L);
				
				//30 - Tien phai thu cho nha nuoc
				mapMaSo.put("tienPhaiThuChoNhaNuoc", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienNhaNuocPhaiThu"));
				
				//31 - Dat phai thu cho nha nuoc
				mapMaSo.put("datPhaiThuChoNhaNuoc", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datNhaNuocPhaiThu"));
				
				//32 - Tien da thu cho nha nuoc				
				mapMaSo.put("tienDaThuChoNhaNuoc", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienNhaNuocDaThu"));
				
				//33 - Dat da thu cho nha nuoc
				mapMaSo.put("datDaThuChoNhaNuoc", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datNhaNuocDaThu"));
				
				//34 - Tien phai tra cho cong dan
				mapMaSo.put("tienPhaiTraChoCongDan", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienCongDanPhaiTra"));
				
				//35 - Dat phai tra cho cong dan
				mapMaSo.put("datPhaiTraChoCongDan", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datCongDanPhaiTra"));
				
				//36 - Tien da tra cho cong dan
				mapMaSo.put("tienDaTraChoCongDan", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienCongDanDaTra"));
				
				//37 - Dat da tra cho cong dan
				mapMaSo.put("datDaTraChoCongDan", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datCongDanDaTra"));
				
				//38 - Ghi chu
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaGiaiQuyetDonToCao")
	@ApiOperation(value = "Tổng hợp kết quả kết quả giải quyết khiếu nại", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaGiaiQuyetDonToCao(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			PersistentEntityResourceAssembler eass) {
		try {
			
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			if (year == null || year == 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			
			Map<String, Object> mapDonVi = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			ThamSo phongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo phuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));			
			Long idCapCQQLPhongBan = Long.valueOf(phongBan.getGiaTri().toString());
			Long idCapCQQLPhuongXa = Long.valueOf(phuongXa.getGiaTri().toString());
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			if (listDonVis != null) {
				for (CoQuanQuanLy dv : listDonVis) {
					CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
					if (coQuan != null) {
						donVis.add(coQuan);
					}				
				}
			}
			
			BooleanExpression predAllDSDon = (BooleanExpression) thongKeBaoCaoTongHopKQGQDService.predicateFindAllGQD(loaiKy, quy, year, month, tuNgay, denNgay);
			predAllDSDon = predAllDSDon.and(QDon.don.loaiDon.eq(LoaiDonEnum.DON_TO_CAO))
					.and(QDon.don.thanhLapDon.eq(true))
					.and(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.DE_XUAT_THU_LY));
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSGQDDonVi = predAllDSDon;
				predAllDSGQDDonVi = predAllDSGQDDonVi.and(QDon.don.donViXuLyGiaiQuyet.id.eq(cq.getId())
						.or(QDon.don.donViXuLyGiaiQuyet.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))
						.or(QDon.don.donViXuLyGiaiQuyet.cha.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))						
						);				
				
				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("donVi", mapDonVi);	
				
				//1 - Tong so don to cao
				mapMaSo.put("tongSoDonToCao", thongKeBaoCaoTongHopKQGQDService.getTongSoDon(predAllDSGQDDonVi));
				
				Long donNhanTrongKyBaoCao = thongKeBaoCaoTongHopKQGQDService.getTongSoDonTrongKyBaoCao(predAllDSGQDDonVi, loaiKy, quy, year, month, tuNgay, denNgay);
				
				ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
				Long donTonKyTruocChuyenSang = loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON) && StringUtils.isBlank(tuNgay) ? 
						0 : thongKeBaoCaoTongHopKQGQDService.getTongSoDonTonKyTruoc(predAllDSGQDDonVi, loaiKy, quy, year, month, tuNgay, denNgay);	
				
				//2 - Don nhan trong ky bao cao
				mapMaSo.put("donNhanTrongKyBaoCao", donNhanTrongKyBaoCao);
				
				//3 - Don ton ky truoc chuyen sang
				mapMaSo.put("donTonKyTruocChuyenSang", donTonKyTruocChuyenSang);
				
				//4 - Tong so vu viec
				mapMaSo.put("tongSoVuViec", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViec(predAllDSGQDDonVi));
				
				//5 - So don thuoc tham quyen
				mapMaSo.put("soDonThuocThamQuyen", thongKeBaoCaoTongHopKQGQDService.getTongSoDonToCaoThuocThamQuyen(predAllDSGQDDonVi));
				
				//6 - So vu viec thuoc tham quyen
				mapMaSo.put("soVuViecThuocThamQuyen",thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecToCaoThuocThamQuyen(predAllDSGQDDonVi));
				
				//7 - to Cao Dung
				mapMaSo.put("toCaoDung", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiDung(predAllDSGQDDonVi));
				
				//8 - To cao sai
				mapMaSo.put("toCaoSai", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiSai(predAllDSGQDDonVi));
				
				//9 - To cao dung 1 phan
				mapMaSo.put("toCaoDungMotPhan",thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecDungMotPhan(predAllDSGQDDonVi));
				
				//10 - Kien nghi thu hoi cho nha nuoc Tien
				mapMaSo.put("kienNghiThuHoiChoNhaNuocTien", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "tienNhaNuoc"));
				
				//11 - Kien nghi thu hoi cho nha nuoc Dat
				mapMaSo.put("kienNghiThuHoiChoNhaNuocDat", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "datNhaNuoc"));
				
				//12 - Tra lai cho cong dan Tien
				mapMaSo.put("traLaiChoCongDanTien", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "tienCongDan"));
				
				//13 - Tra lai cho cong dan Dat
				mapMaSo.put("traLaiChoCongDanDat", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "datCongDan"));
				
				//14 - So nguoi duoc bao ve quyen loi
				mapMaSo.put("soNguoiDuocTraLaiQuyenLoi", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiDuocTraLaiQuyenLoi(predAllDSGQDDonVi));
				
				//15 - Kien nghi xu ly hanh chinh Tong so nguoi
				mapMaSo.put("kienNghiXuLyHanhChinhTongSoNguoi", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiXuLyHanhChinh(predAllDSGQDDonVi));
				
				//16 - Kien nghi xu ly hanh chinh So nguoi da bi xu ly
				mapMaSo.put("kienNghiXuLyHanhChinhSoNguoiDaBiXuLy", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiDaBiXuLyHanhChinh(predAllDSGQDDonVi));
				
				//17 - Chuyen co quan dieu tra so vu
				mapMaSo.put("soVuChuyenCoQuanDieuTra", thongKeBaoCaoTongHopKQGQDService.getTongSoVuChuyenCoQuanDieuTra(predAllDSGQDDonVi));
				
				//18 - Chuyen co quan dieu tra so doi tuong
				mapMaSo.put("soDoiTuongChuyenCoQuanDieuTra", thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongChuyenCoQuanDieuTra(predAllDSGQDDonVi));
				
				//19 - So vu da khoi to	
				mapMaSo.put("soVuDaKhoiTo", thongKeBaoCaoTongHopKQGQDService.getTongSoVuDaKhoiTo(predAllDSGQDDonVi));
				
				//20 - So doi tuong da khoi to
				mapMaSo.put("soDoiTuongDaKhoiTo", thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongDaKhoiTo(predAllDSGQDDonVi));
				
				//21 - So vu viec giai quyet dung thoi han
				mapMaSo.put("soVuViecGiaiQuyetDungThoiHan", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetToCaoDungThoiHan(predAllDSGQDDonVi));
				
				//22 - So vu viec giai quyet qua thoi han
				mapMaSo.put("soVuViecGiaiQuyetQuaThoiHan", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetToCaoQuaThoiHan(predAllDSGQDDonVi));
				
				//23 - Tong so quyet dinh phai to chuc thuc hien trong ky bao cao
				mapMaSo.put("tongSoQuyetDinhPhaiToChucThucHien", 0L);
				
				//24 - Tong so quyet dinh phai to chuc thuc hien trong ky bao cao da thuc hien
				mapMaSo.put("tongSoQuyetDinhPhaiToChucThucHienDaThucHien", 0L);
				
				//25 - Tien phai thu cho nha nuoc
				mapMaSo.put("tienPhaiThuChoNhaNuoc", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienNhaNuocPhaiThu"));
				
				//26 - Dat phai thu cho nha nuoc
				mapMaSo.put("datPhaiThuChoNhaNuoc", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datNhaNuocPhaiThu"));
				
				//27 - Tien da thu cho nha nuoc				
				mapMaSo.put("tienDaThuChoNhaNuoc", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienNhaNuocDaThu"));
				
				//28 - Dat da thu cho nha nuoc
				mapMaSo.put("datDaThuChoNhaNuoc", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datNhaNuocDaThu"));
				
				//29 - Tien phai tra cho cong dan
				mapMaSo.put("tienPhaiTraChoCongDan", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienCongDanPhaiTra"));
				
				//30 - Dat phai tra cho cong dan
				mapMaSo.put("datPhaiTraChoCongDan", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datCongDanPhaiTra"));
				
				//31 - Tien da tra cho cong dan
				mapMaSo.put("tienDaTraChoCongDan", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienCongDanDaTra"));
				
				//32 - Dat da tra cho cong dan
				mapMaSo.put("datDaTraChoCongDan", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datCongDanDaTra"));
				
				//33 - Ghi chu
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaTiepCongDan/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp báo cáo tiếp công dân", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcel(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			@RequestParam(value = "donViId", required = false) Long donViId
			) throws IOException {
		
		try {			
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
						
			if (listDonVis != null) {
				for (CoQuanQuanLy dv : listDonVis) {
					CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
					if (coQuan != null) {
						list.add(coQuan);
					}					
				}
			} else { 
				if (donViId != null && donViId > 0)  {
					coQuanQuanLyRepo.findOne(donViId);
					list.add(coQuanQuanLyRepo.findOne(donViId));
				}
			}
			donVis.addAll(list);
			
			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQTCDService.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			LinhVucDonThu linhVucHanhChinh = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhap = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhung = linhVucDonThuRepo.findOne(39L);
			LinhVucDonThu linhVucKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucChinhTriVanHoaXaHoiKhac = linhVucDonThuRepo.findOne(13L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais.add(55L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachs.add(5L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(57L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs.add(53L);
			
			List<LinhVucDonThu> linhVucTranhChapVeDatDais = new ArrayList<LinhVucDonThu>();
			linhVucTranhChapVeDatDais.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeTranhChapVeDatDais));
			
			List<LinhVucDonThu> linhVucVeChinhSachs = new ArrayList<LinhVucDonThu>();
			linhVucVeChinhSachs.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeChinhSachs));
			
			List<LinhVucDonThu> linhVucVeNhaCuaTaiSans = new ArrayList<LinhVucDonThu>();
			linhVucVeNhaCuaTaiSans.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));
			
			List<LinhVucDonThu> linhVucVeCheDoCCVCs = new ArrayList<LinhVucDonThu>();
			linhVucVeCheDoCCVCs.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeCheDoCCVCs));
			
			
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
				mapMaSo.put("tiepCongDanThuongXuyenVuViecCu", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, false, LoaiVuViecEnum.CU));
				
				//vu viec moi phat sinh - 4
				mapMaSo.put("tiepCongDanThuongXuyenVuViecMoiPhatSinh", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, false, LoaiVuViecEnum.MOI_PHAT_SINH));
				
				//doan dong nguoi
				//so doan - 5
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiSoDoan", thongKeBaoCaoTongHopKQTCDService.getTongSoDoanDongNguoiTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true));
				
				//nguoi - 6
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiSoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, 
						false, true));
				
				//doan dong nguoi - vu viec
				//cu - 7
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiVuViecCu", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true, LoaiVuViecEnum.CU));
				
				//moi phat sinh - 8
				mapMaSo.put("tiepCongDanThuongXuyenDoanDongNguoiVuViecMoiPhatSinh", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, true, LoaiVuViecEnum.MOI_PHAT_SINH));
				
				//tiep cong dan dinh ky dot xuat cua lanh dao
				//luoc - 9
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoLuot", thongKeBaoCaoTongHopKQTCDService.getTongSoLuocTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao));
				
				//nguoi - 10
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao));
				
				//tiep cong dan cua lanh dao - vu viec
				//cu - 11
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecCu", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, false, LoaiVuViecEnum.CU));
				
				//moi phat sinh - 12
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoVuViecMoiPhatSinh", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, false, LoaiVuViecEnum.MOI_PHAT_SINH));
				
				//tiep cong dan cua lanh dao - doan dong nguoi
				//so doan - 13
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoDoan", thongKeBaoCaoTongHopKQTCDService.getTongSoDoanDongNguoiTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true));
				
				//so nguoi - 14
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiSoNguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao,
						false, true));
				
				//tiep cong dan cua lanh dao - doan dong nguoi - vu viec
				//cu - 15
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecCu", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true, LoaiVuViecEnum.CU));
				
				//moi phat sinh - 16
				mapMaSo.put("tiepCongDanDinhKyDotXuatCuaLanhDaoDoanDongNguoiVuViecMoiPhatSinh", thongKeBaoCaoTongHopKQTCDService.getDemDonLoaiVuViecTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, true, LoaiVuViecEnum.MOI_PHAT_SINH));
				
				//ve tranh chap - 17
				mapMaSo.put("linhVucKhieuNaiVeTranhChap", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi, linhVucTranhChapVeDatDais));
								
				//veChinhSach - 18
				mapMaSo.put("linhVucKhieuNaiVeChinhSach", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi, linhVucVeChinhSachs));

				//veNhaCuaVaTaiSan - 19
				mapMaSo.put("linhVucKhieuNaiVeNhaCuaVaTaiSan", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi, linhVucVeNhaCuaTaiSans));
										
				//veCheDoCC,VC - 20
				mapMaSo.put("linhVucKhieuNaiVeCheDoCCVC", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCha(predAllDSTCDDonVi, linhVucVeCheDoCCVCs));
				
				//linhVucTuPhap - 21
				mapMaSo.put("linhVucKhieuNaiTuPhap", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi, linhVucKhieuNaiTuPhap));

				//linhVucChinhTriVanHoaXaHoi - 22
				mapMaSo.put("linhVucKhieuNaiChinhTriVanHoaXaHoiKhac", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi, linhVucChinhTriVanHoaXaHoiKhac));
				
				//don to cao
				//linhVucHanhChinh - 23
				mapMaSo.put("linhVucToCaoHanhChinh", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucDonThu(predAllDSTCDDonVi, linhVucHanhChinh));
				
				//linhVucHanhChinh - 24
				mapMaSo.put("linhVucToCaoTuPhap", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucDonThu(predAllDSTCDDonVi, linhVucTuPhap));
				
				//linhVucThamNhung - 25
				mapMaSo.put("linhVucToCaoThamNhung", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucDonThu(predAllDSTCDDonVi, linhVucThamNhung));
				
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
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
			PersistentEntityResourceAssembler eass) {
		try {
			
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			
			Long donViXuLy = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
			
			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
			
			if (listDonVis != null) {
				for (CoQuanQuanLy dv : listDonVis) {
					CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
					if (coQuan != null) {
						list.add(coQuan);
					}					
				}
			} else {
				coQuanQuanLyRepo.findOne(donViXuLy);
				list.add(coQuanQuanLyRepo.findOne(donViXuLy));			
			}
			donVis.addAll(list);
			
			if (year == null) { 
				year = Utils.localDateTimeNow().getYear();
			}
			if (quy == null) {
				quy = Utils.getQuyHienTai();
			}
			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllXLD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLDTruocHan = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllXLDTruocHan(loaiKy, quy, year, month, tuNgay, denNgay);
			
			//khieu nai
			List<Long> idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(59L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(58L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(57L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(54L);
			
			LinhVucDonThu linhVucHanhChinhDonKhieuNai = linhVucDonThuRepo.findOne(1L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiVeDang = linhVucDonThuRepo.findOne(56L);
			
			//to cao
			LinhVucDonThu linhVucHanhChinhDonToCao = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhapDonToCao = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhungDonToCao = linhVucDonThuRepo.findOne(39L);
			LinhVucDonThu linhVucVeDangDonToCao = linhVucDonThuRepo.findOne(62L);
			LinhVucDonThu linhVucKhacDonToCao = linhVucDonThuRepo.findOne(63L);
			
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
			linhVucVeNhaCuaTaiSans.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDXLDDonViTruocHan = predAllDSXLDTruocHan;
				BooleanExpression predAllDXLDDonVi = predAllDSXLD;
				BooleanExpression predAllDSTCDDonVi = predAllDSTCD;
				predAllDSTCDDonVi = predAllDSTCDDonVi.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(cq.getId())
						.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(cq.getId()))
						.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(cq.getId())));
				
				predAllDXLDDonVi = predAllDXLDDonVi.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId())
						.or(QXuLyDon.xuLyDon.donViXuLy.cha.id.eq(cq.getId()))
						.or(QXuLyDon.xuLyDon.donViXuLy.cha.cha.id.eq(cq.getId())));
				
				predAllDXLDDonViTruocHan = predAllDXLDDonViTruocHan.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId())
						.or(QXuLyDon.xuLyDon.donViXuLy.cha.id.eq(cq.getId()))
						.or(QXuLyDon.xuLyDon.donViXuLy.cha.cha.id.eq(cq.getId())));
				
				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("donVi", mapDonVi);
				
				Long tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTiepNhanTrongKyDonCoNhieuNguoiDungTenXLDTCD(predAllDXLDDonVi,
						loaiKy, quy, month, tuNgay, denNgay);
				Long tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTiepNhanTrongKyDonCoMotNguoiDungTenXLDTCD(predAllDXLDDonVi,
						loaiKy, quy, month, tuNgay, denNgay);
				Long tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang = thongKeBaoCaoTongHopKQXLDService.getTongSoDonKyTruocChuyenSangDonCoNhieuNguoiDungTenXLDTCD(predAllDXLDDonViTruocHan,
						loaiKy, year, quy, month, tuNgay, denNgay);
				Long tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang = thongKeBaoCaoTongHopKQXLDService.getTongSoKyTruocChuyenSangDonCoMotNguoiDungTenXLDTCD(predAllDXLDDonViTruocHan,
						loaiKy, year, quy, month, tuNgay, denNgay);
				Long tongSoDonTiepNhanXLDTCD = tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy + tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy + tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang + 
						tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang;
					
				//tong so don - 1
				mapMaSo.put("tongSoDonTiepNhanXLDTCD", tongSoDonTiepNhanXLDTCD);
				
				//don co nhieu nguoi dung ten - trong ky - 2
				mapMaSo.put("tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy", tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy);
				
				//don co mot nguoi dung ten - trong ky - 3
				mapMaSo.put("tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy", tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy);
				
				//don co nhieu nguoi dung ten - truoc ky - 4
				mapMaSo.put("tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang", tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang);
				
				//don co mot nguoi dung ten - truoc ky - 5
				mapMaSo.put("tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang", tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang);
				
				Long tongSoDonCoHXLLuDonVaTheoDoi = thongKeBaoCaoTongHopKQXLDService.getTongSoDonDuDieuKienThuLyLuuDonVaTheoDoi(predAllDXLDDonVi);
				Long tongSoDonDuDieuKienThuLy = tongSoDonTiepNhanXLDTCD - tongSoDonCoHXLLuDonVaTheoDoi;
				//don du dieu kien xu ly - 6
				mapMaSo.put("tongSoDonDuDieuKienThuLy", tongSoDonDuDieuKienThuLy);
				
				Long tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi, 
						linhVucHanhChinhDonKhieuNai, linhVucLienQuanDenDatDais);
				Long tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi, 
						linhVucHanhChinhDonKhieuNai, linhVucVeNhaCuaTaiSans);
				Long tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi,
						linhVucHanhChinhDonKhieuNai, linhVucVeChinhSachCCVCs);
				Long tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi, 
						linhVucHanhChinhDonKhieuNai, linhVucCTVHXHKKhacs);
				Long tongSoDonKhieuNaiLinhVucHanhChinh = tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai + tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan + tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC
						+ tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac;
				//tong - 7
				mapMaSo.put("tongSoDonKhieuNaiLinhVucHanhChinh", tongSoDonKhieuNaiLinhVucHanhChinh);
				
				//lien quan den dat dai - 8
				mapMaSo.put("tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai", tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai);
				
				//ve nha tai san - 9
				mapMaSo.put("tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan", tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan);
				
				//ve chinh sach che do, cc, vc - 10
				mapMaSo.put("tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC", tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC);
				
				//linh vuc kinh te chinh tri xa hoi khac - 11
				mapMaSo.put("tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac", tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac);
				
				//linh vuc tu phap - 12
				mapMaSo.put("tongSoDonKhieuNaiLinhVucTuPhap",  thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucHanhChinhKhieuNaiTuPhap));
				
				//linh vuc ve dang - 13
				mapMaSo.put("tongSoDonKhieuNaiLinhVucVeDang", thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucHanhChinhKhieuNaiVeDang));
				
				Long tongSoDonToCaoLinhVucHanhChinh = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucHanhChinhDonToCao);
				Long tongSoDonToCaoLinhVucTuPhap = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucTuPhapDonToCao);
				Long tongSoDonToCaoLinhVucThamNhung = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucChiTietCha(predAllDXLDDonVi, linhVucTuPhapDonToCao, linhVucThamNhungDonToCao);
				Long tongSoDonToCaoLinhVucVeDang = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucVeDangDonToCao);
				Long tongSoDonToCaoLinhVucKhac= thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucKhacDonToCao);
				Long tongSoDonLinhVucToCao = tongSoDonToCaoLinhVucHanhChinh + tongSoDonToCaoLinhVucTuPhap + tongSoDonToCaoLinhVucThamNhung + tongSoDonToCaoLinhVucVeDang+ tongSoDonToCaoLinhVucKhac;
				
				//tong so don linh vuc to cao - 14
				mapMaSo.put("tongSoDonLinhVucToCao", tongSoDonLinhVucToCao);
				
				//don to cao linh vuc hanh chinh - 15
				mapMaSo.put("tongSoDonToCaoLinhVucHanhChinh", tongSoDonToCaoLinhVucHanhChinh);
				
				//don to cao linh vuc tu phap - 16
				mapMaSo.put("tongSoDonToCaoLinhVucTuPhap", tongSoDonToCaoLinhVucTuPhap);
				
				//don to cao linh vuc tham nhung - 17 // ?
				mapMaSo.put("tongSoDonToCaoLinhVucThamNhung", tongSoDonToCaoLinhVucThamNhung);
								
				//don to cao linh vuc ve dang - 18
				mapMaSo.put("tongSoDonToCaoLinhVucVeDang", tongSoDonToCaoLinhVucVeDang);

				//don to cao linh vuc khac - 19
				mapMaSo.put("tongSoDonToCaoLinhVucKhac", tongSoDonToCaoLinhVucKhac);
										
				//theo tham quyen giai quyet - cua cac co quan hanh chinh cac cap - 20
				mapMaSo.put("tongSoDonTheoTQGQCuaCacCoQuanHanhChinhCacCap", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoThamQuyenGiaiQuyet(predAllDXLDDonVi, thamQuyenGiaiQuyetHanhChinh));
				
				//theo tham quyen giai quyet - cua cac co quan tu phap cac cap - 21
				mapMaSo.put("tongSoDonTheoTQGQCuaCacCoQuanTuPhapCacCap", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoThamQuyenGiaiQuyet(predAllDXLDDonVi, thamQuyenGiaiQuyetTuPhap));

				//theo tham quyen giai quyet - cua co quan dang - 22
				mapMaSo.put("tongSoDonTheoTQGQCuaCoQuanDang", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoThamQuyenGiaiQuyet(predAllDXLDDonVi, thamQuyenGiaiQuyetCQDang));
				
				//theo trinh tu giai quyet - chua giai quyet - 23
				mapMaSo.put("tongSoDonTheoTTGiaiQuyetChuaGiaiQuyet", 0L);
				
				//theo trinh tu giai quyet - da duoc giai quyet lan dau - 24
				mapMaSo.put("tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyetLanDau", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoTrinhTuGiaiQuyetDaDuocGiaiQuyetLanDau(predAllDXLDDonVi));
				
				//theo trinh tu giai quyet - da duoc giai quyet nhieu lan - 25
				mapMaSo.put("tongSoDonTheoTTGiaiQuyetDaDuocGiaiQuyetNhieuLan", thongKeBaoCaoTongHopKQXLDService.getTongSoDonXLDTheoTrinhTuGiaiQuyetDaDuocGiaiQuyetNhieuLan(predAllDXLDDonVi));
				
				//don kien nghi phan anh - 26
				mapMaSo.put("tongSoDonXLDDonKienNghiPhanAnh", thongKeBaoCaoTongHopKQXLDService.getTongSoDonKienNghiPhanAnhHXLLuuDonVaTheoDoi(predAllDXLDDonVi));
				
				//so van ban huong dan - 27
				mapMaSo.put("tongSoDonXLDSoVanBanHuongDan", thongKeBaoCaoTongHopKQXLDService.getTongSoDonHXLTraDonVaHuongDan(predAllDXLDDonVi));

				//so don chuyen co tham quyen - 28
				mapMaSo.put("tongSoDonChuyenCQCoThamQuyen", thongKeBaoCaoTongHopKQXLDService.getTongSoDonChuyenCQCoThamQuyen(predAllDXLDDonVi));
				
				//so cong van don doc viec giai quyet - 29
				mapMaSo.put("tongSoDonCoSoCongVanDonDocViecGiaiQuyet", 0L);
				
				//tong so don thuoc tham quyen khieu nai - 30
				mapMaSo.put("tongSoDonThuocThamQuyenKhieuNai", thongKeBaoCaoTongHopKQXLDService.getTongSoDonThuocThamQuyenKhieuNai(predAllDXLDDonVi));
				
				//tong so don thuoc tham quyen to cao - 31
				mapMaSo.put("tongSoDonThuocThamQuyenToCao", thongKeBaoCaoTongHopKQXLDService.getTongSoDonThuocThamQuyenToCao(predAllDXLDDonVi));
				
				//ghi chu - 32
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
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaXuLyDonThu/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp báo cáo xử lý đơn thư", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExceluLyDonThu(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis
			) throws IOException {
		
		try {			
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
						
			if (listDonVis != null) {
				for (CoQuanQuanLy dv : listDonVis) {
					CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
					if (coQuan != null) {
						list.add(coQuan);
					}					
				}
			} else { 
				if (donViId != null && donViId > 0)  {
					coQuanQuanLyRepo.findOne(donViId);
					list.add(coQuanQuanLyRepo.findOne(donViId));
				}
			}
			donVis.addAll(list);
			
			if (year == null) { 
				year = Utils.localDateTimeNow().getYear();
			}
			
			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllXLD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLDTruocHan = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllXLDTruocHan(loaiKy, quy, year, month, tuNgay, denNgay);
			
			//khieu nai
			List<Long> idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(59L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(58L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(57L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(54L);
			
			LinhVucDonThu linhVucHanhChinhDonKhieuNai = linhVucDonThuRepo.findOne(1L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiVeDang = linhVucDonThuRepo.findOne(56L);
			
			//to cao
			LinhVucDonThu linhVucHanhChinhDonToCao = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhapDonToCao = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhungDonToCao = linhVucDonThuRepo.findOne(39L);
			LinhVucDonThu linhVucVeDangDonToCao = linhVucDonThuRepo.findOne(62L);
			LinhVucDonThu linhVucKhacDonToCao = linhVucDonThuRepo.findOne(63L);
			
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
			linhVucVeNhaCuaTaiSans.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDXLDDonViTruocHan = predAllDSXLDTruocHan;
				BooleanExpression predAllDXLDDonVi = predAllDSXLD;
				BooleanExpression predAllDSTCDDonVi = predAllDSTCD;
				predAllDSTCDDonVi = predAllDSTCDDonVi.and(QSoTiepCongDan.soTiepCongDan.donViTiepDan.id.eq(cq.getId())
						.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.id.eq(cq.getId()))
						.or(QSoTiepCongDan.soTiepCongDan.donViTiepDan.cha.cha.id.eq(cq.getId())));
				
				predAllDXLDDonVi = predAllDXLDDonVi.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId())
						.or(QXuLyDon.xuLyDon.donViXuLy.cha.id.eq(cq.getId()))
						.or(QXuLyDon.xuLyDon.donViXuLy.cha.cha.id.eq(cq.getId())));
				
				predAllDXLDDonViTruocHan = predAllDXLDDonViTruocHan.and(QXuLyDon.xuLyDon.donViXuLy.id.eq(cq.getId())
						.or(QXuLyDon.xuLyDon.donViXuLy.cha.id.eq(cq.getId()))
						.or(QXuLyDon.xuLyDon.donViXuLy.cha.cha.id.eq(cq.getId())));
				
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("0", cq.getTen());
				
				Long tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTiepNhanTrongKyDonCoNhieuNguoiDungTenXLDTCD(predAllDXLDDonVi,
						loaiKy, quy, month, tuNgay, denNgay);
				Long tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTiepNhanTrongKyDonCoMotNguoiDungTenXLDTCD(predAllDXLDDonVi,
						loaiKy, quy, month, tuNgay, denNgay);
				Long tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang = thongKeBaoCaoTongHopKQXLDService.getTongSoDonKyTruocChuyenSangDonCoNhieuNguoiDungTenXLDTCD(predAllDXLDDonViTruocHan,
						loaiKy, year, quy, month, tuNgay, denNgay);
				Long tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang = thongKeBaoCaoTongHopKQXLDService.getTongSoKyTruocChuyenSangDonCoMotNguoiDungTenXLDTCD(predAllDXLDDonViTruocHan,
						loaiKy, year, quy, month, tuNgay, denNgay);
				Long tongSoDonTiepNhanXLDTCD = tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy + tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy + tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang + 
						tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang;
				
				mapMaSo.put("1", tongSoDonTiepNhanXLDTCD);
				mapMaSo.put("2", tongSoDonCoNhieuNguoiDungTenTiepNhanDonTiepNhanDonTrongKy);
				mapMaSo.put("3", tongSoDonCoMotNguoiDungTenTiepNhanDonTiepNhanDonTrongKy);
				mapMaSo.put("4", tongSoDonCoNhieuNguoiDungTenTiepNhanDonDonKyTruocChuyenSang);
				mapMaSo.put("5", tongSoDonCoMotNguoiDungTenTiepNhanDonDonKyTruocChuyenSang);
				
				Long tongSoDonCoHXLLuDonVaTheoDoi = thongKeBaoCaoTongHopKQXLDService.getTongSoDonDuDieuKienThuLyLuuDonVaTheoDoi(predAllDXLDDonVi);
				Long tongSoDonDuDieuKienThuLy = tongSoDonTiepNhanXLDTCD - tongSoDonCoHXLLuDonVaTheoDoi;
				mapMaSo.put("6", tongSoDonDuDieuKienThuLy);
				
				Long tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi, 
						linhVucHanhChinhDonKhieuNai, linhVucLienQuanDenDatDais);
				Long tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi, 
						linhVucHanhChinhDonKhieuNai, linhVucVeNhaCuaTaiSans);
				Long tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi,
						linhVucHanhChinhDonKhieuNai, linhVucVeChinhSachCCVCs);
				Long tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucHanhChinhChiTiet(predAllDXLDDonVi, 
						linhVucHanhChinhDonKhieuNai, linhVucCTVHXHKKhacs);
				Long tongSoDonKhieuNaiLinhVucHanhChinh = tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai + tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan + tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC
						+ tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac;
				
				mapMaSo.put("7", tongSoDonKhieuNaiLinhVucHanhChinh);
				mapMaSo.put("8", tongSoDonKhieuNaiLinhVucHanhChinhLienQuanDenDatDai);
				mapMaSo.put("9", tongSoDonKhieuNaiLinhVucHanhChinhVeNhaVaTaiSan);
				mapMaSo.put("10", tongSoDonKhieuNaiLinhVucHanhChinhVeChinhSachCheDoCCVC);
				mapMaSo.put("11", tongSoDonKhieuNaiLinhVucHanhChinhLinhVucKTCTXHKhac);
				mapMaSo.put("12", thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucHanhChinhKhieuNaiTuPhap));
				
				mapMaSo.put("13", thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonKhieuNaiTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucHanhChinhKhieuNaiVeDang));
				
				Long tongSoDonToCaoLinhVucHanhChinh = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucHanhChinhDonToCao);
				Long tongSoDonToCaoLinhVucTuPhap = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucTuPhapDonToCao);
				Long tongSoDonToCaoLinhVucThamNhung = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucChiTietCha(predAllDXLDDonVi, linhVucTuPhapDonToCao, linhVucThamNhungDonToCao);
				Long tongSoDonToCaoLinhVucVeDang = thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucVeDangDonToCao);
				Long tongSoDonToCaoLinhVucKhac= thongKeBaoCaoTongHopKQXLDService.getTongSoDonTCDPhanLoaiDonToCaoTheoNoiDungLinhVucCha(predAllDXLDDonVi, linhVucKhacDonToCao);
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
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis
			) throws IOException {
		
		try {		
			
			if (year == null || year == 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
//			ThamSo thamSoCCQQLUBNDThanhPho = repoThamSo.findOne(thamSoService.predicateFindTen("CQQL_UBNDTP_DA_NANG"));
//			ThamSo thamSoCCQQLUBNDSoBanNganh = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_SO_BAN_NGANH"));
			ThamSo phongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo phuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));			
			Long idCapCQQLPhongBan = Long.valueOf(phongBan.getGiaTri().toString());
			Long idCapCQQLPhuongXa = Long.valueOf(phuongXa.getGiaTri().toString());
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
//			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
//			
			List<Map<String, Object>> maSos = new ArrayList<>();
//			capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
//			List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTHTKBC(
//					Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds,
//					"CQQL_UBNDTP_DA_NANG", repoThamSo, thamSoService));
//			donVis.addAll(list);
			if (listDonVis != null) {
				for (CoQuanQuanLy dv : listDonVis) {
					CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
					if (coQuan != null) {
						donVis.add(coQuan);
					}				
				}
			}			
			
			BooleanExpression predAllDSDon = (BooleanExpression) thongKeBaoCaoTongHopKQGQDService.predicateFindAllGQD(loaiKy, quy, year, month, tuNgay, denNgay);
			predAllDSDon = predAllDSDon.and(QDon.don.loaiDon.eq(LoaiDonEnum.DON_KHIEU_NAI))
					.and(QDon.don.thanhLapDon.eq(true))
					.and(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.DE_XUAT_THU_LY));
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSGQDDonVi = predAllDSDon;
				predAllDSGQDDonVi = predAllDSGQDDonVi.and(QDon.don.donViXuLyGiaiQuyet.id.eq(cq.getId())
						.or(QDon.don.donViXuLyGiaiQuyet.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))
						.or(QDon.don.donViXuLyGiaiQuyet.cha.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))						
						);	
						
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("0", cq.getTen());
				mapMaSo.put("1", thongKeBaoCaoTongHopKQGQDService.getTongSoDon(predAllDSGQDDonVi));
				ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
				Long donTonKyTruocChuyenSang = loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON) && StringUtils.isBlank(tuNgay) ? 
						0 : thongKeBaoCaoTongHopKQGQDService.getTongSoDonTonKyTruoc(predAllDSGQDDonVi, loaiKy, quy, year, month, tuNgay, denNgay);
				mapMaSo.put("2", thongKeBaoCaoTongHopKQGQDService.getTongSoDonTrongKyBaoCao(predAllDSGQDDonVi, loaiKy, quy, year, month, tuNgay, denNgay));
				mapMaSo.put("3", donTonKyTruocChuyenSang);
				mapMaSo.put("4", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViec(predAllDSGQDDonVi));
				mapMaSo.put("5", thongKeBaoCaoTongHopKQGQDService.getTongSoDonKhieuNaiThuocThamQuyen(predAllDSGQDDonVi));
				mapMaSo.put("6", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecToCaoThuocThamQuyen(predAllDSGQDDonVi));
				mapMaSo.put("7", thongKeBaoCaoTongHopKQGQDService.getTongSoDonCoQuyetDinh(predAllDSGQDDonVi));
				mapMaSo.put("8", thongKeBaoCaoTongHopKQGQDService.getTongSoDonDinhChi(predAllDSGQDDonVi));
				mapMaSo.put("9", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiDung(predAllDSGQDDonVi));
				mapMaSo.put("10", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiSai(predAllDSGQDDonVi));
				mapMaSo.put("11", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecDungMotPhan(predAllDSGQDDonVi));
				mapMaSo.put("12", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetLan1(predAllDSGQDDonVi));
				mapMaSo.put("13", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetLan2CongNhan(predAllDSGQDDonVi));
				mapMaSo.put("14", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetLan2HuySua(predAllDSGQDDonVi));
				mapMaSo.put("15", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "tienNhaNuoc"));
				mapMaSo.put("16", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "datNhaNuoc"));
				mapMaSo.put("17", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "tienCongDan"));
				mapMaSo.put("18", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "datCongDan"));
				mapMaSo.put("19", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiDuocTraLaiQuyenLoi(predAllDSGQDDonVi));
				mapMaSo.put("20", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiXuLyHanhChinh(predAllDSGQDDonVi));
				mapMaSo.put("21", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiDaBiXuLyHanhChinh(predAllDSGQDDonVi));
				mapMaSo.put("22", thongKeBaoCaoTongHopKQGQDService.getTongSoVuChuyenCoQuanDieuTra(predAllDSGQDDonVi));
				mapMaSo.put("23", thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongChuyenCoQuanDieuTra(predAllDSGQDDonVi));
				mapMaSo.put("24", thongKeBaoCaoTongHopKQGQDService.getTongSoVuDaKhoiTo(predAllDSGQDDonVi));
				mapMaSo.put("25", thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongDaKhoiTo(predAllDSGQDDonVi));
				mapMaSo.put("26", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetKhieuNaiDungThoiHan(predAllDSGQDDonVi));
				mapMaSo.put("27", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetKhieuNaiQuaThoiHan(predAllDSGQDDonVi));
				mapMaSo.put("28", 0L);
				mapMaSo.put("29", 0L);
				mapMaSo.put("30", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienNhaNuocPhaiThu"));
				mapMaSo.put("31", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datNhaNuocPhaiThu"));
				mapMaSo.put("32", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienNhaNuocDaThu"));
				mapMaSo.put("33", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datNhaNuocDaThu"));
				mapMaSo.put("34", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienCongDanPhaiTra"));
				mapMaSo.put("35", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datCongDanPhaiTra"));
				mapMaSo.put("36", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienCongDanDaTra"));
				mapMaSo.put("37", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datCongDanDaTra"));
				mapMaSo.put("38", "");
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
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis
			) throws IOException {
		
		try {		
			
			if (year == null || year == 0) {
				year = Calendar.getInstance().get(Calendar.YEAR);
			}
			
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			ThamSo phongBan = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_PHONG_BAN"));
			ThamSo phuongXa = repoThamSo.findOne(thamSoService.predicateFindTen("CCQQL_UBND_PHUONG_XA_THI_TRAN"));			
			Long idCapCQQLPhongBan = Long.valueOf(phongBan.getGiaTri().toString());
			Long idCapCQQLPhuongXa = Long.valueOf(phuongXa.getGiaTri().toString());
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			if (listDonVis != null) {
				for (CoQuanQuanLy dv : listDonVis) {
					CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
					if (coQuan != null) {
						donVis.add(coQuan);
					}				
				}
			}
			
			BooleanExpression predAllDSDon = (BooleanExpression) thongKeBaoCaoTongHopKQGQDService.predicateFindAllGQD(loaiKy, quy, year, month, tuNgay, denNgay);
			predAllDSDon = predAllDSDon.and(QDon.don.loaiDon.eq(LoaiDonEnum.DON_TO_CAO))
					.and(QDon.don.thanhLapDon.eq(true))
					.and(QDon.don.huongXuLyXLD.eq(HuongXuLyXLDEnum.DE_XUAT_THU_LY));
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSGQDDonVi = predAllDSDon;
				predAllDSGQDDonVi = predAllDSGQDDonVi.and(QDon.don.donViXuLyGiaiQuyet.id.eq(cq.getId())
						.or(QDon.don.donViXuLyGiaiQuyet.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))
						.or(QDon.don.donViXuLyGiaiQuyet.cha.cha.id.eq(cq.getId())
								.and(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhongBan)
										.or(QDon.don.donViXuLyGiaiQuyet.capCoQuanQuanLy.id.eq(idCapCQQLPhuongXa))))						
						);		
						
				mapMaSo = new HashMap<String, Object>();
				mapMaSo.put("0", cq.getTen());
				mapMaSo.put("1", thongKeBaoCaoTongHopKQGQDService.getTongSoDon(predAllDSGQDDonVi));
				ThongKeBaoCaoLoaiKyEnum loaiKyEnum = ThongKeBaoCaoLoaiKyEnum.valueOf(loaiKy);
				Long donTonKyTruocChuyenSang = loaiKyEnum.equals(ThongKeBaoCaoLoaiKyEnum.TUY_CHON) && StringUtils.isBlank(tuNgay) ? 
						0 : thongKeBaoCaoTongHopKQGQDService.getTongSoDonTonKyTruoc(predAllDSGQDDonVi, loaiKy, quy, year, month, tuNgay, denNgay);
				mapMaSo.put("2", thongKeBaoCaoTongHopKQGQDService.getTongSoDonTrongKyBaoCao(predAllDSGQDDonVi, loaiKy, quy, year, month, tuNgay, denNgay));
				mapMaSo.put("3", donTonKyTruocChuyenSang);
				mapMaSo.put("4", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViec(predAllDSGQDDonVi));
				mapMaSo.put("5", thongKeBaoCaoTongHopKQGQDService.getTongSoDonToCaoThuocThamQuyen(predAllDSGQDDonVi));
				mapMaSo.put("6", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecToCaoThuocThamQuyen(predAllDSGQDDonVi));
				mapMaSo.put("7", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiDung(predAllDSGQDDonVi));
				mapMaSo.put("8", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecKhieuNaiSai(predAllDSGQDDonVi));
				mapMaSo.put("9", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecDungMotPhan(predAllDSGQDDonVi));
				mapMaSo.put("10", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "tienNhaNuoc"));
				mapMaSo.put("11", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "datNhaNuoc"));
				mapMaSo.put("12", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "tienCongDan"));
				mapMaSo.put("13", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuVeQDGQ(predAllDSGQDDonVi, "datCongDan"));
				mapMaSo.put("14", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiDuocTraLaiQuyenLoi(predAllDSGQDDonVi));
				mapMaSo.put("15", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiXuLyHanhChinh(predAllDSGQDDonVi));
				mapMaSo.put("16", thongKeBaoCaoTongHopKQGQDService.getTongSoNguoiDaBiXuLyHanhChinh(predAllDSGQDDonVi));
				mapMaSo.put("17", thongKeBaoCaoTongHopKQGQDService.getTongSoVuChuyenCoQuanDieuTra(predAllDSGQDDonVi));
				mapMaSo.put("18", thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongChuyenCoQuanDieuTra(predAllDSGQDDonVi));
				mapMaSo.put("19", thongKeBaoCaoTongHopKQGQDService.getTongSoVuDaKhoiTo(predAllDSGQDDonVi));
				mapMaSo.put("20", thongKeBaoCaoTongHopKQGQDService.getTongSoDoiTuongDaKhoiTo(predAllDSGQDDonVi));
				mapMaSo.put("21", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetToCaoDungThoiHan(predAllDSGQDDonVi));
				mapMaSo.put("22", thongKeBaoCaoTongHopKQGQDService.getTongSoVuViecGiaiQuyetToCaoQuaThoiHan(predAllDSGQDDonVi));
				mapMaSo.put("23", 0L);
				mapMaSo.put("24", 0L);
				mapMaSo.put("25", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienNhaNuocPhaiThu"));
				mapMaSo.put("26", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datNhaNuocPhaiThu"));
				mapMaSo.put("27", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienNhaNuocDaThu"));
				mapMaSo.put("28", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datNhaNuocDaThu"));
				mapMaSo.put("29", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienCongDanPhaiTra"));
				mapMaSo.put("30", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datCongDanPhaiTra"));
				mapMaSo.put("31", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "tienCongDanDaTra"));
				mapMaSo.put("32", thongKeBaoCaoTongHopKQGQDService.getTongSoTienDatPhaiThuTra(predAllDSGQDDonVi, "datCongDanDaTra"));
				mapMaSo.put("33", "");
				maSos.add(mapMaSo);
				mapMaSo = new HashMap<String, Object>();
			}
			map.put("maSos", maSos);
			ExcelUtil.exportTongHopBaoCaoGiaiQuyetToCao(response, "DanhSachTongHopThongKeBaoCaoGiaiQuyetToCao", "sheetName", maSos, tuNgay, denNgay, "Danh sách tổng hợp thống kê báo cáo giải quyết đơn tố cáo");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	
//	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoHanhChinh")
//	@ApiOperation(value = "Tổng hợp kết quả thanh tra theo hành chính", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
//	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaThanhTraTheoHanhChinh(@RequestHeader(value = "Authorization", required = true) String authorization,
//			Pageable pageable,
//			@RequestParam(value = "loaiKy", required = false) String loaiKy,
//			@RequestParam(value = "tuNgay", required = false) String tuNgay,
//			@RequestParam(value = "denNgay", required = false) String denNgay,
//			@RequestParam(value = "quy", required = false) Integer quy,
//			@RequestParam(value = "year", required = false) Integer year,
//			@RequestParam(value = "month", required = false) Integer month,
//			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis,
//			PersistentEntityResourceAssembler eass) {
//		try {
//			
//			if (Utils.tokenValidate(profileUtil, authorization) == null) {
//				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
//						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
//			}
//			
//			Long donViXuLy = Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("donViId").toString());
//			
//			Map<String, Object> mapDonVi = new HashMap<>();
//			Map<String, Object> map = new HashMap<>();
//			Map<String, Object> mapMaSo = new HashMap<>();
//			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
//			List<Map<String, Object>> maSos = new ArrayList<>();
//			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
//			
//			if (listDonVis != null) {
//				for (CoQuanQuanLy dv : listDonVis) {
//					CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
//					if (coQuan != null) {
//						list.add(coQuan);
//					}					
//				}
//			} else {
//				coQuanQuanLyRepo.findOne(donViXuLy);
//				list.add(coQuanQuanLyRepo.findOne(donViXuLy));			
//			}
//			donVis.addAll(list);
//			
//			return Utils.responseInternalServerErrors();
//		} catch (Exception e) {
//			return Utils.responseInternalServerErrors(e);
//		}
//	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoHanhChinh/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả thanh tra theo hành chính", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelKetQuaThanhTraTheoHanhChinh(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis
			) throws IOException {
		
		try {			
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
						
			if (listDonVis != null) {
				for (CoQuanQuanLy dv : listDonVis) {
					CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
					if (coQuan != null) {
						list.add(coQuan);
					}					
				}
			} else { 
				if (donViId != null && donViId > 0)  {
					coQuanQuanLyRepo.findOne(donViId);
					list.add(coQuanQuanLyRepo.findOne(donViId));
				}
			}
			donVis.addAll(list);
			
			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllXLD(loaiKy, quy, year, month, tuNgay, denNgay);
			
			//khieu nai
			List<Long> idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(59L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(58L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(57L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(54L);
			
			LinhVucDonThu linhVucHanhChinhDonKhieuNai = linhVucDonThuRepo.findOne(1L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiVeDang = linhVucDonThuRepo.findOne(56L);
			
			//to cao
			LinhVucDonThu linhVucHanhChinhDonToCao = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhapDonToCao = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhungDonToCao = linhVucDonThuRepo.findOne(39L);
			LinhVucDonThu linhVucVeDangDonToCao = linhVucDonThuRepo.findOne(62L);
			LinhVucDonThu linhVucKhacDonToCao = linhVucDonThuRepo.findOne(63L);
			
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
			linhVucVeNhaCuaTaiSans.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));
			
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
			ExcelUtil.exportTongHopKetQuaThanhTraTheoHanhChinh(response, "DanhSachTongHopKetQuaThanhTraTheoHanhChinh", "sheetName", maSos, tuNgay, denNgay, "Thống kê đối với tất cả các cuộc thanh tra chọn LĨNH VỰC THANH TRA là HÀNH CHÍNH");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoDauTuXayDungCoBan/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả thanh tra theo lĩnh vực đầu tư xây dựng cơ bản", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelKetQuaThanhTraTheoDauTuXayDungCoBan(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis
			) throws IOException {
		
		try {			
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
						
			if (listDonVis != null) {
				for (CoQuanQuanLy dv : listDonVis) {
					CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
					if (coQuan != null) {
						list.add(coQuan);
					}					
				}
			} else { 
				if (donViId != null && donViId > 0)  {
					coQuanQuanLyRepo.findOne(donViId);
					list.add(coQuanQuanLyRepo.findOne(donViId));
				}
			}
			donVis.addAll(list);
			
			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllXLD(loaiKy, quy, year, month, tuNgay, denNgay);
			
			//khieu nai
			List<Long> idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(59L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(58L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(57L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(54L);
			
			LinhVucDonThu linhVucHanhChinhDonKhieuNai = linhVucDonThuRepo.findOne(1L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiVeDang = linhVucDonThuRepo.findOne(56L);
			
			//to cao
			LinhVucDonThu linhVucHanhChinhDonToCao = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhapDonToCao = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhungDonToCao = linhVucDonThuRepo.findOne(39L);
			LinhVucDonThu linhVucVeDangDonToCao = linhVucDonThuRepo.findOne(62L);
			LinhVucDonThu linhVucKhacDonToCao = linhVucDonThuRepo.findOne(63L);
			
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
			linhVucVeNhaCuaTaiSans.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));
			
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
			ExcelUtil.exportTongHopKetQuaThanhTraTheoDauTuXayDungCoBan(response, "DanhSachTongHopKetQuaThanhTraTheoDauTuXayDungCoBan ", "sheetName", maSos, tuNgay, denNgay, "TỔNG HỢP KẾT QUẢ THANH TRA TRONG LĨNH VỰC ĐẦU TƯ XÂY DỰNG CƠ BẢN");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoTaiChinhNganSach/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả thanh tra theo lĩnh vực đầu tư xây dựng cơ bản", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelKetQuaThanhTraTheoTaiChinhNganSach(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis
			) throws IOException {
		
		try {			
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
						
			if (listDonVis != null) {
				for (CoQuanQuanLy dv : listDonVis) {
					CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
					if (coQuan != null) {
						list.add(coQuan);
					}					
				}
			} else { 
				if (donViId != null && donViId > 0)  {
					coQuanQuanLyRepo.findOne(donViId);
					list.add(coQuanQuanLyRepo.findOne(donViId));
				}
			}
			donVis.addAll(list);
			
			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllXLD(loaiKy, quy, year, month, tuNgay, denNgay);
			
			//khieu nai
			List<Long> idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(59L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(58L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(57L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(54L);
			
			LinhVucDonThu linhVucHanhChinhDonKhieuNai = linhVucDonThuRepo.findOne(1L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiVeDang = linhVucDonThuRepo.findOne(56L);
			
			//to cao
			LinhVucDonThu linhVucHanhChinhDonToCao = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhapDonToCao = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhungDonToCao = linhVucDonThuRepo.findOne(39L);
			LinhVucDonThu linhVucVeDangDonToCao = linhVucDonThuRepo.findOne(62L);
			LinhVucDonThu linhVucKhacDonToCao = linhVucDonThuRepo.findOne(63L);
			
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
			linhVucVeNhaCuaTaiSans.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));
			
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
			ExcelUtil.exportTongHopKetQuaThanhTraTheoTaiChinhNganSach(response, "DanhSachTongHopKetQuaThanhTraTheoTaiChinhNganSach", "sheetName", maSos, tuNgay, denNgay, "TỔNG HỢP KẾT QUẢ THANH TRA TRONG LĨNH VỰC TÀI CHÍNH NGÂN SÁCH");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaThanhTraTheoDatDai/xuatExcel")
	@ApiOperation(value = "Xuất file excel tổng hợp kết quả thanh tra theo lĩnh vực đất đai", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	public void exportExcelKetQuaThanhTraTheoDatDai(HttpServletResponse response,
			@RequestParam(value = "loaiKy", required = false) String loaiKy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "quy", required = false) Integer quy,
			@RequestParam(value = "year", required = false) Integer year,
			@RequestParam(value = "month", required = false) Integer month,
			@RequestParam(value = "donViId", required = false) Long donViId,
			@RequestParam(value = "listDonVis", required = false) List<CoQuanQuanLy> listDonVis
			) throws IOException {
		
		try {			
			Map<String, Object> map = new HashMap<>();
			Map<String, Object> mapMaSo = new HashMap<>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			List<Map<String, Object>> maSos = new ArrayList<>();
			List<CoQuanQuanLy> list = new ArrayList<CoQuanQuanLy>();
						
			if (listDonVis != null) {
				for (CoQuanQuanLy dv : listDonVis) {
					CoQuanQuanLy coQuan = coQuanQuanLyRepo.findOne(dv.getId());
					if (coQuan != null) {
						list.add(coQuan);
					}		
				}
			} else { 
				if (donViId != null && donViId > 0)  {
					coQuanQuanLyRepo.findOne(donViId);
					list.add(coQuanQuanLyRepo.findOne(donViId));
				}
			}
			donVis.addAll(list);
			
			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllTCD(loaiKy, quy, year, month, tuNgay, denNgay);
			BooleanExpression predAllDSXLD = (BooleanExpression) thongKeBaoCaoTongHopKQXLDService.predicateFindAllXLD(loaiKy, quy, year, month, tuNgay, denNgay);
			
			//khieu nai
			List<Long> idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiChinhTriVanHoaXaHoiKhacs.add(59L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeChinhSachCCVCs.add(58L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans.add(57L);
			
			List<Long> idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais = new ArrayList<Long>();
			idLinhVucHanhChinhDonKhieuNaiVeLienQuanDenDatDais.add(54L);
			
			LinhVucDonThu linhVucHanhChinhDonKhieuNai = linhVucDonThuRepo.findOne(1L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucHanhChinhKhieuNaiVeDang = linhVucDonThuRepo.findOne(56L);
			
			//to cao
			LinhVucDonThu linhVucHanhChinhDonToCao = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhapDonToCao = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhungDonToCao = linhVucDonThuRepo.findOne(39L);
			LinhVucDonThu linhVucVeDangDonToCao = linhVucDonThuRepo.findOne(62L);
			LinhVucDonThu linhVucKhacDonToCao = linhVucDonThuRepo.findOne(63L);
			
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
			linhVucVeNhaCuaTaiSans.addAll(linhVucDonThuService.getLinhVucDonThuTheoNhieuIds(idLinhVucHanhChinhDonKhieuNaiVeNhaCuaTaiSans));
			
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
			ExcelUtil.exportTongHopKetQuaThanhTraTheoDatDai(response, "DanhSachTongHopKetQuaThanhTraTheoDatDai", "sheetName", maSos, tuNgay, denNgay, "TỔNG HỢP KẾT QUẢ THANH TRA TRONG LĨNH VỰC ĐẤT ĐAI");
		} catch (Exception e) {
			Utils.responseInternalServerErrors(e);
		}
	}
	
}
