package fr.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.core.Company;
import fr.excilys.exception.CompanyDAOException;
import fr.excilys.exception.ComputerDAOException;
import fr.excilys.exception.DeleteCompanyException;
import fr.excilys.persistence.CompanyDAO;
import fr.excilys.persistence.CompanyDaoImpl;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyDAO companyDao;

	private CompanyServiceImpl(CompanyDaoImpl companyDao) {
		this.companyDao = companyDao;
	}
	
	@Override
	public List<Company> getAll() throws CompanyDAOException {
		return this.companyDao.getAll();
	}

	@Override
	public Company getById(long id) throws CompanyDAOException {

		return this.companyDao.getById(id);
	}

	@Override
	public void deleteCompany(long id) throws CompanyDAOException, ComputerDAOException, DeleteCompanyException {
		this.companyDao.deleteCompany(id);

	}

}
