package vn.greenglobal.tttp.model;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.Objects;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.domain.Persistable;

//import com.fasterxml.jackson.annotation.JsonFormat;

import io.katharsis.resource.annotations.JsonApiId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@MappedSuperclass
@ApiModel
public class Model<T extends Model<T>> implements Persistable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonApiId
	private Long id;
	
	private LocalDateTime ngayTao;
	private LocalDateTime ngaySua;
	
	private boolean daXoa;
	
	public boolean equals(Object o) {
		return this == o || o != null && getClass().isAssignableFrom(o.getClass()) && getId() != null && Objects.equals(getId(), ((Model) o).getId());
	}

	public int hashCode() {
		return 31;
	}
	
	@ApiModelProperty(hidden=true)
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

//	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+0000")
	@ApiModelProperty(hidden=true)
	public LocalDateTime getNgaySua() {
		return this.ngaySua;
	}

	public void setNgaySua(LocalDateTime ngaySua1) {
		this.ngaySua = ngaySua1;
	}

//	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+0000")
	@ApiModelProperty(hidden=true)
	public LocalDateTime getNgayTao() {
		return this.ngayTao;
	}


	public void setNgayTao(LocalDateTime ngayTao1) {
		this.ngayTao = ngayTao1;
	}

	@ApiModelProperty(hidden=true)
	public boolean isDaXoa() {
		return daXoa;
	}

	public void setDaXoa(boolean daXoa) {
		this.daXoa = daXoa;
	}

	@Transient
	public boolean isNew() {
		return id == null;
	}

}
