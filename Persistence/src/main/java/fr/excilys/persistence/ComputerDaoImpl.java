package fr.excilys.persistence;

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

import fr.excilys.core.Computer;
import fr.excilys.exception.CompanyDAOException;
import fr.excilys.exception.ComputerDAOException;

@Repository
public class ComputerDaoImpl implements ComputerDAO {

	private static final String GET_BY_ID = "Select computer from Computer computer  LEFT JOIN Company company on company.id=computer.id where computer.id=:id";
	private static final String GET_ALL = "Select computer from Computer computer LEFT JOIN Company company on company.id=computer.id ";
	private static final String GET_ALL_ORDER_BY_NAME = "Select computer from Computer computer LEFT JOIN Company company on company.id=computer.id ORDER BY computer.name";

	private static final String GET_BY_NAME = "Select computer from Computer computer LEFT JOIN Company company on company.id=computer.id where computer.name like :name  ";
	private static final String GET_BY_NAME_ORDER_By_NAME = "Select computer from Computer computer  LEFT JOIN Company company on company.id=computer.id where computer.name like :name ORDER BY computer.name ";
	private static final String DELETE = "Delete  Computer computer where computer.id=:id ";
	private static final String UPDATE = "Update Computer set name= :name, introduced = :introduced, discontinued = :discontinued, company_id=:company_id  where id=:id";
	public final static String ATTRIBUTLIST[] = { "id", "name", "introduced", "discontinued", "company_id" };

	private static final String COUNT_QUERY = "SELECT COUNT(id) AS count FROM Computer ";
	private static final String COUNT_QUERY_SEARCH = "SELECT COUNT(id) AS count FROM Computer  where name like :name";

	private SessionFactory sessionFactory;
	private Logger log;

	private ComputerDaoImpl(HibernateTransactionManager manager) {
		log = LoggerFactory.getLogger(ComputerDAO.class);
		this.sessionFactory = manager.getSessionFactory();
	}

