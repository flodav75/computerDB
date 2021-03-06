package fr.excilys.persistence.dao;

import java.util.List;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.model.Computer;

public interface ComputerDAO {

	void add(Computer computer) throws ComputerDAOException;

	void update(Computer computer) throws ComputerDAOException;

	void remove(Computer computer) throws ComputerDAOException;

	List<Computer> getAll(int limit, int pageNumber) throws CompanyDAOException, ComputerDAOException;

	List<Computer> getAllByName(int limit, int pageNumber) throws CompanyDAOException, ComputerDAOException;

	Computer getById(long id) throws CompanyDAOException, ComputerDAOException;

	List<Computer> getByCompanyId(long id);

	int getRowCount() throws ComputerDAOException;

	List<Computer> getByName(String name, int limit, int pos) throws CompanyDAOException, ComputerDAOException;

	List<Computer> getByNameOrderBy(String name, int limit, int pos) throws CompanyDAOException, ComputerDAOException;

	public int getRowCountSearch(String name) throws ComputerDAOException;
}
