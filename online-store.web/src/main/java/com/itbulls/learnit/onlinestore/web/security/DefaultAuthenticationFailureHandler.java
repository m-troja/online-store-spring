package com.itbulls.learnit.onlinestore.web.security;

import java.io.IOException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

public class DefaultAuthenticationFailureHandler implements AuthenticationFailureHandler 
{
	public static final String UNSUCCESFUL_LOGIN_COUNT_ATTR_KEY = "UNSUCCESSFUL_LOGIN_COUNT";
	private static final int LOGIN_FAILURE_ATTEMPTS_LIMIT = 3;
	
	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException 
	{
		
		
		response.setStatus(HttpStatus.UNAUTHORIZED.value());
		
		StringBuilder sb = new StringBuilder();
		sb.append("timestamp: ").append(Calendar.getInstance().getTime())
			.append(System.lineSeparator())
			.append("exception: ").append(exception.getMessage());
		
		System.out.println("=== In the authentication failure handler ===");
		response.setContentType("text/html; charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().println(sb.toString());
	}

}
