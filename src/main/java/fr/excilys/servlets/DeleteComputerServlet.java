package fr.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;

/**
 * Servlet implementation class DeleteComputerServlet
 */
@Controller
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Autowired
	private ComputerService computerSer;

	private Logger log;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteComputerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);

		this.log = LoggerFactory.getLogger(getClass());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO Auto-generated method stub
		String idComputers = request.getParameter("selection");
		String[] computers = idComputers.split(",");
		for (int i = 0; i < computers.length; i++) {
			try {
				removeComputer(Long.parseLong(computers[i]));
			} catch (NumberFormatException e) {
				this.log.error("error typing name");
				request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
			} catch (CompanyDAOException e) {
				this.log.error("error company request");
				request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
			} catch (ComputerDAOException e) {
				this.log.error("error computer request");
				request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
			}
		}
		request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
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

}
