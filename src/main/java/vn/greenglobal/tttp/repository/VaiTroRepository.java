package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.VaiTro;

@RestResource(path = "api/v1/vaiTros")
public interface VaiTroRepository extends MutableRepo<VaiTro> {

	VaiTro findByTen(final String ten);
}
