package fr.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteComputerServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void init() throws ServletException {
		this.computerSer = ComputerServiceImpl.getInstance();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String idComputers = request.getParameter("selection");
		try {
			if (!idComputers.isEmpty()) {

				String computers[] = idComputers.split(",");
				Long id = null;
				for (int i = 0; i < computers.length; i++) {
					id = Long.parseLong(computers[i]);
					removeComputer(id);
					id = null;

				}
				// TODO Auto-generated method stub
			}
		} catch (SQLException | NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String idComputers = request.getParameter("selection");
		System.out.println(idComputers);
	}

	private void removeComputer(long id) throws SQLException {
		Computer comp = null;
		comp = getComputer(id);
		if (comp != null) {
			this.computerSer.remove(comp);
		}
	}

	private Computer getComputer(long id) throws SQLException {
		Computer comp = null;
		comp = this.computerSer.getById(id);
		return comp;
	}

}
