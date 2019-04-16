package fr.excilys.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.CompanyDAOException;
import fr.excilys.exception.ComputerDAOException;
import fr.excilys.exception.ComputerDateException;
import fr.excilys.exception.ComputerException;
import fr.excilys.exception.ComputerNameException;
import fr.excilys.exception.DateFormatException;
import fr.excilys.service.CompanyService;
import fr.excilys.service.CompanyServiceImpl;
import fr.excilys.service.ComputerService;
import fr.excilys.service.ComputerServiceImpl;


@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

	ComputerService computerService;
	CompanyService companyService;
	Logger log;
	
	public RestController(ComputerServiceImpl computerService, CompanyServiceImpl companyService) {
		this.computerService = computerService;
		this.companyService = companyService;
		this.log = LoggerFactory.getLogger(getClass());
	}
	
	@GetMapping(value="/computers", produces = "application/json")
    public List<ComputerDTO> getComputers(/*@RequestParam int limit, @RequestParam int offset*/) {
		this.log.info("entre dans getall");
        return findComputers(10,1);
    }
	
//	@GetMapping(value="/computers", produces = "application/json")
//    public List<ComputerDTO> getComputers(@RequestParam int limit, @RequestParam int offset) {
//		this.log.info("entre dans getall");
//        return findComputers(limit, offset);
//    }
	@GetMapping(value="/computers/", produces = "application/json")
    public List<ComputerDTO> getComputersByNameSearch(@RequestParam int limit, @RequestParam int offset,@RequestParam String searchName  ) {
		this.log.info("entre dans search et order");
		return computersByNameSearch(limit, offset, searchName);
    }
	
//	@GetMapping(value="/computers/", produces = "application/json")
//    public List<ComputerDTO> getComputersByNameOrderByName(@RequestParam int limit, @RequestParam int offset, @RequestParam String orderBy ) {
//        return computersOrderByName(limit, offset);
//    }
	
	@GetMapping(value="/computers/", produces = "application/json")
    public List<ComputerDTO> getComputerByNameSearchOrderByName(@RequestParam int limit, @RequestParam int offset,@RequestParam String searchName,@RequestParam String orderBy) {

        return getByNameSearchOrderByName(limit, offset, searchName);
    }
	
	
	@GetMapping(value="/computers/{id}", produces = "application/json")
    public ComputerDTO getComputer(@PathVariable int id) {
        return findComputer(id);
    }
	

	
	
	
	
	
	
	@PostMapping(value="/computers", produces ="application/json")
	public ComputerDTO add(@RequestBody ComputerDTO computer) {
			create(computer);
		return computer;
	}
	
	
	@PatchMapping(value="/computers", produces ="application/json")
	public ComputerDTO updateComputer(@RequestBody ComputerDTO computer) {
		update(computer);
		return computer;
	}
	
	
	@DeleteMapping(value="/computers", produces ="application/json")
	public ComputerDTO deleteComputer(@RequestBody ComputerDTO computer) {
		delete(computer);
		return computer;
	}
	
	public void create(ComputerDTO computerDTO) {
		try {
			this.computerService.add(computerDTO);
		} catch (NumberFormatException | ComputerDAOException | ParseException | ComputerNameException
				| DateFormatException | CompanyDAOException | ComputerDateException | ComputerException e) {
			this.log.error("computer add failed");
		}
	}
	
	public void update(ComputerDTO computerDTO) {

			try {
				this.computerService.update(computerDTO);
			} catch (NumberFormatException | ComputerDAOException | ParseException | ComputerNameException
					| DateFormatException | CompanyDAOException | ComputerDateException | ComputerException e) {
				this.log.error("computer update failed");
			}
	}
	
	public void delete(ComputerDTO computerDTO) {
		try {
			long id = Long.parseLong(computerDTO.getId());
			this.computerService.remove(id);
		} catch (NumberFormatException | ComputerDAOException e) {
			this.log.error("computer update failed");
		}
		
}
	
	
	private List<ComputerDTO> findComputers(int limit,int offset) {
		List<ComputerDTO> computerDTOs = new ArrayList<>();
		
		try {
			computerDTOs = this.computerService.getAll(limit, offset);
		} catch (CompanyDAOException | ComputerDAOException | ComputerNameException e) {
			this.log.error("error name computer");
			this.log.debug(e.getMessage(), e);
		}
		
		return computerDTOs;
	}
	
	private ComputerDTO findComputer(int id) {
		ComputerDTO computerDTO = null;
		
		try {
			computerDTO = this.computerService.getById(id);
		} catch (CompanyDAOException | ComputerDAOException | ComputerNameException e) {
			this.log.error("error name computer");
			this.log.debug(e.getMessage(), e);
		}
		
		return computerDTO;
	}
	
	private List<ComputerDTO> computersByNameSearch(int limit, int offset, String name){
		List<ComputerDTO> computerDTOs = new ArrayList<>();
		
		try {
			computerDTOs = this.computerService.getByName(name, limit, offset);
		} catch (ComputerDAOException | CompanyDAOException | ComputerNameException e) {
			this.log.error("error order by name");
			this.log.debug(e.getMessage(), e);
		}
		
		return computerDTOs;
	}
	
	private List<ComputerDTO> computersOrderByName(int limit, int offset){
		List<ComputerDTO> computerDTOs = new ArrayList<>();
		
		try {
			computerDTOs = this.computerService.getAllOrderByName(limit, offset);
		} catch (ComputerDAOException | CompanyDAOException | ComputerNameException e) {
			this.log.error("error order by name");
			this.log.debug(e.getMessage(), e);
		}
		
		return computerDTOs;
	}
	
	private List<ComputerDTO> getByNameSearchOrderByName(int limit, int offset, String name){

		List<ComputerDTO> computerDTOs = new ArrayList<>();
		
		try {
			computerDTOs = this.computerService.getByNameOrderByName(name, limit, offset);
		} catch (ComputerDAOException | CompanyDAOException | ComputerNameException e) {
			this.log.error("error getByNameOrderByName");
			this.log.debug(e.getMessage(), e);
		}

		return computerDTOs;
	}

	

	


}
