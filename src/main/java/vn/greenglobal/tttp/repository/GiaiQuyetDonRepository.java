package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.GiaiQuyetDon;
import vn.greenglobal.tttp.model.XuLyDon;

@RestResource(path = "giaiQuyetDons")
public interface GiaiQuyetDonRepository extends MutableRepo<GiaiQuyetDon> {

}
