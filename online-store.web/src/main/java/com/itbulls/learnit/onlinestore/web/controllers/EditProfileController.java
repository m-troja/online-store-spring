package com.itbulls.learnit.onlinestore.web.controllers;

import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.itbulls.learnit.onlinestore.core.facades.CategoryFacade;
import com.itbulls.learnit.onlinestore.core.facades.UserFacade;
import com.itbulls.learnit.onlinestore.core.facades.impl.DefaultCategoryFacade;
import com.itbulls.learnit.onlinestore.core.services.Validator;
import com.itbulls.learnit.onlinestore.core.services.impl.CorePasswordValidator;
import com.itbulls.learnit.onlinestore.persistence.entities.Category;
import com.itbulls.learnit.onlinestore.persistence.entities.User;
import com.itbulls.learnit.onlinestore.web.Configurations;
import com.itbulls.learnit.onlinestore.web.utils.PBKDF2WithHmacSHA1EncryptionService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Controller
public class EditProfileController  {

	public final Logger logger =  LogManager.getLogger(EditProfileController.class);
	
	@Autowired
	private UserFacade userFacade;

	@Autowired
	private Validator passValidator;

	@Autowired
	private PBKDF2WithHmacSHA1EncryptionService encryptionService;
	
	@GetMapping("/edit-profile")
	public String doGet(HttpSession session)
	{	
		if ((User)session.getAttribute(SignInController.LOGGED_IN_USER_ATTR) == null)
		{
			return "redirect:/homepage";
		}
		return "editProfile";
	}
	
	@PostMapping("/edit-profile")
	public String doPost(@RequestParam("password") String password, @RequestParam("newPassword") String newPassword, 
			@RequestParam("firstName") String firstName, @RequestParam("lastName") String lastName,
			@RequestParam("email") String email, HttpSession session )
	{	
		User loggedInUser = (User)session.getAttribute(SignInController.LOGGED_IN_USER_ATTR);
		User userByEmail = userFacade.getUserByEmail(email);
		
		// User object to edit user
		User newUser = userFacade.getUserById(loggedInUser.getId());
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setEmail(email);
				
		//Validate current password
		if ( !encryptionService.validatePassword(password, loggedInUser.getPassword()))
		{
			session.setAttribute("errMsg", "Wrong password");
			return "editProfile"; 
		}
		
		//Check if user with entered email already exists
		if (userByEmail != null && !email.equals(loggedInUser.getEmail()))
		{
			session.setAttribute("errMsg", "User with this email already exists");
			return "editProfile"; 
		}
		
		// Validate new password
		if (newPassword != null && !newPassword.isEmpty()) {
			List<String> errorMessages = passValidator.validate(newPassword);
			if (errorMessages.size() != 0) {
				session.setAttribute("errMsg", "General error. Check if new password is not empty.");
				if (errorMessages.contains(CorePasswordValidator.LENGTH_OR_SPECIAL_CHARACTER_ERROR)) {
					session.setAttribute("errMsg", "New password must be atleast 8 chars long and must contain a special character.");
				}
				if (errorMessages.contains(CorePasswordValidator.MOST_COMMON_PASSWORD)) {
					session.setAttribute("errMsg", "Your password contains very common password. Please set more difficult password.");
				}
				return "redirect:/edit-profile";
			}
		}
		
		if (newPassword != null && !newPassword.isEmpty()) {
			newUser.setPassword(encryptionService.generatePasswordWithSaltAndHash(newPassword));
		}
		
		userFacade.updateUser(newUser);
		return "redirect:/my-profile";
	}
}
