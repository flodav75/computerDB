package fr.excilys.persistence.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.model.Computer.ComputerBuilder;

public class ComputerDaoImpl implements ComputerDAO {

	private static final String GET_BY_ID = "Select id,name,introduced,discontinued,company_id From computer Where id=? ";
	private static final String GET_ALL = "Select id,name,introduced,discontinued,company_id from computer LIMIT ? OFFSET ?";
	private static final String DELETE = "Delete from computer where id=? ";
	private static final String UPDATE = "Update computer set name=?, introduced = ?, discontinued = ?, company_id=?  where id=?";
	public final static String ATTRIBUTLIST[] = { "id", "name", "introduced", "discontinued", "company_id" };
	private final static String INSERT = "Insert into computer(name, introduced, discontinued, company_id) values(?,?,?,?)";
	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM computer";
	private static final String FIELD_COUNT = "count";
	private static ComputerDAO instance;
	private CompanyDAO companyDao;
	private DAOFactory daoFactory;
	private Logger log;

	private ComputerDaoImpl(DAOFactory daoFactory) {
		this.daoFactory = daoFactory;
		this.companyDao = this.daoFactory.getCompanyDAO();
		log = LoggerFactory.getLogger(ComputerDAO.class);
	}

	public static ComputerDAO getInstance() {
		if (instance == null) {
			instance = new ComputerDaoImpl(DAOFactory.getInstance());
		}
		return instance;
	}

	@Override
	public void add(Computer computer) throws ComputerDAOException {
		Long idCompany = null;
		;
		try (Connection connect = this.daoFactory.getConnection();
				PreparedStatement pSt = connect.prepareStatement(INSERT);) {
			pSt.setString(1, computer.getName());
			pSt.setObject(2, computer.getIntroduced());
			pSt.setObject(3, computer.getDiscontinued());

			if (computer.getCompany() != null) {
				idCompany = computer.getCompany().getId();
			}
			pSt.setObject(4, idCompany);
			pSt.execute();
			log.info("computer added to database");
		} catch (SQLException e) {
			log.error("computer not added to database");
			throw new ComputerDAOException();

		}
	}

	@Override
	public void update(Computer computer) throws ComputerDAOException {

		try (Connection connect = this.daoFactory.getConnection();
				PreparedStatement pSt = connect.prepareStatement(UPDATE)) {
			pSt.setString(1, computer.getName());
			pSt.setObject(2, computer.getIntroduced());
			pSt.setObject(3, computer.getDiscontinued());

			pSt.setLong(4, computer.getCompany().getId());
			pSt.setLong(5, computer.getId());
			pSt.execute();
			log.info("computer updated");
		} catch (SQLException e) {
			log.error("computer not updated");
			throw new ComputerDAOException();

		}
	}

	@Override
	public void remove(Computer computer) throws ComputerDAOException {
		try (Connection connect = this.daoFactory.getConnection();
				PreparedStatement pSt = connect.prepareStatement(DELETE);) {
			pSt.setLong(1, computer.getId());
			pSt.execute();
			log.info("computer removed");
		} catch (SQLException e) {
			log.error("computer not updated");
			throw new ComputerDAOException();

		}
	}

	@Override
	public List<Computer> getAll(int limit, int pageNumber) throws CompanyDAOException, ComputerDAOException {
		List<Computer> computers = new ArrayList<Computer>();
		try (Connection connect = this.daoFactory.getConnection();
				PreparedStatement pSt = connect.prepareStatement(GET_ALL);) {
			pSt.setInt(1, limit);
			pSt.setInt(2, pageNumber);
			System.out.println(pSt);
			ResultSet result = pSt.executeQuery();
			while (result.next()) {
				computers.add(mapResult(result));
			}
			log.info("computers found1");
		} catch (SQLException e) {
			log.error("computers not found");
			throw new ComputerDAOException();

		}
		return computers;
	}

	@Override
	public Computer getById(long id) throws CompanyDAOException, ComputerDAOException {
		Computer computer = null;
		try (Connection connect = this.daoFactory.getConnection();
				PreparedStatement pSt = connect.prepareStatement(GET_BY_ID);) {
			pSt.setLong(1, id);
			ResultSet result = pSt.executeQuery();
			result.next();
			computer = mapResult(result);
			log.info("computer found2");
		} catch (SQLException e) {
			log.info("computer not found");
			throw new ComputerDAOException();
		}
		return computer;
	}

	public int getRowCount() {
		int count = -1;
		try (Connection connect = this.daoFactory.getConnection();) {
			PreparedStatement statement = connect.prepareStatement(COUNT_QUERY);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				count = resultSet.getInt(FIELD_COUNT);
			}
			log.info("computer not found");

		} catch (SQLException e) {
			log.error("cant count computer rows");
		}
		return count;
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
//
//	public LocalDate convertToDate(String date) {
//		LocalDate formattedString = null;
//		if (date != null && !date.isEmpty()) {
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
//			formattedString = LocalDate.parse(date, formatter);
//		}
//		return formattedString;
//	}

}
