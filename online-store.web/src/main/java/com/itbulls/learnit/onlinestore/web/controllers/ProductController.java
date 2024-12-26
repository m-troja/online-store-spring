package com.itbulls.learnit.onlinestore.web.controllers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itbulls.learnit.onlinestore.core.facades.ProductFacade;
import com.itbulls.learnit.onlinestore.persistence.entities.Product;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpSession;

@Controller
public class ProductController extends HttpServlet {
	
	public final Logger logger = LogManager.getLogger(ProductController.class);
	@Autowired
	private ProductFacade productFacade;
	
	@GetMapping("/product")
	public String doGet( @RequestParam("guid") String guid, Model model, HttpSession session)
	{
		Product product = productFacade.getProductByGuid(guid);
		logger.info("desc = " + product.getDescription());
		model.addAttribute("description", product.getDescription());
		model.addAttribute("productName", product.getProductName());
		model.addAttribute("price", product.getPrice());
		model.addAttribute("imgName", product.getImgName());
		model.addAttribute("guid", product.getGuid());
		
		return "pdp";
	}
	

}
