package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.ThongTinEmail;

@RestResource(path = "thongTinEmails")
public interface ThongTinEmailRepository extends MutableRepo<ThongTinEmail> {

}
