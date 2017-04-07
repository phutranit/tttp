package vn.greenglobal.tttp.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import io.katharsis.resource.annotations.JsonApiIncludeByDefault;
import io.katharsis.resource.annotations.JsonApiResource;
import io.katharsis.resource.annotations.JsonApiToMany;

@Entity
@Table(name = "sotiepcongdan")
@JsonApiResource(type = "sotiepcongdans")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class SoTiepCongDan extends Model<SoTiepCongDan> {

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "sotiepcongdan_coquantochuctiepdan", joinColumns = {
			@JoinColumn(name = "soTiepCongDan_id") }, inverseJoinColumns = { @JoinColumn(name = "coQuanToChucTiepDan_id") })
	@Fetch(value = FetchMode.SUBSELECT)
	@Cache(usage = CacheConcurrencyStrategy.READ_ONLY)
	@JsonApiToMany
	@JsonApiIncludeByDefault
	private List<CoQuanToChucTiepDan> coQuanToChucTiepDans = new ArrayList<>();

	public List<CoQuanToChucTiepDan> getCoQuanToChucTiepDans() {
		return coQuanToChucTiepDans;
	}

	public void setCoQuanToChucTiepDans(List<CoQuanToChucTiepDan> coQuanToChucTiepDans) {
		this.coQuanToChucTiepDans = coQuanToChucTiepDans;
	}

}