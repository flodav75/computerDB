package fr.excilys.controller;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.excilys.model.Company;
import fr.excilys.model.Computer;
import fr.excilys.model.ECommandeLine;
import fr.excilys.persistence.dao.ComputerDaoImpl;
import fr.excilys.service.CompanyService;
import fr.excilys.service.CompanyServiceImpl;
import fr.excilys.service.ComputerService;
import fr.excilys.service.ComputerServiceImpl;
import fr.excilys.ui.Menu;

public class Controller {

	private ComputerService computerSer;
	private CompanyService companySer;
	private Boolean isRunning;
	public static Controller instance;
	private static int DEFAULTID = 0;
	public final static List<String> GOOD_COMMAND_LINE = Arrays.asList("ALLCOMPUTERS", "ALLCOMPANIES", "DETAILS", "ADD",
			"UPDATE", "REMOVE", "EXIT");

	private Controller() {
		this.isRunning = true;
		this.computerSer = ComputerServiceImpl.getInstance();
		this.companySer = CompanyServiceImpl.getInstance();
		start();
	}
 
	public static Controller getInstance() {
		if (instance == null) {
			instance = new Controller();
		}
		return instance;
	}

	private void start() {
		while (this.isRunning) {
			
			Menu.displayMenu();
			checkUserRequest(readValue());
		}
	}

	private void checkUserRequest(String userRequest) {
	
		String userValueUpper = userRequest.toUpperCase();
		if (GOOD_COMMAND_LINE.contains(userValueUpper)) {
			switch (ECommandeLine.valueOf(userValueUpper)) {
			case ALLCOMPUTERS:
				try {
					Menu.displayListComputers(getListComputers());
				} catch (SQLException e) {
					e.printStackTrace();
					Menu.displayConnectionErrorDB();
				}
				break;
			case ALLCOMPANIES:
				try {
					Menu.displayListCompanies(getListCompanies());
				} catch (SQLException e) {
					e.printStackTrace();
					Menu.displayConnectionErrorDB();
				}
				break;
			case DETAILS:
				Menu.displayComputer(askAndGetComputer());
				break;
			case ADD:
				addComputerAttribute();
				break;
			case UPDATE:
				try {
					updateComputer(askAndGetComputer());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case REMOVE:

				try {
					Computer comp = askAndGetComputer();
					deleteComputer(comp);
					Menu.displayDeleteComputer(comp);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case EXIT:
				this.isRunning = false;
				// create
				break;
			default:
				Menu.displayErrorTypedCommand();
			}
		} else {
			Menu.displayErrorTypedCommand();
		}
	}

	private List<Computer> getListComputers() throws SQLException {
		List<Computer> computers = null;
		computers = this.computerSer.getAll();
		return computers;
	}

	private List<Company> getListCompanies() throws SQLException {
		List<Company> companies = null;
		companies = this.companySer.getAll();
		return companies;
	}

	private Computer getComputer(long id) throws SQLException {
		Computer computer = null;
		computer = this.computerSer.getById(id);
		return computer;
	}

	private void deleteComputer(Computer comp) throws SQLException {
		this.computerSer.remove(comp);
	}

	private String readValue() {
		@SuppressWarnings("resource")
		Scanner scn = new Scanner(System.in);
		String valuetoRead = scn.nextLine();
		return valuetoRead;
	}

	private void addComputerAttribute() {
		String tabAttribut[] = ComputerDaoImpl.ATTRIBUTLIST;
		String valueUser = null;
		List<String> computerValues = new ArrayList<String>();
		//Stream<String> st = computerValues.stream();
		for (int i = 1; i < tabAttribut.length; i++) {
			Menu.displayComputerAttribut(tabAttribut[i]);
			valueUser = readValue();
			computerValues.add(valueUser);
		}
		try {
			String name = getUserValueCleanName(computerValues.get(0));
			Date introduced = getUserValueCleanDate(computerValues.get(1));
			Date discontinued = getUserValueCleanDate(computerValues.get(2));
			Company company= createCompany(computerValues.get(3));
			
			try {
				this.computerSer.add(new Computer(DEFAULTID, name, introduced, discontinued,company ));
			} catch (SQLException e) {
				e.printStackTrace();
				Menu.displayInputCreate();
			}
		} catch (Exception e) {
			e.printStackTrace();
			Menu.displayInputCreate();
		}

	}

	private Company createCompany(String idCompany) {
		Company company = null;
		if(getUserValueCleanId(idCompany) != null) {
			company= new Company(getUserValueCleanId(idCompany));
		}
		return company;
	}

	private Date getUserValueCleanDate(String userValue) {
		Date userValueReturn = null;
		if (!isNullValue(userValue)) {
			userValueReturn = formatDate(userValue);
		} 
		return userValueReturn;
	}

	private Long getUserValueCleanId(String userValue) {
		Long userValueReturn =null;
		if (!isNullValue(userValue)) {
			userValueReturn = Long.parseLong(userValue);
		}
		return userValueReturn;
	}

	private String getUserValueCleanName(String userValue) throws Exception {
		String userValueReturn = null;
		if (!isNullValue(userValue)) {
			userValueReturn = userValue;
		} else {
			throw new Exception();
		}
		return userValueReturn;
	}

	private boolean isNullValue(String value) {
		boolean returnValue = false;
		if ("null".equals(value.toLowerCase()) || value.isEmpty()) {
			returnValue = true;
		}
		return returnValue;
	}

	private void updateComputer(Computer comp) throws Exception {
		String tabAttribut[] = ComputerDaoImpl.ATTRIBUTLIST;
		String valueUser = null;
		List<String> computerValues = new ArrayList<>();
		if(comp != null) {
			Menu.displayComputer(comp);
			for (int i = 1; i < tabAttribut.length; i++) {
				Menu.displayComputerAttribut(tabAttribut[i]);
				valueUser = readValue();
				computerValues.add(valueUser);
			}
			try {
				comp.setName(getUserValueCleanName(computerValues.get(0)));
				comp.setIntroduced(getUserValueCleanDate(computerValues.get(1)));
				comp.setDiscontinued(getUserValueCleanDate(computerValues.get(2)));
				comp.setCompany(new Company(getUserValueCleanId(computerValues.get(3))));
				try {
					this.computerSer.update(comp);
				} catch (SQLException e) {
					e.printStackTrace();
					Menu.displayInputCreate();
				}
			} catch (Exception e) {
				e.printStackTrace();
				Menu.displayInputCreate();
			}
		}else {
			throw new Exception();

		}
		

	}

	private Date formatDate(String date) {
		Date dateReturn = null;
		try {
			String newDate = date + " 00:00:00";
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			dateReturn = dateFormat.parse(newDate);

		} catch (Exception e) {
			e.printStackTrace();
			Menu.displayErrorDate(date);
		}
		return dateReturn;
	}

	public Computer askAndGetComputer() {

		Menu.askingIdInformation();
		String idString = readValue();
		Computer comp = null;
		try {
			long id = Long.parseLong(idString);
			comp = getComputer(id);

		} catch (NumberFormatException | SQLException e) {
			// Logger fzef= LoggerFactory.getLogger(getClass());
			Menu.displayInputErrorId(idString);
		}
		return comp;
	}

}
