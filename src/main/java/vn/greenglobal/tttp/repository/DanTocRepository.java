package vn.greenglobal.tttp.repository;

import java.util.List;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.DanToc;

@RestResource(path = "danTocs")
public interface DanTocRepository extends MutableRepo<DanToc> {
	
	@Override
	List<DanToc> findAll();
}
