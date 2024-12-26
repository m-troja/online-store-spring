package com.itbulls.learnit.onlinestore.web.controllers;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.Locale;

import com.itbulls.learnit.onlinestore.web.Configurations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChangeLocaleController extends HttpServlet 
{
	@GetMapping("/change-locale")
	public String doGet(HttpSession session, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		String language = request.getParameter("locale");
		if (language == null) {
			Locale.setDefault(new Locale(Configurations.DEFAULT_ENGLISH_LANGUAGE));
			session.setAttribute("locale", Configurations.DEFAULT_ENGLISH_LANGUAGE);
		} else if (language.equals(Configurations.POLISH_LANGUAGE)) {
			Locale.setDefault(new Locale(Configurations.POLISH_LANGUAGE));
			session.setAttribute("locale", Configurations.POLISH_LANGUAGE);
		}
		
		 String baseUrl = request.getScheme()
			      + "://"
			      + request.getServerName()
			      + ":"
			      + request.getServerPort()
			      + request.getServletContext().getContextPath();
		
		return "redirect:/homepage";
	}

}
