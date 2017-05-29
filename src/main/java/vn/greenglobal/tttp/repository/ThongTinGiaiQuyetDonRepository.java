package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.ThongTinGiaiQuyetDon;

@RestResource(path = "giaiQuyetDons")
public interface ThongTinGiaiQuyetDonRepository extends MutableRepo<ThongTinGiaiQuyetDon> {

}
