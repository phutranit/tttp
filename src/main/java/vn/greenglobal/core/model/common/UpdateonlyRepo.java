package vn.greenglobal.core.model.common;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.rest.core.annotation.Description;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

@NoRepositoryBean
public interface UpdateonlyRepo<T> extends CommonRepo<T> {
	@Override
	@Transactional(readOnly=false)
	@RestResource(exported=true, description=@Description("updateonly"))
	<S extends T> S save(S entity);
}
