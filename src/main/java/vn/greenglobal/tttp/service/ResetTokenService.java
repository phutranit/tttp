package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import vn.greenglobal.tttp.model.QResetToken;
import vn.greenglobal.tttp.model.ResetToken;
import vn.greenglobal.tttp.repository.ResetTokenRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class ResetTokenService {
	BooleanExpression base = QResetToken.resetToken.daXoa.isFalse();

	@Autowired
	private ResetTokenRepository repo;

	public Predicate predicateFindOne(Long id) {
		return base.and(QResetToken.resetToken.id.eq(id));
	}
	
	public ResetToken predFindToKenCurrentByToken(String token) {
		BooleanExpression predAll = base;
		predAll = predAll.and(QResetToken.resetToken.token.eq(token));
		if (repo.exists(predAll)) {
			return repo.findOne(predAll);
		}
		return null;
	}
	
	public ResetToken save(ResetToken obj, Long congChucId) {
		return Utils.save(repo, obj, congChucId);
	}

	public ResponseEntity<Object> doSave(ResetToken obj, Long congChucId, PersistentEntityResourceAssembler eass,
			HttpStatus status) {
		return Utils.doSave(repo, obj, congChucId, eass, status);
	}
}
