package vn.greenglobal.tttp.service;

import com.querydsl.core.types.Predicate;

import vn.greenglobal.tttp.model.QXuLyDon;
import vn.greenglobal.tttp.repository.XuLyDonRepository;

public class XuLyDonService {

	public boolean isExists(XuLyDonRepository repo, Long id) {
		Predicate predicate = QXuLyDon.xuLyDon.daXoa.eq(false)
					.and(QXuLyDon.xuLyDon.id.eq(id));
		return repo.exists(predicate);
	}
}
