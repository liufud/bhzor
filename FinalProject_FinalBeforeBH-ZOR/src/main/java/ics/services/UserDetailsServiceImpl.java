package ics.services;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ics.dao.UserDAO;
import ics.model.Role;

//@Service("userDetailsService")
//public class UserDetailsServiceImpl implements UserDetailsService {
//	
//	@Autowired
//	private UserDAO userDAO;
//	
//	@Transactional(readOnly=true)
//	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//		User user = userDAO.findUserByName(username);
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDAO userDAO;
	
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		System.out.println("loadUserByUsername is called.......");
		
		ics.model.User user = userDAO.findUserByName(username); 
		System.out.println(user);
		if(user != null) {
			String password = user.getPassword();
			
			//additional information on security object
//			boolean enabled = user.getStatus().equals(UserStatus.ACTIVE);
//			boolean accountNonExpired = user.getStatus().equals(UserStatus.ACTIVE);
//			boolean credentialIsNonExpired = user.getStatus().equals(UserStatus.ACTIVE);
//			boolean accountNonLocked = user.getStatus().equals(UserStatus.ACTIVE);
			//populate user roles
			Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
			for(Role role : user.getRoles()) {
				authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
			}
			
			//create Spring Security User object
			
//			org.springframework.security.core.userdetails.User securityUser = new
//					org.springframework.security.core.userdetails.User(username, password, enabled, accountNonExpired, credentialIsNonExpired, accountNonLocked, authorities);
//			return securityUser;
			System.out.println("user loaded...");
			return new User(username, password, authorities);
		}else {
			System.out.println("failed to verify");
			throw new UsernameNotFoundException("User Not Found!");
		}
	}
}
