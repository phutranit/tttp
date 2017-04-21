package vn.greenglobal.tttp.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.ChucVuEnum;
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.QuyTrinhXuLyDonEnum;
import vn.greenglobal.tttp.enums.TrangThaiDonEnum;
import vn.greenglobal.tttp.model.ChucVu;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.XuLyDon;
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
	private DonRepository donrepo;

	public XuLyDonController(BaseRepository<XuLyDon, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/xuLyDons")
	@ApiOperation(value = "Quy trình xử lý đơn", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Thêm quy trình xử lý đơn thanh công", response = XuLyDon.class) })
	public ResponseEntity<Object> create(@RequestBody XuLyDon xuLyDon, 
			PersistentEntityResourceAssembler eass) {
		
		if (xuLyDonService.isExists(repo, xuLyDon.getDon().getId())) {

			XuLyDon xuLyDonHienTai = repo.findOne(xuLyDonService.predicateFindMax(
					xuLyDon.getDon().getId()));
			XuLyDon xuLyDonTiepTheo = new XuLyDon();
			String note = "";
			
			if (xuLyDon.getCongChuc().getChucVu().equals(ChucVuEnum.VAN_THU)) {
				
				if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.TRINH_LANH_DAO)) {
					
					// Add ghiChu
					note = ChucVuEnum.VAN_THU.getText() + " " + QuyTrinhXuLyDonEnum.TRINH_LANH_DAO.getText() + " "
							+ xuLyDonHienTai.getPhongBanXuLy().getTen();
					xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
					xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
					xuLyDonHienTai.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
					xuLyDonHienTai.setGhiChu(note);
					repo.save(xuLyDonHienTai);
					xuLyDonTiepTheo.setDon(xuLyDon.getDon());
					xuLyDonTiepTheo.setChucVu(ChucVuEnum.LANH_DAO);
					xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
					xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien()+1);
					return Utils.doSave(repo, xuLyDonTiepTheo, eass, HttpStatus.CREATED);
				}
				
			} else if (xuLyDon.getCongChuc().getChucVu().equals(ChucVuEnum.LANH_DAO)) {
				
				if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.GIAO_VIEC)) {
					
					xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
					xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
					xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
					xuLyDonTiepTheo.setDon(xuLyDon.getDon());
					xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
					xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien()+1);
					xuLyDonTiepTheo.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
					// Add ghiChu
					if (xuLyDon.getCanBoXuLyChiDinh().equals(null)) {
						
						note = ChucVuEnum.LANH_DAO.getText() + " " + QuyTrinhXuLyDonEnum.GIAO_VIEC.getText() + " "
								+ xuLyDon.getPhongBanXuLy().getTen();
						xuLyDonHienTai.setGhiChu(note);
						repo.save(xuLyDonHienTai);
						xuLyDonTiepTheo.setChucVu(ChucVuEnum.TRUONG_PHONG);
						return Utils.doSave(repo, xuLyDonTiepTheo, eass, HttpStatus.CREATED);
					} else {
						
						note = ChucVuEnum.LANH_DAO.getText() + " " + QuyTrinhXuLyDonEnum.GIAO_VIEC.getText() + " "
								+ xuLyDon.getCanBoXuLyChiDinh().getHoVaTen() + " " + xuLyDon.getPhongBanXuLy().getTen();
						xuLyDonHienTai.setGhiChu(note);
						repo.save(xuLyDonHienTai);
						xuLyDonTiepTheo.setChucVu(ChucVuEnum.CAN_BO);
						xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh());
						xuLyDonTiepTheo.setChucVuGiaoViec(ChucVuEnum.LANH_DAO);
						return Utils.doSave(repo, xuLyDonTiepTheo, eass, HttpStatus.CREATED);
					}
				} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.CHUYEN_BO_PHAN_GIAI_QUYET)) {
					
					//Case Don da xu ly
					note = ChucVuEnum.LANH_DAO.getText() + " " + QuyTrinhXuLyDonEnum.CHUYEN_BO_PHAN_GIAI_QUYET.getText() + " "
							+ xuLyDon.getPhongBanXuLy().getTen();
					xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
					xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
					xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
					xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
					xuLyDonHienTai.setGhiChu(note);
					return Utils.doSave(repo, xuLyDonHienTai, eass, HttpStatus.CREATED);
				} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.CHUYEN_CAN_BO_XU_LY)) {
					
					//Case Don da xu ly
					note = ChucVuEnum.LANH_DAO.getText() + " " + QuyTrinhXuLyDonEnum.CHUYEN_CAN_BO_XU_LY.getText() + " "
							+ xuLyDon.getCanBoXuLyChiDinh().getHoVaTen() + " " + xuLyDon.getPhongBanXuLy().getTen();
					xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
					xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
					xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
					xuLyDonHienTai.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
					xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
					xuLyDonHienTai.setGhiChu(note);
					repo.save(xuLyDonHienTai);
					xuLyDonTiepTheo.setDon(xuLyDon.getDon());
					xuLyDonTiepTheo.setChucVu(ChucVuEnum.CAN_BO);
					xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh());
					xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
					xuLyDonTiepTheo.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
					xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien()+1);
					return Utils.doSave(repo, xuLyDonTiepTheo, eass, HttpStatus.CREATED);
				} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.YEU_CAU_KIEM_TRA_LAI)) {
					
					note = ChucVuEnum.LANH_DAO.getText() + " " + QuyTrinhXuLyDonEnum.YEU_CAU_KIEM_TRA_LAI.getText() + " " 
					+ xuLyDon.getPhongBanXuLy().getTen();
					xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
					xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
					xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
					xuLyDonHienTai.setGhiChu(note);
					repo.save(xuLyDonHienTai);
					xuLyDonTiepTheo.setDon(xuLyDon.getDon());
					xuLyDonTiepTheo.setChucVu(ChucVuEnum.TRUONG_PHONG);
					xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
					xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien()+1);
					xuLyDonTiepTheo.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
					return Utils.doSave(repo, xuLyDonTiepTheo, eass, HttpStatus.CREATED);
				} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.DINH_CHI)) {
					
					Don donDau = donService.updateTrangThaiDon(donrepo, xuLyDon.getDon().getId(), TrangThaiDonEnum.DINH_CHI);
					donrepo.save(donDau);
					note = ChucVuEnum.LANH_DAO.getText() + " " + QuyTrinhXuLyDonEnum.DINH_CHI.getText();
					xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
					xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
					xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungYeuCauXuLy());
					xuLyDonHienTai.setGhiChu(note);
					return Utils.doSave(repo, xuLyDonHienTai, eass, HttpStatus.CREATED);
				}
				
			} else if (xuLyDon.getCongChuc().getChucVu().equals(ChucVuEnum.TRUONG_PHONG)) {
				
				if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI)) {
					
					note = ChucVuEnum.TRUONG_PHONG.getText() + " " + QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI.getText() 
					+ " " + ChucVuEnum.LANH_DAO.getText() + xuLyDonHienTai.getPhongBanXuLy().getTen();
					xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
					xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
					xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
					xuLyDonHienTai.setGhiChu(note);
					repo.save(xuLyDonHienTai);
					xuLyDonTiepTheo.setDon(xuLyDon.getDon());
					xuLyDonTiepTheo.setChucVu(ChucVuEnum.LANH_DAO);
					xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
					xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
					xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien()+1);
					return Utils.doSave(repo, xuLyDonTiepTheo, eass, HttpStatus.CREATED);
				} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.GIAO_VIEC)) {
					
					note = ChucVuEnum.TRUONG_PHONG.getText() + " " + QuyTrinhXuLyDonEnum.GIAO_VIEC.getText() 
					+ " " + xuLyDon.getCanBoXuLyChiDinh().getHoVaTen() + xuLyDonHienTai.getPhongBanXuLy().getTen();
					xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
					xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
					xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
					xuLyDonHienTai.setGhiChu(note);
					xuLyDonHienTai.setCanBoXuLyChiDinh(xuLyDon.getCanBoXuLyChiDinh());
					repo.save(xuLyDonHienTai);
					xuLyDonTiepTheo.setDon(xuLyDon.getDon());
					xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLyChiDinh());
					xuLyDonTiepTheo.setChucVu(ChucVuEnum.CAN_BO);
					xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
					xuLyDonTiepTheo.setChucVuGiaoViec(ChucVuEnum.TRUONG_PHONG);
					xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
					xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien()+1);
					return Utils.doSave(repo, xuLyDonTiepTheo, eass, HttpStatus.CREATED);
				} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.YEU_CAU_KIEM_TRA_LAI)) {
					
					note = ChucVuEnum.TRUONG_PHONG.getText() + " " + QuyTrinhXuLyDonEnum.YEU_CAU_KIEM_TRA_LAI.getText() + " " 
							+ xuLyDon.getCanBoXuLy().getHoVaTen() + " " + xuLyDon.getPhongBanXuLy().getTen();
					xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
					xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
					xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
					xuLyDonHienTai.setGhiChu(note);
					xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
					xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
					repo.save(xuLyDonHienTai);
					xuLyDonTiepTheo.setDon(xuLyDon.getDon());
					xuLyDonTiepTheo.setChucVu(ChucVuEnum.CAN_BO);
					xuLyDonTiepTheo.setCongChuc(xuLyDon.getCanBoXuLy());
					xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
					xuLyDonTiepTheo.setyKienXuLy(xuLyDon.getyKienXuLy());
					xuLyDonTiepTheo.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
					xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien()+1);
					xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
					return Utils.doSave(repo, xuLyDonTiepTheo, eass, HttpStatus.CREATED);
				} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.TRINH_LANH_DAO)) {
					
					note = ChucVuEnum.TRUONG_PHONG.getText() + " " + QuyTrinhXuLyDonEnum.TRINH_LANH_DAO.getText() + " "
							+ xuLyDonHienTai.getPhongBanXuLy().getTen();
					xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
					xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
					xuLyDonHienTai.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
					xuLyDonHienTai.setGhiChu(note);
					xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
					xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
					repo.save(xuLyDonHienTai);
					xuLyDonTiepTheo.setDon(xuLyDon.getDon());
					xuLyDonTiepTheo.setChucVu(ChucVuEnum.LANH_DAO);
					xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
					xuLyDonTiepTheo.setHuongXuLy(xuLyDon.getHuongXuLy());
					xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien()+1);
					xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
					xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCanBoXuLy());
					return Utils.doSave(repo, xuLyDonTiepTheo, eass, HttpStatus.CREATED);
				}
			} else if (xuLyDon.getCongChuc().getChucVu().equals(ChucVuEnum.CAN_BO)) {
				
				if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI)) {
					
					note = ChucVuEnum.CAN_BO.getText() + " " + QuyTrinhXuLyDonEnum.DE_XUAT_GIAO_VIEC_LAI.getText() + " "
							+ ChucVuEnum.TRUONG_PHONG.getText() + " " + xuLyDonHienTai.getPhongBanXuLy().getTen();
					xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
					xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
					xuLyDonHienTai.setGhiChu(note);
					repo.save(xuLyDonHienTai);
					xuLyDonTiepTheo.setDon(xuLyDon.getDon());
					xuLyDonTiepTheo.setChucVu(ChucVuEnum.TRUONG_PHONG);
					xuLyDonTiepTheo.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
					xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien()+1);
					return Utils.doSave(repo, xuLyDonTiepTheo, eass, HttpStatus.CREATED);
				} else if (xuLyDon.getQuyTrinhXuLy().equals(QuyTrinhXuLyDonEnum.DE_XUAT_HUONG_XU_LY)) {
					
					note = ChucVuEnum.CAN_BO.getText() + " " + QuyTrinhXuLyDonEnum.DE_XUAT_HUONG_XU_LY.getText() + " "
							+ xuLyDon.getHuongXuLy().getText() + " " ;
					xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
					xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
					xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
					xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
					
					if (xuLyDon.getHuongXuLy().equals(HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO)) {
						
						note = note + ChucVuEnum.LANH_DAO.getText() + " " + xuLyDonHienTai.getPhongBanXuLy().getTen();
						xuLyDonHienTai.setGhiChu(note);
						
						// Cap nhat yeuCauGapLanhDao
						return Utils.doSave(repo, xuLyDonHienTai, eass, HttpStatus.CREATED);
					} else if (xuLyDon.getHuongXuLy().equals(HuongXuLyXLDEnum.YEU_CAU_GAP_LANH_DAO)) {
						
						note = note + ChucVuEnum.TRUONG_PHONG.getText() + " " + xuLyDonHienTai.getPhongBanXuLy().getTen();
						xuLyDonHienTai.setGhiChu(note);
						repo.save(xuLyDonHienTai);
						xuLyDonTiepTheo.setDon(xuLyDon.getDon());
						xuLyDonTiepTheo.setChucVu(ChucVuEnum.TRUONG_PHONG);
						xuLyDonTiepTheo.setPhongBanXuLy(xuLyDonHienTai.getPhongBanXuLy());
						xuLyDonTiepTheo.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
						xuLyDonTiepTheo.setHuongXuLy(xuLyDon.getHuongXuLy());
						xuLyDonTiepTheo.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
						xuLyDonTiepTheo.setCanBoXuLy(xuLyDonHienTai.getCongChuc());
						return Utils.doSave(repo, xuLyDonTiepTheo, eass, HttpStatus.CREATED);
					}
				}
			}
		}

		// Add new record for VAN_THU
		xuLyDon.setPhongBanXuLy(xuLyDon.getPhongBanXuLy());
		xuLyDon.setChucVu(ChucVuEnum.VAN_THU);
		xuLyDon.setThuTuThucHien(0);
		Don donDau = donService.updateTrangThaiDon(donrepo, xuLyDon.getDon().getId(), TrangThaiDonEnum.CHO_XU_LY);
		donrepo.save(donDau);
		return Utils.doSave(repo, xuLyDon, eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/xuLyDons/{id}/dinhchi")
	@ApiOperation(value = "Quy trình rút đơn", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Đình chỉ đơn thanh công", response = XuLyDon.class) })
	public ResponseEntity<Object> rutDon(@RequestBody XuLyDon xuLyDon, @PathVariable("id") Long id,
			PersistentEntityResourceAssembler eass) {

		Don don = donService.updateQuyTrinhXuLyDon(donrepo, id, QuyTrinhXuLyDonEnum.DINH_CHI);
		
		if (don == null) {
			
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		
		don.setLyDoDinhChi("Rút đơn");
		donrepo.save(don);
		if (xuLyDonService.isExists(repo, id)) {
			
			XuLyDon xuLyDonHienTai = repo.findOne(xuLyDonService.predicateFindMax(
					xuLyDon.getDon().getId()));
			xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
			xuLyDonHienTai.setQuyTrinhXuLy(QuyTrinhXuLyDonEnum.DINH_CHI);
			xuLyDonHienTai.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
			xuLyDonHienTai.setMoTaTrangThai(xuLyDon.getMoTaTrangThai());
			xuLyDonHienTai.setThamQuyenGiaiQuyet(xuLyDon.getThamQuyenGiaiQuyet());
			String note = "";
			ChucVu chucVu = xuLyDon.getCongChuc().getChucVu();
			note = chucVu.getTen();
			if (StringUtils.isNotBlank(xuLyDon.getHuongXuLy().getText()) &&
					StringUtils.isBlank(xuLyDon.getQuyTrinhXuLy().getText())){
				note = note + " " + xuLyDon.getHuongXuLy().getText();
			} else if (StringUtils.isBlank(xuLyDon.getHuongXuLy().getText()) &&
					StringUtils.isNotBlank(xuLyDon.getQuyTrinhXuLy().getText())) {
				note = note + " " + xuLyDon.getQuyTrinhXuLy().getText();
			}
			return Utils.doSave(repo, xuLyDonHienTai, eass, HttpStatus.CREATED);
		}

		XuLyDon xuLyDonVanThu = new XuLyDon();
		xuLyDonVanThu.setCongChuc(xuLyDon.getCongChuc());
		xuLyDonVanThu.setPhongBanGiaiQuyet(xuLyDon.getPhongBanGiaiQuyet());
		xuLyDonVanThu.setThuTuThucHien(1);
		xuLyDonVanThu.setQuyTrinhXuLy(QuyTrinhXuLyDonEnum.DINH_CHI);
		return Utils.doSave(repo, xuLyDonVanThu, eass, HttpStatus.CREATED);
	}
}
