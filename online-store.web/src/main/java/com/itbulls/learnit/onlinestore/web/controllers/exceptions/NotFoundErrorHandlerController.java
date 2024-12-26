package com.itbulls.learnit.onlinestore.web.controllers.exceptions;

import javax.servlet.http.HttpServlet;
import java.io.IOException;

import com.itbulls.learnit.onlinestore.web.Configurations;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/error403")
public class NotFoundErrorHandlerController extends HttpServlet {
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher(Configurations.VIEWS_PATH_RESOLVER 
				+ "403.jsp").forward(request, response);
	}
}
