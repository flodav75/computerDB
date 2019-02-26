package fr.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.exceptions.ComputerNameException;
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

	public void init() throws ServletException {
		this.computerSer = ComputerServiceImpl.getInstance();
		this.compaSer = CompanyServiceImpl.getInstance();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Company> companies = null;
		try {
			companies = getCompanies();
			request.setAttribute("companies", companies);
			request.getServletContext().getRequestDispatcher("/WEB-INF/views/addComputer.jsp").forward(request,
					response);
		} catch (SQLException e) {
			e.printStackTrace();
			request.getServletContext().getRequestDispatcher("/ressources/static/views/500.html").forward(request,
					response);
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Computer computer;
		try {
			computer = getComputerForm(request, response);
			if (computer != null) {
				// System.out.println(computer.toString());

				computerSer.add(computer);
				request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
			} else {
				request.getServletContext().getRequestDispatcher("ressources/static/views/404.html").forward(request,
						response);
			}
		} catch (SQLException | NumberFormatException | ParseException | ComputerNameException e) {
			e.printStackTrace();
			request.getServletContext().getRequestDispatcher("ressources/static/views/403.html").forward(request,
					response);
		}
	}

	private List<Company> getCompanies() throws SQLException {
		List<Company> companies = null;
		companies = this.compaSer.getAll();
		return companies;
	}

	private Computer getComputerForm(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException, ParseException, NumberFormatException, SQLException, ComputerNameException {
		Computer computer = null;
		String name = request.getParameter("name");
		if (name != null && name.isEmpty()) {
			throw new ComputerNameException();
		}
		String introduced = request.getParameter("introduced");
		String discontinued = request.getParameter("discontinued");
		String idCompany = request.getParameter("companyId");
		ComputerMapper compMap = new ComputerMapper(
				new ComputerDTO(null, name, introduced, discontinued, idCompany, idCompany));
		computer = compMap.getComputer();
		System.out.println(computer.toString());
		return computer;
	}

}
