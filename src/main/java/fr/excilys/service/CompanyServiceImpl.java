package fr.excilys.service;

import java.sql.SQLException;
import java.util.List;

import fr.excilys.model.Company;
import fr.excilys.persistence.dao.CompanyDAO;
import fr.excilys.persistence.dao.DAOFactory;

public class CompanyServiceImpl implements CompanyService {

	private CompanyDAO companyDao;
	private static CompanyService instance;

	private CompanyServiceImpl() {
		this.companyDao = DAOFactory.getInstance().getCompanyDAO();
	}

	public static CompanyService getInstance() {
		if (instance == null) {
			instance = new CompanyServiceImpl();
		}
		return instance;
	}

	@Override
	public List<Company> getAll() {
		return this.companyDao.getAll();
	}

	@Override
	public Company getById(long id) {

		return this.companyDao.getById(id);
	}

}
