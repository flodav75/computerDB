package fr.excilys.persistence.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.model.Computer.ComputerBuilder;

@Repository
public class ComputerDaoImpl implements ComputerDAO {

	private static final String GET_BY_ID = "Select id,name,introduced,discontinued,company_id From computer Where id=:id ";
	private static final String GET_ALL = "Select id,name,introduced, discontinued, company_id from computer  LIMIT :limit OFFSET :offset";
	private static final String GET_ALL_ORDER_BY_NAME = "Select id,name,introduced,discontinued,company_id from computer ORDER BY name  LIMIT :limit OFFSET :offset";

	private static final String GET_BY_NAME = "Select id,name,introduced,discontinued,company_id from computer where name like :name  LIMIT :limit OFFSET :offset";
	private static final String GET_BY_NAME_ORDER_By_NAME = "Select id,name,introduced,discontinued,company_id from computer where name like :name ORDER BY name LIMIT :limit OFFSET :offset";
	private static final String DELETE = "Delete from computer where id=? ";
	private static final String UPDATE = "Update computer set name= :name, introduced = :introduced, discontinued = :discontinued, company_id=:company_id  where id=:id";
	public final static String ATTRIBUTLIST[] = { "id", "name", "introduced", "discontinued", "company_id" };
	private final static String INSERT = "Insert into computer(name, introduced, discontinued, company_id) values(:name,:introduced,:discontinued,:company_id)";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM computer";
	private static final String COUNT_QUERY_SEARCH = "SELECT COUNT(id)  FROM computer where name like :name";
	@Autowired
	private CompanyDAO companyDao;

	private Logger log;
	private NamedParameterJdbcTemplate jdbcTemplateParam;

	@Autowired
	private ComputerDaoImpl(DataSource ds) {
		log = LoggerFactory.getLogger(ComputerDAO.class);
		setJdbcTemplate(ds);
	}

	public void setJdbcTemplate(DataSource dataSource) {
		this.jdbcTemplateParam = new NamedParameterJdbcTemplate(dataSource);
	}

	@Override
	public void add(Computer computer) throws ComputerDAOException {
		Long idCompany = null;

		if (computer.getCompany() != null) {
			idCompany = computer.getCompany().getId();
		}

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", computer.getId());
		params.put("name", computer.getName());
		params.put("introduced", computer.getIntroduced());
		params.put("discontinued", computer.getDiscontinued());
		params.put("company_id", idCompany);
		try {
			this.jdbcTemplateParam.update(INSERT, params);
			this.log.error("add computer passed");


		}catch (DataAccessException e) {
			this.log.error("add computer error");
			this.log.debug(e.getMessage(), e);
			throw new ComputerDAOException();
		}
	}

	@Override
	public void update(Computer computer) throws ComputerDAOException {
		Long idCompany = null;

		if (computer.getCompany() != null) {
			idCompany = computer.getCompany().getId();
		}

		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("id", computer.getId());
		params.put("name", computer.getName());
		params.put("introduced", computer.getIntroduced());
		params.put("discontinued", computer.getDiscontinued());
		params.put("company_id", idCompany);
		
		try {
			this.jdbcTemplateParam.update(UPDATE, params);
			this.log.error("update computer passed");


		}catch (DataAccessException e) {
			this.log.error("update computer error");
			this.log.debug(e.getMessage(), e);
			throw new ComputerDAOException();
		}
	}

	@Override
	public void remove(Computer computer) throws ComputerDAOException {
		try {
			this.jdbcTemplateParam.getJdbcTemplate().update(DELETE, computer.getId());
			this.log.error("remove computer passed");


		}catch (DataAccessException e) {
			this.log.error("remove computer error");
			this.log.debug(e.getMessage(), e);
			throw new ComputerDAOException();
		}

	}

	@Override
	public List<Computer> getAll(int limit, int pageNumber) throws CompanyDAOException, ComputerDAOException {
		List<Computer> computers = new ArrayList<Computer>();

		RowMapper<Computer> rowMapper = new RowMapper<Computer>() {
			@Override
			public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Computer computer = null;
				try {
					computer = mapResult(rs);
				} catch (CompanyDAOException e) {
					e.printStackTrace();
				}
				return computer;
			}
		};
		HashMap<String, Integer> params = new HashMap<String, Integer>();
		params.put("limit", limit);
		params.put("offset", pageNumber);
		try {
			computers = this.jdbcTemplateParam.query(GET_ALL, params, rowMapper);
			this.log.error("getAll computers passed");
		}catch (DataAccessException e) {
			this.log.error("getAll computers error");
			this.log.debug(e.getMessage(), e);
			throw new ComputerDAOException();
		}

