package fr.excilys.mappers;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.exceptions.DateFormatException;
import fr.excilys.mappers.validations.ValidationComputerDTO;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.model.Computer.ComputerBuilder;
import fr.excilys.service.CompanyService;
import fr.excilys.service.CompanyServiceImpl;

public class ComputerMapper {

	private CompanyService companyServ;

	public ComputerMapper() {
		this.companyServ = CompanyServiceImpl.getInstance();
	}

	public Computer getComputerFromDTO(ComputerDTO compDt) throws ParseException, NumberFormatException,
			ComputerNameException, DateFormatException, CompanyDAOException {
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
		return computerBuilder.build();
	}

	public Company getCompanyFromDTO(ComputerDTO compDt) throws NumberFormatException, CompanyDAOException {
		Company company = null;
		String name = compDt.getCompanyName();
		String idcompany = compDt.getCompanyId();
		if (idcompany != null && !idcompany.isEmpty()) {
			Long idCompany = Long.parseLong(idcompany);
			if (name == null || name.isEmpty()) {
				company = getCompanyById(idcompany);
			} else {
				company = new Company(idCompany, name);
			}
		}
		return company;
	}

	public LocalDate convertToDate(String date) {
		LocalDate formattedString = null;
		if (date != null && !date.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			formattedString = LocalDate.parse(date, formatter);
		}
		return formattedString;
	}

//private
	private Company getCompanyById(String id) throws NumberFormatException, CompanyDAOException {
		Company company = null;
		if (id != null && !id.isEmpty()) {
			long idLong = Long.parseLong(id);
			company = this.companyServ.getById(idLong);
		}
		return company;
	}

}
