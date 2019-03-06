package fr.excilys.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;
import fr.excilys.service.ComputerServiceImpl;

/**
 * Servlet implementation class DeleteComputerServlet
 */
@WebServlet("/DeleteComputer")
public class DeleteComputerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
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
		this.computerSer = ComputerServiceImpl.getInstance();
		this.log = LoggerFactory.getLogger(getClass());
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
//		System.out.println("doGet");
//		String idComputers = request.getParameter("selection");
//		try {
//			if (!idComputers.isEmpty()) {
//				String computers[] = idComputers.split(",");
//				Long id = null;
//				for (int i = 0; i < computers.length; i++) {
//					id = Long.parseLong(computers[i]);
//					removeComputer(id);
//					id = null;
//				}
//			}
//		} catch (NumberFormatException e) {
//			this.log.error("error typing name");
//			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
//		} catch (CompanyDAOException e) {
//			this.log.error("error company request");
//			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
//		} catch (ComputerDAOException e) {
//			this.log.error("error computer request");
//			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);
//		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TODO Auto-generated method stub
		String idComputers = request.getParameter("selection");
		String [] computers = idComputers.split(",");
		for(int i=0; i<computers.length;i++) {
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
			request.getServletContext().getRequestDispatcher("/Index").forward(request, response);

		}
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
