package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.CongDan;
import vn.greenglobal.tttp.model.NguoiDung;

@RestResource(path = "nguoidungs")
public interface NguoiDungRepository extends MutableRepo<NguoiDung> {

	NguoiDung findByTenDangNhap(final String username);
}
