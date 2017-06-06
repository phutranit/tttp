package vn.greenglobal.tttp.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
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
import vn.greenglobal.tttp.model.CongDan;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.model.medial.Medial_DonCongDan;
import vn.greenglobal.tttp.model.medial.Medial_DonCongDan_Delete;
import vn.greenglobal.tttp.model.medial.Medial_DonCongDan_Post_Patch;
import vn.greenglobal.tttp.repository.CongDanRepository;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.service.CongDanService;
import vn.greenglobal.tttp.service.DonCongDanService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "donCongDans", description = "Quan hệ giữa Đơn và Công Dân")
public class DonCongDanController extends TttpController<Don_CongDan> {

	@Autowired
	private DonCongDanRepository repo;

	@Autowired
	private DonCongDanService donCongDanService;

	@Autowired
	private CongDanRepository congDanRepo;
	
	@Autowired
	private CongDanService congDanService;

	public DonCongDanController(BaseRepository<Don_CongDan, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/donCongDans")
	@ApiOperation(value = "Lấy danh sách Quan hệ giữa Đơn và Công Dân", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<Don_CongDan> getList(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestParam(value = "don", required = false) Long don,
			@RequestParam(value = "congDan", required = false) Long congDan,
			@RequestParam(value = "phanLoai", required = false) String phanLoai, Pageable pageable,
			PersistentEntityResourceAssembler eass) {
		Page<Don_CongDan> page = repo.findAll(donCongDanService.predicateFindAll(don, congDan, phanLoai), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/donCongDans")
	@ApiOperation(value = "Thêm mới quan hệ giữa Đơn và Công Dân", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Quan hệ giữa Đơn và Công Dân thành công", response = Don_CongDan.class),
			@ApiResponse(code = 201, message = "Thêm mới Quan hệ giữa Đơn và Công Dân thành công", response = Don_CongDan.class) })
	public ResponseEntity<Object> create(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Don_CongDan donCongDan, PersistentEntityResourceAssembler eass) {
		return Utils.doSave(repo, donCongDan,
				Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
				HttpStatus.CREATED);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.POST, value = "/donCongDans/multi")
	@ApiOperation(value = "Thêm mới nhiều quan hệ giữa Đơn và Công Dân", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới nhiều quan hệ giữa Đơn và Công Dân thành công", response = Medial_DonCongDan_Post_Patch.class),
			@ApiResponse(code = 201, message = "Thêm mới nhiều quan hệ giữa Đơn và Công Dân thành công", response = Medial_DonCongDan_Post_Patch.class) })
	public ResponseEntity<Object> createMulti(
			@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_DonCongDan_Post_Patch params, PersistentEntityResourceAssembler eass) {

		Medial_DonCongDan_Post_Patch result = new Medial_DonCongDan_Post_Patch();

		if (params != null) {
			return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
				@Override
				public Object doInTransaction(TransactionStatus arg0) {
					if (params.getDonCongDans().size() > 0) {
						for (Don_CongDan donCongDan : params.getDonCongDans()) {
							CongDan congDan = null;
							
							if (donCongDan.getCongDan() != null && donCongDan.getCongDan().getId() != null) {
								congDan = congDanRepo.findOne(congDanService.predicateFindOne(donCongDan.getCongDan().getId()));
							}	
							if (congDan == null) {
								congDan = new CongDan();
							}
							congDan.setHoVaTen(donCongDan.getHoVaTen());
							congDan.setDanToc(donCongDan.getDanToc());
							congDan.setQuocTich(donCongDan.getQuocTich());
							congDan.setSoCMNDHoChieu(donCongDan.getSoCMNDHoChieu());
							congDan.setSoDienThoai(donCongDan.getSoDienThoai());
							congDan.setDiaChi(donCongDan.getDiaChi());
							congDan.setNgaySinh(donCongDan.getNgaySinh());
							congDan.setNgayCap(donCongDan.getNgayCap());
							congDan.setGioiTinh(donCongDan.isGioiTinh());
							congDan.setTinhThanh(donCongDan.getTinhThanh());
							congDan.setQuanHuyen(donCongDan.getQuanHuyen());
							congDan.setPhuongXa(donCongDan.getPhuongXa());
							congDan.setToDanPho(donCongDan.getToDanPho());
							congDan.setNoiCapCMND(donCongDan.getNoiCapCMND());
								
							CongDan congDanUpdate = Utils.save(congDanRepo, congDan, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
							if (congDanUpdate != null) {
								donCongDan.setCongDan(congDanUpdate);
								Don_CongDan dcd = Utils.save(repo, donCongDan, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
								result.getDonCongDans().add(dcd);
							}
						}
					}
					return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.CREATED);
				}
			});
		}

		return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/donCongDans/{id}")
	@ApiOperation(value = "Lấy Quan hệ giữa Đơn và Công Dân theo Id", position = 3, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Lấy Quan hệ giữa Đơn và Công Dân thành công", response = Don_CongDan.class) })
	public ResponseEntity<PersistentEntityResource> getDonCongDan(
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		Don_CongDan donCongDan = repo.findOne(donCongDanService.predicateFindOne(id));
		if (donCongDan == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(donCongDan), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/donCongDans/{id}")
	@ApiOperation(value = "Cập nhật quan hệ giữa Đơn và Công Dân", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật quan hệ giữa Đơn và Công Dân thành công", response = Don_CongDan.class) })
	public @ResponseBody ResponseEntity<Object> update(@RequestBody Don_CongDan donCongDan,
			@RequestHeader(value = "Authorization", required = true) String authorization, @PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		donCongDan.setId(id);

		if (!donCongDanService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		return Utils.doSave(repo, donCongDan,
				Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()), eass,
				HttpStatus.OK);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.PATCH, value = "/donCongDans/multi")
	@ApiOperation(value = "Cập nhật nhiều quan hệ giữa Đơn và Công Dân", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Cập nhật nhiều quan hệ giữa Đơn và Công Dân thành công", response = Medial_DonCongDan_Post_Patch.class) })
	public @ResponseBody ResponseEntity<Object> updateMulti(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_DonCongDan_Post_Patch params, PersistentEntityResourceAssembler eass) {

		Medial_DonCongDan_Post_Patch result = new Medial_DonCongDan_Post_Patch();
		List<Don_CongDan> listUpdate = new ArrayList<Don_CongDan>();

		if (params != null) {
			return (ResponseEntity<Object>) getTransactioner().execute(new TransactionCallback() {
				@Override
				public Object doInTransaction(TransactionStatus arg0) {
					if (params.getDonCongDans().size() > 0) {
						for (Don_CongDan donCongDan : params.getDonCongDans()) {
							if (!donCongDanService.isExists(repo, donCongDan.getId())) {
								return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
							}
							listUpdate.add(donCongDan);
						}
						for (Don_CongDan donCongDan : listUpdate) {
							
							CongDan congDan = null;
							
							if (donCongDan.getCongDan() != null && donCongDan.getCongDan().getId() != null) {
								congDan = congDanRepo.findOne(congDanService.predicateFindOne(donCongDan.getCongDan().getId()));
							}		
							if (congDan == null) {
								congDan = new CongDan();
							}
							congDan.setHoVaTen(donCongDan.getHoVaTen());
							congDan.setDanToc(donCongDan.getDanToc());
							congDan.setQuocTich(donCongDan.getQuocTich());
							congDan.setSoCMNDHoChieu(donCongDan.getSoCMNDHoChieu());
							congDan.setSoDienThoai(donCongDan.getSoDienThoai());
							congDan.setDiaChi(donCongDan.getDiaChi());
							congDan.setNgaySinh(donCongDan.getNgaySinh());
							congDan.setNgayCap(donCongDan.getNgayCap());
							congDan.setGioiTinh(donCongDan.isGioiTinh());
							congDan.setTinhThanh(donCongDan.getTinhThanh());
							congDan.setQuanHuyen(donCongDan.getQuanHuyen());
							congDan.setPhuongXa(donCongDan.getPhuongXa());
							congDan.setToDanPho(donCongDan.getToDanPho());
							congDan.setNoiCapCMND(donCongDan.getNoiCapCMND());
								
							CongDan congDanUpdate = Utils.save(congDanRepo, congDan, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
							if (congDanUpdate != null) {
								donCongDan.setCongDan(congDanUpdate);
								Don_CongDan dcd = Utils.save(repo, donCongDan, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
								result.getDonCongDans().add(dcd);
							}
						}
					}
					return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.OK);
				}
			});
		}

		return new ResponseEntity<>(eass.toFullResource(result), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/donCongDans/{id}")
	@ApiOperation(value = "Xoá Quan hệ giữa Đơn và Công Dân", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá Quan hệ giữa Đơn và Công Dân thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value = "Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {
		Don_CongDan dcd = donCongDanService.delete(repo, id);
		if (dcd == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		Utils.save(repo, dcd, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(method = RequestMethod.DELETE, value = "/donCongDans/multi")
	@ApiOperation(value = "Xoá nhiều quan hệ giữa Đơn và Công Dân", position = 5, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = { @ApiResponse(code = 204, message = "Xoá nhiều quan hệ giữa Đơn và Công Dân thành công") })
	public ResponseEntity<Object> deleteMulti(@RequestHeader(value = "Authorization", required = true) String authorization,
			@RequestBody Medial_DonCongDan_Delete params) {
		
		List<Don_CongDan> listDelete = new ArrayList<Don_CongDan>();
		if (params != null && params.getDonCongDans().size() > 0) {
			for (Medial_DonCongDan donCongDan : params.getDonCongDans()) {
				Don_CongDan dcd = donCongDanService.delete(repo, donCongDan.getId());
				if (dcd == null) {
					return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
				}
				listDelete.add(dcd);
			}
			for (Don_CongDan donCongDan : listDelete) {
				Utils.save(repo, donCongDan, Long.valueOf(profileUtil.getCommonProfile(authorization).getAttribute("congChucId").toString()));
			}
		}
		
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
}
