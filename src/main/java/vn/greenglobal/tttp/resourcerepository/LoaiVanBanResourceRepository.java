package vn.greenglobal.tttp.resourcerepository;

import io.katharsis.repository.annotations.JsonApiResourceRepository;
import vn.greenglobal.core.model.common.BaseResourceRepository;
import vn.greenglobal.tttp.model.LoaiVanBan;
import vn.greenglobal.tttp.repository.LoaiVanBanRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@JsonApiResourceRepository(LoaiVanBan.class)
public class LoaiVanBanResourceRepository extends BaseResourceRepository<LoaiVanBan, Long> {

	@Autowired
	public LoaiVanBanResourceRepository(LoaiVanBanRepository repo) {
		super(repo);
	}

}
