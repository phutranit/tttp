package vn.greenglobal.core.model.common;

import io.katharsis.queryParams.QueryParams;
import io.katharsis.queryspec.QuerySpec;
import io.katharsis.repository.ResourceRepository;
import io.katharsis.repository.ResourceRepositoryV2;
import io.katharsis.repository.annotations.JsonApiFindAll;
import io.katharsis.repository.annotations.JsonApiFindAllWithIds;
import io.katharsis.repository.annotations.JsonApiFindOne;
import io.katharsis.resource.list.ResourceList;
import io.katharsis.resource.list.ResourceListBase;
import io.katharsis.response.paging.PagedLinksInformation;
import io.katharsis.response.paging.PagedMetaInformation;

import java.io.Serializable;

public class BaseResourceRepository<T, ID extends Serializable> implements ResourceRepository<T, ID>, ResourceRepositoryV2<T, ID> {
	public class ProjectListMeta implements PagedMetaInformation {

		private Long totalResourceCount;

		@Override
		public Long getTotalResourceCount() {
			return totalResourceCount;
		}

		@Override
		public void setTotalResourceCount(Long totalResourceCount) {
			this.totalResourceCount = totalResourceCount;
		}

	}

	public class ProjectListLinks implements PagedLinksInformation {

		private String first;

		private String last;

		private String next;

		private String prev;

		@Override
		public String getFirst() {
			return first;
		}

		@Override
		public void setFirst(String first) {
			this.first = first;
		}

		@Override
		public String getLast() {
			return last;
		}

		@Override
		public void setLast(String last) {
			this.last = last;
		}

		@Override
		public String getNext() {
			return next;
		}

		@Override
		public void setNext(String next) {
			this.next = next;
		}

		@Override
		public String getPrev() {
			return prev;
		}

		@Override
		public void setPrev(String prev) {
			this.prev = prev;
		}

	}
	
	public class ProjectList extends ResourceListBase<T, ProjectListMeta, ProjectListLinks> {

	}
	
	private final BaseRepository<T, ID> repository;
	//private ResourceRegistry resourceRegistry;

	public BaseRepository<T, ID> getRepository() {
		return repository;
	}

	public BaseResourceRepository(BaseRepository<T, ID> repo) {
		repository = repo;
		//resourceRegistry = aresourceRegistry;
	}

	@JsonApiFindOne
	@Override
	public T findOne(ID id, QueryParams params) {
		return repository.findOne(id);
	}

	@JsonApiFindAll
	@Override
	public Iterable<T> findAll(QueryParams params) {
		return repository.findKatharsis(params);
	}

	@JsonApiFindAllWithIds
	@Override
	public Iterable<T> findAll(Iterable<ID> ids, QueryParams params) {
		return repository.findAll(ids);
	}

	@Override
	public <S extends T> S save(S entity) {
		return null;
	}

	@Override
	public void delete(ID id) {
		
	}

	public Class<T> getResourceClass() {
		return repository.getDomainClass();
	}

	public <S extends T> S create(S entity) {
		return null;
	}

	public T findOne(ID id, QuerySpec querySpec) {
		return null;
	}

	public ResourceList<T> findAll(QuerySpec querySpec) {
		ProjectList list = new ProjectList();
		list.setMeta(new ProjectListMeta());
		list.setLinks(new ProjectListLinks());
		list.addAll(repository.findKatharsis3(querySpec).getContent());
		return list;
	}

	public ResourceList<T> findAll(Iterable<ID> ids, QuerySpec querySpec) {
		return null;
	}
}
