package vn.greenglobal.core.model.common;

import java.io.Serializable;

import io.katharsis.repository.annotations.JsonApiDelete;
import io.katharsis.repository.annotations.JsonApiSave;

public class MutableResourceRepo<T, ID extends Serializable> extends BaseResourceRepository<T, ID> {
	public MutableResourceRepo(BaseRepository<T, ID> repo) {
		super(repo);
	}
	
	@JsonApiSave
	@Override
	public <S extends T> S save(S entity) {
		return getRepository().save(entity);
	}

	@JsonApiDelete
	@Override
	public void delete(ID id) {
		getRepository().delete(id);
	}
}
