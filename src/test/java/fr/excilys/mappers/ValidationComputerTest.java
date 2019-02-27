package fr.excilys.mappers;

import java.sql.SQLException;
import java.text.ParseException;

import org.junit.Test;

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.exceptions.DateFormatException;
import fr.excilys.mappers.validations.ValidationComputerDTO;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyServiceImpl;

//@RunWith(PowerMockRunner.class)
//@PrepareForTest(CompanyServiceImpl.class)
public class ValidationComputerTest {

	private ComputerDTO computerDTO;

	//@Test(expected=NumberFormatException.class)
	public void valideTest1() throws SQLException, NumberFormatException, ParseException, ComputerNameException, DateFormatException {
		this.computerDTO = new ComputerDTO("fdd", "fgfg", "", "", "", "");
		ValidationComputerDTO.validate(this.computerDTO);	
	}
	
	//@Test(expected=ComputerNameException.class)
	public void valideTest2() throws SQLException, NumberFormatException, ParseException, ComputerNameException, DateFormatException {
		this.computerDTO = new ComputerDTO("1", "", "", "", "", "");
		ValidationComputerDTO.validate(this.computerDTO);	
	}
	
	//@Test(expected=ComputerNameException.class)
	public void valideTest3() throws SQLException, NumberFormatException, ParseException, ComputerNameException, DateFormatException {
		this.computerDTO = new ComputerDTO("1", "gdfgdf'", "", "", "", "");
		ValidationComputerDTO.validate(this.computerDTO);	
	}
	
	//@Test(expected=ParseException.class)
	public void valideTest4() throws  NumberFormatException, ParseException, ComputerNameException, DateFormatException {
		this.computerDTO = new ComputerDTO("1", "gdfgdf", "fdgdfg", "", "", "");
		ValidationComputerDTO.validate(this.computerDTO);	
	}
	//@Test(expected=DateFormatException.class)
	public void valideTest5() throws  NumberFormatException, ParseException, ComputerNameException, DateFormatException {
		this.computerDTO = new ComputerDTO("1", "gdfgdf", "9000-40-17", "", "1", "");
		ValidationComputerDTO.validate(this.computerDTO);	
	}
	
	//@Test(expected=DateFormatException.class)
	public void valideTest6() throws  NumberFormatException, ParseException, ComputerNameException, DateFormatException {
		this.computerDTO = new ComputerDTO("1", "gdfgdf", "2000-40-17", "", "", "");
		ValidationComputerDTO.validate(this.computerDTO);	
	}
	//@Test(expected=DateFormatException.class)
	public void valideTest7() throws  NumberFormatException, ParseException, ComputerNameException, DateFormatException {
		this.computerDTO = new ComputerDTO("1", "gdfgdf", "2000-01-40", "", "", "");
		ValidationComputerDTO.validate(this.computerDTO);	
	}
	//@Test(expected=NumberFormatException.class)
	public void valideTest8() throws NumberFormatException, ParseException, ComputerNameException, DateFormatException {
		this.computerDTO = new ComputerDTO("1", "gdfgdf", "", "", "5525fgf", "");
		ValidationComputerDTO.validate(this.computerDTO);	
	}
	//@Test(expected=NumberFormatException.class)
	public void valideTest() throws NumberFormatException, ParseException, ComputerNameException, DateFormatException {
		this.computerDTO = new ComputerDTO("", "gdfgdf", "", "", "5525fgf", "");
		ValidationComputerDTO.validate(this.computerDTO);	
	}
	@Test(expected=DateFormatException.class)
	public void valideTest9() throws  NumberFormatException, ParseException, ComputerNameException, DateFormatException {
		this.computerDTO = new ComputerDTO("1", "gdfgdf", "2001-11-01", "", "1", "");
		ValidationComputerDTO.validate(this.computerDTO);	
	}
	
	
	
	

}
