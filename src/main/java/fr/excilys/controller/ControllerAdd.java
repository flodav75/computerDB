package fr.excilys.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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

@RequestMapping("/add")
@Controller
public class ControllerAdd {

	ComputerService computerSer;
	ComputerMapper compMap;
	private CompanyService compaSer;
	private Logger log;

	@Autowired
	public ControllerAdd(ComputerService computerSer, ComputerMapper compMap, CompanyService compaSer) {
		this.computerSer = computerSer;
		this.compaSer = compaSer;
		this.compMap = compMap;
		this.log = LoggerFactory.getLogger(IndexServlet.class);
	}

	@GetMapping
	public ModelAndView getAdding(ModelAndView model) {
		List<Company> companies = new ArrayList<>();
		try {
			companies = this.compaSer.getAll();
		} catch (CompanyDAOException e) {
			this.log.error("error  computer name add");
			this.log.debug(e.getMessage(), e);
			// return "redirect: index";
		}
		model.addObject("companies", companies);
		model.setViewName("addComputer");
		return model;
	}

	@PostMapping
	public ModelAndView postAdding(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "introduced", required = false) String introduced,
			@RequestParam(name = "discontinued", required = false) String discontinued,
			@RequestParam(name = "idCompany", required = false) String idCompany, ModelAndView model) {

		Computer computer = null;
		ComputerDTOBuilder compDtoBuilder = new ComputerDTOBuilder();
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
				this.computerSer.add(computer);
			} catch (ComputerDAOException e) {
				this.log.error("error  computer add, sql error ");
				this.log.debug(e.getMessage(), e);
			}
		}

		model.setViewName("redirect:index");
		return model;
	}

}
