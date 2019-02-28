package fr.excilys.mappers;

import java.text.SimpleDateFormat;

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.model.Computer;

public class ComputerDtoMapper {

	
	
	public ComputerDtoMapper() {
		
	}
	
	public ComputerDTO ComputerDtoFromComputer(Computer computer) throws ComputerNameException {
		ComputerDTO computerDtoReturn = null;
		String computerId = null;
		String name = null;
		String introduced = null;
		String discontinued = null;
		String idCompany = null;
		String companyName = null;
		name = computer.getName();
		if((name !=null && !name.isEmpty())) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			computerId = String.valueOf(computer.getId());
			if(computer.getIntroduced()!=null) {
				introduced = dateFormat.format(computer.getIntroduced());
 
			}
			if(computer.getDiscontinued()!=null) {
				discontinued = dateFormat.format(computer.getDiscontinued());

			}
			if(computer.getCompany()!= null) {
				idCompany = String.valueOf(computer.getCompany().getId());
				companyName =computer.getCompany().getName();
			}
			computerDtoReturn =  new ComputerDTO(computerId, name, introduced, discontinued, idCompany, companyName);
		}else {
			throw new ComputerNameException();
		}
			
		return computerDtoReturn;
	}

}
