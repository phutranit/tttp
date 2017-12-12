package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.enums.QuyenEnum;
import vn.greenglobal.tttp.enums.TrangThaiBaoCaoDonViEnum;
import vn.greenglobal.tttp.model.BaoCaoDonViChiTiet;
import vn.greenglobal.tttp.model.BaoCaoDonViChiTietTam;
import vn.greenglobal.tttp.model.CoQuanQuanLy;
import vn.greenglobal.tttp.repository.BaoCaoDonViChiTietRepository;
import vn.greenglobal.tttp.repository.BaoCaoDonViChiTietTamRepository;
import vn.greenglobal.tttp.repository.CoQuanQuanLyRepository;
import vn.greenglobal.tttp.service.BaoCaoDonViChiTietService;
import vn.greenglobal.tttp.service.BaoCaoDonViChiTietTamService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "baoCaoDonViChiTietTams", description = "Báo cáo đơn vị chi tiết")
public class BaoCaoDonViChiTietTamController extends TttpController<BaoCaoDonViChiTietTam> {

	@Autowired
	private BaoCaoDonViChiTietTamRepository repo;

	@Autowired
	private BaoCaoDonViChiTietTamService baoCaoDonViChiTietTamService;

	@Autowired
	private BaoCaoDonViChiTietRepository baoCaoDonViChiTietRepo;

	@Autowired
	private BaoCaoDonViChiTietService baoCaoDonViChiTietService;
	
	@Autowired 
	private CoQuanQuanLyRepository coQuanQuanLyRepo;

