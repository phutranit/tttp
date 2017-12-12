package vn.greenglobal.tttp.model;

import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import io.katharsis.resource.annotations.JsonApiId;
import io.swagger.annotations.ApiModel;

@SuppressWarnings("rawtypes")
@MappedSuperclass
@ApiModel
public class ModelThongKe<T extends ModelThongKe<T>>  {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonApiId
	private Long id;

	public boolean equals(Object o) {
		return this == o || o != null && getClass().isAssignableFrom(o.getClass()) && getId() != null
				&& Objects.equals(getId(), ((ModelThongKe) o).getId());
	}

	public int hashCode() {
		return 31;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
