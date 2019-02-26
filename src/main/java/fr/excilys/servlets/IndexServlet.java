package fr.excilys.servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;
import fr.excilys.service.ComputerServiceImpl;

@WebServlet("/Index")
public class IndexServlet extends HttpServlet {

	private ComputerService computerSer;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {
		computerSer = ComputerServiceImpl.getInstance();
//		DAOFactory daoFactory = (DAOFactory) this.getServletContext().getAttribute(ServletUtilitaire.CONF_DAO_FACTORY);
//		this.equipePGLDAO = daoFactory.getEquipePGLDAO();
//		this.utilisateurDAO = daoFactory.getUtilisateurDao();
//		this.membreEquipePGLDAO=daoFactory.getMembreEquipePGLDAO();
//		this.typeEvaluationDAO=daoFactory.getTypeEvaluationDAO();
//		this.notePGLDAO=daoFactory.getNotePGLDAO();
//		this.utilisateurDAO=daoFactory.getUtilisateurDao();
//		this.professeurDAO=daoFactory.getProfesseurDAO();
//		this.juryPGLDAO=daoFactory.getJuryPGLDAO();
//		this.RoleDAO=daoFactory.getRoleDAO();
//		this.sprintDAO=daoFactory.getSprintDAO();
//		this.juryPGLTypeDAO = daoFactory.getJuryPGLTypeDAO();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		List<Computer> computers = new ArrayList<>();

		try {
			List<Computer> computers1 = getAllComputers().get();
			for (int i = 0; i < 4; i++) {
				computers.add(computers1.get(i));
			}
			request.setAttribute("computers", computers);
			request.setAttribute("count", computers.size());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			request.getServletContext().getRequestDispatcher("/WEB-INF/ressources/static/views/404.html")
					.forward(request, response);
		}
		request.getServletContext().getRequestDispatcher("/WEB-INF/views/dashboard.jsp").forward(request, response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private Optional<List<Computer>> getAllComputers() throws SQLException {
		List<Computer> computersReturn = null;
		if (computerSer.getAll() != null) {
			computersReturn = computerSer.getAll();
		}
		return Optional.ofNullable(computersReturn);
	}

}
