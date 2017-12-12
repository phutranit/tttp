package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.CauHinhBaoCao;

@RestResource(path = "cauHinhBaoCaos")
public interface CauHinhBaoCaoRepository extends MutableRepo<CauHinhBaoCao> {

}
