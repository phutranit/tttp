package vn.greenglobal.tttp.model;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.springframework.data.domain.Persistable;

import com.fasterxml.jackson.annotation.JsonIgnore;

import io.katharsis.resource.annotations.JsonApiId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@SuppressWarnings("rawtypes")
@MappedSuperclass
@ApiModel
public class Model<T extends Model<T>> implements Persistable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonApiId
	private Long id;

	private LocalDateTime ngayTao = LocalDateTime.now();
	private LocalDateTime ngaySua = LocalDateTime.now();

	private boolean daXoa;
	@ManyToOne
	private CongChuc nguoiTao;
	@ManyToOne
	private CongChuc nguoiSua;

	public boolean equals(Object o) {
		return this == o || o != null && getClass().isAssignableFrom(o.getClass()) && getId() != null
				&& Objects.equals(getId(), ((Model) o).getId());
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

	
//	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+0000")
	@ApiModelProperty(hidden = true)
	public LocalDateTime getNgaySua() {
		return this.ngaySua;
	}

	public void setNgaySua(LocalDateTime ngaySua) {
		this.ngaySua = ngaySua;
	}

//	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+0000")
	@ApiModelProperty(hidden = true)
	public LocalDateTime getNgayTao() {
		return this.ngayTao;
	}

	public void setNgayTao(LocalDateTime ngayTao) {
		this.ngayTao = ngayTao;
	}

	@ApiModelProperty(hidden = true)
	@JsonIgnore
	public boolean isDaXoa() {
		return daXoa;
	}

	public void setDaXoa(boolean daXoa) {
		this.daXoa = daXoa;
	}

	@Transient
	public boolean isNew() {
		return id == null || id == 0;
	}

	@Override
	public String toString() {
		return ReflectionToStringBuilder.toString(this);
	}

	@ApiModelProperty(hidden = true)
	public CongChuc getNguoiTao() {
		return nguoiTao;
	}

	public void setNguoiTao(CongChuc nguoiTao) {
		this.nguoiTao = nguoiTao;
	}

	@ApiModelProperty(hidden = true)
	public CongChuc getNguoiSua() {
		return nguoiSua;
	}

	public void setNguoiSua(CongChuc nguoiSua) {
		this.nguoiSua = nguoiSua;
	}

}
