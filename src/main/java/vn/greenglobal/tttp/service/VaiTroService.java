package vn.greenglobal.tttp.service;

import org.springframework.stereotype.Component;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;

import vn.greenglobal.tttp.model.QVaiTro;
import vn.greenglobal.tttp.model.VaiTro;
import vn.greenglobal.tttp.repository.VaiTroRepository;

@Component
public class VaiTroService {

	BooleanExpression base = QVaiTro.vaiTro.daXoa.eq(false);
	
	public Predicate predicateFindOne(Long id) {
		return base.and(QVaiTro.vaiTro.id.eq(id));
	}

	public boolean isExists(VaiTroRepository repo, VaiTro vaiTro) {
		
		/*if (id != null && id > 0) {
			return repo.exists(base.and(QVaiTro.vaiTro.id.eq(id)));
		}*/
		return false;
	}

	public VaiTro deleteVaiTro(VaiTroRepository repo, Long id) {
		VaiTro vaiTro = repo.findOne(predicateFindOne(id));
		if(vaiTro!=null){
			vaiTro.setDaXoa(true);
		}
		/*if (isExists(repo, id)) {
			vaiTro = new VaiTro();
			vaiTro.setId(id);
			vaiTro.setDaXoa(true);
		}*/
		return vaiTro;
	}

}
