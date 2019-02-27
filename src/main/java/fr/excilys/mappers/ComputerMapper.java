package fr.excilys.mappers;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.exceptions.DateFormatException;
import fr.excilys.mappers.validations.ValidationComputerDTO;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyService;
import fr.excilys.service.CompanyServiceImpl;

public class ComputerMapper {

	private ComputerDTO compDt;
	private Company company;
	private Computer computer;
	private CompanyService companyServ;

	public ComputerMapper(ComputerDTO compDt) {
		this.compDt = compDt;
		company = null;
		computer = null;
		this.companyServ = CompanyServiceImpl.getInstance();
	}

	public Computer getComputer() throws ParseException,  NumberFormatException, ComputerNameException, DateFormatException {
		this.setComputer(getComputerFromDTO());
		return computer;
	}

	public void setComputer(Computer computer) {
		this.computer = computer;
	}

//private
	public Computer getComputerFromDTO() throws ParseException, NumberFormatException, ComputerNameException, DateFormatException {
		Computer comp = null;
		Long id = null;
		ValidationComputerDTO.validate(this.compDt);
		if (this.compDt.getId() != null && !"".equals(this.compDt.getId())) {
			id = Long.valueOf(this.compDt.getId());
		} else {
			id = 0L;
		}
		String name = this.compDt.getComputerName();
		Date introduced = formatDate(this.compDt.getIntroduced());
		Date discontinued = formatDate(this.compDt.getDiscontinued());
		Company company = getCompanyFromDTO();
		comp = new Computer(id, name, introduced, discontinued, company);
		return comp;
	}

	// private
	public Date formatDate(String date) throws ParseException {
		Date dateReturn = null;
		if (date != null && date.isEmpty()) {
			String newDate = date + " 00:00:00";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			dateReturn = dateFormat.parse(newDate);
		}
		return dateReturn;
	}

//private
	public Company getCompanyFromDTO() throws  NumberFormatException {
		Company company = null;
		String name = this.compDt.getCompanyName();
		String idcompany = this.compDt.getCompanyId();

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

//private
	public Company getCompanyById(String id) throws  NumberFormatException {
		Company company = null;
		if (id != null && !id.isEmpty()) {
			long idLong = Long.parseLong(id);
			company = this.companyServ.getById(idLong);
		}
		return company;
	}

	public Company getCompany() throws  NumberFormatException {
		this.setCompany(getCompanyFromDTO());
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
