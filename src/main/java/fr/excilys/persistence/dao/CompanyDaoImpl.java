package fr.excilys.persistence.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.stereotype.Repository;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.DeleteCompanyException;
import fr.excilys.model.Company;

@Repository
public class CompanyDaoImpl implements CompanyDAO {

	private static final String DELETE_COMPANY = "Delete From Company company Where company.id = :id";
	private static final String DELETE_COMPUTER = "Delete From Computer computer Where computer.company_id = :id";
	private static final String GET_ALL = "Select company from Company company";

	private Logger log;

	private SessionFactory sessionFactory;

	private CompanyDaoImpl(HibernateTransactionManager manage) {
		this.sessionFactory = manage.getSessionFactory();
		this.log = LoggerFactory.getLogger(CompanyDaoImpl.class);

	}

	@Override
	public List<Company> getAll() throws CompanyDAOException {
		List<Company> computers = new ArrayList<Company>();

		try (Session session = this.sessionFactory.openSession();) {
			Query<Company> query = session.createQuery(GET_ALL, Company.class);

			computers = query.list();
			this.log.info("getAll computer passed");
		} catch (HibernateException e) {
			this.log.info("getAll computer failed");
			throw new CompanyDAOException();
		}

		return computers;
	}

	@Override
	public void deleteCompany(long id) throws DeleteCompanyException {
		int row = 0;

		try (Session session = this.sessionFactory.openSession();) {
			try {
				session.getTransaction().begin();
				session.createQuery(DELETE_COMPUTER).setParameter("id", id).executeUpdate();
				session.createQuery(DELETE_COMPANY).setParameter("id", id).executeUpdate();
				session.getTransaction().commit();
				this.log.info("remove computer passed");
			} catch (HibernateException e) {
				session.getTransaction().rollback();
				this.log.info("remove company/computer failed");
				throw new DeleteCompanyException();
			}

			if (row == 0) {
				session.getTransaction().rollback();
				this.log.info("remove company/computer failed");
				throw new DeleteCompanyException();
			}
		}
	}

	@Override
	public Company getById(long id) throws CompanyDAOException {
		// TODO Auto-generated method stub
		return null;
	}

}
