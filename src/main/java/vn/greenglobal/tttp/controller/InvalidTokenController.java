package vn.greenglobal.tttp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.InvalidToken;
import vn.greenglobal.tttp.repository.InvalidTokenRepository;

@RestController
@RepositoryRestController
@Api(value = "invalidTokens", description = "Invalid Tokens")
public class InvalidTokenController extends TttpController<InvalidToken> {

	@Autowired
	private InvalidTokenRepository repo;

	public InvalidTokenController(BaseRepository<InvalidToken, ?> repo) {
		super(repo);
	}

}
