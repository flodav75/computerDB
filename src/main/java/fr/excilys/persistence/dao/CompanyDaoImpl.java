package fr.excilys.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.model.Company;

public class CompanyDaoImpl implements CompanyDAO {

	private static final String SELECT_BY_ID = "Select id,name from company where id=?";
	private static final String GET_ALL = "Select id,name from company";
	private static CompanyDAO instance;
	private Logger log;

	private CompanyDaoImpl(DAOFactory daoFactory) {
		this.log = LoggerFactory.getLogger(CompanyDaoImpl.class);

	}

	public static CompanyDAO getInstance() {
		if (instance == null) {
			instance = new CompanyDaoImpl(DAOFactory.getInstance());

		}
		return instance;
	}

	@Override
	public List<Company> getAll() throws CompanyDAOException {
		List<Company> companies = new ArrayList<Company>();

		try (Connection connect = DAOFactory.getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(GET_ALL);) {
			ResultSet result = preparedStatement.executeQuery();
			while (result.next()) {
				long id = result.getLong("id");
				String nom = result.getString("name");
				companies.add(new Company(id, nom));
			}
			log.info("companies found");
		} catch (SQLException e) {
			log.error("companes not finded");
			throw new CompanyDAOException();

		}
		return companies;
	}

	public Company getById(long id) throws CompanyDAOException {
		Company companyReturn = null;

		try (Connection connect = DAOFactory.getConnection();
				PreparedStatement preparedStatement = connect.prepareStatement(SELECT_BY_ID);) {
			preparedStatement.setLong(1, id);
			ResultSet result = preparedStatement.executeQuery();
			result.next();
			companyReturn = mapResult(result);

		} catch (SQLException e) {
			log.error("company not found");
			throw new CompanyDAOException();

		}
		return companyReturn;
	}

	public static Company mapResult(ResultSet res) throws SQLException {
		Company comp = null;
		long id = (long) res.getObject("id");
		String nom = res.getString("name");
		comp = new Company(id, nom);
		return comp;
	}
}
