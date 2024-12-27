package com.itbulls.learnit.onlinestore.web.security;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class DefaultAuthenticationProvider implements AuthenticationProvider {

	Logger LOGGER = LogManager.getLogger(DefaultAuthenticationSuccessHandler.class);
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String username = authentication.getName();
		String password = authentication.getCredentials().toString();
		UserDetails user = userDetailsService.loadUserByUsername(username);
		if (user != null) {
			if (isPasswordValid(user, password)) {
				LOGGER.info("DefaultAuthenticationProvider: Token returned");
				return new UsernamePasswordAuthenticationToken(username, password, user.getAuthorities());
			} else {
				throw new BadCredentialsException("Incorrect login/password pair");
			}
		} else {
			return null;
		}
	}

	private boolean isPasswordValid(UserDetails user, String password) {
		System.out.println("DefaultAuthenticationProvider: Password is valid");
		return passwordEncoder.matches(password, user.getPassword());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}

