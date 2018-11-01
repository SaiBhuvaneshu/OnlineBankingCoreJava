package com.capgemini.obs.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBUtil {

	public static Connection getConnection(){
		Connection conn=null;
		try {
			
			DriverManager.registerDriver(new oracle.jdbc.driver.OracleDriver());
			conn=DriverManager.getConnection(IQueryMapper.URL,IQueryMapper.UNAME, IQueryMapper.PASSWORD);
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
		return conn;
		
	}
	
}
