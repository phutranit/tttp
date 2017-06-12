package vn.greenglobal.tttp.controller;

import org.pac4j.core.profile.CommonProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.Don;
import vn.greenglobal.tttp.model.LichSuQuaTrinhXuLy;
import vn.greenglobal.tttp.repository.DonRepository;
import vn.greenglobal.tttp.repository.LichSuQuaTrinhXuLyRepository;
import vn.greenglobal.tttp.service.DonService;
import vn.greenglobal.tttp.service.LichSuQuaTrinhXuLyService;
import vn.greenglobal.tttp.util.Utils;

@RestController
@RepositoryRestController
@Api(value = "lichSuQuaTrinhXuLys", description = "Lịch sử quá trình xử lý")
public class LichSuQuaTrinhXuLyController extends TttpController<LichSuQuaTrinhXuLy> {

	@Autowired
	private LichSuQuaTrinhXuLyService lichSuQuaTrinhXuLyService;

	@Autowired
	private LichSuQuaTrinhXuLyRepository repo;
	
	@Autowired
	private DonRepository donRepo;
	
	@Autowired
	private DonService donService;
	
	public LichSuQuaTrinhXuLyController(BaseRepository<LichSuQuaTrinhXuLy, Long> repo) {
		super(repo);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/lichSuQuaTrinhXuLys")
	@ApiOperation(value = "Lấy danh sách Lịch sử quá trình xử lý", position = 1, produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody Object getList(@RequestHeader(value = "Authorization", required = true) String authorization,
			Pageable pageable,
			@RequestParam(value = "donId", required = true) Long donId,
			PersistentEntityResourceAssembler eass) {
		CommonProfile commonProfile = profileUtil.getCommonProfile(authorization);
		Long donViId = Long.valueOf(commonProfile.getAttribute("donViId").toString());
		Don don = donRepo.findOne(donService.predicateFindOne(donId));
		if (don == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DON_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		Page<LichSuQuaTrinhXuLy> page = repo.findAll(lichSuQuaTrinhXuLyService.predicateFindAll(donId, donViId), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}
}
