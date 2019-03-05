package fr.excilys.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import fr.excilys.exceptions.CompanyDAOException;
import fr.excilys.exceptions.ComputerDAOException;
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
				} catch (CompanyDAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ComputerDAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				break;
			case ALLCOMPANIES:

				try {
					Menu.displayListCompanies(getListCompanies());
				} catch (CompanyDAOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
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

				Computer comp = askAndGetComputer();
				try {
					deleteComputer(comp);
				} catch (ComputerDAOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Menu.displayDeleteComputer(comp);

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

	private List<Computer> getListComputers() throws CompanyDAOException, ComputerDAOException {
		List<Computer> computers = null;
		computers = this.computerSer.getAll(10, 1);
		return computers;
	}

	private List<Company> getListCompanies() throws CompanyDAOException {
		List<Company> companies = null;
		companies = this.companySer.getAll();
		return companies;
	}

	private Computer getComputer(long id) throws CompanyDAOException, ComputerDAOException {
		Computer computer = null;
		computer = this.computerSer.getById(id);
		return computer;
	}

	private void deleteComputer(Computer comp) throws ComputerDAOException {
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
		// Stream<String> st = computerValues.stream();
		for (int i = 1; i < tabAttribut.length; i++) {
			Menu.displayComputerAttribut(tabAttribut[i]);
			valueUser = readValue();
			computerValues.add(valueUser);
		}
		try {
			String name = getUserValueCleanName(computerValues.get(0));
			Date introduced = getUserValueCleanDate(computerValues.get(1));
			Date discontinued = getUserValueCleanDate(computerValues.get(2));
			Company company = createCompany(computerValues.get(3));

			this.computerSer.add(new Computer(DEFAULTID, name, introduced, discontinued, company));

		} catch (Exception e) {
			e.printStackTrace();
			Menu.displayInputCreate();
		}

	}

	private Company createCompany(String idCompany) {
		Company company = null;
		if (getUserValueCleanId(idCompany) != null) {
			company = new Company(getUserValueCleanId(idCompany));
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
		Long userValueReturn = null;
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
		if (comp != null) {
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
				this.computerSer.update(comp);

			} catch (Exception e) {
				e.printStackTrace();
				Menu.displayInputCreate();
			}
		} else {
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

		} catch (NumberFormatException e) {
			// Logger fzef= LoggerFactory.getLogger(getClass());
			Menu.displayInputErrorId(idString);
		} catch (CompanyDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ComputerDAOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return comp;
	}

}
