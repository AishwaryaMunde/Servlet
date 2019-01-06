package com.bridgelabz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDao 
{
	public boolean checkDetails(String uname,String password,String role)throws Exception
	{
		String query="select * from Userdata where Username=? and Password=? and Role=?";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login","admin","admin");
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1,uname);
		statement.setString(2,password);
		statement.setString(3,role);
		ResultSet set = statement.executeQuery();
		if(set.next())
		{
			return true;
		}
		statement.close();
		con.close();
		return false;		
	}
}