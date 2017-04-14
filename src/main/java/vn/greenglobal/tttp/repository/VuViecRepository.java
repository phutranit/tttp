package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.VuViec;

@RestResource(path = "vuViecs")
public interface VuViecRepository extends MutableRepo<VuViec> {
	
}
