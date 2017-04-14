package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.DonViHanhChinh;

@RestResource(path = "donvihanhchinhs")
public interface DonViHanhChinhRepository extends MutableRepo<DonViHanhChinh> {
	
}
