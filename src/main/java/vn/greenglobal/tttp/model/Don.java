package vn.greenglobal.tttp.model;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import io.katharsis.resource.annotations.JsonApiResource;
import vn.greenglobal.tttp.enums.LoaiDonEnum;
import vn.greenglobal.tttp.enums.NguonTiepNhanDonEnum;

@Entity
@Table(name = "don")
@JsonApiResource(type = "dons")
@Cache(region = "danhmuc", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Don extends Model<Don> {

	private String ma = "";
	private String noiDUng = "";
	private String yeuCauCuaCongDan = "";
	
	private int soLanKhieuNaiToCao = 0;
	private int soNguoi;
	
	private LocalDateTime ngayTiepNhan;
	
	private Don donLanTruoc;
	
	private LoaiDonEnum loaiDon;
	private NguonTiepNhanDonEnum nguonTiepNhanDon;
	
}