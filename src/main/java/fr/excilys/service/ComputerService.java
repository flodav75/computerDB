package fr.excilys.service;

import java.util.List;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.model.Computer;

public interface ComputerService {
	void add(Computer computer) throws ComputerDAOException ;
	void update(Computer computer) throws ComputerDAOException ;
	void remove(Computer computer) throws ComputerDAOException ;
	List<Computer> getAll(int limit,int pos ) throws CompanyDAOException, ComputerDAOException;
	Computer getById(long id) throws CompanyDAOException, ComputerDAOException;
	List<Computer> getByCompanyId(long id);
	int getCountRow();
}
