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
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.mappers.ComputerDtoMapper;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;
import fr.excilys.service.ComputerServiceImpl;

@WebServlet("/Index")
public class IndexServlet extends HttpServlet {

	private ComputerService computerSer;
	private Logger log;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		computerSer = ComputerServiceImpl.getInstance();
		this.log = LoggerFactory.getLogger(AddComputerServlet.class);

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<ComputerDTO> computers = new ArrayList<>();
		try {
			List<ComputerDTO> computers1 = new ArrayList<>();
			computers1 = getAllComputers();
			if (!computers1.isEmpty()) {
				for (int i = 0; i < 4; i++) {
					computers.add(computers1.get(i));
				}
				request.setAttribute("computers", computers);
				request.setAttribute("count", computers.size());
				request.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request,
						response);
			} else {
				this.log.error("error");
				request.getServletContext().getRequestDispatcher("/ressources/static/views/404.html").forward(request,
						response);
			}

		} catch (ComputerNameException e) {
			this.log.error("error name");
			request.getServletContext().getRequestDispatcher("/ressources/static/views/404.html").forward(request,
					response);
		}
		// TODO: handle exception
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private List<ComputerDTO> getAllComputers() throws ComputerNameException {
		List<ComputerDTO> computersReturn = new ArrayList<>();
		List<Computer> computerToConvert = new ArrayList<>();
		ComputerDtoMapper mapperDTO = null;
		computerToConvert = computerSer.getAll();

		if (computerToConvert != null && !computerToConvert.isEmpty()) {
			mapperDTO = new ComputerDtoMapper();
			for (Computer comp : computerToConvert) {
				computersReturn.add(mapperDTO.ComputerDtoFromComputer(comp));
			}
		}
		return computersReturn;
	}

}
