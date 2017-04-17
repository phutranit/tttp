package vn.greenglobal.core.model.common;

import java.util.List;

import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;

@NoRepositoryBean
public interface CommonRepo<T> extends BaseRepository<T,Long> {
	List<T> findById(@Param("id") Long id);
}
