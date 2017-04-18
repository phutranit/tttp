package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.Don_CongDan;

@RestResource(path = "donCongDans")
public interface DonCongDanRepository extends MutableRepo<Don_CongDan> {
	
}
