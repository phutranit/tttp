package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.BaoCaoDonViChiTietTam;

@RestResource(path = "baoCaoDonViChiTietTams")
public interface BaoCaoDonViChiTietTamRepository extends MutableRepo<BaoCaoDonViChiTietTam> {

}
