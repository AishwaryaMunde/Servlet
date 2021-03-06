package com.bridgelabz;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class RegisterToLogin
 */

public class RegisterToLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterToLogin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**This method fetch data entered by user on register.jsp page and save it into 
	 * database.this method create connection with database and perform operation on it.
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    int count=0;
    String query = "insert into Userdata(FirstName,LastName,Emailid,Username,Password)values(?,?,?,?,?)";
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		try
		{
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/login","admin","admin");
			PreparedStatement statement = connection.prepareStatement(query); 
			// fetching data from web page 
			String firstname = request.getParameter("firstname");
			String lastname = request.getParameter("lastname");
			String emailid = request.getParameter("emailid");
			String uname = request.getParameter("uname");
			String passwd = request.getParameter("password");
			//inserting data in databse
			statement.setString(1,firstname);
			statement.setString(2,lastname);
			statement.setString(3,emailid);
			statement.setString(4,uname);
			statement.setString(5,passwd);
			count = statement.executeUpdate();
			System.out.println(count + " rows affected");
			statement.close();
			connection.close();
			response.sendRedirect("index.jsp");		//after registration this method will redirect you to index page
		}
		catch(Exception e)
		{
			System.out.println(e);
		}
	}
}
