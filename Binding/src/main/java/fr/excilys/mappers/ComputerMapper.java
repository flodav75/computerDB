package fr.excilys.mappers;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import fr.excilys.core.Company;
import fr.excilys.core.Computer;
import fr.excilys.core.Computer.ComputerBuilder;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.ComputerDateException;
import fr.excilys.exception.ComputerException;
import fr.excilys.exception.ComputerNameException;
import fr.excilys.exception.DateFormatException;
import mappers.validations.ValidationComputer;
import mappers.validations.ValidationComputerDTO;

@Component
public class ComputerMapper {

//	@Autowired
//	private CompanyService companyServ;

	public ComputerMapper() {
	}

	public Computer getComputerFromDTO(ComputerDTO compDt) throws ParseException, NumberFormatException,
			ComputerNameException, DateFormatException, ComputerException, ComputerDateException {
		Long id = null;
		ValidationComputerDTO.validate(compDt);
		if (compDt.getId() != null && !"".equals(compDt.getId())) {
			id = Long.valueOf(compDt.getId());
		} else {
			id = 0L;
		}
		String name = compDt.getComputerName();
		LocalDate introduced = convertToDate(compDt.getIntroduced());
		LocalDate discontinued = convertToDate(compDt.getDiscontinued());
		Company company = getCompanyFromDTO(compDt);
		ComputerBuilder computerBuilder = new ComputerBuilder();
		computerBuilder.setId(id);
		computerBuilder.setName(name);
		computerBuilder.setIntroduced(introduced);
		computerBuilder.setDiscontinued(discontinued);
		computerBuilder.setCompany(company);
		Computer computer = computerBuilder.build();
		ValidationComputer.validate(computer);
		return computerBuilder.build();
	}

	public Company getCompanyFromDTO(ComputerDTO compDt) throws NumberFormatException, ComputerException {
		Company company = null;
		String name = compDt.getCompanyName();
		String idcompany = compDt.getCompanyId();
		if (idcompany != null && !idcompany.isEmpty()) {
			Long idCompany = Long.parseLong(idcompany);

			company = new Company(idCompany, name);

		} 
		return company;
	}

	private LocalDate convertToDate(String date) {
		LocalDate formattedString = null;
		if (date != null && !date.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			formattedString = LocalDate.parse(date, formatter);
		}
		return formattedString;
	}

//	private Company getCompanyById(String id) throws NumberFormatException, CompanyDAOException {
//		Company company = null;
//		if (id != null && !id.isEmpty()) {
//			long idLong = Long.parseLong(id);
//			//company = this.companyServ.getById(idLong);
//		}
//		return company;
//	}

}
