package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;

@RestResource(path = "processes")
public interface ProcessRepository extends MutableRepo<vn.greenglobal.tttp.model.Process> {

}
