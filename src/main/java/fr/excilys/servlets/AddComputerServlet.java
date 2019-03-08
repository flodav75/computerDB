package fr.excilys.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.dtos.ComputerDTO.ComputerDTOBuilder;
import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.exceptions.DateFormatException;
import fr.excilys.mappers.ComputerMapper;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyService;
import fr.excilys.service.CompanyServiceImpl;
import fr.excilys.service.ComputerService;
import fr.excilys.service.ComputerServiceImpl;

@WebServlet("/AddComputer")
public class AddComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private ComputerService computerSer;
	private CompanyService compaSer;
	private Logger log;

	public void init() throws ServletException {
		this.computerSer = ComputerServiceImpl.getInstance();
		this.compaSer = CompanyServiceImpl.getInstance();
		this.log = LoggerFactory.getLogger(AddComputerServlet.class);
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies = null;

		try {
			companies = getCompanies();
		} catch (CompanyDAOException e) {
			this.log.error("error adding computer");
			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
		}
		request.setAttribute("companies", companies);
		request.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Computer computer;
		try {
			computer = getComputerForm(request, response);
			if (computer != null) {
				// System.out.println(computer.toString());

				try {
					computerSer.add(computer);
				} catch (ComputerDAOException e) {
					this.log.error("error adding computer");
					request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
				}
				request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
			} else {
				this.log.error("error non gérer");

				request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
			}
		} catch (NumberFormatException e) {
			this.log.error("error typing id");
			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);

		} catch (ParseException e) {
			this.log.error("error date format");
			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
			// TODO Auto-generated catch block
		} catch (ComputerNameException e) {
			this.log.error("error name");
			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
			// TODO Auto-generated catch block
		} catch (DateFormatException e) {
			this.log.error("error date not logical");
			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
		} catch (CompanyDAOException e1) {
			this.log.error("error adding company");
			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
		}
	}

	private List<Company> getCompanies() throws CompanyDAOException {
		List<Company> companies = null;
		companies = this.compaSer.getAll();
		return companies;
	}

	private Computer getComputerForm(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, ParseException, NumberFormatException, ComputerNameException,
			DateFormatException, CompanyDAOException {
		Computer computer = null;
		String name = request.getParameter("name");
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String idCompany = request.getParameter("companyId");
		ComputerDTOBuilder compDtoBuilder = new ComputerDTOBuilder();
		compDtoBuilder.setName(name);
		compDtoBuilder.setIntroduced(introduced);
		compDtoBuilder.setDiscontinued(discontinued);
		compDtoBuilder.setCompanyId(idCompany);
		ComputerMapper compMap = new ComputerMapper();
		computer = compMap.getComputerFromDTO(compDtoBuilder.build());
		return computer;
	}

}
