package fr.excilys.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.junit.Before;
import org.junit.Test;

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.dtos.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.mappers.ComputerDtoMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.model.Computer.ComputerBuilder;

public class ComputerDtoMappertest {

	private Computer computer;
	private LocalDate introduced;
	private LocalDate discontinued;
	private Company company;
	private DateTimeFormatter formatter;
	private ComputerDtoMapper mapper;

	@Before
	public void init() throws ParseException {
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		this.introduced = LocalDate.parse("1995-11-01", formatter);
		this.discontinued = LocalDate.parse("1995-11-02", formatter);
		this.company = new Company(4, "pineapple");
	}

	@Test
	public void mapperDTO1() throws ComputerNameException {
		long id = 1;
		String name = "blabal";
		ComputerDTOBuilder computerDTOBuilder = new ComputerDTOBuilder();
		computerDTOBuilder.setId("1");
		computerDTOBuilder.setName(name);
		computerDTOBuilder.setIntroduced("1995-11-01");
		computerDTOBuilder.setDiscontinued("1995-11-02");
		computerDTOBuilder.setCompanyId("4");
		computerDTOBuilder.setCompanyName("pineapple"); 
		ComputerDTO compDtoGood = computerDTOBuilder.build();
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setId(id);
		computerBuilder.setName(name);
		computerBuilder.setIntroduced(introduced);
		computerBuilder.setDiscontinued(discontinued);
		computerBuilder.setCompany(new Company(4, "pineapple"));		
		ComputerDTO compTested = ComputerDtoMapper.computerDtoFromComputer(computerBuilder.build());

		assertEquals(compDtoGood, compTested);
	}

	@Test
	public void mapperDTO2() throws ComputerNameException {
		long id = 1;
		String name = "blabal";
		ComputerDTOBuilder computerDTOBuilder = new ComputerDTOBuilder();
		computerDTOBuilder.setId("1");
		computerDTOBuilder.setName(name);
		
		ComputerDTO compDtoGood = computerDTOBuilder.build();
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setId(id);
		computerBuilder.setName(name);
		computerBuilder.setIntroduced(introduced);
		computerBuilder.setDiscontinued(discontinued);
		computerBuilder.setCompany(new Company(4, "pineapple"));
		
		ComputerDTO compTested = ComputerDtoMapper.computerDtoFromComputer(this.computer);
		assertTrue(compDtoGood.equals(compTested));
	}

	@Test(expected = ComputerNameException.class)
	public void mapperDTO3() throws ComputerNameException {
		long id = 1;
		ComputerDTOBuilder computerDTOBuilder = new ComputerDTOBuilder();
		computerDTOBuilder.setId("1");
		computerDTOBuilder.setName("");

		ComputerDTO compDtoGood = computerDTOBuilder.build();
		ComputerDtoMapper.computerDtoFromComputer(this.computer);
	}

	@Test(expected = ComputerNameException.class)
	public void mapperDTO4() throws ComputerNameException {
		long id = 1;
		/// this.computer = new Computer(id,null,null,null,null);
		ComputerDtoMapper.computerDtoFromComputer(this.computer);
	}

}
