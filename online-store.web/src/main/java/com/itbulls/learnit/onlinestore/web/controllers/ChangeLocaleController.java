package com.itbulls.learnit.onlinestore.web.controllers;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.Locale;

import com.itbulls.learnit.onlinestore.web.Configurations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/change-locale")
public class ChangeLocaleController extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String language = request.getParameter("locale");
		if (language == null) {
			Locale.setDefault(new Locale(Configurations.DEFAULT_ENGLISH_LANGUAGE));
			request.getSession().setAttribute("locale", Configurations.DEFAULT_ENGLISH_LANGUAGE);
		} else if (language.equals(Configurations.FRENCH_LANGUAGE)) {
			Locale.setDefault(new Locale(Configurations.FRENCH_LANGUAGE));
			request.getSession().setAttribute("locale", Configurations.FRENCH_LANGUAGE);
		}
		
		String baseUrl = request.getScheme()
			      + "://"
			      + request.getServerName()
			      + ":"
			      + request.getServerPort()
			      + request.getServletContext().getContextPath();
		
		response.sendRedirect(baseUrl);
	}

}
