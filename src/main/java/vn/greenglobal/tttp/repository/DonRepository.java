package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.Don;

@RestResource(path = "dons")
public interface DonRepository extends MutableRepo<Don> {

	
}
