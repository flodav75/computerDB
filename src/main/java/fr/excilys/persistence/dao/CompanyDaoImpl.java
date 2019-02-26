package fr.excilys.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.model.Company;

public class CompanyDaoImpl implements CompanyDAO {

	private static final String SELECT_BY_ID = "Select id,name from company where id=?";
	private static final String GET_ALL = "Select id,name from company";
	private DAOFactory daoFactory;
	private static CompanyDAO instance;
	private Logger log;

	private CompanyDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.log = LoggerFactory.getLogger(CompanyDaoImpl.class);

	}

	public static CompanyDAO getInstance() {
		if (instance == null) {
			instance = new CompanyDaoImpl(DAOFactory.getInstance());

		}
		return instance;
	}

	@Override
	public List<Company> getAll() {
		List<Company> companies = new ArrayList<Company>();

		try (Connection connect = this.daoFactory.getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(GET_ALL);) {
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				long id = result.getLong("id");
				String nom = result.getString("name");
				companies.add(new Company(id, nom));
			}
			log.info("companies finded");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			log.error("companes not finded");

		}
		return companies;
	}

	public Company getById(long id) {
		Company companyReturn = null;
		try (Connection connect = this.daoFactory.getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(SELECT_BY_ID);) {
			preparedStatement.setLong(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			companyReturn = mapResult(result);
			log.info("company finded");

		} catch (SQLException e) {
			e.printStackTrace();
			log.error("company not finded");

		}
		return companyReturn;
	}

	public static Company mapResult(ResultSet res) throws SQLException {
		Company comp = null;
		// System.out.println(res.getObject("id"));
		long id = (long) res.getObject("id");
		String nom = res.getString("name");
		comp = new Company(id, nom);
		return comp;
	}
}
