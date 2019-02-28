package fr.excilys.mappers.validations;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import fr.excilys.dtos.ComputerDTO;
import fr.excilys.exceptions.ComputerNameException;
import fr.excilys.exceptions.DateFormatException;

public class ValidationComputerDTO {
private static final String [] BAD_NAME_VALUES = {"'",";",".","*"};

	public static void validate(ComputerDTO comptDTO) throws ComputerNameException, ParseException, NumberFormatException, DateFormatException {
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

	private static void valideDate(String date) throws ParseException, DateFormatException {
		if (!isNullorEmpty(date)) {
			dateFormat(date);
		}

	}
	
	

	@SuppressWarnings("deprecation")
	private static void dateFormat(String dateString) throws ParseException, DateFormatException {
		String dateToTest = dateString ;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = dateFormat.parse(dateToTest);
		System.out.println(date.getYear());
		System.out.println(date.getMonth());
		System.out.println(date.getDay());
		
//		if(date.getYear()<=2036 && date.getYear()>=1950 && date.getDay()<=31 &&date.getDay()>=1 && date.getMonth()<=12 && date.getMonth()>=1 ) {
//			
//		}else {
//			throw new  DateFormatException();
//		}

	}
	
	private static void valideName(String name) throws ComputerNameException {
		if(isNullorEmpty(name)) {
		 throw new ComputerNameException();
		}else {
			valideNameValue(name);
			
		}
	}
	private static void valideNameCompany(String name) throws ComputerNameException {
		if(!isNullorEmpty(name)) {
			valideNameValue(name);
		}
	}
	
	private static void valideNameValue(String name) throws ComputerNameException {
		for(int i=0;i<BAD_NAME_VALUES.length;i++) {
			if(name.contains(BAD_NAME_VALUES[i])) {
				throw new ComputerNameException();
			}
		}
	}
	
	private static void valideId(String id)throws NumberFormatException {
		if(!isNullorEmpty(id)) {
			Long.parseLong(id);
		}
	}

}
