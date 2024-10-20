package com.itbulls.learnit.onlinestore.web.controllers;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itbulls.learnit.onlinestore.core.facades.CategoryFacade;
import com.itbulls.learnit.onlinestore.core.facades.impl.DefaultCategoryFacade;
import com.itbulls.learnit.onlinestore.persistence.entities.Category;
import com.itbulls.learnit.onlinestore.web.Configurations;

@Controller
public class HomepageController  {

	@Autowired
	private CategoryFacade categoryFacade;

	@GetMapping(value = {"/homepage", "/"})
	public String doGet(Model model)
	{	
		System.out.println("/Homepage: GET");
		List<Category> categories = categoryFacade.getCategories();
		model.addAttribute("categories", categories);
		return "homepage";
	}
	

}
