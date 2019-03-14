package fr.excilys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.model.Computer;

@Service
public interface ComputerService {
	void add(Computer computer) throws ComputerDAOException;

	void update(Computer computer) throws ComputerDAOException;

	void remove(Computer computer) throws ComputerDAOException;

	List<Computer> getAll(int limit, int pos) throws CompanyDAOException, ComputerDAOException;

	List<Computer> getAllOrderByName(int limit, int pos) throws CompanyDAOException, ComputerDAOException;

	Computer getById(long id) throws CompanyDAOException, ComputerDAOException;

	List<Computer> getByCompanyId(long id);

	int getCountRow() throws ComputerDAOException;

	List<Computer> getByName(String name, int limit, int pos) throws ComputerDAOException, CompanyDAOException;

	List<Computer> getByNameOrderByName(String name, int limit, int pos)
			throws ComputerDAOException, CompanyDAOException;

	public int getRowCountSearch(String name) throws ComputerDAOException;
}
