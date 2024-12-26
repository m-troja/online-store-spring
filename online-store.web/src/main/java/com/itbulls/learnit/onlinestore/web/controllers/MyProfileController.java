package com.itbulls.learnit.onlinestore.web.controllers;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.itbulls.learnit.onlinestore.core.facades.PurchaseFacade;
import com.itbulls.learnit.onlinestore.core.facades.UserFacade;
import com.itbulls.learnit.onlinestore.persistence.entities.User;

@Controller
public class MyProfileController {
	
	@Autowired
	PurchaseFacade purchaseFacade;
	
	@Autowired
	UserFacade userFacade;
	
	@GetMapping("/my-profile")
	public String doGet(HttpServletRequest request, HttpSession session, Model model)
	{
		User user = (User)session.getAttribute(SignInController.LOGGED_IN_USER_ATTR);
		if (user == null)
		{
			return "redirect:/signin";
		}
		String partnerLink = request.getScheme()
	      + "://"
	      + request.getServerName()
	      + ":"
	      + request.getServerPort()
	      + request.getServletContext().getContextPath() + "?partner_code=" + user.getPartnerCode();
		
		model.addAttribute("partnerLink", partnerLink);
		
		List<User> referrals = userFacade.getReferralsForUser(user);
		model.addAttribute("referrals", referrals);
		
		return "myProfile";
	}
}
