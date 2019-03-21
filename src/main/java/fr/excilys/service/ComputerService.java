package fr.excilys.service;

import java.text.ParseException;
import java.util.List;

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.exceptions.ComputerDateException;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.exceptions.DateFormatException;


public interface ComputerService {
	void add(ComputerDTO computer) throws ComputerDAOException, NumberFormatException, ParseException, ComputerNameException, DateFormatException, CompanyDAOException, ComputerDateException;

	void update(ComputerDTO computer) throws ComputerDAOException, NumberFormatException, ParseException, ComputerNameException, DateFormatException, CompanyDAOException, ComputerDateException;

	void remove(long id) throws ComputerDAOException ;
	List<ComputerDTO> getAll(int limit, int pos) throws CompanyDAOException, ComputerDAOException, ComputerNameException;

	List<ComputerDTO> getAllOrderByName(int limit, int pos) throws CompanyDAOException, ComputerDAOException, ComputerNameException;

	ComputerDTO getById(long id) throws CompanyDAOException, ComputerDAOException, ComputerNameException;

	List<ComputerDTO> getByCompanyId(long id);

	Long getCountRow() throws ComputerDAOException;

	List<ComputerDTO> getByName(String name, int limit, int pos) throws ComputerDAOException, CompanyDAOException, ComputerNameException;

	List<ComputerDTO> getByNameOrderByName(String name, int limit, int pos)
			throws ComputerDAOException, CompanyDAOException, ComputerNameException;

	public Long getRowCountSearch(String name) throws ComputerDAOException;
}
