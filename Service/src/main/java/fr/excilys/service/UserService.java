package fr.excilys.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import fr.excilys.core.User;
import fr.excilys.exception.UserDAOException;
import fr.excilys.persistence.UserDAO;
import fr.excilys.securityModel.UserDetailsImpl;

@Service
public class UserService implements UserDetailsService {
	
	private UserDAO userDAO;
	
	public  UserService(UserDAO userDAO) {
		this.userDAO = userDAO;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = null;
		
			try {
				 user = this.userDAO.getByName(username);
			} catch (UserDAOException e) {

				throw new UsernameNotFoundException(username);
			}
			
		
		return new UserDetailsImpl(user);
	}
	public void save(User user) throws UserDAOException {
		
			this.userDAO.add(user);
		
	}
	
	
	    
	

}
