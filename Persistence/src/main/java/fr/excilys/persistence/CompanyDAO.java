package fr.excilys.persistence;

import java.util.List;

import fr.excilys.core.Company;
import fr.excilys.exception.CompanyDAOException;
import fr.excilys.exception.ComputerDAOException;
import fr.excilys.exception.DeleteCompanyException;

public interface CompanyDAO {

	List<Company> getAll() throws CompanyDAOException;

	Company getById(long id) throws CompanyDAOException;

	void deleteCompany(long id) throws DeleteCompanyException, ComputerDAOException;

}
