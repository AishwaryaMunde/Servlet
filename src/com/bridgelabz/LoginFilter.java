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
	/* This is filter class which is called before servlet 
	 * in this method user name and password is fetch entered by user on web page and 
	 * passed to dao class to check details in database  
	 * (non-Javadoc)
	 * @see javax.servlet.Filter#doFilter(javax.servlet.ServletRequest, javax.servlet.ServletResponse, javax.servlet.FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest req=(HttpServletRequest)request;
		HttpServletResponse res=(HttpServletResponse)response;
		LoginDao dao = new LoginDao();
	    response.setContentType("text/html");  
	    PrintWriter out = response.getWriter();  	
	    //fetching data using http request
	    String name=request.getParameter("userName");  
	    String password=request.getParameter("userPass"); 
	    //Cookie is created to store user details for small amount of time
	    Cookie cookie1 = new Cookie("Username",name);
	    Cookie cookie2 = new Cookie("Password",password);
	   
	    try {
			if(dao.checkDetails(name, password,"User"))		//check entered details is valid or not
			{ 												//if it is valid forward to user welcome page 
				HttpSession session = req.getSession();
				session.setAttribute("uname", name);
				res.addCookie(cookie1);
				res.addCookie(cookie2);
			    RequestDispatcher rd=request.getRequestDispatcher("/welcomeUser.jsp");  
			    rd.forward(request, response);  
			}  
			else if(dao.checkDetails(name, password,"Admin"))	//check entered details is valid or not
			{ 												//if it is valid forward to admin welcome page
				HttpSession session = req.getSession();
				session.setAttribute("uname", name);
				res.addCookie(cookie1);
				res.addCookie(cookie2);
			    RequestDispatcher rd=request.getRequestDispatcher("/WelcomeAdmin.jsp");  
			    rd.forward(request, response);  
			}
			else
			{   
				//if invalid data is entered user will remain on index page only
			    RequestDispatcher rd=request.getRequestDispatcher("/index.jsp");  
			    rd.include(request, response);
			    out.print("Authentication Error"); 			                  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
	}
}