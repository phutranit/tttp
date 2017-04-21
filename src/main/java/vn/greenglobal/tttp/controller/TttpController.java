package vn.greenglobal.tttp.controller;

import org.springframework.beans.factory.annotation.Autowired;

import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.util.MessageByLocaleService;
import vn.greenglobal.tttp.util.ProfileUtils;

public class TttpController<T> extends BaseController<T> {

	@Autowired
	ProfileUtils profileUtil;
	
	@Autowired
	MessageByLocaleService message;
	
	
	public TttpController(BaseRepository<T, ?> repo) {
		super(repo);
	}

	public ProfileUtils getProfileUtil() {
		return profileUtil;
	}
	
	public MessageByLocaleService getMessage() {
		return message;
	}
}
