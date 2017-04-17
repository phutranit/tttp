package vn.greenglobal.core.model.common;

import java.io.Serializable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.transaction.annotation.Transactional;

import com.github.vineey.rql.querydsl.QuerydslMappingResult;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.queryspec.QuerySpec;

@NoRepositoryBean
@Transactional(readOnly=true)
public interface BaseRepository<T, ID extends Serializable> extends JpaRepository<T, ID>, QueryDslPredicateExecutor<T> {	
	QuerydslMappingResult katharsisToQuerydsl(QueryParams params);
	QuerydslMappingResult katharsisToQuerydsl(QuerySpec params);
	Page<T> findByElastic(@Param("rsql") String query);
	Page<T> findByRsql(@Param("rsql") String query);
	Page<T> findKatharsis(QueryParams query);
	Page<T> findKatharsis3(QuerySpec query);
	long countRsql(@Param("rsql") String query);
	boolean existRsql(@Param("rsql") String query);
	Page<T> pageRsql(@Param("rsql") String query, Pageable pageable);
	long countElastic(@Param("rsql") String query);
	boolean existElastic(@Param("rsql") String query);
	Page<T> pageElastic(@Param("rsql") String query, Pageable pageable);	
	@Override
	@Transactional(readOnly=false)
	@RestResource(exported=false)
	<S extends T> S save(S entity);
	@Override
	@Transactional(readOnly=false)
	@RestResource(exported=false)
	void delete(ID id);	
	Class<T> getDomainClass();	
}