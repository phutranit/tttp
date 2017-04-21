package vn.greenglobal.tttp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.HuongGiaiQuyetTCDEnum;
import vn.greenglobal.tttp.enums.LoaiTiepDanEnum;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.model.CoQuanToChucTiepDan;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.SoTiepCongDan;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.repository.CoQuanToChucTiepDanRepository;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.SoTiepCongDanRepository;
import vn.greenglobal.tttp.service.SoTiepCongDanService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "soTiepCongDans", description = "Sổ tiếp công dân")
public class SoTiepCongDanController extends TttpController<SoTiepCongDan> {

	@Autowired
	private SoTiepCongDanRepository repo;
	
	@Autowired
	private SoTiepCongDanService soTiepCongDanService;

	@Autowired
	private DonRepository repoDon;
	
	@Autowired
	private CoQuanQuanLyRepository repoCoQuanQuanLy;

	@Autowired
	private CoQuanToChucTiepDanRepository repoCoQuanToChucTiepDan;

	public SoTiepCongDanController(BaseRepository<SoTiepCongDan, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans")
	@ApiOperation(value = "Lấy danh sách Tiếp Công Dân", position = 1, produces = MediaType.APPLICATION_JSON_VALUE, response = SoTiepCongDan.class)
	public @ResponseBody PagedResources<SoTiepCongDan> getDanhSachTiepCongDans(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "tuKhoa", required = false) String tuKhoa,
			@RequestParam(value = "phanLoaiDon", required = false) String phanLoaiDon,
			@RequestParam(value = "huongXuLy", required = false) String huongXuLy,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay,
			@RequestParam(value = "loaiTiepCongDan", required = false) String loaiTiepCongDan,
			PersistentEntityResourceAssembler eass) {
		
		boolean thanhLapDon = false;
		Page<SoTiepCongDan> page = repo.findAll(soTiepCongDanService.predicateFindAllTCD(tuKhoa, phanLoaiDon, huongXuLy,
				tuNgay, denNgay, loaiTiepCongDan, thanhLapDon), pageable);
		
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/soTiepCongDans/{id}")
	@ApiOperation(value = "Lấy Tiếp Công Dân theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy lượt Tiếp Công Dân thành công", response = SoTiepCongDan.class) })
	public ResponseEntity<PersistentEntityResource> getSoTiepCongDans(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		
		SoTiepCongDan soTiepCongDan = repo.findOne(soTiepCongDanService.predicateFindOne(id));
		if (soTiepCongDan == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(soTiepCongDan), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/soTiepCongDans")
	@ApiOperation(value = "Thêm mới Sổ Tiếp Công Dân", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Sổ Tiếp Công Dân thành công", response = SoTiepCongDan.class),
			@ApiResponse(code = 201, message = "Thêm mới Sổ Tiếp Công Dân thành công", response = SoTiepCongDan.class) })
	public ResponseEntity<Object> createSoTiepCongDan(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody SoTiepCongDan soTiepCongDan, PersistentEntityResourceAssembler eass) {
		
		if (soTiepCongDan != null && soTiepCongDan.getCoQuanToChucTiepDans().isEmpty()) {
			for (CoQuanToChucTiepDan coQuanToChucTiepDan : soTiepCongDan.getCoQuanToChucTiepDans()) {
				repoCoQuanToChucTiepDan.save(coQuanToChucTiepDan);
			}
		}		
		Don don = repoDon.findOne(soTiepCongDan.getDon().getId());
		soTiepCongDan.setDon(don);
		int soLuotTiep = soTiepCongDan.getDon().getTongSoLuotTCD();
		soTiepCongDan.setSoThuTuLuotTiep(soLuotTiep + 1);
		soTiepCongDan.getDon().setTongSoLuotTCD(soLuotTiep + 1);		
		if (LoaiTiepDanEnum.DINH_KY.equals(soTiepCongDan.getLoaiTiepDan()) 
				|| LoaiTiepDanEnum.DOT_XUAT.equals(soTiepCongDan.getLoaiTiepDan())) {
			if (HuongGiaiQuyetTCDEnum.GIAI_QUYET_NGAY.equals(soTiepCongDan.getHuongGiaiQuyet())) {
				soTiepCongDan.getDon().setDaGiaiQuyet(true);
			} else if (HuongGiaiQuyetTCDEnum.GIAO_DON_VI.equals(soTiepCongDan.getHuongGiaiQuyet())) {
				if (soTiepCongDan.getPhongBanGiaiQuyet() != null && soTiepCongDan.getPhongBanGiaiQuyet().getId() > 0) {
					CoQuanQuanLy phongBan = repoCoQuanQuanLy.findOne(soTiepCongDan.getPhongBanGiaiQuyet().getId());
					soTiepCongDan.getDon().setDaXuLy(true);
					soTiepCongDan.getDon().setPhongBanGiaiQuyet(phongBan);
					soTiepCongDan.getDon().setyKienXuLyDon(soTiepCongDan.getyKienXuLy());
					soTiepCongDan.getDon().setGhiChuXuLyDon(soTiepCongDan.getGhiChuXuLy());
				} else {
					return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.name(), ApiErrorEnum.PHONG_BAN_GIAI_QUYET_REQUIRED.getText());
				}
			}
		}			
		repoDon.save(soTiepCongDan.getDon());
		return Utils.doSave(repo, soTiepCongDan, eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/soTiepCongDan/{id}")
	@ApiOperation(value = "Cập nhật Sổ Tiếp Công Dân", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Sổ Tiếp Công Dân thành công", response = SoTiepCongDan.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			@RequestBody SoTiepCongDan soTiepCongDan, PersistentEntityResourceAssembler eass) {

		soTiepCongDan.setId(id);
		for (CoQuanToChucTiepDan coQuanToChucTiepDan : soTiepCongDan.getCoQuanToChucTiepDans()) {
			repoCoQuanToChucTiepDan.save(coQuanToChucTiepDan);
		}

		return Utils.doSave(repo, soTiepCongDan, eass, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/soTiepCongDan/{id}")
	@ApiOperation(value = "Xoá Sổ Tiếp Công Dân", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Sổ Tiếp Công Dân thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {

		SoTiepCongDan soTiepCongDan = soTiepCongDanService.deleteSoTiepCongDan(repo, id);
		if (soTiepCongDan == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(soTiepCongDan);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/hoSoVuViecYeuCauGapLanhDaos")
	@ApiOperation(value = "Lấy danh sách Hồ Sơ Vụ Việc Yêu Cầu Gặp Lãnh Đạo", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<SoTiepCongDan> getListHoSoVuViecYeuCauGapLanhDao(
			@RequestHeader(value = "Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "tuNgay", required = false) String tuNgay,
			@RequestParam(value = "denNgay", required = false) String denNgay, PersistentEntityResourceAssembler eass) {

		Page<SoTiepCongDan> page = repo.findAll(soTiepCongDanService.predicateFindTCDYeuCauGapLanhDao(tuNgay, denNgay),
				pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

}
