package fr.excilys.persistence;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import fr.excilys.core.User;
import fr.excilys.exception.UserDAOException;

@Repository
public class UserDAO {

	private static final String GET_BY_NAME = "Select user from User user where user.login=:name";

	private Logger log;
	private SessionFactory sessionFactory;

	private UserDAO(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		this.log = LoggerFactory.getLogger(UserDAO.class);
	}

	public User getByName(String name) throws UserDAOException {
		User user = null;

		try (Session session = this.sessionFactory.openSession();) {

			try {
				Query<User> query = session.createQuery(GET_BY_NAME, User.class);
				query.setParameter("name",name);
				user = query.getSingleResult();
				this.log.info("getById user passed");
			} catch (HibernateException e) {
				this.log.error("getById user failed");
				throw new UserDAOException();
			}
		}
		return user;
	}
	public void add(User user) throws UserDAOException {

		try (Session session = this.sessionFactory.openSession();) {
			session.save(user);
		} catch (HibernateException e) {
			throw new UserDAOException();
		}
	}

}
