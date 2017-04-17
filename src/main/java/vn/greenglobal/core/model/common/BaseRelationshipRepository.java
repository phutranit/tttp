package vn.greenglobal.core.model.common;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.queryParams.RestrictedPaginationKeys;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.RelationshipRepository;
import io.katharsis.repository.RelationshipRepositoryV2;
import io.katharsis.repository.annotations.JsonApiFindManyTargets;
import io.katharsis.repository.annotations.JsonApiFindOneTarget;
import io.katharsis.resource.list.ResourceList;
import io.katharsis.utils.PropertyUtils;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import com.github.vineey.rql.querydsl.QuerydslMappingResult;
import com.google.common.collect.Iterables;

public class BaseRelationshipRepository<A, AID extends Serializable, B, BID extends Serializable>
		implements RelationshipRepository<A, AID, B, BID>, RelationshipRepositoryV2<A, AID, B, BID> {
	private final BaseRepository<A, AID> aRepository;
	
	public BaseRepository<A, AID> getARepository() {
		return aRepository;
	}

	public BaseRepository<B, BID> getBRepository() {
		return bRepository;
	}

	private final BaseRepository<B, BID> bRepository;

	public BaseRelationshipRepository(BaseRepository<A, AID> arepo, BaseRepository<B, BID> brepo) {
		aRepository = arepo;
		bRepository = brepo;
	}

	protected <T> T getProperty(A instanceA, String fieldName) {
		return (T) PropertyUtils.getProperty(instanceA, fieldName);
	}

	@JsonApiFindOneTarget
	@Override
	public B findOneTarget(AID sourceId, String fieldName, QueryParams requestParams) {
		return this.<B>getProperty(aRepository.findOne(sourceId), fieldName);
	}

	@JsonApiFindManyTargets
	@Override
	public Iterable<B> findManyTargets(AID sourceId, String fieldName, QueryParams params) {
		A aInstance = aRepository.findOne(sourceId);
		try {
			Method meth = aInstance.getClass().getMethod(fieldName, QuerydslMappingResult.class);
			if (meth != null) {
				return (Iterable<B>) meth.invoke(aInstance, bRepository.katharsisToQuerydsl(params));
			}
		} catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
			e.printStackTrace();
		}
		Iterable<B> res = this.<Iterable<B>>getProperty(aInstance, fieldName);
		if (params.getPagination().containsKey(RestrictedPaginationKeys.offset)) {
			res = Iterables.skip(res, params.getPagination().getOrDefault(RestrictedPaginationKeys.offset, 0));
		}
		if (params.getPagination().containsKey(RestrictedPaginationKeys.limit)) {
			res = Iterables.limit(res, params.getPagination().getOrDefault(RestrictedPaginationKeys.limit, 1000));
		}
		return res;
	}

	@Override
	public void setRelation(A source, BID targetId, String fieldName) {
	}

	@Override
	public void setRelations(A source, Iterable<BID> targetIds, String fieldName) {
	}

	@Override
	public void addRelations(A source, Iterable<BID> targetIds, String fieldName) {
	}

	@Override
	public void removeRelations(A source, Iterable<BID> targetIds, String fieldName) {
	}

	public Class<A> getSourceResourceClass() {
		return aRepository.getDomainClass();
	}

	public Class<B> getTargetResourceClass() {
		return bRepository.getDomainClass();
	}

	public B findOneTarget(AID sourceId, String fieldName, QuerySpec querySpec) {
		return null;
	}

	public ResourceList<B> findManyTargets(AID sourceId, String fieldName, QuerySpec querySpec) {
		return null;
	}
}
