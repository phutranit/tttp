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
import vn.greenglobal.tttp.enums.HuongXuLyXLDEnum;
import vn.greenglobal.tttp.enums.QuyTrinhXuLyDonEnum;
import vn.greenglobal.tttp.model.ChucVu;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.service.XuLyDonService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "xuLyDons", description = "Xử lý đơn")
public class XuLyDonController extends BaseController<XuLyDon> {

	private static Log log = LogFactory.getLog(XuLyDonController.class);
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
	@ApiOperation(value = "Quy trình xử lý đơn", position = 1, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 202, message = "Thêm quy trình xử lý đơn thanh công", response = XuLyDon.class) })
	public ResponseEntity<Object> create(@RequestBody XuLyDon xuLyDon, 
			PersistentEntityResourceAssembler eass) {
		
		if (xuLyDonService.isExists(repo, xuLyDon.getDon().getId())) {

			XuLyDon xuLyDonHienTai = repo.findOne(xuLyDonService.predicateFindMax(
					xuLyDon.getDon().getId()));
			xuLyDonHienTai.setCongChuc(xuLyDon.getCongChuc());
			xuLyDonHienTai.setHuongXuLy(xuLyDon.getHuongXuLy());
			xuLyDonHienTai.setQuyTrinhXuLy(xuLyDon.getQuyTrinhXuLy());
			xuLyDonHienTai.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
			xuLyDonHienTai.setNoiDungYeuCauXuLy(xuLyDon.getNoiDungThongTinTrinhLanhDao());
			xuLyDonHienTai.setyKienXuLy(xuLyDon.getyKienXuLy());
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
			xuLyDonHienTai.setGhiChu(note);
			repo.save(xuLyDonHienTai);
			//Update HuongXuLyDon 
			Don don = donService.updateHuongXuLy(donrepo, xuLyDon.getDon().getId(), 
					xuLyDon.getHuongXuLy());
			don = donService.updateQuyTrinhXuLyDon(donrepo, xuLyDon.getDon().getId(), 
					xuLyDon.getQuyTrinhXuLy());
			donrepo.save(don);
			XuLyDon xuLyDonTiepTheo = new XuLyDon();
			xuLyDonTiepTheo.setDon(xuLyDon.getDon());
			xuLyDonTiepTheo.setThuTuThucHien(xuLyDonHienTai.getThuTuThucHien()+1);
			return Utils.doSave(repo, xuLyDonTiepTheo, eass, HttpStatus.CREATED);
		}

		String note = HuongXuLyXLDEnum.CHUYEN_DON.getText() + "cho lãnh đạo";
		xuLyDon.setThuTuThucHien(0);
		xuLyDon.setHuongXuLy(HuongXuLyXLDEnum.CHUYEN_DON);
		xuLyDon.setNoiDungThongTinTrinhLanhDao(xuLyDon.getNoiDungThongTinTrinhLanhDao());
		xuLyDon.setGhiChu(note);
		repo.save(xuLyDon);
		//Update HuongXuLyDon 
		Don donDau = donService.updateHuongXuLy(donrepo, xuLyDon.getDon().getId(), 
				HuongXuLyXLDEnum.CHUYEN_DON);
		donrepo.save(donDau);
		XuLyDon xuLyDonLanhDao = new XuLyDon();
		xuLyDonLanhDao.setDon(xuLyDon.getDon());
		xuLyDonLanhDao.setThuTuThucHien(1);
		return Utils.doSave(repo, xuLyDonLanhDao, eass, HttpStatus.CREATED);
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
