package vn.greenglobal.tttp.controller;

import java.time.LocalDateTime;
import java.time.chrono.Chronology;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.formula.functions.XYNumericFunction;
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
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.Don;
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
@Api(value = "xuLyDons",  description = "Xử lý đơn")
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
				String vaiTroNguoiDungHienTai = vaiTro.getVaiTroEnum().name();
				
				// Thong tin xu ly don
				QuyTrinhXuLyDonEnum quyTrinhXuLy = xuLyDon.getQuyTrinhXuLy();
				String note = vaiTroNguoiDungHienTai + " " + quyTrinhXuLy.getText().toLowerCase() + " ";
				Long congChucId = new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
				Long coQuanQuanLyId = new Long(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());

				if (StringUtils.equals(vaiTroNguoiDungHienTai, VaiTroEnum.VAN_THU.name())) {
					
					// vai tro VAN_THU
					// set thong tin nguoi thuc hien va quy trinh xu ly
					xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
					xuLyDonHienTai.setQuyTrinhXuLy(quyTrinhXuLy);
					if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.TRINH_LANH_DAO)) {
						
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						note = note + VaiTroEnum.LANH_DAO.getText().toLowerCase() + " " + coQuanQuanLyRepo.findOne(coQuanQuanLyId).getTen().toLowerCase().trim() + " ";
						xuLyDonHienTai.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
						xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
						xuLyDonHienTai.setThoiHanXuLy(Utils.convertNumberToLocalDateTime(xuLyDonHienTai.getDon().getNgayTiepNhan(), xuLyDon.getSoNgayXuLy()));
						xuLyDonTiepTheo.setThoiHanXuLy(Utils.convertNumberToLocalDateTime(xuLyDonHienTai.getDon().getNgayTiepNhan(), xuLyDon.getSoNgayXuLy()));
						xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
						xuLyDonTiepTheo.setChucVu(VaiTroEnum.LANH_DAO);
						xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
						xuLyDonTiepTheo.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
						xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
						//xuLyDonTiepTheo.setThoiHanXuLy();
						if (xuLyDonHienTai.isDonChuyen()) {
							
							note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim(); 
							xuLyDonTiepTheo.setDonChuyen(true);
							xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
						}
						xuLyDonHienTai.setGhiChu(note);
						Utils.save(repo, xuLyDonHienTai, congChucId);
						return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.DINH_CHI)) {

						xuLyDonHienTai.setGhiChu(note);
						xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
						xuLyDonHienTai.setNgayQuyetDinhDinhChi(xuLyDon.getNgayQuyetDinhDinhChi());
						xuLyDonHienTai.setSoQuyetDinhDinhChi(xuLyDon.getSoQuyetDinhDinhChi());
						xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						Don don = donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
						don.setNgayQuyetDinhDinhChi(xuLyDon.getNgayQuyetDinhDinhChi());
						don.setSoQuyetDinhDinhChi(xuLyDon.getSoQuyetDinhDinhChi());
						Utils.save(donRepo, don, congChucId);
						return Utils.doSave(repo, xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
					} 
				} else if (StringUtils.equals(vaiTroNguoiDungHienTai, VaiTroEnum.CHUYEN_VIEN.name())) {
					
					// vai tro CHUYEN_VIEN
					xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
					xuLyDonHienTai.setQuyTrinhXuLy(quyTrinhXuLy);
					if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI)) {
						
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						note = note + VaiTroEnum.TRUONG_PHONG.getText().toLowerCase().trim() + " " + xuLyDonHienTai.getPhongBanXuLy().getTen().trim() + " ";
						xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
						xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
						xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
						xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
						xuLyDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
						xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
						xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
						xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
						xuLyDonTiepTheo.setCanBoXuLyChiDinh(xuLyDonHienTai.getCongChuc());
						if (xuLyDonHienTai.isDonChuyen()) {
							
							note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim(); 
							xuLyDonTiepTheo.setDonChuyen(true);
							xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
						}
						xuLyDonHienTai.setGhiChu(note);
						Utils.save(repo, xuLyDonHienTai, congChucId);
						return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.CHUYEN_CHO_VAN_THU)) {
						
						HuongXuLyXLDEnum huongXuLyXLD = xuLyDon.getHuongXuLy();
						// huongXuLy
						note = note + huongXuLyXLD.getText().toLowerCase().trim() + " ";
						xuLyDonHienTai.setHuongXuLy(huongXuLyXLD);
						xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
						if (huongXuLyXLD.equals(HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO)) {
							
							CoQuanQuanLy coQuanQuanLy = xuLyDonHienTai.getPhongBanXuLy().getCha();
							note = note + VaiTroEnum.LANH_DAO.getText() + coQuanQuanLy.getTen().toLowerCase().trim();
							xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
							xuLyDonHienTai.setNgayHenGapLanhDao(xuLyDon.getNgayHenGapLanhDao());
							xuLyDonHienTai.setDiaDiem(xuLyDon.getDiaDiem());
							xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
							Don don = donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
							don.setNgayLapDonGapLanhDaoTmp(xuLyDon.getNgayHenGapLanhDao());
							Utils.save(donRepo, don, congChucId);
							return Utils.doSave(repo, xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
						} else if (huongXuLyXLD.equals(HuongXuLyXLDEnum.DE_XUAT_THU_LY)) {
							
							XuLyDon xuLyDonTiepTheo = new XuLyDon();
							note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " "
							+ xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
							xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
							xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
							xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
							xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
							xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
							xuLyDonTiepTheo.setHuongXuLy(huongXuLyXLD);
							xuLyDonTiepTheo.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
							xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
							xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
							xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
							xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
							xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
							if (xuLyDonHienTai.isDonChuyen()) {
								
								note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim(); 
								xuLyDonTiepTheo.setDonChuyen(true);
								xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
							}
							Utils.save(repo, xuLyDonHienTai, congChucId);
							return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (huongXuLyXLD.equals(HuongXuLyXLDEnum.CHUYEN_DON)) {
							
							XuLyDon xuLyDonTiepTheo = new XuLyDon();
							note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " "
							+ xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
							xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
							xuLyDonHienTai.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
							xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
							xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
							xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
							xuLyDonTiepTheo.setHuongXuLy(huongXuLyXLD);
							xuLyDonTiepTheo.setCoQuanTiepNhan(xuLyDon.getCoQuanTiepNhan());
							xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
							xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
							xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
							xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
							xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
							if (xuLyDonHienTai.isDonChuyen()) {
								
								note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim(); 
								xuLyDonTiepTheo.setDonChuyen(true);
								xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
							}
							Utils.save(repo, xuLyDonHienTai, congChucId);
							return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} else if (huongXuLyXLD.equals(HuongXuLyXLDEnum.KHONG_DU_DIEU_KIEN_THU_LY) || 
								huongXuLyXLD.equals(HuongXuLyXLDEnum.TRA_DON_VA_HUONG_DAN) ||
								huongXuLyXLD.equals(HuongXuLyXLDEnum.LUU_DON_VA_THEO_DOI)) {
							
							XuLyDon xuLyDonTiepTheo = new XuLyDon();
							note = note + VaiTroEnum.VAN_THU.getText().toLowerCase().trim() + " "
							+ xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim();
							xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
							xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
							xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
							xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
							xuLyDonTiepTheo.setHuongXuLy(huongXuLyXLD);
							xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
							xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
							xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
							xuLyDonTiepTheo.setChucVu(VaiTroEnum.VAN_THU);
							xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
							if (xuLyDonHienTai.isDonChuyen()) {
								
								note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim(); 
								xuLyDonTiepTheo.setDonChuyen(true);
								xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
							}
							Utils.save(repo, xuLyDonHienTai, congChucId);
							return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
						} 
					}
				} else if (StringUtils.equals(vaiTroNguoiDungHienTai, VaiTroEnum.TRUONG_PHONG.name())) {
					
					// vai tro TRUONG_PHONG
					xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
					xuLyDonHienTai.setQuyTrinhXuLy(quyTrinhXuLy);
					if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI)) {
						
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						CoQuanQuanLy coQuanQuanLy = xuLyDonHienTai.getPhongBanXuLy().getCha();
						note = note + VaiTroEnum.LANH_DAO.getText().toLowerCase().trim() + " " + coQuanQuanLy.getTen().toLowerCase().trim() + " ";
						xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
						xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
						xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
						xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
						xuLyDonTiepTheo.setChucVu(VaiTroEnum.LANH_DAO);
						xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
						xuLyDonTiepTheo.setPhongBanXuLy(coQuanQuanLy);
						xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
						if (xuLyDonHienTai.isDonChuyen()) {
							
							note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim(); 
							xuLyDonTiepTheo.setDonChuyen(true);
							xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
						}
						xuLyDonHienTai.setGhiChu(note);
						Utils.save(repo, xuLyDonHienTai, congChucId);
						return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					} else if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.GIAO_VIEC)) {
						
						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						note = note + VaiTroEnum.CHUYEN_VIEN.getText().toLowerCase().trim() + " " + xuLyDonHienTai.getPhongBanXuLy().getTen().toLowerCase().trim() + " ";
						xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
						xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
						xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
						xuLyDonTiepTheo.setThoiHanXuLy(xuLyDonHienTai.getThoiHanXuLy());
						xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
						xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh());
						xuLyDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
						xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
						xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
						xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
						if (xuLyDonHienTai.isDonChuyen()) {
							
							note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim(); 
							xuLyDonTiepTheo.setDonChuyen(true);
							xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
						}
						xuLyDonHienTai.setGhiChu(note);
						Utils.save(repo, xuLyDonHienTai, congChucId);
						xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
						return Utils.doSave(repo, xuLyDonTiepTheo, congChucId, eass, HttpStatus.CREATED);
					}
				} else if (StringUtils.equals(vaiTroNguoiDungHienTai, VaiTroEnum.LANH_DAO.name())) {
					
					// vai tro LANH_DAO
					xuLyDonHienTai.setCongChuc(congChucRepo.findOne(congChucId));
					xuLyDonHienTai.setQuyTrinhXuLy(quyTrinhXuLy);
					if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.GIAO_VIEC)) {

						XuLyDon xuLyDonTiepTheo = new XuLyDon();
						xuLyDonHienTai.setPhongBanXuLyChiDinh(xuLyDon.getPhongBanXuLyChiDinh());
						xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
						xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						xuLyDonTiepTheo.setTrangThaiDon(TrangThaiDonEnum.DANG_XU_LY);
						xuLyDonHienTai.setThoiHanXuLy(Utils.convertNumberToLocalDateTime(xuLyDonHienTai.getDon().getNgayTiepNhan(), xuLyDon.getSoNgayXuLy()));
						xuLyDonTiepTheo.setThoiHanXuLy(Utils.convertNumberToLocalDateTime(xuLyDonHienTai.getDon().getNgayTiepNhan(), xuLyDon.getSoNgayXuLy()));
						//xuLyDonHienTai.setThoiHanXuLy(thoiHanXuLy);
						xuLyDonTiepTheo.setDon(xuLyDonHienTai.getDon());
						xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLyChiDinh());
						xuLyDonTiepTheo.setChucVuGiaoViec(VaiTroEnum.LANH_DAO);
						//xuLyDonTiepTheo.setThoiHanXuLy(thoiHanXuLy);
						xuLyDonTiepTheo.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
						xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien() + 1);
						if (xuLyDon.getCanBoXuLyChiDinh() == null) {
							
							note = note + xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim() + " ";
							xuLyDonTiepTheo.setChucVu(VaiTroEnum.TRUONG_PHONG);
						} else {
							
							note = note + xuLyDon.getCanBoXuLyChiDinh().getHoVaTen().trim() + " " +xuLyDon.getPhongBanXuLy().getTen().toLowerCase().trim() + " ";
							xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
							xuLyDonTiepTheo.setChucVu(VaiTroEnum.CHUYEN_VIEN);
							xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh());
						}
						
						if (xuLyDonHienTai.isDonChuyen()) {
							
							note = note + "đơn chuyển từ " + xuLyDonHienTai.getCoQuanChuyenDon().getTen().toLowerCase().trim(); 
							xuLyDonTiepTheo.setDonChuyen(true);
							xuLyDonTiepTheo.setCoQuanChuyenDon(xuLyDonHienTai.getCoQuanChuyenDon());
						}
						xuLyDonHienTai.setGhiChu(note);
						Utils.save(repo, xuLyDonHienTai, congChucId);
						return Utils.doSave(repo, xuLyDonTiepTheo,congChucId, eass, HttpStatus.CREATED);
					} 
					/*else if (quyTrinhXuLy.equals(QuyTrinhXuLyDonEnum.DINH_CHI)) {
						
						xuLyDonHienTai.setGhiChu(note);
						xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
						xuLyDonHienTai.setTrangThaiDon(TrangThaiDonEnum.DA_XU_LY);
						// so quyet dinh va ngay quyet dinh ?
						Don don = donRepo.findOne(donService.predicateFindOne(xuLyDon.getDon().getId()));
						don.setNgayLapDonGapLanhDaoTmp(xuLyDon.getNgayHenGapLanhDao());
						Utils.save(donRepo, don, congChucId);
						return Utils.doSave(repo, xuLyDonHienTai, congChucId, eass, HttpStatus.CREATED);
					}*/
				}
			}
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/xuLyDons")
	@ApiOperation(value = "Quy trình xử lý đơn", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Lưu lại quy trình chuyển đơn thành công", response = XuLyDon.class) })
	public ResponseEntity<Object> save(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody XuLyDon xuLyDon, PersistentEntityResourceAssembler eass) {

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
				// String note = vaiTroNguoiDungHienTai + " " + quyTrinhXuLy.getText().toLowerCase() + " ";
				Long congChucId = new Long(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString());
				Long coQuanQuanLyId = new Long(profileUtil.getCommonProfile(authorization).getAttribute("coQuanQuanLyId").toString());

				if (StringUtils.equals(vaiTroNguoiDungHienTai, VaiTroEnum.VAN_THU.name())) {
					
				}
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());	
		}
		return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
				ApiErrorEnum.ROLE_FORBIDDEN.getText());
	}
}

	// @RequestMapping(method = RequestMethod.PATCH, value =
	// "/xuLyDons/{id}/thuHoi")
	// @ApiOperation(value = "Thu hồi đơn", position = 2, produces =
	// MediaType.APPLICATION_JSON_VALUE)
	// @ApiResponses(value = { @ApiResponse(code = 202, message = "Thu há»“i Ä‘Æ¡n
	// thÃ nh cÃ´ng", response = XuLyDon.class) })
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
	// @ApiResponse(code = 202, message = "Rút đơn thành công", response =
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
