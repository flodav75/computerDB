package fr.excilys.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.core.Company;
import fr.excilys.dto.ComputerDTO;
import fr.excilys.dto.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.exception.CompanyDAOException;
import fr.excilys.exception.ComputerDAOException;
import fr.excilys.exception.ComputerDateException;
import fr.excilys.exception.ComputerException;
import fr.excilys.exception.ComputerNameException;
import fr.excilys.exception.DateFormatException;
import fr.excilys.mappers.ComputerDtoMapper;
import fr.excilys.mappers.ComputerMapper;
import fr.excilys.service.CompanyService;
import fr.excilys.service.ComputerService;

@RequestMapping("/")
@Controller
public class ControllerComputer {

	private final static String LIMIT_DEFAULT = "10";
	private final static String OFFSET_DEFAULT = "1";
	private final static Integer[] LIMIT_VALID = { 10, 20, 50, 100 };

	private Integer offset;
	ComputerService computerSer;
	CompanyService companyServ;
	ComputerDtoMapper mapperDTO;
	ComputerMapper computerMapper;
	private Logger log;

	public ControllerComputer(ComputerService computerSer, CompanyService companyServ, ComputerDtoMapper mapperDTO,
			ComputerMapper computerMapper) {
		this.computerSer = computerSer;
		this.mapperDTO = mapperDTO;
		this.companyServ = companyServ;
		this.computerMapper = computerMapper;
		this.log = LoggerFactory.getLogger(ControllerComputer.class);
	}

	@GetMapping("index")
	public ModelAndView index(
			@RequestParam(name = "limit", required = false, defaultValue = LIMIT_DEFAULT) Integer limit,
			@RequestParam(name = "groupBy", required = false) String groupBy,
			@RequestParam(name = "pageNumber", required = false, defaultValue = OFFSET_DEFAULT) Integer currentPage,
			ModelAndView model) {
		List<ComputerDTO> computersDTO = new ArrayList<>();
		Long nbrRow = null;
		Long maxPage = null;
		valideLimit(limit);
		boolean isGroupBy = false;
		if (groupBy != null && !groupBy.isEmpty()) {
			isGroupBy = true;
		}

		this.offset = (currentPage - 1) * limit;
		try {
			nbrRow = getMaxPage();
		} catch (ComputerDAOException e) {
			this.log.error("error getting page max");
			this.log.debug(e.getMessage(), e);
			model.setViewName("/ressources/static/views/404.html");
		}
		maxPage = getPageNumberMax(nbrRow, limit);
		validePage(currentPage, maxPage);
		this.offset = (currentPage - 1) * limit;
		try {
			if (isGroupBy) {
				computersDTO = computerSer.getAllOrderByName(limit, this.offset);
			} else {
				computersDTO = computerSer.getAll(limit, this.offset);
			}
		} catch (ComputerNameException e) {
			this.log.error("error name computer");
			this.log.debug(e.getMessage(), e);
			model.setViewName("/ressources/static/views/404.html");
		} catch (CompanyDAOException e) {
			this.log.error("error  company");
			this.log.debug(e.getMessage(), e);
			model.setViewName("/ressources/static/views/404.html");
		} catch (ComputerDAOException e) {
			this.log.error("error  computer");
			this.log.debug(e.getMessage(), e);
			model.setViewName("/ressources/static/views/404.html");
		}
		
		model.addObject("computers", computersDTO);
		model.addObject("count", computersDTO.size());
		model.addObject("limit", limit);
		model.addObject("pageNumber", currentPage);
		model.addObject("max", maxPage);
		model.addObject("nbComputer", nbrRow);
		model.addObject("groupBy", groupBy);
		model.setViewName("dashboard");

		return model;
	}

	@GetMapping("search")
	public ModelAndView search(@RequestParam(name = "limit", required = false, defaultValue = LIMIT_DEFAULT) int limit,
			@RequestParam(name = "groupBy", required = false) String groupBy,
			@RequestParam(name = "pageNumber", required = false, defaultValue = OFFSET_DEFAULT) Integer currentPage,
			@RequestParam(name = "searchName", required = true) String searchName, ModelAndView model) {
		List<ComputerDTO> computersDTO = new ArrayList<>();
		Long nbrRow = null;
		Long maxPage = null;
		valideLimit(limit);
		boolean isGroupBy = false;

		if (groupBy != null && !groupBy.isEmpty()) {
			isGroupBy = true;
		}
		this.offset = (currentPage - 1) * limit;

		try {
			nbrRow = (long) this.computerSer.getRowCountSearch(searchName);
		} catch (ComputerDAOException e) {
			this.log.error("error getting page max");
			this.log.debug(e.getMessage(), e);
			model.setViewName("/ressources/static/views/404.html");
		}

		maxPage = getPageNumberMax(nbrRow, limit);
		validePage(currentPage, maxPage);
		this.offset = (currentPage - 1) * limit;

		try {
			if (isGroupBy) {
				computersDTO = computerSer.getByNameOrderByName(searchName, limit, this.offset);
			} else {
				computersDTO = computerSer.getByName(searchName, limit, this.offset);
			}
		} catch (CompanyDAOException e) {
			this.log.error("error  company");
			this.log.debug(e.getMessage(), e);
			model.setViewName("/ressources/static/views/404.html");
		} catch (ComputerDAOException e) {
			this.log.error("error  computer");
			this.log.debug(e.getMessage(), e);
			model.setViewName("/ressources/static/views/404.html");
		} catch (ComputerNameException e) {
			this.log.error("error name computer");
			this.log.debug(e.getMessage(), e);
			model.setViewName("/ressources/static/views/404.html");
		}
		// model.addObject("groupBy", null);
		model.addObject("computers", computersDTO);
		model.addObject("count", computersDTO.size());
		model.addObject("limit", limit);
		model.addObject("pageNumber", currentPage);
		model.addObject("max", maxPage);
		model.addObject("nbComputer", nbrRow);
		model.addObject("searchName", searchName);
		model.addObject("groupBy", groupBy);
		model.setViewName("dashboard");

		return model;
	}

