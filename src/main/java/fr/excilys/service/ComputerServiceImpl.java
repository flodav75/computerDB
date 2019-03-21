package fr.excilys.service;

import java.util.List;

import org.springframework.stereotype.Service;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.model.Computer;
import fr.excilys.persistence.dao.ComputerDAO;
import fr.excilys.persistence.dao.ComputerDaoImpl;

@Service
public class ComputerServiceImpl implements ComputerService {

	private ComputerDAO computerDao;

	private ComputerServiceImpl(ComputerDaoImpl computerDao) {
		this.computerDao = computerDao;
	}

	@Override
	public void add(Computer computer) throws ComputerDAOException {
		this.computerDao.add(computer);
	}

	@Override
	public void update(Computer computer) throws ComputerDAOException {
		this.computerDao.update(computer);
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(Computer computer) throws ComputerDAOException {
		this.computerDao.remove(computer);
	}

	@Override
	public List<Computer> getAll(int limit, int pos) throws CompanyDAOException, ComputerDAOException {
		return this.computerDao.getAll(limit, pos);
	}

	@Override
	public Computer getById(long id) throws CompanyDAOException, ComputerDAOException {

		return this.computerDao.getById(id);
	}

	@Override
	public List<Computer> getByCompanyId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getCountRow() throws ComputerDAOException {
		return this.computerDao.getRowCount();
	}

	@Override
	public List<Computer> getByName(String name, int limit, int pos) throws ComputerDAOException, CompanyDAOException {
		// @Autowired

		return this.computerDao.getByName(name, limit, pos);
	}

	@Override
	public Long getRowCountSearch(String name) throws ComputerDAOException {
		return this.computerDao.getRowCountSearch(name);
	}

	@Override
	public List<Computer> getAllOrderByName(int limit, int pos) throws CompanyDAOException, ComputerDAOException {
		return this.computerDao.getAllByName(limit, pos);
	}

	@Override
	public List<Computer> getByNameOrderByName(String name, int limit, int pos)
			throws ComputerDAOException, CompanyDAOException {
		return this.computerDao.getByNameOrderBy(name, limit, pos);
	}

}
