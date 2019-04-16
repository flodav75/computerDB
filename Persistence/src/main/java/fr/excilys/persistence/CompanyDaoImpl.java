package fr.excilys.persistence;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import fr.excilys.core.Company;
import fr.excilys.exception.CompanyDAOException;
import fr.excilys.exception.DeleteCompanyException;

@Repository
public class CompanyDaoImpl implements CompanyDAO {

	private static final String DELETE_COMPANY = "Delete From Company company Where company.id = :id";
	private static final String DELETE_COMPUTER = "Delete From Computer computer Where computer.company_id = :id";
	private static final String GET_BY_ID = "Select company from Company company where company.id=:id";
	private static final String GET_ALL = "from Company ";

	private Logger log;
	private SessionFactory sessionFactory;

	private CompanyDaoImpl(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.log = LoggerFactory.getLogger(CompanyDaoImpl.class);
	}

	@Override
	public List<Company> getAll() throws CompanyDAOException {
		List<Company> computers = new ArrayList<>();

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
		Company company = null;

		try (Session session = this.sessionFactory.openSession();) {

			try {
				Query<Company> query = session.createQuery(GET_BY_ID, Company.class);
				query.setParameter("id", id);
				company = query.getSingleResult();
				this.log.info("getById company passed");
			} catch (HibernateException e) {
				this.log.error("getById company failed");
				throw new CompanyDAOException();
			}
		}
		return company;
	}

}