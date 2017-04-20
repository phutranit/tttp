package vn.greenglobal.tttp.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.pac4j.core.credentials.TokenCredentials;
import org.pac4j.core.profile.CommonProfile;
import org.pac4j.http.client.direct.HeaderClient;
import org.pac4j.jwt.config.encryption.SecretEncryptionConfiguration;
import org.pac4j.jwt.config.signature.SecretSignatureConfiguration;
import org.pac4j.jwt.config.signature.SignatureConfiguration;
import org.pac4j.jwt.credentials.authenticator.JwtAuthenticator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.enums.ApiErrorEnum;
import vn.greenglobal.tttp.model.ChucVu;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.repository.ChucVuRepository;
import vn.greenglobal.tttp.service.ChucVuService;
import vn.greenglobal.tttp.util.MessageByLocaleService;
import vn.greenglobal.tttp.util.ProfileUtils;
import vn.greenglobal.tttp.util.Utils;
import vn.greenglobal.tttp.util.patch.Patch;
import vn.greenglobal.tttp.util.patch.PatchRequestBody;

@RestController
@RepositoryRestController
@Api(value = "chucVus", description = "Chức Vụ")
public class ChucVuController extends BaseController<ChucVu> {

	private static Log log = LogFactory.getLog(ChucVuController.class);
	private static ChucVuService chucVuService = new ChucVuService();

	@Autowired
	ProfileUtils profileUtil;
	@Autowired
	MessageByLocaleService message;
	@Autowired
	private ChucVuRepository repo;

	public ChucVuController(BaseRepository<ChucVu, Long> repo) {
		super(repo);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/chucVus")
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Thêm mới Chức Vụ thành công", response = ChucVu.class),
			@ApiResponse(code = 201, message = "Thêm mới Chức Vụ thành công", response = ChucVu.class)})
	public ResponseEntity<Object> create(
			@RequestHeader(value="Authorization", required = true) String authorization,
			@RequestBody ChucVu chucVu,
			PersistentEntityResourceAssembler eass) {
		if (chucVu.getTen() == null || "".equals(chucVu.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_REQUIRED.name(),
					ApiErrorEnum.TEN_REQUIRED.getText());
		}
		
		if (chucVuService.checkExistsData(repo, chucVu)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
					ApiErrorEnum.TEN_EXISTS.getText());
		}
		
		repo.save(chucVu);
		return new ResponseEntity<>(eass.toFullResource(chucVu), HttpStatus.CREATED);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(method = RequestMethod.GET, value = "/chucVus")
	@ApiOperation(value = "Lấy danh sách Chức Vụ", position=1, produces=MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody PagedResources<ChucVu> getList(
			@RequestHeader(value="Authorization", required = true) String authorization, Pageable pageable,
			@RequestParam(value = "ten", required = false) String ten,
			PersistentEntityResourceAssembler eass) {
		System.out.println(profileUtil.getUserInfo(authorization));
		Page<ChucVu> page = repo.findAll(chucVuService.predicateFindAll(ten), pageable);
		return assembler.toResource(page, (ResourceAssembler) eass);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/chucVus/{id}")
	@ApiOperation(value = "Lấy Chức Vụ theo Id", position=3, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Lấy Chức Vụ thành công", response = ChucVu.class) })
	public ResponseEntity<PersistentEntityResource> getChucVu(
			@RequestHeader(value="Authorization", required = true) String authorization,
			@PathVariable("id") long id,
			PersistentEntityResourceAssembler eass) {
		
		ChucVu chucVu = repo.findOne(chucVuService.predicateFindOne(id));
		if (chucVu == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(eass.toFullResource(chucVu), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.PUT, value = "/chucVus/{id}")
	@ApiOperation(value = "Cập nhật Chức Vụ", position=4, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 200, message = "Cập nhật Chức Vụ thành công", response = ChucVu.class) })
	public @ResponseBody ResponseEntity<Object> update(
			@RequestHeader(value="Authorization", required = true) String authorization,
			@PathVariable("id") long id,
			@RequestBody ChucVu chucVu,
			PersistentEntityResourceAssembler eass) {
		chucVu.setId(id);
		if (chucVu.getTen() == null || "".equals(chucVu.getTen())) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_REQUIRED.name(),
					ApiErrorEnum.TEN_REQUIRED.getText());
		}
		
		if (chucVuService.checkExistsData(repo, chucVu)) {
			return Utils.responseErrors(HttpStatus.BAD_REQUEST, ApiErrorEnum.TEN_EXISTS.name(),
					ApiErrorEnum.TEN_EXISTS.getText());
		}
		
		if (!chucVuService.isExists(repo, id)) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		
		repo.save(chucVu);
		return new ResponseEntity<>(eass.toFullResource(chucVu), HttpStatus.OK);
	}

	@RequestMapping(method = RequestMethod.DELETE, value = "/chucVus/{id}")
	@ApiOperation(value = "Xoá Chức Vụ", position=5, produces=MediaType.APPLICATION_JSON_VALUE)
	@ApiResponses(value = {@ApiResponse(code = 204, message = "Xoá Chức Vụ thành công") })
	public ResponseEntity<Object> delete(@RequestHeader(value="Authorization", required = true) String authorization,
			@PathVariable("id") Long id) {
		ChucVu chucVu = chucVuService.deleteChucVu(repo, id);
		if (chucVu == null) {
			return Utils.responseErrors(HttpStatus.NOT_FOUND, ApiErrorEnum.DATA_NOT_FOUND.name(),
					ApiErrorEnum.DATA_NOT_FOUND.getText());
		}
		repo.save(chucVu);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
