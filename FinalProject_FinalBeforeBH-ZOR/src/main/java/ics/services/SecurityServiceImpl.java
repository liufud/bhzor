package ics.services;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

public class SecurityServiceImpl implements SecurityService {
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	public String findLoggedInUsername() {
		Object userDetails = SecurityContextHolder.getContext().getAuthentication().getDetails();
		if(userDetails instanceof UserDetails) {
			return ((UserDetails) userDetails).getUsername();
		}
		return null;
	}

	public void autologin(String username, String password, HttpServletRequest request) {
		UserDetails userDetails = userDetailsService.loadUserByUsername(username);
		UsernamePasswordAuthenticationToken upat = 
				new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());
		
		request.getSession();
		upat.setDetails(new WebAuthenticationDetails(request));
		try {
			Authentication authenticatedUser = authenticationManager.authenticate(upat);
			if(authenticatedUser.isAuthenticated()) {
				System.out.println("user is authenticated..........");
				SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
				
	}

}
