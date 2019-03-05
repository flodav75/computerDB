package fr.excilys.mappers;

import java.time.format.DateTimeFormatter;

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
		if ((name != null && !name.isEmpty())) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
			computerId = String.valueOf(computer.getId());
			if (computer.getIntroduced() != null) {
				introduced = computer.getIntroduced().format(formatter);

			}
			if (computer.getDiscontinued() != null) {
				discontinued = computer.getDiscontinued().format(formatter);

			}
			if (computer.getCompany() != null) {
				idCompany = String.valueOf(computer.getCompany().getId());
				companyName = computer.getCompany().getName();
			}
			computerDtoReturn = new ComputerDTO(computerId, name, introduced, discontinued, idCompany, companyName);
		} else {
			throw new ComputerNameException();
		}

		return computerDtoReturn;
	}

}
