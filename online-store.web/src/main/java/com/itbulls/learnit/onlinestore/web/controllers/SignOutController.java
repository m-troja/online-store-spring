package com.itbulls.learnit.onlinestore.web.controllers;

import java.net.http.HttpRequest;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value="/signout", method = RequestMethod.GET)
public class SignOutController {
	private static final Logger LOGGER = LoggerFactory.getLogger(SignOutController.class);
	
	@GetMapping
	public String doGet(HttpServletRequest request)  throws ServletException
	{
		javax.servlet.http.HttpSession session = request.getSession();
		session.invalidate();
		LOGGER.info("GET Signout");
		return "redirect:/homepage";
		
	}
	
}
