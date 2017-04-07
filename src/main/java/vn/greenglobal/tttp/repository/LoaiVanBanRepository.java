package vn.greenglobal.tttp.repository;

import org.springframework.data.rest.core.annotation.RestResource;

import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.LoaiVanBan;

@RestResource(path = "loaivanbans")
public interface LoaiVanBanRepository extends BaseRepository<LoaiVanBan, Long> {

}
