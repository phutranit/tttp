package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.InvalidToken;
import vn.greenglobal.tttp.model.QInvalidToken;
import vn.greenglobal.tttp.repository.InvalidTokenRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class InvalidTokenService {
	BooleanExpression base = QInvalidToken.invalidToken.daXoa.isFalse();

	@Autowired
	private InvalidTokenRepository repo;

	public Predicate predicateFindOne(Long id) {
		return base.and(QInvalidToken.invalidToken.id.eq(id));
	}
	
	public InvalidToken predFindToKenCurrentByUser(Long nguoiDungId) {
		BooleanExpression predAll = base;
		predAll = predAll.and(QInvalidToken.invalidToken.nguoiDung.id.eq(nguoiDungId));
		if (repo.exists(predAll)) {
			return repo.findOne(predAll);
		}
		return null;
	}
	
	public InvalidToken predFindToKenCurrentByToken(String token) {
		BooleanExpression predAll = base;
		predAll = predAll.and(QInvalidToken.invalidToken.active.isTrue())
				.and(QInvalidToken.invalidToken.token.eq(token));
		if (repo.exists(predAll)) {
			return repo.findOne(predAll);
		}
		return null;
	}
	
	public InvalidToken save(InvalidToken obj, Long congChucId) {
		return Utils.save(repo, obj, congChucId);
	}

	public ResponseEntity<Object> doSave(InvalidToken obj, Long congChucId, PersistentEntityResourceAssembler eass,
			HttpStatus status) {
		return Utils.doSave(repo, obj, congChucId, eass, status);
	}
}
