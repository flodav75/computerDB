package fr.excilys.persistence.dao;

import java.util.List;

import fr.excilys.model.Computer;

public interface ComputerDAO {
	
	void add(Computer computer);
	void update(Computer computer);
	void remove(Computer computer);
	List<Computer> getAll();
	Computer getById(long id);
	List<Computer> getByCompanyId(long id);
	
}
