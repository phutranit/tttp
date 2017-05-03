package vn.greenglobal.tttp.controller;

import org.apache.commons.lang3.StringUtils;
import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.VaiTroEnum;
import vn.greenglobal.tttp.enums.QuyTrinhXuLyDonEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.VaiTro;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.service.XuLyDonService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "xuLyDons", description = "Xử lý đơn")
public class XuLyDonController extends TttpController<XuLyDon> {
	
	private static XuLyDonService xuLyDonService = new XuLyDonService();
	private static DonService donService = new DonService();

	@Autowired
	private XuLyDonRepository repo;

	@Autowired
	private DonRepository donRepo;

	@Autowired
	private CongChucRepository congChucRepo;
	
	@Autowired
	private CoQuanQuanLyRepository coQuanQuanLyRepo;

	public XuLyDonController(BaseRepository<XuLyDon, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/xuLyDons")
	@ApiOperation(value = "Quy trình xử lý đơn", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Thêm quy trình xử lý đơn thành công", response = XuLyDon.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody XuLyDon xuLyDon, PersistentEntityResourceAssembler eass) {

		Long timeStart = System.currentTimeMillis();
		NguoiDung nguoiDungHienTai = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DON_SUA);
		CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
		if (nguoiDungHienTai != null && nguoiDungHienTai.getVaiTros().iterator().hasNext() && 
				commonProfile.containsAttribute("congChucId") && commonProfile.containsAttribute("coQuanQuanLyId")) {

			Long donId = xuLyDon.getDon().getId();
			XuLyDon xuLyDonHienTai = xuLyDonService.predFindCurrent(repo, donId);
			if (xuLyDonHienTai != null) {

				// Xac dinh vai tro cua nguoi dung (Nhieu vai tro cho 1 nguoi)
				VaiTro vaiTro = nguoiDungHienTai.getVaiTros().iterator().next();
				// Thay alias
				String vaiTroNguoiDungHienTai = vaiTro.getTen().trim();
				// Thong tin xu ly don
				QuyTrinhXuLyDonEnum quyTrinhXuLy = xuLyDon.getQuyTrinhXuLy();
				String note = vaiTroNguoiDungHienTai + " " + quyTrinhXuLy.getText().toLowerCase() + " ";
				Long congChucId = new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
				Long coQuanQuanLyId = new Long(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());

				if (StringUtils.equals(vaiTroNguoiDungHienTai.toLowerCase(), VaiTroEnum.VAN_THU.getText().toLowerCase())) {
					
					// vai tro VAN_THU
					if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.TRINH_LANH_DAO)) {
						
						note = note + VaiTroEnum.LANH_DAO.getText().toLowerCase() + " ";
						xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
						xuLyDonHienTai.setQuyTrinhXuLy(quyTrinhXuLy);
						xuLyDonHienTai.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
						xuLyDonHienTai.setGhiChu(note);
						
					} else if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.DINH_CHI)) {
					
						// add hanXuLy
						
					} 
				} else if (StringUtils.equals(vaiTroNguoiDungHienTai.toLowerCase(), VaiTroEnum.CHUYEN_VIEN.getText().toLowerCase())) {
					
					// vai tro CHUYEN_VIEN
					if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI)) {
						
						
					} else if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.DE_XUAT_HUONG_XU_LY)) {
						
						HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy();
						// huongXuLy
						if (huongXuLyXLD.equals(HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO)) {
							
						} else if (huongXuLyXLD.equals(HuongXuLyXLDEnum.DE_XUAT_THU_LY)) {
							
						} else if (huongXuLyXLD.equals(HuongXuLyXLDEnum.CHUYEN_DON)) {
							
						} else if (huongXuLyXLD.equals(HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY)) {
							
						} else if (huongXuLyXLD.equals(HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN)) {
							
						} else if (huongXuLyXLD.equals(HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI)) {
							
						}
					}
				} else if (StringUtils.equals(vaiTroNguoiDungHienTai.toLowerCase(), VaiTroEnum.TRUONG_PHONG.getText().toLowerCase())) {
					
					// vai tro TRUONG_PHONG
					if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI)) {
						
						
					} else if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.GIAO_VIEC)) {
						
						
					}
				} else if (StringUtils.equals(vaiTroNguoiDungHienTai.toLowerCase(), VaiTroEnum.LANH_DAO.getText().toLowerCase())) {
					
					// vai tro LANH_DAO
					if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.GIAO_VIEC)) {
						
						
					}
				}
			}
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());

		
		
