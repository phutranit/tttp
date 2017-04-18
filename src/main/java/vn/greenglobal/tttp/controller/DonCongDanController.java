package vn.greenglobal.tttp.controller;

import javax.persistence.EntityManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.*;
import vn.greenglobal.tttp.repository.DonCongDanRepository;
import vn.greenglobal.tttp.service.DonCongDanService;

@SuppressWarnings("unused")
@RestController
@RepositoryRestController
@Api(value = "dongCongDans", description = "Tiếp công dân")
public class DonCongDanController extends BaseController<Don_CongDan> {

	private static Log log = LogFactory.getLog(DonCongDanController.class);
	private static DonCongDanService donCongDanService = new DonCongDanService();

	
	@Autowired
	private DonCongDanRepository repo;
	
	@Autowired
	public EntityManager em;
	@Autowired
	public PlatformTransactionManager transactionManager;
	@Autowired
	public TransactionTemplate transactioner;
	
	public DonCongDanController(BaseRepository<Don_CongDan, Long> repo) {
		super(repo);
	}
}
