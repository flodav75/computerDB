package fr.excilys.mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.exceptions.DateFormatException;
import fr.excilys.mappers.validations.ValidationComputerDTO;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyService;
import fr.excilys.service.CompanyServiceImpl;

public class ComputerMapper {

	
	private CompanyService companyServ;

	public ComputerMapper() {
		this.companyServ = CompanyServiceImpl.getInstance();
	}
	public Computer getComputerFromDTO(ComputerDTO compDt) throws ParseException, NumberFormatException, ComputerNameException, DateFormatException, CompanyDAOException {
		Computer comp = null;
		Long id = null;
		ValidationComputerDTO.validate(compDt);
		if (compDt.getId() != null && !"".equals(compDt.getId())) {
			id = Long.valueOf(compDt.getId());
		} else {
			id = 0L;
		}
		String name = compDt.getComputerName();
		Date introduced = formatDate(compDt.getIntroduced());
		Date discontinued = formatDate(compDt.getDiscontinued());
		Company company = getCompanyFromDTO(compDt);
		comp = new Computer(id, name, introduced, discontinued, company);
		return comp;
	}

	

	public Company getCompanyFromDTO(ComputerDTO compDt) throws  NumberFormatException, CompanyDAOException {
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
	private Date formatDate(String date) throws ParseException {
		Date dateReturn = null;
		if (date != null && !date.isEmpty()) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			dateReturn = dateFormat.parse(date);
		}
		return dateReturn;
	}

//private
	private Company getCompanyById(String id) throws  NumberFormatException, CompanyDAOException {
		Company company = null;
		if (id != null && !id.isEmpty()) {
			long idLong = Long.parseLong(id);
			company = this.companyServ.getById(idLong);
		}
		return company;
	}


}
