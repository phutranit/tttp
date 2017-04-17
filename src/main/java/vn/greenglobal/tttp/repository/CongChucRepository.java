package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.CongChuc;
import vn.greenglobal.tttp.model.VuViec;

@RestResource(path = "congChucs")
public interface CongChucRepository extends MutableRepo<CongChuc> {
	
}
