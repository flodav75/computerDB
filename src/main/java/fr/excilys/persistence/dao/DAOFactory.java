package fr.excilys.persistence.dao;

import java.sql.Connection;
import java.sql.SQLException;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DAOFactory {
	private static HikariConfig config = new HikariConfig(
			Thread.currentThread().getContextClassLoader().getResource("").getPath() + "hikari.properties");
	private static HikariDataSource ds = new HikariDataSource(config);

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";


	private static DAOFactory instance;

	private DAOFactory() {
		// config.setJdbcUrl(URL);
		// config.setUsername(USERNAME);
//		config.setPassword(PASSWORD);
//		config.addDataSourceProperty("cachePrepStmts", "true");
//		config.addDataSourceProperty("prepStmtCacheSize", "250");
//		config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
//		ds = new HikariDataSource(config);
	}

	public static DAOFactory getInstance() {
		if (instance == null) {
			try {
				Class.forName(DRIVER);
			} catch (ClassNotFoundException e) {
			}
			instance = new DAOFactory();
		}
		return instance;
	}

	public static Connection getConnection() throws SQLException {
		
			return ds.getConnection();
		

	}

	public ComputerDAO getComputerDAO() {
		return ComputerDaoImpl.getInstance();
	}

	public CompanyDAO getCompanyDAO() {
		return CompanyDaoImpl.getInstance();
	}

}