	@Override
	public void add(Computer computer) throws ComputerDAOException {

		try (Session session = this.sessionFactory.openSession();) {
			session.save(computer);
		} catch (HibernateException e) {
			throw new ComputerDAOException();
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void update(Computer computer) throws ComputerDAOException {
		Long idCompany = null;
		int row = 0;
		if (computer.getCompany() != null) {
			idCompany = computer.getCompany().getId();
		}

		try (Session session = this.sessionFactory.openSession();) {
			try {
				session.getTransaction().begin();
				Query query = session.createQuery(UPDATE);
				query.setParameter("id", computer.getId());
				query.setParameter("name", computer.getName());
				query.setParameter("introduced", computer.getIntroduced());
				query.setParameter("discontinued", computer.getDiscontinued());
				query.setParameter("company_id", idCompany);
				row = query.executeUpdate();
				session.getTransaction().commit();
				this.log.info("update computer passed");
			} catch (HibernateException e) {
				session.getTransaction().rollback();
				this.log.error("update computer failed");
				throw new ComputerDAOException();
			}

			if (row == 0) {
				session.getTransaction().rollback();
				this.log.error("update computer failed");
				throw new ComputerDAOException();
			}
		}

	}

	@Override
	public void remove(long id) throws ComputerDAOException {
		int row = 0;

		try (Session session = this.sessionFactory.openSession();) {
			try {
				session.getTransaction().begin();
				Query query = session.createQuery(DELETE).setParameter("id", id);
				row = query.executeUpdate();
				session.getTransaction().commit();
				this.log.info("remove computer passed");
			} catch (HibernateException e) {
				session.getTransaction().rollback();
				this.log.info("remove computer failed");
				throw new ComputerDAOException();
			}
			if (row == 0) {
				session.getTransaction().rollback();
				this.log.info("remove computer failed");
				throw new ComputerDAOException();
			}
		}
	}

	@Override
	public List<Computer> getAll(int limit, int pageNumber) throws CompanyDAOException, ComputerDAOException {
		List<Computer> computers = new ArrayList<Computer>();

		try (Session session = this.sessionFactory.openSession();) {
			Query<Computer> query = session.createQuery(GET_ALL, Computer.class);
			query.setFirstResult(pageNumber);
			query.setMaxResults(limit);
			computers = query.list();
			this.log.info("getAll computer passed");
		} catch (HibernateException e) {
			this.log.info("getAll computer failed");
			throw new ComputerDAOException();
		}

		return computers;
	}

	@Override
	public Computer getById(long id) throws CompanyDAOException, ComputerDAOException {
		Computer computer = null;

		try (Session session = this.sessionFactory.openSession();) {

			try {
				Query<Computer> query = session.createQuery(GET_BY_ID, Computer.class);
				query.setParameter("id", id);
				computer = query.getSingleResult();
				this.log.info("getById computer passed");
			} catch (HibernateException e) {
				this.log.error("getById computer failed");
				throw new ComputerDAOException();
			}
		}
		return computer;
	}

	public Long getRowCount() throws ComputerDAOException {
		Long count = (long) -1;

		try (Session session = this.sessionFactory.openSession();) {
			Query<Long> query = session.createQuery(COUNT_QUERY, Long.class);
			count = query.getSingleResult();
			this.log.info("getRowCount computer passed");
		} catch (HibernateException e) {
			this.log.info("getRowCount computer failed");
			throw new ComputerDAOException();
		}

		return count;
	}

	public Long getRowCountSearch(String name) throws ComputerDAOException {
		Long count = (long) -1;
		String value = "%" + name + "%";

		try (Session session = this.sessionFactory.openSession();) {
			Query<Long> query = session.createQuery(COUNT_QUERY_SEARCH, Long.class);
			query.setParameter("name", value);
			count = query.getSingleResult();
			this.log.info("getRowCountSearch computer passed");
		} catch (HibernateException e) {
			this.log.info("getRowCountSearch computer failed");
			throw new ComputerDAOException();
		}

		return count;
	}

	@Override
	public List<Computer> getByName(String name, int limit, int pos) throws CompanyDAOException, ComputerDAOException {
		List<Computer> computers = new ArrayList<Computer>();
		String value = "%" + name + "%";

		try (Session session = this.sessionFactory.openSession();) {
			Query<Computer> query = session.createQuery(GET_BY_NAME, Computer.class);
			query.setParameter("name", value);
			query.setFirstResult(pos);
			query.setMaxResults(limit);
			computers = query.getResultList();
			this.log.info("getByName computer passed");
		} catch (HibernateException e) {
			this.log.info("getByName computer failed");
			throw new ComputerDAOException();
		}

		return computers;
	}

	@Override
	public List<Computer> getByNameOrderBy(String name, int limit, int pos)
			throws CompanyDAOException, ComputerDAOException {
		List<Computer> computers = new ArrayList<Computer>();
		String value = "%" + name + "%";

		try (Session session = this.sessionFactory.openSession();) {
			Query<Computer> query = session.createQuery(GET_BY_NAME_ORDER_By_NAME, Computer.class);
			query.setParameter("name", value);
			query.setFirstResult(pos);
			query.setMaxResults(limit);
			computers = query.getResultList();
			this.log.info("getByNameOrderBy computer passed");
		} catch (HibernateException e) {
			this.log.info("getByNameOrderBy computer failed");
			throw new ComputerDAOException();
		}

		return computers;
	}

	@Override
	public List<Computer> getAllByName(int limit, int pageNumber) throws CompanyDAOException, ComputerDAOException {
		List<Computer> computers = new ArrayList<Computer>();

		try (Session session = this.sessionFactory.openSession();) {
			Query<Computer> query = session.createQuery(GET_ALL_ORDER_BY_NAME, Computer.class);
			query.setFirstResult(pageNumber);
			query.setMaxResults(limit);
			computers = query.getResultList();
			this.log.info("remove computer passed");
		} catch (HibernateException e) {
			this.log.info("remove computer failed");
			throw new ComputerDAOException();
		}

		return computers;
	}

	@Override
	public List<Computer> getByCompanyId(long id) {
		return null;
	}

}
