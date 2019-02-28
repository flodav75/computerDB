package fr.excilys.service;

import java.util.List;

import fr.excilys.model.Computer;

public interface ComputerService {
	void add(Computer computer) ;
	void update(Computer computer) ;
	void remove(Computer computer) ;
	List<Computer> getAll();
	Computer getById(long id);
	List<Computer> getByCompanyId(long id);
}
