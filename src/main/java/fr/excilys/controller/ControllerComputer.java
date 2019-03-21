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

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.dtos.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.exceptions.ComputerDateException;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.exceptions.DateFormatException;
import fr.excilys.mappers.ComputerDtoMapper;
import fr.excilys.mappers.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyService;
import fr.excilys.service.ComputerService;
import fr.excilys.servlets.IndexServlet;;

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

	public ControllerComputer(ComputerService computerSer, CompanyService companyServ, ComputerDtoMapper mapperDTO, ComputerMapper computerMapper) {
		this.computerSer = computerSer;
		this.mapperDTO = mapperDTO;
		this.companyServ = companyServ;
		this.computerMapper = computerMapper;
		this.log = LoggerFactory.getLogger(IndexServlet.class);
	}
	 
	@GetMapping("index")
	public ModelAndView index(
			@RequestParam(name = "limit", required = false, defaultValue = LIMIT_DEFAULT) Integer limit,
			@RequestParam(name = "groupBy", required = false) String groupBy,
			@RequestParam(name = "pageNumber", required = false, defaultValue = OFFSET_DEFAULT) Integer currentPage,
			ModelAndView model)  {
		System.out.println("index");
		List<ComputerDTO> computersDTO = new ArrayList<>();
		Long nbrRow = null;
		Long maxPage = null;
		valideLimit(limit);
		boolean isGroupBy = false;
		if(groupBy !=null && !groupBy.isEmpty()) {
			isGroupBy = true;
		}
		
		this.offset = (currentPage - 1) * limit;
		try {
			nbrRow = getMaxPage();
		} catch (ComputerDAOException e) {
			this.log.error("error getting page max");
			this.log.debug(e.getMessage(),e);
			model.setViewName("/ressources/static/views/404.html");
		}
		maxPage = getPageNumberMax(nbrRow, limit);
		validePage(currentPage, maxPage);
		this.offset = (currentPage - 1) * limit;
		try {
			computersDTO = getAllComputers(limit,isGroupBy);
		} catch (ComputerNameException e) {
			this.log.error("error name computer");
			this.log.debug(e.getMessage(),e);
			model.setViewName("/ressources/static/views/404.html");
		} catch (CompanyDAOException e) {
			this.log.error("error  company");
			this.log.debug(e.getMessage(),e);
			model.setViewName("/ressources/static/views/404.html");
		} catch (ComputerDAOException e) {
			this.log.error("error  computer");
			this.log.debug(e.getMessage(),e);
			model.setViewName("/ressources/static/views/404.html");
		}
		//model.addObject("groupBy", null);
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
	public ModelAndView search(
			@RequestParam(name = "limit", required = false, defaultValue = LIMIT_DEFAULT) int limit,
			@RequestParam(name = "groupBy", required = false) String groupBy,
			@RequestParam(name = "pageNumber", required = false, defaultValue = OFFSET_DEFAULT) Integer currentPage,
			@RequestParam(name = "searchName", required = true) String searchName,
			ModelAndView model)  {
		System.out.println("search");
		List<ComputerDTO> computersDTO = new ArrayList<>();
		Long nbrRow = null;
		Long maxPage = null;
		valideLimit(limit);
		boolean isGroupBy = false;
		
		if(groupBy !=null && !groupBy.isEmpty()) {
			isGroupBy = true;
		}
		
		this.offset = (currentPage - 1) * limit;
		
		try {
			nbrRow = (long) this.computerSer.getRowCountSearch(searchName);
		} catch (ComputerDAOException e) {
			this.log.error("error getting page max");
			this.log.debug(e.getMessage(),e);
			model.setViewName("/ressources/static/views/404.html");
		}
		
		maxPage = getPageNumberMax(nbrRow, limit);
		validePage(currentPage, maxPage);
		this.offset = (currentPage - 1) * limit;
		
		try {
			computersDTO = getSearch(searchName, limit, isGroupBy);
		} catch (ComputerNameException e) {
			this.log.error("error name computer");
			this.log.debug(e.getMessage(),e);
			model.setViewName("/ressources/static/views/404.html");
		} catch (CompanyDAOException e) {
			this.log.error("error  company");
			this.log.debug(e.getMessage(),e);
			model.setViewName("/ressources/static/views/404.html");
		} catch (ComputerDAOException e) {
			this.log.error("error  computer");
			this.log.debug(e.getMessage(),e);
			model.setViewName("/ressources/static/views/404.html");
		}
		//model.addObject("groupBy", null);
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
			// return "redirect: index";
		}
		model.addObject("companies", companies);
		model.setViewName("addComputer");
		return model;
	}

	@PostMapping("add")
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
			computer = this.computerMapper.getComputerFromDTO(compDtoBuilder.build());
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
			model.setViewName("redirect:computer/index");
			return model;
		} catch (CompanyDAOException e) {
			this.log.error("error  company add  ");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:computer/index");
			return model;
		} catch (ComputerDateException e) {
			this.log.error("error  computer date add ");
			this.log.debug(e.getMessage(), e);
			model.setViewName("redirect:computer/index");
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
	
	@PostMapping("delete")
	public ModelAndView delete(@RequestParam(name = "selection", required = true) String idComputers,ModelAndView model) {
		
		String[] computers = idComputers.split(",");
		for (int i = 0; i < computers.length; i++) {
			try {
				removeComputer(Long.parseLong(computers[i]));
			} catch (NumberFormatException e) {
				this.log.error("error typing name");
				model.setViewName("redirect:index");
			} catch (CompanyDAOException e) {
				this.log.error("error company request");
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
			companies = this.companyServ.getAll();
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

	@PostMapping("edit")
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
			computer = computerMapper.getComputerFromDTO(compDtoBuilder.build());
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
	private void removeComputer(long id) throws CompanyDAOException, ComputerDAOException {
		Computer comp = null;
		comp = getComputer(id);
		if (comp != null) {
			this.computerSer.remove(comp);
		}
	}

	private Computer getComputer(long id) throws CompanyDAOException, ComputerDAOException {
		Computer comp = null;
		comp = this.computerSer.getById(id);
		return comp;
	}
	
	public Long getMaxPage() throws ComputerDAOException {
		Long returnPageMax = null;
		returnPageMax = this.computerSer.getCountRow();
		return returnPageMax;
	}

	public Integer getPagination(String value, int defaultValue) {
		Integer returnValue = null;
		if (isNotNullorEmpty(value) && valideInt(value)) {
			returnValue = Integer.parseInt(value);
		} else {
			returnValue = defaultValue;
		}
		return returnValue;
	}

	public static boolean isNotNullorEmpty(String value) {
		boolean isNull = false;
		if (value != null && !value.isEmpty()) {
			isNull = true;
		}
		return isNull;
	}

	private boolean valideInt(String value) {
		boolean isValide = false;
		try {
			Integer.parseInt(value);
			isValide = true;
			return isValide;
		} catch (NumberFormatException e) {
			return isValide;
		}
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
	private List<ComputerDTO> getAllComputers(int limit,boolean isGroupeByName )
			throws ComputerNameException, CompanyDAOException, ComputerDAOException {
		List<ComputerDTO> computersReturn = new ArrayList<>();
		List<Computer> computerToConvert = new ArrayList<>();
		if (isGroupeByName) {
			computerToConvert = computerSer.getAllOrderByName(limit, this.offset);
		} else {
			computerToConvert = computerSer.getAll(limit, this.offset);
		}
		if (computerToConvert != null && !computerToConvert.isEmpty()) {
			mapperDTO = new ComputerDtoMapper();
			for (Computer comp : computerToConvert) {
				computersReturn.add(ComputerDtoMapper.computerDtoFromComputer(comp));
			}
			
		}
		return computersReturn;
	}
	
	private List<ComputerDTO> getSearch(String name,int limit, boolean isGroupeByName)
			throws ComputerNameException, CompanyDAOException, ComputerDAOException {
		List<ComputerDTO> computersReturn = new ArrayList<>();
		List<Computer> computerToConvert = new ArrayList<>();

		if (isGroupeByName) {
			computerToConvert = computerSer.getByNameOrderByName(name, limit, this.offset);
		} else {
			computerToConvert = computerSer.getByName(name, limit, this.offset);
		}
		if (computerToConvert != null && !computerToConvert.isEmpty()) {
			for (Computer comp : computerToConvert) {
				computersReturn.add(ComputerDtoMapper.computerDtoFromComputer(comp));
			}
		}
		return computersReturn;
	}
}
