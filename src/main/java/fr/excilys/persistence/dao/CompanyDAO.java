package fr.excilys.persistence.dao;

import java.sql.SQLException;
import java.util.List;

import fr.excilys.model.Company;

public interface CompanyDAO {

	List<Company>getAll() throws SQLException;
	Company getById(long id);

}
