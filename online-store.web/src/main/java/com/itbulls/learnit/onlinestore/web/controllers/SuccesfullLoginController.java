package com.itbulls.learnit.onlinestore.web.controllers;


import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/success-login")
@Controller
public class SuccesfullLoginController {

	@GetMapping
	public String doGet(HttpSession session) 
	{
		session.setAttribute(SignInController.LOGGED_IN_USER_ATTR, session);
		return "success-login";
	}

 /* Not using with Spring Security
  
	@PostMapping
	public String doPost(@RequestParam String email, HttpSession session, @RequestParam String password) throws ServletException, IOException {
		User user = userFacade.getUserByEmail(email);
		LOGGER.info("Session is requested");
		if (user != null && encryptionService.validatePassword(password, user.getPassword())) {
			session.setAttribute(LOGGED_IN_USER_ATTR, user);
			LOGGER.info("User with ID {} is added to the session", user.getId());
			if (user.getRoleName().equals(ADMIN_ROLE_NAME)) {
				LOGGER.info("User with ID {} is redirected to the admin panel", user.getId());
				return "redirect:/admin/panel";
			} else {
				LOGGER.info("User with ID {} is redirected to the homepage", user.getId());
				return "redirect:/homepage";
			}
		} else {
			// counter for unsuccessful logins 
			Integer failedLoginCounter = (Integer)session.getAttribute(UNSUCCESSFUL_LOGIN_COUNT_ATTR_KEY);
			session.setAttribute(UNSUCCESSFUL_LOGIN_COUNT_ATTR_KEY, 
					failedLoginCounter == null ? 1 : ++failedLoginCounter);
			LOGGER.warn("Unsuccessful login attempt #{}", (Integer)session.getAttribute(UNSUCCESSFUL_LOGIN_COUNT_ATTR_KEY));
			return "redirect:/signin";
		}
		
	}
 */
}