	@GetMapping("add")
	public ModelAndView getAdding(ModelAndView model) {
		List<Company> companies = new ArrayList<>();
		try {
			companies = this.companyServ.getAll();
		} catch (CompanyDAOException e) {
			this.log.error("error  computer name add");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
		}
		model.addObject("companies", companies);
		model.setViewName("addComputer");
		return model;
	}

	@PostMapping("add")
	public ModelAndView postAdding(@RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "introduced", required = false) String introduced,
			@RequestParam(name = "discontinued", required = false) String discontinued,
			@RequestParam(name = "companyId", required = false) String companyId, ModelAndView model) {

		ComputerDTOBuilder compDtoBuilder = new ComputerDTOBuilder();
		compDtoBuilder.setName(name);
		compDtoBuilder.setIntroduced(introduced);
		compDtoBuilder.setDiscontinued(discontinued);
		compDtoBuilder.setCompanyId(companyId);

		try {
			this.computerSer.add(compDtoBuilder.build());
		} catch (ComputerDAOException e) {
			this.log.error("error  computer add, sql error ");
			this.log.debug(e.getMessage(), e);
		} catch (NumberFormatException e) {
			this.log.error("error  computer add");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:computer/index");
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
		} catch (ComputerException e) {
			this.log.error("error  computer  ");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
		}

		model.setViewName("redirect:index");
		return model;
	}

	@PostMapping("delete")
	public ModelAndView delete(@RequestParam(name = "selection", required = true) String idComputers,
			ModelAndView model) {

		String[] computers = idComputers.split(",");
		for (int i = 0; i < computers.length; i++) {

			try {
				this.computerSer.remove(Long.parseLong(computers[i]));
			} catch (NumberFormatException e) {
				this.log.error("error typing name");
				model.setViewName("redirect:index");
			} catch (ComputerDAOException e) {
				this.log.error("error computer request");
				model.setViewName("redirect:index");
			}

		}
		model.setViewName("redirect:index");
		return model;
	}

	@GetMapping("edit")
	public ModelAndView getEdit(@RequestParam(name = "idComputer", required = true) Long id, ModelAndView model) {
		ComputerDTO computerDTO = null;
		try {
			computerDTO = this.computerSer.getById(id);
		} catch (CompanyDAOException e) {
			this.log.error("error  company edit  ");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
			return model;
		} catch (ComputerDAOException e) {
			this.log.error("error  computer edit  ");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
			return model;
		} catch (ComputerNameException e) {
			this.log.error("error  company edit  ");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
		}
		List<Company> companies = new ArrayList<>();
		try {
			companies = this.companyServ.getAll();
		} catch (CompanyDAOException e) {
			this.log.error("error  computer name add");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
			return model;
		}
		model.addObject("computer", computerDTO);
		model.addObject("companies", companies);
		model.setViewName("editComputer");
		return model;

	}

	@PostMapping("edit")
	public ModelAndView postEdit(@RequestParam(name = "idComputer", required = true) String id,
			@RequestParam(name = "name", required = true) String name,
			@RequestParam(name = "introduced", required = false) String introduced,
			@RequestParam(name = "discontinued", required = false) String discontinued,
			@RequestParam(name = "companyId", required = false) String companyId, ModelAndView model) {
		System.out.println("aaaaaaaAAAAAAaAAAAAAAAAAAAAAAAAAAAAAAAA");
		ComputerDTOBuilder compDtoBuilder = new ComputerDTOBuilder();
		compDtoBuilder.setId(id);
		compDtoBuilder.setName(name);
		compDtoBuilder.setIntroduced(introduced);
		compDtoBuilder.setDiscontinued(discontinued);
		compDtoBuilder.setCompanyId(companyId);

		try {
			this.computerSer.update(compDtoBuilder.build());
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
		} catch (ComputerDAOException e) {
			this.log.error("error  computer  add ");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
			return model;
		} catch (ComputerException e) {
			this.log.error("error  computer  add ");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:index");
		}

		model.setViewName("redirect:index");
		return model;
	}

	public Long getMaxPage() throws ComputerDAOException {
		Long returnPageMax = null;
		returnPageMax = this.computerSer.getCountRow();
		return returnPageMax;
	}

	public static boolean isNotNullorEmpty(String value) {

		return value != null && !value.isEmpty();
	}

	private static Long getPageNumberMax(Long nbrRow, int limit) {
		return (long) Math.ceil((1.0 * nbrRow) / limit);
	}

	// TODO mettre dans un validator
	private void valideLimit(int limit) throws NumberFormatException {
		boolean isValide = false;
		for (int i = 0; i < LIMIT_VALID.length; i++) {
			if (LIMIT_VALID[i].equals(limit)) {
				isValide = true;
			}
		}
		if (!isValide) {
			throw new NumberFormatException();
		}
	}

	private void validePage(int page, Long maxPage) throws NumberFormatException {
		if (page > maxPage || page < 1) {
			throw new NumberFormatException();
		}
	}

}
//460
