package vn.greenglobal.core.model.common;

import org.hibernate.search.annotations.Indexed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositorySearchesResource;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

public class BaseController<T> implements ResourceProcessor<RepositorySearchesResource> {
	private BaseRepository<T, ?> repository;
	
	public BaseRepository<T, ?> getRepository() {
		return repository;
	}

	public void setRepository(BaseRepository<T, ?> repository) {
		this.repository = repository;
	}
 
	public BaseController() {
		// TODO Auto-generated constructor stub
	}
	
	public BaseController(BaseRepository<T, ?> repo) {
		repository = repo;
	}

	@Autowired
	protected PagedResourcesAssembler<T> assembler;

	public @ResponseBody PagedResources<?> findByRsql(@RequestParam(value = "rsql") String rsql,
			PersistentEntityResourceAssembler eass) {
		return assembler.toResource(repository.findByRsql(rsql), (ResourceAssembler<T, ?>) eass);
	}

	public @ResponseBody ResponseEntity<Resource<Long>> countRsql(@RequestParam(value = "rsql") String rsql) {
		Resource<Long> resources = new Resource<>(repository.countRsql(rsql));
		resources.add(
				ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(getClass()).countRsql(rsql)).withSelfRel());
		return ResponseEntity.ok(resources);
	}

	public @ResponseBody ResponseEntity<Resource<Boolean>> existRsql(@RequestParam(value = "rsql") String rsql) {
		Resource<Boolean> resources = new Resource<>(repository.existRsql(rsql));
		resources.add(
				ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(getClass()).existRsql(rsql)).withSelfRel());
		return ResponseEntity.ok(resources);
	}

	public @ResponseBody PagedResources<?> pageRsql(@RequestParam(value = "rsql") String rsql, Pageable pageable,
			PersistentEntityResourceAssembler eass) {
		return assembler.toResource(repository.pageRsql(rsql, pageable), (ResourceAssembler<T, ?>) eass);
	}

	public @ResponseBody PagedResources<?> findByElastic(@RequestParam(value = "rsql") String rsql,
			PersistentEntityResourceAssembler eass) {
		return assembler.toResource(repository.findByElastic(rsql), (ResourceAssembler<T, ?>) eass);
	}

	public @ResponseBody ResponseEntity<Resource<Long>> countElastic(@RequestParam(value = "rsql") String rsql) {
		Resource<Long> resources = new Resource<>(repository.countElastic(rsql));
		resources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(getClass()).countElastic(rsql))
				.withSelfRel());
		return ResponseEntity.ok(resources);
	}

	public @ResponseBody ResponseEntity<Resource<Boolean>> existElastic(@RequestParam(value = "rsql") String rsql) {
		Resource<Boolean> resources = new Resource<>(repository.existElastic(rsql));
		resources.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(getClass()).existElastic(rsql))
				.withSelfRel());
		return ResponseEntity.ok(resources);
	}

	public @ResponseBody PagedResources<?> pageElastic(@RequestParam(value = "rsql") String rsql, Pageable pageable,
			PersistentEntityResourceAssembler eass) {
		return assembler.toResource(repository.pageElastic(rsql, pageable), (ResourceAssembler<T, ?>) eass);
	}

	public RepositorySearchesResource process(RepositorySearchesResource resource) {
		if (repository.getDomainClass().equals(resource.getDomainType())) {
			resource.add(ControllerLinkBuilder
					.linkTo(ControllerLinkBuilder.methodOn(getClass()).pageRsql("", null, null)).withRel("pageRsql"));
			resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(getClass()).findByRsql("", null))
					.withRel("findByRsql"));
			resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(getClass()).countRsql(""))
					.withRel("countRsql"));
			resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(getClass()).existRsql(""))
					.withRel("existRsql"));
			if (repository.getDomainClass().isAnnotationPresent(Indexed.class)) {
				resource.add(ControllerLinkBuilder
						.linkTo(ControllerLinkBuilder.methodOn(getClass()).pageElastic("", null, null))
						.withRel("pageElastic"));
				resource.add(
						ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(getClass()).findByElastic("", null))
								.withRel("findByElastic"));
				resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(getClass()).countElastic(""))
						.withRel("countElastic"));
				resource.add(ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(getClass()).existElastic(""))
						.withRel("existElastic"));
			}
		}
		return resource;
	}
}
