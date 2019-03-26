package mappers.validations;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import fr.excilys.dto.ComputerDTO;
import fr.excilys.exception.ComputerNameException;

public class ValidationComputerDTO {
	private static final String[] BAD_NAME_VALUES = { "'", ";", ".", "*" };

	public static void validate(ComputerDTO comptDTO)
			throws ComputerNameException, ParseException, NumberFormatException, DateTimeParseException {
		valideId(comptDTO.getId());
		valideName(comptDTO.getComputerName());
		valideDate(comptDTO.getIntroduced());
		valideDate(comptDTO.getDiscontinued());
		valideId(comptDTO.getCompanyId());
		valideNameCompany(comptDTO.getCompanyName());
	}

	public static boolean isNullorEmpty(String value) {
		boolean isNull = false;
		if (value == null || value.isEmpty()) {
			isNull = true;
		}
		return isNull;
	}

	private static void valideDate(String date) throws ParseException, DateTimeParseException {
		if (!isNullorEmpty(date)) {
			convertToDate(date);
		}
	}

	private static LocalDate convertToDate(String date) throws ParseException, DateTimeParseException {
		LocalDate formattedString = null;
		if (date != null && !date.isEmpty()) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			formattedString = LocalDate.parse(date, formatter);
		}
		return formattedString;
	}

	private static void valideName(String name) throws ComputerNameException {
		if (isNullorEmpty(name)) {
			throw new ComputerNameException();
		} else {
			valideNameValue(name);

		}
	}

	private static void valideNameCompany(String name) throws ComputerNameException {
		if (!isNullorEmpty(name)) {
			valideNameValue(name);
		}
	}

	private static void valideNameValue(String name) throws ComputerNameException {
		for (int i = 0; i < BAD_NAME_VALUES.length; i++) {
			if (name.contains(BAD_NAME_VALUES[i])) {
				throw new ComputerNameException();
			}
		}
	}

	private static void valideId(String id) throws NumberFormatException {
		if (!isNullorEmpty(id)) {
			Long.parseLong(id);
		}
	}

}
