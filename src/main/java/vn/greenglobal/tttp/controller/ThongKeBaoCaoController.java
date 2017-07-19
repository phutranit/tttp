package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.LoaiTiepDanEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.LinhVucDonThu;
import vn.greenglobal.tttp.model.QSoTiepCongDan;
import vn.greenglobal.tttp.model.ThamSo;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.LinhVucDonThuRepository;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.repository.ThamSoRepository;
import vn.greenglobal.tttp.service.CoQuanQuanLyService;
import vn.greenglobal.tttp.service.LinhVucDonThuService;
import vn.greenglobal.tttp.service.ThamSoService;
import vn.greenglobal.tttp.service.ThongKeBaoCaoTongHopKQTCDService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "thongKeBaoCaos", description = "Thống kê báo cáo")
public class ThongKeBaoCaoController extends TttpController<Don> {
	
	@Autowired
	private SoTiepCongDanRepository soTCDRepo;
	
	@Autowired
	private DonRepository donRepo;
	
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
	
	public ThongKeBaoCaoController(BaseRepository<Don, Long> repo) {
		super(repo);
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/thongKeBaoCaos/tongHopKetQuaTiepCongDan")
	@ApiOperation(value = "Tổng hợp kết quả tiếp công dân", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public @ResponseBody ResponseEntity<Object> getTongHopKetQuaTiepCongDan(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, 
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
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
			
			Map<String, Object> mapTCDThuongXuyen = new HashMap<>();
			Map<String, Object> mapTCDDinhKyVaDotXuatCuaLanhDao = new HashMap<>();
			Map<String, Object> mapVuViecTX = new HashMap<>();
			Map<String, Object> mapVuViecDKDX = new HashMap<>();
			Map<String, Object> mapDoanDongNguoiTX = new HashMap<>();
			Map<String, Object> mapDoanDongNguoiDKDX = new HashMap<>();
			Map<String, Object> mapNoiDungTiepCongDan = new HashMap<>();
			Map<String, Object> mapDonKienNghiPhanAnh = new HashMap<>();
			Map<String, Object> mapDonToCao = new HashMap<>();
			Map<String, Object> mapDonToCaoThamNhung = new HashMap<>();
			Map<String, Object> mapDonToCaoTuPhap = new HashMap<>();
			Map<String, Object> mapDonToCaoHanhChinh = new HashMap<>();
			Map<String, Object> mapDonKhieuNai = new HashMap<>();
			Map<String, Object> mapDonLinhVuc = new HashMap<>();
			Map<String, Object> mapDonKhieuNaiLinhVucHanhChinh = new HashMap<>();
			Map<String, Object> mapDonKhieuNaiTuPhap = new HashMap<>();
			Map<String, Object> mapDonKhieuNaiChinhTriVanHoaXaHoi = new HashMap<>();
			Map<String, Object> mapKetQuaQuaTiepDan = new HashMap<>();
			Map<String, Object> mapKetQuaQuaTiepDanChuaDuocGiaiQuyet = new HashMap<>();
			Map<String, Object> mapKetQuaQuaTiepDanDaDuocGiaiQuyet = new HashMap<>();
			Map<String, Object> mapKetQuaQuaTiepDanDaDuocGiaiQuyetChuaCoQuyetDinhGiaiQuyet = new HashMap<>();
			Map<String, Object> mapKetQuaQuaTiepDanDaDuocGiaiQuyetDaCoQuyetDinhGiaiQuyet = new HashMap<>();
			Map<String, Object> mapDonVi = new HashMap<>();
			Map<String, Object> map = new HashMap<>();
			List<Map<String, Object>> coQuans = new ArrayList<>();
			List<Long> capCoQuanQuanLyIds = new ArrayList<Long>();
			List<CoQuanQuanLy> donVis = new ArrayList<CoQuanQuanLy>();
			
			//List<LinhVucDonThu> linhVucKienNghiPhanAnhs = new ArrayList<LinhVucDonThu>();
			//linhVucKienNghiPhanAnhs.addAll(linhVucDonThuService.getDanhSachLinhVucDonThus(LoaiDonEnum.DON_KIEN_NGHI_PHAN_ANH.name()));
			
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()));
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCQQLThanhTraThanhPho.getGiaTri().toString()));
			capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDSoBanNganh.getGiaTri().toString()));
			//capCoQuanQuanLyIds.add(Long.valueOf(thamSoCCQQLUBNDQuanHuyen.getGiaTri().toString()));
			List<CoQuanQuanLy> list = (List<CoQuanQuanLy>) coQuanQuanLyRepo.findAll(coQuanQuanLyService.predicateFindDonViVaConCuaDonViTHTKBC(
					Long.valueOf(thamSoCCQQLUBNDThanhPho.getGiaTri().toString()), capCoQuanQuanLyIds,
					"CQQL_UBNDTP_DA_NANG"));
			donVis.addAll(list);
			System.out.println("donVis " +donVis.size());
			
			BooleanExpression predAllDSTCD = (BooleanExpression) thongKeBaoCaoTongHopKQTCDService.predicateFindAllTCD(tuNgay, denNgay, 0L);
			LinhVucDonThu linhVucHanhChinh = linhVucDonThuRepo.findOne(15L);
			LinhVucDonThu linhVucTuPhap = linhVucDonThuRepo.findOne(16L);
			LinhVucDonThu linhVucThamNhung = linhVucDonThuRepo.findOne(37L);
			LinhVucDonThu linhVucCheDoChinhSach = linhVucDonThuRepo.findOne(3L);
			LinhVucDonThu linhVucDatDaiNhaCuaVaTaiSan = linhVucDonThuRepo.findOne(4L);
			LinhVucDonThu linhVucKhieuNaiTuPhap = linhVucDonThuRepo.findOne(6L);
			LinhVucDonThu linhVucKhieuNaiTonGiaoTinNguong = linhVucDonThuRepo.findOne(2L);
			
			List<LinhVucDonThu> linhVucTranhChapVeDatDais = new ArrayList<LinhVucDonThu>();
			linhVucTranhChapVeDatDais.addAll(linhVucDonThuService.getDanhSachLinhVucDonThusByCha(linhVucDatDaiNhaCuaVaTaiSan));
			List<LinhVucDonThu> linhVucCheDoChinhSachs = new ArrayList<LinhVucDonThu>();
			linhVucCheDoChinhSachs.addAll(linhVucDonThuService.getDanhSachLinhVucDonThusByCha(linhVucCheDoChinhSach));
			
			linhVucCheDoChinhSachs.forEach(lv -> {
				System.out.println("lv " +lv.getId() + " ten " +lv.getTen());
			});
			
			BooleanExpression predAllDSTCDThuongXuyen = predAllDSTCD;
			predAllDSTCDThuongXuyen = predAllDSTCDThuongXuyen.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.THUONG_XUYEN));
			
			BooleanExpression predAllDSTCDLanhDao = predAllDSTCD;
			predAllDSTCDLanhDao = predAllDSTCDLanhDao.and(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DINH_KY)
					.or(QSoTiepCongDan.soTiepCongDan.loaiTiepDan.eq(LoaiTiepDanEnum.DOT_XUAT)));
			
			for (CoQuanQuanLy cq : donVis) {
				BooleanExpression predAllDSTCDDonVi = predAllDSTCD;
				
				mapDonVi.put("ten", cq.getTen());
				mapDonVi.put("coQuanQuanLyId", cq.getId());
				
				// tiep cong dan thuong xuyen
				mapTCDThuongXuyen.put("luoc", thongKeBaoCaoTongHopKQTCDService.getTongSoLuocTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, cq.getId()));
				mapTCDThuongXuyen.put("nguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, cq.getId(), 
						true, false));
				
				mapVuViecTX.put("cu", "0");
				mapVuViecTX.put("moiPhatSinh", "0");
				mapTCDThuongXuyen.put("vuViec", mapVuViecTX);
				
				mapVuViecTX = new HashMap<String, Object>();
				mapDoanDongNguoiTX.put("soDoan", thongKeBaoCaoTongHopKQTCDService.getTongSoDoanDongNguoiTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, cq.getId(), true));
				mapDoanDongNguoiTX.put("nguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanThuongXuyen(predAllDSTCDThuongXuyen, cq.getId(), 
						false, true));
				mapVuViecTX.put("cu", "0");
				mapVuViecTX.put("moiPhatSinh", "0");
				mapDoanDongNguoiTX.put("vuViec", mapVuViecTX);
				
				mapTCDThuongXuyen.put("doanDongNguoi", mapDoanDongNguoiTX);
				mapDonVi.put("mapTCDThuongXuyen", mapTCDThuongXuyen);
				
				//tiep cong dan cua lanh dao
				mapTCDDinhKyVaDotXuatCuaLanhDao.put("luoc", thongKeBaoCaoTongHopKQTCDService.getTongSoLuocTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, cq.getId()));
				mapTCDDinhKyVaDotXuatCuaLanhDao.put("nguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, cq.getId(), 
						true, false));
				
				mapVuViecDKDX.put("cu", "0");
				mapVuViecDKDX.put("moiPhatSinh", "0");
				mapTCDDinhKyVaDotXuatCuaLanhDao.put("vuViec", mapVuViecDKDX);
				
				mapVuViecDKDX = new HashMap<String, Object>();
				mapDoanDongNguoiDKDX.put("soDoan", thongKeBaoCaoTongHopKQTCDService.getTongSoDoanDongNguoiTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, cq.getId(), true));
				mapDoanDongNguoiDKDX.put("nguoi", thongKeBaoCaoTongHopKQTCDService.getTongSoNguoiDungTenTiepCongDanDinhKyDotXuat(predAllDSTCDLanhDao, cq.getId(), 
						false, true));
				mapVuViecDKDX.put("cu", "0");
				mapVuViecDKDX.put("moiPhatSinh", "0");
				mapDoanDongNguoiDKDX.put("vuViec", mapVuViecDKDX);
				
				mapTCDDinhKyVaDotXuatCuaLanhDao.put("doanDongNguoi", mapDoanDongNguoiDKDX);
				mapDonVi.put("mapTCDDinhKyDotXuatCuaLanhDao", mapTCDDinhKyVaDotXuatCuaLanhDao);
				
				//Noi dung tiep cong dan
				//don kien nghi phan anh
				mapDonKienNghiPhanAnh = new HashMap<String, Object>();
				mapDonKienNghiPhanAnh.put("ten", "Phản ánh, kiến nghị, khác");
				mapDonKienNghiPhanAnh.put("giaTri", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKienNghiPhanAnh(predAllDSTCDDonVi, cq.getId()));
				mapNoiDungTiepCongDan.put("kienNghiPhanAnh", mapDonKienNghiPhanAnh);
				
				//don to cao
				mapDonToCaoHanhChinh = new HashMap<String, Object>();
				mapDonToCaoHanhChinh.put("ten", "Lĩnh vực hành chính");
				mapDonToCaoHanhChinh.put("giaTri", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucHanhChinhVaTuPhap(predAllDSTCDDonVi, cq.getId(), linhVucHanhChinh));
				mapDonToCao.put("linhVucHanhChinh", mapDonToCaoHanhChinh);
				
				mapDonToCaoTuPhap = new HashMap<String, Object>();
				mapDonToCaoTuPhap.put("ten", "Lĩnh vực tư pháp");
				mapDonToCaoTuPhap.put("giaTri", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucHanhChinhVaTuPhap(predAllDSTCDDonVi, cq.getId(), linhVucTuPhap));
				mapDonToCao.put("linhVucTuPhap", mapDonToCaoTuPhap);
				
				mapDonToCaoThamNhung = new HashMap<String, Object>();
				mapDonToCaoThamNhung.put("ten", "Tham nhũng");
				mapDonToCaoThamNhung.put("giaTri", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonToCaoLinhVucThamNhung(predAllDSTCDDonVi, cq.getId(), linhVucThamNhung));
				mapDonToCao.put("linhVucThamNhung", mapDonToCaoThamNhung);
				
				mapNoiDungTiepCongDan.put("toCao", mapDonToCao);
				
				//don khieu nai
				mapDonLinhVuc = new HashMap<String, Object>();
				mapDonLinhVuc.put("ten", "Về tranh chấp, đòi đất cũ, đền bù, giải tỏa...");
				mapDonLinhVuc.put("giaTri", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCon(predAllDSTCDDonVi, cq.getId(), linhVucTranhChapVeDatDais));
				mapDonKhieuNaiLinhVucHanhChinh.put("veTranhChap", mapDonLinhVuc);
				
				mapDonLinhVuc = new HashMap<String, Object>();
				mapDonLinhVuc.put("ten", "Về chính sách");
				mapDonLinhVuc.put("giaTri", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVucChiTietCha(predAllDSTCDDonVi, cq.getId(), linhVucCheDoChinhSach));
				mapDonKhieuNaiLinhVucHanhChinh.put("veChinhSach", mapDonLinhVuc);
				
				mapDonLinhVuc = new HashMap<String, Object>();
				mapDonLinhVuc.put("ten", "Về nhà, tài sản");
				mapDonLinhVuc.put("giaTri", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVucChiTietCha(predAllDSTCDDonVi, cq.getId(), linhVucDatDaiNhaCuaVaTaiSan));
				mapDonKhieuNaiLinhVucHanhChinh.put("veNhaVaTaiSan", mapDonLinhVuc);
				
				mapDonLinhVuc = new HashMap<String, Object>();
				mapDonLinhVuc.put("ten", "Về chế độ CC,CV");
				mapDonLinhVuc.put("giaTri", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiNhieuLinhVucChiTietCon(predAllDSTCDDonVi, cq.getId(), linhVucCheDoChinhSachs));
				mapDonKhieuNaiLinhVucHanhChinh.put("veCheDo", mapDonLinhVuc);
				
				mapDonKhieuNaiTuPhap = new HashMap<String, Object>();
				mapDonKhieuNaiTuPhap.put("ten", "Lĩnh vực tư pháp");
				mapDonKhieuNaiTuPhap.put("giaTri", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonKhieuNaiLinhVuc(predAllDSTCDDonVi, cq.getId(), linhVucKhieuNaiTuPhap));
				
				mapDonKhieuNaiChinhTriVanHoaXaHoi = new HashMap<String, Object>();
				mapDonKhieuNaiChinhTriVanHoaXaHoi.put("ten", "Lĩnh vực CT,VH,XH khác");
				mapDonKhieuNaiChinhTriVanHoaXaHoi.put("giaTri", "");
				
				mapDonKhieuNai.put("linhVucHanhChinh", mapDonKhieuNaiLinhVucHanhChinh);
				mapDonKhieuNai.put("linhVucTuPhap", mapDonKhieuNaiTuPhap);
				mapDonKhieuNai.put("linhVucChinhTriVanHoaXaHoi", mapDonKhieuNaiChinhTriVanHoaXaHoi);
				mapNoiDungTiepCongDan.put("ten", "Nội dung tiếp công dân (số vụ việc)");
				mapNoiDungTiepCongDan.put("khieuNai", mapDonKhieuNai);
				mapDonVi.put("noiDungTiepCongDan", mapNoiDungTiepCongDan);
				
				//ket qua tiep dan
				//chua duoc giai quyet
				mapKetQuaQuaTiepDanChuaDuocGiaiQuyet = new HashMap<String, Object>();
				mapKetQuaQuaTiepDanChuaDuocGiaiQuyet.put("ten", "Chưa được giải quyết");
				mapKetQuaQuaTiepDanChuaDuocGiaiQuyet.put("giaTri", "");
				mapKetQuaQuaTiepDan.put("chuaDuocGiaiQuyet", mapKetQuaQuaTiepDanChuaDuocGiaiQuyet);
				
				//da duoc giai quyet
				mapKetQuaQuaTiepDanDaDuocGiaiQuyet = new HashMap<String, Object>();
				mapKetQuaQuaTiepDanDaDuocGiaiQuyetChuaCoQuyetDinhGiaiQuyet = new HashMap<String, Object>();
				mapKetQuaQuaTiepDanDaDuocGiaiQuyetDaCoQuyetDinhGiaiQuyet = new HashMap<String, Object>();
				mapKetQuaQuaTiepDanDaDuocGiaiQuyet.put("ten", "Đã được giải quyết");
				
				mapKetQuaQuaTiepDanDaDuocGiaiQuyetChuaCoQuyetDinhGiaiQuyet.put("ten", "Chưa có QĐ giải quyết");
				mapKetQuaQuaTiepDanDaDuocGiaiQuyetChuaCoQuyetDinhGiaiQuyet.put("giaTri", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonChuaCoQuyetDinhGiaiQuyet(predAllDSTCDDonVi, cq.getId()));
				
				mapKetQuaQuaTiepDanDaDuocGiaiQuyetDaCoQuyetDinhGiaiQuyet.put("ten", "Đã có QĐ giải quyết (lần 1,2, cuối cùng)");
				mapKetQuaQuaTiepDanDaDuocGiaiQuyetDaCoQuyetDinhGiaiQuyet.put("giaTri", thongKeBaoCaoTongHopKQTCDService.getTongSoVuViecTiepCongDanDonDaCoQuyetDinhGiaiQuyet(predAllDSTCDDonVi, cq.getId()));
				
				mapKetQuaQuaTiepDanDaDuocGiaiQuyet.put("chuaCoQuyetDinhGiaiQuyet", mapKetQuaQuaTiepDanDaDuocGiaiQuyetChuaCoQuyetDinhGiaiQuyet);
				mapKetQuaQuaTiepDanDaDuocGiaiQuyet.put("daCoQuyetDinhGiaiQuyet", mapKetQuaQuaTiepDanDaDuocGiaiQuyetDaCoQuyetDinhGiaiQuyet);
				mapKetQuaQuaTiepDan.put("ten", "Kết quả qua tiếp dân (số vụ việc)");
				mapKetQuaQuaTiepDan.put("daDuocGiaiQuyet", mapKetQuaQuaTiepDanDaDuocGiaiQuyet);
				mapDonVi.put("ketQuaTiepCongDan", mapKetQuaQuaTiepDan);
				
				coQuans.add(mapDonVi);	
				mapVuViecTX = new HashMap<String, Object>();
				mapVuViecDKDX = new HashMap<String, Object>();
				mapDoanDongNguoiTX = new HashMap<String, Object>();
				mapDoanDongNguoiDKDX = new HashMap<String, Object>();
				mapTCDThuongXuyen = new HashMap<String, Object>();
				mapTCDDinhKyVaDotXuatCuaLanhDao = new HashMap<String, Object>();
				mapDonToCao = new HashMap<>();
				mapDonKhieuNai = new HashMap<>();
				mapDonKhieuNaiLinhVucHanhChinh = new HashMap<>();
				mapNoiDungTiepCongDan = new HashMap<String, Object>();
				mapKetQuaQuaTiepDan = new HashMap<String, Object>();
				mapDonVi = new HashMap<String, Object>();
			}
			map.put("donVis", coQuans);
			return new ResponseEntity<>(map, HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
}
