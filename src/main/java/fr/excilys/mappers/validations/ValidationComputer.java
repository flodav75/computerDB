package fr.excilys.mappers.validations;

import java.time.LocalDate;

import fr.excilys.exceptions.ComputerDateException;
import fr.excilys.model.Computer;

public class ValidationComputer {

	public static void validate(Computer computer) throws ComputerDateException {
		valideDates(computer.getIntroduced(), computer.getDiscontinued());
	}

	public static boolean isNullorEmpty(LocalDate date) {
		boolean isNull = false;
		if (date == null) {
			isNull = true;
		}
		return isNull;
	}

	private static void valideDates(LocalDate deb, LocalDate fin) throws ComputerDateException {
		if (!isNullorEmpty(deb) && !isNullorEmpty(fin)) {
			if (deb.isAfter(fin)) {
				throw new ComputerDateException();
			}
		}
	}

}
