package fr.excilys.mapper;

import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.mappers.ComputerDtoMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;

public class ComputerDtoMappertest {

	private Computer computer;
	private Date introduced;
	private Date discontinued;
	private Company company;
	private SimpleDateFormat dateFormat;
	private ComputerDtoMapper mapper;

	@Before
	public void init() throws ParseException {
		dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		this.introduced = dateFormat.parse("1995-11-01");
		this.discontinued = dateFormat.parse("1995-11-02");
		this.company = new Company(4, "pineapple");
	}

	@Test
	public void mapperDTO1() throws ComputerNameException {
		long id = 1;
		String name = "blabal";
		// this.computer = new
		// Computer(id,name,this.introduced,this.discontinued,this.company);
		ComputerDTO compDtoGood = new ComputerDTO("1", name, "1995-11-01", "1995-11-02", "4", "pineapple");
		this.mapper = new ComputerDtoMapper();
		ComputerDTO compTested = this.mapper.ComputerDtoFromComputer(this.computer);
		System.out.println(compTested.getComputerName());

		assertTrue(compDtoGood.equals(compTested));
	}

	@Test
	public void mapperDTO2() throws ComputerNameException {
		long id = 1;
		String name = "blabal";
		// this.computer = new Computer(id,name,null,null,null);
		ComputerDTO compDtoGood = new ComputerDTO("1", name, null, null, null, null);
		this.mapper = new ComputerDtoMapper();
		ComputerDTO compTested = this.mapper.ComputerDtoFromComputer(this.computer);
		assertTrue(compDtoGood.equals(compTested));
	}

	@Test(expected = ComputerNameException.class)
	public void mapperDTO3() throws ComputerNameException {
		long id = 1;
		// this.computer = new Computer(id,"",null,null,null);
		this.mapper = new ComputerDtoMapper();
		this.mapper.ComputerDtoFromComputer(this.computer);
	}

	@Test(expected = ComputerNameException.class)
	public void mapperDTO4() throws ComputerNameException {
		long id = 1;
		/// this.computer = new Computer(id,null,null,null,null);
		this.mapper = new ComputerDtoMapper();
		this.mapper.ComputerDtoFromComputer(this.computer);
	}

}
