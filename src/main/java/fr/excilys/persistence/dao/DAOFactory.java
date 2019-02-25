package fr.excilys.persistence.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DAOFactory {
	
	private static final String URL="jdbc:mysql://localhost:3306/computer-database-db";
	private static final String DRIVER="com.mysql.cj.jdbc.Driver";
	private static final String USERNAME="admincdb";
	private static final String PASSWORD="qwerty1234"; 

	private String url;
	private String userName;
	private String password;
	
	private static DAOFactory instance;

	private DAOFactory(String url,String userName, String password){
        this.url = url;
        this.userName = userName;
        this.password = password;
	}
	
	public static DAOFactory getInstance() {
		if(instance==null) {
			try 
	        {
	            Class.forName(DRIVER);
	        } 
	        catch (ClassNotFoundException e) 
	        {
	            System.out.println(e.toString());
	        }
			instance = new DAOFactory(URL,USERNAME,PASSWORD);
		}
		return instance;
	}
	
	Connection getConnection() throws SQLException {
		return DriverManager.getConnection(url,userName,password);
	}
	
	public ComputerDAO getComputerDAO() {
		return ComputerDaoImpl.getInstance();
	}
	
	public CompanyDAO getCompanyDAO() {
		return  CompanyDaoImpl.getInstance();
	}

}	
	


