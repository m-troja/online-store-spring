package com.itbulls.learnit.onlinestore.web.controllers;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.itbulls.learnit.onlinestore.core.facades.ProductFacade;
import com.itbulls.learnit.onlinestore.core.facades.impl.DefaultProductFacade;
import com.itbulls.learnit.onlinestore.persistence.entities.Product;
import com.itbulls.learnit.onlinestore.web.Configurations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class CategoryController extends HttpServlet {
	
	@Value("${pagination.limit}")
	private Integer paginatiomLimit;
	
	@Autowired
	private ProductFacade productFacade;

	@GetMapping("/category")
	public String doGet(@RequestParam("id") Integer categoryId, @RequestParam("page") Integer page, Model model)
	{
		List<Product> products = productFacade.getProductsByCategoryIdForPageWithLimit(categoryId, page,
				Configurations.PAGINATION_LIMIT);
		Integer numberOfPagesForCategory = productFacade.getNumberOfPagesForCategory(categoryId,
				Configurations.PAGINATION_LIMIT);
		List<Integer> pages = IntStream.range(1, numberOfPagesForCategory + 1).boxed().collect(Collectors.toList());

		model.addAttribute("products", products);
		model.addAttribute("pages", pages);
		model.addAttribute("activePage", page);
		model.addAttribute("categoryId", categoryId);
		
		return "plp";
	}

}
