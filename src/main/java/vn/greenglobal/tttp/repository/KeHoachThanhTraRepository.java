package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.KeHoachThanhTra;

@RestResource(path = "keHoachThanhTras")
public interface KeHoachThanhTraRepository extends MutableRepo<KeHoachThanhTra> {

}
