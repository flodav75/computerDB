package fr.excilys.mappers;

import java.sql.SQLException;
import java.text.ParseException;

import org.junit.Test;

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyServiceImpl;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(CompanyServiceImpl.class)
public class ComputerMapperTest {

	private CompanyServiceImpl companyServ;
	private ComputerDTO computerDTO;
	private ComputerMapper computerMapper;

	//@Test/*(expected=IllegalArgumentException.class)*/
	public void getCompanyByIdTest() throws NumberFormatException, SQLException, ParseException {
		this.computerDTO = new ComputerDTO(null, "", "", "", "", "");
		this.computerMapper = new ComputerMapper(computerDTO);
		Computer computer = this.computerMapper.getComputerFromDTO();
		System.out.println(computer.toString());
	}
	//@Test/*(expected=IllegalArgumentException.class)*/
	public void getCompanyByIdTest1() throws NumberFormatException, SQLException, ParseException {
		this.computerDTO = new ComputerDTO("4", "", "", "", "1", "");
		this.computerMapper = new ComputerMapper(computerDTO);
		Computer computer = this.computerMapper.getComputerFromDTO();
		//Company company = this.computerMapper.getCompanyFromDTO();
		System.out.println(computer.toString());	
	}
	@Test
	public void getCompanyByIdTest2() throws NumberFormatException, SQLException, ParseException {
		this.computerDTO = new ComputerDTO("4", "fsdfzef", "1995-01-11 00:00:00", "1995-01-11 00:00:00", "1", "");
		this.computerMapper = new ComputerMapper(computerDTO);
		Computer computer = this.computerMapper.getComputerFromDTO();
		//Company company = this.computerMapper.getCompanyFromDTO();
		System.out.println(computer.toString());	
	}
	@Test
	public void getCompanyByIdTest3() throws NumberFormatException, SQLException, ParseException {
		this.computerDTO = new ComputerDTO("4", "fsdfzef", "1995-01-11 00:00:00", "1995-01-11 00:00:00", "1", "carrefour");
		this.computerMapper = new ComputerMapper(computerDTO);
		Computer computer = this.computerMapper.getComputerFromDTO();
		//Company company = this.computerMapper.getCompanyFromDTO();
		System.out.println(computer.toString());	
	}
	@Test
	public void getCompanyByIdTest4() throws NumberFormatException, SQLException, ParseException {
		this.computerDTO = new ComputerDTO("4", "fsdfzef", "1995-01s", "1995-01-11", "1", "carrefour");
		this.computerMapper = new ComputerMapper(computerDTO);
		Computer computer = this.computerMapper.getComputerFromDTO();
		//Company company = this.computerMapper.getCompanyFromDTO();
		System.out.println(computer.toString());	
	}
	
	

}
