package vn.greenglobal.tttp.model;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.katharsis.resource.annotations.JsonApiResource;

@Entity
@Table(name = "don")
@JsonApiResource(type = "dons")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Don extends Model<Don> {

	
}