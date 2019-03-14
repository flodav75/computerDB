package fr.excilys.ui;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.model.Company;
import fr.excilys.model.Computer;

public class Menu {

	public static void displayMenu() {
		System.out.println(
				"--------------Menu-----------------\n"
				+ "ALLCOMPUTERS	List computers\n" + "ALLCOMPANIES	List companies\n"
				+ "DETAILS	    Show computer details\n" + "ADD	        Create a computer\n"
				+ "UPDATE	 	Update a computer\n" + "REMOVE		Delete a computer\n" + 
				"DELETE\n"+"EXIT          Exit app"
				+ "                                     ");
	}

	public static void displayLogSuccess(String request) {
		Logger fzef = LoggerFactory.getLogger(Menu.class);
		fzef.info("Request \"" + request + "\" achieved succefully");
	}

	public static void displayLogerror(String request) {
		Logger fzef = LoggerFactory.getLogger(Menu.class);
		fzef.error(request);
	}

	public static void displayComputer(Computer comp) {
		System.out.println(
				"Computer name : " + comp.getName() + "                  " + "introduced : " + comp.getIntroduced()
						+ " discontinues : " + comp.getDiscontinued() + " company  : " + comp.getCompany().getName());
	}

	public static void displayDeleteComputer(Computer comp) {
		displayLogSuccess("deleted computer");
		System.out.println("this computer has been deleted : ");
		displayComputer(comp);
	}
	

	public static void displayListComputers(List<Computer> computers) {
		displayLogSuccess("get all computers");
		for (Computer comp : computers) {
			Menu.displayComputer(comp);
		}
	}

	public static void displayListCompanies(List<Company> companies) {
		displayLogSuccess("get all companies");
		for (Company comp : companies) {
			System.out.println("Company name: " + comp.getName());
		}
	}

	public static void displayInputErrorId(String id) {
		displayLogerror("Sorry our database didn't find computer for id: " + id);
	}

	public static void displayInputCreate() {
		displayLogerror("Sorry there is error when you typed computer informations ");
	}

	public static void displayConnectionErrorDB() {
		displayLogerror("Sorry system got issues when he tried to communicate with database ");
	}

	public static void askingIdInformation() {
		System.out.println("Could you please specify an id :");
	}

	public static void displayComputerAttribut(String attribute) {
		StringBuilder message = new StringBuilder("Fill attribute ");
		message.append(attribute);
		switch (attribute) {
		case ("name"):
			message.append(" (this valus has to be filled):");
			break;
		case ("introduced"):
			message.append(" please use this format(\"yyyy-dd-mm\") if value is null type \"null\"): ");
			break;
		case ("discontinued"):
			message.append(" please use this format(\"yyyy-dd-mm\") if value is null type \"null\"): ");
			break;
		case ("company_id"):
			message.append(" if value is null type \"null\"): ");
			break;
		}
		System.out.print(message);
	}

	public static void displayBy() {

		System.out.println("See you soon");
	}

	public static void displayErrorTypedCommand() {
		displayLogerror("Sorry your command line is wrong");
	}

	public static void displayErrorDate(String date) {
		displayLogerror(
				"Sorry You don't use good date format.\n" + "your typed date : " + date + " but format is yyyy-mm-dd");
	}

}
