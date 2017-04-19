package vn.greenglobal.tttp.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.XuLyDon;
import vn.greenglobal.tttp.repository.XuLyDonRepository;
import vn.greenglobal.tttp.service.XuLyDonService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "xuLyDons", description = "Xử lý đơn")
public class XuLyDonController extends BaseController<XuLyDon> {

	private static Log log = LogFactory.getLog(XuLyDonController.class);
	private static XuLyDonService xuLyDonService = new XuLyDonService();
	
	@Autowired
	private XuLyDonRepository repo;
	
	public XuLyDonController(BaseRepository<XuLyDon, Long> repo) {
		super(repo);
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/xuLyDons")
	public ResponseEntity<Object> create(
			@RequestBody XuLyDon xuLyDonBefore,
			@RequestBody XuLyDon xuLyDonAfter,
			PersistentEntityResourceAssembler eass) {
		log.info(xuLyDonBefore);
//		if (xuLyDonBefore.getDon().getId() == xuLyDonAfter.getDon().getId()) {
//		
//			
//		}
//		
//		if (xuLyDonService.isExists(repo, xuLyDonAfter.getDon().getId())) {
//		
//			log.info("Update data row Current User");
//			
//		}
//		log.info("Insert data row Current User");
//		repo.save(xuLyDonAfter);
		return Utils.doSave(repo, xuLyDonBefore, eass, HttpStatus.CREATED);	
	}
	
	/*@RequestMapping(method = RequestMethod.POST, value = "/thamQuyenGiaiQuyets")
	@ApiOperation(value = "Thêm mới Thẩm Quyền Giải Quyết", position = 2, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Thêm mới Thẩm Quyền Giải Quyết thành công", response = ThamQuyenGiaiQuyet.class),
			@ApiResponse(code = 201, message = "Thêm mới Thẩm Quyền Giải Quyết thành công", response = ThamQuyenGiaiQuyet.class) })
	public ResponseEntity<Object> create(@RequestBody ThamQuyenGiaiQuyet thamQuyenGiaiQuyet,
			PersistentEntityResourceAssembler eass) {
		log.info("Tao moi ThamQuyenGiaiQuyet");

		if (StringUtils.isNotBlank(thamQuyenGiaiQuyet.getTen())
				&& thamQuyenGiaiQuyetService.checkExistsData(repo, thamQuyenGiaiQuyet)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
					ApiErrorEnum.TEN_EXISTS.getText());
		}
		return Utils.doSave(repo, thamQuyenGiaiQuyet, eass, HttpStatus.CREATED);
	}
	
	@RequestMapping(method = RequestMethod.PATCH, value = "/thamQuyenGiaiQuyets/{id}")
	@ApiOperation(value = "Cập nhật Thẩm Quyền Giải Quyết", position = 4, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Cập nhật Thẩm Quyền Giải Quyết thành công", response = ThamQuyenGiaiQuyet.class) })
	public @ResponseBody ResponseEntity<Object> update(@PathVariable("id") long id,
			@RequestBody ThamQuyenGiaiQuyet thamQuyenGiaiQuyet, PersistentEntityResourceAssembler eass) {
		log.info("Update ThamQuyenGiaiQuyet theo id: " + id);

		thamQuyenGiaiQuyet.setId(id);

		if (StringUtils.isNotBlank(thamQuyenGiaiQuyet.getTen())
				&& thamQuyenGiaiQuyetService.checkExistsData(repo, thamQuyenGiaiQuyet)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
					ApiErrorEnum.TEN_EXISTS.getText());
		}

		if (!thamQuyenGiaiQuyetService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}

		return Utils.doSave(repo, thamQuyenGiaiQuyet, eass, HttpStatus.OK);
	}*/
}
