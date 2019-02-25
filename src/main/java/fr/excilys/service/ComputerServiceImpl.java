package fr.excilys.service;

import java.sql.SQLException;
import java.util.List;

import fr.excilys.model.Computer;
import fr.excilys.persistence.dao.ComputerDAO;
import fr.excilys.persistence.dao.DAOFactory;

public class ComputerServiceImpl implements ComputerService {
	
	private ComputerDAO computerDao;
	private static ComputerService instance;
	
	
	private ComputerServiceImpl() {
		 DAOFactory.getInstance();
		this.computerDao =DAOFactory.getInstance().getComputerDAO();
	}
	
	public static ComputerService getInstance() {
		if(instance == null) {
			instance = new ComputerServiceImpl();
		}
		return instance;
	}

	@Override
	public void add(Computer computer) throws SQLException {
		this.computerDao.add(computer);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Computer computer) throws SQLException {
		this.computerDao.update(computer);
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(Computer computer) throws SQLException {
		this.computerDao.remove(computer);	
	}

	@Override
	public List<Computer> getAll() throws SQLException {
		return this.computerDao.getAll();
	}

	@Override
	public Computer getById(long id) throws SQLException {
		
		return this.computerDao.getById(id);
	}

	@Override
	public List<Computer> getByCompanyId(long id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


	
	

}
