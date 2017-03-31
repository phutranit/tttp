package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

//import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.base.Strings;

@MappedSuperclass
public class Model<T extends Model<T>> {

	private String trangThai;
	private LocalDateTime ngayTao;
	private LocalDateTime ngaySua;
	private boolean daXoa;
	
	@Column
	public String getTrangThai() {
		return trangThai;
	}

	public void setTrangThai(final String _trangThai) {
		trangThai = Strings.nullToEmpty(_trangThai);
	}
	
	@Column
	//@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+0000")
	public LocalDateTime getNgaySua() {
		return this.ngaySua;
	}

	public void setNgaySua(LocalDateTime ngaySua1) {
		this.ngaySua = ngaySua1;
	}
	
	@Column
	//@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS+0000")
	public LocalDateTime getNgayTao() {
		return this.ngayTao;
	}


	public void setNgayTao(LocalDateTime ngayTao1) {
		this.ngayTao = ngayTao1;
	}

	@Column
	public boolean isDaXoa() {
		return daXoa;
	}

	public void setDaXoa(boolean daXoa) {
		this.daXoa = daXoa;
	}

}
