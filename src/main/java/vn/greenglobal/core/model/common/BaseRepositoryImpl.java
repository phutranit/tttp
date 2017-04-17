package vn.greenglobal.core.model.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.QueryDslJpaRepository;
import org.springframework.data.repository.support.Repositories;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.github.vineey.rql.RqlInput;
import com.github.vineey.rql.querydsl.DefaultQuerydslRqlParser;
import com.github.vineey.rql.querydsl.QuerydslMappingParam;
import com.github.vineey.rql.querydsl.QuerydslMappingResult;
import com.github.vineey.rql.querydsl.spring.SpringUtil;
import com.google.common.base.Strings;
import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.CollectionPathBase;
import com.querydsl.hibernate.search.SearchQuery5;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.queryParams.RestrictedPaginationKeys;
import io.katharsis.queryParams.RestrictedSortingValues;
import io.katharsis.queryParams.params.FilterParams;
import io.katharsis.queryParams.params.SortingParams;
import io.katharsis.queryspec.QuerySpec;

public class BaseRepositoryImpl<T, ID extends Serializable> extends QueryDslJpaRepository<T, ID>
		implements BaseRepository<T, ID> {
	private EntityManager em;

	@Override
	public Class<T> getDomainClass() {
		return super.getDomainClass();
	}
	
	public BaseRepositoryImpl(JpaEntityInformation<T, ID> entityInformation, EntityManager entityManager) {
		super(entityInformation, entityManager);
		em = entityManager;
	}

	public Page<T> findByRsql(String query) {
		return pageRsql(query, null);
	}

	private void prepareFieldMap(Path<?> path, Map<String, Path> pathMapping, String prefix, int additionalLevels) {
		for (Field fld : path.getClass().getFields()) {
			if (Modifier.isPublic(fld.getModifiers()) && !Modifier.isStatic(fld.getModifiers())
					&& Path.class.isAssignableFrom(fld.getType())) {
				try {
					pathMapping.put(prefix + fld.getName(), (Path<?>) fld.get(path));
					if (CollectionPathBase.class.isAssignableFrom(fld.getType())) {
						Path<?> any = (Path<?>) ((CollectionPathBase<?, ?, ?>) fld.get(path)).any();
						pathMapping.put(prefix + fld.getName() + ".any", any);
						if (additionalLevels >= 0) {
							prepareFieldMap(any, pathMapping, prefix + fld.getName() + ".any" + ".",
									additionalLevels - 1);
						}
					} else if (additionalLevels > 0 && fld.getType().getSimpleName().startsWith("Q")) {
						prepareFieldMap((Path<?>) fld.get(path), pathMapping, prefix + fld.getName() + ".",
								additionalLevels - 1);
					}
				} catch (IllegalArgumentException | IllegalAccessException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private QuerydslMappingResult mapRsql(String rsql) {
		System.out.println(rsql);
		String partSep = ";";
		String limit = "limit(0, 1000)";
		if (rsql.contains(partSep + "limit(")) {
			int index = rsql.indexOf(partSep + "limit(") + 1;
			int indexTo = rsql.indexOf(partSep, index);
			limit = rsql.substring(index, indexTo == -1 ? rsql.length() : indexTo);
			rsql = rsql.replace(partSep + limit, "");
		}
		RqlInput rqlInput = new RqlInput().setLimit(limit);
		if (rsql.contains(partSep + "sort(")) {
			int index = rsql.indexOf(partSep + "sort(") + 1;
			int indexTo = rsql.indexOf(partSep, index);
			rqlInput.setSort(rsql.substring(index, indexTo == -1 ? rsql.length() : indexTo));
			rsql = rsql.replace(partSep + rqlInput.getSort(), "");
		}
		if (rsql.contains(partSep + "select(")) {
			int index = rsql.indexOf(partSep + "select(") + 1;
			int indexTo = rsql.indexOf(partSep, index);
			rqlInput.setSelect(rsql.substring(index, indexTo == -1 ? rsql.length() : indexTo));
			rsql = rsql.replace(partSep + rqlInput.getSelect(), "");
		} else {
			rqlInput.setSelect("select(id)");
		}
		System.out.println(rsql);
		rqlInput.setFilter(rsql);
		Map<String, Path> pathMapping = new HashMap<>();
		prepareFieldMap(getEntityPath(), pathMapping, "", 2);
		System.out.println(pathMapping);
		QuerydslMappingResult res = new DefaultQuerydslRqlParser().parse(rqlInput,
				new QuerydslMappingParam().setRootPath(getEntityPath()).setPathMapping(pathMapping));
		if (res.getOrderSpecifiers() == null) {
			res.setOrderSpecifiers(Collections.emptyList());
		}
		return res;
	}

	@Override
	public Page<T> pageRsql(String rsql, Pageable apageable) {
		QuerydslMappingResult mapResult = mapRsql(rsql);
		return findAll(mapResult.getPredicate(),
				apageable == null
						? mapResult.getOrderSpecifiers() == null || mapResult.getOrderSpecifiers().isEmpty()
								? new PageRequest(mapResult.getPage().getOffsetAsInteger(),
										mapResult.getPage().getLimitAsInteger())
								: SpringUtil.toPageable(mapResult.getOrderSpecifiers(), mapResult.getPage())
						: apageable);
	}

	@Override
	public long countRsql(String rsql) {
		return count(mapRsql(rsql).getPredicate());
	}

	@Override
	public boolean existRsql(String rsql) {
		return exists(mapRsql(rsql).getPredicate());
	}

	public QuerydslMappingResult katharsisToQuerydsl(QueryParams params) {
		return mapRsql(katharsisToRsql(params));
	}

	public String katharsisToRsql(QueryParams params) {
		String rsql = "", sort = "";
		for (Entry<String, FilterParams> entry : params.getFilters().getParams().entrySet()) {
			for (Entry<String, Set<String>> secondLevelEntry : entry.getValue().getParams().entrySet()) {
				for (String v : secondLevelEntry.getValue()) {
					rsql += (rsql.isEmpty() ? "" : " and ") + secondLevelEntry.getKey();
					char c = Strings.nullToEmpty(v).trim().concat(" ").charAt(0);
					if (c != '!' && c != '=' && c != '<' && c != '>'
							&& (c == '\'' || !Strings.nullToEmpty(v).trim().endsWith("'"))) {
						rsql += "==";
					}
					rsql += v;
				}
			}
		}
		if (!params.getSorting().getParams().isEmpty()) {
			for (Entry<String, SortingParams> entry : params.getSorting().getParams().entrySet()) {
				for (Entry<String, RestrictedSortingValues> secondLevelEntry : entry.getValue().getParams()
						.entrySet()) {
					sort += (sort.isEmpty() ? "" : ",")
							+ (secondLevelEntry.getValue() == RestrictedSortingValues.desc ? "-" : "+")
							+ secondLevelEntry.getKey();
				}
			}
			rsql += ";sort(" + sort + ")";
		}
		if (!params.getPagination().isEmpty()) {
			rsql += ";limit(" + params.getPagination().getOrDefault(RestrictedPaginationKeys.offset, 0) + ","
					+ params.getPagination().getOrDefault(RestrictedPaginationKeys.limit, 1000) + ")";
		}
		return rsql;
	}

	@Override
	public Page<T> findKatharsis(QueryParams params) {
		return findByRsql(katharsisToRsql(params));
	}

	private EntityPath<T> getEntityPath() {
		try {
			final Class<?> qclass = Class
					.forName(getDomainClass().getPackage().getName() + ".Q" + getDomainClass().getSimpleName());
			String path = Character.toLowerCase(getDomainClass().getSimpleName().charAt(0))
					+ getDomainClass().getSimpleName().substring(1);
			Field[] fields = qclass.getDeclaredFields();
			Field field = null;
			for (Field f : fields) {
				if (f.getName().equals(path + "1")) {
					field = f;
					break;
				} else if (f.getName().equals(path))
					field = f;
			}
			return field == null ? null : (EntityPath<T>) field.get(null);
		} catch (ClassNotFoundException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	private SearchQuery5<T> getSearchQuery5(String query) {
		return new SearchQuery5<>((Session) em.getDelegate(), getEntityPath()).where(mapRsql(query).getPredicate());
	}

	@Override
	public Page<T> findByElastic(String query) {
		return pageElastic(query, null);
	}

	@Override
	public long countElastic(String query) {
		return getSearchQuery5(query).fetchCount();
	}

	@Override
	public boolean existElastic(String query) {
		return getSearchQuery5(query).limit(1).fetchCount() > 0;
	}

	@Override
	public Page<T> pageElastic(String query, Pageable page) {
		QuerydslMappingResult mapResult = mapRsql(query);
		if (page == null)
			page = mapResult.getOrderSpecifiers() == null || mapResult.getOrderSpecifiers().isEmpty()
					? new PageRequest(mapResult.getPage().getOffsetAsInteger(), mapResult.getPage().getLimitAsInteger())
					: SpringUtil.toPageable(mapResult.getOrderSpecifiers(), mapResult.getPage());
		System.out.println(page.getOffset() + " " + page.getPageSize());
		return new PageImpl<>(getSearchQuery5(query).offset(page.getOffset()).limit(page.getPageSize()).fetch(), page,
				countElastic(query));
	}

	public <S extends T> S save(S entity) {
		ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		if (attr != null) {
			ApplicationContext ctx = WebApplicationContextUtils
					.getWebApplicationContext(attr.getRequest().getServletContext());
			for (Class<?> c : new Repositories(ctx).getRepositoryFor(getDomainClass()).getClass().getInterfaces()) {
				for (Method m : c.getMethods()) {
					if (m.getName().equals("save")) {
						RestResource ann = m.getDeclaredAnnotation(RestResource.class);
						if (ann != null) {
							if (em.getEntityManagerFactory().getPersistenceUnitUtil().getIdentifier(entity) != null) {
								if (ann.description().value().contains("insertonly")) {
									return entity;
								}
							} else if (ann.description().value().contains("updateonly")) {
								return entity;
							}
						}
					}
				}
			}
		}
		return super.save(entity);
	}

	@Override
	public QuerydslMappingResult katharsisToQuerydsl(QuerySpec params) {
		return null;
	}

	@Override
	public Page<T> findKatharsis3(QuerySpec query) {
		return null;
	}
}
