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
import com.itbulls.learnit.onlinestore.core.facades.PurchaseFacade;
import com.itbulls.learnit.onlinestore.core.facades.UserFacade;
import com.itbulls.learnit.onlinestore.persistence.entities.Product;
import com.itbulls.learnit.onlinestore.persistence.entities.Purchase;
import com.itbulls.learnit.onlinestore.persistence.entities.User;

import javax.servlet.http.HttpSession;

@Controller
public class CheckoutController {
	
	@Autowired
	PurchaseFacade purchaseFacade;
	@Autowired
	ProductFacade productFacade;
	
	public final Logger LOGGER = LogManager.getLogger(CheckoutController.class);
	
	
	@GetMapping("/checkout")
	public String doGet(HttpSession session, @RequestParam("guid") String guid, Model model) {
		
		User user = (User)session.getAttribute(SignInController.LOGGED_IN_USER_ATTR);
		Product product = productFacade.getProductByGuid(guid);
		purchaseFacade.createPurchase(user, product);
		
		session.setAttribute("orderStatus", "Thank you for the order. Manager will contact you soon");
		return "redirect:/product?guid=" + guid;
	}
}
