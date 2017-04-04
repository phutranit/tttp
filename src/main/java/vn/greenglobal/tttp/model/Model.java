package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

//import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Strings;

import io.katharsis.resource.annotations.JsonApiId;

@MappedSuperclass
public class Model<T extends Model<T>> {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@JsonApiId
	private Long id;
	
	private String trangThai;
	
	private LocalDateTime ngayTao;
	private LocalDateTime ngaySua;
	
	private boolean daXoa;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(final String _trangThai) {
		trangThai = Strings.nullToEmpty(_trangThai);
	}

//	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+0000")
	public LocalDateTime getNgaySua() {
		return this.ngaySua;
	}

	public void setNgaySua(LocalDateTime ngaySua1) {
		this.ngaySua = ngaySua1;
	}

//	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+0000")
	public LocalDateTime getNgayTao() {
		return this.ngayTao;
	}


	public void setNgayTao(LocalDateTime ngayTao1) {
		this.ngayTao = ngayTao1;
	}

	public boolean isDaXoa() {
		return daXoa;
	}

	public void setDaXoa(boolean daXoa) {
		this.daXoa = daXoa;
	}

}
