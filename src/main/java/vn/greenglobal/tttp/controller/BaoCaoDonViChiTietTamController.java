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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.BaoCaoDonViChiTietTam;
import vn.greenglobal.tttp.repository.BaoCaoDonViChiTietTamRepository;
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
}
