package vn.greenglobal.tttp.model;

import java.util.HashMap;

import org.apache.shiro.realm.AuthorizingRealm;

public class Quyen extends HashMap<String, Boolean> {

	private static final long serialVersionUID = 1L;

	public static final char CHAR_CACH = ':';
	public static final String CACH = CHAR_CACH + "";

	private long id;
	private String resource = "";
	private NguoiDung nguoiTao;
	
	public Quyen(AuthorizingRealm realm_) {
		realm = realm_;
	}

	public Quyen(AuthorizingRealm realm_, String resource_) {
		this(realm_);
		resource = resource_;
	}

	public Quyen(AuthorizingRealm realm_, String resource_, long id_, NguoiDung nguoiTao_) {
		this(realm_, resource_);
		resource = resource_;
		id = id_;
		nguoiTao = nguoiTao_;
	}

	private transient AuthorizingRealm realm;

	public AuthorizingRealm getRealm() {
		return realm;
	}

	@Override
	public Boolean get(Object key_) {
		if (key_ == null) {
			return false;
		}
		String key = key_.toString();
		if (!key.isEmpty() && key.charAt(0) == '_') {
			key = resource + key;
		}
		if (id != 0) {
			key += CACH + id;
		}
		boolean result = realm.isPermitted(null, key.replace('_', CHAR_CACH));
		return result;
	}
}
