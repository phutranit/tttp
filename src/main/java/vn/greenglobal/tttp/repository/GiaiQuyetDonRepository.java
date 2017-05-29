package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.GiaiQuyetDon;

@RestResource(path = "lichSuGiaiQuyetDons")
public interface GiaiQuyetDonRepository extends MutableRepo<GiaiQuyetDon> {

}
