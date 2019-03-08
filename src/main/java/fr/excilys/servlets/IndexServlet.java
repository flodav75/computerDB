package fr.excilys.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.mappers.ComputerDtoMapper;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;
import fr.excilys.service.ComputerServiceImpl;

@WebServlet("/Index")
public class IndexServlet extends HttpServlet {

	private final static Integer LIMIT_DEFAULT = 10;
	private final static Integer OFFSET_DEFAULT = 1;
	private final static Integer[] LIMIT_VALID = { 10, 20, 50, 100 };
	private static final long serialVersionUID = 1L;
	private ComputerService computerSer;
	private Logger log;
	private Integer limit;
	private Integer offset;

	public void init() throws ServletException {
		computerSer = ComputerServiceImpl.getInstance();
		this.log = LoggerFactory.getLogger(IndexServlet.class);

	}

	@SuppressWarnings("unchecked")
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ComputerDTO> computersDTO = new ArrayList<>();
		Integer currentpage = null;
		String nameToSearch = request.getParameter("search");
		Integer nbrRow = null;
		Integer maxPage = null;
		try {
			limit = getPagination(request.getParameter("limit"), LIMIT_DEFAULT);
			currentpage = getPagination(request.getParameter("pageNumber"), OFFSET_DEFAULT);
			valideLimit(limit);
			this.offset = (currentpage - 1) * this.limit;

			if (nameToSearch == null || nameToSearch.isEmpty()) {
				nbrRow = getMaxPage();
				maxPage = getPageNumberMax(nbrRow, limit);
				validePage(currentpage, maxPage);
				this.offset = (currentpage - 1) * this.limit;
				computersDTO = getAllComputers();
			} else {
				ComputerDtoMapper computerDtoMapper = new ComputerDtoMapper();
				List<Computer> computers = this.computerSer.getByName(nameToSearch, limit, this.offset);
				for (Computer comp : computers) {
					computersDTO.add(computerDtoMapper.ComputerDtoFromComputer(comp));
				}
				this.offset = (currentpage - 1) * this.limit;
				nbrRow = this.computerSer.getRowCountSearch(nameToSearch);
				maxPage = getPageNumberMax(nbrRow, limit);
				request.setAttribute("searchName", nameToSearch);
				System.out.println("aaaaa"+maxPage);
			}
			System.out.println("aaaaa"+maxPage);
//			if (!computersDTO.isEmpty()) {
				request.setAttribute("computers", computersDTO);
				request.setAttribute("count", computersDTO.size());
				request.setAttribute("limit", this.limit);
				request.setAttribute("pageNumber", currentpage);
				request.setAttribute("max", maxPage);
				request.setAttribute("nbComputer", nbrRow);
				request.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request,response);
//			} else {
//				this.log.error("error");
//				request.getServletContext().getRequestDispatcher("/ressources/static/views/404.html").forward(request,
//						response);
//			}
		} catch (ComputerNameException e) {
			
			this.log.error("error name");
			this.log.debug(e.getMessage(),e);
			request.getServletContext().getRequestDispatcher("/ressources/static/views/404.html").forward(request,
					response);
		} catch (NumberFormatException e) {
			this.log.error("error name");
			this.log.debug(e.getMessage(),e);

			request.getServletContext().getRequestDispatcher("/ressources/static/views/404.html").forward(request,
					response);
		} catch (CompanyDAOException e) {
			this.log.error("error company");
			this.log.debug(e.getMessage(),e);

			request.getServletContext().getRequestDispatcher("/ressources/static/views/404.html").forward(request,
					response);
		} catch (ComputerDAOException e) {
			this.log.error("error computer");
			this.log.error(e.getMessage());
			request.getServletContext().getRequestDispatcher("/ressources/static/views/404.html").forward(request,
					response);
		}

	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private List<ComputerDTO> getAllComputers()
			throws ComputerNameException, CompanyDAOException, ComputerDAOException {
		List<ComputerDTO> computersReturn = new ArrayList<>();
		List<Computer> computerToConvert = new ArrayList<>();
		ComputerDtoMapper mapperDTO = null;

		computerToConvert = computerSer.getAll(this.limit, this.offset);
		if (computerToConvert != null && !computerToConvert.isEmpty()) {
			mapperDTO = new ComputerDtoMapper();
			for (Computer comp : computerToConvert) {
				computersReturn.add(mapperDTO.ComputerDtoFromComputer(comp));
			}
		}
		return computersReturn;
	}

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


}
