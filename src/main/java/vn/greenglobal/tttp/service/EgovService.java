package vn.greenglobal.tttp.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import vn.dtt.sharedservice.WebserviceFactory;
import vn.dtt.sharedservice.cmon.consumer.citizen.CongChucSoap;
import vn.greenglobal.tttp.util.CongChucList;

public class EgovService {

	public static CongChucSoap getCongChucEgovByUserName(String username) {
		CongChucSoap congChuc = new CongChucSoap();
		return congChuc;
	}
	
	public static CongChucSoap getCongChucEgovByCongChucId(long id) {
		CongChucSoap congChuc = new CongChucSoap();
		return congChuc;
	}
	
	public static CongChucList getDsCongChucByCQQL(long cqqlId){
		CongChucSoap congChuc1 = new CongChucSoap();
		CongChucSoap congChuc2 = new CongChucSoap();
		CongChucList list = new CongChucList();
		list.add(congChuc1);
		list.add(congChuc2);
		return list;
	}
}
