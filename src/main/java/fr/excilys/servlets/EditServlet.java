package fr.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.service.CompanyService;
import fr.excilys.service.CompanyServiceImpl;
import fr.excilys.service.ComputerService;
import fr.excilys.service.ComputerServiceImpl;

@WebServlet("/EditComputer")
public class EditServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ComputerService computerSer;
	private CompanyService compaSer ;

	public void init() throws ServletException {
		 this.computerSer = ComputerServiceImpl.getInstance();
		 this.compaSer = CompanyServiceImpl.getInstance();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long idComputer = getIdComputer((String)request.getParameter("idComputer"));
		
		if(idComputer != null) {
			Computer computer;
			try {
				computer = getComputer(idComputer);
				List<Company> companies = getCompanies();
				request.setAttribute("computer", computer);
				request.setAttribute("companies", companies);
				request.getServletContext().getRequestDispatcher("/WEB-INF/views/editComputer.jsp").forward(request, response);
			} catch (SQLException e) {
				
				e.printStackTrace();
				request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
			}
		}else {
			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);

		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Computer  computer = getComputerForm(request,response);
		if(computer != null) {
			
			try {
				computerSer.update(computer);
				request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
			} catch (SQLException e) {
				
				e.printStackTrace();
				request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
			}
		}else {
			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
		}
	}
	
	private Long getIdComputer(String idString){
		Long id = null;
		if(idString != null && !"".equals(idString)) {
			id = Long.valueOf(idString);
		}
			return id;
	}

	private List<Company> getCompanies() throws SQLException {
		List<Company> companies = null;
		companies = this.compaSer.getAll();
		return companies;
	}
	
	private Computer getComputer(long id) throws SQLException {
		Computer computer = null;
		computer = this.computerSer.getById(id);
		return computer;
	}
	private Computer getComputerForm(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		Computer computer= null;
		
		try {
			
			Long idComputer = Long.valueOf(request.getParameter("idComputer"));
			String name = request.getParameter("introduced");
			Date introduced = formatDate(request.getParameter("discontinued"));
			Date discontinued = formatDate(request.getParameter("companyId"));
			Company company = getCompany(Long.valueOf(request.getParameter("companyId")));
			computer = new Computer(idComputer,name,introduced,discontinued,company);

		} catch (ParseException | NumberFormatException | SQLException e) {
			e.printStackTrace();
			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);	
		}
		return computer;
	}
	
	private Date formatDate(String date) throws ParseException {
		Date dateReturn = null;
		String newDate = date + " 00:00:00";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		dateReturn = dateFormat.parse(newDate);

		return dateReturn;
	}
	private Company getCompany(long id) throws SQLException {
		Company companies = null;
		companies = this.compaSer.getById(id);
		return companies;
	}
}
