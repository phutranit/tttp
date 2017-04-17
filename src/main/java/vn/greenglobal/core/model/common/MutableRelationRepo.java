package vn.greenglobal.core.model.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import io.katharsis.repository.annotations.JsonApiAddRelations;
import io.katharsis.repository.annotations.JsonApiRemoveRelations;
import io.katharsis.repository.annotations.JsonApiSetRelation;
import io.katharsis.repository.annotations.JsonApiSetRelations;
import io.katharsis.utils.PropertyUtils;

public class MutableRelationRepo<A, AID extends Serializable, B, BID extends Serializable> extends BaseRelationshipRepository<A, AID, B, BID> {

	public MutableRelationRepo(BaseRepository<A, AID> arepo, BaseRepository<B, BID> brepo) {
		super(arepo, brepo);
	}
	
	protected void setProperty(A instanceA, Object bs, String fieldName) {
		PropertyUtils.setProperty(instanceA, fieldName, bs);
	}
	
	@JsonApiSetRelation
	@Override
	public void setRelation(A instanceA, BID bId, String fieldName) {
		setProperty(instanceA, getBRepository().findOne(bId), fieldName);
		getARepository().save(instanceA);
	}

	@JsonApiSetRelations
	@Override
	public void setRelations(A instanceA, Iterable<BID> bIds, String fieldName) {
		setProperty(instanceA, new ArrayList<>(getBRepository().findAll(bIds)), fieldName);
		getARepository().save(instanceA);
	}

	@JsonApiAddRelations
	@Override
	public void addRelations(A instanceA, Iterable<BID> bIds, String fieldName) {
		final Collection<B> bs = this.<Collection<B>>getProperty(instanceA, fieldName);
		bs.addAll(getBRepository().findAll(bIds));
		setProperty(instanceA, bs, fieldName);
		getARepository().save(instanceA);
	}

	@JsonApiRemoveRelations
	@Override
	public void removeRelations(A instanceA, Iterable<BID> bIds, String fieldName) {
		final Collection<B> bs = this.<Collection<B>>getProperty(instanceA, fieldName);
		bs.removeAll(getBRepository().findAll(bIds));
		setProperty(instanceA, bs, fieldName);
		getARepository().save(instanceA);
	}	
}
