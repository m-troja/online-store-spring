package com.itbulls.learnit.onlinestore.web.controllers;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class TestController {

	public String admin() 
	{
		return "admin";
	}

}