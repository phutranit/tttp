package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.CapCoQuanQuanLy;

@RestResource(path = "capCoQuanQuanLys")
public interface CapCoQuanQuanLyRepository extends MutableRepo<CapCoQuanQuanLy> {

}
