package com.itbulls.learnit.onlinestore.web.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itbulls.learnit.onlinestore.core.facades.ProductFacade;
import com.itbulls.learnit.onlinestore.core.facades.PurchaseFacade;
import com.itbulls.learnit.onlinestore.persistence.entities.Purchase;

@Controller
public class ManagementOrdersController {
	
	@Autowired
	PurchaseFacade purchaseFacade;
	
	@GetMapping("/management-orders")
	public String doGet(Model model)
	{
	
	
		List<Purchase> purchases = purchaseFacade.getNotCompletedPurchases();
		
		model.addAttribute("purchases", purchases);
		
		return "orders";
	}
	
	@PostMapping("/management-orders")
	public String doPost(@RequestParam("purchaseId") String id, Model model)
	{
		purchaseFacade.markFulfilmentStageForPurchaseIdAsCompleted(Integer.valueOf(id));
		List<Purchase> purchases = purchaseFacade.getNotCompletedPurchases();

		model.addAttribute("purchases", purchases);

		return "redirect:/management-orders";
	}
}
