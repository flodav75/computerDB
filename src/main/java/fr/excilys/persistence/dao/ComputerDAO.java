package fr.excilys.persistence.dao;

import java.util.List;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.model.Computer;

public interface ComputerDAO {
	
	void add(Computer computer) throws ComputerDAOException;
	void update(Computer computer) throws ComputerDAOException;
	void remove(Computer computer) throws ComputerDAOException;
	List<Computer> getAll(int limit,int pageNumber) throws CompanyDAOException, ComputerDAOException;
	Computer getById(long id) throws CompanyDAOException, ComputerDAOException ;
	List<Computer> getByCompanyId(long id);
	int getRowCount();
	
}
