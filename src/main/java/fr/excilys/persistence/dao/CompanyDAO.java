package fr.excilys.persistence.dao;

import java.util.List;

import fr.excilys.model.Company;

public interface CompanyDAO {

	List<Company>getAll() ;
	Company getById(long id);

}
