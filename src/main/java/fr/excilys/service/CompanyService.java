package fr.excilys.service;

import java.sql.SQLException;
import java.util.List;

import fr.excilys.model.Company;

public interface CompanyService {
	
	List<Company>getAll() throws SQLException;
	 Company getById(long id) ;

}
