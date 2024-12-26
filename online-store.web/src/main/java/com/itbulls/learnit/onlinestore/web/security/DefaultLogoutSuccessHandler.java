package com.itbulls.learnit.onlinestore.web.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

public class DefaultLogoutSuccessHandler implements LogoutSuccessHandler 
{
	@Override
	public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException
	{
		System.out.println("=== Message from Logout handler ===");
		response.sendRedirect(request.getServletContext().getContextPath() + "/signin");
	}
	
}
