package fr.excilys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.excilys.core.Company;
import fr.excilys.exception.CompanyDAOException;
import fr.excilys.exception.ComputerDAOException;
import fr.excilys.exception.DeleteCompanyException;

@Service
public interface CompanyService {

	List<Company> getAll() throws CompanyDAOException;

	Company getById(long id) throws CompanyDAOException;

	void deleteCompany(long id) throws CompanyDAOException, ComputerDAOException, DeleteCompanyException;

}
