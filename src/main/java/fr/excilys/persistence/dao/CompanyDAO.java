package fr.excilys.persistence.dao;

import java.util.List;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.DeleteCompanyException;
import fr.excilys.model.Company;

public interface CompanyDAO {

	List<Company> getAll() throws CompanyDAOException;

	Company getById(long id) throws CompanyDAOException;
	
	void deleteCompany(long id) throws  DeleteCompanyException;

}