	public BaoCaoDonViChiTietTamController(BaseRepository<BaoCaoDonViChiTietTam, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/baoCaoDonViChiTietTams/{id}")
	@ApiOperation(value = "Lấy danh sách các báo cáo chi tiết đang lưu tạm của biểu mẫu", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable, @PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.tokenValidate(profileUtil, authorization) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}		
			
			List<BaoCaoDonViChiTietTam> listTam = (List<BaoCaoDonViChiTietTam>) repo.findAll(baoCaoDonViChiTietTamService.predicateFindAll(id));
			List<BaoCaoDonViChiTietTam> list = new ArrayList<>();
			for (BaoCaoDonViChiTietTam bc : listTam) {
				bc.setSoLieuBaoCao(baoCaoDonViChiTietTamService.getDataFromDB(bc));
				list.add(bc);
			}
			
			Page<BaoCaoDonViChiTietTam> pages = new PageImpl<BaoCaoDonViChiTietTam>(list, pageable, list.size());
			
			return assembler.toResource(pages, (ResourceAssembler) eass);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	@RequestMapping(method = RequestMethod.GET, value = "/baoCaoDonViChiTietTams/{id}/themDonVi/{idDonVi}")
	@ApiOperation(value = "Lấy số liệu biểu mẫu tạm của đơn vị được thêm mới đã tồn tại", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy số liệu biểu mẫu của báo cáo thành công", response = BaoCaoDonViChiTietTam.class) })
	public ResponseEntity<Object> getThemMoiDonViDaTonTai(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long idChiTiet, @PathVariable("idDonVi") long idDonVi, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.BAOCAODONVI_LIETKE) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			BaoCaoDonViChiTiet baoCaoDonViChiTietCha = baoCaoDonViChiTietRepo.findOne(baoCaoDonViChiTietService.predicateFindOne(idChiTiet));
			if (baoCaoDonViChiTietCha == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			CoQuanQuanLy donVi = coQuanQuanLyRepo.findOne(idDonVi);
			
			BaoCaoDonViChiTiet baoCaoDonViChiTiet = new BaoCaoDonViChiTiet();
			baoCaoDonViChiTiet.setCha(baoCaoDonViChiTietCha);
			baoCaoDonViChiTiet.setBaoCaoDonVi(baoCaoDonViChiTietCha.getBaoCaoDonVi());
			baoCaoDonViChiTiet.setLoaiBaoCao(baoCaoDonViChiTietCha.getLoaiBaoCao());
			baoCaoDonViChiTiet.setId(0L);
			baoCaoDonViChiTiet.setDonVi(donVi);
			baoCaoDonViChiTiet.setTrangThaiBaoCao(TrangThaiBaoCaoDonViEnum.DANG_SOAN);
			baoCaoDonViChiTiet.setSoLieuBaoCao(Utils.getJsonSoLieuByLoaiBaoCao(baoCaoDonViChiTietCha.getLoaiBaoCao(), donVi.getTen()));

			BaoCaoDonViChiTietTam baoCaoDonViChiTietTam = new BaoCaoDonViChiTietTam();
			baoCaoDonViChiTietTam.setId(0L);
			baoCaoDonViChiTietTam.setBaoCaoDonViChiTiet(baoCaoDonViChiTiet);
			baoCaoDonViChiTietTam.setSoLieuBaoCao(Utils.getJsonSoLieuByLoaiBaoCao(baoCaoDonViChiTietCha.getLoaiBaoCao(), donVi.getTen()));
			baoCaoDonViChiTietTam.setSoLieuBaoCao(baoCaoDonViChiTietTamService.getDataFromDB(baoCaoDonViChiTietTam));
			
			return new ResponseEntity<>(eass.toFullResource(baoCaoDonViChiTietTam), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/baoCaoDonViChiTietTams/{id}/themDonVi")
	@ApiOperation(value = "Lấy số liệu biểu mẫu tạm của đơn vị được thêm mới chưa tồn tại", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Lấy số liệu biểu mẫu của báo cáo thành công", response = BaoCaoDonViChiTietTam.class) })
	public ResponseEntity<Object> getThemMoiDonViChuaTonTai(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") long idChiTiet, PersistentEntityResourceAssembler eass) {

		try {
			if (Utils.quyenValidate(profileUtil, authorization, QuyenEnum.BAOCAODONVI_LIETKE) == null) {
				return Utils.responseErrors(HttpStatus.FORBIDDEN, ApiErrorEnum.ROLE_FORBIDDEN.name(),
						ApiErrorEnum.ROLE_FORBIDDEN.getText(), ApiErrorEnum.ROLE_FORBIDDEN.getText());
			}

			BaoCaoDonViChiTiet baoCaoDonViChiTietCha = baoCaoDonViChiTietRepo.findOne(baoCaoDonViChiTietService.predicateFindOne(idChiTiet));
			if (baoCaoDonViChiTietCha == null) {
				return new ResponseEntity<>(HttpStatus.NOT_FOUND);
			}
			
			BaoCaoDonViChiTiet baoCaoDonViChiTiet = new BaoCaoDonViChiTiet();
			baoCaoDonViChiTiet.setCha(baoCaoDonViChiTietCha);
			baoCaoDonViChiTiet.setBaoCaoDonVi(baoCaoDonViChiTietCha.getBaoCaoDonVi());
			baoCaoDonViChiTiet.setLoaiBaoCao(baoCaoDonViChiTietCha.getLoaiBaoCao());
			baoCaoDonViChiTiet.setTuThem(true);
			baoCaoDonViChiTiet.setId(0L);
			baoCaoDonViChiTiet.setTrangThaiBaoCao(TrangThaiBaoCaoDonViEnum.DANG_SOAN);
			baoCaoDonViChiTiet.setSoLieuBaoCao(Utils.getJsonSoLieuByLoaiBaoCao(baoCaoDonViChiTietCha.getLoaiBaoCao(), ""));

			BaoCaoDonViChiTietTam baoCaoDonViChiTietTam = new BaoCaoDonViChiTietTam();
			baoCaoDonViChiTietTam.setId(0L);
			baoCaoDonViChiTietTam.setBaoCaoDonViChiTiet(baoCaoDonViChiTiet);
			baoCaoDonViChiTietTam.setSoLieuBaoCao(Utils.getJsonSoLieuByLoaiBaoCao(baoCaoDonViChiTietCha.getLoaiBaoCao(), ""));
			
			return new ResponseEntity<>(eass.toFullResource(baoCaoDonViChiTietTam), HttpStatus.OK);
		} catch (Exception e) {
			return Utils.responseInternalServerErrors(e);
		}
	}
	
}
