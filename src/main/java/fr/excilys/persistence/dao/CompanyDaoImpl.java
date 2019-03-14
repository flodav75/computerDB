package fr.excilys.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.DeleteCompanyException;
import fr.excilys.model.Company;

@Repository
public class CompanyDaoImpl implements CompanyDAO {

	private static final String DELETE_COMPANY = "Delete From company Where id = ?";
	private static final String DELETE_COMPUTER = "Delete From computer Where company_id = ?";
	private static final String SELECT_BY_ID = "Select id,name from company where id= :id";
	private static final String GET_ALL = "Select id,name from company";

	private Logger log;

	private NamedParameterJdbcTemplate jdbcTemplateNamedParam;

	@Autowired
	private CompanyDaoImpl(DataSource dataSource) {
		this.log = LoggerFactory.getLogger(CompanyDaoImpl.class);
		setDataSource(dataSource);

	}

	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplateNamedParam = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public List<Company> getAll() throws CompanyDAOException {
		List<Company> companies = new ArrayList<Company>();
		RowMapper<Company> rowMapper = new RowMapper<Company>() {
			public Company mapRow(ResultSet pRS, int pRowNum) throws SQLException {
				Company company = new Company(pRS.getInt("id"), pRS.getString("name"));
				return company;
			}
		};
		try {
			companies = this.jdbcTemplateNamedParam.query(GET_ALL, rowMapper);
			log.info("getAll companies passed");

		} catch (DataAccessException e) {
			this.log.error("getAll companies error");
			this.log.debug(e.getMessage(), e);
			throw new CompanyDAOException();
		}
		return companies;
	}

	public Company getById(long id) throws CompanyDAOException {
		Company companyReturn = null;

		RowMapper<Company> rowMapper = new RowMapper<Company>() {
			public Company mapRow(ResultSet pRS, int pRowNum) throws SQLException {
				Company company = new Company(pRS.getInt("id"), pRS.getString("name"));
				return company;
			}
		};
		HashMap<String, Long> params = new HashMap<String, Long>();
		params.put("id", id);
		try {
			companyReturn = this.jdbcTemplateNamedParam.queryForObject(SELECT_BY_ID, params, rowMapper);

		} catch (EmptyResultDataAccessException e) {
			companyReturn = null;
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

	@Transactional
	@Override
	public void deleteCompany(long id) throws DeleteCompanyException {

		try {
			this.jdbcTemplateNamedParam.getJdbcTemplate().update(DELETE_COMPUTER, id);
			this.jdbcTemplateNamedParam.getJdbcTemplate().update(DELETE_COMPANY, id);
		} catch (DataAccessException e) {
			this.log.error("delete company failed");
			this.log.debug(e.getMessage(), e);
			throw new DeleteCompanyException();
		}
	}
}
