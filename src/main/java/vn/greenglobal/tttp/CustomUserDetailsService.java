package vn.greenglobal.tttp;

import java.util.HashSet;
import java.util.Set;

import org.springframework.security.cas.authentication.CasAssertionAuthenticationToken;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class CustomUserDetailsService implements AuthenticationUserDetailsService<CasAssertionAuthenticationToken>{

	@Override 
	  public UserDetails loadUserDetails(CasAssertionAuthenticationToken token) throws UsernameNotFoundException { 
	    System.out.println(" The current user name is ："+token.getName()); 
	    /* I'm here for convenience ， Directly returns a user information ， In practice, this is modified to query the database or invoke services to obtain user information */ 
	    User user = new User(token.getName(), "", true, true, true, true, AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
	    return user; 
	  } 
	
}
