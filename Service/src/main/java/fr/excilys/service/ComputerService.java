package fr.excilys.service;

import java.text.ParseException;
import java.util.List;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.CompanyDAOException;
import fr.excilys.exception.ComputerDAOException;
import fr.excilys.exception.ComputerDateException;
import fr.excilys.exception.ComputerException;
import fr.excilys.exception.ComputerNameException;
import fr.excilys.exception.DateFormatException;

public interface ComputerService {
	
	public  int a =1;
	void add(ComputerDTO computer) throws ComputerDAOException, NumberFormatException, ParseException,
			ComputerNameException, DateFormatException, CompanyDAOException, ComputerDateException, ComputerException;

	void update(ComputerDTO computer) throws ComputerDAOException, NumberFormatException, ParseException,
			ComputerNameException, DateFormatException, CompanyDAOException, ComputerDateException, ComputerException;

	void remove(long id) throws ComputerDAOException;

	List<ComputerDTO> getAll(int limit, int pos)
			throws CompanyDAOException, ComputerDAOException, ComputerNameException;

	List<ComputerDTO> getAllOrderByName(int limit, int pos)
			throws CompanyDAOException, ComputerDAOException, ComputerNameException;

	ComputerDTO getById(long id) throws CompanyDAOException, ComputerDAOException, ComputerNameException;

	List<ComputerDTO> getByCompanyId(long id);

	Long getCountRow() throws ComputerDAOException;

	List<ComputerDTO> getByName(String name, int limit, int pos)
			throws ComputerDAOException, CompanyDAOException, ComputerNameException;

	List<ComputerDTO> getByNameOrderByName(String name, int limit, int pos)
			throws ComputerDAOException, CompanyDAOException, ComputerNameException;

	public Long getRowCountSearch(String name) throws ComputerDAOException;
}
