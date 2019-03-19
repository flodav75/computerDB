package fr.excilys.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dtos.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.exceptions.ComputerDateException;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.exceptions.DateFormatException;
import fr.excilys.mappers.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyService;
import fr.excilys.service.ComputerService;
import fr.excilys.servlets.IndexServlet;;

@Controller
public class EditController {

	private ComputerService computerSer;
	private ComputerMapper compMap;
	private CompanyService compaSer;
	private Logger log;

	@Autowired
	public EditController(ComputerService computerSer, ComputerMapper compMap, CompanyService compaSer) {
		this.computerSer = computerSer;
		this.compaSer = compaSer;
		this.compMap = compMap;
		this.log = LoggerFactory.getLogger(IndexServlet.class);
	}

	
	public ModelAndView getEdit(@RequestParam(name = "idComputer", required = true) Long id,ModelAndView model) {
		Computer computer = null;
		try {
			computer = this.computerSer.getById(id);
		} catch (CompanyDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ComputerDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<Company> companies = new ArrayList<>();
		try {
			companies = this.compaSer.getAll();
		} catch (CompanyDAOException e) {
			this.log.error("error  computer name add");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
			return model; 
		}
		model.addObject("computer", computer);
		model.addObject("companies", companies);
		model.setViewName("editComputer");
		return model;

	}

	
	public ModelAndView postEdit(@RequestParam(name = "idComputer", required = true) String id,
			@RequestParam(name = "name", required = true) String name,
			@RequestParam(name = "introduced", required = false) String introduced,
			@RequestParam(name = "discontinued", required = false) String discontinued,
			@RequestParam(name = "companyId", required = false) String idCompany, ModelAndView model) {

		Computer computer = null;
		ComputerDTOBuilder compDtoBuilder = new ComputerDTOBuilder();
		compDtoBuilder.setId(id);
		compDtoBuilder.setName(name);
		compDtoBuilder.setIntroduced(introduced);
		compDtoBuilder.setDiscontinued(discontinued);
		compDtoBuilder.setCompanyId(idCompany);
		try {
			computer = compMap.getComputerFromDTO(compDtoBuilder.build());
		} catch (NumberFormatException e) {
			this.log.error("error  computer add");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
			return model;
		} catch (ParseException e) {
			this.log.error("error  computer date add");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
			return model;
		} catch (ComputerNameException e) {
			this.log.error("error  computer name add");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
			return model;
		} catch (DateFormatException e) {
			this.log.error("error  computer date add");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
			return model;
		} catch (CompanyDAOException e) {
			this.log.error("error  company add  ");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
			return model;
		} catch (ComputerDateException e) {
			this.log.error("error  computer date add ");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
			return model;
		}
		if (computer != null) {
			try {
				this.computerSer.update(computer);
			} catch (ComputerDAOException e) {
				this.log.error("error  computer add, sql error ");
				this.log.debug(e.getMessage(), e);
			}
		}

		model.setViewName("redirect:index");
		return model;
	}

}
