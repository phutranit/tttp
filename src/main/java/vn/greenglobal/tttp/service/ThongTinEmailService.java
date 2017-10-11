package vn.greenglobal.tttp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import vn.greenglobal.tttp.model.ThongTinEmail;
import vn.greenglobal.tttp.repository.ThongTinEmailRepository;
import vn.greenglobal.tttp.util.Utils;

@Component
public class ThongTinEmailService {
	
	@Autowired
	private ThongTinEmailRepository thongTinEmailRepository;
	
	
	public ThongTinEmail save(ThongTinEmail obj, Long congChucId) {
		return Utils.save(thongTinEmailRepository, obj, congChucId);
	}
	
	public ResponseEntity<Object> doSave(ThongTinEmail obj, Long congChucId, PersistentEntityResourceAssembler eass, HttpStatus status) {
		return Utils.doSave(thongTinEmailRepository, obj, congChucId, eass, status);		
	}

}
