package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.VuViec;

@RestResource(path = "nguoiDungs")
public interface NguoiDungRepository extends MutableRepo<NguoiDung> {
	
}
