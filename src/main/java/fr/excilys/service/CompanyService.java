package fr.excilys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.exceptions.DeleteCompanyException;
import fr.excilys.model.Company;

@Service
public interface CompanyService {

	List<Company> getAll() throws CompanyDAOException;

	Company getById(long id) throws CompanyDAOException;

	void deleteCompany(long id) throws CompanyDAOException, ComputerDAOException, DeleteCompanyException;

}
