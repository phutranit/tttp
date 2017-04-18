package vn.greenglobal.tttp.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import vn.greenglobal.tttp.model.NguoiDung;
import vn.greenglobal.tttp.model.VaiTro;
import vn.greenglobal.tttp.repository.NguoiDungRepository;

@Service("userDetailsService")
public class TttpUserDetailsService implements UserDetailsService {

	@Autowired
	private NguoiDungRepository nguoiDungRepository;

	@Override
	public UserDetails loadUserByUsername(final String username) throws UsernameNotFoundException {
		NguoiDung user = nguoiDungRepository.findByTenDangNhap(username);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getVaiTros());

		return buildUserForAuthentication(user, authorities);
	}

	// Converts NguoiDung to org.springframework.security.core.userdetails.User
	private User buildUserForAuthentication(NguoiDung user, List<GrantedAuthority> authorities) {
		return new User(user.getTenDangNhap(), user.getMatKhau(), user.isActive(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<VaiTro> vaiTros) {
		Set<GrantedAuthority> setAuths = new HashSet<>();
		// Build user's authorities
		for (VaiTro vaiTro : vaiTros) {
			setAuths.add(new SimpleGrantedAuthority(vaiTro.getTen()));
		}
		List<GrantedAuthority> Result = new ArrayList<>(setAuths);
		return Result;
	}
}
