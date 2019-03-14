package fr.excilys.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.model.Computer.ComputerBuilder;
import fr.excilys.service.CompanyService;
import fr.excilys.service.ComputerService;

@Controller
@WebServlet("/EditComputer")
public class EditServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Autowired
	private ComputerService computerSer;

	@Autowired
	private CompanyService compaSer;

	public void init() throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idComputer = getIdComputer((String) request.getParameter("idComputer"));

		if (idComputer != null) {
			Computer computer;
			try {
				computer = getComputer(idComputer);
				List<Company> companies = getCompanies();
				request.setAttribute("computer", computer);
				request.setAttribute("companies", companies);
				request.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request,
						response);
			} catch (CompanyDAOException e) {
				request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
				e.printStackTrace();
			} catch (ComputerDAOException e) {
				request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
				e.printStackTrace();
			}
		} else {
			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Computer computer;
		try {
			computer = getComputerForm(request, response);
			if (computer != null) {
				computerSer.update(computer);
				request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
			} else {
				request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
			}
		} catch (NumberFormatException | ParseException | ComputerNameException e) {
			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
		} catch (ComputerDAOException e) {
			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
		} catch (CompanyDAOException e) {
			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
		}
	}

	private Long getIdComputer(String idString) {
		Long id = null;
		if (idString != null && !"".equals(idString)) {
			id = Long.valueOf(idString);
		}
		return id;
	}

	private List<Company> getCompanies() throws CompanyDAOException {
		List<Company> companies = null;
		companies = this.compaSer.getAll();
		return companies;
	}

	private Computer getComputer(long id) throws CompanyDAOException, ComputerDAOException {
		Computer computer = null;
		computer = this.computerSer.getById(id);
		return computer;
	}

	private Computer getComputerForm(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException, ParseException, NumberFormatException, ComputerNameException, CompanyDAOException {
		ComputerBuilder computerBuilder = new ComputerBuilder();
		Long idComputer = Long.valueOf(request.getParameter("idComputer"));
		String name = request.getParameter("name");
		if (name != null && name.isEmpty()) {
			throw new ComputerNameException();
		}
		LocalDate introduced = convertToDate(request.getParameter("introduced"));
		LocalDate discontinued = convertToDate(request.getParameter("discontinued"));
		Company company = getCompany(Long.valueOf(request.getParameter("companyId")));
		computerBuilder.setId(idComputer);
		computerBuilder.setName(name);
		computerBuilder.setIntroduced(introduced);
		computerBuilder.setDiscontinued(discontinued);
		computerBuilder.setCompany(company);
		return computerBuilder.build();
	}

	public LocalDate convertToDate(String date) {
		LocalDate formattedString = null;
		if (date != null && !date.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			formattedString = LocalDate.parse(date, formatter);
		}
		return formattedString;
	}

	private Company getCompany(long id) throws CompanyDAOException {
		Company company = null;
		company = this.compaSer.getById(id);
		return company;
	}
}
