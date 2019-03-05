package fr.excilys.service;

import java.util.List;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.model.Company;

public interface CompanyService {
	
	List<Company>getAll() throws CompanyDAOException ;
	 Company getById(long id) throws CompanyDAOException ;

}
