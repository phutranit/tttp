package vn.greenglobal.tttp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.Don_CongDan;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.service.DonCongDanService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "donCongDans", description = "Quan hệ giữa Đơn và Công Dân")
public class DonCongDanController extends BaseController<Don_CongDan> {

	private static Log log = LogFactory.getLog(DonCongDanController.class);
	private static DonCongDanService donCongDanService = new DonCongDanService();

	@Autowired
	private DonCongDanRepository repo;

	public DonCongDanController(BaseRepository<Don_CongDan, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/donCongDans")
	@ApiOperation(value = "Lấy danh sách Quan hệ giữa Đơn và Công Dân", position=1, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<Don_CongDan> getList(Pageable pageable,
			@RequestParam(value = "don", required = false) Long don,
			@RequestParam(value = "phanLoai", required = false) String phanLoai,
			PersistentEntityResourceAssembler eass) {
		Page<Don_CongDan> page = repo.findAll(donCongDanService.predicateFindAll(don, phanLoai), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/donCongDans")
	@ApiOperation(value = "Thêm mới Quan hệ giữa Đơn và Công Dân", position=2, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Thêm mới Quan hệ giữa Đơn và Công Dân thành công", response = Don_CongDan.class),
			@ApiResponse(code = 201, message = "Thêm mới Quan hệ giữa Đơn và Công Dân thành công", response = Don_CongDan.class)})
	public ResponseEntity<Object> create(@RequestBody Don_CongDan donCongDan,
			PersistentEntityResourceAssembler eass) {
		return Utils.doSave(repo, donCongDan, eass, HttpStatus.CREATED);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/donCongDans/{id}")
	@ApiOperation(value = "Lấy Quan hệ giữa Đơn và Công Dân theo Id", position=3, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy Quan hệ giữa Đơn và Công Dân thành công", response = Don_CongDan.class) })
	public ResponseEntity<PersistentEntityResource> getDonCongDan(@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		Don_CongDan donCongDan = repo.findOne(donCongDanService.predicateFindOne(id));
		if (donCongDan == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(donCongDan), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PATCH, value = "/donCongDans/{id}")
	@ApiOperation(value = "Cập nhật Quan hệ giữa Đơn và Công Dân", position=4, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Cập nhật Quan hệ giữa Đơn và Công Dân thành công", response = Don_CongDan.class) })
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody Don_CongDan donCongDan, PersistentEntityResourceAssembler eass) {
		donCongDan.setId(id);		

		if (!donCongDanService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		return Utils.doSave(repo, donCongDan, eass, HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/donCongDans/{id}")
	@ApiOperation(value = "Xoá Quan hệ giữa Đơn và Công Dân", position=5, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 204, message = "Xoá Quan hệ giữa Đơn và Công Dân thành công") })
	public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
		Don_CongDan donCongDan = donCongDanService.deleteDonCongDan(repo, id);
		if (donCongDan == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(), ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(donCongDan);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
