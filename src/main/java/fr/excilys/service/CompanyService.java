package fr.excilys.service;

import java.util.List;

import fr.excilys.model.Company;

public interface CompanyService {
	
	List<Company>getAll() ;
	 Company getById(long id) ;

}
