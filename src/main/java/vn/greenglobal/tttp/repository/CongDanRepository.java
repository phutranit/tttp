package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.CongDan;

@RestResource(path = "congDans")
public interface CongDanRepository extends MutableRepo<CongDan> {

}
