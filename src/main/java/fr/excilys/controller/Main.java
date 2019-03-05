package fr.excilys.controller;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Main {

public static void main(String[] args) {
	Timestamp f= convertToTimesTamp(LocalDate.of(1995, 01, 01));
	System.out.println(f);
  }
private static Timestamp convertToTimesTamp(LocalDate date) {
	LocalDate localDate = date;//For reference
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-dd-MM");
	String formattedString = localDate.format(formatter);
	System.out.println(formattedString);
	Timestamp dateReturn = null;
		if (date != null) {
			try {
				//DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
				dateReturn =Timestamp.valueOf(formattedString+ " 00:00:00"); 
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}
	return dateReturn;
}
}