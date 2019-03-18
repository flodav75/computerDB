package fr.excilys.controller;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.mappers.ComputerDtoMapper;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;
import fr.excilys.servlets.IndexServlet;;

@RequestMapping("/index")
@Controller
public class ControllerComputer {

	private final static String LIMIT_DEFAULT = "10";
	private final static String OFFSET_DEFAULT = "1";
	private final static Integer[] LIMIT_VALID = { 10, 20, 50, 100 };

	private Integer offset;
	ComputerService computerSer;
	ComputerDtoMapper mapperDTO;
	private Logger log;
String  cat, dog="";

	@Autowired
	public ControllerComputer(ComputerService computerSer, ComputerDtoMapper mapperDTO) {
		this.computerSer = computerSer;
		this.mapperDTO = mapperDTO;
		this.log = LoggerFactory.getLogger(IndexServlet.class);
	}
	

	@GetMapping()
	public ModelAndView index(
			@RequestParam(name = "limit", required = false, defaultValue = LIMIT_DEFAULT) Integer limit,
			@RequestParam(name = "groupBy", required = false) String groupBy,
			@RequestParam(name = "pageNumber", required = false, defaultValue = OFFSET_DEFAULT) Integer currentPage,
			@RequestParam(name = "seach", required = false, defaultValue = OFFSET_DEFAULT) Integer searchName,
			ModelAndView model)  {
		List<ComputerDTO> computersDTO = new ArrayList<>();
		Integer nbrRow = null;
		Integer maxPage = null;
		valideLimit(limit);
		
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
			computersDTO = getAllComputers(limit,false);
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
		model.setViewName("dashboard");

		return model;
	}
	
	
//	 @GetMapping
//	  public ModelAndView doGet(@RequestParam(value = "page", defaultValue = "1") String pageNumber,
//	                            ModelAndView modelView) throws SQLException {
//	      int totalComputer = serviceComputer.getCount();
//	      int page = Integer.parseInt(pageNumber);
//	      List<Dto> computers = this.mapper.computersToDtos(this.serviceComputer.getComputers((page - 1) * 50));
//	      modelView.addObject("computers", computers);
//	      modelView.addObject("maxcomputer", totalComputer);
//	      modelView.setViewName("Dashboard");
//	      return modelView;
//	}

	public Integer getMaxPage() throws ComputerDAOException {
		Integer returnPageMax = null;
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

	private static int getPageNumberMax(int rows, int limit) {
		return (int) Math.ceil((1.0 * rows) / limit);
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

	private void validePage(int page, int pageMax) throws NumberFormatException {
		if (page > pageMax || page < 1) {
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
				computersReturn.add(ComputerDtoMapper.ComputerDtoFromComputer(comp));
			}
		}
		return computersReturn;
	}
}
