package com.itbulls.learnit.onlinestore.web.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itbulls.learnit.onlinestore.persistence.entities.User;
import com.itbulls.learnit.onlinestore.web.controllers.SignInController;

import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;

@WebFilter(urlPatterns = {"/management-fulfilment", "/management-orders"})
public class AdminFilter implements javax.servlet.Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException 
	{
		User user = (User)((HttpServletRequest)request).getSession().getAttribute(SignInController.LOGGED_IN_USER_ATTR);		
		if (user != null)
		{
			if (user.getRoleName().equals("ROLE_ADMIN"))
			{
				chain.doFilter(request, response);
			}
			else {
				((HttpServletResponse)response).sendError(403);
				
			}
		}
	 else {
		((HttpServletResponse)response).sendRedirect(
				request.getScheme()
			      + "://"
			      + request.getServerName()
			      + ":"
			      + request.getServerPort()
			      + request.getServletContext().getContextPath() + "/signin");
		
	}
	}
	

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
