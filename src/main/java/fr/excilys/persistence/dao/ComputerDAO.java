package fr.excilys.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import fr.excilys.model.Computer;

public interface ComputerDAO {
	
	void add(Computer computer) throws SQLException;
	void update(Computer computer) throws SQLException;
	void remove(Computer computer) throws SQLException;
	List<Computer> getAll()throws SQLException;
	Computer getById(long id)throws SQLException;
	List<Computer> getByCompanyId(long id)throws SQLException;
	
}