		return computers;
	}

	@Override
	public Computer getById(long id) throws CompanyDAOException, ComputerDAOException {
		Computer computer = null;

		RowMapper<Computer> rowMapper = new RowMapper<Computer>() {
			public Computer mapRow(ResultSet pRS, int pRowNum) throws SQLException {
				Computer computer = null;
				try {
					computer = mapResult(pRS);
				} catch (CompanyDAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return computer;
			}
		};
		HashMap<String, Long> params = new HashMap<String, Long>();
		params.put("id", id);
		computer = (Computer) this.jdbcTemplateParam.queryForObject(GET_BY_ID, params, rowMapper);
		try {
			computer = (Computer) this.jdbcTemplateParam.queryForObject(GET_BY_ID, params, rowMapper);
			this.log.error("getById computer passed");
		}catch (DataAccessException e) {
			this.log.error("getById computer error");
			this.log.debug(e.getMessage(), e);
			throw new ComputerDAOException();
		}
		return computer;
	}

	public int getRowCount() throws ComputerDAOException {
		int count = -1;
		
		try {
			count = this.jdbcTemplateParam.getJdbcTemplate().queryForObject(COUNT_QUERY, Integer.class);
			this.log.error("getRowCount computer passed");
		}catch (DataAccessException e) {
			this.log.error("getRowCount computer error");
			this.log.debug(e.getMessage(), e);
			throw new ComputerDAOException();
		}

		return count;
	}

	public int getRowCountSearch(String name) throws ComputerDAOException {
		int count = -1;
		String value = "%" + name + "%";

		SqlParameterSource namedParameters = new MapSqlParameterSource("name", value);

		try {
			count = (Integer) this.jdbcTemplateParam.queryForObject(COUNT_QUERY_SEARCH, namedParameters, Integer.class);
			this.log.error("getRowCountSearch computer passed");
		}catch (DataAccessException e) {
			this.log.error("getRowCountSearch computer error");
			this.log.debug(e.getMessage(), e);
			throw new ComputerDAOException();
		} 
		return count;
	}

	@Override
	public List<Computer> getByName(String name, int limit, int pos) throws CompanyDAOException, ComputerDAOException {
		List<Computer> computers = new ArrayList<Computer>();
		String value = "%" + name + "%";
		RowMapper<Computer> rowMapper = new RowMapper<Computer>() {
			@Override
			public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Computer computer = null;
				try {
					computer = mapResult(rs);
				} catch (CompanyDAOException e) {
					e.printStackTrace();
				}
				return computer;
			}
		};
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("limit", limit);
		params.put("offset", pos);
		params.put("name", value);
		
		try {
			computers = this.jdbcTemplateParam.query(GET_BY_NAME, params, rowMapper);
			this.log.error("getByName computer passed");
		}catch (DataAccessException e) {
			this.log.error("getByName computer error");
			this.log.debug(e.getMessage(), e);
			throw new ComputerDAOException();
		}
		
		return computers;
	}

	@Override
	public List<Computer> getByNameOrderBy(String name, int limit, int pos)
			throws CompanyDAOException, ComputerDAOException {
		List<Computer> computers = new ArrayList<Computer>();
		String value = "%" + name + "%";
		RowMapper<Computer> rowMapper = new RowMapper<Computer>() {
			@Override
			public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Computer computer = null;
				try {
					computer = mapResult(rs);
				} catch (CompanyDAOException e) {
					e.printStackTrace();
				}
				return computer;
			}
		};
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("limit", limit);
		params.put("offset", pos);
		params.put("name", value);
		
		try {
			computers = this.jdbcTemplateParam.query(GET_BY_NAME_ORDER_By_NAME, params, rowMapper);
			this.log.error("getByName computer passed");
		}catch (DataAccessException e) {
			this.log.error("getByName computer error");
			this.log.debug(e.getMessage(), e);
			throw new ComputerDAOException();
		}
		
		return computers;
	}

	@Override
	public List<Computer> getAllByName(int limit, int pageNumber) throws CompanyDAOException, ComputerDAOException {
		List<Computer> computers = new ArrayList<Computer>();
		RowMapper<Computer> rowMapper = new RowMapper<Computer>() {
			@Override
			public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
				Computer computer = null;
				try {
					computer = mapResult(rs);
				} catch (CompanyDAOException e) {
					e.printStackTrace();
				}
				return computer;
			}
		};
		HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("limit", limit);
		params.put("offset", pageNumber);
		
		try {
			computers = this.jdbcTemplateParam.query(GET_ALL_ORDER_BY_NAME, params, rowMapper);
			this.log.error("getAllByName computer passed");
		}catch (DataAccessException e) {
			this.log.error("getAllByName computer error");
			this.log.debug(e.getMessage(), e);
			throw new ComputerDAOException();
		}
		
		
		return computers;
	}

	private Computer mapResult(ResultSet res) throws SQLException, CompanyDAOException {
		ComputerBuilder compBuild = new ComputerBuilder();
		Company company = null;
		long id = res.getLong("id");
		String name = res.getString("name");
		LocalDate introduced = convertToDate(res.getTimestamp("introduced"));
		LocalDate discontinued = convertToDate(res.getTimestamp("discontinued"));
		long idCompany = res.getLong("company_id");
		if (idCompany != 0) {
			company = companyDao.getById(idCompany);
		} else {
			company = new Company(0, null);
		}
		compBuild.setId(id);
		compBuild.setName(name);
		compBuild.setIntroduced(introduced);
		compBuild.setDiscontinued(discontinued);
		compBuild.setCompany(company);
		return compBuild.build();
	}

	public LocalDate convertToDate(Timestamp timeStamp) {
		LocalDate localDate = null;
		if (timeStamp != null) {
			localDate = timeStamp.toLocalDateTime().toLocalDate();
		}
		return localDate;
	}

	@Override
	public List<Computer> getByCompanyId(long id) {
		return null;
	}

}
