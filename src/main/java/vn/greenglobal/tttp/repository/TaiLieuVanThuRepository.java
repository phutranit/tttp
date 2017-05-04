package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.MutableRepo;
import vn.greenglobal.tttp.model.TaiLieuVanThu;

@RestResource(path = "taiLieuVanThus")
public interface TaiLieuVanThuRepository extends MutableRepo<TaiLieuVanThu> {

}