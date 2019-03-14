package fr.excilys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.exceptions.DeleteCompanyException;
import fr.excilys.model.Company;
import fr.excilys.persistence.dao.CompanyDAO;

@Service
public class CompanyServiceImpl implements CompanyService {
	@Autowired
	private CompanyDAO companyDao;

	private CompanyServiceImpl() {
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
