package vn.greenglobal.tttp.service;

//@Service("userDetailsService")
public class TttpUserDetailsService{

	/*@Autowired
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
	}*/
}