//		if (nguoiDung != null) {
//		NguoiDung nguoiDungu = Utils.quyenValidate(profileUtil, authorization, QuyenEnum.DON_THEM);
//		if (nguoiDungu != null) {
//
//			if (xuLyDonService.isExists(repo, xuLyDon.getDon().getId())) {
//
//				XuLyDon xuLyDonHienTai = xuLyDonService.predicateFindMax(repo, xuLyDon.getDon().getDonId());
//				XuLyDon xuLyDonTiepTheo = new XuLyDon();
//				String note = "";
//				String quyTrinhXuLy = xuLyDon.getQuyTrinhXuLy().getText().toLowerCase();
//				String chucVuCuaXuLyDon = "";
//				// Xac dinh vao tro cua nguoi dung
//				if (xuLyDonHienTai.getCongChuc() != null) {
//					CongChuc congChuc = congChucRepo.findOne(xuLyDonHienTai.getCongChuc().getId());
//					NguoiDung nguoiDung = congChuc.getNguoiDung();
//					if (nguoiDung != null) {
//						for (VaiTro vaiTro : nguoiDung.getVaiTros()) {
//							String quyen = vaiTro.getQuyen().trim();
//							if (quyen.equals(ChucVuEnum.VAN_THU.name()) || quyen.equals(ChucVuEnum.LANH_DAO.name())
//									|| quyen.equals(ChucVuEnum.TRUONG_PHONG.name())
//									|| quyen.equals(ChucVuEnum.CAN_BO.name())) {
//								chucVuCuaXuLyDon = vaiTro.getQuyen();
//								break;
//							}
//						}
//					}
//				} else {
//					chucVuCuaXuLyDon = xuLyDonHienTai.getChucVu().name();
//				}
//
//				if (StringUtils.isNotBlank(chucVuCuaXuLyDon)) {
//
//					if (chucVuCuaXuLyDon.equals(ChucVuEnum.VAN_THU.name())) {
//						if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.TRINH_LANH_DAO)) {
//
//							// Add note
//							note = ChucVuEnum.VAN_THU.getText() + " " + quyTrinhXuLy + " "
//									+ ChucVuEnum.LANH_DAO.getText() + " đơn "
//									+ xuLyDon.getDon().getTrangThaiDon().getText().toLowerCase();
//							xuLyDonHienTai.setGhiChu(note.toString());
//							xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
//							xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
//							xuLyDonHienTai.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
//							if (xuLyDon.getDon().getTrangThaiDon().equals(TrangThaiDonEnum.DA_XU_LY)) {
//
//								Utils.save(repo, xuLyDonHienTai, new Long(profileUtil.getCommonProfile(authorization)
//										.getAttribute("congChucId").toString()));
//								xuLyDonTiepTheo
//										.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
//								xuLyDonTiepTheo.setDon(xuLyDon.getDon());
//								xuLyDonTiepTheo.setChucVu(ChucVuEnum.LANH_DAO);
//								xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
//								xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
//								return Utils
//										.doSave(repo, xuLyDonTiepTheo,
//												new Long(profileUtil.getCommonProfile(authorization)
//														.getAttribute("congChucId").toString()),
//												eass, HttpStatus.CREATED);
//							} else if (xuLyDon.getDon().getTrangThaiDon().equals(TrangThaiDonEnum.CHO_XU_LY)) {
//
//								Utils.save(repo, xuLyDonHienTai, new Long(profileUtil.getCommonProfile(authorization)
//										.getAttribute("congChucId").toString()));
//								xuLyDonTiepTheo
//										.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
//								xuLyDonTiepTheo.setDon(xuLyDon.getDon());
//								xuLyDonTiepTheo.setChucVu(ChucVuEnum.LANH_DAO);
//								xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
//								xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
//								return Utils
//										.doSave(repo, xuLyDonTiepTheo,
//												new Long(profileUtil.getCommonProfile(authorization)
//														.getAttribute("congChucId").toString()),
//												eass, HttpStatus.CREATED);
//							}
//						}
//
//						if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.TRA_DON)) {
//
//							note = ChucVuEnum.VAN_THU.getText().toString() + " "
//									+ QuyTrinhXuLyDonEnum.TRA_DON.getText().toString() + " "
//									+ xuLyDonHienTai.getPhongBanXuLy().getTen().toString();
//						}
//
//					} else if (chucVuCuaXuLyDon.equals(ChucVuEnum.LANH_DAO.name())) {
//
//						if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.GIAO_VIEC)) {
//
//							xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
//							xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
//							xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
//							xuLyDonTiepTheo.setDon(xuLyDon.getDon());
//							xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
//							xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
//							xuLyDonTiepTheo.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
//							// Add ghiChu
//							if (xuLyDon.getCanBoXuLyChiDinh() == null) {
//								note = ChucVuEnum.LANH_DAO.getText() + " " + QuyTrinhXuLyDonEnum.GIAO_VIEC.getText()
//										+ " " + xuLyDon.getPhongBanXuLy().getTen();
//								// xuLyDonHienTai.setGhiChu(note);
//								Utils.save(repo, xuLyDonHienTai, new Long(profileUtil.getCommonProfile(authorization)
//										.getAttribute("congChucId").toString()));
//								xuLyDonTiepTheo.setChucVu(ChucVuEnum.TRUONG_PHONG);
//								return Utils
//										.doSave(repo, xuLyDonTiepTheo,
//												new Long(profileUtil.getCommonProfile(authorization)
//														.getAttribute("congChucId").toString()),
//												eass, HttpStatus.CREATED);
//							} else {
//								note = ChucVuEnum.LANH_DAO.getText() + " " + QuyTrinhXuLyDonEnum.GIAO_VIEC.getText()
//										+ " " + xuLyDon.getCanBoXuLyChiDinh().getHoVaTen() + " "
//										+ xuLyDon.getPhongBanXuLy().getTen();
//								xuLyDonHienTai.setGhiChu(note);
//								Utils.save(repo, xuLyDonHienTai, new Long(profileUtil.getCommonProfile(authorization)
//										.getAttribute("congChucId").toString()));
//								xuLyDonTiepTheo.setChucVu(ChucVuEnum.CAN_BO);
//								xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh());
//								xuLyDonTiepTheo.setChucVuGiaoViec(ChucVuEnum.LANH_DAO);
//								return Utils
//										.doSave(repo, xuLyDonTiepTheo,
//												new Long(profileUtil.getCommonProfile(authorization)
//														.getAttribute("congChucId").toString()),
//												eass, HttpStatus.CREATED);
//							}
//						} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.CHUYEN_BO_PHAN_GIAI_QUYET)) {
//
//							// Case Don da xu ly
//							note = ChucVuEnum.LANH_DAO.getText() + " "
//									+ QuyTrinhXuLyDonEnum.CHUYEN_BO_PHAN_GIAI_QUYET.getText().toLowerCase() + " "
//									+ xuLyDonHienTai.getPhongBanXuLy().getTen().toLowerCase();
//							xuLyDonHienTai.setGhiChu(note);
//							xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
//							xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
//							xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
//							xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
//							xuLyDonHienTai.setGhiChu(note);
//							xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
//							Don donDau = donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
//							donDau.setTrangThaiDon(TrangThaiDonEnum.CHO_XU_LY);
//							Utils.save(donRepo, donDau, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//							return Utils.doSave(repo, xuLyDonHienTai, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
//									eass, HttpStatus.CREATED);
//						} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.DUYET)) {
//
//							note = ChucVuEnum.LANH_DAO.getText() + " "
//									+ QuyTrinhXuLyDonEnum.DUYET.getText().toLowerCase() + " "
//									+ xuLyDonHienTai.getHuongXuLy().getText().toLowerCase() + " "
//									+ xuLyDonHienTai.getPhongBanXuLy().getTen().toLowerCase();
//							xuLyDonHienTai.setGhiChu(note);
//							xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
//							xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
//							xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
//							Don don = donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
//							don.setPhongBanGiaiQuyet(xuLyDonHienTai.getPhongBanGiaiQuyet());
//							don.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
//							Utils.save(donRepo, don, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//							return Utils.doSave(repo, xuLyDonHienTai, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
//									eass, HttpStatus.CREATED);
//						} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.CHUYEN_CAN_BO_XU_LY)) {
//
//							// Case Don da xu ly
//							note = ChucVuEnum.LANH_DAO.getText() + " "
//									+ QuyTrinhXuLyDonEnum.CHUYEN_CAN_BO_XU_LY.getText() + " "
//									+ xuLyDon.getCanBoXuLyChiDinh().getHoVaTen() + " "
//									+ xuLyDon.getPhongBanXuLy().getTen();
//							xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
//							xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
//							xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
//							xuLyDonHienTai.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
//							xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
//							// xuLyDonHienTai.setGhiChu(note);
//							Utils.save(repo, xuLyDonHienTai, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//							xuLyDonTiepTheo.setDon(xuLyDon.getDon());
//							xuLyDonTiepTheo.setChucVu(ChucVuEnum.CAN_BO);
//							xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh());
//							xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
//							xuLyDonTiepTheo.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
//							xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
//							return Utils.doSave(repo, xuLyDonTiepTheo, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
//									eass, HttpStatus.CREATED);
//						} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.YEU_CAU_KIEM_TRA_LAI)) {
//
//							note = ChucVuEnum.LANH_DAO.getText() + " "
//									+ QuyTrinhXuLyDonEnum.YEU_CAU_KIEM_TRA_LAI.getText() + " "
//									+ xuLyDon.getPhongBanXuLy().getTen();
//							xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
//							xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
//							xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
//							// xuLyDonHienTai.setGhiChu(note);
//							Utils.save(repo, xuLyDonHienTai, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//							xuLyDonTiepTheo.setDon(xuLyDon.getDon());
//							xuLyDonTiepTheo.setChucVu(ChucVuEnum.TRUONG_PHONG);
//							xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
//							xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
//							xuLyDonTiepTheo.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
//							return Utils.doSave(repo, xuLyDonTiepTheo, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
//									eass, HttpStatus.CREATED);
//						} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.DINH_CHI)) {
//
//							Don donDau = donService.updateTrangThaiDon(donRepo, xuLyDon.getDon().getId(),
//									TrangThaiDonEnum.DINH_CHI);
//							Utils.save(donRepo, donDau, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//							note = ChucVuEnum.LANH_DAO.getText() + " " + QuyTrinhXuLyDonEnum.DINH_CHI.getText();
//							xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
//							xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
//							xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
//							// xuLyDonHienTai.setGhiChu(note);
//							return Utils.doSave(repo, xuLyDonHienTai, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
//									eass, HttpStatus.CREATED);
//						}
//
//					} else if (chucVuCuaXuLyDon.equals(ChucVuEnum.TRUONG_PHONG.name())) {
//
//						if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI)) {
//
//							note = ChucVuEnum.TRUONG_PHONG.getText() + " "
//									+ QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI.getText() + " "
//									+ ChucVuEnum.LANH_DAO.getText() + xuLyDonHienTai.getPhongBanXuLy().getTen();
//							xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
//							xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
//							xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
//							xuLyDonHienTai.setGhiChu(note);
//							Utils.save(repo, xuLyDonHienTai, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//							xuLyDonTiepTheo.setDon(xuLyDon.getDon());
//							xuLyDonTiepTheo.setChucVu(ChucVuEnum.LANH_DAO);
//							xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
//							xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
//							xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
//							return Utils.doSave(repo, xuLyDonTiepTheo, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
//									eass, HttpStatus.CREATED);
//						} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.GIAO_VIEC)) {
//
//							note = ChucVuEnum.TRUONG_PHONG.getText() + " "
//									+ QuyTrinhXuLyDonEnum.GIAO_VIEC.getText().toLowerCase() + " "
//									+ xuLyDon.getCanBoXuLyChiDinh().getHoVaTen()
//									+ xuLyDonHienTai.getPhongBanXuLy().getTen();
//							xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
//							xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
//							xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
//							xuLyDonHienTai.setGhiChu(note);
//							xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
//							Utils.save(repo, xuLyDonHienTai, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//							xuLyDonTiepTheo.setDon(xuLyDon.getDon());
//							xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh());
//							xuLyDonTiepTheo.setChucVu(ChucVuEnum.CAN_BO);
//							xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
//							xuLyDonTiepTheo.setChucVuGiaoViec(ChucVuEnum.TRUONG_PHONG);
//							xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
//							xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
//							return Utils.doSave(repo, xuLyDonTiepTheo, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
//									eass, HttpStatus.CREATED);
//						} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.YEU_CAU_KIEM_TRA_LAI)) {
//
//							note = ChucVuEnum.TRUONG_PHONG.getText() + " "
//									+ QuyTrinhXuLyDonEnum.YEU_CAU_KIEM_TRA_LAI.getText() + " "
//									+ xuLyDon.getCanBoXuLy().getHoVaTen() + " " + xuLyDon.getPhongBanXuLy().getTen();
//							xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
//							xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
//							xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
//							// xuLyDonHienTai.setGhiChu(note);
//							xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
//							xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
//							Utils.save(repo, xuLyDonHienTai, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//							xuLyDonTiepTheo.setDon(xuLyDon.getDon());
//							xuLyDonTiepTheo.setChucVu(ChucVuEnum.CAN_BO);
//							xuLyDonTiepTheo.setCongChuc(xuLyDonHienTai.getCanBoXuLy());
//							xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
//							xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
//							xuLyDonTiepTheo.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
//							xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
//							xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
//							return Utils.doSave(repo, xuLyDonTiepTheo, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
//									eass, HttpStatus.CREATED);
//						} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.TRINH_LANH_DAO)) {
//
//							note = ChucVuEnum.TRUONG_PHONG.getText() + " "
//									+ QuyTrinhXuLyDonEnum.TRINH_LANH_DAO.getText() + " "
//									+ xuLyDonHienTai.getPhongBanXuLy().getTen();
//							xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
//							xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
//							xuLyDonHienTai.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
//							xuLyDonHienTai.setGhiChu(note);
//							Utils.save(repo, xuLyDonHienTai, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//							xuLyDonTiepTheo.setDon(xuLyDon.getDon());
//							xuLyDonTiepTheo.setChucVu(ChucVuEnum.LANH_DAO);
//							xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
//							xuLyDonTiepTheo.setHuongXuLy(xuLyDonHienTai.getHuongXuLy());
//							xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
//							xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
//							xuLyDonTiepTheo.setPhongBanGiaiQuyet(xuLyDonHienTai.getPhongBanGiaiQuyet());
//							xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCanBoXuLy());
//							return Utils.doSave(repo, xuLyDonTiepTheo, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
//									eass, HttpStatus.CREATED);
//						}
//					} else if (chucVuCuaXuLyDon.equals(ChucVuEnum.CAN_BO.name())) {
//
//						if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI)) {
//
//							note = ChucVuEnum.CAN_BO.getText() + " "
//									+ QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI.getText() + " "
//									+ ChucVuEnum.TRUONG_PHONG.getText() + " "
//									+ xuLyDonHienTai.getPhongBanXuLy().getTen();
//							xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
//							xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
//							// xuLyDonHienTai.setGhiChu(note);
//							Utils.save(repo, xuLyDonHienTai, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//							xuLyDonTiepTheo.setDon(xuLyDon.getDon());
//							xuLyDonTiepTheo.setChucVu(ChucVuEnum.TRUONG_PHONG);
//							xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
//							xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
//							return Utils.doSave(repo, xuLyDonTiepTheo, new Long(
//									profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
//									eass, HttpStatus.CREATED);
//						} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.DE_XUAT_HUONG_XU_LY)) {
//
//							note = ChucVuEnum.CAN_BO.getText() + " " + QuyTrinhXuLyDonEnum.DE_XUAT_HUONG_XU_LY.getText()
//									+ " " + xuLyDon.getHuongXuLy().getText() + " ";
//							xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
//							xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
//							xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
//							xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
//
//							if (xuLyDon.getHuongXuLy().equals(HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO)) {
//
//								note = note + ChucVuEnum.LANH_DAO.getText() + " "
//										+ xuLyDonHienTai.getPhongBanXuLy().getTen();
//								// xuLyDonHienTai.setGhiChu(note);
//
//								// Cap nhat yeuCauGapLanhDao
//								Don donDau = donService.updateNgayLapDonGapLanhDao(donRepo, xuLyDon.getDon().getId());
//								Utils.save(donRepo, donDau, new Long(profileUtil.getCommonProfile(authorization)
//										.getAttribute("congChucId").toString()));
//								return Utils
//										.doSave(repo, xuLyDonHienTai,
//												new Long(profileUtil.getCommonProfile(authorization)
//														.getAttribute("congChucId").toString()),
//												eass, HttpStatus.CREATED);
//							} else {
//
//								note = note + ChucVuEnum.TRUONG_PHONG.getText() + " "
//										+ xuLyDonHienTai.getPhongBanXuLy().getTen();
//								xuLyDonHienTai.setGhiChu(note);
//								xuLyDonTiepTheo.setDon(xuLyDon.getDon());
//								xuLyDonTiepTheo.setChucVu(ChucVuEnum.TRUONG_PHONG);
//								xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
//								xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
//								xuLyDonTiepTheo.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
//								xuLyDonTiepTheo.setHuongXuLy(xuLyDon.getHuongXuLy());
//								xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDonHienTai.getThamQuyenGiaiQuyet());
//								xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
//								xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
//
//								if (xuLyDon.getHuongXuLy().equals(HuongXuLyXLDEnum.DE_XUAT_THU_LY)) {
//
//									xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
//									Utils.save(repo, xuLyDonHienTai, new Long(profileUtil
//											.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//									xuLyDonTiepTheo.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
//									return Utils
//											.doSave(repo, xuLyDonTiepTheo,
//													new Long(profileUtil.getCommonProfile(authorization)
//															.getAttribute("congChucId").toString()),
//													eass, HttpStatus.CREATED);
//								} else if (xuLyDon.getHuongXuLy().equals(HuongXuLyXLDEnum.CHUYEN_DON)) {
//
//									xuLyDonHienTai.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
//									Utils.save(repo, xuLyDonHienTai, new Long(profileUtil
//											.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//									xuLyDonTiepTheo.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
//									return Utils
//											.doSave(repo, xuLyDonTiepTheo,
//													new Long(profileUtil.getCommonProfile(authorization)
//															.getAttribute("congChucId").toString()),
//													eass, HttpStatus.CREATED);
//								} else if (xuLyDon.getHuongXuLy().equals(HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN)
//										|| xuLyDon.getHuongXuLy().equals(HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI)
//										|| xuLyDon.getHuongXuLy().equals(HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY)) {
//
//									Utils.save(repo, xuLyDonHienTai, new Long(profileUtil
//											.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//									return Utils
//											.doSave(repo, xuLyDonTiepTheo,
//													new Long(profileUtil.getCommonProfile(authorization)
//															.getAttribute("congChucId").toString()),
//													eass, HttpStatus.CREATED);
//								}
//							}
//						}
//					}
//				}
//			}
//
//			// Add new record for VAN_THU
//			xuLyDon.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
//			xuLyDon.setChucVu(ChucVuEnum.VAN_THU);
//			xuLyDon.setThuTuThucHien(0);
//			xuLyDon.setCongChuc(null);
//			xuLyDon.setQuyTrinhXuLy(null);
//			long donId = xuLyDon.getDon().getId();
//			// Don donDau = donRepo.findOne(donId);
//			Don donDau = donRepo.findOne(donService.predicateFindOne(donId));
//			donDau.setTrangThaiDon(TrangThaiDonEnum.CHO_XU_LY);
//			Utils.save(donRepo, donDau,
//					new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
//
//			return Utils.doSave(repo, xuLyDon,
//					new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
//					HttpStatus.CREATED);
	}


	// @RequestMapping(method = RequestMethod.PATCH, value =
	// "/xuLyDons/{id}/thuHoi")
	// @ApiOperation(value = "Thu hồi đơn", position = 2, produces =
	// MediaType.APPLICATION_JSON_VALUE)
	// @ApiResponses(value = { @ApiResponse(code = 202, message = "Thu hồi đơn
	// thành công", response = XuLyDon.class) })
	// public ResponseEntity<Object> thuHoiDon(@RequestHeader(value =
	// "Authorization", required = true) String authorization,
	// @RequestBody XuLyDon xuLyDon, @PathVariable("id") Long id,
	// PersistentEntityResourceAssembler eass) {
	//
	// if (!xuLyDonService.isExists(repo, xuLyDon.getDon().getId())) {
	//
	// return Utils.responseErrors(HttpStatus.NOT_FOUND,
	// ApiErrorEnum.DATA_NOT_FOUND.name(),
	// ApiErrorEnum.DATA_NOT_FOUND.getText());
	// }
	//
	// XuLyDon xuLyDonCu = xuLyDonService.predicateFindMax(repo,
	// xuLyDon.getDon().getDonId());
	// XuLyDon xuLyDonHienTai = new XuLyDon();
	// XuLyDon xuLyDonTiepTheo = new XuLyDon();
	//
	// String chucVuCuaXuLyDon = "";
	//
	// if (xuLyDonCu.getCongChuc() != null) {
	// CongChuc congChuc =
	// congChucRepo.findOne(xuLyDonCu.getCongChuc().getId());
	// NguoiDung nguoiDung = congChuc.getNguoiDung();
	// if (nguoiDung != null) {
	// for (VaiTro vaiTro : nguoiDung.getVaiTros()) {
	// String quyen = vaiTro.getQuyen().trim();
	// if (quyen.equals(ChucVuEnum.VAN_THU.name())) {
	// chucVuCuaXuLyDon = vaiTro.getQuyen();
	// break;
	// }
	// }
	// }
	// } else {
	// chucVuCuaXuLyDon = xuLyDonCu.getChucVu().name();
	// }
	//
	// if (StringUtils.isNotBlank(chucVuCuaXuLyDon)) {
	// if (chucVuCuaXuLyDon.equals(ChucVuEnum.LANH_DAO.name())) {
	//
	// xuLyDonCu.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
	// xuLyDonCu.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
	// Utils.save(repo, xuLyDonCu, new
	// Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
	// xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
	// xuLyDonHienTai.setChucVu(ChucVuEnum.TRUONG_PHONG);
	// xuLyDonHienTai.setDon(xuLyDon.getDon());
	// xuLyDonHienTai.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
	// xuLyDonHienTai.setPhongBanXuLy(xuLyDonCu.getPhongBanXuLy());
	// xuLyDonHienTai.setCanBoXuLy(xuLyDonCu.getCanBoXuLy());
	// xuLyDonHienTai.setQuyTrinhXuLy(QuyTrinhXuLyDonEnum.THU_HOI_DON);
	// Utils.save(repo, xuLyDonHienTai, new
	// Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
	// xuLyDonTiepTheo.setDon(xuLyDon.getDon());
	// xuLyDonTiepTheo.setChucVu(ChucVuEnum.TRUONG_PHONG);
	// xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonCu.getPhongBanXuLy());
	// xuLyDonTiepTheo.setCanBoXuLy(xuLyDonCu.getCanBoXuLy());
	// return Utils.doSave(repo, xuLyDonTiepTheo, new
	// Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
	// eass, HttpStatus.CREATED);
	// } else if (chucVuCuaXuLyDon.equals(ChucVuEnum.TRUONG_PHONG.name())) {
	//
	// xuLyDonCu.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
	// xuLyDonCu.setyKienXuLy(xuLyDon.getyKienXuLy());
	// Utils.save(repo, xuLyDonCu, new
	// Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
	// xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
	// xuLyDonHienTai.setChucVu(ChucVuEnum.CAN_BO);
	// xuLyDonHienTai.setDon(xuLyDon.getDon());
	// xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
	// xuLyDonHienTai.setPhongBanXuLy(xuLyDonCu.getPhongBanXuLy());
	// xuLyDonHienTai.setQuyTrinhXuLy(QuyTrinhXuLyDonEnum.THU_HOI_DON);
	// Utils.save(repo, xuLyDonHienTai, new
	// Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
	// xuLyDonTiepTheo.setDon(xuLyDon.getDon());
	// xuLyDonTiepTheo.setCongChuc(xuLyDon.getCongChuc());
	// xuLyDonTiepTheo.setChucVu(ChucVuEnum.CAN_BO);
	// xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonCu.getPhongBanXuLy());
	// return Utils.doSave(repo, xuLyDonTiepTheo, new
	// Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()),
	// eass, HttpStatus.CREATED);
	// }
	// }
	//
	// return Utils.responseErrors(HttpStatus.BAD_REQUEST,
	// ApiErrorEnum.DATA_NOT_FOUND.name(),
	// ApiErrorEnum.DATA_NOT_FOUND.getText());
	// }

	// @RequestMapping(method = RequestMethod.PATCH, value =
	// "/xuLyDons/{id}/dinhchi")
	// @ApiOperation(value = "Quy trình rút đơn", position = 1, produces =
	// MediaType.APPLICATION_JSON_UTF8_VALUE)
	// @ApiResponses(value = {
	// @ApiResponse(code = 202, message = "Đình chỉ đơn thanh công", response =
	// XuLyDon.class) })
	// public ResponseEntity<Object> rutDon(@RequestBody XuLyDon xuLyDon,
	// @PathVariable("id") Long id,
	// PersistentEntityResourceAssembler eass) {
	//
	// if (!xuLyDonService.isExists(repo, xuLyDon.getDon().getId())) {
	//
	// return Utils.responseErrors(HttpStatus.NOT_FOUND,
	// ApiErrorEnum.DATA_NOT_FOUND.name(),
	// ApiErrorEnum.DATA_NOT_FOUND.getText());
	// }
	//
	// XuLyDon xuLyDonHienTai = xuLyDonService.predicateFindMax(repo,
	// xuLyDon.getDon().getDonId());
	//
	// String chucVuCuaXuLyDon = "";
	//
	// if (xuLyDonCu.getCongChuc() != null) {
	// CongChuc congChuc =
	// congChucRepo.findOne(xuLyDonCu.getCongChuc().getId());
	// NguoiDung nguoiDung = congChuc.getNguoiDung();
	// if (nguoiDung != null) {
	// for (VaiTro vaiTro : nguoiDung.getVaiTros()) {
	// String quyen = vaiTro.getQuyen().trim();
	// if (quyen.equals(ChucVuEnum.VAN_THU.name())) {
	// chucVuCuaXuLyDon = vaiTro.getQuyen();
	// break;
	// }
	// }
	// }
	// } else {
	// chucVuCuaXuLyDon = xuLyDonCu.getChucVu().name();
	// }
	//
	// Don don = donService.updateQuyTrinhXuLyDon(donRepo, id,
	// QuyTrinhXuLyDonEnum.DINH_CHI);
	//
	// if (don == null) {
	//
	// return Utils.responseErrors(HttpStatus.NOT_FOUND,
	// ApiErrorEnum.DATA_NOT_FOUND.name(),
	// ApiErrorEnum.DATA_NOT_FOUND.getText());
	// }
	//
	// don.setLyDoDinhChi("Rút đơn");
	// Utils.save(donRepo, don);
	// if (xuLyDonService.isExists(repo, id)) {
	//
	// XuLyDon xuLyDonHienTai =
	// repo.findOne(xuLyDonService.predicateFindMax(xuLyDon.getDon().getId()));
	// xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
	// xuLyDonHienTai.setQuyTrinhXuLy(QuyTrinhXuLyDonEnum.DINH_CHI);
	// xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
	// xuLyDonHienTai.setMoTaTrangThai(xuLyDon.getMoTaTrangThai());
	// xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
	// String note = "";
	// ChucVu chucVu = xuLyDon.getCongChuc().getChucVu();
	// note = chucVu.getTen();
	// if (StringUtils.isNotBlank(xuLyDon.getHuongXuLy().getText())
	// && StringUtils.isBlank(xuLyDon.getQuyTrinhXuLy().getText())) {
	// note = note + " " + xuLyDon.getHuongXuLy().getText();
	// } else if (StringUtils.isBlank(xuLyDon.getHuongXuLy().getText())
	// && StringUtils.isNotBlank(xuLyDon.getQuyTrinhXuLy().getText())) {
	// note = note + " " + xuLyDon.getQuyTrinhXuLy().getText();
	// }
	// return Utils.doSave(repo, xuLyDonHienTai, eass, HttpStatus.CREATED);
	// }
	//
	// XuLyDon xuLyDonVanThu = new XuLyDon();
	// xuLyDonVanThu.setCongChuc(xuLyDon.getCongChuc());
	// xuLyDonVanThu.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
	// xuLyDonVanThu.setThuTuThucHien(1);
	// xuLyDonVanThu.setQuyTrinhXuLy(QuyTrinhXuLyDonEnum.DINH_CHI);
	// return Utils.doSave(repo, xuLyDonVanThu, eass, HttpStatus.CREATED);
	// }
}
