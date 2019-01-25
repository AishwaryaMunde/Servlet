package com.bridgelabz;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginDao 
{
	/**This method is to verify from database whether entered user details is valid or not 
	 * @param uname is user name entered by user
	 * @param password is user entered password 
	 * @param role is to check entered user is admin or normal user 
	 * @return boolean value whether entered details is correct or not from database 
	 * @throws Exception
	 */
	public boolean checkDetails(String uname,String password,String role)throws Exception
	{
		//SQL query to verify user details in database
		String query="select * from Userdata where Username=? and Password=? and Role=?";
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/login","admin","admin");
		PreparedStatement statement = con.prepareStatement(query);
		statement.setString(1,uname);
		statement.setString(2,password);
		statement.setString(3,role);
		ResultSet set = statement.executeQuery();
		//condition to check is that user name and password is available in database or not
		if(set.next())
		{
			return true;    //return true when entered details is correct
		}
		statement.close();
		con.close();
		return false;		
	}
}