package com.bridgelabz;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LoginFilter implements Filter
{
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		LoginDao dao = new LoginDao();
	    response.setContentType("text/html");  
	    PrintWriter out = response.getWriter();  
	          
	    String name=request.getParameter("userName");  
	    String password=request.getParameter("userPass"); 
	    //password encryption
	    Cookie cookie1 = new Cookie("Username",name);
	    Cookie cookie2 = new Cookie("Password",password);
	   
	    try {
			if(dao.checkDetails(name, password,"User"))
			{ 
				HttpSession session = req.getSession();
				session.setAttribute("uname", name);
				res.addCookie(cookie1);
				res.addCookie(cookie2);
			    RequestDispatcher rd=request.getRequestDispatcher("/welcomeUser.jsp");  
			    rd.forward(request, response);  
			}  
			else if(dao.checkDetails(name, password,"Admin"))
			{
				HttpSession session = req.getSession();
				session.setAttribute("uname", name);
				res.addCookie(cookie1);
				res.addCookie(cookie2);
			    RequestDispatcher rd=request.getRequestDispatcher("/WelcomeAdmin.jsp");  
			    rd.forward(request, response);  
			}
			else
			{   
			    RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");  
			    rd.include(request, response);
			    out.print("Authentication Error"); 			                  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
}
