package fr.excilys.controller;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
import fr.excilys.model.Computer;
import fr.excilys.service.ComputerService;

@Controller
public class DeleteController {
	
	private ComputerService computerServ;
	private Logger log;
	
	@Autowired
	public DeleteController(ComputerService computerServ) {
		this.computerServ = computerServ;
	}
	
	public ModelAndView getDelete(@RequestParam(name = "selection", required = true) String idComputers,ModelAndView model) {
		
		String[] computers = idComputers.split(",");
		for (int i = 0; i < computers.length; i++) {
			try {
				removeComputer(Long.parseLong(computers[i]));
			} catch (NumberFormatException e) {
				this.log.error("error typing name");
				model.setViewName("redirect:index");
			} catch (CompanyDAOException e) {
				this.log.error("error company request");
				model.setViewName("redirect:index");
			} catch (ComputerDAOException e) {
				this.log.error("error computer request");
				model.setViewName("redirect:index");
			}
		}
		model.setViewName("redirect:model");
		return model;
	}
	private void removeComputer(long id) throws CompanyDAOException, ComputerDAOException {
		Computer comp = null;
		comp = getComputer(id);
		if (comp != null) {
			this.computerServ.remove(comp);
		}
	}

	private Computer getComputer(long id) throws CompanyDAOException, ComputerDAOException {
		Computer comp = null;
		comp = this.computerServ.getById(id);
		return comp;
	}
}
