package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.DoiTuongViPham;

@RestResource(path = "doiTuongViPhams")
public interface DoiTuongViPhamRepository extends MutableRepo<DoiTuongViPham> {

}
