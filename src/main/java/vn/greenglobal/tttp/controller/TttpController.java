package vn.greenglobal.tttp.controller;

import java.util.List;

import javax.persistence.Transient;

import org.springframework.beans.factory.annotation.Autowired;

import vn.greenglobal.core.model.common.BaseController;
import vn.greenglobal.core.model.common.BaseRepository;
import vn.greenglobal.tttp.model.PropertyChangeObject;
import vn.greenglobal.tttp.repository.CongChucRepository;
import vn.greenglobal.tttp.util.MessageByLocaleService;
import vn.greenglobal.tttp.util.ProfileUtils;

public class TttpController<T> extends BaseController<T> {

	@Autowired
	ProfileUtils profileUtil;

	@Autowired
	MessageByLocaleService message;

	@Autowired
	CongChucRepository repoCC;

	public TttpController(BaseRepository<T, ?> repo) {
		super(repo);
	}

	public ProfileUtils getProfileUtil() {
		return profileUtil;
	}

	public MessageByLocaleService getMessage() {
		return message;
	}

	@Transient
	public String getChiTietThayDoi(final List<PropertyChangeObject> list) {
		String out = "";
		if (list.size() > 0) {
			out += "[";
			for (PropertyChangeObject obj : list) {
				out += "{'propertyName': '" + obj.getPropertyName() + "'," + "'oldValue': '" + obj.getOldValue()
						+ "','newValue': '" + obj.getNewValue() + "'},";
			}
			out += "]";
			out = out.replaceAll("},]", "}]");
		}
		return out;
	}
	
	@Transient
	public String getChiTietNoiDung(final List<PropertyChangeObject> list) {
		String out = "";
		if (list.size() > 0) {
			out += "[";
			for (PropertyChangeObject obj : list) {
				out += "{'label': '" + obj.getPropertyName() + "'," + "'name': '" + obj.getOldValue()
						+ "','value': '" + obj.getNewValue() + "'},";
			}
			out += "]";
			out = out.replaceAll("},]", "}]");
		}
		return out;
	}
}
