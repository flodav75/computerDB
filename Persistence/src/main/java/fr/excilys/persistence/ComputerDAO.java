package fr.excilys.persistence;

import java.util.List;

import fr.excilys.core.Computer;
import fr.excilys.exception.CompanyDAOException;
import fr.excilys.exception.ComputerDAOException;

public interface ComputerDAO {

	void add(Computer computer) throws ComputerDAOException;

	void update(Computer computer) throws ComputerDAOException;

	void remove(long id) throws ComputerDAOException;

	List<Computer> getAll(int limit, int pageNumber) throws CompanyDAOException, ComputerDAOException;

	List<Computer> getAllByName(int limit, int pageNumber) throws CompanyDAOException, ComputerDAOException;

	Computer getById(long id) throws CompanyDAOException, ComputerDAOException;

	List<Computer> getByCompanyId(long id);

	Long getRowCount() throws ComputerDAOException;

	List<Computer> getByName(String name, int limit, int pos) throws CompanyDAOException, ComputerDAOException;

	List<Computer> getByNameOrderBy(String name, int limit, int pos) throws CompanyDAOException, ComputerDAOException;

	public Long getRowCountSearch(String name) throws ComputerDAOException;
}
