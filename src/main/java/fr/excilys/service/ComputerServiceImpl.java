package fr.excilys.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.exceptions.ComputerDateException;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.exceptions.DateFormatException;
import fr.excilys.mappers.ComputerDtoMapper;
import fr.excilys.mappers.ComputerMapper;
import fr.excilys.model.Computer;
import fr.excilys.persistence.dao.ComputerDAO;
import fr.excilys.persistence.dao.ComputerDaoImpl;

@Service
public class ComputerServiceImpl implements ComputerService {

	private ComputerDAO computerDao;
	private ComputerMapper computerMapper;

	private ComputerServiceImpl(ComputerDaoImpl computerDao, ComputerMapper computerMapper) {
		this.computerDao = computerDao;
		this.computerMapper = computerMapper;
	}

	@Override
	public void add(ComputerDTO computerDTO) throws ComputerDAOException, NumberFormatException, ParseException,
			ComputerNameException, DateFormatException, CompanyDAOException, ComputerDateException {
		Computer computer = this.computerMapper.getComputerFromDTO(computerDTO);
		this.computerDao.add(computer);
	}

	@Override
	public void update(ComputerDTO computerDTO) throws ComputerDAOException, NumberFormatException, ParseException,
			ComputerNameException, DateFormatException, CompanyDAOException, ComputerDateException {
		Computer computer = this.computerMapper.getComputerFromDTO(computerDTO);
		this.computerDao.update(computer);

	}

	@Override
	public void remove(long id) throws ComputerDAOException  {
		this.computerDao.remove(id);
	}

	@Override
	public List<ComputerDTO> getAll(int limit, int pos)
			throws CompanyDAOException, ComputerDAOException, ComputerNameException {
		List<ComputerDTO> computersDTO = new ArrayList<>();

		for (Computer computer : this.computerDao.getAll(limit, pos)) {
			computersDTO.add(ComputerDtoMapper.computerDtoFromComputer(computer));
		}

		this.computerDao.getAll(limit, pos);
		return computersDTO;
	}

	@Override
	public ComputerDTO getById(long id) throws CompanyDAOException, ComputerDAOException, ComputerNameException {
		return ComputerDtoMapper.computerDtoFromComputer(this.computerDao.getById(id));

	}

	@Override
	public List<ComputerDTO> getByCompanyId(long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getCountRow() throws ComputerDAOException {
		return this.computerDao.getRowCount();
	}

	@Override
	public List<ComputerDTO> getByName(String name, int limit, int pos)
			throws ComputerDAOException, CompanyDAOException, ComputerNameException {
		List<ComputerDTO> computersDTO = new ArrayList<>();

		for (Computer computer : this.computerDao.getByName(name, limit, pos)) {
			computersDTO.add(ComputerDtoMapper.computerDtoFromComputer(computer));
		}

		return computersDTO;
	}

	@Override
	public Long getRowCountSearch(String name) throws ComputerDAOException {
		return this.computerDao.getRowCountSearch(name);
	}

	@Override
	public List<ComputerDTO> getAllOrderByName(int limit, int pos) throws CompanyDAOException, ComputerDAOException, ComputerNameException {
		List<ComputerDTO> computersDTO = new ArrayList<>();
		
		for (Computer computer : this.computerDao.getAllByName(limit, pos)) {
			computersDTO.add(ComputerDtoMapper.computerDtoFromComputer(computer));
		}
		return computersDTO;
	}

	@Override
	public List<ComputerDTO> getByNameOrderByName(String name, int limit, int pos)
			throws ComputerDAOException, CompanyDAOException, ComputerNameException {
		List<ComputerDTO> computersDTO = new ArrayList<>();
		
		for (Computer computer : this.computerDao.getByNameOrderBy(name, limit, pos)) {
			computersDTO.add(ComputerDtoMapper.computerDtoFromComputer(computer));
		}
		
		return computersDTO;
	}

}
